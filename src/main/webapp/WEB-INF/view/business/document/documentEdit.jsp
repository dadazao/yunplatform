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
	ns.document.saveDocument = function(){
		$("#documentForm").submit();
	}

	ns.document.startDocumentFlow = function(){
		var domainId = $("#domainId").val();
		if(domainId==""){
			alertMsg.warn("请先保存表单!");
			return;
		}
		alertMsg.confirm("确定要启动流程吗?", {okCall:function(){
			var flowUrl= __basePath +"/pages/business/document/start.action";
			$.ajax({
		  		type:'POST',
		  		dataType:'json',
		  		url:flowUrl,
		  		data:{actDefId:"ceshifawenliucheng:3:10000003960022",businessKey:domainId},
		  		success:function(json){
					alertMsg.info("流程启动成功!");
				}
			});
		}});
	}
	
	ns.document.nextDocumentFlow = function(){
		alertMsg.confirm("确定要提交吗?", {okCall:function(){
			var flowUrl= __basePath +"/pages/business/document/next.action?taskId=${taskId}";
			$.ajax({
		  		type:'POST',
		  		dataType:'json',
		  		url:flowUrl,
		  		success:function(json){
					alertMsg.info("提交成功!");
				}
			});
		}});
	}
	
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
	});
	
	$(function(){
	});
	
//-->
</script>
<div class="buttonPanel">
	<button type="button" id="editDocument" name="editDocument" class="listbuttonDisable" disabled="disabled">修改</button>
	<button type="button" id="saveDocument" name="saveDocument" class="listbutton" onclick="ns.document.saveDocument();">保存</button>
	<c:if test="${taskId==null}">
		<button type="button" id="saveDocument" name="saveDocument" class="listbutton" onclick="ns.document.startDocumentFlow();" style="width: 100px;">启动流程</button>
	</c:if>
	<c:if test="${taskId!=null}">
		<button type="button" id="saveDocument" name="saveDocument" class="listbutton" onclick="ns.document.nextDocumentFlow();" style="width: 100px;">提交</button>
	</c:if>
</div>
<div id="yunDialog">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected" onclick="ns.common.changeSaveAction('saveDocument','ns.document.saveDocument')"><a><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
				<form id="documentForm" method="post" action="<%=basePath %>/pages/business/document/save.action" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);" onkeydown="return enterNotSubmit(event);">
					<input id="documentId" type=hidden name="document.id" value="${document.id}"/>
					<input id="domainId" type="hidden" name="domainId" value="${document.id}" />
			  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								发文类型
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.type" value="${document.type}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								日期
							</td>
							<td align="left" width="40%">
							<input readonly="true" type="text" id="document.date" name="document.date" value='<fmt:formatDate value="${document.date}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 180px;"/>
							<img onclick="WdatePicker({el:'document.date',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								文号
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.wenhao" value="${document.wenhao}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								密级
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.miji" value="${document.miji}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								标题
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.title" value="${document.title}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								主题词
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.keyword" value="${document.keyword}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								主送
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.zhusong" value="${document.zhusong}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								抄送
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.chaosong" value="${document.chaosong}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								拟稿部门
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.department" value="${document.department}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								拟稿人
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.drafter" value="${document.drafter}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								办理流程
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.flow" value="${document.flow}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								正文
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.content" value="${document.content}"  class="textInput " style="width:180px;"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								总状态
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.flowstate" value="${document.flowstate}"  class="textInput " style="width:180px;"/>
							</td>
							<td align="left" class="Input_Table_Label" width="10%">
								归档时间
							</td>
							<td align="left" width="40%">
							<input readonly="true" type="text" id="document.filetime" name="document.filetime" value='<fmt:formatDate value="${document.filetime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 180px;"/>
							<img onclick="WdatePicker({el:'document.filetime',dateFmt:'yyyy-MM-dd'})" src="js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer"/>
							</td>
						</tr>
						<tr>
							<td align="left" class="Input_Table_Label" width="10%">
								当前审批人
							</td>
							<td align="left" width="40%">
			 		 			<input type="text" name="document.curapprover" value="${document.curapprover}"  class="textInput " style="width:180px;"/>
							</td>
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