<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>上下级选择</title>
<%@include file="/commons/include/form.jsp" %>
<script type="text/javascript"	src="${ctx }/js/hotent/displaytag.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>

<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=bpmNodeUserUplow"></script>
<style type="text/css">
	.error
	{
		border-color: red;
	}
</style>

<script type="text/javascript">
function add(){
	var tr='';
	tr+='<tr>';
	tr+='<td>';
	tr+='<input  type="checkbox" class="pk" name="valueId" value="${demensionItem.valueId}">';
	tr+='</td>';
	tr+='<td>';
	tr+='<select name="demensionId" style="width: 70%;">';
	tr+='<c:forEach items="${demensionList}" var="d" >';
	tr+='<option value="${d.demId}" >${d.demName}</option>';
	tr+='</c:forEach>';
	tr+='</select>';
	tr+='</td>';
	tr+='<td>';
	tr+='<select name="upLowType" style="width: 70%;" onchange="validUpLowType(this);">';
	tr+='<c:forEach items="${uplowtypeList}" var="t" >';
	tr+='<option value="${t.value }">${t.key }</option>';
	tr+='</c:forEach>';
	tr+='</select>	';
	tr+='</td>';
	tr+='<td>';

	tr+='<select  name="upLowLevel" style="width: 70%;display: none;" onchange="validUpLowLevel(this);">';
	tr+='<option value="0" selected="selected">0</option>';
	tr+='<option value="1">1</option>';
	tr+='<option value="2">2</option>';
	tr+='<option value="3">3</option>';
	tr+='<option value="4">4</option>';
	tr+='<option value="5">5</option>';
	tr+='</select>';

	
	tr+='</td>';
	tr+='<td style="text-align: center;">';
	tr+='<input  type="checkbox" class="pk" name="isCharge">';
	tr+='</td>';
	tr+='<td>';
	tr+='<a href="#" class="link del" onclick="handlerDelOne(this);">删除</a>';
	tr+='</td>';
	tr+='</tr>';
	$("#demensionItem").append(tr);

};




function validDuplicate(){
	var yes=true;
	var $aryId = $("select[name='demId'] option:selected");
	if($aryId.length > 0)
	{
		$aryId.each(function(i,t){
			var tp=$(t).val();
			

			$aryId.each(function(j,o){
				var op=$(o).val();
				if(i!=j&&tp==op){
					yes=false;
					
					$(t).parent().addClass("error");
					if($(t).parent().next().html()==null||$(t).parent().next().html()=='')
					$(t).parent().after('<font color="red">维度重复。</font>');
				}
			});
			
		});
	}
	/*if(yes){
		$aryId.parent().removeClass("error");
		if($aryId.parent().next().html()!=null)
			$aryId.parent().next().empty();
	}*/
	return yes;
};


function validateVal(){
	var yes=true;
	var $upLowLevels=$("input[name='upLowLevel']");
	if($upLowLevels.length>0){
		for(i=0;i<$upLowLevels.length;i++){
			
			var upLowLevel=$($upLowLevels[i]).val();
			if(isNaN(upLowLevel)||upLowLevel=="")
	        {
				$($upLowLevels[i]).addClass("error");
			 	if($($upLowLevels[i]).next().html()==null||$($upLowLevels[i]).next().html()=='')
				 	$($upLowLevels[i]).after('<font color="red">请输入数字。</font>');
			 	yes=false;
	    	 }
		}
	}
	
	/*if(yes){
		$upLowLevels.removeClass("error");
		if($upLowLevels.next().html()!=null)
			$upLowLevels.next().empty();
	}*/
	     
	return yes;
};





function handlerDelSelect(){
	//单击删除超链接的事件处理
	$("div.toolBar a.del").click(function()
	{	
		if(!$(this).hasClass('disabled')) {
			var $aryId = $("input[type='checkbox'][class='pk']:checked");
			//提交到后台服务器进行日志删除批处理的日志编号字符串
			if($aryId.length > 0)
			{
				$aryId.each(function(i){
					handlerDelOne(this);
				});
			}
		}
	});
};

function handlerDelOne(obj){
	var tr=$(obj).parents('tr');
	$(tr).remove();
};

$(function(){
	$("#defLayout").ligerLayout({
		height : '90%'
	});
});


function selectUplow(){
	if(!validDuplicate())return;
	if(!validateVal())return;
	var json=getUplowJson();
	var show=getUplowShow();
	window.returnValue={json:json,show:show};
	window.close();	
};

function getUplowShow(){
	var $demensionIds=$("select[name='demensionId'] option:selected");
	var $upLowTypes=$("select[name='upLowType'] option:selected");
	var $upLowLevels=$("select[name='upLowLevel'] option:selected");
	var $isCharge=$("input[name='isCharge']:checkbox");
	var sb=new StringBuffer("");
	if($demensionIds.length>0){
		for(i=0;i<$demensionIds.length;i++){
			var demensionName=$($demensionIds[i]).text();
			var upLowType=$($upLowTypes[i]).val();
			var upLowLevel=$($upLowLevels[i]).val();
			var isCharge=$($isCharge[i]).attr("checked")?1:0;
			var type="同";
			if(upLowType==1) type="上";
			else if(upLowType==-1)type="下";
			else  type="同";	
			var charge = "";
			if(isCharge==1) charge="负责人";
			
			sb.append(demensionName);
			sb.append(type);
			sb.append(upLowLevel);
			sb.append("级");
			sb.append(charge);
			sb.append(",");
		}
		sb=sb.toString();
		if(sb.length>0)sb=sb.substring(0,(sb.length-1));
	}
	return sb;
};

