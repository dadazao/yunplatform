<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
<!--
function closeRTSZ(){
	window.frames["ifrWeboffice"].setRedHeadContent($('#red_head').val());
	$.pdialog.close("redHeadDialog");
}
//-->
</script>
<textarea class="textInput required valid" cols="103" rows="15" name="red_head" id="red_head"></textarea>
<button onclick="closeRTSZ();" style="width:80px;height:24px;" class="listbutton" name="gb"  type="button">关闭</button>