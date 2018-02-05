<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点设置管理</title>

<script type="text/javascript">
	//流程定义ID
	var actDefId="${bpmDefinition.actDefId}";
	
	function save(){
		var type=$("#bpmFormKey").val();
		var isEmpty=isEmptyForm();
		if(!isEmpty){
			if(type==-1){
				alertMsg.error('请设置流程实例业务表单!');
				return;
			}else if(type==0){
				var bpmFormKey=$("#bpmFormKey").val();
				if(!bpmFormKey||bpmFormKey==0){
					alertMsg.error('请设置流程实例业务表单!');
					return;
				}
			}else if(type==1){
				var bpmFormUrl=$("#bpmFormUrl").val();
				if(!bpmFormUrl||bpmFormUrl==''){
					alertMsg.error('请设置流程实例业务表单!');
					return;
				}
			}
		}
		//$('#dataForm')[0].submit();
		var urlParam = "<%=basePath %>/pages/third/bpm/bpmNodeSet/save.action?defId=${bpmDefinition.defId}";
		var param = $("#dataForm").serialize();
		$.ajax( {
			type : 'POST',
			url : urlParam,
			data: param,
			dataType: "json",
			success : function(data) {
				if(data.statusCode == '200') {
					alertMsg.correct(data.message);
				}else{
					alertMsg.error(data.message);
				}
			}
		});
	}
	
	function isEmptyForm(){
		var isEmpty=true;
		var globalFormObj=$("div[name='globalFormUrl']");
		var globalUrlObj=globalFormObj.find('input[name="formUrlGlobal"]');
		var globalDetailObj=globalFormObj.find('input[name="detailUrlGlobal"]');
		var globalUrl=(globalUrlObj!=undefined)?globalUrlObj.val():"";
		var globalDetailUrl=(globalDetailObj!=undefined)?globalDetailObj.val():"";
		var globalFormKey=($("#defaultFormKey")!=undefined)?$("#defaultFormKey").val():0;
		if(globalFormKey!=0){
			isEmpty=false;
		}
		$("div[name='nodeForm']").each(function(){
			var formKeyObj=$(this).find('input[name="formKey"]');
			var formKey=(formKeyObj!=undefined)?formKeyObj.val():0;
			var formUrlObj=$(this).find('input[name="formUrl"]');
			var formUrl=(formUrlObj!=undefined)?formUrlObj.val():"";
			var formDetailObj=$(this).find('input[name="detailUrl"]');
			var formDetail=(formDetailObj!=undefined)?formDetailObj.val():"";
			if(formKey!=0){
				isEmpty=false;
			}
		});
		return isEmpty;
	}
	
	//选择全局表单
	function selectGlobalForm() {
		FormDialog({
			callback : function(ids, names, tableId) {
				var globalFormObj=$("div[name='globalForm']");
				var bpmFormObj=$("div[name='bpmForm']");
				
				globalFormObj.val(0);
				bpmFormObj.val("0");
				
				$("input.formKey",globalFormObj).val(ids);
				$("input.formKey",bpmFormObj).val(ids);
				$("input.formDefName",globalFormObj).val(names);
				$("input.formDefName",bpmFormObj).val(names);
				$("span[name='spanForm']",globalFormObj).html(names);
				$("span[name='spanForm']",bpmFormObj).html(names);
			}
		})
	}
	
	//选择业务表单
	function selectBpmForm(obj) {
		FormDialog({
			callback : function(ids, names, tableId) {
				var tdObj=$(obj).parent();
				$("input.formKey",tdObj).val(ids);
				$("input.formDefName",tdObj).val(names);
				//给表单添加 超链接，使之能看到表单明细
				var namesUrl="<a target='_blank' href="+__ctx+"/platform/form/bpmFormHandler/edit.ht?formDefId="+ids+" >"+names+"</a>";
				$("span[name='spanForm']",tdObj).html(namesUrl);
			}
		})
	}
	
	//选择节点表单
	function selectNodeForm(obj) {
		FormDialog({
			callback : function(ids, names, tableId) {
				var tdObj=$(obj).parent();
				$("input.formKey",tdObj).val(ids);
				$("input.formDefName",tdObj).val(names);
				$("input.tableId",tdObj).val(tableId);
				//给表单添加 超链接，使之能看到表单明细
				var namesUrl="<a target='_blank' href="+__ctx+"/platform/form/bpmFormHandler/edit.ht?formDefId="+ids+" >"+names+"</a>";
				$("span[name='spanForm']",tdObj).html(namesUrl);
				// 是否显示子表授权功能
				$.ajax({
					type : "POST",
					url : __ctx + "/platform/form/bpmFormDef/isSubTable.ht",
					data : {formKey:ids},
					dataType : "json",
					success : function(res) {
						var result= eval('(' + res + ')');
						if(result.success && obj.id == 'subNodeSel'){
							$(obj).siblings("a.grant").show();
						}else{
							$(obj).siblings("a.grant").hide();
						}
					},
					error : function(res) {
						
					}
				});
			}
		})
	}
	//全局表单授权
	function authorizeDialog(aurl){
		var url=aurl;
		var winArgs="dialogWidth=850px;dialogHeight=600px;help=0;status=no;center=yes;resizable=no;";
		url=url.getNewUrl();
		window.showModalDialog(url,"",winArgs);
	}
	
	//清除表单
	function clearForm(obj){
		var btn=$(obj);
		var tdObj=btn.parent();
		$("input.formKey",tdObj).val('');
		$("input.formDefName",tdObj).val('');
		$("span[name='spanForm']",tdObj).text('');
		$(obj).siblings("a.grant").hide();
	}
	
	//表单授权
	function authorize(obj,nodeId){
		var btn=$(obj);
		var tdObj=btn.parent();
		var objDefId=$("input.formKey",tdObj);
		if(objDefId.val()==""||objDefId.val()==0){
			$.ligerDialog.warn('请先选择表单!','提示信息');
			return;
		}
		var formKey=objDefId.val();
	
		var url= __ctx + '/platform/form/bpmFormDef/rightsDialog.ht?actDefId=' +actDefId+'&nodeId=' + nodeId +'&formKey=' + formKey;
	   if(nodeId.length>0){
			var oldformKey=$("input.oldFormKey",tdObj).val();
			if(oldformKey!=formKey)
				url+='&isAlert=true'
		}
		authorizeDialog(url,nodeId,formKey);
	}
	
	// 弹出子表授权脚本填写页面
	function subDataGrant(obj,nodeId){
		var btn=$(obj);
		var tdObj=btn.parent();
		var objDefId=$("input.formKey",tdObj);
		var formKey=objDefId.val();
		var tableId = $("input.tableId",tdObj).val();
		var url= __ctx + '/platform/form/bpmFormDef/subRightsDialog.ht?actDefId=' 
			+actDefId+'&nodeId=' + nodeId +'&formKey=' + formKey+'&tableId='+tableId;
		authorizeDialog(url,nodeId,formKey);
	}

	
	
	$(function(){
		
		$('#ckJumpType').on('click',function(){
			$('.jumpType').attr('checked',this.checked);
		});
		
		$('#ckHidenOption').on('click',function(){
			var checked=this.checked;
			$('.hideOption').attr('checked',checked);
		});
		
		$('#ckHidenPath').on('click',function(){
			var checked=this.checked;
			$('.hidePath').attr('checked',checked);
		});
		
		$('#existSubTable').each(function(){
			var obj = $(this);
			if(obj.val()==0){
				obj.siblings("a.grant").hide();
			}
		});
			
		
		//处理表单类型
		handFormType();
		//验证handler
		validHandler();
		
		
		//是否默认选中  隐藏执行路径
		var isNew = '${isNew}';
		if(isNew=='yes'){             //没有绑定表单时都要默认选中  隐藏执行路径
			$('#ckHidenPath').attr('checked',true);
			$('#ckHidenPath').click();
		}
		
		$("#defaultFormKey").combobox({size:21,required:'required'});
		$("#bpmFormKey").combobox({size:21,required:'required'});
		$(".formKeySelect").combobox({size:21,required:'required'});
	});
	
	function handFormType(){
		//业务表单
		$("select[name='bpmFormType']").change(function(){
			var value=$(this).val();
			var nodeId=$(this).parents(".formBox").find("#nodeId").val();
			if(value==-1){
				$("#formBox_"+nodeId).hide();
				$(".url_"+nodeId).hide();
			}
			else{
				if(value==0){
					$(".form_"+nodeId).show();
					$(".url_"+nodeId).hide();
				}
				else if(value==1){
					$(".form_"+nodeId).hide();
					$(".url_"+nodeId).show();
				}else{
					$(".form_"+nodeId).hide();
					$(".url_"+nodeId).hide();
				}
			}
		});
		//节点表单
		$("select[name='formType']").change(function(){
			var value=$(this).val();
			var nodeId=$(this).parents(".formBox").find("#nodeId").val();
			if(value==-1){
				$("#formBox_"+nodeId).hide();
			}
			else{
				$("#formBox_"+nodeId).show();
				if(value==0){
					$(".form_"+nodeId).show();
					$(".url_"+nodeId).hide();
				}
				else if(value==1){
					$(".form_"+nodeId).hide();
					$(".url_"+nodeId).show();
				}else{
					$(".form_"+nodeId).hide();
					$(".url_"+nodeId).hide();
				}
			}
		});
		
		$("select[name='globalFormType']").change(function(){
			var obj=$("select[name='bpmFormType']");
			var value=$(this).val();
			var nodeId=$(this).parents(".formBox").find("#nodeId").val();
			var objNodeId=obj.parents(".formBox").find("#nodeId").val();
			obj.val(value);
			if(value==-1){
				$("#formBox_"+nodeId).hide();
				$(".form_"+objNodeId).hide();
				$(".url_"+objNodeId).hide();
			}
			else{
				$("#formBox_"+nodeId).show();
				if(value==0){
					$(".form_"+nodeId).show();
					$(".url_"+nodeId).hide();
					$(".form_"+objNodeId).show();
					$(".url_"+objNodeId).hide();
				}
				else if(value==1){
					$(".form_"+nodeId).hide();
					$(".url_"+nodeId).show();
					$(".form_"+objNodeId).hide();
					$(".url_"+objNodeId).show();
				}else{
					$(".form_"+nodeId).hide();
					$(".url_"+nodeId).hide();
					$(".form_"+objNodeId).hide();
					$(".url_"+objNodeId).hide();
				}
			}
		});
	}
	
	function validHandler(){
		$("input.handler").blur(function(){
			var obj=$(this);
			var val=obj.val();
			if(val.trim()==""){
				return;
			}
			var params={handler:val};
			$.post("validHandler.ht",params,function(data){
				var json=eval("(" +data +")");
				if(json.result!='0'){
					$.ligerDialog.warn(json.msg,"提示信息",function(){
						obj.focus();
					});
				}
			});
		});
	}
	
	
	function toggleHelp(){
		var display=$("ul.help").css("display");
		if(display=="none"){
			$("ul.help").show();
			$.setCookie("help","show");
		}
		else{
			$("ul.help").hide();
			$.setCookie("help","hidden");
		}
	}
	
