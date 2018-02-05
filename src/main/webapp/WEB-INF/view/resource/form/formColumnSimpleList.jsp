<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src="../../../../js/edittable/edittable.js"></script>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<style>
<!--
	.input{width:110px;}
-->
</style>
<script type="text/javascript">
<!--
$(function () {
	if('${op}' != 'view'){
		editListTable("simpleformColumnEditTable");
	}
	if('${op}'=='view'){
		$("#formcolumntabid").attr("disabled","disabled");
		$("#formcolumnpartitionid").attr("disabled","disabled");
		$("#tableId").attr("disabled","disabled");
		$("#relColumnId").attr("disabled","disabled");
	}
});

function editListTable(editDomId){
	$("#"+editDomId).editable({ 
	noeditcol: [0,1], //设置这几列不可编辑
	editcol: [{ colindex: 2, edittype: 2, ctrid: "columnId", css: ".input" },{colindex: 3}, { colindex: 4, edittype: 2, ctrid: "sel2", css: ".input" }, { colindex: 5 }, { colindex: 6 },{ colindex: 7, edittype: 2, ctrid: "sel3", css: ".input" },{ colindex: 8, edittype: 2, ctrid: "sel1", css: ".input" }], //配置表格的编辑列 ctrid:为关联的dom元素id
		onok: function (obj) {
			var tds=obj.children();
			var id = $(tds.get(0)).children().get(0).value;
			var columnId = $(tds.get(2)).children().get(0).value;
			var formOrder = $(tds.get(3)).children().get(0).value;
			var isShowInList = $(tds.get(4)).children().get(0).value;
			var listOrder = $(tds.get(5)).children().get(0).value;
			var colWidth = $(tds.get(6)).children().get(0).value;
			
			var isDefaultQuery = $(tds.get(7)).children().get(0).value;
			var inputType = $(tds.get(8)).children().get(0).value;
			
			var params="formColumn.columnId="+columnId+"&formColumn.formOrder="+formOrder+"&formColumn.isShowInList="+isShowInList+"&formColumn.listOrder="+listOrder+"&formColumn.colWidth="+colWidth+"&formColumn.isDefaultQuery="+isDefaultQuery+"&formColumn.inputType="+inputType;
			var urlString = "<%=basePath %>/pages/resource/form/saveColumnAtList.action?formColumn.id=" + id+"&formColumn.formId=${formId}&formId=${formId}";
			var tabId = $("#formcolumntabid").val()==null? "-1" : $("#formcolumntabid").val();
			var partitionId = $("#formcolumnpartitionid").val()==null? "-1" : $("#formcolumnpartitionid").val();
			var tableId = $("#tableId").val();
			var relColumnId  = $("#relColumnId").val();
			urlString +="&tabId="+tabId+"&partitionId="+partitionId+"&tableId="+tableId+"&relColumnId="+relColumnId;
			var bok=true;
			$.ajax({
				type:'post',
				url: urlString,
				data:params,
				async:false,
				dataType: 'json',
				success: function(data){
					if(data=="true"){
						alert("此字段已经添加过，不允许重复添加");
						bok = false;
					}else{
						var formColumnId = data;
						$($(tds.get(0)).children().get(0)).val(formColumnId);
					}
				}
			});
			
			return bok;
		},
		ondel: function (obj) {
			var tds=obj.children();
			var id = $(tds.get(0)).children().get(0).value;
			var urlString = "<%=basePath %>/pages/resource/form/deleteColumn.action?id="+id;
			$.ajax({
				type:'post',
				url: urlString,
				async: false,
				success: function(data){
					alertMsg.info("删除成功");
					//refresh();
				}
			});
			return true;
		},
		onedit : function (obj){
			enableLink(obj,"edit");
			return true;
		}
	});
}

function editTable(obj){
	if($(obj).parent().find("select").html()==null){
		$(obj).parent().find(".edit").click();
	}
}

