package com.cloudstong.platform.resource.editor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.editor.dao.TextEditorDao;
import com.cloudstong.platform.resource.editor.model.TextEditor;
import com.cloudstong.platform.resource.editor.service.TextEditorService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本编辑器服务接口实现类
 */
@Repository("textEditorService")
public class TextEditorServiceImpl implements TextEditorService {

	@Resource
	private TextEditorDao textEditorDao;

	public TextEditorDao getTextEditorDao() {
		return textEditorDao;
	}

	public void setTextEditorDao(TextEditorDao textEditorDao) {
		this.textEditorDao = textEditorDao;
	}

	@Override
	public List<TextEditor> findAllTextEditor() throws Exception {
		return textEditorDao.selectAllTextEditor();
	}

}
