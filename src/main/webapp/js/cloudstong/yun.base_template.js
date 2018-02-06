var _curHighTd = null;
var _tmpErrorCount = 0;
function templateInit() {
	$('#columnSize').focus(function(){
		if($('#templateView tr').size()!=0){
			alertMsg.warn("模板已经存在数据,列值不能修改!");
			$('#rowSize').focus();
		}
	});
		
	$('#columnSize').blur(function(){
		if($(this).val()%2!=0){
			alertMsg.warn("数据不正确,请输入正偶数!");
			$(this).val('');
		}
	});

	$('#sys_template-tbl_content').parent().parent().parent().hide();
	$('#templateViewTd').hide();
	$('#templateDesignTr input:text').bind('focus',function() {
		if(_tmpErrorCount == 1){
			_tmpErrorCount = 0 ;	
		}
	});
	$('#templateDesignTr input:text').bind('blur',function() {
		if($(this).attr('name') != 'cusText'){
			numValidate(this);	
		}
	});
	$("#sys_template-tbl_mobanyongtu").removeAttr("disabled");
	
	$('#tabDivId tr[index="7"]').hide();
}

function setTable() {
	var _rowSize = $('#rowSize').val();
	var _columnSize = $('#columnSize').val();
	if (_rowSize == "" || _columnSize == "") {
		alertMsg.warn("数据不正确,请填写要设计表格的行列数!");
		return;
	}
	
	var _trs =  $('#templateView tr').size();
	_rowSize = parseInt(_rowSize)+_trs;
	
	var _tableTr = '';
	for ( var i = _trs; i <_rowSize ; i++) {
		_tableTr += '<tr width="100%" height="25px" index="' + i + '">';
		_tableTr += '<td width="2px" class="Input_Table_Label"  width="1px"></td>';
		for ( var j = 1; j <= _columnSize; j++) {
			if(j % 2 != 0){
				_tableTr += '<td index="' + (i+1) + ',' + j + '" belong="label">数据字段</td>';
			}else{
				_tableTr += '<td index="' + (i+1) + ',' + j + '"  belong="value">数据值</td>';
			}
		}
		_tableTr += '<td class="Input_Table_Label" width="10px"><nobr><img src="/images/template/moveUp.png" title="表格上移" onclick="moveUp(this)"/>&nbsp;<img src="/images/template/moveDown.png" title="表格下移" onclick="moveDown(this)"/>&nbsp;<img src="/images/template/delete.png" title="删除此行" onclick="deleteTr(this)"/></nobr></td>';
		_tableTr += '</tr>';
	}
	
	var tr_index = $('#tr_num').val();
	if(tr_index>_trs){
		tr_index = _trs;
	}
	if(tr_index<=0){
		$('#templateView').prepend(_tableTr);
	}else{
		$.each($('#templateView tr'),function (entryIndex,entry){
			if(entryIndex==(tr_index-1)){
				$(entry).after(_tableTr);
			}
		});
	}
	var _width = parseInt(100 / _columnSize)+'%';
	$.each($('#templateView tr'),function (entryIndex,entry){
		$.each($(entry).children(),function (entryIndex1,entry1){
			if(entryIndex == 0 && entryIndex1 != 0 && entryIndex1 != ($(entry).children().size()-1)){
				if($(entry1).attr('width') == undefined){
					$(entry1).attr('width', _width);
				}
			}else if(entryIndex1 != 0 && entryIndex1 != ($(entry).children().size()-1)){
				$(entry1).removeAttr('width');
			}
		})
	})
	setTableViewNum();
	setTableIndex();
	setTableTdClick();
}

function setTableWidth(){
	var width = $('#tableWidth').val();
	if($('#templateView tr').size()==0){
		alertMsg.warn("请先设置表格!");
	}else if(width == ''){
		alertMsg.warn("请设置表格宽度!");
	}else{
		$('#templateView').attr('width', width+'%');
	}
}

function setTableHeight(){
	var height = $('#tableHeight').val();
	if($('#templateView tr').size()==0){
		alertMsg.warn("请先设置表格!");
	}else if(height == ''){
		alertMsg.warn("请设置表格左右边距!");
	}else{
		$('#templateView').attr('style', 'margin:'+height+'px auto;');
	}	
}

