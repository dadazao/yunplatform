package com.cloudstong.platform.resource.buttongroup.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.button.service.ButtonService;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonAndGroupService;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description: 按钮和按钮组的中间表对象Action
 */
public class ButtonAndGroupAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 按钮和按钮组的中间表对象
	 */
	private ButtonAndGroup buttonAndGroup = null;

	/**
	 * 按钮组ID
	 */
	private String buttonGroupID = "";

	/**
	 * json数据
	 */
	private String JSONData = "";

	/**
	 * 选中的按钮和按钮组的中间表ID数组
	 */
	private String[] checkboxAndGroupIDs;

	/**
	 * 操作按钮和按钮组的中间对象服务接口,<code>buttonAndGroupService</code> 对象是ButtonAndGroupService接口的一个实例
	 */
	@Resource
	private ButtonAndGroupService buttonAndGroupService = null;

	/**
	 * 操作按钮组的服务接口,<code>buttonGroupService</code> 对象是ButtonGroupService接口的一个实例
	 */
	@Resource
	private ButtonGroupService buttonGroupService;

	/**
	 * 操作按钮的服务接口,<code>buttonService</code> 对象是ButtonService接口的一个实例
	 */
	@Resource
	private ButtonService buttonService = null;

	public String[] getCheckboxAndGroupIDs() {
		return checkboxAndGroupIDs;
	}

	public void setCheckboxAndGroupIDs(String[] checkboxAndGroupIDs) {
		this.checkboxAndGroupIDs = checkboxAndGroupIDs;
	}

	public String getJSONData() {
		return JSONData;
	}

	public void setJSONData(String jSONData) {
		JSONData = jSONData;
	}

	public String getButtonGroupID() {
		return buttonGroupID;
	}

	public void setButtonGroupID(String buttonGroupID) {
		this.buttonGroupID = buttonGroupID;
	}

	public ButtonAndGroup getButtonAndGroup() {
		return buttonAndGroup;
	}

	public void setButtonAndGroup(ButtonAndGroup buttonAndGroup) {
		this.buttonAndGroup = buttonAndGroup;
	}

	/**
	 * 按钮和按钮组的中间表的列表
	 * @return
	 */
	public String list() {
		QueryCriteria queryCriteria = new QueryCriteria();
		List resultList = null;
		int totalCount = 0;
		queryCriteria.addQueryCondition("tbl_buttonGroupID", buttonGroupID);
		buttonAndGroup = new ButtonAndGroup();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 0;
		}
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			resultList = buttonAndGroupService.queryForPageList(queryCriteria);
			totalCount = buttonAndGroupService.getTotalCount(queryCriteria);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("list:" + e.getMessage());
			}
		}
		this.pageResult = new PageResult();
		this.pageResult.setContent(resultList);
		this.pageResult.setTotalCount(totalCount);
		this.pageResult.setPageSize(this.numPerPage);
		this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
		this.pageResult.setCountPage((resultList.size() + this.numPerPage - 1) / this.numPerPage);
		return SUCCESS;
	}

	/**
	 * 在按钮组中添加按钮
	 * @return NONE
	 * @throws Exception
	 */
	public String save() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			int count = buttonAndGroupService.countButton(buttonAndGroup.getButtonGroupID());
			ButtonGroup bg = buttonGroupService.findByID(buttonAndGroup.getButtonGroupID());
			if (count >= bg.getButtonGroupMaxCount()) {
				throw new AppException("按钮组的按钮不能超过" + bg.getButtonGroupMaxCount() + "个！");
			}
//			String result = buttonAndGroupService.doSaveAndUpdate(buttonAndGroup);
			printJSON("success", false);
		} catch (AppException e) {
			printJSON("300", e.getMessage(), false);
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * 已发布的按钮的列表
	 * @return NONE
	 * @throws Exception
	 */
	public String getButtonList() throws Exception {
		List buttonList = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			queryCriteria = new QueryCriteria();
			queryCriteria.addQueryCondition("tbl_passed", 1);
			queryCriteria.setPageSize(-1);
			buttonList = buttonService.queryForPageList(queryCriteria);
			if (buttonList == null) {
				buttonList = new ArrayList();
			}
			org.apache.struts2.ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter printWriter = org.apache.struts2.ServletActionContext.getResponse().getWriter();
			objectMapper.writeValue(printWriter, buttonList);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("getButtonList:" + e.getMessage());
			}
			objectMapper.writeValue(org.apache.struts2.ServletActionContext.getResponse().getWriter(), buttonList);
		}
		return NONE;
	}

	/**
	 * 批量删除按钮组中的按钮
	 * @return NONE
	 * @throws Exception
	 */
	public String batchDel() throws Exception {
		try {
			if (checkboxAndGroupIDs != null && checkboxAndGroupIDs.length > 0) {
				List<String> checkboxArray = new ArrayList<String>();
				for (int i = 0; i < checkboxAndGroupIDs.length; i++) {
					checkboxArray.add(checkboxAndGroupIDs[i]);
				}
				if (checkboxArray != null && checkboxArray.size() > 0) {
					for (int nCount = 0; nCount < checkboxArray.size(); nCount++) {
						//buttonAndGroupService.doDelete(checkboxArray.get(nCount));
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
}
