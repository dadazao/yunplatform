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
	$(function(){
		fSelfWidth = '${form.width}';
		fSelfHeight = '${form.height}';
		selfXjTitle = '${form.xjTitle}';
		selfWhTitle = '${form.whTitle}';
		xjUrl="<%=basePath%>/pages/resource/${simpleModel}compexadd.action?op=new&formId=${formId}";
		plscUrl="<%=basePath%>/theme/delete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath%>/pages/resource/${simpleModel}compexsave.action";
		bzUrl = "<%=basePath%>/pages/resource/${simpleModel}compexshowListHelp.action?listId=${listId}";
		mrUrl = "<%=basePath%>/pages/resource/${simpleModel}compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
		ns.common.mouseForButton();
		
	});
	
	function loadTheme() {
		$.ajax({
	  		type:'POST',
	  		url: '<%=basePath%>/theme/load.action',
	  		global:'false',
	  		dataType:'json',
	  		success:function(data){
				//data.themeCode = 'red';
				var min = '${min}';
				$("head").find("link[href$='style."+min+"css']").attr("href", "themes/"+data.themeCode+"/style."+min+"css");
	  		}
	  	});
	}

	function eventCompexThemeMR(){
		var chks=$("input[name='selectedIDs']");
		var count=0;
		for(var i=0;i<chks.length;i++){
			if(chks[i].checked==true){
				count+=1;
			}
		}
		if(count==0){
			alertMsg.warn("请选择主题！");
			return;
		}
		if(count>1){
			alertMsg.warn("只能选择一个主题！");
			return;
		}
		$.ajaxSetup({async: false});
		$("#tableForm").submit();
		$.ajaxSetup({async: true});
		loadTheme();
	}
</script>
</head>

