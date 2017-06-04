if (typeof FlowUtil == 'undefined') {
	FlowUtil = {};
}

/**
 * 启动流程。
 * @param defId 流程定义ID。
 */
FlowUtil.startFlow=function(definitionId,actDefId){
	$.ajax({
  		type:'POST',
  		dataType:'json',
  		url:__basePath +"/pages/third/bpm/bpmDefinition/getCanDirectStart.action?defId=" + definitionId,
  		success:function(data){
			if(data.statusCode=='200') {
				alertMsg.confirm("需要启动流程吗?", {okCall:function(){
					var flowUrl= __basePath +"/pages/third/bpm/task/startFlow.action";
					$.ajax({
				  		type:'POST',
				  		dataType:'json',
				  		url:flowUrl,
				  		data:{actDefId:actDefId},
				  		success:function(json){
							if(json.statusCode=='200'){//成功
								alertMsg.correct(json.message);
							}
							else{
								alertMsg.error(json.message);
							}
						}
					});
				}});
			}else{
				$.pdialog.open(__basePath +"/pages/third/bpm/task/startFlowForm.action?definitionId="+definitionId,"startFlowFormDialog","",{width:950,height:650,mask:true,resizable:true});
			}
  		}
  	});
	
	/*
	var url= __basePath +"/pages/third/bpm/bpmDefinition/getCanDirectStart.action";
	var params={defId:defId};
	$.post(url,params,function(data){
		if(data){
			var callBack=function(rtn){
				if(!rtn) return;
				var flowUrl= __basePath +"/pages/third/bpm/task/startFlow.action";
				var parameters={actDefId:actDefId};
				$.post(flowUrl,parameters,function(responseText){
					var obj=new ResultMessage(responseText);
					if(obj.isSuccess()){//成功
						alertMsg.correct("启动流程成功!");
					}
					else{
						alertMsg.error("启动流程失败!");
					}
				});
			};
			alertMsg.confirm("需要启动流程吗?", {okCall:function(){ajaxTodo(result,callBack);}});
		}else{
			var url=__ctx +"/platform/bpm/task/startFlowForm.action?defId="+defId;
			jQuery.openFullWindow(url);
		}
	});
	*/
};

