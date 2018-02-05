<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<style type="text/css">
	.desktop-preview-column{
		border:1px dotted #b8d0d6;background-color:white;margin:0px 5px 0px 0px;
		overflow:auto;float:left;
		min-width:100px;min-height:300px;
	}
	.desktop-preview-column.selected{background-color:#C3E4F3;}
</style>
<script type="text/javascript">
	// store the currentColumn selected
	ns.desktop.currentColumn = null;
	ns.desktop.portletDeleteHtml = '<div class="itemremove" style="display:none;float:right;z-index: 100;padding-right:20px;padding-top:6px;width:26px;height:15px;background-color:white;">' +
		'<a style="cursor:pointer;" onclick="ns.desktop.removeItem(this)" title="删除该模块">' +
		'<span style="color:red;">删除</span></a></div>';
	//init the columns 1.set the width 2. bind the click event
	ns.desktop.initColumns = function(){
		var totalWidth = $("#previewPanel").width()-12-15;
		var totalHeight = $("#previewPanel").height();
		$("#desktopColumnsDiv").children("div").each(function(){
			var percentage = $(this).attr("percentage");
			$(this).width(totalWidth*percentage/100-5).height(totalHeight-45).click(function(){
				ns.desktop.currentColumn = $(this);
				$(this).addClass("selected").siblings().removeClass("selected");
			});
		});
		//select the first column as default
		$("#desktopColumnsDiv").children("div").eq(0).click();
	}
	ns.desktop.addDeleteDiv = function(){
		var $columnDivs = $('#desktopColumnsDiv').children('div.desktop-preview-column');
		$columnDivs.each(function(){
			$(this).children("div").each(function(){
				$(this).find('div.phead').append(ns.desktop.portletDeleteHtml);
			});
		});
	}
	ns.desktop.renderItems = function(columnItemIds){
		if(columnItemIds){
			var columnItems= columnItemIds.split(';');
			var $columnDivs = $('#desktopColumnsDiv').children('div.desktop-preview-column');
			for(var i=0;i<columnItems.length;i++){
				var $column = $columnDivs.eq(i);
				var itemIds = columnItems[i].split(',');
				for(var k=0;k<itemIds.length;k++){
					$column.append(function(index,html){
						var _html = '<div>数据获取失败</div>';
						$.ajax({
							async:false,
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
			//add delete div
			ns.desktop.addDeleteDiv();
		}
		//else .. do nothing
	}
	//initialize sortable
	ns.desktop.initSortable = function(){
		$(".desktop-preview-column" ).sortable({
	      connectWith: ".desktop-preview-column",
	      tolerance:"pointer",
	      cursor:"move",
	      placeholder:'sort_placeholder',
	    });
	}
	// add Delete when mouse over
	ns.desktop.initDeletable = function(){
		$(".desktop-preview-column").each(function(){
			$(this).children("div").each(function(){
				$(this).mouseenter(function(){
					$(this).find('div.itemremove').show();
					$(this).find('div.poper').hide();
				}).mouseleave(function(){
					$(this).find('div.itemremove').hide();
					$(this).find('div.poper').show();
				});
			});
		});
	}
	ns.desktop.removeItem = function(dom){
		$(dom).closest('div.portlet').remove();
	}
	$(function(){
		ns.desktop.initColumns();
		ns.desktop.renderItems('${columnItemIds}');
		ns.desktop.initSortable();
		ns.desktop.initDeletable();
	});
</script>
<div id="desktopColumnsDiv">
	<c:forEach items="${columns}" var="column">
		<div class="desktop-preview-column" percentage="${column}"></div>
	</c:forEach>
</div>