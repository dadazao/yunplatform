<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions" 
	xmlns:bg="bpm.graphic"
	xmlns:fg="bpm.graphic"
	xmlns:activiti="http://activiti.org/bpmn" 
	xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
	xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"
	xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" 
	xmlns:ciied="com.ibm.ilog.elixir.diagram" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:calc="xalan://com.cloudstong.platform.third.bpm.graph.TransformUtil" 
	xmlns:ht="http://www.jee-soft.cn/BPMN20EXT"
	extension-element-prefixes="calc">
	<xsl:param name="id" />
	<xsl:param name="name" />
	<xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" />

	<!-- comp变量的值为/dragram下的所有的子结点名组成的字符串，可用于判断是否包含泳道池 -->
	<xsl:variable name="comp">
		<xsl:for-each select="/diagram/child::*">
			<xsl:value-of select="name(.)"></xsl:value-of>
		</xsl:for-each>
	</xsl:variable>

	<!-- 根模板 -->
	<xsl:template match="/">
		<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" 
		xmlns:activiti="http://activiti.org/bpmn" 
		xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
		xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" 
		xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" 
		xmlns:ht="http://www.jee-soft.cn/BPMN20EXT"
		xsi:schemaLocation="http://www.jee-soft.cn/BPMN20EXT BPMN20EXT.xsd"
		targetNamespace="http://activiti.org/bpmn20"
		>

			<!--应用所有为根节点下的节点模板 -->
			<xsl:apply-templates />

			<!-- 生成图形的坐标的描述 -->
			<bpmndi:BPMNDiagram>
				<xsl:attribute name="id">BPMNDiagram_<xsl:value-of select="$id"></xsl:value-of>
				</xsl:attribute>
				<bpmndi:BPMNPlane>
					<xsl:attribute name="bpmnElement"><xsl:value-of select="$id"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="id">BPMNPlane_<xsl:value-of select="$id"></xsl:value-of>
					</xsl:attribute>

					<!-- 图形组件的坐标描述 -->
					<xsl:call-template name="diagram"></xsl:call-template>
					<!-- 图形组件间连线的坐标描述 -->
					<xsl:call-template name="transition"></xsl:call-template>
				</bpmndi:BPMNPlane>
			</bpmndi:BPMNDiagram>
		</definitions>
	</xsl:template>


	<xsl:template match="/diagram">
		<!--调用模板"collaboration"，生成节点<collaboration> -->
		<xsl:call-template name="collaboration"></xsl:call-template>
		<!--调用模板"bg:HPool"，生成节点水平泳道池 -->
		<xsl:apply-templates select="bg:HPool">
		</xsl:apply-templates>

		<!--调用模板"bg:VPool"，生成节点垂直泳道池 -->
		<xsl:apply-templates select="bg:VPool">
		</xsl:apply-templates>


		<!-- 生成不在泳道池内的组件 -->
		<xsl:if test="contains($comp,'Event') or contains($comp,'Task') or contains($comp,'SequenceFlow') or contains($comp,'SubProcess') or contains($comp,'Gateway')">
			<process>
				<xsl:attribute name="id">
				<xsl:value-of select="$id"></xsl:value-of>
			</xsl:attribute>
				<xsl:attribute name="name">
				<xsl:value-of select="$name"></xsl:value-of>
			</xsl:attribute>
				<extensionElements>
					<!-- 流程开始事件监听器 -->
					<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.StartEventListener" event="start" />
					<!-- 流程结束事件监听器 -->
					<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.EndEventListener" event="end" />
				</extensionElements>
				<xsl:apply-templates select="bg:StartEvent">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:EndEvent">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:Task">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:SequenceFlow">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:SubProcess">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:Gateway">
				</xsl:apply-templates>
			</process>
		</xsl:if>

	</xsl:template>
	<xsl:template match="bg:HPool">
		<process>
			<xsl:attribute name="id">
				<xsl:value-of select="$id"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$name"></xsl:value-of>
			</xsl:attribute>
			<extensionElements>
				<!-- 流程开始事件监听器 -->
				<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.StartEventListener" event="start" />
				<!-- 流程结束事件监听器 -->
				<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.EndEventListener" event="end" />
			</extensionElements>
			<laneSet>
				<xsl:attribute name="id">laneSet_process_<xsl:value-of select="@id"></xsl:value-of>
   				</xsl:attribute>
				<xsl:for-each select="bg:HLane">
					<lane>
						<xsl:attribute name="id">
				 			<xsl:value-of select="@id"></xsl:value-of>
				 		</xsl:attribute>
						<xsl:attribute name="name">
				 			<xsl:value-of select="label"></xsl:value-of>
				 		</xsl:attribute>
						<xsl:for-each select="child::*[starts-with(name(.),'bg:') and name(.)!='bg:SequenceFlow']">
							<flowNodeRef>
								<xsl:value-of select="@id"></xsl:value-of>
							</flowNodeRef>
						</xsl:for-each>
					</lane>
				</xsl:for-each>
			</laneSet>
			<xsl:apply-templates select="bg:HLane"></xsl:apply-templates>
			<xsl:apply-templates select="bg:SequenceFlow">
			</xsl:apply-templates>
		</process>
	</xsl:template>
	<xsl:template match="bg:VPool">
		<process>
			<xsl:attribute name="name"><xsl:value-of select="$name"></xsl:value-of>
   			</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$id"></xsl:value-of>
   			</xsl:attribute>
			<extensionElements>
				<!-- 流程开始事件监听器 -->
				<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.StartEventListener" event="start" />
				<!-- 流程结束事件监听器 -->
				<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.EndEventListener" event="end" />
			</extensionElements>
			<laneSet>
				<xsl:attribute name="id">laneSet_process_<xsl:value-of select="@id"></xsl:value-of>
   				</xsl:attribute>
				<xsl:for-each select="bg:VLane">
					<lane>
						<xsl:attribute name="id">
				 			<xsl:value-of select="@id"></xsl:value-of>
				 		</xsl:attribute>
						<xsl:attribute name="name">
				 			<xsl:value-of select="label"></xsl:value-of>
				 		</xsl:attribute>
						<xsl:for-each select="child::*[starts-with(name(.),'bg:') and name(.)!='bg:SequenceFlow']">
							<flowNodeRef>
								<xsl:value-of select="@id"></xsl:value-of>
							</flowNodeRef>
						</xsl:for-each>
					</lane>
				</xsl:for-each>
			</laneSet>
			<xsl:apply-templates select="bg:VLane"></xsl:apply-templates>
			<xsl:apply-templates select="bg:SequenceFlow">
			</xsl:apply-templates>
		</process>
	</xsl:template>
	<xsl:template match="bg:HLane">
		<xsl:apply-templates select="bg:StartEvent">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:EndEvent">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:Task">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:SubProcess">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:Gateway">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:SequenceFlow">
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="bg:VLane">
		<xsl:apply-templates select="bg:StartEvent">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:EndEvent">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:Task">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:SubProcess">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:Gateway">
		</xsl:apply-templates>
		<xsl:apply-templates select="bg:SequenceFlow">
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="bg:SubProcess">
		<xsl:for-each select=".">
			<subProcess>

				<xsl:call-template name="setAttrubute">
					<xsl:with-param name="obj" select="."></xsl:with-param>
				</xsl:call-template>
				<extensionElements>
					<!-- 流程开始事件监听器 -->
					<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.SubProcessStartListener" event="start" />
					<!-- 流程结束事件监听器 -->
					<activiti:executionListener class="com.cloudstong.platform.third.bpm.listener.SubProcessEndListener" event="end" />
				</extensionElements>
				<xsl:if test="@multiInstance='true'">

					<multiInstanceLoopCharacteristics activiti:elementVariable="assignee">
						<xsl:attribute name="isSequential">
							<xsl:choose>
								<xsl:when test="@isSequential='true'">true</xsl:when>
								<xsl:otherwise>false</xsl:otherwise>
							</xsl:choose>
						</xsl:attribute>
						<xsl:attribute name="activiti:collection">${taskUserAssignService.getMultipleUser(execution)}</xsl:attribute>
					</multiInstanceLoopCharacteristics>
				</xsl:if>
				<xsl:apply-templates select="bg:StartEvent">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:EndEvent">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:Task">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:SequenceFlow">
				</xsl:apply-templates>
				<xsl:apply-templates select="bg:Gateway">
				</xsl:apply-templates>

			</subProcess>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="bg:Gateway">
		<xsl:for-each select=".">
			<xsl:variable name="type" select="./gatewayType" />
			<xsl:choose>
				<xsl:when test="$type='AND'">
					<parallelGateway>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</parallelGateway>
				</xsl:when>
				<xsl:when test="$type='OR'">
					<inclusiveGateway>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</inclusiveGateway>
				</xsl:when>
				<xsl:otherwise>
					<exclusiveGateway>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</exclusiveGateway>

				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="bg:Task">
		<xsl:for-each select=".">
			<xsl:choose>
				<xsl:when test="@user='true'">
					<userTask>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<documentation>
							<xsl:value-of select="Description"></xsl:value-of>
						</documentation>
						<extensionElements>
							<activiti:taskListener event="create" class="com.cloudstong.platform.third.bpm.listener.TaskCreateListener" />
							<activiti:taskListener event="assignment" class="com.cloudstong.platform.third.bpm.listener.TaskAssignListener" />
							<activiti:taskListener event="complete" class="com.cloudstong.platform.third.bpm.listener.TaskCompleteListener" />
							
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</userTask>
				</xsl:when>
				<xsl:when test="@script='true'">
					<serviceTask activiti:class="com.cloudstong.platform.service.bpm.ScriptTask">
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<extensionElements>
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</serviceTask>
				</xsl:when>

				<xsl:when test="@mail='true'">
					<serviceTask activiti:class="com.cloudstong.platform.service.bpm.MessageTask">
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<extensionElements>
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</serviceTask>
				</xsl:when>
				<xsl:when test="@webService='true'">
					<serviceTask activiti:class="com.cloudstong.platform.service.bpm.WebServiceTask">
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<extensionElements>
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</serviceTask>
				</xsl:when>
				<xsl:when test="@receive='true'">
					<receiveTask>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<extensionElements>
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</receiveTask>
				</xsl:when>
				<xsl:when test="@businessRule='true'">
					<businessRuleTask>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<extensionElements>
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</businessRuleTask>
				</xsl:when>
				<!-- 会签 -->
				<xsl:when test="@multiInstance='true'">
					<userTask>
						<!-- <xsl:attribute name="activiti:assignee">${assignee}</xsl:attribute> -->
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<extensionElements>
							<activiti:taskListener event="create" class="com.cloudstong.platform.third.bpm.listener.TaskSignCreateListener" />
							<activiti:taskListener event="assignment" class="com.cloudstong.platform.third.bpm.listener.TaskAssignListener" />
							<activiti:taskListener event="complete" class="com.cloudstong.platform.third.bpm.listener.TaskCompleteListener" />
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
						<multiInstanceLoopCharacteristics activiti:elementVariable="assignee">
							<xsl:attribute name="isSequential">
								<xsl:choose>
									<xsl:when test="@isSequential='true'">true</xsl:when>
									<xsl:otherwise>false</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
							<xsl:attribute name="activiti:collection">${taskUserAssignService.getSignUser(execution)}</xsl:attribute>
							<completionCondition>${signComplete.isComplete(execution) }</completionCondition>
						</multiInstanceLoopCharacteristics>
					</userTask>
				</xsl:when>
				<xsl:when test="@startSubFlow='true'">
					<callActivity>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<xsl:if test="./subDefKey">
							<xsl:attribute name="calledElement">
								<xsl:value-of select="./subDefKey"></xsl:value-of>
							</xsl:attribute>
						</xsl:if>
						<extensionElements>
							<activiti:executionListener event="start" class="com.cloudstong.platform.third.bpm.listener.CallSubProcessStartListener"></activiti:executionListener>
							<activiti:executionListener event="end" class="com.cloudstong.platform.third.bpm.listener.CallSubProcessEndListener"></activiti:executionListener>
							<activiti:in source="outPassVars" target="innerPassVars" />
							<activiti:out source="innerPassVars" target="outPassVars" />
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
						<xsl:if test="@startMultiSubFlow='true'">
							<multiInstanceLoopCharacteristics activiti:elementVariable="assignee">
								<xsl:attribute name="isSequential">
									<xsl:choose>
										<xsl:when test="@isSequential='true'">true</xsl:when>
										<xsl:otherwise>false</xsl:otherwise>
									</xsl:choose>
								</xsl:attribute>
								<xsl:attribute name="activiti:collection">${taskUserAssignService.getExtSubProcessMultipleUser(execution)}</xsl:attribute>
							</multiInstanceLoopCharacteristics>
						</xsl:if>
					</callActivity>
				</xsl:when>
				<xsl:otherwise>

					<userTask>
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<documentation>
							<xsl:value-of select="Description"></xsl:value-of>
						</documentation>
						<extensionElements>
							<activiti:taskListener event="create" class="com.cloudstong.platform.third.bpm.listener.TaskCreateListener" />
							<activiti:taskListener event="assignment" class="com.cloudstong.platform.third.bpm.listener.TaskAssignListener" />
							<activiti:taskListener event="complete" class="com.cloudstong.platform.third.bpm.listener.TaskCompleteListener" />
							<xsl:call-template name="setTaskExtProperties">
								<xsl:with-param name="obj" select="."></xsl:with-param>
							</xsl:call-template>
						</extensionElements>
					</userTask>

				</xsl:otherwise>
			</xsl:choose>

		</xsl:for-each>
	</xsl:template>
	<xsl:template match="bg:StartEvent">
		<startEvent activiti:initiator="startUser">
			<xsl:call-template name="setAttrubute">
				<xsl:with-param name="obj" select="."></xsl:with-param>
			</xsl:call-template>
		</startEvent>
	</xsl:template>
	<xsl:template match="bg:EndEvent">
		<xsl:for-each select=".">
			<endEvent>
				<xsl:call-template name="setAttrubute">
					<xsl:with-param name="obj" select="."></xsl:with-param>
				</xsl:call-template>
				<xsl:if test="trigger='Error'">
					<errorEventDefinition>
						<xsl:attribute name="errorRef">
							<xsl:value-of select="'Error'"></xsl:value-of>
						</xsl:attribute>
					</errorEventDefinition>
				</xsl:if>
			</endEvent>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="bg:SequenceFlow">

		<xsl:for-each select=".">
			<xsl:variable name="sourceRef" select="@startPort"></xsl:variable>
			<xsl:variable name="targetRef" select="@endPort"></xsl:variable>
			<sequenceFlow>
				<xsl:attribute name="sourceRef">
					<xsl:value-of select="//ciied:Port[@id=$sourceRef]/parent::*/parent::*/@id"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="targetRef">
					<xsl:value-of select="//ciied:Port[@id=$targetRef]/parent::*/parent::*/@id"></xsl:value-of>
				</xsl:attribute>
				<xsl:call-template name="setAttrubute">
					<xsl:with-param name="obj" select="."></xsl:with-param>
				</xsl:call-template>
				<!-- 设置跳转条件 -->
				<xsl:variable name="commentPre">
					&lt;![CDATA[${
				</xsl:variable>
				<xsl:variable name="commentSuffix">}]]&gt;
				</xsl:variable>
				<xsl:variable name="condition" select="./Condition" />
				<xsl:if test="$condition!=''">
					<conditionExpression xsi:type="tFormalExpression">
						<xsl:value-of select="$commentPre" />
						<xsl:value-of select="$condition" />
						<xsl:value-of select="$commentSuffix" />
					</conditionExpression>
				</xsl:if>

			</sequenceFlow>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="collaboration">
		<xsl:if test="contains($comp,'Pool')">
			<collaboration id="Collaboration">
				<xsl:for-each select="child::*[contains(name(.),'Pool')]">
					<participant>
						<xsl:attribute name="id">
		   					<xsl:value-of select="@id"></xsl:value-of>
		   				</xsl:attribute>
						<xsl:attribute name="name">
		   					<xsl:value-of select="label"></xsl:value-of>
		   				</xsl:attribute>
						<xsl:attribute name="processRef"><xsl:value-of select="$id"></xsl:value-of>
		   				</xsl:attribute>
					</participant>
				</xsl:for-each>
			</collaboration>
		</xsl:if>
	</xsl:template>

	<xsl:template name="diagram">
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:HPool"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:HLane"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:VPool"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:VLane"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:StartEvent"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:EndEvent"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:Task"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:SubProcess"></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:Gateway"></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="transition">
		<xsl:for-each select="//bg:SequenceFlow">
			<bpmndi:BPMNEdge>
				<xsl:attribute name="bpmnElement"><xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
				<xsl:attribute name="id">BPMNEdge_<xsl:value-of select="@id"></xsl:value-of></xsl:attribute>

				<xsl:variable name="startPort" select="@startPort"></xsl:variable>
				<xsl:variable name="endPort" select="@endPort"></xsl:variable>

				<xsl:variable name="parentName" select="name(//ciied:Port[@id=$startPort]/parent::*/parent::*/parent::*)"></xsl:variable>
				<xsl:variable name="fport" select="//ciied:Port[@id=$startPort]"></xsl:variable>
				<xsl:variable name="fromFlowEl" select="$fport/parent::*/parent::*"></xsl:variable>
				<xsl:variable name="fx" select="$fromFlowEl/@x"></xsl:variable>
				<xsl:variable name="fy" select="$fromFlowEl/@y"></xsl:variable>
				<xsl:variable name="fW" select="$fromFlowEl/@width"></xsl:variable>
				<xsl:variable name="fH" select="$fromFlowEl/@height"></xsl:variable>
				<xsl:variable name="fName" select="name($fromFlowEl)"></xsl:variable>
				<xsl:variable name="fportType" select="$fport/@portType"></xsl:variable>
				<xsl:variable name="fDirX" select="$fport/@x"></xsl:variable>
				<xsl:variable name="fDirY" select="$fport/@y"></xsl:variable>
				<xsl:variable name="fHOffset" select="$fport/@horizontalOffset"></xsl:variable>
				<xsl:variable name="fVOffset" select="$fport/@verticalOffset"></xsl:variable>


				<xsl:variable name="tport" select="//ciied:Port[@id=$endPort]"></xsl:variable>
				<xsl:variable name="toFlowEl" select="$tport/parent::*/parent::*"></xsl:variable>
				<xsl:variable name="tx" select="$toFlowEl/@x"></xsl:variable>
				<xsl:variable name="ty" select="$toFlowEl/@y"></xsl:variable>
				<xsl:variable name="tW" select="$toFlowEl/@width"></xsl:variable>
				<xsl:variable name="tH" select="$toFlowEl/@height"></xsl:variable>
				<xsl:variable name="tName" select="name($toFlowEl)"></xsl:variable>
				<xsl:variable name="tportType" select="$tport/@portType"></xsl:variable>
				<xsl:variable name="tDirX" select="$tport/@x"></xsl:variable>
				<xsl:variable name="tDirY" select="$tport/@y"></xsl:variable>
				<xsl:variable name="tHOffset" select="$tport/@horizontalOffset"></xsl:variable>
				<xsl:variable name="tVOffset" select="$tport/@verticalOffset"></xsl:variable>
				
				<xsl:variable name="shapeType" select="./shapeType"></xsl:variable>
				
				<xsl:variable name="intermediatePoints">
					<xsl:for-each select="./intermediatePoints/child::*[name(.)='fg:Point']">
						<xsl:value-of select="concat(@x,':',@y,',')"></xsl:value-of>
					</xsl:for-each>
				</xsl:variable>
				
				<xsl:choose>
					<!--节点为子流程节点 -->
					<xsl:when test="$parentName='bg:SubProcess'">
						<xsl:variable name="fParent" select="//ciied:Port[@id=$startPort]/parent::*/parent::*/parent::*"></xsl:variable>
						<xsl:variable name="tParent" select="//ciied:Port[@id=$endPort]/parent::*/parent::*/parent::*"></xsl:variable>

						<xsl:variable name="fx1" select="calc:add(string($fx),string($fParent/@x))"></xsl:variable>
						<xsl:variable name="fy1" select="calc:add(string($fy),string($fParent/@y))"></xsl:variable>

						<xsl:variable name="tx1" select="calc:add(string($tx),string($tParent/@x))"></xsl:variable>
						<xsl:variable name="ty1" select="calc:add(string($ty),string($tParent/@y))"></xsl:variable>

						<xsl:variable name="fx" select="calc:add(string($fx1),'10')"></xsl:variable>
						<xsl:variable name="fy" select="calc:add(string($fy1),'28')"></xsl:variable>

						<xsl:variable name="tx" select="calc:add(string($tx1),'10')"></xsl:variable>
						<xsl:variable name="ty" select="calc:add(string($ty1),'28')"></xsl:variable>


						<xsl:choose>
							<!-- 如果子过程在水平泳道内 -->
							<xsl:when test="name($fParent/parent::*)='bg:HLane'">
								<xsl:variable name="fGrandparent" select="$fParent/parent::*"></xsl:variable>
								<xsl:variable name="tGrandparent" select="$tParent/parent::*"></xsl:variable>

								<xsl:variable name="fGreatGrandparent" select="$fGrandparent/parent::*"></xsl:variable>
								<xsl:variable name="tGreatGrandparent" select="$tGrandparent/parent::*"></xsl:variable>

								<xsl:variable name="fx1" select="calc:add(string($fx),string($fGreatGrandparent/@x))"></xsl:variable>
								<xsl:variable name="fx" select="calc:add(string($fx1),'40')"></xsl:variable>

								<!-- 计算连线前端组件的Y坐标 -->
								<xsl:variable name="fGreatGrandparentY" select="$fGreatGrandparent/@y"></xsl:variable>
								<xsl:variable name="preSibsHeight">
									<xsl:for-each select="$fGrandparent/preceding-sibling::*[name(.)='bg:HLane']">
										<xsl:value-of select="concat(@height,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>
								<xsl:variable name="fy">
									<xsl:value-of select="calc:add(calc:accumulate($preSibsHeight,$fGreatGrandparentY),$fy)"></xsl:value-of>
								</xsl:variable>
								<xsl:variable name="tx1" select="calc:add(string($tx),string($tGreatGrandparent/@x))"></xsl:variable>
								<xsl:variable name="tx" select="calc:add(string($tx1),'40')"></xsl:variable>

								<!-- 计算连线末端组件的Y坐标 -->
								<xsl:variable name="tGreatGrandparentY" select="$tGreatGrandparent/@y"></xsl:variable>
								<xsl:variable name="tPreSibsHeight">
									<xsl:for-each select="$tGrandparent/preceding-sibling::*[name(.)='bg:HLane']">
										<xsl:value-of select="concat(@height,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>

								<xsl:variable name="ty">
									<xsl:value-of select="calc:add(calc:accumulate($tPreSibsHeight,$tGreatGrandparentY),string($ty))"></xsl:value-of>
								</xsl:variable>
								
								<xsl:value-of select="calc:calc($shapeType,
									$fName,$fx,$fy,$fW,$fH,
									$fportType,$fDirX,$fDirY,$fHOffset,$fVOffset,
									$tName,$tx,$ty,$tW,$tH,
									$tportType,$tDirX,$tDirY,$tHOffset,$tVOffset,
									$intermediatePoints )" />										
							</xsl:when>

							<xsl:when test="name($fParent/parent::*)='bg:VLane'">
								<xsl:variable name="fGrandparent" select="$fParent/parent::*"></xsl:variable>
								<xsl:variable name="tGrandparent" select="$tParent/parent::*"></xsl:variable>

								<xsl:variable name="fGreatGrandparent" select="$fGrandparent/parent::*"></xsl:variable>
								<xsl:variable name="tGreatGrandparent" select="$tGrandparent/parent::*"></xsl:variable>

								<xsl:variable name="fy1" select="calc:add(string($fy),string($fGreatGrandparent/@y))"></xsl:variable>
								<xsl:variable name="fy" select="calc:add(string($fy1),'40')"></xsl:variable>

								<!-- 计算连线前端组件的X坐标 -->
								<xsl:variable name="fGreatGrandparentX" select="$fGreatGrandparent/@x"></xsl:variable>
								<xsl:variable name="preSibsWidth">
									<xsl:for-each select="$fGrandparent/preceding-sibling::*[name(.)='bg:VLane']">
										<xsl:value-of select="concat(@width,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>
								<xsl:variable name="fx">
									<xsl:value-of select="calc:add(calc:accumulate($preSibsWidth,$fGreatGrandparentX),string($fx))"></xsl:value-of>
								</xsl:variable>
								<xsl:variable name="ty1" select="calc:add(string($ty),string($tGreatGrandparent/@y))"></xsl:variable>
								<xsl:variable name="ty" select="calc:add(string($ty1),'40')"></xsl:variable>

								<!-- 计算连线末端组件的X坐标 -->
								<xsl:variable name="tGreatGrandparentX" select="$tGreatGrandparent/@x"></xsl:variable>
								<xsl:variable name="tPreSibsWidth">
									<xsl:for-each select="$tGrandparent/preceding-sibling::*[name(.)='bg:VLane']">
										<xsl:value-of select="concat(@width,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>

								<xsl:variable name="tx">
									<xsl:value-of select="calc:add(calc:accumulate($tPreSibsWidth,$tGreatGrandparentX),$tx)"></xsl:value-of>
								</xsl:variable>
