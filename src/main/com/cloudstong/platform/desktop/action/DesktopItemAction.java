/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.action;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.desktop.engine.FreeMarkerEngine;
import com.cloudstong.platform.desktop.model.DesktopItem;
import com.cloudstong.platform.desktop.service.DesktopItemService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Jason
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/desktop/desktopItem")
@Results({ @Result(name = "preview", location = "/pages/desktop/desktopItemPreview.jsp"), @Result(name = "view", location = "/pages/desktop/desktopItemView.jsp"), @Result(name = "list", location = "/pages/desktop/desktopItemList.jsp"), @Result(name = "edit", location = "/pages/desktop/desktopItemEdit.jsp") })
public class DesktopItemAction extends BaseAction {

	private static final long serialVersionUID = -452071711421120356L;

	@Resource
	private DesktopItemService desktopItemService;

	@Resource
	private BeanHelper beanHelper;

	private DesktopItem desktopItem;
	private Long[] desktopItemIDs;
	private Long id;

	@Action("add")
	public String add() {
		desktopItem = new DesktopItem();
		return "edit";
	}

	@Action("delete")
	public String delete() {
		if (desktopItemIDs != null) {
			desktopItemService.doDeleteDesktopItems(desktopItemIDs);
		}
		printJSON("success");
		return NONE;
	}

	@Action("edit")
	public String edit() {
		desktopItem = desktopItemService.findDesktopItemById(id);
		return "edit";
	}

	@Action("jsonSelect")
	public String jsonSelect() {
		printObject(desktopItemService.findAllDesktopItems());
		return NONE;
	}

	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (desktopItem != null) {
			if (desktopItem.getName() != null && desktopItem.getName().trim().length() > 0) {
				queryCriteria.addQueryCondition("tbl_name", "%" + desktopItem.getName().trim() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		pageResult = desktopItemService.queryDesktopItem(queryCriteria);
		return "list";
	}

	/**
	 * 
	 * Description:预览
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action("preview")
	public String preview() {
		desktopItem = desktopItemService.findDesktopItemById(id);
		try {
			String html = "";
			String methodUrl = desktopItem.getMethodUrl();
			if (methodUrl == null || methodUrl.length() == 0) {
				html = desktopItem.getTemplateHtml();
			} else {
				Configuration config = FreeMarkerEngine.CONFIGURATION.getConfiguration();
				StringTemplateLoader loader = new StringTemplateLoader();
				loader.putTemplate("template", desktopItem.getTemplateHtml());
				config.setTemplateLoader(loader);
				Template template = config.getTemplate("template");
				StringWriter writer = new StringWriter();
				Map root = new HashMap();
				root.put("model", beanHelper.executeMethod(desktopItem.getMethodUrl(), getUser()));
				template.process(root, writer);
				html = writer.toString();
			}
			getRequest().setAttribute("Html", html);
		} catch (IOException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		} catch (TemplateException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		}
		return "preview";
	}

	/**
	 * 
	 * Description:渲染，根据id获取桌面Item，并将其Html代码返回
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action("render")
	public String render() {
		desktopItem = desktopItemService.findDesktopItemById(id);
		try {
			String html = "";
			String methodUrl = desktopItem.getMethodUrl();
			if (methodUrl == null || methodUrl.length() == 0) {
				StringBuilder resultBuilder = new StringBuilder("<div class=\"portlet\" referedDesktopItem=\"").append(desktopItem.getId()).append("\"><div class=\"phead\"><div class=\"ptitle\"><img src=\"pages/resource/portal/image/titleicon.gif\" style=\"padding-top:5px;\">").append(desktopItem.getName()).append("</div><div class=\"poper\" style=\"padding-top:6px;\"><a href=\"#\" onclick=\"loadList('").append(desktopItem.getName()).append("','").append(desktopItem.getAlias()).append("','").append(desktopItem.getModuleUrl()).append("')\" style=\"text-decoration: none;\" title=\"列表查看所有信息\">更多</a></div></div><div class=\"pcontent\">").append(desktopItem.getTemplateHtml()).append("</div></div>");
				html = resultBuilder.toString();
			} else {
				Configuration config = FreeMarkerEngine.CONFIGURATION.getConfiguration();
				StringTemplateLoader loader = new StringTemplateLoader();
				loader.putTemplate("template", desktopItem.getTemplateHtml());
				config.setTemplateLoader(loader);
				Template template = config.getTemplate("template");
				StringWriter strWriter = new StringWriter();
				Map root = new HashMap();
				root.put("model", beanHelper.executeMethod(desktopItem.getMethodUrl(), getUser()));
				template.process(root, strWriter);
				String contentHtml = strWriter.toString();
				StringBuilder resultBuilder = new StringBuilder("<div class=\"portlet\" referedDesktopItem=\"").append(desktopItem.getId()).append("\"><div class=\"phead\"><div class=\"ptitle\"><img src=\"pages/resource/portal/image/titleicon.gif\" style=\"padding-top:5px;\">").append(desktopItem.getName()).append("</div><div class=\"poper\" style=\"padding-top:6px;\"><a href=\"#\" onclick=\"loadList('").append(desktopItem.getName()).append("','").append(desktopItem.getAlias()).append("','").append(desktopItem.getModuleUrl()).append("')\" style=\"text-decoration: none;\" title=\"列表查看所有信息\">更多</a></div></div><div class=\"pcontent\">").append(contentHtml).append("</div></div>");
				html = resultBuilder.toString();
			}
			HttpServletResponse response = getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			Writer writer = response.getWriter();
			writer.write(html);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		} catch (TemplateException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
		}
		return NONE;
	}

	@Action("save")
	public String save() {
		if (desktopItem != null) {
			if (desktopItem.getId() != null) {
				desktopItem.setUpdateBy(getUser().getId());
				desktopItem.setUpdateDate(new Date());
				desktopItemService.doUpdateDesktopItem(desktopItem);
			} else {
				desktopItem.setCreateBy(getUser().getId());
				desktopItem.setCreateDate(new Date());
				desktopItemService.doSaveDesktopItem(desktopItem);
			}
		}
		printSuccess(desktopItem);
		return NONE;
	}

	@Action("view")
	public String view() {
		desktopItem = desktopItemService.findDesktopItemById(id);
		return "view";
	}

	public DesktopItem getDesktopItem() {
		return desktopItem;
	}

	public void setDesktopItem(DesktopItem desktopItem) {
		this.desktopItem = desktopItem;
	}

	public Long[] getDesktopItemIDs() {
		return desktopItemIDs;
	}

	public void setDesktopItemIDs(Long[] desktopItemIDs) {
		this.desktopItemIDs = desktopItemIDs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
