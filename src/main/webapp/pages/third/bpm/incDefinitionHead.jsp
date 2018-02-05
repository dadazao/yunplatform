<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	function showXmlWindow(obj){
		var url="";
		if($(obj).text().trim()=='BPMN-XML'){
			url="${ctx}/platform/bpm/bpmDefinition/bpmnXml.ht?defId=${bpmDefinition.defId}";
		}else{
			url="${ctx}/platform/bpm/bpmDefinition/designXml.ht?defId=${bpmDefinition.defId}";
		}
		url=url.getNewUrl();
		window.open(url);
	}
</script>
<style>
body{ padding:0px; margin:0;overflow-x:hidden;} 
</style>

<div  useSclFlw="false">
<div class="panel-toolbar" style="float:left;width:100%">
		<div class="panel-top-left">流程定义管理->${bpmDefinition.subject }</div>
		<div class="panel-top-right" >
					
							<div class="toolBar" style="margin:0;">
								<div class="l-bar-separator"></div>
									<div class="group"><a class="link xml-bpm"  onclick="showXmlWindow(this);"><span></span>BPMN-XML</a></div>
								<div class="l-bar-separator"></div>
									<div class="group"><a class="link xml-design" onclick="showXmlWindow(this);"><span></span>DESIGN-XML</a></div>
								<div class="l-bar-separator"></div>
									<div class="group">
									<c:choose>
										<c:when test="${not empty param.defIdForReturn}">
											<a class="link back" href="${ctx}/platform/bpm/bpmDefinition/versions.ht?defId=${param.defIdForReturn}"><span></span>返回</a>
										</c:when>
										<c:otherwise>
											<a class="link back" href="${ctx}/platform/bpm/bpmDefinition/list.ht"><span></span>返回</a>
										</c:otherwise>
									</c:choose>
									</div>
							</div>
					</div>
										
	    </div>
<!-- 	<div class="tbar-title"> -->
<%-- 		<span class="tbar-label">流程定义管理->${bpmDefinition.subject }</span> --%>
<!-- 	</div> -->
<!-- 	<div class="panel-toolbar"> -->
		
<!-- 		<div class="toolBar"> -->
<!-- 			<div class="group"><a class="link xml-bpm"  onclick="showXmlWindow(this);">BPMN-XML</a></div> -->
<!-- 			<div class="l-bar-separator"></div> -->
<!-- 			<div class="group"><a class="link xml-design" onclick="showXmlWindow(this);">DESIGN-XML</a></div> -->
<!-- 			<div class="l-bar-separator"></div> -->
<%-- 			<div class="group"><a class="link back" href="${ctx}/platform/bpm/bpmDefinition/list.ht">返回</a></div> --%>
<!-- 		</div>	 -->
<!-- 	</div> -->
</div>