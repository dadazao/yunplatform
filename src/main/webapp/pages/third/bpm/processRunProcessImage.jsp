<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<%@include file="/commons/include/form.jsp" %>
<f:js pre="js/lang/view/platform/bpm" ></f:js>
<title>${processRun.subject}-流程图</title>
<link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
<link href="${ctx}/js/jquery/plugins/powerFloat.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery-powerFloat.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ViewSubFlowWindow.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ViewSuperExecutionFlowWindow.js" ></script>
<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js" ></script>
<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ShowExeInfo.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<style type="text/css">
div.header{
	background-image: url(${ctx}/styles/default/images/tool_bg.jpg);
	height:24px;
	line-height:24px;
	font-weight: bold;
	font-size: 14px;
	padding-left: 5px;
	margin: 0;
	width: 394px;
}
div .legend {
	width:14px;
	height:14px;
	border: 1px solid black;
	float: left;
}

.table-task {
	margin: 0 auto;
	width:210px;
	border-collapse: collapse;
}

.table-task th {
	text-align: right;
	padding-right: 6px;
	color: #000;
	height: 32px;
	border: solid 1px #A8CFEB;
	font-weight: bold;
	text-align: right;
	font-size: 13px;
	font-weight: bold;
	padding-right: 5px;
	background-color: #D7D7D7;
	padding-right: 5px;
	border: 1px solid #656565;
}
.table-task td {
	border: solid 1px #656565;
	padding-left: 6px;
	text-align: left;
	line-height: 20px;
}

.target{
	height:20px;
	float: left;
	margin:10px;
}
div.icon{
	border:1px solid #000;
	line-height: 10px;
	width: 15px;
	height:15px;
	float: left;
	overflow: hidden;
}
.target span{
	margin: 0 0 0 5px;
	font-size: 14px;
	font-weight: bold;
	float:left;
	vertical-align: middle;
	white-space: nowrap;
}
</style>
<script type="text/javascript">
	var processInstanceId="${processInstanceId}";
	var isStatusLoaded=false;
	var _height=${shapeMeta.height};
	//状态数据
	//var aryResult=null;
	//hjx add ifram自适应高度
	$(window.parent.document).find("#flowchart").load(function(){
		var main = $(window.parent.document).find("#flowchart");
		var thisheight = $(document).height()+30;
		main.height(thisheight);
		});
		
	function setIframeHeight(){
		var mainIFrame = window.parent.document.getElementById("flowchart");
		if(!mainIFrame)return;
		mainIFrame.style.height=(parseInt(_height)+200)+'px';
	};
	
	$(function(){
		$.each($("div.flowNode"),function(){
			var obj=$(this);
			var nodeId=$(this).attr('id');
			if(obj.attr('type')=='userTask' || obj.attr('type')=='multiUserTask'){
				obj.css('cursor','pointer');
				//var url="${ctx}/platform/bpm/processRun/taskUser.ht?processInstanceId="+processInstanceId+"&nodeId=" + nodeId;
				//obj.powerFloat({ eventType: "click", target:url, targetMode: "ajax"});
				//只有用户任务和会签任务显示节点。
				checkStatusInfo(nodeId);
			}
			if(obj.attr('type')=='callActivity'){
				obj.css('cursor','pointer');
				obj.click(function(){
					var nodeId=obj.attr('id');
					var conf = {nodeId:nodeId,processInstanceId:processInstanceId};
					viewSubFlow(conf);
				});
			}
		});		
		if(self!=top){
			setIframeHeight();
		}
	});
	
	function showResult(){
		var targetUrl = $(this).attr("candidateUserUrl") ;
		
		var template=$("#txtReceiveTemplate").val();
		$(this).qtip({  
			content:{
				text:$lang.tip.loading,
				ajax:{
					url:targetUrl,
					type:"GET",
					success:function(data,status){
						var html=easyTemplate(template,data).toString();
						this.set("content.text",html);
					}
				},
				title:{
					text:"执行人列表"			
				}
			},
		        position: {
		        	at:'top left',
		        	target:'event',
					viewport:  $(window)
		        },
		        show:{
		        	event:"focus mouseenter",
		        },   			     	
		        hide: {
		        	event:'unfocus mouseleave',
		        	fixed:true
		        },  
		        style: {
		       	  classes:'ui-tooltip-light ui-tooltip-shadow'
		        } 			    
	 	});	
	}
	
	function viewSubFlow(conf){
		if(!conf) conf={};
		var url="${ctx}/platform/bpm/processRun/subFlowImage.ht?processInstanceId="+conf.processInstanceId+"&nodeId=" + conf.nodeId+"&processDefinitionId="+conf.processDefinitionId;
		
		var winArgs="dialogWidth=800px;dialogHeight=600px;help=0;status=0;scroll=1;center=1;";
		
		var winArgs="height=600,width=800,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes";
		url=url.getNewUrl();
		var win=window.open(url,"subFlow",winArgs);
		win.focus();
	}
	


	//初始化qtip
	function checkStatusInfo(nodeId){
		var html =  getTableHtml(nodeId);
		if(html)
		var obj = $("#"+nodeId);
		$("#"+nodeId).qtip({
			content:{
				text:function(){
					var html =  getTableHtml(nodeId);
					if(html){
						return html;
					}
					else{
						return "<span style='color:red;line-height:24px;'>未执行</span>";
					}
					
				},
				title:{
					text: "任务执行情况"			
				}
			},
	        position: {
	        	at:'center',
	        	target:'event',
	        	adjust: {
	        		x:-15,
	        		y:-15
	  				},  
	  				viewport:  ($.isIE6() ? "" : $(window))
	        },
	        show:{   			        	
		     	effect: function(offset) {
					$(this).slideDown(200);
					$("a[candidateUserUrl]").each(showResult);
				}
	        }, 
	        hide: {
	        	event:'mouseleave',
	        	fixed:true,
	        	delay:300
        	},  
	        style: {
	       	  classes:'ui-tooltip-light ui-tooltip-shadow',
	          width : ($.isIE6()? 279 : "" )   
	        } 			    
		});
	};
	


	var taskNodeStatus={};
	

	//加载流程状态数据。
	function loadStatus(nodeId){
		var status = taskNodeStatus[nodeId];
		if(!status && status!=""){
			var url="${ctx}/platform/bpm/processRun/getFlowStatusByInstanceIdAndNodeId.ht";
			var params={
					instanceId:processInstanceId,
					nodeId:nodeId
				};
			$.ajax({
				url:url,
				async:false,
				data:params
			}).done(function(result){
				status=result;
			});
			taskNodeStatus[nodeId]=status;
		}
		return status;
	};
	
	//构建显示的html
	function getTableHtml(nodeId){	
		var node = loadStatus(nodeId);
		if (!node)
			return false;
		var taskOpinionList = node.taskOpinionList;
		var taskExecutorList = node.taskExecutorList;
		var lastCheckStatus = node.lastCheckStatus;
		var html = [ '<div style="max-height:340px;width:260px;overflow:auto;">' ];
		if (lastCheckStatus != "-2") {  //正在执行的节点
			if (taskOpinionList.length == 0) {
				return false;
			} else {
				var tableHtml = $("#txtTaskStatus").val();
				var str = easyTemplate(tableHtml, node);
				html.push(str);
				html.push('</div>');
			}
		}else if(lastCheckStatus == "-2"){ //未执行的节点
			if (taskExecutorList.length == 0) {
				return false;
			} else {
				var tableHtml = $("#txtTaskStatusExecutors").val();
				var str = easyTemplate(tableHtml, node);
				html.push(str);
				html.push('</div>');
			}
		}

		return html.join('');
	}
	

	

	
