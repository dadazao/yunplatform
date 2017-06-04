<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的草稿</title>
<%@include file="/commons/include/get.jsp" %>
<script type="text/javascript">
	function checkFormChange(runId){
		var url="checkForm.ht?runId="+runId;
		$.post(url,function(result){
			if(result){
				$.ligerDialog.confirm('草稿表单版本发生变化时候加载旧版本数据?','提示信息',function(rtn){
					if(rtn){
						jQuery.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?runId='+runId)
					}else{
						jQuery.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?runId='+runId+'&isNewVersion=1')
					}
					this.close();
				})
			}else{
				jQuery.openFullWindow('${ctx}/platform/bpm/task/startFlowForm.ht?runId='+runId)
			}
		})
	}
	function copyDraft(runId){
		$.post("copyDraft.ht?runId="+runId,function(data){
			var obj=new com.hotent.form.ResultMessage(data);
			if(obj.isSuccess()){
				$.ligerDialog.success(obj.getMessage(),"提示信息",function(rtn){
					if(rtn){
						window.location.reload();
					}
				});
			}else{
				$.ligerDialog.error(obj.getMessage(),"提示信息");
			}
		});
	}
</script>
</head>
<body>
	<div class="panel">
	<div class="hide-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">我的草稿列表</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group"><a class="link search" id="btnSearch"><span></span>查询</a></div>
					<div class="l-bar-separator"></div>
					<div class="group"><a class="link del"  action="del.ht"><span></span>删除</a></div>
				</div>	
			</div>
			<div class="panel-search">
				<form id="searchForm" method="post" action="myForm.ht">
					<ul class="row">
					<li>
						<span class="label">流程定义名称:</span><input type="text" name="Q_processName_SL"  class="inputText" value="${param['Q_processName_SL']}"/>
					</li>
					<li>
						<span class="label">流程实例标题:</span><input type="text" name="Q_subject_SL"  class="inputText" value="${param['Q_subject_SL']}"/>
					</li>
					<div class="row_date">
					<li>
						<span class="label">创建时间 从:</span> <input  name="Q_begincreatetime_DL"  class="inputText date" value="${param['Q_begincreatetime_DL']}"/>
					</li>
					<li>
						<span class="label">至: </span><input  name="Q_endcreatetime_DG" class="inputText date" value="${param['Q_endcreatetime_DG']}">
					</li>
					</div>
					</ul>
				</form>
			</div>		
		</div>
		</div>
		<div class="panel-body">
		    	<c:set var="checkAll">
					<input type="checkbox" id="chkall"/>
				</c:set>
			    <display:table name="processRunList" id="processRunItem" requestURI="myForm.ht" sort="external" cellpadding="1" cellspacing="1" export="false"  class="table-grid">
					<display:column title="${checkAll}" media="html" style="width:30px;">
						  	<input type="checkbox" class="pk" name="runId" value="${processRunItem.runId}">
					</display:column>
					<display:column property="subject" title="流程实例标题"  sortable="true" sortName="subject" style="text-align:left"></display:column>
					<display:column property="processName" title="流程定义名称" sortable="true" sortName="processName" style="text-align:left"></display:column>
					<display:column property="creator" title="创建人" sortable="true" sortName="creator" style="text-align:left"></display:column>
					<display:column  title="创建时间" sortable="true" sortName="createtime">
						<fmt:formatDate value="${processRunItem.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</display:column>
					<display:column title="管理" media="html" style="width:50px;text-align:center" class="rowOps">
						<f:a alias="delRun" href="delDraft.ht?runId=${processRunItem.runId}" css="link del">删除</f:a>
						<a href="#" onclick="checkFormChange('${processRunItem.runId}')" class="link run">启动流程</a>
						<c:if test="${processRunItem.formDefId!=0}">
							<a href="#" onclick="copyDraft('${processRunItem.runId}')" class="link copy">复制</a>
						</c:if>
					</display:column>
				</display:table>
				<hotent:paging tableId="processRunItem"/>
			
		</div><!-- end of panel-body -->				
	</div> <!-- end of panel -->
</body>
</html>


