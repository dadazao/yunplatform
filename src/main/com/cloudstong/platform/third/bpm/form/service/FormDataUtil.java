package com.cloudstong.platform.third.bpm.form.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.DateFormatUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.third.bpm.form.dao.BpmFormFieldDao;
import com.cloudstong.platform.third.bpm.form.dao.BpmFormHandlerDao;
import com.cloudstong.platform.third.bpm.form.dao.BpmFormTableDao;
import com.cloudstong.platform.third.bpm.form.model.BpmFormData;
import com.cloudstong.platform.third.bpm.form.model.BpmFormField;
import com.cloudstong.platform.third.bpm.form.model.BpmFormTable;
import com.cloudstong.platform.third.bpm.form.model.PkValue;
import com.cloudstong.platform.third.bpm.form.model.SqlModel;
import com.cloudstong.platform.third.bpm.form.model.SubTable;
import com.cloudstong.platform.third.bpm.form.model.TableRelation;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;

public class FormDataUtil {
	private static Log logger = LogFactory.getLog(FormDataUtil.class);

	public static List<SqlModel> parseSql(BpmFormData formData, String actDefId, String nodeId) {
		PkValue pkValue = formData.getPkValue();
		boolean isAdd = pkValue.getIsAdd();
		List list = new ArrayList();
		String key = "ID".toLowerCase();
		Iterator localIterator1;
		if (isAdd) {
			SqlModel sqlModel = getInsert(formData.getTableName(), formData.getMainFields());
			list.add(sqlModel);
			for (localIterator1 = formData.getSubTableList().iterator(); localIterator1.hasNext();) {
				SubTable subTable = (SubTable) localIterator1.next();
				String tableName = subTable.getTableName();
				List dataList = subTable.getDataList();
				for (Iterator localIterator2 = dataList.iterator(); localIterator2.hasNext();) {
					Map row = (Map) localIterator2.next();

					row.put(key, Long.valueOf(UniqueIdUtil.genId()));

					SqlModel subSqlModel = getInsert(tableName, row);
					list.add(subSqlModel);
				}
			}

		} else {
			SqlModel sqlModel = getUpdate(formData);
			if (sqlModel != null) {
				list.add(sqlModel);
			}

			for (SubTable subTable : formData.getSubTableList()) {
				String tableName = subTable.getTableName();
				BpmFormHandlerDao bpmFormHandlerDao = (BpmFormHandlerDao) AppUtil.getBean(BpmFormHandlerDao.class);
				BpmFormTableDao bpmFormTableDao = (BpmFormTableDao) AppUtil.getBean(BpmFormTableDao.class);
				String name = tableName.replaceFirst("W_", "");
				BpmFormTable table = bpmFormTableDao.getByTableName(name);

				List subDataList = bpmFormHandlerDao.getByFk(table, pkValue.getValue().toString(), actDefId, nodeId);
				List curDataList = subTable.getDataList();

				List updDelList = getUpdDelByList(tableName, curDataList, subDataList);

				List insertList = getInsertByList(tableName, curDataList);

				list.addAll(insertList);
				list.addAll(updDelList);
			}

		}

		return list;
	}

	private static List<SqlModel> getInsertByList(String tableName, List<Map<String, Object>> list) {
		List rtnList = new ArrayList();
		String key = "ID".toLowerCase();
		for (Map map : list) {
			if (map.containsKey(key)) {
				String value = map.get(key).toString();

				if ("0".equals(value)) {
					Map tmp = map;
					tmp.put(key, Long.valueOf(UniqueIdUtil.genId()));

					SqlModel model = getInsert(tableName, tmp);
					rtnList.add(model);
				}
			}
		}
		return rtnList;
	}

	private static List<SqlModel> getUpdDelByList(String tableName, List<Map<String, Object>> curList, List<Map<String, Object>> originList) {
		List rtnList = new ArrayList();
		Map curMap = convertMap(curList);
		Map originMap = convertMap(originList);
		Set curSet = curMap.entrySet();

		for (Iterator it = curSet.iterator(); it.hasNext();) {
			Map.Entry ent = (Map.Entry) it.next();

			if (originMap.containsKey(ent.getKey())) {
				SqlModel updSqlModel = getUpd(tableName, (Map) ent.getValue());
				if (updSqlModel != null) {
					rtnList.add(updSqlModel);
				}
			}
		}

		Set originSet = originMap.entrySet();
		for (Iterator it = originSet.iterator(); it.hasNext();) {
			Map.Entry ent = (Map.Entry) it.next();

			if (!curMap.containsKey(ent.getKey())) {
				String delSql = "delete from " + tableName + " where id=?";
				SqlModel sqlModel = new SqlModel(delSql, new Object[] { ent.getKey() });
				rtnList.add(sqlModel);
			}
		}
		return rtnList;
	}

