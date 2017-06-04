/**
 * 设置流程事件脚本对话框。
 * conf参数属性：
 * actDefId： act流程定义ID
 * activitiId:节点ID
 * defId:流程定义ID
 * @param conf
 */
function FlowEventWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmNodeScript/edit.ht?type="+conf.type+"&actDefId=" + conf.actDefId+"&nodeId=" + conf.activitiId +"&defId=" + conf.defId;

	var dialogWidth=800;
	var dialogHeight=380;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn = window.showModalDialog(url,"",winArgs);
	if (conf.callback) {
		conf.callback.call(this,rtn);
	}
}