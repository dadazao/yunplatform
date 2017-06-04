//初始化对象
function initObject() {
	if($.browser.msie){
		try{
		    webform.WebOffice.WebUrl=path+"/OfficeServlet";
		    var fmt = ".doc";
		    try{
		    	if(fileType==1){
			    	fmt = ".xls";
			    }
		    }catch(a){}
		    
		    webform.WebOffice.RecordID=recordId;
		    webform.WebOffice.Template="";
		    webform.WebOffice.FileName=recordId+fmt;
		    webform.WebOffice.FileType=fmt;
		    webform.WebOffice.UserName="liutao";
		    webform.WebOffice.EditType=editType
		    
		    webform.WebOffice.Language="CH";
		    
		    webform.WebOffice.ShowMenu="0";
		    
		    if(pageType == 'lbdy' || pageType == 'bddy'){
		   		webform.WebOffice.AppendTools("13","打印(&P)",13);
		   	}
		    
		   	if(pageType != 'view'){
		   		webform.WebOffice.ShowToolBar="1";
		   		
		   		webform.WebOffice.VisibleTools('保存文件',false);
			    webform.WebOffice.VisibleTools('新建文件',false);
			    webform.WebOffice.VisibleTools('文字批注',false);
			    webform.WebOffice.VisibleTools('手写批注',false);
			    webform.WebOffice.VisibleTools('文档清稿',false);
			    webform.WebOffice.VisibleTools('重新批注',false);
			    
			    webform.WebOffice.AppendTools("1","另存文件",2);
			    webform.WebOffice.AppendTools("2","保存文件",2);
		   	}else{
		   		webform.WebOffice.ShowToolBar="0";
		   	}
		   	
		    
			
		    webform.WebOffice.Office2007Ribbon = 0;
		    webform.WebOffice.WebOpen(false); 
		    webform.WebOffice.ShowType='1';
		}catch(e){
		    $('#iWebOfficeTip').show();
		}
	}else{
		alert("此页面使用了字处理组件,当前组件只支持IE8以上版本!");
	}
	
}
//作用：保存文档
function SaveDocument() {
	if (!webform.WebOffice.WebSave(true)) { //交互OfficeServer的OPTION="SAVEFILE"  注：WebSave()是保存复合格式文件，包括OFFICE内容和手写批注文档；如只保存成OFFICE文档格式，那么就设WebSave(true)
		alert("文件已经保存到服务器!");
		return false;
	} else {
		alert("文件已保存到服务器!");
		return true;
	}
}

//作用：退出iWebOffice
function UnLoad(){
	if($.browser.msie){
		try{
			webform.WebOffice.WebClose()
		}catch(e){
		}
	}
}

function formListChange(entry){
	getTabColumn(entry.value);
	if(op == '1'){
		setFormListStatus();
	}else{
		deleteAllBookMarks();
	}
}

function getTabColumn(formId){
	$.ajax( {
		type : 'post',
		url : path+'/pages/resource/printtabColumn.action',
		data : 'formId='+formId,
		dataType:'json',
		success : function(data) {
			var _html = '<div id="webOfficeColumnTdDiv"><img src='+path+'"/images/template/mbAdd.png" onclick="eventAddBookmarks();"/>' +
			'&nbsp;&nbsp;<img src='+path+'"/images/template/mbLocal.png" onclick="eventLocateBookmark();"/>' +
			'&nbsp;&nbsp;<img src='+path+'"/images/template/mbDel.png" onclick="eventRemoveBookmark();"/>';
			_html += '<table id="column_select" style="width:100%;background-color:#FFFFFF;"  border="0" cellspacing="0" cellpadding="0">';
			$.each(data,function (entryIndex,entry){
				_html += '<tr><td data="'+entry.columnName+'">'+entry.columnZhName+'</td><td><input type="checkbox" id="'+entry.columnName+'" disabled></td></tr>';
			});
			_html += '</table></div>';
			$('#webOfficeColumn').html(_html);
			$.each($('#column_select tr'),function (entryIndex,entry){
				$(entry).click(columnSelectTdclick);
			})
			$('#webOfficeColumnTdDiv').attr('style', 'height: 480px;overflow-x:hidden;OVERFLOW-Y:auto;');
		}
	});
}

