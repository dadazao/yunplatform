/**
 * 设置操作按钮对话框。
 * conf参数属性：
 * actDefId： act流程定义ID
 * nodeId:节点ID
 * defId:流程定义ID
 * @param conf
 */
function BpmNodeButtonWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmNodeButton/getByNode.ht?buttonFlag=false&nodeId=" + conf.nodeId +"&defId=" + conf.defId;

	var dialogWidth=800;
	var dialogHeight=380;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:1,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if (conf.callback) {
		conf.callback.call(this,rtn);
	}
}