
/**
 * 设置网关跳转规则。
 * conf参数属性：
 * deployId:activiti流程定义ID
 * defId:流程定义ID
 * nodeId:流程节点ID
 * @param conf
 */
function ForkConditionWindow(conf){
	var url=__ctx + "/platform/bpm/bpmDefinition/setCondition.ht?deployId="+conf.deployId+"&defId=" + conf.defId +"&nodeId=" + conf.nodeId;
	var winArgs="dialogWidth=700px;dialogHeight=550px;help=0;status=0;scroll=1;center=1;resizable=1;";
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
}