//td被点击的事件
function tdclick(){
    //0 保存当前td节点
    _curHighTd = $(this);
    //移除其他td设置的高亮背景
   $.each($('#templateView td'),function (entryIndex,entry){
		//给所有td节点添加点击事件
		var _style = $(entry).attr('style');
		if(_style != undefined){
			_style = _style.replace(/background:#00FF99/g," ");
			_style = _style.replace(/background: #00ff99/g," ");
			_style = _style.replace(/undefined/g," ");
			_style = _style.replace(/; ;/g,";");
			_style = _style.replace(/;;/g,";");
			$(entry).attr('style', _style);
		}
	})
	//添加当前选择td的高亮背景
    _curHighTd.attr('style',  _curHighTd.attr('style')+';background:#00FF99;');
}

//移除td被点击的事件
function removeTdclick(){
    //移除td设置的高亮背景
    $.each($('#templateView td'),function (entryIndex,entry){
		//给所有td节点添加点击事件
		$(entry).removeAttr('style');
	})
    _curHighTd = null;
}

function tableValidate() {
	var _viewText = $('#templateView').html();
	if ($.trim(_viewText) == '') {
		alertMsg.warn("请先设置表格!");
		return false;
	}
}

function numValidate(_entry) {
	if($(_entry).val() != ''){
		var re = new RegExp("^[0-9]*[0-9][0-9]*$");
		if ($(_entry).val().match(re) == null && _tmpErrorCount == 0) {
			_tmpErrorCount = 1;
			$(_entry).val('');
			alertMsg.warn("请在文本框内输入非负整数!");
			return;
		}
	}
}

function setMerge() {
	var _rowMergeStart = $('#rowMergeStart').val();
	var _colMergeStart = $('#colMergeStart').val();
	var _rowMergeEnd = $('#rowMergeEnd').val();
	var _colMergeEnd = $('#colMergeEnd').val();

	if (_rowMergeStart == '' || _colMergeStart == '' || _rowMergeEnd == ''
			|| _colMergeEnd == '') {
		alertMsg.warn("合并数据填写有误,请正确填写!");
		return;
	}
	var flag = true;
	for ( var i = parseInt(_rowMergeStart); i <= parseInt(_rowMergeEnd); i++) {
		for ( var j = parseInt(_colMergeStart); j <= parseInt(_colMergeEnd); j++) {
			$.each($('#templateView tr td'), function(entryIndex, entry) {
				var index = $(entry).attr('index');
				if (index != undefined && index == (i + ',' + j)) {
					if (($(entry).attr('rowspan') != undefined && $(entry).attr('rowspan') != '1') //$(entry).attr('rowspan') != '1' 为了兼容IE
							|| ($(entry).attr('colspan') != undefined && $(entry).attr('colspan') != '1')) {
						alertMsg.warn("需要合并的行列不正确!");
						flag = false;
					}
				}
			});
		}
	}
	if (flag) {
		var _rowspan = 0;
		var _colspan = 0;
		if (_rowMergeEnd - _rowMergeStart > 0) {
			_rowspan = _rowMergeEnd - _rowMergeStart + 1;
		}
		if (_colMergeEnd - _colMergeStart > 0) {
			_colspan = _colMergeEnd - _colMergeStart + 1;
		}
		var _startEntry;
		$.each($('#templateView tr td'), function(entryIndex, entry) {
			var index = $(entry).attr('index');
			if (index != undefined
					&& index == (_rowMergeStart + ',' + _colMergeStart)) {
				_startEntry = entry;
			}
		});
		if (_rowspan > 1) {
			$(_startEntry).attr('rowspan', _rowspan);
		}
		if (_colspan > 1) {
			$(_startEntry).attr('colspan', _colspan);
		}
		for ( var i = parseInt(_rowMergeStart); i <= parseInt(_rowMergeEnd); i++) {
			for ( var j = parseInt(_colMergeStart); j <= parseInt(_colMergeEnd); j++) {
				if (i != _rowMergeStart || j != _colMergeStart) {
					$.each($('#templateView tr td'),
							function(entryIndex, entry) {
								var index = $(entry).attr('index');
								if (index != undefined
										&& index == (i + ',' + j)) {
									$(entry).remove();
								}
							});
				}
			}
		}
	}
}

function setHeight() {
	var _numHeight = $('#numHeight').val();
	if (_numHeight == "") {
		alertMsg.warn("请填写你要设置的高度!");
		return;
	}
	_curHighTd.attr('height', _numHeight + 'px');
}

function setWidth() {
	var _numWidth = $('#numWidth').val();
	if (_numWidth == "") {
		alertMsg.warn("请填写你要设置的宽度!");
		return;
	}
	_curHighTd.attr('width',  _numWidth + $('#widthUnit').val());
}

function moveUp(obj) {
	var _curTr = $(obj).parent().parent().parent();//当前行对象
	var _curTdFirst = $(_curTr).children().eq(0).text();//当前行的第一个td的值
	if (_curTdFirst == '1') {
		alertMsg.warn("当前为第一行不需要要上移!");
		return false;
	}
	var _prevTr = $(_curTr).prev();//当前行的上一行对象
	//如果当前列有rowspan属性,不可以上移
	//将当前行和上一行的index的"列编号"取出拼接,进行比较
	var _curColIndex = "";
	var _curTd = "";
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined && $(entry).attr('rowspan') != '1') {
			_curTd = $(entry).clone();
		}
	});
	//如果上一列有rowspan属性,复制后,删除此列,将复制后的列放到当前行
	var _prevArrayTd = new Array(5);
	var _prevArrayIndex = new Array(5);
	var m = 0;
	$.each($(_prevTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined && $(entry).attr('rowspan') != '1') {
			_prevArrayTd[m] = $(entry).clone();
			_prevArrayIndex[m] = entryIndex;
			m++;
			$(entry).remove();
		}
	});
	for ( var i = 0; i < _prevArrayTd.length; i++) {
		if (_prevArrayIndex[i] != undefined) {
			$(_curTr).children().eq(_prevArrayIndex[i] - 1 - i).append(
					_prevArrayTd[i]);
		}
	}
	var _prevTdFirst = $(_prevTr).children().eq(0).text();//当前行的上一行对象的第一个td的值
	//将两行的index的行号值进行互换
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('index') != undefined) {
			$(entry).attr('index',
					_prevTdFirst + ',' + $(entry).attr('index').split(',')[1]);
		}
	});
	$.each($(_prevTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('index') != undefined) {
			$(entry).attr('index',
					_curTdFirst + ',' + $(entry).attr('index').split(',')[1]);
		}
	});
	$(_curTr).children().eq(0).text(_prevTdFirst);//将当前行的第一个td的值变成上一行第一个td的值
	$(_prevTr).children().eq(0).text(_curTdFirst);//将上一行的第一个td的值变成当前行第一个td的值
	var _cloneTr = $(_curTr).clone();//将当前行复制,保存在对象内
	$(_curTr).html($(_prevTr).html());//将当前行的内容换成上一行的内容
	$(_prevTr).html($(_cloneTr).html());//将上一行的内容换成当前行的内容
	setTableTdClick();
}

