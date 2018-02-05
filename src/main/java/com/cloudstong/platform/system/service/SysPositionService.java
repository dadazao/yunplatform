package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysUser;

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
public interface SysPositionService {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryPosition(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	List<SysPosition> selectAllPostion();

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param position
	 * @return
	 */
	Long doSavePosition(SysPosition position,SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param positionId
	 * @return
	 */
	SysPosition findPositionById(Long positionId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	void doDeletePositions(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryUserPosition(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userIds
	 * @param positionId
	 */
	void doAddUser(String userIds, Long positionId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedSubIDs
	 */
	void doDeleteUser(Long[] selectedSubIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userPositionId
	 */
	void doShezhiZhugang(Long userPositionId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 * @return
	 */
	List findSubPositions(Long[] selectedIDs);

}
