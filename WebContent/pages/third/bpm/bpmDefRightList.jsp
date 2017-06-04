<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义权限管理</title>
<script src="<%=basePath%>/js/json2/json2.js" type="text/javascript"></script>
<style type="text/css">
	.owner-span{
		font-size: 13px;
		background-color: #EFF2F7;
	    border: 1px solid #CCD5E4;
	    border-radius: 5px 5px 5px 5px;
	    cursor: default;
	    float: left;
	    height: auto !important;
	    margin: 3px;
	    overflow: hidden;
	    padding: 2px 4px;
	    white-space: nowrap;
	}
	.hidden{
		display: none;
	}
	.yunTable td{
		height:40px;
	}
</style>
<script type="text/javascript">
	$(function() {
		initOwnerSpan();
		selectAllHandler($("#selectAll"));
	});
	
	function saveDefRights() {
		var url = "<%=basePath %>/pages/third/bpm/bpmDefRight/save.action";
		$.ajax( {
			type : 'POST',
			url : url,
			data: $("#bpmDefRightsForm").serialize(),
			dataType: "json",
			success : function(data) {
				if(data.statusCode == '200') {
					alertMsg.correct(data.message);
				}else{
					alertMsg.error(data.message);
				}
			}
		});
	}
	//显示用户详情
	function openDetailWin(conf){
		var dialogWidth=650;
		var dialogHeight=500;
		
		conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

		var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
			+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
		var url = conf.url + '&hasClose=' +conf.hasClose;
		var rtn=window.showModalDialog(url,"",winArgs);		
	};
	//初始化数据库中的数据
	function initOwnerSpan(){
		$("textarea[name='owner']").each(function(){
			var me = $(this),
				tr = me.parents("tr"),
				owner = $("textarea[name='owner']",tr).val();
				rightType = $("input[name='rightType']",tr).val();
			if(rightType==0){
				if(owner){
					$("#selectAll").attr("checked","checked");
				}
				setVal(me,'textarea');
			}else if(rightType==1){
				var href = "${ctx}/platform/system/sysUser/get.ht?userId=";
				setVal(me,'textarea',href);
			}
			else
				setVal(me,'textarea');
		});
		
	};
	//选择器回填
	function setOwnerSpan(tr,json){
		var div = $("div.owner-div",tr);
		if(!div||div.length==0)return;
		div.empty();
		if(!json||json.length==0)return;
		for(var i=0,c;c=json[i++];){
			if(c.id == "")
				continue;
			var a = $('<a class="moreinfo"></a>').html(c.name).attr("ownerId",c.id);
			/**
			if(href){
				a.attr("hrefstr",href+c.id);
				a.attr("href","#");
			}**/
			var	span = $('<span class="owner-span"></span>').html(a);
			div.append(span);
		}
	};
	//重置
	function resetSelect(obj) {
		var tr = $(obj).parents("tr"),
			div = $("div.owner-div",tr),
			owner = $("textarea[name='owner']",tr);
		div.empty();
		owner.val('');
	}
	//设置值
	function setVal(obj,json){
		var tr=$(obj).parents("tr");
		owner = $("textarea[name='owner']",tr);
		if(json=='textarea'){
			json = owner.val();
			json = $.parseJSON(json);
		}
		else{
			var jsonStr = JSON.stringify(json);
			owner.val(jsonStr);
		}
		setOwnerSpan(tr,json);
	};

	function selectAllHandler(obj) {
		var _this = $(obj);
		if( _this.is(":checked")){
			$("table button.listbutton").hide();
			$("div.owner-div").hide();
			$("#chooseAll").trigger("click");
		}else{
			$("table button.listbutton").show();
			$("div.owner-div").show();
			$("#resetAll").trigger("click");
		}
	};

	//选择所有用户
	function chooseAllHandler(obj) {
		var tr=$(obj).parents("tr");
		var json=[{
			id:0,
			name:"所有用户"
		}];
		setVal(obj,json);
	};
	
	function convertToJson(tempIds,tempNames){
		var ids=tempIds.split(",");
		var names=tempNames.split(",");
		var json=[];
		for(var i=0;i<ids.length;i++){
			var obj={};
			obj.id=ids[i];
			obj.name=names[i];
			json.push(obj);
		}
		return json;
	}
	
	//选择用户
	function chooseUser(obj) {
		var callback=function(ids,names){				
			json=convertToJson(ids,names);
			setVal(obj,json);
		};
		ns.common.userDialog({callback:callback});
	};
	//选择组织
	function chooseOrg(obj){
		var tr=$(obj).parents("tr");
		json = $("textarea[name='owner']",tr).val();
		if(json) {
			json = $.parseJSON(json);
		}
		
		var callback=function(orgIds,orgNames){				
			json=convertToJson(orgIds,orgNames);
			setVal(obj,json);
		};
		ns.common.orgDialog({callback:callback});
	};
	//选择角色
	function chooseRole(obj){
		var tr=$(obj).parents("tr");
		json = $("textarea[name='owner']",tr).val();
		if(json) {
			json = $.parseJSON(json);	
		}
		var callback=function(roleIds,roleNames){				
			json=convertToJson(roleIds,roleNames);
			setVal(obj,json);
		};
		ns.common.roleDialog({callback:callback});	
	};
    //岗位选择
    function choosePosition(obj){
    	var tr=$(obj).parents("tr");
		json = $("textarea[name='owner']",tr).val();
		if(json){
			json = $.parseJSON(json);
		}
		var callback=function(positionIds,positionNames){				
			json=convertToJson(positionIds,positionNames);
			setVal(obj,json);
		};
		ns.common.positionDialog({callback:callback});	
    };
    
	function isChangeCheck(){
		var v = $('#isChange').val();
		if(v==0){
			$('#isChange').val(1);
		}else{
			$('#isChange').val(0);
		}
	}
