package com.cloudstong.platform.resource.metadata.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.RelationService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:表关系Action
 */
@ParentPackage("default")
@Namespace("/pages/resource/relation")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/resource/metadata/compexRelationList.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/resource/metadata/compexRelationEdit.jsp"),
		@Result(name="input", location = "/WEB-INF/view/resource/metadata/compexRelationEdit.jsp"),
		@Result(name="add", location = "/WEB-INF/view/resource/metadata/compexRelationEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/resource/metadata/compexRelationView.jsp")
})
public class RelationAction extends CompexDomainAction {

	private static final long serialVersionUID = 5913512081042872366L;

	/**
	 * 表关系类型集合
	 */
	List<Dictionary> relationTypes;

	/**
	 * 表属性集合
	 */
	List<Table> tables;

	/**
	 * 表关系
	 */
	private Relation relation;

	/**
	 * 表关系ID
	 */
	private Long relationId;

	/**
	 * 操作表关系的服务接口,<code>relationService</code> 对象是RelationService接口的一个实例
	 */
	@Resource
	private RelationService relationService;

	/**
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dictionaryService;

	/**
	 * 操作表属性的服务接口,<code>tableService</code> 对象是TableService接口的一个实例
	 */
	@Resource
	private TableService tableService;

	public List<Dictionary> getRelationTypes() {
		return relationTypes;
	}

	public void setRelationTypes(List<Dictionary> relationTypes) {
		this.relationTypes = relationTypes;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public RelationService getRelationService() {
		return relationService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public TableService getTableService() {
		return tableService;
	}

	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}
	
	@Action("list")
	public String list() {
		return super.list();
	}

	/*
	 * 打开表关系新建页面
	 */
	@Action("add")
	public String add() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);
			this.tables = tableService.queryTable(qc);
			this.relationTypes = dictionaryService.queryByParent(302174359L);

			template = "domainEdit.jsp";
			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			this.form = formService.findFormByIdAndDomainVO(formId, domainVOs, user);
			this.formButtons = this.form.getFormButtons();
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("add:" + e.getMessage());
			}
		}
		return "add";
	}

	/*
	 * 保存表关系
	 */
	@Action("save")
	public String save() throws Exception {
		HttpServletRequest request = getRequest();
		String mainId = request.getParameter("sys_relation-tbl_mainid");
		String subId = request.getParameter("sys_relation-tbl_subid");
		String relationType = request.getParameter("sys_relation-tbl_relationtype");
		String comment = request.getParameter("sys_relation-tbl_comment");
		String remark = request.getParameter("sys_relation-tbl_remark");
		String systemteam = request.getParameter("sys_relation-tbl_systemteam");

		String connectType = request.getParameter("sys_relation-tbl_connecttype");
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		try {
			relation = new Relation();
			if (domainId != null && !domainId.equals("")) {
				relation.setId(Long.valueOf(domainId));
			}
			relation.setConnecttype(connectType);
			relation.setMainTableId(mainId);
			relation.setSubTableId(subId);
			relation.setMainTableName(tableService.findTableById(Long.valueOf(mainId)).getTableName());
			relation.setSubTableName(tableService.findTableById(Long.valueOf(subId)).getTableName());
			relation.setRelationType(Integer.valueOf(relationType));
			relation.setComment(comment);
			relation.setRemark(remark);
			relation.setSystemteam(systemteam);
			/*
			 * 分别处理关系表和外键
			 */
			if ("2".equals(connectType)) { // 关系表
				// 判断表关系是否已经存在，存在则给出提示
				List list = relationService.findReplyRelation(relation);
				Relation rel = relationService.selectByMainIdAndSubId(Long.valueOf(subId), Long.valueOf(mainId));

				if (list.size() > 0 || (rel != null && rel.getId() != null)) {
					printJSON("301", "表关系已存在！", false);
					return NONE;
				}
			} else {// 外键
				// 判断是否是否存在
				boolean exist = relationService.checkExistForeignKey(relation);
				if (exist == true) {
					printJSON("301", "表关系已存在！", false);
					return NONE;
				}
			}

			// 判断是新建还是修改
			if (domainId == null || domainId.equals("")) {
				relation.setCreateBy(user.getId());
				relation.setCreateDate(new Date());
				relation.setUpdateBy(user.getId());
				relation.setUpdateDate(new Date());
				domainId = relationService.doSaveRelation(relation);
			} else {
				relation.setUpdateBy(user.getId());
				relation.setUpdateDate(new Date());
				relationService.doUpdateRelation(relation);
			}
			printJSON("success", false, domainId.toString());
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
			if (log.isDebugEnabled()) {
				log.debug("save:" + e.getMessage());
			}
		}
		return NONE;
	}

	/*
	 * 编辑表关系
	 */
	@Action("edit")
	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.relation = relationService.queryById(relationId);
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		this.relationTypes = dictionaryService.queryByParent(302174359L);

		template = "domainEdit.jsp";

		List<DomainVO> vos = new ArrayList<DomainVO>();
		vos.add(new DomainVO(this.model, relationId));
		this.form = formService.findFormByIdAndDomainVO(formId, vos, user);
		this.formButtons = this.form.getFormButtons();

		return "edit";
	}

	/*
	 * 删除表关系
	 */
	@Action("del")
	public String delete() throws IOException {
		try {
			if (selectedVOs != null) {
				relationService.doDeleteRelations(convert(selectedVOs));
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		}
		return NONE;
	}

	/*
	 * 删除表关系
	 */
	@Action("logicDelete")
	public String logicDelete() throws IOException {
		try {
			if (selectedVOs != null) {
				relationService.doLogicDeleteRelations(convert(selectedVOs));
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("delete:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:将参数转换为各个ID以","连接
	 * 
	 * @param selectedVOs
	 *            参数数组，格式为 表名_字段名：ID;
	 * @return ID数组
	 */
	private Long[] convert(String[] selectedVOs) {
		Long[] ret = new Long[selectedVOs.length];
		for (int i = 0; i < selectedVOs.length; i++) {
			String vo = selectedVOs[i];
			ret[i] = Long.valueOf(vo.substring(vo.indexOf(":") + 1, vo.indexOf(";")));
		}
		return ret;
	}

	/**
	 * Description:删除表关系
	 * 
	 * @return NONE
	 */
	@Action("singleDelete")
	public String singleDelete() {
		String retStr = null;
		try {
			Long[] temp = new Long[] { this.relationId };
			this.selectedIDs = temp;
			retStr = this.delete();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("singleDelete:" + e.getMessage());
			}
		}
		return retStr;
	}

	/*
	 * 显示表关系查询页面
	 */
	@Action("view")
	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");

		this.relation = this.relationService.queryById(relationId);
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		this.tables = tableService.queryTable(qc);
		this.relationTypes = dictionaryService.queryByParent(302174359L);

		template = "domainView.jsp";
		Long id = this.relation.getId();
		List<DomainVO> temp = new ArrayList<DomainVO>();
		temp.add(new DomainVO(this.model, id));
		this.form = formService.findFormByIdAndDomainVO(formId, temp, user);
		this.formButtons = this.form.getFormButtons();

		return "view";
	}

}
