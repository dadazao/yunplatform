(function() {
	tinymce.PluginManager.requireLangPack("label");
	tinymce.create("tinymce.plugins.LabelPlugin",{
		init : function(a, b) {
			a.addCommand("mceLabel", function() {
				a.execCommand('mceInsertContent', false, "[数据字段]");
			});
			a.addButton("label", {
				title : "插入字段",
				cmd : "mceLabel",
				image : b + "/img/label.png"
			});
			a.onNodeChange.add(function(d, c, e) {
				c.setActive("label", e.nodeName == "IMG")
			})
		},
		createControl : function(b, a) {
			return null
		},
		getInfo : function() {
			return {
				longname : "Label plugin",
				author : "Some author",
				authorurl : "http://tinymce.moxiecode.com",
				infourl : "http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/label",
				version : "1.0"
			}
		}
	});
	tinymce.PluginManager.add("label", tinymce.plugins.LabelPlugin)
})();