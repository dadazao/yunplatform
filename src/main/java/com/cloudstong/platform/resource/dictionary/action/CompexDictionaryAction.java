package com.cloudstong.platform.resource.dictionary.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:代码Action
 */
public class CompexDictionaryAction extends CompexDomainAction {

	private static final long serialVersionUID = -6145941366262062800L;

	Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 一级目录选择的代码分类的ID
	 */
	private Long levelFirst = -1L;

	/**
	 * 二级目录选择的代码分类的ID
	 */
	private Long levelSecond = -1L;

	/**
	 * 父代码名称
	 */
	private String parentName;

	/**
	 * 父代码ID
	 */
	private String belong;

	/**
	 * 一级代码集合
	 */
	private List<Dictionary> firstDictionarys;
	/**
	 * 二级代码集合
	 */
	private List<Dictionary> secondDictionarys;

	/**
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dictionaryService;

	public Long getLevelFirst() {
		return levelFirst;
	}

	public void setLevelFirst(Long levelFirst) {
		this.levelFirst = levelFirst;
	}

	public Long getLevelSecond() {
		return levelSecond;
	}

	public void setLevelSecond(Long levelSecond) {
		this.levelSecond = levelSecond;
	}

	public List<Dictionary> getFirstDictionarys() {
		return firstDictionarys;
	}

	public void setFirstDictionarys(List<Dictionary> firstDictionarys) {
		this.firstDictionarys = firstDictionarys;
	}

	public List<Dictionary> getSecondDictionarys() {
		return secondDictionarys;
	}

	public void setSecondDictionarys(List<Dictionary> secondDictionarys) {
		this.secondDictionarys = secondDictionarys;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	/*
	 * 显示代码的列表
	 */
	public String list() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			firstDictionarys = dictionaryService.queryDictionarysByLevel(1);
			secondDictionarys = dictionaryService.queryByParent(levelFirst);
			if (belong != null && !belong.equals("")) {
				queryCriteria.addQueryCondition("tbl_parentId", belong);
			}
			if (!levelFirst.equals(-1l)) {
				if (levelSecond.equals(-1l)) {
					queryCriteria.addQueryCondition("tbl_parentId", levelFirst);
				} else {
					List<Dictionary> dics = dictionaryService.queryByParent(levelFirst);
					boolean hasSecond = false;
					for (int i = 0; i < dics.size(); i++) {
						if (dics.get(i).getId() != null && dics.get(i).getId().toString().equals(levelSecond.toString())) {
							hasSecond = true;
							break;
						}
					}
					if (hasSecond)
						queryCriteria.addQueryCondition("tbl_parentId", levelSecond);
					else
						queryCriteria.addQueryCondition("tbl_parentId", levelFirst);
				}
			}
			super.list();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("list:" + e.getMessage());
			}
		}
		return "list";
	}

	/*
	 * 打开代码新建页面
	 */
	public String add() {
		super.add();
		return "add";
	}

	/*
	 * 显示编辑代码表单页面
	 */
	public String edit() throws Exception {
		super.edit();
		return "edit";
	}

	/*
	 * 显示查看代码表单页面
	 */
	public String view() throws Exception {
		super.view();
		return "view";
	}

	/*
	 * 保存代码
	 */
	public String save() {
		try {
			super.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long dictionaryId = this.currentSaveId;
		Dictionary dictionary = dictionaryService.queryById(dictionaryId);
		
		//自动生成唯一编码
		if(dictionary.getDicCode() == null || "".equals(dictionary.getDicCode().trim())) {
			dictionary.setDicCode("BM"+dictionaryId);
		}
		dictionaryService.doUpdateCode(dictionary);
		dictionaryService.doUpdateOrder(dictionary);
		
		CacheUtil.clearAllSCache();
		
		return NONE;
	}

	/*
	 * 根据parentId获取其下所有字典，返回JSON字典值和名键值对
	 */
	public String dics() {
		Long parentId = Long.valueOf(getRequest().getParameter("parentId"));
		try {
			List<Map<String, String>> dicmaps = dictionaryService.queryKeyValueMapByParentId(parentId);
			printObject(dicmaps);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
			
		}
		return NONE;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
