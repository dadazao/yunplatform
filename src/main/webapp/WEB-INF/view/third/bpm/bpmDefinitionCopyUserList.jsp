<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<title>抄送人员设置</title>
<f:js pre="js/lang/view/platform/bpm" ></f:js>
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
		initCcMessageType();
	});

	function initConditionShow(){
		$("td[name='conditionShow']").each(function(){
			var me = $(this),
				textarea = me.find("textarea"),
				value = textarea.val().trim();
			if(value=="")return true;
			value = eval("("+value+")");
			var conShow = [];
			compileConDesc(value,conShow)
			textarea.before(conShow.join(''));
		});
	};
	
	//组装规则描述
	function compileConDesc(json,text){
		for(var i=0,c;c=json[i++];){
			if(c.compType){
				text.push(c.compType=='or'?' 或者 ':' 并且 ');
			}
			if(c.branch){
				var branchDesc = ['('];
				compileConDesc(c.sub,branchDesc);
				branchDesc.push(')');
				text.push(branchDesc.join(''));
			}
			else{
				if(c.judgeCon2){
					text.push('(');
					text.push(c.conDesc);
					text.push(')');
				}
				else{
					text.push(c.conDesc);
				}
			}
		}
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
	//初始化抄送消息提醒类型
	function initCcMessageType(){
		var param = $("input[name='ccMessageType']", window.opener.document).val();
		if(param==null||param==""||param==undefined) {
			return;
		}
		var ccMessageType = eval("("+param+")");
		if(ccMessageType.inner==1){
			$("input[name='inner']").attr("checked","checked");
		}
		else {
			$("input[name='inner']").attr("checked",false);
		}
		if(ccMessageType.mail==1){
			$("input[name='mail']").attr("checked","checked");
		}
		else {
			$("input[name='mail']").attr("checked",false);
		}
		if(ccMessageType.sms==1){
			$("input[name='sms']").attr("checked","checked");
		}
		else {
			$("input[name='sms']").attr("checked",false);
		}
	}
	
	//获取抄送消息提醒类型
	function saveCcMessageType() {
		var inner = $("input[name='inner']").attr("checked")?1:0;
		var mail = $("input[name='mail']").attr("checked")?1:0;
		var sms = $("input[name='sms']").attr("checked")?1:0;
		var ccMessageType = "{\"inner\":"+inner+",\"mail\":"+mail+",\"sms\":"+sms+"}";
		$("input[name='ccMessageType']", window.opener.document).val(ccMessageType);
		window.close();
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
		var url=__ctx + '/platform/bpm/bpmDefinition/conditionEdit.ht?defId='+defJson.defId+'&conditionType=1&nodeId=-1';
		var winArgs="height=500,width=800,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
		url=url.getNewUrl();
		window.open(url,"",winArgs);	
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

		var url=__ctx + '/platform/bpm/bpmDefinition/conditionEdit.ht?defId=' + defJson.defId + '&conditionType=1&nodeId=-1&conditionId='+ conditionId;
		var winArgs="height=600,width=980,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
		url=url.getNewUrl();
		window.open(url,"",winArgs);
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

	//刷新页面
	function refresh(){
		window.location.reload(true);
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
					<div class="group"><a class="link save" onclick="saveCcMessageType()"><span></span>保存</a></div>
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
			<table class="table-grid" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<th width="20%" nowrap="nowarp">全局提醒消息类型: </th>
					<td>
						<label><input class="send_type" name="inner" type="checkbox"  checked="checked"/>站内消息</label>
						<label><input class="send_type" name="mail" type="checkbox"  checked="checked"/>邮件</label>
						<label><input class="send_type" name="sms" type="checkbox" />短信</label>
					</td>
				</tr>
			</table>
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
					<c:forEach items="${copyUserList}" var="copyUserItem" varStatus="cnt">
						<tr class="data-tr ${cnt.index%2==0 ? 'odd' : 'even'}" >
							<td>
								<input type="checkbox" name="nodeUserCk" value="${copyUserItem.id}"/>&nbsp;${cnt.count}						
								<input type="hidden" name="sn" value="${copyUserItem.sn}"/>
							</td>
							<td name="conditionShow">
								<textarea class="hidden">
									${copyUserItem.condition}
								</textarea>							
							</td>
							<td>
								${copyUserItem.conditionShow}
							</td>
							<td>
								<a class="link moveup" title="上移">&nbsp;</a>
								<a class="link movedown" title="下移">&nbsp;</a>
							</td>
							<td name="groupNoTd">
								<div style="width: 80px">
									<input name="groupNo" style="width:70px;" class="inputText" ivalue ="${copyUserItem.groupNo}"  value="${copyUserItem.groupNo}" />
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
					actDefId:"${bpmDefinition.actDefId}"
				}
			</textarea>
					 <a href="" id="goLocation" style="display:none;"></a>
		</div>		
	</div>
</body>
</html>


