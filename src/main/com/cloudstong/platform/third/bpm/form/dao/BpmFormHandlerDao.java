package com.cloudstong.platform.third.bpm.form.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.dialect.Dialect;
import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.TimeUtil;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.third.bpm.dao.BpmSubtableRightsDao;
import com.cloudstong.platform.third.bpm.form.model.BpmFormData;
import com.cloudstong.platform.third.bpm.form.model.BpmFormField;
import com.cloudstong.platform.third.bpm.form.model.BpmFormTable;
import com.cloudstong.platform.third.bpm.form.model.PkValue;
import com.cloudstong.platform.third.bpm.form.model.SqlModel;
import com.cloudstong.platform.third.bpm.form.model.SubTable;
import com.cloudstong.platform.third.bpm.form.service.FormDataUtil;
import com.cloudstong.platform.third.bpm.model.BpmSubtableRights;

@Repository
public class BpmFormHandlerDao
{
  private Log logger = LogFactory.getLog(BpmFormHandlerDao.class);

  @Resource
  private BpmFormTableDao bpmFormTableDao;

  @Resource
  private BpmFormFieldDao bpmFormFieldDao;

  @Resource
  private BpmSubtableRightsDao bpmSubtableRightsDao;

  @Resource
  private Dialect dialect;

  public void handFormData(BpmFormData bpmFormData, String actDefId, String nodeId) throws Exception { JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
    List<SqlModel> list = FormDataUtil.parseSql(bpmFormData, actDefId, nodeId);
    for (SqlModel sqlModel : list) {
      String sql = sqlModel.getSql();
      if (!StringUtil.isEmpty(sql)) {
        Object[] obs = sqlModel.getValues();
        jdbcTemplate.update(sql, obs);
      }
    }
  }

  public boolean getHasData(String tableName)
  {
    JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
    int rtn = jdbcTemplate.queryForInt("select count(*) from " + tableName);
    return rtn > 0;
  }

  public BpmFormData getByKey(long tableId, String pkValue)
    throws Exception
  {
    return getByKey(tableId, pkValue, null, null);
  }

  private String getLimitSql(Long tableId, String actDefId, String nodeId)
  {
    if ((StringUtil.isEmpty(actDefId)) || (StringUtil.isEmpty(nodeId))) return "";
    String limitSql = "";

    BpmSubtableRights rule = bpmSubtableRightsDao.getByDefIdAndNodeId(actDefId, nodeId, tableId);
    if (rule == null) return "";

    switch (rule.getPermissiontype().intValue()) {
    case 0:
      Long userId = AppContext.getCurrentUserId();
      limitSql = " and curentUserId_ = " + userId;
      break;
    case 1:
      SysOrg org = new SysOrg();//ContextUtil.getCurrentOrg();
      if (org == null)
        limitSql = " and 1 = 2";
      else {
        limitSql = " and curentOrgId_ = " + org.getId();
      }
      break;
    case 2:
      GroovyScriptEngine scriptEngine = (GroovyScriptEngine)AppUtil.getBean(GroovyScriptEngine.class);
      limitSql = scriptEngine.executeString(rule.getPermissionseting(), new HashMap());
      break;
    }

    return limitSql;
  }

  public BpmFormData getByKey(long tableId, String pkValue, String actDefId, String nodeId)
    throws Exception
  {
    BpmFormTable mainFormTableDef = (BpmFormTable)bpmFormTableDao.getById(Long.valueOf(tableId));

    List<BpmFormTable> formTableList = bpmFormTableDao.getSubTableByMainTableId(Long.valueOf(tableId));

    JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
    String tableName = mainFormTableDef.getTableName();
    String pkField = "ID";
    int isExternal = mainFormTableDef.getIsExternal();
    if (isExternal == 1)
      pkField = mainFormTableDef.getPkField();
    else {
      tableName = "W_" + tableName;
    }
    PkValue pk = new PkValue(pkField, pkValue);

    Map mainData = getByKey(tableName, pk, Long.valueOf(tableId));

    BpmFormData bpmFormData = new BpmFormData();
    for (BpmFormTable table : formTableList) {
      SubTable subTable = new SubTable();

      String fk = "REFID";
      String subPk = "ID";

      List list = getByFk(table, pk.getValue().toString(), actDefId, nodeId);

      subTable.setTableName(table.getTableName().toLowerCase());
      subTable.setDataList(list);
      subTable.setFkName(fk);
      subTable.setPkName(subPk);
      bpmFormData.addSubTable(subTable);
    }

    bpmFormData.setTableId(tableId);
    bpmFormData.setTableName(tableName);
    bpmFormData.setPkValue(pk);
    bpmFormData.addMainFields(mainData);

    return bpmFormData;
  }

