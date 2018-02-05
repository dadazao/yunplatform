<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
	function beforeClick(operatorType){<c:if test="${not empty mapButton.button}">
		switch(operatorType){<c:forEach items="${mapButton.button }" var="btn"  ><c:if test="${not empty btn.prevscript}">
				case ${btn.operatortype}:
				${btn.prevscript}
				break;</c:if></c:forEach>
			}</c:if>
	}
	
	function afterClick(operatorType){<c:if test="${not empty mapButton.button}">
		switch(operatorType){<c:forEach items="${mapButton.button }" var="btn" ><c:if test="${not empty btn.afterscript}">
			case ${btn.operatortype}:
				${btn.afterscript}
				break;</c:if></c:forEach>
			}</c:if>
	}
</script>
<div class="panelBar">
	
	<c:choose>
		<c:when test="${empty mapButton }">
			<button type="button" class="listbutton run" style="width:70px;">提交</button>
			<button type="button" class="listbutton save" style="width:70px;">保存表单</button>
			<button type="button" class="listbutton flowDesign" onclick="showBpmImageDlg()" style="width:70px;">流程图</button>
			<button type="button" class="listbutton print" onclick="window.print();"  style="width:70px;">打印</button>
			&nbsp;&nbsp;通知方式：
			<input type="checkbox" value="1" name="informType">手机短信 &nbsp;
			<input type="checkbox" value="2" name="informType">邮件&nbsp;
			<input type="checkbox" value="3" name="informType">内部消息&nbsp;
		</c:when>
		<c:otherwise>
			<c:if test="${not empty mapButton.button}">
				<c:forEach items="${mapButton.button }" var="btn"  varStatus="status">
					<c:choose>
						
						<c:when test="${btn.operatortype==1 }">
							<!-- 启动流程 -->
							<button type="button" class="listbutton run">${btn.btnname }</button>
						</c:when>
						
						<c:when test="${btn.operatortype==2 }">
							<!--流程示意图 -->
							<button type="button" class="listbutton flowDesign" onclick="showBpmImageDlg()">${btn.btnname }</button>
						</c:when>
						
						<c:when test="${btn.operatortype==3 }">
							<!--打印 -->
							<button type="button" class="listbutton print" onclick="window.print();">${btn.btnname }</button>
						</c:when>
						
						<c:when test="${btn.operatortype==6 }">
							<!--保存表单 -->
							<button type="button" class="listbutton save">${btn.btnname }</button>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
			<c:if test="${not empty mapButton.inform}">
				通知方式：
				<c:forEach items="${mapButton.inform }" var="btn"  varStatus="status">
					<c:choose>
						<c:when test="${btn.operatortype==4 }">
							<input type="checkbox" value="1" name="informType">${btn.btnname } &nbsp;
						</c:when>
						<c:when test="${btn.operatortype==5 }">
							<input type="checkbox" value="2" name="informType">${btn.btnname } &nbsp;
						</c:when>
						
					</c:choose>
				</c:forEach>
			</c:if>
		</c:otherwise>
	</c:choose>	
	
</div>
							
								
						