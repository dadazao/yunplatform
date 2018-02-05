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
import com.cloudstong.platform.resource.form.dao.FormButtonDao;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.service.FormButtonService;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.useinfo.dao.UseInfoDao;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单按钮服务接口实现类
 */
@Repository("formButtonService")
public class FormButtonServiceImpl implements FormButtonService {
	@Resource
	private FormButtonDao formButtonDao;
	@Resource
	private UseInfoDao useInfoDao;
	@Resource
	private SeqcodeDao seqcodeDao;

	public FormButtonDao getFormButtonDao() {
		return formButtonDao;
	}

	public void setFormButtonDao(FormButtonDao formButtonDao) {
		this.formButtonDao = formButtonDao;
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
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public void doUpdateFormButton(FormButton formButton) {
		this.formButtonDao.updateFormButton(formButton);
	}

	@Override
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public Long doSaveFormButton(FormButton formButton) {
		return this.formButtonDao.insertFormButton(formButton);
	}

	@Override
	@Cacheable(value="formCache")
	public List queryFormButton(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select l.*,g.tbl_comment as tbl_buttongroupcomment,t.tbl_comment as tbl_buttoncomment,t.tbl_buttonName as tbl_buttonname,t.tbl_url as tbl_url,g.tbl_buttonGroupName as tbl_buttongroupname,tb.tbl_mingcheng as tbl_tabname from sys_biaodanbutton l left join sys_button t " +
				"on l.tbl_buttonid = t.id left join sys_buttongroup g on l.tbl_buttonid = g.id left join sys_biaodantab tb on tb.id = l.tbl_tabid where 1=1");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and l." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by l.tbl_tabid,l.tbl_showorder asc");
		return this.formButtonDao.selectFormButton(sql.toString(),
				param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	@Cacheable(value="formCache")
	public int countFormButton(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select count(*) from sys_biaodanbutton l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			sql.append(" and l." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		return formButtonDao.countFormButton(sql.toString(), param.toArray());
	}

	@Override
	@Cacheable(value="formCache")
	public FormButton findFormButtonById(Long id) {
		return this.formButtonDao.selectFormButtonById(id);
	}

	@Override
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public void doDeleteFormButtons(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			FormButton _formButton = this.findFormButtonById(id);
			seqcodeDao.deleteSeq(_formButton.getCode());
			this.useInfoDao.deleteUseInfo(id,2);
			this.formButtonDao.deleteFormButton(id);
		}
	}

	@Override
	@Cacheable(value="formCache")
	public List findReplyResult(FormButton formButton) {
		return this.formButtonDao.findReplyResult(formButton);
	}

	@Override
	@Cacheable(value="formCache")
	public List<FormButton> findFormButton(Long formId, Long tabId) {
		return this.formButtonDao.selectByFormId(formId, tabId);
	}
	
	@Override
	@Cacheable(value="formCache")
	public List<FormButton> findFormButton(Long formId) {
		return this.formButtonDao.selectByFormId(formId);
	}

	@Override
	@Cacheable(value="formCache")
	public List<FormButton> findFormButtonByTabAndPid(Long tabId, Long pId) {
		return this.formButtonDao.selectByTabAndPid(tabId, pId);
	}

	@Override
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public void doUpdateOrder(FormButton formButton) {
		this.formButtonDao.updateOrder(formButton);
	}
}
