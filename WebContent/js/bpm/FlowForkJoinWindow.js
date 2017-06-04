/**
 * 流程节点分发及汇总设置
{actDefId:actDefId,nodeId:activitiId,activityName:activityName}
 * @param conf
 */
FlowForkJoinWindow=function(conf){
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/bpmDefinition/editForkJoin.ht?actDefId=" + conf.actDefId+"&nodeId=" + conf.nodeId;
	var dialogWidth=600;
	var dialogHeight=300;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth:"+conf.dialogWidth+"px;dialogHeight:"+conf.dialogHeight
		+"px;help:" + conf.help +";status:" + conf.status +";scroll:" + conf.scroll +";center:" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
};