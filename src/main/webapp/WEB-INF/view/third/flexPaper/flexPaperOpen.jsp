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
		<script type="text/javascript" src="<%=basePath %>/js/flexpaper/flexpaper.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/flexpaper/flexpaper_handlers.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
				$('#documentViewer').attr('style','width:'+($.pdialog.getCurrent().width()-10)+'px;height:'+($.pdialog.getCurrent().height()-40)+'px;');
			    $('#documentViewer').FlexPaperViewer(
			            { config : {
			                SWFFile : '{${swfDirAndName}[*,0].swf,${fileNum}}',
			
			                Scale : 1.4,
			                ZoomTransition : 'easeOut',
			                ZoomTime : 0.5,
			                ZoomInterval : 0.2,
			                FitPageOnLoad : false,
			                FitWidthOnLoad : false,
			                FullScreenAsMaxWindow : false,
			                ProgressiveLoading : false,
			                MinZoomSize : 0.2,
			                MaxZoomSize : 5,
			                SearchMatchAll : false,
			                InitViewMode : 'Portrait',
			                RenderingOrder : 'flash,html',
			                StartAtPage : '1',
			                PrintEnabled:false,
			                PrintVisible:false,
			
			                ViewModeToolsVisible : true,
			                ZoomToolsVisible : true,
			                NavToolsVisible : true,
			                CursorToolsVisible : true,
			                SearchToolsVisible : true,
			                WMode : 'transparent',
			                localeChain: 'zh_CN'
			            	}
			            }
			    );
			 });
		</script>
	</head>
	<body>
		<div id="cover" class="printCover"></div>
		<div id="documentViewer" class="flexpaper_viewer"></div>
	</body>
</html>