function moveDown(obj) {
	var _curTr = $(obj).parent().parent().parent();//当前行对象
	var _curTdFirst = $(_curTr).children().eq(0).text();//当前行的第一个td的值
	if (_curTdFirst == $('#templateView tr').size()) {
		alertMsg.warn("当前为最后一行不需要下移!");
		return false;
	}
	var _nextTr = $(_curTr).next();//当前行的下一行对象
	//如果当前列有rowspan属性,复制后,删除此列,将复制后的列放到当前行
	var _curArrayTd = new Array(5);
	var _curArrayIndex = new Array(5);
	var m = 0;
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined  && $(entry).attr('rowspan') != '1') {
			_curArrayTd[m] = $(entry).clone();
			_curArrayIndex[m] = entryIndex;
			m++;
			$(entry).remove();
		}
	});
	for ( var i = 0; i < _curArrayTd.length; i++) {
		if (_curArrayIndex[i] != undefined) {
			$(_nextTr).children().eq(_curArrayIndex[i] - 1 - i).append(
					_curArrayTd[i]);
		}
	}
	var _nextTdFirst = $(_nextTr).children().eq(0).text();//当前行的下一行对象的第一个td的值
	//将两行的index的行号值进行互换
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('index') != undefined) {
			$(entry).attr('index',
					_nextTdFirst + ',' + $(entry).attr('index').split(',')[1]);
		}
	});
	$.each($(_nextTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('index') != undefined) {
			$(entry).attr('index',
					_curTdFirst + ',' + $(entry).attr('index').split(',')[1]);
		}
	});
	$(_curTr).children().eq(0).text(_nextTdFirst);//将当前行的第一个td的值变成下一行第一个td的值
	$(_nextTr).children().eq(0).text(_curTdFirst);//将下一行的第一个td的值变成当前行第一个td的值
	var _cloneTr = $(_curTr).clone();//将当前行复制,保存在对象内
	$(_curTr).html($(_nextTr).html());//将当前行的内容换成上一行的内容
	$(_nextTr).html($(_cloneTr).html());//将上一行的内容换成当前行的内容
	setTableTdClick();
}

