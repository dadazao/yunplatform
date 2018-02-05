<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/form.jsp" %>
	<title>用户预览</title>
	<f:js pre="js/lang/view/platform/bpm" ></f:js>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/util/easyTemplate.js"></script>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerTab.js"></script>
	<script type="text/javascript">
		var defId=${param.defId};
		var mockParams;
		var navTab=null;
		var queryOk=false;
		
		$(function(){
			mockParams = window.dialogArguments;
			var params={paramsJson:mockParams};
			var url=__ctx + '/platform/bpm/bpmNodeUser/getByUserParams.ht';
			var template=$("#tbodyPreview").val();
			$.post(url,params,function(data){
				if(data.length==0) return;
				if(data){
					var str=easyTemplate(template,data).toString();
					$("#tbodyMockParams").append(str);
				}
			});
			$("#btnSearch").click(preView);
			
			$("#tabPreView").ligerTab();
			$("#resultView").ligerTab({height:250});
			navTab=$("#tabPreView").ligerGetTabManager();
		});
		
		function preView(){
			var aryInput=$("input", $("#tbodyMockParams"));
			var isValid=true;
			var objParameter={};
			objParameter.defId=defId;
			for(var i=0;i<aryInput.length;i++){
				var obj=$(aryInput.get(i));
				if(obj.val()==""){
					isValid=false;
					break;
				}
				//构建参数对象
				objParameter[obj.attr("name")]=obj.attr("previewId");
			}
			if(!isValid){
				$.ligerDialog.warn($lang_bpm.nodeUser.preview.inputParams,$lang.tip.msg);
				return;
			}
			objParameter.nodeUser=mockParams;
			var url=__ctx +"/platform/bpm/bpmNodeUser/preview.ht";
			
			var template=$("#txtResultView").val();
			
			$.post(url,objParameter,function(data){
				if(data.length==0){
					$.ligerDialog.warn($lang_bpm.nodeUser.preview.notUser,$lang.tip.msg);
					return ;
				}
				navTab.selectTabItem("tabitem2");
				var str=easyTemplate(template,data).toString();
				$("#resultView").html(str);
				
				queryOk=true;
			});
		}
		
		function selectExecutor(obj,type){
			var btn=$(obj);
			var input=$("input", btn.parents("td"));
			callback=function(id,name){
				input.val(name);
				input.attr("previewId",id);
			};
			switch(type){
				//用户单选
				case 1:
					UserDialog({isSingle:true,callback:callback});
					break;
				//用户多选
				case 2:
					UserDialog({isSingle:false,callback:callback});
					break;
				//部门
				case 3:
					OrgDialog({isSingle:false,callback:callback});
					break;
				//岗位
				case 4:
					PosDialog({isSingle:false,callback:callback});
					break;
				//角色
				case 5:
					RoleDialog({isSingle:false,callback:callback});
					break;
				//单选部门
				case 6:
					OrgDialog({isSingle:true,callback:callback});
					break;
			}
		}
	</script>
	
	<style type="text/css">
		thead th{
			text-align: left!important;
			padding-left: 5px;
		}
	</style>
</head>

<body style="overflow-x: hidden;">
	<div class="panel">
		<div class="hide-panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span class="tbar-label">用户预览</span>
				</div>
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link search" id="btnSearch"><span></span>预览</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link close"  href="#" onclick="window.close();"><span></span>关闭</a></div>
					</div>	
				</div>
			</div>
		</div>
		
		<div class="panel-body">
			<div id="tabPreView">
				<div  title="预览参数值">
					<table  class="table-detail">
			   			<thead>
			   				<tr>
			   					<th>参数名</th>
			   					<th>参数值</th>
			   				</tr>
			   			</thead>
			   			<tbody id="tbodyMockParams">
			   			</tbody>
			   		</table>
		   		</div>
		   		<div title="结果" id="divResult">
		   			<div id="resultView" style="border: 1px solid silver;overflow: auto;"></div>
		   		</div>
	   		</div>
		</div>
		
	</div>
	<textarea id="txtResultView" style="display: none;">
			<#list data as user>
				<span><b>\${user.fullname }[\${user.account }]&nbsp;&nbsp;</b></span>
			</#list> 
	</textarea>
			
	<textarea id="tbodyPreview"  style="display: none;" >
	<#list data as item>
		<#if (item.type==1)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text" name="startUserId" readonly="readonly" value="" previewId=""/> 
					<a class="button" onclick="selectExecutor(this,1)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==2)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="preUserId" readonly="readonly" value="" previewId=""/>  
					<a class="button" onclick="selectExecutor(this,1)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==3)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="formUserId" readonly="readonly" value="" previewId=""/>   
					<a class="button" onclick="selectExecutor(this,2)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==4)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="formOrgId" readonly="readonly" value="" previewId=""/>    
					<a class="button" onclick="selectExecutor(this,3)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==6)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="formPosId" readonly="readonly" value="" previewId=""/>    
					<a class="button" onclick="selectExecutor(this,4)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==7)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="formRoleId" readonly="readonly" value="" previewId=""/>    
					<a class="button" onclick="selectExecutor(this,5)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==8)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="startOrgId" readonly="readonly" value="" previewId=""/>    
					<a class="button" onclick="selectExecutor(this,6)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		<#elseif (item.type==9)>
			<tr>
				<td>\${item.title}:</td>
				<td>
					<input type="text"  name="preOrgId" readonly="readonly" value="" previewId=""/>    
					<a class="button" onclick="selectExecutor(this,6)"><span class="chosen">选择</span></a>
				</td>
			</tr>
		</#if>
	</#list>
	</textarea>
		
</body>
</html>


