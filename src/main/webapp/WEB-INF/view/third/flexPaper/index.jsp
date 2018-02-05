<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="js/flexpaper/flexpaper.js"></script>
		<script type="text/javascript" src="js/flexpaper/flexpaper_handlers.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
			    $('#documentViewer').FlexPaperViewer(
			            { config : {
			                SWFFile : '{${swfFilePath}[*,0].swf,${fileNum}}',
			
			                Scale : 0.6,
			                ZoomTransition : 'easeOut',
			                ZoomTime : 0.5,
			                ZoomInterval : 0.2,
			                FitPageOnLoad : true,
			                FitWidthOnLoad : false,
			                FullScreenAsMaxWindow : false,
			                ProgressiveLoading : false,
			                MinZoomSize : 0.2,
			                MaxZoomSize : 5,
			                SearchMatchAll : false,
			                InitViewMode : 'Portrait',
			                RenderingOrder : 'flash,html',
			                StartAtPage : '1',
			
			                ViewModeToolsVisible : true,
			                ZoomToolsVisible : true,
			                NavToolsVisible : true,
			                CursorToolsVisible : true,
			                SearchToolsVisible : true,
			                WMode : 'window',
			                localeChain: 'zh_CN'
			            	}
			            }
			    );
			 });
		</script>
	</head>
	<body>
		<div id="documentViewer" class="flexpaper_viewer" style="width:770px;height:500px"></div>
	</body>
</html>