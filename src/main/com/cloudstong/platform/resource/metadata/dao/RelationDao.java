package com.cloudstong.platform.resource.metadata.dao;

import java.util.List;

import com.cloudstong.platform.resource.metadata.model.Relation;

public interface RelationDao {

	public Relation selectByMainIdAndSubId(Long mainId, Long subId);

	public Relation selectByMainNameAndSubName(String mainName, String subName);

	public List<Relation> select(String sql, Object[] args, int startRow, int rowsCount);

	public int count();

	public Long doSave(Relation relation);

	public void doUpdate(Relation relation);

	public void delete(Long id);

	public Relation selectById(Long id);

	public List findReplyRelation(Relation relation);

	public void logicDelete(Long id);

	/**
	 * Description: 检查subTableName中是否含有名foreignKey表示的字段，有返回true,否则返回false
	 * 
	 * Steps:
	 * 
	 * @param subTableName
	 *            子表名
	 * @param foreignKey
	 *            字段名
	 * @return
	 */
	public boolean findForeignKey(String subTableName, String foreignKey);

	public Long createForeignKey(Relation relation);

	public void updateForeignKey(Relation relation);
}
