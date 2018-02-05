<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<html>
<head>
<script type="text/javascript">
	$(function() {
		changChart(1);
	});
	
	function changChart(value) {
		$.ajax( {
			type : 'POST',
			url : '<%=basePath %>/pages/resource/useinfostate.action?number=15',
			dataType:'json',
			success : function(data) {
				try{
					if(value == 1) {
						if($.browser.version < 9){
							buildFSColumn(data);
						}else{
							buildColumn(data);
						}
					}else if(value == 2) {
						if($.browser.version < 9){
							buildFSPie(data);
						}else{
							buildPie(data);
						}				
					}else {
						if($.browser.version < 9){
							buildFSLine(data);
						}else{
							buildLine(data);
						}
					}
				}catch(e){				
				}
			}
		});
	}
	
	function buildPie(data) {
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
					fontweight:600,
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
				offsetx:-100,
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
			background_color:'#f1f1f1',
			align:'left',//右对齐
			offsetx:100,//设置向x轴负方向偏移位置60px
			offset_angle:-90,//逆时针偏移120度
			width : 990,
			height : 600,
			radius:150
		});
		chart.bound(0);
	}
	
	function buildColumn(data) {
		var chart = new iChart.Column3D({
			render : 'chartId',
			data: data,
			width : 990,
			height : 600,
			shadow : true,
			shadow_blur : 2,
			shadow_color : '#aaaaaa',
			shadow_offsetx : 1,
			shadow_offsety : 0,
			zScale:0.5,
			xAngle : 50,
			offsety:-80,
			bottom_scale:1.1,
			label:{
				color:'#4c4f48',
				rotate:90,
				textBaseline:'top',
				textAlign:'left'
			},
			sub_option:{
				label : {
					color : '#2c2e2a'
				},
				listeners:{
					parseText:function(r,t){
						return r.get('value');
					}
				}
			},
			text_space : 16,//坐标系下方的label距离坐标系的距离。
			coordinate:{
				background_color : '#333333',
				grid_color : '#a4ad96',
				color_factor : 0.24,
				board_deep:10,
				pedestal_height:10,
				left_board:false,//取消左侧面板
				width:990,
				shadow:true,//底座的阴影效果
				wall_style:[{//坐标系的各个面样式
					color : '#333333'
				},{
					color : '#b2b2d3'
				}, {
					color : '#a6a6cb'
				},{
					color : '#333333'
				},{
					color : '#74749b'
				},{
					color : '#a6a6cb'
				}],
				scale:[{
					 position:'left',	
					 start_scale:0,
					 scale_space:80,
					 label:{
						color:'#4c4f48'
					 }
				}]
			}
		});
		chart.draw();
	}
	
	function buildLine(data){
		var values = new Array();
		var labels = new Array();
		for(var i=0;i<data.length;i++){
			values[i]=data[i]["value"];
			labels[i]=data[i]["name"];
		}
		var dt = [
		        	{
		        		name : '北京',
		        		value:values,
		        		color:'#1f7e92',
		        		line_width:3
		        	}
		       ];
		var chart = new iChart.LineBasic2D({
			render : 'chartId',
			data: dt,
			width : 990,
			height : 600,
			offsety:-70,
			coordinate:{height:'90%',background_color:'#f6f9fa'},
			sub_option:{
				hollow_inside:false,//设置一个点的亮色在外环的效果
				point_size:16,
			},
			labels:labels,
			label:{
				color:'#4c4f48',
				rotate:90,
				textBaseline:'top',
				textAlign:'left'
			}
		});
		chart.draw();
	}

	//生成饼图
	function buildFSPie(data) {
		var dataXml = "<chart caption='构件/组件使用情况统计' formatNumberScale='0' baseFontSize='12' palette='4' decimals='0' enableSmartLabels='1' enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70' >";
    	for(var i=0; i<data.length; i++) {
    		if(i==0) {
    			dataXml += "<set label='" + data[i]["name"] + "' value='" + data[i]["value"] + "' isSliced='1'></set>";
    		}else{
				dataXml += "<set label='" + data[i]["name"] + "' value='" + data[i]["value"] + "'></set>";    			
    		}
    	}
    	dataXml += "</chart>";    	
    	
    	var myChart = new FusionCharts("<c:url value='/js/FusionCharts/Charts/Pie3D.swf'/>", "ucPieId", "990", "590", "0", "0");
    	myChart.setDataXML(dataXml);
    	myChart.setTransparent(true);
		myChart.render("chartId");
		document.title = ypTitle;
	}
	
	//生成柱状图
	function buildFSColumn(data) {
		//封装xml
		var dataXml = "<chart caption='构件/组件使用情况统计' xAxisName='构件/组件' yAxisName='value' formatNumberScale='0' baseFontSize='12' shownames='1' showvalues='0' decimals='0' >"
		for(var i=0; i<data.length; i++) {
			dataXml += "<set label='" + data[i]["name"] + "' value='" + data[i]["value"] + "'></set>";
		}
		dataXml += "</chart>";
		//定义一个FusionCharts对象，并指定其显示样式
		var myChart = new FusionCharts("<c:url value='/js/FusionCharts/Charts/Column3D.swf'/>", "ucColumnId", "990", "600", "0", "0");
		//加载xml
		myChart.setDataXML(dataXml);
		myChart.setTransparent(true);
		//渲染数据
		myChart.render("chartId");
		document.title = ypTitle;
	} 
	
	//生成线状图
	function buildFSLine(data) {
		//封装xml
		var dataXml = "<chart caption='构件/组件使用情况统计' xAxisName='构件/组件' yAxisName='数量' formatNumberScale='0' baseFontSize='12' showValues='0' alternateHGridColor='FCB541' alternateHGridAlpha='20' divLineColor='FCB541' divLineAlpha='50' canvasBorderColor='666666' baseFontColor='666666' lineColor='FCB541'>";
		for(var i=0; i<data.length; i++) {
			dataXml += "<set label='" + data[i]["name"] + "'value='" + data[i]["value"] + "'></set>";
		}
		dataXml += "</chart>";
		//定义一个FusionCharts对象，并指定其显示样式
		var myChart = new FusionCharts("/js/FusionCharts/Charts/Line.swf", "ucLineId", "990", "600", "0", "0");
		//加载xml
		myChart.setDataXML(dataXml);
		myChart.setTransparent(true);
		//渲染
		myChart.render("chartId");
		document.title = ypTitle;
	}
	
</script>
</head>
<body>
<br>
<div>
	&nbsp;&nbsp;
	<input name="type" type="radio" checked value="1" onclick="changChart(this.value)">柱状图
	<input name="type" type="radio" value="2" onclick="changChart(this.value)">饼图
	<input name="type" type="radio" value="3" onclick="changChart(this.value)">线状图
	
</div>
<br>
<br>
<div id="chartId" align="center" style="width:990px;height:600px;"/>
</body>
</html>