package com.cloudstong.platform.resource.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.portal.dao.PortalDao;
import com.cloudstong.platform.resource.portal.model.Portal;
import com.cloudstong.platform.resource.portal.service.PortalService;
import com.cloudstong.platform.resource.portal.service.PortletService;
import com.cloudstong.platform.system.model.SysUser;
/**
 * 
 * @author Jason
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 门户业务接口实现类
 */
@Repository("portalService")
public class PortalServiceImpl implements PortalService {
	/**
	 * 门户数据操作接口类对象
	 */
	@Resource
	private PortalDao portalDao;
	/**
	 * 门户组件数据操作接口类对象
	 */
	@Resource
	private PortletService portletService;

	/**
	 * Getters and Setters
	 */
	public PortalDao getPortalDao() {
		return portalDao;
	}

	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}

	public PortletService getPortletService() {
		return portletService;
	}

	public void setPortletService(PortletService portletService) {
		this.portletService = portletService;
	}

	/************************
	 * 以下为实现接口的方法
	 ***********************/

	@Override
	public Portal findPortalByPortalId(SysUser user, String portalId) throws Exception {
		// 首先需要查询获取到portal的基本信息
		Portal portal = portalDao.findPortalById(portalId);
		this.wrapPortal(user, portal);
		return portal;
	}

	@Override
	public Portal doDefaultPortal(SysUser user) throws Exception {
		Portal portal = portalDao.findDefaultPortal(user);
		this.wrapPortal(user, portal);
		return portal;
	}

	/**
	 * 
	 * Description: 
	 * 包装portal，为其设置portlet等信息 Steps:
	 * 
	 * @param user
	 * @param portal
	 * @throws Exception
	 */
	private void wrapPortal(SysUser user, final Portal portal) throws Exception {
		// 然后需要获取到其关联的portlet,首先需要根据portlet的id
		List<Long> portletIdList = portalDao.findPortletIdListByPortalId(portal.getId());
		// 然后根据获取的portlet id 查询出portlet列表
		if (portletIdList.size() > 0) {
			Long[] listArray = new Long[portletIdList.size()];
			portletIdList.toArray(listArray);
			portal.setPortletList(portletService.findPortletByIds(user, listArray));
		}
	}

	@Override
	public Map<String, String> findPortalIds(SysUser user) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> leftIds = portalDao.findCurrentPortletIdListByParams("tbl_belongarea:left_side", user);
		List<String> middleIds = portalDao.findCurrentPortletIdListByParams("tbl_belongarea:middle_side", user);
		List<String> rightIds = portalDao.findCurrentPortletIdListByParams("tbl_belongarea:right_side", user);
		String left = "";
		for (String id : leftIds) {
			left += id + ";";
		}
		map.put("leftIDs", left);

		String middle = "";
		for (String id : middleIds) {
			middle += id + ";";
		}
		map.put("middleIDs", middle);

		String right = "";
		for (String id : rightIds) {
			right += id + ";";
		}
		map.put("rightIDs", right);

		return map;
	}
}
