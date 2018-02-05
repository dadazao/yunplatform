//以下内容为永中weboffcie
//万恶的分割线-------------------------------------------------------------------------
/**
 * 提供一个容器，加载并启动YozoWebOffice的Applet
 * 2012-07-13
 * @param {Object} divID
 */

function EIOWebOffice(divID, eioWebOffice) {
	$('#' + divID).html('');
	if (eioWebOffice == undefined) {
		eioWebOffice = 'EIOWebOffice';
	}
	var _innerHTML = "<object classid = 'clsid:CAFEEFAC-0016-0000-0011-ABCDEFFEDCBA' codebase ='jre/jre-6u-windows-i586-s.exe' WIDTH = '100%' "
			+ "HEIGHT = '500px' MAYSCRIPT=true NAME = '"
			+ eioWebOffice
			+ "' id='"
			+ eioWebOffice
			+ "'>"
			+ "<PARAM NAME = java_arguments VALUE = '-Dsun.java2d.d3d=false -Xmx512m'>"
			+ "<PARAM NAME = CODE VALUE = 'applets.preloader.Office.class'>"
			+ "<PARAM NAME = CODEBASE VALUE = '/components/applet'>"
			+ "<PARAM NAME = ARCHIVE VALUE = 'preloader.jar'>"
			//			+ "<PARAM NAME = OFFICEVERSION VALUE = '1'>"  //当前版本的id数值如果高于历史版本的id数值，则清除历史缓存，重新加载	2012-3-30
			+ "<PARAM NAME = 'type' VALUE = 'application/x-java-applet;jpi-version=1.6.0'>"
			+ "<comment>"
			+ "<embed java_arguments = '-Dsun.java2d.d3d=false -Xmx512m'"
			+ "type = 'application/x-java-applet' "
			+ "CODEBASE = 'applet' "
			+ " ARCHIVE = 'preloader.jar'"
			+ " NAME = 'EIOWebOffice'"
			+ " id='EIOWebOffice' "
			+ "  WIDTH = '100%'  "
			+ "  HEIGHT = '100%'  "
			+ "  MAYSCRIPT = 'true' "
			+ "  pluginspage = 'jre/jre-6u-windows-i586-s.exe'> <noembed></noembed></embed></comment></object>";
	$('#' + divID).html(_innerHTML);
}

//	/**
//	 * 初始化，添加需要在加载时执行的动作
//	 * 2012-07-13
//	 */
function afterLaunchOffice() {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	if (EIOWebOffice != null) {
		var Application = EIOWebOffice.getApp();
		if (Application != null) {
			if (op == "new") {
				doCreate(1);
			} else if (op == "edit") {
				getFormServer($('#editEIOWebOfficeDocid').val());
				$('#EIOWebOfficeDocid').val($('#editEIOWebOfficeDocid').val());
				$.each(getBookmarks().split(';'), function(entryIndex, entry) {
					$('#' + entry).attr('checked', 'checked');
				})
			} else if (op == "yl") {
			} else if (op == "") {
				getFormServer($('#EIOWebOfficeDocid').val());
				var doc = Application.getWorkbooks().getActiveWorkbook()
						.getDocuments().getActiveDocument();
				doc.protect(2, "123456");
			}
		}
	}
}

/**
 * “新建”文件,如果不传值默认建立文字文件
 * 2012-07-13
 * @param {Object} type
 * 0：表格 Excel
 * 1：文字 Word
 * 2：文稿 PPT
 */
function doCreate(type) {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	var Application = EIOWebOffice.getApp();
	if (Application != null) {
		if (type > -1 && type < 3) {
			Application.getWorkbooks().addWorkbook(type);
		} else {
			Application.getWorkbooks().addWorkbook(1);
		}
	}
}

/**
 * 打印预览
 * 2012-07-16
 */