<!-- 								<xsl:value-of select="calc:calc($fName,$fx,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tx,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" /> -->
								<xsl:value-of select="calc:calc($shapeType,
									$fName,$fx,$fy,$fW,$fH,
									$fportType,$fDirX,$fDirY,$fHOffset,$fVOffset,
									$tName,$tx,$ty,$tW,$tH,
									$tportType,$tDirX,$tDirY,$tHOffset,$tVOffset,
									$intermediatePoints )" />	
							</xsl:when>

							<xsl:otherwise>
<!-- 								<xsl:value-of select="calc:calc($fName,$fx,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tx,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" /> -->
								<xsl:value-of select="calc:calc($shapeType,
									$fName,$fx,$fy,$fW,$fH,
									$fportType,$fDirX,$fDirY,$fHOffset,$fVOffset,
									$tName,$tx,$ty,$tW,$tH,
									$tportType,$tDirX,$tDirY,$tHOffset,$tVOffset,
									$intermediatePoints )" />	
							</xsl:otherwise>
						</xsl:choose>

					</xsl:when>
					<xsl:when test="$parentName='bg:HLane'">
						<xsl:variable name="fParent" select="$fromFlowEl/parent::*"></xsl:variable>
						<xsl:variable name="tParent" select="$toFlowEl/parent::*"></xsl:variable>
						<xsl:variable name="fGrandparent" select="$fParent/parent::*"></xsl:variable>
						<xsl:variable name="tGrandparent" select="$tParent/parent::*"></xsl:variable>

						<xsl:variable name="fx1" select="calc:add(string($fx),string($fGrandparent/@x))"></xsl:variable>
						<xsl:variable name="fx" select="calc:add(string($fx1),'40')"></xsl:variable>

						<!-- 计算连线前端组件的Y坐标 -->
						<xsl:variable name="fGrandparentY" select="$fGrandparent/@y"></xsl:variable>
						<xsl:variable name="preSibsHeight">
							<xsl:for-each select="$fParent/preceding-sibling::*[name(.)='bg:HLane']">
								<xsl:value-of select="concat(@height,',')"></xsl:value-of>
							</xsl:for-each>
						</xsl:variable>
						<xsl:variable name="fy">
							<xsl:value-of select="calc:add(calc:accumulate($preSibsHeight,$fGrandparentY),string($fromFlowEl/@y))"></xsl:value-of>
						</xsl:variable>
						<xsl:variable name="tx1" select="calc:add(string($tx),string($tGrandparent/@x))"></xsl:variable>
						<xsl:variable name="tx" select="calc:add(string($tx1),'40')"></xsl:variable>

						<!-- 计算连线末端组件的Y坐标 -->
						<xsl:variable name="tGrandparentY" select="$tGrandparent/@y"></xsl:variable>
						<xsl:variable name="tPreSibsHeight">
							<xsl:for-each select="$tParent/preceding-sibling::*[name(.)='bg:HLane']">
								<xsl:value-of select="@height"></xsl:value-of>
								,
							</xsl:for-each>
						</xsl:variable>
						<xsl:variable name="ty">
							<xsl:value-of select="calc:add(calc:accumulate($tPreSibsHeight,$tGrandparentY),string($toFlowEl/@y))"></xsl:value-of>
						</xsl:variable>
