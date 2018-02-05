package com.cloudstong.platform.resource.catalog.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.catalog.model.Catalog;

/**
 * @author Allan
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:目录操作数据库接口
 */
public interface CatalogDao {

	/**
	 * 查询目录信息
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 目录信息集合
	 */
	@Cacheable(value = "resourceCache")
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * 更新目录信息
	 * @param u 目录
	 */
	public void update(Catalog u);

	/**
	 * 插入目录信息
	 * @param u 目录
	 */
	public void insert(Catalog u);

	/**
	 * 根据ID查找目录信息
	 * @param id 目录ID
	 * @return 目录
	 */
	@Cacheable(value = "resourceCache", key = "'Catalog_selectById:'+#id")
	public Catalog selectById(Long id);

	/**
	 * 根据ID删除目录信息
	 * @param id 目录ID
	 */
	public void delete(Long id);

	/**
	 * 统计目录信息数量
	 * @return 目录信息数量
	 */
	public int count();

	/**
	 * 判断此目录下是否有子目录
	 * @param id 目录ID
	 * @return 是否有子目录
	 */
	public boolean isHasNode(Long id);

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
	
	public Catalog findCatalogByListId(Long tabulationId);

	/**
	 * 查找列表已经被几个目录关联
	 * @param listid 列表ID
	 * @param id 目录ID
	 * @return 此列表被关联的数量
	 */
	public List useListCount(String listid, Long id);

	/**
	 * Description: 更新目录的排序
	 * @param catalog 目录
	 */
	public void updateOrder(Catalog catalog);

	/**
	 * 
	 * Description:执行sql语句
	 * 
	 * Steps:
	 * 
	 * @param sql
	 */
	public void executeSql(String sql);

	/**
	 * 根据表单ID查找目录
	 * @param id 表单ID
	 * @return 目录
	 */
	public Catalog getCatalogByFormId(Long id);
	
	/**
	 * 根据表单ID查找目录,servlet中使用
	 * @param id 表单ID
	 * @return 目录
	 */
	public Catalog findCatalogByFormId(Long id);

	public void updateAuth(Long catalogId,Integer isAuth);
	
	public String getMaintable(Long id);
}
