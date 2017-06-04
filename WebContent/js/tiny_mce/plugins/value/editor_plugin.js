(function() {
	tinymce.PluginManager.requireLangPack("value");
	tinymce.create("tinymce.plugins.ValuePlugin",{
		init : function(a, b) {
			a.addCommand("mceValue", function() {
				a.execCommand('mceInsertContent', false, "[数据值]");
			});
			a.addButton("value", {
				title : "插入值",
				cmd : "mceValue",
				image : b + "/img/value.png"
			});
			a.onNodeChange.add(function(d, c, e) {
				c.setActive("value", e.nodeName == "IMG")
			})
		},
		createControl : function(b, a) {
			return null
		},
		getInfo : function() {
			return {
				longname : "Value plugin",
				author : "Some author",
				authorurl : "http://tinymce.moxiecode.com",
				infourl : "http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/value",
				version : "1.0"
			}
		}
	});
	tinymce.PluginManager.add("value", tinymce.plugins.ValuePlugin)
})();