function addRecord(){
	var tabid=$("#formcolumntabid option:selected").val()==undefined?"-1":$("#formcolumntabid option:selected").val();
	if(tabid=="-1"){
		alertMsg.info("请选择选项卡！");
		return false;
	}

	var trs=$("#formcolumn_list_divid").find("tr");
	if(trs.length>${fn:length(columns)}){
		alertMsg.info("超过最大字段值，不允许添加！");
		return;
	}
	$("#simpleformColumnEditTable:first-child").prepend("<tr id='newtr'><td class='tdClass' width='3%'><input type='checkbox' style='margin-top:4px;*margin-top:0px;'  name='selectedSubIDs' value=''></td><td class='tdClass' width='3%'></td><td class='tdClass'></td><td class='tdClass'></td><td class='tdClass'></td><td class='tdClass'></td><td class='tdClass'></td><td class='tdClass'></td><td class='tdClass'></td><td class='tdClass'><a href='#' style='text-decoration: none;'>详细设置</a></td></tr>");
	editListTable("newtr");
	$("#newtr").find(".edit").click();
	
	var tr=$($("#newtr"));
	var tds=tr.children();
	
	//控制查询条件和详细设置按钮
	enableLink($($(tds.get(7)).children().get(0)));
	//初始化
	enableTableSelect();
}

function autoAddRecord(){
	var tabid=$("#formcolumntabid option:selected").val()==undefined?"-1":$("#formcolumntabid option:selected").val();
	var partitionid=$("#formcolumnpartitionid option:selected").val()==undefined?"-1":$("#formcolumnpartitionid option:selected").val();
	var tableid=$("#tableId option:selected").val()==undefined?"-1":$("#tableId option:selected").val();
	var relColumnId=$("#relColumnId option:selected").val()==undefined?"-1":$("#relColumnId option:selected").val();
	var urlString="<%=basePath %>/pages/resource/form/autoAddFormColumn.action?formId=${formId}&tabId="+tabid+"&partitionId="+partitionid+"&tableId="+tableid+"&relColumnId="+relColumnId;
	if(tabid=="-1"){
		alertMsg.info("请选择选项卡！");
		return false;
	}
	$.ajax({
		type:'post',
		url: urlString,
		success: function(data){
			refreshRecord();
		}
	});
}

function showDetailsPage(obj,inputType,id){
	var currentInputType;
	var tr = $(obj).parent().parent();
	var tds=tr.children();
	var trs = tr.parent().children();
	//重置背景颜色
	for(var m=0;m<trs.length;m++){
		var tmptr = trs[m];
		var tmptds = $(tmptr).children();
		for(var n=0;n<tmptds.length;n++){
			$(tmptds[n]).css("background-color","white");
		}
	}
	//设置tr背景颜色start
	for(var i=0;i<tds.length;i++){
		$(tds[i]).css("background-color","#D5E7FD");
	}
	//设置tr背景颜色end
	
	if($(tds.get(8)).find("select").html()==null){
		currentInputType=inputType;
	}else{
		currentInputType=$(tds.get(8)).children().get(0).value;
	}
	var kd = 600;
	var gd = 280;
	if(currentInputType=="1"||currentInputType=="8"){
		gd = 485;
	}else if(currentInputType == "0"){
		gd=320;
	}
	$.pdialog.open("<%=basePath %>/pages/resource/form/showDetailPage.action?type="+currentInputType+"&id="+id+"&formId=${formId}&op=${op}","detailsDialog","详细设置",{width:kd,height:gd,mask:true,resizable:true});
}

function refreshRecord(){
	$(".goto").each(function(){
		var $this = $(this);
		$this.click();
	});
	setTimeout(function(){
	    enableTableSelect();
	},500);
	
}

function formPreView(){
	var selfWidth = '${form.width}';
	var selfHeight = '${form.height}';
	var url="<%=basePath %>/pages/resource/compexpreview.action?op=new&formId=${formId}";
	$.pdialog.open(url,"preViewDialog","预览",{width:selfWidth,height:selfHeight,mask:true,resizable:true});
	$("#ziduanId").click();
}

