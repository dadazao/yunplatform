package com.cloudstong.platform.resource.useinfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.useinfo.dao.UseInfoDao;
import com.cloudstong.platform.resource.useinfo.model.ChartComp;
import com.cloudstong.platform.resource.useinfo.model.UseInfo;
import com.cloudstong.platform.resource.useinfo.service.UseInfoService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:使用信息服务接口实现类
 */
@Repository("useInfoService")
public class UseInfoServiceImpl implements UseInfoService {
	
	@Resource
	private UseInfoDao useInfoDao;

	public UseInfoDao getUseInfoDao() {
		return useInfoDao;
	}

	public void setUseInfoDao(UseInfoDao useInfoDao) {
		this.useInfoDao = useInfoDao;
	}

	@Override
	@CacheEvict(value = "resourceCache", allEntries = true, beforeInvocation = true)
	public void doSaveUseInfo(UseInfo useInfo) {
		useInfoDao.insert(useInfo);
	}

	@Override
	@CacheEvict(value = "resourceCache", allEntries = true, beforeInvocation = true)
	public void doUpdateUseInfo(UseInfo useInfo) {
		useInfoDao.update(useInfo);
	}

	@Override
	@Cacheable(value = "resourceCache")
	public List<ChartComp> stat(Integer number) {
		return useInfoDao.stat(number);
	}

	@Override
	@CacheEvict(value = "resourceCache", allEntries = true, beforeInvocation = true)
	public void doDeleteUseInfo(Long relId,int type) {
		useInfoDao.deleteUseInfo(relId,type);
	}

}
