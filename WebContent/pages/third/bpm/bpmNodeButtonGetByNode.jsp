<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作按钮设置</title>
<%@include file="/commons/include/get.jsp" %>
<base target="_self"> 
<script type="text/javascript">

$(function(){
	$("a.add").click(function(){
		var url=__ctx + "/platform/bpm/bpmNodeButton/edit.ht?defId=${defId}&nodeId=${nodeId}&buttonFlag=${buttonFlag}";	
		$.gotoDialogPage(url);
	});
	$("a.save").unbind("click").click(save);
	$("a.link.init").unbind("click");
	init();
});


function save(){
	var aryId=[];
	$("tr.trNodeBtn").each(function(){
		aryId.push($(this).attr("id"));
	});
	if(aryId.length==0){
		$.ligerDialog.warn("没有定义按钮!",'提示信息');
		return;
	}
	var ids=aryId.join(",");
	var url="sort.ht";
	$.post(url,{ids:ids},function(responseText){
		var obj=new com.hotent.form.ResultMessage(responseText);
		if(obj.isSuccess()){//成功
			$.ligerDialog.success("修改排序成功!",'提示信息',function(){
				var nurl =__ctx + "/platform/bpm/bpmNodeButton/getByNode.ht?defId=${defId}&nodeId=${nodeId}&buttonFlag=${buttonFlag}";
				$.gotoDialogPage(nurl);
			});
			
		}
		else{
			$.ligerDialog.err("出错信息","修改排序失败",obj.getMessage());
		}
	})
}


function sortTr(obj,isUp) {
	var thisTr = $(obj).parents("tr");
	if(isUp){
		var prevTr = $(thisTr).prev();
		if(prevTr){
			thisTr.insertBefore(prevTr);
		}
	}
	else{
		var nextTr = $(thisTr).next();
		if(nextTr){
			thisTr.insertAfter(nextTr);
		}
	}
};


function init(){
	$("a.link.init").click(function(){
		var ele=this;
		$.ligerDialog.confirm('确认初始化按钮吗？','提示信息',function(rtn) {
			if(rtn){
				var url =__ctx + "/platform/bpm/bpmNodeButton/init.ht?defId=${defId}&nodeId=${nodeId}&buttonFlag=${buttonFlag}";
				$.gotoDialogPage(url);
			}
		});
		return false;
	});
}
</script>

</head>
<body>
  <c:if test="${buttonFlag}">  
	    <jsp:include page="incDefinitionHead.jsp">
	   		<jsp:param value="节点操作按钮" name="title"/>
		</jsp:include>
	    <f:tab curTab="button" tabName="flow"/>
   </c:if>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">操作按钮列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link add" href="#"><span></span>添加</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link save" id="btnUpd" action="sort.ht"><span></span>保存排序</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a  class="link init" id="bntInit" href="#"><span></span>初始化按钮</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a  class="link del"  href="deByDefNodeId.ht?defId=${defId}&nodeId=${nodeId}&buttonFlag=${buttonFlag}"><span></span>删除所有</a></div>
					<c:if test="${buttonFlag}"> 
						<div class="l-bar-separator"></div>
						<div class="group"><a  class="link back"  href="list.ht?defId=${defId}"><span></span>返回</a></div>
					</c:if>
				</div>	
			</div>
		</div>
		</div>
		<div class="panel-body">
			<table cellpadding="1" cellspacing="1" class="table-grid table-list">
				<thead>
				<tr>
					<th>序号</th>
					<th>按钮名</th>
					<th>操作类型</th>
					<th>排序</th>
					<th>
						管理
					</th>
				</tr>
				</thead>
			
				
				<c:forEach items="${btnList}" var="item" varStatus="status">
				<tr class="trNodeBtn" id="${item.id}">
					<td>
						${status.index +1}
					</td>
					<td>
						${item.btnname }
					</td>
					<td nowrap="nowrap">
						<c:choose>
							<c:when test="${item.isstartform==1 }">
								<c:choose>
									<c:when test="${item.operatortype==1 }">启动流程</c:when>
									<c:when test="${item.operatortype==2 }">流程示意图</c:when>
									<c:when test="${item.operatortype==3 }">打印</c:when>
									<c:when test="${item.operatortype==4 }">短信</c:when>
									<c:when test="${item.operatortype==5 }">邮件</c:when>
									<c:when test="${item.operatortype==6 }">保存草稿</c:when>
								    <c:when test="${item.operatortype==14 }">Web签章</c:when>
								    <c:when test="${item.operatortype==15 }">手写签章</c:when>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${item.operatortype==1 }">同意</c:when>
									<c:when test="${item.operatortype==2 }">反对</c:when>
									<c:when test="${item.operatortype==3 }">弃权</c:when>
									<c:when test="${item.operatortype==4 }">驳回</c:when>
									<c:when test="${item.operatortype==5 }">驳回到发起人</c:when>
									<c:when test="${item.operatortype==6 }">交办</c:when>
									<c:when test="${item.operatortype==7 }">补签</c:when>
									<c:when test="${item.operatortype==8 }">保存表单</c:when>
									<c:when test="${item.operatortype==9 }">流程示意图</c:when>
									<c:when test="${item.operatortype==10 }">打印</c:when>
									<c:when test="${item.operatortype==11}">审批历史</c:when>
									<c:when test="${item.operatortype==14 }">Web签章</c:when>
									<c:when test="${item.operatortype==15 }">手写签章</c:when>
									<c:when test="${item.operatortype==16 }">沟通</c:when>
									<c:when test="${item.operatortype==17 }">加签</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
						
					</td>
					<td>
						<a alt='上移' href='#' class='link moveup' onclick='sortTr(this,true)'>&nbsp;</a>
				        <a alt='下移' href='#' class='link movedown' onclick='sortTr(this,false)'>&nbsp;</a>
					</td>
					<td>
						<a class="link edit" href="edit.ht?id=${item.id}&defId=${defId}&nodeId=${nodeId}&buttonFlag=${buttonFlag}">编辑</a>
						<a class="link del" href="del.ht?id=${item.id}&buttonFlag=${buttonFlag}">删除</a>
					</td>
					
				</tr>
				</c:forEach>
			</table>
		</div>
	</div><!-- end of panel-body -->				
</div> <!-- end of panel -->
</body>
</html>





