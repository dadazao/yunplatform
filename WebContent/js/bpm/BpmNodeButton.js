if (typeof BpmNodeButton == 'undefined') {
	BpmNodeButton = {};
}

BpmNodeButton.startWindowOperator=
	[{operatortype:1, name:"启动流程"},{operatortype:6, name:"保存表单"},{operatortype:2, name:"流程示意图"},
			                 {operatortype:3, name:"打印"},{operatortype:4, name:"短信"},
			                 {operatortype:5, name:"邮件"}];

BpmNodeButton.nodeOperator=[{operatortype:1, name:"同意"},{operatortype:2, name:"反对"},
			                 {operatortype:3, name:"弃权"},{operatortype:4, name:"驳回"},
			                 {operatortype:5, name:"驳回到发起人"},{operatortype:6, name:"交办"},
			                 {operatortype:7, name:"补签"},{operatortype:8, name:"保存表单"},
			                 {operatortype:9, name:"流程示意图"},{operatortype:10, name:"打印"},
			                 {operatortype:11, name:"审批历史"},{operatortype:12, name:"短信"},
			                 {operatortype:13, name:"邮件"}];

/**
 * 添加选项。
 * @param objOperator
 * @param obj
 * @param type
 */
BpmNodeButton.addOption =function(objOperator,obj,type){
	
	if(type==obj.operatortype){
		objOperator.append("<option value='"+ obj.operatortype +"' selected>"+obj.name +"</option>");  
	}
	else{
		objOperator.append("<option value='"+ obj.operatortype +"'>"+obj.name +"</option>"); 
	}
};

/**
 * 获取操作类型下拉框。
 */
BpmNodeButton.getOperatorType=function (isStartForm,isSign){
	var objOperator=$("#operatortype");
	objOperator.empty();
	objOperator.append("<option value='0'>请选择操作类型</option>"); 
	var type=objOperator.attr("operatortype");
	
	if(isStartForm==1){
		for(var i=0;i<BpmNodeButton.startWindowOperator.length;i++){
			var obj=BpmNodeButton.startWindowOperator[i];
			BpmNodeButton.addOption(objOperator,obj,type);   
		}
	}
	else{
		for(var i=0;i<BpmNodeButton.nodeOperator.length;i++){
			var obj=BpmNodeButton.nodeOperator[i];
			if(isSign==0 && (obj.operatortype==3 || obj.operatortype==7)){
				continue;
			}
			BpmNodeButton.addOption(objOperator,obj,type);  
		}
	}
	
};

