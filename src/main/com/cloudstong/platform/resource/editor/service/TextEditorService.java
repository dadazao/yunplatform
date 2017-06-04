package com.cloudstong.platform.resource.editor.service;

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
 * Description:文本编辑器服务接口
 */
public interface TextEditorService {
	/**
	 * 查询所有发布的文本编辑器
	 * @return 文本编辑器j集合
	 * @throws Exception
	 */
	public List<TextEditor> findAllTextEditor() throws Exception;
}
