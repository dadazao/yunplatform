<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
</script>
</head>
<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/${simpleModel}compexsingleList.action">
		<input type=hidden name="model" value="${model}"/>
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<input type="hidden" name="formId" value="${formId}"/>
		<input type="hidden" name="tabId" value="${tabId}"/>
		<input type="hidden" name="partitionId" value="${partitionId}"/>
		<input type="hidden" name="params" value="${params}"/>
		<input type="hidden" name="listDiv" value="${listDiv}"/>
	</form>
	<div class="pageContent" style="display: none">
		<div class="panelBar">
			<c:forEach items="${tabulationButtons}" var="tabulationButton" varStatus="status">
				<c:if test="${tabulationButton.hasAuth=='0'}">
					<c:if test="${tabulationButton.buttonType == '0'}">
						<button name="b${status.count}" class="listbutton" onclick="eventCompex${tabulationButton.funcName}();" style="width:${tabulationButton.button.buttonWidth}px;height:${tabulationButton.button.buttonHeight}px;background: ${tabulationButton.button.buttonBackGroundColor};border-color: ${tabulationButton.button.buttonBorderColor};border-width: ${tabulationButton.button.buttonBorderSize}px;"><font color="${tabulationButton.button.buttonNameFontColor}" style="font-family: ${tabulationButton.button.buttonNameFontStyle};font-size: ${tabulationButton.button.buttonNameFontSize}px;">${tabulationButton.showName}</font></button>	
					</c:if>
				</c:if>
				<c:if test="${tabulationButton.hasAuth=='1'}">
					<c:if test="${tabulationButton.buttonType == '0'}">
						<button name="b${status.count}" class="listbuttonDisable" disabled="disabled" onclick="eventCompex${tabulationButton.funcName}();" style="width:${tabulationButton.button.buttonWidth}px;height:${tabulationButton.button.buttonHeight}px;background: ${tabulationButton.button.buttonBackGroundColor};border-color: ${tabulationButton.button.buttonBorderColor};border-width: ${tabulationButton.button.buttonBorderSize}px;"><font color="${tabulationButton.button.buttonNameFontColor}" style="font-family: ${tabulationButton.button.buttonNameFontStyle};font-size: ${tabulationButton.button.buttonNameFontSize}px;">${tabulationButton.showName}</font></button>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<input type="hidden" name="numPerPage" value="20">
			<span>20条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" rel="${listDiv}" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
	</div>
	<form id="${listDiv}Form" name="${listDiv}Form" method="post">
	    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
			<tbody>
				<thead>
					<tr>
						<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">序号</th>
						<c:forEach items="${tabulationColumns}" var="ce">
							<th class="thClass" style=" border-bottom:1px #d0d0d0 solid; border-right:1px #d0d0d0 solid;">${ce.formColumn.columnZhName}</th>
						</c:forEach>
					</tr>
				</thead>
	            <c:forEach items="${pageResult.content}" var="dom" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                    <td style="width: 5%; border-bottom:1px #ededed solid; border-right:1px #ededed solid;" class="tdClass">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    
	                    <c:forEach items="${dom.tabulationColumnExtends}" var="ce" varStatus="s">
	                    	<c:if test="${ce.formColumn.columnName != 'id'}">
								<td class="tdClass" style=" border-bottom:1px #ededed solid; border-right:1px #ededed solid;">
									<c:choose>
										<c:when test="${ce.formColumn.inputType == '0'}">
											<c:if test="${ce.formColumn.defaultValue == '%username%'}">
												<div id="textboxValueSpan${status.count}${s.count}"></div>
												<script>
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "pages/resource/compexshowUserName.action?userid=${ce.value}",
														async:false,
														success: function(data){
															var user = data;
															value = user.person.tblXingming;
															$("#textboxValueSpan"+${status.count}+${s.count}).html(value);
														}
													});
												</script>
											</c:if>
											<c:if test="${ce.formColumn.defaultValue == '%orgname%'}">
												<div id="textboxValueSpan${status.count}${s.count}"></div>
												<script>
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "pages/resource/compexshowOrgName.action?orgid=${ce.value}",
														async:false,
														success: function(data){
															var org = data;
															value = org.tblName;
															$("#textboxValueSpan"+${status.count}+${s.count}).html(value);
														}
													});
												</script>
											</c:if>
											<c:if test="${ce.formColumn.defaultValue != '%username%' && ce.formColumn.defaultValue != '%orgname%'}">
												<c:if test="${ce.formColumn.isLinkView == 1}">
													<a href="#" onclick="eventCompexWH('${viewurl}','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="tce" ><c:if test="${tce.formColumn.columnName == \"id\"}">${tce.formColumn.belongTable}-${tce.formColumn.columnName}:${tce.value};</c:if></c:forEach>')" style="cursor: pointer;color: blue;text-decoration: underline;">${ce.value}</a>
												</c:if>
												<c:if test="${ce.formColumn.isLinkView == 0}">${ce.value}</c:if>
											</c:if>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '1'}">
											<c:forEach items="${ce.codes}" var="code">
												<c:if test="${code.value==ce.value}">
													${code.text}
												</c:if>
											</c:forEach>
											<c:if test="${ce.value=='-1'}">
												系统默认
											</c:if>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '3'}">
											<c:forEach items="${ce.codes}" var="code">
												<c:if test="${code.value==ce.value}">
													${code.text}
												</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '8'}">
											<c:forEach items="${ce.codes}" var="code">
												<c:if test="${code.value==ce.value}">
													${code.text}
												</c:if>
											</c:forEach>
											<c:if test="${ce.value=='-1'}">
												系统默认
											</c:if>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '4'}">
											<c:set var="checkboxvalue" value=""/>
											<c:forEach items='${fn:split(ce.value, ",")}' var="vc" varStatus="counts">
												<c:forEach items="${ce.codes}" var="code">
													<c:if test="${vc==code.value}">
														<c:choose>
														<c:when test="${counts.count==1}">
															<c:set var="checkboxvalue" value="${code.text}"/>
														</c:when>
														<c:otherwise>
															<c:set var="checkboxvalue" value="${checkboxvalue},${code.text}"/>
														</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</c:forEach>
											<c:out value="${checkboxvalue}" />
										</c:when>
										<c:when test="${ce.formColumn.inputType == '7'}">
											<c:if test="${ce.value!=''}">
												${ce.valueText}
											</c:if>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '15'}">
											<c:if test="${ce.value!=''}">
												<div id="treeValueSpan${status.count}${s.count}"></div>
												<script>
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "pages/resource/personChoisedepmPersonChange.action?choisePerson="+"${ce.value}",
														success: function(data){
															var _name='';
															$.each(data,function (entryIndex,entry){
																_name += entry.tblXingming+";";
															})
															$("#treeValueSpan"+${status.count}+${s.count}).html(_name);
														}
													});
												</script>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${ce.value=='-1'}">
												系统默认
											</c:if>
											<c:if test="${ce.value!='-1'}">
												${ce.value}
											</c:if>
										</c:otherwise>
									</c:choose>
									
								</td>
							</c:if>
	                    </c:forEach>
	                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>