<!-- 						<xsl:value-of select="calc:calc($fName,$fx,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tx,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" /> -->
						<xsl:value-of select="calc:calc($shapeType,
									$fName,$fx,$fy,$fW,$fH,
									$fportType,$fDirX,$fDirY,$fHOffset,$fVOffset,
									$tName,$tx,$ty,$tW,$tH,
									$tportType,$tDirX,$tDirY,$tHOffset,$tVOffset,
									$intermediatePoints )" />	
					</xsl:when>
					<xsl:when test="$parentName='bg:VLane'">
						<xsl:variable name="fParent" select="$fromFlowEl/parent::*"></xsl:variable>
						<xsl:variable name="tParent" select="$toFlowEl/parent::*"></xsl:variable>
						<xsl:variable name="fGrandparent" select="$fParent/parent::*"></xsl:variable>
						<xsl:variable name="tGrandparent" select="$tParent/parent::*"></xsl:variable>

						<xsl:variable name="fy1" select="calc:add(string($fy),string($fGrandparent/@y))"></xsl:variable>
						<xsl:variable name="fy" select="calc:add(string($fy1),'40')"></xsl:variable>

						<!-- 计算连线前端组件的X坐标 -->
						<xsl:variable name="fGrandparentX" select="$fGrandparent/@x"></xsl:variable>
						<xsl:variable name="preSibsWidth">
							<xsl:for-each select="$fParent/preceding-sibling::*[name(.)='bg:VLane']">
								<xsl:value-of select="concat(@width,',')"></xsl:value-of>
							</xsl:for-each>
						</xsl:variable>
						<xsl:variable name="fx">
							<xsl:value-of select="calc:add(calc:accumulate($preSibsWidth,$fGrandparentX),string($fromFlowEl/@x))"></xsl:value-of>
						</xsl:variable>
						<xsl:variable name="ty1" select="calc:add(string($ty),string($tGrandparent/@y))"></xsl:variable>
						<xsl:variable name="ty" select="calc:add(string($ty1),'40')"></xsl:variable>

						<!-- 计算连线末端组件的X坐标 -->
						<xsl:variable name="tGrandparentX" select="$tGrandparent/@x"></xsl:variable>
						<xsl:variable name="tPreSibsWidth">
							<xsl:for-each select="$tParent/preceding-sibling::*[name(.)='bg:VLane']">
								<xsl:value-of select="concat(@width,',')"></xsl:value-of>
							</xsl:for-each>
						</xsl:variable>
						<xsl:variable name="tx">
							<xsl:value-of select="calc:add(calc:accumulate($tPreSibsWidth,$tGrandparentX),string($toFlowEl/@x))"></xsl:value-of>
						</xsl:variable>
