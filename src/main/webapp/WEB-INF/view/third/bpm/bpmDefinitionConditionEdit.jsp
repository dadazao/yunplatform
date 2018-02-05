<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="net.sf.json.JSONArray" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<%@include file="/pages/third/bpm/nodeUserSelectorJS.jsp" %>
<script type="text/javascript">
	var deployId="${deployId}";
	var actDefId="${actDefId}";
	var nodeId="${nodeId}";
	$(function() {
  		//$("#layoutFlowRule").ligerLayout({ rightWidth:200, height: '95%' });
		 //加载列表
		 loadRuleList(); 		
	});
		
	//删除规则	
	function delCondition(objId){	
		$('#'+objId).remove();
	}
	
	function getRow(key,ruleName,idx,conditionJson,operateType){
		if($('#contr_'+key).length>0){
			$.ligerMessageBox.warn("该表单变量条件已经存在");
			return;
		}			
		var className=(idx % 2==0)?"odd":"even";
		var aryRow=["<tr  id='contr_"+key+"'>",
		"<td>",
		"<span class='ruledetail' >"+ruleName+"</span>",
		"<input type='hidden'  id='"+key+"'    value=\""+conditionJson+"\"  operateType=\""+operateType+"\"   />",
		"</td>",
		"<td>",
		"<a alt='删除'  class='delrule link del' onclick=\"delCondition('contr_"+key+"')\">&nbsp;&nbsp;&nbsp;</a>",
		"</td>",
		"</tr>"];
		return aryRow.join("");
	}
	//加载规则列表
	function loadRuleList(){
		var condition="${bpmUserCondition.condition}";
		var tbodyList=$("#ruleList");	
		if(condition.length<5) return;
		var  jsonAry=eval('('+condition+')');
		tbodyList.empty();
		for(var i=0;i<jsonAry.length;i++){			
				var obj=jsonAry[i];
				var conditionJson="{'key':'"+obj.key+"','conditionValue':'"+obj.conditionValue+"','compare':'"+obj.compare+"','operator':'"+obj.operator+"','showCondition':'"+obj.showCondition+"'}";			
				var row=getRow(obj.key,obj.showCondition,i,conditionJson,obj.operator);
				tbodyList.append($(row));
		}
	}
	
	//更新那个bpm_node_set的IsJumpForDef字段
	function updateIsJumpForDef(ck){
		var url=__ctx+"/platform/bpm/bpmNodeRule/updateIsJumpForDef.ht";
		var params={
			nodeId:nodeId,
			actDefId:actDefId,
			isJumpForDef:ck.checked? 1:0
		};
		$.post(url,params,function(data){});
	}

	/**
	* Select a different flow var
	* @params obj,target dom object
	*/
	function selectFlowVar(obj,type){
		var obj=$(obj);
		obj.qtip("destroy");
		if(type==1){
			var fname=obj.val();
			if(!fname){
				return;
			}
			var ftype = obj.find("option:selected").attr("ftype");
			constructFlowOperate(ftype);
			
			var valueInput=$("<input name='flowValue'/>");
			var oldValueInput=$("input[name='flowValue']");
			
			if('date'==ftype.toLowerCase()){
				valueInput.addClass("date");
				valueInput.focus(dateTimePicker);
			}
			oldValueInput.replaceWith(valueInput);
			
		}else if(type==2){
			var fname = obj.val();
			var pos=actionScriptEditor.getCursor();
			actionScriptEditor.replaceRange(fname,pos);
		}	
	}
	
	function constructFlowOperate(type){
			var select = $("select[name='flowOperate']");
			select.html("");
			type=type.toLowerCase();
			switch(type){
			case 'int':
			case 'number':
			case 'date':
				select.append($("<option value='<'>小于</option>"));
				select.append($("<option value='<='>小于或等于</option>"));
				select.append($("<option value='>'>大于</option>"));
				select.append($("<option value='>='>大于或等于</option>"));
				select.append($("<option value='!='>不等于</option>"));
				select.append($("<option value='=='>完全等于</option>"));
				break;
			case 'varchar':		
				select.append($("<option value='!='>不等于</option>"));
				select.append($("<option value='.equals()'>等于</option>"));
				select.append($("<option value='.equalsIgnoreCase()'>等于(忽略大小写)</option>"));
				select.append($("<option value='=='>完全等于</option>"));
				break;
			}
		}
	function generateCondition(obj){
		var div = $(obj).closest("div.condExp-control");
		var flowVar=div.find("select[name='flowVar']").find("option:selected");
		var flowVarName=flowVar.val();
		var flowVarOperate=div.find("select[name='flowOperate']").find("option:selected").val();
		var flowVarOperateDesc=div.find("select[name='flowOperate']").find("option:selected").text();
		var flowVarValue=div.find("input[name='flowValue']").val();
		var flowVarDes=$(flowVar[0]).text();
		if(!flowVarName){
			$.ligerMessageBox.warn("请选择流程变量！");
			return;
		}
		if(!flowVarOperate){
			$.ligerMessageBox.warn("请选择变量操作类型变量！");
			return;
		}
		if(!flowVarValue){
			$.ligerMessageBox.warn("请输入流程变量值！");
			return;
		}
		var options=div.find("select[name='compType']").find("option:selected").val();
		var exp=flowVarOperate;
		var showStr="";
		showStr=flowVarDes +flowVarOperateDesc +flowVarValue;
		
		var conditionJson="{'key':'"+flowVarName+"','conditionValue':'"+flowVarValue+"','compare':'"+exp+"','operator':'"+options+"','showCondition':'"+showStr+"'}";
		var tbodyList=$("#ruleList");
		var num=$(tbodyList).children('tr').length+1;
		var row=getRow(flowVarName,showStr,num,conditionJson,options);
		tbodyList.append($(row));
	}
	function isEmpty (v){
        return v === null || v === undefined || v === '' ;
    }
	
	function isNumber(v){
		var vn = parseInt(v); 
		if(typeof vn != 'number' || isNaN(vn)){
			return false;
		}
		return true;
	}
	
	function setShowStr(str,operateType,isFirst){
		var result="";
		if(isFirst){
			result=str;
			if(operateType==2){
				result=" ("+str+")取反";
			}
			return result;
		}
			if(operateType==0){
				result=" 并且 "+str;
			}else if(operateType==2){
				result=" 并且 ("+str+")取反";
			}else{
				result=" 或者 "+str;
			}
		return result;
	}
	
	function checkAndSubmit(){
		var conditionName=$('#conditionName').val();
		
		if($.trim(conditionName).length<1){			
			$.ligerMessageBox.warn("请输入规则条件名称！");
			return;
		}
		var flag =false;
		$('input[name="assignType"]').each(function(){
			var v = $(this).val();
			if(isEmpty(v) || !isNumber(v)){
				flag = true;
				$(this).siblings('#assignType_input').focus();
				return true;
			}
		});
		
		if(flag){
			alertMsg.warn("请正确选择用户类型！");
			return;	
		}	
		var tbodyList=$("#ruleList");
		var conditionId=$('#conditionId').val();
		var actDefId=$('input[name="actDefId"]').val();
		var nodeId=$('input[name="outnodeId"]').val();
		var setId=$('#flow_setId').val();
		var sn=$('input[name="sn"]').val();
		var conditionArray="[";
		var conditionShow="";
		var currentIndex=0;
		var variableids=$('input[name="variableids"]:first').val();
<%--		$('input[type=hidden]', tbodyList).each(--%>
<%--			function(){--%>
<%--				conditionArray+=$(this).val()+",";--%>
<%--				var operate=$(this).attr('operateType')				--%>
<%--				if(currentIndex==0 && $(this).prev('span').html()){							--%>
<%--						conditionShow+="表单条件:"+setShowStr($(this).prev('span').html(),operate,true);--%>
<%--				}else if($(this).prev('span').html()){					--%>
<%--					conditionShow+=setShowStr($(this).prev('span').html(),operate);--%>
<%--				}--%>
<%--				currentIndex++;--%>
<%--			}		--%>
<%--		);--%>
		conditionArray+=$("#cmpIds").val();
		conditionArray+="]";
		currentIndex=0;
		$('textarea[name=cmpNames]').each(
				function(){		
					var select=$(this).parents('tr').find('select[name="assignType"]')
					if(select.length>0){
						var asstypeStr=$(select).find("option:selected").text();
						if(currentIndex==0){
							conditionShow+="";
						}
						if($.trim($(this).val()).length>0){
							conditionShow+=asstypeStr+":"+$(this).val()+";";
						}else{
							conditionShow+=asstypeStr+";";
						}
					}else{
						var asstypeStr=$.trim($(this).parents('tr').find('td').eq(1).find('span').html());
						if(currentIndex==0){
							conditionShow+="";
						}
						if($.trim($(this).val()).length>0){
							conditionShow+=asstypeStr+":"+$(this).val()+";";
						}else{
							conditionShow+=asstypeStr+";";
						}
					}			
				}
		);
		if(conditionArray.length>5){
			//conditionArray=conditionArray.substring(0, conditionArray.length-1);
			//conditionShow+=";\n"
		}
		var postData={
				'setId':setId,
				'actDefId':actDefId,
				'nodeId':nodeId,
				'conditionShow':conditionShow,
				'condition':conditionArray,
				'conditionName':conditionName,
				'conditionId':conditionId,
				'variableids':variableids,
				'sn':sn
		};
		
		$.ajax( {
			type : 'POST',
			url : __basePath+"/pages/third/bpm/bpmUserCondition/save.action",
			data: postData,
			dataType: 'json',
			success : function(json) {
				if(json.statusCode ==200 ) {
					$.ajax( {
						type : 'POST',
						url : __basePath+"/pages/third/bpm/bpmDefinition/saveUser.action",
						data: postData,
						dataType: 'json',
						success : function(json) {
							if(json.statusCode==200){
								alertMsg.correct(json.message);
								$.pdialog.closeCurrent(); 
								ns.bpm.userSet('${definitionId}');
							}else{
								alertMsg.warn(json.message);
							}	

						}
					});
				
				}else{
					alertMsg.warn(json.message);
				}
			}
		});
		
	}
	

  	/*
	 * 模拟参数
	 */
	var mockParams = {
  			status:0,
			startUser:{
				id:"",
				name:"",
				need:false
			},
			prevUser:{
				id:"",
				name:"",
				need:false
			},
			nodeUsers:[]
	};
	/**
	 * 预览用户设置 
	 */
	function previewUserSetting(){
		
		var startUserId = 0;
		var prevExecUserId = 0;
		var otherNodeUserIds=[];

		mockParams.startUser.need=false;
		mockParams.prevUser.need=false;
		for(var i=0;i<mockParams.nodeUsers.length;i++){
			mockParams.nodeUsers[i].need=false;
		}
		
		
		var needMockParams = false;
		var users = extractNodeUserDatas();
		for(var i=0;i<users.length;i++){
			var user=users[i];
			
			//是否需要模拟发起人
			if(user.assignType==0
				||user.assignType==6
				||user.assignType==9
				||user.assignType==11
				||user.assignType==14
				||user.assignType==16){
				needMockParams = true;
				mockParams.startUser.need=true;
			}
			//是否需要模拟上一个执行人
			if(user.assignType==13
			    ||user.assignType==15
				||user.assignType==16){
				needMockParams = true;
				mockParams.prevUser.need=true;
			}
			
			//是不需要模拟其它节点的执行人
			if(user.assignType==10){
				var nodeId =user.cmpIds;
				var nodeName =user.cmpNames;
				
				if(!nodeId){
					continue;
				}
				
				var inFlag = false;
				for(var j=0;j<mockParams.nodeUsers.length;j++){
					if(mockParams.nodeUsers[j].nid==nodeId){
						needMockParams = true;
						mockParams.nodeUsers[j].need=true;
						inFlag = true;
						break;
					}
				}
				if(!inFlag){
					needMockParams = true;
					var nodeUser = {
						nid:nodeId,
						nname:nodeName,
						id:"",
						name:"",
						need:true
					};
					mockParams.nodeUsers.push(nodeUser);
				}
			}
		}
		if(needMockParams){
			var mprtn = nodeUserMockParamsDialog(mockParams);
			console.dir(mprtn);
			if(mprtn.status!=0){
				return;
			}
			
			//返回值设置
			mockParams=mprtn;
			if(mockParams.startUser.need){
				startUserId = mockParams.startUser.id;
			}
			if(mockParams.prevUser.need){
				prevExecUserId = mockParams.prevUser.id;
			}
			for(var j=0;j<mockParams.nodeUsers.length;j++){
				otherNodeUserIds.push(
					{
						nid:mockParams.nodeUsers[j].nid,
						uid:mockParams.nodeUsers[j].id
					}
				);
			}
		}
		
		var defId = $("[name='defId']").val();
		
		var params = {
			session:"1",
			userAry:JSON.stringify(users),
			defId:defId,
			startUserId:startUserId,
			prevExecUserId:prevExecUserId,
			otherNodeUserIds:JSON.stringify(otherNodeUserIds)
		};
		var url = __ctx+"/platform/bpm/bpmNodeUser/getNodeUser.ht";		
		$.post(url,params,function(data){
			if(data.status==0){
				nodeUserListPreviewDialog();
			}else{
				$.ligerDialog.err("出错提示","预览出错！",data.msg);
			}
		});
	};
	
	/**
	 * 打开取得用户窗口
	 */
	function nodeUserListPreviewDialog(){
		var winArgs = "dialogWidth=800px;dialogHeight=500px;status=no;help=no;scroll=no";
		var url=__ctx + "/platform/bpm/bpmNodeUser/getNodeUserPreview.ht?cleanSession=1";
		var rtn=window.showModalDialog(url,"",winArgs);
	};
	
	/**
	 * 打开预览，设置参数窗口
	 */
	function nodeUserMockParamsDialog(mockParams){
		var winArgs = "dialogWidth=600px;dialogHeight=300px;status=no;help=no;scroll=no";
		var url=__ctx + "/platform/bpm/bpmNodeUser/previewMockParams.ht";
		var rtn=window.showModalDialog(url,mockParams,winArgs);
		return rtn;
	};
	
	/**
	 * 获取用户设置
	 */
	function extractNodeUserDatas(){
		var users = new Array();
		$("#defUserForm table tbody.data tr").each(function(){
			var tr = $(this);
			var assignType = tr.find("[name='assignType']").val();
			var assignUseType = tr.find("[name='assignUseType']").val();
			var nodeId = tr.find("[name='nodeId']").val();
			var cmpIds = tr.find("[name='cmpIds']").val();
			var cmpNames = tr.find("[name='cmpNames']").val();
			var nodeUserId = tr.find("[name='nodeUserId']").val();
			var compType = tr.find("[name='compType']").val();
			var user={
				assignType:assignType,	
				assignUseType:assignUseType,
				nodeId:nodeId,
				cmpIds:cmpIds,
				cmpNames:cmpNames,
				nodeUserId:nodeUserId,
				compType:compType
			};
			users.push(user);
		});
		return users;
	};
	
	/**
	 * 添加用户设置
	 */
	function addNewNodeUser(nodeTag){
		var userSetTypeSelectOpt = $("#userSetTypesSelectOpt").html();
		addUserSetRow(nodeTag,userSetTypeSelectOpt)
	};
