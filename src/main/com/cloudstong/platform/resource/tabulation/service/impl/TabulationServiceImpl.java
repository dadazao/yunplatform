package com.cloudstong.platform.resource.tabulation.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.combox.dao.ComboxDao;
import com.cloudstong.platform.resource.combox.model.Combox;
import com.cloudstong.platform.resource.dictionary.dao.DictionaryDao;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.form.dao.FormDao;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.listcontrol.dao.ListControlDao;
import com.cloudstong.platform.resource.listcontrol.model.ListControl;
import com.cloudstong.platform.resource.metadata.dao.CompexDomainDao;
import com.cloudstong.platform.resource.metadata.dao.RelationDao;
import com.cloudstong.platform.resource.metadata.dao.TableDao;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.operationmanage.dao.OperationManageDao;
import com.cloudstong.platform.resource.operationmanage.model.OperationManage;
import com.cloudstong.platform.resource.ordermanage.dao.OrderManageDao;
import com.cloudstong.platform.resource.ordermanage.model.OrderManage;
import com.cloudstong.platform.resource.pagemanage.dao.PageManageDao;
import com.cloudstong.platform.resource.pagemanage.model.PageManage;
import com.cloudstong.platform.resource.personChiose.dao.PersonChioseDao;
import com.cloudstong.platform.resource.querycontrol.dao.QueryControlDao;
import com.cloudstong.platform.resource.querycontrol.model.AdvanceQueryControl;
import com.cloudstong.platform.resource.querycontrol.model.QueryControl;
import com.cloudstong.platform.resource.searchcombox.dao.SearchComboxDao;
import com.cloudstong.platform.resource.searchcombox.model.SearchCombox;
import com.cloudstong.platform.resource.selectmanage.dao.SelectManageDao;
import com.cloudstong.platform.resource.selectmanage.model.SelectManage;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.tabulation.dao.TabulationButtonDao;
import com.cloudstong.platform.resource.tabulation.dao.TabulationDao;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationColumn;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.model.TabulationQuery;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.resource.tree.dao.MgrTreeDao;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.resource.useinfo.dao.UseInfoDao;
import com.cloudstong.platform.system.dao.IDataSearchDao;
import com.cloudstong.platform.system.dao.IUserDao;
import com.cloudstong.platform.system.model.DataSearch;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.Role;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:列表服务接口实现类
 */
@Repository("tabulationService")
public class TabulationServiceImpl implements TabulationService {

	@Resource
	private TabulationDao tabulationDao;

	@Resource
	private TabulationButtonDao tabulationButtonDao;
	
	@Resource
	private IUserDao userDao;

	@Resource
	private FormDao formDao;

	@Resource
	private UseInfoDao useInfoDao;

	@Resource
	private SeqcodeDao seqcodeDao;

	@Resource
	private CatalogDao catalogDao;

	@Resource
	private MgrTreeDao mgrTreeDao;

	@Resource
	private ComboxDao comboxDao;

	@Resource
	private SearchComboxDao searchComboxDao;

	@Resource
	private DictionaryDao dictionaryDao;

	@Resource
	private ListControlDao listControlDao;

	@Resource
	private SelectManageDao selectManageDao;

	@Resource
	private QueryControlDao queryControlDao;
	@Resource
	private OrderManageDao orderManageDao;
	@Resource
	private OperationManageDao operationManageDao;
	@Resource
	private PageManageDao pageManageDao;
	@Resource
	private IDataSearchDao dataSearchDao;
	@Resource
	private RelationDao relationDao;
	@Resource
	private CompexDomainDao compexdomainDao;
	
	@Resource
	private PersonChioseDao personChioseDao;
	
