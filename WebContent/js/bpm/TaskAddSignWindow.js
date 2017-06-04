//任务补签窗口  依赖于LigerWindow,UserDialog
function TaskAddSignWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx+'/platform/bpm/task/toAddSign.ht?taskId=' + conf.taskId;
	var winArgs="dialogWidth=500px;dialogHeight=200px;help=0;status=0;scroll=0;center=1";
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this);
		}
	}
};