<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/theme/list.action">
		<input type="hidden" name="listId" value="${listId}"/>
		<input type=hidden name="model" value="${model}"/>
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
			<c:if test="${ce.formColumn.isDefaultQuery == 1}">
				<input type="hidden" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
			</c:if>
		</c:forEach>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
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
			</td></tr></table>
		</div>
	</div>
	<div id="defaultQuery" class="pageHeader" style="height: ${tabulation.queryControl.queryControlHeight==0 ? '' : tabulation.queryControl.queryControlHeight}px;width:${tabulation.queryControl.queryControlWidth}%">
		<div class="searchBar">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/theme/list.action" method="post">
				<table class="searchContent">
					<tr>
						<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce" varStatus="status">
							<c:if test="${ce.formColumn.isDefaultQuery == 1}">
								<td>
									${ce.formColumn.columnZhName}
								</td>
								<td class="queryTd">
									<c:if test="${ce.formColumn.inputType==0}">
										<input class="queryInput" type="text" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
									</c:if>
									<c:if test="${ce.formColumn.inputType==1}">
										<select name="dyncMapString.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
											<option value="">全部</option>
											<c:forEach items="${ce.codes}" var="code">
												<option value="${code.value}" <c:if test="${dyncMapString[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${ce.formColumn.inputType==8}">
										<select id="queryselect${status.count}" name="dyncMapString.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
											<option value="">全部</option>
											<c:forEach items="${ce.codes}" var="code">
												<option value="${code.value}" <c:if test="${dyncMapString[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
											</c:forEach>
										</select>
										<script type="text/javascript">
											$("#queryselect"+${status.count}).combobox({size:15});
										</script>
									</c:if>
								</td>
							</c:if>
						</c:forEach>
						<td>
							<input type="hidden" name="listId" value="${listId}"/>
							<c:if test="${tabulation.queryControl.isHasButton==0}">
								<button type="button" class="listbutton" onclick="query();">查询</button>
								<div style="display: none;"><button id="querySubmit" type="submit" >查询</button></div>
							</c:if>
							<c:if test="${tabulation.queryControl.isHasGjbutton==0}">
								<button type="submit" class="listbutton">高级查询</button>
							</c:if>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<c:if test="${tabulation.listControl.pageId!='-1'&&tabulation.listControl.pageId!=null}">
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<input type="hidden" name="numPerPage" value="${pageResult.pageSize }">
			<span>${pageResult.pageSize }条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${tabulation.listControl.pageManage.showPageNumberCount }" currentPage="${pageResult.currentPage}"></div>
	</div>
	</c:if>
	<form id="tableForm" name="logoForm" method="post" action="<%=basePath %>/theme/change.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<c:if test="${tabulation.listControl.selectId!='-1'&&tabulation.listControl.selectId!=null}">
					<th width="${tabulation.listControl.selectManage.selectManageWidth}${tabulation.listControl.selectManage.selectManageUnit}" align="${tabulation.listControl.selectManage.selectManagePosition}">
						<c:if test="${tabulation.listControl.selectManage.selectManageIsSelect==1}">
							<input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedIDs')"/>
						</c:if>
					</th>
					</c:if>
					<c:if test="${tabulation.listControl.orderId!='-1'&&tabulation.listControl.orderId!=null}">
						<th width="${tabulation.listControl.orderManage.orderManageWidth}${tabulation.listControl.orderManage.orderManageUnit}" align="${tabulation.listControl.orderManage.orderManagePosition}">${tabulation.listControl.orderManage.orderManageShowName}</th>
					</c:if>
					<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
						<c:if test="${ce.formColumn.isShowInList==1}">
							<th align="center" width="${ce.formColumn.colWidth}${ce.formColumn.colUnit}">${ce.formColumn.columnZhName}</th>
						</c:if>
					</c:forEach>
					<c:if test="${tabulation.listControl.operationId!='-1'&&tabulation.listControl.operationId!=null}">
						<th width="${tabulation.listControl.operationManage.optManageWidth}${tabulation.listControl.operationManage.optManageUnit}" align="${tabulation.listControl.operationManage.optManagePosition}">${tabulation.listControl.operationManage.optManageShowName}</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
	            <c:forEach items="${pageResult.content}" var="dom" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr target="id_column" rel="1" class='event'>
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr target="id_column" rel="1">
	                </c:if>
	                	<c:if test="${tabulation.listControl.selectId!='-1'&&tabulation.listControl.selectId!=null}">
	                    	<td align="${tabulation.listControl.selectManage.selectManagePosition}">
	                    		<input type="checkbox" class="checkbox"  name="selectedIDs" value="<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == 'id'}">${ce.value}</c:if></c:forEach>" />
	                    	</td>
	                    </c:if>
	                    <c:if test="${tabulation.listControl.orderId!='-1'&&tabulation.listControl.orderId!=null}">
	                    	<td align="${tabulation.listControl.orderManage.orderManagePosition}">${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</td>
	                    </c:if>
	                    <c:forEach items="${dom.tabulationColumnExtends}" var="ce" varStatus="s">
	                    	<c:if test="${ce.formColumn.columnName == 'comm_createBy'}">
	                    		<c:set var="curruser" value="${ce.valueText}"></c:set>
	                    	</c:if>
	                    	<c:if test="${ce.formColumn.columnName != 'id'}">
	                    		<c:if test="${ce.formColumn.isShowInList==1}">
								<td>
									<c:choose>
										<c:when test="${ce.formColumn.inputType == '0'}">
											<c:if test="${ce.formColumn.columnName != 'comm_createBy'}">${ce.value}</c:if>
											<c:if test="${ce.formColumn.columnName == 'comm_createBy'}">${ce.valueText}</c:if>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '1'}">
											<c:forEach items="${ce.codes}" var="code">
												<c:if test="${code.value==ce.value}">
													${code.text}
												</c:if>
											</c:forEach>
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
										</c:when>
										<c:when test="${ce.formColumn.inputType == '4'}">
											<c:forEach items='${fn:split(ce.value, ",")}' var="vc" varStatus="counts">
												<c:forEach items="${ce.codes}" var="code">
													<c:if test="${vc==code.value}">
														<c:choose>
														<c:when test="${counts.count==1}">
															${code.text}
														</c:when>
														<c:otherwise>
															,${code.text}
														</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</c:forEach>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '7'}">
											<c:if test="${ce.value!=''}">
												<div id="treeValueSpan${status.count}${s.count}"></div>
												<script>
													var treeId="${ce.value}";
													var mgrTreeId="${ce.formColumn.treeId}";
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "<%=basePath %>/pages/resource/compexshowTree.action?treeId="+treeId+"&mgrTreeId="+mgrTreeId,
														success: function(data){
															var tree=data;
															$("#treeValueSpan"+${status.count}+${s.count}).html(tree.name);
														}
													});
												</script>
											</c:if>
										</c:when>
										<c:otherwise>
											${ce.value}
										</c:otherwise>
									</c:choose>
									
								</td>
								</c:if>
							</c:if>
	                    </c:forEach>
						<c:if test="${tabulation.listControl.operationId!='-1'&&tabulation.listControl.operationId!=null}">
							<td align="center">
								<c:forEach items="${tabulation.tabulationOpts}" var="tabulationOpt" varStatus="ops">
									<c:if test="${tabulationOpt.hasAuth=='0' && ops.count<=tabulation.listControl.operationManage.optManagerCount}">
									<c:choose>
										<c:when test="${tabulationOpt.button.buttonName=='发布'||tabulationOpt.button.buttonName=='撤回'}">
											&nbsp;
											<c:if test="${curruser==user.fullname}">
											<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<%=basePath %>/pages/resource/compexview.action','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.showName}</font></span></a>
											</c:if>
											<c:if test="${curruser!=user.fullname}">
											<a style="cursor: default;" ><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:gray">${tabulationOpt.showName}</font></span></a>
											</c:if>
											&nbsp;
										</c:when>
										<c:otherwise>
											&nbsp;
											<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<%=basePath %>/pages/resource/compexview.action','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.showName}</font></span></a>
											&nbsp;
										</c:otherwise>
									</c:choose>
									</c:if>
								</c:forEach>
							</td>
						</c:if>
	                </tr>
	            </c:forEach>
			</tbody>	
		</table>
	</form>
</body>
</html>