</script>
</head>
<body>
	<div class="panelBar">
		<button type="button" class="listbutton" onclick="saveDefRights()">保存</button>
		<button type="button" class="listbutton close">关闭</button>
		<c:if test="${isParent==1}">
				<input type="checkbox" id="chbIsChange" onclick="isChangeCheck()" >子类型随父类型权限变更
		</c:if>
	</div>
	<form id="bpmDefRightsForm" action="<%=basePath %>/pages/third/bpm/bpmDefRight/save.action" method="post">
		<input type="hidden"  name="id" value="${id}">
		<input type="hidden"  name="type" value="${type}">
		<input type="hidden"  name="defKey" value="${defKey}">
		<input type="hidden"  id="isChange" name="isChange" value="0">
				<table id="bpmDefRightTable"  class="yunTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th width="150px;" nowrap="nowrap" >权限分类</th>
							<th style="text-align: center;" >授权给</th>
							<th  width="180px;" nowrap="nowrap"  style="text-align: center;" >选择</th>
						</tr> 
					</thead>
						<tr>
							<td colspan="3"  style="text-align: left;height:40px;line-height: 40px;"> 
								<input id="selectAll" style="float:left" type="checkbox" onclick="selectAllHandler(this)"> <label style="float:left"  for="selectAll">允许所有人访问</label>
							</td>
						</tr>
						<tr style="display: none">
							<td >所有用户</td>
							<td >
								<div class="owner-div"></div>
								<textarea rightType="0" class="hidden" name="owner">${rightsMap.all}</textarea>
								<input type="hidden" name="rightType" value="0">
							</td >
							<td>			
								<a id="chooseAll" onclick="chooseAllHandler(this);" ></a>
                                <a id="resetAll" onclick="resetSelect(this);" ></a>
							</td>
						</tr>
						<tr>
							<td >用户授权</td>
							<td >
								<div class="owner-div"></div>
								<textarea class="hidden" name="owner">${rightsMap.user}</textarea>
								<input type="hidden" name="rightType" value="1">
							</td>
							<td >
								<button class="listbutton" type="button" onclick="chooseUser(this);" >选择..</button>
								<button class="listbutton" type="button" onclick="resetSelect(this);" >重置</button>
							</td>
						</tr>
						<tr>
							<td >角色授权</td>
							<td >
								<div class="owner-div"></div>
								<textarea class="hidden" name="owner">${rightsMap.role}</textarea>
								<input type="hidden" name="rightType" value="2">				
							</td>
							<td >
								<button class="listbutton" type="button" onclick="chooseRole(this);" >选择..</button>
								<button class="listbutton" type="button" onclick="resetSelect(this);" >重置</button>
							</td>
						</tr>
						<tr>
							<td >组织授权(本层级)</td>
							<td >
								<div class="owner-div"></div>
								<textarea class="hidden" name="owner">${rightsMap.org}</textarea>
								<input type="hidden" name="rightType" value="3">
							</td>
							<td >
								<button class="listbutton" type="button" onclick="chooseOrg(this);" >选择..</button>
								<button class="listbutton" type="button" onclick="resetSelect(this);" >重置</button>
							</td>
						</tr>
						<tr>
							<td >组织授权(包含子组织)</td>
							<td >
								<div class="owner-div"></div>
								<textarea class="hidden" name="owner">${rightsMap.orgGrant}</textarea>
								<input type="hidden" name="rightType" value="7">
								</td>
							<td >
								<button class="listbutton" type="button" onclick="chooseOrg(this);" >选择..</button>
								<button class="listbutton" type="button" onclick="resetSelect(this);" >重置</button>
							</td>
						</tr>
						<tr>
							<td >岗位授权</td>
							<td >
								<div class="owner-div"></div>
								<textarea class="hidden" name="owner">${rightsMap.position}</textarea>
								<input type="hidden" name="rightType" value="4">
							</td>
							<td >
								<button class="listbutton" type="button" onclick="choosePosition(this);" >选择..</button>
								<button class="listbutton" type="button" onclick="resetSelect(this);" >重置</button>
							</td>
						</tr>
				</table>
		</form>
</body>
</html>


