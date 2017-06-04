/**
 * 用户选择器 .
 * ns.common.userDialog({callback:function(userIds,fullnames,emails,mobiles){},selectUsers:[{id:'',name:''}]})
 */
ns.common.userDialog = function(options){
	var dialogWidth=800;
	var dialogHeight=500;
	
	options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true},options);
	url=__basePath + "/pages/system/user/dialog.action";
	
	//重新选择的时候，展现上次数据
	var selectUsers="";
	if(options.selectUserIds && options.selectFullNames){
		selectUsers={
			selectUserIds:options.selectUserIds ,
			selectUserNames:options.selectFullNames
		}
	}	
	
	$.pdialog.open(url,"selectUserDialog","选择用户",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable,param:{userIds:'',fullNames:'',emails:'',mobiles:''},close:function(param){
			if(options.callback){
				options.callback.call(this,param.userIds,param.fullNames,param.emails,param.mobiles);
			}
			return true;
		}
	});
}

/**
 * 组织选择器
 */
ns.common.orgDialog = function(options){
	var dialogWidth=600;
	var dialogHeight=500;
	
	options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true},options);
	url=__basePath + "/pages/system/org/sysOrgDialog.jsp";
	
	//重新选择的时候，展现上次数据
	var selectOrgs="";
	if(options.selectOrgIds && options.selectOrgNames){
		selectOrgs={
			selectOrgIds:options.selectOrgIds,
			selectOrgNames:options.selectOrgNames
		}
	}
	$.pdialog.open(url,"selectOrgDialog","选择机构",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable,
		param:{orgIds:'',orgNames:''},
		close:function(param){
			if(options.callback){
				options.callback.call(this,param.orgIds,param.orgNames);
			}
			return true;
		}
	});
}

/**
 * 角色选择器 
 */
ns.common.roleDialog = function(options){
	var dialogWidth=600;
	var dialogHeight=500;
	options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true},options);
	$.pdialog.open(__basePath + "/pages/system/role/dialog.action","selectRoleDialog","选择角色",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable,param:{roleIds:'',roleNames:''},close:function(param){
		if(options.callback){
			options.callback.call(this,param.roleIds,param.roleNames);
		}
		return true;
	}});
}

/**
 * 岗位选择器
 */
ns.common.positionDialog = function(options){
	var dialogWidth=600;
	var dialogHeight=500;
	options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true},options);
	$.pdialog.open(__basePath + "/pages/system/position/sysPositionDialog.jsp","selectPositionDialog","选择岗位",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable,param:{positionIds:'',positionNames:''},close:function(param){
		if(options.callback){
			options.callback.call(this,param.positionIds,param.positionNames);
		}
		return true;
	}});
}

/**
 * 表单选择器 
 */
ns.common.formDialog = function(options){
	var dialogWidth=800;
	var dialogHeight=500;
	options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true},options);
	$.pdialog.open(__basePath + "/pages/resource/form/dialog.action","selectRoleDialog","选择表单",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable,param:{formIds:'',formNames:''},close:function(param){
		if(options.callback){
			options.callback.call(this,param.formIds,param.formNames);
		}
		return true;
	}});
}