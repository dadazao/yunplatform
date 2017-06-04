package com.cloudstong.platform.third.bpm.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.third.bpm.dao.BpmProStatusDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeSign;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.service.BpmNodeSignService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.service.ISignComplete;
import com.cloudstong.platform.third.bpm.service.TaskSignDataService;

public class SignComplete implements ISignComplete {
	private Logger logger = LoggerFactory.getLogger(SignComplete.class);

	@Resource
	private BpmService bpmService;

	@Resource
	private TaskSignDataService taskSignDataService;

	@Resource
	private BpmNodeSignService bpmNodeSignService;

	@Resource
	private BpmProStatusDao bpmProStatusDao;

	public boolean isComplete(ActivityExecution execution) {
		logger.debug("entert the SignComplete isComplete method...");

		String nodeId = execution.getActivity().getId();
		String actInstId = execution.getProcessInstanceId();

		boolean isCompleted = false;
		String signResult = "";
		ProcessDefinition processDefinition = bpmService.getProcessDefinitionByProcessInanceId(actInstId);

		BpmNodeSign bpmNodeSign = bpmNodeSignService.getByDefIdAndNodeId(processDefinition.getId(), nodeId);

		Integer completeCounter = (Integer) execution.getVariable("nrOfCompletedInstances");

		Integer instanceOfNumbers = (Integer) execution.getVariable("nrOfInstances");

		Short approvalStatus = (Short) execution.getVariable("approvalStatus_" + nodeId);

		Long orgId = Long.valueOf(0L);

		SysOrg curOrg = new SysOrg();//ContextUtil.getCurrentOrg();

		if (curOrg != null) {
			orgId = curOrg.getId();
		}

		if ((approvalStatus.shortValue() == 5) || (approvalStatus.shortValue() == 6)) {
			isCompleted = true;
			if (approvalStatus.shortValue() == 5)
				signResult = "pass";
			else {
				signResult = "refuse";
			}

		} else {
			boolean isOneVote = bpmNodeSignService.checkNodeSignPrivilege(processDefinition.getId(), nodeId,
					BpmNodeSignService.BpmNodePrivilegeType.ALLOW_ONE_VOTE, AppContext.getCurrentUserId(), orgId);
			if ((isOneVote) && (!execution.hasVariable("resultOfSign_" + nodeId))) {
				execution.setVariable("resultOfSign_" + nodeId, approvalStatus);
			}

			Short oneVoteResult = null;
			if (execution.hasVariable("resultOfSign_" + nodeId)) {
				oneVoteResult = (Short) execution.getVariable("resultOfSign_" + nodeId);
			}

			VoteResult voteResult = calcResult(bpmNodeSign, actInstId, nodeId, completeCounter, instanceOfNumbers, oneVoteResult);

			signResult = voteResult.getSignResult();
			isCompleted = voteResult.getIsComplete();
		}

		if (isCompleted) {
			bpmService.delLoopAssigneeVars(execution.getId());
			logger.debug("set the sign result + " + signResult);

			taskSignDataService.batchUpdateCompleted(actInstId, nodeId);

			Short status = TaskOpinion.STATUS_PASSED;
			if (signResult.equals("refuse")) {
				status = TaskOpinion.STATUS_NOT_PASSED;
			}

			execution.setVariable("signResult_" + nodeId, signResult);

			bpmProStatusDao.updStatus(new Long(actInstId), nodeId, status);
		}

		return isCompleted;
	}

	private VoteResult calcResult(BpmNodeSign bpmNodeSign, String actInstId, String nodeId, Integer completeCounter, Integer instanceOfNumbers,
			Short oneVoteResult) {
		VoteResult voteResult = new VoteResult();

		Integer agreeAmount = taskSignDataService.getAgreeVoteCount(actInstId, nodeId);

		Integer refuseAmount = taskSignDataService.getRefuseVoteCount(actInstId, nodeId);

		if (bpmNodeSign == null) {
			voteResult = getResultNoRule(oneVoteResult, refuseAmount, agreeAmount, instanceOfNumbers);
			return voteResult;
		}

		voteResult = getResultByRule(bpmNodeSign, oneVoteResult, agreeAmount, refuseAmount, completeCounter, instanceOfNumbers);
		return voteResult;
	}

