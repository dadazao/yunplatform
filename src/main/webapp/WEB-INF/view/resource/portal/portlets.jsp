<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<c:forEach items="${portletList}" var="portlet">
	<ul class="portlet" style="min-height: ${portlet.minHeight}px; max-height:${portlet.maxHeight}px;overflow:hidden;">
		<div class="phead">
			<div class="ptitle">
				<img src="<c:url value="./pages/resource/portal/image/titleicon.gif"/>" style="padding-top:5px;">
				${portlet.name}
			</div>
			<div class="poper" style="padding-top:6px;"><a href="#" onclick="gotoList('${portlet.listId}','${portlet.name}');" style="text-decoration: none;" title="列表查看所有信息">更多</a> </div>
		</div>
		<div class="pcontent">
		<table class="ptable" width="100%">
			<thead>
				<tr>
					<c:set var="totalHeadNum" value="${fn:length(portlet.tabulation.tabulationColumnExtends)}"></c:set>
					<c:forEach items="${portlet.tabulation.tabulationColumnExtends}" var="ce">
						<c:if test="${ce.formColumn.isShowIndex==1}">
							<th align="left" width="${100/totalHeadNum}%">${ce.formColumn.columnZhName}</th>
						</c:if>
					</c:forEach>
				</tr>
			</thead>
		</table>
		<c:if test="${portlet.isScroll==1}">
		<marquee direction="up" behavior="scroll" onmouseover="this.stop()"  onmouseout="this.start()" scrollamount="2" scrolldelay="100" style="height:300px;width:100%;margin-bottom: 10px;">
		</c:if>
		<table class="ptable" width="100%">
			<tbody>
	            <c:forEach items="${portlet.domainList}" var="dom" varStatus="status">
	                <c:if test="${status.count%2==0}">
	                	<tr class="treven">
	                </c:if>
	                <c:if test="${status.count%2!=0}">
	                	<tr class="trnoeven">
	                </c:if>
	                <c:set var="counter" value="1"></c:set>
	                <c:set var="totalNum" value="${fn:length(dom.tabulationColumnExtends)}"></c:set>
	                	<c:forEach items="${dom.tabulationColumnExtends}" var="ce" varStatus="s">
	                    	<c:if test="${ce.formColumn.columnName == 'comm_createBy'}">
	                    		<c:set var="curruser" value="${ce.valueText}"></c:set>
	                    	</c:if>
	                    	<c:if test="${ce.formColumn.columnName != 'id'}">
	                    		<c:if test="${ce.formColumn.isShowIndex==1}">
	                    			<c:if test="${counter==1}">
										<td align="left" width="${100/totalNum}%" style="text-align:left;padding-left:10px;">
									</c:if>
									<c:if test="${counter!=1}">
										<td align="left" width="${100/totalNum}%" style="text-align:center;"> 
									</c:if>
									<c:set var="counter" value="2"></c:set>
									<c:choose>
										<c:when test="${ce.formColumn.inputType == '0'}">
											<c:if test="${ce.formColumn.defaultValue == '%username%'}">
												<div id="textboxValueSpan${status.count}${s.count}"></div>
												<script>
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "/pages/resource/compexshowUserName.action?userid=${ce.value}",
														success: function(data){
															var user = data;
															value = user.fullname;
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
														url: "/pages/resource/compexshowOrgName.action?orgid=${ce.value}",
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
													<a href="#" onclick="eventCompexWH('/pages/resource/compexview.action','formId=${portlet.formId}&params=<c:forEach items="${dom.tabulationColumnExtends}" var="tce" ><c:if test="${tce.formColumn.columnName == \"id\"}">${tce.formColumn.belongTable}-${tce.formColumn.columnName}:${tce.value};</c:if></c:forEach>')" style="cursor: pointer;color: #10418c;text-decoration: none;">${ce.value}</a>
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
												<div id="treeValueSpan${status.count}${s.count}"></div>
												<script>
													var treeId="${ce.value}";
													var mgrTreeId="${ce.formColumn.compexId}";
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "/pages/resource/compexshowTree.action?treeId="+treeId+"&mgrTreeId="+mgrTreeId,
														success: function(data){
															var tree=data;
															$("#treeValueSpan"+${status.count}+${s.count}).html(tree.name);
														}
													});
												</script>
											</c:if>
										</c:when>
										<c:when test="${ce.formColumn.inputType == '15'}">
											<c:if test="${ce.value!=''}">
												<div id="treeValueSpan${status.count}${s.count}"></div>
												<script>
													$.ajax({
														type:'post',
														dataType: 'json',
														url: "/pages/resource/personChoisedepmPersonChange.action?choisePerson="+"${ce.value}",
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
											${ce.value}
										</c:otherwise>
									</c:choose>
								</td>
								</c:if>
							</c:if>
	                    </c:forEach>
	                </tr>
	            </c:forEach>
			</tbody>
		</table>
		<c:if test="${portlet.isScroll==1}">
		</marquee>
		</c:if>
		</div>
	</ul>
	</c:forEach>
</body>
</html>