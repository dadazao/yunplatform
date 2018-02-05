package com.cloudstong.platform.resource.buttongroup.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮组Action
 */
public class ButtonGroupAction extends CompexDomainAction {

	private static final long serialVersionUID = 1L;

	Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 按钮组
	 */
	private ButtonGroup buttonGroup = null;

	/**
	 * 按钮组ID
	 */
	private Long buttonGroupID;

	/**
	 * json数据
	 */
	private String JSONData = "";

	/**
	 * 列表选中项ID
	 */
	private String[] checkboxIDs;

	/**
	 * 按钮组类型代码集合
	 */
	private List<Dictionary> typeList;

	/**
	 * 代码
	 */
	private Dictionary dictionary;

	/**
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dictionaryService;
	
	/**
	 * 操作按钮组的服务接口,<code>buttonGroupService</code> 对象是ButtonGroupService接口的一个实例
	 */
	@Resource
	private ButtonGroupService buttonGroupService = null;

	/**
	 * 按钮组中按钮的列表
	 * @return forward
	 */
	public String findByIDForButtonConfig() {
//		try {
//			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
//			
//			typeList = dictionaryService.queryByParent("d055ab27719c4ce3a136873b9197bb75");
//			if (!buttonGroupID.equals("")) {
//				buttonGroup = buttonGroupService.findByID(buttonGroupID);
//			} else {
//				buttonGroup = new ButtonGroup();
//			}
//		} catch (Exception e) {
//			if (log.isDebugEnabled()) {
//				e.printStackTrace();
//				log.debug("findByIDForButtonConfig:" + e.getMessage());
//			}
//		}
		return "buttonConfig";
	}

	/**
	 * 批量删除按钮组
	 * @return NONE
	 * @throws Exception
	 */
	public String batchDel() throws Exception {
		try {
			if (checkboxIDs != null && checkboxIDs.length > 0) {
				List<String> checkboxArray = new ArrayList<String>();
				for (int i = 0; i < checkboxIDs.length; i++) {
					checkboxArray.add(checkboxIDs[i]);
				}
				if (checkboxArray != null && checkboxArray.size() > 0) {
					for (int nCount = 0; nCount < checkboxArray.size(); nCount++) {
						//buttonGroupService.doDelete(checkboxArray.get(nCount));
					}
				}
				printJSON("success");
			} else {
				printJSON("fail");
			}
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("batchDel:" + e.getMessage());
			}
		}
		return NONE;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public void setButtonGroup(ButtonGroup buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	public Long getButtonGroupID() {
		return buttonGroupID;
	}

	public void setButtonGroupID(Long buttonGroupID) {
		this.buttonGroupID = buttonGroupID;
	}

	public String getJSONData() {
		return JSONData;
	}

	public void setJSONData(String jSONData) {
		JSONData = jSONData;
	}

	public String[] getCheckboxIDs() {
		return checkboxIDs;
	}

	public void setCheckboxIDs(String[] checkboxIDs) {
		this.checkboxIDs = checkboxIDs;
	}

	public List<Dictionary> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Dictionary> typeList) {
		this.typeList = typeList;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

}
