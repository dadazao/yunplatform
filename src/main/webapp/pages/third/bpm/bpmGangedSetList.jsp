<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>联动设置</title>
<style type="text/css">
	.change-panel{
		white-space: nowrap;
	}
</style>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript">
	var table = null
		globalFormkey = 0,
		nodeSelectHtml = '',
		choseFieldsHtml = '',
		changeFieldsHtml = '';
	
	//生成guid
	function guidGen(){
		var G = function(){
			return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
		};
		return (G()+G()+"-"+G()+"-"+G()+"-"+G()+G()+G());
	};

	function cascadeSet(obj){
		GangedSetCascade(obj,cascadeSetCallback);
	}

	function cascadeSetCallback(t){
		var guid = t.guid,		
			span = $("span[name='change-type-span'][key='"+guid+"']");
		delete t.guid;
		span.data("cascade",t);
	};
	
	$(function(){
		table = $("#bpmGangedSetItem");
		
		$("a.add").click(function(){
			$("tr.empty",table).remove();
			if(!globalFormkey)
				choseFieldsHtml = '';

			var newTr = getNewTr(0);
			table.append(newTr);
			$("select[name='choose-field']",newTr).trigger("change");
		});
		$("a.clean").click(function(){
			var pks = $("input.pk:checked");
			if(pks.length==0){
				$.ligerDialog.warn($lang.operateTip.selectRecord,$lang.tip.msg);
				return;
			}
			$.ligerDialog.confirm($lang.operateTip.sureDelete,$lang.tip.msg,function(r){
				if(r){
					pks.each(function(){
						$(this).parents("tr").remove();
					});
					if($("tr",table).length<2){
						table.append('<tr class="empty"><td colspan="6">'+$lang.tip.notRecord+'</td></tr>');
					}
				}
				else{
					return;
				}
			});
		});
		//判断 当选择的是select字段时，变更类型 添加 级联
		$("#bpmGangedSetItem").delegate("select[name='change-field']","change",function(){
			var	me = $(this),
				nextTd = me.parents("td").next(),
				spanGuid = me.parents("span").attr("key"),
				changeSpan = $("span[key='"+spanGuid+"']",nextTd),
				changeSelect = changeSpan.find("select");

			changeSelect.val('1');
			$("a.done",changeSpan).hide();
		});

		//变更类型下拉框
		$("#bpmGangedSetItem").delegate("select[name='changeTypeSel']","change",function(){
			var me = $(this),
				selectOpt = me.find("option:selected");

			me.siblings("a.done").hide();
			if(selectOpt.val()=='8'){
				var span = me.parents("span"),
					fowardTd = span.parents("td").prev(),
					guid = span.attr("key"),
					changeSelectOpt = $("span[key='"+guid+"']",fowardTd).find("option:selected"),
					controltype = changeSelectOpt.attr("controltype");

				if(!controltype||controltype!="11"){
					$.ligerDialog.warn("只有下拉框才支持级联变换");
					me.val('1');
				}
				else{					
					me.siblings("a.done").show();
				}
			}
		});
		$("#bpmGangedSetItem").delegate("a.done","click",function(){
			var me = $(this),
				span = me.parents("span"),
				fowardTd = span.parents("td").prev(),
				guid = span.attr("key"),
				changeSelectOpt = $("span[key='"+guid+"']",fowardTd).find("option:selected");

			var ops = changeSelectOpt.attr("ops"),
				text = changeSelectOpt.text(),
				cascade = span.data("cascade"),
				obj = {};

			obj.guid = guid;
			obj.ops = ops;
			obj.text = text;
			obj.cascade = cascade;
			cascadeSet(obj);
		});
		$("a.save").click(function(){
			parse2json(table);
		});
		bindEvent();
		initNodeSelect(${nodes});
	});
	
	//保存时，将设置好的联动字段解析为json字符串
	function parse2json(m){
		var html = [],
			defId = $("input[name='defid']").val(),	
			json="",
			validResult = true;
		$("tr",m).each(function(){
			var firstTd = $(this).find("td:first"),
				td = firstTd.next();
			
			if(!td.length)return true;
			var	nodeOption = td.find("select").find("option:selected"),
				obj = {},
				nodeId = nodeOption.attr("nodeid");
			
			obj.id = $("input.pk",firstTd).val();
			obj.defId = defId;
			obj.nodeId = nodeId?nodeId:"";
			obj.nodeName = nodeOption.text();
			obj.chooseField = [];
			obj.changeField = [];
			
			//解析选择框字段
			td = td.next();
			var nextTd = td.next(),
				chooseOption = $("select",td).find("option:selected");
			
			for(var i=0,c;c=chooseOption[i++];){
				var c = $(c),
					curSpan = c.parents("span"),
					chooseVal = $("span[key='"+curSpan.attr("key")+"']",nextTd).find("select > option:selected").val();
				if(!chooseVal){
					validResult = false;
					$.ligerDialog.error($lang_bpm.bpmGangedSet.chooseNotSetTip,$lang.tip.msg);
					return false;
				}
				var chooseObj = {};
				chooseObj.id = c.val();
				chooseObj.name = c.text();
				chooseObj.key = c.attr("key");
				chooseObj.value = chooseVal;
				obj.chooseField.push(chooseObj);
			}
			
			//解析变更字段
			td = nextTd.next();
			var nextTd = td.next(),
				changeOption = $("select",td).find("option:selected");
			
			if(changeOption.length==0){
				validResult = false;
				$.ligerDialog.error($lang_bpm.bpmGangedSet.changeNotSetTip,$lang.tip.msg);
				return false;
			}
			
			for(var i=0,c;c=changeOption[i++];){
				var c = $(c),
					curSpan = c.parents("span"),
					changeTypeSpan = $("span[key='"+curSpan.attr("key")+"']",nextTd),
					changeTypeSelect = changeTypeSpan.find("select"),
					changeType = changeTypeSelect.val(),
					cascade = changeTypeSpan.data('cascade');
				if(!changeType){
					validResult = false;
					$.ligerDialog.error($lang_bpm.bpmGangedSet.changeTypeNotSetTip,$lang.tip.msg);
					return false;
				}
				var changeObj = {};
				
				changeObj.id = c.val();
				changeObj.name = c.text();
				changeObj.key = c.attr("key");
				changeObj.type = changeType;
				changeObj.fieldtype = c.attr('fieldtype');
				if(changeType=='8')
					changeObj.cascade = cascade; 
				obj.changeField.push(changeObj);
			}
			html.push(obj);
		});

		if(!validResult)return;
		
		if(html.length>0)
			json = JSON2.stringify(html);
		
		var url = __ctx + "/platform/bpm/bpmGangedSet/save.ht";
		$.post(url,{json:json,defid:defId},function(t){
			t = eval("("+t+")");
			if(t.result)
				$.ligerDialog.success(t.message,$lang.tip.msg);
			else
				$.ligerDialog.error(t.message,$lang.tip.msg);
		});
	};
	
	//获取字段变换类型
	function getChangeType(){
		var html = ['<select name="changeTypeSel"><option value="1">隐藏</option><option value="2">显示</option>'];
		html.push('<option value="3">只读</option><option value="4">非只读</option>');
		html.push('<option value="5">必填</option><option value="6">非必填</option>');
		html.push('<option value="7">置空</option>');
		html.push('<option value="8">级联</option>');
		html.push('</select><a class="link done" style="display:none;">级联设置</a>');
		return html.join('');
	};

	//解析选择字段json
	function parseChooseField(t,json){
		var td = t.parents("td");
		
		for(var i=0,c;c=json[i++];){
			addChooseItem(t,c);
		}
	};
	
	//解析变更字段json
	function parseChangeField(t,json){
		var f = json.shift(),
			td = t.parents("td");
		//第一个item直接处理
		var cSelect = $("span",td).find("select");
		
		var vSpan = $("span",td.next()),
			vSelect = vSpan.find("select");

		if(f.cascade)
			vSpan.data('cascade',f.cascade);
		
		cSelect.find("option").each(function(){
			var key = $(this).attr("key");
			if(key==f.key){
				$(this).attr("selected",true);
				return false;
			}
		}).trigger("change");
		vSelect.val(f.type).trigger("change");
		
		//其余的item添加以后处理
		for(var i=0,c;c=json[i++];){
			addChangeItem(t,c);
		}
	};
	
	//初始化数据
	function init(t){
		if(!t){
			return false;
		}
		t = $.parseJSON(t);
		if(t&&t.length>0){
			$("tr.empty",table).remove();
			for(var i=0;i<t.length;i++){
				var c = t[i],
					choiseField = c.choisefield,
					firstChoise = choiseField.shift(),
					newTr = getNewTr(c.id,firstChoise);
				table.append(newTr);

				var td = newTr.find("td:first").next();
				//选中对应的节点
				$("select",td).find("option").each(function(){
					var txt = $(this).text();
					if(txt==c.nodename){
						$(this).attr("selected",true);
						return false;
					}
				}).trigger("change");
				
				//通过解析选择框字段来构建html代码
				parseChooseField($("span",td.next()),choiseField);
				//通过解析变更字段来构建变更html代码
				parseChangeField($("span",td.next().next().next()),c.changefield);
			}
			return true;
		}
		return false;
	};
	
	//通过主键获取到对应的tr
	function getTrById(id){
		var tr = null;
		
		$("tr",table).each(function(){
			var td = $(this).find("td:first"),
				pk = $("input.pk",td).val();
			if(pk==id){
				tr = $(this);
				return false;
			}
			else
				return true;
		});
		return tr;
	};
	
	//初始化流程节点
	function initNodeSelect(t){
		var html = ['<select name="choose-node">'],
			tag = false;
		for(var i=0;i<t.length;i++){
			var c = t[i];
			if(!c)continue;
			if(!c.nodeId&&!c.nodeName&&c.setId){	
				html.push('<option setId="');
				html.push(c.setId);
				html.push('" value="');
				html.push(c.formKey);
				html.push('" nodeid="');
				html.push(c.setType);
				if(c.setType==2){					
					html.push('">所有节点</option>');
					globalFormkey = c.formKey;	
				}
				if(c.setType==1){
					tag = true;
					html.push('">起始节点</option>');
				}
			}
			else{
				html.push('<option setId="');
				html.push(c.setId);
				html.push('" value="');
				html.push(c.formKey);
				html.push('" nodeid="');
				html.push(c.nodeId);
				html.push('">');
				html.push(c.nodeName);
				html.push('</option>');
			}
		}
		
		if(!tag){
			html.push('<option setId="');
			html.push('0');
			html.push('" nodeid="');
			html.push('1');
			html.push('" value="');
			html.push(globalFormkey);
			html.push('">起始节点</option>');
		}
		
		html.push('</select>');
		nodeSelectHtml = html.join('');
		getChooseFieldHtml(function(t){
			choseFieldsHtml = t;
			getChooseFieldHtml(function(t){
				changeFieldsHtml = t;
				var setList = '${bpmGangedSetList}';
				var initResult = init(setList);
				if(!initResult){
					$("tr.empty").find("td").html($lang.tip.notRecord);
				}
			},0);
		},1);
	};
	
	//获取该表单的所有主表、子表字段
	function getChooseFieldHtml(callback,filter,setId){
		if(!setId)setId = globalFormkey;//如果没有传递某个节点的setId，则使用全局节点setId
		if(!setId){
			callback('');
			return;
		}
		var url = __ctx+"/platform/bpm/bpmGangedSet/getFields.ht";
		$.post(url,{formKey:setId,filter:filter},function(t){
			var fields = eval("("+t+")"),
				html = [],
				size = fields.length;
			for(var i=0;i<size;i++){
				var c = fields[i];
				if(!c)continue;
				html.push('<optgroup label="');
				html.push(c.name);
				html.push('" ismain="');
				html.push(c.type);
				html.push('">');
				for(var j=0,m;m=c.fields[j++];){
					html.push('<option value="');
					html.push(m.fieldId);
					html.push('" ops="');
					html.push(JSON2.stringify(m.jsonOptions).jsonEscape());
					html.push('" fieldtype="');
					html.push(m.type);
					html.push('" controltype="');
					html.push(m.controlType);
					html.push('" key="');
					html.push(m.type==1?m.fieldName:((c.type==1?"m:":"s:") +c.key+":"+m.fieldName));
					html.push('" ');
					html.push(m.type==1?'style="background:red"':'');
					html.push(' >');
					html.push(m.fieldDesc?m.fieldDesc:m.fieldName);
					html.push('</option>');
				}
				html.push('</optgroup>');
			}
			callback(html.join(''));
		});
	};
	
	//添加选择框字段值（包括选择值）
	function addChooseItem(t,m){
		var td = t.parents("td"),
			nextTd = td.next(),
			//选择框字段和选择框值之间通过guid关联
			guid = guidGen();

		var cSelect = $('<select class="fieldsSelect" name="choose-field"></select>').html(choseFieldsHtml),
			cSpan = $('<span class="change-panel"></span>').attr("key",guid).append(cSelect).append('<a href="javascript:;" class="remove-field">'+$lang.button.del+'</a>');
		
		td.append('<p><br /><br /></p>').append(cSpan);

		var vSelect = $('<select></select>'),
			vSpan = $('<span></span>').attr("key",guid).append(vSelect);
			
		nextTd.append('<p><br /><br /></p>').append(vSpan);

		if(m){
			cSelect.find("option").each(function(){
				var key = $(this).attr("key");
				if(key==m.key){
					$(this).attr("selected",true);
					return false;
				}
			});
			
			updateChooseVal.call(cSelect,m.value);
		}
		else{
			cSelect.trigger("change");
		}
	};
	
	//添加变更字段（包括变更类型）
	function addChangeItem(t,m){
		var td = t.parents("td"),
			nextTd = td.next(),
			guid = guidGen();

		var cSelect = $('<select class="fieldsSelect" name="change-field"></select>').html(changeFieldsHtml),
			cSpan = $('<span class="change-panel"></span>').attr("key",guid).append(cSelect).append('<a href="javascript:;" class="remove-field">'+$lang.button.del+'</a>'),
			vSelect = $(getChangeType()),
			vSpan = $('<span class="change-panel" name="change-type-span"></span>').attr("key",guid).append(vSelect);

		if(m){
			cSelect.find("option").each(function(){
				var key = $(this).attr("key");
				if(key==m.key){
					$(this).attr("selected",true);
					return false;
				}
			});
			
			$(vSelect[0]).val(m.type);
			if(m.cascade)
				vSpan.data('cascade',m.cascade);
		}

		cSelect.trigger("change");
		
		td.append('<p><br /><br /></p>').append(cSpan);
		nextTd.append('<p><br /><br /></p>').append(vSpan);
		
		$(vSelect[0]).trigger("change");
	};
	
	//绑定事件
	function bindEvent(){		
		//选择不同的节点时，更新选择框选项
		$("select[name='choose-node']").live("change",function(){
			var nodeFormKey = parseInt($(this).val()),
				tr = $(this).parents("tr");
			if(!nodeFormKey&&!globalFormkey){
				//未设置全局表单，所选节点也没设置表单，则清空选择字段和变更字段HTML
				choseFieldsHtml = '';
				$.ligerDialog.warn($lang_bpm.bpmGangedSet.notBindForm,$lang.tip.msg,function(){
					//移除选择框中的所选值
					$("select.fieldsSelect",tr).empty().trigger("change");
				});
			}
			else{
				if(globalFormkey == nodeFormKey)return;
				if(nodeFormKey)
					getChooseFieldHtml(function(t){
						choseFieldsHtml = t;
						$("select.fieldsSelect",tr).empty();
						$("select.fieldsSelect",tr).append(choseFieldsHtml).trigger("change");
						getChooseFieldHtml(function(t){
							changeFieldsHtml = t;
						},0,nodeFormKey);
					},1,nodeFormKey);
			}
		});
		//添加选择框
		$("a.add-chose-field").live("click",function(){
			addChooseItem($(this));
		});
		//添加变更字段
		$("a.add-change-field").live("click",function(){
			addChangeItem($(this));
		});
		//选择不同的字段时，更新不同的选择值
		$("select[name='choose-field']").live("change",updateChooseVal);
		//移除选择框、变更字段
		$("a.remove-field").live("click",function(){
			var span = $(this).parents("span"),
				td = $(this).parents("td"),
				nextTd = td.next();
			span.prev("p").remove();
			span.remove();
			$("span[key='"+span.attr("key")+"']",nextTd).prev("p").remove();
			$("span[key='"+span.attr("key")+"']",nextTd).remove();
		});
	};
	
	function updateChooseVal(d){
		var option = $(this).find("option:selected").attr("ops"),
			span = $(this).parents("span"),
			td = $(this).parents("td"),
			nextTd = td.next(),
			cValSel = $('<select></select>'),
			options = [];
		
		if(option){
			options = eval("("+option+")");
		}
		
		for(var i=0,c;c=options[i++];){
			var opt = $('<option></option>').val(c.key).text(c.value);
			cValSel.append(opt);
		}
		
		var s = $("span[key='"+span.attr("key")+"']",nextTd);
		
		s.empty().append(cValSel);
		if(d){
			cValSel.val(d);
		}
	};
	
	//添加一条联动设置	
	function getNewTr(id,data){
		var guid1 = guidGen(),
			guid2 = guidGen();

		var td1Input = $('<input type="checkbox" class="pk" name="id" />').val(id),
			td1 = $('<td rowspan="1"></td>').append(td1Input),
			td2 = $('<td rowspan="1"></td>').html(nodeSelectHtml),
			td3 = $('<td></td>'),
			td4 = $('<td></td>'),
			vSelect = $('<select class="fieldsSelect" name="change-field"></select>').html(changeFieldsHtml),
			vSpan = $('<span class="change-panel"></span>').attr("key",guid2).append(vSelect).append('<a href="javascript:;" class="add-change-field">'+$lang.button.add+'</a>'),
			td5 = $('<td></td>').append(vSpan),
			vVal = $(getChangeType()),
			vValSpan = $('<span class="change-panel" name="change-type-span"></span>').attr("key",guid2).append(vVal),
			td6 = $('<td></td>').append(vValSpan);

		if(data||id=='0'){
			var cSelect = $('<select class="fieldsSelect" name="choose-field"></select>').html(choseFieldsHtml),
				cSpan = $('<span class="change-panel"></span>').attr("key",guid1).append(cSelect).append('<a href="javascript:;" class="add-chose-field">'+$lang.button.add+'</a><a href="javascript:;" class="remove-field">'+$lang.button.del+'</a>');
			td3.append(cSpan);
			var cVal = $('<span></span>').attr("key",guid1).append('<select></select>');
			td4.append(cVal);
		}
		var	newTr = $('<tr></tr>').append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);

		if(data){
			cSelect.find("option").each(function(){
				var key = $(this).attr("key");
				if(key==data.key){
					$(this).attr("selected",true);
					return false;
				}
			});
			updateChooseVal.call(cSelect,data.value);
		}
		return newTr;
	};
