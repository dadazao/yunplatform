<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	//删除
	ns.system.deleteSub = function(){
		var url = "<%=basePath%>/pages/system/authCustomerHadware/delete.action";
		var param = $("#subListForm").serialize();
		var url = url + "?" + param;
		var items = $("#subListForm input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return false;
		}
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(url,ns.system.loadSubList);}});
	}
	
	//保存
	ns.system.saveSub = function(){
		var domainId = $("#domainId").val();
		if(domainId){
			var $form = $("#subEditForm");
			var url = '<%=basePath %>/pages/system/authCustomerHadware/save.action?authCustomerId='+domainId;
			$form.attr("action",url);
			$('#pagerForm input[name="authCustomerId"]').val(domainId);
			$form.submit();
			ns.system.loadSubList();
		}else{
			alertMsg.warn("请先保存主记录");
		}
	}
	
</script>
<div id="subEditDiv">
	<form id="subEditForm" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, refreshList);" novalidate="novalidate">
		<div style="display: none">
			<input type="hidden" name="authCustomerHadware.id" value="${authCustomerHadware.id}" />
		</div>
		<table class="Input_Table" width="100%" cellspacing="0" cellpadding="2" border="0">
			<tbody>
				<tr>
					<td class="Input_Table_Label" width="10%" height="25px">Mac地址</td>
					<td width="40%" height="25px">
						<input maxlength="50" type="text" name="authCustomerHadware.mac" value="${authCustomerHadware.mac}" style="width:180px;height:16px;" class="textInput required"/>
					</td>
					<td class="Input_Table_Label" width="10%" height="25px">机器码</td>
					<td width="40%" height="25px">
						<input maxlength="50" type="text" name="authCustomerHadware.computerkey" value="${authCustomerHadware.computerkey}" style="width:180px;height:16px;" class="textInput required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div style="margin-top:5px;" align="right">
		<button onclick="ns.system.saveSub();" style="width:60px;height:24px;" class="listbutton"  type="button">
			保存
		</button>
		<button onclick="ns.system.deleteSub('subListForm');" style="width:60px;height:24px;" class="listbutton"  type="button">
			删除
		</button>
	</div>
</div>
