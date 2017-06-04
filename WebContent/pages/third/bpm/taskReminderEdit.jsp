<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>任务催办设置</title>
<base target="_self" />
<%@include file="/commons/include/form.jsp" %>
<%-- <link href="${ctx}/styles/default/css/jquery.qtip.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/codemirror/lib/codemirror.css" > --%>
<f:link href="jquery.qtip.css"></f:link>
<f:link href="codemirror/lib/codemirror.css"></f:link>
<script type="text/javascript" src="${ctx}/js/hotent/displaytag.js" ></script>
<script type="text/javascript" src="${ctx}/servlet/ValidJs?form=taskReminder"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/TemplateDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/ScriptDialog.js" ></script>
<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor_remind.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/jquery.qtip.js" ></script>
<script type="text/javascript" src="${ctx}/js/codemirror/lib/codemirror.js"></script>
<script type="text/javascript" src="${ctx}/js/codemirror/lib/util/matchbrackets.js"></script>
<script type="text/javascript" src="${ctx}/js/codemirror/mode/groovy/groovy.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<style>
 .sub{ display: none;}
 .condExp-control{
 	width: 98%;
 	padding: 2px;
 	margin:2px;
 	border: solid 1px #A8CFEB;
 }
 .condExp-control [name='flowOperate']{
 	width:65px;
 }
 .condExp-editor{
 	width: 540px;
 	padding: 2px;
 	margin:2px;
 	height: 100px;
 	border: solid 1px #A8CFEB;
 	overflow: auto;
 }
 .condExp-editor-input{
 	width:100%;
 	height: 100px;
 }
 
 .script-control{
 	width: 98%;
 	padding: 2px;
 	margin:2px;
 	border: solid 1px #A8CFEB;
 }
 .script-editor{
	 width: 98%;
 	padding: 2px;
 	margin:2px;
 	height: 100px;
 	border: solid 1px #A8CFEB;
 	overflow: auto;
 }
  .script-editor-input{
 	width:100%;
 	height: 100px;
 }
 .send-msg-tr{
 	display:none;
 }
 .choose-assigner{
 	display:none;
 }
 .day-input{
 	width:40px;
 }
