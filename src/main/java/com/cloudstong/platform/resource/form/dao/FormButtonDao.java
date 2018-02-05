package com.cloudstong.platform.resource.form.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.form.model.FormButton;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单按钮操作数据库接口
 */
public interface FormButtonDao {
	/**
	 * Description:修改表单按钮
	 * @param formButton 表单按钮
	 */
	public void updateFormButton(FormButton formButton);

	/**
	 * Description:向数据库中插入一个表单按钮
	 * @param formButton 表单按钮
	 * @return 表单按钮ID
	 */
	public Long insertFormButton(FormButton formButton);

	/**
	 * Description:查询表单按钮的集合
	 * @param sql sql语句
	 * @param array 参数值
	 * @param currentIndex 当前页
	 * @param pageSize 每页记录数
	 * @return
	 */
	@Cacheable(value = "formCache")
	public List selectFormButton(String sql, Object[] array, int currentIndex,
			int pageSize);

	/**
	 * Description:统计表单按钮的数量
	 * @param sql sql语句
	 * @param array 参数值
	 * @return 表单按钮数量
	 */
	@Cacheable(value = "formCache")
	public int countFormButton(String sql, Object[] array);

	/**
	 * Description:根据表单按钮ID查找表单按钮
	 * @param id 表单按钮ID
	 * @return 表单按钮
	 */
	@Cacheable(value = "formCache", key = "'selectFormButtonById:'+#id")
	public FormButton selectFormButtonById(Long id);

	/**
	 * Description:根据表单按钮ID删除表单按钮
	 * @param id 表单按钮ID
	 */
	public void deleteFormButton(Long id);

	/**
	 * Description:根据表单ID查询表单按钮的集合
	 * @param formId 表单ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value = "formCache", key = "'selectByFormId:'+#formId")
	public List<FormButton> selectByFormId(Long formId);

	/**
	 * Description:根据表单ID和选项卡ID查询表单按钮的集合
	 * @param formId 表单ID
	 * @param tabId 选项卡ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value = "formCache", key = "'selectByFormId:'+#formId+#tabId")
	public List<FormButton> selectByFormId(Long formId, Long tabId);

	/**
	 * Description:根据选项卡ID查询表单按钮的集合
	 * @param tabId 选项卡ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value = "formCache", key = "'selectByTabId:'+#tabId")
	public List<FormButton> selectByTabId(Long tabId);

	/**
	 * Description:根据选项卡ID和分区ID查找表单按钮的集合
	 * @param tabId 选项卡ID
	 * @param partitionId 分区ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value = "formCache", key = "'selectByTabAndPid:'+#tabId+#partitionId")
	public List<FormButton> selectByTabAndPid(Long tabId, Long partitionId);

	/**
	 * Description:根据分区ID查找表单按钮的集合
	 * @param partitionId 分区ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value = "formCache", key = "'FormButton_selectByPid:'+#partitionId")
	public List<FormButton> selectByPid(Long partitionId);

	/**
	 * Description:查找表单按钮是否被重复添加，若重复添加，返回的List长度大于0
	 * @param formButton 表单按钮
	 * @return List集合
	 */
	public List findReplyResult(FormButton formButton);
	
	/**
	 * Description:更新表单按钮显示顺序
	 * @param formButton 表单按钮
	 */
	public void updateOrder(FormButton formButton);
}
