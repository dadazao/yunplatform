<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>流程代理设定 管理列表</title>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/UserInfo.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
<link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js"></script>
<script type="text/javascript">
function  selectUser(){
	UserDialog({
		isSingle:true,
		callback:function(userId,fullname){
			$("#Q_authid_L").val(userId);
			$("#creator").val(fullname);
		}});
}
$(function(){
	$("[id='partialAgent']").each(function(){
		var template=$("#txtReceiveTemplate").val();
		var id = $(this).closest('tr').find('.pk').val();
		$(this).qtip({
			content:{
				text:'正在加载……',
				ajax:{
					url:'getAgentDefList.ht',
					data:{id:id},
					type:"GET",
					success:function(data,status){
						var html=easyTemplate(template,data).toString();
						this.set("content.text",html);
					}
				},title:{
					text: '代理流程列表'
				}
			},
	        position: {
	        	at:'center center',
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
	});
});
</script>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程代理设定 管理列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询 </a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link add" href="editAll.ht"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link update" id="btnUpd" action="editAll.ht"><span></span>编辑</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="manageList.ht">
					<ul class="row">
					<li>
						<span class="label">创建人授权人:</span>
						<input type="hidden" id="Q_authid_L" name="Q_authid_L" value="${param['Q_authid_L']}"/>
						<input type="text" name="creator" id="creator"  class="inputText"  value="${param['creator']}" readonly="readonly"/>	
						<input type="button" value="..."  onclick="selectUser();" />
					</li>
					<li>
						<span class="label">是否启用:</span>
							<select name="Q_enabled_N">
								<option value="">所有</option>
								<option value="0">禁止</option>
								<option value="1">启用</option>
							</select>
					</li>
					<div class="row_date">
					<li>
						<span class="label">开始时间 :</span><input  name="Q_beginstartdate_DL"  class="inputText datePicker"  datetype="1" />
					</li>
					<li>	
						<span class="label">结束时间 :</span><input  name="Q_beginenddate_DL"  class="inputText datePicker"  datetype="2" />
					</li>
					</div>	
					
					</ul>
				</form>
			</div>
		</div>
		</div>
		<div class="panel-body">
	    	<c:set var="checkAll">
				<input type="checkbox" id="chkall"/>
			</c:set>
		    <display:table name="agentSettingList" id="agentSettingItem" requestURI="manageList.ht" sort="external" cellpadding="1" cellspacing="1"  class="table-grid">
				<display:column title="${checkAll}" media="html" style="width:30px;">
			  		<input type="checkbox" class="pk" name="id" value="${agentSettingItem.id}">
				</display:column>
				<display:column  title="授权人" sortable="true" sortName="authname">
					<a href="javascript:userDetail('${agentSettingItem.authid}');">${agentSettingItem.authname}</a>
				</display:column>
				<display:column title="授权类型" sortable="true" sortName="authtype">
					<c:choose>
						<c:when test="${agentSettingItem.authtype==0}">全权代理</c:when>
						<c:when test="${agentSettingItem.authtype==1}">部分代理</c:when>
						<c:when test="${agentSettingItem.authtype==2}">条件代理</c:when>
						
					</c:choose>
				</display:column>
				
				
				<display:column  title="开始时间" sortable="true" sortName="startdate">
					<fmt:formatDate value="${agentSettingItem.startdate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column  title="结束时间" sortable="true" sortName="enddate">
					<fmt:formatDate value="${agentSettingItem.enddate}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="流程名称" sortable="true" sortName="flowname">
					<c:choose>
						<c:when test="${agentSettingItem.authtype==0}"><span style="color:green;">所有流程</span></c:when>
						<c:when test="${agentSettingItem.authtype==1}"><a href="#" id="partialAgent">部分流程</a></c:when>
						<c:when test="${agentSettingItem.authtype==2}">${agentSettingItem.flowname}</c:when>
					</c:choose>
				</display:column>
				<display:column  title="是否启用" sortable="true" sortName="enabled">
					<c:choose>
						<c:when test="${agentSettingItem.enabled==0}"><span class='red'>禁止</span></c:when>
						<c:otherwise >启用</c:otherwise>
					</c:choose>
				</display:column>
				
				<display:column  title="代理人" sortable="true" sortName="agent">
					<a href="javascript:userDetail('${agentSettingItem.agentid}');">${agentSettingItem.agent}</a>
				</display:column>
				<display:column title="授权时间" sortable="true" sortName="createtime">
					<fmt:formatDate value="${agentSettingItem.createtime}" pattern="yyyy-MM-dd"/>
				</display:column>
				<display:column title="管理" media="html" style="width:180px;line-height:21px;">
					<a href="del.ht?id=${agentSettingItem.id}" class="link del">删除</a>
					<a href="edit.ht?id=${agentSettingItem.id}" class="link edit">编辑</a>
					 
					 <c:choose>
					 	<c:when test="${agentSettingItem.enabled==0}">
					 		<a href="updStatus.ht?id=${agentSettingItem.id}&status=1" class="link unlock">启用</a>
					 	</c:when>
						<c:otherwise >
							<a href="updStatus.ht?id=${agentSettingItem.id}&status=0" class="link lock">禁止</a>
						</c:otherwise>
					 </c:choose>
				</display:column>
			</display:table>
			<hotent:paging tableId="agentSettingItem"></hotent:paging>
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
	<textarea id="txtReceiveTemplate"  style="display: none;">
	    <div style="height:150px;width:150px;overflow:auto">
	    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
	  		<#list data as obj>
	  		<tr>
	  			<th>\${obj_index+1}</th>
	  			<td>
	  			\${obj.flowname}
	  			</td>
	  		</tr>
	  		</#list>
		</table>
	  	</div>
	</textarea>
</body>
</html>