	@Resource
	private TableDao tableDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Cacheable(value="tabulationCache",key="'queryTabulation:'+#qc+'List'")
	public List<Tabulation> queryTabulation(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select l.*,m.tbl_templatechname as tbl_templatename,t.tbl_tableZhName as tbl_tableZhName,bd.tbl_biaodanming as tbl_formName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd on l.tbl_formid=bd.id left join sys_user u on l.comm_createBy = u.id left join sys_user u2 on l.comm_updateBy = u2.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and " + entry.getKey() + " like ? ");
			param.add("%" + entry.getValue() + "%");
		}
		sql.append("order by l.comm_updateDate desc");
		return this.tabulationDao.select(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());

	}

	@Override
	@Cacheable(value="tabulationCache")
	public int countTabulation(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_liebiao l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and " + entry.getKey() + " like ? ");
			param.add("%" + entry.getValue() + "%");
		}
		sql.append("order by id desc");
		return compexdomainDao.count(sql.toString(), param.toArray());
	}

	public TabulationDao getTabulationDao() {
		return tabulationDao;
	}

	public void setTabulationDao(TabulationDao tabulationDao) {
		this.tabulationDao = tabulationDao;
	}

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
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public Long doSaveTabulation(Tabulation tabulation) {
		return tabulationDao.insert(tabulation);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulation(Tabulation tabulation) {
		tabulationDao.update(tabulation);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationPath(Tabulation tabulation) {
		tabulationDao.updatePath(tabulation);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List queryTabulationColumn(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_columnName as tbl_ziduanming from "
						+ "sys_liebiaosheji a left join tables b on a.tbl_tableid=b.id left join columns c on a.tbl_ziduan=c.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and a." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by a.id desc");
		return this.tabulationDao.selectTabulationColumn(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	@Cacheable(value="tabulationCache")
	public int countTabulationColumn(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_liebiaosheji l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and l." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by l.id desc");
		return tabulationDao.countTabulationColumn(sql.toString(), param.toArray());
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationColumn(TabulationColumn tabulationColumn) {
		this.tabulationDao.updateTabulationColumn(tabulationColumn);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doSaveTabulationColumn(TabulationColumn tabulationColumn) {
		this.tabulationDao.insertTabulationColumn(tabulationColumn);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulations(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			// 查看表单是否被列表引用，被引用则不能删除
			List list = this.tabulationDao.findCataByListId(id);
			if (list.size() > 0) {
				throw new AppException("选中的部分列表未能成功删除，正被目录引用！");
			}
			// 删除列表对应的编码
			Tabulation _tabulation = this.findTabulationById(id);
			seqcodeDao.deleteSeq(_tabulation.getCode());

			this.tabulationDao.delete(id);
		}
	}

	@Override
	@Cacheable(value="tabulationCache")
	public Tabulation findTabulationById(Long id) {
		return tabulationDao.selectById(id);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationColumns(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			this.tabulationDao.deleteTabulation(id);
		}
	}

	@Override
	@Cacheable(value="tabulationCache")
	public TabulationColumn findTabulationColumnById(Long id) {
		return this.tabulationDao.selectTabulationColumnById(id);
	}

	// @Override
	// public Tabulation findTabulationById(Long id, QueryCriteria qc,User
	// user) {
	// return this.tabulationDao.selectTabulationById(id, qc,user);
	// }

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationDetails(Tabulation tabulation) {
		tabulationDao.updateDetails(tabulation);

	}

	@Override
	@Cacheable(value="domainCache",key="'findTabulationByListId:'+#id+'-QueryCriteria:'+#qc+#user.id")
	public Tabulation findTabulationByListId(Long id, QueryCriteria qc, SysUser user) {
		// 查询出列表 ,获得只含有基本信息的tabulation对象
		String sql = "select l.*,m.tbl_templatechname as tbl_templatename,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
				+ "bd.tbl_biaodanming as tbl_formName from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd "
				+ "on l.tbl_formid = bd.id where l.id=?";
		Tabulation t = tabulationDao.compexQuery(sql, id);

		// 列表操作按钮
		String optSql = "select o.*,b.tbl_url as tbl_url from sys_oprtbutton o left join sys_button b on o.tbl_buttonid = b.id where o.tbl_tabulation = ? order by o.tbl_shunxu asc";
		List<TabulationOpt> tabulationOpts = tabulationDao.selectTabulationOpt(optSql, new Object[] { t.getId() }, 0, -1);
		/*Map<String, Seqcode> curUserSeqcode = user.getCurUserSeqcode();*/
		/*Map<String, SysResource> curUserResource = user.getCurUserResource();
		for (TabulationOpt tabulationOpt : tabulationOpts) {
			if (null == curUserResource.get(tabulationOpt.getCode())) {
				tabulationOpt.setHasAuth("1");
			} else {
				tabulationOpt.setHasAuth("0");
			}
		}*/
		t.setTabulationOpts(tabulationOpts);

		// 列表组件
		ListControl listControl = null;
		if (t.getListControlId() != null) {
			listControl = listControlDao.findListControlById(t.getListControlId());

			SelectManage selectManage = selectManageDao.findSelectManageById(String.valueOf(listControl.getSelectId()));
			OrderManage orderManage = orderManageDao.findOrderManageById(String.valueOf(listControl.getOrderId()));
			OperationManage operationManage = operationManageDao.findOperationManageById(String.valueOf(listControl.getOperationId()));
			PageManage pageManage = pageManageDao.findPageManageById(String.valueOf(listControl.getPageId()));

			listControl.setSelectManage(selectManage);
			listControl.setOrderManage(orderManage);
			listControl.setOperationManage(operationManage);
			listControl.setPageManage(pageManage);

//			if (pageManage != null && pageManage.getPagesize() != null) {
//				if (pageManage.getPagesize() > 0) {
//					qc.setPageSize(pageManage.getPagesize());
//				}
//			}

			t.setListControl(listControl);
		}
		// 查询组件
		QueryControl queryControl = null;
		if (t.getQueryControlId() != null) {
			queryControl = queryControlDao.findQueryControlById(String.valueOf(t.getQueryControlId()));
			t.setQueryControl(queryControl);
		}

		// 高级查询组件
		AdvanceQueryControl advanceQueryControl = null;
		if (t.getAdvanceQueryControlId() != null) {
			if (t.getAdvanceQueryControlId()==-1L) {
				advanceQueryControl = new AdvanceQueryControl();
				advanceQueryControl.setQueryControlName("无");
				advanceQueryControl.setColumnNumber(4);
			} else {
				advanceQueryControl = queryControlDao.findAdvanceQueryControlById(t.getAdvanceQueryControlId());
			}
			t.setAdvanceQueryControl(advanceQueryControl);
		}

		// 列表按钮
		List<TabulationButton> buttons = tabulationButtonDao.selectByTabulationId(t.getId());

		/*for (TabulationButton tabulationButton : buttons) {
			if (null == curUserResource.get(tabulationButton.getCode())) {
				tabulationButton.setHasAuth("1");
			} else {
				tabulationButton.setHasAuth("0");
			}
		}*/
		t.setTabulationButtons(buttons);

		// 列表筛选条件
		List<TabulationQuery> querys = tabulationDao.selectQueryByTabulationId(t.getId());

		// 构造出列表所包含的列
		List<FormColumn> tabulationColumns = tabulationDao.selectColumnByTabulationId(t.getId());
		List<FormColumn> formColumns = tabulationDao.selectColumnByFormId(t.getFormId());
		Form form = formDao.selectById(t.getFormId());

		// 根据当前用户的角色与查询的表(formColumns.get(0).getTableId())查询用户所能操作的 数据级权限控制
		StringBuilder usrDataContent = new StringBuilder();
		Set<Role> setRoles = user.getRoles();
		if (setRoles != null) {
			for (Role role : setRoles) {
				List<DataSearch> lstDataSearch = dataSearchDao.getDataSearchContent(String.valueOf(role.getId()), String.valueOf(form.getTableId()));
				for (DataSearch dataSearch : lstDataSearch) {
					if (usrDataContent.length() > 0) {
						usrDataContent.append(" and (");
					} else {
						usrDataContent.append("(");
					}

					usrDataContent.append(dataSearch.getTblContent());
					usrDataContent.append(")");
				}
			}
		}


		t.setDomains(listdata(qc, user, t, querys, tabulationColumns, formColumns, form.getTableName(), form.getTableId(), usrDataContent));

		// 查询出列表元素，作为表头
		String listSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c "
				+ "on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? and a.tbl_partitionid = -1 order by a.tbl_liebiaoxianshishunxu asc";
		Object[] listParams = new Object[] { t.getFormId() };
		List<FormColumn> listColumns = tabulationDao.queryThead(listSql, listParams);
		List<FormColumnExtend> listColumnExtends = new ArrayList<FormColumnExtend>();
		for (FormColumn formColumn : listColumns) {
			FormColumnExtend fce = new FormColumnExtend();
			fce.setFormColumn(formColumn);
			if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
				Combox cb = comboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(cb);

				String order = "asc";
				if (formColumn.getCodeShowOrder() != null) {
					if (formColumn.getCodeShowOrder() == 0) {
						order = "asc";
					} else if (formColumn.getCodeShowOrder() == 1) {
						order = "desc";
					}
				}

				List<Dictionary> dl = null;
				if (formColumn.getCanSelectItem() != null && !formColumn.getCanSelectItem().equals("")) {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), formColumn.getCanSelectItem(), order);
				} else {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), order);
				}

				List<Code> codes = new ArrayList<Code>();
				for (Dictionary d : dl) {
					Code code = new Code(d.getId(), d.getName(), d.getValue());
					codes.add(code);
				}
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
				SearchCombox scb = searchComboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(scb);

				String order = "asc";
				if (formColumn.getCodeShowOrder() != null) {
					if (formColumn.getCodeShowOrder() == 0) {
						order = "asc";
					} else if (formColumn.getCodeShowOrder() == 1) {
						order = "desc";
					}
				}

				List<Dictionary> dl = null;
				if (formColumn.getCanSelectItem() != null && !formColumn.getCanSelectItem().equals("")) {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), formColumn.getCanSelectItem(), order);
				} else {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), order);
				}

				List<Code> codes = new ArrayList<Code>();
				for (Dictionary d : dl) {
					Code code = new Code(d.getId(), d.getName(), d.getValue());
					codes.add(code);
				}
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
				Combox cb = comboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(cb);
				List<Code> codes = tabulationDao.selectByRelation(formColumn.getRelationTable(), formColumn.getRelationColumn());
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
				SearchCombox scb = searchComboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(scb);
				List<Code> codes = tabulationDao.selectByRelation(formColumn.getRelationTable(), formColumn.getRelationColumn());
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_TREE) {
				Catalog catalog = catalogDao.selectById(Long.valueOf(formColumn.getCompexId()));
				fce.setCatalog(catalog);
			}
			if (formColumn.getInputType() == 7 && formColumn.getCompexId() != null) {
				MgrTree mgrTree = mgrTreeDao.selectById(formColumn.getCompexId());
				fce.setTableType(mgrTree.getTableType());
			}
			listColumnExtends.add(fce);
		}
		t.setTabulationColumnExtends(listColumnExtends);
		return t;
	}

	@Override
	@Cacheable(value="tabulationCache")
	public Tabulation selectNoPageTabulationByListId(Long id, QueryCriteria qc, SysUser user) {
		// 查询出列表
		String sql = "select l.*,m.tbl_templatechname as tbl_templatename,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
				+ "bd.tbl_biaodanming as tbl_formName from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd "
				+ "on l.tbl_formid = bd.id where l.id=?";
		Tabulation t = tabulationDao.compexQuery(sql, Long.valueOf(id));

		// 列表筛选条件
		List<TabulationQuery> querys = this.selectQueryByTabulationId(t.getId());

		// 构造出列表所包含的表
		List<FormColumn> tabulationColumns = this.tabulationDao.selectColumnByTabulationId(t.getId());
		List<FormColumn> formColumns = this.tabulationDao.selectColumnByFormId(t.getFormId());
		List<String> tables = new ArrayList<String>();
		List<String> tableIds = new ArrayList<String>();

		// 2012-11-14 sam start
		// 根据当前用户的角色与查询的表(formColumns.get(0).getTableId())查询用户所能操作的 数据级权限控制
		StringBuilder userDataContent = new StringBuilder();
		Set<Role> setRoles = user.getRoles();
		if (setRoles != null) {
			for (Role role : setRoles) {
				List<DataSearch> lstDataSearch = dataSearchDao.getDataSearchContent(String.valueOf(role.getId()), String.valueOf(formColumns.get(0).getTableId()));
				for (DataSearch dataSearch : lstDataSearch) {
					if (userDataContent.length() > 0) {
						userDataContent.append(" or (");
					} else {
						userDataContent.append("(");
					}

					userDataContent.append(dataSearch.getTblContent());
					userDataContent.append(")");
				}
			}
		}
		// 2012-11-14 sam end
		Form form = formDao.selectById(t.getFormId());
		t.setDomains(listdata(qc, user, t, querys, tabulationColumns, formColumns, form.getTableName(), form.getTableId(), userDataContent));

		// 查询出列表元素，作为表头
		String listSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
				+ "c.tbl_columnName as tbl_ziduanming,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c "
				+ "on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? and a.tbl_partitionid = -1 order by a.tbl_liebiaoxianshishunxu asc";
		Object[] listParams = new Object[] { t.getFormId() };
		List<FormColumn> listColumns = tabulationDao.queryThead(listSql, listParams);
		List<FormColumnExtend> listColumnExtends = new ArrayList<FormColumnExtend>();
		for (FormColumn formColumn : listColumns) {
			FormColumnExtend fce = new FormColumnExtend();
			fce.setFormColumn(formColumn);
			if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
				Combox cb = comboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(cb);

				String order = "asc";
				if (formColumn.getCodeShowOrder() != null) {
					if (formColumn.getCodeShowOrder() == 0) {
						order = "asc";
					} else if (formColumn.getCodeShowOrder() == 1) {
						order = "desc";
					}
				}

				List<Dictionary> dl = null;
				if (formColumn.getCanSelectItem() != null && !formColumn.getCanSelectItem().equals("")) {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), formColumn.getCanSelectItem(), order);
				} else {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), order);
				}

				List<Code> codes = new ArrayList<Code>();
				for (Dictionary d : dl) {
					Code code = new Code(d.getId(), d.getName(), d.getValue());
					codes.add(code);
				}
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
				SearchCombox scb = searchComboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(scb);

				String order = "asc";
				if (formColumn.getCodeShowOrder() != null) {
					if (formColumn.getCodeShowOrder() == 0) {
						order = "asc";
					} else if (formColumn.getCodeShowOrder() == 1) {
						order = "desc";
					}
				}

				List<Dictionary> dl = null;
				if (formColumn.getCanSelectItem() != null && !formColumn.getCanSelectItem().equals("")) {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), formColumn.getCanSelectItem(), order);
				} else {
					dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(), order);
				}

				List<Code> codes = new ArrayList<Code>();
				for (Dictionary d : dl) {
					Code code = new Code(d.getId(), d.getName(), d.getValue());
					codes.add(code);
				}
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
				Combox cb = comboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(cb);
				List<Code> codes = tabulationDao.selectByRelation(formColumn.getRelationTable(), formColumn.getRelationColumn());
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
					&& formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
				SearchCombox scb = searchComboxDao.findByID(formColumn.getCompexId());
				fce.setComponent(scb);
				List<Code> codes = tabulationDao.selectByRelation(formColumn.getRelationTable(), formColumn.getRelationColumn());
				fce.setCodes(codes);
			} else if (formColumn.getCompexId() != null && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_TREE) {
				Catalog catalog = catalogDao.selectById(Long.valueOf(formColumn.getCompexId()));
				fce.setCatalog(catalog);
			}
			if (formColumn.getInputType() == 7 && formColumn.getCompexId() != null) {
				MgrTree mgrTree = mgrTreeDao.selectById(formColumn.getCompexId());
				fce.setTableType(mgrTree.getTableType());
			}
			listColumnExtends.add(fce);
		}
		t.setTabulationColumnExtends(listColumnExtends);
		return t;
	}

	@Override
	public Tabulation selectNoPageAllColumnsTabulationByListId(Long id, QueryCriteria qc, SysUser user) {
		// 查询出列表
		String sql = "select l.*,m.tbl_templatechname as tbl_templatename,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
				+ "bd.tbl_biaodanming as tbl_formName from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd "
				+ "on l.tbl_formid = bd.id where l.id=?";
		final Tabulation t = tabulationDao.compexQuery(sql, Long.valueOf(id));

		// 列表筛选条件
		List<TabulationQuery> querys = this.selectQueryByTabulationId(t.getId());

		// 构造出列表所包含的表
		List<FormColumn> tabulationColumns = this.tabulationDao.selectAllColumnsByTabulationId(t.getId());
		List<FormColumn> formColumns = this.tabulationDao.selectColumnByFormId(t.getFormId());
		List<String> tables = new ArrayList<String>();
		List<String> tableIds = new ArrayList<String>();

		// 2012-11-14 sam start
		// 根据当前用户的角色与查询的表(formColumns.get(0).getTableId())查询用户所能操作的 数据级权限控制
		StringBuilder userDataContent = new StringBuilder();
		Set<Role> setRoles = user.getRoles();
		if (setRoles != null) {
			for (Role role : setRoles) {
				List<DataSearch> lstDataSearch = dataSearchDao.getDataSearchContent(String.valueOf(role.getId()), String.valueOf(formColumns.get(0).getTableId()));
				for (DataSearch dataSearch : lstDataSearch) {
					if (userDataContent.length() > 0) {
						userDataContent.append(" or (");
					} else {
						userDataContent.append("(");
					}

					userDataContent.append(dataSearch.getTblContent());
					userDataContent.append(")");
				}
			}
		}
		// 2012-11-14 sam end
		Form form = formDao.selectById(t.getFormId());
		t.setDomains(listdata(qc, user, t, querys, tabulationColumns, formColumns, form.getTableName(), form.getTableId(), userDataContent));
		return t;
	}

	public List<Domain> listdata(QueryCriteria qc, SysUser user, final Tabulation t, List<TabulationQuery> querys, List<FormColumn> tabulationColumns,
			List<FormColumn> formColumns, String tableName, Long tableId, StringBuilder userDataContent) {
		List<String> tables = new ArrayList<String>();
		List<Long> tableIds = new ArrayList<Long>();
		tables.add(tableName);
		tableIds.add(tableId);
		for (FormColumn fc : formColumns) {
			if (!tables.contains(fc.getBelongTable())) {
				tables.add(fc.getBelongTable());
			}
			if (!tableIds.contains(fc.getTableId())) {
				tableIds.add(fc.getTableId());
			}
		}
		// 查询主表总记录条数
		if (tables.size() > 0) {
			// 2012-11-14 sam start 将原有的判读去掉,以+拼接的字符串改为append
			StringBuffer countSql = new StringBuffer();
			countSql.append("select count(*) from (select * from ");
			countSql.append(tables.get(0));
			countSql.append(" where ");
			countSql.append((userDataContent.length() == 0) ? "1=1" : userDataContent);
			countSql.append(") m where 1=1 ");
			// 2012-11-14 sam end

			Map<String, Object> map = qc.getQueryCondition();
			Iterator iterator = map.entrySet().iterator();
			List param = new ArrayList();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				if (entry.getValue() instanceof String) {
					if (entry.getKey().indexOf("_start") != -1) {
						countSql.append(" and m." + entry.getKey().replace("_start", "") + " >= ? ");
						param.add(entry.getValue());
					}
					if (entry.getKey().indexOf("_end") != -1) {
						countSql.append(" and m." + entry.getKey().replace("_end", "") + " <= ? ");
						param.add(entry.getValue());
					}
					if (entry.getKey().indexOf("_start") == -1 && entry.getKey().indexOf("_end") == -1) {
						if (((String) entry.getValue()).contains("%")) {
							countSql.append(" and m." + entry.getKey() + " like ? ");
							param.add(((String)entry.getValue()).trim());
						} else {
							countSql.append(" and m." + entry.getKey() + " = ? ");
							param.add(entry.getValue());
						}
					}
				} else {
					countSql.append(" and m." + entry.getKey() + " = ? ");
					param.add(entry.getValue());
				}

			}
			if (querys.size() > 0) {
				String _tempSql = "";
				for (int q = 0; q < querys.size(); q++) {
					TabulationQuery tabulationQuery = querys.get(q);
					String _columnValue = tabulationQuery.getColumnValue();
					String _relationValue = "";
					String _conditionValue = "=";
					if (tabulationQuery.getCondition() == 4) {
						_columnValue = "%" + _columnValue + "%";
						_conditionValue = "like";
					} else if (tabulationQuery.getCondition() == 3) {
						_conditionValue = "<";
					} else if (tabulationQuery.getCondition() == 2) {
						_conditionValue = ">";
					} else if (tabulationQuery.getCondition() == 1) {
						_conditionValue = "=";
					} else if (tabulationQuery.getCondition() == 0) {
						_conditionValue = "!=";
					}
					if (tabulationQuery.getRelation() == 1) {
						_relationValue = "and";
					} else if (tabulationQuery.getRelation() == 2) {
						_relationValue = "or";
					}
					if (tables.get(0).equals(tabulationQuery.getTableName())) {
						if (tabulationQuery.getColumnValue().equals("%username%")) {
							// countSql.append(" "+_relationValue+" m." +
							// tabulationQuery.getColumnName() +
							// ""+_conditionValue+"'" + user.getId() + "'");
							if (q == 0) {
								_tempSql = " (m." + tabulationQuery.getColumnName() + " " + _conditionValue + "'" + user.getId() + "')";
							} else {
								_tempSql = "(" + _tempSql + " " + _relationValue + " m." + tabulationQuery.getColumnName() + " " + _conditionValue
										+ "'" + user.getId() + "')";
							}

						} else if (tabulationQuery.getColumnValue().equals("%orgname%")) {
							String orgId = "-1";
							if (user.getOrgs().size() > 0) {
								orgId = String.valueOf(user.getOrgs().get(0).getId());
							}
							// countSql.append(" "+_relationValue+" m." +
							// tabulationQuery.getColumnName() +
							// ""+_conditionValue+"'" + orgId + "'");
							if (q == 0) {
								_tempSql = " (m." + tabulationQuery.getColumnName() + " " + _conditionValue + "'" + orgId + "')";
							} else {
								_tempSql = "(" + _tempSql + " " + _relationValue + " m." + tabulationQuery.getColumnName() + " " + _conditionValue
										+ "'" + orgId + "')";
							}
						} else {
							// countSql.append(" "+_relationValue+" m." +
							// tabulationQuery.getColumnName() +
							// ""+_conditionValue+"'" + _columnValue + "'");
							if (q == 0) {
								_tempSql = " (m." + tabulationQuery.getColumnName() + " " + _conditionValue + "'" + _columnValue + "')";
							} else {
								_tempSql = "(" + _tempSql + " " + _relationValue + " m." + tabulationQuery.getColumnName() + " " + _conditionValue
										+ "'" + _columnValue + "')";
							}
						}
					}
				}
				if (!_tempSql.equals("")) {
					countSql.append(" and " + _tempSql);
				}
			}

			if (this.tableHasColumn(tables.get(0), "tbl_passed")) {
				if (!user.isSuper()) {
					countSql.append("and  (m.tbl_passed = 1 or m.comm_createBy = ?)");
					param.add(user.getId());
				}
			}

			countSql.append(" order by m.id desc");
			int total = compexdomainDao.count(countSql.toString(), param.toArray());
			t.setTotalCount(total);
		}

		List<Domain> domains = new ArrayList<Domain>();

		// 2012-6-27 liuqi
		List<String> alltable = new ArrayList<String>();
		if (tables != null && tables.size() > 0) {
			alltable.add(tables.get(0));
			for (int i = 1; i < tables.size(); i++) {
				Relation relation = relationDao.selectByMainIdAndSubId(tableIds.get(0), tableIds.get(i));
				if (relation.getRelationType() == Constants.RELATION_1_1) {
					alltable.add(tables.get(i));
				}
			}

			StringBuffer showsql = new StringBuffer(
					"m.*,m.id as tbl_mid,m.comm_createDate as main_createDate,m.comm_updateDate as main_updateDate,u.tbl_username as tbl_createusername,u2.tbl_username as tbl_updateusername,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename");
			for (int s = 1; s < alltable.size(); s++) {
				showsql.append(",s" + s + ".*,s" + s + ".id as s" + s + "id");
			}

			if (alltable.get(0).equals("")) {

			} else {

			}
			// 2012-11-14 sam start 将原有的判读去掉,以+拼接的字符串改为append
			StringBuffer listsql = new StringBuffer("");
			listsql.append("select ");
			listsql.append(showsql.toString());
			listsql.append(" from (select * from ");
			listsql.append(tables.get(0));
			listsql.append(" where ");
			listsql.append((userDataContent.length() == 0) ? "1=1" : userDataContent);
			listsql.append(") m ");
			// 2012-11-14 sam end

			for (int j = 1; j < alltable.size(); j++) {
				listsql.append(" left join " + StringUtil.getTableString(alltable.get(0), alltable.get(j), 14) + " st" + j + " on m.id=st" + j
						+ ".tbl_mainid left join " + alltable.get(j) + " s" + j + " on st" + j + ".tbl_subid=s" + j + ".id ");
			}
			listsql.append(" left join sys_user u on m.comm_createBy = u.id left join sys_user u2 on m.comm_updateBy = u2.id ");
			listsql.append(" where 1=1 ");

			// 通过筛选条件过滤
			if (querys.size() > 0) {
				String _tempSql = "";
				for (int q = 0; q < querys.size(); q++) {
					TabulationQuery tabulationQuery = querys.get(q);
					String _columnValue = tabulationQuery.getColumnValue();
					String _relationValue = "";
					String _conditionValue = "=";
					if (tabulationQuery.getCondition() == 4) {
						_columnValue = "%" + _columnValue + "%";
						_conditionValue = "like";
					} else if (tabulationQuery.getCondition() == 3) {
						_conditionValue = "<";
					} else if (tabulationQuery.getCondition() == 2) {
						_conditionValue = ">";
					} else if (tabulationQuery.getCondition() == 1) {
						_conditionValue = "=";
					} else if (tabulationQuery.getCondition() == 0) {
						_conditionValue = "!=";
					}
					if (tabulationQuery.getRelation() == 1) {
						_relationValue = "and";
					} else if (tabulationQuery.getRelation() == 2) {
						_relationValue = "or";
					}
					if (alltable.get(0).equals(tabulationQuery.getTableName())) {
						if (tabulationQuery.getColumnValue().equals("%username%")) {
							// listsql.append(" "+_relationValue+" m." +
							// tabulationQuery.getColumnName() +
							// ""+_conditionValue+"'" + user.getId() + "'");
							if (q == 0) {
								_tempSql = " (m." + tabulationQuery.getColumnName() + " " + _conditionValue + "'" + user.getId() + "')";
							} else {
								_tempSql = "(" + _tempSql + " " + _relationValue + " m." + tabulationQuery.getColumnName() + " " + _conditionValue
										+ "'" + user.getId() + "')";
							}
						} else if (tabulationQuery.getColumnValue().equals("%orgname%")) {
							String orgId = "-1";
							if (user.getOrgs().size() > 0) {
								orgId = String.valueOf(user.getOrgs().get(0).getId());
							}
							// listsql.append(" "+_relationValue+" m." +
							// tabulationQuery.getColumnName() +
							// ""+_conditionValue+"'" + orgId + "'");
							if (q == 0) {
								_tempSql = " (m." + tabulationQuery.getColumnName() + " " + _conditionValue + "'" + orgId + "')";
							} else {
								_tempSql = "(" + _tempSql + " " + _relationValue + " m." + tabulationQuery.getColumnName() + " " + _conditionValue
										+ "'" + orgId + "')";
							}
						} else {
							// listsql.append(" "+_relationValue+" m." +
							// tabulationQuery.getColumnName() +
							// ""+_conditionValue+"'" + _columnValue + "'");
							if (q == 0) {
								_tempSql = " (m." + tabulationQuery.getColumnName() + " " + _conditionValue + "'" + _columnValue + "')";
							} else {
								_tempSql = "(" + _tempSql + " " + _relationValue + " m." + tabulationQuery.getColumnName() + " " + _conditionValue
										+ "'" + _columnValue + "')";
							}
						}
					}
				}
				if (!_tempSql.equals("")) {
					listsql.append(" and " + _tempSql);
				}
			}

			Map<String, Object> map = qc.getQueryCondition();
			Iterator iterator = map.entrySet().iterator();
			List param = new ArrayList();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				if (entry.getValue() instanceof String) {
					if (entry.getKey().indexOf("_start") != -1) {
						listsql.append(" and m." + entry.getKey().replace("_start", "") + " >= ? ");
						param.add(entry.getValue());
					}
					if (entry.getKey().indexOf("_end") != -1) {
						listsql.append(" and m." + entry.getKey().replace("_end", "") + " <= ? ");
						param.add(entry.getValue());
					}
					if (entry.getKey().indexOf("_start") == -1 && entry.getKey().indexOf("_end") == -1) {
						if (((String) entry.getValue()).contains("%")) {
							listsql.append(" and m." + entry.getKey() + " like ? ");
							param.add(((String)entry.getValue()).trim());
						} else {
							listsql.append(" and m." + entry.getKey() + " = ? ");
							param.add(entry.getValue());
						}
					}
				} else {
					listsql.append(" and m." + entry.getKey() + " = ? ");
					param.add(entry.getValue());
				}

			}
			if (this.tableHasColumn(tables.get(0), "tbl_passed")) {
				if (!user.isSuper()) {
					listsql.append("and  (m.tbl_passed = 1 or m.comm_createBy = ?)");
					param.add(user.getId());
				}
			}
			if(!"".equals(qc.getOrderField())) {
				listsql.append(" order by m."+qc.getOrderField()+" "+qc.getOrderDirection());
			}else{
				listsql.append(" order by m.comm_updateDate desc");
			}

			List retList = null;
			if (qc.getPageSize() == -1) {
				retList = compexdomainDao.selectData(listsql.toString(), param.toArray());
			} else {
				retList = compexdomainDao.selectData(listsql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());

			}
			composeDomainList(domains, alltable, tabulationColumns, retList);
		}
		return domains;
	}

	/**
	 * 组装domain
	 * 
	 * 优化组装组件的查询次数，每一行只查询一次，然后存于内存Map中，其他行从内存中读取。By Allan
	 * 
	 * @param domains
	 * @param mainTable
	 * @param subTable
	 * @param columnList
	 * @param retList
	 */
	private void composeDomainList(List<Domain> domains, List<String> alltable, List columnList, List retList) {
		String tableSql = "select * from sys_tables where tbl_tableName=?";
		Object[] tableParams = new Object[] { alltable.get(0) };
		List tableList = tableDao.select(tableSql,tableParams,0,-1);
		Map<Integer, Map<Integer, Combox>> comBoxMap = new HashMap<Integer, Map<Integer, Combox>>();
		Map<Integer, Map<Integer, List<Dictionary>>> dictionaryMap = new HashMap<Integer, Map<Integer, List<Dictionary>>>();
		for (int i = 0; retList != null && i < retList.size(); i++) {
			List ceList = new ArrayList();
			Map retMap = (Map) retList.get(i);
			Set keySet = retMap.keySet();

			Domain u = new Domain();
			// 设置table
			if (tableList != null && tableList.size() > 0) {
				u.setTable((Table) tableList.get(0));
			}
			// 设置列表元素和对应值

			for (int j = 0; columnList != null && j < columnList.size(); j++) {
				FormColumn c = (FormColumn) (columnList.get(j));
				if (keySet.contains(c.getColumnName())) {
					FormColumnExtend ce = new FormColumnExtend();
					ce.setFormColumn(c);
					ce.setValue(retMap.get(c.getColumnName()));
					if (c.getColumnName().equalsIgnoreCase("comm_createBy")) {
						ce.setValue(retMap.get("tbl_createusername"));
						ce.setValueText((String) retMap.get("tbl_createname"));
					}
					if (c.getColumnName().equalsIgnoreCase("comm_updateBy")) {
						ce.setValue(retMap.get("tbl_updateusername"));
						ce.setValueText((String) retMap.get("tbl_createname"));
					}

					if (c.getInputType() == 6) {
						if (c.getColumnName().equalsIgnoreCase("comm_createDate") || c.getColumnName().equalsIgnoreCase("comm_updateDate")) {
							String tempColumnName = "";
							if (c.getColumnName().equalsIgnoreCase("comm_createDate")) {
								tempColumnName = "main_createDate";
								ce.setValue(retMap.get(tempColumnName));
							} else if (c.getColumnName().equalsIgnoreCase("comm_updateDate")) {
								tempColumnName = "main_updateDate";
								ce.setValue(retMap.get(tempColumnName));
							}
							if (retMap.get(tempColumnName) != null && !retMap.get(tempColumnName).equals("")) {
								if (retMap.get(tempColumnName) instanceof String) {// 日期用字符串表示时
									ce.setValue(retMap.get(tempColumnName));
								} else {
									if (ce.getValue() != null) {
										if (ce.getFormColumn().getDataType().equals("timestamp")) {
											Timestamp ts = (Timestamp) (ce.getValue());
											Date date = new Date(ts.getTime());
											ce.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(retMap.get(tempColumnName)));
										} else if (ce.getFormColumn().getDataType().equals("date")) {
											ce.setValue(new SimpleDateFormat("yyyy-MM-dd").format(retMap.get(tempColumnName)));
										}
									}
								}
							}
						} else {
							if (retMap.get(c.getColumnName()) != null && !retMap.get(c.getColumnName()).equals("")) {
								if (retMap.get(c.getColumnName()) instanceof String) {// 日期用字符串表示时
									ce.setValue(retMap.get(c.getColumnName()));
								} else {
									if (ce.getValue() != null) {
										if (ce.getFormColumn().getDataType().equals("timestamp")) {
											Timestamp ts = (Timestamp) (ce.getValue());
											Date date = new Date(ts.getTime());
											ce.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(retMap.get(c.getColumnName())));
										} else if (ce.getFormColumn().getDataType().equals("date")) {
											ce.setValue(new SimpleDateFormat("yyyy-MM-dd").format(retMap.get(c.getColumnName())));
										}
									}
								}
							}
						}
					}

					if (c.getInputType() == 0) {
						if (c.getDefaultValue() != null) {
							if (c.getDefaultValue().equals("%username%")) {
								String _userId = String.valueOf(ce.getValue());
								SysUser _user = (SysUser) userDao.getUserResource("id", _userId, 0);
								ce.setValueText(_user.getFullname());
							}
							if (c.getDefaultValue().equals("%orgname%")) {
								Org _org = userDao.getOrgById(String.valueOf(ce.getValue()));
								ce.setValueText(_org.getTblName());
							}
						}
					}
					if (c.getInputType() == 7) {
						MgrTree mgrTree = mgrTreeDao.selectById(c.getCompexId());
						if (mgrTree.getTableType() == null) {
							mgrTree.setTableType("");
						}
						if (!mgrTree.getTableType().equals("1")) {
							// 显示所有的上级父模块
							if ("sys_catalog".equalsIgnoreCase(c.getBelongTable()) && "tbl_parentId".equalsIgnoreCase(c.getColumnName())) {
								String pName = mgrTreeDao.findParentName(Long.valueOf(ce.getValue().toString()));
								ce.setValueText(pName);
							} else {
								CommonTree commonTree = mgrTreeDao.findCommonTree(mgrTree, Long.valueOf(ce.getValue().toString()));
								ce.setValueText(commonTree.getName());
							}
						}
						if (mgrTree.getTableType().equals("1") && mgrTree.getType() == 4L) {
							List<Person> _persons = userDao.selectPersonNameByIds(String.valueOf(ce.getValue()));
							String pName = "";
							for (int p = 0; p < _persons.size(); p++) {
								Person _person = _persons.get(p);
								pName += _person.getTblXingming() + ";";
							}
							ce.setValueText(pName);
						} else if (mgrTree.getTableType().equals("1") && mgrTree.getType() == 5L) {
							List<SysUser> _users = userDao.selectUserNameByIds(String.valueOf(ce.getValue()));
							String pName = "";
							for (int p = 0; p < _users.size(); p++) {
								SysUser _user = _users.get(p);
								pName += _user.getFullname() + ";";
							}
							ce.setValueText(pName);
						}
					}
					if (c.getInputType() == 13) {
						if (ce.getValue() != null && !ce.getValue().equals("")) {
							Dictionary _dictionary = dictionaryDao.selectById(Long.valueOf(ce.getValue().toString()));
							ce.setValueText(_dictionary.getName());
						}
					}
					if (c.getInputType() == 15) {
						List<Person> _lstPerson = personChioseDao.getPersons();
						List<Person> _lstPersonTmp = new ArrayList<Person>();
						for (Person person : _lstPerson) {
							String _choisePerson = String.valueOf(ce.getValue());
							for (String _sPerson : _choisePerson.split(";")) {
								if (person.getId().equals(_sPerson)) {
									_lstPersonTmp.add(person);
								}
							}
						}

						String pName = "";
						for (int p = 0; p < _lstPersonTmp.size(); p++) {
							pName += _lstPersonTmp.get(p).getTblXingming() + ";";
						}
						ce.setValueText(pName);
					}
					// 下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						Map<Integer, Combox> coms = comBoxMap.get(0);
						if (coms != null) {
							Combox cb = coms.get(j);
							if (cb == null) {
								cb = comboxDao.findByID(c.getCompexId());
								ce.setComponent(cb);
								coms.put(j, cb);
							}
							ce.setComponent(cb);
						} else {
							Map<Integer, Combox> comst = new HashMap<Integer, Combox>();
							Combox cb = comboxDao.findByID(c.getCompexId());
							ce.setComponent(cb);
							comst.put(j, cb);
							comBoxMap.put(0, comst);
						}

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						Map<Integer, List<Dictionary>> dictionarys = dictionaryMap.get(0);
						List<Dictionary> dl = null;
						if (dictionarys != null) {
							dl = dictionarys.get(j);
							if (dl == null) {
								if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
									dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
								} else {
									dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
								}
								dictionarys.put(j, dl);
							}
						} else {
							Map<Integer, List<Dictionary>> dll = new HashMap<Integer, List<Dictionary>>();
							if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
								dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
							} else {
								dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
							}
							dll.put(j, dl);
							dictionaryMap.put(0, dll);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 搜索下拉框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);

						String order = "asc";
						if (c.getCodeShowOrder() != null) {
							if (c.getCodeShowOrder() == 0) {
								order = "asc";
							} else if (c.getCodeShowOrder() == 1) {
								order = "desc";
							}
						}

						List<Dictionary> dl = null;
						if (c.getCanSelectItem() != null && !c.getCanSelectItem().equals("")) {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), c.getCanSelectItem(), order);
						} else {
							dl = dictionaryDao.selectByParent(c.getCodeParentId(), order);
						}

						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 单选框代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_RADIO
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						/*
						 * TextBox tb = textBoxDao.findByID(c.getRadioId());
						 * ce.setTextBox(tb);
						 */
						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 多选框 代码
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_CHECKBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
						/*
						 * TextBox tb = textBoxDao.findByID(c.getCheckBoxId());
						 * ce.setTextBox(tb);
						 */
						List<Dictionary> dl = dictionaryDao.selectByParent(c.getCodeParentId(), "asc");
						List<Code> codes = new ArrayList<Code>();
						for (Dictionary d : dl) {
							Code code = new Code(d.getId(), d.getName(), d.getValue());
							codes.add(code);
						}
						ce.setCodes(codes);
					}
					// 下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						Combox cb = comboxDao.findByID(c.getCompexId());
						ce.setComponent(cb);
						List<Code> codes = tabulationDao.selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}
					// 搜索下拉框 关系表
					if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
							&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
						SearchCombox scb = searchComboxDao.findByID(c.getCompexId());
						ce.setComponent(scb);
						List<Code> codes = tabulationDao.selectByRelation(c.getRelationTable(), c.getRelationColumn());
						ce.setCodes(codes);
					}

					ceList.add(ce);
				}
			}
			ceList.add(getIdFormColumn(retMap, alltable.get(0), "tbl_mid"));
			for (int j = 1; j < alltable.size(); j++) {
				FormColumnExtend fce = getIdFormColumn(retMap, alltable.get(j), "s" + j + "id");
				if (fce != null) {
					ceList.add(fce);
				}
			}
			u.setTabulationColumnExtends(ceList);
			Object _objId = retMap.get("tbl_mid");
			u.setId(Long.valueOf(_objId.toString()));
			domains.add(u);
		}
	}
	
	/**
	 * 创建ID字段
	 * 
	 * @param retMap
	 * @param table
	 * @param idstr
	 * @return
	 */
	public FormColumnExtend getIdFormColumn(Map retMap, String table, String idstr) {
		String id;
		if (retMap.get(idstr) == null) {
			id = (String) retMap.get(idstr);
		} else {
			id = retMap.get(idstr).toString();
		}
		if (id == null) {
			return null;
		}
		FormColumn c = new FormColumn();
		c.setColumnName("id");
		c.setBelongTable(table);
		FormColumnExtend ce = new FormColumnExtend();
		ce.setFormColumn(c);
		ce.setValue(id);
		return ce;
	}

	@Override
	@Cacheable(value="tabulationCache")
	public Tabulation findTabulationBySeqcode(String seqcode) {
		return tabulationDao.selectTabulationBySeqcode(seqcode);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List queryTabulationQuery(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,c.tbl_columnName as tbl_columnName,c.tbl_columnZhName as tbl_columnZhName from "
						+ "sys_liebiaoquery a left join sys_tables t on a.tbl_suoshubiao = t.id left join sys_columns c on a.tbl_ziduan = c.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and a." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by a.tbl_order asc");
		return this.tabulationDao.selectTabulationQuery(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	@Cacheable(value="tabulationCache")
	public int countTabulationQuery(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_liebiaoquery l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and l." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by l.id desc");
		return tabulationDao.countTabulationQuery(sql.toString(), param.toArray());
	}

	@Override
	@Cacheable(value="tabulationCache")
	public TabulationQuery findTabulationQueryById(Long tabulationQueryId) {
		return this.tabulationDao.selectTabulationQueryById(tabulationQueryId);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationQuerys(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			this.tabulationDao.deleteTabulationQuery(id);
		}
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationQuery(TabulationQuery tabulationQuery) {
		this.tabulationDao.updateTabulationQuery(tabulationQuery);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doSaveTabulationQuery(TabulationQuery tabulationQuery) {
		this.tabulationDao.insertTabulationQuery(tabulationQuery);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List<TabulationQuery> selectQueryByTabulationId(Long tabulationId) {
		return this.tabulationDao.selectQueryByTabulationId(tabulationId);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List queryTabulationOpt(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select o.*,b.tbl_url as tbl_url from sys_oprtbutton o left join sys_button b on o.tbl_buttonid = b.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and o." + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by o.tbl_shunxu asc");
		return this.tabulationDao.selectTabulationOpt(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	@Cacheable(value = "tabulationCache", key = "'countTabulationOpt:'+#qc.toString()")
	public int countTabulationOpt(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_oprtbutton where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and " + entry.getKey() + " = ? ");
			param.add(entry.getValue());
		}
		sql.append("order by id desc");
		return tabulationDao.countTabulationOpt(sql.toString(), param.toArray());
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationOpts(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			TabulationOpt _tabulationOpt = this.findTabulationOptById(id);
			seqcodeDao.deleteSeq(_tabulationOpt.getCode());
			this.useInfoDao.deleteUseInfo(id, 4);
			this.tabulationDao.deleteTabulationOpt(id);
		}
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationOpt(TabulationOpt tabulationOpt) {
		this.tabulationDao.updateTabulationOpt(tabulationOpt);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public Long doSaveTabulationOpt(TabulationOpt tabulationOpt) {
		return this.tabulationDao.insertTabulationQuery(tabulationOpt);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public TabulationOpt findTabulationOptById(Long tabulationOptId) {
		return this.tabulationDao.selectTabulationOptById(tabulationOptId);
	}

	@Override
	public boolean tableHasColumn(String tableName, String columnName) {
		return this.tabulationDao.tableHasColumn(tableName, columnName);
	}

	@Override
	public List useFormCount(Long formId, Long id) {
		return this.tabulationDao.useFormCount(formId, id);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List<TabulationOpt> findTabulationOpt(Long tabulationId) {
		return this.tabulationDao.findTabulationOpt(tabulationId);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List<TabulationButton> findTabulationButton(Long tabulationId) {
		return this.tabulationButtonDao.selectByTabulationId(tabulationId);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateButtonOrder(TabulationButton tabulationButton, Long tabulationButtonId) {
		this.tabulationButtonDao.updateButtonOrder(tabulationButton, tabulationButtonId);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateOptOrder(TabulationOpt tabulationOpt, Long tabulationOptId) {
		this.tabulationButtonDao.updateOptOrder(tabulationOpt, tabulationOptId);
	}

	@Override
	@Cacheable(value="tabulationCache")
	public List<Tabulation> queryTabulationByFormid(Long formid) {
		return this.tabulationDao.queryTabulationByFormid(formid);
	}

	@Override
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doLogicDeleteTabulations(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			// 查看表单是否被列表引用，被引用则不能删除
			List list = this.tabulationDao.findCataByListId(id);
			if (list.size() > 0) {
				throw new AppException("选中的部分列表未能成功删除，正被目录引用！");
			}
			// 删除列表对应的编码
			Tabulation _tabulation = this.findTabulationById(id);
			seqcodeDao.logicDeleteSeq(_tabulation.getCode());

			this.tabulationDao.logicDelete(id);
		}
	}
	
}
