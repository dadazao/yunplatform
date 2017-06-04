package com.cloudstong.platform.resource.tabulation.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.button.dao.ButtonDao;
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
import com.cloudstong.platform.resource.metadata.dao.RelationDao;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.model.TableRowMapper;
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
import com.cloudstong.platform.resource.textbox.dao.TextBoxDao;
import com.cloudstong.platform.resource.tree.dao.MgrTreeDao;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.system.dao.IDataSearchDao;
import com.cloudstong.platform.system.dao.IUserDao;
import com.cloudstong.platform.system.dao.SysLogDao;
import com.cloudstong.platform.system.model.DataSearch;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.Role;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:列表操作数据库接口实现类
 */
@Repository("tabulationDao")
public class TabulationDaoImpl implements TabulationDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Resource
	private TabulationButtonDao tabulationButtonDao;

	@Resource
	private FormDao formDao;

	@Resource
	private RelationDao relationDao;

	@Resource
	private TextBoxDao textBoxDao;

	@Resource
	private ComboxDao comboxDao;

	@Resource
	private SearchComboxDao searchComboxDao;

	@Resource
	private DictionaryDao dictionaryDao;

	@Resource
	private CatalogDao catalogDao;

	@Resource
	private ButtonDao buttonDao;

	@Resource
	private MgrTreeDao mgrTreeDao;

	@Resource
	private PersonChioseDao personChioseDao;

	@Resource
	private IUserDao userDao;

	@Resource
	private ListControlDao listControlDao;
	@Resource
	private QueryControlDao queryControlDao;
	@Resource
	private SelectManageDao selectManageDao;
	@Resource
	private OrderManageDao orderManageDao;
	@Resource
	private OperationManageDao operationManageDao;
	@Resource
	private PageManageDao pageManageDao;
	@Resource
	private IDataSearchDao dataSearchDao;
	@Resource
	private SeqcodeDao seqcodeDao;
	@Resource
	private SysLogDao sysLogDao;

	@Override
	@Cacheable(value = "tabulationCache")
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args, new TabulationRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow, rowsCount, new TabulationRowMapper());
		}
		return retList;
	}

	class TabulationRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Tabulation t = new Tabulation();
			t.setId(rs.getLong("id"));
			t.setTabulationName(rs.getString("tbl_liebiaomingcheng"));
			t.setCode(rs.getString("tbl_bianma"));
			t.setRight(rs.getString("tbl_quanxian"));
			t.setCreateBy(rs.getLong("comm_createBy"));
			t.setCreateDate(rs.getTimestamp("comm_createDate"));
			t.setTemplateListId(rs.getInt("tbl_templatelistid"));
			t.setTemplateListName(rs.getString("tbl_templatename"));
			t.setTableId(rs.getLong("tbl_tableid"));
			t.setTableZhName(rs.getString("tbl_tableZhName"));
			t.setTabulationPath(rs.getString("tbl_liebiaolujing"));
			t.setFormId(rs.getLong("tbl_formid"));
			t.setFormName(rs.getString("tbl_formName"));
			t.setIsSelect(rs.getString("tbl_isselect"));
			t.setIsModify(rs.getString("tbl_ismodify"));
			t.setRemarks(rs.getString("tbl_remarks"));
			t.setDesc(rs.getString("tbl_beizhu"));
			t.setUserName(rs.getString("tbl_createname"));
			t.setSystemTeam(rs.getString("tbl_systemteam"));
			return t;
		}
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void update(Tabulation tabulation) {
		String sql = "update sys_liebiao set tbl_systemteam=?,tbl_liebiaomingcheng=?,tbl_quanxian=?,tbl_templatelistid = ?,tbl_tableid = ?,tbl_isselect = ?,tbl_ismodify = ?,tbl_modifyname = ?,"
				+ "tbl_listcontrolid = ?,tbl_querycontrolid = ?,tbl_advancequerycontrolid=?,tbl_formid = ?,tbl_remarks = ?,tbl_beizhu = ?,comm_updateBy=?,comm_updateDate=? where id=?";
		Object[] params = new Object[] { tabulation.getSystemTeam(), tabulation.getTabulationName(), tabulation.getRight(),
				tabulation.getTemplateListId(), tabulation.getTableId(), tabulation.getIsSelect(), tabulation.getIsModify(),
				tabulation.getModifyName(), tabulation.getListControlId(), tabulation.getQueryControlId(), tabulation.getAdvanceQueryControlId(),
				tabulation.getFormId(), tabulation.getRemarks(), tabulation.getDesc(), tabulation.getUpdateBy(), tabulation.getUpdateDate(),
				tabulation.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updatePath(Tabulation tabulation) {
		String sql = "update sys_liebiao set tbl_liebiaolujing=? where id=?";
		Object[] params = new Object[] { tabulation.getTabulationPath(), tabulation.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public Long insert(Tabulation tabulation) {
		Long id = UniqueIdUtil.genId();
		String sql = "insert into sys_liebiao(id,tbl_systemteam,tbl_liebiaomingcheng,tbl_bianma,tbl_quanxian,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,tbl_templatelistid,tbl_tableid,"
				+ "tbl_isselect,tbl_ismodify,tbl_isshownumber,tbl_modifyname,tbl_listcontrolid,tbl_querycontrolid,tbl_advancequerycontrolid,tbl_formid,tbl_remarks,tbl_beizhu) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { id, tabulation.getSystemTeam(), tabulation.getTabulationName(), tabulation.getCode(), tabulation.getRight(),
				tabulation.getCreateBy(), tabulation.getCreateDate(), tabulation.getUpdateBy(), tabulation.getUpdateDate(),
				tabulation.getTemplateListId(), tabulation.getTableId(), tabulation.getIsSelect(), tabulation.getIsModify(),
				tabulation.getIsNumber(), tabulation.getModifyName(), tabulation.getListControlId(), tabulation.getQueryControlId(),
				tabulation.getAdvanceQueryControlId(), tabulation.getFormId(), tabulation.getRemarks(), tabulation.getDesc() };
		jdbcTemplate.update(sql, params);
		return id;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List selectTabulationColumn(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args, new TabulationColumnRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow, rowsCount, new TabulationColumnRowMapper());
		}
		return retList;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public int countTabulationColumn(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class TabulationColumnRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TabulationColumn t = new TabulationColumn();
			t.setId(rs.getLong("id"));
			t.setColumnId(rs.getString("tbl_ziduan"));
			t.setTableId(rs.getLong("tbl_tableid"));
			t.setColumnName(rs.getString("tbl_ziduanming"));
			t.setColumnZhName(rs.getString("tbl_ziduanmingzh"));
			t.setBelongTable(rs.getString("tbl_suoshubiao"));
			t.setTableZhName(rs.getString("tbl_suoshubiaoming"));
			t.setIsShowInList(rs.getInt("tbl_liebiaoxianshi"));
			t.setIsQuery(rs.getInt("tbl_chaxuntiaojian"));
			t.setIsDefaultQuery(rs.getInt("tbl_morenchaxun"));
			t.setIsUse(rs.getInt("tbl_qiyong"));
			t.setListOrder(rs.getInt("tbl_liebiaoxianshishunxu"));
			return t;
		}
	}

	class TabulationQueryRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TabulationQuery t = new TabulationQuery();
			t.setId(rs.getLong("id"));
			t.setTabulationId(rs.getLong("tbl_tabulation"));
			t.setTableId(rs.getString("tbl_suoshubiao"));
			t.setColumnId(rs.getString("tbl_ziduan"));
			t.setColumnValue(rs.getString("tbl_ziduanzhi"));
			t.setTableName(rs.getString("tbl_tableName"));
			t.setTableCNName(rs.getString("tbl_tableZhName"));
			t.setColumnName(rs.getString("tbl_columnName"));
			t.setColumnCNName(rs.getString("tbl_columnZhName"));
			t.setCondition(rs.getInt("tbl_condition"));
			t.setRelation(rs.getInt("tbl_relation"));
			t.setOrder(String.valueOf(rs.getInt("tbl_order")));
			return t;
		}
	}

	class TabulationOptRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TabulationOpt t = new TabulationOpt();
			t.setId(rs.getLong("id"));
			t.setName(rs.getString("tbl_oprtname"));
			t.setOrder(rs.getInt("tbl_shunxu"));
			t.setButtonId(rs.getLong("tbl_buttonid"));
			t.setCode(rs.getString("tbl_bianma"));
			t.setShowName(rs.getString("tbl_showname"));
			t.setFuncName(rs.getString("tbl_funcname"));
			t.setFcomment(rs.getString("tbl_comment"));
			t.setUrl(rs.getString("tbl_url"));

			t.setButton(buttonDao.findByID(Long.valueOf(t.getButtonId())));
			return t;
		}
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateTabulationColumn(TabulationColumn tabulationColumn) {
		String sql = "update sys_liebiaosheji set tbl_ziduan=?,tbl_tableid=?,tbl_liebiaoxianshi=?,tbl_liebiaoxianshishunxu=?,tbl_chaxuntiaojian=?,tbl_morenchaxun=?,tbl_iyong=?,"
				+ "tbl_tabulation=?,tbl_suoshubiao=? where id=?";
		Object[] params = new Object[] { tabulationColumn.getColumnId(), tabulationColumn.getTableId(), tabulationColumn.getIsShowInList(),
				tabulationColumn.getListOrder(), tabulationColumn.getIsQuery(), tabulationColumn.getIsDefaultQuery(), tabulationColumn.getIsUse(),
				tabulationColumn.getListId(), tabulationColumn.getBelongTable(), tabulationColumn.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void insertTabulationColumn(TabulationColumn tabulationColumn) {
		String sql = "insert into sys_liebiaosheji(id,tbl_ziduan,tbl_tableid,tbl_liebiaoxianshi,tbl_liebiaoxianshishunxu,tbl_chaxuntiaojian,tbl_morenchaxun,tbl_qiyong,tbl_tabulation,tbl_suoshubiao) values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), tabulationColumn.getColumnId(), tabulationColumn.getTableId(),
				tabulationColumn.getIsShowInList(), tabulationColumn.getListOrder(), tabulationColumn.getIsQuery(),
				tabulationColumn.getIsDefaultQuery(), tabulationColumn.getIsUse(), tabulationColumn.getListId(), tabulationColumn.getBelongTable() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void delete(Long id) {
		// 删除列表
		Object[] params = new Object[] { id };
		String sql = "delete from sys_liebiao where id = ?";
		jdbcTemplate.update(sql, params);
		// 删除列表按钮
		String selbtnsql = "select * from sys_liebiaobutton where tbl_tabulation=?";
		List<Map<String, Object>> btnList = jdbcTemplate.queryForList(selbtnsql, params);
		for (int i = 0; i < btnList.size(); i++) {
			// 删除编码
			if(((Map<String, Object>) btnList.get(i)).get("tbl_bianma")!=null){
				String code = ((Map<String, Object>) btnList.get(i)).get("tbl_bianma").toString();
				seqcodeDao.deleteSeq(code);
			}
			
		}

		String buttonSql = "delete from sys_liebiaobutton where tbl_tabulation = ?";
		jdbcTemplate.update(buttonSql, params);
		// 删除列表操作按钮
		String seloptbtnsql = "select * from sys_oprtbutton where tbl_tabulation=?";
		List<Map<String, Object>> optbtnList = jdbcTemplate.queryForList(seloptbtnsql, params);
		for (int i = 0; i < optbtnList.size(); i++) {
			// 删除编码
			if(((Map<String, Object>) optbtnList.get(i)).get("tbl_bianma")!=null){
				String code = ((Map<String, Object>) optbtnList.get(i)).get("tbl_bianma").toString();
				seqcodeDao.deleteSeq(code);
			}
		}

		String optSql = "delete from sys_oprtbutton where tbl_tabulation = ?";
		jdbcTemplate.update(optSql, params);
		// 删除列表筛选条件
		String querySql = "delete from sys_liebiaoquery where tbl_tabulation = ?";
		jdbcTemplate.update(querySql, params);
		// String sql3 =
		// "delete from sys_liebiaosheji where tabulation = '"+id+"'";
		// list.add(sql3);
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public Tabulation selectById(Long id) {
		String sql = "select l.*,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
				+ "bd.tbl_biaodanming as tbl_formName,u.tbl_username as tbl_createusername,u2.tbl_username as tbl_updateusername,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id "
				+ "left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd on l.tbl_formid = bd.id left join sys_user u on l.comm_createBy = u.id   left join sys_user u2 "
				+ "on l.comm_updateBy = u2.id   where l.id=?";
		final Tabulation t = new Tabulation();
		final Object[] params = new Object[] { id };
		try {
			query(sql, params, t);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}

	// @Override
	// public Tabulation selectTabulationById(Long id, QueryCriteria qc,User
	// user) {
	// // 查询出列表
	// String sql =
	// "select l.*,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
	// +
	// "bd.tbl_biaodanming as tbl_formName,u.tbl_username as tbl_createusername,u2.tbl_username as tbl_updatename from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id "
	// +
	// "left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd on l.tbl_formid = bd.id left join sys_user u on l.comm_createBy = u.id left join sys_user u2 on l.comm_updateBy = u2.id where l.id=?";
	// final Tabulation t = new Tabulation();
	// final Object[] params = new Object[] { id };
	// query(sql, params, t);
	//
	// // 列表按钮
	// List<TabulationButton> buttons = tabulationButtonDao
	// .selectByTabulationId(t.getId());
	// t.setTabulationButtons(buttons);
	//
	// // 构造出列表所包含的表
	// List<FormColumn> tabulationColumns = this.selectColumnByTabulationId(t
	// .getId());
	// List<FormColumn> formColumns = this.selectColumnByFormId(t.getFormId());
	// List<String> tables = new ArrayList<String>();
	// List<String> tableIds = new ArrayList<String>();
	// for (FormColumn fc : formColumns) {
	// if (!tables.contains(fc.getBelongTable())) {
	// tables.add(fc.getBelongTable());
	// }
	// if (!tableIds.contains(fc.getTableId())) {
	// tableIds.add(fc.getTableId());
	// }
	// }
	// // 查询主表总记录条数
	// if (tables.size() > 0) {
	// String countSql = "select count(*) from sys_" + tables.get(0);
	// int total = jdbcTemplate.queryForInt(countSql);
	// t.setTotalCount(total);
	// }
	//
	// List<Domain> domains = new ArrayList<Domain>();
	// if (tableIds.size() > 1) {// 列表有多个表
	// for (int i = 1; i < tableIds.size(); i++) {
	// // 查询出主表和次表的关系
	// Relation relation = relationDao.selectByMainIdAndSubId(
	// tableIds.get(0), tableIds.get(i));
	// if (relation != null) {
	// if (relation.getRelationType() == Constants.RELATION_1_1) {// 一对一关系
	// String main = relation.getMainTableName();
	// String sub = relation.getSubTableName();
	// StringBuffer domainSql = new StringBuffer("select "
	// + main + ".*," + main + ".id as tbl_mid," + sub
	// + ".*," + sub + ".id as tbl_sid from sys_" + main + " "
	// + main + " left join " + StringUtil.getTableString(main, sub, 14)
	// + " ms on " + main + ".id=ms.tbl_mainid left join "
	// + sub + " " + sub + " on ms.tbl_subid=" + sub
	// + ".id  where 1=1 ");
	// Map<String, Object> map = qc.getQueryCondition();
	// Iterator iterator = map.entrySet().iterator();
	// List param = new ArrayList();
	// while (iterator.hasNext()) {
	// Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
	// .next();
	// if (entry.getValue() instanceof String) {
	// domainSql.append(" and " + entry.getKey()
	// + " like ? ");
	// param.add("%" + entry.getValue() + "%");
	// } else {
	// domainSql.append(" and " + entry.getKey()
	// + " =? ");
	// param.add(entry.getValue());
	// }
	//
	// }
	// domainSql.append(" order by " + main + ".id desc");
	// List retList = null;
	// if (qc.getPageSize() == -1) {
	// retList = jdbcTemplate.query(domainSql.toString(),
	// param.toArray(), new ColumnMapRowMapper());
	// } else {
	// retList = jdbcTemplate.querySP(
	// domainSql.toString(), param.toArray(),
	// qc.getCurrentIndex(), qc.getPageSize());
	// }
	// composeDomainList(domains, tables.get(0),
	// tables.get(i), tabulationColumns, retList,user);
	// } else if (relation.getRelationType() == Constants.RELATION_1_N) {
	// StringBuffer domainSql = new StringBuffer(
	// "select * from sys_" + tables.get(0)
	// + " where 1=1 ");
	// Map<String, Object> map = qc.getQueryCondition();
	// Iterator iterator = map.entrySet().iterator();
	// List param = new ArrayList();
	// while (iterator.hasNext()) {
	// Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
	// .next();
	// if (entry.getValue() instanceof String) {
	// domainSql.append(" and " + entry.getKey()
	// + " like ? ");
	// param.add("%" + entry.getValue() + "%");
	// } else {
	// domainSql.append(" and " + entry.getKey()
	// + " =? ");
	// param.add(entry.getValue());
	// }
	// }
	// domainSql.append(" order by id desc");
	// List retList = null;
	// if (qc.getPageSize() == -1) {
	// retList = jdbcTemplate.query(domainSql.toString(),
	// param.toArray(), new ColumnMapRowMapper());
	// } else {
	// retList = jdbcTemplate.querySP(
	// domainSql.toString(), param.toArray(),
	// qc.getCurrentIndex(), qc.getPageSize());
	// }
	// composeDomainList(domains, tables.get(0), null,
	// tabulationColumns, retList,user);
	// }
	// }
	// }
	// } else if (tableIds.size() == 1) {// 列表只有一个表
	// StringBuffer domainSql = new StringBuffer("select * from sys_"
	// + tables.get(0) + " where 1=1 ");
	// Map<String, Object> map = qc.getQueryCondition();
	// Iterator iterator = map.entrySet().iterator();
	// List param = new ArrayList();
	// while (iterator.hasNext()) {
	// Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
	// .next();
	// if (entry.getValue() instanceof String) {
	// domainSql.append(" and " + entry.getKey() + " like ? ");
	// param.add("%" + entry.getValue() + "%");
	// } else {
	// domainSql.append(" and " + entry.getKey() + " =? ");
	// param.add(entry.getValue());
	// }
	// }
	// domainSql.append(" order by id desc");
	// List retList = null;
	// if (qc.getPageSize() == -1) {
	// retList = jdbcTemplate.query(domainSql.toString(),
	// param.toArray(), new ColumnMapRowMapper());
	// } else {
	// retList = jdbcTemplate
	// .querySP(domainSql.toString(), param.toArray(),
	// qc.getCurrentIndex(), qc.getPageSize());
	// }
	// composeDomainList(domains, tables.get(0), null, tabulationColumns,
	// retList,user);
	// }
	//
	// t.setDomains(domains);
	//
	// // 查询出列表元素，作为表头
	// String listSql =
	// "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
	// +
	// "c.tbl_columnName as tbl_ziduanming,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tableid=b.id left join sys_columns c on a.ziduan=c.id "
	// +
	// "left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? and a.tbl_liebiaoxianshi=1 order by a.tbl_liebiaoxianshishunxu asc";
	// Object[] listParams = new Object[] { t.getFormId() };
	// List<FormColumn> listColumns = jdbcTemplate.query(listSql, listParams,
	// new FormColumnRowMapper());
	// List<FormColumnExtend> listColumnExtends = new
	// ArrayList<FormColumnExtend>();
	// for (FormColumn formColumn : listColumns) {
	// FormColumnExtend fce = new FormColumnExtend();
	// fce.setFormColumn(formColumn);
	// listColumnExtends.add(fce);
	// }
	// t.setTabulationColumnExtends(listColumnExtends);
	//
	// return t;
	// }

	@Override
	@Cacheable(value = "tabulationCache")
	public List<FormColumn> queryThead(String listSql, Object[] listParams) {
		return jdbcTemplate.query(listSql, listParams, new FormColumnRowMapper());
	}

	/**
	 * 组装domain
	 * 
	 * @param domains
	 * @param mainTable
	 * @param subTable
	 * @param columnList
	 * @param retList
	 */
	private void composeDomainList(List<Domain> domains, String mainTable, String subTable, List columnList, List retList, SysUser user) {
		if (domains.size() == 0) {// 组装主表
			for (int i = 0; retList != null && i < retList.size(); i++) {
				List ceList = new ArrayList();
				Map retMap = (Map) retList.get(i);
				Set keySet = retMap.keySet();

				Domain u = new Domain();
				// 设置table
				String tableSql = "select * from sys_tables where tbl_tableName=?";
				Object[] tableParams = new Object[] { mainTable };
				List tableList = this.jdbcTemplate.query(tableSql, tableParams, new TableRowMapper());
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

						// 下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
							Combox cb = comboxDao.findByID(c.getCompexId());
							ce.setComponent(cb);
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
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
						}

						ceList.add(ce);
					}
				}

				// 组装ID字段
				if (subTable != null) {
					ceList.add(getIdFormColumn(retMap, mainTable, "tbl_mid"));
					FormColumnExtend fce = getIdFormColumn(retMap, subTable, "tbl_sid");
					if (fce != null) {
						ceList.add(fce);
					}
				} else {
					ceList.add(getIdFormColumn(retMap, mainTable, "id"));
				}
				u.setTabulationColumnExtends(ceList);
				u.setId((Long)(retMap.get("tbl_mid")));
				domains.add(u);
			}
		} else {// 组装次表
			for (int i = 0; retList != null && i < retList.size(); i++) {
				List ceList = new ArrayList();
				Map retMap = (Map) retList.get(i);
				Set keySet = retMap.keySet();
				for (Domain domain : domains) {
					if (domain.getId().equals(retMap.get("tbl_mid"))) {// 找出对应的主表domain
						for (int j = 0; columnList != null && j < columnList.size(); j++) {
							FormColumn c = (FormColumn) (columnList.get(j));
							if (keySet.contains(c.getColumnName())) {
								FormColumnExtend ce = new FormColumnExtend();
								ce.setFormColumn(c);
								ce.setValue(retMap.get(c.getColumnName()));
								// 下拉框 代码
								if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
										&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
									Combox cb = comboxDao.findByID(c.getCompexId());
									ce.setComponent(cb);
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
									List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
									ce.setCodes(codes);
								}
								domain.getTabulationColumnExtends().add(ce);
							}
						}

						// 组装ID字段
						if (subTable != null) {
							domain.getTabulationColumnExtends().add(getIdFormColumn(retMap, subTable, "tbl_subid"));
						}
					}
				}
			}
		}
	}

	/**
	 * 查询出指定表的字段列表
	 * 
	 * @param relationTable
	 * @param relationColumn
	 * @return
	 */
	@Cacheable(value = "resourceCache", key = "'selectByRelation:'+#relationTable+'-'+#relationColumn")
	public List<Code> selectByRelation(String relationTable, String relationColumn) {
		String sql = "select id, " + relationColumn + " as tbl_relationColumn from " + relationTable + " order by comm_updateDate desc";
		List dl = this.jdbcTemplate.query(sql, new CodeRowMapper());
		return dl;
	}

	class CodeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Code d = new Code();
			d.setValue(rs.getString("id"));
			d.setText(rs.getString("tbl_relationColumn"));
			return d;
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
	@Cacheable(value = "tabulationCache")
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
		ce.setValue(Long.valueOf(id));
		return ce;
	}

	private void query(String sql, Object[] params, final Tabulation t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setTabulationName(rs.getString("tbl_liebiaomingcheng"));
				t.setCode(rs.getString("tbl_bianma"));
				t.setRight(rs.getString("tbl_quanxian"));
				t.setCreateBy(rs.getLong("comm_createBy"));
				t.setCreateDate(rs.getTimestamp("comm_createDate"));
				t.setUpdateBy(rs.getLong("comm_updateBy"));
				t.setUpdateDate(rs.getTimestamp("comm_updateDate"));
				t.setTemplateListId(rs.getInt("tbl_templatelistid"));
				t.setTabulationPath(rs.getString("tbl_liebiaolujing"));
				t.setTableName(rs.getString("tbl_tableName"));
				t.setTableId(rs.getLong("tbl_tableid"));
				t.setTableZhName(rs.getString("tbl_tableZhName"));
				t.setTemplateFileName(rs.getString("tbl_templatefilename"));
				t.setFormId(rs.getLong("tbl_formid"));
				t.setFormName(rs.getString("tbl_formName"));
				t.setIsSelect(rs.getString("tbl_isselect"));
				t.setIsModify(rs.getString("tbl_ismodify"));
				t.setModifyName(rs.getString("tbl_modifyname"));
				t.setListControlId(rs.getLong("tbl_listcontrolid"));
				t.setQueryControlId(rs.getLong("tbl_querycontrolid"));
				t.setAdvanceQueryControlId(rs.getLong("tbl_advancequerycontrolid"));
				t.setIsNumber(rs.getString("tbl_isshownumber"));
				t.setRemarks(rs.getString("tbl_remarks"));
				t.setDesc(rs.getString("tbl_beizhu"));
				t.setUserName(rs.getString("tbl_createname"));
				t.setUpdateName(rs.getString("tbl_updatename"));
				t.setSystemTeam(rs.getString("tbl_systemteam"));
			}
		});
	}

	public Tabulation compexQuery(String sql, Long id) {
		return (Tabulation)jdbcTemplate.queryForObject(sql, new Object[] { id }, new TabulationOneRowMapper());
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void deleteTabulation(Long id) {
		String sql = "delete from sys_liebiaosheji where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public TabulationColumn selectTabulationColumnById(Long id) {
		String sql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_columnName as tbl_ziduanming from sys_liebiaosheji a left join sys_tables b "
				+ "on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id where a.id=?";
		final TabulationColumn t = new TabulationColumn();
		final Object[] params = new Object[] { id };
		queryTabulationColumn(sql, params, t);
		return t;
	}

	private void queryTabulationColumn(String sql, Object[] params, final TabulationColumn t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setColumnId(rs.getString("tbl_ziduan"));
				t.setTableId(rs.getLong("tbl_tableid"));
				t.setColumnName(rs.getString("tbl_ziduanming"));
				t.setColumnZhName(rs.getString("tbl_ziduanmingzh"));
				t.setBelongTable(rs.getString("tbl_suoshubiao"));
				t.setTableZhName(rs.getString("tbl_suoshubiaoming"));
				t.setIsShowInList(rs.getInt("tbl_liebiaoxianshi"));
				t.setIsQuery(rs.getInt("tbl_chaxuntiaojian"));
				t.setIsDefaultQuery(rs.getInt("tbl_morenchaxun"));
				t.setIsUse(rs.getInt("tbl_qiyong"));
				t.setListOrder(rs.getInt("tbl_liebiaoxianshishunxu"));
			}
		});
	}

	private void queryTabulationQuery(String sql, Object[] params, final TabulationQuery t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setColumnId(rs.getString("tbl_ziduan"));
				t.setTableId(rs.getString("tbl_suoshubiao"));
				t.setTabulationId(rs.getLong("tbl_tabulation"));
				t.setColumnValue(rs.getString("tbl_ziduanzhi"));
				t.setCondition(rs.getInt("tbl_condition"));
				t.setOrder(String.valueOf(rs.getInt("tbl_order")));
			}
		});
	}

	@Cacheable(value = "tabulationCache")
	public List<FormColumn> selectColumnByTabulationId(Long id) {
		String sql = "select tbl_formid from sys_liebiao where id=?";
		Object[] params = new Object[] { id };
		String formId = (String) jdbcTemplate.queryForObject(sql, params, String.class);

		String formSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_columnName as tbl_ziduanming,"
				+ "c.tbl_datatype as tbl_datatype from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id where a.tbl_form=? and "
				+ "a.tbl_liebiaoxianshi=1 order by a.tbl_liebiaoxianshishunxu asc";
		Object[] formParams = new Object[] { formId };
		return jdbcTemplate.query(formSql, formParams, new FormColumnRowMapper());
	}

	@Cacheable(value = "tabulationCache")
	public List<FormColumn> selectColumnByFormId(Long formId) {
		String formSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_columnName as tbl_ziduanming,"
				+ "c.tbl_datatype as tbl_datatype from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id where a.tbl_form=? order by a.id asc,"
				+ "a.tbl_liebiaoxianshishunxu asc";
		Object[] formParams = new Object[] { formId };
		return jdbcTemplate.query(formSql, formParams, new FormColumnRowMapper());
	}

	class TabulationOneRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Tabulation t = new Tabulation();
			t.setId(rs.getLong("id"));
			t.setTabulationName(rs.getString("tbl_liebiaomingcheng"));
			t.setCode(rs.getString("tbl_bianma"));
			t.setRight(rs.getString("tbl_quanxian"));
			t.setCreateBy(rs.getLong("comm_createBy"));
			t.setCreateDate(rs.getDate("comm_createDate"));
			t.setUpdateBy(rs.getLong("comm_updateBy"));
			t.setUpdateDate(rs.getDate("comm_updateDate"));
			t.setTemplateListId(rs.getInt("tbl_templatelistid"));
			t.setTemplateListName(rs.getString("tbl_templatename"));
			t.setTabulationPath(rs.getString("tbl_liebiaolujing"));
			t.setTableName(rs.getString("tbl_tableName"));
			t.setTableId(rs.getLong("tbl_tableid"));
			t.setTableZhName(rs.getString("tbl_tableZhName"));
			t.setTemplateFileName(rs.getString("tbl_templatefilename"));
			t.setFormId(rs.getLong("tbl_formid"));
			t.setFormName(rs.getString("tbl_formName"));
			t.setIsSelect(rs.getString("tbl_isselect"));
			t.setIsModify(rs.getString("tbl_ismodify"));
			t.setModifyName(rs.getString("tbl_modifyname"));
			t.setListControlId(rs.getLong("tbl_listcontrolid"));
			t.setQueryControlId(rs.getLong("tbl_querycontrolid"));
			t.setAdvanceQueryControlId(rs.getLong("tbl_advancequerycontrolid"));
			t.setIsNumber(rs.getString("tbl_isshownumber"));
			t.setRemarks(rs.getString("tbl_remarks"));
			t.setDesc(rs.getString("tbl_beizhu"));
			t.setSystemTeam(rs.getString("tbl_systemteam"));
			return t;
		}
	}

	class FormColumnRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			FormColumn u = new FormColumn();
			u.setId(rs.getLong("id"));
			u.setColumnId(rs.getLong("tbl_ziduan"));
			u.setTableId(rs.getLong("tbl_tableid"));
			u.setColumnZhName(rs.getString("tbl_ziduanmingzh"));
			u.setTableZhName(rs.getString("tbl_suoshubiaoming"));
			u.setFormOrder(rs.getInt("tbl_biaodanxianshishunxu"));
			u.setIsShowInList(rs.getInt("tbl_liebiaoxianshi"));
			u.setIsQuery(rs.getInt("tbl_chaxuntiaojian"));
			u.setIsDefaultQuery(rs.getInt("tbl_morenchaxun"));
			u.setIsUse(rs.getInt("tbl_qiyong"));
			u.setListOrder(rs.getInt("tbl_liebiaoxianshishunxu"));
			u.setInputType(rs.getInt("tbl_luruleixing"));
			u.setTextBoxId(rs.getLong("tbl_wenbenkuang"));
			u.setComBoxId(rs.getLong("tbl_xialakuang"));
			u.setSelectDataType(rs.getInt("tbl_shujuleixing"));
			u.setCodeParentId(rs.getLong("tbl_daima"));
			u.setRelationTable(rs.getString("tbl_guanxibiao"));
			u.setRelationColumn(rs.getString("tbl_guanxiziduan"));
			u.setTextFieldId(rs.getLong("tbl_wenbenyu"));
			u.setRequired(rs.getInt("tbl_required"));
			u.setValidate(rs.getString("tbl_validate"));
			u.setDateId(rs.getLong("tbl_dateid"));
			u.setRadioId(rs.getLong("tbl_radioid"));
			u.setCheckBoxId(rs.getLong("tbl_checkboxid"));
			u.setSearchComBoxId(rs.getLong("tbl_searchcomboxid"));
			u.setTreeId(rs.getLong("tbl_treeid"));
			u.setCompexId(rs.getLong("tbl_compexid"));
			u.setColWidth(rs.getInt("tbl_kuandu"));
			u.setColUnit(rs.getString("tbl_danwei"));
			u.setDefaultValue(rs.getString("tbl_defaultvalue"));
			u.setCanSelectItem(rs.getString("tbl_canselectitem"));
			u.setCodeShowOrder(rs.getInt("tbl_codeshoworder"));
			u.setHasDefaultItem(rs.getInt("tbl_hasdefaultitem"));
			u.setDefaultSelectItem(rs.getString("tbl_defaultselectitem"));
			u.setRecordColumn(rs.getString("tbl_recordcolumn"));
			u.setIsShowIndex(rs.getInt("tbl_showindex"));
			u.setIsLinkView(rs.getInt("tbl_islinkview"));
			u.setSupportOrder(rs.getInt("tbl_supportorder"));

			String c = rs.getString("tbl_ziduanming");
			String t = rs.getString("tbl_suoshubiao");
			u.setDataType(rs.getString("tbl_datatype"));
			u.setColumnName(c);
			u.setBelongTable(t);
			u.setQueryStr(t + "_" + c);

			return u;
		}
	}

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public TabulationButtonDao getTabulationButtonDao() {
		return tabulationButtonDao;
	}

	public void setTabulationButtonDao(TabulationButtonDao tabulationButtonDao) {
		this.tabulationButtonDao = tabulationButtonDao;
	}

	public FormDao getFormDao() {
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	public RelationDao getRelationDao() {
		return relationDao;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	public TextBoxDao getTextBoxDao() {
		return textBoxDao;
	}

	public void setTextBoxDao(TextBoxDao textBoxDao) {
		this.textBoxDao = textBoxDao;
	}

	public ComboxDao getComboxDao() {
		return comboxDao;
	}

	public void setComboxDao(ComboxDao comboxDao) {
		this.comboxDao = comboxDao;
	}

	public SearchComboxDao getSearchComboxDao() {
		return searchComboxDao;
	}

	public void setSearchComboxDao(SearchComboxDao searchComboxDao) {
		this.searchComboxDao = searchComboxDao;
	}

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	public CatalogDao getCatalogDao() {
		return catalogDao;
	}

	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	public ButtonDao getButtonDao() {
		return buttonDao;
	}

	public void setButtonDao(ButtonDao buttonDao) {
		this.buttonDao = buttonDao;
	}

	public ListControlDao getListControlDao() {
		return listControlDao;
	}

	public void setListControlDao(ListControlDao listControlDao) {
		this.listControlDao = listControlDao;
	}

	public SelectManageDao getSelectManageDao() {
		return selectManageDao;
	}

	public void setSelectManageDao(SelectManageDao selectManageDao) {
		this.selectManageDao = selectManageDao;
	}

	public OrderManageDao getOrderManageDao() {
		return orderManageDao;
	}

	public void setOrderManageDao(OrderManageDao orderManageDao) {
		this.orderManageDao = orderManageDao;
	}

	public OperationManageDao getOperationManageDao() {
		return operationManageDao;
	}

	public void setOperationManageDao(OperationManageDao operationManageDao) {
		this.operationManageDao = operationManageDao;
	}

	public PageManageDao getPageManageDao() {
		return pageManageDao;
	}

	public void setPageManageDao(PageManageDao pageManageDao) {
		this.pageManageDao = pageManageDao;
	}

	public QueryControlDao getQueryControlDao() {
		return queryControlDao;
	}

	public void setQueryControlDao(QueryControlDao queryControlDao) {
		this.queryControlDao = queryControlDao;
	}

	public SeqcodeDao getSeqcodeDao() {
		return seqcodeDao;
	}

	public void setSeqcodeDao(SeqcodeDao seqcodeDao) {
		this.seqcodeDao = seqcodeDao;
	}

	public SysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(SysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateDetails(Tabulation tabulation) {
		try {
			String sql = "update sys_liebiao set tbl_isselect = ?,tbl_ismodify = ?,tbl_modifyname = ?,tbl_isshownumber = ?,tbl_listControlId = ? where id=?";
			Object[] params = new Object[] { tabulation.getIsSelect(), tabulation.getIsModify(), tabulation.getModifyName(),
					tabulation.getIsNumber(), tabulation.getListControlId(), tabulation.getId() };
			jdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public Tabulation selectTabulationByListId(Long id, QueryCriteria qc,
	// SysUser user) {
	// // 查询出列表 ,获得只含有基本信息的tabulation对象
	// String sql =
	// "select l.*,m.tbl_templatechname as tbl_templatename,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
	// +
	// "bd.tbl_biaodanming as tbl_formName from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd "
	// +
	// "on l.tbl_formid = bd.id where l.id=?";
	// Tabulation t = compexQuery(sql, id);
	//
	// // 列表操作按钮
	// List<TabulationOpt> tabulationOpts = this.selectTabulationOpt(
	// "select * from sys_oprtbutton where tbl_tabulation = '" +
	// t.getId()+"' order by tbl_shunxu asc",
	// null, 0, -1);
	// Map<String, Seqcode> curUserSeqcode= user.getCurUserSeqcode();
	// for (TabulationOpt tabulationOpt : tabulationOpts) {
	// if(null == curUserSeqcode.get(tabulationOpt.getCode())){
	// tabulationOpt.setHasAuth("1");
	// }else{
	// tabulationOpt.setHasAuth("0");
	// }
	// }
	// t.setTabulationOpts(tabulationOpts);
	//
	// // 列表组件
	// ListControl listControl = null;
	// if (t.getListControlId() != null) {
	// listControl = listControlDao.findListControlById(String.valueOf(t
	// .getListControlId()));
	//
	// SelectManage selectManage = selectManageDao
	// .findSelectManageById(String.valueOf(listControl
	// .getSelectId()));
	// OrderManage orderManage = orderManageDao.findOrderManageById(String
	// .valueOf(listControl.getOrderId()));
	// OperationManage operationManage = operationManageDao
	// .findOperationManageById(String.valueOf(listControl
	// .getOperationId()));
	// PageManage pageManage = pageManageDao.findPageManageById(String
	// .valueOf(listControl.getPageId()));
	//
	// listControl.setSelectManage(selectManage);
	// listControl.setOrderManage(orderManage);
	// listControl.setOperationManage(operationManage);
	// listControl.setPageManage(pageManage);
	//
	// if(pageManage!=null && pageManage.getPagesize() != null) {
	// if(pageManage.getPagesize()>0 &&
	// pageManage.getPagesize()>qc.getPageSize()){
	// qc.setPageSize(pageManage.getPagesize());
	// }
	// }
	//
	// t.setListControl(listControl);
	// }
	// // 查询组件
	// QueryControl queryControl = null;
	// if (t.getQueryControlId() != null) {
	// queryControl = queryControlDao.findQueryControlById(String
	// .valueOf(t.getQueryControlId()));
	// t.setQueryControl(queryControl);
	// }
	//
	// // 高级查询组件
	// AdvanceQueryControl advanceQueryControl = null;
	// if (t.getAdvanceQueryControlId() != null) {
	// if(t.getAdvanceQueryControlId().equals("-1")){
	// advanceQueryControl = new AdvanceQueryControl();
	// advanceQueryControl.setQueryControlName("无");
	// advanceQueryControl.setColumnNumber(4);
	// }else{
	// advanceQueryControl =
	// queryControlDao.findAdvanceQueryControlById(t.getAdvanceQueryControlId());
	// }
	// t.setAdvanceQueryControl(advanceQueryControl);
	// }
	//
	// // 列表按钮
	// List<TabulationButton> buttons =
	// tabulationButtonDao.selectByTabulationId(t.getId());
	//
	// for (TabulationButton tabulationButton : buttons) {
	// if(null == curUserSeqcode.get(tabulationButton.getCode())){
	// tabulationButton.setHasAuth("1");
	// }else{
	// tabulationButton.setHasAuth("0");
	// }
	// }
	// t.setTabulationButtons(buttons);
	//
	// // 列表筛选条件
	// List<TabulationQuery> querys = this.selectQueryByTabulationId(t.getId());
	//
	// // 构造出列表所包含的列
	// List<FormColumn> tabulationColumns =
	// this.selectColumnByTabulationId(t.getId());
	// List<FormColumn> formColumns = this.selectColumnByFormId(t.getFormId());
	// List<String> tables = new ArrayList<String>();
	// List<String> tableIds = new ArrayList<String>();
	//
	// //2012-11-14 sam start
	// 根据当前用户的角色与查询的表(formColumns.get(0).getTableId())查询用户所能操作的 数据级权限控制
	// StringBuilder userDataContent = new StringBuilder();
	// Set<Role> setRoles = user.getRoles();
	// if(setRoles != null){
	// for (Role role : setRoles) {
	// List<DataSearch> lstDataSearch =
	// dataSearchDao.getDataSearchContent(role.getId(),
	// formColumns.get(0).getTableId());
	// for (DataSearch dataSearch : lstDataSearch) {
	// if(userDataContent.length() > 0){
	// userDataContent.append(" and (");
	// }else{
	// userDataContent.append("(");
	// }
	//
	// userDataContent.append(dataSearch.getTblContent());
	// userDataContent.append(")");
	// }
	// }
	// }
	// //2012-11-14 sam end
	// Form form = formDao.selectById(t.getFormId());
	// listdata(qc, user, t, querys, tabulationColumns, formColumns,
	// form.getTableName(), form.getTableId(), userDataContent);
	//
	// // 查询出列表元素，作为表头
	// String listSql =
	// "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_dataType as tbl_datatype,"
	// +
	// "c.tbl_columnName as tbl_ziduanming,d.tbl_mingcheng as tbl_tabmingcheng from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c "
	// +
	// "on a.tbl_ziduan=c.id left join sys_biaodantab d on a.tbl_tabid=d.id where a.tbl_form=? and a.tbl_partitionid = -1 order by a.tbl_liebiaoxianshishunxu asc";
	// Object[] listParams = new Object[] { t.getFormId() };
	// List<FormColumn> listColumns = jdbcTemplate.query(listSql, listParams,
	// new FormColumnRowMapper());
	// List<FormColumnExtend> listColumnExtends = new
	// ArrayList<FormColumnExtend>();
	// for (FormColumn formColumn : listColumns) {
	// FormColumnExtend fce = new FormColumnExtend();
	// fce.setFormColumn(formColumn);
	// if (formColumn.getCompexId() != null
	// && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
	// && formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
	// Combox cb = comboxDao.findByID(formColumn.getCompexId());
	// fce.setComponent(cb);
	//
	// String order="asc";
	// if(formColumn.getCodeShowOrder()!=null){
	// if(formColumn.getCodeShowOrder()==0){
	// order="asc";
	// }else if(formColumn.getCodeShowOrder()==1){
	// order="desc";
	// }
	// }
	//
	// List<Dictionary> dl=null;
	// if(formColumn.getCanSelectItem()!=null &&
	// !formColumn.getCanSelectItem().equals("")){
	// dl =
	// dictionaryDao.selectByParent(formColumn.getCodeParentId(),formColumn.getCanSelectItem(),order);
	// }else{
	// dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(),order);
	// }
	//
	// List<Code> codes = new ArrayList<Code>();
	// for (Dictionary d : dl) {
	// Code code = new Code(d.getId(), d.getName(), d.getValue());
	// codes.add(code);
	// }
	// fce.setCodes(codes);
	// } else if (formColumn.getCompexId() != null
	// && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
	// && formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
	// SearchCombox scb = searchComboxDao.findByID(formColumn.getCompexId());
	// fce.setComponent(scb);
	//
	// String order="asc";
	// if(formColumn.getCodeShowOrder()!=null){
	// if(formColumn.getCodeShowOrder()==0){
	// order="asc";
	// }else if(formColumn.getCodeShowOrder()==1){
	// order="desc";
	// }
	// }
	//
	// List<Dictionary> dl=null;
	// if(formColumn.getCanSelectItem()!=null &&
	// !formColumn.getCanSelectItem().equals("")){
	// dl =
	// dictionaryDao.selectByParent(formColumn.getCodeParentId(),formColumn.getCanSelectItem(),order);
	// }else{
	// dl = dictionaryDao.selectByParent(formColumn.getCodeParentId(),order);
	// }
	//
	// List<Code> codes = new ArrayList<Code>();
	// for (Dictionary d : dl) {
	// Code code = new Code(d.getId(), d.getName(), d.getValue());
	// codes.add(code);
	// }
	// fce.setCodes(codes);
	// } else if (formColumn.getCompexId() != null
	// && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
	// && formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION)
	// {
	// Combox cb = comboxDao.findByID(formColumn.getCompexId());
	// fce.setComponent(cb);
	// List<Code> codes =
	// selectByRelation(formColumn.getRelationTable(),formColumn.getRelationColumn());
	// fce.setCodes(codes);
	// } else if (formColumn.getCompexId() != null
	// && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
	// && formColumn.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION)
	// {
	// SearchCombox scb = searchComboxDao.findByID(formColumn.getCompexId());
	// fce.setComponent(scb);
	// List<Code> codes = selectByRelation(
	// formColumn.getRelationTable(),
	// formColumn.getRelationColumn());
	// fce.setCodes(codes);
	// } else if (formColumn.getCompexId() != null
	// && formColumn.getInputType() == Constants.INPUT_TYPE_TEXTBOX_TREE) {
	// Catalog catalog = catalogDao.selectById(formColumn.getCompexId());
	// fce.setCatalog(catalog);
	// }
	// if(formColumn.getInputType()==7 && formColumn.getCompexId()!=null){
	// MgrTree mgrTree = mgrTreeDao.selectById(formColumn.getCompexId());
	// fce.setTableType(mgrTree.getTableType());
	// }
	// listColumnExtends.add(fce);
	// }
	// t.setTabulationColumnExtends(listColumnExtends);
	//
	// return t;
	// }

	@Override
	@Cacheable(value = "tabulationCache")
	public Tabulation selectTabulationBySeqcode(String seqcode) {
		String sql = "select l.*,m.tbl_templatefilename as tbl_templatefilename,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,"
				+ "bd.tbl_biaodanming as tbl_formName from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd "
				+ "on l.tbl_formid = bd.id where l.tbl_bianma=?";
		final Tabulation t = new Tabulation();
		final Object[] params = new Object[] { seqcode };
		query(sql, params, t);

		// 列表按钮
		List<TabulationButton> buttons = tabulationButtonDao.selectByTabulationId(t.getId());
		t.setTabulationButtons(buttons);

		return t;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List selectTabulationQuery(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(where, args, new TabulationQueryRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(where, args, startRow, rowsCount, new TabulationQueryRowMapper());
		}
		return retList;
	}

	@Override
	public int countTabulationQuery(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void insertTabulationQuery(TabulationQuery tabulationQuery) {
		String sql = "insert into sys_liebiaoquery(id,tbl_suoshubiao,tbl_ziduan,tbl_ziduanzhi,tbl_condition,tbl_relation,tbl_tabulation,tbl_order) values (?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), tabulationQuery.getTableId(), tabulationQuery.getColumnId(),
				tabulationQuery.getColumnValue(), tabulationQuery.getCondition(), tabulationQuery.getRelation(), tabulationQuery.getTabulationId(),
				tabulationQuery.getOrder() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateTabulationQuery(TabulationQuery tabulationQuery) {
		String sql = "update sys_liebiaoquery set tbl_suoshubiao=?,tbl_ziduan=?,tbl_ziduanzhi=?,tbl_condition=?,tbl_relation=?,tbl_tabulation=?,tbl_order=? where id=?";
		Object[] params = new Object[] { tabulationQuery.getTableId(), tabulationQuery.getColumnId(), tabulationQuery.getColumnValue(),
				tabulationQuery.getCondition(), tabulationQuery.getRelation(), tabulationQuery.getTabulationId(), tabulationQuery.getOrder(),
				tabulationQuery.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void deleteTabulationQuery(Long id) {
		String sql = "delete from sys_liebiaoquery where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public TabulationQuery selectTabulationQueryById(Long tabulationQueryId) {
		String sql = "select a.* from sys_liebiaoquery a where a.id=?";
		final TabulationQuery t = new TabulationQuery();
		final Object[] params = new Object[] { tabulationQueryId };
		queryTabulationQuery(sql, params, t);
		return t;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List<TabulationQuery> selectQueryByTabulationId(Long tabulationId) {
		String sql = "select a.*,t.tbl_tableName as tbl_tableName,t.tbl_tableZhName as tbl_tableZhName,c.tbl_columnName as tbl_columnName,c.tbl_columnZhName as tbl_columnZhName from "
				+ "sys_liebiaoquery a left join sys_tables t on a.tbl_suoshubiao = t.id left join sys_columns c on a.tbl_ziduan = c.id where a.tbl_tabulation=? order by a.tbl_order asc";
		Object[] params = new Object[] { tabulationId };
		List<TabulationQuery> querys = jdbcTemplate.query(sql, params, new TabulationQueryRowMapper());
		return querys;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List selectTabulationOpt(String sql, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		if (rowsCount == -1) {// 查询出所有记录
			retList = this.jdbcTemplate.query(sql, args, new TabulationOptRowMapper());
		} else {
			retList = this.jdbcTemplate.querySP(sql, args, startRow, rowsCount, new TabulationOptRowMapper());
		}
		return retList;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public int countTabulationOpt(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void deleteTabulationOpt(Long id) {
		String sql = "delete from sys_oprtbutton where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateTabulationOpt(TabulationOpt tabulationOpt) {
		String sql = "update sys_oprtbutton set tbl_oprtname=?,tbl_shunxu=?,tbl_buttonid=?,tbl_tabulation=?,tbl_showname=?,tbl_funcname=?,tbl_comment=? where id=?";
		Object[] params = new Object[] { tabulationOpt.getName(), tabulationOpt.getOrder(), tabulationOpt.getButtonId(),
				tabulationOpt.getTabulationId(), tabulationOpt.getShowName(), tabulationOpt.getFuncName(), tabulationOpt.getFcomment(),
				tabulationOpt.getId() };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public Long insertTabulationQuery(TabulationOpt tabulationOpt) {
		Long id = UniqueIdUtil.genId();
		String sql = "insert into sys_oprtbutton(id,tbl_oprtname,tbl_shunxu,tbl_buttonid,tbl_tabulation,tbl_showname,tbl_funcname,tbl_comment) values (?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { id, tabulationOpt.getName(), tabulationOpt.getOrder(), tabulationOpt.getButtonId(),
				tabulationOpt.getTabulationId(), tabulationOpt.getShowName(), tabulationOpt.getFuncName(), tabulationOpt.getFcomment() };
		jdbcTemplate.update(sql, params);
		return id;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public TabulationOpt selectTabulationOptById(Long tabulationOptId) {
		String sql = "select o.*,b.tbl_url as tbl_url from sys_oprtbutton o left join sys_button b on o.tbl_buttonid = b.id where o.id=?";
		final TabulationOpt t = new TabulationOpt();
		final Object[] params = new Object[] { tabulationOptId };
		queryTabulationOpt(sql, params, t);
		return t;
	}

	private void queryTabulationOpt(String sql, Object[] params, final TabulationOpt t) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				t.setId(rs.getLong("id"));
				t.setName(rs.getString("tbl_oprtname"));
				t.setOrder(rs.getInt("tbl_shunxu"));
				t.setTabulationId(rs.getLong("tbl_tabulation"));
				t.setButtonId(rs.getLong("tbl_buttonid"));
				t.setCode(rs.getString("tbl_bianma"));
				t.setShowName(rs.getString("tbl_showname"));
				t.setFuncName(rs.getString("tbl_funcname"));
				t.setFcomment(rs.getString("tbl_comment"));
				t.setUrl(rs.getString("tbl_url"));
			}
		});
	}

	@Override
	public boolean tableHasColumn(String tableName, String columnName) {
		Boolean has = false;
		String _sql = "select * from sys_columns where tbl_belongTable=? and tbl_columnName=?";
		Object[] _params = new Object[] { tableName, columnName };
		List list = jdbcTemplate.queryForList(_sql, _params);
		if (list.size() > 0) {
			has = true;
		}
		return has;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List findCataByListId(Long listId) {
		String _sql = "select * from sys_catalog where tbl_listid = ?";
		Object[] _params = new Object[] { listId };
		return jdbcTemplate.queryForList(_sql, _params);
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List useFormCount(Long formId, Long id) {
		String _sql = "select * from sys_liebiao where tbl_formid = ? and tbl_formid != '-1'";
		Object[] _objs = new Object[] { formId };
		if (id != null && !id.equals("")) {
			_sql = "select * from sys_liebiao where tbl_formid = ? and tbl_formid != '-1' and id <> ?";
			_objs = new Object[] { formId, id };
		}
		List list = null;
		try {
			list = jdbcTemplate.queryForList(_sql, _objs);
		} catch (Exception e) {
			list = new ArrayList();
		}
		return list;
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public List<TabulationOpt> findTabulationOpt(Long tabulationId) {
		String sql = "select o.*,b.tbl_url as tbl_url from sys_oprtbutton o left join sys_button b on o.tbl_buttonid = b.id where o.tbl_tabulation = ? order by o.tbl_shunxu asc";
		Object[] params = new Object[] { tabulationId };
		return jdbcTemplate.query(sql, params, new TabulationOptRowMapper());
	}

	@Override
	@Cacheable(value = "tabulationCache")
	public Tabulation selectTabulationByFormId(Long formId) {
		String sql = "select l.*,m.tbl_templatechname as tbl_templatename,t.tbl_tableZhName as tbl_tableZhName,bd.tbl_biaodanming as tbl_formName,u.tbl_username as tbl_createusername,u2.tbl_username as tbl_updateusername,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_liebiao l left join sys_template m on l.tbl_templatelistid = m.id left join sys_tables t on l.tbl_tableid = t.id left join sys_biaodan bd on l.tbl_formid=bd.id left join sys_user u on l.comm_createBy = u.id   left join sys_user u2 on l.comm_updateBy = u2.id   where l.tbl_formid = ?";
		Object[] params = new Object[] { formId };
		return (Tabulation) jdbcTemplate.queryForObject(sql, params, new TabulationRowMapper());
	}

	@Cacheable(value = "tabulationCache")
	public List<FormColumn> selectAllColumnsByTabulationId(Long id) {
		String sql = "select tbl_formid from sys_liebiao where id=?";
		Object[] params = new Object[] { id };
		String formId = (String) jdbcTemplate.queryForObject(sql, params, String.class);

		String formSql = "select a.*,b.tbl_tableName as tbl_suoshubiao,b.tbl_tableZhName as tbl_suoshubiaoming,c.tbl_columnZhName as tbl_ziduanmingzh,c.tbl_columnName as tbl_ziduanming,"
				+ "c.tbl_datatype as tbl_datatype from sys_biaodansheji a left join sys_tables b on a.tbl_tableid=b.id left join sys_columns c on a.tbl_ziduan=c.id where a.tbl_form=? order by a.tbl_liebiaoxianshishunxu asc";
		Object[] formParams = new Object[] { formId };
		return jdbcTemplate.query(formSql, formParams, new FormColumnRowMapper());
	}

	@Cacheable(value = "tabulationCache")
	public List<Tabulation> queryTabulationByFormid(Long formid) {
		return jdbcTemplate.query("select id from sys_liebiao where tbl_formid = ?", new Object[] { formid }, new TabulationByFRowMapper());
	}

	class TabulationByFRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Tabulation t = new Tabulation();
			t.setId(rs.getLong("id"));
			return t;
		}
	}

	@Override
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void logicDelete(Long id) {
		// 删除列表
		Object[] params = new Object[] { id };
		String sql = "update sys_liebiao set tbl_dstatus = 1 where id = ?";
		jdbcTemplate.update(sql, params);
		// 删除列表按钮
		String selbtnsql = "select * from sys_liebiaobutton where tbl_tabulation=?";
		List<Map<String, Object>> btnList = jdbcTemplate.queryForList(selbtnsql, params);
		for (int i = 0; i < btnList.size(); i++) {
			// 删除编码
			String code = ((Map<String, Object>) btnList.get(i)).get("tbl_bianma").toString();
			seqcodeDao.logicDeleteSeq(code);
		}

		String buttonSql = "update sys_liebiaobutton set tbl_dstatus = 1 where tbl_tabulation = ?";
		jdbcTemplate.update(buttonSql, params);
		// 删除列表操作按钮
		String seloptbtnsql = "select * from sys_oprtbutton where tbl_tabulation=?";
		List<Map<String, Object>> optbtnList = jdbcTemplate.queryForList(seloptbtnsql, params);
		for (int i = 0; i < optbtnList.size(); i++) {
			// 删除编码
			String code = ((Map<String, Object>) optbtnList.get(i)).get("tbl_bianma").toString();
			seqcodeDao.logicDeleteSeq(code);
		}

		String optSql = "update sys_oprtbutton set tbl_dstatus = 1 where tbl_tabulation = ?";
		jdbcTemplate.update(optSql, params);
		// 删除列表筛选条件
		String querySql = "update sys_liebiaoquery set tbl_dstatus = 1 where tbl_tabulation = ?";
		jdbcTemplate.update(querySql, params);
		// String sql3 =
		// "delete from sys_liebiaosheji where tabulation = '"+id+"'";
		// list.add(sql3);
	}

}
