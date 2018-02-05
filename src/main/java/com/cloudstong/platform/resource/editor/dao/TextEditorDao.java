package com.cloudstong.platform.resource.editor.dao;

import java.util.List;

import com.cloudstong.platform.resource.editor.model.TextEditor;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本编辑器操作数据库接口
 */
public interface TextEditorDao {
	/**
	 * 查询所有发布的文本编辑器
	 * @return 文本编辑器j集合
	 * @throws Exception
	 */
	public List<TextEditor> selectAllTextEditor();

	/**
	 * 根据文本编辑器ID查找文本编辑器
	 * @param editorId 文本编辑器ID
	 * @return
	 */
	public TextEditor findTextEditorById(Long editorId);
}
