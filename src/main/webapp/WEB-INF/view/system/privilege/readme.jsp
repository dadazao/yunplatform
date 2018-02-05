<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style type="text/css">
<!--
.prireadme {
	font-size:13px;
	margin-left: 10px;
	line-height: 30px;
}
-->  
</style>
<script type="text/javascript">
<!--
$(function() {
	$("#pripanel").height(getContentHeight());
});
//-->
</script>
<div id="pripanel" class="panel" defH="${param.height}">
	<div>
		<p class="prireadme">1、本模块分为两部分，一部分为系统配置出来的模块做权限控制，一部分为自定义开发的模块做权限控制。</p>
		<p class="prireadme">2、点击左边的模块树，右边上半部分将显示出列表按钮和表单按钮的复选框，下半部分显示已经添加的权限列表。</p>
		<p class="prireadme">3、点击权限列表中的维护，打开权限维护表单，可以给这个权限添加或者删除资源。</p>
	</div> 
</div>