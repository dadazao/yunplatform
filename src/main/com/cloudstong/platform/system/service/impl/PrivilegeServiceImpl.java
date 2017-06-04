package com.cloudstong.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.system.dao.IPrivilegeDao;
import com.cloudstong.platform.system.model.FunctionTmp;
import com.cloudstong.platform.system.model.Privilege;
import com.cloudstong.platform.system.service.IPrivilegeService;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:权限管理接口的实现类
 */
@Service("privilegeService")
public class PrivilegeServiceImpl implements IPrivilegeService {

	@Resource
	private IPrivilegeDao privilegeDao;

	public void doSavePrivilege(Privilege privilege){
		privilegeDao.savePrivilege(privilege);
	}
	
	public List<Privilege> queryPrivilege(String plId){
		return privilegeDao.queryPrivilege(plId);
	}
	
	public void doUpdatePrivilege(Privilege privilege){
		privilegeDao.updatePrivilege(privilege);
	}
	
	public void doDeleltePrivilege(String plId){
		privilegeDao.deleltePrivilege(plId);
	}
	
	public IPrivilegeDao getPrivilegeDao() {
		return privilegeDao;
	}

	public void setPrivilegeDao(IPrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}
	
	@Override
	public List<FunctionTmp> queryFunctionTmp() {
		return privilegeDao.queryFunctionTmp();
	}
	
	@Override
	public void doUpdateFunctionTmp(FunctionTmp ft, String psId) {
		privilegeDao.updateFunctionTmp(ft, psId);
	}
	
	@Override
	public void doUpdateFunctionTmp(FunctionTmp ft) {
		privilegeDao.updateFunctionTmp(ft);
	}
	
	public List<Privilege> queryPrivilegeByCatalog(String catalog){
		return privilegeDao.queryPrivilegeByCatalog(catalog);
	}
	
	public int queryPrivilegeNum(String catalogId){
		return privilegeDao.queryPrivilegeNum(catalogId);
	}
	
	public boolean hasDelPrivilege(String bianma){
		return privilegeDao.hasDelPrivilege(bianma);
	}
}
