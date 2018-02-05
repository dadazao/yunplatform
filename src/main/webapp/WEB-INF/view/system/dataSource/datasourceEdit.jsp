<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript" charset="utf-8">
<!--
	//保存实体
	ns.dataSource.saveDataSource = function(){
		$("#dataSourcePojoForm").submit();
	}
	
	ns.dataSource.showDbInfo = function(obj){
		if(obj.value=='mysql'){
			$("#dataSourcePojo_dsDriver").val("com.mysql.jdbc.Driver");
			$("#dataSourcePojo_dsUrl").val("jdbc:mysql://<hostname>[<:3306>]/<dbname>");
		}else if(obj.value=='Db2'){
			$("#dataSourcePojo_dsDriver").val("COM.ibm.db2.jdbc.app.DB2Driver");
			$("#dataSourcePojo_dsUrl").val("jdbc:db2:<dbname>");
		}else if(obj.value=='oracle'){
			$("#dataSourcePojo_dsDriver").val("oracle.jdbc.driver.OracleDriver");
			$("#dataSourcePojo_dsUrl").val("jdbc:oracle:thin:@<server>[:<1521>]:<database_name>");
		}else if(obj.value=='shentong'){
			$("#dataSourcePojo_dsDriver").val("com.oscar.Driver");
			$("#dataSourcePojo_dsUrl").val("jdbc:oscar://<server>[:2003]/<database_name>");
		}
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
	
	$(function(){
		if($("#dataSourcePojo_dsDriver").val()==""&&$("#dataSourcePojo_dsUrl").val()==""){
			ns.dataSource.showDbInfo(document.getElementById("dataSourcePojo_dsType"));
		}
		
	});
	
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editDataSource" name="editDataSource" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveDataSource" name="saveDataSource" class="listbutton" onclick="ns.dataSource.saveDataSource();">保存</button>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected" onclick="ns.common.changeSaveAction('saveDataSource','ns.dataSourcePojo.saveDataSource')"><a><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="dataSourcePojoForm" method="post" action="<%=basePath%>/pages/system/dataSource/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="dataSourcePojoId" type=hidden name="dataSourcePojo.id" value="${dataSourcePojo.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${dataSourcePojo.id}" />
					<input id="dataSourcePojoOptCounter" type=hidden name="dataSourcePojo.optCounter" value="${dataSourcePojo.optCounter}"/>
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								数据源名称
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="dataSourcePojo.dsName" value="${dataSourcePojo.dsName}" maxlength="50" class="textInput required" style="width:180px;"/>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								数据库类型
							</td>
							<td align="left" width="40%">
								<select id="dataSourcePojo_dsType" name="dataSourcePojo.dsType" onchange="ns.dataSource.showDbInfo(this)">
									<c:forEach items="${dbTypes}" var="code">
										<option value="${code.value}" <c:if test="${dataSourcePojo.dsType==code.value}">selected="selected"</c:if>>${code.name}</option>
									</c:forEach>
								</select>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								数据库驱动
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" id="dataSourcePojo_dsDriver" name="dataSourcePojo.dsDriver" value="${dataSourcePojo.dsDriver}" maxlength="100" class="textInput required" style="width:450px;"/>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								URL
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" id="dataSourcePojo_dsUrl" name="dataSourcePojo.dsUrl" value="${dataSourcePojo.dsUrl}" maxlength="200" class="textInput required" style="width:450px;"/>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								用户名
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="dataSourcePojo.dsUser" value="${dataSourcePojo.dsUser}"  class="textInput required" style="width:180px;"/>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								密码
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="dataSourcePojo.dsPasswd" value="${dataSourcePojo.dsPasswd}"  class="textInput required" style="width:180px;"/>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								连接编码
							</td>
							<td align="left" width="40%">
			 		 			<select name="dataSourcePojo.dsEncoding">
									<c:forEach items="${dbEncodings}" var="code">
										<option value="${code.value}" <c:if test="${dataSourcePojo.dsEncoding==code.value}">selected="selected"</c:if>>${code.name}</option>
									</c:forEach>
								</select>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								功能说明
							</td>
							<td align="left" width="40%">
			 		 			<textarea rows="5" cols="100" name="dataSourcePojo.comment">${dataSourcePojo.comment}</textarea>
							</td>
			  	 		</tr>
			  	 		<tr>
			  	 			<td align="left" class="Input_Table_Label" width="10%">
								备注
							</td>
							<td align="left" width="40%">
			 		 			<textarea rows="5" cols="100" name="dataSourcePojo.remark">${dataSourcePojo.remark}</textarea>
							</td>
			  	 		</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>