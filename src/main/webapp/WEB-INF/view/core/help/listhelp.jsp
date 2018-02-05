<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
<!--		
	
//-->
</script>
<div style="overflow-y: auto; overflow-x: hidden; height: 610px;background-color: white;">
<div style="margin-left: 20px;margin-right: 20px;">
<p align="center" style="font-weight: bold;font-size: 18px;">
<br/>
${catalog.name}列表界面<br/>
</p>
<p style="font-size: 13px;line-height: 30px;">
列表界面是对【<font color="blue">${catalog.name}</font>】的集中展示与操作区域，可实现对【<font color="blue">${catalog.name}</font>】的快速浏览、检索以及维护修改；点击系统平台左侧功能目录区的【<font color="blue">${catalog.name}</font>】即可在屏幕左侧显示列表界面；<br/>

【<font color="blue">${catalog.name}</font>】列表界面分为按钮区、查询区、分页区和信息展示与操作区。<br/><br/>

1、按钮区使用说明<br/>
<c:forEach items="${tabulation.tabulationButtons}" var="tabulationButton" varStatus="status">
	<c:if test="${tabulationButton.buttonType == '1'}">
		<c:forEach items="${tabulationButton.buttonGroup.buttonAndGroups}" var="buttonAndGroup" varStatus="stat">
			&bull;【<font color="blue">${buttonAndGroup.button.buttonName}</font>】按钮：【<font color="blue">${buttonAndGroup.button.comment}</font>】；<br/>
		</c:forEach>
	</c:if>
	<c:if test="${tabulationButton.buttonType == '0'}">
		&bull;【<font color="blue">${tabulationButton.button.buttonName}</font>】按钮：【<font color="blue">${tabulationButton.button.comment}</font>】；<br/>
	</c:if>
</c:forEach>
<br/>
2、查询区使用说明<br/>
<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce" varStatus="status">
	<c:if test="${ce.formColumn.isDefaultQuery == 1}">
		&bull;按照【<font color="blue">${ce.formColumn.columnZhName}</font>】进行信息检索，在搜索框中【输入关键字】，输入后按回车键或点击【查询】按钮，则出现所有【包括关键字】的信息；<br/>
	</c:if>
</c:forEach>

以上查询均支持组合查询
<br/><br/>
3、分页区使用说明<br/>
&bull; 系统默认每页显示信息【20】条，点击【上页】和【下页】实现上翻页和下翻页，在右侧输入框中输入具体页码数，并点击【前往】，可直接进入到指定页面；
<br/><br/>
4、信息展示<br/>
本区域是全部【<font color="blue">${catalog.name}</font>】的展示区域，用以集中展示【<font color="blue">${catalog.name}</font>】中最为常用和重要的【<font color="blue">${showcount}</font>】项信息，总计分为如下【<font color="blue">${totalcount}</font>】列，系统默认的展示顺序是按照【创建时间】排列，最新创建的信息排列在最前面：<br/>
<c:if test="${tabulation.isSelect==1}">
&bull;【选择】列：点击【选择】图标，显示为打钩状态，表示选中，可继续进行【删除】等操作；<br/>
</c:if>
<c:if test="${tabulation.isSelect==1}">
&bull;【序号】列：显示每条信息的序号；<br/>
</c:if>

<c:forEach items="${tabulation.tabulationColumnExtends}" var="ce">
	<c:if test="${ce.formColumn.isShowInList==1}">
		&bull;【<font color="blue">${ce.formColumn.columnZhName}</font>】列：显示每条信息的【<font color="blue">${ce.formColumn.columnZhName}</font>】信息；<br/>
	</c:if>
</c:forEach>

<c:if test="${tabulation.isSelect==1}">
&bull;【操作】列：点击【操作】列的【维护】按钮，即可弹出表单【维护】界面；<br/>
</c:if>

每列的宽度由系统根据显示信息的长度自动调整<br/>
</p>
</div>
</div>