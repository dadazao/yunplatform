package com.cloudstong.platform.resource.pagemanage.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.pagemanage.dao.PageManageDao;
import com.cloudstong.platform.resource.pagemanage.model.PageManage;
import com.cloudstong.platform.resource.pagemanage.service.PageManageService;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分页构件服务接口实现类
 */
@Repository("pageManageService")
public class PageManageServiceImpl implements PageManageService {

	@Resource
	private PageManageDao pageManageDao;

	public PageManageDao getPageManageDao() {
		return pageManageDao;
	}

	public void setPageManageDao(PageManageDao pageManageDao) {
		this.pageManageDao = pageManageDao;
	}

	@Override
	public PageManage findPageManageById(String pageId) {
		return this.pageManageDao.findPageManageById(pageId);
	}

}
