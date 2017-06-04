package com.cloudstong.platform.system.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.system.dao.IPrivilegeDao;
import com.cloudstong.platform.system.model.FunctionTmp;
import com.cloudstong.platform.system.model.Privilege;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:权限管理数据库接口的实现
 */
@Repository("privilegeDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PrivilegeDaoImpl implements IPrivilegeDao {

	@javax.annotation.Resource
	private JdbcTemplateExtend jdbcTemplate;

	public void savePrivilege(Privilege privilege) {
		try {
			String sql = "insert into sys_privilege(id,tbl_name,tbl_catalog,tbl_type,tbl_comment,tbl_seq,comm_createDate,comm_updateDate,tbl_referenceId,tbl_tablename,comm_createBy) values (?,?,?,?,?,?,?,?,?,?,?)";
			Seqcode seqcode = (Seqcode)jdbcTemplate.queryForObject("select * from sys_bianma where id=?", new Object[]{privilege.getTblSeq()}, new BeanPropertyRowMapper(Seqcode.class));
			String referenceId = "";
			if("sys_biaodansheji".equalsIgnoreCase(seqcode.getTblTablename())){
				referenceId = seqcode.getTblObject().split("_")[1]+"_"+seqcode.getTblType();
			}else{
				referenceId = jdbcTemplate.queryForObject("select id from "+seqcode.getTblTablename()+" where tbl_bianma=?", new Object[]{seqcode.getTblValue()}, String.class);
			}
			Object[] _objParams = new Object[] { UniqueIdUtil.genId(),privilege.getTblName(),
					privilege.getTblCatalog(), privilege.getTblType(),
					privilege.getTblComment(), privilege.getTblSeq(),
					new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),
					referenceId, seqcode.getTblTablename(),privilege.getCreateBy()};
			jdbcTemplate.update(sql, _objParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Privilege> queryPrivilege(String plId){
		if(plId == null){
			return jdbcTemplate.query("select * from sys_privilege", new BeanPropertyRowMapper(Privilege.class));
		}else{
			return jdbcTemplate.query("select * from sys_privilege where id=?", new Object[] { plId}, new BeanPropertyRowMapper(Privilege.class));
		}
		
	}
	
	public List<Privilege> queryPrivilegeByCatalog(String catalog){
		if(catalog.equals("1")){
			return jdbcTemplate.query("select * from sys_privilege", new BeanPropertyRowMapper(Privilege.class));
		}else{
			return jdbcTemplate.query("select * from sys_privilege where tbl_catalog=?", new Object[] { catalog}, new BeanPropertyRowMapper(Privilege.class));
		}
	}
	
	public void updatePrivilege(Privilege privilege){
		try {
			String sql = "update sys_privilege set tbl_name=?,comm_updateDate=? where tbl_catalog=? and tbl_seq=?";
			Object[] _objParams = new Object[] { privilege.getTblName(), new Date(System.currentTimeMillis()), privilege.getTblCatalog(), privilege.getTblSeq()};
			jdbcTemplate.update(sql, _objParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleltePrivilege(String plId){
		String sql = "delete from sys_privilege where id=?";
		Object[] _objParams = new Object[] { plId };
		jdbcTemplate.update(sql, _objParams);
	}

	@Override
	public List<FunctionTmp> queryFunctionTmp() {
		return jdbcTemplate.query("select * from sys_function_tmp where id=?", new Object[] {"1" }, new BeanPropertyRowMapper(FunctionTmp.class));
	}
	
	@Override
	public void updateFunctionTmp(FunctionTmp ft, String psId) {
		try {
			List<FunctionTmp> _lstResult = queryFunctionTmp();
			FunctionTmp _functionTmp  = _lstResult.get(0);
			boolean _bTmp = true;
			if(_functionTmp.getTblCatalogs() != null){
				for (String catalog : _functionTmp.getTblCatalogs().split(";")) {
					for (String str : ft.getTblCatalogs().split(";")) {
						if(catalog.equals(str)){
							_bTmp = false;
							break;
						}
					}
					if(_bTmp){
						ft.setTblCatalogs(ft.getTblCatalogs()+catalog+";");
					}else{
						_bTmp = true;
					}
					
				}
			}
			if(_functionTmp.getTblResources() != null){
				for (String resource : _functionTmp.getTblResources().split(";")) {
					for (String str : ft.getTblResources().split(";")) {
						if(resource.equals(str)){
							_bTmp = false;
							break;
						}
					}
					if(_bTmp){
						ft.setTblResources(ft.getTblResources()+resource+";");
					}else{
						_bTmp = true;
					}
					
				}
			}
			
			String sql = "update sys_function_tmp set tbl_catalogs=?,tbl_resources=? where id=?";
			Object[] _objParams = new Object[] {ft.getTblCatalogs(), ft.getTblResources(), psId };
			jdbcTemplate.update(sql, _objParams);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void updateFunctionTmp(FunctionTmp ft) {
		try {
			String sql = "update sys_function_tmp set tbl_catalogs=?,tbl_resources=? where id=?";
			Object[] _objParams = new Object[] {ft.getTblCatalogs(), ft.getTblResources(), "1" };
			jdbcTemplate.update(sql, _objParams);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public int queryPrivilegeNum(String catalogId){
		return jdbcTemplate.queryForInt("select count(*) from sys_privilege where tbl_catalog=?",new Object[] {catalogId});
	}
	
	public boolean hasDelPrivilege(String bianma){
		return (jdbcTemplate.queryForInt("select count(*) from sys_privilege where tbl_seq in (select id from sys_bianma where tbl_value = ?)",new Object[] {bianma.split(",")[0]}))>0 ? true :false;
	}
}
