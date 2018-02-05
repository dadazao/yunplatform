<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	
	//禁止缓存		
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<link rel="shortcut icon" href="favicon.ico"/>
	<title>${enterpriseInfo.systemCnName}</title>
	<link href="<%=basePath%>/themes/${theme.themeCode}/style.${min}css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="<%=basePath%>/themes/css/print.${min}css" rel="stylesheet" type="text/css" media="print"/>		
	<link href="<%=basePath%>/themes/css/jquery-ui.${min}css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="<%=basePath%>/themes/css/core.${min}css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="<%=basePath%>/js/uploadify/uploadify.css" rel="stylesheet" type="text/css" media="screen">
	<link href="<%=basePath%>/themes/css/ztree/zTreeStyle.css" rel="stylesheet" type="text/css">
	<!--[if IE]>
	<link href="<%=basePath%>/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->
	
	<script type="text/javascript">
		var userid = '${session.user.id}';
		var currpath = '<%=path%>';
		var timeOut = '<%=session.getMaxInactiveInterval()%>';  
		var lastActivity = new Date().getTime();
		var preId = '1';
		var __basePath = "<%=basePath%>";
	</script>
	
	<script src="<%=basePath%>/js/common/namespace.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/common/common.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery/jquery-1.7.1.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery/jquery-ui.1.9.0.${min}js" type="text/javascript" ></script>
	<script src="<%=basePath%>/js/jquery/jquery-ui-i18n.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery/jquery.cookie.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery/jquery.validate.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jquery/jquery-combobox.${min}js" type="text/javascript" ></script>
	<script src="<%=basePath%>/js/jquery/jquery.PrintArea.${min}js" type="text/javascript" ></script>		
	<script src="<%=basePath%>/js/uploadify/jquery.uploadify-3.1.${min}js" type="text/javascript" ></script>
	<script src="<%=basePath%>/js/ichartjs/ichart.1.1.${min}js" type="text/javascript" ></script>
	
	<script src="<%=basePath%>/js/dwz/dwz.core.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.util.date.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.validate.method.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.regional.zh.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.barDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.drag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.tree.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.accordion.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.ui.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.theme.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.switchEnv.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.alertMsg.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.contextmenu.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.navTab.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.tab.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.resize.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.dialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.dialogDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.sortDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.cssTable.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.stable.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.taskBar.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.ajax.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.pagination.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.database.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.datepicker.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.effects.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.panel.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.checkbox.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.history.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.combox.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.print.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwz/dwz.regional.zh.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwr/engine.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwr/util.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/dwr/interface/MessagePusher.js" type="text/javascript"></script>
	<!--
	<script src="<%=basePath%>/bin/dwz.min.js" type="text/javascript"></script>
	-->
	
	<script src="<%=basePath%>/js/artDialog/artDialog.source.${min}js?skin=opera" type="text/javascript"></script>
	<script src="<%=basePath%>/js/xheditor/xheditor-1.1.12-zh-cn.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/xheditor/xheditor-1.1.12-en.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/xheditor/xheditor-1.1.12-zh-tw.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ckeditor/ckeditor.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ckeditor/adapters/jquery.js"></script>
	<script src="<%=basePath%>/js/My97DatePicker/WdatePicker.js" type="text/javascript" language="javascript" ></script>
	<script src="<%=basePath%>/js/cloudstong/yun.form.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/cloudstong/yun.button.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/cloudstong/yun.list.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/cloudstong/yun.tip.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/cloudstong/yun.office.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/cloudstong/yun.office.${min}js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/cloudstong/yun.sysDialog.${min}js" type="text/javascript"></script>
	
	<script type="text/javascript" src="js/jquery/jquery.ztree.all-3.5.min.js"></script>
	
	<script type="text/javascript">
		$(function() {
			//加载左边菜单
			$.ajaxSetup( {async : false});
			loadCatalog();
			//初始化
			init();
			$.ajaxSetup( {async : true});
			//加载桌面
			loadDesktop();			
			//点击document重新计算最后激活的时间
			$(document).click(
				function(){
					lastActivity = new Date().getTime();
				}
			);
			//检查session是否过期
			checkTimeOut();
			//隐藏加载提示信息
			setTimeout(hideOverlay,300);
			//系统标题
			var ypTitle=$('title').text();
		});
		
		function init() {
			DWZ.init("dwz.frag.xml", {
				loginTitle : "登录", // 弹出登录对话框
				loginUrl:"<%=basePath%>/logout",	// 跳到登录页面
				statusCode : {
					ok : 200,
					error : 300,
					timeout : 301
				}, //【可选】
				pageInfo : {
					pageNum : "pageNum",
					numPerPage : "numPerPage",
					orderField : "orderField",
					orderDirection : "orderDirection"
				}, //【可选】
				debug : true, // 调试模式 【true|false】
				callback : function() {
					initEnv();
					//$("#themeList").theme( {themeBase : "themes"}); // themeBase 相对于index页面的主题base路径
				}
			});
		}
		
		window.onresize = function(){
			$("div.j-resizeGrid").each(function(){
				var $grid = $(this);
				var nowlength = $grid.innerWidth();
				var tlength = $("table",this).width();
				$("table",this).attr("style","width:"+(nowlength-20)+";");
				var aStyles = [];
	
				var oldThs = $grid.find("thead>tr:last-child").find("th");
				var oldTrs = $grid.find("tbody>tr");
				
				var tl = 0;
				for(var i = 0, l = oldThs.size(); i < l; i++) {
					var $th = $(oldThs[i]);
					var oldwidth = parseInt($th.css("width"));
					var per = $th.attr("per");
					var width = nowlength * parseFloat(per);
					var style = [], width;
					style[0] = Math.ceil(width);
					style[1] = $th.attr("align");
					aStyles[aStyles.length] = style;
				}
				
				var thead = $grid.find("thead");
				var ths = $("th", thead);
				var num;
				ths.each(function(i){
					var $th = $(this), style = aStyles[i];
					$th.find("div").css("width",style[0]);
					$th.css("width",style[0]);	
				});
				oldTrs.each(function(i){
					var $tr = $(this);
					$tr.find(">td").each(function(j){
						var $td=$(this),style=aStyles[j];
						$td.find("div").css("width",style[0]);
						$td.css("width",style[0]);								
					})
				});
			});
		}
		
		function changeTitle(originalTitle) {
			var originalTitle = originalTitle.split("#")[0];  
			addEvent(document,'propertychange', function (evt) {
			    evt = evt || window.event;     
			    if(evt.propertyName === 'title' && document.title){
			    	setTimeout(function () {document.title = originalTitle;}, 1);  
			    }
			});
		}
		function checkTimeOut(){
		    var to = parseInt(timeOut);
		    if(to>0) {
		    	if(new Date().getTime() > lastActivity + to*1000){
       				location.href="<%=basePath%>/logout";
   				}else{
       				window.setTimeout(checkTimeOut, 1000); // check once per second
   				}
		    }
		}
		
		function hideOverlay() {
			$('#overlay').hide();
		}
		
		function loadHome() {
			navTab._switchTab(0);
		}
		
		function loadDesktop() {
			$.ajax( {
				type : 'POST',
				url:'<%=basePath%>/pages/platform/desktop/desktopDesign/desktop.action',
				success : function(data) {
					$("#desktop").html(data);
				}
			});
		}
		
		function loadCatalog() {
			$.ajax( {
				type : 'POST',
				url : '<%=basePath%>/getTreeData.action',
				global:false,
				aysnc:false,
				dataType: "json",
				success : function(data) {
					var divs = "<div class='accordionHeader'><h2><span>Folder</span>";
					var divm = "</h2></div><div class='accordionContent'><ul class='ztree' id='ztree";
					var dive = "'></ul></div>";
					var totalDiv = "";
					var ids = new Array();
					for(var i=0;i<data.length;i++) {
						var accordion = data[i];
						ids[i] = accordion.key.id;
						var div = divs + accordion.key.name + divm + accordion.key.id + dive;
						totalDiv += div;
					}
					$("#mainMenu").after("<div class='accordion' fillSpace='sidebar'>" + totalDiv + "</div>");
					for(var i=0;i<data.length;i++) {
						var setting = {
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
								onClick: leftMenuOnClick
							}
						};
						$.fn.zTree.init($("#ztree"+ids[i]), setting, data[i].value);
					}
				}
			});
		}
		
		function leftMenuOnClick(event, treeId, treeNode){
			if(treeNode.path != undefined){
				//利用定时器,在1/2秒内5次设置title的值,防止title变换
				var titleOrg = $(treeNode.name);
				navTab.openTab(treeNode.id, '<%=basePath%>' + treeNode.path, {title:titleOrg.html()});
			}
		}
		
		function loadMenu() {
			$.ajax( {
				type : 'POST',
				dataType:'json',
				url : '<%=basePath%>/pages/system/menuload.action',
				success : function(data) {
					for(var i=data.length-1;i>=0;i--) {
						$("#homeSplitId").after("<li><a><span onclick=\"menuListener('"+data[i].menuItemName+"','"+data[i].catalogId+"','"+data[i].href+"')\" >"+data[i].menuItemName+"</span></a></li><li class='split_line' style='width:2px;'></li>");
					}
					if ($.fn.navMenu) $("#navMenu").navMenu();
				},
				error: DWZ.ajaxError
			});
		}
		
		function menuListener(title,catalogId,href){
			showZtree("leftMenuTree","<%=basePath%>/pages/resource/treemenuChildren.action","rootId="+catalogId,leftMenuOnClick);
			$("#navTitle").html(title);
			navTab.openTab("main", "<%=basePath%>/pages/core/help/menuhelp.jsp?catalogId="+catalogId);
		}
		
		function loadContact(){
			$.pdialog.open("<%=basePath%>/pages/resource/enterinfo/aboutus.jsp","aboutusDialog","关于我们",{width:630,height:360,mask:true,resizable:true});
		}
		
		function changePassword() {
			$.pdialog.open("<%=basePath%>/pages/home/changePass.jsp","changePasswordDialog","修改密码",{width:400,height:200,mask:true,resizable:true});
		}
		
		function loadList(name,alias,url){
			navTab.openTab(alias,url,{title:name});
		}

		function updateMessage(){
			$.ajax( {
				type : 'POST',
				dataType:'json',
				global : false,
				url: '<%=basePath%>/pages/platform/message/receivedMessage/size.action',
				success : function(data) {
					if(data>0){
						$("#index_MessageDiv").html('新消息<a onclick="loadReceivedMessage()" style="cursor:pointer;"><span style="color:red;">('+data+')</span></a>');
					}else{
						$("#index_MessageDiv").html('');
					}
				}
			});
		}
		function loadReceivedMessage(){
			navTab.openTab('10000001540014','<%=basePath%>/pages/platform/message/receivedMessage/list.action',{title:'收到的消息'});
		}
		function updateDWRSession(){
			//MessagePusher.onPageLoad();
			//updateMessage();
		}
	</script>

	<style type="text/css">
		#header{height:60px;}
		#leftside, #container, #splitBar, #splitBarProxy{top:60px}
	</style>
