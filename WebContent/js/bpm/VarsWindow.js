
function VarsWindow(conf)
{
		   	if(!conf) conf={};
		   	var url=__ctx + "/platform/bpm/bpmDefVar/edit.ht?defId="+conf.defId +"&varId=" + conf.varId;
		   
		   	var dialogWidth=500;
			var dialogHeight=300;
			conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
			var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
				+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,"",winArgs);
			if(conf){
				location.reload();
			}
}
