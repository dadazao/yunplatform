package com.cloudstong.platform.resource.date.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.date.dao.DateDao;
import com.cloudstong.platform.resource.date.model.DateControl;
import com.cloudstong.platform.resource.date.service.DateService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:日期组件服务接口实现类
 */
@Repository("dateService")
public class DateServiceImpl implements DateService {

	@Resource
	private DateDao dateDao;

	@Resource
	private SeqcodeService seqcodeService;

	public DateDao getDateDao() {
		return dateDao;
	}

	public void setDateDao(DateDao dateDao) {
		this.dateDao = dateDao;
	}

	public SeqcodeService getSeqcodeService() {
		return seqcodeService;
	}

	public void setSeqcodeService(SeqcodeService seqcodeService) {
		this.seqcodeService = seqcodeService;
	}

	@Override
	public Long doSaveOrUpdate(DateControl date) throws Exception {
		Long result = -1L;
		if (!date.getId().equals("")) {
			date.setUpdateBy(date.getUpdateBy());
			date.setUpdateDate(new java.util.Date(System.currentTimeMillis()));
			result = dateDao.update(date);
		} else {
			date.setId(System.currentTimeMillis());
			date.setStatus(new Integer(0));
			date.setCreateBy(date.getCreateBy());
			date.setCreateDate(new java.util.Date(System.currentTimeMillis()));
			result = dateDao.insert(date);
		}

		return result;
	}

	@Override
	public DateControl findById(Long id) throws Exception {
		return dateDao.findById(id);
	}

	@Override
	public String doDelete(Long id) throws Exception {
		try {
			int result = dateDao.delete(id);
			return (result > 0 ? String.valueOf(id) : "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception {
		StringBuffer where = new StringBuffer();
		List queryForPageList = null;
		List paramList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			if (entry.getValue() instanceof String) {
				where.append(" AND  " + entry.getKey() + " like ?");
				paramList.add("%" + entry.getValue().toString() + "%");
			} else {
				where.append(" AND  " + entry.getKey() + " = ?");
				paramList.add(entry.getValue());
			}

		}
		where.append(" ORDER BY comm_createDate");
		try {
			queryForPageList = dateDao.queryForPageList(where.toString(),
					paramList.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			throw e;
		}
		return queryForPageList;
	}

	@Override
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception {
		StringBuffer where = new StringBuffer();
		int rowCount = 0;
		List paramList = new ArrayList();
		Map<String, Object> paramMap = queryCriteria.getQueryCondition();
		for (Iterator it = paramMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			where.append(" AND  " + entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}
		try {
			rowCount = dateDao.getTotalCount(where.toString(),
					paramList.toArray());
		} catch (Exception e) {
			throw e;
		}
		return rowCount;
	}

	@Override
	public List queryFormList(QueryCriteria qc) throws Exception {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select bd.tbl_biaodanming as tbl_formName,bd.tbl_bianma as tbl_bianma,bd.comm_createDate as tbl_createtime from sys_biaodan bd where bd.id in(select sj.tbl_form from sys_biaodansheji sj where sj.tbl_luruleixing = 6");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if (entry.getKey().equals("compexid")) {
				sql.append(" and sj." + entry.getKey() + " = ? ");
				param.add(entry.getValue());
			}
		}
		sql.append(") ");
		sql.append(" order by bd.id desc");
		return this.dateDao.selectFormList(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	public int countFormList(QueryCriteria qc) throws Exception {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select count(bd.id) as formName from sys_biaodan bd where bd.id in( select sj.tbl_form from sys_biaodansheji sj where sj.tbl_luruleixing = 6");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if (entry.getKey().equals("compexid")) {
				sql.append(" and sj." + entry.getKey() + " = ? ");
				param.add(entry.getValue());
			}
		}
		sql.append(") order by bd.id desc");
		return dateDao.countFormList(sql.toString(), param.toArray());
	}
}
