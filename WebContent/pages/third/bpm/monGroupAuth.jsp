<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<%@include file="/commons/include/get.jsp" %>
<title>对【${monGroup.name}】授权</title>
<f:link href="form.css" ></f:link>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
<script type="text/javascript">
function chooseRole(obj){
	
	var tr=$(obj).parents("tr");
	var roleIds = $("#roleIds",tr).val();
	var roleNames = $("#roleNames",tr).val();
	
	var aryRole=[];
	if(roleIds!=""){
		var aryRoleId=roleIds.split(",");
		var aryRoleName=roleNames.split(",");
		for(var i=0;i<aryRoleId.length;i++){
			var obj={};
			obj.id=	aryRoleId[i];
			obj.name=aryRoleName[i];
			aryRole.push(obj);
		}
	}
	RoleDialog({isSingle:false,arguments:aryRole,callback:function(roleIds, rolenames,json){
		//setVal(obj,json);
		$("#roleIds",tr).val(roleIds);
		$("#roleNames",tr).val(rolenames);
	}});	
	
	
};

function clearRole(obj){
	var tr=$(obj).parents("tr");
	$("#roleIds",tr).val("");
	$("#roleNames",tr).val("");
}

function clearOrg(obj){
	var tr=$(obj).parents("tr");
	$("#orgIds",tr).val("");
	$("#orgNames",tr).val("");
}


function chooseOrg(obj){
	
	var tr=$(obj).parents("tr");
	var orgIds = $("#orgIds",tr).val();
	var orgNames = $("#orgNames",tr).val();
	
	var aryOrg=[];
	if(orgIds!=""){
		var aryOrgId=orgIds.split(",");
		var aryOrgName=orgNames.split(",");
		for(var i=0;i<aryOrgId.length;i++){
			var obj={};
			obj.id=	aryOrgId[i];
			obj.name=aryOrgName[i];
			aryOrg.push(obj);
		}
	}
	OrgDialog({isSingle:false,arguments:aryOrg,callback:function(orgIds, orgNames,json){
		//setVal(obj,json);
		$("#orgIds",tr).val(orgIds);
		$("#orgNames",tr).val(orgNames);
	}});	
	
	
};



$(function(){
	$("#dataFormSave").click(saveAuth);
	
});

function saveAuth(){
	var groupId=$("#groupId").val();
	var roleIds=$("#roleIds").val();
	var orgIds=$("#orgIds").val();
	var rtn=$("#monGroupForm").form().valid();
	if(!rtn){
		return;
	}
	var url=__ctx +"/platform/bpm/monGroup/saveAuth.ht";
	var params={groupId:groupId,orgIds:orgIds,roleIds:roleIds};
	$.post(url,params,function(data){
		if(data.result==1){
			$.ligerDialog.success($lang.save.success,$lang.tip.msg,function(){
				window.close();
			});
		}
		else{
			$.ligerDialog.warn(data.message,$lang.tip.msg);
		}
	},"json");
}



</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
			 	对【${monGroup.name}】授权
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link close" href="#" onclick="window.close();"><span></span>关闭</a></div>
				</div>
			</div>
		</div>
		<div class="panel-body">
				<form id="monGroupForm" method="post" action="save.ht">
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th width="20%">选择查看数据角色:<span class="required">*</span> </th>
								<td >
									<input type="hidden" id="roleIds" name="roleIds" value="${roleIds}" />
									<textarea id="roleNames" name="roleNames"  readonly="readonly" rows="5" validate="{required:true}" cols="60" style="width: 300px;" class="inputText">${roleNames}</textarea>
									<br>
									<a class="link roles"  href="#" onclick="chooseRole(this);">选择</a>
									<a class="link reset"  onclick="clearRole(this)">清空</a>
								</td>
							</tr>
							<tr>
								<th width="20%">选择查看数据组织:<span class="required">*</span> </th>
								<td >
									<input type="hidden" id="orgIds" name="orgIds" value="${orgIds}" />
									<textarea id="orgNames" name="orgNames" readonly="readonly" rows="5" cols="60" validate="{required:true}" style="width: 300px;" class="inputText">${orgNames}</textarea>
									<br>
									<a class="link orgs"  href="#" onclick="chooseOrg(this);">选择</a>
									<a class="link reset" onclick="clearOrg(this)">清空</a>
								</td>
							</tr>
						</table>
						<input type="hidden" id="returnUrl" value="${returnUrl}" />
						<input type="hidden" id="groupId" name="id" value="${monGroup.id}" />
					
				</form>
		</div>
</div>
	
</body>
</html>


