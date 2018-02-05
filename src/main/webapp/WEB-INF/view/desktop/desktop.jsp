<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<style type="text/css">
div.noDesktop{width:500px;height:150px;background-color:#E5EFFF;margin:100px auto;border: 5px solid #dedede;-moz-border-radius: 15px;-webkit-border-radius: 15px;border-radius:15px;}
div.tip-header{padding:10px 0px 5px 10px;background-color:#E5EFFF;font-size:16px;color:red;font-weight:bold;font-family: 'Microsoft Yahei', verdana;-moz-border-radius: 15px;-webkit-border-radius: 15px;border-radius:15px;}
</style>
<script type="text/javascript">
	//init the columns width 
	ns.desktop.index.initColumnsWidth = function(){
		var totalWidth = getContentWidth();
		$("#desktopDiv").children("div").each(function(){
			var percentage = $(this).attr("percentage");
			$(this).width(totalWidth*percentage/100-5);
		});
	}
	ns.desktop.index.renderItems = function(columnItemIds){
		if(columnItemIds){
			var columnItems= columnItemIds.split(';');
			var $columnDivs = $('#desktopDiv').children('div.desktop-index-column');
			for(var i=0;i<columnItems.length;i++){
				var $column = $columnDivs.eq(i);
				var itemIds = columnItems[i].split(',');
				for(var k=0;k<itemIds.length;k++){
					$column.append(function(index,html){
						var _html = '<div>数据获取失败</div>';
						$.ajax({
							async:false,
							global:false,
							url:'<%=basePath%>/pages/platform/desktop/desktopItem/render.action',
							data:{"id":itemIds[k]},
							dataType:'html',
							success:function(responseHtml){
								_html=responseHtml;
							}
						});
						return _html;
					});
				}
			}
		}
		//else .. do nothing
	}
	// initialize sortable and draggable
	ns.desktop.index.initSortable = function(){
		$(".desktop-index-column" ).sortable({
	      connectWith: ".desktop-index-column",
	      tolerance:"pointer",
	      cursor:"move",
	      placeholder:'sort_placeholder',
	    });
	}
	$(function(){
		ns.desktop.index.initColumnsWidth();
		ns.desktop.index.renderItems('${desktopDesign.layoutItemIds}');
		ns.desktop.index.initSortable();
	});
</script>
<div id="desktopDiv">
	<c:if test="${fn:length(desktopDesign.columnWidths)>0}">
	<c:forEach items="${fn:split(desktopDesign.columnWidths,',')}" var="column">
		<div class="desktop-index-column" percentage="${column}"></div>
	</c:forEach>
	</c:if>
	<c:if test="${fn:length(desktopDesign.columnWidths)<=0}">
		<div class="noDesktop">
			<div class="tip-header">友情提示</div>
			<div class="divider"></div>
			<div style="margin:40px auto;">
				<span style="padding:20px;font-family: 'Microsoft Yahei', verdana;font-size:14px;">系统桌面尚未设置，请联系管理员</span>
			</div>
		</div>
	</c:if>
</div>