function printView() {
	var doc = getDocument();
	if (doc != null) {
		var printPreview = doc.getPrintPreview();
		printPreview.preview();
		setPrintUI();
	}
}

/**
 * 打印预览界面
 * 2012-07-16
 */
function setPrintUI() {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	var Application = EIOWebOffice.getApp();
	if (Application != null) {
		Application.getOptions().showRulers(false);//去掉标尺
		Application.setAllToolbarVisible(false);//去掉所有的工具条
		Application.setToolbarVisible(37, false);//表格工具条显示
		Application.setToolbarVisible(2, false);//格式化工具条显示
		Application.setToolbarVisible(0, false);//隐藏菜单工具栏
	}
}

/**
 * 关闭打印预览，返回编辑状态
 * 2012-07-16
 * @param {Object} bool
 */
function closePrintViewToEdit(bool) {
	var doc = getDocument();
	if (doc != null) {
		var printPreview = doc.getPrintPreview();//获取打印预览管理器
		printPreview.closePreview();//关闭打印预览进入编辑模式
		setEditUI();
	}
}

/**
 * 编辑状态界面
 * 2012-07-16
 */
function setEditUI() {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	var Application = EIOWebOffice.getApp();
	if (Application != null) {
		Application.getOptions().showRulers(false);//去掉标尺
		Application.setAllToolbarVisible(false);//去掉所有的工具条
		Application.setToolbarVisible(37, true);//表格工具条显示
		Application.setToolbarVisible(2, true);//格式化工具条显示
		Application.setToolbarVisible(0, true);//显示菜单工具栏
	}
}

/**
 * 设置文件修改状态
 * 2012-07-16
 * @param {Object} bool
 * true：修改中
 * false：未修改
 */
function changModifiedState(bool) {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	var Application = EIOWebOffice.getApp();
	if (Application != null) {
		if (Application.getWorkbooks() != null) {
			if (Application.getWorkbooks().getActiveWorkbook() != null) {
				var workbook = Application.getWorkbooks().getActiveWorkbook();
				if (workbook != null) {
					workbook.setModifiedFlag(bool);
				}
			}
		}
	}
}

/*************************************************************************
 *保存文件至服务器
 *文件名称写在servlet里面了
 *具体服务器端路径写入servlet里面
 **************************************************************************/
function saveToServer() {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	if (EIOWebOffice != null) {
		EIOWebOffice.doSaveServerDoc2(serverHost + "/OfficeServlet", "docid="
				+ $('#EIOWebOfficeDocid').val() + ".eio&type=save", 0);
	}
}

function getFormServer(docid) {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	if (EIOWebOffice != null) {
		EIOWebOffice.doOpenURL(serverHost + "/OfficeServlet?docid=" + docid
				+ ".eio&type=get");
	}
}

/**
 * 设置标签内容
 * 2012-07-13
 * @param {Object} name
 * @param {Object} value
 */
function setBookMarkValue(name, value) {
	var doc = getDocument();
	if (doc != null) {
		var bookmarks = doc.getBookmarks();
		var bookmark = bookmarks.get(name);
		if (bookmark != null) {
			bookmark.setValue(value == "null" ? "" : value);
		}

	}
}

/*******************************************************************************
 * 设置书签
 *@param String content 书签内容 
 ******************************************************************************/
function setBookmarks(bookmark) {
	var doc = getDocument();
	if (doc != null) {
		var baseText = doc.getBaseText();
		var bookmarks = doc.getBookmarks();
		bookmarks.add(doc.getSelectionStart(), 0, baseText, bookmark);
	}
}

function delBookmarks() {
	var doc = getDocument();
	if (doc != null) {
		var baseText = doc.getBaseText();
		var bookmarks = doc.getBookmarks();
		var bookNames = bookmarks.getBookmarkNames();
		for ( var i = 0; i < bookNames.length; i++) {
			bookmarks.locateBookmark(bookNames[i]);
			var range = baseText.getRange(doc.getSelectionStart(), doc
					.getSelectionEnd());
			range.insertTextBefore("");
			removeBookmark(bookNames[i]);
		}
	}
}

