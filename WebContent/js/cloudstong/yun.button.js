//表单属性
var xjTitle = "新建";
var xgTitle = "修改";
var whTitle = "维护";
var selfXjTitle;
var selfXgTitle;
var selfWhTitle;
var fDefaultTile = "对话框";
var fDefaultWidth = 950;
var fDefaultHeight = 650;
var fSelfWidth;
var fSelfHeight;
//弹出树属性
var tDefaultTitle = "弹出树";
var tDefaultWidth = 300;
var tDefaultHeight = 600;
//高级查询
var sDefaultTitle = "弹出树";
var sDefaultWidth = 620;
var sDefaultHeight = 250;

var xjUrl;//新建URL
var bcUrl;//保存URL
var scUrl;//删除URL
var plscUrl;//批量删除URL
var xgUrl;//修改URL
var params;//查询参数,格式如：tableName_id:value;
var tjTabUrl;//添加tab URL
var bcTabUrl;//保存tab URL
var scTabUrl;//删除tab URL
var whTabUrl;//维护tab URL
var partitionListDiv;//分区div ID
var zwUrl = __basePath + "/pages/system/applet/appletopenWO.action";//正文URL
var bzUrl;//帮助URL
var formbzUrl;//表单帮助URL
var mrUrl;//默认URL
var globalParentId;

function isDY(v) {
	if (v != undefined) {
		return true;
	} else {
		return false;
	}
}

/***
 * 生成业务---------------------------------------start-------------------------------------------
 */
