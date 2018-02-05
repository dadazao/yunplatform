package com.cloudstong.platform.resource.template.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.model.Template;
import com.cloudstong.platform.resource.template.service.PartitionService;
import com.cloudstong.platform.resource.template.service.TemplateService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分区Action
 */
public class PartitionAction extends BaseAction {
	
	private static final long serialVersionUID = 5764031123935593758L;

	/**
	 * 操作分区的服务接口,<code>partitionService</code> 对象是PartitionService接口的一个实例
	 */
	@Resource
	private PartitionService partitionService;

	/**
	 * 操作代码的服务接口,<code>dictionaryService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dictionaryService;

	/**
	 * 操作模板的服务接口,<code>templateService</code> 对象是TemplateService接口的一个实例
	 */
	@Resource
	private TemplateService templateService;

	/**
	 * 分区
	 */
	private Partition partition;

	/**
	 * 主记录Id
	 */
	private String templateId;

	/**
	 * 分区id
	 */
	private Long partitionId;
	
	/**
	 * 分区类型
	 */
	private String partitionType;

	/**
	 * 分区类型列表
	 */
	private List<Dictionary> partitionTypes;

	/**
	 * 模板列表
	 */
	private List<Template> templates;

	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Long getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(Long partitionId) {
		this.partitionId = partitionId;
	}

	public String getPartitionType() {
		return partitionType;
	}

	public void setPartitionType(String partitionType) {
		this.partitionType = partitionType;
	}

	public List<Dictionary> getPartitionTypes() {
		return partitionTypes;
	}

	public void setPartitionTypes(List<Dictionary> partitionTypes) {
		this.partitionTypes = partitionTypes;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	/**
	 * Description:显示分区列表
	 * @return forward
	 */
	public String list() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			
			this.partitionTypes = dictionaryService.queryByParent(101628436920L);
			queryCriteria = new QueryCriteria();
			queryCriteria.addQueryCondition("tbl_templateid", templateId);
			if (this.pageNum == null || "".equals(this.pageNum)) {
				this.pageNum = 1;
			}
			queryCriteria.setPageSize(this.numPerPage);
			queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
			
			List templateList = partitionService.queryPartition(queryCriteria);
			this.pageResult = new PageResult();
			this.pageResult.setContent(templateList);
			try {
				this.pageResult.setTotalCount(partitionService
						.countPartition(templateId));
			} catch (Exception e) {
				this.pageResult.setTotalCount(0);
			}
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult
					.setCountPage((templateList.size() + this.numPerPage - 1)
							/ this.numPerPage);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("list:" + e.getMessage());
			}
		}
		return "loadlist";
	}

	/**
	 * Description:加载分区表单页面
	 * @return forward
	 */
	public String loadPartitionForm() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		this.partition = new Partition();
		this.partitionTypes = dictionaryService.queryByParent(101628436920L);
		QueryCriteria queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		queryCriteria.addQueryCondition("tbl_type", 0);
		queryCriteria.addQueryCondition("tbl_templatetype", 0);
		this.templates = templateService.queryTemplate(queryCriteria);
		return "loadform";
	}

	/**
	 * Description:保存分区
	 * @return NONE
	 */
	public String save() {
		if (this.partition.getId() != null && !this.partition.getId().equals("")) {
			partitionService.doUpdatePartition(partition);
		} else {
			partition.setTemplateId(templateId);
			partitionService.doSavePartition(partition);
		}
		return NONE;
	}

	/**
	 * Description:编辑分区
	 * @return forward
	 * @throws Exception
	 */
	public String edit() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		this.partitionTypes = dictionaryService.queryByParent(101628436920L);
		QueryCriteria queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		queryCriteria.addQueryCondition("tbl_type", 0);
		this.partition = partitionService.findPartitionById(partitionId);
		queryCriteria.addQueryCondition("tbl_templatetype", this.partition.getPartitionType());
		this.templates = templateService.queryTemplate(queryCriteria);
		return "edit";
	}
	
	/**
	 * Description:删除分区
	 * 
	 * Steps:
	 * 
	 * @return
	 * @throws IOException
	 */
	public String delete() throws IOException {
		try {
			if (selectedIDs != null) {
				this.partitionService.doDeletePartitions(selectedIDs);
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
	 * Description:为模板下拉框提供数据，返回模板下拉框的字符串
	 * @return NONE
	 * @throws Exception
	 */
	public String showTemplate() throws Exception{
		QueryCriteria queryCriteria = new QueryCriteria();
		queryCriteria.setPageSize(-1);
		queryCriteria.addQueryCondition("tbl_type", 0);
		queryCriteria.addQueryCondition("tbl_templatetype", partitionType);
		this.templates = templateService.queryTemplate(queryCriteria);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("<select name=\"partition.baseTemplate\" style=\"width:135px\">");
		int i=0;
		for (Template template : this.templates) {
			sb.append("<option value=\"" + template.getId() + "\" ");
			sb.append(" >" + template.getTemplateChName() + "</option>");
			
		}
		sb.append("</select>");
		out.write(sb.toString());
		return NONE;
	}

}