function getUplowJson(){
	var $demensionIds=$("select[name='demensionId'] option:selected");
	var $upLowTypes=$("select[name='upLowType'] option:selected");
	var $upLowLevels=$("select[name='upLowLevel'] option:selected");
	var $isCharge=$("input[name='isCharge']:checkbox");
	var sb=new StringBuffer();
	sb.append("[");
	
	if($demensionIds.length>0){
		
		for(i=0;i<$demensionIds.length;i++){
			var demensionId=$($demensionIds[i]).val();
			var demensionName=$($demensionIds[i]).text();
			var upLowType=$($upLowTypes[i]).val();
			var upLowLevel=$($upLowLevels[i]).val();
			var isCharge=$($isCharge[i]).attr("checked")?1:0;

			sb.append("{");
			
			sb.append("demensionId");
			sb.append(":");
			sb.append(demensionId);
			sb.append(",");

			sb.append("demensionName");
			sb.append(":");
			sb.append("\"");
			sb.append(demensionName);
			sb.append("\"");
			
			sb.append(",");

			sb.append("upLowType");
			sb.append(":");
			sb.append(upLowType);
			sb.append(",");

			sb.append("upLowLevel");
			sb.append(":");
			sb.append(upLowLevel);
			sb.append(",")
			
			sb.append("isCharge");
			sb.append(":");
			sb.append(isCharge);
			
			sb.append("}");

			sb.append(",");
			
		}
		sb=sb.toString();
		if(sb.length>0)sb=sb.substring(0,(sb.length-1));
		
		
	}
	sb+="]";
	return sb;
};


function validUpLowType(obj){
	var td1=$(obj).parents("td");
	
	
	var upLowType=$(obj).val();
	var td2=$(td1).next("td");
	if(upLowType==0){
		$(td2).find("select[name='upLowLevel']").val(0);
		$(td2).find("select[name='upLowLevel']").hide();
	}else{
		$(td2).find("select[name='upLowLevel']").show();
		var upLowLevel=$(td2).find("select[name='upLowLevel']").val();
		if(upLowLevel==0)
			$(td2).find("select[name='upLowLevel']").val(1);
	}
};
function validUpLowLevel(obj){
	var upLowLevel=$(obj).val();
	if(upLowLevel==0){
		var td2=$(obj).parents("td");
		var td1=$(td2).prev("td");
		$(td1).find("select[name='upLowType']").val(0);
		$(obj).hide();
	}else{
		var td2=$(obj).parents("td");
		var td1=$(td2).prev("td");
		var upLowType=$(td1).find("select[name='upLowType']").val();
		if(upLowType==0)$(obj).val(0);
		
	}
};

function preview(){
	var json=getUplowJson();
	var dialogWidth=650;
	var dialogHeight=500;
	var conf={dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1};
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/bpm/bpmNodeUserUplow/getByUserId.ht?json='+json;
	url=url.getNewUrl();
	window.showModalDialog(url,"",winArgs);
};
</script>
</head>
<body>
<div id="defLayout">
<div position="center">


			<div class="panel">
				<div class="panel-top">
					<div class="tbar-title">
						<span class="tbar-label">上下级选择</span>
					</div>
					<div class="panel-toolbar">
						<div class="toolBar">
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link add " href="javascript:add();"><span></span>添加</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a onclick="preview();" class="link preview"><span></span>预览</a></div>
						</div>	
					</div>
				</div>
				<div class="panel-body">
				    	<div class="panel-data" style="overflow: auto;">
						<form id="sysUserParamForm" method="post" action="${ctx }/platform/system/sysUserParam/saveByUserId.ht">
							<input type="hidden" name="userId" value="${userId }">
							
							<table id="demensionItem" cellpadding="1" cellspacing="1"  class="table-grid">
								<head>
									<th style="text-align: center;">
										<input type="checkbox" id="chkall"/>
									</th>
									<th style="text-align: center;">维度</th>
									<th style="text-align: center;">上下级关系</th>
									<th style="text-align: center;">级数</th>
									<th style="text-align: center;">是否负责人</th>
									<th style="text-align: center;">管理</th>
								</head>
								<tbody>
										
								</tbody>
							</table>
							
						</form>
						</div>
				</div>	
			</div> 
</div>
</div>


<div position="bottom"  class="bottom" style='margin-top:10px'>
		<a href='#' class='button'  onclick="selectUplow()" ><span class="icon ok"></span><span >选择</span></a>
		<a href='#' class='button'  style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
</div>
</body>
</html>


