<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
<!--		
	
//-->
</script>
<div style="overflow-y: auto; overflow-x: hidden; height: 610px;background-color: white;">
<div style="margin-left: 20px;margin-right: 20px;">
<p align="center" style="font-weight: bold;font-size: 18px;">
<br/>
${catalog.name}表单界面<br/>
</p>
<p style="font-size: 13px;line-height: 30px;">
表单【新建】界面是对【<font color="blue">${catalog.name}</font>】表单单条信息进行录入操作的区域；点击列表界面的【新建】按钮，即可弹出表单【新建】界面；分为按钮区、信息操作区。<br/><br/>

1、按钮区<br/>
<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
	<c:if test="${formButton.buttonType == '1'}">
		<c:forEach items="${formButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
			&bull;【<font color="blue">${buttonAndGroup.button.buttonName}</font>】按钮：【<font color="blue">${buttonAndGroup.button.comment}</font>】；<br/>
		</c:forEach>
	</c:if>
	<c:if test="${formButton.buttonType == '0'}">
		&bull;【<font color="blue">${formButton.button.buttonName}</font>】按钮：【<font color="blue">${formButton.button.comment}</font>】；<br/>
	</c:if>
</c:forEach>
<br/>
2、信息操作区<br/>
【<font color="blue">${catalog.name}</font>】表单表单【新建】界面信息操作区共包括【<font color="blue">${fn:length(form.tabs)}</font>】个选项卡<br/>
<c:forEach items="${form.tabs}" var="tab" varStatus="status">
	2.${status.count} 【<font color="blue">${tab.tabName}</font>】选项卡的信息操作说明<br/>
	<c:if test="${tab.template.type==0}">
	<c:forEach items="${tab.formColumnExtends}" var="formColumnExtend" varStatus="cstatus">
		<p>&nbsp;&nbsp;&nbsp;&nbsp;2.${status.count}.${cstatus.count} 【<font color="blue">${formColumnExtend.formColumn.columnZhName}</font>】<br/><br/></p>
		<table border="1" style="border-style: solid;border-color: black;" width="500px">
			<tr>
				<td style="border-style: solid;border-color: black;width: 20%">字段名称</td>
				<td style="border-style: solid;border-color: black;width: 30%"><font color="blue">${formColumnExtend.formColumn.columnZhName}</font></td>
				<td style="border-style: solid;border-color: black;width: 20%">表单显示顺序</td>
				<td style="border-style: solid;border-color: black;width: 30%"><font color="blue">${formColumnExtend.formColumn.formOrder}</font></td>
			</tr>
			<tr>
				<td style="border-style: solid;border-color: black;">功能说明</td>
				<td colspan="3" style="border-style: solid;border-color: black;"><font color="blue">${formColumnExtend.formColumn.colComment}</font></td>
			</tr>
			<tr>
				<td style="border-style: solid;border-color: black;">是否必填</td>
				<td style="border-style: solid;border-color: black;">
					<font color="blue">
					<c:if test="${formColumnExtend.formColumn.required==0}">
						否
					</c:if>
					<c:if test="${formColumnExtend.formColumn.required==1}">
						是
					</c:if>
					</font>
				</td>
				<td style="border-style: solid;border-color: black;">校验方式</td>
				<td style="border-style: solid;border-color: black;"><font color="blue">${formColumnExtend.formColumn.validate}</font></td>
			</tr>
			<tr>
				<td style="border-style: solid;border-color: black;">字符长度限制</td>
				<td style="border-style: solid;border-color: black;"><font color="blue"><fmt:formatNumber value="${formColumnExtend.formColumn.length/2}"></fmt:formatNumber></font></td>
				<td style="border-style: solid;border-color: black;">默认内容</td>
				<td style="border-style: solid;border-color: black;"><font color="blue">${formColumnExtend.formColumn.defaultValue}</font></td>
			</tr>
			<tr>
				<td style="border-style: solid;border-color: black;">录入方式</td>
				<td colspan="3" style="border-style: solid;border-color: black;">
					<font color="blue">
					<c:choose>
						<c:when test="${formColumnExtend.formColumn.inputType==0}">文本框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==1}">下拉框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==2}">文本域</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==3}">单选框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==4}">多选框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==5}">上传文件框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==6}">日期组件</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==7}">树</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==8}">搜索下拉框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==9}">自定义</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==10}">文本编辑器</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==11}">密码框</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==12}">字处理组件</c:when>
						<c:when test="${formColumnExtend.formColumn.inputType==13}">代码级联</c:when>
					</c:choose>
					</font>
				</td>
			</tr>
		</table>
		<br/>
	</c:forEach>
	</c:if>
	<c:if test="${tab.template.type==1}">
		<br/>此选项卡包含【<font color="blue">${fn:length(tab.partitions)}</font>】个分区(分区分操作区和列表区两种,这里仅显示操作区的字段信息,列表区用于显示操作区添加的所有记录.)<br/><br/>
		<c:forEach items="${tab.partitions }" var="partition" varStatus="pstatus">
			2.${status.count}.${pstatus.count} 【<font color="blue">${partition.partitionName}</font>】<br/><br/>
			<c:if test="${partition.partitionType==0}">
			<c:forEach items="${partition.formColumnExtends}" var="formColumnExtend" varStatus="pcstatus">
				<p>&nbsp;&nbsp;&nbsp;&nbsp;2.${status.count}.${pstatus.count}.${pcstatus.count} 【<font color="blue">${formColumnExtend.formColumn.columnZhName}</font>】<br/><br/></p>
				<table border="1" style="border-style: solid;border-color: black;" width="500px">
					<tr>
						<td style="border-style: solid;border-color: black;width: 20%">字段名称</td>
						<td style="border-style: solid;border-color: black;width: 30%"><font color="blue">${formColumnExtend.formColumn.columnZhName}</font></td>
						<td style="border-style: solid;border-color: black;width: 20%">表单显示顺序</td>
						<td style="border-style: solid;border-color: black;width: 30%"><font color="blue">${formColumnExtend.formColumn.formOrder}</font></td>
					</tr>
					<tr>
						<td style="border-style: solid;border-color: black;">功能说明</td>
						<td colspan="3" style="border-style: solid;border-color: black;"><font color="blue">${formColumnExtend.formColumn.colComment}</font></td>
					</tr>
					<tr>
						<td style="border-style: solid;border-color: black;">是否必填</td>
						<td style="border-style: solid;border-color: black;">
							<font color="blue">
							<c:if test="${formColumnExtend.formColumn.required==0}">
								否
							</c:if>
							<c:if test="${formColumnExtend.formColumn.required==1}">
								是
							</c:if>
							</font>
						</td>
						<td style="border-style: solid;border-color: black;">校验方式</td>
						<td style="border-style: solid;border-color: black;"><font color="blue">${formColumnExtend.formColumn.validate}</font></td>
					</tr>
					<tr>
						<td style="border-style: solid;border-color: black;">字符长度限制</td>
						<td style="border-style: solid;border-color: black;"><font color="blue"><fmt:formatNumber value="${formColumnExtend.formColumn.length/2}"></fmt:formatNumber></font></td>
						<td style="border-style: solid;border-color: black;">默认内容</td>
						<td style="border-style: solid;border-color: black;"><font color="blue">${formColumnExtend.formColumn.defaultValue}</font></td>
					</tr>
					<tr>
						<td style="border-style: solid;border-color: black;">录入方式</td>
						<td colspan="3" style="border-style: solid;border-color: black;">
							<font color="blue">
							<c:choose>
								<c:when test="${formColumnExtend.formColumn.inputType==0}">文本框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==1}">下拉框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==2}">文本域</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==3}">单选框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==4}">多选框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==5}">上传文件框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==6}">日期组件</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==7}">树</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==8}">搜索下拉框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==9}">自定义</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==10}">文本编辑器</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==11}">密码框</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==12}">字处理组件</c:when>
								<c:when test="${formColumnExtend.formColumn.inputType==13}">代码级联</c:when>
							</c:choose>
							</font>
						</td>
					</tr>
				</table>
				<br/>
			</c:forEach>
			</c:if>
		</c:forEach>
	</c:if>
</c:forEach>
<br/><br/>
<p>备注：创建人/创建时间/修改人/修改时间四项信息由系统统一生成，用户尽可查看不能新建和修改。</p>
<br/><br/>
3.  表单【维护】界面是对【<font color="blue">${catalog.name}</font>】表单单条信息进行查看和修改的区域；点击列表界面最右侧【操作】列的【维护】按钮，即可弹出表单【维护】界面
</p>
</div>
</div>