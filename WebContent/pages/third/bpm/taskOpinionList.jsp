<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/get.jsp" %>
	<f:js pre="js/lang/view/platform/bpm" ></f:js>
	<title>${f:removeHTMLTag(processRun.subject)}--流程审批历史</title>
	<link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
	<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ShowExeInfo.js" ></script>
	<script type="text/javascript">
		$(function(){
			$("a[opinionId]").each(showResult);	
		});
	
		
		function showResult(){
			var opinionId=$(this).attr("opinionId"),
				checkStatus= $(this).attr("checkStatus");
			var template=$("#txtReceiveTemplate").val();
			$(this).qtip({  
				content:{
					text:$lang.tip.loading,
					ajax:{
						url:__ctx +"/platform/bpm/commuReceiver/getByOpinionId.ht",
						type:"GET",
						data:{opinionId: opinionId },
						success:function(data,status){
							var html=easyTemplate(template,data).toString();
							this.set("content.text",html);
						}
					},
					title:{
						text: checkStatus==36?$lang_bpm.commuReceiver.reStart :(checkStatus==15?$lang_bpm.commuReceiver.title : $lang_bpm.transToReceiver.title)			
					}
				},
			        position: {
			        	at:'top left',
			        	target:'event',
			        	
   					viewport:  $(window)
			        },
			        show:{
			        	event:"click",
	   			     	solo:true
			        },   			     	
			        hide: {
			        	event:'unfocus',
			        	fixed:true
			        },  
			        style: {
			       	  classes:'ui-tooltip-light ui-tooltip-shadow'
			        } 			    
		 	});	
		}
		
	</script>
	
</head>
<body>
	<div class="l-layout-header">流程实例-【<i>${processRun.subject}</i>】审批历史明细。</div>
	<div class="panel">
		
		<c:if test="${param.tab eq 1 }">
			<c:choose>
					<c:when test="${processRun.status==2  || processRun.status==3}">
						<f:tab curTab="taskOpinion" tabName="process" />
					</c:when>
					<c:otherwise>
						<f:tab curTab="taskOpinion" tabName="process" hideTabs="copyUser"/>
					</c:otherwise>
			</c:choose>
		</c:if>
		<div class="panel-body" style="height:100%;overflow:auto;">
	
  		 <display:table name="taskOpinionList" id="taskOpinionItem" requestURI="list.ht" sort="external" cellpadding="0" cellspacing="0" class="table-grid">
			<display:column title="序号" style="width:30px;">
			  ${taskOpinionItem_rowNum}
			</display:column>
			<display:column title="任务名称" property="taskName"></display:column>
			<display:column title="开始时间">
				<fmt:formatDate value="${taskOpinionItem.startTime}" pattern="yyyy-MM-dd HH:mm"/>
			</display:column>
			<display:column title="结束时间">
				<fmt:formatDate value="${taskOpinionItem.endTime}" pattern="yyyy-MM-dd HH:mm"/>
			</display:column>
			<display:column title="处理时长">
			  ${f:getTime(taskOpinionItem.durTime)}
			</display:column>
			<display:column  title="执行人">
			
				<c:choose>
					<c:when test="${taskOpinionItem.exeUserId ne null and taskOpinionItem.exeUserId ne null}">
						<a href="${ctx}/platform/system/sysUser/get.ht?userId=${taskOpinionItem.exeUserId}&canReturn=2&hasClose=1" target="_blank">${taskOpinionItem.exeFullname}</a>
					</c:when>
					<c:otherwise>
						<c:forEach items="${taskOpinionItem.candidateUsers }" var="user">
							<a href="${ctx}/platform/system/sysUser/get.ht?userId=${user.userId}&canReturn=2&hasClose=1" target="_blank">${user.fullname}</a>
							<br/>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column  titleKey="审批意见" >
				 ${f:parseText(taskOpinionItem.opinion)}
				<c:if test="${taskOpinionItem.checkStatus==15}">
					<a  href="#" onclick="return false;" opinionId="${taskOpinionItem.opinionId }" checkStatus="15" >接收人</a>
				</c:if>
				<c:if test="${taskOpinionItem.checkStatus==38}">
					<a  href="#" onclick="return false;" opinionId="${taskOpinionItem.opinionId }" checkStatus="40" >接收人</a>
				</c:if>
				
			</display:column>
			<display:column title="审批状态">
				<f:taskStatus status="${taskOpinionItem.checkStatus}" flag="0"></f:taskStatus>
			</display:column>
		</display:table>
		</div>
  </div>
  <textarea id="txtReceiveTemplate"  style="display: none;">
    <div  style="height:150px;width:150px;overflow:auto">
	  	
	  		<#list data as obj>
	  		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
	  		<tr>
	  			<th>接收人</th>
	  			<td>\${obj.receivername }</td>
	  		</tr>
	  		<tr>
	  			<th>状态</th>
	  			<td><#if (obj.status==0) >
	  					<span class="red">未读</span>
	  				<#elseif (obj.status==1)>
	  					<span class="green">已读</span>
	  				<#else>
	  					<span class="green">已反馈</span>	
	  				</#if>
	  			</td>
	  		</tr>
	  		<#if (obj.status==0) >
		  		<tr>
		  			<th>创建时间</th>
		  			<td>\${obj.createtimeStr}</td>
		  		</tr>
		  	<#elseif (obj.status==1)>
			  	<tr>
		  			<th>接收时间</th>
		  			<td>\${obj.receivetimeStr}</td>
			  	</tr>
		  	<#elseif (obj.status==2)>
		  		<tr>
		  			<th>反馈时间</th>
		  			<td>\${obj.feedbacktimeStr}</td>
		  		</tr>
	  		</#if>
	  		</table>
	  		</#list>
	  	
  	</div>
  </textarea>
</body>
</html>
