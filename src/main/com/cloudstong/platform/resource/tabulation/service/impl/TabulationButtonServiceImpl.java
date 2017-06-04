package com.cloudstong.platform.resource.tabulation.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.tabulation.dao.TabulationButtonDao;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.service.TabulationButtonService;
import com.cloudstong.platform.resource.useinfo.dao.UseInfoDao;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表按钮服务接口实现类
 */
@Repository("tabulationButtonService")
public class TabulationButtonServiceImpl implements TabulationButtonService {
	@Resource
	private TabulationButtonDao tabulationButtonDao;
	@Resource
	private UseInfoDao useInfoDao;
	@Resource
	private SeqcodeDao seqcodeDao;
	
	public TabulationButtonDao getTabulationButtonDao() {
		return tabulationButtonDao;
	}

	public void setTabulationButtonDao(TabulationButtonDao tabulationButtonDao) {
		this.tabulationButtonDao = tabulationButtonDao;
	}

	public UseInfoDao getUseInfoDao() {
		return useInfoDao;
	}

	public void setUseInfoDao(UseInfoDao useInfoDao) {
		this.useInfoDao = useInfoDao;
	}

	public SeqcodeDao getSeqcodeDao() {
		return seqcodeDao;
	}

	public void setSeqcodeDao(SeqcodeDao seqcodeDao) {
		this.seqcodeDao = seqcodeDao;
	}

	@Override
	@CacheEvict(value="tabulationCache",allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationButton(TabulationButton tabulationButton) {
		this.tabulationButtonDao.updateTabulationButton(tabulationButton);
	}

	@Override
	@CacheEvict(value="tabulationCache",allEntries=true,beforeInvocation=true)
	public Long doSaveTabulationButton(TabulationButton tabulationButton) {
		return this.tabulationButtonDao.insertTabulationButton(tabulationButton);
	}

	@Override
	public List queryTabulationButton(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment, l.*,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,b.tbl_biaodanming as tbl_formname from sys_liebiaobutton l left join sys_button t on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodan b on l.tbl_formid = b.id  where 1=1");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and l." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by l.tbl_showorder asc");
		return this.tabulationButtonDao.selectTabulationButton(sql.toString(),
				param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	@Cacheable(value="tabulationCache",key="'countTabulationButton:'+#queryCriteria")
	public int countTabulationButton(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select count(*) from sys_liebiaobutton l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and l." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by l.id desc");
		return tabulationButtonDao.countTabulationButton(sql.toString(),
				param.toArray());
	}

	@Override
	@Cacheable(value="tabulationCache",key="'findTabulationButtonById:'+#id")
	public TabulationButton findTabulationButtonById(Long id) {
		return this.tabulationButtonDao.selectTabulationButtonById(id);
	}

	@Override
	@CacheEvict(value="tabulationCache",allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationButtons(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			TabulationButton tabulationButton = this.findTabulationButtonById(id);
			seqcodeDao.deleteSeq(tabulationButton.getCode());
			this.useInfoDao.deleteUseInfo(id,3);
			this.tabulationButtonDao.deleteTabulationButton(id);
		}
	}

	@Override
	@Cacheable(value="tabulationCache",key="'findTabulationOptById:'+#id")
	public TabulationOpt findTabulationOptById(Long id) {
		return this.tabulationButtonDao.selectTabulationOptById(id);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List findReplyResult(TabulationButton tabulationButton) {
		return this.tabulationButtonDao.findReplyResult(tabulationButton);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List findReplyOptResult(TabulationOpt tabulationOpt) {
		return this.tabulationButtonDao.findReplyOptResult(tabulationOpt);
	}

}
