<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	function selectDataType() {
		var type = $("#selectDataTypeId  option:selected").val();
		if(type==0) {
			$("#codeId").show();
			$("#codeLabel").show();
			$("#relationId").hide();
			$("#relationTableDivId").html("");
			$("#relationColumnDivId").html("");
			$("#default_tr").show();
			$("#canselect_tr").show();
		}else if(type ==1) {
			$("#codeId").hide();
			$("#codeLabel").hide();
			$("#relationId").show();
			$.ajaxSetup({async: false});
			showRelationTable();
			showRelationColumn();
			$.ajaxSetup({async: true});
			$("#default_tr").hide();
			$("#canselect_tr").hide();
		}
	}
	
	function showComBox() {
		var urlString = "<%=basePath %>/pages/resource/form/showComBox.action?comBoxId=" + $("#comBoxValueId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#comBoxId").html(data);
			}
		});
	}

	function showTextField() {
		$("#textFieldLabel").show();
		var urlString = "<%=basePath %>/pages/resource/form/showTextField.action?textFieldId=" + $("#textFieldValueId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#textFieldId").html(data);
			}
		});
	}
	
	function showDate() {
		var urlString = "<%=basePath %>/pages/resource/form/showDate.action?dateId=" + $("#dateId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#dateDivId").html(data);
			}
		});
	}
	
	function showRadio() {
		var urlString = "<%=basePath %>/pages/resource/form/showRadio.action?radioId=" + $("#radioId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#radioDivId").html(data);
			}
		});
	}
	
	function showCheckBox() {
		var urlString = "<%=basePath %>/pages/resource/form/showCheckBox.action?checkBoxId=" + $("#checkBoxId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#checkBoxDivId").html(data);
			}
		});
	}

	function showSearchComBox() {
		var urlString = "<%=basePath %>/pages/resource/form/showSearchComBox.action?searchComBoxId=" + $("#searchComBoxId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#searchComBoxDivId").html(data);
			}
		});
	}
	
	function showTree(){
		var urlString = "<%=basePath %>/pages/resource/form/showTree.action?treeId=" + $("#treeId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#treeDivId").html(data);
			}
		});
	}
	
	function showEditor() {
		var urlString = "<%=basePath %>/pages/resource/form/showEditor.action?editorId=" + $("#editorId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#editorDivId").html(data);
			}
		});
	}
	
	function showRelationTable() {
		var urlString = "<%=basePath %>/pages/resource/form/showRelationTable.action?table=" + $("#relationTableId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#relationTableDivId").html(data);
			}
		});
	}
	
	function showRelationColumn() {
		var urlString = "<%=basePath %>/pages/resource/form/showRelationColumn.action?table=" + $("#relationTableSelectId option:selected").val() + "&column=" + $("#relationColumnId").val();
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#relationColumnDivId").html(data);
			}
		});
	}
	
	function showCodeDialog() {
		$.pdialog.open("<%=basePath %>/pages/resource/tree/fetchShowTreeParam.action?belongTable=sys_dictionarys&rootId=1&nameColumn=tbl_name&parentColumn=tbl_parentId&orderColumn=tbl_dicOrder&expand=false&level=1&showRoot=true&parentName=parentName&parentId=parentId", "selectDialog", "选择代码", {width:300,height:600,mask:true,resizable:true});
	}
	
	function showListOrder(value) {
		if(value==0) {//不显示列表顺序时
			$("#listOrderId").attr("disabled","disabled");
			$("#listOrderId").attr("class","listbuttonDisable");
		}else{
			$("#listOrderId").removeAttr("disabled");
			$("#listOrderId").attr("class","listbutton");
		}
	}
	
	$(function(){
		//加载完tab页时加载对应的分区，再加载分区对应的页面
		showFormColumnTab();
		//showHideContent();
		//selectDataType();
		//var oCbo = $("#tableId").combobox({size:19});
		showListOrder($("#isShowInListId").val());
	});

	function showFormColumnTab(){
		var urlString = "<%=basePath %>/pages/resource/form/showFormColumnTab.action?formId="+$("#formId").val()+"&formColumnTabId="+$("#formColumnTabId").val();
			$.ajax({
				async: false,
				type:'post',
				url: urlString,
				success: function(data){
					$("#tabSelectId").html(data);
				}
			});
		showFormColumnPartition($("#formcolumntabid option:selected").val());
	}
	
	function showFormColumnPartition(tabId){
		if(tabId==undefined){
			tabId='-1';
		}
		var urlString = "<%=basePath %>/pages/resource/form/showFormColumnPartition.action?formColumnTabId="+tabId+"&formColumnPartitionId="+$("#formColumnPartitionId").val();
		$.ajax({
			async: false,
			type:'post',
			url: urlString,
			success: function(data){
				$("#partitionSelectId").html(data);
				enablePartitionSel();
			}
		});
		
		//判断选项卡的类型
		$.ajax({
			async: false,
			type:'post',
			url: "<%=basePath %>/pages/resource/form/checkTabType.action?tabId="+tabId,
			success: function(data){
				var tabType = data;
				if(tabType == "1"){
					$.ajax({
						async: false,
						type:'post',
						url: "<%=basePath %>/pages/resource/form/showAllTable.action?formId="+$("#formId").val()+"&tabId="+tabId,
						success: function(data){
							$("#reltblDiv").html(data);
						}
					});
				}else if(tabType == "0"){
					$.ajax({
						async: false,
						type:'post',
						url: "<%=basePath %>/pages/resource/form/showRelTable.action?formId="+$("#formId").val()+"&tabId="+tabId,
						success: function(data){
							$("#reltblDiv").html(data);
						}
					});
				}
			}
		});
		showFormColumnList();
	}
	
	function showFormColumnList(){
		var tabid=$("#formcolumntabid option:selected").val();
		var partitionid=$("#formcolumnpartitionid option:selected").val();
		var tableid=$("#tableId option:selected").val();
		//根据所属表加载关联字段
		var urlString = "<%=basePath %>/pages/resource/form/showRelColumn.action?tableId="+tableid;
		$.ajax({
			async: false,
			type:'post',
			url: urlString,
			success: function(data){
				$("#relcolDiv").html(data);
			}
		});
		loadFormColumnList(tabid,partitionid,tableid,'${op}');
		enableTableSelect();
	}
	
	function enablePartitionSel(){
		if($("#formcolumnpartitionid").val()==undefined){
			$("#formcolumnpartitionid").attr("disabled","disabled");
		}else{
			$("#formcolumnpartitionid").removeAttr("disabled");
		}
	}
