(function() {
	tinymce.PluginManager.requireLangPack("labelAndValue");
	tinymce.create("tinymce.plugins.labelAndValuePlugin",{
		init : function(ed, url) {
			// Register commands
			ed.addCommand('mcelabelAndValue', function() {
				ed.windowManager.open({
					file : url + '/labelAndValue.htm',
					width : 250,
					height : 160,
					inline : 1
				}, {
					plugin_url : url
				});
			});
			ed.addButton("labelAndValue", {
				title : "插入选择项",
				cmd : "mcelabelAndValue",
				image : url + "/img/labelAndValue.png"
			});
		},
		createControl : function(b, a) {
			return null
		},
		getInfo : function() {
			return {
				longname : "labelAndValue plugin",
				author : "sam",
				authorurl : "http://tinymce.moxiecode.com",
				infourl : "http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/labelAndValue",
				version : "1.0"
			}
		}
	});
	tinymce.PluginManager.add("labelAndValue", tinymce.plugins.labelAndValuePlugin)
})();