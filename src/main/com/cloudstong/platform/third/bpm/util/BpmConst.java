package com.cloudstong.platform.third.bpm.util;

public class BpmConst {
	public static final String START_USER_ID = "startUserId";
	public static final String StartUser = "startUser";
	public static final String PrevUser = "prevUser";
	public static final String StartEvent = "start";
	public static final String EndEvent = "end";
	public static final String CreateEvent = "create";
	public static final String CompleteEvent = "complete";
	public static final String AssignmentEvent = "assignment";
	public static final String PROCESS_EXT_VARNAME = "outPassVars";
	public static final String PROCESS_INNER_VARNAME = "innerPassVars";
	public static final String FLOW_RUN_SUBJECT = "subject_";
	public static final String FLOW_INFORM_TYPE = "informType";
	public static final String IS_EXTERNAL_CALL = "isExtCall";
	public static final Integer StartScript = Integer.valueOf(1);

	public static final Integer EndScript = Integer.valueOf(2);

	public static final Integer ScriptNodeScript = Integer.valueOf(3);

	public static final Integer AssignScript = Integer.valueOf(4);
	public static final String NODE_APPROVAL_STATUS = "approvalStatus";
	public static final String NODE_APPROVAL_CONTENT = "approvalContent";
	public static final Integer TASK_BACK = Integer.valueOf(1);

	public static final Integer TASK_BACK_TOSTART = Integer.valueOf(2);

	public static final Short OnLineForm = Short.valueOf((short) 0);

	public static final Short UrlForm = Short.valueOf((short) 1);
	public static final String FORM_PK_REGEX = "\\{pk\\}";
	public static final String FLOW_BUSINESSKEY = "businessKey";
	public static final String FLOW_RUNID = "flowRunId";
	public static final String SIGN_USERIDS = "signUsers";
	public static final String SUBPRO_MULTI_USERIDS = "subAssignIds";
	public static final String SUBPRO_EXT_MULTI_USERIDS = "subExtAssignIds";
}