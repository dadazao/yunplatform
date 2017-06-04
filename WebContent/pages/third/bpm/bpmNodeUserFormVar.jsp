<%@page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<%@include file="/commons/include/form.jsp" %>
		<title>人员表单变量</title>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js"></script>
		<script type="text/javascript">
			$(function(){
				$("input[name='varType']").click(function(){
					checkType(this);
				});
			});
			
			//点击人员参照单选项时
			function checkType(ck){
				var objTr=$(ck).closest("tr");
				$("select", objTr).attr('disabled',false);
				$('select', objTr.siblings('tr')).attr('disabled',true);
			}
			
			function save(){
				var objType=$("[name='varType']:checked");
				if(objType.length==0){
					$.ligerDialog.warn("请选择表单变量类型!",$lang.tip.msg);
					return ;
				}
				var varType=objType.val();
				var varTypeName=objType.attr("memo");
				var objTr=objType.closest("tr");
				var objSelect=$("select[name='varName'] option",objTr);
				var len=objSelect.length;
				if(len==0){
					$.ligerDialog.warn("没有表单变量!",$lang.tip.msg);
					return ;
				}
				var objSelect=$("select[name='varName'] option:selected",objTr);
				len=objSelect.length;
				if(len==0){
					$.ligerDialog.warn("没有选择表单变量!",$lang.tip.msg);
					return ;
				}
				
				var varName=objSelect.get(0).value;
				var varText=objSelect.get(0).text;
				var obj={};
				var userType,userText;
				if(varType=='6') {
					var userSelect = $("select[name='userType'] option:selected",objTr);
					userType = userSelect.get(0).value;
					userText = userSelect.get(0).text;
					obj.json="{type:"+ varType +",userType:" + userType + ",varName:\""+varName+"\"}";
					obj.description=varTypeName +",类型【"+userText+"】,变量名【"+varText +"】";
				}
				else {
					obj.json="{type:"+ varType +",varName:\""+varName+"\"}";
					obj.description=varTypeName +",变量名【"+varText +"】";
				}
				window.returnValue= obj;
				window.close();
			}
			
		</script>
	</head>
	
<body style="overflow: hidden;">
	<div class="panel">
		<div class="hide-panel">
			<div class="panel-top">
				<div class="tbar-title">
				    <span class="tbar-label">人员表单变量</span>
				</div>
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link save" id="dataFormSave" onclick="save();" href="#"><span></span>保存</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link close" onclick="window.close()" href="#"><span></span>关闭</a></div>
					</div>
				</div>
			</div>
		</div>
		<div style="text-align: center;padding-top: 10px;">
			<fieldset>
				<legend><b>表单变量类型</b></legend>
				<table class="table-grid" width="90%">
					<tr>
						<th style="width: 20px"></th>
						<th>执行人表单类型</th>
						<th>表单变量</th>
						
					</tr>
					<tr>
						<td><input type="radio" name="varType" value="1" memo="用户" <c:if test="${varType==1}">checked="checked"</c:if> /></td> 
						<td>用户</td>
						<td>
							<select name="varName" <c:if test="${varType!=1}">disabled="disabled"</c:if> >
								<c:forEach items="${userVarList}" var="item">
									<option value="${item.fieldName}" <c:if test="${varName==item.fieldName}">selected="selected"</c:if> >${item.fieldDesc}</option>	
								</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
						<td><input type="radio" name="varType" value="2"  memo="组织" <c:if test="${varType==2}">checked="checked"</c:if> /></td> 
						<td>组织</td>
						<td>
							<select name="varName" <c:if test="${varType!=2}">disabled="disabled"</c:if>>
								<c:forEach items="${orgVarList}" var="item">
									<option value="${item.fieldName}" <c:if test="${varName==item.fieldName}">selected="selected"</c:if> >${item.fieldDesc}</option>	
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><input type="radio" name="varType" value="3"  memo="组织负责人" <c:if test="${varType==3}">checked="checked"</c:if>/></td> 
						<td>组织负责人</td>
						<td>
							<select name="varName" <c:if test="${varType!=3}">disabled="disabled"</c:if> >
								<c:forEach items="${orgVarList}" var="item">
									<option value="${item.fieldName}" <c:if test="${varName==item.fieldName}">selected="selected"</c:if> >${item.fieldDesc}</option>	
								</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
						<td><input type="radio" name="varType" value="4"  memo="角色"  <c:if test="${varType==4}">checked="checked"</c:if>/></td> 
						<td>角色</td>
						<td>
							<select name="varName" <c:if test="${varType!=4}">disabled="disabled"</c:if>>
								<c:forEach items="${roleVarList}" var="item">
									<option value="${item.fieldName}" <c:if test="${varName==item.fieldName}">selected="selected"</c:if> >${item.fieldDesc}</option>
								</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
						<td><input type="radio" name="varType" value="5" memo="岗位"  <c:if test="${varType==5}">checked="checked"</c:if>/></td> 
						<td>岗位</td>
						<td>
							<select name="varName" <c:if test="${varType!=5}">disabled="disabled"</c:if>>
								<c:forEach items="${posVarList}" var="item">
									<option value="${item.fieldName}" <c:if test="${varName==item.fieldName}">selected="selected"</c:if> >${item.fieldDesc}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><input type="radio" name="varType" value="6" memo="普通变量"  <c:if test="${varType==6}">checked="checked"</c:if>/></td> 
						<td>普通变量</td>
						<td>
							<select name="userType" <c:if test="${varType!=6}">disabled="disabled"</c:if>>
								<option value="1" <c:if test="${userType eq '1'}">selected='selected'</c:if> >用户</option>
								<option value="2" <c:if test="${userType eq '2'}">selected='selected'</c:if> >组织</option>
								<option value="3" <c:if test="${userType eq '3'}">selected='selected'</c:if> >组织负责人</option>
								<option value="4" <c:if test="${userType eq '4'}">selected='selected'</c:if> >角色</option>
								<option value="5" <c:if test="${userType eq '5'}">selected='selected'</c:if> >岗位</option>
							</select>
							<select name="varName" <c:if test="${varType!=6}">disabled="disabled"</c:if>>
								<c:forEach items="${otherList}" var="item">
									<option value="${item.fieldName}" <c:if test="${varName==item.fieldName}">selected="selected"</c:if> >${item.fieldDesc}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
		
	</div>
</body>
</html>