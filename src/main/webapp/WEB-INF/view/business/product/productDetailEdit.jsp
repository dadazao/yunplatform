<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	ns.product.saveProductDetail = function(){
		var domainId = $('#domainId').val();
		if(productId){
			var $form = $("#productDetailForm");
			var url ='<%=basePath %>/pages/business/product/productDetail/save.action?productId='+domainId;
			$form.attr('action',url);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录!");
		}
	}
//-->
</script>
<div>
	<form id="productDetailForm" method="post" action="<%=basePath%>/pages/business/product/productDetail/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSingleDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
		<input id="productDetailId" type=hidden name="productDetail.id" value="${productDetail.id}"/>
  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					规格
				</td>
				<td align="left" width="40%">
 		 			<input type="text" name="productDetail.guige" value="${productDetail.guige}"  class="textInput " style="width:180px;"/>
				</td>
				<td align="left" class="Input_Table_Label" width="10%">
					说明
				</td>
				<td align="left" width="40%">
 		 			<input type="text" name="productDetail.resume" value="${productDetail.resume}"  class="textInput " style="width:180px;"/>
				</td>
			</tr>
		</table>
	</form>
</div>	      	
