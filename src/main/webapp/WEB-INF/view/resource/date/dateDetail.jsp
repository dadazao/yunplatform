<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/tld/buttontag" prefix="customButtonTag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<html>
<head>
<script type="text/javascript">
$(function(){
	initPageDiv("${date.id}");
});

function initPageDiv(dateId){
	if(dateId != ""){
	   $('#Tab1').loadUrl("<%=basePath %>/pages/resource/date/findById?dateId="+dateId+"&result=view","",function(){
          $("#buttonBC").attr("disabled","disabled");
          $("#buttonSC").attr("disabled","disabled");
          $("#buttonBC").attr("class","listbuttonDisable");
          $("#buttonSC").attr("class","listbuttonDisable");
          
	   });
	   $('#Tab2').loadUrl("<%=basePath %>/pages/resource/date/formList.action?dateId="+dateId);
	}else{
		$('#Tab1').loadUrl("<%=basePath %>/pages/resource/date/findById?result=update","",function(){
          $("#buttonBC").removeAttr("disabled");
          $("#buttonSC").removeAttr("disabled");
          $("#buttonXG").attr("disabled","disabled");
          .attr("class","listbuttonDisable");
	   });
		$('#Tab2').loadUrl("<%=basePath %>/pages/resource/date/formList.action");
	}
}

//修改
function eventXG(){
	$('#Tab1').loadUrl("<%=basePath %>/pages/resource/date/findById?dateId=${date.id}&result=update","",function(){
          $("#buttonBC").removeAttr("disabled");
          $("#buttonSC").removeAttr("disabled");
          $("#buttonXG").attr("disabled","disabled");
	 });
}

//保存
function eventBC(){
   $("#dateForm").submit();
}

//删除
function eventSC(){
  alertMsg.confirm("是否删除所选内容?", {
		okCall: function(){
			$("#dateForm").attr("action","<%=basePath %>/pages/resource/date/doDel?dateId=${date.id}");
            validateCallback($("#dateForm"),dialogAjaxDone);
		}
  });
}

</script>
</head>
<body>  
<!-- 操作按钮区 -->
<div class="pageContent">
	<div class="panelBar" style="width:1010px;OVERFLOW-Y:hidden;OVERFLOW-X:auto;">
		<table>
			<tr><td>
				<c:forEach items="${formButtons}" var="formButton" varStatus="status">
					<c:if test="${formButton.buttonType == '1'}">
						<c:forEach items="${formButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
							<button type="button" id="${buttonAndGroup.button.buttonBM}" name="b${status.count*stat.count}" class="listbutton" onclick="event${buttonAndGroup.button.buttonBM}();">${buttonAndGroup.button.buttonName}</button>
						</c:forEach>
					</c:if>
					<c:if test="${formButton.buttonType == '0'}">
						<button type="button" name="b${status.count}" class="listbutton" onclick="event${formButton.button.buttonBM}();">${formButton.button.buttonName}</button>	
					</c:if>
				</c:forEach>
			</td></tr>
		</table>
	</div>
</div>
<!-- 分割层 -->
<div style="height:2px;"></div>
<!-- Tab信息，默认下标为0的Tab被选中，切换出发click事件类型 -->
<div id="t" class="tabs" >
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;"><span>基本信息</span></a></li>
				<li><a href="javascript:;"><span>使用信息</span></a></li>
			</ul>
		</div>
	</div>

	<div  class="tabsContent">
		<div id="Tab1"></div>
		<div id="Tab2" align="center"></div>
	</div>
    <div class="tabsFooter"><div class="tabsFooterContent"></div>
</div>
</body>
</html>
