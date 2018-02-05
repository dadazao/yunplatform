package com.cloudstong.platform.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysPositionDao;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserPosition;
import com.cloudstong.platform.system.service.SysPositionService;

/**
 * @author liuqi
 * Created on 2014-8-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
@Repository("positionService")
public class SysPositionServiceImpl implements SysPositionService {
	
	@Resource
	private SysPositionDao positionDao;

	@Override
	public PageResult queryPosition(QueryCriteria queryCriteria) {
		return positionDao.query("select * from sys_position where id != 1",queryCriteria,new SysPositionRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysPosition> selectAllPostion() {
		return positionDao.getAll(new SysPositionRowMapper());
	}

	@Override
	public Long doSavePosition(SysPosition position,SysUser user) {
		Long id = -1L;
		Date now = new Date(System.currentTimeMillis());
		if(position.getId()==null||position.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_position (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_positionname,tbl_positionno,tbl_xinzidengji,tbl_comment,tbl_parentid,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,position.getPositionName(),position.getPositionNo(),position.getXinzidengji(),position.getComment(),position.getParentId(),position.getOptCounter()};
			positionDao.insert(sql, args);
			id = uuid;
		}else{
			id = position.getId();
			String sql = "update sys_position set comm_updateBy=?,comm_updateDate=?,tbl_positionname=?,tbl_positionno=?,tbl_xinzidengji=?,tbl_comment=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,position.getPositionName(),position.getPositionNo(),position.getXinzidengji(),position.getComment(),position.getId()};
			positionDao.update(sql, args);
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysPosition findPositionById(Long positionId) {
		SysPosition position = positionDao.selectById(positionId, new SysPositionRowMapper());
		return position;
	}

	@Override
	public void doDeletePositions(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			//删除岗位
			positionDao.delete(id);
			//删除用户岗位中间表的记录
			positionDao.deleteMiddleTable(id);
		}
	}

	@Override
	public PageResult queryUserPosition(QueryCriteria queryCriteria) {
		String sql = "select up.*,u.tbl_yonghuxingming as fullname,p.tbl_positionname as positionname  from sys_user_position up left join sys_user u on up.tbl_userid=u.id left join sys_position p on up.tbl_positionid=p.id where 1=1 ";
		return positionDao.query(sql,queryCriteria,new SysUserPositionRowMapper());
	}

	@Override
	public void doAddUser(String userIds, Long positionId) {
		if(userIds!=null && !userIds.equals("") && positionId!=null && !positionId.equals("")){
			String []uids = userIds.split(";");
			for(int i=0;i<uids.length;i++){
				String sql = "select * from sys_user_position where tbl_userid = ? and tbl_positionid = ?";
				Object[]args = new Object[]{uids[i],positionId};
				List<Map<String,Object>> _list =  positionDao.select(sql, args);
				if(_list.size()==0){
					String insertSql = "insert into sys_user_position (id,tbl_userid,tbl_positionid) values(?,?,?)";
					Object[]insertArgs = new Object[]{UniqueIdUtil.genId(),uids[i],positionId};
					positionDao.insert(insertSql,insertArgs);
				}
			}
		}
	}

	@Override
	public void doDeleteUser(Long[] selectedSubIDs) {
		for(int i=0;i<selectedSubIDs.length;i++){
			positionDao.delete("sys_user_position",selectedSubIDs[i]);
		}
	}

	@Override
	public void doShezhiZhugang(Long userPositionId) {
		try {
			String sql = "update sys_user_position set tbl_zhugangwei = 1 where id = ?";
			Object[]args = new Object[]{userPositionId};
			positionDao.update(sql,args);
			
			List<Map<String,Object>> _list = positionDao.select("select * from sys_user_position where id = ?", new Object[]{userPositionId});
			if(_list.size()>0){
				String usql = "update sys_user_position set tbl_zhugangwei = 0 where tbl_userid = ? and id != ?";
				Object[]uargs = new Object[]{_list.get(0).get("tbl_userid"),userPositionId};
				positionDao.update(usql,uargs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	class SysPositionRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysPosition sysPosition = new SysPosition();
			sysPosition.setId(rs.getLong("id"));
			sysPosition.setPositionName(rs.getString("tbl_positionname"));
			sysPosition.setPositionNo(rs.getString("tbl_positionno"));
			sysPosition.setXinzidengji(rs.getString("tbl_xinzidengji"));
			sysPosition.setComment(rs.getString("tbl_comment"));
			sysPosition.setParentId(rs.getLong("tbl_parentid"));
			sysPosition.setCreateBy(rs.getLong("comm_createBy"));
			sysPosition.setCreateDate(rs.getTimestamp("comm_createDate"));
			sysPosition.setUpdateBy(rs.getLong("comm_updateBy"));
			sysPosition.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			sysPosition.setOptCounter(rs.getLong("comm_opt_counter"));
			sysPosition.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return sysPosition;
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
	class SysUserPositionRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysUserPosition sysUserPosition = new SysUserPosition();
			sysUserPosition.setId(rs.getLong("id"));
			sysUserPosition.setZhugangwei(rs.getBoolean("tbl_zhugangwei"));

			SysUser sysUser = new SysUser();
			sysUser.setId(rs.getLong("tbl_userid"));
			sysUser.setFullname(rs.getString("fullname"));
			
			SysPosition sysPosition = new SysPosition();
			sysPosition.setId(rs.getLong("tbl_positionid"));
			sysPosition.setPositionName(rs.getString("positionname"));
			
			sysUserPosition.setSysUser(sysUser);
			sysUserPosition.setSysPosition(sysPosition);
			return sysUserPosition;
		}
	}

	@Override
	public List findSubPositions(Long[] selectedIDs) {
		StringBuffer sql = new StringBuffer("select * from sys_position where tbl_parentid in(");
		for(int i=0;i<selectedIDs.length;i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
		}
		sql.append(")");
		return positionDao.select(sql.toString(), selectedIDs);
	}
}