function deleteTr(obj) {
	var _curTr = $(obj).parent().parent().parent();
	var _nextTr = $(_curTr).next();//当前行的下一行对象
	//如果当前列有rowspan属性,复制后,删除此列,将复制后的列放到当前行
	var _curArrayTd = new Array(5);
	var _curArrayIndex = new Array(5);
	var m = 0;
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined && $(entry).attr('rowspan') != '1') {
			if ($(entry).attr('rowspan') > 2) {
				$(entry).attr('rowspan', $(entry).attr('rowspan') - 1);
			} else {
				$(entry).removeAttr('rowspan');
			}
			_curArrayTd[m] = $(entry).clone();
			_curArrayIndex[m] = entryIndex;
			m++;
		}
	});

	for ( var i = 0; i < _curArrayTd.length; i++) {
		if (_curArrayIndex[i] != undefined) {
			$(_nextTr).children().eq(_curArrayIndex[i] - 1 - i).after(
					_curArrayTd[i]);
		}
	}
	$(_curTr).remove();
	setTableViewNum();
	setTableIndex();
	setTableTdClick();
}

function labelChange(){
	if($('#labelSet').val() == '数据字段'){
		$('#cusText').show();
		$('#tdAlign').show();
	}else if($('#labelSet').val() == '数据值'){
		$('#cusText').hide();
		$('#tdAlign').show();
	}else if($('#labelSet').val() == '自定义文本'){
		$('#cusText').show();
		$('#tdAlign').show();
	}else if($('#labelSet').val() == '自定义样式'){
		$('#cusText').show();
		$('#tdAlign').hide();
	}
	$('#cusText').val('');
}

function setTdValue(){//compexDomainTabEdit_label_t compexDomainTabEdit_value_t
	if($('#labelSet').val() == '数据字段'){
		_curHighTd.attr('belong', 'label');
		if($('#cusText').val() != ''){
			_curHighTd.html($('#cusText').val());
		}else{
			_curHighTd.html('数据字段');
		}
		_curHighTd.attr('align', $('#tdAlign').val());
	}else if($('#labelSet').val() == '数据值'){
		_curHighTd.attr('belong', 'value');
		_curHighTd.html('数据值');
		_curHighTd.attr('align', $('#tdAlign').val());
	}else if($('#labelSet').val() == '自定义文本'){
		_curHighTd.attr('belong', 'custom');
		_curHighTd.html($('#cusText').val());
		_curHighTd.attr('align', $('#tdAlign').val());
	}else if($('#labelSet').val() == '自定义样式'){
		if(_curHighTd.html() == '数据值'){
			alertMsg.warn("数据值不需要设置样式!");
		}else{
			var _curStyle = _curHighTd.attr('style');
			if( _curStyle != undefined){
				_curHighTd.attr('style', _curStyle+';'+$('#cusText').val());
			}else{
				_curHighTd.attr('style', $('#cusText').val());
			}
			
		}
	}
}

function lalelSet(entry){
	var  _entry= $(entry).find('option:selected');
	if($(_entry).text()=='div'){
		var _text= $(entry).parent().find('input');
		if($(_text).attr('name')!=undefined){
			$(_text).remove();
		}
	}else{
		var _text= $(entry).parent().find('input');
		if($(_text).attr('name')==undefined){
			$(_entry).parent().after('<input type="text" name="cusText"/>');
		}
	}
	$.each($(entry).children(), function(entryIndex1, entry1) {
		$(entry1).removeAttr('selected' , 'selected');
	})
	$(_entry).attr('selected' , 'selected');
}

function changeMbyt() {
	$.ajax( {
		type : 'POST',
		async : false,
		dataType : 'json',
		url : __basePath+'/pages/resource/template/content.action?templateId=' + $("#sys_template-tbl_mobanyongtu").val(),
		success : function(data) {
			if(data.templateChName=='空模板'){
				$('#templateDesignDiv').html("");
				$('#rowSize').val('0');
				$('#columnSize').val('0');
			}else{
				$('#sys_template-tbl_content').val(data.content);
				viewContent();
			}
		}
	});
}

function viewContent() {
	var template_content = $('#sys_template-tbl_content').val();
	if (template_content != "") {
		$('#templateDesignDiv').html(template_content);
		$.each($('#templateDesignDiv td'), function(entryIndex, entry) {
			$(this).removeAttr('class');
		});
		insertTdSelect();
		insertTdOperate();
	}
	setTableIndex();
	setTableRowAndColumnNum();
	setTableTdClick();
}

