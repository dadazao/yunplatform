(function($) {
	$.fn.yunTreeTable = function(options){
		var defaults = {
			ztreeTitle : "",
			ztreePropId: "ztreeProp",
			tableTitle : ["列表"],
			tableNum:1
		}
		var opts = $.extend( {}, defaults, options);
		$('#treeTable').html(getTableHtml(opts));
		return $.fn.yunTreeTable;
	}

	$.fn.yunTreeTable.ztree = function(options) {
		var defaults = {
			setting : {
				check : {
					enable : true,
					chkStyle : "checkbox",
					chkboxType : {
						"Y" : "ps",
						"N" : "ps"
					}
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId",
						rootPId : 0
					}
				}
			},
			zNodes : {},
			async : {},
			callback : {}
		};
		var opts = $.extend( {}, defaults, options);
		return $.fn.zTree.init($("#compexTree"), opts.setting, opts.zNodes);
	}

	$.fn.yunTreeTable.table = function(options) {
		var defaults = {
			title : '',
			columns : [],
			tableNum : 1
		};
		var opts = $.extend( {}, defaults, options);
		tabelHeadInit(opts);
		tabelFooterInit(opts);
	}

	$.fn.yunTreeTable.table.addTableTr = function(ktValue, tableNum) {
		var _bodyTr = '';
		for ( var m = 0; m < ktValue.length; m++) {
			_bodyTr += '<tr>';
			for ( var i = 0; i < ktValue[m].length; i++) {
				if (isNn(ktValue[m][i].display) == '' || ktValue[m][i].display) {
					_bodyTr += '<td ' + isNnk("width", ktValue[m][i].width)
							+ ' ' + isNnk("align", ktValue[m][i].align) + ' '
							+ ' ' + isNnk("class", ktValue[m][i].css) + ' '
							+ isNnk("onclick", ktValue[m][i].onclick) + '>'
							+ isNn(ktValue[m][i].context) + '</td>';
				} else {
					_bodyTr += '<td>' + isNn(ktValue[m][i].context) + '</td>';
				}
			}
			_bodyTr += '</tr>';
		}
		$('#compexTableBody'+tableNum).append(_bodyTr);
		tabelFooterRedraw(tableNum);
	}

	$.fn.yunTreeTable.table.removeTableTr = function(obj, tableNum) {
		$(obj).closest('tr').remove();
		tabelFooterRedraw(tableNum);
	}
	
	$.fn.yunTreeTable.table.firstPage = function(tableNum){
		var curPage = 1;
		hideTableTr(tableNum, curPage);
	}
	
	$.fn.yunTreeTable.table.lastPage = function(tableNum){
		var allPage = $('#compexTableBody'+tableNum).data("allPage"); 
		allPage =  parseInt(allPage);
		hideTableTr(tableNum, allPage);
	}
	
	$.fn.yunTreeTable.table.prevPage = function(tableNum){
		var curPage = $('#compexTableBody'+tableNum).data("curPage"); 
		curPage = curPage-1;
		if(curPage<=0){
			curPage = 1;
			return false;
		}
		hideTableTr(tableNum, curPage);
	}
	
	$.fn.yunTreeTable.table.nextPage = function(tableNum){
		var curPage = $('#compexTableBody'+tableNum).data("curPage"); 
		var allPage = $('#compexTableBody'+tableNum).data("allPage"); 
		curPage = parseInt(curPage)+1;
		allPage =  parseInt(allPage);
		if(curPage>allPage){
			curPage = allPage;
			return false;
		}
		hideTableTr(tableNum, curPage);
	}
	
	
	function getTableHtml(opts){
		var innerZtree = '<table border="0" cellspacing="0" cellpadding="0">'+
				'<tr>'+
					'<td style="background-image: url(images/system/grayLeft.jpg); width: 3px; height: 22px; padding: 0px; background-repeat: no-repeat;"></td>'+
					'<td align="left" style="background-image: url(images/system/gray.jpg); height: 22px; width: 37%; padding: 0px; background-repeat: repeat-x; font-size: 12px; color: #FFF; line-height: 22px;">' +
					'&nbsp;'+opts.ztreeTitle+'</td>'+
					'<td style="background-image: url(images/system/img.jpg); padding: 0px; width: 21px; height: 22px; background-repeat: no-repeat;"></td>'+
					'<td id="ztreeButton" style="background-image: url(images/system/blue.jpg); padding: 0px; height: 22px; width: 53%; background-repeat: repeat-x; text-align: right;">'+
					'</td>'+
					'<td style="background-image: url(images/system/blueRight.jpg); padding: 0px; width: 3px; height: 22px; background-repeat: no-repeat;"></td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="5" style="background-color: #ffffff; padding: 0px;">' +
					'<ul id="compexTree" class="ztree" style="width: 255px; height: 450px; overflow-y: scroll; overflow-x: hidden; background-color: #FFFFFF;">' +
					'</ul>'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td colspan="5" style=" padding: 0px; height:50px;" id="'+opts.ztreePropId+'">&nbsp;属性区' +
					'</td>'+
				'</tr>'+
			'</table>';
		var innerTableTop = '';
		for(var i=0; i<opts.tableNum; i++){
			innerTableTop += '<table border="0" cellspacing="0" cellpadding="0" width="100%" style="padding: 7px;">' +
				'<tr>' +
				'<td style="background-image: url(images/system/grayLeft.jpg); width: 1%; height: 22px; padding: 0px; background-repeat: no-repeat;"></td>' +
				'<td align="left" style="background-image: url(images/system/gray.jpg); height: 22px; width: 23%; padding: 0px; background-repeat: repeat-x; font-size: 12px; color: #FFF; line-height: 22px;">' +
				'&nbsp;'+opts.tableTitle[i]+'</td>' +
				'<td style="background-image: url(images/system/img.jpg); padding: 0px; width: 50%; height: 22px; background-repeat: no-repeat;"></td>' +
				'<td id="tableButton'+i+'" align="left" style="background-image: url(images/system/blue.jpg); padding: 0px; height: 22px; width: 20%; background-repeat: repeat-x; text-align: right;"></td>' +
				'<td style="background-image: url(images/system/blueRight.jpg); padding: 0px; width: 0.5%; height: 22px; background-repeat: no-repeat;"></td>' +
				'</tr>' +
				'<tr> <td valign="top" colspan="5" style="padding: 0px; width: 98%;"> <ul class="ztree_table" style="width: 100%; height: '+(parseInt(480/opts.tableNum)-opts.tableNum*14)+'px; overflow-y: scroll; overflow-x: hidden; background-color: #FFFFFF;">' +
				'<table width="100%" height="25px" border="0" cellpadding="0" cellspacing="0" align="center" class="tableClass" style="border-left: 1px #ededed solid;">' +
				'<thead id="compexTableHead'+i+'"> </thead><tbody id="compexTableBody'+i+'"></tbody></table></ul></td>' +
				'</tr>' +
				'<tr><td height="30px" colspan="5" style="padding: 0px; width: 68%;"><div style="float:left;" id="footinfo'+i+'"></div><div style="float:right;"><a onClick="$.fn.yunTreeTable.table.firstPage('+i+')">首页</a>&nbsp;&nbsp;<a onClick="$.fn.yunTreeTable.table.prevPage('+i+')">上一页</a>&nbsp;&nbsp;<a onClick="$.fn.yunTreeTable.table.nextPage('+i+')">下一页</a>&nbsp;&nbsp;<a onClick="$.fn.yunTreeTable.table.lastPage('+i+')">尾页</a></div> </td></tr>' +
			'</table>';
		}
		var outTable = '<table width="98%" border="0" cellspacing="0" cellpadding="0"'+
			'style="padding: 0px; margin: 0px; border: 1px #b8d1d6 solid; width: 100%; height: 100%; display: table; background-color: #eef3f6; font-size: 12px;">'+
			'<tr><td align="center" valign="top" height="100%" style="padding: 7px;">'+
			innerZtree
			+'</td><td valign="top" style="padding: 0px; width: 100%; border-left: 1px #b8d1d6 solid;">'+
			innerTableTop
			+'</td></tr>'+
		'</table>';
		return outTable;
	}
	/**
	 * 表格头部初始化
	 * @param {Object} opts
	 * 				当前表格对象的参数
	 */
	function tabelHeadInit(opts) {
		for(var m =0; m<opts.tableNum; m++){
			var _headTr = '<tr>';
			for ( var i = 0; i < opts.columns[m].length; i++) {
				if (opts.columns[m][i].display == undefined || opts.columns[m][i].display) {
					_headTr += '<th class="' + opts.columns[m][i].css + '" width="'
							+ opts.columns[m][i].width + '" align="'
							+ opts.columns[m][i].align + '">' + opts.columns[m][i].header
							+ '</th>';
				} else {
					_headTr += '<th>' + opts.columns[m][i].title + '</th>';
				}
	
			}
			_headTr += '</tr>';
			for(var i=0; i<opts.tableNum; i++){
				$('#compexTableHead'+i).html(_headTr);
			}
		}
	}
	
	function tabelFooterInit(opts){
		for(var i=0; i<opts.tableNum; i++){
			var allPage = $('#compexTableBody'+i+' tr').length;
			allPage = (parseInt(allPage/5)+1);
			hideTableTr(i, 1);
		}
	}
	
	function tabelFooterRedraw(tableNum){
		var curPage = $('#compexTableBody'+tableNum).data("curPage"); 
		curPage = parseInt(curPage);
		hideTableTr(tableNum, curPage);
	}
	
	function hideTableTr(tableNum, curPage){
		var count = 5;
		var allPage = $('#compexTableBody'+tableNum+' tr').length-1;
		if(allPage%5 == 0){
			allPage = parseInt(allPage/5);
		}else{
			allPage = (parseInt(allPage/5)+1);
		}
		
		$('#footinfo'+tableNum).html('每页显示'+count+'条,共'+allPage+'页,当前为第'+curPage+'页');;
		var start = (curPage-1)*count;
		var end = curPage*5;
		var tableTr = $('#compexTableBody'+tableNum+' tr');
		for(var i=0; i<tableTr.length; i++){
			if(i<=start || i>end){
				$(tableTr[i]).hide();
			}else{
				$(tableTr[i]).show();
			}
		}
		$('#compexTableBody'+tableNum).data("allPage", allPage); 
		$('#compexTableBody'+tableNum).data("curPage", curPage); 
	}
	
	function isNn(obj) {
		if (obj == undefined) {
			return '';
		} else {
			return obj;
		}
	}

	function isNnk(key, obj) {
		if (obj != undefined) {
			return key + '="' + obj + '"';
		} else {
			return '';
		}
	}
})(jQuery)