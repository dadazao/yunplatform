<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	
	function refreshNavTab() {
		navTab.openTab("main", "<c:url value='/pages/resource/columnlist.action' />", { title:"字段管理", fresh:true, data:{} });
	}
	
	function openDialog() {
		  $.pdialog.open("<%=basePath %>/pages/resource/columnadd.action?op=new","newDialog","新建",{width:1024,height:768,mask:true,resizable:false});
	}
	
	function openSearch() {
		$.pdialog.open("<%=basePath %>/pages/resource/columnsearch.action","searchDialog","高级查询",{width:600,height:240,mask:false,resizable:true});
	}
	
	function deleteDomains(){
		var urlString = "<%=basePath%>/pages/resource/columndelete.action";
		var param = $("#columnForm").serialize();
		var result = urlString + "?" + param;
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result);}
		});
	}
	
	function publish() {
		var urlString = "<%=basePath%>/pages/resource/columnpublish.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#columnId").val(),
			success: function(data){
				if(data == 'success') {
					alertMsg.correct("操作成功");	
					if($("#publishId").val() == 0) {
						$("#buttonFB").val("回收");
						$("#publishId").val(1);
					}else{
						$("#buttonFB").val("发布");
						$("#publishId").val(0);
					}
				}else{
					alertMsg.error("操作失败");
				}
				
			}
		});
	}
	
	function search(){
		$("#columnformSubmitId").submit();
	}
	
	function eventCompexColumnLock(id) {
		$.ajax( {
			type : 'POST',
			url : '<%=basePath%>/pages/resource/columnlock.action',
			data: 'id=' + id,
			dataType:"json",
			success : function(json) {
				refreshList(json);
			}
		});
	}
	
	function eventCompexColumnUnLock(id) {
		$.ajax( {
			type : 'POST',
			url : '<%=basePath%>/pages/resource/columnunLock.action',
			data: 'id=' + id,
			dataType:"json",
			success : function(json) {
				refreshList(json);
			}
		});
	}
	
	$(function(){
		fSelfWidth = '${form.width}';
		fSelfHeight = '${form.height}';
		selfXjTitle = '${form.xjTitle}';
		selfWhTitle = '${form.whTitle}';
		xjUrl="<%=basePath%>/pages/resource/columnadd.action?op=new&formId=${formId}";
		plscUrl="<%=basePath%>/pages/resource/columndelete.action?model=${model}&formId=${formId}";
		ljscUrl="<%=basePath%>/pages/resource/columnlogicDelete.action?model=${model}&formId=${formId}";
		bcUrl = "<%=basePath%>/pages/resource/columnsave.action";
		bzUrl = "<%=basePath%>/pages/resource/compexshowListHelp.action?listId=${listId}";
		mrUrl = "<%=basePath%>/pages/resource/compexisDefault.action?mainTable=${model}&colName=tbl_isdefault&";
		ns.common.mouseForButton();
		
	});
	
</script>
</head>

