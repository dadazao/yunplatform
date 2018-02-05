/**
 * 
 */
package com.cloudstong.platform.system.service.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;

/**
 * @author liuqi
 * Created on 2014-8-23
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@Repository("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Resource
	private SysUserDao sysUserDao;
	
	
	@Override
	public List<SysUser> findAllUser() {
		return sysUserDao.getAll(new SysUserRowMapper());
	}

	@Override
	public PageResult queryUser(String queryType,String relationId,QueryCriteria queryCriteria) {
		queryCriteria.setDomain("sys_user");
		PageResult pageResult = new PageResult();
		if(relationId.equals("1")){
			pageResult = sysUserDao.query(queryCriteria,new SysUserRowMapper());
		}else{
			if(queryType.equals("org")){
				String sql = "select u.* from sys_user u left join sys_user_org o on u.id = o.tbl_userid where 1=1 ";
				queryCriteria.addQueryCondition("o.tbl_orgid", relationId);
				pageResult = sysUserDao.query(sql,queryCriteria,new SysUserRowMapper());
			}
		}
		return pageResult;
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
			sysUser.setUserPic(rs.getString("tbl_userpic"));
			sysUser.setCreateBy(rs.getLong("comm_createBy"));
			sysUser.setCreateDate(rs.getTimestamp("comm_createDate"));
			sysUser.setUpdateBy(rs.getLong("comm_updateBy"));
			sysUser.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			sysUser.setOptCounter(rs.getLong("comm_opt_counter"));
			sysUser.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return sysUser;
		}
	}

	@Override
	public PageResult queryUser(QueryCriteria queryCriteria) {
		return sysUserDao.query(queryCriteria,new SysUserRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysUser findUserById(Long userId) {
		return sysUserDao.selectById(userId, new SysUserRowMapper());
	}

	@Override
	public Long doSaveUser(SysUser sysUser, SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(sysUser.getId()==null||sysUser.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_user (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_username,tbl_password,tbl_yonghuxingming,tbl_mobile,tbl_email,tbl_phone,tbl_sex,tbl_suoding,tbl_guoqi,tbl_jihuo,tbl_userpic,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,sysUser.getUsername(),EncryptUtil.encryptSha256(sysUser.getPassword()),sysUser.getFullname(),sysUser.getMobile(),sysUser.getEmail(),sysUser.getPhone(),sysUser.getSex(),sysUser.getLock(),sysUser.getOverdue(),sysUser.getActive(),sysUser.getUserPic(),sysUser.getOptCounter()};
			sysUserDao.insert(sql, args);
			id = uuid;
		}else{
			id = sysUser.getId();
			String sql = "update sys_user set comm_updateBy=?,comm_updateDate=?,tbl_username=?,tbl_yonghuxingming=?,tbl_mobile=?,tbl_email=?,tbl_phone=?,tbl_sex=?,tbl_suoding=?,tbl_guoqi=?,tbl_jihuo=?,tbl_userpic=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,sysUser.getUsername(),sysUser.getFullname(),sysUser.getMobile(),sysUser.getEmail(),sysUser.getPhone(),sysUser.getSex(),sysUser.getLock(),sysUser.getOverdue(),sysUser.getActive(),sysUser.getUserPic(),sysUser.getId()};
			sysUserDao.update(sql, args);
		}
		return id; 
	}

	@Override
	public void doDeleteUsers(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			//删除用户
			sysUserDao.delete(id);
			//删除用户机构，用户岗位，用户角色等中间表记录
			sysUserDao.deleteOrgMiddle(id);
			sysUserDao.deletePositionMiddle(id);
			sysUserDao.deleteRoleMiddle(id);
		}
	}

	@Override
	public void doAddUserOrg(Long userId, String orgIds) {
		String [] ids = orgIds.split(";");
		for (int i = 0; i < ids.length; i++) {
			if(!ids[i].equals("1")){
				List<Map<String,Object>> _tmpList = sysUserDao.select("select * from sys_user_org where tbl_userid = ? and tbl_orgid = ?", new Object[]{userId,ids[i]});
				if(_tmpList.size()==0){
					String insertSql = "insert into sys_user_org (id,tbl_userid,tbl_orgid) values(?,?,?)";
					Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),userId,ids[i]};
					sysUserDao.insert(insertSql,insertArgs);
				}
			}
		}
	}

	@Override
	public void doDeleteUserOrg(Long userOrgId) {
		sysUserDao.delete("sys_user_org", userOrgId);
	}

	@Override
	public void doResetPassword(SysUser sysUser, SysUser user) {
		String sql = "update sys_user set tbl_password=? where id = ?";
		Object[]args = new Object[]{EncryptUtil.encryptSha256(sysUser.getPassword()),sysUser.getId()};
		sysUserDao.update(sql, args);
	}

	@Override
	public void doResetStatus(SysUser sysUser, SysUser user) {
		String sql = "update sys_user set tbl_suoding=?,tbl_jihuo=? where id = ?";
		Object[]args = new Object[]{sysUser.getLock(),sysUser.getActive(),sysUser.getId()};
		sysUserDao.update(sql, args);
	}

	@Override
	public void doAddUserPosition(Long userId, String positionIds) {
		String [] ids = positionIds.split(";");
		for (int i = 0; i < ids.length; i++) {
			if(!ids[i].equals("1")){
				List<Map<String,Object>> _tmpList = sysUserDao.select("select * from sys_user_position where tbl_userid = ? and tbl_positionid = ?", new Object[]{userId,ids[i]});
				if(_tmpList.size()==0){
					String insertSql = "insert into sys_user_position (id,tbl_userid,tbl_positionid) values(?,?,?)";
					Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),userId,ids[i]};
					sysUserDao.insert(insertSql,insertArgs);
				}
			}
		}
	}

	@Override
	public void doDeleteUserPosition(Long userPositionId) {
		sysUserDao.delete("sys_user_position", userPositionId);
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doAddUserRole(Long userId, String roleIds) {
		String [] ids = roleIds.split(";");
		for (int i = 0; i < ids.length; i++) {
			if(!ids[i].equals("1")){
				List<Map<String,Object>> _tmpList = sysUserDao.select("select * from sys_user_role where tbl_userid = ? and tbl_roleid = ?", new Object[]{userId,ids[i]});
				if(_tmpList.size()==0){
					String insertSql = "insert into sys_user_role (id,tbl_userid,tbl_roleid) values(?,?,?)";
					Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),userId,ids[i]};
					sysUserDao.insert(insertSql,insertArgs);
				}
			}
		}
	}

	@Override
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	public void doDeleteUserRole(Long userRoleId) {
		sysUserDao.delete("sys_user_role", userRoleId);
	}

	@Override
	public List<SysUser> getByOrgParam(String cmpIds) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public SysUser getById(Long id) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<SysUser> getByUserIdAndUplow(long startUserId, long nodeUserId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<SysUser> getByIdSet(Set uidSet) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<SysUser> getByUserParam(String cmpIds) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public SysUser getByAccount(String username) {
		ICache cache = (ICache)AppUtil.getBean("iCache");
		SysUser sysUser = (SysUser)cache.getByKey(username);
		if(sysUser == null){
			sysUser = sysUserDao.getByAccount(username);
			cache.add(username, sysUser);
		}
		return sysUser;
	}

	@Override
	public SysUser findUserByUsername(String username, String password) {
		String sql = "select * from sys_user where tbl_username=? and tbl_password=?";
		Object[] params = new Object[] {username, password};
		List<SysUser> sysUserList =  sysUserDao.select(sql, params, new RowMapper<SysUser>() {
			public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysUser sysUser = new SysUser();
				sysUser.setId(rs.getLong("id"));
				sysUser.setUsername(rs.getString("tbl_username"));
				sysUser.setPassword(rs.getString("tbl_password"));
				sysUser.setFullname(rs.getString("tbl_yonghuxingming"));
				return sysUser;
			}
		});
		
		if(sysUserList != null && sysUserList.size()>0) {
			return sysUserList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		ICache cache = (ICache)AppUtil.getBean("iCache");
		SysUser sysUser = (SysUser)cache.getByKey(username);
		if(sysUser == null){
			sysUser = sysUserDao.getByAccount(username);
			cache.add(username, sysUser);
		}
	    if (sysUser == null) {
	      throw new UsernameNotFoundException("用户不存在");
	    }
	    return sysUser;
	}

	@Override
	public void updatePwd(Long id, String password) {
		ICache cache = (ICache)AppUtil.getBean("iCache");
		
	}

	@Override
	public void doChangePassword(Long id, String encPassword) {
		sysUserDao.changePassword(id,encPassword);
		
		SysUser user = sysUserDao.selectById(id, new SysUserRowMapper());
		ICache cache = (ICache)AppUtil.getBean("iCache");
		cache.delByKey(user.getUsername());
	}

}
