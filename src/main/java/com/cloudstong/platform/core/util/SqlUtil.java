/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author michael
 * Created on 2012-12-7
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:sql语句帮助类
 * 
 */
public class SqlUtil {
	public static String getRealSql(String sql,Object[]objs){
		if(sql.indexOf("?")>0){
			for(int i = 0;i<objs.length;i++){
				if(objs[i] instanceof String || objs[i] instanceof Date){
					sql=sql.replaceFirst("\\?", "'"+String.valueOf(objs[i])+"'");
				}else{
					sql=sql.replaceFirst("\\?", String.valueOf(objs[i]));
				}
			}
			return sql;
		}else{
			return sql;
		}
	}
	
	public static void main(String[] args) {
		FileUtils utils = new FileUtils();
		try {
			List<String> lines = utils.readLines(new File("C:\\updateHuaOA.sql"),"utf-8");
			int j=1;
			for(int i = 1;i<lines.size();i++){
				String s = lines.get(i);
				if(s.matches("^#130314 15:00[^;]*")){
					System.out.println(s);
					System.out.println(i);
					j=i;
					break;
				}
			}
			List<String> freshlines = new ArrayList<String>();
			boolean canWrite = true;
			for(;j<lines.size();j++){
				String l = lines.get(j);
				if(l.isEmpty()){
					continue;
				}
				if(l.matches("^#[^\n]*")){
					continue;
				}
				if(l.matches("^COMMIT[^\n]*")||
						l.matches("^DELIMITER[^\n]*")||
						l.matches("^BEGIN[^\n]*")||
						l.matches("^ROLLBACK[^\n]*")||
						l.matches("^SET[^\n]*")||
						l.matches("^use database[^\n]*")
						){
					//System.out.println(l);
					continue;
				}
				if(l.matches("^BINLOG[^\n]*")){
					canWrite = false;
				}
				if(!canWrite&&l.contains("'/*!*/;")){
					canWrite = true;
				}
				if(l.matches("^/*[^;]*;$")){
					//System.out.println(l);
					l=";";
				}
				if(canWrite){
					System.out.println(l);
					freshlines.add(l);
				}else{
					continue;
				}
			}
			utils.writeLines(new File("C:\\newtest.sql"),"utf-8", freshlines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
