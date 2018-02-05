<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	ns.detail.saveDetail = function(){
		var domainId = $('#domainId').val();
		if(employeeId){
			var $form = $("#detailForm");
			var url ='<%=basePath%>/pages/business/employee/detail/save.action?employeeId='+domainId;
			$form.attr('action',url);
			$form.submit();
		}else{
			alertMsg.warn("请先保存主记录!");
		}
	}
//-->
</script>
<div>
	<form id="detailForm" method="post" action="<%=basePath%>/pages/business/detail/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSingleDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
		<input id="detailId" type=hidden name="detail.id" value="${detail.id}"/>
  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					地址
				</td>
				<td align="left" width="40%">
 		 			<input type="text" name="detail.location" value="${detail.location}"  class="textInput " style="width:180px;"/>
				</td>
				<td align="left" class="Input_Table_Label" width="10%">
					工号
				</td>
				<td align="left" width="40%">
 		 			<input type="text" name="detail.cardnum" value="${detail.cardnum}"  class="textInput " style="width:180px;"/>
				</td>
			</tr>
		</table>
	</form>
</div>	      	
