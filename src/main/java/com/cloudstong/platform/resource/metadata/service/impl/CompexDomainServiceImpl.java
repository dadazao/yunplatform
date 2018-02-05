package com.cloudstong.platform.resource.metadata.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.resource.attachment.dao.AttachmentDao;
import com.cloudstong.platform.resource.attachment.model.Attachment;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.metadata.dao.CompexDomainDao;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.metadata.service.RelationService;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;

/**
 * @author michael Created on 2012-11-13
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description: 配置服务接口实现类
 */
@Repository("compexDomainService")
public class CompexDomainServiceImpl implements CompexDomainService {

	@Resource
	private CompexDomainDao compexDomainDao;

	@Resource
	private AttachmentDao attachmentDao;

	@Resource
	private RelationService relationService;
	@Resource
	protected SeqcodeService seqcodeService;
	@Resource
	protected CatalogService catalogService;
	/**
	 * 操作列表的服务接口,<code>tabulationService</code> 对象是TabulationService接口的一个实例
	 */
	@Resource
	protected TabulationService tabulationService;
	/**
	 * 操作表单的服务接口,<code>formService</code> 对象是FormService接口的一个实例
	 */
	@Resource
	protected FormService formService;

	@Resource
	protected IUserService userService;

	@Override
	public List<Long> doSaveOrUpdateDomain(List<Domain> domains, List<String> tables, List<DomainVO> vos, SysUser user) {
		DomainVO domainVO = null;
		if (vos != null && vos.size() > 0) {
			domainVO = vos.get(0);
		}
		List<Long> ids = new ArrayList<Long>();
		for (Domain domain : domains) {
			Long rid;
			if (domain.getId() != null && !domain.getId().equals("")) {
				// 执行更新操作
				doUpdateDomain(domain, user);
				ids.add(domain.getId());
				rid = domain.getId();
			} else {
				Long id = doSaveDomain(domain, user);
				ids.add(id);
				// 插入中间表
				if (domainVO != null && !domainVO.getTable().equalsIgnoreCase(tables.get(0))) {
					compexDomainDao.insertMiddleTable(StringUtil.getTableString(domainVO.getTable(), tables.get(0), 14), domainVO.getDomainId(), id);
				}
				rid = id;
			}

			// 查找表单中多文件上传的文件
			for (FormColumnExtend formColumnExtend : domain.getFormColumnExtends()) {
				if (formColumnExtend.getFormColumn().getInputType() == Constants.INPUT_TYPE_TEXTBOX_UPLOADIFY) {
					String uploadifyValue = String.valueOf(formColumnExtend.getValue());
					attachmentDao.updateRecordId(uploadifyValue, rid);
				}
			}
			// 修改记录时判断是否上传了附件，附件表（sys_attachment）中是否有此条记录的信息
			List<Attachment> attachments = attachmentDao.findAttachmentsByRid(rid);
			if (attachments.size() > 0) {
				for (int i = 0; i < attachments.size(); i++) {
					Attachment attachment = attachments.get(i);
					attachmentDao.updateAttachment(attachment, domain, rid);
				}
			}
		}
		return ids;
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public Long doSaveDomain(Domain domain, SysUser user) {
		return compexDomainDao.insert(domain, user);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateDomain(Domain domain, SysUser user) {
		compexDomainDao.update(domain, user);
	}

	/**
	 * 为目录添加基本码，表单，列表添加扩展码和上级码
	 */
	public void doGeneratorCode(Long currentSaveId, List<Domain> domains, Form form, HttpServletRequest request) {
		if (form.getTableName().equalsIgnoreCase("sys_catalog")) {
			Domain domain = domains.get(0);
			if (domain.getId() == null || domain.getId().equals(0l)) {
				Seqcode seqcode = new Seqcode();
				seqcode.setTblName(request.getParameter("sys_catalog-tbl_name"));
				seqcode.setTblSystematom(Constants.SYSTEM_ATOM_FUNC);

				String bianma = seqcodeService.doSaveSeqcode(seqcode);
				List<Seqcode> _seqlist = seqcodeService.querySeqByValue(bianma);
				Seqcode _seqcode = null;
				if (_seqlist.size() > 0) {
					_seqcode = _seqlist.get(0);
					userService.addOrUpdateUserSeq(request.getSession(), _seqcode);
				}
				doUpdateCode("sys_catalog", "tbl_bianma", currentSaveId, bianma);
				// 保存上级码和扩展码
				String _parentId = request.getParameter("sys_catalog-tbl_parentId");
				Catalog _parentCata = catalogService.findCatalogById(Long.valueOf(_parentId));
				seqcodeService.doUpdateSeq(bianma, _parentCata.getCode());
				seqcodeService.doUpdateSeqExtends(bianma, _parentCata.getCode());

				Long listid = Long.valueOf(request.getParameter("sys_catalog-tbl_listid"));
				String type = request.getParameter("sys_catalog-tbl_type");
				Catalog catalog = catalogService.findCatalogById(Long.valueOf(currentSaveId));
				// 目录表中保存关联的列表id
				catalogService.doUpdateCatalogList(currentSaveId, listid);
				if (listid != null && !listid.equals(0l) && !listid.equals("undefined") && !listid.equals(-1l) && "1".equals(type)) {
					// 给列表加上级码
					Tabulation _tabulation = tabulationService.findTabulationById(listid);
					seqcodeService.doUpdateSeq(_tabulation.getCode(), catalog.getCode());
					seqcodeService.doUpdateSeqExtends(_tabulation.getCode(), catalog.getCode());
					// 给表单加上级码
					if (_tabulation.getFormId() != null && !_tabulation.getFormId().equals(-1l)) {
						Form _form = formService.findFormById(_tabulation.getFormId());
						seqcodeService.doUpdateSeq(_form.getCode(), catalog.getCode());
						seqcodeService.doUpdateSeqExtends(_form.getCode(), catalog.getCode());
					}
				}
			} else {
				String listid = request.getParameter("sys_catalog-tbl_listid");
				Catalog catalog = catalogService.findCatalogById(Long.valueOf(domain.getId()));
				seqcodeService.doUpdateSeqName(catalog.getCode(), catalog.getName());// 更新编码表中的名称

				// 保存上级码和扩展码
				Catalog _parentCata = catalogService.findCatalogById(Long.valueOf(catalog.getParentId()));
				seqcodeService.doUpdateSeq(catalog.getCode(), _parentCata.getCode());
				seqcodeService.doUpdateSeqExtends(catalog.getCode(), _parentCata.getCode());

				String type = request.getParameter("sys_catalog-tbl_type");
				
				if (listid != null && !listid.equals(0l) && !listid.equals("undefined") && !listid.equals("-1") && "1".equals(type)) {
					Long lid = Long.valueOf(listid);
					// 目录表中保存关联的列表id
					catalogService.doUpdateCatalogList(currentSaveId, lid);
					// 给列表加上级码
					Tabulation _tabulation = tabulationService.findTabulationById(lid);
					seqcodeService.doUpdateSeq(_tabulation.getCode(), catalog.getCode());
					seqcodeService.doUpdateSeqExtends(_tabulation.getCode(), catalog.getCode());
					// 给表单加上级码
					if (_tabulation.getFormId() != null && !_tabulation.getFormId().equals(-1l)) {
						Form _form = formService.findFormById(_tabulation.getFormId());
						seqcodeService.doUpdateSeq(_form.getCode(), catalog.getCode());
						seqcodeService.doUpdateSeqExtends(_form.getCode(), catalog.getCode());
					}
				}
			}
		}
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDeleteDomains(List<DomainVO> ds, Long formId, SysUser user) {
		if (ds.size() > 1) {
			// 删除主表
			compexDomainDao.delete(ds.get(0).getTable(), ds.get(0).getDomainId(), formId, user);
			for (int i = 1; i < ds.size(); i++) {
				// 删除中间表
				compexDomainDao.deleteMiddleTable(StringUtil.getTableString(ds.get(0).getTable(), ds.get(i).getTable(), 14), ds.get(0).getDomainId(),
						ds.get(i).getDomainId());
				// 删除次表
				compexDomainDao.delete(ds.get(0).getTable(), ds.get(0).getDomainId(), formId, user);
			}
		} else if (ds.size() == 1) {
			// 删除主表
			compexDomainDao.delete(ds.get(0).getTable(), ds.get(0).getDomainId(), formId, user);
		}
	}

	@Override
	@Cacheable(value = "formCache", key = "'findSubListColumn:'+#tabId+#partitionId")
	public List<FormColumnExtend> findSubListColumn(Long tabId, Long partitionId) {
		return compexDomainDao.selectSubListColumn(tabId, partitionId);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'queryDomain:'+#queryCriteria+#tabId+#partitionId+#vo.domainId+#user.id")
	public List<Domain> queryDomain(QueryCriteria queryCriteria, Long tabId, Long partitionId, DomainVO vo, SysUser user) {
		return compexDomainDao.selectDomains(queryCriteria, tabId, partitionId, vo, user);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'countDomain:'+#domain+#vo.domainId")
	public int countDomain(String domain, DomainVO vo) {
		return compexDomainDao.count(domain, vo);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'singleCountDomain:'+#domain+#vo.domainId+#relColumnId")
	public int singleCountDomain(String domain, DomainVO vo, Long relColumnId) {
		return compexDomainDao.singleCount(domain, vo, relColumnId);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doSaveSub(Domain domain, Long domainId, String domainTable, SysUser user) {
		this.compexDomainDao.doSaveSub(domain, domainId, domainTable, user);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateSub(Domain domain, SysUser user) {
		this.compexDomainDao.doUpdateSub(domain, user);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDeleteDomains(List<DomainVO> ds, String mainTable, Long listId, SysUser user) {
		if (ds.size() > 0) {
			Relation relation = relationService.selectByMainNameAndSubName(mainTable, ds.get(0).getTable());
			if (relation.getRelationType().equals(Constants.RELATION_1_N)) {// 一对多删除
				compexDomainDao.delete(ds.get(0).getTable(), ds.get(0).getDomainId(), listId, user);
				compexDomainDao.deleteTemp(StringUtil.getTableString(mainTable, ds.get(0).getTable(), 14), ds.get(0).getDomainId());
			} else if (relation.getRelationType().equals(Constants.RELATION_M_N)) {// 多对多删除
				compexDomainDao.deleteTemp(StringUtil.getTableString(mainTable, ds.get(0).getTable(), 14), ds.get(0).getDomainId());
			}
		}
	}

	@Override
	@Cacheable(value = "domainCache", key = "'queryTableData:'+#psTableName+#psParams")
	public List queryTableData(String psTableName, String psParams) {
		return compexDomainDao.queryTableData(psTableName, psParams);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDeleteTableData(String psTableName, String psParams) {
		compexDomainDao.deleteTableData(psTableName, psParams);
	}

	@Override
	public List findReplyResult(String belongTable, String columnName, String value, Long id) {
		return compexDomainDao.findReplyResult(belongTable, columnName, value, id);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDefault(String mainTable, String column, Long psId) {
		compexDomainDao.setdefault(mainTable, column, psId);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doPassed(String tableName, Long recordId) {
		compexDomainDao.passed(tableName, recordId);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doRecall(String tableName, Long recordId) {
		compexDomainDao.recall(tableName, recordId);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateCode(String tableName, String columnName, Long id, String code) {
		compexDomainDao.updateCode(tableName, columnName, id, code);

	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doRemoveFile(String model, String column, Long fileid, Long recordid) {
		compexDomainDao.removeFile(model, column, fileid, recordid);
	}

	@Override
	public List findTreeTypeReply(String _tableName, String _name, Long _parentId, Long _domainId) {
		return compexDomainDao.findTreeTypeReply(_tableName, _name, _parentId, _domainId);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateMainInfo(String tableName, Long mainId, SysUser user) {
		compexDomainDao.updateMainInfo(tableName, mainId, user);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTableData(String tableName, String columnName, String value, Long id) {
		compexDomainDao.updateTableData(tableName, columnName, value, id);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTableData(String tableName, List<String> columnNames, List<String> values, Long id) {
		compexDomainDao.updateTableData(tableName, columnNames, values, id);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public int doSaveDomainList(List<Domain> domainList, SysUser user) {
		Assert.notNull(domainList, "参数非法");
		Assert.notNull(user, "用户信息错误，非法操作");
		int i = 0;
		for (Domain domain : domainList) {
			try {
				compexDomainDao.insert(domain, user);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			i++;
		}
		return i;
	}

	@Override
	@Cacheable(value = "domainCache", key = "'querySingleDomain:'+#queryCriteria+#tabId+#vo+#user.id")
	public List<Domain> querySingleDomain(QueryCriteria queryCriteria, Long tabId, DomainVO vo, SysUser user) {
		return compexDomainDao.selectSingleDomain(queryCriteria, tabId, vo, user);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'queryDomain:'+#queryCriteria+#model+#user.id")
	public List<Domain> queryDomain(QueryCriteria queryCriteria, String model, SysUser user) {
		return compexDomainDao.selectDomains(queryCriteria, model, user);
	}

	@Override
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doLogicDeleteDomains(List<DomainVO> ds, Long formId, SysUser user) {
		if (ds.size() > 1) {
			// 删除主表
			compexDomainDao.logicDelete(ds.get(0).getTable(), ds.get(0).getDomainId(), formId, user);
			for (int i = 1; i < ds.size(); i++) {
				// 删除次表
				compexDomainDao.logicDelete(ds.get(0).getTable(), ds.get(0).getDomainId(), formId, user);
			}
		} else if (ds.size() == 1) {
			// 删除主表
			compexDomainDao.logicDelete(ds.get(0).getTable(), ds.get(0).getDomainId(), formId, user);
		}

	}

	@Override
	public boolean hasDefaultCol(String model) {
		return compexDomainDao.hasDefaultCol(model);
	}

	@Override
	public boolean isDefaultData(String model, String column, Long domainId) {
		return compexDomainDao.isDefaultData(model, column, domainId);
	}

	@Override
	public void doSaveFilter(String filterName, String tableName, String queryCriteria) {
		compexDomainDao.saveFileter(filterName, tableName, queryCriteria);
	}

	@Override
	public Object queryColumnValue(String model, String column, Long domainId) {
		return compexDomainDao.queryColumnValue(model, column, domainId);
	}
	
	

}
