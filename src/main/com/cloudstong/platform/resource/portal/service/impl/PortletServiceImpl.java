package com.cloudstong.platform.resource.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.portal.dao.PortletDao;
import com.cloudstong.platform.resource.portal.model.Portlet;
import com.cloudstong.platform.resource.portal.service.PortletService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
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
 * portlet业务逻辑实现类
 */
@Repository("portletService")
public class PortletServiceImpl implements PortletService {

	@Resource
	private PortletDao portletDao;

	@Resource
	private TabulationService tabulationService;

	public PortletDao getPortletDao() {
		return portletDao;
	}

	public void setPortletDao(PortletDao portletDao) {
		this.portletDao = portletDao;
	}

	/**
	 * 根据portlet id 查找portlet
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Portlet> findPortletByIds(SysUser user, Long[]ids)
			throws Exception {
		// 对于每一个portlet id,查找到相应的Portlet，然后获取其中的列表id
		List<Portlet> portletList = new ArrayList<Portlet>();
		for (Long id : ids) {
			Portlet portlet = portletDao.findPortletById(id);
			if (portlet != null) {
				if(portlet.getListId()!=null && !"-1".equals(portlet.getListId())){
					try {
						QueryCriteria qc = new QueryCriteria();
						qc.setCurrentIndex(1);
						qc.setCurrentPage(1);
						qc.setPageSize(portlet.getRecordCount());
						Tabulation tabulation = tabulationService.selectNoPageTabulationByListId(portlet.getListId(), qc, user);
						portlet.setDomainList(tabulation.getDomains());
						//设置该portle对应的formid
						portlet.setFormId(tabulation.getFormId());
						portlet.setTabulation(tabulation);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				portletList.add(portlet);
			}
		}
		return portletList;
	}
}
