/**
 * 设置流程常用语对话框。
 * conf参数属性：
 * activitiId:节点ID
 * defId:流程定义ID
 * @param conf
 */
function FlowApprovalItemWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/taskApprovalItems/edit.ht?nodeId=" + conf.activitiId +"&defId=" + conf.defId
		+"&actDefId="+conf.actDefId;

	var dialogWidth=600;
	var dialogHeight=300;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if (conf.callback) {
		conf.callback.call(this,rtn);
	}
}