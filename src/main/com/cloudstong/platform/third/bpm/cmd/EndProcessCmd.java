package com.cloudstong.platform.third.bpm.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskManager;

public class EndProcessCmd implements Command<Void> {
	private String taskId = "";

	public EndProcessCmd(String taskId) {
		this.taskId = taskId;
	}

	public Void execute(CommandContext context) {
		TaskEntity task = context.getTaskManager().findTaskById(taskId);
		String executionId = task.getExecutionId();

		ExecutionEntity executionEntity = context.getExecutionManager().findExecutionById(executionId);
		ExecutionEntity parentEnt = getTopExecution(executionEntity);

		parentEnt.end();
		return null;
	}

	private ExecutionEntity getTopExecution(ExecutionEntity executionEntity) {
		ExecutionEntity parentEnt = executionEntity.getParent();
		if (parentEnt == null) {
			return executionEntity;
		}
		return getTopExecution(parentEnt);
	}
}