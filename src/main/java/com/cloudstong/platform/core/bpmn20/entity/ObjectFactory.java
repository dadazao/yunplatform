package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _ExtensionElements_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "extensionElements");
  private static final QName _HumanPerformer_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "humanPerformer");
  private static final QName _Collaboration_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "collaboration");
  private static final QName _ParticipantMultiplicity_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "participantMultiplicity");
  private static final QName _ScriptTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "scriptTask");
  private static final QName _SequenceFlow_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "sequenceFlow");
  private static final QName _GlobalBusinessRuleTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalBusinessRuleTask");
  private static final QName _DataAssociation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataAssociation");
  private static final QName _InputSet_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "inputSet");
  private static final QName _DataInputAssociation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataInputAssociation");
  private static final QName _IntermediateThrowEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "intermediateThrowEvent");
  private static final QName _ErrorEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "errorEventDefinition");
  private static final QName _ReceiveTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "receiveTask");
  private static final QName _Conversation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "conversation");
  private static final QName _ImplicitThrowEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "implicitThrowEvent");
  private static final QName _InclusiveGateway_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "inclusiveGateway");
  private static final QName _OutputSet_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "outputSet");
  private static final QName _IntermediateCatchEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "intermediateCatchEvent");
  private static final QName _LoopCharacteristics_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "loopCharacteristics");
  private static final QName _Relationship_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "relationship");
  private static final QName _Process_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "process");
  private static final QName _Extension_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "extension");
  private static final QName _ConditionalEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "conditionalEventDefinition");
  private static final QName _Assignment_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "assignment");
  private static final QName _Artifact_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "artifact");
  private static final QName _FlowNode_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "flowNode");
  private static final QName _EndPoint_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "endPoint");
  private static final QName _MessageFlow_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "messageFlow");
  private static final QName _SubProcess_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "subProcess");
  private static final QName _EndEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "endEvent");
  private static final QName _Documentation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "documentation");
  private static final QName _BaseElement_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "baseElement");
  private static final QName _TerminateEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "terminateEventDefinition");
  private static final QName _EventBasedGateway_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "eventBasedGateway");
  private static final QName _GlobalScriptTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalScriptTask");
  private static final QName _TimerEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "timerEventDefinition");
  private static final QName _ComplexGateway_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "complexGateway");
  private static final QName _ManualTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "manualTask");
  private static final QName _CallableElement_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "callableElement");
  private static final QName _CancelEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "cancelEventDefinition");
  private static final QName _ServiceTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "serviceTask");
  private static final QName _Operation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "operation");
  private static final QName _SubChoreography_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "subChoreography");
  private static final QName _CorrelationSubscription_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "correlationSubscription");
  private static final QName _ChoreographyActivity_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "choreographyActivity");
  private static final QName _Event_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "event");
  private static final QName _GlobalConversation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalConversation");
  private static final QName _Import_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "import");
  private static final QName _ResourceAssignmentExpression_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "resourceAssignmentExpression");
  private static final QName _EventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "eventDefinition");
  private static final QName _Monitoring_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "monitoring");
  private static final QName _ThrowEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "throwEvent");
  private static final QName _ItemDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "itemDefinition");
  private static final QName _AdHocSubProcess_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "adHocSubProcess");
  private static final QName _GlobalUserTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalUserTask");
  private static final QName _Category_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "category");
  private static final QName _StartEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "startEvent");
  private static final QName _Participant_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "participant");
  private static final QName _Performer_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "performer");
  private static final QName _FormalExpression_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "formalExpression");
  private static final QName _MessageEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "messageEventDefinition");
  private static final QName _CatchEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "catchEvent");
  private static final QName _DataOutputAssociation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataOutputAssociation");
  private static final QName _DataObjectReference_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataObjectReference");
  private static final QName _BoundaryEvent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "boundaryEvent");
  private static final QName _ParticipantAssociation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "participantAssociation");
  private static final QName _SendTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "sendTask");
  private static final QName _CategoryValue_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "categoryValue");
  private static final QName _Choreography_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "choreography");
  private static final QName _GlobalChoreographyTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalChoreographyTask");
  private static final QName _CallChoreography_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "callChoreography");
  private static final QName _MultiInstanceLoopCharacteristics_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "multiInstanceLoopCharacteristics");
  private static final QName _PotentialOwner_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "potentialOwner");
  private static final QName _CorrelationPropertyBinding_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "correlationPropertyBinding");
  private static final QName _Signal_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "signal");
  private static final QName _UserTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "userTask");
  private static final QName _ParallelGateway_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "parallelGateway");
  private static final QName _Lane_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "lane");
  private static final QName _SubConversation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "subConversation");
  private static final QName _BaseElementWithMixedContent_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "baseElementWithMixedContent");
  private static final QName _DataStore_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataStore");
  private static final QName _SignalEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "signalEventDefinition");
  private static final QName _RootElement_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "rootElement");
  private static final QName _DataState_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataState");
  private static final QName _CorrelationPropertyRetrievalExpression_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "correlationPropertyRetrievalExpression");
  private static final QName _IoSpecification_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "ioSpecification");
  private static final QName _ConversationAssociation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "conversationAssociation");
  private static final QName _LaneSet_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "laneSet");
  private static final QName _Activity_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "activity");
  private static final QName _GlobalTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalTask");
  private static final QName _Error_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "error");
  private static final QName _Task_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "task");
  private static final QName _Resource_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "resource");
  private static final QName _Interface_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "interface");
  private static final QName _CorrelationKey_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "correlationKey");
  private static final QName _Rendering_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "rendering");
  private static final QName _ResourceParameterBinding_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "resourceParameterBinding");
  private static final QName _ExclusiveGateway_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "exclusiveGateway");
  private static final QName _CorrelationProperty_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "correlationProperty");
  private static final QName _Message_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "message");
  private static final QName _DataStoreReference_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataStoreReference");
  private static final QName _GlobalManualTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "globalManualTask");
  private static final QName _Escalation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "escalation");
  private static final QName _CallActivity_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "callActivity");
  private static final QName _TextAnnotation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "textAnnotation");
  private static final QName _Group_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "group");
  private static final QName _Auditing_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "auditing");
  private static final QName _DataOutput_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataOutput");
  private static final QName _Expression_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "expression");
  private static final QName _Transaction_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "transaction");
  private static final QName _ChoreographyTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "choreographyTask");
  private static final QName _Gateway_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "gateway");
  private static final QName _ResourceRole_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "resourceRole");
  private static final QName _PartnerEntity_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "partnerEntity");
  private static final QName _PartnerRole_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "partnerRole");
  private static final QName _BusinessRuleTask_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "businessRuleTask");
  private static final QName _IoBinding_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "ioBinding");
  private static final QName _FlowElement_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "flowElement");
  private static final QName _DataObject_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataObject");
  private static final QName _LinkEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "linkEventDefinition");
  private static final QName _ResourceParameter_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "resourceParameter");
  private static final QName _Text_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "text");
  private static final QName _Association_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "association");
  private static final QName _CallConversation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "callConversation");
  private static final QName _EscalationEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "escalationEventDefinition");
  private static final QName _Definitions_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "definitions");
  private static final QName _Property_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "property");
  private static final QName _Script_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "script");
  private static final QName _DataInput_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataInput");
  private static final QName _ConversationNode_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "conversationNode");
  private static final QName _CompensateEventDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "compensateEventDefinition");
  private static final QName _ComplexBehaviorDefinition_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "complexBehaviorDefinition");
  private static final QName _ConversationLink_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "conversationLink");
  private static final QName _MessageFlowAssociation_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "messageFlowAssociation");
  private static final QName _StandardLoopCharacteristics_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "standardLoopCharacteristics");
  private static final QName _DataAssociationSourceRef_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "sourceRef");
  private static final QName _OutputSetDataOutputRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataOutputRefs");
  private static final QName _OutputSetOptionalOutputRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "optionalOutputRefs");
  private static final QName _OutputSetWhileExecutingOutputRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "whileExecutingOutputRefs");
  private static final QName _OutputSetInputSetRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "inputSetRefs");
  private static final QName _InputSetWhileExecutingInputRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "whileExecutingInputRefs");
  private static final QName _InputSetOutputSetRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "outputSetRefs");
  private static final QName _InputSetOptionalInputRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "optionalInputRefs");
  private static final QName _InputSetDataInputRefs_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "dataInputRefs");
  private static final QName _LaneFlowNodeRef_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/MODEL", "flowNodeRef");

  public DataObjectReference createDataObjectReference()
  {
    return new DataObjectReference();
  }

  public SubProcess createSubProcess()
  {
    return new SubProcess();
  }

  public Signal createSignal()
  {
    return new Signal();
  }

  public MessageEventDefinition createMessageEventDefinition()
  {
    return new MessageEventDefinition();
  }

  public SubChoreography createSubChoreography()
  {
    return new SubChoreography();
  }

  public ResourceParameter createResourceParameter()
  {
    return new ResourceParameter();
  }

  public DataObject createDataObject()
  {
    return new DataObject();
  }

  public DataAssociation createDataAssociation()
  {
    return new DataAssociation();
  }

  public DataInput createDataInput()
  {
    return new DataInput();
  }

  public ResourceAssignmentExpression createResourceAssignmentExpression()
  {
    return new ResourceAssignmentExpression();
  }

  public ManualTask createManualTask()
  {
    return new ManualTask();
  }

  public CorrelationPropertyRetrievalExpression createCorrelationPropertyRetrievalExpression()
  {
    return new CorrelationPropertyRetrievalExpression();
  }

  public Documentation createDocumentation()
  {
    return new Documentation();
  }

  public DataOutput createDataOutput()
  {
    return new DataOutput();
  }

  public Conversation createConversation()
  {
    return new Conversation();
  }

  public CallChoreography createCallChoreography()
  {
    return new CallChoreography();
  }

  public Assignment createAssignment()
  {
    return new Assignment();
  }

  public GlobalScriptTask createGlobalScriptTask()
  {
    return new GlobalScriptTask();
  }

  public SignalEventDefinition createSignalEventDefinition()
  {
    return new SignalEventDefinition();
  }

  public GlobalManualTask createGlobalManualTask()
  {
    return new GlobalManualTask();
  }

  public EndPoint createEndPoint()
  {
    return new EndPoint();
  }

  public MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics()
  {
    return new MultiInstanceLoopCharacteristics();
  }

  public CategoryValue createCategoryValue()
  {
    return new CategoryValue();
  }

  public Transaction createTransaction()
  {
    return new Transaction();
  }

  public ResourceRole createResourceRole()
  {
    return new ResourceRole();
  }

  public ConditionalEventDefinition createConditionalEventDefinition()
  {
    return new ConditionalEventDefinition();
  }

  public ChoreographyTask createChoreographyTask()
  {
    return new ChoreographyTask();
  }

  public ExclusiveGateway createExclusiveGateway()
  {
    return new ExclusiveGateway();
  }

  public Choreography createChoreography()
  {
    return new Choreography();
  }

  public Resource createResource()
  {
    return new Resource();
  }

  public Group createGroup()
  {
    return new Group();
  }

  public Monitoring createMonitoring()
  {
    return new Monitoring();
  }

  public CorrelationProperty createCorrelationProperty()
  {
    return new CorrelationProperty();
  }

  public DataStore createDataStore()
  {
    return new DataStore();
  }

  public ItemDefinition createItemDefinition()
  {
    return new ItemDefinition();
  }

  public Error createError()
  {
    return new Error();
  }

  public ConversationLink createConversationLink()
  {
    return new ConversationLink();
  }

  public CancelEventDefinition createCancelEventDefinition()
  {
    return new CancelEventDefinition();
  }

  public PartnerRole createPartnerRole()
  {
    return new PartnerRole();
  }

  public Association createAssociation()
  {
    return new Association();
  }

  public MessageFlowAssociation createMessageFlowAssociation()
  {
    return new MessageFlowAssociation();
  }

  public GlobalChoreographyTask createGlobalChoreographyTask()
  {
    return new GlobalChoreographyTask();
  }

  public MessageFlow createMessageFlow()
  {
    return new MessageFlow();
  }

  public LaneSet createLaneSet()
  {
    return new LaneSet();
  }

  public PartnerEntity createPartnerEntity()
  {
    return new PartnerEntity();
  }

  public Lane createLane()
  {
    return new Lane();
  }

  public ErrorEventDefinition createErrorEventDefinition()
  {
    return new ErrorEventDefinition();
  }

  public TimerEventDefinition createTimerEventDefinition()
  {
    return new TimerEventDefinition();
  }

  public BoundaryEvent createBoundaryEvent()
  {
    return new BoundaryEvent();
  }

  public Category createCategory()
  {
    return new Category();
  }

  public Definitions createDefinitions()
  {
    return new Definitions();
  }

  public ParallelGateway createParallelGateway()
  {
    return new ParallelGateway();
  }

  public DataOutputAssociation createDataOutputAssociation()
  {
    return new DataOutputAssociation();
  }

  public DataState createDataState()
  {
    return new DataState();
  }

  public SendTask createSendTask()
  {
    return new SendTask();
  }

  public TextAnnotation createTextAnnotation()
  {
    return new TextAnnotation();
  }

  public GlobalBusinessRuleTask createGlobalBusinessRuleTask()
  {
    return new GlobalBusinessRuleTask();
  }

  public ComplexGateway createComplexGateway()
  {
    return new ComplexGateway();
  }

  public ReceiveTask createReceiveTask()
  {
    return new ReceiveTask();
  }

  public ExtensionElements createExtensionElements()
  {
    return new ExtensionElements();
  }

  public Task createTask()
  {
    return new Task();
  }

  public CorrelationPropertyBinding createCorrelationPropertyBinding()
  {
    return new CorrelationPropertyBinding();
  }

  public CompensateEventDefinition createCompensateEventDefinition()
  {
    return new CompensateEventDefinition();
  }

  public Interface createInterface()
  {
    return new Interface();
  }

  public EndEvent createEndEvent()
  {
    return new EndEvent();
  }

  public DataInputAssociation createDataInputAssociation()
  {
    return new DataInputAssociation();
  }

  public Escalation createEscalation()
  {
    return new Escalation();
  }

  public AdHocSubProcess createAdHocSubProcess()
  {
    return new AdHocSubProcess();
  }

  public PotentialOwner createPotentialOwner()
  {
    return new PotentialOwner();
  }

  public Property createProperty()
  {
    return new Property();
  }

  public ParticipantMultiplicity createParticipantMultiplicity()
  {
    return new ParticipantMultiplicity();
  }

  public OutputSet createOutputSet()
  {
    return new OutputSet();
  }

  public InclusiveGateway createInclusiveGateway()
  {
    return new InclusiveGateway();
  }

  public FormalExpression createFormalExpression()
  {
    return new FormalExpression();
  }

  public Extension createExtension()
  {
    return new Extension();
  }

  public SequenceFlow createSequenceFlow()
  {
    return new SequenceFlow();
  }

  public Auditing createAuditing()
  {
    return new Auditing();
  }

  public Expression createExpression()
  {
    return new Expression();
  }

  public Gateway createGateway()
  {
    return new Gateway();
  }

  public ServiceTask createServiceTask()
  {
    return new ServiceTask();
  }

  public ConversationAssociation createConversationAssociation()
  {
    return new ConversationAssociation();
  }

  public LinkEventDefinition createLinkEventDefinition()
  {
    return new LinkEventDefinition();
  }

  public EscalationEventDefinition createEscalationEventDefinition()
  {
    return new EscalationEventDefinition();
  }

  public IntermediateThrowEvent createIntermediateThrowEvent()
  {
    return new IntermediateThrowEvent();
  }

  public IntermediateCatchEvent createIntermediateCatchEvent()
  {
    return new IntermediateCatchEvent();
  }

  public UserTask createUserTask()
  {
    return new UserTask();
  }

  public GlobalUserTask createGlobalUserTask()
  {
    return new GlobalUserTask();
  }

  public StartEvent createStartEvent()
  {
    return new StartEvent();
  }

  public CallActivity createCallActivity()
  {
    return new CallActivity();
  }

  public HumanPerformer createHumanPerformer()
  {
    return new HumanPerformer();
  }

  public ResourceParameterBinding createResourceParameterBinding()
  {
    return new ResourceParameterBinding();
  }

  public EventBasedGateway createEventBasedGateway()
  {
    return new EventBasedGateway();
  }

  public CorrelationSubscription createCorrelationSubscription()
  {
    return new CorrelationSubscription();
  }

  public Collaboration createCollaboration()
  {
    return new Collaboration();
  }

  public Message createMessage()
  {
    return new Message();
  }

  public GlobalTask createGlobalTask()
  {
    return new GlobalTask();
  }

  public CorrelationKey createCorrelationKey()
  {
    return new CorrelationKey();
  }

  public CallableElement createCallableElement()
  {
    return new CallableElement();
  }

  public CallConversation createCallConversation()
  {
    return new CallConversation();
  }

  public ImplicitThrowEvent createImplicitThrowEvent()
  {
    return new ImplicitThrowEvent();
  }

  public ComplexBehaviorDefinition createComplexBehaviorDefinition()
  {
    return new ComplexBehaviorDefinition();
  }

  public SubConversation createSubConversation()
  {
    return new SubConversation();
  }

  public StandardLoopCharacteristics createStandardLoopCharacteristics()
  {
    return new StandardLoopCharacteristics();
  }

  public Participant createParticipant()
  {
    return new Participant();
  }

  public Relationship createRelationship()
  {
    return new Relationship();
  }

  public Script createScript()
  {
    return new Script();
  }

  public Process createProcess()
  {
    return new Process();
  }

  public GlobalConversation createGlobalConversation()
  {
    return new GlobalConversation();
  }

  public Import createImport()
  {
    return new Import();
  }

  public InputSet createInputSet()
  {
    return new InputSet();
  }

  public Text createText()
  {
    return new Text();
  }

  public Rendering createRendering()
  {
    return new Rendering();
  }

  public ScriptTask createScriptTask()
  {
    return new ScriptTask();
  }

  public BusinessRuleTask createBusinessRuleTask()
  {
    return new BusinessRuleTask();
  }

  public ParticipantAssociation createParticipantAssociation()
  {
    return new ParticipantAssociation();
  }

  public TerminateEventDefinition createTerminateEventDefinition()
  {
    return new TerminateEventDefinition();
  }

  public InputOutputSpecification createInputOutputSpecification()
  {
    return new InputOutputSpecification();
  }

  public Performer createPerformer()
  {
    return new Performer();
  }

  public DataStoreReference createDataStoreReference()
  {
    return new DataStoreReference();
  }

  public Operation createOperation()
  {
    return new Operation();
  }

  public InputOutputBinding createInputOutputBinding()
  {
    return new InputOutputBinding();
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="extensionElements")
  public JAXBElement<ExtensionElements> createExtensionElements(ExtensionElements value)
  {
    return new JAXBElement(_ExtensionElements_QNAME, ExtensionElements.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="humanPerformer", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="performer")
  public JAXBElement<HumanPerformer> createHumanPerformer(HumanPerformer value)
  {
    return new JAXBElement(_HumanPerformer_QNAME, HumanPerformer.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="collaboration", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Collaboration> createCollaboration(Collaboration value)
  {
    return new JAXBElement(_Collaboration_QNAME, Collaboration.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="participantMultiplicity")
  public JAXBElement<ParticipantMultiplicity> createParticipantMultiplicity(ParticipantMultiplicity value)
  {
    return new JAXBElement(_ParticipantMultiplicity_QNAME, ParticipantMultiplicity.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="scriptTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ScriptTask> createScriptTask(ScriptTask value)
  {
    return new JAXBElement(_ScriptTask_QNAME, ScriptTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="sequenceFlow", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<SequenceFlow> createSequenceFlow(SequenceFlow value)
  {
    return new JAXBElement(_SequenceFlow_QNAME, SequenceFlow.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalBusinessRuleTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<GlobalBusinessRuleTask> createGlobalBusinessRuleTask(GlobalBusinessRuleTask value)
  {
    return new JAXBElement(_GlobalBusinessRuleTask_QNAME, GlobalBusinessRuleTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataAssociation")
  public JAXBElement<DataAssociation> createDataAssociation(DataAssociation value)
  {
    return new JAXBElement(_DataAssociation_QNAME, DataAssociation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="inputSet")
  public JAXBElement<InputSet> createInputSet(InputSet value)
  {
    return new JAXBElement(_InputSet_QNAME, InputSet.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataInputAssociation")
  public JAXBElement<DataInputAssociation> createDataInputAssociation(DataInputAssociation value)
  {
    return new JAXBElement(_DataInputAssociation_QNAME, DataInputAssociation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="intermediateThrowEvent", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<IntermediateThrowEvent> createIntermediateThrowEvent(IntermediateThrowEvent value)
  {
    return new JAXBElement(_IntermediateThrowEvent_QNAME, IntermediateThrowEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="errorEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<ErrorEventDefinition> createErrorEventDefinition(ErrorEventDefinition value)
  {
    return new JAXBElement(_ErrorEventDefinition_QNAME, ErrorEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="receiveTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ReceiveTask> createReceiveTask(ReceiveTask value)
  {
    return new JAXBElement(_ReceiveTask_QNAME, ReceiveTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="conversation", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="conversationNode")
  public JAXBElement<Conversation> createConversation(Conversation value)
  {
    return new JAXBElement(_Conversation_QNAME, Conversation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="implicitThrowEvent", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ImplicitThrowEvent> createImplicitThrowEvent(ImplicitThrowEvent value)
  {
    return new JAXBElement(_ImplicitThrowEvent_QNAME, ImplicitThrowEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="inclusiveGateway", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<InclusiveGateway> createInclusiveGateway(InclusiveGateway value)
  {
    return new JAXBElement(_InclusiveGateway_QNAME, InclusiveGateway.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="outputSet")
  public JAXBElement<OutputSet> createOutputSet(OutputSet value)
  {
    return new JAXBElement(_OutputSet_QNAME, OutputSet.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="intermediateCatchEvent", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<IntermediateCatchEvent> createIntermediateCatchEvent(IntermediateCatchEvent value)
  {
    return new JAXBElement(_IntermediateCatchEvent_QNAME, IntermediateCatchEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="loopCharacteristics")
  public JAXBElement<LoopCharacteristics> createLoopCharacteristics(LoopCharacteristics value)
  {
    return new JAXBElement(_LoopCharacteristics_QNAME, LoopCharacteristics.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="relationship")
  public JAXBElement<Relationship> createRelationship(Relationship value)
  {
    return new JAXBElement(_Relationship_QNAME, Relationship.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="process", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Process> createProcess(Process value)
  {
    return new JAXBElement(_Process_QNAME, Process.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="extension")
  public JAXBElement<Extension> createExtension(Extension value)
  {
    return new JAXBElement(_Extension_QNAME, Extension.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="conditionalEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<ConditionalEventDefinition> createConditionalEventDefinition(ConditionalEventDefinition value)
  {
    return new JAXBElement(_ConditionalEventDefinition_QNAME, ConditionalEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="assignment")
  public JAXBElement<Assignment> createAssignment(Assignment value)
  {
    return new JAXBElement(_Assignment_QNAME, Assignment.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="artifact")
  public JAXBElement<Artifact> createArtifact(Artifact value)
  {
    return new JAXBElement(_Artifact_QNAME, Artifact.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="flowNode")
  public JAXBElement<FlowNode> createFlowNode(FlowNode value)
  {
    return new JAXBElement(_FlowNode_QNAME, FlowNode.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="endPoint", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<EndPoint> createEndPoint(EndPoint value)
  {
    return new JAXBElement(_EndPoint_QNAME, EndPoint.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="messageFlow")
  public JAXBElement<MessageFlow> createMessageFlow(MessageFlow value)
  {
    return new JAXBElement(_MessageFlow_QNAME, MessageFlow.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="subProcess", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<SubProcess> createSubProcess(SubProcess value)
  {
    return new JAXBElement(_SubProcess_QNAME, SubProcess.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="endEvent", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<EndEvent> createEndEvent(EndEvent value)
  {
    return new JAXBElement(_EndEvent_QNAME, EndEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="documentation")
  public JAXBElement<Documentation> createDocumentation(Documentation value)
  {
    return new JAXBElement(_Documentation_QNAME, Documentation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="baseElement")
  public JAXBElement<BaseElement> createBaseElement(BaseElement value)
  {
    return new JAXBElement(_BaseElement_QNAME, BaseElement.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="terminateEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<TerminateEventDefinition> createTerminateEventDefinition(TerminateEventDefinition value)
  {
    return new JAXBElement(_TerminateEventDefinition_QNAME, TerminateEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="eventBasedGateway", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<EventBasedGateway> createEventBasedGateway(EventBasedGateway value)
  {
    return new JAXBElement(_EventBasedGateway_QNAME, EventBasedGateway.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalScriptTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<GlobalScriptTask> createGlobalScriptTask(GlobalScriptTask value)
  {
    return new JAXBElement(_GlobalScriptTask_QNAME, GlobalScriptTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="timerEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<TimerEventDefinition> createTimerEventDefinition(TimerEventDefinition value)
  {
    return new JAXBElement(_TimerEventDefinition_QNAME, TimerEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="complexGateway", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ComplexGateway> createComplexGateway(ComplexGateway value)
  {
    return new JAXBElement(_ComplexGateway_QNAME, ComplexGateway.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="manualTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ManualTask> createManualTask(ManualTask value)
  {
    return new JAXBElement(_ManualTask_QNAME, ManualTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="callableElement")
  public JAXBElement<CallableElement> createCallableElement(CallableElement value)
  {
    return new JAXBElement(_CallableElement_QNAME, CallableElement.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="cancelEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<CancelEventDefinition> createCancelEventDefinition(CancelEventDefinition value)
  {
    return new JAXBElement(_CancelEventDefinition_QNAME, CancelEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="serviceTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ServiceTask> createServiceTask(ServiceTask value)
  {
    return new JAXBElement(_ServiceTask_QNAME, ServiceTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="operation")
  public JAXBElement<Operation> createOperation(Operation value)
  {
    return new JAXBElement(_Operation_QNAME, Operation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="subChoreography", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<SubChoreography> createSubChoreography(SubChoreography value)
  {
    return new JAXBElement(_SubChoreography_QNAME, SubChoreography.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="correlationSubscription")
  public JAXBElement<CorrelationSubscription> createCorrelationSubscription(CorrelationSubscription value)
  {
    return new JAXBElement(_CorrelationSubscription_QNAME, CorrelationSubscription.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="choreographyActivity")
  public JAXBElement<ChoreographyActivity> createChoreographyActivity(ChoreographyActivity value)
  {
    return new JAXBElement(_ChoreographyActivity_QNAME, ChoreographyActivity.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="event", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<Event> createEvent(Event value)
  {
    return new JAXBElement(_Event_QNAME, Event.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalConversation", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="collaboration")
  public JAXBElement<GlobalConversation> createGlobalConversation(GlobalConversation value)
  {
    return new JAXBElement(_GlobalConversation_QNAME, GlobalConversation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="import")
  public JAXBElement<Import> createImport(Import value)
  {
    return new JAXBElement(_Import_QNAME, Import.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="resourceAssignmentExpression")
  public JAXBElement<ResourceAssignmentExpression> createResourceAssignmentExpression(ResourceAssignmentExpression value)
  {
    return new JAXBElement(_ResourceAssignmentExpression_QNAME, ResourceAssignmentExpression.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="eventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<EventDefinition> createEventDefinition(EventDefinition value)
  {
    return new JAXBElement(_EventDefinition_QNAME, EventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="monitoring")
  public JAXBElement<Monitoring> createMonitoring(Monitoring value)
  {
    return new JAXBElement(_Monitoring_QNAME, Monitoring.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="throwEvent")
  public JAXBElement<ThrowEvent> createThrowEvent(ThrowEvent value)
  {
    return new JAXBElement(_ThrowEvent_QNAME, ThrowEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="itemDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<ItemDefinition> createItemDefinition(ItemDefinition value)
  {
    return new JAXBElement(_ItemDefinition_QNAME, ItemDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="adHocSubProcess", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<AdHocSubProcess> createAdHocSubProcess(AdHocSubProcess value)
  {
    return new JAXBElement(_AdHocSubProcess_QNAME, AdHocSubProcess.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalUserTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<GlobalUserTask> createGlobalUserTask(GlobalUserTask value)
  {
    return new JAXBElement(_GlobalUserTask_QNAME, GlobalUserTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="category", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Category> createCategory(Category value)
  {
    return new JAXBElement(_Category_QNAME, Category.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="startEvent", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<StartEvent> createStartEvent(StartEvent value)
  {
    return new JAXBElement(_StartEvent_QNAME, StartEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="participant")
  public JAXBElement<Participant> createParticipant(Participant value)
  {
    return new JAXBElement(_Participant_QNAME, Participant.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="performer", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="resourceRole")
  public JAXBElement<Performer> createPerformer(Performer value)
  {
    return new JAXBElement(_Performer_QNAME, Performer.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="formalExpression", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="expression")
  public JAXBElement<FormalExpression> createFormalExpression(FormalExpression value)
  {
    return new JAXBElement(_FormalExpression_QNAME, FormalExpression.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="messageEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<MessageEventDefinition> createMessageEventDefinition(MessageEventDefinition value)
  {
    return new JAXBElement(_MessageEventDefinition_QNAME, MessageEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="catchEvent")
  public JAXBElement<CatchEvent> createCatchEvent(CatchEvent value)
  {
    return new JAXBElement(_CatchEvent_QNAME, CatchEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataOutputAssociation")
  public JAXBElement<DataOutputAssociation> createDataOutputAssociation(DataOutputAssociation value)
  {
    return new JAXBElement(_DataOutputAssociation_QNAME, DataOutputAssociation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataObjectReference", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<DataObjectReference> createDataObjectReference(DataObjectReference value)
  {
    return new JAXBElement(_DataObjectReference_QNAME, DataObjectReference.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="boundaryEvent", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<BoundaryEvent> createBoundaryEvent(BoundaryEvent value)
  {
    return new JAXBElement(_BoundaryEvent_QNAME, BoundaryEvent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="participantAssociation")
  public JAXBElement<ParticipantAssociation> createParticipantAssociation(ParticipantAssociation value)
  {
    return new JAXBElement(_ParticipantAssociation_QNAME, ParticipantAssociation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="sendTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<SendTask> createSendTask(SendTask value)
  {
    return new JAXBElement(_SendTask_QNAME, SendTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="categoryValue")
  public JAXBElement<CategoryValue> createCategoryValue(CategoryValue value)
  {
    return new JAXBElement(_CategoryValue_QNAME, CategoryValue.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="choreography", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="collaboration")
  public JAXBElement<Choreography> createChoreography(Choreography value)
  {
    return new JAXBElement(_Choreography_QNAME, Choreography.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalChoreographyTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="choreography")
  public JAXBElement<GlobalChoreographyTask> createGlobalChoreographyTask(GlobalChoreographyTask value)
  {
    return new JAXBElement(_GlobalChoreographyTask_QNAME, GlobalChoreographyTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="callChoreography", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<CallChoreography> createCallChoreography(CallChoreography value)
  {
    return new JAXBElement(_CallChoreography_QNAME, CallChoreography.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="multiInstanceLoopCharacteristics", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="loopCharacteristics")
  public JAXBElement<MultiInstanceLoopCharacteristics> createMultiInstanceLoopCharacteristics(MultiInstanceLoopCharacteristics value)
  {
    return new JAXBElement(_MultiInstanceLoopCharacteristics_QNAME, MultiInstanceLoopCharacteristics.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="potentialOwner", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="performer")
  public JAXBElement<PotentialOwner> createPotentialOwner(PotentialOwner value)
  {
    return new JAXBElement(_PotentialOwner_QNAME, PotentialOwner.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="correlationPropertyBinding")
  public JAXBElement<CorrelationPropertyBinding> createCorrelationPropertyBinding(CorrelationPropertyBinding value)
  {
    return new JAXBElement(_CorrelationPropertyBinding_QNAME, CorrelationPropertyBinding.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="signal", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Signal> createSignal(Signal value)
  {
    return new JAXBElement(_Signal_QNAME, Signal.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="userTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<UserTask> createUserTask(UserTask value)
  {
    return new JAXBElement(_UserTask_QNAME, UserTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="parallelGateway", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ParallelGateway> createParallelGateway(ParallelGateway value)
  {
    return new JAXBElement(_ParallelGateway_QNAME, ParallelGateway.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="lane")
  public JAXBElement<Lane> createLane(Lane value)
  {
    return new JAXBElement(_Lane_QNAME, Lane.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="subConversation", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="conversationNode")
  public JAXBElement<SubConversation> createSubConversation(SubConversation value)
  {
    return new JAXBElement(_SubConversation_QNAME, SubConversation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="baseElementWithMixedContent")
  public JAXBElement<BaseElementWithMixedContent> createBaseElementWithMixedContent(BaseElementWithMixedContent value)
  {
    return new JAXBElement(_BaseElementWithMixedContent_QNAME, BaseElementWithMixedContent.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataStore", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<DataStore> createDataStore(DataStore value)
  {
    return new JAXBElement(_DataStore_QNAME, DataStore.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="signalEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<SignalEventDefinition> createSignalEventDefinition(SignalEventDefinition value)
  {
    return new JAXBElement(_SignalEventDefinition_QNAME, SignalEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="rootElement")
  public JAXBElement<RootElement> createRootElement(RootElement value)
  {
    return new JAXBElement(_RootElement_QNAME, RootElement.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataState")
  public JAXBElement<DataState> createDataState(DataState value)
  {
    return new JAXBElement(_DataState_QNAME, DataState.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="correlationPropertyRetrievalExpression")
  public JAXBElement<CorrelationPropertyRetrievalExpression> createCorrelationPropertyRetrievalExpression(CorrelationPropertyRetrievalExpression value)
  {
    return new JAXBElement(_CorrelationPropertyRetrievalExpression_QNAME, CorrelationPropertyRetrievalExpression.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="ioSpecification")
  public JAXBElement<InputOutputSpecification> createIoSpecification(InputOutputSpecification value)
  {
    return new JAXBElement(_IoSpecification_QNAME, InputOutputSpecification.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="conversationAssociation")
  public JAXBElement<ConversationAssociation> createConversationAssociation(ConversationAssociation value)
  {
    return new JAXBElement(_ConversationAssociation_QNAME, ConversationAssociation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="laneSet")
  public JAXBElement<LaneSet> createLaneSet(LaneSet value)
  {
    return new JAXBElement(_LaneSet_QNAME, LaneSet.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="activity")
  public JAXBElement<Activity> createActivity(Activity value)
  {
    return new JAXBElement(_Activity_QNAME, Activity.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<GlobalTask> createGlobalTask(GlobalTask value)
  {
    return new JAXBElement(_GlobalTask_QNAME, GlobalTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="error", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Error> createError(Error value)
  {
    return new JAXBElement(_Error_QNAME, Error.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="task", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<Task> createTask(Task value)
  {
    return new JAXBElement(_Task_QNAME, Task.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="resource", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Resource> createResource(Resource value)
  {
    return new JAXBElement(_Resource_QNAME, Resource.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="interface", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Interface> createInterface(Interface value)
  {
    return new JAXBElement(_Interface_QNAME, Interface.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="correlationKey")
  public JAXBElement<CorrelationKey> createCorrelationKey(CorrelationKey value)
  {
    return new JAXBElement(_CorrelationKey_QNAME, CorrelationKey.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="rendering")
  public JAXBElement<Rendering> createRendering(Rendering value)
  {
    return new JAXBElement(_Rendering_QNAME, Rendering.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="resourceParameterBinding")
  public JAXBElement<ResourceParameterBinding> createResourceParameterBinding(ResourceParameterBinding value)
  {
    return new JAXBElement(_ResourceParameterBinding_QNAME, ResourceParameterBinding.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="exclusiveGateway", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ExclusiveGateway> createExclusiveGateway(ExclusiveGateway value)
  {
    return new JAXBElement(_ExclusiveGateway_QNAME, ExclusiveGateway.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="correlationProperty", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<CorrelationProperty> createCorrelationProperty(CorrelationProperty value)
  {
    return new JAXBElement(_CorrelationProperty_QNAME, CorrelationProperty.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="message", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Message> createMessage(Message value)
  {
    return new JAXBElement(_Message_QNAME, Message.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataStoreReference", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<DataStoreReference> createDataStoreReference(DataStoreReference value)
  {
    return new JAXBElement(_DataStoreReference_QNAME, DataStoreReference.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="globalManualTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<GlobalManualTask> createGlobalManualTask(GlobalManualTask value)
  {
    return new JAXBElement(_GlobalManualTask_QNAME, GlobalManualTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="escalation", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<Escalation> createEscalation(Escalation value)
  {
    return new JAXBElement(_Escalation_QNAME, Escalation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="callActivity", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<CallActivity> createCallActivity(CallActivity value)
  {
    return new JAXBElement(_CallActivity_QNAME, CallActivity.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="textAnnotation", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="artifact")
  public JAXBElement<TextAnnotation> createTextAnnotation(TextAnnotation value)
  {
    return new JAXBElement(_TextAnnotation_QNAME, TextAnnotation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="group", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="artifact")
  public JAXBElement<Group> createGroup(Group value)
  {
    return new JAXBElement(_Group_QNAME, Group.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="auditing")
  public JAXBElement<Auditing> createAuditing(Auditing value)
  {
    return new JAXBElement(_Auditing_QNAME, Auditing.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataOutput")
  public JAXBElement<DataOutput> createDataOutput(DataOutput value)
  {
    return new JAXBElement(_DataOutput_QNAME, DataOutput.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="expression")
  public JAXBElement<Expression> createExpression(Expression value)
  {
    return new JAXBElement(_Expression_QNAME, Expression.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="transaction", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<Transaction> createTransaction(Transaction value)
  {
    return new JAXBElement(_Transaction_QNAME, Transaction.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="choreographyTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<ChoreographyTask> createChoreographyTask(ChoreographyTask value)
  {
    return new JAXBElement(_ChoreographyTask_QNAME, ChoreographyTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="gateway")
  public JAXBElement<Gateway> createGateway(Gateway value)
  {
    return new JAXBElement(_Gateway_QNAME, Gateway.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="resourceRole")
  public JAXBElement<ResourceRole> createResourceRole(ResourceRole value)
  {
    return new JAXBElement(_ResourceRole_QNAME, ResourceRole.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="partnerEntity", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<PartnerEntity> createPartnerEntity(PartnerEntity value)
  {
    return new JAXBElement(_PartnerEntity_QNAME, PartnerEntity.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="partnerRole", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="rootElement")
  public JAXBElement<PartnerRole> createPartnerRole(PartnerRole value)
  {
    return new JAXBElement(_PartnerRole_QNAME, PartnerRole.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="businessRuleTask", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<BusinessRuleTask> createBusinessRuleTask(BusinessRuleTask value)
  {
    return new JAXBElement(_BusinessRuleTask_QNAME, BusinessRuleTask.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="ioBinding")
  public JAXBElement<InputOutputBinding> createIoBinding(InputOutputBinding value)
  {
    return new JAXBElement(_IoBinding_QNAME, InputOutputBinding.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="flowElement")
  public JAXBElement<FlowElement> createFlowElement(FlowElement value)
  {
    return new JAXBElement(_FlowElement_QNAME, FlowElement.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataObject", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="flowElement")
  public JAXBElement<DataObject> createDataObject(DataObject value)
  {
    return new JAXBElement(_DataObject_QNAME, DataObject.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="linkEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<LinkEventDefinition> createLinkEventDefinition(LinkEventDefinition value)
  {
    return new JAXBElement(_LinkEventDefinition_QNAME, LinkEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="resourceParameter")
  public JAXBElement<ResourceParameter> createResourceParameter(ResourceParameter value)
  {
    return new JAXBElement(_ResourceParameter_QNAME, ResourceParameter.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="text")
  public JAXBElement<Text> createText(Text value)
  {
    return new JAXBElement(_Text_QNAME, Text.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="association", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="artifact")
  public JAXBElement<Association> createAssociation(Association value)
  {
    return new JAXBElement(_Association_QNAME, Association.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="callConversation", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="conversationNode")
  public JAXBElement<CallConversation> createCallConversation(CallConversation value)
  {
    return new JAXBElement(_CallConversation_QNAME, CallConversation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="escalationEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<EscalationEventDefinition> createEscalationEventDefinition(EscalationEventDefinition value)
  {
    return new JAXBElement(_EscalationEventDefinition_QNAME, EscalationEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="definitions")
  public JAXBElement<Definitions> createDefinitions(Definitions value)
  {
    return new JAXBElement(_Definitions_QNAME, Definitions.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="property")
  public JAXBElement<Property> createProperty(Property value)
  {
    return new JAXBElement(_Property_QNAME, Property.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="script")
  public JAXBElement<Script> createScript(Script value)
  {
    return new JAXBElement(_Script_QNAME, Script.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataInput")
  public JAXBElement<DataInput> createDataInput(DataInput value)
  {
    return new JAXBElement(_DataInput_QNAME, DataInput.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="conversationNode")
  public JAXBElement<ConversationNode> createConversationNode(ConversationNode value)
  {
    return new JAXBElement(_ConversationNode_QNAME, ConversationNode.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="compensateEventDefinition", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="eventDefinition")
  public JAXBElement<CompensateEventDefinition> createCompensateEventDefinition(CompensateEventDefinition value)
  {
    return new JAXBElement(_CompensateEventDefinition_QNAME, CompensateEventDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="complexBehaviorDefinition")
  public JAXBElement<ComplexBehaviorDefinition> createComplexBehaviorDefinition(ComplexBehaviorDefinition value)
  {
    return new JAXBElement(_ComplexBehaviorDefinition_QNAME, ComplexBehaviorDefinition.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="conversationLink")
  public JAXBElement<ConversationLink> createConversationLink(ConversationLink value)
  {
    return new JAXBElement(_ConversationLink_QNAME, ConversationLink.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="messageFlowAssociation")
  public JAXBElement<MessageFlowAssociation> createMessageFlowAssociation(MessageFlowAssociation value)
  {
    return new JAXBElement(_MessageFlowAssociation_QNAME, MessageFlowAssociation.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="standardLoopCharacteristics", substitutionHeadNamespace="http://www.omg.org/spec/BPMN/20100524/MODEL", substitutionHeadName="loopCharacteristics")
  public JAXBElement<StandardLoopCharacteristics> createStandardLoopCharacteristics(StandardLoopCharacteristics value)
  {
    return new JAXBElement(_StandardLoopCharacteristics_QNAME, StandardLoopCharacteristics.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="sourceRef", scope=DataAssociation.class)
  @XmlIDREF
  public JAXBElement<Object> createDataAssociationSourceRef(Object value)
  {
    return new JAXBElement(_DataAssociationSourceRef_QNAME, Object.class, DataAssociation.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataOutputRefs", scope=OutputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createOutputSetDataOutputRefs(Object value)
  {
    return new JAXBElement(_OutputSetDataOutputRefs_QNAME, Object.class, OutputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="optionalOutputRefs", scope=OutputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createOutputSetOptionalOutputRefs(Object value)
  {
    return new JAXBElement(_OutputSetOptionalOutputRefs_QNAME, Object.class, OutputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="whileExecutingOutputRefs", scope=OutputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createOutputSetWhileExecutingOutputRefs(Object value)
  {
    return new JAXBElement(_OutputSetWhileExecutingOutputRefs_QNAME, Object.class, OutputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="inputSetRefs", scope=OutputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createOutputSetInputSetRefs(Object value)
  {
    return new JAXBElement(_OutputSetInputSetRefs_QNAME, Object.class, OutputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="whileExecutingInputRefs", scope=InputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createInputSetWhileExecutingInputRefs(Object value)
  {
    return new JAXBElement(_InputSetWhileExecutingInputRefs_QNAME, Object.class, InputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="outputSetRefs", scope=InputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createInputSetOutputSetRefs(Object value)
  {
    return new JAXBElement(_InputSetOutputSetRefs_QNAME, Object.class, InputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="optionalInputRefs", scope=InputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createInputSetOptionalInputRefs(Object value)
  {
    return new JAXBElement(_InputSetOptionalInputRefs_QNAME, Object.class, InputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="dataInputRefs", scope=InputSet.class)
  @XmlIDREF
  public JAXBElement<Object> createInputSetDataInputRefs(Object value)
  {
    return new JAXBElement(_InputSetDataInputRefs_QNAME, Object.class, InputSet.class, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", name="flowNodeRef", scope=Lane.class)
  @XmlIDREF
  public JAXBElement<Object> createLaneFlowNodeRef(Object value)
  {
    return new JAXBElement(_LaneFlowNodeRef_QNAME, Object.class, Lane.class, value);
  }
}