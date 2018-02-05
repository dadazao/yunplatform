<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<title>人员设置</title>
<f:js pre="js/lang/view/platform/bpm" ></f:js>
<base target="_self" />
<style type="text/css">
	html{
		overflow:auto;
	}
	body{
		overflow:auto;
		height: 100%;
	}
	.inputChange{
		background-color: #BBAAAA!important;
	}
</style>
<script type="text/javascript">
	$(function(){
		$("a.moveup,a.movedown").unbind("click").click(function(){
			sortCondition(this);
		});
		$("#chkall").click(function(){			
			var state=$(this).attr("checked");
		    if(!state)
		    	state = false;
			
	      	$("input[name='nodeUserCk']").each(function(){
				$(this).attr("checked",state);
	        });
		});		
		$("body").delegate("tr.odd,tr.even","mouseover",function(){
			$(this).addClass("over");
		}).delegate("tr.odd,tr.even","mouseout",function(){
			$(this).removeClass("over");
		});

		$("tr.odd,tr.even").each(function(){			
			$(this).bind("mousedown",function(event){
				if(event.target.tagName=="TD")  
					var strFilter='input:checkbox:enabled[name="nodeUserCk"]';
					var obj=$(this).find(strFilter);
					if(obj.length==1){
						var state=obj.attr("checked");
						obj.attr("checked",!state);
					}
				}
			);    
		});
		initConditionShow();

		$("body").delegate("input[name=groupNo]","change",changeGroupNo);
	});

	function initConditionShow(){
		$("td[name='conditionShow']").each(function(){
			var me = $(this),
				textarea = me.find("textarea"),
				value = textarea.val().trim();
			if(value=="")return true;
			value = eval("("+value+")");
			var conShow = [];
			for(var i=0,c;c=value[i++];){
				if(conShow.length>0){
					if(c.compType=="or")
						conShow.push(' '+ $lang.operateType.either +' ');
					else
						conShow.push(' '+$lang.operateType.also+' ');
				}
				conShow.push('(');
				conShow.push(c.conDesc);
				conShow.push(')');
			}
			textarea.before(conShow.join(''));
		});
	};
	
	//排序
	function sortCondition(btnObj){
		var obj=$(btnObj);
		var direct=obj.hasClass("moveup")?1:0;
		var curObj = obj.closest('tr');
		var tbodyObj = obj.closest('tbody');
		if(direct==1){
			var prevObj=curObj.prev();
			if(prevObj!=null){
				curObj.insertBefore(prevObj);	
			}
		}
		else{
			var nextObj=curObj.next();
			if(nextObj!=null){
				curObj.insertAfter(nextObj);
			}
		}
		updSn(tbodyObj);
		
	}
	//更新排序到后台
	function updSn(tbodyObj){
		var conditionObjs=$("input[name='nodeUserCk']",tbodyObj);
		if(conditionObjs.length<2)return;
		var aryCondition=[];
		conditionObjs.each(function(){
			aryCondition.push(this.value);
		});
		var conditionIds=aryCondition.join(",");
		$.post('${ctx}/platform/bpm/bpmUserCondition/updateSn.ht',{"conditionIds":conditionIds},function(data){
			var resultData=eval('('+data+')');
			if(resultData.result==1){
				
			}else{
				$.ligerDialog.warn(resultData.message);
			}
		});
	}
	//条件规则
	function conditionDialog(tableId,edit){
		var defVal = $("#bpmDefinition").val(),
			defJson = eval("("+defVal+")");
		var conditionType = ${conditionType};
		var hw = $.getWindowRect();
		dialogH = hw.height*19/20;
		dialogW = hw.width*19/20;
		
		var url=__ctx + '/platform/bpm/bpmDefinition/conditionEdit.ht?defId='+defJson.defId+'&conditionType='+conditionType+'&nodeId='+defJson.nodeId;
		url=url.getNewUrl();
		var winArgs="dialogWidth="+dialogW+"px;dialogHeight="+dialogH+"px;help:0;status:0;scroll:1;center:1;resizable:1";
	 	var rtn = window.showModalDialog(url,"",winArgs);
	 	refresh();
	};
	//编辑规则
	function editCondition(){
		var conditionObj = $("input[name='nodeUserCk']:checked");
		
		if(conditionObj.length!=1){
			$.ligerDialog.warn($lang_bpm.bpmDefinition.copyUserList.selectOneItem,$lang.tip.msg);
			return;
		}
		
		var conditionId = conditionObj.val(),
			defVal = $("#bpmDefinition").val(),
			defJson = eval("("+defVal+")");
		if(!conditionId){
			$.ligerDialog.warn($lang.operateTip.selectOneRecord,$lang.tip.msg);
			return;
		}
		var conditionType = ${conditionType};

		var hw = $.getWindowRect();
		dialogH = hw.height*19/20;
		dialogW = hw.width*19/20;
		var url=__ctx + '/platform/bpm/bpmDefinition/conditionEdit.ht?defId=' + defJson.defId + '&conditionType='+conditionType+'&conditionId='+ conditionId+"&nodeId="+defJson.nodeId;
		url=url.getNewUrl();
		var winArgs="dialogWidth="+dialogW+"px;dialogHeight="+dialogH+"px;help:0;status:0;scroll:1;center:1;resizable:1";
	 	var rtn = window.showModalDialog(url,"",winArgs);
	 	refresh();
	};
	
	//删除条件
	function delCondition(){
		var conditionObj = $("input[name='nodeUserCk']:checked");
		if(conditionObj.length==0){
			$.ligerDialog.warn($lang.operateTip.selectOneRecord,$lang.tip.msg);
			return;
		}
		var conditionIds = [];
		conditionObj.each(function(){
			conditionIds.push($(this).val());
		});

		conditionIds = conditionIds.join(',');
		
		var url =__ctx + '/platform/bpm/bpmUserCondition/delByAjax.ht';

		$.ligerDialog.confirm($lang.operateTip.sureDelete,$lang.tip.msg,function(r){
			if(r){
				$.post(url,{id:conditionIds},function(t){
					var resultData=eval('('+t+')');
					if(resultData.result){
						$.ligerDialog.success(resultData.message,$lang.tip.msg,function(rtn){
							refresh();
						});
					}else{
						$.ligerDialog.warn(resultData.message,$lang.tip.msg);
					}
				});
			}
		});
	};
	//消息设置
	function messageSet(conditionId){
		var defVal = $("#bpmDefinition").val(),
			defJson = eval("("+defVal+")");
		
		var url=__ctx + '/platform/system/message/copyEdit.ht?actDefId=' + defJson.actDefId;

		if(conditionId){
			url = url + '&conditionId=' + conditionId +'&messageSetType=2';
		}
		else{
			url = url + '&messageSetType=1';
		}
		var winArgs="height=500,width=700,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
		url=url.getNewUrl();
		window.open(url,"",winArgs);
	};
	//刷新页面
	function refresh(){
		if($.isIE()){
			// Get the link object to simulate user click
			var reload = document.getElementById('goLocation');
			// Assign the modal url to the link then click!
			reload.href = window.location.href;
			reload.click();
		}else{
			window.location.reload();
		}
	};


	/**
	* 分组号值变更
	*/
	function changeGroupNo(){
		var _this=$(this);
		
		var td = _this.closest("td");
		var tr = _this.closest("tr");

		var groupNo = _this.val();
		
		groupNo = groupNo.replace(/(^\s*0*)|(\s*$)/g,"");
		if(!/^\d+$/.test(groupNo)){
			groupNo=1;
		}
		_this.val(groupNo);
		
		$("div[name=groupNo]",td).text(groupNo).show();

		var url = __ctx+"/platform/bpm/bpmUserCondition/updateGroup.ht";

		var oldGroup = _this.attr("ivalue");

		if(oldGroup==groupNo){
			tr.removeClass("inputChange");
			return;
		}else{
			if(!tr.hasClass("inputChange"))
				tr.addClass("inputChange");
		}

	};


	/**
	* 保存分组号
	*/
	function saveGroupNo(obj){
		var conditionIds=[],
			groupNos=[];
		var url = __ctx+"/platform/bpm/bpmUserCondition/updateGroup.ht";
		var _this = $(obj);
		$("tbody.data").find("input[name=groupNo]").each(function(){
			var _this = $(this);
			if(_this.val()!=_this.attr("ivalue")){
				var groupNo = _this.val();
				var tr = _this.closest("tr");
				var conditionId = $("input[name='nodeUserCk']",tr).val();
				conditionIds.push(conditionId);
				groupNos.push(groupNo);
			}
		});
		if(conditionIds.length==0){
			return;
		}
		var params = {
				conditionIds:conditionIds.join(","),
				groupNos:groupNos.join(",")
			};
		$.post(url,params,function(data){
			var obj = new com.hotent.form.ResultMessage(data);
			if (obj.isSuccess()) {
				$.ligerDialog.success( obj.getMessage(),function(){
					window.location.reload();
				});
				
			} else {
				$.ligerDialog.err($lang.tip.msg,obj.getMessage());
			}			
		});
	};
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">抄送人员设置</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link ok" href="javascript:window.close()" ><span></span>确定</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" onclick="conditionDialog()"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" onclick="editCondition()"><span></span>编辑</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del" onclick="delCondition()"><span></span>删除</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link save" onclick="saveGroupNo(this)" id="btnSaveGroupNo" ><span></span>保存批次号</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<table id="condition-table" class="table-grid" cellpadding="1" cellspacing="1" border="0" >
				<thead>
					<tr>
						<th width="5%" nowrap="nowrap">
							<label><input type="checkbox" id="chkall" />序号</label>							
						</th>
						<th width="40%">规则描述</th>
						<th width="35%">用户描述</th>
						<th width="80" nowrap="nowrap">位置调整</th>
						<th width="90px" nowrap="nowrap">批次号</th>
					</tr>
				</thead>
				<tbody class="data">
					<c:forEach items="${receiverSettings}" var="item" varStatus="cnt">
						<tr id="${item.id}" class="data-tr ${cnt.index%2==0?'odd':'even'}" >
							<td>
								<input type="checkbox" name="nodeUserCk" value="${item.id}"/>&nbsp;${cnt.count}						
								<input type="hidden" name="sn" value="${item.sn}"/>
							</td>
							<td name="conditionShow">
								<textarea class="hidden">
									${item.condition}
								</textarea>							
							</td>
							<td>
								${item.conditionShow}
							</td>
							<td>
								<a class="link moveup" title="上移">&nbsp;</a>
								<a class="link movedown" title="下移">&nbsp;</a>
							</td>
							<td name="groupNoTd">
								<div style="width: 80px">
									<input name="groupNo" style="width:70px;" class="inputText" ivalue ="${item.groupNo}"  value="${item.groupNo}" />
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div class="hidden">
			<textarea id="bpmDefinition">
				{
					defId:"${bpmDefinition.defId}",
					actDefId:"${bpmDefinition.actDefId}",
					nodeId:"${nodeId}"
				}
			</textarea>
			<a href="" id="goLocation" style="display:none;"></a>
		</div>		
	</div>
</body>
</html>


