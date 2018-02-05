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
列表表单界面<br/>
</p>
<p style="font-size: 13px;line-height: 30px;">
表单【新建】界面是对【<font color="blue">列表表单</font>】单条信息进行录入操作的区域；点击列表界面的【新建】按钮，即可弹出列表【新建】界面；分为按钮区、信息操作区。<br/><br/>

1、按钮区<br/>
•【<font color="blue">修改</font>】按钮：【<font color="blue">本按钮在新建界面中不使用，置灰；在维护界面中正常使用，点击后即可实现对原信息的修改</font>】；<br/>
•【<font color="blue">保存</font>】按钮：【<font color="blue">在表单界面录入信息的过程中，点击本按钮可实现即时的信息保存，点击后页面仍停留在原状态，可继续录入；如保存成功，会出现操作成功提示信息，如未保存成功，会出现错误提示</font>】；<br/>
•【<font color="blue">帮助</font>】按钮：【<font color="blue">显示该表单的帮助信息</font>】；
<br/>
2、信息操作区<br/>
【<font color="blue">列表表单</font>】表单【新建】界面信息操作区共包括【<font color="blue">4</font>】个选项卡<br/>
2.1、【<font color="blue">基本信息</font>】选项卡<br/>
2.2、【<font color="blue">按钮信息</font>】选项卡<br/>
2.3、【<font color="blue">操作信息</font>】选项卡<br/>
2.4、【<font color="blue">筛选条件</font>】选项卡<br/>
<br/><br/>
<p>备注：创建人/创建时间/修改人/修改时间四项信息由系统统一生成，用户尽可查看不能新建和修改。</p>
<br/><br/>
3.  表单【维护】界面是对【<font color="blue">列表表单</font>】单条信息进行查看和修改的区域；点击列表界面最右侧【操作】列的【维护】按钮，即可弹出表单【维护】界面
</p>
</div>
</div>