</style>
<script type="text/javascript">        
		function showRequest(formData, jqForm, options) { 
			return true;
		} 
		//CodeMirror Editor
		var condExpScriptEditor=null,
			actionScriptEditor=null,
			curTime = '${taskReminder.times}';
		
		$(function() {
			$("input.day-input").focus(function(){
				$(this).select();
			});
			//total page layout
			$("#reminder-layout").ligerLayout({
				rightWidth:210
			});
			//是否发送催办信息的checkbox
			$("#needSendMsg").change(function(){
				var me = $(this),
					sendMsg = me.attr("checked");
				
				if(sendMsg){
					$(".send-msg-tr").show();
				}
				else{
					$("select[name='times']").val(0);
					$(".send-msg-tr").hide();
				}
			});
			if(curTime>0){
				$("#needSendMsg").attr("checked","checked").trigger("change");
			}
			//TaskReminder form Edit Layout
			$("#reminder-div-tab").ligerTab({
			});
			//reminder action change handle
			change();
			//save reminder
			$("a.save").click(save);
			//new reminder
			$("a.add").click(add);
			//ckeditor Editor
			editorMail=ckeditor('mailContent');
			editorMsg=ckeditor('msgContent');	
			
			setTimeout(function(){
				var height=$("#condExp").height();
				condExpScriptEditor = CodeMirror.fromTextArea(document.getElementById("condExp"), {
					mode: "text/x-groovy",
			        lineNumbers: true,
			        matchBrackets: true
			    });
				condExpScriptEditor.setSize(null,height);
			},0);
			setTimeout(function(){
				var height=$("#script").height();
				actionScriptEditor = CodeMirror.fromTextArea(document.getElementById("script"), {
					mode: "text/x-groovy",
			        lineNumbers: true,
			        matchBrackets: true
			    });
				actionScriptEditor.setSize(null,height);
			},0);
			
			$("#reminders-list-table tbody tr").click(function(){
				var id=$(this).find("input.pk").val();
				url=__ctx + "/platform/bpm/taskReminder/edit.ht?actDefId=${actDefId}&nodeId=${nodeId}&id=" + id;
				document.getElementById('goLocation').href = url;
				document.getElementById('goLocation').click();
			});
		});
		/**
		* Action Change handler
		*/
		function change(){
			var s= $("#action").val();
			$(".sub").hide();
			$(".choose-assigner").hide();
			if(s==7){//选择执行脚本
				$(".sub").show();
				if(actionScriptEditor){
					actionScriptEditor.refresh();
				}
			}
			if(s==5){//选择交办
				$(".choose-assigner").show();
			}
		}
		
		/**
		* Select Template
		*/
		function slectTemplate(txtId,isText){
			var objcondExpCode=document.getElementById(txtId);
		    TemplateDialog({isText:isText,callback:function(content){
		    	if(isText)
					jQuery.insertText(objcondExpCode,content);
		    	else{		    		
		    		CKEDITOR.instances[txtId].setData(content);
		    	}				
			}});
		};		
		
		/**
		* 添加
		*/
		function add(){
			url=__ctx + "/platform/bpm/taskReminder/edit.ht?actDefId=${actDefId}&nodeId=${nodeId}&id=0";
			document.getElementById('goLocation').href = url;
			document.getElementById('goLocation').click();
		}
		/**
		* 保存
		*/
		function save(){
			condExpScriptEditor.save();
			actionScriptEditor.save();
			var name=$("#name").val();
			if(!name){
				$.ligerDialog.warn("请输入任务任务催办名称","提示信息");
				return;
			}
			var ctime=getTotalMinute($("#completeTr"));
			var stime=getTotalMinute($("#startTr"));
			//每次时间间隔*催办次数。
			var etime=getTotalMinute($("#endTr")) * ( parseInt($("#times").val())-1);
			if(ctime<stime+etime){				
				$.ligerDialog.warn('办结时间不能比催办时间短',"提示信息");
				return;
			}
			$(".ckeditor-editor").each(function(){
				$(this).val(CKEDITOR.instances[$(this).attr('name')].getData());
			});
			 var rtn=$("#taskReminderForm").valid();
	   		 if(!rtn) return;
			 var url=__ctx+ "/platform/bpm/taskReminder/save.ht";
	   		 var para=$('#taskReminderForm').serialize();
	   		 $.post(url,para,showResult);
		}
		function showResult(responseText){
			var obj=new com.hotent.form.ResultMessage(responseText);
			if(!obj.isSuccess()){
				$.ligerDialog.err('出错信息',"任务催办提醒失败",obj.getMessage());
				return;
			}else{
				$.ligerDialog.confirm(obj.getMessage()+',是否继续操作?','提示信息',function(rtn){
					if(!rtn){
						window.close();
					}else{
						url=__ctx + "/platform/bpm/taskReminder/edit.ht?actDefId=${actDefId}&nodeId=${nodeId}&id=0";
						document.getElementById('goLocation').href = url;
						document.getElementById('goLocation').click();
					}
				});
			}
		}
		
		/**
		* 
		*/
		function scriptSelectScript(obj){
			ScriptDialog({callback:function(script){
				var pos=scriptScriptEditor.getCursor();
				scriptScriptEditor.replaceRange(script,pos);
			}});
		}
		/**
		*
		*/
		function condExpSelectScript(obj){
			ScriptDialog({callback:function(script){
				var pos=condExpScriptEditor.getCursor();
				condExpScriptEditor.replaceRange(script,pos);
			}});
		}
		
		function getTotalMinute(e){
			var t=0;
			$(e).find(".dayInput").each(function(){
				t+= parseInt(3600* this.value);
			});
			$(e).find(".hourInput").each(function(){
				t+=parseInt(60* this.value);
			});			
			$(e).find(".minuteInput").each(function(){
				t+=parseInt(this.value);
			});
			return t;
		}
		
		function constructFlowOperate(type){
			var select = $("select[name='flowOperate']");
			select.html("");
			type=type.toLowerCase();
			switch(type){
			case 'int':
			case 'number':
			case 'date':
				var eq=$("<option value='eq'>等于</option>");
				var ne=$("<option value='ne'>不等于</option>");
				var gt=$("<option value='gt'>大于</option>");
				var lt=$("<option value='lt'>小于</option>");
				select.append(eq);
				select.append(ne);
				select.append(gt);
				select.append(lt);
				break;
			case 'varchar':
				var eq=$("<option value='eq'>等于</option>");
				var ne=$("<option value='ne'>不等于</option>");
				select.append(eq);
				select.append(ne);
				break;
			}
		}
		
		function dateTimePicker(){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});
				$(this).blur();
		}
		/**
		* Select a different flow var
		* @params obj,target dom object
		*/
		function selectFlowVar(obj,type){
			var obj=$(obj);
			obj.qtip("destroy");
			if(type==1){
				var fname=obj.val();
				if(!fname){
					return;
				}
				var ftype = obj.find("option:selected").attr("ftype");
				constructFlowOperate(ftype);
				
				var valueInput=$("<input name='flowValue'/>");
				var oldValueInput=$("input[name='flowValue']");
				
				if('date'==ftype.toLowerCase()){
					valueInput.addClass("date");
					valueInput.focus(dateTimePicker);
				}
				oldValueInput.replaceWith(valueInput);
				
			}else if(type==2){
				var fname = obj.val();
				var pos=actionScriptEditor.getCursor();
				actionScriptEditor.replaceRange(fname,pos);
			}
			var opt = obj.find("option:selected")
			var fname=opt.attr("fname");
			var fdesc=opt.attr("fdesc");
			var ftype=opt.attr("ftype");
			ftype=dbTypeToGroovyType(ftype);
			var content=""
				+"<table class='table-detail'>"
					+"<tr>"
						+"<th>名称</th>"
						+"<td>"+fname+"</td>"
					+"</tr>"
					+"<tr>"
						+"<th>注释</th>"
						+"<td>"+fdesc+"</td>"
					+"</tr>"
					+"<tr>"
						+"<th>类型</th>"
						+"<td>"+ftype+"</td>"
					+"</tr>"
				+"</table>";
			obj.qtip({
				content:content
			});
		}
		
		/**
		* Generate Express from Gui setting
		* @params obj,target dom object
		*/
		function generateExpress(obj){
			var div = $(obj).closest("div.condExp-control");
			var flowVar=div.find("select[name='flowVar']").find("option:selected");
			var flowVarName=flowVar.val();
			var flowVarType=flowVar.attr("ftype");
			var flowVarOperate=div.find("select[name='flowOperate']").find("option:selected").val();
			var flowVarValue=div.find("input[name='flowValue']").val();
			
			if(!flowVarName){
				$.ligerDialog.warn("请选择流程变量！");
				return;
			}
			if(!flowVarOperate){
				$.ligerDialog.warn("请选择变量操作类型变量！");
				return;
			}
			if(!flowVarValue){
				$.ligerDialog.warn("请输入流程变量值！");
				return;
			}
			var exp=null;
			flowVarType=flowVarType.toLowerCase();
			switch(flowVarType){
			case 'int':
			case 'number':
				switch(flowVarOperate){
				case "eq":
					exp = flowVarName+" == "+flowVarValue;
					break;
				case "ne":
					exp = flowVarName+" != "+flowVarValue;
					break;
				case "gt":
					exp = flowVarName+" > "+flowVarValue;
					break;
				case "ge":
					exp = flowVarName+" >= "+flowVarValue;
					break;
				case "lt":
					exp = flowVarName+" < "+flowVarValue;
					break;
				case "le":
					exp = flowVarName+" <= "+flowVarValue;
					break;
				}
				break;
			case 'date':
				flowVarValue = "com.hotent.core.util.TimeUtil.convertString(\""+flowVarValue+'\","yyyy-MM-dd HH:mm:ss")';
				switch(flowVarOperate){
				case "eq":
					exp = flowVarName+".compareTo("+flowVarValue+") == 0";
					break;
				case "ne":
					exp = flowVarName+".compareTo("+flowVarValue+") !=0 ";
					break;
				case "gt":
					exp = flowVarName+".compareTo("+flowVarValue+") > 0";
					break;
				case "lt":
					exp = flowVarName+".compareTo("+flowVarValue+") < 0";
					break;
				}
				break;
			case 'varchar':
				switch(flowVarOperate){
				case "eq":
					exp = flowVarName+".equals(\""+flowVarValue+"\")";
					break;
				case "ne":
					exp =" !"+flowVarName+".equals(\""+flowVarValue+"\")";
					break;
				}
				break;
			}
			var pos=condExpScriptEditor.getCursor();
			condExpScriptEditor.replaceRange(exp,pos);
		}
		
		/**
		* 数据库类型到Groovy类型的转换
		* @params type relation data type
		* @return groovy type
		*/
		function dbTypeToGroovyType(type){
			type=type.toLowerCase();
			var t;
			switch(type){
			case 'int':
				t='int';
				break;
			case 'number':
				t='double';
				break;
			case 'date':
				t='java.lang.Date';
				break;
			case 'varchar':
			case 'clob':
				t='java.lang.String';
				break;
			default:
				t=type;		
			}
			return t;
		};
		//选择交办人
		function chooseAssigner(){
			UserDialog({
				isSingle:true,
				callback:function(userId,fullname){
					if(userId=='' || userId==null || userId==undefined) return;
					$("input[name='assignerId']").val(userId);
					$("input[name='assignerName']").val(fullname);
				}
			});
		};
	</script>
   </head>
