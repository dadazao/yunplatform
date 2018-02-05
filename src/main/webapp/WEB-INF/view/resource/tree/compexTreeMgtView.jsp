<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		compexViewJson('<%=basePath %>/pages/resource/compexviewJson.action?params=${params}&formId=${formId}');
		$("#BC").attr("disabled","disabled");
		$("#BCBXZ").attr("disabled","disabled");
		$("#BC").attr("class","listbuttonDisable");
		$("#BCBXZ").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		xgUrl = "<%=basePath %>/pages/resource/treeedit.action?formId=${formId}&params=${params}" + "&op=edit";
		ns.common.mouseForButton();
		
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
	});
	
	function selectTreeRoot(){
		var tableId = $("#mgrTreetableId").val();
		var parentColumnId = $("#mgrTreeparentColumnId").val();
		var disColumnId = $("#mgrTreedisColumnId").val();
		var orderColumnId = $("#mgrTreeorderColumnId").val();
		
	    var url = '<%=basePath %>/pages/resource/treefetchShowTreeParamAction.action' +
		 '?tableId='+tableId+'&columnId='+parentColumnId +
		 '&disColumnId='+disColumnId+'&orderColumnId='+orderColumnId;
	    
		var id = $("#mgrTreeId").val();
		if(id!=null && id.length>0){
			url +="&id="+id;
		}
		$.pdialog.open(url,"selectDialog","选择",{width:300,height:600,mask:true,resizable:true});
	}

//-->
</script>
<div>
	<div style="display: none;">
		<input id="domainSubmit" type="submit">
		<input id="formId" name="formId" value="${formId}"/>
		<input id="domainId" name="domainId" value="${domainId}">
		<input id="paramsId" name="params" value="${params}">
		<input id="mainTable" name="mainTable" value="${mainTable}">
		
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<table><tr><td>
				<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
					<c:if test="${formButton.hasAuth=='0'}">
						<c:if test="${formButton.buttonType == '0'}">
							<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbutton" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;" onclick="eventCompex${formButton.funcName}();" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;font-size: ${formButton.button.buttonNameFontSize};background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
						</c:if>
					</c:if>
					<c:if test="${formButton.hasAuth=='1'}">
						<c:if test="${formButton.buttonType == '0'}">
							<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbuttonDisable" disabled="disabled" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;font-size: ${formButton.button.buttonNameFontSize};background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
						</c:if>
					</c:if>
				</c:forEach>
				<input id="tabId" type="hidden" value=""/>
				<input id="modelId" type="hidden" value="${id}"/>
				<button id="buttonBZ" name="buttonBZ" class="listbutton"  onclick="selectTreeRoot();" >预 览</button>
			</td></tr></table>
		</div>
	</div>
