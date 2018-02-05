package com.cloudstong.platform.resource.catalog.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:目录Action
 */
public class CatalogAction extends CompexDomainAction {

	Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 目录
	 */
	private Catalog catalog;
	/**
	 * 目录ID
	 */
	private Long id;
	/**
	 * 关联路径
	 */
	private String path;
	/**
	 * 列表集合
	 */
	private List<Tabulation> tabulations;
	
	/**
	 * 判断打开的表单是新建还是修改
	 */
	private String op;

	/**
	 * 操作目录的服务接口,<code>catalogService</code> 对象是CatalogService接口的一个实例
	 */
	@Resource
	private CatalogService catalogService;
	/**
	 * 操作列表的服务接口,<code>tabulationService</code> 对象是TabulationService接口的一个实例
	 */
	@Resource
	private TabulationService tabulationService;


	/**
	 * 解析字符串为domainVO
	 * 
	 * @param params
	 * @return
	 */
	public List<DomainVO> getDomainVos(String params) {
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		domainVOs.add(new DomainVO(this.model, this.id));
		return domainVOs;
	}


	public String add() {
		this.catalog = new Catalog();

		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		tabulations = tabulationService.queryTabulation(queryCriteria);
		String retStr = null;
		try {
			retStr = super.add();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		}
		return retStr;
	}

	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.catalog = catalogService.findCatalogById(id);

		queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		tabulations = tabulationService.queryTabulation(queryCriteria);

		template = "domainEdit.jsp";
		List<DomainVO> dvos = new ArrayList<DomainVO>();
		dvos.add(new DomainVO(this.model, id));
		this.form = formService.findFormByIdAndDomainVO(formId, dvos, user);
		this.formButtons = this.form.getFormButtons();

		return "edit";
	}

	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.catalog = catalogService.findCatalogById(id);
		template = "domainView.jsp";
		List<DomainVO> dvos = new ArrayList<DomainVO>();
		dvos.add(new DomainVO(this.model, id));
		this.form = formService.findFormByIdAndDomainVO(formId, dvos, user);
		this.formButtons = this.form.getFormButtons();
		return "view";
	}

	public String save() throws Exception {
		if (path != null && !"".equals(path)) {
			catalog.setPath(path);
		}
		try {
			if (catalog.getId() != null && !catalog.getId().equals("")) {
				catalog.setUpdateDate(new Date());
				catalog.setUpdateBy(1l);
				catalogService.doUpdateCatalog(catalog);
			} else {
				catalog.setCreateDate(new Date());
				catalog.setCreateBy(1l);
				catalogService.doSaveCatalog(catalog);
			}
			printJSON("success");
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}

	public String delete() throws IOException {
		try {
			if (selectedIDs != null) {
				this.catalogService.doDeleteCatalogs(selectedIDs);
			} else {
				this.catalogService.doDeleteCatalog(id);
			}
			printJSON("success");
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}

	/**
	 * 是否在首页显示
	 * @return NONE
	 * @throws IOException
	 */
	public String showIndex() throws IOException {
		Catalog catalog = this.catalogService.findCatalogById(Long.parseLong("134"));
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(catalog.getPath());
		return NONE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Tabulation> getTabulations() {
		return tabulations;
	}

	public void setTabulations(List<Tabulation> tabulations) {
		this.tabulations = tabulations;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

}