<body>
<div class="panel">
		<div class="panel-top">
				<div class="tbar-title">
					<span class="tbar-label">编辑任务节点催办时间设置</span>
				</div>
				<div class="panel-toolbar">
					<div class="toolBar">
						<div class="group"><a class="link add" id="btnAdd"><span></span>增加</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link save" id="dataFormSave" href="#"><span></span>保存</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link del" onclick="javascript:window.close();"><span></span>关闭</a></div>
					</div>
				</div>
		</div>
		<div class="panel-body">
			<div class="reminder-layout" id="reminder-layout">
				<div class="reminder-edit" position="center">
					<div style="height:570px;overflow: auto;">
					<form id="taskReminderForm" method="post" action="save.ht">
						<div class="reminder-div" >
							<div class="reminder-div-tab" id="reminder-div-tab">
							<div class="reminder-div-base" title="催办基本信息设置">
								<div class="panel-detail">
								<fieldset class="fieldset-detail">
									<legend>
										<span>到期条件设置</span>
									</legend>
									<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<th width="120px">名称:</th>
												<td >
													<input id="name" name="name" value="${taskReminder.name}" class="inputText" />
												</td>
<!-- 												<th >默认:</th> -->
<!-- 												<td > -->
<%-- 													<input id="isDefaultTrue" name="isDefault" type="radio" value="1"  <c:if test="${taskReminder.isDefault==1 }">checked="checked"</c:if> /> --%>
<!-- 													<label for="isDefaultTrue">是</label>				 -->
<%-- 													<input id="isDefaultFalse" name="isDefault" type="radio" value="0" <c:if test="${taskReminder.isDefault!=1 }">checked="checked"</c:if> /> --%>
<!-- 													<label for="isDefaultFalse">否</label> -->
<!-- 												</td> -->
												<th width="100px">当前节点:</th>
												<td><input type="text" value="${nodeId}" disabled="disabled"/></td>
											</tr>
											<tr>
												<th>相对节点:</th>
												<td>
													<select name="relativeNodeId">
														<c:forEach items="${nodes}" var="node">
																<option value="${node.nodeId}" <c:if test="${node.nodeId==taskReminder.relativeNodeId}">selected="selected"</c:if>>${node.nodeName}</option>
														</c:forEach>
													</select>
												</td>
												<th>相对动作:</th>
												<td>
													<select name="relativeNodeType">
														<option value="0" >创建</option>
														<option value="1" <c:if test="${taskReminder.relativeNodeType==1}">selected="selected"</c:if>>完成</option>
													</select>
												</td>
											</tr>
											<tr>
												<th >相对时间: </th>
												<td id="completeTr">
												    <input class="day-input" type="text" name="completeTimeDay" value="${completeTimeDay}"/>
													<span>天</span>
													<select id="completeTimeHour" class="hourInput" name="completeTimeHour">
														<c:forEach var="i" begin="0" end="23" step="1">
															<option value="${i}" <c:if test="${completeTimeHour==i}">selected="selected"</c:if>>${i}小时</option>
														</c:forEach>
													</select>
													<select id="completeTimeMinute" class="minuteInput" name="completeTimeMinute">
														<c:forEach var="i" begin="0" end="4" step="1">
															<option value="${i}" <c:if test="${completeTimeMinute==i}">selected="selected"</c:if>>${i}分钟</option>
														</c:forEach>
														<c:forEach var="i" begin="5" end="59" step="5">
															<option value="${i}" <c:if test="${completeTimeMinute==i}">selected="selected"</c:if>>${i}分钟</option>
														</c:forEach>
													</select>					
										    	</td>
										    	<th>相对时间类型:</th>
												<td>
													<select name="relativeTimeType">
														<option value="0">工作日</option>
														<option value="1" <c:if test="${taskReminder.relativeTimeType==1}">selected="selected"</c:if>>日历日</option>
													</select>
												</td>
											</tr>
											<tr>
												<th >
													<a href="#" class="link tipinfo"><span style="z-index: 100;text-align: left;">条件表达要求是返回Boolean值的脚本。返回true,表示满足条件；返回talse,表示条件不满足。如果表达式为空，将视为返回true。</span></a>
													条件表达式:
												</th>
												<td colspan="3">
													<div class="condExp-control">
														<a href="#"  class="link var" title="常用脚本" onclick="condExpSelectScript(this)">常用脚本</a>
