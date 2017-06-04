package com.cloudstong.platform.resource.metadata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.dao.RelationDao;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Relation;

@Repository("relationDao")
public class RelationDaoImpl implements RelationDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Resource
	private ColumnDao columnDao;

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Relation selectByMainIdAndSubId(Long mainId, Long subId) {
		Relation relation = null;
		try {
			String sql = "select * from sys_relation r where r.tbl_mainid = ? and r.tbl_subid = ?";
			relation = new Relation();
			final Object[] params = new Object[] { mainId, subId };
			queryRelation(sql, params, relation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relation;
	}

	public Relation selectByMainNameAndSubName(String mainName, String subName) {
		Relation relation = null;
		try {
			String sql = "select * from sys_relation r where r.tbl_mainname = ? and r.tbl_subname = ?";
			relation = new Relation();
			final Object[] params = new Object[] { mainName, subName };
			queryRelation(sql, params, relation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relation;
	}

	private void queryRelation(String sql, Object[] params, final Relation r) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				r.setId(rs.getLong("id"));
				r.setMainTableId(rs.getString("tbl_mainid"));
				r.setMainTableName(rs.getString("tbl_mainname"));
				r.setSubTableId(rs.getString("tbl_subid"));
				r.setSubTableName(rs.getString("tbl_subname"));
				r.setRelationType(rs.getInt("tbl_relationtype"));
			}
		});
	}

	@Override
	public List<Relation> select(String sql, Object[] args, int startRow, int rowsCount) {
		List list = new ArrayList();
		try {
			if (rowsCount == -1) {
				list = this.jdbcTemplate.query(sql, args, new RelationRowMapper());
			} else {
				list = this.jdbcTemplate.querySP(sql, args, startRow, rowsCount, new RelationRowMapper());
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return list;
	}

	class RelationRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Relation relation = new Relation();
			relation.setId(rs.getLong("id"));
			relation.setMainTableId(rs.getString("tbl_mainid"));
			relation.setMainTableName(rs.getString("tbl_mainname"));
			relation.setSubTableId(rs.getString("tbl_subid"));
			relation.setSubTableName(rs.getString("tbl_subname"));
			relation.setRelationType(rs.getInt("tbl_relationtype"));
			relation.setMainTableCNName(rs.getString("tbl_mainTableName"));
			relation.setSubTableCNName(rs.getString("tbl_subTableName"));
			return relation;
		}
	}

	@Override
	public int count() {
		String sql = "select count(*) from sys_relation";
		return jdbcTemplate.queryForInt(sql);
	}

	@Override
	public Long doSave(Relation relation) {
		String sql = "insert into sys_relation (id,tbl_systemteam,tbl_object,tbl_mainid,tbl_mainname,tbl_subid,tbl_subname,tbl_relationtype,tbl_comment,tbl_remark,tbl_connecttype,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Long id = UniqueIdUtil.genId();
		String mainTable = relation.getMainTableName();
		String subTable = relation.getSubTableName();
		String middleTable = StringUtil.getTableString(mainTable, subTable, 14);
		Object[] params = new Object[] { id, relation.getSystemteam(), middleTable, relation.getMainTableId(), relation.getMainTableName(),
				relation.getSubTableId(), relation.getSubTableName(), relation.getRelationType(), relation.getComment(), relation.getRemark(),
				relation.getConnecttype(), relation.getCreateBy(), relation.getCreateDate(), relation.getUpdateBy(), relation.getUpdateDate() };
		this.jdbcTemplate.update(sql, params);
		

		String createsql = "create table " + middleTable
				+ "(id varchar(50) NOT NULL,tbl_mainid varchar(50),tbl_subid varchar(50),PRIMARY KEY (`id`))";
		this.jdbcTemplate.execute(createsql);

		return id;
	}

	@Override
	public void doUpdate(Relation relation) {
		Relation r = this.selectById(relation.getId());
		String mainTable = r.getMainTableName();
		String subTable = r.getSubTableName();

		String oldConnectType = r.getConnecttype();

		// 判断原来的表关系，并删除原来的关系数据
		if (oldConnectType.equals("2")) { // 如果原来的关系是：关系表，那么就先删掉该关系表
			String delsql = "drop table " + StringUtil.getTableString(mainTable, subTable, 14);
			this.jdbcTemplate.execute(delsql);

		} else { // 如果原来采用外键关联，则需要把原来的外键删掉，同时把sys_columns表中对应的记录删掉
			int oldRelationType = r.getRelationType();

			// 判断原来的关系类型，以便于确定外键字段在哪张表中
			if (oldRelationType == 2) { // 多对一，那么外键在MainTable中
				String tmp = subTable;
				subTable = mainTable; // 主次表颠倒
				mainTable = tmp;
			}
			String oldForeignKey = StringUtil.getForeignKeyString(mainTable, subTable);

			String dropColumnSql = "ALTER TABLE `" + subTable + "` DROP COLUMN `" + oldForeignKey + "` ";
			jdbcTemplate.update(dropColumnSql);// 删除原来的外键字段
			/**
			 * 接下来把sys_columns表中对应的字段也删除
			 */
			String delColumnSql = "delete from  sys_columns where tbl_columnName = ? and tbl_belongTable = ?";
			jdbcTemplate.update(delColumnSql, oldForeignKey, subTable);

		}

		/**
		 * 接下来创建新的表关系
		 */

		String sql = "update sys_relation set tbl_systemteam = ?,tbl_object=?,tbl_mainid = ?,tbl_mainname = ?,tbl_subid = ?,tbl_subname = ?,tbl_relationtype = ?,tbl_connecttype=?,tbl_comment = ?,tbl_remark = ?,comm_updateBy=?,comm_updateDate=? where id = ?";
		String middleTable = StringUtil.getTableString(relation.getMainTableName(), relation.getSubTableName(), 14);
		Object[] params = new Object[] { relation.getSystemteam(), middleTable, relation.getMainTableId(),
		relation.getMainTableName(), relation.getSubTableId(), relation.getSubTableName(), relation.getRelationType(), relation.getConnecttype(),
				relation.getComment(), relation.getRemark(), relation.getUpdateBy(), relation.getUpdateDate(), relation.getId() };
		this.jdbcTemplate.update(sql, params);

		String createsql = "create table " + middleTable
				+ "(id varchar(50) NOT NULL ,tbl_mainid varchar(50),tbl_subid varchar(50),PRIMARY KEY (`id`))";
		this.jdbcTemplate.execute(createsql);
	}

	@Override
	public void delete(Long id) {
		Relation r = this.selectById(id);
		String mainTable = r.getMainTableName();
		String subTable = r.getSubTableName();
		String delSql = null;

		if ("2".equals(r.getConnecttype())) { // 删除关系表
			delSql = "drop table " + StringUtil.getTableString(mainTable, subTable, 14);
		} else { // 删除外键
			if (2 == r.getRelationType()) { // 多对一
				String tmp = subTable;
				subTable = mainTable;// 主次表颠倒
				mainTable = tmp;
			}
			String fk = StringUtil.getForeignKeyString(mainTable, subTable);
			delSql = "ALTER TABLE `" + subTable + "` DROP COLUMN `" + fk + "`";
			// 删除sys_columns表中的记录
			jdbcTemplate.update("delete from sys_columns where tbl_columnName=? and tbl_belongTable = ? ", fk, subTable);
		}
		this.jdbcTemplate.execute(delSql);

		String sql = "delete from sys_relation where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Relation selectById(Long id) {
		String sql = "select r.*,t1.tbl_tableZhName as tbl_mainTableName,t2.tbl_tableZhName as tbl_subTableName from sys_relation r left join sys_tables t1 on r.tbl_mainid = t1.id left join sys_tables t2 on r.tbl_subid = t2.id where r.id = ?";
		final Relation relation = new Relation();
		final Object[] obj = new Object[] { id };
		try {
			jdbcTemplate.query(sql, obj, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					relation.setId(rs.getLong("id"));
					relation.setMainTableId(rs.getString("tbl_mainid"));
					relation.setMainTableName(rs.getString("tbl_mainname"));
					relation.setSubTableId(rs.getString("tbl_subid"));
					relation.setSubTableName(rs.getString("tbl_subname"));
					relation.setRelationType(rs.getInt("tbl_relationtype"));
					relation.setMainTableCNName(rs.getString("tbl_mainTableName"));
					relation.setSubTableCNName(rs.getString("tbl_subTableName"));
					relation.setConnecttype(rs.getString("tbl_connecttype"));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relation;
	}

	@Override
	public List findReplyRelation(Relation relation) {
		String sql = "select * from sys_relation where tbl_mainid = ? and tbl_subid = ?";
		Object[] objs = new Object[] { relation.getMainTableId(), relation.getSubTableId() };
		if (relation.getId() != null && !relation.getId().equals("")) {
			sql = "select * from sys_relation where tbl_mainid = ? and tbl_subid = ? and id <> ?";
			objs = new Object[] { relation.getMainTableId(), relation.getSubTableId(), relation.getId() };
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
	public void logicDelete(Long id) {
		Relation r = this.selectById(id);
		String mainTable = r.getMainTableName();
		String subTable = r.getSubTableName();

		// String delsql = "drop table " +StringUtil.getTableString(mainTable,
		// subTable, 14);
		// this.jdbcTemplate.execute(delsql);

		String sql = "update sys_relation set tbl_dstatus = 1 where id=?";
		Object[] params = new Object[] { id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	public boolean findForeignKey(String subTableName, String foreignKey) {
		String sql = "select count(1) from sys_columns s where s.tbl_columnName = ? and s.tbl_belongTable=?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, foreignKey, subTableName);
		return count > 0;
	}

	/*
	 * @see
	 * com.cloudstongplatform.resource.metadata.dao.RelationDao#createForeignKey
	 * (com.cloudstongplatform.resource.metadata.model.Relation)
	 */
	@Override
	public Long createForeignKey(Relation relation) {
		String sql = "insert into sys_relation (id,tbl_systemteam,tbl_object,tbl_mainid,tbl_mainname,tbl_subid,tbl_subname,tbl_relationtype,tbl_comment,tbl_remark,tbl_connecttype,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Long id = UniqueIdUtil.genId();
		String mainTable = relation.getMainTableName();
		String subTable = relation.getSubTableName();
		String foreignKey = StringUtil.getForeignKeyString(mainTable, subTable);
		Object[] params = new Object[] { id, relation.getSystemteam(), foreignKey, relation.getMainTableId(), relation.getMainTableName(),
				relation.getSubTableId(), relation.getSubTableName(), relation.getRelationType(), relation.getComment(), relation.getRemark(),
				relation.getConnecttype(), relation.getCreateBy(), relation.getCreateDate(), relation.getUpdateBy(), relation.getUpdateDate() };
		this.jdbcTemplate.update(sql, params);
		

		if (2 == relation.getRelationType()) { // 多对一
			String tmp = subTable;
			subTable = mainTable;// 主次表颠倒
			mainTable = tmp;
		}

		

		// id,tbl_columnName,tbl_columnZhName,tbl_tableId,tbl_belongTable,tbl_dataType,tbl_defaultValue,tbl_length,
		// tbl_isPrimaryKey,tbl_isNullable,tbl_isPublish,tbl_comment,
		// comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,comm_status,tbl_remark

		Long subTableId = jdbcTemplate.queryForObject("select id from sys_tables where tbl_tableName = ?", Long.class, subTable);

		Column c = new Column();
		c.setColumnName(foreignKey);
		c.setColumnZhName("外键");
		c.setTableId(subTableId);
		c.setBelongTable(subTable);
		c.setDataType("bigint");
		c.setLength(50);
		c.setIsPrimaryKey(0);
		c.setIsNullable(0);
		c.setIsPublish(1);
		c.setCreateBy(2052751705L);// 创建人
		c.setUpdateBy(2052751705L);
		c.setComment("外键：主表-->" + mainTable + ",子表--->" + subTable);
		columnDao.insert(c, true);
		return id;
	}

	/*
	 * @see
	 * com.cloudstongplatform.resource.metadata.dao.RelationDao#updateForeignKey
	 * (com.cloudstongplatform.resource.metadata.model.Relation)
	 */
	@Override
	public void updateForeignKey(Relation relation) {
		Relation oldRelation = selectById(relation.getId());
		// 首先要获取原来的关联方式：外键还是关系表
		String oldConnectType = oldRelation.getConnecttype();

		String oldMainTable = oldRelation.getMainTableName();
		String oldSubTable = oldRelation.getSubTableName();

		// 判断原来的表关系，并删除原来的关系数据
		if (oldConnectType.equals("2")) { // 如果原来的关系是：关系表，那么就先删掉该关系表
			String delsql = "drop table " + StringUtil.getTableString(oldMainTable, oldSubTable, 14);
			this.jdbcTemplate.execute(delsql);

		} else { // 如果原来采用外键关联，则需要把原来的外键删掉，同时把sys_columns表中对应的记录删掉
			// 获取原来的关系类型：一对一还是多对一等
			Integer oldRelationType = oldRelation.getRelationType();

			// 判断原来的关系类型，以便于确定外键字段在哪张表中
			if (oldRelationType == 2) { // 多对一，那么外键在MainTable中
				String tmp = oldSubTable;
				oldSubTable = oldMainTable; // 主次表颠倒
				oldMainTable = tmp;
			}
			String oldForeignKey = StringUtil.getForeignKeyString(oldMainTable, oldSubTable);

			String dropColumnSql = "ALTER TABLE `" + oldSubTable + "` DROP COLUMN `" + oldForeignKey + "` ";
			jdbcTemplate.update(dropColumnSql);// 删除原来的外键字段
			/**
			 * 接下来把sys_columns表中对应的字段也删除
			 */
			String delColumnSql = "delete from  sys_columns where tbl_columnName = ? and tbl_belongTable = ?";
			jdbcTemplate.update(delColumnSql, oldForeignKey, oldSubTable);
		}

		/**
		 * 接下来创建新的表关系
		 */

		String mainTable = relation.getMainTableName();
		String subTable = relation.getSubTableName();

		if (2 == relation.getRelationType()) { // 多对一
			String tmp = subTable;
			subTable = mainTable; // 主次表颠倒
			mainTable = tmp;
		}

		String foreignKey = StringUtil.getForeignKeyString(mainTable, subTable);

		Long subTableId = jdbcTemplate.queryForObject("select id from sys_tables where tbl_tableName = ?", Long.class, subTable);

		Column c = new Column();
		c.setColumnName(foreignKey);
		c.setColumnZhName("外键");
		c.setTableId(subTableId);
		c.setBelongTable(subTable);
		c.setDataType("bigint");
		c.setLength(50);
		c.setIsPrimaryKey(0);
		c.setIsNullable(0);
		c.setIsPublish(1);
		c.setCreateBy(2052751705L);// 创建人
		c.setUpdateBy(2052751705L);
		c.setComment("外键：主表-->" + mainTable + ",子表--->" + subTable);
		columnDao.insert(c, true);

		// 更新sys_relation表
		String sql = "update sys_relation set tbl_systemteam = ?,tbl_object=?,tbl_mainid = ?,tbl_mainname = ?,tbl_subid = ?,tbl_subname = ?,tbl_relationtype = ?,tbl_connecttype=?,tbl_comment = ?,tbl_remark = ?,comm_updateBy=?,comm_updateDate=? where id = ?";
		Object[] params = new Object[] { relation.getSystemteam(),foreignKey, relation.getMainTableId(),

		relation.getMainTableName(), relation.getSubTableId(), relation.getSubTableName(), relation.getRelationType(), relation.getConnecttype(),
				relation.getComment(), relation.getRemark(), relation.getUpdateBy(), relation.getUpdateDate(), relation.getId() };
		this.jdbcTemplate.update(sql, params);
	}
}
