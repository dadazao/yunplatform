<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript" src="<%=basePath %>/js/edittable/edittable2.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/json2/json2.js"></script>
<style>
<!--
	.input{width:110px;}
-->
</style>
<script type="text/javascript">
<!--
var columnsMap = new Map();
function CfgColumn(){
	this.id=null;this.zhName=null;this.enName=null;
	this.formOrder=0;this.listOrder=0;
	this.isEdit=1;this.isView=1;this.notNull=0;this.isQuery=1;this.isInList=1;
	this.defaultValue=null;
	this.inputType=null;
	this.belongTable=null;
	
	this.formInputTypes={
		INPUT:1,//文本框
		SELECT:2,//下拉框
		TEXTAREA:3,//文本域
		DATE:4//日期
	},
	this.getInputStr = function(){
		var str = null;
		if(this.inputType){
			if(this.inputType==this.formInputTypes.INPUT){
				str="文本框";
			}else if(this.inputType==this.formInputTypes.SELECT){
				str="下拉框";
			}else if(this.inputType==this.formInputTypes.TEXTAREA){
				str = "文本域";
			}else{
				str = "日期组件";
			}
		}
		return str;
	}
}
var _tbodyHtml = '';
$.getJSON('<%=basePath %>/pages/resource/tablelistColumns.action?tableName=${table.tableName}',function(data){
	$.each(data,function(index,column){
		var cfgcolumn = new CfgColumn();
		cfgcolumn.id = column.id;cfgcolumn.zhName = column.zhName;cfgcolumn.enName=column.enName;cfgcolumn.belongTable=column.belongTable;
		cfgcolumn.notNull = column.notNull;
		cfgcolumn.inputType = column.inputType;
		cfgcolumn.isView=column.isView; cfgcolumn.isInList=column.isInList;	cfgcolumn.isEdit=column.isEdit;	cfgcolumn.isQuery=column.isQuery;
		cfgcolumn.formOrder = column.formOrder==0? (index+1):column.formOrder;
		cfgcolumn.listOrder = column.listOrder==0? (index+1):column.listOrder;
		
		var _trHtml = '<tr id="'+cfgcolumn.id+'">'+
		'<td class="tdClass" width="3%">'+(index+1)+'</td>'+
		'<td class="tdClass">'+cfgcolumn.zhName+'</td>'+
		'<td rel="formOrder" class="tdClass" onmousedown="editTable(this)">'+cfgcolumn.formOrder+'</td>'+
		'<td rel="isInList" class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isInList?'是':'否')+'</td>'+
		'<td rel="listOrder" class="tdClass" onmousedown="editTable(this)">'+cfgcolumn.listOrder+'</td>'+
		'<td rel="isEdit" class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isEdit?'是':'否')+'</td>'+
		'<td rel="isView" class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isView?'是':'否')+'</td>'+
		'<td rel="notNull" class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.notNull?'是':'否')+'</td>'+
		'<td rel="isQuery" class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isQuery?'是':'否')+'</td>'+
		'<td rel="inputType" class="tdClass" onmousedown="editTable(this)">'+cfgcolumn.getInputStr()+'</td>'+
		'<td class="tdClass">--</td>'+
		'</tr>';
		_tbodyHtml += _trHtml;
		columnsMap.put(cfgcolumn.id,cfgcolumn);
	});
	$('#cfgColumnTbody').append(_tbodyHtml);
	editListTable('columnCfgTable');
});

function renderTable(columnMap){
	var _bodyHtml = '';
	$.each(columnMap,function(id,cfgcolumn){
		var _trHtml = '<tr id="'+id+'">'+
		'<td class="tdClass" width="3%">'+(index+1)+'</td>'+
		'<td class="tdClass">'+cfgcolumn.zhName+'</td>'+
		'<td rel="formOrder" class="tdClass" onmousedown="editTable(this)">'+cfgcolumn.formOrder+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isInList?'是':'否')+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+cfgcolumn.listOrder+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isEdit?'是':'否')+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isView?'是':'否')+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.notNull?'是':'否')+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+(cfgcolumn.isQuery?'是':'否')+'</td>'+
		'<td class="tdClass" onmousedown="editTable(this)">'+cfgcolumn.getInputStr()+'</td>'+
		'<td class="tdClass">--</td>'+
		'</tr>';
		_bodyHtml += _trHtml;
	});
	$('#cfgColumnTbody').append(_bodyHtml);
}

