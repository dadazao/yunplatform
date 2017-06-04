/**
 * 流程节点催办时间设置窗口。
 * conf:参数如下：
 * actDefId：流程定义ID
 * nodeId：流程节点Id
 * dialogWidth：窗口宽度，默认值650
 * dialogWidth：窗口宽度，默认值400
 * @param conf
 */

function FlowReminderWindow(conf)
{
//	var url=__ctx + "/platform/bpm/taskReminder/edit.ht?actDefId="+conf.actDefId+"&nodeId=" + conf.nodeId;
//	var w = 1024;
//	var h = 550;
//	var x = screen.width/2 - w/2;
//    var y = screen.height/2 - h/2-10;
//	var winArgs="width="+w+",height="+h+",left="+x+",top="+y+",toolbar=no,menubar=no,resizable=no,location=no,status=no";
//	var rtn=window.open(url,"",winArgs);
	
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/taskReminder/edit.ht?actDefId="+conf.actDefId+"&nodeId=" + conf.nodeId;
	var dialogWidth=900;
	var dialogHeight=600;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:1,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if (conf.callback) {
		conf.callback.call(this,rtn);
	}
}