	private static Map<String, Map<String, Object>> convertMap(List<Map<String, Object>> list) {
		String key = "ID".toLowerCase();
		Map rtnMap = new HashMap();
		for (Map map : list)
			if (map.containsKey(key)) {
				String value = map.get(key).toString();
				if (!"0".equals(value))
					rtnMap.put(value, map);
			}
		return rtnMap;
	}

	public static BpmFormData parseJson(String json, PkValue pkValue) throws Exception {
		BpmFormTableDao bpmFormTableDao = (BpmFormTableDao) AppUtil.getBean("bpmFormTableDao");
		BpmFormFieldDao bmpFormFieldDao = (BpmFormFieldDao) AppUtil.getBean("bpmFormFieldDao");

		JSONObject jsonObj = JSONObject.fromObject(json);

		JSONObject mainTable = jsonObj.getJSONObject("main");

		BpmFormData bpmFormData = new BpmFormData();

		long tableId = mainTable.getLong("tableId");

		BpmFormTable mainTableDef = (BpmFormTable) bpmFormTableDao.getById(Long.valueOf(tableId));

		List listTable = bpmFormTableDao.getSubTableByMainTableId(Long.valueOf(tableId));

		if (pkValue == null) {
			pkValue = getPk(mainTableDef);
		}
		bpmFormData.setPkValue(pkValue);

		handleMain(jsonObj, bpmFormData, mainTableDef, bmpFormFieldDao);

		handSub(jsonObj, listTable, bmpFormFieldDao, mainTableDef, bpmFormData);

		handOpinion(bpmFormData, jsonObj);

		return bpmFormData;
	}

	public static BpmFormData parseJson(String json) throws Exception {
		return parseJson(json, null);
	}

	private static void handleMain(JSONObject jsonObj, BpmFormData bpmFormData, BpmFormTable mainTableDef, BpmFormFieldDao bmpFormFieldDao)
			throws Exception {
		JSONObject mainTable = jsonObj.getJSONObject("main");

		long tableId = mainTableDef.getTableId().longValue();

		List mainFields = bmpFormFieldDao.getByTableIdContainHidden(Long.valueOf(tableId));

		Map mainFieldTypeMap = convertFieldToMap(mainFields);

		int isExternal = mainTableDef.getIsExternal();

		String tablePrefix = isExternal == 1 ? "" : "W_";

		String colPrefix = isExternal == 1 ? "" : "F_";

		String mainTableName = mainTableDef.getTableName();

		JSONObject mainFieldJson = mainTable.getJSONObject("fields");

		bpmFormData.setTableId(tableId);
		bpmFormData.setTableName(tablePrefix + mainTableName);

		Map mainFiledsData = handleRow(colPrefix, mainFieldTypeMap, mainFieldJson);

		bpmFormData.addMainFields(mainFiledsData);

		PkValue pkValue = bpmFormData.getPkValue();

		bpmFormData.addMainFields(pkValue.getName(), pkValue.getValue());

		if (pkValue.getIsAdd()) {
			List mapFormField = getFieldsFromScript(mainFields);

			Map map = caculateField(colPrefix, mapFormField, bpmFormData.getMainFields());

			bpmFormData.addMainFields(map);
		}

		Map variables = getVariables(mainFieldJson, mainFields);
		bpmFormData.setVariables(variables);

		List formUsers = getFormUsers(colPrefix, mainFields, bpmFormData.getMainFields());
		TaskUserAssignService.setFormUsers(formUsers);
	}

