package com.cloudstong.platform.system.dao;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.system.model.SysPosition;

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
public interface SysPositionDao extends BaseJdbcDao<SysPosition, Long>  {

	public SysPosition getById(Long id);

	public SysPosition getPosByUserId(Long userId);

	/**
	 * Description:删除用户岗位中间表记录
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void deleteMiddleTable(Long id);

}
