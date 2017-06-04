DefVarTree = function(divId, conf) {
	{
		this.tree = null;
		this.currentNode = null;
		this.conf = conf;
		this.divId = divId;
		var me = this;
	};

	this.loadTree = function() {
		var setting = {
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				key : { name: "name"}
			},
			callback: {
				beforeDrag: this.beforeDrag,
				beforeDrop: this.beforeDrop
			}
		};
		$.post(conf.url, conf.params, function(result) {
					var json = eval('(' + result + ')');
					me.tree = $.fn.zTree.init($("#" + me.divId), setting,
							json);
					me.tree.expandAll(true);
				});
	};
	this.beforeDrag = function(){
		//alert("222");
	};
	this.beforeDrop = function(){
		//alert("111");
	};
};
