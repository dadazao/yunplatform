package com.cloudstong.platform.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.system.dao.IUserDao;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:用户管理接口的实现类
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserDao userDao;

	public SysUser getUser(String username, String password, boolean pbSuperUser) {
		return userDao.getUser(username, password, pbSuperUser);
	}

	public boolean username(String psUsername) {
		return userDao.username(psUsername);
	}

	@Override
	public void doChangePassword(Long id, String password) {
		userDao.changePassword(id, password);
	}

	@Override
	public Org getOrgById(String psOrgId) {
		return userDao.getOrgById(psOrgId);
	}
	
	public void doDeleteUserRole(String plUserId, String plRoleId){
		userDao.doDeleteUserRole(plUserId, plRoleId);
	}

	@Override
	public Object getUserResource(String psColumn, String psValue, int piFlag) {
		return userDao.getUserResource(psColumn, psValue, piFlag);
	}

	@Override
	public Person findPersonById(String personId) {
		return userDao.selectPersonById(personId);
	}

	@Override
	public List<Person> selectPersonNameByIds(String ids) {
		return userDao.selectPersonNameByIds(ids);
	}

	@Override
	public List<SysUser> selectUserNameByIds(String ids) {
		return userDao.selectUserNameByIds(ids);
	}

	@Override
	public void addOrUpdateUserSeq(HttpSession session, Seqcode seqcode){
		try {
			SysUser user = (SysUser) session.getAttribute("user");
//			Map<String, Seqcode> curUserSeqcode = user.getCurUserSeqcode();
//			curUserSeqcode.put(seqcode.getTblValue(), seqcode);
			session.setAttribute("user", user);
		} catch (Exception e) {
		}
	}

	@Override
	public SysUser getUser(String username) {
		return userDao.getUser(username);
	}
	
	
}
