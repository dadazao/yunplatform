<%
	//显示流程示意图及显示每个任务节点上的执行人员
%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>流程执行示意图</title>
<%@include file="/commons/include/form.jsp" %>
<%-- <link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" /> --%>
<f:link href="jquery.qtip.css"></f:link>
<script type="text/javascript" src="${ctx}/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery-powerFloat.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ViewSubFlowWindow.js" ></script>
<link href="${ctx}/js/jquery/plugins/powerFloat.css" rel="stylesheet" type="text/css" />
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
ul.legendContainer{
	position: relative;
	top:10px;
	left:10px;
}
	
ul.legendContainer li{
	list-style: none;
	font-size: 14px;
	display: inline-block;
	font-weight: bold;
}
	
ul.legendContainer li .legend{
	width:14px;
	height: 14px;
	border: 1px solid black;
	margin-right:5px;
	margin-top:2px;
	float: left;
}
.table-task {
	margin: 0 auto;
	width:260px;
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
	background-color: #edf6fc;
	padding-right: 5px;
	border: 1px solid #8dc2e3;
}
.table-task td {
	border: solid 1px #A8CFEB;
	padding-left: 6px;
	text-align: left;
}
</style>

<script type="text/javascript">
	var processDefinitionId = "${processDefinitionId}";
// 	var processInstanceId="${processInstanceId}";
	var subProcessRun = "${subProcessRun}";
	var isStatusLoaded=false;
	var _height=${shapeMeta.height};
	//状态数据
	var aryResult=null;
	
	function setIframeHeight(){
		var mainIFrame = window.parent.document.getElementById("flowchart");
		if(!mainIFrame)return;
		mainIFrame.style.height=_height+200;
	};
	
	$(function(){
		
		if(subProcessRun==1){
			$.each($("div.flowNode"),function(){
				var processInstanceId = $(this).parent('[name="divTaskContainer"]').attr('procInstId');
				if($(this).attr('type')=='userTask' || $(this).attr('type')=='multiUserTask'){
					$(this).css('cursor','pointer');
					var nodeId=$(this).attr('id');
					var url="${ctx}/platform/bpm/processRun/taskUser.ht?processInstanceId="+processInstanceId+"&nodeId=" + nodeId;
					$(this).powerFloat({ eventType: "click", target:url, targetMode: "ajax"});
				}
				if($(this).attr('type')=='callActivity'){
					$(this).css('cursor','pointer');
					$(this).click(function(){
						var nodeId=$(this).attr('id');
						var conf = {nodeId:nodeId,processInstanceId:processInstanceId,processDefinitionId:processDefinitionId};
						viewSubFlow(conf);
					});
				}
				
				loadStatus(processInstanceId);
			});		
// 			loadStatus();		
			if(self!=top){
				setIframeHeight();
			}
		}else{
			$.each($("div.flowNode"),function(){
				var processInstanceId = "";
				if($(this).attr('type')=='callActivity'){
					$(this).css('cursor','pointer');
					$(this).click(function(){
						var nodeId=$(this).attr('id');
						var conf = {nodeId:nodeId,processInstanceId:processInstanceId,processDefinitionId:processDefinitionId};
						viewSubFlow(conf);
					});
				}
			});		
		}
	});
	
	function viewSubFlow(conf){
		if(!conf) conf={};
		var url="${ctx}/platform/bpm/processRun/subFlowImage.ht?processInstanceId="+conf.processInstanceId+"&nodeId=" + conf.nodeId+"&processDefinitionId="+conf.processDefinitionId;
		var winArgs="dialogWidth=800px;dialogHeight=600px;help=0;status=0;scroll=1;center=1;";
		url=url.getNewUrl();
		window.showModalDialog(url,"",winArgs);
	}
	
	
	//初始化qtip
	function eventHandler(){
		$("div.flowNode").each(function(){
			if(!isStatusLoaded){
				loadStatus();
				return;
			}
			var obj=$(this);
			var taskId=obj.attr("id");
			var html=getTableHtml(taskId);
			if(!html)return;
			$(this).qtip({  
					content:{
						text:html,
						title:{
							text: '任务执行情况'				
						}
					},
   			        position: {
   			        	at:'center',
   			        	target:'event',
   			        	adjust: {
   			        		x:-15,
   			        		y:-15
   		   				},  
   		   				viewport: $(window)
   			        },
   			        show:{   			        	
	   			     	effect: function(offset) {
	   						$(this).slideDown(200);
	   					}
   			        },   			     	
   			        hide: {
   			        	event:'mouseleave',
   			        	fixed:true
   			        	},  
   			        style: {
   			       	  classes:'ui-tooltip-light ui-tooltip-shadow'
   			        } 			    
 		 	});	
		});
	}
	
	//加载流程状态数据。
	function loadStatus(processInstanceId){
		var url="${ctx}/platform/bpm/processRun/getFlowStatusByInstanceId.ht";
		var params={instanceId:processInstanceId};
		$.post(url,params,function(result){
			aryResult=result;
			isStatusLoaded=true;
			eventHandler();
		});
	}

	//构建显示的html
	function getTableHtml(taskId){		
		var node=getNode(taskId);
		if(node==null) return false;
		var aryOptions=node.taskOpinionList;
		if(aryOptions.length==0)return false;
 		var html=['<div>'];
		for(var i=0;i<aryOptions.length;i++){
			var p=aryOptions[i];
			var sb=new StringBuffer();			
			var exeFullname=p.exeFullname;
			var taskName=p.taskName;
			var startTime=p.startTimeStr;
			var endTime=p.endTimeStr;
			var status=p.status;
			var opinion=p.opinion==null ?"无":p.opinion;
			var durTime=p.durTimeStr;

			sb.append('<table class="table-task" cellpadding="0" cellspacing="0" border="0">');
			
			sb.append('<tr><th width="30%">任务名: </th>');
			sb.append('<td>'+taskName+'</td></tr>');
			
			sb.append('<tr><th width="30%">执行人: </th>');
			sb.append('<td>'+exeFullname+'</td></tr>');
			
			sb.append('<tr><th width="30%">开始时间: </th>');
			sb.append('<td>'+startTime+'</td></tr>');
			
			sb.append('<tr><th width="30%">结束时间: </th>');
			sb.append('<td>'+endTime+'</td></tr>');
			
			sb.append('<tr><th width="30%">时长: </th>');
			sb.append('<td>'+durTime+'</td></tr>');
			
			sb.append('<tr><th width="30%">状态: </th>');
			sb.append('<td>'+status+'</td></tr>');
			
			sb.append('<tr><th width="30%">意见: </th>');
			sb.append('<td>'+opinion+'</td></tr>');
			sb.append("</table><br>");
			html.push(sb.toString());
		}
		html.push('</div>');
		return html.join('');
	}
	
	//从返回的结果中返回状态数据。
	function getNode(taskId){
		if(aryResult==null) return null;
		for(var i=0;i<aryResult.length;i++){
			var node=aryResult[i];
			var taskKey=node.taskKey;
			if(taskId==taskKey){
				return node;
			}
		}
		return null;
	}
