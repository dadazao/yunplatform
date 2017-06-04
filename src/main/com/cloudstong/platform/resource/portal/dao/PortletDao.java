package com.cloudstong.platform.resource.portal.dao;

import java.util.List;

import com.cloudstong.platform.resource.portal.model.Portlet;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
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
 * 门户组件数据操作接口
 */
public interface PortletDao {

	/**
	 * 根据portlet id 查询portlet
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Portlet> findPortletListByIds(String... ids) throws Exception;

	/**
	 * 根据portlet id 查询Portlet
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Portlet findPortletById(Long id) throws Exception;

}