//td被点击的事件
function columnSelectTdclick(){
    //0 保存当前td节点
    _curPrintHighTr = $(this);
    //移除其他td设置的高亮背景
    $.each($('#column_select tr'),function (entryIndex,entry){
		//给所有td节点添加点击事件
		$(entry).removeAttr('style');
	})
	//添加当前选择td的高亮背景
    _curPrintHighTr.attr('style', 'background:#CCCCCC;');
}

function eventAddBookmarks(){
	var _firstTd = $(_curPrintHighTr).children().first();
	if(_firstTd.attr('data') != undefined){
		webform.WebOffice.WebObject.Bookmarks.Add(_firstTd.attr('data'));
		$('#'+_firstTd.attr('data')).attr('checked', 'checked');
		alert("书签添加成功");
	}else{
		alert("请选择你需要的数据!");
	}
}

function eventLocateBookmark(){
	var _firstTd = $(_curPrintHighTr).children().first();
	if(_firstTd.attr('data') != undefined){
		webform.WebOffice.WebObject.Application.Selection.GoTo(-1,0,0,_firstTd.attr('data'))
	}else{
		alert("请选择你需要的数据!");
	}
}

function eventRemoveBookmark(){
	var _firstTd = $(_curPrintHighTr).children().first();
	if(_firstTd.attr('data') != undefined){
		if(confirm("确定要删除此书签吗？")){
			webform.WebOffice.WebObject.Bookmarks(_firstTd.attr('data')).Delete();
			$('#'+_firstTd.attr('data')).removeAttr('checked');
			alert("书签删除成功");
		}
	}else{
		alert("请选择你需要的数据!");
	}
}

function getAllBookMarks(){
	var count =  webform.WebOffice.WebObject.Bookmarks.Count;
	var bookMarks = new Array();
	for (i=1;i<=count;i++){
	    var mBookName = webform.WebOffice.WebObject.Bookmarks.Item(i).Name;
	    var j = i - 1;  //由于数组从0开始
	    bookMarks[j]=mBookName;//使用数组暂存书签名称
	}
	return bookMarks;
}

function deleteAllBookMarks(){
	var bookMarks = getAllBookMarks();
	for(var k=0;k<=bookMarks.length-1;k++){  //读取数组中的书签名称
		webform.WebOffice.WebObject.Bookmarks(bookMarks[k]).Delete();  //删除指定标签
	}
}

function setFormListStatus(){
	var si = setInterval(function(){
		var bookMarks = getAllBookMarks();
		if($('#'+bookMarks[k]).attr('checked') == undefined){
			for(var k=0;k<=bookMarks.length-1;k++){  //读取数组中的书签名称
				$('#'+bookMarks[k]).attr('checked', 'checked');	
			}
		}else{
			clearInterval(si);
		}	
	},1000);
}
//printFormYL.jsp
function getPrintTemplate(fileName){
	var framePage = "printFormYLframe";
	if(op != 'formyl'){
		framePage = "printListYLframe";
	}
	$('#webOfficeDiv').html('<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="600" src="pages/resource/template/print/'+framePage+'.jsp?editType=1,1&recordId='+fileName+'&op=0"></iframe>');
	var si = setInterval(function(){
		if(window.frames["ifrWeboffice"].document.getElementById("formId") != null){
			if($('#formId').val() == undefined){
				window.frames["ifrWeboffice"].document.getElementById("formId").value = $('#cloudstongFormId').val();
				window.frames["ifrWeboffice"].document.getElementById("params").value=$('#tableForm').data('params');
				
			}else{
				window.frames["ifrWeboffice"].document.getElementById("formId").value = $('#formId').val();
				window.frames["ifrWeboffice"].document.getElementById("params").value = $('#paramsId').val();
			}			
			clearInterval(si);
		}			
	},1000);
}

function zTreeOnClick(event, treeId, treeNode) {
	if(treeNode.pId != null){
		getPrintTemplate(treeNode.filename);
	}
}

