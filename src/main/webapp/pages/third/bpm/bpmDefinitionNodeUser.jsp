<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div style="padding:4px;margin: 4px;font-size:12px;line-height: 20px;">
<c:forEach items="${candidateUsers}" var="userId">
	<div><f:userName userId="${userId}"/></div>
</c:forEach>
</div>