  public Map<String, Object> getByKey(String tableName, PkValue pk, Long tableId)
  {
    JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
    String sql = "select a.* from " + tableName + " a where " + pk.getName() + "=" + pk.getValue();
    List fieldList = bpmFormFieldDao.getByTableId(tableId);
    Map fieldMap = convertToMap(fieldList);
    Map map = null;
    try {
      map = jdbcTemplate.queryForMap(sql);
      map = handMap(fieldMap, map);
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      map = new HashMap();
    }
    return map;
  }

  public Map<String, Object> getByKey(String tableName, String pk) {
    JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
    String sql = "select a.* from " + tableName + " a where id=" + pk;

    Map map = null;
    try {
      map = jdbcTemplate.queryForMap(sql);
    } catch (Exception ex) {
      map = new HashMap();
    }
    return map;
  }

  private Map<String, BpmFormField> convertToMap(List<BpmFormField> fieldList)
  {
    Map map = new HashMap();
    for (BpmFormField field : fieldList) {
      String fieldName = field.getFieldName().toLowerCase();
      map.put(fieldName, field);
    }
    return map;
  }

  public List<Map<String, Object>> getByFk(BpmFormTable table, String fkValue, Long tableId)
  {
    return getByFk(table, fkValue, null, null);
  }

  public List<Map<String, Object>> getByFk(BpmFormTable table, String fkValue, String actDefId, String nodeId)
  {
    String subTableName = table.getTableName();
    Long tableId = table.getTableId();

    subTableName = "W_" + subTableName;

    JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
    String fk = "REFID";
    String sql = "select a.* from " + subTableName + " a where " + fk + "=" + fkValue;

    String limitSql = getLimitSql(tableId, actDefId, nodeId);

    if (!StringUtil.isEmpty(limitSql)) {
      sql = sql + " " + limitSql;
    }

    List fieldList = bpmFormFieldDao.getByTableId(tableId);
    Map fieldMap = convertToMap(fieldList);

    List<Map<String,Object>> list = (List<Map<String,Object>>)jdbcTemplate.queryForList(sql);

    List rtnList = new ArrayList();

    for (Map map : list) {
      Map obj = handMap(fieldMap, map);
      rtnList.add(obj);
    }
    return rtnList;
  }

  private Map<String, Object> handMap(Map<String, BpmFormField> fieldMap, Map<String, Object> map)
  {
    Map rtnMap = new HashMap();
    for (Map.Entry entry : map.entrySet()) {
      String fieldName = ((String)entry.getKey()).toLowerCase();
      if (!"createtime".equalsIgnoreCase(fieldName))
      {
        String key = fieldName;
        if (fieldName.indexOf("F_".toLowerCase()) == 0) {
          key = fieldName.replaceFirst("F_".toLowerCase(), "");
        }
        Object obj = entry.getValue();
        if (obj == null) {
          rtnMap.put(key, "");
        }
        else if ((obj instanceof Date)) {
          BpmFormField bpmFormField = (BpmFormField)fieldMap.get(key);
          String format = (String)bpmFormField.getPropertyMap().get("format");
          if (StringUtil.isEmpty(format)) {
            format = "yyyy-MM-dd";
          }
          String str = TimeUtil.getDateTimeString((Date)obj, format);
          rtnMap.put(key, str);
        } else {
          BpmFormField bpmFormField = (BpmFormField)fieldMap.get(key);

          if ((BeanUtils.isNotEmpty(bpmFormField)) && (BeanUtils.isNotEmpty(bpmFormField.getControlType())) && (bpmFormField.getControlType().shortValue() == 9)) {
            obj = ((String)obj).replace("\"", "￥@@￥");
          }
          rtnMap.put(key, obj);
        }
      }
    }
    return rtnMap;
  }

  public List<Map<String, Object>> getAll(Long tableId, Map<String, Object> param)
    throws Exception
  {
    BpmFormTable table = (BpmFormTable)bpmFormTableDao.getById(tableId);
    JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");

    StringBuffer where = new StringBuffer();
    List values = new ArrayList();
    for (Map.Entry entry : param.entrySet()) {
      where.append((String)entry.getKey()).append(" LIKE ? AND ");
      values.add(entry.getValue());
    }

    String tableName = table.getTableName();
    if (table.getIsExternal() != 1) {
      tableName = "W_" + tableName;
    }

    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT a.* FROM ");
    sql.append(tableName + " a");
    if (where.length() > 0) {
      sql.append(" WHERE ");
      sql.append(where.substring(0, where.length() - 5));
    }

    RowMapper rowMapper = new RowMapper()
    {
      public Map<String, Object> mapRow(ResultSet rs, int num) throws SQLException
      {
        Map map = new HashMap();
        ResultSetMetaData rsm = rs.getMetaData();
        for (int i = 1; i <= rsm.getColumnCount(); i++) {
          String name = rsm.getColumnName(i);
          Object value = rs.getObject(name);
          map.put(name, value);
        }
        return map;
      }
    };
    return jdbcTemplate.query(sql.toString(), values.toArray(), rowMapper);
  }
}