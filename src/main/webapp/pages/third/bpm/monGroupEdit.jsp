<%--
	time:2013-06-08 11:14:50
	desc:edit the 监控分组
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<title>添加监控分组</title>
	<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/BpmDefinitionDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js" ></script>
	<script type="text/javascript">
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			var frm=$('#monGroupForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				if(frm.valid()){
					frm.submit();
				}
			});
		
			$("a.add").click(function(){
				BpmDefinitionDialog({isSingle:false,callback:dlgCallBack,returnDefKey:true,showAll:1});
			});
			
			$("#tbodySub").delegate("a.del","click",function(){
				var obj=$(this);
				obj.closest("tr").remove();
			});
			
		});
		
		
		function dlgCallBack(defIds,subjects,defKeys){
			if(subjects==null || subjects =="") return;
			var template=$("#txtTemplate").val();
			var arySubject=subjects.split(",");
			var aryDefKey=defKeys.split(",");
			var list=[];
			for(var i=0;i<aryDefKey.length;i++){
				var key=aryDefKey[i];
				var tr=$("#" + key);
				if(tr.length>0) continue;
				var subject=arySubject[i];
				var obj={};
				obj.flowkey=key;
				obj.flowName=subject;
				list.push(obj);
			}
			var str= easyTemplate(template,list).toString();
			$("#tbodySub").append(str);
		};
		
		
		function showResponse(responseText) {
			var obj = new com.hotent.form.ResultMessage(responseText);
			if (obj.isSuccess()) {
				$.ligerDialog.confirm(obj.getMessage()+","+$lang.operateTip.continueOp,$lang.tip.msg,  function(rtn) {
					if(rtn){
						this.close();
					}else{
						window.location.href = "${ctx}/platform/bpm/monGroup/list.ht";
					}
				});
			} else {
				$.ligerDialog.error($lang.tip.msg,obj.getMessage());
			}
		}
		
	</script>
</head>
<body>
<div class="panel">
	<div class="panel-top">
		<div class="tbar-title">
		    <c:choose>
			    <c:when test="${monGroup.id !=null}">
			        <span class="tbar-label">编辑监控分组</span>
			    </c:when>
			    <c:otherwise>
			        <span class="tbar-label">添加监控分组</span>
			    </c:otherwise>			   
		    </c:choose>
		</div>
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
				<div class="l-bar-separator"></div>
				<div class="group"><a class="link back" href="list.ht"><span></span>返回</a></div>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<form id="monGroupForm" method="post" action="save.ht">
				<input type="hidden" id="creator" name="creator" value="${monGroup.creator}"  class="inputText" />
				<input type="hidden" id="creatorid" name="creatorid" value="${monGroup.creatorid}"  class="inputText" />
				<input type="hidden" id="createtime" name="createtime" value="<fmt:formatDate value="${monGroup.createtime}" pattern="yyyy-MM-dd HH:mm:sss"/>"  class="inputText" />
		
			<table class="table-detail" cellpadding="0" cellspacing="0" border="0" type="main">
				<tr>
					<th width="20%">分组名称: </th>
					<td><input type="text" id="name" name="name" value="${monGroup.name}"  style="width: 400px;"  class="inputText" validate="{required:false,maxlength:40}"  /></td>
				</tr>
				<tr>
					<th width="20%">权限:</th>
					<td>
					<select name="grade">
						<option value="1" <c:if test="${monGroup.grade==1}">selected="selected"</c:if> >查看标题</option>
						<option value="2" <c:if test="${monGroup.grade==2}">selected="selected"</c:if>>查看流程实例明细</option>
						<option value="3" <c:if test="${monGroup.grade==3}">selected="selected"</c:if>>可干预</option>
					</select>
					</td>
				</tr>
				<c:if test="${monGroup.id !=null}">
					<tr>
						<th width="20%">状态: </th>
						<td>
							<input type="radio" name="enabled" value="1"  <c:if test="${monGroup.enabled==1}">checked="checked"</c:if> />启用
							<input type="radio" name="enabled" value="0"  <c:if test="${monGroup.enabled==0}">checked="checked"</c:if> />禁用
						</td>
					</tr>
				</c:if>
				
			</table>
			<table class="table-grid table-list" cellpadding="1" style="margin-top:10px;"  cellspacing="1" id="monGroupItem" formType="window" type="sub">
				<tr>
					<td colspan="3">
						<div class="group" align="left">
				   			<a id="btnAdd" class="link add">添加监控流程项目</a>
			    		</div>
			    		
		    		</td>
				</tr>
				<tr>
					<th>流程定义KEY</th>
					<th>流程定义名称</th>
					<th style="text-align: center">操作</th>
				</tr>
				<tbody id="tbodySub">
					<c:forEach items="${monGroupItemList}" var="monGroupItemItem" varStatus="status">
					    <tr id="${monGroupItemItem.flowkey}">
						    <td >
						        ${monGroupItemItem.flowkey}
							    <input type="hidden" name="flowkey" value="${monGroupItemItem.flowkey}"/>
						    </td>
						    <td>
						    	${monGroupItemItem.flowName}
						    </td>
						    <td style="text-align: center">
						    	<a href="#" class="link del">删除</a>
						    </td>
							
					    </tr>
					</c:forEach>
				</tbody>
		    </table>
			<input type="hidden" name="id" value="${monGroup.id}" />
		</form>
		
	</div>
	
</div>
<textarea style="display:none" id="txtTemplate">
	<#list data as obj>
		<tr id="\${obj.flowkey}">
		    <td >
		        \${obj.flowkey}
			    <input type="hidden" name="flowkey" value="\${obj.flowkey}"/>
		    </td>
		    <td>
		    	\${obj.flowName}
		    </td>
		    <td style="text-align: center">
		    	<a href="#" class="link del">删除</a>
		    </td>
	 	</tr>
	</#list>
</textarea>

</body>
</html>
