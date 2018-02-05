<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--
	$(function(){
		xgUrl="<%=basePath %>/pages/resource/templateeditBase.action?formId=${formId}&params=${params}" + "&op=edit";
		ns.common.mouseForButton();
		$tr = $('#templateView tr')
		for(var i=6; i<$tr.length; i++){
			$tr.eq(i).hide();
		}
		$.ajax( {
			type : 'post',
			async : false,
			dataType:'json',
			url : '<%=basePath %>/pages/resource/compextableData.action?mainTable=sys_templatelibrary&params=id:${sysTemplate.tblMobanyongtu}',
			success : function(data) {
				if(data.length>0){
					$.each(data,function(entryIndex, entry){
						$('#tblMobanyongtu').html(entry.tbl_mobanmingcheng);
					})
				}else{
					$('#tblMobanyongtu').html('无');
				}
			}
		});
	});
//-->
</script>
<div class="buttonPanel">
	<div style="display: none;">
		<input type="submit" id="domainSubmit"
			value="compexDomainTabEdit111157Submit">
		<input value="109" name="formId" id="formId">
		<input value="" name="domainId" id="domainId">
		<input value="sys_template-id:bba9087bc3a446ae882827a92996f7b8;"
			name="params" id="paramsId">
		<input value="" name="mainTable" id="mainTable">
	</div>
	<button onclick="eventCompexXG();" style="width: 60px; height: 24px;"
		class="listbutton" name="b1" id="XG" type="button">
		修改
	</button>
	<button onclick="eventCompexBC();" style="width: 60px; height: 24px;"
		class="listbuttonDisable" name="b2" id="BC" type="button"
		disabled="disabled">
		保存
	</button>
	<button onclick="eventCompexFORMBZ();"
		style="width: 60px; height: 24px;" class="listbutton" name="b4"
		id="FORMBZ" type="button">
		帮助
	</button>
	<input type="hidden" value="compexDomainTabEdit111157" id="tabId">
	<input type="hidden" value="" id="modelId">
</div>
<div id="yunDialog"
	style="height: 100%; overflow-x: hidden; OVERFLOW-Y: auto;">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected">
						<a onclick="setCompexTabForm(&quot;compexDomainTabEdit111157&quot;);"><span
							style="color: ; font-family: &amp; amp;">基本信息</span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div id="tabDivId" class="tabsContent">
			<div id="compexDomainTabEdit111157" style="display: block;"
				inited="1000">
				<form
					onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);"
					class="pageForm required-validate" method="post"
					action="<%=basePath %>/pages/resource/templateBasecompexsave.action"
					id="compexDomainTabEdit111157FormID" novalidate="novalidate">
					<div style="display: none;" id="compexDomainTabEdit111157ParamDiv">
						<input type="submit" name="submit"
							id="compexDomainTabEdit111157Submit">
						<input type="hidden" value="109" name="formId">
						<input type="hidden" value="157" name="tabId">
						<input type="hidden" value="-1" name="partitionId">
						<input type="hidden"
							value="sys_template-id:bba9087bc3a446ae882827a92996f7b8;"
							name="params">
					</div>
					<table width="100%" cellspacing="0" cellpadding="2" border="0"
						class="Input_Table" style="margin: 0px auto;" id="templateView">
						<tbody>
							<tr height="25" width="100%" index="1">
								<td width="15%" index="1,3" belong="label"
									class="Input_Table_Label">
									<div id="compexDomainTabEdit111157_label_t2"
										title="平台内使用的模板的规范名称，可由中文、数字、字母组成，长度不能超过100个字符，必填项；">
										模板名称
									</div>
								</td>
								<td width="35%" index="1,4" belong="value">
									<div id="compexDomainTabEdit111157_value_t2">
										${sysTemplate.tblTemplatechname}
									</div>
								</td>
								<td index="1,5" belong="label" class="Input_Table_Label">
									<div id="compexDomainTabEdit111157_label_t4"
										title="模板库中的通用模板，非必填项；">
										继承模板
									</div>
								</td>
								<td id="tblMobanyongtu" >
									<c:forEach items="${templateLibaryList}" var="library">
										<c:if test="${sysTemplate.tblMobanyongtu==library.id}">${library.templateChName}</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr height="25" width="100%" index="5">
								<td index="5,1" belong="label" class="Input_Table_Label">
									<div id="compexDomainTabEdit111157_label_t7"
										title="模板的创建人，由系统自动设置">
										创建人
									</div>
								</td>
								<td index="5,2" belong="value">
									${sysTemplate.createUser}
								</td>
								<td index="5,3" belong="label" class="Input_Table_Label">
									<div id="compexDomainTabEdit111157_label_t8"
										title="模板的创建时间，由系统自动设置">
										创建时间
									</div>
								</td>
								<td index="5,4" belong="value">
									<fmt:formatDate value="${sysTemplate.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr height="25" width="100%" index="6">
								<td index="6,1" belong="label" class="Input_Table_Label">
									<div id="compexDomainTabEdit111157_label_t9"
										title="模板的修改人，由系统自动设置">
										修改人
									</div>
								</td>
								<td index="6,2" belong="value">
									${sysTemplate.updateUser}
								</td>
								<td index="6,3" belong="label" class="Input_Table_Label">
									<div id="compexDomainTabEdit111157_label_t10"
										title="模板的修改时间，由系统自动设置">
										修改时间
									</div>
								</td>
								<td index="6,4" belong="value">
									<fmt:formatDate value="${sysTemplate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>