function editListTable(editDomId){
	$("#"+editDomId).editable({ 
		noeditcol: [0,1], //设置这几列不可编辑
		editcol: [{ colindex: 2},
			{colindex: 3,edittype:2,ctrid: "selectTag", css: ".input"}, 
			{colindex: 4}, 
			{colindex: 5,edittype:2,ctrid: "selectTag", css: ".input"},
			{colindex: 6,edittype:2,ctrid: "selectTag", css: ".input"},
			{colindex: 7,edittype:2,ctrid: "selectTag", css: ".input"},
			{colindex: 8,edittype:2,ctrid: "selectTag", css: ".input"},
			{colindex: 9,edittype:2,ctrid: "inputTypeSelect", css: ".input"}],
			editcolumn:false,
		onok: function (tr) {
			var $tr = $(tr);
			var id = $tr.attr('id');
			var $tds = $tr.find('td');
			var column = columnsMap.get(id);
			$.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) {
				var c = $(td).children().get(0);
				var _prop = $(td).attr('rel');
				if(_prop){
					var _value ;
					if (c != null){
						if (c.tagName.toLowerCase() == "select") { 
							$(td).html(c.options[c.selectedIndex].text);
							_value = $(c).val(); 
						} 
						else if (c.tagName.toLowerCase() == "input") { 
							if(c.type.toLowerCase() != "checkbox"){
								$(td).html(c.value); 
								_value = c.value;
							}
						} 
					}
					column[_prop]=_value;
				}
			});
			return true;
		},
		ondel: function (obj) {
			return true;
		},
		onedit : function (obj){
			return true;
		}
	});
}
function saveColumnConfig(){
	var oldName = $('#tableNameOld').val();
	var nName = $('#tableName').val();
	var tableId = $('#tableId').val();
	var param = {"cfgcolumns":JSON.stringify(columnsMap),"tableId":tableId};
	if(oldName.trim()!=nName.trim()){
		param.tableName = nName;
	}
	$.post('<%=basePath %>/pages/resource/tablesaveColumnConfig.action',param,refreshList,'json');
}

function editTable(obj){
	if($(obj).parent().find("select").html()==null){
		$(obj).parent().find(".edit").click();
	}
}

function closeDetailWin(){
	$.pdialog.close("configColumnDialog");
}

function enableLink(select){
	var $select = $(select);
	var value = $select.val();
	if(value==2){
		$select.closest('td').next('td').empty().html('<a href="#" onclick="" style="color:blue;">--</a>');
	}
}

$(function(){
	var dialog  = $.pdialog.getCurrent();
	dialog.find(".dialogContent").css({"overflow":"auto"}); //添加滚动条
});
//-->
</script>
<span>
	<select id="selectTag" style="width:75px;float:center;display: none">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<select id="inputTypeSelect" style="width:75px;float:center;display: none" onchange="enableLink(this)">
		<option value="1">文本框</option>
		<option value="2">下拉框</option>
		<option value="3">文本域</option>
		<option value="4">日期组件</option>
	</select>
</span>
<div>
	<button id="saveCfgButton" style="width:60px;height:24px;border-width: 1px;" onclick="saveColumnConfig()" class="listbutton" name="b1">保存</button>
</div>
<div>
	<div style="margin-top: 5px;">
		<table class="Input_Table" style="width:100%;">
			<tr>
				<td class="Input_Table_Label" width="15%">表名</td>
				<td width="35%"><input id="tableName" value="${table.tableZhName}"/>
					<input id="tableNameOld" value="${table.tableZhName}" type="hidden"/>
					<input id="tableId" type="hidden" value="${table.id}"/>
				</td>
				<td class="Input_Table_Label" width="15%">表拼音名</td><td width="35%">${table.tableName}</td>
			</tr>
		</table>
	    <table id="columnCfgTable" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;overflow:auto;">
			<thead>
				<tr>
					<th class="thClass" width="3%">序号</th>
					<th class="thClass">字段名</th>
					<th class="thClass">表单顺序</th>
					<th class="thClass">列表显示</th>
					<th class="thClass">列表顺序</th>
					<th class="thClass">是否编辑</th>
					<th class="thClass">是否查看</th>
					<th class="thClass">是否必填</th>
					<th class="thClass">查询条件</th>
					<th class="thClass">录入类型</th>
					<th class="thClass">详细设置</th>
					<th class="thClass">操作</th>
				</tr>
			</thead>
			<tbody id="cfgColumnTbody">
			</tbody>	
		</table>
	</div>
</div>