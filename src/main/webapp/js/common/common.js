/**
 * 判断是否是中文字符串
 * @param {Object} s
 * @return {TypeName} 
 */
function ischinese(s){ 
	var ret=true; 
	for(var i=0;i<s.length;i++) 
		ret=ret && (s.charCodeAt(i)>=10000); 
	return ret; 
}

/**
 * 判断是否含有中文字符串
 * @param {Object} s
 * @return {TypeName} 
 */
function haschinese(s){
	var ret=false; 
	for(var i=0;i<s.length;i++) 
		if(s.charCodeAt(i)>=10000){
			ret = true;
		} 
	return ret; 
}

function getUnique() {
	var now = new Date();
	return now.getFullYear().toString()+(now.getMonth() + 1)+now.getDate()+now.getHours()+now.getMinutes()+now.getSeconds();
}

function getDay() {
	var now = new Date();
	return now.getFullYear().toString()+(now.getMonth() + 1)+now.getDate();
}

function allPrpos(obj) {
    // 用来保存所有的属性名称和值
    var props = "";
    // 开始遍历
    for(var p in obj){
        // 方法
        if(typeof(obj[p])=="function"){
            //obj[p]();
        }else{
            // p 为属性名称，obj[p]为对应属性的值
            props += p + "=" + obj[p] + ";  ";
        }
    }
    // 最后显示所有的属性
    alert(props);
}

function showZtree(object, url, params, onClickCallback, setting) {
	if(setting == undefined){
		setting = {
			view: {
				nameIsHTML: true,
				showTitle: false
			},
			data : {
				simpleData : {
					enable : true,
					idKey: "id",
					pIdKey: "pId"					
				}
			},
			callback: {
				onClick: onClickCallback
			}
		};
	}
	$.ajax({
		url : url,
		type : 'POST',
		data : params,
		async : false,
		dataType : "json",
		success : function(zNodes) {
			$.fn.zTree.init($("#"+object), setting, zNodes);
			//$("#"+object).data('zTree', $("#"+object).parent().html());
		},
		error : function(msg) {
			alertMsg.warn("数据返回异常");
		}
	});
}

var addEvent = function(el, type, fn) { 
    //如果是标准浏览器，则使用addEventListener方法添加事件 
    if(window.addEventListener){ 
        el.addEventListener(type, fn, false); 
        return; 
    } 
    //计数器，用于给每个事件处理函数一个唯一的ID 
    if(!addEvent.guid) 
        addEvent.guid = 0; 
    //将ID赋给事件处理函数 
    if(!fn._id) 
        fn._id = addEvent.guid++; 
    //events对象用于存储所有的事件类型 
    if(!el.events) 
        el.events = {}; 
    //不同的事件类型(click、mouseover等)作为events的一个属性 
    var handles = el.events[type]; 
    if(!handles){ 
        //el.events[type]作为一个存储此类事件处理函数的对象 
        handles = el.events[type] = {}; 
        if(el["on" + type]) 
        //handles[0]用于存储已使用el.onclick形式定义的事件处理函数（也可以使用其他的属性，不一定要使用0） 
            handles[0] = el["on" + type]; 
    } 
    handles[addEvent.guid] = fn; 
    el["on" + type] = addEvent.handleEvent; 
}; 
/** 
 * 执行事件 
 * @param {Object} event 
 */ 
addEvent.handleEvent = function(event) { 
    event = event || addEvent.fixEvent(); 
    var handles = this.events[event.type]; 
    for(var i in handles){ 
        handles[i].call(this,event); 
    } 
}; 
/** 
 * 修复IE的event对象 
 */ 
addEvent.fixEvent = function(){ 
    var event = window.event; 
    event.target = event.srcElememt; 
    if(event.type == "mouseover") 
        event.relatedTarget = event.fromElement; 
    else if(event.type == "mouseout") 
        event.relatedTarget = event.toElement; 
    event.charCode = (event.type == "keypress" || event.type == "keydown" || event.type == "keyup") ? event.keyCode : 0; 
    event.preventDefault = function(){ 
        this.returnValue = false; 
    } 
    event.stopPropagation = function(){ 
        this.cancelBubble = true; 
    } 
    return event; 
}; 
/** 
 * 移除事件 
 * @param {Object} el 
 * @param {Object} type 
 * @param {Object} fn 
 */ 
var delEvent = function(el, type, fn){ 
    if(window.removeEventListener){ 
        el.removeEventListener(type, fn); 
        return; 
    } 
    if(el.events){ 
        var fns = el.events[type]; 
        if(fns) 
            delete fns[fn._id]; 
    } 
}; 

function systemSort(array){ 
	return array.sort(
		function(a, b){ return b["name"].length-a["name"].length; }); 
}

/**
 * 显示或隐藏高级查询
 * @param {Object} type
 */
ns.common.showQuery = function(type){
	var ih = $(".navTab-panel").height() - $(".pageContent").height() - $(".panelBar").height() - 40;
	if(type == 1){
		$("#defaultQuery").hide();
		$("#advanceQuery").show();
		$(".gridScroller").height((ih - $("#advanceQuery").height()) + "px");
	}else{
		$("#defaultQuery").show();
		$("#advanceQuery").hide();
		$(".gridScroller").height((ih - $("#defaultQuery").height()) + "px");
	}
}

/**
 * 点击查询按钮
 * @param {Object} id
 */
ns.common.query = function(id){
	$("#" + id).submit();
}

/**
 * 双击关闭对话框
 */
ns.common.dbclickCloseDialog = function(){
	$.pdialog.getCurrent().dblclick(function(){
		$.pdialog.closeCurrent();
	});
	$("td",$.pdialog.getCurrent()).not(".Input_Table_Label").dblclick(function(event){
		event.stopPropagation(); 
	});
}

/**
 * 鼠标放在按钮上样式变化
 * @memberOf {TypeName} 
 */
ns.common.mouseForButton = function (){
	var $button=$('.listbutton');
    $button.mouseover(function(){
    	$(this).removeClass("listbutton");
        $(this).addClass("listbuttonOver");                                                          
     });

    $button.mouseout(function(){
        $(this).removeClass("listbuttonOver");  
    	$(this).addClass("listbutton");
    });
}

/**
 * 全选列表
 * @param {Object} obj
 * @param {Object} cName
 * @return {TypeName} 
 */
ns.common.selectAll = function(obj, cName) {
	var checkboxs = document.getElementsByName(cName);
	if(checkboxs.length == 0 ){
		alertMsg.warn("当前列表无数据!");
		$(obj).attr('checked', false);
		return false;
	}
	for ( var i = 0; i < checkboxs.length; i++) {
		if(checkboxs[i].disabled == false) {
			checkboxs[i].checked = obj.checked;	
		}
	}
}
/**
 * Dialog Tab页切换事件
 * @param {Object} callback 切换后需要执行的回调函数
 */
ns.common.changeDialogTab = function(callback){
	if(callback){
		var $callback = callback;
		if (! $.isFunction($callback)){
			$callback = eval('(' + callback + ')');
		}
		$callback.apply();
	}
}
/**
 * 修改保存方法
 * @param {Object} buttonid 按钮id
 * @param {Object} funcName 方法名称
 */
ns.common.changeSaveAction = function(buttonid,funcName){
	if(buttonid&&funcName){
		if($('#'+buttonid).length>0){
			var $button = $('#'+buttonid);
			$button.attr('onclick',funcName+'()');
		}else{
			DWZ.debug('no button with id:'+buttonid +'is found!');
		}
	}
}
    
			