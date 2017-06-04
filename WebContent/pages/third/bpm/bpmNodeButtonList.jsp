<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点按钮管理</title>
<script type="text/javascript">

$(function(){
	$(".table-detail").width($(".table-grid").width());
	$(".tbar-title").width($(".table-grid").width());
	$("a.del").unbind("click");
});
function initAll(defId){
	var url =  __ctx+'/platform/bpm/bpmNodeButton/initAll.ht?defId='+defId;
	$.ligerDialog.confirm($lang_bpm.nodeButton.initAllTip,$lang.tip.msg,function(rtn) {
		if(rtn){
			gotoUrl(url);
		}
	});	
}
function init(defId,nodeId){
	if($.isEmpty(nodeId)) nodeId ='';
	var url =  __ctx+'/platform/bpm/bpmNodeButton/init.ht?defId='+defId+'&nodeId='+nodeId;
	$.ligerDialog.confirm($lang_bpm.nodeButton.initTip,$lang.tip.msg,function(rtn) {
		if(rtn){
			gotoUrl(url);
		}
	});	
}
function clearAll(defId){
	var url =  __ctx+'/platform/bpm/bpmNodeButton/delByDefId.ht?defId='+defId;
	$.ligerDialog.confirm($lang_bpm.nodeButton.clearAllTip,$lang.tip.msg,function(rtn) {
		if(rtn){
			
			gotoUrl(url);
		}
	});	
}

function del(defId,nodeId){
	if($.isEmpty(nodeId)) nodeId ='';
	var url =  __ctx+'/platform/bpm/bpmNodeButton/deByDefNodeId.ht?defId='+defId+'&nodeId='+nodeId;
	$.ligerDialog.confirm($lang_bpm.nodeButton.delTip,$lang.tip.msg,function(rtn) {
		if(rtn){
			gotoUrl(url);
		}
	});	
}
function edit(defId,nodeId) {
	var dialogWidth=500;
	var dialogHeight=300;
	var options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true});
	var url =__basePath+"/pages/platform/third/bpm/bpmNodeButton/getByNode.action?defId="+defId+"&nodeId="+nodeId;
	$.pdialog.open(url,"selectButtonDialog","编辑",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable});
}
function gotoUrl(url){
	if($.browser.msie){
		$.gotoDialogPage(url);
	}
	else{
		location.href=url;
	}
}
</script>

</head>
<body>
<div class="panelBar">
	<button class="listbutton"  type="button" style="width:110px;" onclick="initAll('${bpmDefinition.defId}');">初始化全部按钮</button>
	<button class="listbutton"  type="button" style="width:110px;" onclick="clearAll('${bpmDefinition.defId}')">清除按钮配置</button>
</div>
<form action="save.action" method="post" id="dataForm">
    <input type="hidden" name="defId" value="${bpmDefinition.defId}"/>
			<!-- 节点对应表 -->
	    <table cellpadding="1" cellspacing="1" class="tableClass" width="100%" style="border-top: 0;">
	    <thead>
			<tr>
				<th class="thClass">序号</th>
				<th class="thClass">节点名</th>
				<th class="thClass">类型</th>
				<th class="thClass">
					操作按钮
				</th>
				<th class="thClass">
					管理
				</th>
			</tr>
			</thead>
			<tr>
					<td class="tdClass">0</td>
					<td class="tdClass">启动流程</td>
					<td class="tdClass" style="color:red">起始节点	</td>
					<td class="tdClass" style="text-align: left;">
						<c:set var="btnList" value="${btnMap.start}"></c:set>
						<c:set var="flag" value="0"></c:set>
						<!-- 启动流程 -->
						<c:choose>
							<c:when test="${empty btnList }">
								<c:forEach items="${startBtnList}" var="btn" varStatus="status" >
									${btn.text}<c:if test="${!status.last}">，</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${btnList}" var="btn" varStatus="status" >
											${btn.btnname}<c:if test="${!status.last}">，</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>
                    <td class="tdClass">
						<a style="color:blue;" href="getByNode.ht?defId=${bpmDefinition.defId}">编辑</a>
						
						<a style="color:blue;" href="init.ht?defId=${bpmDefinition.defId}">初始化</a>
						
						<a style="color:blue;"  href="deByDefNodeId.ht?defId=${bpmDefinition.defId}">删除</a>
					</td>
			</tr>
			
			
			<c:forEach items="${bpmNodeSetList}" var="item" varStatus="status">
			<tr>
				<td class="tdClass">
					${status.index +1}
				</td>
				<td class="tdClass">
					<input type="hidden" name="nodeId" value="${item.nodeId}"/>
					<input type="hidden" name="bpmNodeName" value="${item.nodeName}"/>${item.nodeName}
				</td>
				<td class="tdClass">
					<c:choose>
						<c:when test="${taskMap[item.nodeId].isSignNode }"><span style="color:red">会签节点</span></c:when>
						<c:when test="${taskMap[item.nodeId].isSubProcess or taskMap[item.nodeId].isCallActivity}"><span style="color:red">子流程</span></c:when>
						<c:otherwise><span style="color:green;">普通节点</span></c:otherwise>
					</c:choose>
				</td>
				<td nowrap="nowrap" class="tdClass" style="text-align: left;">
					<c:set var="btnList" value="${btnMap[item.nodeId] }"></c:set>
					<c:choose>
						<c:when test="${empty btnList }">
							<c:choose>
								<c:when test="${taskMap[item.nodeId].isSignNode}"><!-- 表示会签节点 -->
									<c:forEach items="${signBtnList}" var="btn" varStatus="status" >
										${btn.text}<c:if test="${!status.last}">，</c:if>
									</c:forEach>
								</c:when>
								<c:when test="${taskMap[item.nodeId].isFirstNode }"><!-- 表示第一个节点 -->
									<c:forEach items="${firstNodeBtnList}" var="btn" varStatus="status" >
										${btn.text}<c:if test="${!status.last}">，</c:if>
									</c:forEach>
								</c:when>
								<c:when test="${taskMap[item.nodeId].isSubProcess or taskMap[item.nodeId].isCallActivity}" ><!-- 子流程-->
									&nbsp;
								</c:when>
								<c:otherwise>
									<c:forEach items="${commonBtnList}" var="btn" varStatus="status" >
										${btn.text}<c:if test="${!status.last}">，</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:forEach items="${btnList}" var="btn" varStatus="status" >
								${btn.btnname}<c:if test="${!status.last}">，</c:if>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</td>
				<td class="tdClass">
					<c:choose>
						<c:when test="${taskMap[item.nodeId].isSubProcess or taskMap[item.nodeId].isCallActivity}" >
						</c:when>
						<c:otherwise>
							<a style="color:blue;" href="#" onclick="edit('${bpmDefinition.defId}','${item.nodeId}')">编辑</a>
							<a style="color:blue;" href="#" onclick="init('${bpmDefinition.defId}','${item.nodeId}')">初始化</a>
							<a style="color:blue;" href="#" onclick="del('${bpmDefinition.defId}','${item.nodeId}')">删除</a>
						</c:otherwise>
					</c:choose>
				</td>
				
			</tr>
			</c:forEach>
		</table>
</form>
</body>
</html>





