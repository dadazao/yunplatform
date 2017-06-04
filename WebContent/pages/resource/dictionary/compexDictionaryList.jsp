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
		xjUrl="<%=basePath %>/pages/resource/dictionarysCompexadd.action?op=new&formId=${formId}";
		plscUrl="<%=basePath %>/pages/resource/${simpleModel}compexdelete.action?model=${model}&formId=${formId}";
		ljscUrl="<%=basePath %>/pages/resource/compexlogicDelete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath %>/pages/resource/dictionarysCompexsave.action";
		bzUrl = "<%=basePath %>/pages/resource/compexshowListHelp.action?listId=${listId}";
		mrUrl = "<%=basePath %>/pages/resource/compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
		ns.common.mouseForButton();
	});

	function selectPreCode(){
		var url="<%=basePath %>/pages/resource/treefetchShowTreeParamById.action?treeId=118&parentId=parentId&parentName=parentName";
		$.pdialog.open(url,"selectDialog","选择",{width:300,height:600,mask:true,resizable:true});
	}
	
	function search() {
		$("#dictionarySearchForm").submit();
	}
	
	function ownerReset(){
		document.forms["dictionarySearchForm"].reset();
	}
</script>
</head>

<body>
	<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/dictionarysCompexlist.action">
		<input type="hidden" id="cloudstongListId" name="listId" value="${listId}"/>
		<input type="hidden" id="cloudstongFormId" name="formId" value="${formId}"/>
		<input type="hidden" name="model" value="${model}"/>
		<input type="hidden" id="rule" name="rule" value="${rule}"/>
		<input type="hidden" name="belong" value="${belong}">
		<input type="hidden" name="parentName" value="${parentName}">
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="levelFirst" value="${levelFirst}" />
		<input type="hidden" name="levelSecond" value="${levelSecond}" />
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize }" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce" varStatus="status">
			<c:if test="${ce.formColumn.isDefaultQuery == 1 || ce.formColumn.isQuery == 1}">
				<c:if test="${fn:containsIgnoreCase(ce.formColumn.dataType, 'varchar')}">
					<c:choose>
						<c:when test="${ce.formColumn.inputType==8}">
							<input type="hidden" name="dyncMapStringPrecise.${ce.formColumn.columnName}" value="${dyncMapStringPrecise[ce.formColumn.columnName]}"/>
						</c:when>
						<c:otherwise>
							<input type="hidden" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${ce.formColumn.dataType=='bigint'}">
					<input type="hidden" name="dyncMapLong.${ce.formColumn.columnName}" value="${dyncMapLong[ce.formColumn.columnName]}"/>
				</c:if>
				<c:if test="${ce.formColumn.dataType=='int'}">
					<input type="hidden" name="dyncMapInteger.${ce.formColumn.columnName}" value="${dyncMapInteger[ce.formColumn.columnName]}"/>
				</c:if>
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
	<c:if test="${hasDefaultQuery==true}">
	<div id="defaultQuery" class="pageHeader" style="height: ${tabulation.queryControl.queryControlHeight==0?'':tabulation.queryControl.queryControlHeight}px;width:${tabulation.queryControl.queryControlWidth}%">
		<div class="searchBar">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/resource/dictionarysCompexlist.action?queryTpe=0" method="post">
				<table class="searchContent">
					<tr>
						<td align="right">
							&nbsp;&nbsp;上级代码
						</td>
						<td align="left">
							<input id="parentName" name="parentName" type="text" readonly='true' onclick="selectPreCode();" value="${parentName}"/>
							<input id="parentId" name="belong" type='hidden' value="${belong}"/>
						</td>
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
										<c:if test="${fn:containsIgnoreCase(ce.formColumn.dataType, 'varchar')}">
											<select name="dyncMapString.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapString[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='int'}">
											<select name="dyncMapInteger.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapInteger[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='bigint'}">
											<select name="dyncMapLong.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapLong[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
									</c:if>
									<c:if test="${ce.formColumn.inputType==6}">
										<c:set var="startdate" scope="page">${ce.formColumn.columnName}_start</c:set>
										<input readonly="true" type="text" id="dyncMapDate.${ce.formColumn.columnName}_start" name="dyncMapDate.${ce.formColumn.columnName}_start" value="${dyncMapDate[startdate]}" style="width: 80px;"/><img onclick="WdatePicker({el:'dyncMapDate.${ce.formColumn.columnName}_start',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
										到
										<c:set var="enddate" scope="page">${ce.formColumn.columnName}_end</c:set>
										<input readonly="true" type="text" id="dyncMapDate.${ce.formColumn.columnName}_end" name="dyncMapDate.${ce.formColumn.columnName}_end" value="${dyncMapDate[enddate]}" style="width: 80px;"/><img onclick="WdatePicker({el:'dyncMapDate.${ce.formColumn.columnName}_end',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
									</c:if>
									<c:if test="${ce.formColumn.inputType==8}">
										<c:if test="${fn:containsIgnoreCase(ce.formColumn.dataType, 'varchar')}">
											<select id="queryselect${status.count}" name="dyncMapStringPrecise.${ce.formColumn.columnName}" belong="list" style="width: ${ce.component.searchComboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapStringPrecise[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='bigint'}">
											<select id="queryselect${status.count}" name="dyncMapLong.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapLong[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='int'}">
											<select id="queryselect${status.count}" name="dyncMapInteger.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapInteger[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<input type="hidden" id="queryselect${status.count}_hidden" name="dyncMapStringCombobox.${ce.formColumn.columnName}" class="combobox" value="true"/>
										<script type="text/javascript">
											$("#queryselect"+${status.count}).combobox({size:20});
											if("false"=="${dyncMapStringCombobox[ce.formColumn.columnName]}") {
												$("#combobox_" + "queryselect"+'${status.count}').val("");
											}
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
							<c:if test="${tabulation.advanceQueryControlId != '-1'}">
								<button type="button" class="listbutton" onclick="showQuery(1);">高级查询</button>
							</c:if>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	</c:if>
	<div id="advanceQuery" align="center" class="pageHeader" style="display:none;height: ${tabulation.advanceQueryControl.queryControlHeight==0?'':tabulation.advanceQueryControl.queryControlHeight}px;">
		<fieldset class="queryFieldset" style="height:${tabulation.advanceQueryControl.queryControlHeight==0?'':tabulation.advanceQueryControl.queryControlHeight-25}px;" >
			<legend style="border:0px;">查询项</legend>
			<form onsubmit="return navTabSearch(this);" action="${listurl}?queryType=1" method="post">
				<table width="95%"  class="queryTable" >
						<c:forEach items="${tabulation.queryColumnExtends}" var="ce" varStatus="status">
							<c:if test="${status.count%tabulation.advanceQueryControl.columnNumber==1 }">
								<tr>
							</c:if>
							<c:if test="${ce.formColumn.isQuery == 1}">
								<td align="right" class="advanceTd" >
									${ce.formColumn.columnZhName}
								</td>
								<td align="left" class="advanceTd">
									<c:if test="${ce.formColumn.inputType==0}">
										<input style="width:120px;" type="text" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
									</c:if>
									<c:if test="${ce.formColumn.inputType==1}">
										<c:if test="${fn:containsIgnoreCase(ce.formColumn.dataType, 'varchar')}">
											<select name="dyncMapString.${ce.formColumn.columnName}" style="width: 127px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapString[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='int'}">
											<select name="dyncMapInteger.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapInteger[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='bigint'}">
											<select name="dyncMapLong.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapLong[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
									</c:if>
									<c:if test="${ce.formColumn.inputType==2}">
										<input type="text" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
									</c:if>
									<c:if test="${ce.formColumn.inputType==3}">
										<input type="radio" name="dyncMapString.${ce.formColumn.columnName}" <c:if test="${dyncMapString[ce.formColumn.columnName]=='-1'}">checked</c:if> value="-1">全部
										<c:forEach items="${ce.codes}" var="code">
											<input type="radio" value="${code.value}" <c:if test="${dyncMapString[ce.formColumn.columnName]==code.value}">checked</c:if> />${code.text}
										</c:forEach>
									</c:if>
									<c:if test="${ce.formColumn.inputType==6}">
										<c:set var="startdate" scope="page">${ce.formColumn.columnName}_start</c:set>
										<input readonly="true" type="text" id="dyncMapDate.${ce.formColumn.columnName}_advanceStart" name="dyncMapDate.${ce.formColumn.columnName}_start" value="${dyncMapDate[startdate]}" style="width: 80px;"/><img onclick="WdatePicker({el:'dyncMapDate.${ce.formColumn.columnName}_advanceStart',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
										到
										<c:set var="enddate" scope="page">${ce.formColumn.columnName}_end</c:set>
										<input readonly="true" type="text" id="dyncMapDate.${ce.formColumn.columnName}_advanceEnd" name="dyncMapDate.${ce.formColumn.columnName}_end" value="${dyncMapDate[enddate]}" style="width: 80px;"/><img onclick="WdatePicker({el:'dyncMapDate.${ce.formColumn.columnName}_advanceEnd',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
									</c:if>
									<c:if test="${ce.formColumn.inputType==8}">
										<c:if test="${fn:containsIgnoreCase(ce.formColumn.dataType, 'varchar')}">
											<select id="queryselect${status.count}" name="dyncMapStringPrecise.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapStringPrecise[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='bigint'}">
											<select id="queryselect${status.count}" name="dyncMapLong.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapLong[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${ce.formColumn.dataType=='int'}">
											<select id="queryselect${status.count}" name="dyncMapInteger.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
												<option value="-1">全部</option>
												<c:forEach items="${ce.codes}" var="code">
													<option value="${code.value}" <c:if test="${dyncMapInteger[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
												</c:forEach>
											</select>
										</c:if>
										<input type="hidden" id="queryselect${status.count}_hidden" name="dyncMapStringCombobox.${ce.formColumn.columnName}" class="combobox" value="true"/>
										<script type="text/javascript">
											$("#queryselect"+${status.count}).combobox({size:20});
											if("false"=="${dyncMapStringCombobox[ce.formColumn.columnName]}") {
												$("#combobox_" + "queryselect"+'${status.count}').val("");
											}
										</script>
									</c:if>
								</td>
							</c:if>
							<c:if test="${status.count%tabulation.advanceQueryControl.columnNumber==0 }">
								</tr>
							</c:if>
						</c:forEach>
				</table>
				<div align="center" style="margin-top: 5px;">
					<input type="hidden" name="listId" value="${listId}"/>
					<button type="button" class="listbutton" onclick="showQuery(0);">隐藏</button>
					<button type="button" class="listbutton" onclick="advanceQuery();">查询</button>
					<div style="display: none;"><button id="advanceQuerySubmit" type="submit" >查询</button></div>
				</div>
			</form>
		</fieldset>
	</div>
	<c:if test="${tabulation.listControl.pageId!='-1'&&tabulation.listControl.pageId!=null}">
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${pageResult.pageSize==20}">selected</c:if>>20</option>
				<option value="50" <c:if test="${pageResult.pageSize==50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${pageResult.pageSize==100}">selected</c:if>>100</option>
				<option value="200" <c:if test="${pageResult.pageSize==200}">selected</c:if>>200</option>
			</select>
			<span>条，共${pageResult.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize }" pageNumShown="${tabulation.listControl.pageManage.showPageNumberCount }" currentPage="${pageResult.currentPage}"></div>
	</div>
	</c:if>
	<form id="tableForm" name="tableForm" method="post">
	<div>
		<table class="table" width="100%" layoutH="168">
			<thead>
				<tr>
					<c:if test="${tabulation.listControl.selectId!='-1'&&tabulation.listControl.selectId!=null}">
					<th width="${tabulation.listControl.selectManage.selectManageWidth}${tabulation.listControl.selectManage.selectManageUnit}" align="${tabulation.listControl.selectManage.selectManagePosition}">
						<c:if test="${tabulation.listControl.selectManage.selectManageIsSelect==1}">
							<input name="all" type="checkbox"  class="checkbox" onclick="selectAll(this,'selectedVOs')"/>
						</c:if>
					</th>
					</c:if>
					<c:if test="${tabulation.listControl.orderId!='-1'&&tabulation.listControl.orderId!=null}">
						<th width="${tabulation.listControl.orderManage.orderManageWidth}${tabulation.listControl.orderManage.orderManageUnit}" align="${tabulation.listControl.orderManage.orderManagePosition}">${tabulation.listControl.orderManage.orderManageShowName}</th>
					</c:if>
					<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
						<c:if test="${ce.formColumn.isShowInList==1}">
							<th align="center" width="${ce.formColumn.colWidth}%">${ce.formColumn.columnZhName}</th>
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
	                    		<input type="checkbox" class="checkbox"  name="selectedVOs" value="<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == 'id'}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>" />
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
											<c:if test="${ce.formColumn.defaultValue == '%username%'}">
												${ce.valueText}
											</c:if>
											<c:if test="${ce.formColumn.defaultValue == '%orgname%'}">
												${ce.valueText}
											</c:if>
											<c:if test="${ce.formColumn.defaultValue != '%username%' && ce.formColumn.defaultValue != '%orgname%'}">
												<c:if test="${ce.formColumn.isLinkView == 1}">
													<a href="#" onclick="eventCompexWH('${viewurl}','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="tce" ><c:if test="${tce.formColumn.columnName == \"id\"}">${tce.formColumn.belongTable}-${tce.formColumn.columnName}:${tce.value};</c:if></c:forEach>')" style="cursor: pointer;color: blue;text-decoration: underline;">${ce.value}</a>
												</c:if>
												<c:if test="${ce.formColumn.isLinkView == 0}">
													<c:if test="${ce.formColumn.columnName != 'comm_createBy'}">${ce.value}</c:if>
													<c:if test="${ce.formColumn.columnName == 'comm_createBy'}">${ce.valueText}</c:if>
												</c:if>
											</c:if>
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
												${ce.valueText}
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
											<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<%=basePath %>/pages/resource/dictionarysCompexview.action','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.button.buttonName}</font></span></a>
											</c:if>
											<c:if test="${curruser!=user.fullname}">
											<a style="cursor: default;" ><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:gray">${tabulationOpt.button.buttonName}</font></span></a>
											</c:if>
											&nbsp;
										</c:when>
										<c:when test="${tabulationOpt.button.buttonName=='查看文件'}">
											&nbsp;
											<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.button.buttonName}</font></span></a>
										</c:when>
										<c:otherwise>
											&nbsp;
											<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<%=basePath %>/pages/resource/dictionarysCompexview.action','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.showName}</font></span></a>
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
	</div>
	</form>
	
</body>
</html>