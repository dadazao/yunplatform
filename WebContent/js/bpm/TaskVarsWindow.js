function TaskVarsWindow(conf){
	if(!conf)conf={};
	var taskId=conf.task;
	var varsValue=conf.varsValue;
	var id=conf.id;
	var url="update.ht?taskId="+taskId;
	
	var dialogWidth=400;
	var dialogHeight=150;
	$.extend(conf,{dialogWidth:dialogWidth,dialogHeight:dialogHeight,help:0,status:0,scroll:0,center:1});
	var obj={taskId:conf.taskId,varsValue:conf.varsValue,id:conf.id};
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
	+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,obj,winArgs);
	if(conf){
		location.reload();
	}
}

