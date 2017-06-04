/**
 * 任务跳转规则设置。
 * conf参数属性：
 * actDefId act流程定义ID
 * nodeId:流程节点ID
 * @param conf
 */
function ViewSuperExecutionFlowWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx+ "/platform/bpm/processRun/userImage.ht?processInstanceId="+conf.processInstanceId;
	
	var dialogWidth=800;
	var dialogHeight=600;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:true,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
}