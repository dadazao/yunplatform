<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
<!--
	$(function(){
		//双击关闭
		ns.common.dbclickCloseDialog();
		
		//按钮样式根据鼠标停留而变化
		ns.common.mouseForButton();
			});
		//--> 
</script>
<div class="buttonPanel">
	<button type="button" id="editDocument" name="editDocument" class="listbutton" onclick="ns.document.editDocument('${document.id}');">修改</button>
	<button type="button" id="saveDocument" name="saveDocument" class="listbuttonDisable" disabled="disabled">保存</button>
</div>
<div id="yunDialog">
	<div style="display: none">
		<input type="hidden" id="domainId" value="${document.id}"/>
	</div>
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="tabLiId">
					<li class="selected"><a><span>基本信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" id="tabDivId">
			<div>
		  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							发文类型
						</td>
						<td align="left" width="40%">
							${document.type}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							日期
						</td>
						<td align="left" width="40%">
							<fmt:formatDate value="${document.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							文号
						</td>
						<td align="left" width="40%">
							${document.wenhao}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							密级
						</td>
						<td align="left" width="40%">
							${document.miji}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							标题
						</td>
						<td align="left" width="40%">
							${document.title}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							主题词
						</td>
						<td align="left" width="40%">
							${document.keyword}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							主送
						</td>
						<td align="left" width="40%">
							${document.zhusong}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							抄送
						</td>
						<td align="left" width="40%">
							${document.chaosong}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							拟稿部门
						</td>
						<td align="left" width="40%">
							${document.department}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							拟稿人
						</td>
						<td align="left" width="40%">
							${document.drafter}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							办理流程
						</td>
						<td align="left" width="40%">
							${document.flow}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							正文
						</td>
						<td align="left" width="40%">
							${document.content}
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							总状态
						</td>
						<td align="left" width="40%">
							${document.flowstate}
						</td>
						<td align="left" class="Input_Table_Label" width="10%">
							归档时间
						</td>
						<td align="left" width="40%">
							<fmt:formatDate value="${document.filetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					<tr>
						<td align="left" class="Input_Table_Label" width="10%">
							当前审批人
						</td>
						<td align="left" width="40%">
							${document.curapprover}
						</td>
				</table>
			</div>	      	
		</div>
		<!-- Tab结束层 -->
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>