	private static Map<String, Object> getVariables(JSONObject jsonObject, List<BpmFormField> list) {
		Map map = new HashMap();
		Map fieldsMap = convertFieldToMap(list);
		for (Iterator it = jsonObject.keys(); it.hasNext();) {
			String key = (String) it.next();
			String lowerKey = key.toLowerCase();
			BpmFormField field = (BpmFormField) fieldsMap.get(lowerKey);
			if ((field != null) && (field.getIsFlowVar().shortValue() == 1)) {
				String value = (String) jsonObject.get(key);
				Object obj;
				try {
					obj = convertType(value, field);
					map.put(key, obj);
				} catch (ParseException e) {
				}
			}
		}
		return map;
	}

	private static void handSub(JSONObject jsonObj, List<BpmFormTable> listTable, BpmFormFieldDao bmpFormFieldDao, BpmFormTable mainTableDef,
			BpmFormData bpmFormData) throws Exception {
		Map subTableMap = convertTable(listTable);
		Map formTableMap = convertTableMap(listTable);

		int isExternal = mainTableDef.getIsExternal();

		String tablePrefix = isExternal == 1 ? "" : "W_";

		String colPrefix = isExternal == 1 ? "" : "F_";

		JSONArray arySub = jsonObj.getJSONArray("sub");

		for (int i = 0; i < arySub.size(); i++) {
			SubTable subTable = new SubTable();
			JSONObject subTableObj = arySub.getJSONObject(i);
			String subName = subTableObj.getString("tableName").toLowerCase();
			Long subTableId = (Long) subTableMap.get(subName);
			if (subTableId != null) {
				BpmFormTable bpmFormTable = (BpmFormTable) formTableMap.get(subName);

				List subTableFields = bmpFormFieldDao.getByTableId(subTableId);
				Map subTableTypeMap = convertFieldToMap(subTableFields);

				List scriptFields = getFieldsFromScript(subTableFields);

				subTable.setTableName(tablePrefix + subName);

				if (isExternal == 1) {
					TableRelation tableRelation = mainTableDef.getTableRelation();
					Map mapRelation = tableRelation.getRelations();
					String fk = (String) mapRelation.get(subName);
					String pk = bpmFormTable.getPkField();
					subTable.setPkName(pk);
					subTable.setFkName(fk);
				} else {
					subTable.setPkName("ID");
					subTable.setFkName("REFID");
				}

				JSONArray arySubFields = subTableObj.getJSONArray("fields");
				for (int j = 0; j < arySubFields.size(); j++) {
					JSONObject subFieldObj = arySubFields.getJSONObject(j);
					Map subRow = handleRow(colPrefix, subTableTypeMap, subFieldObj);

					Map map = caculateField(colPrefix, scriptFields, subRow);

					subRow.putAll(map);

					handFkRow(mainTableDef, bpmFormTable, subRow, bpmFormData.getPkValue());

					subTable.addRow(subRow);

					List formUsers = getFormUsers(colPrefix, subTableFields, subRow);
					TaskUserAssignService.addFormUsers(formUsers);
				}
				bpmFormData.addSubTable(subTable);
			}
		}
	}

	private static void handOpinion(BpmFormData bpmFormData, JSONObject jsonObj) {
		JSONArray aryOpinion = jsonObj.getJSONArray("opinion");

		for (int i = 0; i < aryOpinion.size(); i++) {
			JSONObject opinion = aryOpinion.getJSONObject(i);
			String formName = opinion.getString("name");
			String value = opinion.getString("value");
			bpmFormData.addOpinion(formName, value);
		}
	}

	private static void handFkRow(BpmFormTable mainTabDef, BpmFormTable bpmFormTable, Map<String, Object> rowData, PkValue pkValue) throws Exception {
		int isExternal = bpmFormTable.getIsExternal();

		if (isExternal == 1) {
			PkValue pk = getPk(bpmFormTable);
			if (pk.getValue().toString().equals("-1")) {
				logger.debug("外键值不能为-1,请设置主表的主键生成规则!");
				throw new Exception("外键值不能为-1,请设置主表的主键生成规则!");
			}
			rowData.put(pk.getName(), pk.getValue());

			TableRelation tableRelation = mainTabDef.getTableRelation();
			Map relation = tableRelation.getRelations();
			String fk = (String) relation.get(bpmFormTable.getTableName());
			rowData.put(fk, pkValue.getValue());
		} else {
			rowData.put("REFID", pkValue.getValue());
		}
	}

