/**
 * 设置流程常用语对话框。
 * conf参数属性：
 * activitiId:节点ID
 * defId:流程定义ID
 * @param conf
 */
function TaskCommentWindow(conf)
{
	if(!conf) conf={};
	var url=__ctx + "/platform/bpm/taskComment/edit.ht?taskId=" + conf.taskId;

	var dialogWidth=800;
	var dialogHeight=450;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=0;status=0;scroll=1;center=1;resizable=1;" ;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
}