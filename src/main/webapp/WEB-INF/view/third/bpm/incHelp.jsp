<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div style="float:right;margin-right:30px;">
	<c:if test="${sysFile !=null }">
		<c:choose>
			<c:when test="0">
				<a href="#" onclick="openHelpDoc(${sysFile.fileId})"  class="link help" title="${sysFile.fileName}">帮助</a>
			</c:when>
			<c:otherwise>
				<div class="group"><a id="file" class="link help"  href="${ctx}/platform/system/sysFile/file_${sysFile.fileId}.ht" target="_blank" ><span></span>帮助</a></div>
			</c:otherwise>
		</c:choose>
		
		
	</c:if>
</div>