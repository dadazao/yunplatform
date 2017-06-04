/**
 * 任务回退窗口
 */
function TaskBackWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/task/back.ht?taskId="+conf.taskId;

	var dialogWidth=400;
	var dialogHeight=200;
	
	$.extend(conf, {dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1});

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
}






