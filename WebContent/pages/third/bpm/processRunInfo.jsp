<%@ page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
	<%@include file="/commons/include/customForm.jsp" %>
	<title>流程明细</title>
	<f:js pre="js/lang/view/platform/bpm" ></f:js>
	<script type="text/javascript" src="${ctx}/js/util/form.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/CheckVersion.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
	<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
	<script type="text/javascript">
		var isExtForm=eval('${isExtForm}');
		
		var runId=${processRun.runId};
		
		$(function(){
			if(isExtForm){
				var formUrl=$('#divExternalForm').attr("formUrl");
				$('#divExternalForm').load(formUrl, function() {});
			}
			$(".taskopinion").each(function(){
				$(this).removeClass("taskopinion");
				var actInstId=$(this).attr("instanceId");
				$(this).load("${ctx}/platform/bpm/taskOpinion/listform.ht?actInstId="+actInstId);
			});
		});
		
	
		
		//显示审批历史
		function showProcessRunInfo(obj){
			var vars="height=600,width=800,status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
			var url = $(obj).attr("action");
			window.open(url,"",vars);
		};
		
		//催办
		function urge(id){
			ProcessUrgeDialog({actInstId : id});
		};
		//追回
		function recover(runId){
			FlowUtil.recover({runId:runId,backToStart:1,callback:function(){
				//location.reload();
				if(window.opener){
					try{
						window.opener.location.href=window.opener.location.href.getNewUrl();
					}
					catch(e){}
					window.close();
				}
			}});
		};
		//重新提交
		function executeTask(procInstId){
			 var url= "${ctx}/platform/bpm/task/toStart.ht?instanceId="+procInstId+"&voteArgee=34";
			 jQuery.openFullWindow(url);
		};
		
		//打印表单
		function printForm(runId){
			var url="${ctx}/platform/bpm/processRun/printForm.ht?runId="+runId;
			jQuery.openFullWindow(url);
		}

		//删除 
		function delByInstId(instanceId){
			var url="${ctx}/platform/bpm/processRun/delDialog.ht?instanceId=" + instanceId;
			var winArgs="dialogWidth=500px;dialogHeight=250px;help=0;status=0;scroll=0;center=1";
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,"",winArgs);
			if(rtn!=undefined){
				try{
					window.opener.location.href=window.opener.location.href.getNewUrl();
				}
				catch(e){};
				window.close();
			}
		};
		function onClose(obj){
			if(window.opener ){
				try{
					window.opener.location.href=window.opener.location.href.getNewUrl();
				}
				catch(e){}
			}
			window.close();
		};
		
		//转发
		function divert(){
			forward({runId:runId});
		}
		
		function forward(conf)
		{
			if(!conf) conf={};	
			var url=__ctx + '/platform/bpm/bpmProCopyto/forward.ht?runId=' + conf.runId;
			var dialogWidth=500;
			var dialogHeight=250;
			conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1,reload:true},conf);

			var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
				+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
			url=url.getNewUrl();
			return window.showModalDialog(url,"",winArgs);
		}
		
		
		//追回
		function redo(runId)
		{
			var url=  '${ctx}/platform/bpm/processRun/redoDialog.ht?runId=' + runId +'&backToStart=' + 0;
			var dialogWidth=500;
			var dialogHeight=300;
			var conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1,reload:true},conf);
			var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight +"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
			url=url.getNewUrl();
			var rtn=window.showModalDialog(url,"",winArgs);
			
		}
		
		function downloadToWord(runId){
			var form=$(".panel-body").html();
			var frm=new com.hotent.form.Form();
			frm.creatForm("bpmPreview", '${ctx}/platform/bpm/processRun/downloadToWord.ht');
			frm.addFormEl("form",form);
			frm.addFormEl("runId",runId);
			frm.submit();
			
		}
	</script>
