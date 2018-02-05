<%--
	time:2013-04-29 11:15:10
	desc:edit the 代理设定
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<title>编辑代理设定</title>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/BpmDefinitionDialog.js"></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#agentSettingForm').form();
			$("a.save").click(function() {
				frm.setData();
				frm.ajaxForm(options);
				if(frm.valid()){
					var rtn =  customValidate();
					if(rtn.status){
						if(rtn.msg){
							$.ligerDialog.tipDialog("提示信息","代理设置有问题!",rtn.msg);
						}
						return;
					}
					form.submit();
				}
			});
			$("input[name='authtype']").click(handAgentType);
			
		});
		
		/**
		* 自定义验证
		* 1、如果是部分代理，必须选择一个以上的流程
		* 2、如是是部分代理，一个流程不能被重复代理
		*/
		function customValidate(){
			var rtn = {
				status:0,
				msg:""
			};
			var authtype = $("input[name='authtype']:checked").val();
			switch(authtype){
			case '0':
				var agentId = $("#agentid").val();
				if(!agentId){
					rtn.status=-1;
					rtn.msg="代理人不能为空!";
					return rtn;
				}
				var agentSettingStatus = $("input[name='enabled']:checked").val();
				if(agentSettingStatus==1){
					rtn = validateSettingComplictAgainstAll();
					if(rtn.status){
						return rtn;
					}
				}
				
				
				break;
			case '1':
				var agentId = $("#agentid").val();
				if(!agentId){
					rtn.status=-1;
					rtn.msg="代理人不能为空!";
					return rtn;
				}
				var flows = $('#bpmAgentItem [name="flowkey"]');
				if(!flows || flows.length<1){
					rtn.status = -1;
					rtn.msg="部分代理必须选择要代理的流程！" ;
					return rtn;
				}
				var agentSettingStatus = $("input[name='enabled']:checked").val();
				if(agentSettingStatus==1){
					rtn = validateComplictAgainstGeneral();
					if(rtn.status){
						return rtn;
					}
					rtn = validateComplictByFlowKey();
					if(rtn.status){
						return rtn;
					}
				}
				
				break;
			case '2':
				var flowkey = $("#flowkey").val();
				if(!flowkey){
					rtn.status=-1;
					rtn.msg ="请选择代理流程";
					return rtn;
				}

				var conditions = $("#agentCondition tr[type='subdata']");
				if(conditions.length<1){
					rtn.status=-1;
					rtn.msg ="请设置代理条件!";
					return rtn;
				}
								
				var agentSettingStatus = $("input[name='enabled']:checked").val();
				if(agentSettingStatus==1){
					rtn = validateComplictAgainstGeneral();
					if(rtn.status){
						return rtn;
					}
					rtn = validateComplictByFlowKey();
					if(rtn.status){
						return rtn;
					}
				}
				
				
				break;
			}
			return rtn;
		};
		
		/**
		* 验证流程是否已经有了有效的代理设置
		*/
		function validateComplictByFlowKey(){
			var rtn = {
					status:0,
					msg:""
				};
			var authtype = $("input[name='authtype']:checked").val();
			
			
			var flowKeys = [];
			var flowNames= [];
			
			switch(authtype){
			case "1":
				var flows = $('#bpmAgentItem [name="flowkey"]');
				$(flows).each(function(){
					var flow=$(this);
					var flowKey = flow.val();
					var flowName = flow.parent().find("input[name='flowname']").val();
					flowKeys.push(flowKey);
					flowNames.push(flowName);
				});
				break;
			case "2":
				var flowKey = $("#flowkey").val();
				var flowName = $("#flowname").val();
				flowKeys.push(flowKey);
				flowNames.push(flowName);
				break;
			}
			
			var agentSettingId = $("#id").val();
			if(!agentSettingId){
				agentSettingId=0;
			}
			var startDate = $("#startdate").val();
			var endDate = $("#enddate").val();
			
			var params = {
				flowKeys:flowKeys.join(","),
				agentSettingId:agentSettingId,
				startDate:startDate,
				endDate:endDate
			};
			var url=__ctx+"/platform/bpm/agentSetting/validateSettingComplictByFlow.ht";
			$.ajax({
				url:url,
				data:params,
				async:false
			}).done(function(data){
				if(data.status){
					rtn.status = -1;
					for(var k in data.msgMap){
						var index = $.inArray(k,flowKeys);
						var flowName = flowNames[index];
						rtn.msg += flowName +" : " + data.msgMap[k]+"<br/>";
					}
				}
			}).fail(function(){
				rtn.status = -1;
				rtn.msg="后台出错!";
			});

			return rtn;
		}
		
		/**
		* 验证是否与全权代理冲突
		*/
		function validateComplictAgainstGeneral(){
			var rtn = {
					status:0,
					msg:""
				};

			var agentSettingId = $("#id").val();
			if(!agentSettingId){
				agentSettingId=0;
			}
			var startDate = $("#startdate").val();
			var endDate = $("#enddate").val();
			
			var params = {
				agentSettingId:agentSettingId,
				startDate:startDate,
				endDate:endDate
			};
			var url=__ctx+"/platform/bpm/agentSetting/validateSettingComplictAgainstGeneral.ht";
			$.ajax({
				url:url,
				data:params,
				async:false
			}).done(function(data){
				if(data.status){
					rtn.status = -1;
					rtn.msg = data.msg;
				}
			}).fail(function(){
				rtn.status = -1;
				rtn.msg='后台出错!';
			});
			return rtn;
		}
		/**
		* 验证是否与其它代理冲突
		*/
		function validateSettingComplictAgainstAll(){
			var rtn = {
					status:0,
					msg:""
				};

			var agentSettingId = $("#id").val();
			if(!agentSettingId){
				agentSettingId=0;
			}
			var startDate = $("#startdate").val();
			var endDate = $("#enddate").val();
			
			var params = {
				agentSettingId:agentSettingId,
				startDate:startDate,
				endDate:endDate
			};
			var url=__ctx+"/platform/bpm/agentSetting/validateSettingComplictAgainstAll.ht";
			$.ajax({
				url:url,
				data:params,
				async:false
			}).done(function(data){
				if(data.status){
					rtn.status = -1;
					rtn.msg = data.msg;
				}
			}).fail(function(){
				rtn.status = -1;
				rtn.msg='后台出错!';
			});
			return rtn;
		}
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.success(obj.getMessage(),'提示',function(){
					var agentType = $("#agentType").val();
					if(agentType==1){   //管理入口
						window.location.href = "${ctx}/platform/bpm/agentSetting/manageList.ht";
					}else{
						window.location.href = "${ctx}/platform/bpm/agentSetting/list.ht";
					}					
				});
				
			} else {
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		}
		
		/**
		* 流程类型变更处理
		*/
		function handAgentType(){
			var val=parseInt( $(this).val());
			
			switch(val){
				case 0:
					$("#trDefinition,#bpmAgent,#linkAgentCondition").hide();
					$("#trAgent").show();
					break;
				case 1:
					$("#trDefinition,#linkAgentCondition").hide();
					$("#trAgent,#bpmAgent").show();
					
					break;
				case 2:
					$("#trDefinition,#linkAgentCondition").show();
					$("#trAgent,#bpmAgent").hide();
					break;
			}
		}
		/**
		* 选择流程代理人
		*/
		function selectAgent(){
			UserDialog({isSingle:true,
				callback:function(userIds, fullnames){
					$("#agentid").val(userIds);
					$("#agent").val(fullnames);
				}
			});
		};
		
		/**
		* 选择流程受权人
		*/
		function selectAuth(){
			UserDialog({isSingle:true,
				callback:function(userIds, fullnames){
					$("#authid").val(userIds);
					$("#authname").val(fullnames);
				}
			});
		};
		
		/**
		* 选择流程
		*/
		function selectFlow(){
			BpmDefinitionDialog({isSingle:true,showAll:1,validStatus:2,callback:dlgCallBack,returnDefKey:true});
		};
		
		/**
		* 选择流程的回调处理
		*/
		function dlgCallBack(defIds,subjects,defKeys){
			if(subjects==null || subjects =="") return;
			$("#flowkey").val(defKeys);
			$("#flowname").val(subjects);
		};
		
		/**
		* 部分代理 添加流程
		*/
		function addFlow(){
			BpmDefinitionDialog({isSingle:false,showAll:1,returnDefKey:true,validStatus:2,callback:function(defIds,subjects,defKeys){
				if(!subjects) return ;
				$('#firstRow').remove();
				var newSubjects=subjects.split(",");
				var newDefKeys=defKeys.split(",");
				for(var i=0,len=newDefKeys.length;i<len;i++){
					var defKey=newDefKeys[i];
					
					var subject=newSubjects[i];
					var row=$("#def_" + defKey);
					if(row.length>0) continue;
					var tr=getRow(defKey,subject);
					$("#bpmAgentItem").append(tr);
				}
			}});
		}
		
		/**
		* 部分代理 构造一行流程(用于添加到表中)
		*/
		function getRow(defKey,subject){
			var template=$("#tableRowTemplate").val();
			return template.replaceAll("#defKey",defKey).replaceAll("#subject",subject);
		}
		
		/**
		* 删除一行
		*/
		function singleDel(obj){
			var tr=$(obj).closest('tr');
			$(tr).remove();
		};
		/**
		* 设置代理流程的条件
		*/
		function setCondition(){
			var flowkey=$("#flowkey").val();
		
			if(flowkey=="") {
				$.ligerDialog.warn("请先选择流程!" ,"提示信息");
				return;
			}
			
			var url="${ctx}/platform/bpm/agentCondition/edit.ht?flowKey=" +flowkey ;
			var winArgs="dialogWidth:980px;dialogHeight:600px;help:0;status:1;scroll:1;center:1";
			
			var conditions= getAgentConditions();
			
			var params={
					conditions:conditions	
			};
			url=url.getNewUrl();
			
			var rtn=window.showModalDialog(url,params,winArgs);
			if(rtn && rtn.status && rtn.status==1){
				setAgentConditions(rtn.conditions);
			}
			
		}
		/**
		* 获取条件代理
		*/
		function getAgentConditions(){
			
			var conditions = [];
			$("#agentCondition tr").each(function(){
				var tr = $(this);
				var conditionStr = $("[name='condition']",tr).val();
				condition = [];
				if(conditionStr){
					condition = $.parseJSON(conditionStr);
				}
				var memo = $("[name='memo']",tr).val();
				var agentid = $("[name='agentid']",tr).val();
				var agent = $("[name='agent']",tr).val();
				var item = {
						condition:condition,
						memo:memo,
						agentid:agentid,
						agent:agent
				};
				conditions.push(item);
			});
			return conditions;
		}
		
		/**
		* 设置条件代理
		*/
		function setAgentConditions(conditions){
			var table = $("#agentCondition");
			table.empty();
			for(var i=0;i<conditions.length;i++){
				var cond = conditions[i];
				var tr = getConditionRow(cond);
				table.append(tr);
			}
		}
		
		/**
		* 条件代理 构造一行条件(用于添加到表中)
		*/
		function getConditionRow(cond){
			var condition = cond.condition;
			var conditionStr = JSON2.stringify(condition);
			var memo = cond.memo;
			var agentid = cond.agentid;
			var agent = cond.agent;
			var template=$("#agentConditionTableRowTemplate").val();
			var html = template.replaceAll("#memo",memo).replaceAll("#agentid",agentid).replaceAll("#agent",agent);
			var item = $(html);
			$("[name='condition']",item).val(conditionStr);		
			return item;
			
		}
		
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${agentSetting.id !=null}">
			        <span class="tbar-label">更新代理设定</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加代理设定</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="javascript:history.back(-1);"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<form id="agentSettingForm" method="post" action="save.ht">
	<div class="panel-body">
		    <input type="hidden" id="agentType" name="agentType" value="${agentType}">  <!-- 入口标记 1为管理入口  0为普通入口 -->
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<c:if test="${agentType==1}">  <!-- 管理入口时 -->
					<tr>
						<th width="20%">受权人: </th>
						<td colspan="3">
							<input type="hidden" id="authid" name="authid" value="${agentSetting.authid}"  class="inputText" validate="{required:false,maxlength:100}"  />
							<input type="text" id="authname" style="float:left;" name="authname" readonly="readonly" value="${agentSetting.authname}"  class="inputText" validate="{required:false,maxlength:100}"  />
							<a href="#" class="current" onclick="selectAuth();"><span>选择受权人</span></a>
						</td>
					</tr>				
				</c:if>
				
				<tr>
					<th width="20%">代理类型: </th>
					<td colspan="3">
						<c:choose>
							<c:when test="${agentSetting.id!=null && agentSetting.id!=0 }">
								<c:choose>
									<c:when test="${agentSetting.authtype==0}">
										全权代理
										
									</c:when>
									<c:when test="${agentSetting.authtype==2 }">
										条件代理
									</c:when>
									<c:otherwise>
										部分代理
									</c:otherwise>
								</c:choose>
								<input type="radio" name="authtype" value="${agentSetting.authtype}" checked="checked" style="display: none"/>
							</c:when>
							<c:otherwise>
								<label><input type="radio" value="0" name="authtype" <c:if test="${agentSetting.authtype==0}">checked="checked"</c:if> >全权代理</label>
								<label><input type="radio" value="1" name="authtype" <c:if test="${agentSetting==null or agentSetting.authtype==1}">checked="checked"</c:if> >部分代理</label>
								<label><input type="radio" value="2" name="authtype" <c:if test="${agentSetting.authtype==2}">checked="checked"</c:if> >条件代理</label>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th width="20%">开始时间: </th>
					<td><input type="text" id="startdate" name="startdate" value="<fmt:formatDate value='${agentSetting.startdate}' pattern='yyyy-MM-dd'/>" class="inputText datePicker"  datetype="1"  validate="{required:true,date:true}" /></td>
					<th width="20%">结束时间: </th>
					<td><input type="text" id="enddate" name="enddate" value="<fmt:formatDate value='${agentSetting.enddate}' pattern='yyyy-MM-dd'/>" class="inputText datePicker"  datetype="2"  validate="{required:true,date:true}" /></td>
				</tr>
				
				<tr id="trAgent"  <c:if test="${agentSetting.authtype==2}"> style="display: none"</c:if> >
					<th width="20%">代理人: </th>
					<td colspan="3">
						<input type="hidden" id="agentid" name="agentid" value="${agentSetting.agentid}"  class="inputText" validate="{required:false,maxlength:100}"  />
						<input type="text" id="agent" style="float:left;" name="agent" readonly="readonly" value="${agentSetting.agent}"  class="inputText" validate="{required:false,maxlength:100}"  />
						<a href="#" class="current" onclick="selectAgent();"><span>选择代理人</span></a>
					</td>
				</tr>
				<tr>
					<th>是否有效:</th>
					<td colspan="3">
						<input id="enabled_y" name="enabled" value="0" type="radio" <c:if test="${agentSetting.enabled!=1}" >checked="checked" </c:if> /> <label for="enabled_y">禁止</label>
						<input id="enabled_n" name="enabled" value="1" type="radio" <c:if test="${agentSetting==null or agentSetting.enabled==1}" >checked="checked" </c:if> /> <label for="enabled_n">启用</label>
					</td>
				</tr>
				<tr id="trDefinition" <c:if test="${ agentSetting.authtype !=2 }">style="display:none;" </c:if> >
					<th width="20%">流程名称: </th>
					<td colspan="3">
						<input type="hidden" id="flowkey" name="flowkey" value="${agentSetting.flowkey}"  class="inputText" validate="{required:false,maxlength:200}"  />
						<input type="text" style="width:350px;float:left;" id="flowname" name="flowname"
							   value="${agentSetting.flowname}"  class="inputText" validate="{required:false,maxlength:200}"   readonly="readonly" />
						<a href="#" class="current" onclick="selectFlow();"><span>选择流程</span></a>
						<a href="#" class="current" id="linkAgentCondition" onclick="setCondition();"><span>设置代理条件</span></a>
					</td>
				</tr>
			</table>
	</div>
	
			<div id="bpmAgent"  <c:if test="${agentSetting.authtype==0 or agentSetting.authtype==2}"> style="display: none"</c:if> >
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">授权流程</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group">
								<a class="link add" href="#" onclick="addFlow();"><span></span>添加流程定义</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body" >		
					   	<table  class="table-grid table-list"  Id = "agentDef" cellpadding="1" cellspacing="1" style="width:100%" type="sub">
					   		<thead>
					   			<tr>
					    			<th>流程名称</th>
					    			<th width="150px">管理</th>
					    		</tr>
					    	</thead>
					    	<tbody id="bpmAgentItem">
					    	<c:choose>
					    		<c:when test="${fn:length(agentSetting.agentDefList)>0}">
					    			<c:forEach items="${agentSetting.agentDefList}" var="bpmAgentItem">
							    		<tr id="def_${bpmAgentItem.flowkey}" type="subdata">
							    			<td>
							    				<input type="hidden" name="flowkey" value="${bpmAgentItem.flowkey}" />
							    				<input type="hidden" name="flowname" value="${bpmAgentItem.flowname}">
							    				<a href="${ctx}/platform/bpm/bpmDefinition/get.ht?defKey=${bpmAgentItem.flowkey}" target="_blank">${bpmAgentItem.flowname}</a>
							    			</td>
							    			<td>
							    				<a href="#" class="link del" onclick="singleDel(this);" class="link del">删除</a>
							    				
											</td>
							    		</tr>
							    	</c:forEach>
					    		</c:when>
					    		<c:otherwise>
					    			<tr id="firstRow">
					    				<td colspan="2" align="center">
					    					<font color='red'>还未选择流程</font>
					    				</td>
					    			</tr>
					    		</c:otherwise>
					    	</c:choose>
					    	</tbody>
					    </table>
				</div>
			</div>
			
			<div id="agentConditionDiv" style="display: none">
			<table id="agentCondition"  type="sub">
				<c:forEach items="${agentSetting.agentConditionList}" var="conditionItem">
					<tr type="subdata">
						<input type="hidden" name="condition" value="${fn:escapeXml(conditionItem.condition)}">
						<input type="hidden" name="memo" value="${conditionItem.memo}">
						<input type="hidden" name="agentid" value="${conditionItem.agentid}">
						<input type="hidden" name="agent" value="${conditionItem.agent}">
					</tr>
				</c:forEach>
			</table>
			</div>
			
			<input type="hidden" id="id" name="id" value="${agentSetting.id}" />
			<input type="hidden" name="createtime" value="<fmt:formatDate value='${agentSetting.createtime}' pattern='yyyy-MM-dd'/>"/>
	</form>
</div>

<textarea id="tableRowTemplate" style="display: none;">
	<tr id="def_#defKey"}" type="subdata">
			<td>
			<input type="hidden" name="flowkey" value="#defKey">
			<input type="hidden" name="flowname" value="#subject">
			<a href="${ctx}/platform/bpm/bpmDefinition/get.ht?defKey=#defKey" target="_blank">#subject</a>
			</td>
			<td>
				<a href="#" class="link del" onclick="singleDel(this);">删除</a>
			</td>
	</tr>
</textarea>

<textarea id="agentConditionTableRowTemplate" style="display: none;">
	<tr type="subdata">
		<td>
			<input type="hidden" name="condition" value="#condition" >
			<input type="hidden" name="memo" value="#memo">
			<input type="hidden" name="agentid" value="#agentid">
			<input type="hidden" name="agent" value="#agent">
		</td>
	</tr>
</textarea>

</body>
</html>
