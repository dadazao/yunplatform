<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
<!--
	// init the select button with the desktopitems 
	ns.desktop.layoutDesign.initDesktopItemSelect = function(selectID){
		$.post('<%=basePath%>/pages/platform/desktop/desktopItem/jsonSelect.action',null,function(data){
			var _html = '';
			$.each(data,function(i,obj){
				var selected=(i==0 ? 'selected="selected"' : '');
				_html+='<option value="'+obj.id+'"'+selected+'>'+obj.name+'</option>';
			});
			$('#'+selectID).empty().html(_html);
		},'json');
	}
	//cache the current layout id
	ns.desktop.layoutDesign.currentLayout = '${desktopLayout.id}';
	ns.desktop.layoutDesign.initPreviewArea = function(desktopLayoutId){
		$("#layoutDesign_PreviewArea").loadUrl("<%=basePath%>/pages/platform/desktop/desktopLayout/relayout.action?id="+desktopLayoutId);
	}
	// add a new item to certain column
	ns.desktop.layoutDesign.addItemToColumn = function(){
		var currentColumn = ns.desktop.currentColumn;
		if(currentColumn){
			currentColumn.append(function(index,html){
				var _html = '<div>数据获取失败</div>';
				var itemid = $("#desktopDesign_desktopItemSelect").val();
				$.ajax({
					async:false,
					url:'<%=basePath%>/pages/platform/desktop/desktopItem/render.action',
					data:{"id":itemid},
					dataType:'html',
					success:function(responseHtml){
						_html=responseHtml;
					}
				});
				return _html;
			});
			currentColumn.children('div').eq(-1).find('div.phead').append(ns.desktop.portletDeleteHtml);
			ns.desktop.initDeletable();
		}else{
			alertMsg.warn("请先选择目标列");
			return false;
		}
	}
	// save the settings to the server
	ns.desktop.layoutDesign.saveLayout = function(){
		var columns = '';
		$('#desktopColumnsDiv').children('div.desktop-preview-column').each(function(i){
			var _itemids = '';
			$(this).children('div.portlet').each(function(index){
				_itemids+=$(this).attr('referedDesktopItem')+',';
			});
			if(_itemids.length>0){
				columns+=_itemids.substring(0,_itemids.length-1)+';';
			}
		});
		if(columns.length>0){
			columns = columns.substring(0,columns.length-1);
		}
		$('#layoutDesignItems').val(columns);
		$("#layoutDesignForm").submit();
	}
	
	$(function(){
		ns.desktop.layoutDesign.initDesktopItemSelect('desktopDesign_desktopItemSelect');
		ns.desktop.layoutDesign.initPreviewArea('${desktopLayout.id}');
	});
//-->
</script>
</head>
<body>
	<div style="display:none;">
		<form id="layoutDesignForm" method="post" action="<%=basePath%>/pages/platform/desktop/desktopLayout/saveDesign.action" class="pageForm required-validate" onsubmit="return validateCallback(this, DWZ.ajaxDone);" onkeydown="return enterNotSubmit(event);">>
			<input type="hidden" name="id" value="${desktopLayout.id}"  />
			<input type="hidden" id="layoutDesignItems" name="items" value="${desktopLayout.items}"/>
		</form>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<select id="desktopDesign_desktopItemSelect" style="width: 200px;"></select>
					</td>
					<td class="queryTd">
						<button type="button"  class="listbutton" onclick="ns.desktop.layoutDesign.addItemToColumn();" style="width: 100px;">添加到此列</button>
					</td>
					<td><div style="margin-left: 30px;"><button type="button" class="listbutton" onclick="ns.desktop.layoutDesign.saveLayout();" >保存</button></div></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="previewPanel" class="panel collapse" defH="745">
		<h1>首页预览</h1>
		<div id="layoutDesign_PreviewArea"></div>
	</div>
</body>
</html>