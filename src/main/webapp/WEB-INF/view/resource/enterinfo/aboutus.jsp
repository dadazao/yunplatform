<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path;

%>
<html>
<head>
<script type="text/javascript">
	$(function() {
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		dataType:"json",
	  		url:'<%=basePath %>/pages/resource/enterpriseInfoloadDefault.action',
	  		success:function(data){
				$("#content").html(data.enterAbout);
	  		}
	  	});
	});
</script>
</head>
<body>
	<div style="background-color: #FFFFFF;">
		<table cellspacing="0" cellpadding="0" border="0" height="260" width="100%">
			<tr >
				<td width="30%" align="center" valign="middle">
					<img id="aboutLogoImg" src="<%=basePath %>/${logo.logoPath}" width="100" height="100">
				</td>
				<td width="70%">
					<div id="versionId" style="text-align:left;padding-left:10px;">${defaultInfo.version}</div>
					<div id="content" style="line-height:30px; text-align:left;padding:10px;"></div>
					
				</td>
			</tr>
		</table>
		<div align="center" style="padding-top: 15px;">
			&copy;<span id="enterPricename"> ${enterpriseInfo.name}</span>版权所有
		</div>
		<br><br>
	</div>
</body>
</html>