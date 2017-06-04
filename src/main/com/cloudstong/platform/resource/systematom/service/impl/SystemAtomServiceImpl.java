package com.cloudstong.platform.resource.systematom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.systematom.dao.SystemAtomDao;
import com.cloudstong.platform.resource.systematom.model.SystemAtom;
import com.cloudstong.platform.resource.systematom.service.SystemAtomService;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统元素服务接口实现类
 */
@Repository("systemAtomService")
public class SystemAtomServiceImpl implements SystemAtomService {

	@Resource
	private SystemAtomDao systemAtomDao;

	public SystemAtomDao getSystemAtomDao() {
		return systemAtomDao;
	}

	public void setSystemAtomDao(SystemAtomDao systemAtomDao) {
		this.systemAtomDao = systemAtomDao;
	}

	@Override
	public SystemAtom getSystemAtomByCatalogId(String catalogId) {
		return systemAtomDao.getSystemAtomByCatalogId(catalogId);
	}

	public List<SystemAtom> getSystemAtom(){
		return this.getSystemAtomTree();
	}
	
	public List<SystemAtom> getSystemAtomTree(){
		return systemAtomDao.getSystemAtomTree();
	}
}