<body>
	<form id="pagerForm" name="pagerForm" method="post" action="<%=basePath %>/pages/resource/columnlist.action">
		<input type="hidden" name="status" value="${param.status}">
		<input type="hidden" name="dyncMapStringPrecise.tbl_tableId" value="${dyncMapStringPrecise.tbl_tableId}" />
		<input type="hidden" name="dyncMapString.tbl_columnZhName" value="${dyncMapString.tbl_columnZhName}" />
		<input type="hidden" name="dyncMapString.tbl_dataType" value="${dyncMapString.tbl_dataType}" />
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
		<input type="hidden" name="orderField" value="${param.orderField}" />
		<input type="hidden" name="listId" value="${listId}"/>
		<input type=hidden name="model" value="${model}"/>
		<input type=hidden id="rule" name="rule" value="${rule}"/>
		<input type="hidden" name="seqcode" value="${seqcode}"/>
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
	<div id="defaultQuery" class="pageHeader" style="height: ${tabulation.queryControl.queryControlHeight==0?'':tabulation.queryControl.queryControlHeight}px;width:${tabulation.queryControl.queryControlWidth}%"> 
		<div class="searchBar">
			<form onsubmit="return navTabSearch(this);" action="<%=basePath %>/pages/resource/columnlist.action" method="post">
				<table class="searchContent">
					<tr>
						<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce" varStatus="status">
							<c:if test="${ce.formColumn.isDefaultQuery == 1}">
								<td >
									${ce.formColumn.columnZhName}
								</td>
								<td class="queryTd" >
									<c:if test="${ce.formColumn.inputType==0}">
										<input class="queryInput" type="text" name="dyncMapString.${ce.formColumn.columnName}" value="${dyncMapString[ce.formColumn.columnName]}"/>
									</c:if>
									<c:if test="${ce.formColumn.inputType==1}">
										<select name="dyncMapInteger.${ce.formColumn.columnName}" style="width: ${ce.component.comboxWidth}px;">
											<option value="-1">全部</option>
											<c:forEach items="${ce.codes}" var="code">
												<option value="${code.value}" <c:if test="${dyncMapInteger[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${ce.formColumn.inputType==8}">
										<select id="queryselect${status.count}" name="dyncMapStringPrecise.${ce.formColumn.columnName}" style="width: ${ce.component.searchComboxWidth}px;">
											<option value="-1">全部</option>
											<c:forEach items="${ce.codes}" var="code">
												<option value="${code.value}" <c:if test="${dyncMapStringPrecise[ce.formColumn.columnName]==code.value}">selected</c:if> >${code.text}</option>
											</c:forEach>
										</select>
										<input type="hidden" id="queryselect${status.count}_hidden" name="dyncMapStringCombobox.${ce.formColumn.columnName}" value="true"/>
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
							<input type="hidden" name="seqcode" value="${seqcode}"/>
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
							<input name="all" type="checkbox" class="checkbox" onclick="selectAll(this,'selectedVOs')"/>
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
	                    		<input 
	                    			type="checkbox" 
	                    			name="selectedVOs" 
	                    			class="checkbox"
	                    			value="<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == 'id'}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>"  
	                    			<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${(ce.formColumn.columnName == 'tbl_columnName' && (ce.value == 'comm_createBy' || ce.value == 'comm_createDate' || ce.value == 'comm_updateBy' || ce.value == 'comm_updateDate' || ce.value == 'comm_opt_counter' || ce.value == 'comm_mark_for_delete'))||(ce.formColumn.columnName == 'comm_status' && ce.value==1)}">disabled  </c:if></c:forEach>
	                    		/>
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
											<c:if test="${ce.formColumn.columnName == 'comm_status'}">
												<c:set var="commStatus" value="${ce.value}"></c:set>
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
								<c:forEach items="${dom.tabulationColumnExtends}" var="ce" >
									<c:if test="${ce.formColumn.columnName == 'comm_status' && ce.value==0}">
										<c:forEach items="${tabulation.tabulationOpts}" var="tabulationOpt">
											<c:choose>
												<c:when test="${tabulationOpt.button.buttonName=='发布'||tabulationOpt.button.buttonName=='撤回'}">
													&nbsp;
													<c:if test="${curruser==user.fullname}">
													<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<%=basePath %>/pages/resource/columnview.action','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.showName}</font></span></a>
													</c:if>
													<c:if test="${curruser!=user.fullname}">
													<a style="cursor: default;" ><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:gray">${tabulationOpt.showName}</font></span></a>
													</c:if>
													&nbsp;
												</c:when>
												<c:otherwise>
													&nbsp;
													<a style="cursor: pointer;" onclick="eventCompex${tabulationOpt.button.buttonBM}('<%=basePath %>/pages/resource/columnview.action','formId=${formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.formColumn.belongTable}-${ce.formColumn.columnName}:${ce.value};</c:if></c:forEach>')"><span><font style="font-family:${tabulationOpt.button.buttonNameFontStyle};font-size: ${tabulationOpt.button.buttonNameFontSize}px;color:${tabulationOpt.button.buttonNameFontColor}">${tabulationOpt.showName}</font></span></a>
													&nbsp;
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:if>
								</c:forEach>
								<c:if test="${commStatus==0}">
									<a style="cursor: pointer;" onclick="eventCompexColumnLock('<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.value}</c:if></c:forEach>')"><span><font color="blue">加锁</font></span></a>
								</c:if>
								<c:if test="${commStatus==1}">
									<a style="cursor: pointer;" onclick="eventCompexColumnUnLock('<c:forEach items="${dom.tabulationColumnExtends}" var="ce" ><c:if test="${ce.formColumn.columnName == \"id\"}">${ce.value}</c:if></c:forEach>')"><span><font color="blue">解锁</font></span></a>
								</c:if>
								&nbsp;
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