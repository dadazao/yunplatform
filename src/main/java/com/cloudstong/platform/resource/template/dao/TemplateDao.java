package com.cloudstong.platform.resource.template.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.template.model.SysTemplate;
import com.cloudstong.platform.resource.template.model.Template;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:模板管理数据库接口
 */
@SuppressWarnings("rawtypes")
public interface TemplateDao {
	/**
	 * Description:利用Id查找表单模板
	 * 
	 * Steps:
	 * 
	 * @param id 
	 * 				表单模板ID
	 * @return
	 */
	@Cacheable(value = "formCache", key = "'template_sequeryTemplateById:'+#id")
	public SysTemplate queryTemplateById(Long id);
	/**
	 * Description:保存或修改表单模板
	 * 
	 * Steps:
	 * 
	 * @param SysTemplate 
	 * 				表单模板对象
	 * @return
	 */
	@CacheEvict(value={"formCache"},allEntries=true,beforeInvocation=true)
	public Long saveOrUpdateTemplate(SysTemplate sysTemplate);
	/**
	 * Description:根据条件查询模板
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria 
	 * 				查询条件
	 * @return
	 */
	@Cacheable(value = "formCache")
	public List select(String sql, Object[] params, int startRow, int rowsCount);

	/**
	 * Description:根据模板ID查询模板
	 * 
	 * Steps:
	 * 
	 * @param id 
	 * 				模板ID
	 * @return 模板对象
	 */
	@Cacheable(value = "formCache", key = "'template_selectById:'+#id")
	public Template selectById(Long id);
	
	/**
	 * Description:从表单模板中复制模板到模板库
	 * 
	 * Steps:
	 * 
	 * @param template 表单模板对象
	 */
	public void insertLibrary(Template template);
	
	/**
	 * Description:从模板库获得所有模板
	 * 
	 * Steps:
	 * 
	 * @return 模板集合
	 */
	public List<Template> selectLibary();
	
	/**
	 * 删除模板
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	@CacheEvict(value={"formCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id);
}
