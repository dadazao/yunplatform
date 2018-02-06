package com.cloudstong.platform.resource.metadata.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.system.model.SysUser;

@ParentPackage("default")
@Namespace("/pages/resource/join")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/core/compexJoinList.jsp")
})
public class JoinAction extends CompexDomainAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 挂接列表，根据查询条件过滤数据，显示在列表中
	 * 
	 * @return forward
	 */
	@Action("list")
	public String list() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		// 通过while循环构造查询条件,dyncMapString,dyncMapStringPrecise,dyncMapLong都是不同的查询条件集合
		@SuppressWarnings("rawtypes")
		Iterator iterator = dyncMapString.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && !"-1".equals(entry.getValue())) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), "%" + entry.getValue() + "%");
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratorp = dyncMapStringPrecise.entrySet().iterator();
		while (iteratorp.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iteratorp.next();
			if (dyncMapStringCombobox.containsKey(entry.getKey())) {
				String flag = dyncMapStringCombobox.get(entry.getKey());
				if ("false".equals(flag)) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), new Double(Math.E).toString());
				} else if ("true".equals(flag) && !"-1".equals(entry.getValue())) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
				}
			} else {
				if (entry.getValue() != null && !"".equals(entry.getValue()) && !"-1".equals(entry.getValue())) {
					queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
				}
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratorLong = dyncMapLong.entrySet().iterator();
		while (iteratorLong.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iteratorLong.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && -1L != entry.getValue()) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
			}
		}

		@SuppressWarnings("rawtypes")
		Iterator iteratorInteger = dyncMapInteger.entrySet().iterator();
		while (iteratorInteger.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorInteger.next();
			if (entry.getValue() != null && !"".equals(entry.getValue()) && -1 != entry.getValue()) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
			}
		}
		@SuppressWarnings("rawtypes")
		Iterator iteratord = dyncMapDate.entrySet().iterator();
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		while (iteratord.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iteratord.next();
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				queryCriteria.addQueryCondition(entry.getKey().replace("-", "."), entry.getValue());
				keyList.add(entry.getKey());
				valueList.add(entry.getValue());
			}
		}

		// 判断规则
		Catalog catalog = catalogService.findCatalogByListId(listId);
		if (catalog!= null) {
			rule = catalog.getRule();
			if(rule != null){
				if (log.isDebugEnabled()) {
					log.debug("当前" + catalog.getName() + "的规则为：" + rule);
				}
				if (!user.isSuper() && rule.equals(Constants.RULE_NO_READ_WRITE)) {
					queryCriteria.addQueryCondition("comm_createBy", user.getId());
				}
			}
		} 
		//设置当前页
		queryCriteria.setCurrentIndex((Integer.valueOf(this.pageNum) - 1) * this.numPerPage + 1);
		//设置每页记录数
		queryCriteria.setPageSize(this.numPerPage);
		try {
			//通过列表ID，查询条件及客户信息查询出列表的信息
			tabulation = tabulationService.findTabulationByListId(Long.valueOf(listId), queryCriteria, user);

			if (tabulation != null) {
				// 判断是否有默认查询条件
				for (int i = 0; i < tabulation.getTabulationColumnExtends().size(); i++) {
					if (tabulation.getTabulationColumnExtends().get(i).getFormColumn().getIsDefaultQuery() == 1) {
						hasDefaultQuery = true;
						break;
					}
				}
				// 判断是否是高级查询
				List<FormColumnExtend> queryColumnExtends = new ArrayList<FormColumnExtend>();
				for (int i = 0; i < tabulation.getTabulationColumnExtends().size(); i++) {
					FormColumnExtend fce = tabulation.getTabulationColumnExtends().get(i);
					if (fce.getFormColumn().getIsQuery() == 1) {
						queryColumnExtends.add(fce);
					}
				}
				tabulation.setQueryColumnExtends(queryColumnExtends);
			}

			//取出主列表对应的模板文件
			template = tabulation.getTemplateFileName();
			//取出列表上所有的按钮
			tabulationButtons = tabulation.getTabulationButtons();
			//取出列表关联的表单ID
			formId = tabulation.getFormId();
			//根据表单ID查询出列表关联的表单
			form = formService.findFormById(formId);
			//取出表单关联的主表
			model = form.getTableName();

			// 返回分页结果
			this.pageResult = new PageResult();
			this.pageResult.setContent(tabulation.getDomains());
			this.pageResult.setTotalCount(tabulation.getTotalCount());
			this.pageResult.setPageSize(this.numPerPage);
			this.pageResult.setCurrentPage(Integer.valueOf(this.pageNum));
			this.pageResult.setCountPage((tabulation.getDomains().size() + this.numPerPage - 1) / this.numPerPage);

		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("list:" + e.getMessage());
			}
		}
		return "list";
	}
}