<!-- 						<xsl:value-of select="calc:calc($fName,$fx,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tx,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" /> -->
						<xsl:value-of select="calc:calc($shapeType,
									$fName,$fx,$fy,$fW,$fH,
									$fportType,$fDirX,$fDirY,$fHOffset,$fVOffset,
									$tName,$tx,$ty,$tW,$tH,
									$tportType,$tDirX,$tDirY,$tHOffset,$tVOffset,
									$intermediatePoints )" />	
					</xsl:when>
					<xsl:otherwise>
<!-- 						<xsl:value-of select="calc:calc($fName,$fx,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tx,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" /> -->
					<xsl:value-of select="calc:calc($shapeType,
								$fName,$fx,$fy,$fW,$fH,
								$fportType,$fDirX,$fDirY,$fHOffset,$fVOffset,
								$tName,$tx,$ty,$tW,$tH,
								$tportType,$tDirX,$tDirY,$tHOffset,$tVOffset,
								$intermediatePoints )" />	
					</xsl:otherwise>
				</xsl:choose>
				<bpmndi:BPMNLabel>
					<xsl:if test="./label">
						<xsl:variable name="label" select="./label"></xsl:variable>
						<xsl:value-of select="calc:calcLabelPosition($label)" />
					</xsl:if>
				</bpmndi:BPMNLabel>
			</bpmndi:BPMNEdge>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="diagram_block">
		<xsl:param name="elName" />
		<xsl:for-each select="$elName">
			<bpmndi:BPMNShape>
				<xsl:variable name="name" select="name(.)"></xsl:variable>
				<xsl:variable name="parentName" select="name(./parent::*)"></xsl:variable>
				<xsl:variable name="grandParentName" select="name(./parent::*/parent::*)"></xsl:variable>
				<xsl:attribute name="bpmnElement">
					<xsl:value-of select="@id"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="id">BPMNShape_<xsl:value-of select="@id"></xsl:value-of>
				</xsl:attribute>

				<xsl:variable name="x" select="calc:nan2Zero(@x)"></xsl:variable>
				<xsl:variable name="y" select="calc:nan2Zero(@y)"></xsl:variable>
				<xsl:if test="contains($name,'HPool') or contains($name,'HLane')">
					<xsl:attribute name="isHorizontal">
						<xsl:value-of select="'true'"></xsl:value-of>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="contains($name,'VPool') or contains($name,'VLane')">
					<xsl:attribute name="isHorizontal">
						<xsl:value-of select="'false'"></xsl:value-of>
					</xsl:attribute>
				</xsl:if>

				<omgdc:Bounds>
					<!-- Height and Width -->
					<xsl:choose>
						<xsl:when test="$name='bg:StartEvent' or $name='bg:EndEvent'">
							<xsl:attribute name="width">
								<xsl:value-of select="@width"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="height">
								<xsl:value-of select="@width"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$name='bg:VLane'">
							<xsl:attribute name="width">
								<xsl:value-of select="@width"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="height">
								<xsl:value-of select="calc:add(string(parent::*/@height),'-20')"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$name='bg:HPool'">
							<xsl:attribute name="width">
								<xsl:value-of select="@width"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="height">
								<xsl:variable name="lanes">
									<xsl:for-each select="child::*[name(.)='bg:HLane']">
										<xsl:value-of select="concat(name(.),',')">
									</xsl:value-of></xsl:for-each>
								</xsl:variable>
								<xsl:value-of select="calc:add(string(@height),string(-@gap*(calc:splitLength($lanes,',') -1)))"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$name='bg:HLane'">
							<xsl:attribute name="width">
								<xsl:value-of select="calc:add(string(parent::*/@width),'-20')"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="height">
								<xsl:value-of select="@height"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$name='bg:VPool'">
							<xsl:attribute name="width">
								<xsl:variable name="lanes">
									<xsl:for-each select="child::*[name(.)='bg:VLane']">
										<xsl:value-of select="concat(name(.),',')">
									</xsl:value-of></xsl:for-each>
								</xsl:variable>
								<xsl:value-of select="calc:add(string(@width),string(-@gap*(calc:splitLength($lanes,',') -1)))"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="height">
								<xsl:value-of select="@height"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="height">
								<xsl:value-of select="@height"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="width">
								<xsl:value-of select="@width"></xsl:value-of>
							</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<!-- X , Y -->
					<xsl:choose>
						<xsl:when test="$name='bg:HLane'">
							<xsl:attribute name="x">
								<xsl:value-of select="calc:add(string(parent::*/@x) ,'20')"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="y">
								<xsl:variable name="parentY">
									<xsl:value-of select="parent::*/@y"></xsl:value-of>
								</xsl:variable>
								<xsl:variable name="preSibsHeight">
									<xsl:for-each select="preceding-sibling::*[name(.)='bg:HLane']">
										<xsl:value-of select="concat(@height,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>
								<xsl:value-of select="calc:accumulate($preSibsHeight,$parentY)"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$name='bg:VLane'">
							<xsl:attribute name="x">
								<xsl:variable name="parentX">
									<xsl:value-of select="parent::*/@x"></xsl:value-of>
								</xsl:variable>
								<xsl:variable name="preSibsWidth">
									<xsl:for-each select="preceding-sibling::*[name(.)='bg:VLane']">
										<xsl:value-of select="concat(@width,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>
								<xsl:value-of select="calc:accumulate($preSibsWidth,$parentX)"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="y">
								<xsl:value-of select="calc:add(string(parent::*/@y) ,'20')"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$parentName='bg:HLane'">
							<xsl:variable name="grandparent" select="./parent::*/parent::*"></xsl:variable>
							<xsl:variable name="x1" select="calc:add(string($x),string($grandparent/@x))"></xsl:variable>
							<xsl:variable name="x" select="calc:add(string($x1),'40')"></xsl:variable>
							<xsl:attribute name="x"><xsl:value-of select="$x"></xsl:value-of></xsl:attribute>
							<xsl:attribute name="y">
								<xsl:variable name="grandparentY" select="$grandparent/@y"></xsl:variable>
								<xsl:variable name="preSibsHeight">
									<xsl:for-each select="parent::*/preceding-sibling::*[name(.)='bg:HLane']">
										<xsl:value-of select="@height"></xsl:value-of>,</xsl:for-each>
								</xsl:variable>
								<xsl:value-of select="calc:add(calc:accumulate($preSibsHeight,$grandparentY),@y)"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$parentName='bg:VLane'">
							<xsl:variable name="grandparent" select="./parent::*/parent::*"></xsl:variable>
							<xsl:variable name="y1" select="calc:add(string($y),string($grandparent/@y))"></xsl:variable>
							<xsl:variable name="y" select="calc:add(string($y1),'40')"></xsl:variable>
							<xsl:attribute name="y">
								<xsl:value-of select="$y"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="x">
								<xsl:variable name="grandparentX" select="$grandparent/@x"></xsl:variable>
								<xsl:variable name="preSibsWidth">
									<xsl:for-each select="parent::*/preceding-sibling::*[name(.)='bg:VLane']">
										<xsl:value-of select="concat(@width,',')"></xsl:value-of>
									</xsl:for-each>
								</xsl:variable>
								<xsl:value-of select="calc:add(calc:accumulate($preSibsWidth,$grandparentX),@x)"></xsl:value-of>
							</xsl:attribute>
						</xsl:when>
						<xsl:when test="$parentName='bg:SubProcess'">
							<xsl:variable name="parent" select="./parent::*"></xsl:variable>
							<xsl:variable name="x1" select="calc:add(string($x),string($parent/@x))"></xsl:variable>
							<xsl:variable name="y1" select="calc:add(string($y),string($parent/@y))"></xsl:variable>

							<xsl:variable name="x" select="calc:add(string($x1),'10')"></xsl:variable>
							<xsl:variable name="y" select="calc:add(string($y1),'28')"></xsl:variable>
							<xsl:choose>
								<xsl:when test="$grandParentName='bg:HLane'">
									<xsl:variable name="grandparent" select="./parent::*/parent::*"></xsl:variable>
									<xsl:variable name="greatGrandparent" select="$grandparent/parent::*"></xsl:variable>
									<xsl:variable name="x1" select="calc:add(string($x),string($greatGrandparent/@x))"></xsl:variable>
									<xsl:variable name="x" select="calc:add(string($x1),'40')"></xsl:variable>
									<xsl:attribute name="x"><xsl:value-of select="$x"></xsl:value-of></xsl:attribute>
									<xsl:attribute name="y">
										<xsl:variable name="grandparentY" select="$greatGrandparent/@y"></xsl:variable>
										<xsl:variable name="preSibsHeight">
											<xsl:for-each select="$grandparent/preceding-sibling::*[name(.)='bg:HLane']">
												<xsl:value-of select="concat(@height,',')"></xsl:value-of>
											</xsl:for-each>
										</xsl:variable>
										<xsl:value-of select="calc:add(calc:accumulate($preSibsHeight,$grandparentY),$y)"></xsl:value-of>
									</xsl:attribute>
								</xsl:when>
								<xsl:when test="$grandParentName='bg:VLane'">
									<xsl:variable name="grandparent" select="./parent::*/parent::*"></xsl:variable>
									<xsl:variable name="greatGrandparent" select="$grandparent/parent::*"></xsl:variable>
									<xsl:variable name="y1" select="calc:add(string($y),string($greatGrandparent/@y))"></xsl:variable>
									<xsl:variable name="y" select="calc:add(string($y1),'40')"></xsl:variable>
									<xsl:attribute name="y"><xsl:value-of select="$y"></xsl:value-of></xsl:attribute>
									<xsl:attribute name="x">
										<xsl:variable name="grandparentX" select="$greatGrandparent/@x"></xsl:variable>
										<xsl:variable name="preSibsWidth">
											<xsl:for-each select="$grandparent/preceding-sibling::*[name(.)='bg:VLane']">
												<xsl:value-of select="concat(@width,',')"></xsl:value-of>
											</xsl:for-each>
										</xsl:variable>
										<xsl:value-of select="calc:add(calc:accumulate($preSibsWidth,$grandparentX),$x)"></xsl:value-of>
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="x"><xsl:value-of select="$x"></xsl:value-of></xsl:attribute>
									<xsl:attribute name="y"><xsl:value-of select="$y"></xsl:value-of></xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>

						<xsl:otherwise>
							<xsl:attribute name="x"><xsl:value-of select="$x"></xsl:value-of></xsl:attribute>
							<xsl:attribute name="y"><xsl:value-of select="$y"></xsl:value-of></xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>

				</omgdc:Bounds>

			</bpmndi:BPMNShape>
		</xsl:for-each>
	</xsl:template>


	<xsl:template name="setAttrubute">
		<xsl:param name="obj" />
		<xsl:attribute name="id">
			<xsl:value-of select="$obj/@id"></xsl:value-of>
		</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="$obj/label"></xsl:value-of>
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template name="setTaskExtProperties">
		<xsl:param name="obj"></xsl:param>
		<ht:order>
			<xsl:value-of select="calc:nan2Zero($obj/@order)"></xsl:value-of>
		</ht:order>
	</xsl:template>
</xsl:stylesheet>
