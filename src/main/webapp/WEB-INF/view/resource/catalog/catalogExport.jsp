<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script type="text/javascript">
<!--

	function exportZip(){
		window.open("<%=basePath %>/pages/resource/catalogTreeexportZip.action?"+$("#exportForm").serialize());
	}
//-->
</script>
  <body>
  	<form id="exportForm">
  		<br>
	    <input type="hidden" name="id" value="${param.id}">
	    <table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td width="20%" height="30" align="left" class="Input_Table_Label">
					<label>包</label>
				</td>
				<td width="80%" height="30" align="left">
					<input name="srcPackge" value="${srcPackge}" type="text" size="50" />
				</td>
			</tr>
			<tr>	
				<td width="20%" height="30" align="left" class="Input_Table_Label">
					<label>页面路径</label>
				</td>
				<td width="80%" height="30" align="left">
					<input name="pagePackage" value="${pagePackage}" type="text" size="50" />
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<button id="domainSubmit" type="button" onclick="exportZip();" class="listbutton" >确认</button>
			<button type="button" class="listbutton close">取消</button>
		</div>
	</form>
  </body>
</html>
