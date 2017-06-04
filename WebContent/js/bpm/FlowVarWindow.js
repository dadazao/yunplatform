/**
 * 流程选择变量窗口。
 * conf:参数如下：
 * defId：流程定义ID
 * dialogWidth：窗口宽度，默认值500
 * dialogWidth：窗口宽度，默认值300
 * callback：回调函数
 * 回调参数如下：
 * key:参数key
 * name:参数名称
 * 使用方法如下：
 * 
 * FlowVarWindow({defId:defId,callback:function(varKey,varName){
 *		//回调函数处理
 *	}});
 * @param conf
 */
function FlowVarWindow(conf)
{
	
	if(!conf) conf={};
	var url;
	if(conf.defId!=null){
		url=__ctx + "/platform/bpm/bpmDefVar/getByDeployNode.ht?defId="+conf.defId;
	}else{
		url=__ctx + "/platform/bpm/bpmDefVar/getByDeployNode.ht?deployId="+conf.deployId+"&nodeId=" + conf.nodeId;
	}
	var dialogWidth=500;
	var dialogHeight=300;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn!=undefined){
		if(conf.callback){
			conf.callback.call(this,rtn.key,rtn.name);
		}
	}

	
}