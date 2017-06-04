/**
 * 功能:流程运行对话框
 * 
 * 参数：
 * conf:配置为一个JSON
 * 
 * dialogWidth:对话框的宽度。
 * dialogHeight：对话框高度。
 * 
 * actDefId:流程定义ID。
 * 
 */
function BpmImageDialog(conf)
{
	if(!conf) conf={};	
	var url=__ctx + '/platform/bpm/bpmDefinition/flowImg.ht?actDefId=' + conf.actDefId;
	var winArgs="height=600,width=800,status=no,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes";
	url=url.getNewUrl();
	window.open(url,null,winArgs);
}