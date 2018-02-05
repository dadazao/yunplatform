<%@page pageEncoding="UTF-8" %>
<script type="text/javascript">
<!--
	function resetUser(){
		$("#_executors_").val('');
		$("#spanSelectUser").html('');
	}
//-->
</script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<table class="table-grid">
		<thead>
		<tr>
			<th height="28" width="20%">选择执行人</th>
			<td>
				<input type="hidden" id="_executors_" name="_executors_">
				<span id="spanSelectUser"></span>
				<a href="javascript:;" class="link grant" onclick="selExeUsers()">选择...</a>
				<a href="javascript:;" class="link reset" onclick="resetUser()">重置</a>
				</td>
		</tr>
		</thead>			
</table>