</div>
<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
        	<ul>
            	<li class="selected"><a><span>基本信息</span></a></li>
            </ul>
        </div>
	</div>
	<div class="tabsContent" id="tabDivId">
		<div align="center">
			<input id="mgrTreeId" type=hidden name="mgrTree.id" value="${mgrTree.id}"/>
			<input id="mgrTreetableId" type=hidden name="mgrTree.tableId" value="${mgrTree.tableId}"/>
			<input id="mgrTreeparentColumnId" type=hidden name="mgrTree.parentColumnId" value="${mgrTree.parentColumnId}"/>
			<input id="mgrTreedisColumnId" type=hidden name="mgrTree.disColumnId" value="${mgrTree.disColumnId}"/>
			<input id="mgrTreeorderColumnId" type=hidden name="mgrTree.orderColumnId" value="${mgrTree.orderColumnId}"/>
			
	  	 	<table width="100%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
	  	 		<tr>
   					<td width="10%" height="30" align="left" class="Input_Table_Label">
						分类
					</td>
					<td height="30" align="left" >
						<c:if test="${'sys'==mgrTree.systemTeam }">系统类</c:if>
						<c:if test="${'bus'==mgrTree.systemTeam }">业务类</c:if>
						<c:if test="${'tes'==mgrTree.systemTeam }">测试类</c:if>
					</td>
   				</tr>
   				<c:if test="${'1'!=mgrTree.tableType }">
					<tr>
						<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
							&nbsp;树名称
						</td>
						<td style="width:40%;height:30px;" align="left" colspan="1">
							${mgrTree.treename}&nbsp;
						</td>
						<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
							&nbsp;所属类型
						</td>
						<td style="width:40%;height:30px;" align="left" colspan="1">
							<c:forEach items="${treeTypes}" var="type">
								<c:if test="${type.value==mgrTree.type}">${type.name}</c:if>
							</c:forEach>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
							&nbsp;所属表
						</td>
						<td  align="left" style="width:40%;height:30px;" >
							${mgrTree.tableZhName}&nbsp;
						</td>	
						<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
							&nbsp;显示字段
						</td>
						<td  align="left" style="width:40%;height:30px;" >
							${mgrTree.disColumnZhName}&nbsp;
						</td>
					</tr>
					<tr>
						<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
							&nbsp;顺序字段
						</td>
						<td  align="left" style="width:40%;height:30px;">
							${mgrTree.orderColumnZhName}&nbsp;
						</td>
						<td align="left" style="width:10%;height:30px;" class="Input_Table_Label" >
							&nbsp;父节点字段
						</td>
						<td align="left" style="width:40%;height:30px;" >
							${mgrTree.parentColumnZhName}&nbsp;
						</td>
					</tr>
					<tr>	
						<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
							&nbsp;根节点
						</td>
						<td align="left" style="width:40%;height:30px;" colspan="3">
							${mgrTree.rootZhName}&nbsp;
						</td>
					</tr>
				</c:if>
				<c:if test="${'1'==mgrTree.tableType }">
					<tr>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;树名称
						</td>
						<td style="width: 40%; height: 30px;" align="left" colspan="1">
							${mgrTree.treename}
						</td>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;所属类型
						</td>
						<td align="left" style="width: 40%; height: 30px;">
							<c:forEach items="${treeTypes}" var="type">
								<c:if test="${type.value==mgrTree.type}">${type.name}</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表一所属表	</td>
						<td align="left" style="width: 40%; height: 30px;">
							${mgrTree.tableZhName}&nbsp;
						</td>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
					&nbsp;表二所属表	</td>
						<td align="left" style="width: 40%; height: 30px;">
							${tableChildName}&nbsp;
						</td>
					</tr>
					<tr>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表一显示字段</td>
						<td align="left" style="width: 40%; height: 30px;">
							${mgrTree.disColumnZhName}&nbsp;
						</td>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表二显示字段</td>
						<td align="left" style="width: 40%; height: 30px;">
							${disColumnIdChildName}&nbsp;
						</td>
					</tr>
					<tr>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表一顺序字段
						</td>
						<td align="left" style="width: 40%; height: 30px;">
							${mgrTree.orderColumnZhName}&nbsp;
						</td>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表二顺序字段
						</td>
						<td align="left" style="width: 40%; height: 30px;">
							${orderColumnIdChildName}&nbsp;
						</td>
					</tr>
					<tr>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表一父节点字段
						</td>
						<td align="left" style="width: 40%; height: 30px;">
							${mgrTree.parentColumnZhName}&nbsp;
						</td>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表二关联字段
						</td>
						<td align="left" style="width: 40%; height: 30px;">
							${parentColumnIdChildName}&nbsp;
						</td>
					</tr>
					<tr>
						<td style="width: 10%; height: 30px;" align="left"
							class="Input_Table_Label">
							&nbsp;表一根节点
						</td>
						<td align="left" style="width: 40%; height: 30px;" colspan="3">
							${mgrTree.rootZhName}&nbsp;
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="width:10%;" align="left"" class="Input_Table_Label">
						<label>&nbsp;功能说明</label>
					</td>
   						<td colspan=3  align="left"" >
   							${mgrTree.comment}&nbsp;
   						</td>
				</tr>
				<tr>
					<td style="width:10%;" align="left"" class="Input_Table_Label">
						<label>&nbsp;备注</label>
					</td>
   						<td colspan=3  align="left"" >
   							${mgrTree.remarks}&nbsp;
   						</td>
				</tr>
				<tr>
					<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
						&nbsp;创建人
					</td>
					<td  align="left" style="width:40%;height:30px;" >
						${mgrTree.createUser}&nbsp;
					</td>
					<td  style="width:10%;height:30px;" align="left" class="Input_Table_Label">
						&nbsp;创建时间
					</td>
					<td align="left" style="width:40%;height:30px;" colspan=3 >
						<fmt:formatDate value="${mgrTree.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td style="width:10%;height:30px;" align="left" class="Input_Table_Label">
						&nbsp;修改人
					</td>
					<td  align="left" style="width:40%;height:30px;" >
						${mgrTree.updateUser}&nbsp;
					</td>
					<td  style="width:10%;height:30px;" align="left" class="Input_Table_Label">
						&nbsp;修改时间
					</td>
					<td align="left" style="width:40%;height:30px;" colspan=3 >
						<fmt:formatDate value="${mgrTree.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
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
