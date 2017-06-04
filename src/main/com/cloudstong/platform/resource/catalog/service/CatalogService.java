package com.cloudstong.platform.resource.catalog.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.resource.catalog.model.Catalog;

/**
 * @author Allan
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:目录服务接口
 */
public interface CatalogService {
	/**
	 * 保存目录信息到数据库
	 * 
	 * @param catalog 目录信息
	 */
	public void doSaveCatalog(Catalog catalog);

	/**
	 * 根据目录ID删除目录信息
	 * 
	 * @param id 目录ID
	 */
	public void doDeleteCatalog(Long id);

	/**
	 * 更新目录信息
	 * 
	 * @param catalog 目录信息
	 */
	public void doUpdateCatalog(Catalog catalog);

	/**
	 * 根据目录ID查询目录信息
	 * 
	 * @param id 目录ID
	 * @return 目录信息
	 */
	public Catalog findCatalogById(Long id);

	/**
	 * 根据查询条件查询目录信息
	 * @param qc 查询条件
	 * @return 目录信息集合
	 */
	public List<Catalog> queryCatalog(QueryCriteria qc);

	/**
	 * 统计目录信息数量
	 * @return 目录信息数量
	 */
	public int countCatalog();

	/**
	 * 删除目录信息
	 * @param selectedIDs 目录ID数组
	 */
	public void doDeleteCatalogs(Long[] SelectedIDs);

	/**
	 * 得到目录树的HTML代码
	 * @param rootId 根目录ID
	 * @param isOnclick 是否有click事件
	 * @param showRoot 是否显示根节点
	 * @return
	 */
	public String getTreeCatalog(String rootId, boolean isOnclick,
			boolean showRoot);

	/**
	 * 得到所有的目录集合
	 * @return 目录集合
	 */
	public List<Catalog> getSysCatalog();
	
	/**
	 * 得到所有的目录集合
	 * @return 目录集合
	 */
	public List<Catalog> getSysCatalogTree();

	/**
	 * 更新目录关联的列表
	 * @param currentSaveId 要更新的目录ID
	 * @param listId 列表ID
	 */
	public void doUpdateCatalogList(Long currentSaveId, Long listId);

	/**
	 * 根据列表ID查找目录
	 * @param tabulationId 列表ID
	 * @return 目录
	 */
	@Cacheable(value="resourceCache",key="'findCatalogByListId:'+#tabulationId")
	public Catalog findCatalogByListId(Long tabulationId);

	/**
	 * 查找列表已经被几个目录关联
	 * @param listid 列表ID
	 * @param id 目录ID
	 * @return 此列表被关联的数量
	 */
	public List useListCount(String listid, Long id);
	
	/**
	 * 更新目录序号
	 * @param catalog 
	 */
	public void doUpdateOrder(Catalog catalog);

	/**
	 * 
	 * 生成目录下所有相关的数据和生成表的sql语句
	 * 
	 * @param catalogId
	 * @param formId
	 * @param model
	 * @return
	 */
	public String buildAllCatalogSql(String catalogId);
	
	/**
	 * 
	 * Description:执行sql
	 * 
	 * Steps:
	 * 
	 * @param sql
	 */
	public void doExecuteSqlFile(String path) throws AppException;

	/**
	 * 
	 * Description:该模块下所有数据，包括表单、列表、数据表<br><br>和字段都将清除
	 * 
	 * 
	 * @param id
	 */
	public void doDeleteAllCatalog(Long id);

	/**
	 * 根据表单ID查找目录
	 * @param id 表单ID
	 * @return 目录
	 */
	public Catalog findCatalogByFormId(Long id);
}