function enableLink(obj,status){
	var inputType;
	if(status=="edit"){
		var obj = $(obj.children().get(8)).find("select").first();
		inputType = obj.val();
	}else{
		inputType = obj.value;
	}
	var tr = $(obj).parent().parent();
	var tds=tr.children();
	
	var id=$(tds.get(0)).find("input").val();
	
	if(inputType=='9'||inputType=='15'){
		$(tds.get(9)).html("<a style='color: gray;'>详细设置</a>");
	}else{
		if(tds.get(1).innerHTML==""){
			$(tds.get(9)).html("<a style='color: gray;'>详细设置</a>");
		}else{
			$(tds.get(9)).html("<a href=\"#\" style=\"color: blue;\" onclick=\"showDetailsPage(this,'"+inputType+"','"+id+"')\">详细设置</a>");	
		}
	}
	//不为以下类型时，不能作为查询条件
	if(inputType!='0' && inputType!='1' && inputType!='6' && inputType!='7' && inputType!='8'){
		$(tds.get(7)).html("<select id='sel3' name='formColumn.isDefaultQuery' style='width:75px;float:center;'><option value='0'>否</option></select><input type='hidden' value='否' />");
	}else{
		var dqvalue;
		$.ajax({
			type:'post',
			url: "/pages/resource/form/getFormColumnObj.action?formColumnId="+id,
			async:false,
			dataType: 'json',
			success: function(data){
				var formColumn = data;
				dqvalue = formColumn.isDefaultQuery;
			}
		});
		if(dqvalue==0){
			$(tds.get(7)).html("<select id='sel3' name='formColumn.isDefaultQuery' style='width:75px;float:center;'><option value='1'>是</option><option value='0' selected='selected'>否</option></select><input type='hidden' value='否' />");
		}else if(dqvalue==1){
			$(tds.get(7)).html("<select id='sel3' name='formColumn.isDefaultQuery' style='width:75px;float:center;'><option value='1' selected='selected'>是</option><option value='0'>否</option></select><input type='hidden' value='是' />");
		}
	}
}

function closeDetailWin(){
	$.pdialog.close("detailsDialog");
}
//-->
</script>
<c:choose>
	<c:when test="${op=='view'}">
		<p align="right" style="padding-top: 5px;padding-bottom: 5px;">
		<input type="button" name="preview" class="listbuttonDisable" disabled="disabled" value="预览" onclick="formPreView();">
		<c:if test="${pageResult.totalCount==0}">
		<input type="button" name="auto" class="listbuttonDisable" disabled="disabled" value="自动生成" onclick="autoAddRecord();">
		</c:if>
		<c:if test="${pageResult.totalCount>0}">
		<input type="button" name="auto" class="listbuttonDisable" disabled="disabled" value="自动生成" onclick="autoAddRecord();" disabled="disabled">
		</c:if>
		<input type="button" name="fresh" class="listbuttonDisable" disabled="disabled" value="刷新" onclick="refreshRecord();">
		<input type="button" name="add" class="listbuttonDisable" disabled="disabled" value="增加" onclick="addRecord();">
		<input type="button" name="delete" class="listbuttonDisable" disabled="disabled" value="批量删除" onclick="deleteFormColumn();">
		</p>
	</c:when>
	<c:otherwise>
		<p align="right" style="padding-top: 5px;padding-bottom: 5px;">
		<input type="button" name="preview" class="listbutton" value="预览" onclick="formPreView();">
		<c:if test="${pageResult.totalCount==0}">
		<input type="button" name="auto" class="listbutton" value="自动生成" onclick="autoAddRecord();">
		</c:if>
		<c:if test="${pageResult.totalCount>0}">
		<input type="button" name="auto" class="listbutton" value="自动生成" onclick="autoAddRecord();" disabled="disabled">
		</c:if>
		<input type="button" name="fresh" class="listbutton" value="刷新" onclick="refreshRecord();">
		<input type="button" name="add" class="listbutton" value="增加" onclick="addRecord();">
		<input type="button" name="delete" class="listbutton" value="批量删除" onclick="deleteFormColumn();">
		</p>
	</c:otherwise>
