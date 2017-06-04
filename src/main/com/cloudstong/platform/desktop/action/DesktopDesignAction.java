/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.action;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.desktop.model.DesktopDesign;
import com.cloudstong.platform.desktop.model.DesktopLayout;
import com.cloudstong.platform.desktop.service.DesktopDesignService;
import com.cloudstong.platform.desktop.service.DesktopLayoutService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-29
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/desktop/desktopDesign")
@Results({ @Result(name = "design", location = "/pages/desktop/desktopDesign.jsp"),
		@Result(name = "desktop", location = "/pages/desktop/desktop.jsp"),
		@Result(name = "showDesigned", location = "/pages/desktop/previewArea.jsp") })
public class DesktopDesignAction extends BaseAction {

	private static final long serialVersionUID = -2900663135926887634L;

	@Resource
	private DesktopDesignService desktopDesignService;
	@Resource
	private DesktopLayoutService desktopLayoutService;

	private DesktopDesign desktopDesign;
	private Long id;
	private Long[] desktopDesignIDs;

	private Long currentLayouId; // 当前所选的布局方式id

	@Action("desktop")
	public String desktop() {
		desktopDesign = desktopDesignService.loadDesktop(getUser().getId());
		return "desktop";
	}

	@Action("design")
	public String design() {
		desktopDesign = desktopDesignService.findByUserId(getUser().getId());
		if (desktopDesign == null) {
			desktopDesign = new DesktopDesign();
		}
		return "design";
	}

	@Action("save")
	public String save() {
		if (desktopDesign != null) {
			if (currentLayouId != null) {
				DesktopLayout desktopLayout = desktopLayoutService.findDesktopLayoutById(currentLayouId);
				desktopDesign.setColumnNum(desktopLayout.getColumnNum());
				desktopDesign.setColumnWidths(desktopLayout.getColumnWidths());
			}
			desktopDesign.setUserid(getUser().getId());
			desktopDesignService.doSaveOrUpdate(desktopDesign);
		}
		printSuccess(desktopDesign);
		return NONE;
	}

	/**
	 * Description:显示之前设计过的界面
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	@Action("showDesigned")
	public String showDesigned() {
		desktopDesign = desktopDesignService.findById(id);
		HttpServletRequest request = getRequest();
		request.setAttribute("columns", Arrays.asList(desktopDesign.getColumnWidths().split(",")));
		request.setAttribute("columnItemIds", desktopDesign.getLayoutItemIds());
		return "showDesigned";
	}

	public DesktopDesign getDesktopDesign() {
		return desktopDesign;
	}

	public void setDesktopDesign(DesktopDesign desktopDesign) {
		this.desktopDesign = desktopDesign;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getDesktopDesignIDs() {
		return desktopDesignIDs;
	}

	public void setDesktopDesignIDs(Long[] desktopDesignIDs) {
		this.desktopDesignIDs = desktopDesignIDs;
	}

	public Long getCurrentLayouId() {
		return currentLayouId;
	}

	public void setCurrentLayouId(Long currentLayouId) {
		this.currentLayouId = currentLayouId;
	}

}
