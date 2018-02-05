<%--
	time:2011-12-01 16:50:08
	desc:edit the 流程变量定义
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>流程变量</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmDefVar"></script>
	<script type="text/javascript">
	var defId="${defId}";
	var actDeployId="${actDeployId}";
	var actDefId="${actDefId}";
	function showRequest(formData, jqForm, options) { 
		return true;
	} 
	$(function() {
		valid(showRequest,function(){});
		change();
		$("a.save").click(save);
	});
	function change(){
		var s= $("#varScope").val();
		$("#n").each(function(i,o){
			if(s=='task'){
				$(".sub").eq(i).show();
			}else{
				$(".sub").hide();
			}
		});
	}
	function save(){
		 var rtn=$("#bpmDefVarForm").valid();
   		 if(!rtn) return;
		 var url=__ctx+ "/platform/bpm/bpmDefVar/save.ht";
   		 var para=$('#bpmDefVarForm').serialize();
   		 $.post(url,para,showResult);
	}
	function showResult(responseText)
   	{
   		var obj=new com.hotent.form.ResultMessage(responseText);
   		if(!obj.isSuccess()){
   			$.ligerDialog.err('出错信息',"保存流程变量失败",obj.getMessage());
   			return;
   		}else{
   			$.ligerDialog.confirm(obj.getMessage()+',是否继续操作?','提示信息',function(rtn){
   				if(!rtn){
   					window.close();
   				}
   				else{
   					valid.resetForm();
   				}
   			});
   		}
   	}
</script>
<style type="text/css">
        .sub{display:none;}
</style>
</head>
<body>
    <div class="panel">
		<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">
						<c:if test="${bpmDefVar.varId==null }">添加流程变量</c:if>
						<c:if test="${bpmDefVar.varId!=null }">编辑流程变量</c:if>
						</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="group"><a class="link save" id="btnSearch"><span></span>保存</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del" onclick="javasrcipt:window.close()"><span></span>关闭</a></div>
						
						</div>	
					</div>
				</div>
		<div class="panel-body">

				<form id="bpmDefVarForm" method="post" action="save.ht">
					<div class="panel-detail">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th width="20%">变量名称:</th>
								<td><input type="text" id="varName" name="varName" value="${bpmDefVar.varName}"  class="inputText"/>
								</td>
							</tr>
							<tr>
								<th width="20%">变量Key: </th>
								<td><input type="text" id="varKey" name="varKey" value="${bpmDefVar.varKey}"  class="inputText"/></td>
							</tr>		
							<tr>
								<th width="20%">数据类型: </th>
								<td>
									<select name="varDataType"  >
										<option value="varchar" <c:if test="${bpmDefVar.varDataType eq 'varchar'}">selected='selected'</c:if>>字符串(varchar)</option>
									<%-- 	<option value="short" <c:if test="${bpmDefVar.varDataType eq 'short'}">selected='selected'</c:if>>短整形(short)</option>
										<option value="int" <c:if test="${bpmDefVar.varDataType eq 'int'}">selected='selected'</c:if>>整形(integer)</option>
										<option value="long" <c:if test="${bpmDefVar.varDataType eq 'long'}">selected='selected'</c:if>>长整型(long)</option>
										<option value="double" <c:if test="${bpmDefVar.varDataType eq 'double'}">selected='selected'</c:if>>浮点数(double)</option> --%>
										<option value="number" <c:if test="${bpmDefVar.varDataType eq 'number'}">selected='selected'</c:if>>数字(number)</option>
										<option value="date" <c:if test="${bpmDefVar.varDataType eq 'date'}">selected='selected'</c:if>>日期(date)</option>
									</select>
								</td>
							</tr>					
							<%-- <tr>
								<th width="20%">作用域: </th>
								<td>
								<select name="varScope" id="varScope"  onchange="change()" style="width:40%;">
								<option value="global">--请选择--</option>
								<option value="global" <c:if test="${bpmDefVar.varScope eq 'global'}">selected='selected'</c:if>>全局</option>
								<option value="task" <c:if test="${bpmDefVar.varScope eq 'task'}">selected='selected'</c:if>>局部</option>
								</select></td>
								
							</tr> --%>
							<tr class="sub" id="n">
							<th width="20%" >节点名称: </th>
								<td>
								<select id="nodeId" name="nodeId" style="width:40%;">
								<c:forEach items="${nodeMap}" var="node">
								<option value="${node.key},${node.value}"  <c:if test="${node.value==bpmDefVar.nodeName}">selected='selected'</c:if>>${node.value}</option>
								</c:forEach>
								</select>
								</td>
							</tr>
						</table>
						<input type="hidden" name="varId"  id="varId" value="${bpmDefVar.varId}" />
						<input type="hidden" id="actDeployId" name="actDeployId" value="${actDeployId}"/>
						<input type="hidden" id="defId" name="defId" value="${defId}"  />
					</div>
				</form>
			</div>
		</div>
</body>
</html>
