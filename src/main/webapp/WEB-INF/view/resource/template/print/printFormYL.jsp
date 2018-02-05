<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link rel="stylesheet" href="<%=basePath %>/themes/css/ztree/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="<%=basePath %>/js/jquery/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/cloudstong/yun.iweboffice.js"></script>
		<script type="text/javascript">
			var op = "formyl";
			
			var setting = {
				data : {
					simpleData : {
						enable : true						
					}
				},
				callback: {
					onClick: zTreeOnClick,
					onNodeCreated: zTreeOnNodeCreated
				}
			};
			$(function(){
				$('#yunDialog1').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
				$('#iWebOfficeTip').hide();
				
				$.ajax( {
					type : 'POST',
					url : '<%=basePath %>/pages/resource/printtableData.action?mainTable=sys_dayinmoban&params=tbl_formId:'+$('#formId').val()+';tbl_types:0',
					dataType:'json',
					success : function(data) {
						var zNodes = '';
						if(data.length == 0){
							zNodes ='[{"id":"100","open":true,"pId":"-1","name":"无打印模板"}]';
						}else{
							zNodes ='[{"id":"100","open":true,"pId":"-1","name":"可选模板"}';
							$.each(data,function(entryIndex, entry){
								zNodes += ',{"id":"'+entryIndex+'","open":true,"pId":"100","name":"'+entry.tbl_name.substr(0,6) + "..."+'","remark":"'+entry.tbl_name+'","filename":"'+entry.tbl_content+'"}';
								//_printNameTree += (entryIndex+1)+'.<a style="cursor:hand;text-decoration:none;" onclick="getPrintTemplate(\''+entry.tbl_content+'\')">'+entry.tbl_name+'</a><br/>';
							})
							zNodes += ']';
						}
						$.fn.zTree.init($("#printNameTree"), setting, $.parseJSON(zNodes));
					}
				});
				var si = setInterval(function(){
					try{
						if(!window.frames["ifrWeboffice"].webform.WebOffice.ShowMenu){
							$('#iWebOfficeTip').show();
						}	
						clearInterval(si);
					}catch(e){
						$('#iWebOfficeTip').show();
						clearInterval(si);
					}
				},1000);
			})
		</script>
	</head>
	<body>
		<div id="yunDialog">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr id="iWebOfficeTip" style="display: none;font-size: 12px;">
			    <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;如果不能正常自动安装iWebOffice2009插件，请<strong><a href="<%=path%>/js/iweboffice/InstallClient.zip">下载</a></strong>本地安装程序，如果插件异常请运行KMWUninstall.exe卸载程序</td>
			  </tr>
			  <%--<tr>
			    <td colspan="2"><button onclick="eventCompexListQPXS();" style="width:80px;height:24px;" class="listbutton" name="b2" id="QPXS" type="button">全屏显示</button><button onclick="eventCompexListPrint();" style="width:60px;height:24px;" class="listbutton" name="b2" id="DY" type="button">打印</button></td>
			  </tr>--%>
			  <tr>
			    <td align="left" valign="top" width="15%"><ul id="printNameTree" class="ztree"></ul></td>
			    <td width="85%">
				    <div id="webOfficeDiv">
				    	 <iframe id="ifrWeboffice" name="ifrWeboffice" width="100%" height="600" src="pages/resource/template/print/iWebofficeInit.jsp"></iframe>
				    </div>
			    </td>
			  </tr>
			</table>
			
		</div>
	</body>
</html>