var dialogDefaultSettings = {
	lock: true, 
	time: 3, 
	background: '#FFF', 
	opacity : 0,
	width: 120,
	height:60
};

ypArtDialog = function(msg, options, type, title) {
	var opts = $.extend(dialogDefaultSettings, options);
	var time = 2;
	var _title = '';
	if (title != undefined) {
		_title = title;
	} else {
		_title = '信息提示';
	}
	var _icon = '';
	var _buttons = [ {
		name : '确定',
		callback : function() {
			this.close();
			return false;
		}
	} ];
	if (type == 'info') {
		opts.time = time;
		_icon = 'succeed';
	} else if (type == 'error') {
		opts.time = undefined;
		_icon = 'error';
	} else if (type == 'warn') {
		opts.time = time;
		_icon = 'warning';
	} else if (type == 'confirm' || type == undefined) {
		_icon = 'confirm';
		opts.time = undefined;
		_buttons = [ {
			name : '确定',
			callback : options.okCall
		}, {
			name : '取消',
			callback : function() {
			}
		} ]
	} else if (type == 'open') {
		opts.time = undefined;
		_icon = 'open';
	}else {
		opts.time = time;
		_icon = 'succeed';
	}
	var dialog = art.dialog( {
		title : _title,
		lock : opts.lock,
		icon : _icon,
		time : opts.time,
		background : opts.background,
		opacity : opts.opacity,
		width : opts.width,
		height : opts.height,
		content : msg,
		button : _buttons
	});
	dialog.lock();
}
