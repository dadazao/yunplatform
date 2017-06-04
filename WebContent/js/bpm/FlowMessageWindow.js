/**
 * 消息参数设置。
 * conf参数属性：
 * actDefId： act流程定义ID
 * nodeId:流程节点ID
 * @param conf
 */
function FlowMessageWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmNodeMessage/edit.ht?actDefId=" + conf.actDefId +"&nodeId=" + conf.nodeId;
	var dialogWidth=780;
	var dialogHeight=580;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:1,center:1},conf);
	

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);


}