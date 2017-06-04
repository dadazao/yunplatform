(function() {
	tinymce.PluginManager.requireLangPack("formAndList");
	tinymce.create("tinymce.plugins.FormAndListPlugin",{
		init : function(ed, url) {
			// Register commands
			ed.addCommand('mceFormAndList', function() {
				ed.windowManager.open({
					file : url + '/formAndList.htm',
					width : 250,
					height : 160,
					inline : 1
				}, {
					plugin_url : url
				});
			});
			ed.addButton("formAndList", {
				title : "插入选择项",
				cmd : "mceFormAndList",
				image : url + "/img/formAndList.png"
			});
		},
		createControl : function(b, a) {
			return null
		},
		getInfo : function() {
			return {
				longname : "formAndList plugin",
				author : "sam",
				authorurl : "http://tinymce.moxiecode.com",
				infourl : "http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/formAndList",
				version : "1.0"
			}
		}
	});
	tinymce.PluginManager.add("formAndList", tinymce.plugins.FormAndListPlugin)
})();