</script>
	<div class="pageContent">
		<div class="pageFormContent" layouth="60">
				<table>
					<tr>
						<td>规则名称：</td>
						<td>
							<c:choose>
							<c:when test="${bpmUserCondition.conditionname!=null && bpmUserCondition.conditionname!=''}">
									<input type="text" id="conditionName" name="conditionName" size="40" value="${bpmUserCondition.conditionname}" />
							</c:when>
							<c:otherwise>
									<input type="text" id="conditionName" name="conditionName" size="40" value="默认规则" />
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
								
<!--								<tr>-->
<!--									<td width="20%">表单规则: </td>-->
<!--									<td >		-->
<!--											<div class="condExp-control">-->
<!--														<span class="green">表单变量</span>		-->
<!--														<select name="flowVar" onchange="selectFlowVar(this,1)">-->
<!--															<option value="">请选择...</option>-->
<!--															<c:forEach items="${flowVars}" var="flowVar">-->
<!--																<option class="flowvar-item" value="${flowVar.fieldName}"  fname="${flowVar.fieldName}" fdesc="${flowVar.fieldDesc}" ftype="${flowVar.fieldType}">${flowVar.fieldDesc}</option>-->
<!--															</c:forEach>-->
<!--														</select>-->
<!--														<span class="green">比较</span>-->
<!--														<select name="flowOperate" >-->
<!--														</select>-->
<!--														<span class="green">值</span>-->
<!--														<input name="flowValue"/>-->
<!--														<br/><br/>-->
<!--														<span class="green">运算类型</span>-->
<!--															<select name="compType">-->
<!--																		<option value="0">并且</option>-->
<!--																		<option value="1">或者</option>		-->
<!--																		<option value="2">取反</option>																					-->
<!--															</select>-->
<!--														<a onclick="generateCondition(this)" href="#" class="button">-->
<!--															<span>生成</span>-->
<!--														</a>-->
<!--													</div>-->
<!--									</td>-->
<!--								</tr>-->
					<form action="saveUser.ht" method="post" id="defUserForm">
							<input type="hidden" name="definitionId" value="${definitionId}" />											
							<input type="hidden" id="conditionId" name="conditionId" value="${bpmUserCondition.id}" />
							<%@include file="/pages/third/bpm/nodeUserSelector.jsp" %>
					</form>
