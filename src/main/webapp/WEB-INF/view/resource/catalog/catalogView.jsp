<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	//function modify() {
	//	var urlString = "<%=basePath %>/pages/resource/catalog/edit.action?id="+$("#domId").val() + "&op=edit";
	//	$.pdialog.reload(urlString);
	//}

	$(function(){
		//修改ACTION URL
		xgUrl ="<%=basePath %>/pages/resource/catalog/edit.action?id="+$("#domId").val() + "&op=edit&model=${model}&formId=${formId}";
		//删除ACTION URL
		var id = $('#domId').val();
		scUrl = "<%=basePath %>/pages/resource/catalog/singleDelete.action?params=${params}&model=${model}&id="+id;
	});
//-->
</script>
	<div>
		<input type=hidden name="model" value="catalog"/>
		<input type=hidden id="formId" name="formId" value="${formId}" />
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
					<input id="domId" type=hidden name="catalog.id" value="${catalog.id}"/>
			  	 	<table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>目录名称</label>
							</td>
							<td width="40%" height="30" align="left">
								${catalog.name}
							</td>	
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>编码</label>
							</td>
							<td width="40%" height="30" align="left">
								${catalog.code}&nbsp;
							</td>
						</tr>
						<tr>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>父目录</label>
							</td>
							<td width="40%" height="30" align="left">
								${catalog.parentName}
							</td>	
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>顺序</label>
							</td>
							<td width="40%" height="30" align="left">
								${catalog.orderNum}
							</td>
						</tr>	
						<tr>
							<td width="10%" height="30" align="left" class="Input_Table_Label">
								<label>列表路径</label>
							</td>
							<td colspan="3" height="30" align="left">
								${catalog.path}
							</td>
						</tr>	
					</table>
			</div>
		</div>
	</div>
