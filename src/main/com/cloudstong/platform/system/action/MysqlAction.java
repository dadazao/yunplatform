package com.cloudstong.platform.system.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.SystemProperty;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.metadata.model.SchemaColumns;
import com.cloudstong.platform.resource.metadata.model.SchemaTables;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.MysqlService;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:mysql数据库的备份与恢复
 */
public class MysqlAction extends CompexDomainAction {
	
	@Resource
	protected MysqlService mysqlService;
	
	private String filename;
	
	public String backup() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			filename = DateUtil.getNowTimeSecond()+".sql";
			mysqlService.doSaveMysqlBackup(filename, user.getId());
			StringBuilder sdbSql = new StringBuilder();
			List<SchemaTables> lstAllTables = mysqlService.getSchemaTables();
			List<SchemaColumns> lstAllColumns = mysqlService.getSchemaColumns();
			
			for (SchemaTables schemaTables : lstAllTables) {
				sdbSql.append("DROP TABLE IF EXISTS ");
				sdbSql.append(schemaTables.getTableName());
				sdbSql.append(";;\n");
			}
			
			sdbSql.append(createMysqlTables(lstAllTables, lstAllColumns));
			
			sdbSql.append(exportData(lstAllTables, lstAllColumns));
			
			Properties sp = (Properties) getSession().getAttribute("global");
			String mysqlPath = sp.getProperty("mysqlPath");
			String serverPath = getSession().getServletContext().getRealPath(""); 
			
			File file = new File(serverPath+mysqlPath);
			if(!file.exists()){
				file.mkdirs();
			}
			FileWriter backupFw = new FileWriter(serverPath+mysqlPath+"/"+filename);
			//将缓冲对文件的输出
            BufferedWriter backupBw = new BufferedWriter(backupFw); 
            backupBw.write(sdbSql.toString()); 
            backupBw.flush(); 
            backupBw.close();
            backupFw.close();
            printJSON("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	private StringBuilder createMysqlTables(List<SchemaTables> lstAllTables, List<SchemaColumns> lstAllColumns) {
		StringBuilder _sTableSql = new StringBuilder();
		for (SchemaTables table : lstAllTables) {
			List<SchemaColumns> _lstTmpcolumn = new ArrayList<SchemaColumns>();
			for (SchemaColumns column : lstAllColumns) {
				if (table.getTableName().equals(column.getTableName())) {
					_lstTmpcolumn.add(column);
				}
			}
			_sTableSql.append("create table "+ table.getTableName() + "(");
			for (SchemaColumns column : _lstTmpcolumn) {
				_sTableSql.append(column.getColumnName());
				_sTableSql.append(" ");
				_sTableSql.append(column.getColumnType());
				if (column.getDataType().equalsIgnoreCase("timestamp")) {
					_sTableSql.append(" NULL");
					if(null != column.getColumnDefault()){
						if(column.getColumnDefault().equalsIgnoreCase("CURRENT_TIMESTAMP")){
							_sTableSql.append(" DEFAULT " + column.getColumnDefault());
						}else{
							_sTableSql.append(" DEFAULT '" + column.getColumnDefault() + "'");
						}
					}else{
						_sTableSql.append(" DEFAULT NULL");
					}
				}else{
					if(null != column.getColumnDefault()){
						_sTableSql.append(" DEFAULT '" + column.getColumnDefault() + "'");
					}else{
						_sTableSql.append(" DEFAULT NULL");
					}
				}
				_sTableSql.append(",");
			}
			_sTableSql.append("  PRIMARY KEY (id)) ENGINE=MyISAM DEFAULT CHARSET=utf8;;\n");
		}
		return _sTableSql;
	}
	
	private StringBuilder exportData(List<SchemaTables> lstAllTables, List<SchemaColumns> lstAllColumns) {
		StringBuilder sbdSql = new StringBuilder();
		for (SchemaTables table : lstAllTables) {
			try {
				List<SchemaColumns> _lstTmpcolumn = new ArrayList<SchemaColumns>();
				for (SchemaColumns column : lstAllColumns) {
					if (table.getTableName().equals(column.getTableName())) {
						_lstTmpcolumn.add(column);
					}
				}
				List<Map<String, Object>> _lstResult = mysqlService.getDatas(table.getTableName());
				if (_lstResult != null) {
					for (Map<String, Object> map : _lstResult) {
						sbdSql.append("insert into "+ table.getTableName() + "(");
						StringBuilder _sbdTemp = new StringBuilder();
						for (SchemaColumns column : _lstTmpcolumn) {
							if (map.get(column.getColumnName()) != null) {
								sbdSql.append(column.getColumnName()+ ",");
								_sbdTemp.append("\'"+filterHtmlChar(map.get(column.getColumnName()).toString())+"\',");
							}
						}
						sbdSql.delete(sbdSql.length() - 1, sbdSql.length());
						_sbdTemp.delete(_sbdTemp.length() - 1, _sbdTemp.length());
						sbdSql.append(") VALUES (");
						sbdSql.append(_sbdTemp+");;\n");
					}
				}
			} catch (Exception e) {}
		}
		return sbdSql;
	}
	
	public String recover() throws IOException {
		StringBuilder sdbSql = new StringBuilder();
		Properties sp = (Properties) getSession().getAttribute("global");
		String mysqlPath = sp.getProperty("mysqlPath");
		String serverPath = getSession().getServletContext().getRealPath(""); 
		
		FileReader fr =  new FileReader (serverPath+mysqlPath+"/"+filename);
        BufferedReader br = new BufferedReader (fr);
        String s;
        String tmp = "";
        while ((s = br.readLine() )!=null) {
        	if(s.endsWith(";;")){
        		if(!tmp.equals("")){
        			mysqlService.doMysqlRecover(tmp+s);
        			tmp = "";
        		}else{
        			mysqlService.doMysqlRecover(s);
        		}
        	}else{
        		tmp += s;
        	}
       }
        br.close();
        fr.close();
		printJSON("success");
		return NONE;
	}

	private String filterHtmlChar(String str){
		str = str.replaceAll("'", "\\\\\'");
		return str;
	}
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}

