<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--		
	$(function(){
		compexViewJson('<%=basePath %>/pages/resource/compexviewJson.action?params=${params}&formId=${formId}');
		$("#BC").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl="<%=basePath %>/pages/system/dbDriver/edit.action?formId=${formId}&params=${params}" + "&op=edit";
	
		$('#tabDivId').children().prepend($('#enabledDb').html());
		$('#enabledDb').text('');
		$('#resetConnDb').hide();
		$('#tabDivId').children().append('<div style="float:right;margin-right:12px;margin-top:5px;height:20px;"><input class="listbutton" type="button" onclick="connDbEnable();" value="启用" name="connDbEnable" id="connDbEnable" disabled="disabled"/></div>');
		ns.common.mouseForButton();
	});
	
	function connDbTest(){
		var params = '';
		$.each($('#enabledDbTable input:not(:button)'),function (entryIndex,entry){
			params +='ctp.'+$(entry).attr('name')+'='+$(entry).val()+'&';
		});
		$.ajax({
			type:'post',
			dataType:'json',
			url:'<%=basePath %>/pages/system/dbDriver/connDbTest.action?'+params+'ctp.connDsType='+$('#connDsType').val(),
			success: function(data){
				if(data.status == 'success'){
					alertMsg.info(data.message);
					$('#resetConnDb').show();
					$.each($('#enabledDbTable input:not(:button)'),function (entryIndex,entry){
						$(entry).attr('disabled', 'disabled');
					});
					$('#connDsType').attr('disabled', 'disabled');
					$('#connDbEnable').removeAttr('disabled');
				}
				if(data.status == 'error'){
					alertMsg.warn(data.message);
				}
			}
		})
	}
	
	function resetConnDb(){
		$.each($('#enabledDbTable input:not(:button)'),function (entryIndex,entry){
			$(entry).removeAttr('disabled');
		});
		$('#connDsType').removeAttr('disabled');
		$('#connDbEnable').attr('disabled', 'disabled');
	}
	
	function connDbEnable(){
		$.ajax({
			url: '<%=basePath %>/pages/system/dbDriver/change.action?id='+$("#domainId").val(),
			dataType: 'json',
			success: function(data){
				if(data.status == 'info'){
					alertMsg.info(data.message);
				}else if(data.status == 'warn'){
					alertMsg.warn(data.message);
				}else if(data.status == 'error'){
					alertMsg.error(data.message);
				}
			}
		});
	}
//-->
</script>
<div>
	<div style="display: none;">
		<input id="domainSubmit" type="submit">
		<input id="formId" name="formId" value="${formId}" />
		<input id="domainId" name="domainId" value="${domainId}">
		<input id="paramsId" name="params" value="${params}">
		<input id="mainTable" name="mainTable" value="${mainTable}">
	</div>
	<c:forEach items="${form.formButtons}" var="formButton"
		varStatus="status">
		<c:if test="${formButton.buttonType == '1'}">
			<c:forEach items="${formButton.buttonGroup.buttonAndGroups}"
				var="buttonAndGroup" varStatus="stat">
				<button type="button" id="${buttonAndGroup.button.buttonBM}"
					name="b${status.count*stat.count}" class="listbutton"
					onclick="eventCompex${buttonAndGroup.button.buttonBM}();">
					${buttonAndGroup.button.buttonName}
				</button>
			</c:forEach>
		</c:if>
		<c:if test="${formButton.buttonType == '0'}">
			<button type="button" name="b${status.count}" class="listbutton"
				onclick="eventCompex${formButton.button.buttonBM}();">
				${formButton.button.buttonName}
			</button>
		</c:if>
	</c:forEach>
	<input id="tabId" type="hidden" value="" />
	<input id="modelId" type="hidden" value="${id}" />
</div>
<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul id="tabLiId">
			</ul>
		</div>
	</div>
	<div class="tabsContent" id="tabDivId">
	</div>
	<!-- Tab结束层 -->
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>
<div style="display: none" id="enabledDb">
	<table width="100%" cellspacing="0" cellpadding="2" border="0"
		class="Input_Table" id="enabledDbTable">
		<tr>
			<td width="15%" height="50" align="left" class="Input_Table_Label">
				IP地址：
			</td>
			<td width="35%" align="left">
				<input type="text" name="connDsIp" value="192.168.1.120"
					class="required textInput valid">
				<!-- <span class="star">*</span> -->
			</td>
			<td width="15%" height="50" align="left" class="Input_Table_Label">
				端口：
			</td>
			<td width="35%" align="left">
				<input type="text" name="connPort" value="2003"
					class="required textInput valid">
				<!-- <span class="star">*</span> -->
			</td>
		</tr>
		<tr>
			<td width="15%" height="50" align="left" class="Input_Table_Label">
				数据库类型：
			</td>
			<td width="85%" align="left" colspan="3">
				<select name="connDsType" id="connDsType" style="width: 135px;">
					<option value="">
						---请选择---
					</option>
					<option value="mysql">
						mysql
					</option>
					<option value="oracle">
						oracle
					</option>
					<option value="shentong">
						shentong
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="15%" height="50" align="left" class="Input_Table_Label">
				数据库/实例名：
			</td>
			<td width="85%" align="left" colspan="3">
				<input type="text" name="connDsSid" value=""
					class="required textInput valid">
				<!-- <span class="star">*</span> -->
			</td>
		</tr>
		<tr>
			<td width="15%" height="50" align="left" class="Input_Table_Label">
				用户名：
			</td>
			<td width="35%" align="left">
				<input type="text" name="connDsUser" value=""
					class="required textInput valid">
				<!-- <span class="star">*</span> -->
			</td>
			<td width="15%" height="50" align="left" class="Input_Table_Label">
				密码：
			</td>
			<td width="35%" align="left">
				<input type="text" name="connDsPwd" value=""
					class="required textInput valid">
				<!-- <span class="star">*</span> -->
			</td>
		</tr>
		<tr id="enabledDbTableLastTr">
			<td colspan="4" align="right" class="Input_Table_Label">
				<input class="listbutton" type="button" onclick="resetConnDb();"
					value="重新连接" name="resetConnDb" id="resetConnDb">
				<input class="listbutton" type="button" onclick="connDbTest();"
					value="连接测试" name="connDb" id="connDb">
			</td>
		</tr>
	</table>
</div>
