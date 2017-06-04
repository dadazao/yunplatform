<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form method="post" action="<c:url value='/pages/system/catalog/list.action'/>" onsubmit="return navTabSearch(this);">
	<div align="center">
		<input type="hidden" name="domainTable" value="catalog"/>		
  	 	<table width="98%" cellspacing="0" cellpadding="2" border="0" class="Input_Table">
			<tr>
				<td width="15%" height="30" align="left" class="Input_Table_Label">
					<label>目录：</label>
				</td>
				<td width="35%" height="30" align="left">
					<input type="text" name="dyncMapString.name" value="${catalog.name}" />
				</td>
				<td width="15%" height="30" align="left" class="Input_Table_Label">
					编码：
				</td>
				<td width="35%" height="30" align="left">
					<input type="text" name="dyncMapString.code" value="${catalog.code}" />
				</td>
			</tr>
			
			<tr>
				<td height="30" align="left" class="Input_Table_Label">
					父目录：
				</td>
				<td height="30" align="left">
					<table cellspacing="0" cellpadding="0" border="0" >
						<tr>
							<td align="left" style="background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;">
								<input id="parentName" type="text" value="" readonly="true" class="required"/>
								<input id="parentId" type="hidden" name="dyncMapLong.parentId" value="${catalog.parentId}" />
							</td>
							<td style="background-color: #FFFFFF;border-bottom: #CECCCD 0px solid;border-right: #CECCCD 0px solid;">
								<a id="selectDialog" rel="selectDialog" class="button" target="dialog" mask="true" resizable="true" width="200" height="600" href="<%=basePath %>/pages/system/tree/catalogTree.jsp" ><span>选择</</span></a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" height="30" class="Input_Table_Label">
					顺序：
				</td>
				<td align="left" height="30">
					<input type="text" name="dyncMapString.orderNum" value="${catalog.orderNum}" />
				</td>
			</tr>
			<tr>
				
				<td height="30" align="left" class="Input_Table_Label">
					访问路径：
				</td>
				<td colspan="3" align="left">
					<input type="text" name="dyncMapString.path" value="${catalog.path}" size="70"/>
				</td>
		</table>
		<br><br>
		<div>
			<button type="submit" class="listbutton">查询</button>
			<button type="button" class="listbutton close">取消</button>
		</div>
	</div>
</form>