	private static List<BpmFormField> getFieldsFromScript(List<BpmFormField> list) {
		List map = new ArrayList();
		for (BpmFormField field : list) {
			if (field.getValueFrom().shortValue() == 2)
				map.add(field);
		}
		return map;
	}

	private static List<TaskExecutor> getFormUsers(String colPrefix, List<BpmFormField> list, Map<String, Object> data) {
		List formUsers = new ArrayList();
		for (BpmFormField field : list) {
			Map property = field.getPropertyMap();
			if ((property.containsKey("isformuser")) && (((String) property.get("isformuser")).equals("1"))) {
				String idKey = colPrefix + field.getFieldName() + "ID";
				String nameKey = colPrefix + field.getFieldName();

				if ((field.getControlType().shortValue() == 4) || (field.getControlType().shortValue() == 8)) {
					Object userIds = data.get(idKey);
					Object userNames = data.get(nameKey);
					if (userIds != null) {
						String[] aryUserId = userIds.toString().split(",");
						String[] aryUserName = userNames.toString().split(",");
						for (int i = 0; i < aryUserId.length; i++) {
							String userId = aryUserId[i];
							String userName = aryUserName[i];
							if (StringUtil.isNotEmpty(userId)) {
								formUsers.add(TaskExecutor.getTaskUser(userId, userName));
							}
						}
					}

				} else if (field.getControlType().shortValue() == 6) {
					Object orgIds = data.get(idKey);
					Object orgNames = data.get(nameKey);
					if (orgIds != null) {
						String[] aryOrgId = orgIds.toString().split(",");
						String[] aryOrgName = orgNames.toString().split(",");

						for (int i = 0; i < aryOrgId.length; i++) {
							String orgId = aryOrgId[i];
							if (StringUtil.isNotEmpty(orgId)) {
								String orgName = aryOrgName[i];
								formUsers.add(TaskExecutor.getTaskOrg(orgId, orgName));
							}
						}
					}
				}
			}
		}
		return formUsers;
	}

	private static Map<String, Object> caculateField(String colPrefix, List<BpmFormField> fields, Map<String, Object> data) {
		Map result = new HashMap();
		for (BpmFormField field : fields) {
			String name = colPrefix + field.getFieldName();

			String script = field.getScript();
			Object value = FormUtil.calcuteField(script, data, colPrefix);
			result.put(name, value);
		}
		return result;
	}

	private static PkValue getPk(BpmFormTable bpmFormTable) throws Exception {
		Object pkValue = null;
		String pkField = "ID";
		if (bpmFormTable.getIsExternal() == 1) {
			pkField = bpmFormTable.getPkField();
		}

		if (bpmFormTable.getIsExternal() == 1) {
			Short keyType = bpmFormTable.getKeyType();
			String keyValue = bpmFormTable.getKeyValue();
			pkValue = FormUtil.getKey(keyType.shortValue(), keyValue);
		} else {
			pkValue = UniqueIdUtil.genId();
		}

		PkValue pk = new PkValue();
		pk.setIsAdd(true);
		pk.setName(pkField);
		pk.setValue(pkValue);
		return pk;
	}

	private static Map<String, Object> handleRow(String colPrefix, Map<String, BpmFormField> fieldTypeMap, JSONObject fieldsJsonObj) {
		Map row = new HashMap();

		for (Iterator it = fieldsJsonObj.keys(); it.hasNext();) {
			String key = (String) it.next();
			Object obj = fieldsJsonObj.get(key);
			String value = "";
			if (((obj instanceof JSONArray)) || ((obj instanceof JSONObject))) {
				value = obj.toString();
			} else {
				value = (String) obj;
			}

			if ("ID".toLowerCase().equals(key)) {
				if (StringUtil.isEmpty(value)) {
					value = "0";
					row.put("curentUserId_", AppContext.getCurrentUserId());
					Long orgId = Long.valueOf(0L);
					SysOrg sysOrg = new SysOrg();//ContextUtil.getCurrentOrg();
					if (sysOrg != null) {
						orgId = sysOrg.getId();
					}
					row.put("curentOrgId_", orgId);
				}

			}

			if ("ID".toLowerCase().equals(key)) {
				row.put(key, new Long(value));
			} else {
				BpmFormField bpmFormField = (BpmFormField) fieldTypeMap.get(key.toLowerCase());

				try {
					Object convertValue = convertType(value, bpmFormField);
					row.put(colPrefix + key, convertValue);
				} catch (ParseException e) {
				}
			}
		}

		return row;
	}

