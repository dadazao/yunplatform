<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
function loadCatalog() {
	$.pdialog.open("<%=basePath %>/pages/resource/catalog/catalogTree.jsp","selectCatalogDialog","选择目录",{width:300,height:600,mask:true,resizable:true});
}

function selectPath() {
	var path = $('#pathSelectId option:selected').val();
	if(path != ""){
		$("#pathId").hide();
		$("#pathId").val(path);
	}else{
		$("#pathId").show();
		$("#pathId").val("");
	}
}

$(function(){
	var path = $('#pathSelectId option:selected').val();
	if(path != ""){
		$("#pathId").hide();
		$("#pathId").val(path);
	}
	//将修改按钮置灰
	$("#XG").attr("disabled","disabled");
	$("#XG").attr("class","listbuttonDisable");
	
	//新增情况：将【删除】按钮设置为：不可用（disabled）
	var op = $("#op").val();
	if( op != null && "new" == op ){
		$("#SC").attr("DISABLED","true");
	}
});

function eventBC(){
	$("#domainSubmit").click();
}

//-->
</script>
<form method="post" action="<c:url value='/pages/resource/catalogsave.action'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	 <div>
		 <input id="domainSubmit" type="submit" style="display:none;" />
		 <input id="domId" type=hidden name="catalog.id" value="${catalog.id}"/>
		 <input type=hidden name="model" value="catalog"/>
		 <input id="op" type="hidden" name="op" value="${op}" />
	 </div>
 	 <div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<c:forEach items="${formButtons}" var="formButton" varStatus="status">
					<c:if test="${formButton.buttonType == '1'}">
						<c:forEach items="${formButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
							<button type="button" id="${buttonAndGroup.button.buttonBM}" name="b${status.count*stat.count}" class="listbutton" onclick="event${buttonAndGroup.button.buttonBM}();">${buttonAndGroup.button.buttonName}</button>&nbsp;
						</c:forEach>
					</c:if>
					<c:if test="${formButton.buttonType == '0'}">
						<button type="button" name="b${status.count}" class="listbutton" onclick="event${formButton.button.buttonBM}();">${formButton.button.buttonName}</button>&nbsp;	
					</c:if>
				</c:forEach>
			</td></tr></table>
		</div>
	</div>
	<div class="tabs">
	      <div class="tabsHeader">
	            <div class="tabsHeaderContent">
	                  <ul>
	                        <li class="selected"><a><span>基本信息</span></a></li>
	                  </ul>
	            </div>
	      </div>
	      <div class="tabsContent" >
			<div align="center">
				
			  	 	<table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>目录名称</label>
							</td>
							<td width="40%" height="30" align="left">
								<input name="catalog.name" value="${catalog.name}" type="text"/>
							</td>	
<%--							<td width="10%" height="30" align="left" class="Input_Table_Label">--%>
<%--								<label>编码</label>--%>
<%--							</td>--%>
<%--							<td width="40%" height="30" align="left">--%>
<%--								<input name="catalog.code" value="${catalog.code}" type="text"/>--%>
<%--							</td>--%>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>父目录</label>
							</td>
							<td width="40%" height="30" align="left">
								<table cellspacing="0" cellpadding="0" border="0" >
									<tr>
										<td align="left" style="background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;">
											<input id="parentName" type="text" name="catalog.parentName" value="${catalog.parentName}" readonly="true" />
											<input id="parentId" name="catalog.parentId" value="${catalog.parentId}" type="hidden"/>
										</td>
										<td style="background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;">
											<a id="selectDialog" rel="selectDialog" class="button" target="dialog" mask="true" resizable="true" width="300" height="600" href="<%=basePath %>/pages/resource/catalog/catalogTree.jsp" ><span>选择</</span></a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>顺序</label>
							</td>
							<td width="40%" height="30" align="left" colspan="3">
								<input name="catalog.orderNum" value="${catalog.orderNum}" type="text"/>
							</td>
						</tr>	
						<tr>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>对应路径</label>
							</td>
							<td colspan="3" height="30" align="left">
								<select id="pathSelectId" name="catalog.path" style="width:135px" onchange="selectPath();">
									<option value="">自定义</option>
									<c:forEach items="${tabulations}" var="tabulation">
										<option value="${tabulation.tabulationPath}" <c:if test="${tabulation.tabulationPath==catalog.path }">selected="selected"</c:if>>${tabulation.tabulationName}</option>
									</c:forEach>									
								</select>
								<input id="pathId" name="path" type="text" size="102" value="${catalog.path}"/>
							</td>
						</tr>	
					</table>
			</div>
		</div>
	</div>
</form>