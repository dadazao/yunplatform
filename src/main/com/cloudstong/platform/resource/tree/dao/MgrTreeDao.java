package com.cloudstong.platform.resource.tree.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.template.vo.TemplateColumn;
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
 * Description:树模板操作数据库接口
 */
public interface MgrTreeDao {

	/**
	 * Description:根据表属性ID查找表
	 * @param tableId 表属性ID
	 * @return 表属性
	 */
	public Table getTable(Long tableId);

	/**
	 * Description:根据字段ID查找字段
	 * @param columnId 字段ID
	 * @return 字段
	 */
	public Column getColumn(Long columnId);

	/**
	 * Description:根据表名和字段名获得根节点ID
	 * @param tableName 表名
	 * @param colName 字段名
	 * @return 根节点ID
	 */
	public Long getRootId(String tableName, String colName);

	/**
	 * Description:查询树模板
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 树模板集合
	 */
	@Cacheable(value = "resourceCache")
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * Description:更新树模板
	 * @param templateTree 树模板
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(MgrTree u);

	/**
	 * Description:保存树模板到数据库
	 * @param templateTree 模板树
	 * @return 模板树ID
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public Long insert(MgrTree u);

	/**
	 * Description:根据Id查询树模板
	 * @param id 树模板ID
	 * @return 树模板
	 */
	@Cacheable(value = "resourceCache", key = "'MgrTree_selectById:'+#id")
	public MgrTree selectById(Long id);

	/**
	 * Description:根据Id删除树模板
	 * @param id 树模板ID
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id);

	/**
	 * Description:统计树模板
	 * @return 树模板总记录数
	 */
	public int count();

	/**
	 * Description:根据表属性ID查询表里所有字段
	 * @param id 表属性ID
	 * @return 表字段集合
	 */
	public List<TemplateColumn> selectColumnAll(Long id);

	/**
	 * Description:根据表名，显示字段名，节点ID获得节点名称
	 * @param tableName 表名
	 * @param disColName 显示字段名
	 * @param rootId 节点ID
	 * @return 节点名称
	 */
	public String getRootName(String tableName, String disColName, Long rootId);
	
	@Cacheable(value = "resourceCache", key = "'MgrTree_queryTreeByParam:'+#belongTable+#orderColumn+#orderType")
	public List<Map> queryTreeByParam(String belongTable, String orderColumn, String orderType);
	
	public List<Catalog> getCommentById(Long catalogId);

	public String getValueByParam(String belongTable, String columntName, Long id);
	
	/**
	 * Description:根据Id和树管理信息返回通用树模型
	 * 
	 * @param mgrTree
	 * @param id
	 * @return
	 */
	@Cacheable(value = "resourceCache", key = "'MgrTree_findCommonTree:'+#mgrTree.id+#id")
	public CommonTree findCommonTree(MgrTree mgrTree, Long id);
	
	/**
	 * Description:根据上一级节点id查找所有的上级父节点名称
	 * @param value 上一级节点id
	 * @return
	 */
	@Cacheable(value = "resourceCache", key = "'MgrTree_findParentName:'+#pid")
	public String findParentName(Long pid) ;
}
