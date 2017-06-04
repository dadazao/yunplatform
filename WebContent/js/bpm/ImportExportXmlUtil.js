/**
 * 该文件包含xml导入导出的操作方法
 */
if (typeof ImportExportXml == 'undefined') {
	ImportExportXml = {};
}


/**
 *  弹出的模态窗口
 * @author zxh
 * @param {Object} conf 配置信息
 * <pre>
 * 	{
 * 		url：（必填）窗口跳转地址,
 * 		width:（可选，默认550）窗口宽度，
 * 		height：（可选，默认250）窗口长度，
 * 		returnUrl：（可选）返回URL
 * 	}
 * </pre>
 */
ImportExportXml.showModalDialog = function(conf){
	if(!conf) conf={};
	var url= conf.url;
	//
	var dialogWidth= conf.width?conf.width:550;
	var dialogHeight=conf.height?conf.height:250;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn){
		if(conf.returnUrl)
			window.location.href = conf.returnUrl;
		else
			window.location.reload(true);
	}
	
};

/**
 * 根据复选框的class获取选中值，使用逗号分隔。
 * 
 * @param class
 * @returns {String}
 */
ImportExportXml.getChkValue = function(cla) {
	var str = "";
	$('input[type="checkbox"][class=' + cla + ']:checked').each(function() {
			str += $(this).val() + ",";
	});
	if (str != "")
		str = str.substring(0, str.length - 1);
	return str;
};
