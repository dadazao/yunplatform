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
	ns.desktop.initDesktopItemSelect = function(selectID){
		$.post('<%=basePath%>/pages/platform/desktop/desktopItem/jsonSelect.action',null,function(data){
			var _html = '';
			$.each(data,function(i,obj){
				var selected=(i==0 ? 'selected="selected"' : '');
				_html+='<option value="'+obj.id+'"'+selected+'>'+obj.name+'</option>';
			});
			$('#'+selectID).empty().html(_html);
		},'json');
	}
	//init the select button of desktop layout
	ns.desktop.initDesktopLayoutSelect = function(){
		$.ajaxSetup({async:false}); // important!!
		$.post('<%=basePath%>/pages/platform/desktop/desktopLayout/jsonSelect.action',null,function(data){
			var _html = '';
			$.each(data,function(i,obj){
				var selected=(i==0 ? 'selected="selected"' : '');
				_html+='<option value="'+obj.id+'"'+selected+'>'+obj.name+'</option>';
			});
			$('#desktopLayoutSelect').empty().html(_html);
		},'json');
		$.ajaxSetup({async:true});
	}
	//cache the current layout id
	ns.desktop.currentLayout = null;
	//init the PreView Area 
	ns.desktop.initPreviewArea = function(desktopDesignId){
		if(!desktopDesignId){
			var id = $("#desktopLayoutSelect").val();
			ns.desktop.currentLayout = id;
			$("#previewArea").loadUrl("<%=basePath%>/pages/platform/desktop/desktopLayout/relayout.action?id="+id);
		}else{
			$("#previewArea").loadUrl("<%=basePath%>/pages/platform/desktop/desktopDesign/showDesigned.action?id="+desktopDesignId);
		}
	}
	// change Layout
	ns.desktop.changeLayout = function(){
		alertMsg.confirm("更换后将清除当前布局内容，您确定吗?",{
			okCall:function(){
				var id = $("#desktopLayoutSelect").val();
				ns.desktop.currentLayout = id;
				$('#previewArea').loadUrl("<%=basePath%>/pages/platform/desktop/desktopLayout/relayout.action?id="+id);
			}
		});
	}
	// add a new item to certain column
	ns.desktop.addItemToColumn = function(){
		var currentColumn = ns.desktop.currentColumn;
		if(currentColumn){
			currentColumn.append(function(index,html){
				var _html = '<div>数据获取失败</div>';
				var itemid = $("#desktopItemSelect").val();
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
	ns.desktop.saveLayout = function(){
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
		$('#desktopDesignLayoutItemIds').val(columns);
		$("#currentLayouId").val(ns.desktop.currentLayout);
		$("#desktopDesignForm").submit();
	}
	
	$(function(){
		ns.desktop.initDesktopItemSelect('desktopItemSelect');
		ns.desktop.initDesktopLayoutSelect();
		ns.desktop.initPreviewArea('${desktopDesign.id}');
	});
//-->
</script>
</head>
<body>
	<div style="display:none;">
		<form id="desktopDesignForm" method="post" action="<%=basePath %>/pages/platform/desktop/desktopDesign/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, DWZ.ajaxDone);" onkeydown="return enterNotSubmit(event);">>
			<input id="desktopItemId" type=hidden name="desktopDesign.id" value="${desktopDesign.id}"/>
			<input id="domainId" type="hidden" name="domainId" value="${desktopDesign.id}" />
			<input type="hidden" name="desktopDesign.columnNum" value="${desktopDesign.columnNum}"  />
			<input type="hidden" name="desktopDesign.columnWidths" value="${desktopDesign.columnWidths}"/>
			<input id="desktopDesignLayoutItemIds" type="hidden" name="desktopDesign.layoutItemIds" value="${desktopDesign.layoutItemIds}"/>
			<input id="currentLayouId" type="hidden" name="currentLayouId" value="${currentLayouId}"/>
		</form>
	</div>
	<div id="defaultQuery" class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<select id="desktopLayoutSelect" style="width: 200px;"></select>
					</td>
					<td class="queryTd">
						<button type="button"  class="listbutton" onclick="ns.desktop.changeLayout();" style="width: 100px;">更换布局</button>
					</td>
					<td>
						<select id="desktopItemSelect" style="width: 200px;"></select>
					</td>
					<td class="queryTd">
						<button type="button"  class="listbutton" onclick="ns.desktop.addItemToColumn();" style="width: 100px;">添加到此列</button>
					</td>
					<td><div style="margin-left: 30px;"><button type="button"  class="listbutton" onclick="ns.desktop.saveLayout();" >保存</button></div></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="previewPanel" class="panel collapse" defH="745">
		<h1>首页预览</h1>
		<div id="previewArea"></div>
	</div>
</body>
</html>