</script>
<style type="text/css">
	ul.help li { list-style:inside circle;list-style-type:decimal ;padding-left: 10px;font-weight: normal; } 
	.foldBox { border-top: 2px solid #488DF5;clear: both; display: inline-block;margin: 10px 5px 5px; position: relative; width: 99%;}
	.foldBox a.drop {cursor: pointer;text-decoration: none;}
	.foldBox .title {  background: none repeat scroll 0 0 #FFFFFF;color: #1673FF;font-size: 14px; font-weight: bold; left: 12px;padding-left: 10px;padding-right: 15px;position: absolute; top: -8px;}
	.foldBox .drop {background: none repeat scroll 0 0 #FFFFFF;color: #488DF5;font-weight: bold;padding: 0 10px;position: absolute;right: 12px;top: -12px;}
	.foldBox .content { margin: 12px 8px; padding: 10px;}
</style>

</head>
<body>

    <div class="panelBar" >
		<button class="listbutton" type="button" onclick="save()"> 保存</button>
	</div>	
	<div>			
		<form action="<%=basePath %>/pages/third/bpm/bpmNodeSet/save.action?defId=${bpmDefinition.defId}" method="post" id="dataForm">				
		    <input type="hidden" name="defId" value="${bpmDefinition.defId}"/>
		     <div class="foldBox formBox">
		     	 <input id="nodeId" type="hidden" value="globalFormType"/>
		               <div class="title" >
							全局表单
						</div>
		     			<div  class="content">
     						<table id="formBox_globalFormType" class="table-noborder">
									<tr>
										<th nowrap="nowrap">表单:</th>
										<td>
											<div name="globalForm" class="form_globalFormType">
												<input name="defaultFormSetId" value="${globalForm.setId}" type="hidden"/>
												<select name="defaultFormKey" id="defaultFormKey">
													<option value="-1">请选择...</option>
													<c:forEach items="${formList}" var="item" >
														<option value="${item.id}" <c:if test="${globalForm.formKey == item.id }">selected="selected"</c:if>>${item.formName}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
<!-- 													<tr> -->
<!-- 														<th nowrap="nowrap" >前置处理器:</th> -->
<!-- 														<td> -->
<!-- 															<input type="text" name="beforeHandlerGlobal" value="${globalForm.beforeHandler }" class="inputText handler" size="40"/> -->
											
<!-- 														</td> -->
<!-- 													</tr> -->
<!-- 													<tr> -->
<!-- 														<th nowrap="nowrap" >后置处理器:</th> -->
<!-- 														<td> -->
<!-- 															<input type="text" name="afterHandlerGlobal" value="${globalForm.afterHandler }" class="inputText handler" size="40"/> -->
											
<!-- 														</td> -->
<!-- 													</tr> -->
								</table>
		     			</div>
		     </div>
			<div class="foldBox formBox">
				<input type="hidden" id="nodeId" value="bpmForm"/>
					 <div class="title" >流程实例业务
					 	 <input name="bpmFormType" id="bpmFormType" type="hidden" value="-1"/>
					 </div>
					 <div class="content">
					 	<div  id="formBox_bpmForm"  name="bpmForm" class="form_bpmForm">
							&nbsp;&nbsp;表单:&nbsp;
							<input name="bpmFormSetId" value="${bpmForm.setId}" type="hidden"/>
							<select name="bpmFormKey" id="bpmFormKey">
								<option value="-1">请选择...</option>
								<c:forEach items="${formList}" var="item" >
									<option value="${item.id}" <c:if test="${bpmForm.formKey == item.id }">selected="selected"</c:if>>${item.formName}</option>
								</c:forEach>
							</select>
						</div>
					 </div>
			</div>
			<div class="foldBox">
			      <div class="title" >节点表单</div>
			       <div class="content">
			           <table cellpadding="1" cellspacing="1" class="tableClass" width="100%">
							<thead>
								<tr>
									<th width="20%" class="thClass">节点名</th>
									<th width="20%" class="thClass">
										<label><input type="checkbox" id="ckJumpType">跳转类型</label>
									</th>
									<th width="10%" class="thClass">
										<label title="隐藏表单意见"><input type="checkbox" id="ckHidenOption">隐藏意见</label>
									</th>
									<th width="10%" class="thClass">
										<label title="隐藏执行路径"><input type="checkbox" id="ckHidenPath">隐藏路径</label>
									</th>
									<th width="30%" class="thClass">表单</th>
								</tr>													
								</thead>
								<c:forEach items="${bpmNodeSetList}" var="item" varStatus="status">
								<tr  <c:if test="${status.index%2=='0' }">class="odd"</c:if>>
									<td class="tdClass">
										<input type="hidden" name="nodeId" value="${item.nodeId}"/>
										<input type="hidden" name="bpmNodeName" value="${item.nodeName}"/>${item.nodeName}
									</td>
									<td nowrap="nowrap" class="tdClass" style="text-align: left;">
									
										<ul>
											<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="1"  <c:if test="${fn:indexOf(item.jumpType,'1')!=-1}">checked="checked"</c:if> />正常跳转</li> 
											<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="2" <c:if test="${fn:indexOf(item.jumpType,'2')!=-1}">checked="checked"</c:if> />选择路径跳转</li> 
											<li><input type="checkbox" class="jumpType" name="jumpType_${item.nodeId}" value="3" <c:if test="${fn:indexOf(item.jumpType,'3')!=-1}">checked="checked"</c:if> />自由跳转</li>
											
										</ul>
									</td>
									<td class="tdClass">
										<input type="checkbox" class="hideOption" name="isHideOption_${item.nodeId}" value="1" <c:if test="${item.isHideOption==1}">checked="checked"</c:if> />
									</td>
									<td class="tdClass">
										<input type="checkbox" class="hidePath" name="isHidePath_${item.nodeId}" value="1" <c:if test="${item.isHidePath==1}">checked="checked"</c:if> />
									</td>
									<td class="tdClass">
										<input id="nodeId" type ="hidden" value="${item.nodeId}"/>
										<div id="formBox_${item.nodeId}" name="nodeForm">
										<table class="table-detail table-noborder" >
											<tr class="form_${item.nodeId}">
												<td align="center">
													<div>
														<input type="hidden" name="formType" value="0"/>
														<select name="formKey" class="formKeySelect">
															<option value="-1">请选择...</option>
															<c:forEach items="${formList}" var="fitem" >
																<option value="${fitem.id}" <c:if test="${item.formKey == fitem.id }">selected="selected"</c:if>>${fitem.formName}</option>
															</c:forEach>
														</select>
													</div>
												</td>
											</tr>
										</table>
										</div>
									</td>
								</tr>
								</c:forEach>
							</table>			       
			       </div>
			</div>    
		</form>
	</div>				
</body>
</html>


