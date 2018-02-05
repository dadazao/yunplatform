<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<div>
  	 	<table width="100%"  cellspacing="0" cellpadding="2" border="0" class="Input_Table">
					<tr>
				<td align="left" class="Input_Table_Label" width="10%">
					规格
				</td>
				<td align="left" width="40%">
					${productDetail.guige}
				</td>
				<td align="left" class="Input_Table_Label" width="10%">
					说明
				</td>
				<td align="left" width="40%">
					${productDetail.resume}
				</td>
			</tr>
		</table>
	</div>
