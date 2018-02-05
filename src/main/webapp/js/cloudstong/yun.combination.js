var _tmpErrorCount = 0;
function templateInit() {
	$('#sys_template-tbl_content').parent().parent().parent().hide();
	$('#templateViewTr').hide();
	$('#templateDesignTr').show();
	$('#templateDesign1Tr').show();
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
	$("#templateFile").removeAttr("class");
}

function setTable() {
	var _rowSize = $('#rowSize').val();
	var _columnSize = $('#columnSize').val();
	if (_rowSize == "" || _columnSize == "") {
		alertMsg.info("数据不正确,请填写要设计表格的行列数!");
		return;
	}
	var _table = '';
	var _width = parseInt(100 / _columnSize);
	for ( var i = 1; i <= _rowSize; i++) {
		_table += '<tr width="100%" height="25px" index="'+i+'">';
		for ( var j = 1; j <= _columnSize; j++) {
			_table += '<td width="' + _width + '%" index="'+i+','+j+'">&nbsp;</td>';
		}
		_table += '</tr>';
	}
	$('#templateView1').html(_table);
	$.each($('#templateView1 tr td'),function (entryIndex,entry){
		$(entry).html('<select name="select'+entryIndex+'"><option value="form" selected="selected">表单</option><option value="list">列表</option></select><select name="text'+entryIndex+'"><option value="1" selected="selected">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select>');
	});
	$.each($('#templateView1 tr'),function (entryIndex,entry){
		$(entry).prepend('<td width="2px" class="Input_Table_Label">'+(entryIndex+1)+'</td>');
		$(entry).append('<td class="Input_Table_Label"><nobr><img src="/images/template/moveUp.png" title="表格上移" onclick="moveUp(this)"/>&nbsp;<img src="/images/template/moveDown.png" title="表格下移" onclick="moveDown(this)"/>&nbsp;<img src="/images/template/delete.png" title="删除此行" onclick="deleteTr(this)"/></nobr></td>');
	});
	$('#templateView1 select').change(function(){
		if($(this).val() == 'list'){
			$(this).html('<option value="form">表单</option><option value="list" selected="selected">列表</option>');
		}else if($(this).val() == 'form'){
			$(this).html('<option value="form" selected="selected">表单</option><option value="list">列表</option>');
		}else if($(this).val() == '1'){
			$(this).html('<option value="1" selected="selected">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option>');
		}else if($(this).val() == '2'){
			$(this).html('<option value="1">1</option><option value="2" selected="selected">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option>');
		}else if($(this).val() == '3'){
			$(this).html('<option value="1">1</option><option value="2">2</option><option value="3" selected="selected">3</option><option value="4">4</option><option value="5">5</option>');
		}else if($(this).val() == '4'){
			$(this).html('<option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4" selected="selected">4</option><option value="5">5</option>');
		}else if($(this).val() == '5'){
			$(this).html('<option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5" selected="selected">5</option>');
		}
	})
}

function setTableWidth() {
	var _tableWidthNum = $('#tableWidthNum').val();
	if (_tableWidthNum == "") {
		alertMsg.info("请填写你要设置的表格宽度!");
		return;
	}
	$('#templateView1').attr('width',
			_tableWidthNum + $('#tableWidthUnit').val());
}

