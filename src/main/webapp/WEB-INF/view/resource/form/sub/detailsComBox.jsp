<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
$(function(){
	selectDataType();
	enableNullName(${formColumn.hasNull});
	initSelAndDefault();
	
	String.prototype.trimEnd = function(trimString) { 
	var re = new RegExp(trimString+"*$", "g"); 
	return this.replace(re, ""); 
	}; 
	Array.prototype.indexOf = function(itemValue) { 
	var nIndex = -1; 
	for (var i=0; i<this.length; i++) 
	{ 
	if (this[i] == itemValue) 
	nIndex = i; 
	} 
	return nIndex; 
	}; 
	var oSele = document.getElementById("canSelectBox"); 
	function getSelectedIndexes(oSele) 
	{ 
	var sSelectedIndexes = ""; 
	var separator = ","; 
	for (var i=0; i<oSele.options.length; i++) 
	{ 
	if (oSele.options[i].selected) 
	sSelectedIndexes += i.toString() + separator; 
	} 
	if (sSelectedIndexes == "") 
	return new Array(0); 
	else 
	return sSelectedIndexes.trimEnd(separator).split(separator); 
	} 
	oSele.onclick = function() { 
	this.selectedIndexes = getSelectedIndexes(this); 
	
	if (this.selectedIndexes.length == 1) 
	{ 
	var nSelectedIndex = parseInt(this.selectedIndexes[0]); 
	if (!isNaN(nSelectedIndex)) 
	this.options[nSelectedIndex].selected = true; 
	} 
	}; 
	oSele.onchange = function() { 
	var j = this.selectedIndexes.indexOf(this.selectedIndex.toString()); 
	if (j > -1) 
	{ 
	this.options[this.selectedIndex].selected = false; 
	this.selectedIndexes.splice(j, 1); 
	} 
	if (this.selectedIndexes.length > 0) 
	{ 
	var nSelectedIndex; 
	for (var i=0; i<this.selectedIndexes.length; i++) 
	{ 
	nSelectedIndex = parseInt(this.selectedIndexes[i]); 
	this.options[nSelectedIndex].selected = true; 
	} 
	} 
	};
});

function initSelAndDefault(){
	if('${formColumn.codeParentId}'!='0'&&'${formColumn.codeParentId}'!=""){
		var urlString = "<%=basePath %>/pages/resource/formshowCanSelectBox.action?parentNodeID=${formColumn.codeParentId}";
		$.ajax({
			type:'post',
			url: urlString,
			async:false,
			success: function(data){
				$("#canSelectDiv").html(data);
			}
		});
		var urlString = "<%=basePath %>/pages/resource/formshowDefaultSelectItem.action?parentNodeID=${formColumn.codeParentId}&defaultSelectItem=${formColumn.defaultSelectItem}";
		$.ajax({
			type:'post',
			url: urlString,
			async:false,
			success: function(data){
				$("#defaultSelectDiv").html(data);
				enableDefaultItem(${formColumn.hasDefaultItem});
			}
		});
	}
}

function enableNullName(val){
	if(val==0){
		$("#nullName").attr("disabled","disabled");
	}else if(val==1){
		$("#nullName").removeAttr("disabled");
	}
}

function enableDefaultItem(val){
	if(val==0){
		$("#defaultSelectBox").attr("disabled","disabled");
	}else if(val==1){
		$("#defaultSelectBox").removeAttr("disabled");
	}
}

