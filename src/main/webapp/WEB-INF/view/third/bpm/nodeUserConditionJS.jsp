<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
 // 移动行
 function move(tableId, direct,obj){
	var objTr=$("#"+tableId+">tbody>tr");
	if(objTr.length==0) return;

	var curObj = $(obj).parents('tr:first');
	if(direct=='up'){
		var prevObj=curObj.prev();
		if(prevObj.length>0){			
			alertConditionSn(curObj,prevObj,direct);		
		}
	}
	else{
		var nextObj=curObj.next();
		if(nextObj.length>0){
			alertConditionSn(curObj,nextObj,direct)	;
		}
	}
};

function alertConditionSn(curObj,otherObj,direct){
	var currentId=$('input[name="conditionId"]',curObj).val();
	var otherId=$('input[name="conditionId"]',otherObj).val();
	var currentSn=$('input[name="sn"]',curObj).val();
	var otherSn=$('input[name="sn"]',otherObj).val();
	if(currentId.length>0 && otherId.length>0){
		$.ajax( {
			type : 'POST',
			url : __basePath+'/pages/third/bpm/bpmUserCondition/updateSn.action',
			data: {'currentId':currentId,'otherId':otherId,'currentSn':otherSn,'otherSn':currentSn},
			dataType: 'json',
			success : function(json) {
				if(json.statusCode == 200){
					if(direct=='up'){
						curObj.insertBefore(otherObj);	
					}else{
						curObj.insertAfter(otherObj);
					}
				}else{
					$.alserMsg.warn(json.message);
				}
			}
		});
	}
}


function delRows(tableId){
	var tableObj=$('#'+tableId);
	 var deletes=$('input[type=checkbox]:checked',tableObj);
	if(deletes.length==0){
		alertMsg.info('请选择要删除的行!');
		return ;
	}
	for(var i=deletes.length-1;i>=0;i--){
		
		var conditionId=$(deletes[i]).next('input[name="conditionId"]').val();
		if(conditionId!='' && conditionId!=undefined){
			$.ajax( {
				type : 'POST',
				url : __basePath + '/pages/third/bpm/bpmUserCondition/del.action',
				data: {'id':conditionId},
				dataType: 'json',
				success : function(json) {
					if(json.statusCode == 200) {
						alertMsg.correct(json.message);
					}
				}
			});
		}
		$(deletes[i]).parent().parent().remove();
	}
}

function dynamicSelect(obj){
	var assignType=$(obj).parent().prev('td').children('[name="assignType"]').val();
	var callback=function(ids,names){
		var $varIds=$(obj).siblings(":input[name='variableIds']");
		if($varIds.get(0).tagName.toLowerCase()=="select"){
			//防止值设置不上
			$varIds.append("<option value='"+ids+"' selected>"+names+"</option>");
		}else{
			$varIds.val(ids);
		}
		 $(obj).siblings(":input[name='variableNames']").val(names);
		 $(obj).siblings(":input[name='dynamicSelector']").val(names);
	};
	
	if(assignType==57){
		RoleDialog({isSingle:true,callback:callback});
	}
	else if(assignType==58){
		PosDialog({isSingle:true,callback:callback});
	}
}

function selectCmp(obj){
	var cmpIds=$(obj).siblings("input[name='cmpIds']");
	var cmpNames=$(obj).siblings("textarea[name='cmpNames']");
	var assignType=$(obj).parent().prev('td').children('[name="assignType"]').val();
	var nodeUserId=$(obj).parent().prev('td').prev('td').children('[name="nodeUserId"]').val();
	var nodeId=$(obj).parent().prev('td').prev('td').children('[name="nodeId"]').val();
	
	var callback=function(ids,names){				
		cmpIds.val(ids);
		cmpNames.val(names);
	};
	if(assignType==1){
		 ns.common.userDialog({callback:callback});
	}
	else if(assignType==2){
		RoleDialog({callback:callback});
	}
	else if(assignType==3 || assignType==4){
		OrgDialog({callback:callback});	
	}
	else if(assignType==5){
		PosDialog({callback:callback});
	}
	else if(assignType==6){
		UplowDialog({callback:callback});
	}
	else if(assignType==7){
		UserParamDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
	}
	else if(assignType==8){
		OrgParamDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
	}else if(assignType==10){
		showOtherNodeDlg({callback:callback,nodeId:nodeId});
	}
	else if(assignType==12){
		showScript(cmpNames);
	}
	else if(assignType>=20 && assignType<=25){
		ns.common.userDialog({callback:callback});
	}
	else if(assignType==26){
		OrgDialog({isSingle:true,callback:callback});
	}
	//新加的
	else if(assignType==27 ||assignType==29  ||assignType==31 ||assignType==33 ||assignType==35 ||assignType==37 ||assignType==39 ||assignType==42 ||assignType==45 || assignType==48 || assignType==51 || assignType==54){
		RoleDialog({callback:callback});
	}
	else if(assignType==28 ||assignType==30  ||assignType==32 ||assignType==34 ||assignType==36 ||assignType==38 ||assignType==40 ||assignType==43 ||assignType==46 || assignType==49 || assignType==52 || assignType==55){
		PosDialog({callback:callback});
	}
	else if(assignType==57||assignType==58){
		OrgDialog({isSingle:true,callback:callback});
	}
	
}

