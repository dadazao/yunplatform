<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程变量定义管理</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript" src="${ctx }/js/lg/plugins/ligerWindow.js" ></script>
<script type="text/javascript" src="${ctx }/js/hotent/platform/bpm/VarsWindow.js"></script> 
<script type="text/javascript">
var defId="${defId}";
function addVar(falg,varId){
	VarsWindow({falg:falg,varId:varId,defId:defId});
}
</script>
</head>
<body > 
           <jsp:include page="incDefinitionHead.jsp">
	    	<jsp:param value="流程变量定义管理列表" name="title"/>
			</jsp:include>
			<div class="panel-container">
            <f:tab curTab="flowVar" tabName="flow"/>
			<div class="panel">
			<div class="hide-panel">
				<div class="panel-top">
					
					<div class="panel-toolbar">
						<div class="toolBar">
							<!-- <div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
							<div class="l-bar-separator"></div> -->
							<div class="group"><a class="link add" href="#" onclick="addVar('add','')" ><span></span>添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
						</div>	
					</div>
				<%-- 	<div class="panel-search">
							<form id="searchForm" method="post" action="list.ht?defId=${defId}&actDefId=${actDefId}&actDeployId=${actDeployId }">
									<ul class="row">
										<li><span class="label">变量名称:</span><input type="text" name="Q_varName_SL"  class="inputText"  value="${param['Q_varName_SL']}"/></li>
									</ul>
							</form>
					</div> --%>
				</div>
				</div>
				<div class="panel-body">
						<c:set var="checkAll">
							<input type="checkbox" id="chkall"/>
						</c:set>
					    <display:table name="bpmDefVarList" id="bpmDefVarItem" requestURI="list.ht" sort="external" cellpadding="1" cellspacing="1"   class="table-grid">
							<display:column title="${checkAll}" media="html" style="width:30px;">
								  	<input type="checkbox" class="pk" name="varId" value="${bpmDefVarItem.varId}">
							</display:column>
							<display:column property="varName" title="变量名称" ></display:column>
							<display:column property="varKey" title="变量Key" ></display:column>							
							
							<display:column title="管理" media="html" style="width:180px;text-align:center">
								<%-- <a  href="del.ht?varId=${bpmDefVarItem.varId}" class="link del">删除</a>
								<a href="get.ht?varId=${bpmDefVarItem.varId}&actDefId=${actDefId}" class="link detail">明细</a> --%>
								<a onclick="addVar('edit','${bpmDefVarItem.varId}')" class="link flowDesign" href="#">编辑</a>
							</display:column>
						</display:table>
					
				</div><!-- end of panel-body -->	
		</div>
		</div>		
				
			 <!-- end of panel -->
			 
			
</body>
</html>