function setTree(id) {
	if($("#canSelectBox").length>0){
		var urlString = "<%=basePath %>/pages/resource/formshowCanSelectBox.action?parentNodeID="+id;
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#canSelectDiv").html(data);
			}
		});
	}
	if($("#defaultSelectBox").length>0){
		var urlString = "<%=basePath %>/pages/resource/formshowDefaultSelectItem.action?parentNodeID="+id;
		$.ajax({
			type:'post',
			url: urlString,
			success: function(data){
				$("#defaultSelectDiv").html(data);
			}
		});
	}
	
	$.pdialog.close("selectDialog");
}
//-->
</script>
<div id="compId">
<form id="tempForm" method="post" action="<%=basePath %>/pages/resource/formsaveColumnDetails.action" class="pageForm required-validate" onsubmit="return validateCallback(this, detailDialogAjaxDone);">
<input type="hidden" name="formId" value="${formId}" />
<input type="hidden" name="formColumn.id" value="${formColumn.id }"/>
<input type="hidden" name="currentInputType" value="${currentInputType}">
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
	<tr>
		<td class="Input_Table_Label" align="center" colspan="4">
			<label style="font-weight: bold;font-size: 12px;">
				下拉框属性
			</label>
		</td>
	</tr>
	<tr>
		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				选择下拉框
			</label>
		</td>
		<td align="left" colspan="3">
			<select name="formColumn.compexId" style="width:175px">
				<c:forEach items="${components }" var="combox">
					<option value="${combox.id}" <c:if test="${formColumn.compexId == combox.id}">selected="selected"</c:if>>${combox.comboxName}</option>				
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr id="selectDataTypeId">
		 <td height="30" class="Input_Table_Label" width="16%" align="left"><label id="selectDataTypeLable" >数据来源</label></td>
	     <td width="34%" align="left">
	     	<select id="selectDataTypeId" name="formColumn.selectDataType" style="width:175px" onchange="selectDataType();">
	     		<option value="0" <c:if test="${formColumn.selectDataType == 0}">selected="selected"</c:if>>代码表</option>
	     		<option value="1" <c:if test="${formColumn.selectDataType == 1}">selected="selected"</c:if>>关系表</option>
	     	</select></td>
	     <td class="Input_Table_Label" width="12%" align="left"><label id="codeLabel">代码</label></td>
	     <td width="38%" align="left" height="30">
	     	<div id="codeId" >
	     		<input style="float:left;" id="parentName" class="textInput" type="text" name="formColumn.codeParentName" value="${formColumn.codeParentName}" readonly="true" onclick="showCodeDialog();"/>
				<input id="parentId" name="formColumn.codeParentId"	value="${formColumn.codeParentId}" type="hidden" />
				<font style="float:left;" color="red">*</font>
				<span style="float:left;">&nbsp;&nbsp;</span>
				<button type="button" style="width:44px;height:20px;" class="listbutton" onclick="showCodeDialog();">选择</button>
			</div>
	     </td>
   	</tr>
   	<tr id="relationId" style="display:none;">
	     <td height="30" class="Input_Table_Label"><label>关系表</label></td>
	     <td>
	     	<div id="relationTableDivId"></div>
	     	<input id="relationTableId" type="hidden" value="${formColumn.relationTable}" />
	     </td>
	     <td class="Input_Table_Label"><label>字段</label></td>
	     <td>
	     	<div id="relationColumnDivId"></div>
	     	<input id="relationColumnId" type="hidden" value="${formColumn.relationColumn}" />
	     </td>
   	</tr>
   	<tr id="canselect_tr">
   		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				可选代码项
			</label>
		</td>
		<td align="left">
			<div id="canSelectDiv">
				<select id="canSelectBox" name="formColumn.canSelectItem" multiple="multiple" style="width: 175px;height: 120px;">
					
				</select>
			</div>
		</td>
		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				显示顺序
			</label>
		</td>
		<td align="left">
			<div id="canSelectDiv">
				<select id="codeShowOrder" name="formColumn.codeShowOrder" style="width: 175px;">
					<option <c:if test="${formColumn.codeShowOrder == 0}">selected="selected"</c:if> value="0">正序</option>
					<option <c:if test="${formColumn.codeShowOrder == 1}">selected="selected"</c:if> value="1">逆序</option>
				</select>
			</div>
		</td>
   	</tr>
   	<tr id="default_tr">
   		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				是否有默认项
			</label>
		</td>
		<td align="left">
			<input type="radio" onclick="enableDefaultItem(1)" name="formColumn.hasDefaultItem" value="1" <c:if test="${formColumn.hasDefaultItem==1 }">checked="checked"</c:if> />是
			<input type="radio" onclick="enableDefaultItem(0)" name="formColumn.hasDefaultItem" value="0" <c:if test="${formColumn.hasDefaultItem==0 }">checked="checked"</c:if> />否
		</td>
		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				默认项
			</label>
		</td>
		<td align="left">
			<div id="defaultSelectDiv">
				<select id="defaultSelectBox" name="formColumn.defaultSelectItem" style="width:175px">
					
				</select>
			</div>
		</td>
   	</tr>
   	<tr>
   		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				是否有空选项
			</label>
		</td>
		<td align="left">
			<input type="radio" onclick="enableNullName(1)" name="formColumn.hasNull" value="1" <c:if test="${formColumn.hasNull==1 }">checked="checked"</c:if> />是
			<input type="radio" onclick="enableNullName(0)" name="formColumn.hasNull" value="0" <c:if test="${formColumn.hasNull==0 }">checked="checked"</c:if> />否
		</td>
		<td class="Input_Table_Label" align="left">
			<label id="requiredLabel">
				空选项
			</label>
		</td>
		<td align="left">
			<input type="text" id="nullName" name="formColumn.nullName" value="${formColumn.nullName}"/>
		</td>
   	</tr>
</table>
<br/>
<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
	<tr>
		<td class="Input_Table_Label" align="center" colspan="4">
			<label style="font-weight: bold;font-size: 12px;">
				公共属性
			</label>
		</td>
	</tr>
	<tr>
		<td class="Input_Table_Label" align="left" width="15%">
			<label>
				高级查询
			</label>
		</td>
		<td width="35%">
			<input type="radio" name="formColumn.isQuery" value="1" <c:if test="${formColumn.isQuery==1 }">checked="checked"</c:if> />是
			<input type="radio" name="formColumn.isQuery" value="0" <c:if test="${formColumn.isQuery==0 }">checked="checked"</c:if> />否
		</td>
		<td class="Input_Table_Label" align="left" width="15%">
			<label>
				支持排序
			</label>
		</td>
		<td width="35%" colspan="3">
			<input type="radio" name="formColumn.supportOrder" value="1" <c:if test="${formColumn.supportOrder==1 }">checked="checked"</c:if> />是
			<input type="radio" name="formColumn.supportOrder" value="0" <c:if test="${formColumn.supportOrder==0 }">checked="checked"</c:if> />否
		</td>
	</tr>
</table>
<table width="100%" border="0">
	<tr>
		<td align="right">
			<c:if test="${op!='view'}">
				<button type="submit" class="listbutton" value="确定">
					确定
				</button>
				&nbsp;
				<button type="button" class="listbutton" value="取消" onclick="closeDetailWin()">
					取消
				</button>
			</c:if>
		</td>
	</tr>
</table>
</form>
</div>