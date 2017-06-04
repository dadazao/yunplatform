//字体
var FONTSTYLE = [{VALUE:"",TEXT:"系统默认",SELECTED:"selected"},
				 {VALUE:"宋体",TEXT:"宋体",SELECTED:""},
	             {VALUE:"新宋体",TEXT:"新宋体",SELECTED:""},
	             {VALUE:"黑体",TEXT:"黑体",SELECTED:""},
	             {VALUE:"楷体_GB2312",TEXT:"楷体_GB2312",SELECTED:""},
	             {VALUE:"仿宋_GB2312",TEXT:"仿宋_GB2312",SELECTED:""},
	             {VALUE:"微软雅黑",TEXT:"微软雅黑",SELECTED:""},
	             {VALUE:"方正舒体",TEXT:"方正舒体",SELECTED:""},
	             {VALUE:"方正姚体",TEXT:"方正姚体",SELECTED:""},
	             {VALUE:"隶书",TEXT:"隶书",SELECTED:""}];
function initFontStyleComBoxItems(comBoxItemValue){
	for(var nCount = 0;nCount < FONTSTYLE.length;nCount++){
	    var obj = new Object(FONTSTYLE[nCount]);
	    if(comBoxItemValue == obj.VALUE){
	       obj.SELECTED = "selected";
	    }
	    $('#tabFontStyle').append("<option value=\""+obj.VALUE+"\" "+obj.SELECTED+">"+obj.TEXT+"</option>");
	}
}

//字体颜色
var TabFontColor = [{VALUE:"",TEXT:"系统默认",SELECTED:"selected"},
	         {VALUE:"#000000",TEXT:"黑色",SELECTED:""},
	         {VALUE:"#c0c0c0",TEXT:"灰色",SELECTED:""},
	         {VALUE:"#808A87",TEXT:"冷灰",SELECTED:""},
	         {VALUE:"#F0FFFF",TEXT:"天蓝色",SELECTED:""},
             {VALUE:"#F5F5F5",TEXT:"白烟",SELECTED:""},
             {VALUE:"#FAEBD7",TEXT:"古董白",SELECTED:""},
             {VALUE:" #FF0000",TEXT:"红色",SELECTED:""}];
function initTabFontColorComBoxItems(comBoxItemValue){
	for(var nCount = 0;nCount < TabFontColor.length;nCount++){
	    var obj = new Object(TabFontColor[nCount]);
	    if(comBoxItemValue == obj.VALUE){
	       obj.SELECTED = "selected";
	    }
	    $('#tabFontColor').append("<option value=\""+obj.VALUE+"\" "+obj.SELECTED+">"+obj.TEXT+"</option>");
	}
}