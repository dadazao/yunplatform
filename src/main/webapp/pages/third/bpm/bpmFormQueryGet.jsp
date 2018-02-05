
<%--
	time:2012-11-27 10:37:13
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>通用表单查询构建界面</title>
<%@include file="/commons/include/getById.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/util/json2.js"></script>
<script type="text/javascript">
	var condition = ${bpmFormQuery.conditionfield};
	var alias = "${bpmFormQuery.alias}";
	
	$(function(){
		initUI();
		//查询
		$("#btnSearch").click(search);
		//帮助
		$("a.detail").click(help);
	});
	
	function initUI(){
		var table = $("#queryCondition"),
		columnNum = 2,
		html = [];
		for(var i=0,c;c=condition[i++];){
			if(c.defaultType == 5 && !c.field) continue;
			var columnRes = i % columnNum;
			if(columnRes == 1)
				html = ['<tr>'];
			html.push('<th>');
			html.push(c.comment);
			html.push('</th><td>');
			html.push('<input type="input" class="inputText" name="');
			html.push(c.field);
			html.push('"');
			if(c.defaultType == 2 || c.defaultType == 3){
				html.push(' disabled="disabled"');
				html.push(' value="');
				html.push(c.defaultValue);
				html.push('"');
			}
			html.push(' /></td>');
			if(columnRes == 0){
				html.push('</tr>');
				table.append(html.join(''));
				html = [];
			}
		}
		html.push('</tr>');
		table.append(html.join(''));
	}
	
	
	//查询
	function search (){	
		var div = $("#resultDiv");			
		div.html('<span class="brown">正在进行查询...</span>');
		var data = getQueryData();
		var condition = {alias:alias,page:1,pagesize:10,querydata:data};
		DoQuery(condition,function(data){
			div.html('');
			if(data.errors)
				div.html('<span class="red">'+data.errors+'</span>');
			else{
				var html = JSON2.stringify(data.list);
				div.html(html);
			}					
		});
	}

	function getQueryData(){
		var data = [];
		$("#queryCondition tr").each(function(){
			var tr = $(this);				
			$("input",tr).each(function(){
				var input = $(this);
				if(!input.attr("disabled")){
					var item ='"' + input.attr("name") + '":"' + input.val() + '"';
					data.push(item);
				}
			});
		});
		data = '{' + data.join(',') + '}';		
		return data;
	};
	
	//帮助
	function help(){
			var html = ['<br /><table class="table-detail" cellpadding="0" cellspacing="0" border="0">'];			
			html.push('<tr><th width="90">POST参数:</th><td>');
			html.push('var <span class="brown">querydata</span> = \'');
			html.push(getQueryData());			
			html.push('\',<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="brown">condition</span> = {alias:"');
			html.push(alias);
			html.push('",page:1,pagesize:10,querydata:<span class="brown">querydata</span>};</td></tr>');			
			html.push('<tr><th>调用的方法:</th><td>');
			html.push('DoQuery(<span class="brown">condition</span>');
			html.push(',function(<span class="brown">data</span>){});<br />该方法定义在<span class="green">${ctx}/js/hotent/platform/form/CommonDialog.js</span>中');
			html.push('</td></tr>');
			html.push('<tr><th>回调方法:</th><td>');
			html.push('查询的返回值<span class="brown">data</span>是<span class="green">QueryResult</span>类的一个实例');
			html.push('</td></tr>');
			html.push('<tr><th>其他说明:</th><td>1、POST的参数中<span class="brown">page</span>是页码、<span class="brown">pagesize</span>是每页条数，分页查询时需要传递这2个参数，不传表示不进行分页查询；<br />');
			html.push('2、<span class="brown">alias</span>是通用表单查询别名，<span class="brown">querydata</span>是用作查询的字段名、字段值；<br />');
			html.push('3、返回值data中，<span class="brown">data.errors</span>存放错误信息，没有出错则为空白，<span class="brown">data.list</span>存放了查询的结果，<span class="brown">data.isPage</span>表示是否分页(0:不分页,1:分页),<span class="brown">data.totalCount</span>和<span class="brown">data.totalPage</span>分别记录了查询结果的总数和分页的总页数。<br />');
			html.push('</td></tr>');
			$("#resultDiv").html(html.join(''));
	}
	
	
</script>
</head>
<body>
	<div class="panel">	
		<div class="panel-toolbar">
			<div class="toolBar">
				<div class="group">
					<a class="link search" id="btnSearch"><span></span>查询</a>
					<div class="l-bar-separator"></div>
					<a class="link detail"><span></span>使用帮助</a>
				</div>
			</div>	
		</div>	
		<div class="panel-body">
			<table class="table-detail" id="queryCondition" cellpadding="0" cellspacing="0" border="0"></table>
		</div>
		<div id="resultDiv">			
		</div>
	</div>
</body>
</html>