function setMerge() {
	var _rowMergeStart = $('#rowMergeStart').val();
	var _colMergeStart = $('#colMergeStart').val();
	var _rowMergeEnd = $('#rowMergeEnd').val();
	var _colMergeEnd = $('#colMergeEnd').val();

	if (_rowMergeStart == '' || _colMergeStart == '' || _rowMergeEnd == ''
			|| _colMergeEnd == '') {
		alertMsg.info("合并数据填写有误,请正确填写!");
		return;
	}
	var flag = true;
	for ( var i = parseInt(_rowMergeStart); i <= parseInt(_rowMergeEnd); i++) {
		for ( var j = parseInt(_colMergeStart); j <= parseInt(_colMergeEnd); j++) {
			$.each($('#templateView1 tr td'), function(entryIndex, entry) {
				var index = $(entry).attr('index');
				if (index != undefined && index == (i + ',' + j)) {
					if ($(entry).attr('rowspan') != undefined
							|| $(entry).attr('colspan') != undefined) {
						alertMsg.info("需要合并的行列不正确!");
						flag = false;
					}
				}
				if ($(entry).attr('rowspan') != undefined
						|| $(entry).attr('colspan') != undefined) {

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
		$.each($('#templateView1 tr td'), function(entryIndex, entry) {
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
					$.each($('#templateView1 tr td'),
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

function setBreak() {

}

function setHeight() {
	var _numHeight = $('#numHeight').val();
	if (_numHeight == "") {
		alertMsg.info("请填写你要设置的高度!");
		return;
	}
	if($('#rowHeight').val()==''){
		$.each($('#templateView1 tr'),function (entryIndex,entry){
			$(entry).attr('height', _numHeight+$('#heightUnit').val());
		});
	}else{
		$.each($('#templateView1 tr'),function (entryIndex,entry){
			if($(entry).attr('index')==$('#rowHeight').val()){
				$(entry).attr('height', $('#numHeight').val()+$('#heightUnit').val());
			}
		})
	}
}

function setWidth() {
	var _rowWidth = $('#rowWidth').val();
	var _columnWidth = $('#columnWidth').val();
	var _numWidth = $('#numWidth').val();
	if (_numWidth == "") {
		alertMsg.info("请填写你要设置的宽度!");
		return;
	}
	if(_columnWidth==''){
		$('#templateView1').attr('width', _numWidth+$('#widthUnit').val());
	}else{
		if(_rowWidth == ''){
			$.each($('#templateView1 td'),function (entryIndex,entry){
				var index = $(entry).attr('index');
				if(index !=undefined && index.split(',')[1]==_columnWidth){
					$(entry).attr('width', _numWidth+$('#widthUnit').val());
				}
			});
		}else{
			$.each($('#templateView1 td'),function (entryIndex,entry){
				var index = $(entry).attr('index');
				if(index !=undefined && index ==(_rowWidth+','+_columnWidth)){
					$(entry).attr('width', _numWidth+$('#widthUnit').val());
				}
			});
		}
	}
}

function numValidate(_entry) {
	if($('#sys_template-tbl_templatechname').val()==''){
		$('#sys_template-tbl_templatechname').focus();
		alertMsg.warn("请先输入模板名称!");
		return;
	}else{
		var re = new RegExp("^[0-9]*[0-9][0-9]*$");
		if ($(_entry).val().match(re) == null && _tmpErrorCount == 0) {
			_tmpErrorCount = 1;
			alertMsg.warn("请在文本框内输入非负整数!");
			return;
		}
	}
}

$('#templateDesignTr input:text').bind('blur', function() {
	if ($(this).attr('id') != 'rowSize' && $(this).attr('id') != 'columnSize') {
		tableValidate();
	}
	numValidate(this);
});

function tableValidate() {
	var _viewText = $('#templateView1').html();
	if ($.trim(_viewText) == '') {
		alertMsg.info("请先设置表格!");
		return false;
	}
}

function moveUp(obj) {
	var _curTr = $(obj).parent().parent().parent();//当前行对象
	var _curTdFirst = $(_curTr).children().eq(0).text();//当前行的第一个td的值
	if (_curTdFirst == '1') {
		alertMsg.info("当前为第一行不需要要上移!");
		return false;
	}
	var _prevTr = $(_curTr).prev();//当前行的上一行对象
	//如果当前列有rowspan属性,不可以上移
	//将当前行和上一行的index的"列编号"取出拼接,进行比较
	var _curColIndex = "";
	var _curTd = "";
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined) {
			_curTd = $(entry).clone();
		}
	});
	//如果上一列有rowspan属性,复制后,删除此列,将复制后的列放到当前行
	var _prevArrayTd = new Array(5);
	var _prevArrayIndex = new Array(5);
	var m = 0;
	$.each($(_prevTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined) {
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
}

function moveDown(obj) {
	var _curTr = $(obj).parent().parent().parent();//当前行对象
	var _curTdFirst = $(_curTr).children().eq(0).text();//当前行的第一个td的值
	if (_curTdFirst == $('#templateView1 tr').size()) {
		alertMsg.info("当前为最后一行不需要下移!");
		return false;
	}
	var _nextTr = $(_curTr).next();//当前行的下一行对象
	//如果当前列有rowspan属性,复制后,删除此列,将复制后的列放到当前行
	var _curArrayTd = new Array(5);
	var _curArrayIndex = new Array(5);
	var m = 0;
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined) {
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
}

function deleteTr(obj) {
	var _curTr = $(obj).parent().parent().parent();
	var _nextTr = $(_curTr).next();//当前行的下一行对象
	//如果当前列有rowspan属性,复制后,删除此列,将复制后的列放到当前行
	var _curArrayTd = new Array(5);
	var _curArrayIndex = new Array(5);
	var m = 0;
	$.each($(_curTr).children(), function(entryIndex, entry) {
		if ($(entry).attr('rowspan') != undefined) {
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
}

function setTableViewNum(){
	$.each($('#templateView1 tr td:first-child'),function (entryIndex,entry){
		$(this).html(entryIndex+1);
	});
}

function eventCombTemplateBC(){
	setTableRowAndColumnNum();
	$('#templateView2').html($('#templateView1').html());
	$.each($('#templateView2 tr'),function (entryIndex,entry){
		$(entry).children().first().remove();
		$(entry).children().last().remove();
	});
	$.each($('#templateView2 tr td'),function (entryIndex,entry){
		var _text = $(this).children().eq(0).find('option:selected').text();
		var _value = $(this).children().eq(1).find('option:selected').text();
		if(_text == '表单'){
			$(this).html('<div id="partitionForm'+_value+'"></div>');
		}
		if(_text == '列表'){
			$(this).html('<div id="partitionList'+_value+'"></div>');
		}
	});
	$('#sys_template-tbl_content').val($.trim($('#templateView2').parent().html()));
	eventCompexBC();
}

function viewContent(){
	var template_content = $('#sys_template-tbl_content').val();
	if (template_content != "") {
		$('#templateView1').html(template_content);
		$.each($('#templateView1 tr td'), function(entryIndex, entry) {
			$(this).removeAttr('class');
		});
		insertTdSelect();
		insertTdOperate();
	}
}

function insertTdSelect(){
	$.each($('#templateView1 tr'),function (entryIndex,entry){
		$.each($(entry).children(),function (entryIndex1,entry1){
			$(entry1).html('<select name="select'+entryIndex1+'"><option value="form" selected="selected">表单</option><option value="list">列表</option></select><select name="text'+entryIndex1+'"><option value="1" selected="selected">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select>');
		})
	})
}

function insertTdOperate(){
	$.each($('#templateView1 tr'),function (entryIndex,entry){
		$(entry).prepend('<td width="1px" class="Input_Table_Label">'+(entryIndex+1)+'</td>');
		$(entry).append('<td class="Input_Table_Label" width="10px"><nobr><img src="/images/template/moveUp.png" title="表格上移" onclick="moveUp(this)"/>&nbsp;<img src="/images/template/moveDown.png" title="表格下移" onclick="moveDown(this)"/>&nbsp;<img src="/images/template/delete.png" title="删除此行" onclick="deleteTr(this)"/></nobr></td>');
	});
}

function setTableIndex(){
	$.each($('#templateView1 tr'),function (entryIndex,entry){
		$(entry).attr('index', (entryIndex+1));
		$.each($(entry).children(),function (entryIndex1,entry1){
			if($(entry1).attr('index') != undefined){
				$(entry1).attr('index', (entryIndex+1)+','+entryIndex1);
			}
		})
	})
}

function setTableRowAndColumnNum(){
	var _tds = 0;
	$.each($('#templateView1 tr:eq(0)').children(),function (entryIndex,entry){
		if($(entry).attr('colspan') != undefined){
			_tds += parseInt($(entry).attr('colspan'));
		}else{
			_tds++;
		}
	});
	$('#sys_template-tbl_trs').val($('#templateView1 tr').size());
	$('#rowSize').val($('#templateView1 tr').size());
	if($('#templateView1 tr:eq(0)').children().size()>2){
		$('#sys_template-tbl_tds').val(_tds-2);
		$('#columnSize').val(_tds-2);
	}else{
		$('#sys_template-tbl_tds').val(0);
		$('#columnSize').val(0);
	}
}

function loadPartition(){
	$("#partitionFormDiv").loadUrl("pages/resource/partitionloadPartitionForm.action");
}

function addPartition(){
	$partition = $("#partitionForm");
	if (!$partition.valid()) {
		return false;
	}
	$("#partitionId").val("");
	if($('#domainId').val() == ""){
		alertMsg.warn("请先建立模板!");
	}else{
		var urlString = "pages/resource/partitionsave.action?templateId=" + $('#domainId').val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#partitionForm").serialize(),
			success: function(data){
				loadPartitionList();
			}
		});
	}
}

function updatePartition(){
	$partition = $("#partitionForm");
	if (!$partition.valid()) {
		return false;
	}
	if($('#domainId').val() == ""){
		alertMsg.warn("请先建立模板!");
	}else{
		var urlString = "pages/resource/partitionsave.action?templateId=" + $('#domainId').val();
		$.ajax({
			type:'post',
			url: urlString,
			data:$("#partitionForm").serialize(),
			success: function(data){
				loadPartitionList();
			}
		});
	}
}

function deletePartition(){
	alertMsg.confirm("确定要删除吗?", {okCall:
		function(){
        	var urlString = "pages/resource/partitiondelete.action";
			$.ajax({
				type:'post',
				url: urlString,
				data:$("#partitionListForm").serialize(),
				success: function(data){
					loadPartition();
					loadPartitionList();
				}
			});
		}
	});
}

function loadPartitionList() {
	var urlString = "pages/resource/partitionlist.action?templateId=" + $('#domainId').val();
	$.ajaxSetup({async: false});
	$("#partitionListDiv").load(urlString);	
	initPagination();
	$.ajaxSetup({async: true});
}

function loadEditPartition(id){
	var urlString = "pages/resource/partitionedit.action?templateId="+$('#domainId').val()+"&partitionId=" + id;
	$('#partitionFormDiv').load(urlString);	
}

function addPartitionPage(){
	$('#tabLiId').append('<li><span style="color:;font-family:&quot;&quot;;">分区</span></li>');
	$('#tabDivId').append('<div><div align="center" id="partitionFormDiv"></div><br><div align="center" id="partitionListDiv"></div></div>');
	loadPartition();
	loadPartitionList();
}