</script>
</head>
<body>
	<ul class="legendContainer">
		<li><div style="background-color:gray; " class="legend"></div>未执行</li>
		<li><div style="background-color:#00FF00;" class="legend"></div>同意</li>
		<li><div style="background-color:orange;" class="legend"></div>弃权</li>
		<li><div style="background-color:red;" class="legend"></div>当前节点</li>
		<li><div style="background-color:blue;" class="legend"></div>反对</li>
		<li><div style="background-color:#8A0902;" class="legend"></div>驳回</li>
		<li><div style="background-color:#023B62;" class="legend"></div>追回</li>
		<li><div style="background-color:#338848;" class="legend"></div>会签通过</li>
		<li><div style="background-color:#82B7D7;" class="legend"></div>会签不通过</li>
	</ul>
	<div style="padding-top:40px;background-color: white;">
		<div><b>说明：</b>点击任务节点可以查看节点的执行人员</div>
		<c:choose>
			<c:when test="${subProcessRun==1}">
				<c:forEach items="${processInstanceIds}" var="processInstanceId">
				<div name="divTaskContainer" procInstId="${processInstanceId }" style="margin:0 auto;  position: relative;background:url('${ctx}/bpmImage?processInstanceId=${processInstanceId}&randId=<%=Math.random()%>') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
					${shapeMeta.xml}
				</div>
				<hr/>
				</c:forEach>
			</c:when>
			<c:when test="${subProcessRun==0}">
				<div id="divTaskContainer" style="margin:0 auto;  position: relative;background:url('${ctx}/bpmImage?definitionId=${processDefinitionId}&randId=<%=Math.random()%>') no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height}px;">
					${shapeMeta.xml}
				</div>
			</c:when>
		</c:choose>
	</div>
</body>
</html>