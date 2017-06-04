
/**
 * 任务会签规则设置。
 * conf:参数如下：
 * deployId:流程定义ID
 * actDefId：act流程节点Id
 * nodeId：流程节点Id
 * dialogWidth：窗口宽度，默认值500
 * dialogWidth：窗口宽度，默认值300
 * @param conf
 */

function FlowRuleWindow(conf)
{	
	var url=__ctx + "/platform/bpm/bpmNodeRule/edit.ht?deployId="+conf.deployId+"&actDefId=" + conf.actDefId +"&nodeId=" + conf.nodeId+"&nodeName=" +encodeURIComponent(conf.nodeName);
	var winArgs="dialogWidth=800px;dialogHeight=600px;help=0;status=0;scroll=1;center=0;resizable=1;";
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if (conf.callback) {
		conf.callback.call(this,rtn);
	}
}