function eventCompexDY(){
		var urlString = __basePath + '/pages/resource/compexenabled.action?mainTable=sys_tableui&';
		var items = $("#tableForm").find('input:checked');
		if(items.length > 0){
			var params = '';
			$.each(items, function (entryIndex,entry){
				if($(entry).val()!= 'on'){
					params += $(entry).val();
				}
			});
			$('#tableForm').data('params', params);
		}else{
			alertMsg.warn("请选择需要打印的数据!");
			return;
		}
		$.pdialog.open(__basePath + "/pages/resource/template/print/printListYL.jsp","lbdyDialog","列表打印预览",{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
    }

	function eventCompexBDDY(){
		$.pdialog.open(__basePath + "/pages/resource/template/print/printFormYL.jsp","bddyDialog","表单打印预览",{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
	}
	
	function eventCompexXJ(){
		if(fSelfWidth == undefined || fSelfWidth == "0" || fSelfWidth == ""){
			fSelfWidth = fDefaultWidth;
		}
		if(fSelfHeight == undefined || fSelfHeight == "0" || fSelfHeight == ""){
			fSelfHeight = fDefaultHeight;
		}
		if(selfXjTitle == undefined || selfXjTitle == "") {
			selfXjTitle = xjTitle;
		}
		$.pdialog.open(xjUrl,"newDialog",selfXjTitle,{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
	}
	
	function eventCompexWH(url,param,other){
		if(fSelfWidth == undefined || fSelfWidth == "0" || fSelfWidth == ""){
			fSelfWidth = fDefaultWidth;
		}
		if(fSelfHeight == undefined || fSelfHeight == "0" || fSelfHeight == ""){
			fSelfHeight = fDefaultHeight;
		}
		if(selfWhTitle == undefined || selfWhTitle == "" ) {
			selfWhTitle = whTitle;
		}
		url=url+"?"+param;
		if(other!=undefined){
			url+="&"+other;
		}
		$.pdialog.open(url,"whDialog",selfWhTitle,{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
	}
	
	function eventCompexFABU(url,param,other){
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		url:__basePath+'/pages/resource/compexpassed.action?'+param,
	  		success:function(data){
				alertMsg.info("发布成功");
				refresh();
	  		}
	  	});
	}
	
	function eventCompexCHEHUI(url,param,other){
		//判断是否可以撤回  start
		var canrecall="true";
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		url:__basePath + '/pages/resource/compexisCanRecall.action?'+param,
	  		success:function(data){
				canrecall=data;
	  		}
	  	});
		
		if(canrecall=="false"){
			alertMsg.warn("正在被引用的数据不能被撤回！");
			return false;
		}
		if(canrecall=="false.isdefault"){
			alertMsg.warn("选中的记录中包含默认的数据，不能执行撤回操作！");
			return false;
		}
		//判断是否可以撤回  end
		
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		url:__basePath + '/pages/resource/compexrecall.action?'+param,
	  		success:function(data){
				alertMsg.info("撤回成功");
				refresh();
	  		}
	  	});
	}

	function eventCompexTREE(url){
		$.pdialog.open(url,"selectDialog",tDefaultTitle,{width:tDefaultWidth,height:tDefaultHeight,mask:true,resizable:false,minable:false,mixable:false});
	}
	
	function eventCompexMultiTREE(url){
		$.pdialog.open(url,"selectDialog",tDefaultTitle,{width:1024,height:768,mask:true,resizable:false,minable:false,mixable:false});
	}
	
	function eventCompexPersonTREE(url){
		$.pdialog.open(url,"personSelectDialog",tDefaultTitle,{width:fDefaultWidth,height:fDefaultHeight,mask:true,resizable:false,minable:false,mixable:false});
	}
	
	function eventCompexBC(){
		if($("#isFirstTab").val()=="false"){
			if($("#domainId").val()==""||$("#domainId").val()==undefined){
				alertMsg.warn('请先保存主记录！');
				return;
			}
		}
		var formid=$("#domainSubmit").val().replace(/Submit/,"FormID");
		if($("#domainId").val()==undefined){
			$("#"+formid).attr("action",bcUrl);
		}else{
			$("#"+formid).attr("action",bcUrl+"?domainId="+$("#domainId").val());
		}
		$("#" + $("#domainSubmit").val()).click();
	}
	
	function eventCompexUploadifyBC(){
		if($("#isFirstTab").val()=="false"){
			if($("#domainId").val()==""||$("#domainId").val()==undefined){
				alertMsg.warn('请先保存主记录！');
				return;
			}
		}
		var formid=$("#domainSubmit").val().replace(/Submit/,"FormID");
		if($("#domainId").val()==undefined){
			$("#"+formid).attr("action",bcUrl);
		}else{
			$("#"+formid).attr("action",bcUrl+"?domainId="+$("#domainId").val());
		}
		
		$(".uploadify",$.pdialog.getCurrent()).uploadify('upload','*');
		setTimeout(function () {$("#" + $("#domainSubmit").val()).click();},3000);
	}
	
	function eventCompexBCBXZ(){
		if($("#isFirstTab").val()=="false"){
			if($("#domainId").val()==""||$("#domainId").val()==undefined){
				alertMsg.warn('请先保存主记录！');
				return;
			}
		}
		$("#domainId").val("");
		var formid=$("#domainSubmit").val().replace(/Submit/,"FormID");
		$("#"+formid).attr("action",bcUrl+"?domainId="+$("#domainId").val());
		$("#" + $("#domainSubmit").val()).click();
		//$.pdialog.closeCurrent();
		//$.pdialog.open(xjUrl,"newDialog",selfXjTitle,{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
		$.pdialog.reload(xjUrl);
	}
	
	function eventCompexXG() {
		$.ajaxSetup( {async : false});
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		dataType: "json",
	  		url:__basePath + '/pages/resource/compexisEdit.action?'+xgUrl.split('?')[1]+'&rule='+$('#rule').val(),
	  		success:function(data){
				if(data == 'true'){
					$.pdialog.close("whDialog");
					$.pdialog.open(xgUrl,"xgDialog",selfWhTitle,{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
				}else{
					alertMsg.warn("此数据为其他用户创建,你无权修改！");
				}
	  		}
	  	});
		
		$.ajaxSetup( {async : true});
	}
	
	function eventCompexFORMXG(){
		$.pdialog.close("whDialog");
		$.pdialog.open(xgUrl,"xgDialog",selfWhTitle,{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
	}
	
	function eventCompexPLSC(){
		var urlString = plscUrl;
		var param = $("#tableForm").serialize();
		var result = urlString + "&" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		//判断是否可以删除  start
		var candelete="true";
		var dparam =result.substr(result.indexOf("?")+1,result.length);
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		url:__basePath + '/pages/resource/compexisCanDelete.action?'+dparam,
	  		success:function(data){
				candelete=data;
	  		}
	  	});
		
		if(candelete=="false"){
			alertMsg.warn("选中的组件已经被其他模块引用，不能删除！");
			return false;
		}
		if(candelete=="false.isdefault"){
			alertMsg.warn("选中的记录中包含默认的数据，不能删除！");
			return false;
		}
		if(candelete=="false.system"){
			alertMsg.warn("系统数据，不能删除！");
			return false;
		}
		//判断是否可以删除  end
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
	}
	
	function eventCompexLJSC(){
		var urlString = ljscUrl;
		var param = $("#tableForm").serialize();
		var result = urlString + "&" + param;
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		//判断是否可以删除  start
		var candelete="true";
		var dparam =result.substr(result.indexOf("?")+1,result.length);
		$.ajax({
	  		type:'POST',
	  		async: false,
	  		url:__basePath + '/pages/resource/compexisCanDelete.action?'+dparam,
	  		success:function(data){
				candelete=data;
	  		}
	  	});
		
		if(candelete=="false"){
			alertMsg.warn("选中的组件已经被其他模块引用，不能执行删除操作！");
			return false;
		}
		//判断是否可以删除  end
		alertMsg.confirm("确定要删除吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
	}
	
	function eventCompexPLSCJS(){
		var param = $("#tableForm").serialize();
		$.ajax({
	  		type:'post',
	  		async: false,
	  		url:__basePath + '/pages/system/funcRoledeleteRoleRoles.action?'+param,
	  		success:function(data){
				eventCompexPLSC();
	  		}
	  	});		
	}
	
	//整个模块导出
	function eventCompexDC() {
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要导出的目录!");
			return;
		}
		if(items > 1) {
			alertMsg.warn("只能选择一个目录!");
			return;
		}
		var param = $("input[type='checkbox']:checked").val();
		var id=param.substring(param.indexOf(":")+1,param.length-1);
		$.pdialog.open(__basePath + "/pages/resource/catalog/catalogExport.jsp?id=" + id,"appDialog", "导出", {
			width : 450,
			height : 180,
			mask : true,
			resizable : false
		});
	}
	
	//整个模块导入
	function eventCompexDR(){
		$.pdialog.open(__basePath + "/pages/resource/catalog/catalogImport.jsp","appDialog", "导入", {
			width : 400,
			height : 150,
			mask : true,
			resizable : false
		});
	}
	
	//彻底删除整个目录相关的所有数据
	function eventCompexCDSC() {
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要删除的目录!");
			return;
		}
		if(items > 1) {
			alertMsg.warn("只能选择一个目录!");
			return;
		}
		var param = $("input[type='checkbox']:checked").val();
		var id=param.substring(param.indexOf(":")+1,param.length-1);
		var result = __basePath + "/pages/resource/catalogTreedeleteAll.action?id=" + id;
		alertMsg.confirm("该模块下所有数据，包括表单、列表、数据表<br><br>和字段都将清除，确定要彻底删除吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
	}
	
	function eventCompexCKWJ(fileId){
		/*var param = $("#tableForm").serialize();
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要查看的文件!");
			return;
		}
		if(items>1){
			alertMsg.warn("只能选择一个文件!");
			return;
		}*/
		window.open(__basePath + "/pages/resource/uploadifyshowFile.action?fileId="+fileId);
	}
	
	// 2012-07-11
	function eventCompexZW() {
		$.pdialog.open(zwUrl,"appDialog", "正文", {
							width : 950,
							height : 650,
							mask : true,
							resizable : false
						});
	}
	
	// 2012-07-13
	function eventCompexQWYL() {
		var param = $('#paramsId').val().split(';')[0];
		var qwylUrl = __basePath + "/pages/third/office/officepreview.action?params="+param+"&formId="+$('#formId').val();//全文预览URL
		$.pdialog.open(qwylUrl,"pvDialog", "全文预览", {
							width : 950,
							height : 650,
							mask : true,
							resizable : false
						});
	}
	
	//2012-08-09
	function eventCompexBZ(){
		$.pdialog.open(bzUrl,"helpDialog", "帮助信息", {
			width : 950,
			height : 650,
			mask : true,
			resizable : false
		});
	}
	
	//add by lq 2012-9-18
	function eventCompexFORMBZ(){
		$.pdialog.open(formbzUrl,"formhelpDialog", "帮助信息", {
			width : 950,
			height : 650,
			mask : true,
			resizable : false
		});
	}
	
	function compexTabTJ(pf,t,p){
		if($("#domainId").val()==""||$("#domainId").val()==undefined){
			alertMsg.warn('请先保存主记录！');
			return;
		}
		var domianId=$("#domainId").val();
		var mainTable=$("#mainTable").val();
		params=mainTable+"-id:"+domianId+";";
		$("input[name='params']").each(function(){
			var $this = $(this);
			$this.val(params);
		});
		var fileName=pf+t+p;
		$("#"+fileName+"ID").val("");
		$("#"+fileName+"FormID").attr("action",tjTabUrl+"?domainId="+$("#domainId").val());
		$.ajaxSetup({async: false});
		$("#"+fileName+"Submit").click();
		$.ajaxSetup({async: true});
		$(".goto").each(function(){
			var $this = $(this);
			$this.click();
		});
	}
	
	function compexTabUploadifyTJ(pf,t,p){
		if($("#domainId").val()==""||$("#domainId").val()==undefined){
			alertMsg.warn('请先保存主记录！');
			return;
		}
		var domianId=$("#domainId").val();
		var mainTable=$("#mainTable").val();
		params=mainTable+"-id:"+domianId+";";
		$("input[name='params']").each(function(){
			var $this = $(this);
			$this.val(params);
		});
		var fileName=pf+t+p;
		$("#"+fileName+"ID").val("");
		$("#"+fileName+"FormID").attr("action",tjTabUrl+"?domainId="+$("#domainId").val());
		$.ajaxSetup({async: false});
		$(".uploadify",$.pdialog.getCurrent()).uploadify('upload','*');
		setTimeout(function () {$("#"+fileName+"Submit").click();},3000);
		$.ajaxSetup({async: true});
		$(".goto").each(function(){
			var $this = $(this);
			$this.click();
		});
	}
	
	function compexTabBC(pf,t,p){
		if($("#domainId").val()==""||$("#domainId").val()==undefined){
			alertMsg.warn('请先保存主记录！');
			return;
		}
		var fileName=pf+t+p;
		
		$("#"+fileName+"FormID").attr("action",bcTabUrl+"?domainId="+$("#domainId").val());
		$.ajaxSetup({async: false});
		$("#"+fileName+"Submit").click();
		$.ajaxSetup({async: true});
		$(".goto").each(function(){
			var $this = $(this);
			$this.click();
		});
	}
	
	function compexTabUploadifyBC(pf,t,p){
		if($("#domainId").val()==""||$("#domainId").val()==undefined){
			alertMsg.warn('请先保存主记录！');
			return;
		}
		var fileName=pf+t+p;
		
		$("#"+fileName+"FormID").attr("action",bcTabUrl+"?domainId="+$("#domainId").val());
		$.ajaxSetup({async: false});
		$(".uploadify",$.pdialog.getCurrent()).uploadify('upload','*');
		setTimeout(function () {$("#"+fileName+"Submit").click();},3000);
		$.ajaxSetup({async: true});
		$(".goto").each(function(){
			var $this = $(this);
			$this.click();
		});
	}
	
	function compexTabSC(){
		var form=partitionListDiv;
		var urlString = scTabUrl+"&mainTable="+$("#mainTable").val();
		var param = $("#"+form+"Form").serialize();
		var result = urlString + "&" + param;
		
		if(param==undefined || param==''){
			alertMsg.warn("请选择要删除的数据!");
			return;
		}
		
		alertMsg.confirm("确定要删除吗?", {okCall:function(){
				$.ajaxSetup({async: false});
				ajaxTodo(result,dialogRefresh);
				$.ajaxSetup({async: true});
			}
		});
	}
	
	function loadCompexEditSubList(tabId,id,plId,str) {
		var pfId = plId.replace(/List/,'Form');
		var model = $("#" + pfId+"Model").val();
		var partitionId = $("#" + pfId + "Pid").val();
		var urlString = whTabUrl+"op="+str+"&tabId="+tabId+"&model="+model+"&subDomainId=" + id +"&partitionId=" + partitionId+"&partitionForm="+pfId;
		$("#" + pfId).loadUrl(urlString);
	}
	
	function setCompexTabForm(id,index) {
		if(index==0){
			$("#isFirstTab").val("true");
		}else{
			$("#isFirstTab").val("false");
		}
		$("#tabId").val(id);
		$("#domainSubmit").val(id+"Submit");
	}
	
	/*
	 * 自定义启用按钮区域,在注入借口完成后,去掉
	 */
	function eventCompexXZJS(url,param){
		var id = param.substring(param.indexOf(":")+1,param.length-1);
		$.pdialog.open(__basePath + "/pages/system/funclevel/userRoles.jsp?id="+id,"xzjsDialog","选择角色",{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
	}
	
	function eventCompexXZQXZ(url,param){
		var id=param.substring(param.indexOf(":")+1,param.length-1);
		$.pdialog.open(__basePath + "/pages/system/funclevel/roleRoles.jsp?id="+id,"xzqxzDialog","选择权限组",{width:1280,height:650,mask:true,resizable:true});
	}
	
	function eventCompexMBYL(){
		$.pdialog.open(__basePath + "/pages/resource/template/base/templateYL.jsp","mbylDialog","表单预览",{width:fDefaultWidth,height:fDefaultHeight,mask:true,resizable:true});
	}
	
	function eventCompexFORMQY(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_tableui&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);setFormUISize();}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
		
	function eventCompexTIPQY(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_tip&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
		
	function eventCompexTREEQY(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_treeui&colName=tbl_isdefault&';
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);setTreeUISize}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
		
	function eventCompexSEARCHQY(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_searchui&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);setSearchUISize}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexICONQY(){
		var urlString = __basePath + '/pages/system/compexisDefault.action?mainTable=sys_icon&colName=tbl_isdefault&';
		
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "?params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexLAYOUTQY(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_layout&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexZCLQY(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=bus_zichulizujian&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	function eventCompexQYXXMR(){
		
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_enterpriseinfo&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){
				$.ajaxSetup( {async : false});
				ajaxTodo(result,refreshList);
				$.ajax({
			  		type:'POST',
			  		async: false,
			  		dataType:"json",
			  		url:__basePath + '/pages/resource/enterpriseInfoloadDefault.action',
			  		success:function(data){
						$("#systemCnName").html(data.systemCnName);
						$("#systemEnName").html(data.systemEnName);
						$("#enterprisename").html(data.enterPricename);
						document.title = data.systemCnName;
			  		}
			  	});
				$.ajaxSetup( {async : true});
			}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
		
	}
	function eventCompexMR(){
		var urlString = mrUrl;
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexTEXTBOXMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_textbox&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexTEXTAREAMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_textarea&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexCOMBOXMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_combox&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexSEARCHCOMBOXMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_searchcombox&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexUPLOADBOXMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_uploadfilebox&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexPASSWORDBOXMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_passwordbox&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexRADIOMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_radiomgt&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexCHECKBOXMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_checkboxmgt&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexTREEMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_tree&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexDATECONTROLMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_riqizujian&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	function eventCompexTEXTEDITORMR(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_texteditor&colName=tbl_isdefault&';
		var items = $("input[name='selectedVOs']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	function eventCompexDWJSC(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_uploadify&colName=tbl_isdefault&';
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	function eventCompexDMJL(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_codecasecade&colName=tbl_isdefault&';
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	function eventCompexMOBANFZ(){
		var urlString = __basePath + '/pages/resource/templatecopyBase.action?';
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要另存吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	function eventCompexDMCSH(){
		alertMsg.confirm("确定要初始化编码吗?", 
			{okCall:function(){
				ajaxTodo(__basePath + "/pages/system/seqCodeCompexinit.action",refreshList);
			}
		});
	}
	/***
	 * 生成业务---------------------------------------end-------------------------------------------
	 */
	function eventCompexFONTMR() {
		var chks=$("input[name='selectedVOs']");
		var count=0;
		var param;
		for(var i=0;i<chks.length;i++){
			if(chks[i].checked==true){
				count+=1;
				param = chks[i].value;
			}
		}
		if(count==0){
			alertMsg.warn("请选择字体！");
			return;
		}
		if(count>1){
			alertMsg.warn("只能选择一个字体！");
			return;
		}
		
		var id = param.substring(param.indexOf(":")+1,param.indexOf(";"));
		var url = __basePath + "/pages/system/fontenable.action?id=" + id;
		ajaxTodo(url,refreshList);
	}
	function eventCompexMENUMR(){
		var chks=$("input[name='selectedVOs']");
		var count=0;
		var param;
		for(var i=0;i<chks.length;i++){
			if(chks[i].checked==true){
				count+=1;
				param = chks[i].value;
			}
		}
		if(count==0){
			alertMsg.warn("请选择菜单！");
			return;
		}
		if(count>1){
			alertMsg.warn("只能选择一个菜单！");
			return;
		}
		
		var id = param.substring(param.indexOf(":")+1,param.indexOf(";"));
		var url = __basePath + "/pages/system/menuchange.action?id=" + id;
		ajaxTodo(url,refreshList);
	}
	
	function query() {
		$("#querySubmit").click();
	}
	
	function advanceQuery() {
		$("#advanceQuerySubmit").click();
	}
	
	/**
	 * 表单复制
	 */
	function eventCompexCOPYFORM(){
		var items = $("input[type='checkbox']:checked").length;
		if(items == 0){
			alertMsg.warn("请选择要你要复制的表单！");
			return;
		}
		if(items > 1){
			alertMsg.warn("只能选择一个表单！");
			return;
		}
		//判断是否可以删除  end
		alertMsg.confirm("确定要复制吗？", {okCall:function(){
			$.pdialog.open(__basePath + '/pages/resource/form/copyForm.jsp', 'copyformDialog', '输入表单名称', {width:"290",height:"70",max:false,mask:true,maxable:false,minable:false,resizable:false,drawable:false});
		}});
	}
	
	/**
	 * 门户设为默认
	 */
	function eventCompexMHDEFAULT(){
		var urlString = __basePath + '/pages/resource/compexisDefault.action?mainTable=sys_portal&colName=tbl_isdefault&';
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			var result = urlString + "params=" + param;
			alertMsg.confirm("确定要设为默认吗?", {okCall:function(){ajaxTodo(result,refreshList);}});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	/**
	 * 统计日志信息
	 */
	function eventCompexRZTJ(){
		$.pdialog.open(__basePath + "/pages/resource/report/compexReport.jsp","compexReport","统计信息",{width:800,height:600,mask:true,resizable:true});
	}
	
	/**
	 * 统计构件/组件信息
	 */
	function eventCompexTONGJITU(){
		$.pdialog.open(__basePath + "/pages/resource/useinfo/compStat.jsp","compStat","构件/组件统计信息",{width:1000,height:700,mask:true,resizable:true});
	}
	/**
	 * 导出Excel表
	 * author: Jason
	 */
	function eventCompexExportExcel(){
		var formid = xjUrl.substring(xjUrl.indexOf("formId=")+7);
		var listid = bzUrl.substring(bzUrl.indexOf("listId=")+7);
		if(formid==''||listid==''){
			alertMsg.warn("该列表数据不支持导出");
			return false;
		}
		var param = $("#pagerForm", navTab.getCurrentPanel()).serialize();
		var exportUrl = __basePath + '/pages/resource/compexexportExcel.action?'+encodeURI(param);
	 	window.open(exportUrl);
	}
	/**
	 * 导入Excel表
	 * Author:Jason
	 */
	function eventCompexImportExcel(){
		$.pdialog.open(__basePath + "/pages/core/excelimport.jsp","uploadExcelFileDialog", "请选择您要导入的Excel文件", {
			height : 90,
			width : 350,
			minH : 40,
			minW : 50,
			mask : true,
			resizable : false,
			max : false
		});
	}
	/**
 	 * 邮件发送(点击通知按钮触发)
	 */
	function eventCompexEmailSend(){
		var formId = $("#formId").val();
		var domainId = $("#domainId").val();
		if (domainId == "" ||domainId == undefined) {
			alertMsg.warn('请先保存主记录！');
			return false;
		}
		//发送Action
		var fsUrl = __basePath + "/pages/module/mail/sendUrl.action"+"?formId="+formId+"&domainId="+domainId;
		$.pdialog.open(fsUrl,"emailDialog","发送邮件通知",{width:fSelfWidth,height:fSelfHeight,mask:true,resizable:true});
	}
	
	/**
	 * 机构人员同步到RTX
	 */
	function eventCompexSYNCOU(){
		var result = __basePath + "/pages/module/org/synchro.action";
		alertMsg.confirm("是否将机构用户同步到RTX?", {okCall:function(){ajaxTodo(result,refreshList);}});
	}
	/**
	 * mysql数据备份
	 */
	function eventCompexMYSQLSJBF(){
		alertMsg.confirm("确定要备份数据吗?", 
			{okCall:function(){
				ajaxTodo(__basePath + "/pages/system/mysqlbackup.action",refreshList);
			}
		});
	}
	
	/**
	 * mysql数据恢复
	 */
	function eventCompexMYSQLSJHF(){
		var items = $("input[type='checkbox']:checked").length;
		if(items == 1){
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length-1);
			$.ajax({
		  		type:'POST',
		  		url:__basePath + '/pages/resource/compextableData.action?mainTable=sys_mysqlbackup&params=id:'+param.split(":")[1],
		  		dataType:'json',
		  		success:function(data){
					$.each(data,function(entryIndex, entry){
						alertMsg.confirm("确定要恢复数据吗?", 
							{okCall:function(){
								ajaxTodo(__basePath + "/pages/system/mysqlrecover.action?filename="+entry.tbl_filename,refreshList);
							}
						});
					})
		  		}
		  	});
		}else{
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	/**
	 * 机构树
	 */
	function showOrgTree(showid,hideid){
		$.pdialog.open(__basePath + "/pages/system/org/orgTree.jsp?showid="+showid+"&hideid="+hideid,"selectDialog","选择部门",{width:300,height:600,mask:true,resizable:false,minable:false,mixable:false});
	}
	
	function eventCompexConfigColumns(tableid){
		$.pdialog.open(__basePath + '/pages/resource/tablecolumnCfg.action?tableId='+tableid,"configColumnDialog","生成代码设置",{width:950,height:600,mask:true,resizable:false,minable:false,mixable:false});
	}
	
	function eventCompexBBBGNEW() {
		var urlString = __basePath + '/pages/resource/tablechange.action';
		var items = $("input[type='checkbox']:checked").length;
		if (items == 1) {
			var param = $("#tableForm").find('input:checked').val();
			param = param.substring(0, param.length - 1);
			var id = param.split(":")[1];
			var result = urlString + "?id=" + id;
			alertMsg.confirm("确定要版本变更吗?", {
				okCall : function() {
					ajaxTodo(result, refreshList);
				}
			});
		} else {
			alertMsg.warn("请选择一条数据!");
			return;
		}
	}
	
	function eventCompexLSBB(id) {
		$.pdialog.open(__basePath + "/pages/resource/tablehistory.action?id=" + id,
				"lsbbDialog", "历史版本", {
					width : 800,
					height : 600,
					mask : true,
					resizable : true
				});
	}
	
	function eventCompexVISIT(url,param) {
		var id = param.substring(param.indexOf(":")+1,param.length-1);
		$.ajax({
	  		type:'POST',
	  		url:__basePath+'/pages/system/subsystem/visit.action?subSystemId='+id,
	  		dataType:"json",
	  		success:function(data){
				window.open(data.defaultUrl, "_blank");
	  		}
	  	});
	}