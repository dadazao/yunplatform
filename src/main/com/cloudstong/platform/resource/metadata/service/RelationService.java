package com.cloudstong.platform.resource.metadata.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.model.Relation;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表关系服务接口
 */
public interface RelationService {
	/**
	 * Description:根据主表ID和子表ID查询表关系
	 * @param mainId 主表ID
	 * @param subId 子表ID
	 * @return 表关系
	 */
	public Relation selectByMainIdAndSubId(Long mainId, Long subId);

	/**
	 * Description:根据查询条件查询表关系
	 * @param qc 查询条件
	 * @return 表关系集合
	 */
	public List<Relation> queryRelation(QueryCriteria qc);

	/**
	 * Description:统计表关系
	 * @return 表关系数量
	 */
	public int countRelation();

	/**
	 * Description:保存表关系
	 * @param relation 表关系
	 * @return 表关系ID
	 */
	public Long doSaveRelation(Relation relation);

	/**
	 * Description:更新表关系
	 * @param relation 表关系
	 */
	public void doUpdateRelation(Relation relation);

	/**
	 * Description:删除表关系
	 * @param selectedIDs 表关系ID数组
	 */
	public void doDeleteRelations(Long[] selectedIDs);

	/**
	 * Description:根据表关系ID查找表关系
	 * @param id 表关系ID
	 * @return 表关系
	 */
	public Relation queryById(Long id);

	/**
	 * Description:查找表关系是否重复,若返回的List大于0，则重复
	 * @param relation 表关系
	 * @return List
	 */
	public List findReplyRelation(Relation relation);

	/**
	 * Description:根据主表名称和子表名称查找表关系
	 * @param mainTable 主表名称
	 * @param subTable 子表名称
	 * @return 表关系
	 */
	public Relation selectByMainNameAndSubName(String mainTable, String subTable);

	/**
	 * Description:删除表关系
	 * @param selectedIDs 表关系ID数组
	 */
	public void doLogicDeleteRelations(Long[] selectedIDs);

	/**
	 * Description:判断外键关系是否已经建立
	 * 
	 * Steps:
	 * 
	 * @param relation
	 * @return
	 */
	public boolean checkExistForeignKey(Relation relation);
}