function locateBookmark(bookmarkName) {
	var doc = getDocument();
	if (doc != null) {
		var bookmarks = doc.getBookmarks();
		bookmarks.locateBookmark(bookmarkName);
	}
}

function getBookmarks() {
	var doc = getDocument();
	if (doc != null) {
		var bookmarks = doc.getBookmarks();
		var bookNames = bookmarks.getBookmarkNames();
		var _bookNames = '';
		for ( var i = 0; i < bookNames.length; i++) {
			_bookNames += bookNames[i] + ';';
		}
		return _bookNames;
	}
}

function removeBookmark(bookmarkName) {
	var doc = getDocument();
	if (doc != null) {
		var bookmarks = doc.getBookmarks();
		bookmarks.remove(bookmarkName);
	}
}

function fullScreen() {
	//设置全屏模式
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	EIOWebOffice.setFullScreen();
}

function setOfficeTable() {
	var doc = getDocument();
	if (doc != null) {
		try {
			var table = doc.getTables().getTable(0);
			if (table == null) {
				alert('当前模板中不存在表格,模版错误!');
			} else {
				var rows = table.getRows();
				for ( var i = 1; i < rows.getCount(); i++) {
				}
				var params = $('#tableForm').data('params').split(';');
				for ( var i = 0; i < params.length - rows.getCount(); i++) {
					var beforeRow = rows.getRow(0);
					rows.addRow(beforeRow, 1);
				}
				_setOfficeTable(doc, table, params)
			}
		} catch (e) {
			alert('当前模板中存在错误,不能正常显示!');
		}

	}
}

function _setOfficeTable(doc, table, params) {
	var tableCode = '';
	var tilteName = '';
	for ( var i = 0; i < params.length - 1; i++) {
		$.ajax( {
			type : 'post',
			async : false,
			dataType : 'json',
			url : 'pages/resource/printbookMarkJson.action?formId='+$('#cloudstongFormId').val()+'&params='+params[i]+';',
			success : function(data) {
				var bookmarks = doc.getBookmarks();
				var bookNames = bookmarks.getBookmarkNames();
				for ( var i = 0; i < bookNames.length; i++) {
					$.each(data, function(entryIndex, entry) {
						if (entry.label == bookNames[i]) {
							tableCode += entry.value + '-_-';
							tilteName += entry.name + '-_-';
						}
					})
				}
			}
		});
		tableCode += '@_@';
		tilteName += '@_@';
	}
	try {
		var rowTitle = table.getRows().getRow(0);
		var tiles = tilteName.split("@_@")[0].split("-_-");
		for ( var m = 0; m < tiles.length; m++) {
			var cellsTitle = rowTitle.getCells();
			var rangeTitle = cellsTitle.getCell(m).getRange();
			rangeTitle.insertText(tiles[m]);
		}
	}catch (e) {}
	delBookmarks();
	for ( var i = 1,n=0; i < params.length; i++) {
		var row = table.getRows().getRow(i);
		if (row != null) {
			var rowCode = tableCode.split('@_@')[n];
			var cells = row.getCells();
			for ( var m = 0; m < cells.getCount(); m++) {
				var range = cells.getCell(m).getRange();
				try {
					range.insertText(rowCode.split('-_-')[m]);
				} catch (e) {
				}
			}
		}
		n++;
	}
	//doc.protect(2, '111111');
}

function getDocument() {
	var EIOWebOffice = document.getElementById("EIOWebOffice");
	var Application = EIOWebOffice.getApp();
	if (Application != null) {
		var doc = Application.getWorkbooks().getActiveWorkbook().getDocuments()
				.getActiveDocument();
		if (doc != null) {
			return doc;
		} else {
			return null;
		}
	}
}