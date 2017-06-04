package com.cloudstong.platform.resource.metadata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.SqlUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.dictionary.dao.DictionaryDao;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.form.dao.FormDao;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.dao.CompexDomainDao;
import com.cloudstong.platform.resource.metadata.dao.RelationDao;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.model.TableRowMapper;
import com.cloudstong.platform.resource.personChiose.dao.PersonChioseDao;
import com.cloudstong.platform.resource.tabulation.dao.TabulationDao;
import com.cloudstong.platform.resource.tree.dao.MgrTreeDao;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.system.dao.IUserDao;
import com.cloudstong.platform.system.dao.SysLogDao;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysLog;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-13
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:配置模块操作数据库接口实现类
 */
@Repository("compexDomainDao")
public class CompexDomainDaoImpl implements CompexDomainDao {
	private final Log log = LogFactory.getLog(getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Resource
	private FormDao formDao;

	@Resource
	private RelationDao relationDao;

	@Resource
	private DictionaryDao dictionaryDao;

	@Resource
	private CatalogDao catalogDao;

	@Resource
	private TabulationDao tabulationDao;

	@Resource
	private ColumnDao columnDao;

	@Resource
	SysLogDao sysLogDao;

	@Resource
	private MgrTreeDao mgrTreeDao;

	@Resource
	private PersonChioseDao personChioseDao;

	@Resource
	private IUserDao userDao;

	@Override
	public void update(Domain domain, SysUser user) {
		String name = "";// 作为记录日志的主名称
		List cList = domain.getFormColumnExtends();
		StringBuffer sql = new StringBuffer("update " + domain.getTable().getTableName() + " set ");
		List paramList = new ArrayList();
		for (int i = 0; i < cList.size(); i++) {
			FormColumnExtend ce = (FormColumnExtend) cList.get(i);
			if ("id".equals(ce.getFormColumn().getColumnName())) {
				continue;
			}
			if (ce.getFormColumn().getColumnName().contains("_name")) {
				name = String.valueOf(ce.getValue());
			}
			sql.append(ce.getFormColumn().getColumnName() + "=?,");
			paramList.add(ce.getValue());
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where id=?");
		paramList.add(domain.getId());
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());

		// 更新数据时记录系统日志
//		SysLog sysLog = new SysLog();
//		sysLog.setOperator(user.getId());
//		sysLog.setIp(user.getIp());
//		sysLog.setName(name);
//		sysLog.setOperationModule(catalogDao.findCatalogByListId(
//				tabulationDao.selectTabulationByFormId(domain.getFormColumnExtends().get(0).getFormColumn().getFormId()).getId()).getId());
//		sysLog.setOperationContent("5");
//		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
//		sysLog.setSql(SqlUtil.getRealSql(sql.toString(), paramList.toArray()));
		// sysLogDao.doSaveLog(sysLog);

		// String _sName = "sys_biaodanbutton";
		// for(int i=0; i<=300; i++){
		// try {
		// List _lstResult = jdbcTemplate.queryForList("select tbl_bianma from "+_sName+" where id="+i);
		// if(_lstResult != null && _lstResult.size()>0){
		// Thread.sleep(1000L);
		// this.jdbcTemplate.update("update "+_sName+" set tbl_bianma = 'ROOT-CATG-FUNC-FORM-BUTN-"+DateUtil.getNowTimeSecond()+"' where id="+i);
		// }
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
	}

	@Override
	public Long insert(Domain domain, SysUser user) {
		Long id = UniqueIdUtil.genId();
		String name = "";// 作为记录日志的主名称
		List cList = domain.getFormColumnExtends();
		final StringBuffer sql = new StringBuffer("insert into " + domain.getTable().getTableName() + " (id,");
		StringBuffer valueSql = new StringBuffer(" values(?,");
		final List paramList = new ArrayList();
		paramList.add(id);
		for (int i = 0; i < cList.size(); i++) {
			FormColumnExtend ce = (FormColumnExtend) cList.get(i);
			if ("id".equals(ce.getFormColumn().getColumnName())) {
				continue;
			}
			if (ce.getFormColumn().getColumnName().contains("_name")) {
				name = String.valueOf(ce.getValue());
			}
			sql.append(ce.getFormColumn().getColumnName() + ",");
			valueSql.append("?,");
			paramList.add(ce.getValue());
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") ");
		valueSql.deleteCharAt(valueSql.length() - 1);
		valueSql.append(")");
		final String insertSql = sql.append(valueSql).toString();
		jdbcTemplate.update(insertSql, paramList.toArray());

		// 插入数据时记录系统日志
//		SysLog sysLog = new SysLog();
//		sysLog.setOperator(user.getId());
//		sysLog.setIp(user.getIp());
//		sysLog.setName(name);
//		sysLog.setOperationModule(catalogDao.findCatalogByListId(
//				tabulationDao.selectTabulationByFormId(domain.getFormColumnExtends().get(0).getFormColumn().getFormId()).getId()).getId());
//		sysLog.setOperationContent("4");
//		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
//		sysLog.setSql(SqlUtil.getRealSql(insertSql.toString(), paramList.toArray()));
		// sysLogDao.doSaveLog(sysLog);
		return id;
	}

	@Override
	public void insertMiddleTable(String table, Long mainid, Long subid) {
		String sql = "insert into " + table + " (id,tbl_mainid,tbl_subid) values(?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), mainid, subid };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void delete(String domain, Long id, Long formId, SysUser user) {
		String name = "";
		String selSql = "select * from " + domain + " where id = ?";

		String sql = "delete from " + domain + " where id=?";
		Object[] params = new Object[] { id };

		// 查找名称字段
		List<Map<String, Object>> list = jdbcTemplate.queryForList(selSql, params);
		if (list.size() > 0) {
			Map<String, Object> record = list.get(0);
			Set<Entry<String, Object>> entry = record.entrySet();
			Iterator<Entry<String, Object>> it = entry.iterator();
			while (it.hasNext()) {
				Entry<String, Object> et = it.next();
				if (et.getKey().contains("_name")) {
					name = String.valueOf(et.getValue());
				}
			}
		}
		// 执行删除操作
		jdbcTemplate.update(sql, params);

		// 删除数据时记录系统日志
//		SysLog sysLog = new SysLog();
//		sysLog.setOperator(user.getId());
//		sysLog.setIp(user.getIp());
//		sysLog.setName(name);
//		sysLog.setOperationModule(catalogDao.findCatalogByListId(tabulationDao.selectTabulationByFormId(formId).getId()).getId());
//		sysLog.setOperationContent("6");
//		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
//		sysLog.setSql(SqlUtil.getRealSql(sql, params));
		// sysLogDao.doSaveLog(sysLog);
	}

	@Override
	public void deleteTemp(String domain, Long id) {
		String sql = "delete from " + domain + " where tbl_subid = ?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void deleteMiddleTable(String table, Long mainId, Long subId) {
		String sql = "delete from " + table + " where tbl_mainid=? and tbl_subid=?";
		Object[] params = new Object[] { mainId, subId };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public List<FormColumnExtend> selectSubListColumn(Long tabId, Long partitionId) {
		List<FormColumn> listColumns = formDao.selectByTabAndPid(tabId, partitionId);
		List<FormColumnExtend> formColumnExtends = new ArrayList<FormColumnExtend>();
		for (FormColumn formColumn : listColumns) {
			FormColumnExtend fce = new FormColumnExtend();
			fce.setFormColumn(formColumn);
			formColumnExtends.add(fce);
		}
		return formColumnExtends;
	}

	@Override
	public List<Domain> selectDomains(QueryCriteria qc, Long tabId, Long partitionId, DomainVO vo, SysUser user) {
		List<Domain> dms = new ArrayList<Domain>();
		List<FormColumn> listColumns = formDao.selectByTabAndPid(tabId, partitionId);

		String subTable = null;
		if (listColumns != null && listColumns.size() > 0) {
			subTable = listColumns.get(0).getBelongTable();
		}

		if (vo.getTable() != null) {
			String sql = "select a.* from " + subTable + " a," + StringUtil.getTableString(vo.getTable(), subTable, 14)
					+ " b where a.id=b.tbl_subid and b.tbl_mainid=? ";
			Object[] param = new Object[] { vo.getDomainId() };
			List retList = null;
			if (qc.getPageSize() == -1) {// 查询出所有记录
				retList = this.jdbcTemplate.querySP(sql.toString(), param, 0, count(subTable, vo));
			} else {
				retList = this.jdbcTemplate.querySP(sql.toString(), param, qc.getCurrentIndex(), qc.getPageSize());
			}

			for (int i = 0; retList != null && i < retList.size(); i++) {
				Domain u = new Domain();
				Table table = new Table();
				table.setTableName(subTable);
				u.setTable(table);

				// 设置column
				List listCeList = new ArrayList();
				Map retMap = (Map) retList.get(i);
				Set keySet = retMap.keySet();
				for (int j = 0; listColumns != null && j < listColumns.size(); j++) {
					FormColumn c = (FormColumn) (listColumns.get(j));
					if (keySet.contains(c.getColumnName())) {
						FormColumnExtend ce = new FormColumnExtend();
						ce.setFormColumn(c);
						ce.setValue(retMap.get(c.getColumnName()));
						// 下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
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
						// 搜索下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
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
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
						}
						// 搜索下拉框 关系表
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
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
						if (c.getInputType() == 7) {
							MgrTree mgrTree = mgrTreeDao.selectById(c.getCompexId());
							if (mgrTree.getTableType() == null) {
								mgrTree.setTableType("");
							}
							if (!mgrTree.getTableType().equals("1")) {
								CommonTree commonTree = mgrTreeDao.findCommonTree(mgrTree, (Long) ce.getValue());
								ce.setValueText(commonTree.getName());
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
						listCeList.add(ce);
					}
				}
				u.setTabulationColumnExtends(listCeList);
				u.setId(Long.valueOf(retMap.get("id").toString()));
				dms.add(u);
			}
		}

		return dms;
	}

	/**
	 * 查询出指定表的字段列表
	 * 
	 * @param relationTable
	 * @param relationColumn
	 * @return
	 */
	private List<Code> selectByRelation(String relationTable, String relationColumn) {
		String sql = "select id," + relationColumn + " as tbl_relationColumn from " + relationTable + " order by id desc";
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

	@Override
	public int count(String domain, DomainVO vo) {
		if (vo.getTable() == null || "".equals(vo.getTable())) {
			return 0;
		}
		String mainTable = vo.getTable();
		Long mianId = vo.getDomainId();
		String sql = "select count(*) from " + domain + " a," + StringUtil.getTableString(mainTable, domain, 14)
				+ " b where a.id=b.tbl_subid and b.tbl_mainid = " + mianId;
		return jdbcTemplate.queryForInt(sql);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'singleCount:'+#domain+#vo+#relColumnId")
	public int singleCount(String domain, DomainVO vo, Long relColumnId) {
		if (vo.getTable() == null || "".equals(vo.getTable())) {
			return 0;
		}
		String mainTable = vo.getTable();
		Long mianId = vo.getDomainId();
		String sql = null;
		if (relColumnId != null && !relColumnId.equals("-1")) {
			Column _column = columnDao.selectById(relColumnId);
			sql = "select count(*) from " + domain + " where " + _column.getColumnName() + " = '" + mianId + "'";
			;
		} else {
			sql = "select count(*) from " + domain + " a," + StringUtil.getTableString(mainTable, domain, 14)
					+ " b where a.id=b.tbl_subid and b.tbl_mainid = '" + mianId + "'";
		}
		return jdbcTemplate.queryForInt(sql);
	}

	@Override
	public void doSaveSub(Domain domain, Long domainId, String domainTable, SysUser user) {
		String name = "";
		String insertSql = "";
		List paramList = null;
		Relation relation = relationDao.selectByMainNameAndSubName(domainTable, domain.getTable().getTableName());
		if (relation.getRelationType() == Constants.RELATION_1_N) {
			Long id = UniqueIdUtil.genId();
			List cList = domain.getFormColumnExtends();
			final StringBuffer sql = new StringBuffer("insert into " + domain.getTable().getTableName() + " (id,");
			StringBuffer valueSql = new StringBuffer(" values(?,");
			paramList = new ArrayList();
			paramList.add(id);
			for (int i = 0; i < cList.size(); i++) {
				FormColumnExtend ce = (FormColumnExtend) cList.get(i);
				if ("id".equals(ce.getFormColumn().getColumnName())) {
					continue;
				}
				if (ce.getFormColumn().getColumnName().contains("_name")) {
					name = String.valueOf(ce.getValue());
				}
				sql.append(ce.getFormColumn().getColumnName() + ",");
				valueSql.append("?,");
				paramList.add(ce.getValue());
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
			valueSql.deleteCharAt(valueSql.length() - 1);
			valueSql.append(")");
			insertSql = sql.append(valueSql).toString();
			jdbcTemplate.update(insertSql, paramList.toArray());

			Long subid = id;
			String middleSql = "insert into " + StringUtil.getTableString(domainTable, domain.getTable().getTableName(), 14)
					+ " (id,tbl_mainid,tbl_subid) values(?,?,?)";
			Object[] args = new Object[] { UniqueIdUtil.genId(), domainId, subid };
			this.jdbcTemplate.update(middleSql, args);
		} else if (relation.getRelationType() == Constants.RELATION_M_N) {
			List cList = domain.getFormColumnExtends();
			String subid = null;
			for (int i = 0; i < cList.size(); i++) {
				FormColumnExtend ce = (FormColumnExtend) cList.get(i);
				if ("id".equals(ce.getFormColumn().getColumnName())) {
					continue;
				}
				try {
					subid = (String) ce.getValue();
					break;
				} catch (Exception e) {
					continue;
				}
			}

			Long uuid = UniqueIdUtil.genId();
			String middleSql = "insert into " + StringUtil.getTableString(domainTable, domain.getTable().getTableName(), 14)
					+ " (id,tbl_mainid,tbl_subid) values(?,?,?)";
			Object[] args = new Object[] { uuid, domainId, subid };

			insertSql = middleSql;
			paramList = new ArrayList();
			paramList.add(uuid);
			paramList.add(domainId);
			paramList.add(subid);

			this.jdbcTemplate.update(middleSql, args);
		}
		// 向数据库插入数据时记录系统日志
//		SysLog sysLog = new SysLog();
//		sysLog.setOperator(user.getId());
//		sysLog.setIp(user.getIp());
//		sysLog.setName(name);
//		sysLog.setOperationModule(catalogDao.findCatalogByListId(
//				tabulationDao.selectTabulationByFormId(domain.getFormColumnExtends().get(0).getFormColumn().getFormId()).getId()).getId());
//		sysLog.setOperationContent("4");
//		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
//		sysLog.setSql(SqlUtil.getRealSql(insertSql, paramList.toArray()));
		// sysLogDao.doSaveLog(sysLog);
	}

	@Override
	public void doUpdateSub(Domain domain, SysUser user) {
		String name = "";
		List cList = domain.getFormColumnExtends();
		StringBuffer sql = new StringBuffer("update " + domain.getTable().getTableName() + " set ");
		List paramList = new ArrayList();
		for (int i = 0; i < cList.size(); i++) {
			FormColumnExtend ce = (FormColumnExtend) cList.get(i);
			if ("id".equals(ce.getFormColumn().getColumnName())) {
				continue;
			}
			if (ce.getFormColumn().getColumnName().contains("name")) {
				name = String.valueOf(ce.getValue());
			}
			if (ce.getFormColumn().getInputType() == 14) {
				sql.append(ce.getFormColumn().getColumnName() + "='" + ce.getValue() + "',");
			} else {
				sql.append(ce.getFormColumn().getColumnName() + "=?,");
				paramList.add(ce.getValue());
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where id=?");
		paramList.add(domain.getId());
		this.jdbcTemplate.update(sql.toString(), paramList.toArray());

		// 更新数据库数据时记录系统日志
//		SysLog sysLog = new SysLog();
//		sysLog.setOperator(user.getId());
//		sysLog.setIp(user.getIp());
//		sysLog.setName(name);
//		sysLog.setOperationModule(catalogDao.findCatalogByListId(
//				tabulationDao.selectTabulationByFormId(domain.getFormColumnExtends().get(0).getFormColumn().getFormId()).getId()).getId());
//		sysLog.setOperationContent("5");
//		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
//		sysLog.setSql(SqlUtil.getRealSql(sql.toString(), paramList.toArray()));
		// sysLogDao.doSaveLog(sysLog);
	}

	@Override
	@Cacheable(value = "domainCache", key = "'queryTableData:'+#psTableName+#psParams")
	public List queryTableData(String psTableName, String psParams) {
		StringBuilder _sbdSql = new StringBuilder();
		Object[] _argObj = null;
		if (psParams != null) {
			_argObj = new Object[psParams.split(";").length];
		}
		try {
			_sbdSql.append("select * from ");
			_sbdSql.append(psTableName);
			_sbdSql.append(" where 1=1 ");
			int i = 0;
			if (psParams != null && !"".equals(psParams.trim())) {
				for (String str : psParams.split(";")) {
					_sbdSql.append(" and " + str.split(":")[0] + "=? ");
					_argObj[i] = str.split(":")[1];
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.jdbcTemplate.queryForList(_sbdSql.toString(), _argObj);
	}

	@Override
	public void deleteTableData(String psTableName, String psParams) {
		StringBuilder _sbdSql = new StringBuilder();
		Object[] _argObj = new Object[psParams.split(";").length];
		try {
			_sbdSql.append("delete from ");
			_sbdSql.append(psTableName);
			_sbdSql.append(" where ");
			int i = 0;
			for (String str : psParams.split(";")) {
				_sbdSql.append(str.split(":")[0] + "=?,");
				_argObj[i] = str.split(":")[1];
			}
			if (_sbdSql.length() > 0) {
				_sbdSql.delete(_sbdSql.length() - 1, _sbdSql.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jdbcTemplate.update(_sbdSql.toString(), _argObj);
	}

	@Override
	public List findReplyResult(String belongTable, String columnName, String value, Long id) {
		String sql = "select * from " + belongTable + " where " + columnName + " = ?";
		Object[] objs = new Object[] { value };
		;
		if (id != null && !id.equals("")) {
			sql = "select * from " + belongTable + " where " + columnName + " = ? and id <> ?";
			objs = new Object[] { value, id };
		}

		List list = null;
		try {
			list = jdbcTemplate.queryForList(sql, objs);
		} catch (Exception e) {
			list = new ArrayList();
		}

		return list;
	}

	@Override
	public void setdefault(String mainTable, String column, Long psId) {
		String sql = "UPDATE " + mainTable + " SET " + column + "=? WHERE ID=?";
		Object[] _objParams = new Object[] { "1", psId };
		jdbcTemplate.update(sql, _objParams);

		sql = "UPDATE " + mainTable + " SET " + column + "=? WHERE ID<>?";
		_objParams = new Object[] { "0", psId };
		jdbcTemplate.update(sql, _objParams);
	}

	@Override
	public void passed(String tableName, Long recordId) {
		String _sql = "update " + tableName + " set tbl_passed = 1 where id = ?";
		Object[] _objParams = new Object[] { recordId };
		jdbcTemplate.update(_sql, _objParams);
	}

	@Override
	public void recall(String tableName, Long recordId) {
		String _sql = "update " + tableName + " set tbl_passed = 0 where id = ?";
		Object[] _objParams = new Object[] { recordId };
		jdbcTemplate.update(_sql, _objParams);
	}

	@Override
	public void updateCode(String tableName, String columnName, Long id, String code) {
		String _sql = "update " + tableName + " set " + columnName + " = ? where id = ?";
		Object[] _objParams = new Object[] { code, id };
		jdbcTemplate.update(_sql, _objParams);
	}

	@Override
	public void removeFile(String model, String column, Long fileid, Long recordid) {
		String _selsql = "select " + column + " from " + model + " where id = ?";
		Object[] _objParams = new Object[] { recordid };
		List list = jdbcTemplate.queryForList(_selsql, _objParams);
		if (list.size() > 0) {
			String value = ((Map) list.get(0)).get(column).toString();
			String[] ids = value.split(",");
			String columnValue = "";
			for (int i = 0; i < ids.length; i++) {
				if (!fileid.equals(ids[i])) {
					if (columnValue.equals("")) {
						columnValue = ids[i];
					} else {
						columnValue = columnValue + "," + ids[i];
					}
				}
			}
			String _usql = "update " + model + " set " + column + " = ? where id = ?";
			Object[] _updateParams = new Object[] { columnValue, recordid };
			jdbcTemplate.update(_usql, _updateParams);
		}
	}

	@Override
	public List findTreeTypeReply(String _tableName, String _name, Long _parentId, Long _domainId) {
		String _sql = "select * from " + _tableName + " where tbl_name = ? and tbl_parentId = ?";
		Object[] _objs = new Object[] { _name, _parentId };
		if (_domainId != null && !_domainId.equals("")) {
			_sql = "select * from " + _tableName + " where tbl_name = ? and tbl_parentId = ? and id <> ?";
			_objs = new Object[] { _name, _parentId, _domainId };
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
	public void updateMainInfo(String tableName, Long mainId, SysUser user) {
		String _sql = "update " + tableName + " set comm_updateBy = ?,comm_updateDate = ? where id = ?";
		Object[] _params = new Object[] { user.getId(), new Date(System.currentTimeMillis()), mainId };
		jdbcTemplate.update(_sql, _params);
	}

	@Override
	public void updateTableData(String tableName, String columnName, String value, Long id) {
		String sql = "update " + tableName + " set " + columnName + " = ? where id = ?";
		Object[] params = new Object[] { value, id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void updateTableData(String tableName, List<String> columnNames, List<String> values, Long id) {
		StringBuffer sql = new StringBuffer("update " + tableName + " set ");
		List params = new ArrayList();
		for (int i = 0; i < columnNames.size(); i++) {
			sql.append(columnNames.get(i) + " = ?,");
			params.add(values.get(i));
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where id = ?");
		params.add(id);
		jdbcTemplate.update(sql.toString(), params.toArray());
	}

	@Override
	public List<Domain> selectSingleDomain(QueryCriteria qc, Long tabId, DomainVO vo, SysUser user) {
		List<Domain> dms = new ArrayList<Domain>();
		List<FormColumn> listColumns = formDao.selectByTabId(tabId);

		String subTable = null;
		Long relColumnId = null;
		if (listColumns != null && listColumns.size() > 0) {
			FormColumn _fc = listColumns.get(0);
			subTable = _fc.getBelongTable();
			relColumnId = _fc.getRelColumnId();
		}

		if (vo.getTable() != null && vo.getDomainId() != null) {
			Column _column = columnDao.selectById(relColumnId);
			String sql = null;
			if (relColumnId != null && !relColumnId.equals("-1")) {
				sql = "select * from " + subTable + " where " + _column.getColumnName() + " = ?";
			} else {
				sql = "select a.* from " + subTable + " a," + StringUtil.getTableString(vo.getTable(), subTable, 14)
						+ " b where a.id=b.tbl_subid and b.tbl_mainid=? ";
			}

			Object[] param = new Object[] { vo.getDomainId() };
			List retList = null;
			if (qc.getPageSize() == -1) {// 查询出所有记录
				retList = this.jdbcTemplate.querySP(sql.toString(), param, 0, singleCount(subTable, vo, relColumnId));
			} else {
				retList = this.jdbcTemplate.querySP(sql.toString(), param, qc.getCurrentIndex(), qc.getPageSize());
			}
			for (int i = 0; retList != null && i < retList.size(); i++) {
				Domain u = new Domain();
				Table table = new Table();
				table.setTableName(subTable);
				u.setTable(table);

				// 设置column
				List listCeList = new ArrayList();
				Map retMap = (Map) retList.get(i);
				Set keySet = retMap.keySet();
				for (int j = 0; listColumns != null && j < listColumns.size(); j++) {
					FormColumn c = (FormColumn) (listColumns.get(j));
					if (keySet.contains(c.getColumnName())) {
						FormColumnExtend ce = new FormColumnExtend();
						ce.setFormColumn(c);
						ce.setValue(retMap.get(c.getColumnName()));
						// 下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_COMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
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
						// 搜索下拉框 代码
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_CODE) {
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
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
						}
						// 搜索下拉框 关系表
						if (c.getCompexId() != null && c.getInputType() == Constants.INPUT_TYPE_TEXTBOX_SEARCHCOMBOX
								&& c.getSelectDataType() == Constants.SELECT_DATA_TYPE_RELATION) {
							List<Code> codes = selectByRelation(c.getRelationTable(), c.getRelationColumn());
							ce.setCodes(codes);
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
						if (c.getInputType() == 7) {
							MgrTree mgrTree = mgrTreeDao.selectById(c.getCompexId());
							if (mgrTree.getTableType() == null) {
								mgrTree.setTableType("");
							}
							if (!mgrTree.getTableType().equals("1")) {
								CommonTree commonTree = mgrTreeDao.findCommonTree(mgrTree, Long.valueOf((String) ce.getValue()));
								ce.setValueText(commonTree.getName());
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
						listCeList.add(ce);
					}
				}
				u.setTabulationColumnExtends(listCeList);
				u.setId((Long) retMap.get("id"));
				dms.add(u);
			}
		}
		return dms;
	}

	@Override
	public List<Domain> selectDomains(QueryCriteria queryCriteria, String model, SysUser user) {
		Map<String, Object> map = queryCriteria.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select * from " + model + " t where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getValue() instanceof String) {
				sql.append(" and t." + entry.getKey() + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and t." + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}
		}
		sql.append(" order by t.comm_updateDate desc ");
		List<Map> maps = jdbcTemplate.querySP(sql.toString(), param.toArray(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());
		List<Domain> domains = new ArrayList<Domain>();
		for (int i = 0; maps != null && i < maps.size(); i++) {
			List ceList = new ArrayList();
			Map retMap = (Map) maps.get(i);
			Set keySet = retMap.keySet();
			Iterator it = keySet.iterator();

			Domain u = new Domain();
			// 设置table
			String tableSql = "select * from sys_tables where tbl_tableName=?";
			Object[] tableParams = new Object[] { model };
			List tableList = this.jdbcTemplate.query(tableSql, tableParams, new TableRowMapper());
			if (tableList != null && tableList.size() > 0) {
				u.setTable((Table) tableList.get(0));
			}
			// 设置列表元素和对应值
			while (it.hasNext()) {
				FormColumnExtend ce = new FormColumnExtend();
				FormColumn c = new FormColumn();
				c.setColumnName((String) it.next());
				ce.setFormColumn(c);
				ce.setValue(retMap.get(c.getColumnName()));
				ceList.add(ce);
			}
			u.setTabulationColumnExtends(ceList);
			domains.add(u);
		}
		return domains;
	}

	@Override
	public void logicDelete(String domain, Long id, Long formId, SysUser user) {
		String name = "";
		String selSql = "select * from " + domain + " where id = ?";

		String sql = "update " + domain + " set comm_mark_for_delete = 1 where id = ?";
		Object[] params = new Object[] { id };

		// 查找名称字段
		List<Map<String, Object>> list = jdbcTemplate.queryForList(selSql, params);
		if (list.size() > 0) {
			Map<String, Object> record = list.get(0);
			Set<Entry<String, Object>> entry = record.entrySet();
			Iterator<Entry<String, Object>> it = entry.iterator();
			while (it.hasNext()) {
				Entry<String, Object> et = it.next();
				if (et.getKey().contains("_name")) {
					name = String.valueOf(et.getValue());
				}
			}
		}
		// 执行删除操作
		jdbcTemplate.update(sql, params);

		// 删除数据时记录系统日志
//		SysLog sysLog = new SysLog();
//		sysLog.setOperator(user.getId());
//		sysLog.setIp(user.getIp());
//		sysLog.setName(name);
//		sysLog.setOperationModule(catalogDao.findCatalogByListId(tabulationDao.selectTabulationByFormId(formId).getId()).getId());
//		sysLog.setOperationContent("6");
//		sysLog.setOperationTime(new Date(System.currentTimeMillis()));
//		sysLog.setSql(SqlUtil.getRealSql(sql, params));
		// sysLogDao.doSaveLog(sysLog);

	}

	@Override
	@Cacheable(value = "domainCache")
	public int count(String sql, Object[] array) {
		List counts = null;
		try {
			counts = jdbcTemplate.query(sql, array, new CountRowMapper());
			return (Integer) (counts.get(0));
		} catch (Exception e) {
			return 0;
		}
		
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public boolean hasDefaultCol(String model) {
		String sql = "select * from SYS_COLUMNS c left join SYS_TABLES t on c.TBL_TABLEID = t.id where c.TBL_COLUMNNAME = 'tbl_isdefault' and t.TBL_TABLENAME = ?";
		Object[] params = new Object[] { model };
		List list = jdbcTemplate.queryForList(sql, params);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isDefaultData(String model, String column, Long domainId) {
		String sql = "select " + column + " from " + model + " where id = ?";
		Object[] params = new Object[] { domainId };
		String defaultValue = jdbcTemplate.queryForObject(sql, params, String.class);
		if (defaultValue.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void saveFileter(String filterName, String tableName, String queryCriteria) {
		String sql = "insert into bus_filter (id,tbl_filtername,tbl_tableName,tbl_querycriteria) values(?,?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), filterName, tableName, queryCriteria };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable(value = "domainCache")
	public List selectData(String sql, Object[] array) {
		return jdbcTemplate.query(sql, array, new ColumnMapRowMapper());
	}

	@Override
	@Cacheable(value = "domainCache")
	public List selectData(String sql, Object[] array, int currentIndex, int pageSize) {
		return jdbcTemplate.querySP(sql, array, currentIndex, pageSize);
	}

	@Override
	public Object queryColumnValue(String model, String column, Long domainId) {
		String sql = "select * from " + model + " where id = ?";
		Object[] params = new Object[] { domainId };
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, params);
		if (list != null && list.size()>0) {
			Map<String,Object> map = list.get(0);
			return map.get(column);
		} else {
			return null;
		}
	}
	
	

}
