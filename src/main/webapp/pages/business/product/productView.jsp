<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
				$("#tabDivId").append('<div id="productDetailTab"></div>');
		$("#productDetailTab").loadUrl('pages/business/product/productDetail/view.action?productId='+$('#domainId').val());
		$("#tabDivId").append('<div id="pingjiaTab"></div>');
		$("#pingjiaTab").loadUrl('pages/business/product/pingjiaTab.jsp');
	});
		//--> 
</script>
<div class="buttonPanel">
	<button type="button" id="editProduct" name="editProduct" class="listbutton" onclick="ns.product.editProduct('${product.id}');">修改</button>
	<button type="button" id="saveProduct" name="saveProduct" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${product.id}"/>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
					<li><a><span>商品详情</span></a></li>
					<li><a><span>评价</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							名称
						</td>
						<td align="left" width="40%">
							${product.name}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							价格
						</td>
						<td align="left" width="40%">
							${product.price}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							描述
						</td>
						<td align="left" width="40%">
							${product.description}
						</td>
				</table>
			</div>	      	
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>