</script>
</head>
<body>
<div class="l-layout-header">流程实例-【<i>${processRun.subject}</i>】流程图。</div>
<div class="panel">

	<c:if test="${param.tab eq 1 }">
			<c:choose>
				<c:when test="${processRun.status==2  || processRun.status==3}">
					<f:tab curTab="processImage" tabName="process" />
				</c:when>
				<c:otherwise>
					<f:tab curTab="processImage" tabName="process" hideTabs="copyUser"/>
				</c:otherwise>
			</c:choose>
	</c:if>
	
	<div class="panel-body">
		<div>
			<div class="target">
				<div class="icon" style="background:gray;"></div>
				<span>未执行</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#F89800;"></div>
				<span>提交</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#FFE76E;"></div>
				<span>重新提交</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#00FF00;"></div>
				<span>同意</span>
			</div>
			<div class="target">
				<div class="icon" style="background:orange;"></div>
				<span>弃权</span>
			</div>
			<div class="target">
				<div class="icon" style="background:red;"></div>
				<span>当前节点</span>
			</div>
			<div class="target">
				<div class="icon" style="background:blue;"></div>
				<span>反对</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#8A0902;"></div>
				<span>驳回</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#023B62;"></div>
				<span>撤销</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#338848;"></div>
				<span>会签通过</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#82B7D7;"></div>
				<span>会签不通过</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#EEAF97;"></div>
				<span>人工终止</span>
			</div>
			<div class="target">
				<div class="icon" style="background:#C33A1F;"></div>
				<span>完成</span>
			</div>
		</div>
		<div style="padding-top:20px;background-color: white;float:left;clear:both;">
			<div style="margin-bottom: 5px; "><b>说明：</b>点击任务节点可以查看节点的执行人员。
				<c:if test="${superInstanceId != null}">
					<a class="link setting" onclick="ViewSuperExecutionFlowWindow({'actInstanceId':'${superInstanceId}'})">查看主流程</a>
				</c:if>
			</div>
			<div id="divTaskContainer" style="margin:0 auto;  position: relative;background:url('${ctx}/bpmImage?processInstanceId=${processInstanceId}&randId=<%=Math.random()%>') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
				${shapeMeta.xml}
			</div>
		</div>
		<textarea id="txtTaskStatus" style="display:none">
			<#list data.taskOpinionList as obj>
				<table class="table-task" cellpadding="0" cellspacing="0" border="0">
				<tr><th>任务名称: </th>
				<td>\${obj.taskName}</td></tr>
				<#if (obj.checkStatus == -1)> <!-- 正在审批 -->
					<tr>
						<th>执行人: </th>
						<#if (obj.taskExeStatus==null)>
							<td></td>
						<#else>
							<td> <a href="javascript:userDetail('\${obj.taskExeStatus.executorId}');">\${obj.taskExeStatus.executor}</a>--\${obj.taskExeStatus.mainOrgName}\${obj.taskExeStatus.isRead==false?"(未读)":"(已读)"}</td>
						</#if>
					</tr>
					<tr>
						<th>候选人: </th>
						<#if (obj.candidateUserStatusList==null)>
							<td></td>
						<#else>
							<td>
								<#list obj.candidateUserStatusList as candidateUserStatus>
									<#if (candidateUserStatus.type=="user")>
									<a href="javascript:userDetail('\${candidateUserStatus.executorId}');">\${candidateUserStatus.candidateUser}</a>
										<span>--\${candidateUserStatus.mainOrgName}\${candidateUserStatus.isRead==false?"(<font color='red'>未读</font>)":"(<font color='green'>已读</font>)"} </span><br/>
									<#else>
										<#if (candidateUserStatus.type=="org")>
											<span><a href="#" candidateUserUrl="${ctx}/platform/system/sysUserOrg/getUserListByOrgId.ht?orgId=\${candidateUserStatus.executorId}">\${candidateUserStatus.candidateUser}</a>(组织)</span><br/>
										<#elseif (candidateUserStatus.type=="pos")>
											<span><a href="#" candidateUserUrl="${ctx}/platform/system/userPosition/getUserListByPosId.ht?posId=\${candidateUserStatus.executorId}">\${candidateUserStatus.candidateUser}</a>(岗位)</span><br/>
										<#elseif (candidateUserStatus.type=="role")>
											<span><a href="#" candidateUserUrl="${ctx}/platform/system/userRole/getUserListByRoleId.ht?roleId=\${candidateUserStatus.executorId}">\${candidateUserStatus.candidateUser}</a>(角色)</span><br/>
										<#elseif (candidateUserStatus.type=="group")>
											<span>\${candidateUserStatus.candidateUser}(用户分组)</span><br/>
										</#if>
									</#if>
								</#list>
							</td>
						</#if>
					</tr>
				<#else>
					<tr>
						<th>执行人: </th>
						<td><a href="javascript:userDetail('\${obj.exeUserId}');">\${obj.exeFullname}</a></td>
					</tr>
				</#if>
				<tr><th  nowrap="nowrap">开始时间: </th>
				<td>\${obj.startTimeStr}</td></tr>
				
				<tr><th>结束时间: </th>
				<td>\${obj.endTimeStr}</td></tr>
				
				<tr><th >时长: </th>
				<td>\${obj.durTimeStr}</td></tr>
				
				<tr><th>状态: </th>
				<td>\${obj.status}</td></tr>
				
				<tr><th>意见: </th>
				<td>\${obj.opinion==null?"":obj.opinion}</td></tr>
				</table><br>
			</#list>
		</textarea>
		<textarea id="txtTaskStatusExecutors" style="display:none">
			<div>执行状态 ： <span style='color:red;line-height: 24px;'>未执行</span> </div>
				<table class="table-task" cellpadding="0" cellspacing="0" border="0">
					<tr >
					<th width="50">执行人: </th>
						<td>
						<#list data.taskExecutorList as obj>
						<a href="javascript:userDetail('\${obj.executeId}');">\${obj.executor}</a>
							-- \${obj.mainOrgName}</br>
						</#list>
						</td>
					</tr>
				</table>
		</textarea>
		<textarea id="txtReceiveTemplate"  style="display: none;">
		    <div  style="height:150px;width:150px;overflow:auto">
		  		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
			  		<#list data as obj>
				  		<tr>
				  			<th>\${obj_index+1}</th>
				  			<#if (obj.fullname==null)>
				  				<td>\${obj.userName}</td>
			  				<#else>
			  					<td>\${obj.fullname}</td>
		  					</#if>
				  		</tr>
			  		</#list>
		  		</table>
		  	</div>
	   </textarea>
	</div>
</div>
</body>
</html>