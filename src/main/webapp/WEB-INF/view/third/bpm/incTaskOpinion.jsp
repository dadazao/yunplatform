<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<table class="table-detail">
			<tr>
				<th>意见</th>
				<td>
					<c:if test="${!empty taskAppItems}">
						常用语选择:
						<select  id="selTaskAppItem" onchange="addComment()" >
							<option value="" style="text-align:center;">-- 请选择 --</option>
							<c:forEach var="item" items="${taskAppItems}">
								<option value="${item}">${item}</option>
							</c:forEach>
						</select>
						<br>
					</c:if>
					<textarea rows="2" cols="78" id="voteContent" name="voteContent" maxlength="512">${taskOpinion.opinion}</textarea>
				</td>
			</tr>
</table>




