tinyMCEPopup.requireLangPack();

function insertLV() {
	var fl = document.getElementById("dataLabel").value;
	if(document.getElementById("dataValue").checked) {
		fl = document.getElementById("dataValue").value;
	}
	var order = document.getElementById("order").value;
	if(isNaN(order)){
		alert("你的输入有误,'字段与值的序列数'的值应为数字");
		return;
	}
	var inst = tinyMCEPopup.editor;
	inst.execCommand('mceInsertContent', false, "【"+fl+","+order+"】");
	tinyMCEPopup.close();
}