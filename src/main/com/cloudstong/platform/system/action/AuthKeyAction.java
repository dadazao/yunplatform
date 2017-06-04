/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.model.AuthCustomer;
import com.cloudstong.platform.system.model.AuthKey;
import com.cloudstong.platform.system.model.AuthProduct;
import com.cloudstong.platform.system.model.AuthTemplate;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthCustomerService;
import com.cloudstong.platform.system.service.AuthKeyService;
import com.cloudstong.platform.system.service.AuthProductService;
import com.cloudstong.platform.system.service.AuthTemplateService;

/**
 * @author John
 * 
 *         Created on 2014-11-25
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/authKey")
@Results(value = { @Result(name = "list", location = "/pages/system/authorization/authKeyList.jsp"),
		@Result(name = "add", location = "/pages/system/authorization/authKeyEdit.jsp"), 
		@Result(name = "edit", location = "/pages/system/authorization/authKeyEdit.jsp"),
		@Result(name = "view", location = "/pages/system/authorization/authKeyView.jsp") })
public class AuthKeyAction extends BaseAction {

	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = -5696810272403004877L;
	
	@Resource
	private AuthKeyService authKeyService;
	@Resource
	private AuthProductService authProductService;
	@Resource
	private AuthTemplateService authTemplateService;
	@Resource
	private AuthCustomerService authCustomerService;
	
	private AuthKey authKey;
	
	private List<AuthProduct> authProducts;
	
	private List<AuthTemplate> authTemplates;
	
	private List<AuthCustomer> authCustomers;
	
	private Long authProductId;
	
	private String authProductname;
	
	
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (authKey != null) {
			if (authKey.getSn() != null && !"".equals(authKey.getSn())) {
				queryCriteria.addQueryCondition("tbl_sn", "%"+authKey.getSn().trim()+"%");
			}
			if (authKey.getProductid() != null && !"".equals(authKey.getProductid())) {
				queryCriteria.addQueryCondition("tbl_productid", authKey.getProductid());
			}
			if (authKey.getTemplateid() != null && !"".equals(authKey.getTemplateid())) {
				queryCriteria.addQueryCondition("tbl_templateid", authKey.getTemplateid());
			}
			if (authKey.getCustomerid() != null && !"".equals(authKey.getCustomerid())) {
				queryCriteria.addQueryCondition("tbl_customerid", authKey.getCustomerid());
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		// 列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		this.pageResult = authKeyService.queryAuthKey(queryCriteria);
		authProducts = authProductService.queryAll();
		authCustomers = authCustomerService.queryAll();

		return "list";
	}
	
	@Action("add")
	public String add() {
		authKey = new AuthKey();
		authProducts = authProductService.queryAll();
		authCustomers = authCustomerService.queryAll();
		return "add";
	}
	
	@Action("save")
	public String save() throws Exception {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			String[] arrays = authKeyService.doSaveOrUpdate(authKey, user);
			printJSON("success", "SN添加成功："+arrays[1], false, arrays[0]);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	
	@Action("delete")
	public String delete() throws Exception {
		try {
			if (selectedIDs != null) {
				SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
				authKeyService.doUpdateAuthKeysUnValid(selectedIDs, user);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}

	@Action("view")
	public String view() {
		this.authKey = authKeyService.findAuthKeyById(authKey.getId());
		AuthProduct ap = authProductService.findAuthProductById(authKey.getProductid());
		if(ap !=null){
			this.authProductname = ap.getName();
		}
		return "view";
	}
	
	@Action("findTemplatesByPId")
	public String findTemplatesByPId() throws Exception {
		try {
			List<AuthTemplate> tlist = authTemplateService.findAuthTemplateByPId(authProductId);
			printObject(tlist);
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}

	/**
	 * @return Returns the value of authKey field with the type AuthKey.
	 */
	public AuthKey getAuthKey() {
		return authKey;
	}

	/**
	 * @param authKey. Set the value of authKey field with the type AuthKey by the authKey parameter.
	 */
	public void setAuthKey(AuthKey authKey) {
		this.authKey = authKey;
	}

	/**
	 * @return Returns the value of authProducts field with the type List<AuthProduct>.
	 */
	public List<AuthProduct> getAuthProducts() {
		return authProducts;
	}

	/**
	 * @param authProducts. Set the value of authProducts field with the type List<AuthProduct> by the authProducts parameter.
	 */
	public void setAuthProducts(List<AuthProduct> authProducts) {
		this.authProducts = authProducts;
	}

	/**
	 * @return Returns the value of authTemplates field with the type List<AuthTemplate>.
	 */
	public List<AuthTemplate> getAuthTemplates() {
		return authTemplates;
	}

	/**
	 * @param authTemplates. Set the value of authTemplates field with the type List<AuthTemplate> by the authTemplates parameter.
	 */
	public void setAuthTemplates(List<AuthTemplate> authTemplates) {
		this.authTemplates = authTemplates;
	}

	/**
	 * @return Returns the value of authProductId field with the type Long.
	 */
	public Long getAuthProductId() {
		return authProductId;
	}

	/**
	 * @param authProductId. Set the value of authProductId field with the type Long by the authProductId parameter.
	 */
	public void setAuthProductId(Long authProductId) {
		this.authProductId = authProductId;
	}

	/**
	 * @return Returns the value of authCustomers field with the type List<AuthCustomer>.
	 */
	public List<AuthCustomer> getAuthCustomers() {
		return authCustomers;
	}

	/**
	 * @param authCustomers. Set the value of authCustomers field with the type List<AuthCustomer> by the authCustomers parameter.
	 */
	public void setAuthCustomers(List<AuthCustomer> authCustomers) {
		this.authCustomers = authCustomers;
	}

	/**
	 * @return Returns the value of authProductname field with the type String.
	 */
	public String getAuthProductname() {
		return authProductname;
	}

	/**
	 * @param authProductname. Set the value of authProductname field with the type String by the authProductname parameter.
	 */
	public void setAuthProductname(String authProductname) {
		this.authProductname = authProductname;
	}
	
	

}