function zTreeOnNodeCreated(event, treeId, treeNode){  
    var span=$("#"+treeNode.tId+"_span");  
    if(treeNode.remark!=undefined&&treeNode.remark!=""){  
    span[0].title=treeNode.remark;}  
} 

function eventCompexListPrint(){
	window.frames["ifrWeboffice"].WebOpenPrint();
}

function eventCompexListQPXS(){
	window.frames["ifrWeboffice"].qpxs();
}

function setRedHeadContent(){
	webform.WebOffice.WebObject.Bookmarks.Add('iweboffice_red_head');
	webform.WebOffice.WebSetBookMarks('iweboffice_red_head', '[\n设置红头\n]');
}

function qpxs(){
	webform.WebOffice.FullSize();
}

function getAllBookMarks(){
	var count =  webform.WebOffice.WebObject.Bookmarks.Count;
	var bookMarks = new Array();
	for (i=1;i<=count;i++){
	    var mBookName = webform.WebOffice.WebObject.Bookmarks.Item(i).Name;
	    var j = i - 1;  //由于数组从0开始
	    bookMarks[j]=mBookName;//使用数组暂存书签名称
	}
	return bookMarks;
}

function setFormBookmarksStatus(){
	var si = setInterval(function(){
		if($('#formId').val() != '0'){
			$.ajax( {
				type : 'post',
				url : path+'/pages/resource/printbookMarkJson.action?formId='+$('#formId').val()+'&params='+$('#params').val(),
				dataType:'json',
				success : function(data) {
					$.each(data,function(entryIndex, entry){
						webform.WebOffice.WebSetBookMarks(entry.label,entry.value);
					})
				}
			});
			clearInterval(si);
		}			
	},1000);
}

function setListBookmarksStatus(){
	var si = setInterval(function(){
		if($('#formId').val() != '0'){
			try{
				var table = webform.WebOffice.WebObject.Application.ActiveDocument.Tables(1);
				designTable(table);
				insertTableCell(table);
			}catch(e){
				alert("当前模板有误,不存在列表模板中所需要的表格!");
			}
			clearInterval(si);
		}			
	},1000);
}

function designTable(table){
	var paramsCount = $('#params').val().split(';').length;
	var tableCount = table.Rows.Count;
	if(paramsCount>=tableCount){
		for(var i = tableCount; i<paramsCount; i++ ){
			var mRow = table.Rows(1);
			table.Rows.Add(mRow);
		}
	}else{
		for(var i = tableCount; i>paramsCount; i-- ){
			table.Rows(i).Delete();
		}
	}
}

function insertTableCell(table){
	var paramsCount = $('#params').val().split(';').length;
	var bookMarks = getAllBookMarks();
	for(var i = 0; i<paramsCount-1; i++ ){
		$.ajax( {
			type : 'post',
			url : path+'/pages/resource/printbookMarkJson.action?formId='+$('#formId').val()+'&params='+$('#params').val().split(';')[i],
			async : false,
			dataType:'json',
			success : function(data) {
				var tableColCount = 1;
				$.each(data,function(entryIndex, entry){
					for(var k=0;k<=bookMarks.length-1;k++){  //读取数组中的书签名称
						if(bookMarks[k] == entry.label){
							table.Cell(1, tableColCount).Range.Text=entry.name;
							table.Cell((i+2), tableColCount).Range.Text=(entry.value=='null'?'':entry.value);
							tableColCount++;
						}
					}
				})
			}
		});
	}
}

function WebOpenPrint(){
	try{
		webform.WebOffice.WebObject.Bookmarks.Item("iweboffice_red_head").Range.Font.Color = webform.WebOffice.WebObject.Background.Fill.ForeColor.RGB;
	    webform.WebOffice.WebOpenPrint();
	    webform.WebOffice.WebObject.Bookmarks.Item("iweboffice_red_head").Range.Font.Color = 255;
	}catch(e){
		 alert(e.description);
	}
}

//打开打印模板时使用,全屏显示 方法
function eventCompexQPXS(){
	window.frames["ifrWeboffice"].qpxs();
}