	private static Object convertType(String strValue, BpmFormField formField) throws ParseException {
		String type = formField.getFieldType();
		if (StringUtil.isEmpty(strValue))
			return null;
		Object value = strValue;
		if ("date".equals(type)) {
			value = DateFormatUtil.parseDate(strValue);
		} else if ("number".equals(type)) {
			if (formField.getDecimalLen().intValue() > 0) {
				value = Double.valueOf(Double.parseDouble(strValue));
			} else if (formField.getIntLen().intValue() <= 10) {
				value = Integer.valueOf(Integer.parseInt(strValue));
			} else {
				value = Long.valueOf(Long.parseLong(strValue));
			}

		}

		return value;
	}

	private static Map<String, BpmFormField> convertFieldToMap(List<BpmFormField> list) {
		Map map = new HashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			BpmFormField field = (BpmFormField) it.next();
			map.put(field.getFieldName().toLowerCase(), field);
		}
		return map;
	}

	private static Map<String, Long> convertTable(List<BpmFormTable> list) {
		Map map = new HashMap();
		for (BpmFormTable tb : list) {
			map.put(tb.getTableName().toLowerCase(), tb.getTableId());
		}
		return map;
	}

	private static Map<String, BpmFormTable> convertTableMap(List<BpmFormTable> list) {
		Map map = new HashMap();
		for (BpmFormTable tb : list) {
			map.put(tb.getTableName().toLowerCase(), tb);
		}
		return map;
	}

	private static SqlModel getInsert(String tableName, Map<String, Object> mapData) {
		StringBuffer fieldNames = new StringBuffer();
		StringBuffer params = new StringBuffer();
		List values = new ArrayList();

		for (Map.Entry entry : mapData.entrySet()) {
			fieldNames.append((String) entry.getKey()).append(",");
			params.append("?,");
			values.add(entry.getValue());
		}
		StringBuffer sql = new StringBuffer();

		sql.append(" INSERT INTO ");
		sql.append(tableName);
		sql.append("(");
		sql.append(fieldNames.substring(0, fieldNames.length() - 1));
		sql.append(")");
		sql.append(" VALUES (");
		sql.append(params.substring(0, params.length() - 1));
		sql.append(")");

		SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());
		return sqlModel;
	}

	private static SqlModel getUpd(String tableName, Map<String, Object> mapData) {
		List values = new ArrayList();

		String key = "ID".toLowerCase();

		String pkValue = mapData.get(key).toString();

		StringBuffer set = new StringBuffer();

		for (Map.Entry entry : mapData.entrySet()) {
			if (!key.equals(entry.getKey())) {
				set.append((String) entry.getKey()).append("=?,");
				values.add(entry.getValue());
			}
		}
		if (values.size() == 0)
			return null;

		StringBuffer sql = new StringBuffer();

		sql.append(" update ");
		sql.append(tableName);
		sql.append(" set ");
		sql.append(set.substring(0, set.length() - 1));
		sql.append(" where ");
		sql.append(key);
		sql.append("=?");
		values.add(pkValue);
		SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());

		return sqlModel;
	}

	private static SqlModel getUpdate(BpmFormData bpmFormData) {
		PkValue pk = bpmFormData.getPkValue();
		String tableName = bpmFormData.getTableName();
		Map mapData = bpmFormData.getMainCommonFields();
		StringBuffer set = new StringBuffer();

		List values = new ArrayList();
		Set<Map.Entry> entrys = mapData.entrySet();
		for (Map.Entry entry : entrys) {
			set.append((String) entry.getKey()).append("=?,");
			values.add(entry.getValue());
		}
		if (values.size() == 0)
			return null;

		StringBuffer sql = new StringBuffer();
		if (set.length() > 0) {
			sql.append(" update ");
			sql.append(tableName);
			sql.append(" set ");
			sql.append(set.substring(0, set.length() - 1));
			sql.append(" where ");
			sql.append(pk.getName());
			sql.append("=?");
			values.add(pk.getValue());
		}
		SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());
		return sqlModel;
	}
}