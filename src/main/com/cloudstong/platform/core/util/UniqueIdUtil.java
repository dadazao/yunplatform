package com.cloudstong.platform.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UniqueIdUtil {
	private static long base = 1L;

	private static long nextId = 0L;

	private static long lastId = -1L;
	private static JdbcTemplate jdbcTemplate;

	private static void init() {
		jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplateGenId");
		String path = PathUtil.getWebInfPath() + "WEB-INF/classes/global.properties".replace("/", File.separator);
		String strBase = FileUtil.readFromProperties(path, "genId.base");
		if (strBase != null)
			base = Integer.parseInt(strBase);
	}

	private static void getNextIdBlock() {
		if (jdbcTemplate == null) {
			init();
		}
		Long bound = Long.valueOf(-1L);
		Integer incremental = Integer.valueOf(-1);
		String sql = "SELECT tbl_bound,tbl_incremental FROM sys_db_id T WHERE T.id=?";
		String upSql = "UPDATE sys_db_id  SET tbl_bound=? WHERE id=?";
		try {
			Map map = jdbcTemplate.queryForMap(sql, new Object[] { Long.valueOf(base) });
			bound = Long.valueOf(Long.parseLong(map.get("tbl_bound").toString()));
			incremental = Integer.valueOf(Integer.parseInt(map.get("tbl_incremental").toString()));
			nextId = bound.longValue();
			lastId = bound.longValue() + incremental.intValue();
			jdbcTemplate.update(upSql, new Object[] { Long.valueOf(lastId), Long.valueOf(base) });
		} catch (EmptyResultDataAccessException e) {
			insertNewComputer();
		}
	}

	private static void insertNewComputer() {
		try {
			lastId = 10000L;
			String sql = "INSERT INTO sys_db_id (id,tbl_incremental,tbl_bound) VALUES(" + base + ",10000," + lastId + ")";
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized long genId() {
		if (lastId <= nextId) {
			getNextIdBlock();
		}
		long _nextId = nextId++;
		return _nextId + base * 10000000000000L;
	}

	public static synchronized List<Long> genId(int num) {
		List<Long> list = new ArrayList<Long>(num);
		int i = 0;
		while (i < num) {
			if (lastId <= nextId) {
				getNextIdBlock();
			}
			long _nextId = nextId++;
			list.add(_nextId + base * 10000000000000L);
			i++;
		}
		return list;
	}
	
	public static final String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	private static void changAllIdToLong() {
		ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml"); 
		JdbcTemplate jdbcTemplate = (JdbcTemplate)factory.getBean("jdbcTemplate");
		
//		String sql = "select * from sys_tables";
//		List<Map<String,Object>> tables = jdbcTemplate.queryForList(sql);
//		String message = "";
//		for(Map table:tables) {
//			String tableName = (String)table.get("tbl_tableName");
//			try{
//				update(jdbcTemplate,tableName,new String[]{"comm_createBy","comm_updateBy"});
//			}catch(Exception e) {
//				System.err.println("fail table======>" + tableName);
//				e.printStackTrace();
//				message += "table:"+tableName + ",exception:" + e.getMessage() + "\r\n";
//			}
//		}
		
//		update(jdbcTemplate,"sys_catalog",new String[]{"tbl_listid","tbl_parentId"});
//		
//		update(jdbcTemplate,"bus_gwflownode",new String[]{"tbl_operator","tbl_officialdocid"});
//		update(jdbcTemplate,"bus_gwgongwenrizhi",new String[]{"tbl_operatorid","tbl_docid"});
//		update(jdbcTemplate,"bus_gwliucheng_liuchengji",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"bus_inbox",new String[]{"tbl_receiver","tbl_sender"});
//		update(jdbcTemplate,"bus_liuchengjiedian",new String[]{"tbl_banlirenyuan"});
//		update(jdbcTemplate,"bus_project",new String[]{"tbl_employee_id"});
//		update(jdbcTemplate,"sys_attachment",new String[]{"tbl_recordid"});
//		update(jdbcTemplate,"sys_biaodansheji",new String[]{"tbl_ziduan","tbl_form","tbl_tableid","tbl_tabid","tbl_daima","tbl_partitionid","tbl_compexid","tbl_defaultselectitem"});
//		update(jdbcTemplate,"sys_biaodantab",new String[]{"tbl_form","tbl_tableid","tbl_muban"});
//		update(jdbcTemplate,"sys_dictionarys",new String[]{"tbl_parentId"});
//		update(jdbcTemplate,"sys_liebiao",new String[]{"tbl_advancequerycontrolid","tbl_formid","tbl_listcontrolid"});
//		update(jdbcTemplate,"sys_liebiaobutton",new String[]{"tbl_buttonid","tbl_tabulation","tbl_formid"});
//		update(jdbcTemplate,"sys_biaodan",new String[]{"tbl_zhubiaoid"});
//		update(jdbcTemplate,"sys_liebiaoquery",new String[]{"tbl_tabulation","tbl_suoshubiao","tbl_ziduan"});
//		update(jdbcTemplate,"sys_liebiaozujian",new String[]{"tbl_orderid","tbl_operationid","tbl_paginationid"});
//		update(jdbcTemplate,"sys_log",new String[]{"tbl_operationmodule","tbl_operator"});
//		update(jdbcTemplate,"sys_menu_menuitem",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_menuitem",new String[]{"tbl_mulu"});
//		update(jdbcTemplate,"sys_oprtbutton",new String[]{"tbl_tabulation","tbl_buttonid"});
//		update(jdbcTemplate,"sys_org",new String[]{"tbl_parentId"});
//		update(jdbcTemplate,"sys_biaodanbutton",new String[]{"tbl_buttonid","tbl_formid","tbl_tabid","tbl_partitionid"});
//		update(jdbcTemplate,"sys_partition",new String[]{"tbl_basetemplate","tbl_templateid"});
//		update(jdbcTemplate,"sys_portal_portlet",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_portlet",new String[]{"tbl_listid"});
//		update(jdbcTemplate,"sys_privilege",new String[]{"tbl_catalog","tbl_seq","tbl_referenceId"});
//		update(jdbcTemplate,"sys_relation",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_renyuan",new String[]{"tbl_suoshubumen"});
//		update(jdbcTemplate,"sys_role_roles",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_student_course",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_student_studentdet",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_studentdetails",new String[]{"tbl_shenggao","tbl_tizhong","tbl_studentid"});
//		update(jdbcTemplate,"sys_tree",new String[]{"tbl_genid","tbl_parentcolumnidchild","tbl_tableidchild","tbl_discolumnidchild","tbl_ordercolumnidchild"});
//		update(jdbcTemplate,"sys_useinfo",new String[]{"tbl_yewuid","tbl_zujianid","tbl_relationid"});
//		update(jdbcTemplate,"sys_user",new String[]{"tbl_department","tbl_person"});
//		update(jdbcTemplate,"sys_user_position",new String[]{"tbl_userid","tbl_positionid"});
//		update(jdbcTemplate,"sys_user_role",new String[]{"tbl_mainid","tbl_subid"});
//		update(jdbcTemplate,"sys_xitongyuansubiao",new String[]{"tbl_parentId"});
		update(jdbcTemplate,"sys_columns",new String[]{"tbl_tableId"});
		
//		System.err.println("faile table:" + message);
	}
	
	private static void update(JdbcTemplate jdbcTemplate,String table,String[] columns){
		//转换字符串到long
		String sql = "select * from "+table;
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		StringBuffer updateSql = new StringBuffer("update "+table+" set id=?");
		if(columns != null) {
			for(String column:columns) {
				updateSql.append("," + column+"=?");
			}
		}
		updateSql.append(" where id=?");
		
		for(Map map:list) {
			String id = map.get("id").toString();
			String newId = changeStringToLong(id);
			
			List<String> paramList = new ArrayList<String>();
			paramList.add(newId);
			if(columns != null) {
				for(String column:columns) {
					String value = String.valueOf(map.get(column));
					paramList.add(changeStringToLong(value));
				}
			}
			paramList.add(id);
			jdbcTemplate.update(updateSql.toString(), paramList.toArray());
		}
		
		
		//修改字段类型
		if(columns != null) {
			for(String column:columns) {
				StringBuffer modifySql = new StringBuffer("alter table "+table+" modify ");
				StringBuffer columnSql = new StringBuffer("update sys_columns set tbl_dataType='bigint',tbl_length=20 where tbl_belongTable=? and tbl_columnName=?");
				modifySql.append(column+" bigint(20)");
				jdbcTemplate.execute(modifySql.toString());
				jdbcTemplate.update(columnSql.toString(),new String[]{table,column});
			}
			jdbcTemplate.execute("alter table "+table+" modify id bigint(20)");
		}
	}
	
	private static String changeStringToLong(String id) {
		if(id==null) {
			return null;
		}
		if(StringUtil.isNumberic(id)){
			return id;
		}
		String result = "";
		if(id.hashCode()<0) {
			result += "10"+Math.abs(id.hashCode());
		}else{
			result +=id.hashCode();
		}
		if("0".equals(result)){
			result = null;
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(changeStringToLong("18322b8940634da18b7ee037f177744b"));
		changAllIdToLong();
		System.out.println(genId(10));
	}
}