</script>
<style type="text/css"> 
    body{ padding:0px; margin:0;overflow:auto;}
    #bpmGangedSetItem td{
    	text-align: center;
    }
    .add-chose-field{
    	margin-left:5px;
    }
    .remove-field{
    	margin-left:5px;
    }
</style>
</head>
<body>
	 <jsp:include page="incDefinitionHead.jsp">
   		<jsp:param value="联动设置" name="title"/>
	</jsp:include>
	 <div class="panel-container">
		<f:tab curTab="gangedSet" tabName="flow"/>
		<div class="panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span class="tbar-label">联动设置</span>
				</div>
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link save" href="javascript:;"><span></span>保存</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link add" href="javascript:;"><span></span>添加</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link clean" href="javascript:;"><span></span>删除</a></div>
					</div>	
				</div>
			</div>
			<div class="panel-body">
				<input type="hidden" name="defid" value="${defid}"/>
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
				<div class="panel-table">
					<table id="bpmGangedSetItem" cellpadding="1" cellspacing="1" border="1" style="border-collapse:collapse;" class="table-grid">
						<thead>
							<tr>
								<th><input type="checkbox" id="chkall"></th>
								<th>节点名</th>
								<th>所选字段</th>
								<th>所选值</th>
								<th>变换字段</th>
								<th>变换类型</th>
							</tr>
						</thead>
						<tbody>
							<tr class="empty">
								<td colspan="6">
									<div class="loading-div">正在加载...</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>			
			</div>				
		</div>
	</div>
</body>
</html>