tinyMCEPopup.requireLangPack();

function insertFL() {
	var fl = document.getElementById("formAndList").value;
	var order = document.getElementById("order").value;
	var inst = tinyMCEPopup.editor;
	inst.execCommand('mceInsertContent', false, "["+fl+order+"]");
	tinyMCEPopup.close();
}