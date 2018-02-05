<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path +"/";
%>
<script type="text/javascript">
	//删除实体
	ns.product.deleteSubPingjia = function(){
		var items = $("#pingjiaSubListForm input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return false;
		}
		var url = "<%=basePath%>/pages/business/product/pingjia/delete.action?"+$("#pingjiaSubListForm").serialize();
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,dialogRefresh);}});
	}
	
	//保存实体
	ns.product.saveSubPingjia = function(){
		var domainId = $("#domainId").val();
		if(domainId){
			var $form = $("#pingjiaSubEditForm");
			var url = '<%=basePath %>/pages/business/product/pingjia/save.action?productId='+domainId;
			$form.attr("action",url);
			$('#pagerForm input[name="productId"]').val(domainId);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录");
		}
	}
	
	//添加实体
	ns.product.addSubPingjia = function(){
		var domainId = $("#domainId").val();
		if(domainId){
			var $form = $("#pingjiaSubEditForm");
			var url = '<%=basePath %>/pages/business/product/pingjia/save.action?productId='+domainId;
			$form.attr("action",url);
			$('#pagerForm input[name="productId"]').val(domainId);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录");
		}
	}
</script>
<div id="pingjiaSubEditDiv">
	<form id="pingjiaSubEditForm" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogRefresh);" novalidate="novalidate">
		<div style="display: none">
			<input type="hidden" name="pingjia.id" value="${pingjia.id}" />
		</div>
		<table class="Input_Table" width="100%" cellspacing="0" cellpadding="2" border="0">
			<tbody>  
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							分数
						</td>
						<td align="left" width="40%">
		 		 			<input type="text" name="pingjia.scorce" value="${pingjia.scorce}"  class="textInput " style="width:180px;"/>
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							详细描述
						</td>
						<td align="left" width="40%">
		 		 			<input type="text" name="pingjia.xxms" value="${pingjia.xxms}"  class="textInput " style="width:180px;"/>
						</td>
					</tr>
			</tbody>
		</table>
	</form>
	<div style="margin-top:5px;" align="right">
		<button onclick="ns.product.addSubPingjia();" style="width:60px;height:24px;" class="listbutton"  type="button">
			添加
		</button>
		<button onclick="ns.product.saveSubPingjia();" style="width:60px;height:24px;" class="listbutton"  type="button">
			保存
		</button>
		<button onclick="ns.product.deleteSubPingjia();" style="width:60px;height:24px;" class="listbutton"  type="button">
			删除
		</button>
	</div>
</div>
