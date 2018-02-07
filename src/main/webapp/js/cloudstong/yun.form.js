	function enterNotSubmit(event) {
		if ($(event.target).is("input")) {
			if (event.keyCode == 13) {
				return false;
			}
		}
	
	}
	function dataJson(urlString) {
		$.ajaxSetup({async: false});
		$.ajax({
			type:'post',
			url: urlString,
			dataType: 'json',
			success: function(data){
				buildEditPage(data);
			}
		});
		$.ajaxSetup({async: true});
	}

	function buildEditPage(form) {
		var tabs = form.tabs;
		for(var i=0; i<tabs.length; i++) {
			var tab = tabs[i];
			//设置tab标题
			var clazz = "";
			var templateFileName = tab.templateFileName;
			//去掉文件扩展名
			var temp = templateFileName.substring(0,templateFileName.indexOf("."));
			if(i==0) {
				clazz = "class='selected'";
				$("#tabId").val(temp+tab.id);
				$("#domainSubmit").val(temp+tab.id+"Submit");
			}
			$("#tabLiId").append("<li " + clazz + "><a onclick='setTabForm(\""+temp+tab.id+"\");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
			//填充tab内容
			var fces = tab.formColumnExtends;
			$("#tabDivId").append("<div id='" + temp+tab.id + "'/>")
			var urlString = __basePath+"/pages/core/" + templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&params=" + $("#paramsId").val();
			$.ajax({
				type:'post',
				url: urlString,
				async:false,
				success: function(data){
					var regS = new RegExp(temp,"g"); 
					var result = data.replace(regS, temp+tab.id);
					$("#" + temp+tab.id).html(result);
				}
			});
			var sum = 0;
			var l = 0;
			for(var j=0; j<fces.length; j++) {
				var fce = fces[j];
				var fc = fce.formColumn;
				var value="";
				if(fce.value != null) {
					value = fce.value;
				}
				var name=fc.belongTable + "-" + fc.columnName;
				var columnId = fc.columnName + "Id";
				if(fc.columnName == 'id') {
					$("#" + temp+tab.id + "_value_t" + (l+1)).html("<input type='hidden'" + " id='" + columnId + "' name='" + name + "' value='" + value + "'/>" );
				}else{
					l++;
					if(fc.isLine == '1'){//单独成行
						singleLine(temp,tab,l);
						createContent(temp,tab.id,fc,fce,l,name,value,columnId);
						l++;
						sum += 1;
					}else{
						createContent(temp,tab.id,fc,fce,l,name,value,columnId);
					}
					sum += 1;
				}
			}
			
			$("#" + temp+tab.id + "_tr_t0").hide();
			eval(temp+tab.id + "HideTr(" + sum + ")");
			initTab();
		}
	}
	
	function singleLine(temp,tab,l){
		if(l%2 == 0) {
			var tr = $("#" + temp+tab.id + "_tr_t" + (Math.floor(l/2)+2)).children("td");
			var td1 = tr.eq(0);
			var td2 = tr.eq(1);
			
			td2.attr("colspan",3);
			td2.removeAttr("width");
			
			var ltr = $("#" + temp+tab.id + "_tr_t" + (Math.floor(l/2)+1)).children("td");
			var td3 = ltr.eq(2);
			var td4 = ltr.eq(3);
			td3.html("");
			td4.html("");
			
			$("#" + temp+tab.id + "_tr_t" + (Math.floor(l/2)+1)).html("");
			$("#" + temp+tab.id + "_tr_t" + (Math.floor(l/2)+1)).append(td1).append(td2);
		}else{
			var tr = $("#" + temp+tab.id + "_tr_t" + Math.floor(l/2+1)).children("td");
			var td1 = tr.eq(0);
			var td2 = tr.eq(1);
			td2.attr("colspan",3);
			td2.removeAttr("width");
			$("#" + temp+tab.id + "_tr_t" + Math.floor(l/2+1)).html("");
			$("#" + temp+tab.id + "_tr_t" + Math.floor(l/2+1)).append(td1).append(td2);
		}
	}

	function createContent(temp,tabId,fc,fce,l,name,value,colId){
		$("#" + temp+tabId + "_label_t" + l).html(fc.columnZhName);
		
		if(fc.inputType == 0) {//文本框
			var required = "";
			var xinghao = "";
			if(fc.required == 1) {
				required = "required";
				xinghao = "";//"<font color='red'>*</font>"
			}
			var textBox = fce.component;
			var width = 180;
			var height = 15;
			if(textBox != null) {
				width = textBox.textBoxWidth;
				height = textBox.textBoxHeight;
			}
			
			var style = "style='width:" + width + "px;height:" + height + "px;'";
				
			$("#" + temp+tabId + "_value_t" + l).html("<input " + style + " class='" + fc.validate +" textInput " + required + "' name='" + name + "' id='" + colId + "' value='" + value + "'/>" + xinghao);	
		}
		if(fc.inputType == 1) {//下拉框
			var width = 186;
			var combox = fce.component;
			if(combox != null) {
				width = combox.comboxWidth;
			}
			var select = "<select name='" + name + "' id='" + colId + "' style='width:"+width+"px;'>";
			var codes = fce.codes;
			var options ="";
			for(var k=0; codes!=null&&k<codes.length; k++) {
				var selected = "";
				if(codes[k].value == fce.value) {
					selected = "selected='selected'";
				}
				options += "<option " + selected + " value='" + codes[k].value + "'>" + codes[k].text + "</option>";
			}
			select += options + "</select>";
			$("#" + temp+tabId + "_value_t" + l).html(select);
		}
		if(fc.inputType == 2) {//文本域
			var width = 103;
			var height = 5;
			var textarea = fce.component;
			if(textarea != null) {
				width = textarea.textareaWidth;
				height = textarea.textareaHeight;
			}
			$("#" + temp+tabId + "_value_t" + l).html("<textarea class='textInput' name='" + name + "' id='" + colId + "' rows='" + height + "' cols='" + width + "'>" + value +"</textarea>");
		}
		if(fc.inputType == 3){//单选框
			var radios="";
			var codes = fce.codes;
			for(var k=0; codes!=null&&k<codes.length; k++) {
				var checked = "";
				if(fce.value==null&&k==0){
					checked = "checked='checked'";
				}
				if(codes[k].value == fce.value) {
					checked = "checked='checked'";
				}
				radios += "<input type='radio' name='"+name+"' value='"+codes[k].value+"'  "+checked+"/>"+codes[k].text;
			}
			$("#" + temp+tabId + "_value_t" + l).html(radios);
		}
		if(fc.inputType == 4){//复选框
			var checkBoxes="";
			var codes = fce.codes;
			var res=fce.value.split(",");
			for(var k=0; codes!=null&&k<codes.length; k++) {
				var checked = "";
				for(var c=0;c<res.length;c++){
					if(res[c]==codes[k].value){
						checked = "checked='checked'";
						break;
					}
				}
				checkBoxes += "<input type='checkbox' name='"+name+"' value='"+codes[k].value+"' "+checked+"/>"+codes[k].text;
			}
			$("#" + temp+tabId + "_value_t" + l).html(checkBoxes);
		}
		if(fc.inputType == 6) {//日期组件
			if(fc.dataType=='date') {
				$("#" + temp+tabId + "_value_t" + l).html("<input readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'yyyy-MM-dd'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>");
			}else {
				$("#" + temp+tabId + "_value_t" + l).html("<input readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'yyyy-MM-dd HH:mm:ss'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>");
			}
		}
		
	}

	function viewJson(urlString) {
		$.ajax({
			type:'post',
			url: urlString,
			dataType: 'json',
			success: function(data){
				buildViewPage(data);
			}
		});
	}

	function buildViewPage(form) {
		var tabs = form.tabs;
		for(var i=0; i<tabs.length; i++) {
			var tab = tabs[i];
			//设置tab标题
			var clazz = "";
			var templateFileName = tab.templateFileName;
			//去掉文件扩展名
			var temp = templateFileName.substring(0,templateFileName.indexOf("."));
			if(i==0) {
				clazz = "class='selected'";
				$("#tabId").val(temp+tab.id);
			}
			$("#tabLiId").append("<li " + clazz + "><a onclick='setTabForm(\""+temp+tab.id+"\");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
			
			//填充tab内容
			var fces = tab.formColumnExtends;
			$("#tabDivId").append("<div id='" + temp+tab.id + "'/>")
			var model;
			if(fces != null && fces.length>0){
				model = fces[0].formColumn.belongTable;
			}
			var urlString = __basePath+"/pages/core/" + templateFileName+"?model=" + model;
			$.ajax({
				type:'post',
				url: urlString,
				async:false,
				success: function(data){
					var regS = new RegExp(temp,"g"); 
					var result = data.replace(regS, temp+tab.id);
					$("#" + temp+tab.id).html(result);
				}
			});
			
			var sum = 0;
			var l = 0;
			for(var j=0; j<fces.length; j++) {
				var fce = fces[j];
				var fc = fce.formColumn;
				if(fc.columnName != 'id') {
					l++;
					if(fc.isLine == '1'){//单独成行
						singleLine(temp,tab,l);
					}
					$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
					var value="";
					if(fce.value != null) {
						if(fc.inputType == 1) {
							var codes = fce.codes;
							value = getCodeText(codes,fce.value);
						}else{
							value = fce.value;
						}
					}
						
					$("#" + temp+tab.id + "_value_t" + l).html(value+"&nbsp;");
					if(fc.isLine == '1'){//单独成行
						l++;
						sum += 1;
					}
					sum += 1;
				}
			}
			
			$("#" + temp+tab.id + "_tr_t0").hide();
			eval(temp+tab.id + "HideTr(" + sum + ")");
			initTab();
		}
	}
	
	function getCodeText(codes,value) {
		for(var c=0; c<codes.length; c++) {
			if(value==codes[c].value) {
				return codes[c].text;
			}
		}
	}
	
	function selfDialogAjaxDone(json) {
		navTab.reload("", {navTabId: json.navTabId});
		DWZ.ajaxDone(json);
	}
	
	
	function detailDialogAjaxDone(json) {
		DWZ.ajaxDone(json);
		$.pdialog.close("detailsDialog");
	}
	
	function selfIframeDialogAjaxDone(json) {
		if(typeof json == "string"){
			var domainId = json.substring(json.indexOf("domainId")+11,json.indexOf("navTabId")-3)
			if(domainId != undefined && domainId != ""){
				$("#domainId").val(domainId);
				$("#userId").val(domainId);
			}
		}else if(typeof json == "object"){
			$("#domainId").val(json.domainId);
			$("#userId").val(json.domainId);
		}
		
		if(json.statusCode == '200') {
			alertMsg.correct("操作成功");
			//navTab.reload("", {navTabId: json.navTabId});
			refresh();
		}else if(json.statusCode == '300') {
			alertMsg.error("操作失败");
		}else if(json.statusCode == '301'){
			alertMsg.error(json.message);
		}else if(json.indexOf("statusCode") > 0 && json.indexOf("200")>0) {
			alertMsg.correct("操作成功");
			$(".goto").each(function(){
				var $this = $(this);
				$this.click();
			});
		}else if(json.indexOf("statusCode") > 0 && json.indexOf("301")>0){
			alertMsg.error(json.substring(json.indexOf("message")+10,json.indexOf("navTabId")-3));
		}else{
			alertMsg.error("操作失败");
		}
	}
	
	/***
	 * 生成业务---------------------------------------start-------------------------------------------
	 */
	function compexSelfDialogAjaxDone(json) {
		if(json.domainId){
			$("#domainId").val(json.domainId);
		}else{
			var domainObject = json.domainObject;
			if(domainObject){
				$.each(domainObject,function(key,value){
					$('#'+key).val(value);
				});
			}
		}
		refreshList(json);
	}
	
	function compexSingleDialogAjaxDone(json){
		var domainObject = json.domainObject;
		if(domainObject){
			$.each(domainObject,function(key,value){
				$('#'+key).val(value);
			});
		}
		DWZ.ajaxDone(json);
	}
	
	
	function compexDataJson(urlString,buttonFlag) {
		$.ajax({
			type:'post',
			url: urlString,
			dataType: 'json',
			success: function(data){
				compexBuildPage(data,buttonFlag);
			}
		});
	}

	function compexBuildPage(form,buttonFlag) {
		var buttondis="";
		if(buttonFlag!=undefined&&buttonFlag==true){
			buttondis="disabled='disabled'"
		}
		$("#mainTable").val(form.tableName);
		var tabs = form.tabs;
		var _content_start = '<form id="compexDomainTabEditFormID" action="'+__basePath+'/pages/resource/'+form.simpleModel+'compexsave.action" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);">';
		_content_start += '<div  id="compexDomainTabEditParamDiv" style="display:none;">';
		_content_start += '<input id="compexDomainTabEditSubmit" type="submit" name="submit"/>';
		_content_start += '<input type="hidden" name="formId" value="<%=request.getParameter("formId")%>"/>';
		_content_start += '<input type="hidden" name="tabId" value="<%=request.getParameter("tabId")%>"/>';
		_content_start += '<input type="hidden" name="partitionId" value="-1"/>';
		_content_start += '<input type="hidden" name="params" value="<%=request.getParameter("params")%>"/>';
		_content_start += '<div id="subDomainIdDiv"></div>';
		_content_start += '</div>';
		var _content_end = '</form>';
		for(var i=0; i<tabs.length; i++) {
			var tab = tabs[i];
			if(tab.hasAuth == 1){
				$("#tabLiId").append("<li><a><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
				//填充tab内容
				$("#tabDivId").append("<div><font color='red'>你没有查看此选项卡的权限.</font></div>");
			}else{
				if(tab.tabType == 1){
					$("#tabLiId").append("<li><a onclick='setCompexTabForm(\"listTab"+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>");
					//填充tab内容
					$("#tabDivId").append("<div id='listTab" + tab.id + "'/>");
					$("#listTab"+tab.id).loadUrl(__basePath+"/pages/resource/compexsingleList?formId="+$("#formId").val()+"&tabId="+tab.id+"&listDiv=listTab"+tab.id+"&params=" + $("#paramsId").val());
				}else{
					var template=tab.template;
					if(template.type==0){
						//设置tab标题
						var clazz = "";
						var templateFileName = "";
						var temp = "";
						var fces = tab.formColumnExtends;
						//判断是否有上传文件
						var isUploadFile = false;
						
						for(var k=0; k<fces.length; k++) {
							var fce = fces[k];
							var fc = fce.formColumn;
							if(fc.inputType ==5) {
								isUploadFile = true;
							}
						}
						if(template.designType==0){
							templateFileName = template.templateFileName;
							//去掉文件扩展名
							temp = templateFileName.substring(0,templateFileName.indexOf("."));
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								if(buttonFlag!=undefined&&buttonFlag==true){
									$("#domainSubmit").val("buttonSubmit");
								}else{
									$("#domainSubmit").val(temp+tab.id+"Submit");
								}
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
							//填充tab内容
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							
							var urlString = __basePath+"/pages/core/" + templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&params=" + $("#paramsId").val();
							$.ajax({
								type:'post',
								url: urlString,
								async:false,
								success: function(data){
									var regS = new RegExp(temp,"g");
									var result = data.replace(regS, temp+tab.id);
									$("#" + temp+tab.id).html(result);
									//上传文件form
									if(isUploadFile) {
										$uploadForm = $("#" + temp+tab.id + "FormID");
										$uploadForm.attr("enctype","multipart/form-data");
										//$uploadForm.attr("onsubmit","return iframeCallback(this,selfIframeDialogAjaxDone);");
									}
								}
							});
						}else if(template.designType==1){
							temp="compexDomainTabEdit"+template.id;
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								if(buttonFlag!=undefined&&buttonFlag==true){
									$("#domainSubmit").val("buttonSubmit");
								}else{
									$("#domainSubmit").val(temp+tab.id+"Submit");
								}
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
							//填充tab内容
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							var regS = new RegExp("compexDomainTabEdit","g");
							var result = (_content_start+template.content+_content_end).replace(regS, temp+tab.id);
							$("#" + temp+tab.id).html(result);
							//上传文件form
							if(isUploadFile) {
								$uploadForm = $("#" + temp+tab.id + "FormID");
								$uploadForm.attr("enctype","multipart/form-data");
								//$uploadForm.attr("onsubmit","return iframeCallback(this,selfIframeDialogAjaxDone);");
							}
							
							var submitHidden="<input id='"+temp+tab.id+"Submit' type='submit' name='submit'/>";
							var formIdHidden="<input type='hidden' name='formId' value='"+$("#formId").val()+"'/>";
							var tabIdHidden="<input type='hidden' name='tabId' value='"+tab.id+"'/>";
							var partitionIdHidden="<input type='hidden' name='partitionId' value='-1'/>";
							var paramsHidden="<input type='hidden' name='params' value='"+$("#paramsId").val()+"'/>";
							$("#" + temp+tab.id + "ParamDiv").html(submitHidden+formIdHidden+tabIdHidden+partitionIdHidden+paramsHidden);
						}
						
						$("#" + temp+tab.id + "FormID").attr("onkeydown","return enterNotSubmit(event);");
						
						var sum = 0;
						var l = 0;
					
						var recordId="-1";
						for(var j=0; j<fces.length; j++) {
							var fce = fces[j];
							var fc = fce.formColumn;
							var value="";
							if(fce.value != null) {
								value = fce.value;
							}
							var name=fc.belongTable + "-" + fc.columnName;
							if(fc.columnName == 'id') {
								$("#" + temp+tab.id + "_value_t" + (j+1)).html("<input type='hidden'" + "' name='" + name + "' value='" + value + "'/>" );
								recordId = value==undefined?"-1":value;
							}else{
								if(fc.isEdit==1){
									l++;
									if($("#" + temp+tab.id + "_label_t" + l).html()==""){
										$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
									}
									$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
									var xinghao = "";
									var required = "";
									if(fc.required == 1) {
										required = "required";
										xinghao = "";//"<font color='red'>*</font>"
									}
									if(fc.hasAuth == 2 || fc.hasAuth == 3){
										if(fc.inputType == 0) {//文本框
											var textBox = fce.component;
											var width = 180;
											var height = 15;
											if(textBox != null) {
												width = textBox.textBoxWidth;
												height = textBox.textBoxHeight;
											}
											var style = " style='width:" + width + "px;height:" + height + "px;'";
											var defaultValue = fc.defaultValue;
											var defaultCurrentInfo="";
											if(defaultValue=="%username%"){
												defaultCurrentInfo = "<input type='hidden' id='"+name+"' name='"+name+"' value='"+value+"' />";
												$("#" + temp+tab.id + "_value_t" + l).html("<input maxlength='"+ Math.floor(fc.length/2) + "'" + style + " readonly='true' class='" + fc.validate +" textInput " + required + "' id='currentUserName"+name+"' value=''/>" + xinghao+defaultCurrentInfo);
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexshowUserName.action?userid="+value,
													async:false,
													success: function(data){
														var user = data;
														$("#currentUserName"+name).val(user.tblYonghuxingming);
													}
												});
											}else if(defaultValue=="%orgname%"){
												defaultCurrentInfo = "<input type='hidden' id='"+name+"' name='"+name+"' value='"+value+"' />";
												$("#" + temp+tab.id + "_value_t" + l).html("<input maxlength='"+ Math.floor(fc.length/2) + "'" + style + " readonly='true' class='" + fc.validate +" textInput " + required + "' id='currentOrgName"+name+"' value=''/>" + xinghao+defaultCurrentInfo);
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexshowOrgName.action?orgid="+value,
													async:false,
													success: function(data){
														var org = data;
														$("#currentOrgName"+name).val(org.tblName);
													}
												});
											}else{
												$("#" + temp+tab.id + "_value_t" + l).html("<input maxlength='"+ Math.floor(fc.length/2) + "'" + style + " class='" + fc.validate +" textInput " + required + "' id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
											}
										}
										if(fc.inputType == 16){//自动补齐文本框
											var autoComplete = fce.component;
											var width = 180;
											var height = 15;
											var sourceArray = [];
											if(autoComplete != null) {
												width = autoComplete.autoCompleteWidth;
												height = autoComplete.autoCompleteHeight;
												var _source = autoComplete.source;
												sourceArray = _source.split(",");
											}
											var style = " style='width:" + width + "px;height:" + height + "px;'";
											$("#" + temp+tab.id + "_value_t" + l).html("<input maxlength='"+ Math.floor(fc.length/2) + "'" + style + " class='textInput " + required + "' id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
											
										    $( "#"+name ).autocomplete({
										      source: sourceArray
										    });
										    
										}
										if(fc.inputType == 1||fc.inputType ==8) {//下拉框
											var width = 186;
											var height = 22;
											if(fc.inputType == 1){
												var combox = fce.component;
												if(combox != null) {
													width = combox.comboxWidth;
													height = combox.comboxHeight;
												}
											}else if(fc.inputType ==8){
												var searchcombox = fce.component;
												if(searchcombox != null) {
													width = searchcombox.searchComboxWidth;
												}
											}
											
											var select = "<div style='float:left;'><select id='"+name+"' name='" + name + "' style='width:"+width+"px;height:"+height+"px;'>";
											var codes = fce.codes;
											var options ="";
											if(fc.hasNull==1){
												options+="<option value='-1'>"+fc.nullName+"</option>";
											}
											for(var k=0; codes!=null&&k<codes.length; k++) {
												var selected = "";
												if(fc.hasDefaultItem==1){
													if(codes[k].id == fc.defaultSelectItem && fce.value==null) {
														selected = "selected='selected'";
													}
												}
												if(codes[k].value == fce.value) {
													selected = "selected='selected'";
												}
												options += "<option " + selected + " value='" + codes[k].value + "'>" + codes[k].text + "</option>";
											}
											select += options + "</select></div>";
											$("#" + temp+tab.id + "_value_t" + l).html(select);
											if(fc.inputType ==8){
												$("#"+name).combobox();
												$("#combobox_"+name).attr("style","width:"+width+"px;");
											}
										}
										if(fc.inputType == 2) {//文本域
											var width = 103;
											var height = 5;
											var textarea = fce.component;
											if(textarea != null) {
												width = textarea.textareaWidth;
												height = textarea.textareaHeight;
											}
											$("#" + temp+tab.id + "_value_t" + l).html("<textarea class='textInput "+required+"' id='"+name+"' name='" + name + "' rows='" + height + "' cols='" + width + "'>" + value +"</textarea>"+xinghao);
										}
										if(fc.inputType == 3){
											var radio = fce.radio;
											
											var radios="";
											var codes = fce.codes;
											for(var k=0; codes!=null&&k<codes.length; k++) {
												var checked = "";
												if(fce.value==null&&k==0){
													checked = "checked='checked'";
												}
												if(codes[k].value == fce.value) {
													checked = "checked='checked'";
												}
												radios += "<input type='radio' style='border-style: "+radio.tbl_borderStyle+";border-width: "+radio.tbl_borderWidth+"px;border-color: "+radio.tbl_borderColor+";background-color: "+radio.tbl_bgColor+";zoom:"+radio.tbl_zoom+"' name='"+name+"' value='"+codes[k].value+"'  "+checked+"/>"+codes[k].text;
											}
											$("#" + temp+tab.id + "_value_t" + l).html(radios);
										}
										if(fc.inputType == 4){
											var checkbox = fce.checkBox;
											
											var checkBoxes="";
											var codes = fce.codes;
											if(fce.value==null){
												fce.value="";
											}
											var res=fce.value.split(",");
											for(var k=0; codes!=null&&k<codes.length; k++) {
												var checked = "";
												for(var c=0;c<res.length;c++){
													if(res[c]==codes[k].value){
														checked = "checked='checked'";
														break;
													}
												}
												checkBoxes += "<input type='checkbox' style='border-style: "+checkbox.tbl_borderStyle+";border-width: "+checkbox.tbl_borderWidth+"px;border-color: "+checkbox.tbl_borderColor+";background-color: "+checkbox.tbl_bgColor+";zoom:"+checkbox.tbl_bili+"' name='"+name+"' value='"+codes[k].value+"' "+checked+"/>"+codes[k].text;
											}
											$("#" + temp+tab.id + "_value_t" + l).html(checkBoxes);
										}
										if(fc.inputType == 5){//上传文件
											var uploadbox = fce.component;
											if(op == "new"){
												$("#" + temp+tab.id + "_value_t" + l).html("<input class='"+required+"' style='width:"+uploadbox.uploadBoxWidth+"px;height:"+uploadbox.uploadBoxHeight+"px;' type='file' id='"+name+"' name='upload'/>"+xinghao );
											}else{
												$("#" + temp+tab.id + "_value_t" + l).html("<input type='file' style='width:"+uploadbox.uploadBoxWidth+"px;height:"+uploadbox.uploadBoxHeight+"px;' id='"+name+"' name='upload'/>");
											}
										}
										if(fc.inputType == 6) {//日期组件
											var date=fce.date;
											var dtwidth="";
											var dtstyle="";
											if(date!=null){
												dtwidth=date.dateWidth;
												dtstyle=date.dateStyle;
											}
											if(fc.dataType=='date') {
												if(dtstyle==""){
													dtstyle="yyyy-MM-dd";
												}
												$("#" + temp+tab.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);	
											}else {
												if(dtstyle==""){
													dtstyle="yyyy-MM-dd HH:mm:ss";
												}
												$("#" + temp+tab.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);
											}
											
										}
										if(fc.inputType == 7){
											var parentName="";
											var tableType="";
											if(value!=""){
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexshowTree.action?treeId="+fce.value+"&mgrTreeId="+fc.compexId,
													async:false,
													success: function(data){
														var tree=data;
														parentName=tree.name==null?"":tree.name;
														tableType=tree.tableType;
													}
												});
											}else{
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexgetTreeTableType.action?mgrTreeId="+fc.compexId,
													async:false,
													success: function(data){
														tableType=data.tableType;
													}
												});
											}
											
											if(tableType=="1"){
												$('#' + temp+tab.id + '_value_t' + l).html(
													"<table cellspacing='0' cellpadding='0' border='0'>" +
													"<tr><td align='left' style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'>" +
													"<input id='parentName"+name+"' type='text' class='"+required+"' readonly='true' value='"+parentName+"'/>"+xinghao+
													"<input id='parentId"+name+"' name='"+name+"' value='"+value+"' type='hidden'/>" +
													"</td>" +
													"<td style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'>" +
													"<button style='width:44px;height:20px;' type='button' class='listbutton' onclick=\"eventCompexMultiTREE("+__basePath+"'/pages/resource/tree/fetchShowTreeParamById.action?treeId="+fc.compexId+"&parentName=parentName"+name+"&parentId=parentId"+name+"')\">选择</button></td></tr></table>");
											}else{
												$('#' + temp+tab.id + '_value_t' + l).html(
													"<table cellspacing='0' cellpadding='0' border='0'>" +
													"<tr><td align='left' style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'>" +
													"<input id='parentName"+name+"' type='text' class='"+required+"' readonly='true' value='"+parentName+"'/>"+xinghao+
													"<input id='parentId"+name+"' name='"+name+"' value='"+value+"' type='hidden'/>" +
													"</td>" +
													"<td style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'>" +
													"<button style='width:44px;height:20px;' type='button' class='listbutton' onclick=\"eventCompexTREE("+__basePath+"'/pages/resource/tree/fetchShowTreeParamById.action?treeId="+fc.compexId+"&parentName=parentName"+name+"&parentId=parentId"+name+"')\">选择</button></td></tr></table>");	
											}
											
										}
										if(fc.inputType == 9){//自定义
											$("#" + temp+tab.id + "_value_t" + l).html("<div id='"+name+"Custom'></div><input type='hidden' id='"+name+"CustomInput' value='"+value+"'/>");
										}
										if(fc.inputType == 10){
											$("#" + temp+tab.id + "_value_t" + l).html("<textarea class='"+required+"' id='"+name+"' name='" + name + "' rows='"+fce.textEditor.rows+"' cols='"+fce.textEditor.cols+"'>" + value +"</textarea>"+xinghao);
											if(fce.textEditor.editorId=="xh_editor"){
												$('textarea#'+name).xheditor({
													tools:'full',
													skin:'default',
													upMultiple:false,
													upImgUrl: "/servlet/UploadFileServlet",
													upImgExt: "jpg,jpeg,gif,bmp,png"
													//onUpload:insertUpload //指定回调函数,需要自己另外在编写函数实现回调处理.
												});
											}else if(fce.textEditor.editorId=="ck_editor"){
												$('textarea#'+name).ckeditor();
											}
											
										}
										if(fc.inputType == 11){
											var passbox = fce.component;
											var width = 180;
											var height = 15;
											if(passbox != null) {
												width = passbox.passboxWidth;
												height = passbox.passboxHeight;
											}
											var style = " style='width:" + width + "px;height:" + height + "px;'";
											$("#" + temp+tab.id + "_value_t" + l).html("<input type='password' maxlength='"+ Math.floor(fc.length/2) + "'" + style + " class=' textInput " + required + "' id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
										}
										if(fc.inputType == 13){
											var contentDivID = temp+tab.id + "_value_t" + l;
											
											var caseCade=fce.codeCaseCade;
											var selectname="";
											if(caseCade.showProgression==1){
												selectname="name='"+name+"'";
											}
											$.ajax({
												type:'post',
												dataType: 'json',
												url: __basePath+"/pages/resource/compexshowCaseCade.action?topCode="+caseCade.topCode+"&showProgression="+caseCade.showProgression,
												async:false,
												success: function(data){
													var selectStr="";
													selectStr+="<select "+selectname+" style='width:"+caseCade.width+"px;' onchange='show(this,\""+contentDivID+"\",\""+name+"\",\""+caseCade.showProgression+"\",\""+caseCade.width+"\",\""+fce.value+"\")'>";
													selectStr+="<option value='-1'>请选择</option>";
													for(var ci=0;ci<data.length;ci++){
														var selected="";
														if(caseCade.showProgression==1){
															if(fce.value==data[ci].id){
																selected = "selected='selected'";
															}
														}
														selectStr+="<option "+selected+" value='"+data[ci].id+"'>"+data[ci].name+"</option>";
													}
													selectStr+="</select>";
													$("#" + temp+tab.id + "_value_t" + l).append(selectStr);
												}
											});
											
											if(fce.value!=null&&fce.value!=undefined){
												for(var si=1;si<caseCade.showProgression;si++){
													var currselect = $("#"+contentDivID+"> select:last");
													$.ajax({
														type:'post',
														dataType: 'json',
														url: __basePath+"/pages/resource/compexgetParentCodeByProg.action?progression="+(caseCade.showProgression-si)+"&value="+fce.value,
														async:false,
														success: function(data){
															var dic = data;
															currselect.val(dic.id);
														}
													});
													show(currselect,contentDivID,name,caseCade.showProgression,caseCade.width,fce.value);
												}
											}
										}
										if(fc.inputType == 12){
											$('div[title="'+fc.columnZhName+'"]').parent().parent().hide();
											$("#tabLiId").append("<li><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + fc.columnZhName + "</span></li>");
											$("#tabDivId").append('<div><div id="WebOfficeDiv" style="float:right;width:100%;height:100%;border:1px solid #CECCCD;"></div></div>');
											$.ajax({
												type:'post',
												url : 'pages/resource/compextableData.action?mainTable=bus_zichulizujian&params=tbl_isdefault:1',
												dataType: 'json',
												async: false,
												success : function(data) {
													$.each(data,function(entryIndex, entries){
														if(entries.tbl_defaultMethod == 'iWebOffice'){
															var iwebofficeOp = '1';
															if(value==''){
																value = getUnique();
																iwebofficeOp = 0;
															}
															$("#" + temp+tab.id + "ParamDiv").append('<input id="recordId" name="'+name+'" value="'+value+'"/>');
															if($('#mainTable').val() == 'sys_dayinmoban'){
																$('#WebOfficeDiv').html('<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="500" src="/pages/resource/template/print/printEdit.jsp?editType=1,0&recordId='+value+'&op='+iwebofficeOp+'"></iframe>');
															}else{
																$('#WebOfficeDiv').html('<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="500" src="/pages/third/office/officeEdit.jsp?fileType='+fc.officeMode+'&editType=1,0&recordId='+value+'&op='+iwebofficeOp+'"></iframe>');
															}
														}else{
															if($('#mainTable').val() == 'sys_dayinmoban'){
																$('#WebOfficeDiv').html('<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="500" src="/pages/resource/template/print/printEioEdit.jsp"></iframe>');
															}else{
																$('#WebOfficeDiv').html('<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="500" src="/pages/resource/template/print/printEioEdit.jsp"></iframe>');
															}
														}
													})
												}
											});
											
											
											
										}
										if(fc.inputType == 14){
											var uploadify = fce.uploadify;
											var buttonText = "";
											var multi = true;
											var auto = false;
											var fileSizeLimit = "10MB";
											var uploadLimit = 5;
											var fileTypeExts = "*.*";
											
											if(uploadify!=null){
												buttonText = uploadify.buttonText;
												multi = uploadify.multi==1?true:false;
												auto = uploadify.autoUpload==1?true:false;
												if(uploadify.fileSizeLimit!=null){
													fileSizeLimit = uploadify.fileSizeLimit+"MB";
												}
												if(uploadify.queueSizeLimit!=0){
													uploadLimit = uploadify.queueSizeLimit;
												}
												if(uploadify.fileExt){
													fileTypeExts = uploadify.fileExt;
												}
												
											}
											
											$("#" + temp+tab.id + "_value_t" + l).html("<div id='"+name+"fileQueue'></div><input type='file' id='"+name+"' class='uploadify' style='display: none;'/><input id='uploadify"+name+"' type='hidden' name='"+name+"' value='"+value+"'>");
											if(recordId!="-1"){
												$.ajax({
													type:'post',
													url: __basePath+"/pages/resource/uploadifyshowCompleteFile.action?fileIds="+value+"&recordId="+recordId,
													async:false,
													success: function(data){
														$("#"+name+"fileQueue").html(data);
													}
												});
											}
											$("#"+name).uploadify({
									        	'buttonText' : buttonText,
									        	'formData' : {'model' : fc.belongTable , 'columnname' : fc.columnName , 'createby' : userid, 'recordColumn' : fc.recordColumn==null?'':fc.recordColumn, 'formId' : form.id},
									        	'method' : 'get',
									            'swf'      : __basePath+'/js/uploadify/uploadify.swf',
									            'uploader' : __basePath+'/CompexServlet',
									            'multi'    : multi,
									            'queueID'  : name+'fileQueue',
									            'auto'     : auto,
									            'fileSizeLimit' : fileSizeLimit,
									            'queueSizeLimit' : 999,
									            'fileTypeExts' : fileTypeExts,
									            'removeCompleted' : false,
									            'uploadLimit' : uploadLimit,
									            'height' : 18,
									            'width' : 80,
									            'onUploadSuccess' : function(file, data, response) {
									        		var atta=$.parseJSON(data);
									        		var uploadifyValue = $("#uploadify"+atta.name).val();
									        		if(uploadifyValue!=""){
									        			$("#uploadify"+atta.name).val(uploadifyValue+","+atta.attachmentId);
									        		}else{
									        			$("#uploadify"+atta.name).val(atta.attachmentId);
									        		}
										        },
										        'onCancel' : function(file) {
										           	 
										        },
										        'onUploadStart' : function(file) {
										        	
										        }
									        });
											//<p><button type='button' name='buttonUpload' class='listbutton' onclick='uploadfile(\""+name+"\")'>上传</button>&nbsp;<button type='button' name='buttonCancleUpload' class='listbutton' style='width: 70px;' onclick='cancelupload(\""+name+"\")'>取消上传</button></p>
										}
										if(fc.inputType == 15){
											var showValue="";
											var hideValue="";
											if(value!=""){
												hideValue=value;
												$.ajax({
												type:'post',
												dataType: 'json',
												url: __basePath+"/pages/resource/personChoise/depmPersonChange.action?choisePerson="+value,
												async:false,
												success: function(data){
													var _name='';
													$.each(data,function (entryIndex,entry){
														_name += entry.tblXingming+";";
													})
													showValue = _name;
												}
											});
											}
											$("#" + temp+tab.id + "_value_t" + l).html("<textarea id='person_"+name+"' rows='5' cols='103' disabled>" + showValue +"</textarea><input type='hidden' id='"+name+"' name='" + name + "' value='"+hideValue+"'><br/><button  id='personSelectDialog' type='button' class='listbutton' onclick=\"eventCompexPersonTREE("+__basePath+"'/pages/resource/personChoise/page.action?personChoiseName=person_"+name+"&personChoiseId="+name+"')\">选择</button>");
										}
									}else{
										$("#" + temp+tab.id + "_value_t" + l).html("<font color='red'>无权限</font>");
									}
									sum += 1;
								}
							}
						}
						$("#" + temp+tab.id + "_tr_t0").hide();
						if(template.designType==0){
							eval(temp+tab.id + "HideTr(" + sum + ")");
						}
						initTab();
					}else if(template.type==1){
						//设置tab标题
						var clazz = "";
						var templateFileName;
						//去掉文件扩展名
						var temp;
						
						if(template.designType==0){
							templateFileName = template.templateFileName;
							
							//去掉文件扩展名
							temp = templateFileName.substring(0,templateFileName.indexOf("."));
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								if(buttonFlag!=undefined&&buttonFlag==true){
									$("#domainSubmit").val("buttonSubmit");
								}else{
									$("#domainSubmit").val(temp+tab.id+"Submit");
								}
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
							//填充tab框架
							var fces = tab.formColumnExtends;
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							
							var urlString = __basePath+"/pages/core/" + templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&params=" + $("#paramsId").val();
							
							$.ajax({
								type:'post',
								url: urlString,
								async:false,
								success: function(data){
									var regS = new RegExp(temp,"g");
									var result = data.replace(regS, temp+tab.id);
									result=result.replace("partitionForm",tab.id+"_partitionForm");
									result=result.replace("partitionList",tab.id+"_partitionList");
									$("#" + temp+tab.id).html(result);
								}
							});
						}else if(template.designType==1){
							temp="CombinationStandard"+template.id;
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								if(buttonFlag!=undefined&&buttonFlag==true){
									$("#domainSubmit").val("buttonSubmit");
								}else{
									$("#domainSubmit").val(temp+tab.id+"Submit");
								}
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>");
							//填充tab框架
							var fces = tab.formColumnExtends;
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							
							var regS = new RegExp(temp,"g");
							var result = template.content.replace(regS, temp+tab.id);
							result=result.replace("partitionForm",tab.id+"_partitionForm");
							result=result.replace("partitionList",tab.id+"_partitionList");
							$("#" + temp+tab.id).html(result);
							
						}
						
						//遍历tab内 所有的分区,填充分区模板
						var formOrder=1;//操作区顺序
						var listOrder=1;//列表区顺序
						var partitions=tab.partitions;
						for(var p=0;p<partitions.length;p++){
							var partition = partitions[p];
							if(partition.partitionType=="0"){
								var ptemplate=partition.template;
								var ptemplateFileName;
								if(ptemplate.designType==0){
									ptemplateFileName=partition.templateFileName.substring(0,partition.templateFileName.indexOf("."));
									var formUrlString = __basePath+"/pages/core/"+partition.templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&partitionId="+partition.id+"&params=" + $("#paramsId").val();
									$.ajax({
										type:'post',
										url: formUrlString,
										async:false,
										success: function(data){
											var regS = new RegExp(ptemplateFileName,"g");
											var result = data.replace(regS, ptemplateFileName+tab.id+partition.id);
											$("#"+tab.id+"_partitionForm"+formOrder).html(result);
										}
									});
								}else if(ptemplate.designType==1){
									ptemplateFileName="compexDomainTabEdit"+ptemplate.id;
									var regS = new RegExp("compexDomainTabEdit","g");
									var result = (_content_start+ptemplate.content+_content_end).replace(regS, ptemplateFileName+tab.id+partition.id);
									$("#"+tab.id+"_partitionForm"+formOrder).html(result);
									var submitHidden="<input id='"+ptemplateFileName+tab.id+partition.id+"Submit' type='submit' name='submit'/>";
									var formIdHidden="<input type='hidden' name='formId' value='"+$("#formId").val()+"'/>";
									var tabIdHidden="<input type='hidden' name='tabId' value='"+tab.id+"'/>";
									var partitionIdHidden="<input type='hidden' name='partitionId' value='"+partition.id+"'/>";
									var paramsHidden="<input type='hidden' name='params' value='"+$("#paramsId").val()+"'/>";
									$("#" + ptemplateFileName+tab.id+partition.id + "ParamDiv").html(submitHidden+formIdHidden+tabIdHidden+partitionIdHidden+paramsHidden);
								}
								
								if(partition.formColumnExtends[0]==undefined){
									break;
								}
								var model = partition.formColumnExtends[0].formColumn.belongTable;
								$("#"+tab.id+"_partitionForm"+formOrder).append("<input type='hidden' id='"+tab.id+"_partitionForm"+formOrder+"Model' value='"+model+"'/>" );
								$("#"+tab.id+"_partitionForm"+formOrder).append("<input type='hidden' id='"+tab.id+"_partitionForm"+formOrder+"Pid' value='"+partition.id+"'/>" );
								
								//分区的所有按钮
								var pformButtons=partition.formButtons;
								$("#"+tab.id+"_partitionForm"+formOrder).append("<div style='margin-top:5px;' align='right' id='" + ptemplateFileName+tab.id+partition.id+"Button'/>");
								for(var pf=0;pf<pformButtons.length;pf++){
									var pformButton = pformButtons[pf];
									if(pformButton.hasAuth=="0"){
										if(pformButton.buttonType=="1"){
											var pformButtonGroup=pformButton.buttonGroup;
											for(var pbg=0;pbg<pformButtonGroup.buttonAndGroups.length;pbg++){
												var buttonAndGroup=pformButtonGroup.buttonAndGroups[pbg];
												$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+buttonAndGroup.button.buttonBM+"' class='listbutton' style='width:"+buttonAndGroup.button.buttonWidth+"px;height:"+buttonAndGroup.button.buttonHeight+"px;background: "+buttonAndGroup.button.buttonBackGroundColor+";border-color: "+buttonAndGroup.button.buttonBorderColor+";' onclick='compex"+buttonAndGroup.button.buttonBM+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");' "+buttondis+">"+"<font color='"+buttonAndGroup.button.buttonNameFontColor+"' style='font-family:"+buttonAndGroup.button.buttonNameFontStyle+";font-size: "+buttonAndGroup.button.buttonNameFontSize+"px;'>"+buttonAndGroup.button.buttonName+"</font></button>");
											}
										}else if(pformButton.buttonType=="0"){
											//var pformButton=pformButton.button;
											$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+pformButton.funcName+"' class='listbutton' style='width:"+pformButton.button.buttonWidth+"px;height:"+pformButton.button.buttonHeight+"px;background: "+pformButton.button.buttonBackGroundColor+";border-color: "+pformButton.button.buttonBorderColor+";' onclick='compex"+pformButton.funcName+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");' "+buttondis+">"+"<font color='"+pformButton.button.buttonNameFontColor+"' style='font-family:"+pformButton.button.buttonNameFontStyle+";font-size: "+pformButton.button.buttonNameFontSize+"px;'>"+pformButton.showName+"</font></button>");
										}
									}else if(pformButton.hasAuth=="1"){
										if(pformButton.buttonType=="1"){
											var pformButtonGroup=pformButton.buttonGroup;
											for(var pbg=0;pbg<pformButtonGroup.buttonAndGroups.length;pbg++){
												var buttonAndGroup=pformButtonGroup.buttonAndGroups[pbg];
												$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+buttonAndGroup.button.buttonBM+"' class='listbuttonDisable' disabled='disabled' style='width:"+buttonAndGroup.button.buttonWidth+"px;height:"+buttonAndGroup.button.buttonHeight+"px;background: "+buttonAndGroup.button.buttonBackGroundColor+";border-color: "+buttonAndGroup.button.buttonBorderColor+";' onclick='compex"+buttonAndGroup.button.buttonBM+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+buttonAndGroup.button.buttonNameFontColor+"' style='font-family:"+buttonAndGroup.button.buttonNameFontStyle+";font-size: "+buttonAndGroup.button.buttonNameFontSize+"px;'>"+buttonAndGroup.button.buttonName+"</font></button>");
											}
										}else if(pformButton.buttonType=="0"){
											//var pformButton=pformButton.button;
											$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+pformButton.funcName+"' class='listbuttonDisable' disabled='disabled' style='width:"+pformButton.button.buttonWidth+"px;height:"+pformButton.button.buttonHeight+"px;background: "+pformButton.button.buttonBackGroundColor+";border-color: "+pformButton.button.buttonBorderColor+";' onclick='compex"+pformButton.funcName+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+pformButton.button.buttonNameFontColor+"' style='font-family:"+pformButton.button.buttonNameFontStyle+";font-size: "+pformButton.button.buttonNameFontSize+"px;'>"+pformButton.showName+"</font></button>");
										}
									}
									
								}
								
								var pfces=partition.formColumnExtends;
								
								var sum = 0;
								var l = 0;
								for(var j=0; j<pfces.length; j++) {
									var fce = pfces[j];
									var fc = fce.formColumn;
									
									var value="";
									if(fce.value != null) {
										value = fce.value;
									}
									var name=fc.belongTable + "-" + fc.columnName;
									
									if(fc.columnName == 'id') {
										$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + (j+1)).html("<input type='hidden'" + "' name='" + name + "' value='" + value + "'/>" );
									}else{
										l++;
										if($("#" + ptemplateFileName+tab.id+partition.id + "_label_t" + l).html()==""){
											$("#" + ptemplateFileName+tab.id+partition.id + "_label_t" + l).html(fc.columnZhName);
										}
										$("#" + ptemplateFileName+tab.id+partition.id + "_label_t" + l).attr("title",fc.colComment);
										var required = "";
										var xinghao = "";
										if(fc.required == 1) {
											required = "required";
											xinghao = "";//"<font color='red'>*</font>"
										}
										if(fc.hasAuth == 2 || fc.hasAuth ==3){
											if(fc.inputType == 0) {//文本框
												var textBox = fce.component;
												var width = 180;
												var height = 15;
												if(textBox != null) {
													width = textBox.textBoxWidth;
													height = textBox.textBoxHeight;
												}
												var style = " style='width:" + width + "px;height:" + height + "px;'";
													
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input maxlength='"+ Math.floor(fc.length/2) + "' " + style + " class='" + fc.validate +" textInput " + required + "' name='" + name + "' value='" + value + "'/>" + xinghao);	
											}
											if(fc.inputType == 16){//自动补齐文本框
												var autoComplete = fce.component;
												var width = 180;
												var height = 15;
												var sourceArray = [];
												if(autoComplete != null) {
													width = autoComplete.autoCompleteWidth;
													height = autoComplete.autoCompleteHeight;
													var _source = autoComplete.source;
													sourceArray = _source.split(",");
												}
												var style = " style='width:" + width + "px;height:" + height + "px;'";
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input maxlength='"+ Math.floor(fc.length/2) + "'" + style + " class='textInput " + required + "' id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
											    $( "#"+name ).autocomplete({
											      source: sourceArray
											    });
											    
											}
											if(fc.inputType == 1||fc.inputType == 8) {//下拉框
												var width = 186;
												if(fc.inputType == 1){
													var combox = fce.component;
													if(combox != null) {
														width = combox.comboxWidth;
													}
												}else if(fc.inputType ==8){
													var searchcombox = fce.component;
													if(combox != null) {
														width = searchcombox.searchComboxWidth;
													}
												}
												var select = "<select id='queryPselect"+tab.id+partition.id+j+"' name='" + name + "' style='width:"+width+"px;'>";
												var codes = fce.codes;
												var options ="";
												if(fc.hasNull==1){
													options+="<option  value='-1'>"+fc.nullName+"</option>";
												}
												for(var k=0; codes!=null&&k<codes.length; k++) {
													var selected = "";
													if(fc.hasDefaultItem==1){
														if(codes[k].id == fc.defaultSelectItem && fce.value==null) {
															selected = "selected='selected'";
														}
													}
													if(codes[k].value == fce.value) {
														selected = "selected='selected'";
													}
													options += "<option " + selected + " value='" + codes[k].value + "'>" + codes[k].text + "</option>";
												}
												select += options + "</select>";
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html(select);
												if(fc.inputType ==8){
													$("#queryPselect"+tab.id+partition.id+j).combobox();
												}
											}
											if(fc.inputType == 3){
												var radio = fce.radio;
												var radios="";
												var codes = fce.codes;
												for(var k=0; codes!=null&&k<codes.length; k++) {
													var checked = "";
													if(fce.value==null&&k==0){
														checked = "checked='checked'";
													}
													if(codes[k].value == fce.value) {
														checked = "checked='checked'";
													}
													radios += "<input type='radio' style='border-style: "+radio.tbl_borderStyle+";border-width: "+radio.tbl_borderWidth+"px;border-color: "+radio.tbl_borderColor+";background-color: "+radio.tbl_bgColor+";zoom:"+radio.tbl_zoom+"' name='"+name+"' value='"+codes[k].value+"'  "+checked+"/>"+codes[k].text;
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html(radios);
											}
											if(fc.inputType == 4){
												var checkbox = fce.checkbox;
												var checkBoxes="";
												var codes = fce.codes;
												if(fce.value==null){
													fce.value="";
												}
												var res=fce.value.split(",");
												for(var k=0; codes!=null&&k<codes.length; k++) {
													var checked = "";
													for(var c=0;c<res.length;c++){
														if(res[c]==codes[k].value){
															checked = "checked='checked'";
															break;
														}
													}
													checkBoxes += "<input type='checkbox' style='border-style: "+checkbox.tbl_borderStyle+";border-width: "+checkbox.tbl_borderWidth+"px;border-color: "+checkbox.tbl_borderColor+";background-color: "+checkbox.tbl_bgColor+";zoom:"+checkbox.tbl_bili+"' name='"+name+"' value='"+codes[k].value+"' "+checked+"/>"+codes[k].text;
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html(checkBoxes);
											}
											if(fc.inputType == 2) {//文本域
												var width = 103;
												var height = 5;
												var textarea = fce.component;
												if(textarea != null) {
													width = textarea.textareaWidth;
													height = textarea.textareaHeight;
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<textarea class='textInput "+required+"' name='" + name + "' rows='" + height + "' cols='" + width + "'>" + value +"</textarea>"+xinghao);
											}
											if(fc.inputType == 6) {//日期组件
												var date=fce.date;
												var dtwidth="";
												var dtstyle="";
												if(date!=null){
													dtwidth=date.dateWidth;
													dtstyle=date.dateStyle;
												}
												if(fc.dataType=='date') {
													if(dtstyle==""){
														dtstyle="yyyy-MM-dd";
													}
													$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);	
												}else {
													if(dtstyle==""){
														dtstyle="yyyy-MM-dd HH:mm:ss";
													}
													$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);
												}
												
											}
											if(fc.inputType == 7){
												var parentName="";
												if(value!=""){
													$.ajax({
														type:'post',
														dataType: 'json',
														url: __basePath+"/pages/resource/compexshowTree.action?treeId="+fce.value+"&mgrTreeId="+fc.compexId,
														async:false,
														success: function(data){
															var tree=data;
															parentName=tree.name;
														}
													});
												}
												$('#' + ptemplateFileName+tab.id+partition.id + '_value_t' + l).html("<table cellspacing='0' cellpadding='0' border='0'><tr><td align='left' style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'><input id='parentName"+name+"' type='text' class='"+required+"' readonly='true' value='"+parentName+"'/>"+xinghao+"<input id='parentId"+name+"' name='"+name+"' value='"+value+"' type='hidden'/></td><td style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'><button style='width:44px;height:20px;' type='button' class='listbutton' onclick=\"eventCompexTREE("+__basePath+"'/pages/resource/tree/fetchShowTreeParamById.action?treeId="+fc.compexId+"&parentName=parentName"+name+"&parentId=parentId"+name+"')\">选择</button></td></tr></table>");
											}
											if(fc.inputType ==9){//自定义
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<div id='"+name+"Custom'></div>");
											}
											if(fc.inputType == 10){
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<textarea name='" + name + "' rows='"+fce.textEditor.rows+"' cols='"+fce.textEditor.cols+"'>" + value +"</textarea>");
												if(fce.textEditor.editorId=="xh_editor"){
													$('textarea#'+name).xheditor({
														tools:'full',
														skin:'default',
														upMultiple:false,
														upImgUrl: "/servlet/UploadFileServlet",
														upImgExt: "jpg,jpeg,gif,bmp,png"
														//onUpload:insertUpload //指定回调函数,需要自己另外在编写函数实现回调处理.
													});
												}else if(fce.textEditor.editorId=="ck_editor"){
													$('textarea#'+name).ckeditor();
												}
											}
											if(fc.inputType == 11){
												var passbox = fce.component;
												var width = 180;
												var height = 15;
												if(passbox != null) {
													width = passbox.passboxWidth;
													height = passbox.passboxHeight;
												}
												var style = " style='width:" + width + "px;height:" + height + "px;'";
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input type='password' maxlength='"+ Math.floor(fc.length/2) + "'" + style + " id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
											}								
											if(fc.inputType == 13){
												var contentDivID = ptemplateFileName+tab.id+partition.id + "_value_t" + l;
												
												var caseCade=fce.codeCaseCade;
												var selectname="";
												if(caseCade.showProgression==1){
													selectname="name='"+name+"'";
												}
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexshowCaseCade.action?topCode="+caseCade.topCode+"&showProgression="+caseCade.showProgression,
													async:false,
													success: function(data){
														var selectStr="";
														selectStr+="<select "+selectname+" style='width:"+caseCade.width+"px;' onchange='show(this,\""+contentDivID+"\",\""+name+"\",\""+caseCade.showProgression+"\",\""+caseCade.width+"\",\""+fce.value+"\")'>";
														selectStr+="<option value='-1'>请选择</option>";
														for(var ci=0;ci<data.length;ci++){
															var selected="";
															if(caseCade.showProgression==1){
																if(fce.value==data[ci].id){
																	selected = "selected='selected'";
																}
															}
															selectStr+="<option "+selected+" value='"+data[ci].id+"'>"+data[ci].name+"</option>";
														}
														selectStr+="</select>";
														$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).append(selectStr);
													}
												});
												
												if(fce.value!=null&&fce.value!=undefined){
													for(var si=1;si<caseCade.showProgression;si++){
														var currselect = $("#"+contentDivID+"> select:last");
														$.ajax({
															type:'post',
															dataType: 'json',
															url: __basePath+"/pages/resource/compexgetParentCodeByProg.action?progression="+(caseCade.showProgression-si)+"&value="+fce.value,
															async:false,
															success: function(data){
																var dic = data;
																currselect.val(dic.id);
															}
														});
														show(currselect,contentDivID,name,caseCade.showProgression,caseCade.width,fce.value);
													}
												}
											}
											if(fc.inputType == 14){
												var uploadify = fce.uploadify;
												var buttonText = "";
												var multi = true;
												var auto = false;
												var fileSizeLimit = "10MB";
												var uploadLimit = 5;
												var fileTypeExts = "*.*";
												
												if(uploadify!=null){
													buttonText = uploadify.buttonText;
													multi = uploadify.multi==1?true:false;
													auto = uploadify.autoUpload==1?true:false;
													if(uploadify.fileSizeLimit!=null){
														fileSizeLimit = uploadify.fileSizeLimit+"MB";
													}
													if(uploadify.queueSizeLimit!=0){
														uploadLimit = uploadify.queueSizeLimit;
													}
													if(uploadify.fileExt){
														fileTypeExts = uploadify.fileExt;
													}
													
												}
												
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<div id='"+name+"fileQueue'></div><input type='file' id='"+name+"' style='display: none;'/><input id='uploadify"+name+"' type='hidden' name='"+name+"' value='"+value+"'>");
												if(recordId!="-1"){
													$.ajax({
														type:'post',
														url: __basePath+"/pages/resource/uploadifyshowCompleteFile.action?fileIds="+value+"&recordId="+recordId,
														async:false,
														success: function(data){
															$("#"+name+"fileQueue").html(data);
														}
													});
												}
												$("#"+name).uploadify({
										        	'buttonText' : buttonText,
										        	'formData' : {'model' : fc.belongTable , 'columnname' : fc.columnName , 'createby' : userid, 'recordColumn' : fc.recordColumn==null?'':fc.recordColumn},
										        	'method' : 'get',
										            'swf'      : __basePath+'/js/uploadify/uploadify.swf',
										            'uploader' : __basePath+'/CompexServlet',
										            'multi'    : multi,
										            'queueID'  : name+'fileQueue',
										            'auto'     : auto,
										            'fileSizeLimit' : fileSizeLimit,
										            'queueSizeLimit' : 999,
										            'fileTypeExts' : fileTypeExts,
										            'removeCompleted' : false,
										            'uploadLimit' : uploadLimit,
										            'height' : 18,
										            'width' : 80,
										            'onUploadSuccess' : function(file, data, response) {
										        		var atta=$.parseJSON(data);
										        		var uploadifyValue = $("#uploadify"+atta.name).val();
										        		if(uploadifyValue!=""){
										        			$("#uploadify"+atta.name).val(uploadifyValue+","+atta.attachmentId);
										        		}else{
										        			$("#uploadify"+atta.name).val(atta.attachmentId);
										        		}
											        },
											        'onCancel' : function(file) {
											           	 
											        },
											        'onUploadStart' : function(file) {
											        	
											        }
										        });
												
												//<p><button type='button' name='buttonUpload' class='listbutton' onclick='uploadfile(\""+name+"\")'>上传</button>&nbsp;<button type='button' name='buttonCancleUpload' class='listbutton' style='width: 70px;' onclick='cancelupload(\""+name+"\")'>取消上传</button></p>
											}
											if(fc.inputType == 15){
												var showValue="";
												var hideValue="";
												if(value!=""){
													hideValue=value;
													$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/personChoise/depmPersonChange.action?choisePerson="+value,
													async:false,
													success: function(data){
														var _name='';
														$.each(data,function (entryIndex,entry){
															_name += entry.tblXingming+";";
														})
														showValue = _name;
													}
												});
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<textarea id='person_"+name+"' rows='5' cols='103' disabled>" + showValue +"</textarea><input type='hidden' id='"+name+"' name='" + name + "' value='"+hideValue+"'><br/><button  id='personSelectDialog' type='button' class='listbutton' onclick=\"eventCompexPersonTREE("+__basePath+"'/pages/resource/personChoise/page.action?personChoiseName=person_"+name+"&personChoiseId="+name+"')\">选择</button>");
											}
		
										}else{
											$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<font color='red'>无权限</font>");
										}
										
										sum += 1;
									}
								}
								formOrder+=1;
								$("#" + ptemplateFileName+tab.id+partition.id + "_tr_t0").hide();
								if(ptemplate.designType==0){
									eval(ptemplateFileName+tab.id+partition.id + "HideTr(" + sum + ")");
								}
								initTab();
							}else if(partition.partitionType=="1"){
								$("#"+tab.id+"_partitionList"+listOrder).loadUrl(__basePath+"/pages/resource/compexlistsub?op=edit&formId="+$("#formId").val()+"&tabId=" + tab.id+"&partitionId="+partition.id+"&partitionListDiv="+$("#"+tab.id+"_partitionList"+listOrder).attr("id")+"&params=" + $("#paramsId").val());
								listOrder+=1;
							}
						}
					}
				}
			}
		}
	}
	
	function compexViewJson(urlString) {
		$.ajax({
			async: false,
			type:'post',
			url: urlString,
			dataType: 'json',
			success: function(data){
				viewBuildPage(data);
			}
		});
	}

	function viewBuildPage(form) {
		$("#mainTable").val(form.tableName);
		var tabs = form.tabs;
		var _content_start = '<form id="compexDomainTabEditFormID" action="'+__basePath+'/pages/resource/'+form.simpleModel+'compexsave.action" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);">';
		_content_start += '<div  id="compexDomainTabEditParamDiv" style="display:none;">';
		_content_start += '<input id="compexDomainTabEditSubmit" type="submit" name="submit"/>';
		_content_start += '<input type="hidden" name="formId" value="<%=request.getParameter("formId")%>"/>';
		_content_start += '<input type="hidden" name="tabId" value="<%=request.getParameter("tabId")%>"/>';
		_content_start += '<input type="hidden" name="partitionId" value="-1"/>';
		_content_start += '<input type="hidden" name="params" value="<%=request.getParameter("params")%>"/>';
		_content_start += '<div id="subDomainIdDiv"></div>';
		_content_start += '</div>';
		var _content_end = '</form>';
		for(var i=0; i<tabs.length; i++) {
			var tab = tabs[i];
			if(tab.hasAuth == 1){
				$("#tabLiId").append("<li><a><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
				//填充tab内容
				$("#tabDivId").append("<div><font color='red'>你没有查看此选项卡的权限.</font></div>");
			}else{
				if(tab.tabType == 1){
					$("#tabLiId").append("<li><a onclick='setCompexTabForm(\"listTab"+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
					//填充tab内容
					$("#tabDivId").append("<div id='listTab" + tab.id + "'/>");
					$("#listTab"+tab.id).loadUrl(__basePath+"/pages/resource/compexsingleList?formId="+$("#formId").val()+"&tabId="+tab.id+"&listDiv=listTab"+tab.id+"&params=" + $("#paramsId").val());
				}else{
					var template=tab.template;
					if(template.type==0){
						//设置tab标题
						var clazz = "";
						var templateFileName = "";
						var temp = "";
						var fces;
						if(template.designType==0){
							templateFileName = template.templateFileName;
							//去掉文件扩展名
							temp = templateFileName.substring(0,templateFileName.indexOf("."));
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								$("#domainSubmit").val(temp+tab.id+"Submit");
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
							//填充tab内容
							fces = tab.formColumnExtends;
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							
							var urlString = __basePath+"/pages/core/" + templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&params=" + $("#paramsId").val();
							$.ajax({
								type:'post',
								url: urlString,
								async:false,
								success: function(data){
									var regS = new RegExp(temp,"g");
									var result = data.replace(regS, temp+tab.id);
									$("#" + temp+tab.id).html(result);
								}
							});
						}else if(template.designType==1){
							temp="compexDomainTabEdit"+template.id;
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								$("#domainSubmit").val(temp+tab.id+"Submit");
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
							//填充tab内容
							fces = tab.formColumnExtends;
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							var regS = new RegExp("compexDomainTabEdit","g");
							var result = (_content_start+template.content+_content_end).replace(regS, temp+tab.id);
							$("#" + temp+tab.id).html(result);
		
							var submitHidden="<input id='"+temp+tab.id+"Submit' type='submit' name='submit'/>";
							var formIdHidden="<input type='hidden' name='formId' value='"+$("#formId").val()+"'/>";
							var tabIdHidden="<input type='hidden' name='tabId' value='"+tab.id+"'/>";
							var partitionIdHidden="<input type='hidden' name='partitionId' value='-1'/>";
							var paramsHidden="<input type='hidden' name='params' value='"+$("#paramsId").val()+"'/>";
							$("#" + temp+tab.id + "ParamDiv").html(submitHidden+formIdHidden+tabIdHidden+partitionIdHidden+paramsHidden);
							
						}
						
						if(i==0){//追加模板（用于显示查看界面需显示的字段）
							var cols=template.tds;//模板列数
							var trCount=0;//应追加的行数
							var viewCount=0;//只在查看页面显示的字段数
							var editCount=0;//编辑页面显示的字段数
							
							for(var f=0;f<fces.length;f++){
								var fce = fces[f];
								var fc = fce.formColumn;
								if(fc.isEdit==1){
									editCount+=1;
								}
								if(fc.isEdit==0&&fc.isView==1){
									viewCount+=1;
								}
							}
							var objform=$("#"+temp+tab.id+"FormID table tbody");
							
							//-----------------------------------------------------------start
							if(viewCount%(cols/2)==0){
								trCount=viewCount/(cols/2);
								for(var tc=0;tc<trCount;tc++){
									var _objformTd = "";
									_objformTd += "<tr>";
									for(var tdc=0;tdc<cols/2;tdc++){
										_objformTd += "<td height='25px' align='left' class='Input_Table_Label'><div id='"+temp+tab.id + "_label_t" + (editCount)+"'></div></td><td height='25px' align='left'><div id='"+temp+tab.id + "_value_t" + (editCount)+"'></div></td>";
										editCount+=1;
									}
									_objformTd += "</tr>";
									objform.append(_objformTd);
								}
							}else{
								trCount=Math.floor(viewCount/(cols/2)+1);
								if(viewCount<cols/2){
									var _appendTd = "";
									_appendTd += "<tr>";
									for(var vc=0;vc<viewCount;vc++){
										if(vc==viewCount-1){
											_appendTd += "<td height='25px' align='left' class='Input_Table_Label'><div id='"+temp+tab.id + "_label_t" + (editCount)+"'></div></td><td height='25px' align='left' colspan='"+((cols/2)-viewCount)*2+1+"'><div id='"+temp+tab.id + "_value_t" + (editCount)+"'></div></td>";
										}else{
											_appendTd += "<td height='25px' align='left' class='Input_Table_Label'><div id='"+temp+tab.id + "_label_t" + (editCount)+"'></div></td><td height='25px' align='left'><div id='"+temp+tab.id + "_value_t" + (editCount)+"'></div></td>";
										}
										editCount+=1;
									}
									_appendTd += "</tr>";
									objform.append(_appendTd);
								}else{
									for(var tc=0;tc<trCount;tc++){
										var _appendTd = "";
										_appendTd += "<tr>";
										if(tc==trCount-1){
											for(var vc=0;vc<viewCount%(cols/2);vc++){
												if(vc==viewCount%(cols/2)-1){
													_appendTd += "<td height='25px' align='left' class='Input_Table_Label'><div id='"+temp+tab.id + "_label_t" + (editCount)+"'></div></td><td height='25px' align='left' colspan='"+((cols/2)-viewCount%(cols/2))*2+1+"'><div id='"+temp+tab.id + "_value_t" + (editCount)+"'></div></td>";
												}else{
													_appendTd += "<td height='25px' align='left' class='Input_Table_Label'><div id='"+temp+tab.id + "_label_t" + (editCount)+"'></div></td><td height='25px' align='left'><div id='"+temp+tab.id + "_value_t" + (editCount)+"'></div></td>";
												}
												editCount+=1;
											}
										}else{
											for(var tdc=0;tdc<cols/2;tdc++){
												_appendTd += "<td height='25px' align='left' class='Input_Table_Label'><div id='"+temp+tab.id + "_label_t" + (editCount)+"'></div></td><td height='25px' align='left'><div id='"+temp+tab.id + "_value_t" + (editCount)+"'></div></td>";
												editCount+=1;
											}
										}
										_appendTd += "</tr>";
										objform.append(_appendTd);
									}
								}
							}
							//-----------------------------------------------------------end
						}
						
						var sum = 0;
						var l = 0;
						for(var j=0; j<fces.length; j++) {
							var fce = fces[j];
							var fc = fce.formColumn;
							var value="";
							if(fce.value != null) {
								if(fc.inputType == 0){
									var defaultValue = fc.defaultValue;
									var defaultCurrentInfo="";
									if(defaultValue=="%username%"){
										$.ajax({
											type:'post',
											dataType: 'json',
											url: __basePath+"/pages/resource/compexshowUserName.action?userid="+fce.value,
											async:false,
											success: function(data){
												var user = data;
												value = user.tblYonghuxingming;
											}
										});
									}else if(defaultValue=="%orgname%"){
										$.ajax({
											type:'post',
											dataType: 'json',
											url: __basePath+"/pages/resource/compexshowOrgName.action?orgid="+fce.value,
											async:false,
											success: function(data){
												var org = data;
												value = org.tblName;
											}
										});
									}else{
										value = fce.value;
									}
								}else if(fc.inputType == 1||fc.inputType==3||fc.inputType==8) {
									var codes = fce.codes;
									for(var c=0;c<codes.length;c++) {
										if(fce.value==codes[c].value) {
											value = codes[c].text;
										}
									}
									if(fce.value == -1) {
										value = fc.nullName;
									}
								}else if(fc.inputType == 4){
									if(fce.value==null){
										fce.value="";
									}
									var vcs=fce.value.split(",");
									
									var codes = fce.codes;
									for(var vc=0;vc<vcs.length;vc++){
										for(var c=0;c<codes.length;c++) {
											if(vcs[vc]==codes[c].value) {
												if(vc==0){
													value += codes[c].text;
												}else{
													value += ","+codes[c].text;
												}
											}
										}
									}
									
								}else if(fc.inputType == 7){
									if(fce.value!=""){
										$.ajax({
											type:'post',
											dataType: 'json',
											url: __basePath+"/pages/resource/compexshowTree.action?treeId="+fce.value+"&mgrTreeId="+fc.compexId,
											async:false,
											success: function(data){
												var tree=data;
												value=tree.name==null?"":tree.name;
											}
										});
									}
								}else if(fc.inputType == 15){
									$.ajax({
										type:'post',
										dataType: 'json',
										url: __basePath+"/pages/resource/personChoise/depmPersonChange.action?choisePerson="+fce.value,
										async:false,
										success: function(data){
											var _name='';
											$.each(data,function (entryIndex,entry){
												_name += entry.tblXingming+";";
											})
											value = _name;
										}
									});
								}else{
									value = fce.value;	
								}
							}
							
							var name=fc.belongTable + "-" + fc.columnName;
							if(fc.columnName == 'id') {
								$("#" + temp+tab.id + "_value_t" + (j+1)).html("<input type='hidden'" + "' name='" + name + "' value='" + value + "'/>" );
							}else{
								if(fc.isView==1){
									l++;
									if(fc.hasAuth == 1 || fc.hasAuth == 3){
										if(fc.inputType == 14){
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											$("#" + temp+tab.id + "_value_t" + l).html("<div id='"+name+"fileQueue'></div>");
											$.ajax({
												type:'post',
												url: __basePath+"/pages/resource/uploadifyshowCompleteFileView.action?fileIds="+value,
												async:false,
												success: function(data){
													$("#"+name+"fileQueue").html(data);
												}
											});
											sum += 1;
										} else if(fc.inputType == 5){
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											$("#" + temp+tab.id + "_value_t" + l).html("<a href='#' onclick='showSingleFile(\""+value+"\")' style='color: blue;cursor: pointer;'>查看</a>");
											sum += 1;
										} else if(fc.inputType == 2){
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											$("#" + temp+tab.id + "_value_t" + l).html("<div style='line-height: 25px;'>"+value+"</div>");
										} else if(fc.inputType == 10){
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											$("#" + temp+tab.id + "_value_t" + l).html(value.replace(new RegExp("<p>","g") ,"<p style='line-height: 25px;'>")+"&nbsp;");
										} else if(fc.inputType == 11){
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											$("#" + temp+tab.id + "_value_t" + l).html("******");
										}else if(fc.inputType == 13){
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											var caseCade=fce.codeCaseCade;
											if(value!=null&&value!=undefined){
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexshowCaseCadeName.action?caseCadeId="+value,
													async:false,
													success: function(data){
														$("#" + temp+tab.id + "_value_t" + l).html(data.name);
													}
												});
											}
										}else{
											if($("#" + temp+tab.id + "_label_t" + l).html()==""){
												$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
											}
											$("#" + temp+tab.id + "_label_t" + l).attr("title",fc.colComment);
											$("#" + temp+tab.id + "_value_t" + l).html(value+"&nbsp;");
											if(fc.inputType ==9){//自定义
												$("#" + temp+tab.id + "_value_t" + l).html("<div id='"+name+"CustomDiv'>"+value+"</div>");
											}
											if(fc.inputType == 12){
												$('div[title="'+fc.columnZhName+'"]').closest('tr').hide();
												$("#tabLiId").append("<li><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + fc.columnZhName + "</span></li>");
												$("#tabDivId").append('<div><div id="WebOfficeDiv" style="float:right;width:100%;height:100%;border:1px solid #CECCCD;"></div></div>');
												$("#" + temp+tab.id + "ParamDiv").append('<input id="recordId" name="'+name+'" value="'+value+'"/>');
												$('#WebOfficeDiv').html('<iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="500" src="/pages/resource/template/print/printView.jsp?fileType='+fc.officeMode+'&editType=4,0&recordId='+value+'"></iframe>');
											} 
											sum += 1;
										}
									}else{
										if($("#" + temp+tab.id + "_label_t" + l).html()==""){
											$("#" + temp+tab.id + "_label_t" + l).html(fc.columnZhName);
										}
										$("#" + temp+tab.id + "_value_t" + l).html("<font color='red'>无权限</font>");
									}
								}
							}
						}
						$("#" + temp+tab.id + "_tr_t0").hide();
						if(template.designType==0){
							eval(temp+tab.id + "HideTr(" + sum + ")");
						}
						initTab();
					}else if(template.type==1){
						//设置tab标题
						var clazz = "";
						var templateFileName;
						//去掉文件扩展名
						var temp;
						
						if(template.designType==0){
							templateFileName = template.templateFileName;
							//去掉文件扩展名
							temp = templateFileName.substring(0,templateFileName.indexOf("."));
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								$("#domainSubmit").val(temp+tab.id+"Submit");
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>");
							//填充tab框架
							var fces = tab.formColumnExtends;
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							
							var urlString = __basePath+"/pages/core/" + templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&params=" + $("#paramsId").val();
							$.ajax({
								type:'post',
								url: urlString,
								async:false,
								success: function(data){
									var regS = new RegExp(temp,"g");
									var result = data.replace(regS, temp+tab.id);
									result=result.replace("partitionForm",tab.id+"_partitionForm");
									result=result.replace("partitionList",tab.id+"_partitionList");
									$("#" + temp+tab.id).html(result);
								}
							});
						}else if(template.designType==1){
							temp="CombinationStandard"+template.id;
							if(i==0) {
								clazz = "class='selected'";
								$("#tabId").val(temp+tab.id);
								$("#domainSubmit").val(temp+tab.id+"Submit");
							}
							$("#tabLiId").append("<li " + clazz + "><a onclick='setCompexTabForm(\""+temp+tab.id+"\","+i+");'><span style='color:"+tab.tabFontColor+";font-family:\""+tab.tabFontStyle+"\";'>" + tab.tabName + "</span></a></li>")
							//填充tab框架
							var fces = tab.formColumnExtends;
							$("#tabDivId").append("<div id='" + temp+tab.id + "'/>");
							
							var regS = new RegExp(temp,"g");
							var result = template.content.replace(regS, temp+tab.id);
							result=result.replace("partitionForm",tab.id+"_partitionForm");
							result=result.replace("partitionList",tab.id+"_partitionList");
							$("#" + temp+tab.id).html(result);
							
						}
						
						//遍历tab内 所有的分区,填充分区模板
						var formOrder=1;//操作区顺序
						var listOrder=1;//列表区顺序
						var partitions=tab.partitions;
						for(var p=0;p<partitions.length;p++){
							var partition = partitions[p];
							if(partition.partitionType=="0"){
								var ptemplate=partition.template;
								var ptemplateFileName;
								if(ptemplate.designType==0){
									ptemplateFileName=partition.templateFileName.substring(0,templateFileName.indexOf("."));
									var formUrlString = __basePath+"/pages/core/"+partition.templateFileName+"?formId="+$("#formId").val()+"&tabId=" + tab.id+"&partitionId="+partition.id+"&params=" + $("#paramsId").val();
									$.ajax({
										type:'post',
										url: formUrlString,
										async:false,
										success: function(data){
											var regS = new RegExp(ptemplateFileName,"g");
											var result = data.replace(regS, ptemplateFileName+tab.id+partition.id);
											$("#"+tab.id+"_partitionForm"+formOrder).html(result);
										}
									});
								}else if(ptemplate.designType==1){
									ptemplateFileName="compexDomainTabEdit"+ptemplate.id;
									var regS = new RegExp("compexDomainTabEdit","g");
									var result = (_content_start+ptemplate.content+_content_end).replace(regS, ptemplateFileName+tab.id+partition.id);
									$("#"+tab.id+"_partitionForm"+formOrder).html(result);
									var submitHidden="<input id='"+ptemplateFileName+tab.id+partition.id+"Submit' type='submit' name='submit'/>";
									var formIdHidden="<input type='hidden' name='formId' value='"+$("#formId").val()+"'/>";
									var tabIdHidden="<input type='hidden' name='tabId' value='"+tab.id+"'/>";
									var partitionIdHidden="<input type='hidden' name='partitionId' value='"+partition.id+"'/>";
									var paramsHidden="<input type='hidden' name='params' value='"+$("#paramsId").val()+"'/>";
									$("#" + ptemplateFileName+tab.id+partition.id + "ParamDiv").html(submitHidden+formIdHidden+tabIdHidden+partitionIdHidden+paramsHidden);
								}
								
								if(partition.formColumnExtends[0] == undefined) {
									break;
								}
								var model = partition.formColumnExtends[0].formColumn.belongTable;
								$("#"+tab.id+"_partitionForm"+formOrder).append("<input type='hidden' id='"+tab.id+"_partitionForm"+formOrder+"Model' value='"+model+"'/>" );
								$("#"+tab.id+"_partitionForm"+formOrder).append("<input type='hidden' id='"+tab.id+"_partitionForm"+formOrder+"Pid' value='"+partition.id+"'/>" );
								//分区的所有按钮
								var pformButtons=partition.formButtons;
								$("#"+tab.id+"_partitionForm"+formOrder).append("<div style='margin-top:5px;' align='right' id='" + ptemplateFileName+tab.id+partition.id+"Button'/>");
								for(var pf=0;pf<pformButtons.length;pf++){
									var pformButton = pformButtons[pf];
									if(pformButton.hasAuth=="0"){
										if(pformButton.buttonType=="1"){
											var pformButtonGroup=pformButton.buttonGroup;
											for(var pbg=0;pbg<pformButtonGroup.buttonAndGroups.length;pbg++){
												var buttonAndGroup=pformButtonGroup.buttonAndGroups[pbg];
												$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+buttonAndGroup.button.buttonBM+"' class='listbuttonDisable' disabled='disabled' style='width:"+buttonAndGroup.button.buttonWidth+"px;height:"+buttonAndGroup.button.buttonHeight+"px;background: "+buttonAndGroup.button.buttonBackGroundColor+";border-color: "+buttonAndGroup.button.buttonBorderColor+";' onclick='compex"+buttonAndGroup.button.buttonBM+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+buttonAndGroup.button.buttonNameFontColor+"' style='font-family:"+buttonAndGroup.button.buttonNameFontStyle+";font-size: "+buttonAndGroup.button.buttonNameFontSize+"px;'>"+buttonAndGroup.button.buttonName+"</font></button>");
											}
										}else if(pformButton.buttonType=="0"){
											//var pformButton=pformButton.button;
											$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+pformButton.funcName+"' class='listbuttonDisable' disabled='disabled' style='width:"+pformButton.button.buttonWidth+"px;height:"+pformButton.button.buttonHeight+"px;background: "+pformButton.button.buttonBackGroundColor+";border-color: "+pformButton.button.buttonBorderColor+";' onclick='compex"+pformButton.funcName+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\")'>"+"<font color='"+pformButton.button.buttonNameFontColor+"' style='font-family:"+pformButton.button.buttonNameFontStyle+";font-size: "+pformButton.button.buttonNameFontSize+"px;'>"+pformButton.showName+"</font></button>");
										}
									}else if(pformButton.hasAuth=="1"){
										if(pformButton.buttonType=="1"){
											var pformButtonGroup=pformButton.buttonGroup;
											for(var pbg=0;pbg<pformButtonGroup.buttonAndGroups.length;pbg++){
												var buttonAndGroup=pformButtonGroup.buttonAndGroups[pbg];
												$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+buttonAndGroup.button.buttonBM+"' class='listbuttonDisable' disabled='disabled' style='width:"+buttonAndGroup.button.buttonWidth+"px;height:"+buttonAndGroup.button.buttonHeight+"px;background: "+buttonAndGroup.button.buttonBackGroundColor+";border-color: "+buttonAndGroup.button.buttonBorderColor+";' onclick='compex"+buttonAndGroup.button.buttonBM+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+buttonAndGroup.button.buttonNameFontColor+"' style='font-family:"+buttonAndGroup.button.buttonNameFontStyle+";font-size: "+buttonAndGroup.button.buttonNameFontSize+"px;'>"+buttonAndGroup.button.buttonName+"</font></button>");
											}
										}else if(pformButton.buttonType=="0"){
											//var pformButton=pformButton.button;
											$("#"+ptemplateFileName+tab.id+partition.id+"Button").append("&nbsp;<button type='button' id='"+pformButton.funcName+"' class='listbuttonDisable' disabled='disabled' style='width:"+pformButton.button.buttonWidth+"px;height:"+pformButton.button.buttonHeight+"px;background: "+pformButton.button.buttonBackGroundColor+";border-color: "+pformButton.button.buttonBorderColor+";' onclick='compex"+pformButton.funcName+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+pformButton.button.buttonNameFontColor+"' style='font-family:"+pformButton.button.buttonNameFontStyle+";font-size: "+pformButton.button.buttonNameFontSize+"px;'>"+pformButton.showName+"</font></button>");
										}
									}
								}
	
								var pfces=partition.formColumnExtends;
								var sum = 0;
								var l = 0;
								for(var j=0; j<pfces.length; j++) {
									var fce = pfces[j];
									var fc = fce.formColumn;
									
									var value="";
									if(fce.value != null) {
										value = fce.value;
									}
									var name=fc.belongTable + "-" + fc.columnName;
									
									if(fc.columnName == 'id') {
										$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + (j+1)).html("<input type='hidden'" + "' name='" + name + "' value='" + value + "'/>" );
									}else{
										l++;
										if($("#" + ptemplateFileName+tab.id+partition.id + "_label_t" + l).html()==""){
											$("#" + ptemplateFileName+tab.id+partition.id + "_label_t" + l).html(fc.columnZhName);
										}
										$("#" + ptemplateFileName+tab.id+partition.id + "_label_t" + l).attr("title",fc.colComment);
										var required = "";
										var xinghao = "";
										if(fc.required == 1) {
											required = "required";
											xinghao = "";//"<font color='red'>*</font>"
										}
										if(fc.hasAuth == 2 || fc.hasAuth == 3){
											if(fc.inputType == 0) {//文本框
												var textBox = fce.component;
												var width = 180;
												var height = 15;
												if(textBox != null) {
													width = textBox.textBoxWidth;
													height = textBox.textBoxHeight;
												}
												var style = "style='width:" + width + "px;height:" + height + "px;'";
													
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input disabled='disabled' maxlength='"+ Math.floor(fc.length/2) + "' " + style + " class='" + fc.validate +" textInput " + required + "' name='" + name + "' value='" + value + "'/>" + xinghao);	
											}
											if(fc.inputType == 16){//自动补齐文本框
												var autoComplete = fce.component;
												var width = 180;
												var height = 15;
												var sourceArray = [];
												if(autoComplete != null) {
													width = autoComplete.autoCompleteWidth;
													height = autoComplete.autoCompleteHeight;
													var _source = autoComplete.source;
													sourceArray = _source.split(",");
												}
												var style = " style='width:" + width + "px;height:" + height + "px;'";
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input disabled='disabled' maxlength='"+ Math.floor(fc.length/2) + "'" + style + " class='textInput " + required + "' id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
												
											    $( "#"+name ).autocomplete({
											      source: sourceArray
											    });
											    
											}
											if(fc.inputType == 1||fc.inputType == 8) {//下拉框
												var width = 186;
												if(fc.inputType == 1){
													var combox = fce.component;
													if(combox != null) {
														width = combox.comboxWidth;
													}
												}else if(fc.inputType ==8){
													var searchcombox = fce.component;
													if(combox != null) {
														width = searchcombox.searchComboxWidth;
													}
												}
												var select = "<select disabled='disabled' id='queryPselect"+tab.id+partition.id+j+"' name='" + name + "' style='width:"+width+"px;'>";
												var codes = fce.codes;
												var options ="";
												if(fc.hasNull==1){
													options+="<option  value='-1'>"+fc.nullName+"</option>";
												}
												for(var k=0; codes!=null&&k<codes.length; k++) {
													var selected = "";
													if(fc.hasDefaultItem==1){
														if(codes[k].id == fc.defaultSelectItem && fce.value==null) {
															selected = "selected='selected'";
														}
													}
													if(codes[k].value == fce.value) {
														selected = "selected='selected'";
													}
													options += "<option " + selected + " value='" + codes[k].value + "'>" + codes[k].text + "</option>";
												}
												select += options + "</select>";
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html(select);
												if(fc.inputType ==8){
													$("#queryPselect"+tab.id+partition.id+j).combobox();
												}
											}
											if(fc.inputType == 3){
												var radio = fce.radio;
												var radios="";
												var codes = fce.codes;
												for(var k=0; codes!=null&&k<codes.length; k++) {
													var checked = "";
													if(fce.value==null&&k==0){
														checked = "checked='checked'";
													}
													if(codes[k].value == fce.value) {
														checked = "checked='checked'";
													}
													radios += "<input disabled='disabled' type='radio' style='border-style: "+radio.tbl_borderStyle+";border-width: "+radio.tbl_borderWidth+"px;border-color: "+radio.tbl_borderColor+";background-color: "+radio.tbl_bgColor+";zoom:"+radio.tbl_zoom+"' name='"+name+"' value='"+codes[k].value+"'  "+checked+"/>"+codes[k].text;
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html(radios);
											}
											if(fc.inputType == 4){
												var checkbox = fce.checkbox;
												var checkBoxes="";
												var codes = fce.codes;
												if(fce.value==null){
													fce.value="";
												}
												var res=fce.value.split(",");
												for(var k=0; codes!=null&&k<codes.length; k++) {
													var checked = "";
													for(var c=0;c<res.length;c++){
														if(res[c]==codes[k].value){
															checked = "checked='checked'";
															break;
														}
													}
													checkBoxes += "<input disabled='disabled' type='checkbox' style='border-style: "+checkbox.tbl_borderStyle+";border-width: "+checkbox.tbl_borderWidth+"px;border-color: "+checkbox.tbl_borderColor+";background-color: "+checkbox.tbl_bgColor+";zoom:"+checkbox.tbl_bili+"' name='"+name+"' value='"+codes[k].value+"' "+checked+"/>"+codes[k].text;
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html(checkBoxes);
											}
											
											if(fc.inputType == 2) {//文本域
												var width = 103;
												var height = 5;
												var textarea = fce.component;
												if(textarea != null) {
													width = textarea.textareaWidth;
													height = textarea.textareaHeight;
												}
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<textarea disabled='disabled' class='textInput "+required+"' name='" + name + "' rows='" + height + "' cols='" + width + "'>" + value +"</textarea>"+xinghao);
											}
											if(fc.inputType == 6) {//日期组件
												var date=fce.date;
												var dtwidth="";
												var dtstyle="";
												if(date!=null){
													dtwidth=date.dateWidth;
													dtstyle=date.dateStyle;
												}
												if(fc.dataType=='date') {
													if(dtstyle==""){
														dtstyle="yyyy-MM-dd";
													}
													$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);	
												}else {
													if(dtstyle==""){
														dtstyle="yyyy-MM-dd HH:mm:ss";
													}
													$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);
												}
											}
											if(fc.inputType == 7){
												var parentName="";
												if(value!=""){
													$.ajax({
														type:'post',
														dataType: 'json',
														url: __basePath+"/pages/resource/compexshowTree.action?treeId="+fce.value+"&mgrTreeId="+fc.compexId,
														async:false,
														success: function(data){
															var tree=data;
															parentName=tree.name;
														}
													});
												}
												$('#' + ptemplateFileName+tab.id+partition.id + '_value_t' + l).html("<table cellspacing='0' cellpadding='0' border='0'><tr><td align='left' style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'><input disabled='disabled' id='parentName"+name+"' type='text' class='"+required+"' readonly='true' value='"+parentName+"'/>"+xinghao+"<input id='parentId"+name+"' name='"+name+"' value='"+value+"' type='hidden'/></td><td style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'><button disabled='disabled' style='width:44px;height:20px;' type='button' class='listbutton' onclick=\"eventCompexTREE("+__basePath+"'/pages/resource/tree/fetchShowTreeParamById.action?treeId="+fc.compexId+"&parentName=parentName"+name+"&parentId=parentId"+name+"')\">选择</button></td></tr></table>");
											}
											if(fc.inputType ==9){//自定义
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<div id='"+name+"Custom'></div>");
											}
											if(fc.inputType == 10){
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<textarea disabled='disabled' name='" + name + "' rows='"+fce.textEditor.rows+"' cols='"+fce.textEditor.cols+"'>" + value +"</textarea>");
												if(fce.textEditor.editorId=="xh_editor"){
													$('textarea#'+name).xheditor({
														tools:'full',
														skin:'default',
														upMultiple:false,
														upImgUrl: "/servlet/UploadFileServlet",
														upImgExt: "jpg,jpeg,gif,bmp,png"
														//onUpload:insertUpload //指定回调函数,需要自己另外在编写函数实现回调处理.
													});
												}else if(fce.textEditor.editorId=="ck_editor"){
													$('textarea#'+name).ckeditor();
												}
											}
											if(fc.inputType == 11){
												var passbox = fce.component;
												var width = 180;
												var height = 15;
												if(passbox != null) {
													width = passbox.passboxWidth;
													height = passbox.passboxHeight;
												}
												var style = " style='width:" + width + "px;height:" + height + "px;'";
												$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<input disabled='disabled' type='password' maxlength='"+ Math.floor(fc.length/2) + "'" + style + " id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
											}
											if(fc.inputType == 13){
												var contentDivID = ptemplateFileName+tab.id+partition.id + "_value_t" + l;
												
												var caseCade=fce.codeCaseCade;
												var selectname="";
												if(caseCade.showProgression==1){
													selectname="name='"+name+"'";
												}
												$.ajax({
													type:'post',
													dataType: 'json',
													url: __basePath+"/pages/resource/compexshowCaseCade.action?topCode="+caseCade.topCode+"&showProgression="+caseCade.showProgression,
													async:false,
													success: function(data){
														var selectStr="";
														selectStr+="<select disabled='disabled' "+selectname+" style='width:"+caseCade.width+"px;' onchange='show(this,\""+contentDivID+"\",\""+name+"\",\""+caseCade.showProgression+"\",\""+caseCade.width+"\",\""+fce.value+"\")'>";
														selectStr+="<option value='-1'>请选择</option>";
														for(var ci=0;ci<data.length;ci++){
															var selected="";
															if(caseCade.showProgression==1){
																if(fce.value==data[ci].id){
																	selected = "selected='selected'";
																}
															}
															selectStr+="<option "+selected+" value='"+data[ci].id+"'>"+data[ci].name+"</option>";
														}
														selectStr+="</select>";
														$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).append(selectStr);
													}
												});
												
												if(fce.value!=null&&fce.value!=undefined){
													for(var si=1;si<caseCade.showProgression;si++){
														var currselect = $("#"+contentDivID+"> select:last");
														$.ajax({
															type:'post',
															dataType: 'json',
															url: __basePath+"/pages/resource/compexgetParentCodeByProg.action?progression="+(caseCade.showProgression-si)+"&value="+fce.value,
															async:false,
															success: function(data){
																var dic = data;
																currselect.val(dic.id);
															}
														});
														show(currselect,contentDivID,name,caseCade.showProgression,caseCade.width,fce.value);
													}
												}
											}
										}else{
											$("#" + ptemplateFileName+tab.id+partition.id + "_value_t" + l).html("<font color='red'>无权限</font>");
										}
										
										sum += 1;
									}
								}
								formOrder+=1;
								$("#" + ptemplateFileName+tab.id+partition.id + "_tr_t0").hide();
								if(ptemplate.designType==0){
									eval(ptemplateFileName+tab.id+partition.id + "HideTr(" + sum + ")");
								}
								initTab();
							}else if(partition.partitionType=="1"){
								$("#"+tab.id+"_partitionList"+listOrder).loadUrl(__basePath+"/pages/resource/compexlistsub?op=view&formId="+$("#formId").val()+"&tabId=" + tab.id+"&partitionId="+partition.id+"&partitionListDiv="+$("#"+tab.id+"_partitionList"+listOrder).attr("id")+"&params=" + $("#paramsId").val());
								listOrder+=1;
							}
						}
					}
				}
			}
		}
	}
	
	function dataJsonsub(urlString,partitionForm,str) {
		$.ajax({
			type:'post',
			url: urlString,
			dataType: 'json',
			success: function(data){
				subBuildPage(data,partitionForm,str);
			}
		});
	}

	function subBuildPage(partition,partitionForm,str) {
		var disa = "";
		var btclass = "listbutton"; 
		if(str=='view'){
			disa="disabled='disabled'";
			btclass="listbuttonDisable";
		}
		var _content_start = '<form id="compexDomainTabEditFormID" action="'+__basePath+'/pages/resource/'+partitionForm.simpleModel+'compexsave.action" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, compexSelfDialogAjaxDone);">';
		_content_start += '<div  id="compexDomainTabEditParamDiv" style="display:none;">';
		_content_start += '<input id="compexDomainTabEditSubmit" type="submit" name="submit"/>';
		_content_start += '<input type="hidden" name="formId" value="<%=request.getParameter("formId")%>"/>';
		_content_start += '<input type="hidden" name="tabId" value="<%=request.getParameter("tabId")%>"/>';
		_content_start += '<input type="hidden" name="partitionId" value="-1"/>';
		_content_start += '<input type="hidden" name="params" value="<%=request.getParameter("params")%>"/>';
		_content_start += '<div id="subDomainIdDiv"></div>';
		_content_start += '</div>';
		var _content_end = '</form>';
		if(partition.partitionType=="0"){
			var ptemplate=partition.template;
			var ptemplateFileName;
			if(ptemplate.designType==0){
				ptemplateFileName=partition.templateFileName.substring(0,templateFileName.indexOf("."));
				var formUrlString = __basePath+"/pages/core/"+partition.templateFileName+"?formId="+$("#formId").val()+"&tabId=" + partition.tabId+"&partitionId="+partition.id+"&params=" + $("#paramsId").val();
				$.ajax({
					type:'post',
					url: formUrlString,
					async:false,
					success: function(data){
						var regS = new RegExp(ptemplateFileName,"g");
						var result = data.replace(regS, ptemplateFileName+partition.tabId+partition.id);
						$("#compexSubEditId_"+partitionForm).html(result);
					}
				});
			}else if(ptemplate.designType==1){
				ptemplateFileName="compexDomainTabEdit"+ptemplate.id;
				var regS = new RegExp("compexDomainTabEdit","g");
				var result = (_content_start+ptemplate.content+_content_end).replace(regS, ptemplateFileName+partition.tabId+partition.id);
				$("#compexSubEditId_"+partitionForm).html(result);
				var submitHidden="<input id='"+ptemplateFileName+partition.tabId+partition.id+"Submit' type='submit' name='submit'/>";
				var formIdHidden="<input type='hidden' name='formId' value='"+$("#formId").val()+"'/>";
				var tabIdHidden="<input type='hidden' name='tabId' value='"+partition.tabId+"'/>";
				var partitionIdHidden="<input type='hidden' name='partitionId' value='"+partition.id+"'/>";
				var paramsHidden="<input type='hidden' name='params' value='"+$("#paramsId").val()+"'/>";
				var subDomainIdDiv="<div id='subDomainIdDiv'></div>";
				$("#" + ptemplateFileName+partition.tabId+partition.id + "ParamDiv").html(submitHidden+formIdHidden+tabIdHidden+partitionIdHidden+paramsHidden+subDomainIdDiv);
			}
			var model = partition.formColumnExtends[0].formColumn.belongTable;
			$("#compexSubEditId_"+partitionForm).append("<input type='hidden' id='"+partitionForm+"Model' value='"+model+"'/>" );
			$("#compexSubEditId_"+partitionForm).append("<input type='hidden' id='"+partitionForm+"Pid' value='"+partition.id+"'/>" );
			
			//分区的所有按钮
			
			var pformButtons=partition.formButtons;
			
			$("#compexSubEditId_"+partitionForm).append("<div align='right' id='" + ptemplateFileName+partition.tabId+partition.id+"Button'/>");
			
			for(var pf=0;pf<pformButtons.length;pf++){
				var pformButton = pformButtons[pf];
				if(pformButton.hasAuth=="0"){
					if(pformButton.buttonType=="1"){
						var pformButtonGroup=pformButton.buttonGroup;
						for(var pbg=0;pbg<pformButtonGroup.buttonAndGroups.length;pbg++){
							var buttonAndGroup=pformButtonGroup.buttonAndGroups[pbg];
							$("#"+ptemplateFileName+partition.tabId+partition.id+"Button").append("<button type='button' id='"+buttonAndGroup.button.buttonBM+"' class='"+btclass+"' "+disa+" style='width:"+buttonAndGroup.button.buttonWidth+"px;height:"+buttonAndGroup.button.buttonHeight+"px;background: "+buttonAndGroup.button.buttonBackGroundColor+";border-color: "+buttonAndGroup.button.buttonBorderColor+";' onclick='compex"+buttonAndGroup.button.buttonBM+"(\""+ptemplateFileName+"\",\""+partition.tabId+"\",\""+partition.id+"\");'>"+"<font color='"+buttonAndGroup.button.buttonNameFontColor+"' style='font-family:"+buttonAndGroup.button.buttonNameFontStyle+";font-size: "+buttonAndGroup.button.buttonNameFontSize+"px;'>"+buttonAndGroup.button.buttonName+"</font></button>");
						}
					}else if(pformButton.buttonType=="0"){
						//var pformButton=pformButton.button;
						$("#"+ptemplateFileName+partition.tabId+partition.id+"Button").append("<button type='button' id='"+pformButton.funcName+"' class='"+btclass+"' "+disa+" style='width:"+pformButton.button.buttonWidth+"px;height:"+pformButton.button.buttonHeight+"px;background: "+pformButton.button.buttonBackGroundColor+";border-color: "+pformButton.button.buttonBorderColor+";' onclick='compex"+pformButton.funcName+"(\""+ptemplateFileName+"\",\""+partition.tabId+"\",\""+partition.id+"\");'>"+"<font color='"+pformButton.button.buttonNameFontColor+"' style='font-family:"+pformButton.button.buttonNameFontStyle+";font-size: "+pformButton.button.buttonNameFontSize+"px;'>"+pformButton.showName+"</font></button>");
					}
				}else if(pformButton.hasAuth=="1"){
					if(pformButton.buttonType=="1"){
						var pformButtonGroup=pformButton.buttonGroup;
						for(var pbg=0;pbg<pformButtonGroup.buttonAndGroups.length;pbg++){
							var buttonAndGroup=pformButtonGroup.buttonAndGroups[pbg];
							$("#"+ptemplateFileName+partition.tabId+partition.id+"Button").append("&nbsp;<button type='button' id='"+buttonAndGroup.button.buttonBM+"' class='listbuttonDisable' style='width:"+buttonAndGroup.button.buttonWidth+"px;height:"+buttonAndGroup.button.buttonHeight+"px;background: "+buttonAndGroup.button.buttonBackGroundColor+";border-color: "+buttonAndGroup.button.buttonBorderColor+";' disabled='disabled' onclick='compex"+buttonAndGroup.button.buttonBM+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+buttonAndGroup.button.buttonNameFontColor+"' style='font-family:"+buttonAndGroup.button.buttonNameFontStyle+";font-size: "+buttonAndGroup.button.buttonNameFontSize+"px;'>"+buttonAndGroup.button.buttonName+"</font></button>");
						}
					}else if(pformButton.buttonType=="0"){
						//var pformButton=pformButton.button;
						$("#"+ptemplateFileName+partition.tabId+partition.id+"Button").append("&nbsp;<button type='button' id='"+pformButton.funcName+"' class='listbuttonDisable' style='width:"+pformButton.button.buttonWidth+"px;height:"+pformButton.button.buttonHeight+"px;background: "+pformButton.button.buttonBackGroundColor+";border-color: "+pformButton.button.buttonBorderColor+";' disabled='disabled' onclick='compex"+pformButton.funcName+"(\""+ptemplateFileName+"\",\""+tab.id+"\",\""+partition.id+"\");'>"+"<font color='"+pformButton.button.buttonNameFontColor+"' style='font-family:"+pformButton.button.buttonNameFontStyle+";font-size: "+pformButton.button.buttonNameFontSize+"px;'>"+pformButton.showName+"</font></button>");
					}
				}
				
			}

			
			var pfces=partition.formColumnExtends;
			
			var sum = 0;
			var l = 0;
			var recordId="-1";
			for(var j=0; j<pfces.length; j++) {
				var fce = pfces[j];
				var fc = fce.formColumn;
				
				var value="";
				if(fce.value != null) {
					value = fce.value;
				}
				var name=fc.belongTable + "-" + fc.columnName;
				if(fc.columnName == 'id') {
					recordId=value;
					$("#subDomainIdDiv").html("<input type='hidden' id='"+ptemplateFileName+partition.tabId+partition.id+"ID'" + "' name='" + name + "' value='" + value + "'/>" );
				}else{
					l++;
					if($("#" + ptemplateFileName+partition.tabId+partition.id + "_label_t" + l).html()==""){
						$("#" + ptemplateFileName+partition.tabId+partition.id + "_label_t" + l).html(fc.columnZhName);
					}
					$("#" + ptemplateFileName+partition.tabId+partition.id + "_label_t" + l).attr("title",fc.colComment);
					var required = "";
					var xinghao = "";
					if(fc.required == 1) {
						required = "required";
						xinghao = "";//"<font color='red'>*</font>"
					}
					if(fc.hasAuth == 2 || fc.hasAuth == 3){
						if(fc.inputType == 0) {//文本框
							var textBox = fce.component;
							var width = 180;
							var height = 15;
							if(textBox != null) {
								width = textBox.textBoxWidth;
								height = textBox.textBoxHeight;
							}
							var style = "style='width:" + width + "px;height:" + height + "px;'";
								
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<input "+disa+" maxlength='"+ Math.floor(fc.length/2) + "' " + style + " class='" + fc.validate +" textInput " + required + "' name='" + name + "' value='" + value + "'/>" + xinghao);	
						}
						if(fc.inputType == 16){//自动补齐文本框
							var autoComplete = fce.component;
							var width = 180;
							var height = 15;
							var sourceArray=[];
							if(autoComplete != null) {
								width = autoComplete.autoCompleteWidth;
								height = autoComplete.autoCompleteHeight;
								var _source = autoComplete.source;
								sourceArray = _source.split(",");
							}
							var style = " style='width:" + width + "px;height:" + height + "px;'";
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<input "+disa+" maxlength='"+ Math.floor(fc.length/2) + "'" + style + " class='textInput " + required + "' id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
						    $( "#"+name ).autocomplete({
						      source: sourceArray
						    });
						    
						}
						if(fc.inputType == 1||fc.inputType == 8) {//下拉框
							var width = 186;
							if(fc.inputType == 1){
								var combox = fce.component;
								if(combox != null) {
									width = combox.comboxWidth;
								}
							}else if(fc.inputType ==8){
								var searchcombox = fce.component;
								if(searchcombox != null) {
									width = searchcombox.searchComboxWidth;
								}
							}
							var select = "<select "+disa+" id='queryPselect"+partition.tabId+partition.id+j+"' name='" + name + "' style='width:"+width+"px;'>";
							var codes = fce.codes;
							var options ="";
							if(fc.hasNull==1){
								options+="<option  value='-1'>"+fc.nullName+"</option>";
							}
							for(var k=0; codes!=null&&k<codes.length; k++) {
								var selected = "";
								if(fc.hasDefaultItem==1){
									if(codes[k].id == fc.defaultSelectItem && fce.value==null) {
										selected = "selected='selected'";
									}
								}
								if(codes[k].value == fce.value) {
									selected = "selected='selected'";
								}
								options += "<option " + selected + " value='" + codes[k].value + "'>" + codes[k].text + "</option>";
							}
							select += options + "</select>";
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html(select);
							if(fc.inputType ==8){
								$("#queryPselect"+partition.tabId+partition.id+j).combobox();
							}
						}
						if(fc.inputType == 3){
							var radio = fce.radio;
							var radios="";
							var codes = fce.codes;
							for(var k=0; codes!=null&&k<codes.length; k++) {
								var checked = "";
								if(fce.value==null&&k==0){
									checked = "checked='checked'";
								}
								if(codes[k].value == fce.value) {
									checked = "checked='checked'";
								}
								radios += "<input "+disa+" type='radio' style='border-style: "+radio.tbl_borderStyle+";border-width: "+radio.tbl_borderWidth+"px;border-color: "+radio.tbl_borderColor+";background-color: "+radio.tbl_bgColor+";zoom:"+radio.tbl_zoom+"' name='"+name+"' value='"+codes[k].value+"'  "+checked+"/>"+codes[k].text;
							}
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html(radios);
						}
						if(fc.inputType == 4){
							var checkbox = fce.checkbox;
							var checkBoxes="";
							var codes = fce.codes;
							if(fce.value==null){
								fce.value="";
							}
							var res=fce.value.split(",");
							for(var k=0; codes!=null&&k<codes.length; k++) {
								var checked = "";
								for(var c=0;c<res.length;c++){
									if(res[c]==codes[k].value){
										checked = "checked='checked'";
										break;
									}
								}
								checkBoxes += "<input "+disa+" type='checkbox' style='border-style: "+checkbox.tbl_borderStyle+";border-width: "+checkbox.tbl_borderWidth+"px;border-color: "+checkbox.tbl_borderColor+";background-color: "+checkbox.tbl_bgColor+";zoom:"+checkbox.tbl_bili+"' name='"+name+"' value='"+codes[k].value+"' "+checked+"/>"+codes[k].text;
							}
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html(checkBoxes);
						}
						if(fc.inputType == 2) {//文本域
							var width = 103;
							var height = 5;
							var textarea = fce.component;
							if(textarea != null) {
								width = textarea.textareaWidth;
								height = textarea.textareaHeight;
							}
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<textarea "+disa+" class='textInput "+required+"' name='" + name + "' rows='" + height + "' cols='" + width + "'>" + value +"</textarea>"+xinghao);
						}
						if(fc.inputType == 6) {//日期组件
							var date=fce.date;
							var dtwidth="";
							var dtstyle="";
							if(date!=null){
								dtwidth=date.dateWidth;
								dtstyle=date.dateStyle;
							}
							if(fc.dataType=='date') {
								if(dtstyle==""){
									dtstyle="yyyy-MM-dd";
								}
								$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);	
							}else {
								if(dtstyle==""){
									dtstyle="yyyy-MM-dd HH:mm:ss";
								}
								$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<input class='"+required+"' readonly='true' style='width: "+dtwidth+"px;' type='text' id='"+name+"' name='" + name + "' value='" + value + "'/><img onclick=\"WdatePicker({el:'" + name + "',dateFmt:'"+dtstyle+"'})\" src=\"js/My97DatePicker/skin/datePicker.gif\" width='16' height='22' align='absmiddle' style='cursor:pointer'/>"+xinghao);
							}
						}
						
						if(fc.inputType == 7){
							var parentName="";
							if(value!=""){
								$.ajax({
									type:'post',
									dataType: 'json',
									url: __basePath+"/pages/resource/compexshowTree.action?treeId="+fce.value+"&mgrTreeId="+fc.compexId,
									async:false,
									success: function(data){
										var tree=data;
										parentName=tree.name;
									}
								});
							}
							$('#' + ptemplateFileName+partition.tabId+partition.id + '_value_t' + l).html("<table cellspacing='0' cellpadding='0' border='0'><tr><td align='left' style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'><input "+disa+" id='parentName"+name+"' type='text' class='"+required+"' readonly='true' value='"+parentName+"'/>"+xinghao+"<input id='parentId"+name+"' name='"+name+"' value='"+value+"' type='hidden'/></td><td style='background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;'><button "+disa+" style='width:44px;height:20px;' type='button' class='listbutton' onclick=\"eventCompexTREE("+__basePath+"'/pages/resource/tree/fetchShowTreeParamById.action?treeId="+fc.compexId+"&parentName=parentName"+name+"&parentId=parentId"+name+"')\">选择</button></td></tr></table>");
						}
						if(fc.inputType ==9){//自定义
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<div id='"+name+"Custom'></div>");
						}
						if(fc.inputType == 10){
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<textarea "+disa+" name='" + name + "' rows='"+fce.textEditor.rows+"' cols='"+fce.textEditor.cols+"'>" + value +"</textarea>");
							if(fce.textEditor.editorId=="xh_editor"){
								$('textarea#'+name).xheditor({
									tools:'full',
									skin:'default',
									upMultiple:false,
									upImgUrl: "/servlet/UploadFileServlet",
									upImgExt: "jpg,jpeg,gif,bmp,png"
									//onUpload:insertUpload //指定回调函数,需要自己另外在编写函数实现回调处理.
								});
							}else if(fce.textEditor.editorId=="ck_editor"){
								$('textarea#'+name).ckeditor();
							}
						}
						if(fc.inputType == 11){
							var passbox = fce.component;
							var width = 180;
							var height = 15;
							if(passbox != null) {
								width = passbox.passboxWidth;
								height = passbox.passboxHeight;
							}
							var style = " style='width:" + width + "px;height:" + height + "px;'";
							$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<input "+disa+" type='password' maxlength='"+ Math.floor(fc.length/2) + "'" + style + " id='"+name+"' name='" + name + "' value='" + value + "'/>" + xinghao);
						}
						if(fc.inputType == 13){
							var contentDivID = ptemplateFileName+partition.tabId+partition.id + "_value_t" + l;
							
							var caseCade=fce.codeCaseCade;
							var selectname="";
							if(caseCade.showProgression==1){
								selectname="name='"+name+"'";
							}
							$.ajax({
								type:'post',
								dataType: 'json',
								url: __basePath+"/pages/resource/compexshowCaseCade.action?topCode="+caseCade.topCode+"&showProgression="+caseCade.showProgression,
								async:false,
								success: function(data){
									var selectStr="";
									selectStr+="<select "+disa+" "+selectname+" style='width:"+caseCade.width+"px;' onchange='show(this,\""+contentDivID+"\",\""+name+"\",\""+caseCade.showProgression+"\",\""+caseCade.width+"\",\""+fce.value+"\")'>";
									selectStr+="<option value='-1'>请选择</option>";
									for(var ci=0;ci<data.length;ci++){
										var selected="";
										if(caseCade.showProgression==1){
											if(fce.value==data[ci].id){
												selected = "selected='selected'";
											}
										}
										selectStr+="<option "+selected+" value='"+data[ci].id+"'>"+data[ci].name+"</option>";
									}
									selectStr+="</select>";
									$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).append(selectStr);
								}
							});
							
							if(fce.value!=null&&fce.value!=undefined){
								for(var si=1;si<caseCade.showProgression;si++){
									var currselect = $("#"+contentDivID+"> select:last");
									$.ajax({
										type:'post',
										dataType: 'json',
										url: __basePath+"/pages/resource/compexgetParentCodeByProg.action?progression="+(caseCade.showProgression-si)+"&value="+fce.value,
										async:false,
										success: function(data){
											var dic = data;
											currselect.val(dic.id);
										}
									});
									show(currselect,contentDivID,name,caseCade.showProgression,caseCade.width,fce.value);
								}
							}
						}
						if(fc.inputType == 14){
							if(str=='view'){
								if($("#" + ptemplateFileName+partition.tabId+partition.id + "_label_t" + l).html()==""){
									$("#" + ptemplateFileName+partition.tabId+partition.id + "_label_t" + l).html(fc.columnZhName);
								}
								$("#" + ptemplateFileName+partition.tabId+partition.id + "_label_t" + l).attr("title",fc.colComment);
								$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<div id='"+name+"fileQueue'></div>");
								$.ajax({
									type:'post',
									url: __basePath+"/pages/resource/uploadifyshowCompleteFileView.action?fileIds="+value,
									async:false,
									success: function(data){
										$("#"+name+"fileQueue").html(data);
									}
								});
								sum += 1;
							}else{
								if(fc.inputType == 14){
									var uploadify = fce.uploadify;
									var buttonText = "";
									var multi = true;
									var auto = false;
									var fileSizeLimit = "10MB";
									var uploadLimit = 5;
									var fileTypeExts = "*.*";
									
									if(uploadify!=null){
										buttonText = uploadify.buttonText;
										multi = uploadify.multi==1?true:false;
										auto = uploadify.autoUpload==1?true:false;
										if(uploadify.fileSizeLimit!=null){
											fileSizeLimit = uploadify.fileSizeLimit+"MB";
										}
										if(uploadify.queueSizeLimit!=0){
											uploadLimit = uploadify.queueSizeLimit;
										}
										if(uploadify.fileExt){
											fileTypeExts = uploadify.fileExt;
										}
										
									}
									
									$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<div id='"+name+"fileQueue'></div><input type='file' id='"+name+"' style='display: none;'/><input id='uploadify"+name+"' type='hidden' name='"+name+"' value='"+value+"'>");
									$.ajax({
										type:'post',
										url: __basePath+"/pages/resource/uploadifyshowCompleteFile.action?fileIds="+value+"&recordId="+recordId,
										async:false,
										success: function(data){
											$("#"+name+"fileQueue").html(data);
										}
									});
									
									$("#"+name).uploadify({
							        	'buttonText' : buttonText,
							        	'formData' : {'model' : fc.belongTable , 'columnname' : fc.columnName , 'createby' : userid, 'recordColumn' : fc.recordColumn==null?'':fc.recordColumn},
							        	'method' : 'get',
							            'swf'      : __basePath+'/js/uploadify/uploadify.swf',
							            'uploader' : __basePath+'/CompexServlet',
							            'multi'    : multi,
							            'queueID'  : name+'fileQueue',
							            'auto'     : auto,
							            'fileSizeLimit' : fileSizeLimit,
							            'queueSizeLimit' : 999,
							            'fileTypeExts' : fileTypeExts,
							            'removeCompleted' : false,
							            'uploadLimit' : uploadLimit,
							            'height' : 18,
							            'width' : 80,
							            'onUploadSuccess' : function(file, data, response) {
							        		var atta=$.parseJSON(data);
							        		var uploadifyValue = $("#uploadify"+atta.name).val();
							        		if(uploadifyValue!=""){
							        			$("#uploadify"+atta.name).val(uploadifyValue+","+atta.attachmentId);
							        		}else{
							        			$("#uploadify"+atta.name).val(atta.attachmentId);
							        		}
								        },
								        'onCancel' : function(file) {
								           	 
								        },
								        'onUploadStart' : function(file) {
								        	
								        }
							        });
								}
							}
						}
					}else{
						$("#" + ptemplateFileName+partition.tabId+partition.id + "_value_t" + l).html("<font color='red'>无权限</font>");
					}
					
					sum += 1;
				}
			}
			$("#" + ptemplateFileName+partition.tabId+partition.id + "_tr_t0").hide();
			if(ptemplate.designType==0){
				eval(ptemplateFileName+partition.tabId+partition.id + "HideTr(" + sum + ")");
			}
		}
	}

	function setTree(id,nameId,parentInputId,parentInputName) {
		if((parentInputId==""&&parentInputName=="")||(parentInputId=="null"&&parentInputName=="null")){
			$("#parentId").val($("#"+id).val());
			$("#parentName").val($("#"+nameId).val());
		}else{
			$("#"+parentInputId).val($("#"+id).val());
			$("#"+parentInputName).val($("#"+nameId).val());
		}
		$.pdialog.close("selectDialog");
	}
	
	function show(selectobj,id,name,showProgression,width,currvalue){
		if(showProgression==1){
			return;
		}
		var selectname="";
		var selectcount=$("#"+id+"> select").length;
		if(selectcount == showProgression){
			var currselName = $(selectobj).attr("name");
			if(currselName != undefined){
				return;
			}
		}
		
		if((selectcount+1)>=showProgression){
			selectname="name='"+name+"'";
		}

		var obj = selectobj;
		var currentObj = $(obj);
		var s1 = $(obj).nextAll("select");
		s1.each(function (i){
			$(this).remove();
		});
		var value = $(obj).val();
		
		$.ajax({
			type:'post',
			dataType: 'json',
			url: __basePath+"/pages/resource/compexshowCaseCade.action?topCode="+value,
			async:false,
			success: function(data){
				if(data.length>0){
					var selectStr="";
					selectStr+="<select "+selectname+" style='width:"+width+"px;' onchange='show(this,\""+id+"\",\""+name+"\",\""+showProgression+"\")'>";
					selectStr+="<option value='-1'>请选择</option>";
					for(var ci=0;ci<data.length;ci++){
						var selected="";
						if(selectname!=""){
							if(currvalue==data[ci].id){
								selected = "selected='selected'";
							}
						}
						selectStr+="<option "+selected+" value='"+data[ci].id+"'>"+data[ci].name+"</option>";
					}
					selectStr+="</select>";
					$("#"+id).append(selectStr);
				}
			}
		});
	}
	
	function uploadfile(name){
		$("#"+name).uploadify("upload","*");
	}
	
	function cancelupload(name){
		$("#"+name).uploadify("cancel", "*");
	}
	
	function deleteFile(model,column,swfdiv,filepath,fileid,recordId){
		$("#"+model+"-"+column).uploadify("cancel",swfdiv);
		var param = "model="+model+"&column="+column+"&filepath="+filepath+"&fileid="+fileid+"&recordId="+recordId;
		$.ajax({
			type:'post',
			url: __basePath+"/pages/resource/uploadifydeleteFile.action",
			data:param,
			success: function(){
				
			}
		});
		var newUploadifyValue="";
		var fileids= new Array();
		fileids = $("#uploadify"+model+"-"+column).val().split(",");
		for(var i=0;i<fileids.length;i++){
			if(fileids[i]!=fileid){
				if(newUploadifyValue==""){
					newUploadifyValue=fileids[i];
				}else{
					newUploadifyValue=newUploadifyValue+","+fileids[i];
				}
			}
		}
		$("#uploadify"+model+"-"+column).val(newUploadifyValue);
	}
	
	function showSingleFile(path){
		window.open(__basePath+"/pages/resource/uploadbox/showSingleFile.action?path="+path);
	}
	
	//xbhEditor编辑器图片上传回调函数
	function insertUpload(msg) {
		var _msg = msg.toString();
		var _picture_name = _msg.substring(_msg.lastIndexOf("/")+1);
		var _picture_path = Substring(_msg);
		var _str = "<input type='checkbox' name='_pictures' value='"+_picture_path+"' checked='checked' onclick='return false'/><label>"+_picture_name+"</label><br/>";
		$("#xh_editor").append(_msg);
		$("#uploadList").append(_str);
	}
	//处理服务器返回到回调函数的字符串内容,格式是JSON的数据格式.
	function Substring(s){
		return s.substring(s.substring(0,s.lastIndexOf("/")).lastIndexOf("/"),s.length);
	}
	/***
	 * 生成业务---------------------------------------end-------------------------------------------
	 */