</head>
<body>
      <div class="panel">
      <div class="hide-panel">
       	<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">流程明细--${processRun.subject}</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
						<c:if test="${isCanRedo or ((param.prePage ==\"myRequest\") and isCanRecover)}">
							<div class="group"><a href="###" onclick="redo(${processRun.runId})" class="link redo"><span></span>追回</a></div>
						</c:if>
						<c:if test="${isCanRecover and !(param.prePage ==\"myRequest\")}">
							<div class="l-bar-separator"></div>
							<div class="group">
								<a href="javascript:void(0);" onclick="recover(${processRun.runId})" class="link redo"><span></span>撤销</a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<a href="javascript:void(0);" onclick="urge(${processRun.actInstId})" class="link urge"><span></span>催办</a>
							</div>
						</c:if>
						<c:if test="${isFirst and (processRun.status==4 or processRun.status==5)}">
							<div class="l-bar-separator"></div>
							<div class="group">
								<a href="javascript:executeTask('${processRun.actInstId}')" class="link run"><span></span>重新提交</a>
							</div>
							<div class="l-bar-separator"></div>
							<div class="group">
								<a href="javascript:delByInstId(${processRun.actInstId})" class="link del"><span></span>删除</a>
							</div>
						</c:if>
						<c:if test="${isCopy}">
							<div class="l-bar-separator"></div>
							<div class="group">
								<a href="javascript:void(0);" class="link copy" onclick="checkVersion({type:false,defId:'${processRun.defId}',businessKey:'${processRun.businessKey}'});"><span></span>复制</a>
							</div>
						</c:if>
						<c:if test="${isFinishedDiver}">
							<div class="l-bar-separator"></div>
							<div class="group">
								<a href="javascript:void(0);" onclick="divert(${processRun.runId},${processRun.actInstId})" class="link goForward"><span></span>转发</a>
							</div>
						</c:if>
					 <div class="l-bar-separator"></div>
					 <div class="group"><a action="${ctx}/platform/bpm/processRun/get.ht?isOpenDialog=1&link=1&runId=${processRun.runId}" onclick="showProcessRunInfo(this)" class="link detail"><span></span>运行明细</a></div>
					 <div class="l-bar-separator"></div>
					 <div class="group"><a action="${ctx}/platform/bpm/processRun/processImage.ht?aprocess&isOpenDialog=1&link=1&runId=${processRun.runId}" onclick="showProcessRunInfo(this)" class="link flowDesign"><span></span>流程图</a></div>
					 <div class="l-bar-separator"></div>
					 <div class="group"><a action="${ctx}/platform/bpm/taskOpinion/list.ht?action=process&isOpenDialog=1&link=1&runId=${processRun.runId}" onclick="showProcessRunInfo(this)" class="link history"><span></span>审批历史</a></div>
					 <div class="l-bar-separator"></div>
					 <c:if test="${processRun.status==2}">
					 	<div class="group"><a action="${ctx}/platform/bpm/bpmProCopyto/getCopyUserByInstId.ht?isOpenDialog=1&link=1&runId=${processRun.runId}" onclick="showProcessRunInfo(this)" class="link copyTo"><span></span>抄送人</a></div>
					 	<div class="l-bar-separator"></div>
				 	</c:if>
				 	<c:if test="${isPrintForm}">
					    <a href="javascript:void(0);" onclick="printForm(${processRun.runId})" class="link print"><span></span>打印表单</a>
				  		 <div class="l-bar-separator"></div>
				   </c:if>
					 <div class="group"><a href="javascript:void(0);" onclick="downloadToWord(${processRun.runId})" class="link print"><span></span>导出成word文档</a></div>
					 <div class="l-bar-separator"></div>
				</div>
			</div>
		</div>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${isExtForm==true }">
					<div id="divExternalForm" formUrl="${form}"></div>
				</c:when>
				<c:otherwise>
					${form}
				</c:otherwise>
			</c:choose>
	   </div>
     </div> 
</body>
</html>