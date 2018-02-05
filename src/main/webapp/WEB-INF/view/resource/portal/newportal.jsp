<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
function loadLeft(){
	$.ajax({
		url:'<%=basePath%>/pages/resource/portal/portletfetch.action',
		data:{param:$("#leftIDs").val()},
		method:'get',
		async:false,
		global:false,
		success:function(data){
			$("#leftpart").html(data);
			$("#leftpart .portlet").css("min-height",($("#desktop").height()-15)/$("#leftpart .portlet").size());
		},
		error:function(errorinfo){
		}
	});
}
function loadMiddle(){
	$.ajax({
		url:'<%=basePath%>/pages/resource/portal/portletfetch.action',
		data:{param:$("#middleIDs").val()},
		method:'get',
		async:false,
		global:false,
		success:function(data){
			$("#middleDiv").html(data);
			$("#middlepart .portlet").css("min-height",($("#desktop").height()-15)/$("#middlepart .portlet").size());
			loadChart(($("#desktop").height()-15)/$("#leftpart .portlet").size() - 27);
		},
		error:function(errorinfo){
		}
	});
}

function loadRight(){
	$.ajax({
		url:'<%=basePath%>/pages/resource/portal/portletfetch.action',
		data:{param:$("#rightIDs").val()},
		method:'get',
		global:false,
		success:function(data){
			$("#rightpart").html(data);
			$("#rightpart .portlet").css("min-height",($("#desktop").height()-15)/$("#rightpart .portlet").size());
		},
		error:function(errorinfo){
		}
	});
}

function loadChart(height) {
	$.ajax( {
		type : 'POST',
		url : '<%=basePath%>/pages/resource/useinfostate.action?number=9',
		dataType:'json',
		async:false,
		success : function(data) {
			if($.browser.version < 9){
				buildFSPie(data,height);
			}else{
				try{
					buildPie(data,height);	
				}catch(e){}
			}
		}
	});
}

//生成饼图
function buildPie(data,height) {
	var chart = new iChart.Pie3D({
		render : 'chartId',
		data: data,
		sub_option : {
			mini_label_threshold_angle : 40,//迷你label的阀值,单位:角度
			mini_label:{//迷你label配置项
				fontsize:20,
				fontweight:600,
				color : '#ffffff'
			},
			label : {
				background_color:null,
				sign:false,//设置禁用label的小图标
				padding:'0 6',
				border:{
					enable:false,
					color:'#666666'
				},
				fontsize:11,
				fontweight:normal,
				color : '#4572a7'
			},
			border : {
				width : 2,
				color : '#ffffff'
			},
			listeners:{
				parseText:function(d, t){
					return d.get('value');//自定义label文本
				}
			} 
		},
		legend:{
			enable:true,
			padding:0,
			line_height:20,//设置行高
			sign_space:10,//小图标与文本间距
			border:false,
			align:'right',
			background_color : null//透明背景
		}, 
		shadow : true,
		shadow_blur : 6,
		shadow_color : '#aaaaaa',
		shadow_offsetx : 0,
		shadow_offsety : 0,
		background_color:'#f8f8f8',
		border : {
			color : '#f8f8f8'
		},
		align:'left',//右对齐
		offsetx:10,//设置向x轴负方向偏移位置60px
		offset_angle:-90,//逆时针偏移120度
		width : $("#chartId").parent().width(),
		height : height,
		radius:150
	});
	chart.bound(0);
}

function buildFSPie(data,height) {
	var tmp = systemSort(data);
	var dataXml = "<chart formatNumberScale='0' baseFontSize='12' palette='4' decimals='0' enableSmartLabels='1' enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70' >";
   	for(var i=0; i<tmp.length; i++) {
   		if(i==0) {
   			dataXml += "<set label='" + data[i]["name"] + "' value='" + data[i]["value"] + "' isSliced='1'></set>";
   		}else{
			dataXml += "<set label='" + data[i]["name"] + "' value='" + data[i]["value"] + "'></set>";    			
   		}
   	}
   	dataXml += "</chart>";    	
   	var width = Math.floor($("#chartId").parent().width());
   	height = Math.floor(height);
   	var myChart = new FusionCharts("<c:url value='/js/FusionCharts/Charts/Pie3D.swf'/>", "cId", width, height, "0", "0");
   	myChart.setDataXML(dataXml);
   	myChart.setTransparent(true);
	myChart.render("chartId");
	document.title = ypTitle;
}

function gotoList(listid,portletName){
	navTab.reload("<%=basePath%>/pages/resource/compexlist.action?listId="+ listid, {navTabId : "main"});
	$("#navTitle").html(portletName);
}
function gotoStat() {
	navTab.openTab("main", "<%=basePath%>/pages/resource/compexlist.action?listId=144");
	$("#navTitle").html("构件/组件使用信息");
}

$(function(){
	loadLeft();
	loadMiddle();
	loadRight();
	$("#rightpart,#middlepart,#leftpart" ).sortable({
		connectWith: '.sortable',
		cursor: 'move',
		helper: 'original',
		tolerance :'pointer',
		placeholder:'sort_placeholder',
		opacity:0.7
    });
});
</script>
</head>
<body>
<div id="pcontent">
<input type="hidden" id="leftIDs" value="${leftIDs}"/>
<input type="hidden" id="middleIDs" value="${middleIDs}"/>
<input type="hidden" id="rightIDs" value="${rightIDs}"/>

<table class="portaltable">
<tr valign="top">
	<td id="leftpart" class="leftpart sortable">
	</td>
	<td id="middlepart" class="middlepart sortable">
		<ul class="portlet" style="min-height: 250px; max-height:250px;overflow:hidden;">
		<div class="phead">
			<div class="ptitle">
				<img src="<c:url value="./pages/resource/portal/image/titleicon.gif"/>" style="padding-top:5px;">
				前十位构件/组件使用情况统计
			</div>
			<div class="poper" style="padding-top:6px;"><a href="#" onclick="gotoStat();" style="text-decoration: none;" title="列表查看所有信息">更多</a> </div>
		</div>
		<div class="pcontent">
			<div id="chartId" align="center" />
		</div>
		</ul>
		<div id="middleDiv"></div>
	</td>
	<td id="rightpart" class="rightpart sortable"  >
	</td>
</tr>
</table>
</div>
</body>
</html>