function assignTypeChange(obj){
	var cmpIds=$(obj).parent().next('td').children('input[name="cmpIds"]');
	var cmpdNames=$(obj).parent().next('td').children('textarea[name="cmpNames"]');
	var selField=$(obj).parent().next('td').find(':input[name="variableIds"]');
	var varNames=$(obj).parent().next('td').find(':input[name="variableNames"]');
	var selButtons=$(obj).parent().next('td').children('.listbutton');
	var addiParam=$(obj).parent().next('td').find("input[name='additionalParam']");
	var dynamicSelector=$(obj).parent().next('td').find(":input[name='dynamicSelector']");
	var dynamicButton=$(obj).parent().next('td').find(".dynamicButton");
	
	if(obj.value==0||obj.value==11||obj.value==9 || obj.value==13 ||obj.value==14 || obj.value==15){
		selButtons.hide();
		cmpdNames.hide();
		selField.hide();
		addiParam.hide();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//只选一个表单变量
	else if((obj.value>=16&&obj.value<=19) ||obj.value==41  ||obj.value==44 ||obj.value==47){
		selButtons.hide();
		cmpdNames.hide();
		selField.show();
		addiParam.hide();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//不需要做任何选择的
	else if((obj.value>=20&&obj.value<=22)){
		selButtons.hide();
		cmpdNames.hide();
		selField.hide();
		addiParam.hide();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//需要填写一个部门名称的
	else if(obj.value>=23&&obj.value<=25){
		selButtons.hide();
		cmpdNames.hide();
		cmpdNames.attr("readonly",true);
		selField.hide();
		addiParam.show();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//需要填写部门，选择角色两项
	else if((obj.value>=33&&obj.value<=38)){
		selButtons.show();
		cmpdNames.show();
		cmpdNames.attr("readonly",true);
		selField.hide();
		addiParam.show();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//需要选择表单变量,填写部门、选择角色三项的
	else if(obj.value==48||obj.value==49||obj.value==51||obj.value==52||obj.value==54||obj.value==55){
		selButtons.show();
		cmpdNames.show();
		cmpdNames.attr("readonly",true);
		selField.show();
		addiParam.show();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//选一个表单变量，选一个角色，部门等的
	else if(obj.value==50||obj.value==53||obj.value==56){
		selButtons.hide();
		cmpdNames.hide();
		cmpdNames.attr("readonly",true);
		selField.show();
		addiParam.show();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	//需要选择一个表单变量，一个角色等的
	else if(obj.value==39||obj.value==40||obj.value==42||obj.value==43||obj.value==45||obj.value==46){
		selButtons.show();
		cmpdNames.show();
		cmpdNames.attr("readonly",true);
		selField.show();
		addiParam.hide();
		varNames.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	else if(obj.value==57||obj.value==58){
		selButtons.show();
		cmpdNames.show();
		cmpdNames.attr("readonly",true);
		selField.hide();
		addiParam.hide();
		dynamicSelector.attr("readonly",true);
		dynamicSelector.show();
		dynamicButton.show();
	}
	//剩下的都只选择一岗位，组织，角色等选项
	else{
		selButtons.show();
		cmpdNames.show();
		cmpdNames.attr("readonly",true);
		selField.hide();
		addiParam.hide();
		dynamicSelector.hide();
		dynamicButton.hide();
	}
	
	cmpIds.val('');
	cmpdNames.val('');
	addiParam.val("");
	selField.val("");
	dynamicSelector.val("");
}


function selectScript(){
	var objConditionCode=$("#txtScriptData")[0];
	ScriptDialog({callback:function(script){
		jQuery.insertText(objConditionCode,script);
	}});
}

var win;
function showScript(obj){
	$("#txtScriptData").val(obj.val());
	
	var divObj=$("#divScriptData");
	win= $.ligerDialog.open({ target:divObj , height: 350,width:500, modal :true,
		buttons: [ { text: '确定', onclick: function (item, dialog) { 
				obj.val($("#txtScriptData").val());
				dialog.hide();
			} 
		}, 
		{ text: '取消', onclick: function (item, dialog) { dialog.hide(); } } ] }); 
	
}

function changeVar(obj){
	var val=$(obj).val();
	var objScript=$("#txtScriptData")[0];
	jQuery.insertText(objScript,val);
}

//显示其他节点的对话框
function showOtherNodeDlg(options){
	var dialogWidth=800;
	var dialogHeight=500;
	options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true},options);
	var url=__basePath + "/pages/third/bpm/bpmDefinition/taskNodes.action?actDefId=${bpmDefinition.actDefId}&nodeId="+conf.nodeId;
	$.pdialog.open(url,"选择用户",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable,param:{nodeId:'',nodeName:''},close:function(param){
		if(options.callback){
			options.callback.call(this,param.nodeId,param.nodeName);
		}
		return true;
	}});
}
function conditionDialog(tableId,edit)
{
	var tableObj=$('#'+tableId);
	var defId=$('input[type="hidden"][name="definitionId"]',tableObj).val();
	var nodeId=$('input[type="hidden"][name="nodeId"]',tableObj).val();
	var conditionId;
	
	if(edit){
		 conditionId=$('input[type="checkbox"]:checked:first',tableObj).next('input[name="conditionId"]').val();
	}
	
	var url;
	if(conditionId){
		url=__basePath + '/pages/third/bpm/bpmDefinition/conditionEdit.action?definitionId='+defId+'&nodeTag='+nodeId+'&conditionId='+conditionId;
	}	
	else if(edit && !conditionId){
		$.ligerMessageBox.warn("请先选择要修改的规则条件！");
		return;
	}else{
		url=__basePath + '/pages/third/bpm/bpmDefinition/conditionEdit.action?definitionId='+defId+'&nodeTag='+nodeId;
	}
	
	$.pdialog.open(url,"conditionEditDialog","流程节点人员设置",{width:800,height:500,mask:true,resizable:true});
}

function repairOldData(tableId){
	var conditionName='系统修复规则';
	var tableObj=$('#'+tableId);
	var actDefId=0;
	var nodeId=$('input[name="nodeId"]',tableObj).val();
	var setId=$('input[name="setId"]',tableObj).val();
	var formDefId=0;
	var conditionArray="[]";
	var conditionShow="数据已修复,请点击编辑该规则";
	var conditionId=0;
	var postData={
			'setId':setId,
			'actDefId':actDefId,
			'nodeId':nodeId,
			'formDefId':formDefId,
			'conditionShow':conditionShow,
			'condition':conditionArray,
			'conditionName':conditionName,
			'conditionId':conditionId
	};
	$.post(__basePath+"/platform/bpm/bpmUserCondition/save.ht",postData,function(response){
		var resultJson=eval('('+response+')');
		if(resultJson.result==1){		
			if(!conditionId){
				conditionId=resultJson.message;
			}
			$.post(__basePath+"/platform/bpm/bpmDefinition/repairData.ht", {'conditionId':conditionId,'setId':setId},function(data){
				var result=eval('('+data+')');
				if(result.result==1){
					window.location.reload(true);
				}else{
					$.ligerMessageBox.warn(resultJson.message);
				}					
			});		
		}else{
			$.ligerMessageBox.warn(resultJson.message);
		}
	});
}
function changeCheck(obj){
	var tableObj=$(obj).parents('table');
	$('input[type="checkbox"]:checked',tableObj).attr('checked',false);
	$(obj).attr('checked',true);
}
$(function(){

	$("div.group > a.link.update").unbind('click');

	$("select[name='variableIds']").live("change",function(){
		var _this=this;
		var assignType=$(_this).parent().parent().find(':input[name="assignType"]').val();
		//兼容老版本
		if(assignType>=16&&assignType<=19){
			$(_this).parent().find('input[name="cmpIds"]').val(_this.value);
			$(_this).parent().find('textarea[name="cmpNames"]').val($("option:selected",_this).text());
			$(_this).parent().find(':input[name="variableNames"]').val($("option:selected",_this).text());
		}else{
			$(_this).parent().find(':input[name="variableNames"]').val($("option:selected",_this).text());
		}
	});
	
});
</script>