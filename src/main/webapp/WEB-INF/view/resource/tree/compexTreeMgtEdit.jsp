<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		$("#XG").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		$('#yunDialog').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
		initTableType('${mgrTree.tableType}', $("#operation").val());
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		ns.common.mouseForButton();
	});
	${form.jiaoben}
	
	function getColumn() {
		var urlString = "<%=basePath %>/pages/resource/treegetColumnParent.action?selectType=parentIdColumn";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tables").val(),
			success: function(data){
				$("#parentTreeName").html(data);
				genPath();
			}
		});
		
		var urlString = "<%=basePath %>/pages/resource/treegetColumnParent.action?selectType=nameColumn";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tables").val(),
			success: function(data){
				$("#nameColumnId").html(data);
				genPath();
			}
		});
		
		var urlString = "<%=basePath %>/pages/resource/treegetColumnParent.action?selectType=paiXu";
		$.ajax({
			url: urlString,
			async: true,
			type:'post',
			data:"id="+$("#tables").val(),
			success: function(data){
				$("#paiXu").html(data);
				genPath();
			}
		});
	}

	function getColumnChild() {
		var urlString = "<%=basePath %>/pages/resource/treegetColumnParentChild.action?selectType=parentIdColumn";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tablesChild").val(),
			success: function(data){
				$("#parentTreeNameChild").html(data);
			}
		});
		
		var urlString = "<%=basePath %>/pages/resource/treegetColumnParentChild.action?selectType=nameColumn";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tablesChild").val(),
			success: function(data){
				$("#nameColumnIdChild").html(data);
			}
		});
		
		var urlString = "<%=basePath %>/pages/resource/treegetColumnParentChild.action?selectType=paiXu";
		$.ajax({
			url: urlString,
			async: true,
			type:'post',
			data:"id="+$("#tablesChild").val(),
			success: function(data){
				$("#paiXuChild").html(data);
			}
		});
	}
	
	function showDevelopPath(b) {
		if(b==1){
			$("#tpFormDevelopPath").removeAttr("disabled");
			$("#tpFormDevelopPath").attr("class","listbutton");
			$("#tpFormDevelopPath").removeAttr("readonly");
			$("#tpFormDevelopPath").removeClass("readonly");
		}else{
			$("#tpFormDevelopPath").attr("disabled","disabled");
			$("#tpFormDevelopPath").attr("class","listbuttonDisable");
			$("#tpFormDevelopPath").attr("readonly","true");
			$("#tpFormDevelopPath").addClass("readonly");
		}
	}
	function genPath() {
		
		var name=$("#tpFormName  option:selected").val();
		var urlString = "<%=basePath %>/pages/system/"+name+"/edit.action?model="+name;
		$("#biaodanlujing").val(urlString);
	}
	
	function publish() {
		var urlString = "<%=basePath %>/pages/resource/tablepublish.action";
		$.ajax({
			url: urlString,
			type:'post',
			data:"id="+$("#tableId").val(),
			success: function(data){
				alertMsg.correct("操作成功");
			}
		});
	}
	
	function selectTreeRoot(){
		var tableId = $('#tables').val();
		var parentColumnId = $('#parentColumnId').val();
		var disColumnId = $('#disColumnId').val();
		var orderColumnId = $('#orderColumnId').val();
		
	    var url = '<%=basePath %>/pages/resource/treefetchShowTreeParamAction.action' +
		 '?tableId='+tableId+'&columnId='+parentColumnId +
		 '&disColumnId='+disColumnId+'&orderColumnId='+orderColumnId+'&parentName=parentName&parentId=parentId&showRoot=true';
	    
		$.pdialog.open(url,"selectDialog","选择",{width:300,height:600,mask:true,resizable:true});
	}
	
	function treeSelfDialogAjaxDone(json){
		var treeId = json.domainId;
		$("#mgrTreeId").val(treeId);
		//navTab.reload("", {navTabId: json.navTabId});
		//DWZ.ajaxDone(json);
		refreshList(json);
	}
	
	function changeTableType(type){
		var $trs = $('tr[belong="dynamic"]');
		$.each($trs,function (entryIndex,entry){
			$(entry).remove();
		});
		var tableTypeUrl = '';
		if(type=='1'){
			tableTypeUrl = "<%=basePath %>/pages/resource/treeaddboth";
		}else{
			tableTypeUrl = "<%=basePath %>/pages/resource/treeaddsingle";
		}
		$.ajax({
		   url: tableTypeUrl,
		   dataType: "text",
		   success: function(html){
		   		$('#tabDivId tr').eq(0).after(html);
		   		getColumn();
				getColumnChild();
		   }
		});
	}
	
	function initTableType(type, op){
		var $trs = $('tr[belong="dynamic"]');
		$.each($trs,function (entryIndex,entry){
			$(entry).remove();
		});
		if(op!='edit'){
			var tableTypeUrl = '';
			if(type=='1'){
				tableTypeUrl = "<%=basePath %>/pages/resource/treeaddboth";
			}else{
				tableTypeUrl = "<%=basePath %>/pages/resource/treeaddsingle";
			}
			$.ajax({
			   url: tableTypeUrl,
			   dataType: "text",
			   success: function(html){
			   		$('#tabDivId tr').eq(0).after(html);
			   		getColumn();
			   }
			});
		}else{
			var tableTypeUrl = '';
			if(type=='1'){
				tableTypeUrl = "<%=basePath %>/pages/resource/treeeditboth";
			}else{
				tableTypeUrl = "<%=basePath %>/pages/resource/treeeditsingle";
			}
			$.ajax({
			   url: tableTypeUrl,
			   dataType: "text",
			   success: function(html){
			   		$('#tabDivId tr').eq(0).after(html);
			   }
			});
		}
	}
