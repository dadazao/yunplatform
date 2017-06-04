package com.cloudstong.platform.resource.tree.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.template.model.TreeShowForm;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;

/**
 * @author Allan
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:树模板服务接口
 */
public interface MgrTreeService {

	/**
	 * Description:获取展示树的参数
	 * @param tableId 表属性ID
	 * @param columnId 字段ID
	 * @param disColumnId 显示字段ID
	 * @param orderColumnId 排序字段ID
	 * @param id 树模板ID
	 * @return 显示树表单
	 */
	public TreeShowForm doSearchTreeParam(Long tableId, Long columnId,
			Long disColumnId, Long orderColumnId, Long id);

	/**
	 * Description:保存树模板到数据库
	 * @param mgrTree 模板树
	 * @return 模板树ID
	 */
	public Long doSaveMgrTree(MgrTree mgrTree);

	/**
	 * Description:根据Id删除树模板
	 * @param id 树模板ID
	 */
	public void doDeleteMgrTree(Long id);

	/**
	 * Description:更新树模板
	 * @param mgrTree 树模板
	 */
	public void doUpdateMgrTree(MgrTree mgrTree);

	/**
	 * Description:根据Id查询树模板
	 * @param id 树模板ID
	 * @return 树模板
	 */
	public MgrTree findMgrTreeById(Long id);

	/**
	 * Description:根据查询条件查询树模板
	 * @param qc 查询条件
	 * @return 树模板集合
	 */
	public List<MgrTree> queryMgrTree(QueryCriteria qc);

	/**
	 * Description:统计树模板
	 * @return 树模板总记录数
	 */
	public int countMgrTree();

	/**
	 * Description:删除树模板
	 * @param selectedIDs 树模板ID数组
	 */
	public void doDeleteMgrTrees(Long[] selectedIDs);

	/**
	 * Description:根据表属性ID查询表里所有字段
	 * @param id 表属性ID
	 * @return 表字段集合
	 */
	public List getColumnAll(Long id);

	public List<Map> queryTreeByParam(String belongTable, String orderColumn, String orderType);
	
	public List<Catalog> getCommentById(Long catalogId);
	
	public String getValueByParam(String belongTable, String columntName, Long id);
	
	public CommonTree findCommonTreeByMgrTree(MgrTree mgrTree, Long id);
}
