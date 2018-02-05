<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="js/uploadify/uploadify.css">
    <script type="text/javascript" src="js/uploadify/jquery.uploadify-3.1.min.js"></script>
    <script type="text/javascript">
    $(function() {
        $('#file_upload').uploadify({
        	'buttonText' : '上传文件',
        	'method'   : 'get',
            'swf'      : 'js/uploadify/uploadify.swf',
            'uploader' : '/CompexServlet',
            'multi'    : true,
            'queueID'  : 'fileQueue',
            'auto'     : true,
            'fileSizeLimit' : '10MB',
            'queueSizeLimit' : 4,
            'fileTypeExts' : '*.doc;*.docx'
        });
    });
    </script>


  </head>
  
  <body>
  		<div id="fileQueue">
   		</div>
  		<input type="file" name="file_upload" id="file_upload" style="display: none;"/>
  </body>
</html>
