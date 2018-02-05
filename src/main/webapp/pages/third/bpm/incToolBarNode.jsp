<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<script type="text/javascript">
	function beforeClick(operatorType){<c:if test="${not empty mapButton.button}">
		switch(operatorType){<c:forEach items="${mapButton.button }" var="btn"  ><c:if test="${not empty btn.prevscript}">
				case ${btn.operatortype}:
				${btn.prevscript}
				break;</c:if></c:forEach>
			}</c:if>
	}
	
	function afterClick(operatorType){<c:if test="${not empty mapButton.button}">
		switch(operatorType){<c:forEach items="${mapButton.button }" var="btn" ><c:if test="${not empty btn.afterscript}">
			case ${btn.operatortype}:
				${btn.afterscript}
				break;</c:if></c:forEach>
			}</c:if>
	}
	
</script>
<div class="noprint">
	<div class="panel-toolbar">
		<div class="toolBar">
			<c:choose>
				<c:when test="${ isManage==0 }">
						<c:choose>
						<c:when test="${empty mapButton }">
							<c:if test="${isSignTask && isAllowDirectExecute}">
								<div class="group">
									特权：<input type="checkbox" value="1" id="chkDirectComplete"><label for="chkDirectComplete"><span></span>直接结束</label>
								</div>
							</c:if>
						<div class="group"><a id="btnAgree" class="link agree"><span></span>同意</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${isSignTask==true}">
							<div class="group"><a id="btnNotAgree" class="link notAgree"><span></span>反对</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a id="btnAbandon" class="link abandon"><span></span>弃权</a></div>
							<div class="l-bar-separator"></div>
							<c:if test="${isAllowRetoactive==true}">
								<div class="group"><a class="link flowDesign" onclick="showAddSignWindow()"><span></span>补签</a></div>
								<div class="l-bar-separator"></div>
							</c:if>
						</c:if>
						<div class="group"><a id="btnSave" class="link save" ><span></span>保存表单</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${isCanAssignee}">
							<div class="group"><a id="btnForward" class="link goForward " onclick="changeAssignee()"><span></span>交办</a></div>
							<div class="l-bar-separator"></div>
						</c:if>
						<c:if test="${isCanBack}">
						<div class="group"><a id="btnReject" class="link reject" ><span></span>驳回</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a id="btnRejectToStart" class="link rejectToStart" ><span></span>驳回到发起人</a></div>
						<div class="l-bar-separator"></div>
						</c:if>
						
						<div class="group"><a class="link setting" onclick="showTaskUserDlg()"><span></span>流程图</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link search" onclick="showTaskOpinions()"><span></span>审批历史</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link sendMessage" onclick="showTaskCommunication()"><span></span>沟通</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${isSignTask==false && task.description!='39'}">
							<div class="group"><a class="link switchuser" onclick="showTaskTransTo()"><span></span>加签</a></div>
							<div class="l-bar-separator"></div>
						</c:if>
						<div class="group"><a class="link print" onclick="window.print();"><span></span>打印</a></div>
						
						<c:if test="${isExtForm}">
							<c:choose>
								<c:when test="${!empty detailUrl && !empty form}">
									<div class="l-bar-separator"></div>
									<div class="group"><a class="link edit" onclick="openForm('${form}')" ><span></span>编辑表单</a></div>
								</c:when>
							</c:choose>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty mapButton.button}">
							<c:if test="${isSignTask && isAllowDirectExecute}">
								<div class="group">
									特权：<input type="checkbox" value="1" id="chkDirectComplete"><label for="chkDirectComplete">直接结束</label>
								</div>
							</c:if>
							<c:forEach items="${mapButton.button }" var="btn"  varStatus="status">
								<c:choose>
									<c:when test="${btn.operatortype==1 }">
										<!--  同意-->
										<div class="group"><a id="btnAgree" class="link agree"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==2 }">
										<!-- 反对-->
										<div class="group"><a id="btnNotAgree" class="link notAgree"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==3 }">
										<!--弃权-->
										<c:if test="${isSignTask==true}">
										<div class="group"><a id="btnAbandon" class="link abandon"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
										</c:if>
									</c:when>
									
									<c:when test="${btn.operatortype==4 }">
										<!--驳回-->
										<c:if test="${isCanBack}">
										<div class="group"><a id="btnReject" class="link reject"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
										</c:if>
									</c:when>
									<c:when test="${btn.operatortype==5 }">
										<!--驳回到发起人-->
										<c:if test="${isCanBack && toBackNodeId!=task.taskDefinitionKey}">
											<div class="group"><a id="btnRejectToStart" class="link rejectToStart"><span></span>${btn.btnname }</a></div>
											<div class="l-bar-separator"></div>
										</c:if>
									</c:when>
									<c:when test="${btn.operatortype==6 && isCanAssignee}">
										<!--交办-->
										<div class="group"><a id="btnForward" class="link goForward" onclick="changeAssignee()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==7 }">
										<c:if test="${isSignTask==true}">
										<!--补签-->
										<c:if test="${isAllowRetoactive==true}">
											<div class="group"><a class="link flowDesign" onclick="showAddSignWindow()"><span></span>${btn.btnname }</a></div>
											<div class="l-bar-separator"></div>
										</c:if>
										</c:if>
									</c:when>
									<c:when test="${btn.operatortype==8 }">
										<!--保存表单-->
										<div class="group"><a id="btnSave" class="link save" ><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==9 }">
										<!--流程图-->
										<div class="group"><a class="link setting" onclick="showTaskUserDlg()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==10 }">
										<!--打印-->
										<div class="group"><a class="link print" onclick="window.print();"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==11 }">
										<!--审批历史-->
										<div class="group"><a class="link history" onclick="showTaskOpinions()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==14 }">
										<!--Web签章-->
										<div class="group"><a class="link addWebSigns" onclick="addWebSigns()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==15 }">
										<!--手写签章-->
										<div class="group"><a class="link addHangSigns" onclick="addHangSigns()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==16 }">
										<!--沟通-->
										<div class="group"><a class="link sendMessage" onclick="showTaskCommunication()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
									<c:when test="${btn.operatortype==17 && task.description!='39'}">
										<!--加签-->
										<div class="group"><a class="link switchuser" onclick="showTaskTransTo()"><span></span>${btn.btnname }</a></div>
										<div class="l-bar-separator"></div>
									</c:when>
								</c:choose>
							</c:forEach>
						</c:if>
						<c:if test="${isExtForm}">
							<c:choose>
								<c:when test="${!empty detailUrl && !empty form}">
									<div class="l-bar-separator"></div>
									<div class="group"><a class="link edit" onclick="openForm('${form}')" ><span></span>编辑表单</a></div>
								</c:when>
							</c:choose>
						</c:if>
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
						<c:if test="${isSignTask && isAllowDirectExecute}">
								<div class="group">
									特权：<input type="checkbox" value="1" id="chkDirectComplete"><label for="chkDirectComplete">直接结束</label>
								</div>
						</c:if>
						<div class="group"><a id="btnAgree" class="link agree"><span></span>同意</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${isSignTask==true}">
							<div class="group"><a id="btnNotAgree" class="link notAgree"><span></span>反对</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a id="btnAbandon" class="link abandon"><span></span>弃权</a></div>
							<div class="l-bar-separator"></div>
							<c:if test="${isAllowRetoactive==true}">
								<div class="group"><a class="link flowDesign" onclick="showAddSignWindow()"><span></span>补签</a></div>
								<div class="l-bar-separator"></div>
							</c:if>
						</c:if>
						<div class="group"><a id="btnSave" class="link save" ><span></span>保存表单</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${isCanBack}">
							<div class="group"><a id="btnReject" class="link reject" ><span></span>驳回</a></div>
							<div class="l-bar-separator"></div>
							<div class="group"><a id="btnRejectToStart" class="link rejectToStart" ><span></span>驳回到发起人</a></div>
							<div class="l-bar-separator"></div>
						</c:if>
						<div class="group"><f:a alias="endProcess" css="link abandon"  id="btnEnd" href="#"><span></span>终止</f:a></div>
						<div class="l-bar-separator"></div>
						
						<div class="group"><a class="link setting" onclick="showTaskUserDlg()"><span></span>流程图</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link search" onclick="showTaskOpinions()"><span></span>审批历史</a></div>
						<div class="l-bar-separator"></div>
						
						<div class="group"><a class="link print" onclick="window.print();"><span></span>打印</a></div>
						<div class="l-bar-separator"></div>
						
						<div class="group"><a class="link sendMessage" onclick="showTaskCommunication();"><span></span>沟通</a></div>
						<div class="l-bar-separator"></div>
						<c:if test="${isSignTask==false && task.description!='39'}">
							<div class="group"><a class="link switchuser" onclick="showTaskTransTo()"><span></span>加签</a></div>
							<div class="l-bar-separator"></div>
						</c:if>
						<!--Web签章-->
						<div class="group"><a class="link addWebSigns" onclick="addWebSigns()"><span></span>Web签章</a></div>
						<div class="l-bar-separator"></div>
						<!--手写签章-->
						<div class="group"><a class="link addHangSigns" onclick="addHangSigns()"><span></span>手写签章</a></div>
						<div class="l-bar-separator"></div>
									
						<c:if test="${isExtForm}">
							<c:choose>
								<c:when test="${!empty detailUrl && !empty form}">
									<div class="l-bar-separator"></div>
									<div class="group"><a class="link edit" onclick="openForm('${form}')" >编辑表单</a></div>
								</c:when>
							</c:choose>
						</c:if>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${ isManage==0 }">
				<c:if test="${fn:indexOf(bpmNodeSet.jumpType,'1')!=-1}">
					<span style="height:30px;line-height:30px;"><input type="radio" checked="checked" name="jumpType" onclick="chooseJumpType(1)" value="1" />&nbsp;正常跳转</span>
				</c:if>
				<c:if test="${fn:indexOf(bpmNodeSet.jumpType,'2')!=-1}">
					<span style="height:30px;line-height:30px;"><input type="radio" name="jumpType" onclick="chooseJumpType(2)" value="2" />&nbsp;选择路径跳转</span>
				</c:if>
				<c:if test="${fn:indexOf(bpmNodeSet.jumpType,'3')!=-1}">
					<span style="height:30px;line-height:30px;"><input type="radio" name="jumpType" onclick="chooseJumpType(3)" value="3" />&nbsp;自由跳转</span>
				</c:if>
			</c:when>
			<c:otherwise>
				<span style="height:30px;line-height:30px;"><input type="radio" checked="checked" name="jumpType" onclick="chooseJumpType(1)" value="1" />&nbsp;正常跳转</span>
				<span style="height:30px;line-height:30px;"><input type="radio" name="jumpType" onclick="chooseJumpType(2)" value="2" />&nbsp;选择路径跳转</span>
				<span style="height:30px;line-height:30px;"><input type="radio" name="jumpType" onclick="chooseJumpType(3)" value="3" />&nbsp;自由跳转</span>
			</c:otherwise>
		</c:choose>
		<c:if test="${bpmDefinition.allowRefer==1}">
			<!-- 流程参考 -->
			<div class="group"><a id="btnReference" class="link reference" onclick="reference()"><span></span>流程参考</a></div>
		</c:if>
			<c:if test="${bpmDefinition.attachment!=''}">
				<%@include file="incHelp.jsp" %>
			</c:if>
		</div>	
	</div>
	
</div>