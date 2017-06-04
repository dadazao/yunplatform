<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>上级类型部门负责人选择</title>
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
var    params;
var sysOrgTypeList=[
 <c:forEach items="${sysOrgTypelist}"  var="t" >
			['${t.demId}' , '${t.id }' , '${t.name }'] ,
</c:forEach>
];


function changeDemId(obj){
	var currentDem=$(obj).val();
	var childrens=$(obj).children();
	var levlSelect=	$('select:[name=level]');
	
	levlSelect.children().remove();
	for(var i=0;i<(sysOrgTypeList.length-1);i++){
			if (sysOrgTypeList[i][0]==currentDem){
				levlSelect.html(levlSelect.html()+'<option value="'+sysOrgTypeList[i][1]+'">'+sysOrgTypeList[i][2]+'</option>');	
			}	
	}	
}


$(function(){
	changeDemId($('select:[name=demensionId]'));	
	 params=    window.dialogArguments ;
	var jsonStr=params.cmpIds;	
	var json;
	if(jsonStr && jsonStr.length>0)
		json=eval('('+jsonStr+')');
	if(json)
		eidtInit(json);
});

function eidtInit(json){
		$('select:[name=demensionId]').children().each(function(){
				if($(this).val()==json.demId){
					$(this).attr('selected','true');
				}
		});
		changeDemId($('select:[name=demensionId]'));
		$('select:[name=level]').children().each(function(){
			if($(this).val()==json.level){
				 $(this).attr('selected','true');
			}
		});
		$('input:radio[name=OrgSource]:[value='+json.orgSource+']').attr('checked','true');
		$('input:radio[name=stategy]:[value='+json.stategy+']').attr('checked','true');
}


function select(){
	var currentLevel=$(' select:[name=level]').val();
	if(!currentLevel || currentLevel.length<1){
		$.ligerDialog.warn("请选择部门类型，如没可选请先通知 管理员添加部门类型!","提示信息");
		return;
	}
	var demId=$('select:[name=demensionId]').val();
	var orgSource=$('input:radio[name=OrgSource]:checked').val();
	var currentLevelTxt=$('select:[name=level]').find("option:selected").text();
	var stategy=$('input:radio[name=stategy]:checked').val();
	var demTxt=$('select:[name=demensionId]').find("option:selected").text();
	var orgSourcetxt=$('input:radio[name=OrgSource]:checked').next().html();
	var stategyTxt=$('input:radio[name=stategy]:checked').next().html();
	
		var hiddenJson ="{\"demId\":\""+demId+"\",\"orgSource\":\""+orgSource+"\",\"level\":\""+currentLevel+"\",\"stategy\":"+stategy+"}";
		var showTxt="维度:"+demTxt+";类型:"+orgSourcetxt+";部门类型:"+currentLevelTxt+";查找策略:"+stategyTxt;
		window.returnValue={json:hiddenJson,show:showTxt};
		window.close();
};
</script>
</head>
<body>
<div  style="height:280px">  				
							<input type="hidden" name="userId" value="${userId }">							
							<table id="demensionItem" cellpadding="1" cellspacing="1"  class="table-grid">
								<head>								
									<th style="text-align: center;">分类</th>
									<th style="text-align: center;">选项</th>
								
								</head>
								<tbody>									
										<tr>
											<td >维度</td>
											<td>
														<select name="demensionId" style="width: 70%;" onchange="changeDemId(this)">
															<c:forEach items="${demensionList}" var="d" >
																	<option value="${d.demId}" >${d.demName}</option>
															</c:forEach>
														</select>
											</td>
										</tr>
										<tr>
											<td>类型</td>
											<td>
														<input type="radio"  name="OrgSource"    value="0"  checked="checked"  /> <span>发起人 </span>
														<input type="radio"  name="OrgSource"'  value="1" /> <span>上一步执行者</span>
											</td>
										</tr>
										<tr>
											<td>部门类型</td>
											<td>
															<select name="level" style="width: 70%;">																	
															</select>																				
											</td>
										</tr>
										<tr>
											<td>查找策略</td>
											<td>
														<input type="radio"  name="stategy"    value="0"  checked="checked"/><span>查找指定级别，为空时继续向上一级查询 </span>
														<br/><br/>
														<input type="radio"  name="stategy"'  value="1" /><span>只查指定级别，为空时不继续往上找</span>
												
											</td>										
										</tr>
								</tbody>
							</table>				
<div position="bottom"  class="bottom" style='margin-top:10px'>
		<a href='#' class='button'  onclick="select()" ><span class="icon ok"></span><span >选择</span></a>
		<a href='#' class='button'  style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
</div>
</div>



</body>
</html>


