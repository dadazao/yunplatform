/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.userChiose.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.userChiose.service.UserChioseService;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-12-25
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:机构用户选择树Action
 * 
 */
public class UserChioseAction extends CompexDomainAction {
	
	private static final long serialVersionUID = 6971230938843146121L;

	@Resource
	private UserChioseService userChioseService;
	
	/**
	 * Description:机构用户树
	 * @return NONE
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public String depmUserTree(){
		try {
			List<Map> _lstZtree = new ArrayList<Map>();
			List<Org> _lstSysCatalog = personChioseService.getOrgs();
			for (Org org : _lstSysCatalog) {
				Map ztree = new HashMap();
				ztree.put("id", org.getId());
				ztree.put("pId", org.getTblParentid());
				ztree.put("name", org.getTblName());
				ztree.put("open", true);
				ztree.put("isParent", true);
				_lstZtree.add(ztree);
			}
			List<SysUser> _lstUser= userChioseService.getUsers();
			Properties sp = (Properties) getSession().getAttribute("global");
			for (SysUser user : _lstUser) {
				if(!user.getUsername().equals(sp.getProperty("superUser"))){
					Map ztree = new HashMap();
					ztree.put("id", user.getId());
					ztree.put("pId", user.getDepartment());
					ztree.put("name", user.getFullname());
					ztree.put("open", false);
					ztree.put("isParent", false);
					_lstZtree.add(ztree);
				}
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			Writer writer = (Writer) response.getWriter();
			writer.write(objectMapper.writeValueAsString(_lstZtree));
			writer.close();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("depmUserTree:" + e.getMessage());
			}
		}
		return NONE;
	}

	public UserChioseService getUserChioseService() {
		return userChioseService;
	}

	public void setUserChioseService(UserChioseService userChioseService) {
		this.userChioseService = userChioseService;
	}
	
}