	private VoteResult getResultByRule(BpmNodeSign bpmNodeSign, Short oneVoteResult, Integer agreeAmount, Integer refuseAmount,
			Integer completeCounter, Integer instanceOfNumbers) {
		VoteResult voteResult = new VoteResult();

		boolean isDirect = bpmNodeSign.getFlowMode().shortValue() == 1;

		if (oneVoteResult != null) {
			String result = oneVoteResult.shortValue() == 1 ? "pass" : "refuse";

			if (isDirect) {
				voteResult = new VoteResult(result, true);
			} else if (completeCounter.equals(instanceOfNumbers)) {
				voteResult = new VoteResult(result, true);
			}
			return voteResult;
		}

		if (BpmNodeSign.VOTE_TYPE_PERCENT.equals(bpmNodeSign.getVoteType())) {
			voteResult = getResultByPercent(bpmNodeSign, agreeAmount, refuseAmount, instanceOfNumbers, completeCounter);
		} else {
			voteResult = getResultByVotes(bpmNodeSign, agreeAmount, refuseAmount, instanceOfNumbers, completeCounter);
		}

		return voteResult;
	}

	private VoteResult getResultNoRule(Short oneVoteResult, Integer refuseAmount, Integer agreeAmount, Integer instanceOfNumbers) {
		VoteResult voteResult = new VoteResult();

		if (oneVoteResult != null) {
			if (oneVoteResult.shortValue() == 1) {
				voteResult.setSignResult("pass");
			} else {
				voteResult.setSignResult("refuse");
			}
			voteResult.setIsComplete(true);
		} else if (refuseAmount.intValue() > 0) {
			voteResult.setSignResult("refuse");
			voteResult.setIsComplete(true);
		} else if (agreeAmount.equals(instanceOfNumbers)) {
			voteResult.setSignResult("pass");
			voteResult.setIsComplete(true);
		}

		return voteResult;
	}

	private VoteResult getResultByVotes(BpmNodeSign bpmNodeSign, Integer agree, Integer refuse, Integer instanceOfNumbers, Integer completeCounter) {
		boolean isComplete = instanceOfNumbers.equals(completeCounter);
		VoteResult voteResult = new VoteResult();
		String result = "";
		boolean isDirect = bpmNodeSign.getFlowMode().shortValue() == 1;
		boolean isPass = false;

		if (BpmNodeSign.DECIDE_TYPE_PASS.equals(bpmNodeSign.getDecideType())) {
			if (agree.intValue() >= bpmNodeSign.getVoteAmount().longValue()) {
				result = "pass";
				isPass = true;
			} else {
				result = "refuse";
			}

		} else if (refuse.intValue() >= bpmNodeSign.getVoteAmount().longValue()) {
			result = "refuse";
			isPass = true;
		} else {
			result = "pass";
		}

		if ((isDirect) && (isPass)) {
			voteResult = new VoteResult(result, true);
		} else if (isComplete) {
			voteResult = new VoteResult(result, true);
		}
		return voteResult;
	}

	private VoteResult getResultByPercent(BpmNodeSign bpmNodeSign, Integer agree, Integer refuse, Integer instanceOfNumbers, Integer completeCounter) {
		boolean isComplete = instanceOfNumbers.equals(completeCounter);
		VoteResult voteResult = new VoteResult();
		String result = "";
		boolean isPass = false;
		boolean isDirect = bpmNodeSign.getFlowMode().shortValue() == 1;
		float percents = 0.0F;

		if (BpmNodeSign.DECIDE_TYPE_PASS.equals(bpmNodeSign.getDecideType())) {
			percents = agree.intValue() / instanceOfNumbers.intValue();

			if (percents * 100.0F >= (float) bpmNodeSign.getVoteAmount().longValue()) {
				result = "pass";
				isPass = true;
			} else {
				result = "refuse";
			}
		} else {
			percents = refuse.intValue() / instanceOfNumbers.intValue();

			if (percents * 100.0F >= (float) bpmNodeSign.getVoteAmount().longValue()) {
				result = "refuse";
				isPass = true;
			} else {
				result = "pass";
			}
		}

		if ((isDirect) && (isPass)) {
			voteResult = new VoteResult(result, true);
		} else if (isComplete) {
			voteResult = new VoteResult(result, true);
		}
		return voteResult;
	}

	class VoteResult {
		private String signResult = "";

		private boolean isComplete = false;

		public VoteResult() {
		}

		public VoteResult(String signResult, boolean isComplate) {
			this.signResult = signResult;
			isComplete = isComplate;
		}

		public String getSignResult() {
			return signResult;
		}

		public void setSignResult(String signResult) {
			this.signResult = signResult;
		}

		public boolean getIsComplete() {
			return isComplete;
		}

		public void setIsComplete(boolean isComplete) {
			this.isComplete = isComplete;
		}
	}
}