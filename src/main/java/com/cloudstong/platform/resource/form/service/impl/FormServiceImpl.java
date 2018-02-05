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
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.form.dao.FormDao;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.useinfo.dao.UseInfoDao;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:表单服务接口实现类
 */
@Repository("formService")
public class FormServiceImpl implements FormService {

	@Resource
	private FormDao formDao;

	@Resource
	private UseInfoDao useInfoDao;

	@Resource
	private SeqcodeDao seqcodeDao;

	public FormDao getFormDao() {
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
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
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long doSaveForm(Form form) {
		return formDao.insert(form);
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteForm(Long id) {
		// 查看表单是否被列表引用，被引用则不能删除
		List list = this.formDao.findTabuByFormId(id);
		if (list.size() > 0) {
			throw new AppException("选中的部分表单未能成功删除，正被列表引用！");
		}
		Form _form = this.findFormById(id);
		seqcodeDao.deleteSeq(_form.getCode());
		formDao.delete(id);
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doUpdateForm(Form form) {
		formDao.update(form);
	}

	@Override
	public Form findFormById(Long id) {
		return formDao.selectById(id);
	}

	@Override
	public List<Form> queryForm(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select b.*,t.tbl_tableZhName as tbl_tableChName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_biaodan b left join sys_tables t on "
						+ "b.tbl_zhubiao = t.tbl_tableName left join sys_user u on b.comm_createBy = u.id  left join sys_user u2 on b.comm_updateBy = u2.id  where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and b." + entry.getKey() + " like ? ");
			param.add(entry.getValue());
		}
		sql.append("order by b.comm_updateDate desc");
		return this.formDao.select(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	public int countForm(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_biaodan b where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and b." + entry.getKey() + " like ? ");
			param.add(entry.getValue());
		}
		sql.append("order by b.id desc");
		return formDao.count(sql.toString(), param.toArray());
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteForms(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			// 查看表单是否被列表引用，被引用则不能删除
			List list = this.formDao.findTabuByFormId(id);
			if (list.size() > 0) {
				throw new AppException("选中的部分表单未能成功删除，正被列表引用！");
			}
			// 删除表单对应的编码
			Form _form = this.findFormById(id);
			seqcodeDao.deleteSeq(_form.getCode());

			this.formDao.delete(id);
		}
	}

	@Override
	public List queryFormColumn(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_dataType as tbl_datatype,c.tbl_columnZhName as tbl_ziduanmingzh,"
						+ "c.tbl_columnName as tbl_ziduanming,c.tbl_length as tbl_length,c.tbl_comment as tbl_colcomment,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b "
						+ "on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and a." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by a.tbl_biaodanxianshishunxu desc");
		return this.formDao.selectFormColumn(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());

	}

	@Override
	public FormColumn findFormColumnById(Long id) {
		return this.formDao.selectFormColumnById(id);
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doUpdateFormColumn(FormColumn formColumn) {
		this.formDao.updateFormColumn(formColumn);
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long doSaveFormColumn(FormColumn formColumn) {
		return this.formDao.insertFormColumn(formColumn);

	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteFormColumns(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			// 删除字段时删除编码表中对应的编码
			FormColumn formColumn = this.formDao.selectFormColumnById(id);
			this.useInfoDao.deleteUseInfo(id, 1);
			this.formDao.deleteColumn(id);
		}
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteFormColumn(Long id) {
		// 删除字段时删除编码表中对应的编码
		FormColumn formColumn = this.formDao.selectFormColumnById(id);
		try {
			seqcodeDao.deleteSeq(formColumn.getCode().split(",")[0]);
			seqcodeDao.deleteSeq(formColumn.getCode().split(",")[1]);
		} catch (Exception e) {
			seqcodeDao.deleteSeq(formColumn.getCode());
		}

		this.useInfoDao.deleteUseInfo(id, 1);
		this.formDao.deleteColumn(id);
	}

	@Override
	public Long getCurrentFormId() {
		return formDao.selectMaxId();
	}

	@Override
	@Cacheable(value = "formCache", key = "'findFormByIdAndDomainVO:'+#id+#domains.toString()+#user.id")
	public Form findFormByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) throws Exception {
		return formDao.selectByIdAndDomainVO(id, domains, user);
	}

	@Override
	public int countFormColumn(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_biaodansheji b where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and b." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		return formDao.countFormColumn(sql.toString(), param.toArray());
	}

	@Override
	@Cacheable(value = "formCache", key = "'findFormBySeqcode:'+#seqcode")
	public Form findFormBySeqcode(String seqcode) {
		return formDao.selectFormBySeqcode(seqcode);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'getFormByIdAndDomainVO:'+#id+#domains.toString()+#user.id")
	public Form getFormByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) {
		return formDao.getByIdAndDomainVO(id, domains, user);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findDomainById:'+#model+#tabId+#partitionId+#subDomainId+#user.id")
	public Partition findDomainById(String model, Long tabId, Long partitionId, Long subDomainId, SysUser user) {
		return formDao.findDomainById(model, tabId, partitionId, subDomainId, user);
	}

	@Override
	@Cacheable(value = "formCache", key = "'getEditFormByIdAndDomainVO:'+#formId+#domainVOs.toString()+#user.id")
	public Form getEditFormByIdAndDomainVO(Long formId, List<DomainVO> domainVOs, SysUser user) {
		return formDao.getEditByIdAndDomainVO(formId, domainVOs, user);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findCompById:'+#compexId+#inputType")
	public List findCompById(Long compexId, int inputType) {
		return formDao.getCompById(compexId, inputType);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findIsUse:'+#compexId+#inputType")
	public List findIsUse(Long compexId, String inputType) {
		return formDao.findIsUse(compexId, inputType);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findIsUseButtonGroup:'+#buttonGroupId")
	public String findIsUseButtonGroup(Long buttonGroupId) {
		return formDao.findIsUseButtonGroup(buttonGroupId);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findIsUseButton:'+#buttonId")
	public String findIsUseButton(Long buttonId) {
		return formDao.findIsUseButton(buttonId);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findIsUseQuery:'+#queryId")
	public String findIsUseQuery(Long queryId) {
		return formDao.findIsUseQuery(queryId);
	}

	@Override
	public String findIsUseList(Long listId) {
		return formDao.findIsUseList(listId);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findIsUseListControl:'+#controlId+#columnname")
	public String findIsUseListControl(Long controlId, String columnname) {
		return formDao.findIsUseListControl(controlId, columnname);
	}

	@Override
	@Cacheable(value = "formCache", key = "'findDefaultCompex:'+#inputType")
	public Long findDefaultCompex(int inputType) {
		return formDao.getDefaultCompex(inputType);
	}

	@Override
	@Cacheable(value = "formCache")
	public int countColumnInForm(Long columnId) {
		return formDao.countColumnInForm(columnId);
	}

	@Override
	@Cacheable(value = "formCache")
	public Form findSimpleFormById(Long id) {
		return formDao.findSimpleFormById(id);
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormColumn> findFormColumnByTabId(Long tabId) {
		return formDao.selectByTabId(tabId);
	}

	@Override
	@Cacheable(value = "formCache")
	public List<FormColumn> findFormColumnByTabAndPid(Long tabId, Long pId) {
		return formDao.selectByTabAndPid(tabId, pId);
	}

	@Override
	@Cacheable(value = "formCache")
	public List findReplyColumn(FormColumn formColumn) {
		return formDao.findReplyColumn(formColumn);
	}

	@Override
	@Cacheable(value = "formCache")
	public List<Form> queryFormByTableid(Long tableid) {
		return formDao.queryFormByTableid(tableid);
	}

	@Override
	@Cacheable(value = "formCache")
	public String findIsUseAdvQuery(Long queryId) {
		return formDao.findIsUseAdvQuery(queryId);
	}

	@Override
	@Cacheable(value = "formCache")
	public List findFormColumnByFormId(Long id) {
		return formDao.findFormColumnByFormId(id);
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doLogicDeleteForm(Long id) {
		// 查看表单是否被列表引用，被引用则不能删除
		List list = this.formDao.findTabuByFormId(id);
		if (list.size() > 0) {
			throw new AppException("选中的部分表单未能成功删除，正被列表引用！");
		}
		Form _form = this.findFormById(id);
		seqcodeDao.logicDeleteSeq(_form.getCode());
		formDao.logicDelete(id);
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doLogicDeleteForms(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			// 查看表单是否被列表引用，被引用则不能删除
			List list = this.formDao.findTabuByFormId(id);
			if (list.size() > 0) {
				throw new AppException("选中的部分表单未能成功删除，正被列表引用！");
			}
			// 删除表单对应的编码
			Form _form = this.findFormById(id);
			seqcodeDao.logicDeleteSeq(_form.getCode());

			this.formDao.logicDelete(id);
		}
	}

}