<!--				<td valign="top" width="30%">-->
<!--					<div  style="width:100%;" position="right" title="规则列表" style="overflow-x: scroll;">-->
<!--						<table  cellpadding="1" class="table-grid table-list" cellspacing="1">-->
<!--							<tr>-->
<!--								<th>条件</th><th>管理</th>-->
<!--							</tr>-->
<!--							<tbody id="ruleList">-->
<!--							</tbody>-->
<!--						</table>-->
<!--					</div>				-->
<!--				</td>-->
				<input type="hidden" name="sn" value="${bpmUserCondition.sn}" />
				<input type="hidden" name="actDefId" value="${actDefId}" />
				<input type="hidden" name="outnodeId" value="${nodeTag}" />
				<input type="hidden" id="flow_setId" name="setId" value="${setId}" />
		</div>
		<div class="formBar" align="center">
			<button class="listbutton" type="button" id="btnSave"  onclick="checkAndSubmit()">保存</button>
			<button class="listbutton" type="button" onclick="javasrcipt:$.pdialog.closeCurrent(); ">关闭</button>	
		</div>							
	</div>
	
			
	<!-- 隐藏域，主要用于方便代码的编写 -->	
	<div style="display: none;">
				<div style="display: none;">
					<div id="userSetTypesSelectOpt">
						<c:forEach items="${userSetTypes}" var="item">
							<option value="${item.key}" <c:if test="${item.key==1}">selected="selected"</c:if> >${item.value}</option>
						</c:forEach>
					</div>
				</div>

			<textarea id="conditionEntity">
					{
					setId:"${setId}",
					defId:${defId},
					actDefId:"${bpmDefinition.actDefId}",
					nodeId:"${nodeId}",
					conditionId:"${bpmUserCondition.id}",
					conditionType:"${bpmUserCondition.conditionType}",
					sn:"${bpmUserCondition.sn}",
					groupNo:"${bpmUserCondition.groupNo}",
					formIdentity:"${bpmUserCondition.formIdentity}"
					}
			</textarea>
			<textarea id="conditionTxt">
				${bpmUserCondition.condition}
			</textarea>
	</div>
				
	<div style="display: none;">
		<div id="userSetTypesSelectOpt">
			<c:forEach items="${userSetTypes}" var="item">
				<option value="${item.key}" <c:if test="${item.key==1}">selected="selected"</c:if> >${item.value}</option>
			</c:forEach>
		</div>
	</div>
	
	<div id="divScriptData" style="display: none;">

		<a href="#" id="btnScript" class="link var" title="常用脚本" onclick="selectScript()">常用脚本</a>
		<ul>
			<li>可以使用的流程变量,[startUser],开始用户,<li>[startUser],上个任务的用户[prevUser]。</li>
			<li>表达式必须返回Set&lt;String&gt;集合类型的数据。</li>
		</ul>
		<textarea id="txtScriptData" rows="10" cols="80" style="height: 200px;width:480px"></textarea>
	</div>