<%-- 													<f:flowVar defId="${defId}"></f:flowVar> --%>
														<span class="green">表单变量</span>		
														<select name="flowVar" onchange="selectFlowVar(this,1)">
															<option value="">请选择...</option>
															<optgroup label="表单变量"></optgroup>
															<c:forEach items="${flowVars}" var="flowVar">
																<option class="flowvar-item" value="${flowVar.fieldName}"  fname="${flowVar.fieldName}" fdesc="${flowVar.fieldDesc}" ftype="${flowVar.fieldType}">${flowVar.fieldDesc}</option>
															</c:forEach>
															<c:if test="${not empty defVars}">
																<optgroup label="自定义变量"></optgroup>
																<c:forEach items="${defVars}" var="defVars">
																	<option class="flowvar-item" value="${defVars.varKey}"  fname="${defVars.varKey}" fdesc="${defVars.varName}" ftype="${defVars.varDataType}">${defVars.varName}</option>
																</c:forEach>
															</c:if>
														</select>
														<span class="green">比较</span>
														<select name="flowOperate" >
														</select>
														<span class="green">值</span>
														<input name="flowValue"/>
														<a onclick="generateExpress(this)" href="#" class="button">
															<span>生成</span>
														</a>
													</div>
													<div class="condExp-editor">
														<textarea id="condExp" name="condExp" class="condExp-editor-input">${taskReminder.condExp}</textarea>
													</div> 
												</td>
											</tr>
											</table>
										</fieldset>
										<fieldset class="fieldset-detail">
											<legend>
												<span>到期动作设置</span>
											</legend>
											<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<th width="100">执行动作: </th>
												<td colspan="3">
													<select id="action" onchange="change()" name="action">
														<option value="0" <c:if test="${taskReminder.action==0}">selected="selected"</c:if>>无动作</option>
														<option value="1" <c:if test="${taskReminder.action==1}">selected="selected"</c:if>>执行同意操作</option>
														<option value="2" <c:if test="${taskReminder.action==2}">selected="selected"</c:if>>执行反对操作</option>
														<option value="3" <c:if test="${taskReminder.action==3}">selected="selected"</c:if>>执行驳回操作</option>
														<option value="4" <c:if test="${taskReminder.action==4}">selected="selected"</c:if>>执行驳回到发起人操作</option>
														<option value="5" <c:if test="${taskReminder.action==5}">selected="selected"</c:if>>执行交办操作</option>
														<option value="6" <c:if test="${taskReminder.action==6}">selected="selected"</c:if>>结束该流程</option>
														<option value="7" <c:if test="${taskReminder.action==7}">selected="selected"</c:if>>调用指定方法</option>
													</select>
												</td>
											</tr>	
											<tr class="sub" width="100">
												<th >执行脚本: </th>
												<td colspan="3">
													<div class="condExp-control">
														<a href="#"  class="link var" title="常用脚本" onclick="scriptSelectScript(this)">常用脚本</a>
														<span class="green">表单变量:</span>
