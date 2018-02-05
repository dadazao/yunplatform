package com.cloudstong.platform.third.bpm.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.BpmRunLogDao;
import com.cloudstong.platform.third.bpm.dao.ProcessRunDao;
import com.cloudstong.platform.third.bpm.model.BpmRunLog;
import com.cloudstong.platform.third.bpm.model.ProcessRun;

@Service
public class BpmRunLogService {

	@Resource
	private BpmRunLogDao dao;

	@Resource
	private ProcessRunDao processRunDao;

	public List<BpmRunLog> getByUserId(Long userId) {
		List list = dao.getByUserId(userId);
		return list;
	}

	public List<BpmRunLog> getByRunId(Long runId) {
		List list = dao.getByRunId(runId);
		return list;
	}

	public void addRunLog(Long runId, Integer operatortype, String memo) {
		ProcessRun processRun = processRunDao.getById(runId);
		addRunLog(processRun, operatortype, memo);
	}

	public void addRunLog(SysUser user, Long runId, Integer operatortype, String memo) {
		ProcessRun processRun = processRunDao.getById(runId);
		addRunLog(processRun, operatortype, memo);
	}

	public void addRunLog(ProcessRun processRun, Integer operatortype, String memo) {
		SysUser user = AppContext.getCurrentUser();
		Long userId = 1L;
		String userName = "系统";
		if (user != null) {
			userId = user.getId();
			userName = user.getFullname();
		}
		Date now = Calendar.getInstance().getTime();
		BpmRunLog runLog = new BpmRunLog();
		runLog.setId(UniqueIdUtil.genId());
		runLog.setUserid(userId);
		runLog.setUsername(userName);
		runLog.setRunid(processRun.getRunId());
		runLog.setProcessSubject(processRun.getSubject());
		runLog.setCreatetime(now);
		runLog.setOperatortype(operatortype);
		runLog.setMemo(memo);

		dao.save(runLog);
	}

	public void addRunLog(SysUser user, ProcessRun processRun, Integer operatortype, String memo) {
		BpmRunLog runLog = new BpmRunLog();
		runLog.setId(UniqueIdUtil.genId());
		runLog.setUserid(user.getId());
		runLog.setUsername(user.getFullname());
		runLog.setRunid(processRun.getRunId());
		runLog.setProcessSubject(processRun.getSubject());
		runLog.setCreatetime(new Date());
		runLog.setOperatortype(operatortype);
		runLog.setMemo(memo);

		dao.save(runLog);
	}

	public List<BpmRunLog> getAll(QueryCriteria queryCriteria) {
		return dao.getAll(queryCriteria);
	}
	
	public PageResult query(QueryCriteria queryCriteria) {
		return dao.query(queryCriteria);
	}

	public void delByIds(Long[] selectedIDs) {
		for(Long id:selectedIDs) {
			dao.delById(id);
		}
	}

	public BpmRunLog getById(Long runLogId) {
		return (BpmRunLog)dao.getById(runLogId);
	}
}