//-->
</script>
<div id="yunDialog">
<form id="domainFormId" action="<%=basePath %>/pages/resource/treesave.action" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, treeSelfDialogAjaxDone);">
<div  class="buttonPanel">
	<div style="display:none;">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="paramsId" name="params" value="${params}" />
		<input id="operation" name="op" value="${op}" />
		<input id="treeSubmit" type="submit" name="submit"/>
		<input id="domainSubmit" type="hidden" value="treeSubmit"/>
	</div>
	<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
		<c:if test="${formButton.hasAuth=='0'}">
			<c:if test="${formButton.buttonType == '0'}">
				<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbutton" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;" onclick="eventCompex${formButton.funcName}();" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
			</c:if>
		</c:if>
		<c:if test="${formButton.hasAuth=='1'}">
			<c:if test="${formButton.buttonType == '0'}">
				<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbuttonDisable" disabled="disabled" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
			</c:if>
		</c:if>
	</c:forEach>
</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div align="center">
				<input id="mgrTreeId" type="hidden" name="mgrTree.id"
					value="${mgrTree.id}" />
				<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
      					<td width="10%" height="30" align="left" class="Input_Table_Label">
							&nbsp;分类
						</td>
						<td height="30" align="left">
							<select style="width:186px;" name="mgrTree.systemTeam">
								<option value="sys" <c:if test="${'sys'==mgrTree.systemTeam }">selected="selected"</c:if>>系统类</option>
								<option value="bus" <c:if test="${'bus'==mgrTree.systemTeam }">selected="selected"</c:if>>业务类</option>
								<option value="tes" <c:if test="${'tes'==mgrTree.systemTeam }">selected="selected"</c:if>>测试类</option>
							</select>
						</td>
						<td width="10%" height="30" align="left" class="Input_Table_Label">
							&nbsp;表类型
						</td>
						<td height="30" align="left">
							<select style="width:186px;" name="mgrTree.tableType" onchange="changeTableType(this.value);">
								<option value="0" <c:if test="${'0'==mgrTree.tableType }">selected="selected"</c:if>>单数据表</option>
								<option value="1" <c:if test="${'1'==mgrTree.tableType }">selected="selected"</c:if>>双数据表</option>
							</select>
						</td>
      				</tr>
      				<tr>
						<td style="width: 10%;" align="left" class="Input_Table_Label">
							<label>
								&nbsp;功能说明
							</label>
						</td>
						<td colspan=3 align="left">
							<textarea id="mgrTreecomment" name="mgrTree.comment"
								class="required" rows="3" cols="104">${mgrTree.comment}</textarea>
							<!-- <span class="star">*</span> -->
						</td>
					</tr>
					<tr>
						<td style="width: 10%;" align="left" class="Input_Table_Label">
							<label>
								&nbsp;备注
							</label>
						</td>
						<td colspan=3 align="left">
							<textarea id="mgrTreeremarks" name="mgrTree.remarks"
								rows="3" cols="104">${mgrTree.remarks}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</form>
</div>