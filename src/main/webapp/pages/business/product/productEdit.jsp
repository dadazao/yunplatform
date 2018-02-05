<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	//保存实体
	ns.product.saveProduct = function(){
		$("#productForm").submit();
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
	
	$(function(){
		$("#tabDivId").append('<div id="productDetailTab"></div>');
		$("#productDetailTab").loadUrl('pages/business/product/productDetail/edit.action?productId='+$('#domainId').val());
		$("#tabDivId").append('<div id="pingjiaTab"></div>');
		$("#pingjiaTab").loadUrl('pages/business/product/pingjiaTab.jsp');
	});
	
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editProduct" name="editProduct" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveProduct" name="saveProduct" class="listbutton" onclick="ns.product.saveProduct();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected" onclick="ns.common.changeSaveAction('saveProduct','ns.product.saveProduct')"><a><span>基本信息</span></a></li>
					<li onclick="ns.common.changeSaveAction('saveProduct','ns.product.saveProductDetail')"><a><span>商品详情</span></a></li>
					<li onclick="ns.common.changeSaveAction('saveProduct','ns.product.saveProduct')"><a><span>评价</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="productForm" method="post" action="<%=basePath %>/pages/business/product/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="productId" type=hidden name="product.id" value="${product.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${product.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								名称
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="product.name" value="${product.name}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								价格
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="product.price" value="${product.price}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								描述
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="product.description" value="${product.description}"  class="textInput " style="width:180px;"/>
							</td>
					</table>
				</form>
			</div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>