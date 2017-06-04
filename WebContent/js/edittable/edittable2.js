/* 
code: editable.js 
version: v1.0 
date: 2011/10/21 
author: lq
usage: 
$("table").editable({ selector 可以选择table或者tr 
head: true, 是否有标题 
noeditcol: [1, 0], 哪些列不能编辑 
编辑列配置：colindex：列索引 
edittype：编辑时显示的元素 0：input 1：checkbox 2：select 
ctrid：关联元素的id 如edittype=2， 那么需要设置select的元素 
css:元素的样式 
editcol: [{ colindex: 2, edittype: 2, ctrid: "sel",css:""}], 
onok: function () { 
return true; 根据结果返回true or false 
}, 
ondel: function () { 
return true; 根据结果返回true or false 
} 
}); 
*/ 
(function ($) { 
$.fn.editable = function (options) { 
	options = options || {}; 
	opt = $.extend({}, $.fn.editable.defaults, options); 
	trs = []; 
	$.each(this, function () { 
		if (this.tagName.toString().toLowerCase() == "table") { 
			$(this).find("tr:not(:first)").each(//除了标题行都加入
				function () {
					trs.push(this); 
				}
			); 
		}else if (this.tagName.toString().toLowerCase() == "tr") { 
			trs.push(this); 
		}
	});
	$trs = $(trs);
	var button = "<td class='tdClass' width='7%'><a href='#' class='" + opt.editcss + "'>编辑</a> <a href='#' style='color:blue;' class='" + opt.onokcss + "'>保存</a> <a href='#' style='color:blue;' class='" + opt.canclcss + "'>取消</a></td>"; 
	$trs.each(function (i, tr) {
		if (opt.head && i == 0) { 
			$(tr).append("<td></td>"); 
			return true;
		} 
		$(tr).append(button); 
	});
	$trs.find(".onok, .cancl").hide(); 
	if ($trs.size() == 0 || (opt.head && $trs.size() == 1)){return false;} 
    $trs.find(".edit").click( //给每一行绑定编辑事件
	function () { 
		$tr = $(this).closest("tr");
		$tds = $tr.find("td");
		$.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) {
		if ($.inArray(i, opt.noeditcol) != -1){//序号列不在编辑列
			return true;
		}
		var t = $.trim($(td).text()); 
		if (opt.editcol != undefined) {
			 $.each(opt.editcol, function (j, obj) { 
				if (obj.colindex == i){
					css = obj.css ? "class='" + obj.css + "'" : ""; 
						if (obj.edittype == undefined || obj.edittype == 0) { //文本框
							$(td).data("v", t); 
							$(td).html("<input onkeyup='this.value=this.value.replace(\/[^\\d]\/g,\"\")' onpaste='return false' maxlength='4' type='text' value='" + t + "' " + css + " style='width:50px' />"+"<input type='hidden' value='" + t + "' />");
						} else if (obj.edittype == 2) { //select 
							if (obj.ctrid == undefined) { 
								alert('请指定select元素id ctrid'); 
								return; 
							}
							$(td).empty().append($("#" + obj.ctrid).clone().show());
							$(td).append("<input type='hidden' value='" + t + "' />");
							var options = $(td).find("option");
							options.each(function(){
								if($(this).text()==t){
									$(this).attr("selected", true);
								}
							});
						}else if(obj.edittype==1){//checkbox
							if (obj.ctrid == undefined) { 
								alert('请指定checkbox元素id ctrid'); 
								return; 
							}
							var _ckbox = '';
							if(t=='是'){
								_ckbox = '<input id="aa" type="checkbox" checked="checked"></input>'
							}else{
								_ckbox = '<input id="aa" type="checkbox"></input>';
							}
							$(td).empty().append(_ckbox);
//							$(td).append("<input type='hidden' value='" + t + "' />");
						}
						/* 可以在此处扩展input、select以外的元素编辑行为 */ 
				} 
			}); 
			} 
			else { 
				$(td).data("v", t);
				$(td).html("<input type='text' value='" + t + "' "+css + " style='width:"+($(td).innerWidth()-14)+"px' />"+"<input type='hidden' value='" + t + "' />");
			}
		}); 
		$tr.find(".onok, .cancl, .edit").toggle();
		opt.onedit($tr);
		return false; 
	}
);

$trs.find(".del").click(function () {
	$tr = $(this).closest("tr"); 
	if (opt.ondel($tr)) { 
		$tr.remove(); 
	} 
	return false; 
});

$trs.find(".onok").click(function () {
	$tr = $(this).closest("tr");
	$tds = $tr.find("td");
	if (opt.onok($tr)) {
		$.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) {
			var c = $(td).children().get(0); 
			if (c != null){
				if (c.tagName.toLowerCase() == "select") { 
					$(td).html(c.options[c.selectedIndex].text); 
				} 
				else if (c.tagName.toLowerCase() == "input") { 
					if(c.type.toLowerCase() != "checkbox"){
						$(td).html(c.value); 
					}
				} 
				/* 可以在此处扩展input、select以外的元素确认行为 */ 
			}
		});
		$tr.find(".onok, .cancl, .edit").toggle();
	}
	return false; 
});

$trs.find(".cancl").click(
	function () { 
		$tr = $(this).closest("tr"); 
		$tds = $tr.find("td"); 
		$.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) { 
			var c = $(td).children().get(1); 
			if (c != null) 
				if (c.tagName.toLowerCase() == "select") { 
					$(td).html(c.options[c.selectedIndex].text); 
				} 
				else if (c.tagName.toLowerCase() == "input") { 
					if(c.type.toLowerCase() != "checkbox"){
						$(td).html(c.value);
					}
				}
				/* 可以在此处扩展input、select以外的元素取消行为 */ 
		});
		$tr.find(".onok, .cancl, .edit, .del").toggle();
		return false;
	}
);
};

$.fn.editable.defaults = {head: false,editcolumn:true,
/* 
如果为空那么所有的列都可以编辑，并且默认为文本框的方式编辑 
如下形式： 
{{colindex:'', edittype:'', ctrid:'', css:''}, ...} 
edittype 0:input 1:checkbox 2:select 
*/ 
//editcol:{}, 
/* 
设置不可以编辑的列，默认为空 
如下形式： 
[0,2,3,...] 
*/ 
	noeditcol: [],
	onok: function () { 
				alert("this's default onok click event"); 
				return true; 
	}, 
	ondel: function () { 
				alert("this's default on del click event"); 
				return true; 
	}, 
	onedit: function (){
				alert("this's default on edit click event"); 
				return true; 
	},
	editcss: "edit", 
	delcss: "del", 
	onokcss: "onok", 
	canclcss: "cancl" 
	}; 
})(jQuery);