//-->
</script>
<form onkeydown="return enterNotSubmit(event);" id="formColumnFormId" name="formColumn" class="pageForm required-validate">
		<input id="fcId" type="hidden" name="formColumn.id" value="${formColumn.id}" />
		<div>
			<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
				<tr>
	 	 			<td width="15%" height="30" align="left" class="Input_Table_Label">
						所属选项卡
					</td>
					<td height="30" width="35%" align="left">
						<div id="tabSelectId">
							
						</div>
						<input type="hidden" id="formColumnTabId" name="formColumnTabId" value="${formColumn.tabId}"/>
					</td>
					<td width="15%" height="30" align="left" class="Input_Table_Label">
						所属分区
					</td>
					<td height="30" width="35%" align="left">
						<div id="partitionSelectId">
							
						</div>
						<input type="hidden" id="formColumnPartitionId" name="formColumnPartitionId" value="${formColumn.partitionId}"/>
					</td>
	 	 		</tr>
	 	 		<tr>
	 	 			<td width="15%" height="30" align="left" class="Input_Table_Label">
						所属表
					</td>
					<td height="30" width="35%" align="left">
						<div id="reltblDiv">
							<select id="tableId" name="formColumn.tableId" style="width:175px" onchange="showFormColumnList();">
								<c:forEach items="${tables}" var="table">
									<option value="${table.id}" <c:if test="${table.id==formColumn.tableId }">selected="selected"</c:if>>${table.tableZhName}</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td width="15%" height="30" align="left" class="Input_Table_Label">
						关联字段
					</td>
					<td height="30" width="35%" align="left" colspan="3">
						<div id="relcolDiv">
							
						</div>
					</td>
	 	 		</tr>
			</table>
		</div>
<%--		<div id="formColumnContentDiv">--%>
<%--			--%>
<%--		</div>--%>
<%--	<div align="right">--%>
<%--		<input type="button" name="add" class="listbutton" value="添加" onclick="addFormColumn();">--%>
<%--		<input type="button" name="save" class="listbutton" value="保存" onclick="updateFormColumn();">--%>
<%--		<input type="button" name="delete" class="listbutton" value="删除" onclick="deleteFormColumn();">--%>
<%--	</div>--%>
</form>