function insertTdSelect(){
	$.each($('#templateView tr'),function (entryIndex,entry){
		$.each($(entry).children(),function (entryIndex1,entry1){
			var _div = $(entry1).find("div");
			if($(entry1).attr('belong') == 'label'){
				if($(_div).html() == ''){
					$(entry1).html('数据字段');
				}else{
					$(entry1).html($(_div).html());
				}
			} else if($(entry1).attr('belong') == 'value'){
				$(entry1).html('数据值');
			}
		})
	})
}

function insertTdOperate(){
	$.each($('#templateView tr'),function (entryIndex,entry){
		$(entry).prepend('<td width="1px" class="Input_Table_Label">'+(entryIndex+1)+'</td>');
		$(entry).append('<td class="Input_Table_Label" width="10px"><nobr><img src="/images/template/moveUp.png" title="表格上移" onclick="moveUp(this)"/>&nbsp;<img src="/images/template/moveDown.png" title="表格下移" onclick="moveDown(this)"/>&nbsp;<img src="/images/template/delete.png" title="删除此行" onclick="deleteTr(this)"/></nobr></td>');
	});
}

function setTableViewNum(){
	$.each($('#templateView tr td:first-child'),function (entryIndex,entry){
		$(this).html(entryIndex+1);
	});
}

function setTableIndex(){
	$.each($('#templateView tr'),function (entryIndex,entry){
		$(entry).attr('index', (entryIndex+1));
		$.each($(entry).children(),function (entryIndex1,entry1){
			if(entryIndex1!=0 && (entryIndex1+1) !=$(entry).children().length){
				$(entry1).attr('index', (entryIndex+1)+','+entryIndex1);
			}
		})
	})
}

function setTableTdClick(){
	$.each($('#templateView tr'),function (entryIndex,entry){
		$.each($(entry).children(),function (entryIndex1,entry1){
			if(entryIndex1!=0 && (entryIndex1+1) !=$(entry).children().length){
				$(entry1).click(tdclick);
			}
		})
	})
}

function setTableRowAndColumnNum(){
	var _tds = 0;
	$.each($('#templateView tr:eq(0)').children(),function (entryIndex,entry){
		if($(entry).attr('colspan') != undefined){
			_tds += parseInt($(entry).attr('colspan'));
		}else{
			_tds++;
		}
	});
	$('#sys_template-tbl_trs').val($('#templateView tr').size());
	$('#rowSize').val($('#templateView tr').size());
	if($('#templateView tr:eq(0)').children().size()>2){
		$('#sys_template-tbl_tds').val(_tds-2);
		$('#columnSize').val(_tds-2);
	}else{
		$('#sys_template-tbl_tds').val(0);
		$('#columnSize').val(0);
	}
}

function removeTableRedundancy(){
	$.each($('#templateViewTd tr td:first-child'),function (entryIndex,entry){
		$(this).remove();
	});
	$.each($('#templateViewTd tr td:last-child'),function (entryIndex,entry){
		$(this).remove();
	});
	$.each($('#templateView td'),function (entryIndex,entry){
		//给所有td节点添加点击事件
		var _style = $(entry).attr('style');
		if(_style != undefined){
			_style = _style.replace(/background:#00FF99/g," ");
			_style = _style.replace(/background: #00ff99/g," ");
			_style = _style.replace(/undefined/g," ");
			_style = _style.replace(/; ;/g,";");
			_style = _style.replace(/;;/g,";");
			$(entry).attr('style', _style);
		}
	})
}

function setTemplateView(){
	$('#templateViewTd').html($('#templateDesignDiv').html());
	removeTableRedundancy();
	var i=1;
	var m=1;
	$.each($('#templateViewTd td'), function(entryIndex, entry) {
		var _text = $(this).text();
		if($(this).attr('belong') == 'label'){
			$(this).attr('class', 'Input_Table_Label');
			if(_text == '数据字段'){
				$(this).html('<div id="compexDomainTabEdit_label_t' + i + '"></div>');
			}else{
				$(this).html('<div id="compexDomainTabEdit_label_t' + i + '">'+$(this).html()+'</div>');
			}
			
			i++;
		}else if($(this).attr('belong') == 'value'){
			$(this).removeAttr('class');
			$(this).html('<div id="compexDomainTabEdit_value_t' + m + '"></div>');
			m++;
		}else{
			$(this).html($(this).html());
		}
	})
}

function eventFormTemplateBC(){
	if($('#templateView tr').size()==0){
		alertMsg.warn("请先设置表格!");
	}else{
		setTemplateView();
		$('#sys_template-tbl_content').val($.trim($('#templateViewTd').html()));
		$('#templateViewTd').html('');
		eventCompexBC();
	}
}