</c:choose>
<form id="pagerForm" method="post" action="<%=basePath %>/pages/resource/form/listColumn.action?formId=${formId}&tabId=${tabId}&partitionId=${partitionId}">
	<input type=hidden name="model" value="${model}"/>
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${pageResult.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="tableId" value="${tableId}">
</form>
<span>
	<select id="sel1" name="formColumn.inputType" style="width:80px;float:center;display: none" onchange="enableLink(this)">
		<c:forEach items="${inputTypeList}" var="inputType">
			<option value="${inputType.value}" <c:if test="${formColumn.inputType==inputType.value }">selected="selected"</c:if>>${inputType.name}</option>
		</c:forEach>
	</select>
	<select id="sel2" name="formColumn.isShowInList" style="width:75px;float:center;display: none">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<select id="sel3" name="formColumn.isDefaultQuery" style="width:75px;float:center;display: none">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<select id="sel4" name="formColumn.isEdit" style="width:75px;float:center;display: none">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<select id="sel5" name="formColumn.isView" style="width:75px;float:center;display: none">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<select id="sel6" name="formColumn.required" style="width:75px;float:center;display: none">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<select id="columnId" name="formColumn.columnId" style="width:75px;float:center;display: none">
		<c:forEach items="${columns}" var="column">
			<option value="${column.id}" <c:if test="${column.id==formColumn.columnId }">selected="selected"</c:if>>${column.columnZhName}</option>
		</c:forEach>
	</select>
</span>
<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<input type="hidden" name="numPerPage" value="${pageResult.pageSize}">
		<span>${pageResult.pageSize}条，共${pageResult.totalCount}条</span>
	</div>
	<div class="pagination" targetType="dialog" rel="formColumnListId"   totalCount="${pageResult.totalCount}" numPerPage="${pageResult.pageSize}" pageNumShown="${pageResult.pageCountShow}" currentPage="${pageResult.currentPage}"></div>
</div>
<div id="formcolumn_list_divid">
   	<form id="formColumnListForm" name="formColumnListForm">
    <table id="simpleformColumnEditTable" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style=" border-left:1px #ededed solid;">
		<tbody>
		<thead>
			<tr>
				<th class="thClass" width="3%"><input name="all" type="checkbox"  style="margin-top:4px;*margin-top:0px;" onclick="selectAll(this,'selectedSubIDs')"/></th>
				<th class="thClass" width="3%">序号</th>
				<th class="thClass">字段名</th>
				<th class="thClass">列表显示</th>
				<th class="thClass">列表顺序</th>
				<th class="thClass">列宽度</th>
				<th class="thClass">录入类型</th>
				<th class="thClass">详细设置</th>
				<c:if test="${op!='view'}">
					<th class="thClass" width="7%">操作</th>
				</c:if>
			</tr>
		</thead>
		<c:forEach items="${pageResult.content}" var="column" varStatus="status">
	        <c:if test="${status.count%2==0}">
               <tr target="id_column" rel="1" class='event'>
               </c:if>
               <c:if test="${status.count%2!=0}">
               <tr target="id_column" rel="1">
               </c:if>
	             <td class="tdClass" width="3%"><input type="checkbox" style="margin-top:4px;*margin-top:0px;"  name="selectedSubIDs" value="${column.id}"></td>
	             <td class="tdClass" width="3%"><div>${(pageResult.currentPage-1) * pageResult.pageSize + status.count}</div></td>
	             <td class="tdClass" style="text-align: center;" onmousedown="editTable(this)">${column.columnZhName}</td>
	             <td class="tdClass" onmousedown="editTable(this)">
	             	<c:if test="${column.isShowInList==1 }">是</c:if>
	             	<c:if test="${column.isShowInList==0 }">否</c:if>
	             </td>
	             <td class="tdClass" onmousedown="editTable(this)">${column.listOrder}</td>
	             <td class="tdClass" onmousedown="editTable(this)">${column.colWidth}</td>
				 <td class="tdClass" onmousedown="editTable(this)">
					<c:forEach items="${inputTypeList}" var="inputType">
						<c:if test="${column.inputType==inputType.value }">${inputType.name}</c:if>
					</c:forEach>
				 </td>
				 <td class="tdClass">
				 	<c:choose>
				 		<c:when test="${column.inputType==9||column.inputType==15}">
				 			<a style="color: gray;">详细设置</a>
				 		</c:when>
				 		<c:otherwise>
				 			<a href="#" style="color: blue;" onclick="showDetailsPage(this,'${column.inputType}','${column.id}')">详细设置</a>
				 		</c:otherwise>
				 	</c:choose>
				 </td>
	        </tr>
	    </c:forEach>
		</tbody>	
	</table>
	</form>
</div>
<br/>