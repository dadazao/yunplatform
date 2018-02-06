<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	function subTable() {
		var urlString = "<%=basePath %>/pages/resource/column/subTable.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tableId option:selected").val(),
			success: function(data){
				$('#pinyinId').val(data+"_");
			}
		});
	}
	function showColumnInput() {
		if($("#columnNameSelect option:selected").val() == '-1'){
			$("#pinyinId").append("<input size='15' class='textInput' maxlength='50' id='columnNameId' name='columnName' /><font id='columnNameRequired' color='red'>*</font>");
			$("#columnRequired").remove();
		}else{
			$("#columnNameId").remove();
			$("#columnNameRequired").remove();
			if($("#columnRequired").html() != "*") {
				$("#pinyinId").append("<font id='columnRequired' color='red'>*</font>")	
			}
		}
			
	}
	
	function checkDBData(value){
		if(value==0 || value=='0'){
			$("#defaultValue").addClass("required");
		}else if(value==1 || value=='1'){
			$("#defaultValue").removeClass("required");
		}
	}
	
	var op="";
	$(function(){
		var name = $("#columnNameId").val();
		if(name != "") {
			$("#columnNameId").show();
			$("#columnNameSelect").hide();
			$("#columnNameSelect").removeClass("required");
			$("#columnNameId").addClass("required");
		}

		var edit = "${op}";
		if(edit == 'edit') {
			$("#tableSelectId").html("");
			$("#tableSelectId").hide();
			$("#prefix").html("");
			$("#BCBXZ").hide();
			$("#pinyinId").attr("maxlength",28);
		}else{
			$("#tableInputId").html("");
			$("#tableInputId").hide();
			$("#pinyinId").attr("style","width:157px;");
		}
		
		checkDBData('${column.isNullable}');
		ns.common.mouseForButton();
	});
	
	function addTableColumn() {
		$("#columnId").val("");
		if($("#tableId").val()==""||$("#tableId").val()==undefined){
			alertMsg.warn('请先保存数据表！');
			return;
		}
		
		$("#tableColumnFormId").attr("action","<%=basePath %>/pages/resource/column/save.action?column.tableId="+$("#tableId").val());
		$.ajaxSetup({async: false});
		$("#tableColumnFormId").submit();
		$.ajaxSetup({async: false});
		loadTableColumnList();
	}
	
	function updateTableColumn() {
		if($("#tableId").val()==""||$("#tableId").val()==undefined){
			alertMsg.warn('请先保存数据表！');
			return;
		}
		$("#tableColumnFormId").attr("action","<%=basePath %>/pages/resource/column/save.action?column.tableId="+$("#tableId").val());
		$.ajaxSetup({async: false});
		$("#tableColumnFormId").submit();
		$.ajaxSetup({async: true});
		loadTableColumnList();
	}
	
	function deleteTableColumn() {
		var params = $("#tableColumnListForm").serialize();
		if(params==undefined || params==''){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		var urlString = "<%=basePath %>/pages/resource/column/deleteColumn.action";
		var result = urlString + "?" + params;
			alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result,loadTableColumnList);}
		});
	}
//-->
</script>
<form id="tableColumnFormId" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div align="center">
		<input id="columnId" type=hidden name="column.id" value="${column.id}"/>
  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					字段中文名
				</td>
				<td align="left" width="40%">
					<input id="columnZhName" maxlength="50" type="text" name="column.columnZhName" value="${column.columnZhName}"  class="textInput required" style="width:180px;"/><!-- <span class="star">*</span> -->
				</td> 
				<td align="left" class="Input_Table_Label" width="10%">
					字段拼音名
				</td>
				<td align="left" >
					<span id="prefix" style="float: left;height:25px;line-height:25px;">tbl_</span>
					<input id="pinyinId" maxlength="24" type="text" name="column.columnName" value="${column.columnName}"  class="textInput required pinyin" style="width:180px;float:left;"/><!-- <span class="star">*</span> -->
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					字段英文名
				</td>
				<td align="left" width="40%">
					<input id="columnEnName" maxlength="50" type="text" name="column.columnEnName" value="${column.columnEnName}"  class="textInput required" style="width:180px;"/><!-- <span class="star">*</span> -->
				</td>  
				<td align="left" class="Input_Table_Label">
					数据类型
				</td>
				<td align="left">
					<select name="column.dataType" style="width:186px">
						<c:forEach items="${dataTypeList}" var="dataType">
							<option value="${dataType.value}" <c:if test="${column.dataType==dataType.value }">selected="selected"</c:if>>${dataType.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					是否空值
				</td>
				<td align="left" width="40%">
					<input type="radio" name="column.isNullable" onclick="checkDBData(1)" value="1" <c:if test="${column.isNullable==1 }">checked="checked"</c:if> />是
					<input type="radio" name="column.isNullable" onclick="checkDBData(0)" value="0" <c:if test="${column.isNullable==0 }">checked="checked"</c:if> />否
				</td>
				<td align="left" class="Input_Table_Label" width="10%"> 
					数据长度
				</td>
				<td align="left">
					<input type="text" maxlength='5' name="column.length" value="${column.length}"  style="width:180px" class="number"/>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label">
					数据库默认值
				</td>
				<td align="left" colspan="3">
					<input id="defaultValue" type="text" maxlength='50' name="column.defaultValue" value="${column.defaultValue}"  style="width:180px"/>
				</td>
			</tr>
			<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					功能说明
				</td>
				<td colspan="3" align="left">
					<textarea name="column.comment" rows="4" cols="103" class="textInput">${column.comment}</textarea>
				</td>
			</tr>
		</table>
		<div align="right" style="padding-top: 5px;padding-bottom: 5px;">
			<input type="button" name="add" class="listbutton" value="添加" onclick="addTableColumn();"/>
			<input type="button" name="save" class="listbutton" value="保存" onclick="updateTableColumn();"/>
			<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteTableColumn();"/>
		</div>
	</div>
</form>