<%-- 														<f:flowVar defId="${defId}" controlName="selFlowVar"></f:flowVar> --%>
														<select name="flowVar" onchange="selectFlowVar(this,2)">
															<option value="">请选择...</option>
															<c:forEach items="${flowVars}" var="flowVar">
																<option class="flowvar-item" value="${flowVar.fieldName}" fname="${flowVar.fieldName}" fdesc="${flowVar.fieldDesc}" ftype="${flowVar.fieldType}">${flowVar.fieldDesc}</option>
															</c:forEach>
														</select>			
													</div>
													<div class="script-editor">
														<textarea rows="6" cols="60" id="script" name="script" class="script-editor-input">${taskReminder.script}</textarea>
													</div> 
												</td>
											</tr>
											<tr class="choose-assigner" width="100">
												<th>指定交办人员:</th>
												<td colspan="3">
													<input type="hidden" name="assignerId" value="${taskReminder.assignerId}"/>
													<input type="text" name="assignerName" readonly="readonly" value="${taskReminder.assignerName}"/>
													<a href="javascript:;" onclick="chooseAssigner()" class="button">
														<span>选择</span>
													</a>
												</td>
											</tr>										
											</table>
										</fieldset>	
										<fieldset class="fieldset-detail">
											<legend>
												<span>发送催办消息设置</span>
											</legend>
											<table class="table-detail" cellpadding="0" cellspacing="0" border="0">										
											<tr>
												<th width="100">
												  发送催办信息:
												</th>
												<td colspan="3">
													<label><input type="checkbox" id="needSendMsg"/>发送</label>
												</td>
											</tr>
											<tr class="send-msg-tr">
												<th>开始发送时间:</th>
												<td id="startTr" colspan="3">
													<input class="day-input" type="text" name="reminderStartDay" value="${reminderStartDay}"/>
													<span>天</span>
													<select id="reminderStartHour" class="hourInput" name="reminderStartHour">
													<c:forEach var="i" begin="0" end="23" step="1">
														<option value="${i}" <c:if test="${reminderStartHour==i}">selected="selected"</c:if>>${i}小时</option>
													</c:forEach>
													</select>
													<select id="reminderStartMinute" class="minuteInput" name="reminderStartMinute">
														<c:forEach var="i" begin="0" end="4" step="1">
															<option value="${i}" <c:if test="${reminderStartMinute==i}">selected="selected"</c:if>>${i}分钟</option>
														</c:forEach>
														<c:forEach var="i" begin="5" end="59" step="5">
															<option value="${i}" <c:if test="${reminderStartMinute==i}">selected="selected"</c:if>>${i}分钟</option>
														</c:forEach>
													</select>							
												</td>
											</tr>
											<tr class="send-msg-tr">
												<th><a href="#" class="tipinfo"><span>每过多长的时间发送催办信息。 </span></a>发送的间隔:</th>
												<td id="endTr">
													<input class="day-input" type="text" name="reminderEndDay" value="${reminderEndDay}"/>
													<span>天</span>
													<select id="reminderEndHour" class="hourInput" name="reminderEndHour">
														<c:forEach var="i" begin="0" end="23" step="1">
															<option value="${i}" <c:if test="${reminderEndHour==i}">selected="selected"</c:if>>${i}小时</option>
														</c:forEach>
													</select>
													<select id="reminderEndMinute" class="minuteInput" name="reminderEndMinute">
														<c:forEach var="i" begin="1" end="4" step="1">
															<option value="${i}" <c:if test="${reminderEndMinute==i}">selected="selected"</c:if>>${i}分钟</option>
														</c:forEach>
														<c:forEach var="i" begin="5" end="59" step="5">
															<option value="${i}" <c:if test="${reminderEndMinute==i}">selected="selected"</c:if>>${i}分钟</option>
														</c:forEach>
													</select>
												</td>
												<th>发送信息次数: </th>
												<td>
													<select name="times" >
														<c:forEach var="i" begin="0" end="10" step="1">
															<option value="${i}" <c:if test="${taskReminder.times==i}">selected="selected"</c:if>>${i}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
										</table>
										</fieldset>
								</div>
							</div>
							    <div class="reminder-div-msg-mail" title="邮件内容">
							    	<div class="panel-detail">
									    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
									    	<tr>
									     		<th width="60" >邮件内容: </th>
												<td>
													<div>
														<a href="#"  class="link var" title="选择模板内容" onclick="slectTemplate('mailContent',false)">选择模板内容</a>
													</div>
													<textarea id="mailContent"  name="mailContent" class="ckeditor-editor" rows="20" cols="50">${taskReminder.mailContent}</textarea>
												</td>
											</tr>
										</table>
									</div>
							    </div>
							    <div class="reminder-div-msg-inter" title="站内消息内容">
						     		<div class="panel-detail">
									    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
										    <tr>
										     	<th width="60" >站内消息内容: </th>
												<td>
													<div>
														<a href="#"  class="link var" title="选择模板内容" onclick="slectTemplate('msgContent',false)">选择模板内容</a>
													</div>
													<textarea id="msgContent"  name="msgContent" class="ckeditor-editor" rows="12" cols="50">${taskReminder.msgContent}</textarea>
												</td>
												</tr>
										</table>
									</div>
							    </div>
							    <div class="reminder-div-msg-sms" title="手机短信内容">
							    	<div class="panel-detail">
									    <table class="table-detail" cellpadding="0" cellspacing="0" border="0">
									    	<tr >
										     	<th width="60" >手机短信内容: </th>
												<td>
												<div>
													<a href="#"  class="link var" title="选择模板内容" onclick="slectTemplate('smsContent',true)">选择模板内容</a>
												</div>
												<textarea id="smsContent" name="smsContent" rows="12" cols="50">${taskReminder.smsContent}</textarea>
												</td>
											</tr>
										</table>
									</div>
						     	</div>
							</div>
						</div>
						<div>
							<input type="hidden" name="taskDueId" value="${taskReminder.taskDueId}" />
							<input type="hidden" name="actDefId" value="${actDefId}" />
							<input type="hidden" name="nodeId" value="${nodeId}" />
							<input type="hidden" id="defId" name="defId" value="${defId}" />
						</div>
					</form>
					</div>
				</div>
				<div class="reminders-list" position="right">
					<div class="reminders-div">
						<table class="table-grid" id="reminders-list-table">
							<thead>
								<tr>
									<th>名称</th>
									<th>管理</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${taskReminders }" var="reminder" varStatus="status">
									<tr <c:if test="${status.index%2==0 }">class="odd"</c:if><c:if test="${status.index%2==1 }">class="even"</c:if>>
										<td>
											<span>${reminder.name }</span>
											<input class="pk" type="hidden" value="${reminder.taskDueId}"/>
										</td>
										<td>
											<a class="link del" href="del.ht?taskDueId=${reminder.taskDueId}">删除</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		 <a href="" id="goLocation" style="display:none;"></a>
	</div>
</body>
</html>