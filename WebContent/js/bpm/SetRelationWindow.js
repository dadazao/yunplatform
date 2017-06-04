function SetRelationWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/form/bpmFormTable/setRelation.ht?tableId="+conf.tableId+"&dsName=" + conf.dsName;
	
	var dialogWidth=600;
	var dialogHeight=350;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1,reload:true},conf);
	

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.reload){
		location.reload();
	}
}