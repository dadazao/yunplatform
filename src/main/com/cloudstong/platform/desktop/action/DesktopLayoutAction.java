/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.desktop.model.DesktopLayout;
import com.cloudstong.platform.desktop.service.DesktopLayoutService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/desktop/desktopLayout")
@Results({ @Result(name = "list", location = "/pages/desktop/desktopLayoutList.jsp"),
		@Result(name = "edit", location = "/pages/desktop/desktopLayoutEdit.jsp"),
		@Result(name = "relayout", location = "/pages/desktop/previewArea.jsp"),
		@Result(name = "design", location = "/pages/desktop/desktopLayoutDesign.jsp"),
		@Result(name = "add", location = "/pages/desktop/desktopLayoutEdit.jsp") })
public class DesktopLayoutAction extends BaseAction {

	private static final long serialVersionUID = 1376890318442886773L;

	@Resource
	private DesktopLayoutService desktopLayoutService;

	private DesktopLayout desktopLayout;
	private Long id;
	private Long[] desktopLayoutIDs;

	@Action("add")
	public String add() {
		desktopLayout = new DesktopLayout();
		return "add";
	}

	@Action("delete")
	public String delete() {
		if (desktopLayoutIDs != null) {
			desktopLayoutService.doDeleteDesktopLayouts(desktopLayoutIDs);
		}
		printJSON("success");
		return NONE;
	}

	@Action("design")
	public String design() {
		desktopLayout = desktopLayoutService.findDesktopLayoutById(id);
		return "design";
	}

	@Action("edit")
	public String edit() {
		desktopLayout = desktopLayoutService.findDesktopLayoutById(id);
		return "edit";
	}

	/**
	 * Description: 返回所有的布局，{id:name}形式
	 * 
	 * @return
	 */
	@Action("jsonSelect")
	public String jsonSelect() {
		printObject(desktopLayoutService.findAllDesktopLayouts());
		return NONE;
	}

	/**
	 * 
	 * Description: 更换布局
	 * 
	 * @return
	 */
	@Action("relayout")
	public String relayout() {
		DesktopLayout layout = desktopLayoutService.findDesktopLayoutById(id);
		HttpServletRequest request = getRequest();
		request.setAttribute("columns", layout.caculateWidthPercent());
		String items = layout.getItems();
		if (items != null && items.length() > 0) {
			request.setAttribute("columnItemIds", items);
		}
		return "relayout";
	}

	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (desktopLayout != null) {
			String name = desktopLayout.getName();
			if (name != null && name.trim().length() != 0) {
				queryCriteria.addQueryCondition("tbl_name", "%" + name.trim() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		pageResult = desktopLayoutService.queryDesktopLayout(queryCriteria);
		return "list";
	}

	@Action("save")
	public String save() {
		if (desktopLayout != null) {
			Long userid = getUser().getId();
			desktopLayout.setCreateBy(userid);
			desktopLayout.setUpdateBy(userid);
			desktopLayoutService.doSaveOrUpdateDesktopLayout(desktopLayout);
		}
		printSuccess(desktopLayout);
		return NONE;
	}

	@Action("saveDesign")
	public String saveDesign() {
		HttpServletRequest request = getRequest();
		String items = request.getParameter("items");
		desktopLayoutService.doSaveOrUpdateLayoutDesign(id, items);
		printJSON("success");
		return NONE;
	}

	@Action("saveLayout")
	public String saveLayout() {
		String columns = getRequest().getParameter("columns"); // columns参数格式：aaa,bbb;ccc,dddd;eee分号分隔的是不同列，逗号分隔的是每一列中的不同desktopItem的id
		desktopLayoutService.doSaveDesktopDesign(getUser(), id, columns);
		printJSON("success");
		return NONE;
	}

	@Action("setDefault")
	public String setDefault() {
		desktopLayoutService.doSetDefaultLayout(id);
		printJSON("success");
		return NONE;
	}

	public DesktopLayout getDesktopLayout() {
		return desktopLayout;
	}

	public void setDesktopLayout(DesktopLayout desktopLayout) {
		this.desktopLayout = desktopLayout;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getDesktopLayoutIDs() {
		return desktopLayoutIDs;
	}

	public void setDesktopLayoutIDs(Long[] desktopLayoutIDs) {
		this.desktopLayoutIDs = desktopLayoutIDs;
	}

}