</head>

<body scroll="no" onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);updateDWRSession();">
	<div id="overlay" style="background:#FFFFFF;position:absolute;top: 0px;left: 0px;width: 100%;height: 100%;z-index: 9999;">
		<div class='overlayProgressBar'>验证用户权限中...</div>
	</div>
	<div id="layout">
		<div id="header">
			<div>
				<div style="width:100%; height:50px;">
			    	<div style="float:left; padding-top:9px; margin-left:15px; width:42px; height:41px;"><img id="logoImg" src="<%=basePath %>/${logo.logoPath}" width="42" height="41" style="float: left;"/></div>
			        <div style="float:left; padding-top:14px;margin-left:10px; height:43px;">
			        	 <div id="systemCnName" style="color:#FFF; font-family:'微软雅黑'; font-size:21px; font-weight:bold; height:25px;">${enterpriseInfo.systemCnName}</div>
			      	     <div id="systemEnName" style="color:#FFF; font-family:'微软雅黑'; font-size:12px; font-weight:bold; height:18px;">${enterpriseInfo.systemEnName}</div>
			        </div>
			        <div style="float:right;">
			        	<table height="22" width="368px" cellspacing="0" cellpadding="0" border="0" background="<%=basePath%>/images/top/topright.gif" align="right">
							<tbody>
								<tr> 
								  <td width="25%" align="center"><a style="cursor:pointer;" onclick="loadHome();"><img height="16" width="39" border="0/" src="<%=basePath%>/images/top/homepage.gif"></a></td>
								  <td width="25%" align="center"><a style="cursor:pointer;" onclick="changePassword();"><img height="16" width="65" border="0/" src="<%=basePath%>/images/top/updatepwd.gif"></a></td>
								  <td width="25%" align="center"><a style="cursor:pointer;" onclick="loadContact();"><img height="16" width="43" border="0/" src="<%=basePath%>/images/top/help.gif"></a></td>
								  <td width="25%" align="center"><a style="cursor:pointer;" href="<%=basePath%>/logout"><img height="16" width="41" border="0/" src="<%=basePath%>/images/top/logoff.gif"></a></td>
								</tr>
							  </tbody>
						</table>
			        </div>
			        <div style="float:right;color:#FFF;margin-top: 8px;margin-right: 30px;">
			        	欢迎您，${user.fullname}
			        </div>
			    </div>
			</div>
		</div>
		
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse" id="mainMenu"><h2>主菜单</h2><div>收缩</div></div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<div class="alertInfo">
							</div>
						</div>
						<div id="desktop"></div>
					</div>
					
				</div>
			</div>
		</div>

	</div>

	<div id="footer">
		<div>&copy;<span id="enterprisename"> ${enterpriseInfo.name}版权所有</span>
			<div style="float: right;margin-right:30px;" id="index_MessageDiv"></div>
		</div>
	</div>


</body>
</html>