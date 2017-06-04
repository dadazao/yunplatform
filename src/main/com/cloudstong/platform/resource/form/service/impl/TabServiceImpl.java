package com.cloudstong.platform.resource.form.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.form.dao.TabDao;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.form.service.TabService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选项卡服务接口实现类
 */
@Repository("tabService")
public class TabServiceImpl implements TabService {

	@Resource
	private TabDao tabDao;

	public TabDao getTabDao() {
		return tabDao;
	}

	public void setTabDao(TabDao tabDao) {
		this.tabDao = tabDao;
	}

	@Override
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public Long doSaveTab(Tab tab) {
		return tabDao.insert(tab);
	}

	@Override
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doDeleteTab(Long id) {
		//删除tab页时删除此tab页下的字段信息
		this.tabDao.deleteColumns(id);
		tabDao.delete(id);
	}

	@Override
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTab(Tab tab) {
		tabDao.update(tab);
	}

	@Override
	@Cacheable(value = "formCache")
	public Tab findTabById(Long id) {
		return tabDao.selectById(id);
	}

	@Override
	@Cacheable(value = "formCache")
	public List<Tab> queryTab(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,b.tbl_templatechname as tbl_templatename,b.tbl_templatefilename as tbl_templatefilename from sys_biaodantab a left join sys_template b on a.tbl_muban=b.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and a." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by a.tbl_taborder asc");
		return this.tabDao.select(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	@Cacheable(value = "formCache")
	public int countTab(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select count(*) from sys_biaodantab a where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and a." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by a.id asc");
		return tabDao.count(sql.toString(), param.toArray());
	}

	@Override
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doDeleteTabs(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			//删除tab页时删除此tab页下的字段信息
			this.tabDao.deleteColumns(id);
			this.tabDao.delete(id);
		}
	}

	@Override
	public Long getCurrentTabId() {
		return tabDao.selectMaxId();
	}

	@Override
	@Cacheable(value = "formCache")
	public List findReplyResult(String tableName, String columnName, String tabName,
			Long id, Long formId) {
		return tabDao.findReplyResult(tableName,columnName,tabName,id,formId);
	}

	@Override
	@Cacheable(value = "formCache")
	public List<Tab> findTabListByFormId(Long id) {
		return tabDao.selectByFormId(id);
	}

	@Override
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doUpdateOrder(Tab tab, Long tabId) {
		tabDao.updateOrder(tab,tabId);
	}

	@Override
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTabTable(Tab tab) {
		tabDao.updateTabTable(tab);
	}

}
