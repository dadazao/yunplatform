package com.cloudstong.platform.resource.systematom.service;

import java.util.List;

import com.cloudstong.platform.resource.systematom.model.SystemAtom;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统元素服务接口
 */
public interface SystemAtomService {
	/**
	 * Description: 根据目录ID查找系统元素
	 * @param catalogId 目录ID
	 * @return 系统元素
	 */
	public SystemAtom getSystemAtomByCatalogId(String catalogId);

	/**
	 * Description:获得系统元素列表
	 * @return 系统元素列表
	 */
	public List<SystemAtom> getSystemAtom();
	
	/**
	 * Description:获得系统元素列表
	 * @return 系统元素列表
	 */
	public List<SystemAtom> getSystemAtomTree();
}
