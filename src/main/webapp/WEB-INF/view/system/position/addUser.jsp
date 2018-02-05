<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
<!--
	ns.position.loadAllUser = function(){
		$("#add_all_user").loadUrl("<%=basePath %>/pages/system/user/userpUserList.action?relationId=1");
	}
	
	ns.position.closePAddUser = function(){
		$.pdialog.close("addUserDialog");
	}
	
	ns.position.savePAddUser = function(){
		var ids = '';
		$.each($("div[belong='usersId']"),function (entryIndex,entry){
			ids += $(entry).html()+";";
		});
		$.ajax({
			url : "<%=basePath %>/pages/system/position/positionaddUser.action",
			type: 'post',
			data: 'userIds='+ids+'&positionId=${param.positionId}',
			dataType : "json",
			success : function(data) {
				alertMsg.info("操作成功!");
				if(${param.forward=='setUserList'}){
					ns.position.loadSetUserTabList('${param.positionId}');
				}else{
					ns.position.loadUserTabList();
				}
				$.pdialog.close("addUserDialog");
			}
		});
	}
	
	$(function(){
		$("#orgtreeId").load("<%=basePath %>/pages/system/position/orgTree.jsp");
		ns.position.loadAllUser();
	});
//-->
</script>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td width="25%">
			<div class="accordion">
				<div class="accordionHeader">
					<h2>
						<span>icon</span>按组织查找
					</h2>
				</div>
				<div class="accordionContent" style="height:350px">
					<div id="orgtreeId"/>
				</div>
				<div class="accordionHeader">
					<h2>
						<span>icon</span>按岗位查找
					</h2>
				</div>
				<div class="accordionContent">
					
				</div>
				<div class="accordionHeader">
					<h2>
						<span>icon</span>按角色查找
					</h2>
				</div>
				<div class="accordionContent">
					
				</div>
			</div>
		</td>
		<td width="55%">
			<div class="panel" defH="390">
				<h1>
					所有用户
				</h1>
				<div id="add_all_user">
					
				</div>
			</div>
		</td>
		<td width="20%">
			<div class="panel" defH="390">
				<h1>
					选择用户
				</h1>
				<div>
					<table border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" width="100%">
						<thead>
							<tr>
								<th height="14px" align="center" style="font-weight: bold;">用户姓名</th>
								<th height="14px" align="center" style="font-weight: bold;">操作</th>
							</tr>
						</thead>
						<tbody id="selectUserList">
						</tbody>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
<div class="buttonPanel" align="center">
	<button type="button" class="listbutton" onclick="ns.position.savePAddUser()">
		确定
	</button>
	<button type="button" class="listbutton" onclick="ns.position.closePAddUser()">
		取消
	</button>
</div>