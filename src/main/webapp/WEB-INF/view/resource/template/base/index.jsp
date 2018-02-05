<%@ page language="java" contentType="text/html; charset=utf-8"    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Full featured example using jQuery plugin</title>

<!-- Load jQuery -->
<script type="text/javascript" src="/js/jquery/jquery-1.7.1.min.js"></script>

<!-- Load TinyMCE -->
<script type="text/javascript" src="/tiny_mce/jquery.tinymce.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$('textarea.tinymce').tinymce({
			// Location of TinyMCE script
			script_url : '/tiny_mce/tiny_mce.js',

			// General options
			plugins : "style,table," +
			"emotions,iespell,inlinepopups,insertdatetime,preview,searchreplace,print,contextmenu,paste," +
			"directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras",

			// Theme options
			theme_advanced_buttons2 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
			theme_advanced_buttons1 : "tablecontrols,|,hr,removeformat,visualaid|,charmap,emotions,iespell,advhr|,ltr,rtl,|,fullscreen |search,replace,|,bullist,numlist,|outdent,indent|,undo,redo,|cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom"
		});
	});
</script>
<!-- /TinyMCE -->

</head>
<body>
<form>
<textarea id="elm1" name="elm1" rows="15" cols="30" class="tinymce"></textarea>
</form>
</body>
</html>
