/**
 * 
 */
package com.cloudstong.platform.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysOrgDao;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysOrgType;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.system.model.SysUserPosition;
import com.cloudstong.platform.system.service.SysOrgService;
import com.cloudstong.platform.system.service.impl.SysPositionServiceImpl.SysPositionRowMapper;
import com.cloudstong.platform.system.service.impl.SysPositionServiceImpl.SysUserPositionRowMapper;
import com.cloudstong.platform.system.service.impl.SysPositionServiceImpl.SysUserRowMapper;

/**
 * @author liuqi
 * Created on 2014-8-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@Repository("sysOrgService")
public class SysOrgServiceImpl implements SysOrgService {
	@Resource
	private SysOrgDao orgDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<SysOrg> selectAllOrg() {
		return orgDao.select("select o1.*,o2.tbl_name as parentName from sys_org o1 left join sys_org o2 on o1.tbl_parentId=o2.id", new Object[]{}, new SysOrgRowMapper());
	}

	@Override
	public PageResult queryOrg(QueryCriteria queryCriteria) {
		return orgDao.query("select o1.*,o2.tbl_name as parentName from sys_org o1 left join sys_org o2 on o1.tbl_parentId=o2.id where o1.id != 1 ", queryCriteria, new SysOrgRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysOrg findOrgById(Long orgId) {
		return orgDao.selectById(orgId, new SysOrgRowMapper());
	}

	@Override
	public Long doSaveOrg(SysOrg sysOrg,SysUser user) {
		Long id =null;
		Date now = new Date(System.currentTimeMillis());
		if(sysOrg.getId()==null||sysOrg.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_org (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_name,tbl_bumenzhineng,tbl_bumenjibie,tbl_jigoupaixu,tbl_parentId,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,sysOrg.getOrgName(),sysOrg.getOrgFunction(),sysOrg.getOrgLevel(),sysOrg.getOrgOrder(),sysOrg.getParentId(),sysOrg.getOptCounter()};
			orgDao.insert(sql, args);
			id = uuid;
		}else{
			id = sysOrg.getId();
			String sql = "update sys_org set comm_updateBy=?,comm_updateDate=?,tbl_name=?,tbl_bumenzhineng=?,tbl_bumenjibie=?,tbl_jigoupaixu=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,sysOrg.getOrgName(),sysOrg.getOrgFunction(),sysOrg.getOrgLevel(),sysOrg.getOrgOrder(),sysOrg.getId()};
			orgDao.update(sql, args);
		}
		return id; 
	}

	@Override
	public void doDeleteOrgs(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			//删除机构
			orgDao.delete(id);
			//删除用户机构的中间表记录
			orgDao.deleteMiddleTable(id);
		}		
	}

	@Override
	public PageResult queryUserOrg(QueryCriteria queryCriteria) {
		String sql = "select uo.*,u.tbl_yonghuxingming as fullname,o.tbl_name as orgname from sys_user_org uo left join sys_user u on uo.tbl_userid=u.id left join sys_org o on uo.tbl_orgid=o.id where 1=1 ";
		return orgDao.query(sql,queryCriteria,new SysUserOrgRowMapper());
	}

	@Override
	public void doAddUser(String userIds, Long orgId) {
		if(userIds!=null && !userIds.equals("") && orgId!=null && !orgId.equals("")){
			String []uids = userIds.split(";");
			for(int i=0;i<uids.length;i++){
				String sql = "select * from sys_user_org where tbl_userid = ? and tbl_orgid = ?";
				Object[]args = new Object[]{uids[i],orgId};
				List<Map<String,Object>> _list =  orgDao.select(sql, args);
				if(_list.size()==0){
					String insertSql = "insert into sys_user_org (id,tbl_userid,tbl_orgid) values(?,?,?)";
					Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),uids[i],orgId};
					orgDao.insert(insertSql,insertArgs);
				}
			}
		}
	}

	@Override
	public void doDeleteUser(Long[] selectedSubIDs) {
		for(int i=0;i<selectedSubIDs.length;i++){
			orgDao.delete("sys_user_org",selectedSubIDs[i]);
		}
	}

	@Override
	public void doSetupMainOrg(Long userOrgId) {
		try {
			String sql = "update sys_user_org set tbl_zhujigou = 1 where id = ?";
			Object[]args = new Object[]{userOrgId};
			orgDao.update(sql,args);
			
			List<Map<String,Object>> _list = orgDao.select("select * from sys_user_org where id = ?", new Object[]{userOrgId});
			if(_list.size()>0){
				String usql = "update sys_user_org set tbl_zhujigou = 0 where tbl_userid = ? and id != ?";
				Object[]uargs = new Object[]{_list.get(0).get("tbl_userid"),userOrgId};
				orgDao.update(usql,uargs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSetupPrincipal(Long userOrgId) {
		try {
			String sql = "update sys_user_org set tbl_fuzeren = 1 where id = ?";
			Object[]args = new Object[]{userOrgId};
			orgDao.update(sql,args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doCancelPrincipal(Long userOrgId) {
		try {
			String sql = "update sys_user_org set tbl_fuzeren = 0 where id = ?";
			Object[]args = new Object[]{userOrgId};
			orgDao.update(sql,args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SysOrgRowMapper implements RowMapper {
		@SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysOrg sysOrg = new SysOrg();
			sysOrg.setId(rs.getLong("id"));
			sysOrg.setOrgName(rs.getString("tbl_name"));
			sysOrg.setOrgFunction(rs.getString("tbl_bumenzhineng"));
			sysOrg.setOrgLevel(rs.getInt("tbl_bumenjibie"));
			sysOrg.setOrgOrder(rs.getInt("tbl_jigoupaixu"));
			sysOrg.setParentId(rs.getLong("tbl_parentId"));
			try{
				sysOrg.setParentName(rs.getString("parentName"));
			}catch(Exception e) {
			}
			sysOrg.setCreateBy(rs.getLong("comm_createBy"));
			sysOrg.setCreateDate(rs.getTimestamp("comm_createDate"));
			sysOrg.setUpdateBy(rs.getLong("comm_updateBy"));
			sysOrg.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			sysOrg.setOptCounter(rs.getLong("comm_opt_counter"));
			sysOrg.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return sysOrg;
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SysUserRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysUser sysUser = new SysUser();
			sysUser.setId(rs.getLong("id"));
			sysUser.setUsername(rs.getString("tbl_username"));
			sysUser.setPassword(rs.getString("tbl_password"));
			sysUser.setFullname(rs.getString("tbl_yonghuxingming"));
			sysUser.setMobile(rs.getString("tbl_mobile"));
			sysUser.setEmail(rs.getString("tbl_email"));
			sysUser.setPhone(rs.getString("tbl_phone"));
			sysUser.setSex(rs.getString("tbl_sex"));
			sysUser.setLock(rs.getInt("tbl_suoding"));
			sysUser.setOverdue(rs.getInt("tbl_guoqi"));
			sysUser.setActive(rs.getInt("tbl_jihuo"));
			return sysUser;
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SysUserOrgRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysUserOrg sysUserOrg = new SysUserOrg();
			sysUserOrg.setId(rs.getLong("id"));
			sysUserOrg.setZhujigou(rs.getBoolean("tbl_zhujigou"));
			sysUserOrg.setFuzeren(rs.getBoolean("tbl_fuzeren"));
			
			SysUser sysUser = new SysUser();
			sysUser.setId(rs.getLong("tbl_userid"));
			sysUser.setFullname(rs.getString("fullname"));
			
			SysOrg sysOrg = new SysOrg();
			sysOrg.setId(rs.getLong("tbl_orgid"));
			sysOrg.setOrgName(rs.getString("orgname"));
			
			sysUserOrg.setSysUser(sysUser);
			sysUserOrg.setSysOrg(sysOrg);
			return sysUserOrg;
		}
	}

	@Override
	public SysOrg getDefaultOrgByUserId(Long startUserId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public SysOrg getById(Long orgId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public SysOrg getParentWithTypeLevel(SysOrg sysOrg, SysOrgType sysOrgType) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List findSubOrgs(Long[] selectedIDs) {
		StringBuffer sql = new StringBuffer("select * from sys_org where tbl_parentId in(");
		for(int i=0;i<selectedIDs.length;i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
		}
		sql.append(")");
		return orgDao.select(sql.toString(), selectedIDs);
	}

}
 