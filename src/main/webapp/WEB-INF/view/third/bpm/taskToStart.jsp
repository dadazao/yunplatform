<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@include file="/commons/include/html_doctype.html"%>
<html>
	<head>
		<title>流程任务-[${task.name}]执行</title>
		<%@include file="/commons/include/customForm.jsp" %>
		<%@include file="/js/msg.jsp"%>
		
			
		<!-- 悬浮工具栏实现的CSS -->
		<style type="text/css">  
		{ 
		    margin: 0px; 
		    padding: 0px; 
		} 
		body { 
		   
		} 
		#topNavWrapper { 
		    width: 100%; 
		    text-align: left; 
		    height: 70px; 
		    margin: 0px auto; 
		    z-index:100; 
		    _position: relative ; 
		    _top:0px; 
		} 
		#topNav { 
		    width: 100%; 
		    float: left; 
		    display: block; 
		    z-index: 100; 
		    overflow: visible; 
		    position: fixed; 
		    top: 0px; /* position fixed for IE6 */ 
		    _position: absolute; 
		    _top: expression(documentElement.scrollTop + "px"); 
	/* 	    background-color: yellow;  */
		    background-repeat: no-repeat; 
		    background-position: right; 
		    height: 70px; 
	/* 	    border:1px solid red; */
		} 
		</style> 
		
		
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskSignWindow.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskAddSignWindow.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskBackWindow.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/TaskImageUserDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/tabOperator.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmTaskExeAssignDialog.js"></script>
		<script type="text/javascript">
		var taskId="${task.id}";
		var isExtForm=${isExtForm};
		var isEmptyForm=${isEmptyForm};
		var isSignTask=${isSignTask};
		var isHidePath=${isHidePath};
		var isManage=${isManage};
		var isNeedSubmitConfirm=${bpmDefinition.submitConfirm==1};
		
		var bpmGangedSets=[];
		//操作类型
		//1.完成任务
		//2.保存数据
		var operatorType=1;
		
	
		//补签
		function showAddSignWindow(){
			TaskAddSignWindow({taskId:taskId,callback:function(sign){
				loadTaskSign();
			}});
		}
		
		//加载会签数据。
		function loadTaskSign(){
			$(".taskOpinion").load('${ctx}/platform/bpm/task/toSign.ht?taskId=${task.id}');
		}
		//显示流程图
		function showTaskUserDlg(){
			TaskImageUserDialog({actInstId:${processRun.actInstId}});
		}
		//显示沟通意见。
		function showTaskCommunication(conf){
			var obj = {data:CustomForm.getData()};
			isTaskEnd(function(){
				if(!conf) conf={};
				//输入子页面
				var url=__ctx + "/platform/bpm/task/toStartCommunicate.ht?taskId=${task.id}";
				var winArgs="dialogWidth=500px;dialogHeight=280px;help=0;status=0;scroll=0;center=1";
				url=url.getNewUrl();
				var rtn=window.showModalDialog(url,obj,winArgs);
			})
		}
		//显示流转意见
		function showTaskTransTo(conf) {
			var obj = {data:CustomForm.getData()};
			isTaskEnd(function(){
				if(!conf) conf={};
				//输入子页面
				var url=__ctx + "/platform/bpm/task/toTransTo.ht?taskId=${task.id}";
				var winArgs="dialogWidth=550px;dialogHeight=400px;help=0;status=0;scroll=0;center=1";
				url=url.getNewUrl();
				var rtn=window.showModalDialog(url,obj,winArgs);
				if(rtn=="ok"){
					handJumpOrClose();
				}
			})
		}
		
		function isTaskEnd(callBack){
			var url="${ctx}/platform/bpm/task/isTaskExsit.ht";
			var params={taskId:"${task.id}"};
			
			$.post(url,params,function(responseText){
				var obj=new com.hotent.form.ResultMessage(responseText);			
				if(obj.isSuccess()){
					callBack.apply(this);
				}
				else{
					$.ligerDialog.warn("这个任务已经完成或被终止了!",$lang.tip.warn);
				}
			});
		}
		
		function submitForm(action,button){
			var ignoreRequired=false;
			if(button=="#btnSave"){
				ignoreRequired=true;
			}
			
			if($(button).hasClass("disabled"))return;
			if(isEmptyForm){
				$.ligerDialog.error("还没有设置表单!",'提示信息');
				return;
			}
			
			$('#frmWorkFlow').attr("action",action);
			if(isExtForm){
				$(button).addClass("disabled");
				$('#frmWorkFlow').ajaxForm({success:showResponse }); //terry add				
				$('#frmWorkFlow').submit();
			}else{
				var rtn=CustomForm.validate({ignoreRequired:ignoreRequired});
				if(!rtn){
					$.ligerDialog.warn("表单验证不成功,请检查表单是否正确填写!","提示信息");
					return;
				}
				
				var data=CustomForm.getData();
				var rtn=CustomForm.validate();
				if(!rtn) return;
				
				//子表必填检查(兼容新旧版本)					
				rtn = SubtablePermission.subRequired();
				if(!rtn){	
					$.ligerDialog.warn("请填写子表表单数据！","提示信息");
					return;
				}
				
				//var rtn=CustomForm.validate();
				//if(!rtn) return;
				if(button!="#btnSave"){
					//子表必填检查(兼容新旧版本)					
					rtn = SubtablePermission.subRequired();
					if(!rtn){	
						$.ligerDialog.warn("请填写子表表单数据！","提示信息");
						return;
					}
				}
				
				var data=CustomForm.getData();
				//WebSign控件提交。 有控件时才提交   xcx
				if(WebSignPlugin.hasWebSignField){
					WebSignPlugin.submit();
				}				
				
				$(button).addClass("disabled");
				
				var uaName=navigator.userAgent.toLowerCase();				
				if(uaName.indexOf("firefox")>=0||uaName.indexOf("chrome")>=0){  //异步处理
					//Office控件提交。 有可以提交的文档
					if(OfficePlugin.submitNum>0){
						OfficePlugin.submit();             //火狐和谷歌 的文档提交包括了  业务提交代码部分（完成  OfficePlugin.submit()后面的回调 函数 有 业务提交代码），所以 后面就不用加上业务提交代码
					}else{   //没有可提交的文档时 直接做 业务提交代码
						data=CustomForm.getData();
						//设置表单数据
						$("#formData").val(data);					
						$('#frmWorkFlow').submit();
					}					
				}else{   //同步处理
					//Office控件提交。 有可以提交的文档
					if(OfficePlugin.submitNum>0){
						OfficePlugin.submit();
						//当提交问题 等于 提交数量的变量 时 表示所有文档 都提交了  然后做 业务相关的提交
						if(OfficePlugin.submitNum == OfficePlugin.submitNewNum){    
							//获取自定义表单的数据
							data=CustomForm.getData();
							//设置表单数据
							$("#formData").val(data);					
							$('#frmWorkFlow').submit();
							OfficePlugin.submitNewNum = 0; //重置  提交数量的变量
						}else{
							$.ligerDialog.warn("提交失败,OFFICE控件没能正常使用，请重新安装 ！！！","提示");
						}
					}else{
						//获取自定义表单的数据
						data=CustomForm.getData();
						//设置表单数据
						$("#formData").val(data);					
						$('#frmWorkFlow').submit();
					}		
				}
				
			}
		}
		
		function saveData(){
			var operatorObj=getByOperatorType();
			var button="#" +operatorObj.btnId;
			
			var rtn=beforeClick(operatorType);
			
			if( rtn==false){
				return;
			}
			if(isExtForm){//提交第三方表单时检查该表单的参数
				var frm=$('#frmWorkFlow');
				if(!frm.valid()) return ;
				if(frm.setData)frm.setData();
			}
			
			var action="${ctx}/platform/bpm/task/saveData.ht";
			submitForm(action,button);
		}

		//完成当前任务。
		function completeTask(){
			var nextPathId=$("input[name='nextPathId']");
			if(nextPathId.length>0){
				var v=$("input[name='nextPathId']:checked").val();
				if(!v){
					$.ligerDialog.error("在同步条件后的执行路径中，您至少需要选择一条路径!",'提示信息');
					return;	
				}
			}
			var operatorObj=getByOperatorType();
		
			var button="#" +operatorObj.btnId;
			var action="${ctx}/platform/bpm/task/complete.ht";
			//执行前置脚本
			var rtn=beforeClick(operatorType);
			if( rtn==false){
				return;
			}
			//确认执行任务。
			if(isNeedSubmitConfirm){
				$.ligerDialog.confirm("您确定执行此操作吗？","提示",function(rtn){
					if(rtn){
						submitForm(action,button);
					}
				});
			}
			else{
				submitForm(action,button);	
			}
		}
		
		
		function getByOperatorType(){
			var obj={};
			switch(operatorType){
				//同意
				case 1:
					obj.btnId="btnAgree";
					obj.msg="执行任务成功!";
					break;
				//反对
				case 2:
					obj.btnId="btnNotAgree";
					obj.msg="执行任务成功!";
					break;
				//弃权
				case 3:
					obj.btnId="btnAbandon";
					obj.msg="执行任务成功!";
					break;
					//驳回
				case 4:
					obj.btnId="btnReject";
					obj.msg="驳回任务成功!";
					break;
				//驳回到发起人
				case 5:
					obj.btnId="btnRejectToStart";
					obj.msg="驳回到发起人成功!";
					break;
				//保存表单
				case 8:
					obj.btnId="btnSave";
					obj.msg="保存表单数据成功!";
					break;
				
			}
			return obj;
		}
		
		function getErrorByOperatorType(){
			var rtn="";
			switch(operatorType){
				//同意
				case 1:
				//反对
				case 2:
				//弃权
				case 3:
					rtn="执行任务失败!";
					break;
				//驳回
					//驳回
				case 4:
					rtn="驳回任务失败!";
					break;
				//驳回到发起人
				case 5:
					rtn="驳回到发起人失败!";
					break;
				//保存表单
				case 8:
					rtn="保存表单数据失败!";
					break;
			}
			return rtn;
		}
		
		function showResponse(responseText){
			var operatorObj=getByOperatorType();
			$("#" +operatorObj.btnId).removeClass("disabled");
			var obj=new com.hotent.form.ResultMessage(responseText);
			if(obj.isSuccess()){
				$.ligerDialog.success(operatorObj.msg,'提示信息',function(){
					var rtn=afterClick(operatorType);
					if( rtn==false){
						return;
					}
					handJumpOrClose();
				});
			}else{
				var title=getErrorByOperatorType();
				$.ligerDialog.err('出错信息',title,obj.getMessage());
			}
		}
		
		//弹出回退窗口 TODO 去除
		function showBackWindow(){
			new TaskBackWindow({taskId:taskId}).show();
		}
		
		$(function(){
			initForm();
			//显示路径
			chooseJumpType(1);
			resizeIframe();
			//初始化联动设置
			<c:if test="${!empty bpmGangedSets}">
				bpmGangedSets = ${bpmGangedSets};
				FormUtil.InitGangedSet(bpmGangedSets);
			</c:if>			
		});	
		
		//获取是否允许直接结束。
		function getIsDirectComplete(){
			var isDirectComlete = false;
			if($("#chkDirectComplete").length>0){
				if($("#chkDirectComplete").attr("checked")=="checked"){
					isDirectComlete = true;
				}
			}
			return isDirectComlete;
		}
		
		//提交第三方表单时检查该表单的参数
		function setExtFormData(){
			if(isExtForm){
				var frm=$('#frmWorkFlow');
				if(!frm.valid()) return ;
				if(frm.setData)frm.setData();
			}
		}
		
		function initBtnEvent(){
			//0，弃权，1，同意，2反对。
			var objVoteAgree=$("#voteAgree");
			var objBack=$("#back");
			
			//同意
			if($("#btnAgree").length>0){
				$("#btnAgree").click(function(){
					setExtFormData();
					
					var isDirectComlete = getIsDirectComplete();
					
					operatorType=1;
					//同意:5，一票通过。
					var tmp =isDirectComlete?"5":"1";
					
					objVoteAgree.val(tmp);
					objBack.val("0");
					completeTask();
				});
			}
			//反对
			if($("#btnNotAgree").length>0){
				$("#btnNotAgree").click(function(){
					setExtFormData();
					var isDirectComlete = getIsDirectComplete();
					operatorType=2;
					////直接一票通过
					var tmp =isDirectComlete?'6':'2';
					objVoteAgree.val(tmp);
					objBack.val("0");
					completeTask();
				});
			}
			//弃权
			if($("#btnAbandon").length>0){
				$("#btnAbandon").click(function(){
					setExtFormData();
					operatorType=3;
					objVoteAgree.val(0);
					objBack.val("");
					completeTask();
				})
			}
			//驳回
			if($("#btnReject").length>0){
				$("#btnReject").click(function(){
					var voteContent = $('#voteContent'),
						content = voteContent.val();
					if(voteContent.length==0||(content && content.trim()!='')){
						setExtFormData();
						
						operatorType=4;
						objVoteAgree.val(3);
						objBack.val(1);
						completeTask(); 
					}else{
						$.ligerDialog.warn("请填写驳回意见","提示信息");
					}
				})
			}
			//驳回到发起人
			if($("#btnRejectToStart").length>0){
				$("#btnRejectToStart").click(function(){
					var voteContent = $('#voteContent'),
					content = voteContent.val();
					if(voteContent.length==0||(content && content.trim()!='')){
						setExtFormData();
						
						operatorType=5;
						//驳回到发起人
						objVoteAgree.val(3);
						objBack.val(2);
						completeTask();
					}else{
						$.ligerDialog.warn("请填写驳回意见","提示信息");
					}
				})
			}
			//保存表单
			if($("#btnSave").length>0){
				$("#btnSave").click(function(){
					setExtFormData();
					
					operatorType=8;
					saveData();
				})
			}
			
			//终止流程
			$("#btnEnd").click(function(){
				isTaskEnd(endTask);
			});
		}
		
		//终止流程。
		function endTask(){
			var url=__ctx + "/platform/bpm/task/toStartEnd.ht";
			var winArgs="dialogWidth=400px;dialogHeight=200px;help=0;status=0;scroll=0;center=1";
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,"",winArgs);
			if(rtn){
				var url="${ctx}/platform/bpm/task/endProcess.ht?taskId=${task.id}";
				var params={taskId:taskId,memo:rtn};
				$.post(url,params,function(responseText){
					var obj=new com.hotent.form.ResultMessage(responseText);
					if(!obj.isSuccess()){
						$.ligerDialog.err("提示信息","终止任务失败!",obj.getMessage());
						return;
					}
					$.ligerDialog.success(obj.getMessage(),"提示信息",function(){
						handJumpOrClose();
					});
				});	
			}
		}
		
		function handJumpOrClose(){
			if(window.opener){
				try{
					window.opener.location.href=window.opener.location.href.getNewUrl();
				}
				catch(e){}
			}	
			window.close();
		}
		
		function initForm(){
			//初始化按钮事件。
			initBtnEvent();
			
			if(isEmptyForm) return;
		
			if(isExtForm){				
				var formUrl=$('#divExternalForm').attr("formUrl");
				$('#divExternalForm').load(formUrl, function() {
					hasLoadComplete=true;
					//动态执行第三方表单指定执行的js
					try{
						afterOnload();
					}catch(e){}
					initSubForm();
				});
			}else{
				$(".taskopinion").each(function(){
					$(this).removeClass("taskopinion");
					var actInstId=$(this).attr("instanceId");
					$(this).load("${ctx}/platform/bpm/taskOpinion/listform.ht?actInstId="+actInstId);
				});
				initSubForm();
			}
		}
		
		function initSubForm(){
			$('#frmWorkFlow').ajaxForm({success:showResponse }); 
		}
		
		function showRoleDlg(){
			RoleDialog({callback:function(roleId,roleName){$('#forkUserUids').val(roleId);}}); 
		}
		
		function chooseJumpType(val){
			if(isHidePath&&isManage==0) return;
			var obj=$('#jumpDiv');
			var url="";
			if(val==1){
				url="${ctx}/platform/bpm/task/tranTaskUserMap.ht?taskId=${task.id}&selectPath=0";
			}
			//选择路径跳转
			else if(val==2){
				url="${ctx}/platform/bpm/task/tranTaskUserMap.ht?taskId=${task.id}";
			}
			//自由跳转
			else if(val==3){
				url="${ctx}/platform/bpm/task/freeJump.ht?taskId=${task.id}";
			}
		
			url=url.getNewUrl();
			
			if(val==3){  //自由跳转
				$.ajaxSetup({async:false});  //同步 获得结果后  再去查询相关的人员
				obj.html(obj.attr("tipInfo")).show().load(url);
				$.ajaxSetup({async:true}); //异步
				//自由跳转 时 从下拉节点的默认的值 中查出相关的人员 
				var destTask=$('#destTask');   //默认的选中的对象
				changeDestTask(destTask);    //查出相关的人员 并改显示出来
			}else{
				obj.html(obj.attr("tipInfo")).show().load(url);
			}
			
		};
		
		//为目标节点选择执行的人员列表		
		function selExeUsers(obj,nodeId){
			var span=$(obj).siblings("span");
			
			var aryChk=$(":checkbox",span);
			var selectExecutors =[];  
			aryChk.each(function(){   
				var val=$(this).val();
			    var k=val.split("^");
				var userObj={};
				userObj.type=k[0];
				userObj.id=k[1];
				userObj.name=k[2];
				selectExecutors.push(userObj);    
			});    
		
			FlowUserDialog({selectUsers:selectExecutors,callback:function(types,objIds,objNames){
				if(objIds==null) return;
				var aryTmp=[];
				for(var i=0;i<objIds.length;i++){
					var check="<input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+types[i] +"^"+  objIds[i]+"^"+objNames[i] +"'/>&nbsp;"+objNames[i];
					aryTmp.push(check);
				}
				span.html(aryTmp.join(''));
			}});
		}
		
		function selectExeUsers(obj){
			var destTask=$("#destTask");
			if(destTask){
				$("#lastDestTaskId").val(destTask.val());
			}
			selExeUsers(obj,destTask.val());
		}
		//显示审批历史
		function showTaskOpinions(){
			var url="${ctx}/platform/bpm/taskOpinion/list.ht?runId=${processRun.runId}&isOpenDialog=1";
			url=url.getNewUrl();
			window.open(url,"taskOpinion","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}
		//更改
		function changeDestTask(sel){
			var nodeId=sel.value;
			if(typeof nodeId == 'undefined'){    //对象不是用原始JS的，而是通过Jquery获取的对象
				nodeId = sel.val();
			}
			if(typeof nodeId == 'undefined' || nodeId==null || nodeId==""){
				$('#jumpUserDiv').html("");
				$('#lastDestTaskId').val("");
				return;
			}
			$('#lastDestTaskId').val(nodeId);
			var url="${ctx}/platform/bpm/task/getTaskUsers.ht?taskId=${task.id}&nodeId="+nodeId;
			$.getJSON(url, function(dataJson){
				var data=eval(dataJson);
				var aryHtml=[];
				for(var i=0;i<data.length;i++){
				  var span="<input type='checkbox' name='" + nodeId + "_userId' checked='checked' value='"+data[i].type+"^"+data[i].executeId+"^"+data[i].executor+"'/>&nbsp;"+data[i].executor;
				  aryHtml.push(span);
				}
				$('#jumpUserDiv').html(aryHtml.join(''));
			});
			
		}
		
		function isTaskEnd(callBack){
			var url="${ctx}/platform/bpm/task/isTaskExsit.ht";
			var params={taskId:"${task.id}"};
			
			$.post(url,params,function(responseText){
				var obj=new com.hotent.form.ResultMessage(responseText);			
				if(obj.isSuccess()){
					callBack.apply(this);
				}
				else{
					$.ligerDialog.warn("当前任务已经完成或被终止","提示信息");
				}
			});
		}
		
		//转交待办
		function changeAssignee(){
			if(${isCanAssignee}){
				isTaskEnd(BpmTaskExeAssignDialog({taskId:taskId,callback:function(rtn){
					if(rtn){
						handJumpOrClose();
					}
				}
				}));
			}
			else{
				$.ligerDialog.warn("当前任务不能转办!","提示信息");
			}
		};
		
		
		function resizeIframe(){
			if($("#frameDetail").length==0) return;
			$("#frameDetail").load(function() { 
				$(this).height($(this).contents().height()+20); 
			}) ;
		}
		
		
		// 选择常用语
		function addComment(){
			var objContent=document.getElementById("voteContent");
			var selItem = $('#selTaskAppItem').val();
			jQuery.insertText(objContent,selItem);
		}
		
		function openForm(formUrl){
			var winArgs="dialogWidth=500px;dialogHeight=400px;help=0;status=0;scroll=0;center=1";
			var url=formUrl.getNewUrl();
			window.open(url,"",winArgs);
		}
		
		function reference(){
			var defId=${bpmDefinition.defId};
			
			var url="${ctx}/platform/bpm/processRun/getRefList.ht?defId=" +defId;
			
			var params="height=400,width=700,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
			window.open(url);
		}
		
		function openHelpDoc(fileId){
			var h=screen.availHeight-35;
			var w=screen.availWidth-5;
			var vars="top=0,left=0,height="+h+",width="+w+",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
			var showUrl = __ctx+"/platform/system/sysFile/get.ht?canReturn=1&fileId=" + fileId;
			window.open(showUrl,"myWindow",vars);	
		}
		
		//增加Web签章
		function addWebSigns(){
			AddSecSignFromServiceX();          //WebSignPlugin JS类     
		}
		
		//增加手写签章
		function addHangSigns(){
			AddSecHandSignNoPromptX();          //WebSignPlugin JS类     
		}
		
	</script>	
	<style type="text/css" media="print">
		.noprint{display:none;} 
		.printForm{display:"block";} 
	</style>
</head>
<body>
	<form id="frmWorkFlow"  method="post" >
		 <div class="panel">

		 		<div class="panel-top"> 
		            <!-- 悬浮工具栏实现的对象topNavWrapper 和 topNav 名称ID可以更改,但要和css的对象一致-->
		            <div id="topNavWrapper">
						<ul id="topNav">
						  <iframe  style="position:absolute; visibility:inherit; top:0px; left:0px; width:100%; height:70px; z-index:-1; border-width:0px;"></iframe>									
					      <div class="l-layout-header noprint" >
			            	任务审批处理--<b>${task.name}</b>--<i>[${bpmDefinition.subject}-V${bpmDefinition.versionNo}]</i>
				          </div>
				          <c:choose>
				            	<c:when test="${(empty task.executionId) && task.description=='15' }">
				            		<jsp:include page="incTaskNotify.jsp"></jsp:include>
				            	</c:when>
				            	<c:when test="${(empty task.executionId) && task.description=='38' }">
				            		<jsp:include page="incTaskTransTo.jsp"></jsp:include>
				            	</c:when>
				            	<c:otherwise>
				            		<jsp:include page="incToolBarNode.jsp"></jsp:include>
				            	</c:otherwise>
				          </c:choose>
						</ul>                      
					</div>
		         </div>
	            

				<div class="panel-body">
					<c:choose>
						<c:when test="${bpmNodeSet.isHidePath==0||isManage==1}">
						<div id="jumpDiv" class="noprint" style="display:none;" tipInfo="正在加载表单请稍候...">
						</div>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${bpmNodeSet.isHideOption==0 && task.description!='15' &&  task.description!='38'}">
							<div class="noprint">
								<jsp:include page="incTaskOpinion.jsp"></jsp:include>
							</div>
						</c:when>
					</c:choose>
					<div class="printForm" style="overflow: auto;">
							<c:choose>
								<c:when test="${isEmptyForm==true}">
									<div class="noForm">没有设置流程表单。</div>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${isExtForm}">
											<c:choose>
												<c:when test="${!empty detailUrl}">
													<iframe id="frameDetail" src="${detailUrl}" scrolling="no"  frameborder="no"  width="100%" height="100%"></iframe>
												</c:when>
												<c:otherwise>
													<div class="printForm" id="divExternalForm" formUrl="${form}"></div>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<div class="printForm" type="custform">
											${form}
											</div>
											<input type="hidden" name="formData" id="formData" />
										</c:otherwise>
									</c:choose>	
								</c:otherwise>
							</c:choose>
					</div>
					<input type="hidden" id="taskId" name="taskId" value="${task.id}"/> 
					<%--驳回和撤销投票为再次提交 --%>
					<c:choose>
						<c:when test="${processRun.status eq 5 or processRun.status eq 6}">
							<input type="hidden" id="voteAgree" name="voteAgree" value="34"/>	
						</c:when>
						<c:otherwise>
							<input type="hidden" id="voteAgree" name="voteAgree" value="1"/>
						</c:otherwise>
					</c:choose>
					<input type="hidden" id="back" name="back" value=""/>
					<input type="hidden" name="actDefId" value="${bpmDefinition.actDefId}"/>
					<input type="hidden" name="defId" value="${bpmDefinition.defId}"/>
					<input type="hidden" id="isManage" name="isManage" value="${isManage}"/>
					<input type="hidden" id="businessKey" name="businessKey" value="${processRun.businessKey}"/>
					<input type="hidden" id="startNode" name="startNode" value="${toBackNodeId}"/>
				</div>
         </div> 
    </form>
</body>
</html>