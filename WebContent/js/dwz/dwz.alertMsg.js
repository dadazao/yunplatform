/**
 * @author ZhangHuihua@msn.com
 */
$.setRegional("alertMsg", {
	title : {
		error : "Error",
		info : "Information",
		warn : "Warning",
		correct : "Successful",
		confirm : "Confirmation"
	},
	butMsg : {
		ok : "OK",
		yes : "Yes",
		no : "No",
		cancel : "Cancel"
	}
});
var alertMsg = {
	_boxId : "#alertMsgBox",
	_bgId : "#alertBackground",
	_closeTimer : null,

	_types : {
		error : "error",
		info : "info",
		warn : "warn",
		correct : "correct",
		confirm : "confirm",
		open: "open"
	},

	_getTitle : function(key) {
		return $.regional.alertMsg.title[key];
	},

	/**
	 * 
	 * @param {Object} type
	 * @param {Object} msg
	 * @param {Object} buttons [button1, button2]
	 */
	_open : function(type, msg, buttons, title) {
		$(this._boxId).remove();
		var butsHtml = "";
		if (buttons) {
			for ( var i = 0; i < buttons.length; i++) {
				var sRel = buttons[i].call ? "callback" : "";
				butsHtml += DWZ.frag["alertButFrag"].replace("#butMsg#",
						buttons[i].name).replace("#callback#", sRel);
			}
		}
		var _title = "";
		if (title != undefined) {
			_title = title;
		} else {
			_title = this._getTitle(type);
		}
		var boxHtml = DWZ.frag["alertBoxFrag"].replace("#type#", type).replace(
				"#title#", _title).replace("#message#", msg).replace(
				"#butFragment#", butsHtml);
		$(boxHtml).appendTo("body").css(
				{
					top : ($(window).height() / 2 - $(this._boxId).height())
							+ $(window).scrollTop() + "px",
					left : $(window).width() / 2 + $(window).scrollLeft()
							+ "px"
				});

		if (this._closeTimer) {
			clearTimeout(this._closeTimer);
			this._closeTimer = null;
		}
		if (this._types.info == type || this._types.correct == type) {
			this._closeTimer = setTimeout(function() {
				alertMsg.close()
			}, 3500);
		} else {
			$(this._bgId).show();
		}
		var jCallButs = $(this._boxId).find("[rel=callback]");
		for ( var i = 0; i < buttons.length; i++) {
			if (buttons[i].call)
				jCallButs.eq(i).click(buttons[i].call);
		}
	},

	close : function() {
		$(this._boxId).animate(
				{
					top : ($(window).height() / 2 - $(this._boxId).height())
							+ $(window).scrollTop() + "px",
					left : $(window).width() / 2 + $(window).scrollLeft()
							+ "px"
				}, 1, function() {
					$(this).remove();
				});
		$(this._bgId).hide();
	},
	error : function(msg, options) {
		this._alert(this._types.error, msg, options);
	},
	error : function(msg, options, title) {
		this._alert(this._types.error, msg, options);
	},
	info : function(msg, options) {
		this._alert(this._types.info, msg, options);
	},
	warn : function(msg, options) {
		this._alert(this._types.warn, msg, options);
	},
	correct : function(msg, options) {
		this._alert(this._types.correct, msg, options);
	},
	correct : function(msg, options, title) {
		this._alert(this._types.correct, msg, options, title);
	},
	open : function(msg, options, title) {
		this._alert(this._types.open, msg, options, title);
	},
	_alert : function(type, msg, options) {
		var op = {
			okName : $.regional.alertMsg.butMsg.ok,
			okCall : null
		};
		$.extend(op, options);
		var buttons = [ {
			name : op.okName,
			call : op.okCall
		} ];
		//this._open(type, msg, buttons);
		ypArtDialog(msg, options, type);
	},
	_alert : function(type, msg, options, title) {
		var op = {
			okName : $.regional.alertMsg.butMsg.ok,
			okCall : null
		};
		$.extend(op, options);
		var buttons = [ {
			name : op.okName,
			call : op.okCall
		} ];
		ypArtDialog(msg, options, type, title);
		//this._open(type, msg, buttons, title);
},

confirm : function(msg, options) {
	ypArtDialog(msg, options);
}
};