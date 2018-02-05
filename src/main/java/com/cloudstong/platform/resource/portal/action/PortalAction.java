package com.cloudstong.platform.resource.portal.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.portal.model.Portal;
import com.cloudstong.platform.resource.portal.model.Portlet;
import com.cloudstong.platform.resource.portal.service.PortalService;
import com.cloudstong.platform.resource.portal.service.PortletService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * 门户Action
 * 
 * @author Jason 2012-10-29下午06:29:27
 */
@ParentPackage("default")
@Namespace("/pages/resource/portal")
@Results(value = { @Result(name = "portal", location = "/WEB-INF/view/resource/portal/portal.jsp"),
		@Result(name = "newportal", location = "/WEB-INF/view/resource/portal/newportal.jsp"),
		@Result(name = "portlets", location = "/WEB-INF/view/resource/portal/portlets.jsp") })
public class PortalAction extends BaseAction {
	private static final long serialVersionUID = 2872831611601867398L;
	/**
	 * 门户id
	 */
	private String portalId;
	/**
	 * 门户对象
	 */
	private Portal portal;
	/**
	 * 门户Service
	 */
	@Resource
	private PortalService portalService;
	@Resource
	private PortletService portletService;
	
	/**
	 * 门户组件id组成的字符串
	 */
	private String leftIDs;
	private String middleIDs;
	private String rightIDs;
	private String param;
	private List<Portlet> portletList;

	/**
	 * 
	 * Description: 加载当前默认门户 Steps:
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action("portalload")
	public String load() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		try {
			portal = portalService.doDefaultPortal(user);
		} catch (Exception e) {
			printJSON("", e.toString(), false);
		}
		return "portal";
	}

	@Action("portalloadDefault")
	public String loadDefault() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (user == null) {
			printJSON("fail");
			return NONE;
		}
		try {
			//首先需要获取当前默认门户
			portal = portalService.doDefaultPortal(user);
			List<Portlet> list = portal.getPortletList();
			leftIDs = "";
			middleIDs = "";
			rightIDs = "";
			for (Portlet p : list) {
				if ("left_side".equals(p.getBelongArea())) {
					leftIDs += p.getId() + ";";
				} else if ("middle_side".equals(p.getBelongArea())) {
					middleIDs += p.getId() + ";";
				} else if ("right_side".equals(p.getBelongArea())) {
					rightIDs += p.getId() + ";";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("300", "门户载入失败", false);
		}
		return "newportal";
	}

	@Action("portletfetch")
	public String fecth() throws IOException {
		SysUser user = (SysUser)getRequest().getSession().getAttribute("user");
		if(user == null){
			printJSON("fail");
			return NONE;
		}
		try{
			//获取portlet id
			String params[] = param.split(";");
			Long[] ids = new Long[params.length];
			for(int i=0;i<params.length;i++) {
				ids[i]=Long.valueOf(params[i]);
			}
			portletList = portletService.findPortletByIds(user, ids);
		}catch(Exception e ){
			e.printStackTrace();
			printJSON("300","获取数据失败 ",true);
		}
		return "portlets";
	}

	/**
	 * Getters and Setters
	 */
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public Portal getPortal() {
		return portal;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	public PortalService getPortalService() {
		return portalService;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public String getLeftIDs() {
		return leftIDs;
	}

	public void setLeftIDs(String leftIDs) {
		this.leftIDs = leftIDs;
	}

	public String getMiddleIDs() {
		return middleIDs;
	}

	public void setMiddleIDs(String middleIDs) {
		this.middleIDs = middleIDs;
	}

	public String getRightIDs() {
		return rightIDs;
	}

	public void setRightIDs(String rightIDs) {
		this.rightIDs = rightIDs;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public PortletService getPortletService() {
		return portletService;
	}

	public void setPortletService(PortletService portletService) {
		this.portletService = portletService;
	}

	public List<Portlet> getPortletList() {
		return portletList;
	}

	public void setPortletList(List<Portlet> portletList) {
		this.portletList = portletList;
	}

}
