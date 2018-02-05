function ScriptDialog(d) {
	if (!d) {
		d = {};
	}
	var c = __basePath + "/platform/expression/expression.ht";
	var f = 800;
	var b = 600;
	d = $.extend({}, {
		dialogWidth : f,
		dialogHeight : b,
		help : 0,
		status : 0,
		scroll : 0,
		center : 1
	}, d);
	var a = "dialogWidth=" + d.dialogWidth + "px;dialogHeight="
			+ d.dialogHeight + "px;help=" + d.help + ";status=" + d.status
			+ ";scroll=" + d.scroll + ";center=" + d.center;
	c = c.getNewUrl();
	var e = window.showModalDialog(c, "", a);
	if (e != undefined) {
		if (d.callback) {
			d.callback.call(this, e);
		}
	}
}
function setScriptCondition(d) {
	if (!d) {
		d = {};
	}
	var c = __basePath + "/platform/expression/setScriptCondition.ht";
	var f = 800;
	var b = 600;
	d = $.extend({}, {
		dialogWidth : f,
		dialogHeight : b,
		help : 0,
		status : 0,
		scroll : 1,
		center : 1
	}, d);
	var a = "dialogWidth=" + d.dialogWidth + "px;dialogHeight="
			+ d.dialogHeight + "px;help=" + d.help + ";status=" + d.status
			+ ";scroll=" + d.scroll + ";center=" + d.center;
	c = c.getNewUrl();
	var e = window.showModalDialog(c, d.defId, a);
	if (e != undefined) {
		if (d.callback) {
			d.callback.call(this, e);
		}
	}
}