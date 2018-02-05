package com.cloudstong.platform.resource.catalog.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.form.dao.FormButtonDao;
import com.cloudstong.platform.resource.form.dao.FormDao;
import com.cloudstong.platform.resource.form.dao.TabDao;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.dao.CompexDomainDao;
import com.cloudstong.platform.resource.metadata.dao.TableDao;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;
import com.cloudstong.platform.resource.tabulation.dao.TabulationButtonDao;
import com.cloudstong.platform.resource.tabulation.dao.TabulationDao;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;

/**
 * @author Allan
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 目录服务接口实现类
 */
@Repository("catalogService")
public class CatalogServiceImpl implements CatalogService {

	@javax.annotation.Resource
	private CatalogDao catalogDao;
	
	@javax.annotation.Resource
	private ColumnDao columnDao;
	
	@javax.annotation.Resource
	private TableDao tableDao;
	
	@javax.annotation.Resource
	private FormDao formDao;
	
	@javax.annotation.Resource
	private FormButtonDao formButtonDao;
	
	@javax.annotation.Resource
	private TabDao tabDao;
	
	@javax.annotation.Resource
	private TabulationDao tabulationDao;
	
	@javax.annotation.Resource
	private TabulationButtonDao tabulationButtonDao;
	
	@javax.annotation.Resource
	private CompexDomainDao compexDomainDao;
	
	@Resource
	private SeqcodeService seqcodeService;

	public CatalogDao getCatalogDao() {
		return catalogDao;
	}

	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	@Override
	public void doSaveCatalog(Catalog catalog) {
		this.catalogDao.insert(catalog);
	}

	@Override
	public void doDeleteCatalog(Long id) {
		this.catalogDao.delete(id);
	}

	@Override
	public void doUpdateCatalog(Catalog catalog) {
		this.catalogDao.update(catalog);
	}

	@Override
	public Catalog findCatalogById(Long id) {
		return this.catalogDao.selectById(id);
	}

	@Override
	public List<Catalog> queryCatalog(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select a.*,b.tbl_name as tbl_parentName from sys_catalog a left join sys_catalog b on a.tbl_parentId=b.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if ("tbl_parentName".equals(entry.getKey())) {
				sql.append(" and b.tbl_name like ? ");
			} else {
				sql.append(" and a." + entry.getKey() + " like ? ");
			}
			param.add(entry.getValue());
		}
		sql.append("order by a.tbl_parentId desc,a.tbl_orderNum asc");
		return this.catalogDao.select(sql.toString(), param.toArray(),
				qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	public int countCatalog() {
		return this.catalogDao.count();
	}

	@Override
	public void doDeleteCatalogs(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			this.catalogDao.delete(id);
		}
	}

	@Override
	public String getTreeCatalog(String rootId, boolean isOnclick,
			boolean showRoot) {
		Map<String, Catalog> tree = new TreeMap<String, Catalog>();

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("ul");
		root.addAttribute("class", "tree treeFolder");

		String sql = "select a.*,b.tbl_name as tbl_parentName from sys_catalog a left join sys_catalog b on a.tbl_parentId=b.id order by a.tbl_parentId asc,tbl_orderNum asc";
		List catalogList = this.catalogDao.select(sql, new Object[] {}, 0, -1);
		for (int i = 0; i < catalogList.size(); i++) {
			Catalog node = (Catalog) (catalogList.get(i));
			treeBuilding(tree, node, rootId);
		}

		htmlBuilding(tree, rootId, root, isOnclick, showRoot);
		String xml = doc.asXML();
		String html = xml.substring(xml.indexOf("?>") + 2);
		return html;
	}

	/**
	 * 生成树结构的IXML
	 * 
	 * @param tree
	 * @param key
	 * @param root
	 */
	private void htmlBuilding(Map<String, Catalog> tree, String key, Element root,
			boolean isOnclick, boolean showRoot) {
		Catalog node = tree.get(key);
		if (node.getId() != 1 || showRoot) {
			Element nodeRoot = null;

			nodeRoot = root.addElement("li");
			Element aNode = nodeRoot.addElement("a");
			if (!catalogDao.isHasNode(node.getId())) {
				if (isOnclick) {
					aNode.addAttribute("onclick",
							"setCatalog('catalog" + node.getId()
									+ "','catalogName" + node.getId() + "');");
					Element inputElement = aNode.addElement("input");
					inputElement.addAttribute("id", "catalog" + node.getId());
					inputElement.addAttribute("value", node.getId().toString());
					inputElement.addAttribute("style", "display:none;");
					Element nameElement = aNode.addElement("input");
					nameElement
							.addAttribute("id", "catalogName" + node.getId());
					nameElement.addAttribute("value", node.getName());
					nameElement.addAttribute("style", "display:none;");
				} else {
					aNode.addAttribute("href", node.getPath());
					aNode.addAttribute("target", "navTab");
					aNode.addAttribute("rel", "main");
				}
			}
			aNode.setText(node.getName());
			if (catalogDao.isHasNode(node.getId())) {
				if (isOnclick) {
					aNode.addAttribute("onclick",
							"setCatalog('catalog" + node.getId()
									+ "','catalogName" + node.getId() + "');");
					Element inputElement = aNode.addElement("input");
					inputElement.addAttribute("id", "catalog" + node.getId());
					inputElement.addAttribute("value", node.getId().toString());
					inputElement.addAttribute("style", "display:none;");
					Element nameElement = aNode.addElement("input");
					nameElement
							.addAttribute("id", "catalogName" + node.getId());
					nameElement.addAttribute("value", node.getName());
					nameElement.addAttribute("style", "display:none;");
				} else {
					if (node.getPath() != null && !"".equals(node.getPath())) {
						aNode.addAttribute("href", node.getPath());
						aNode.addAttribute("target", "navTab");
						aNode.addAttribute("rel", "main");
					}
				}
				nodeRoot = nodeRoot.addElement("ul");
				Set<Catalog> childrenCatalog = node.getChildren();
				if (childrenCatalog != null) {
					for (Iterator<Catalog> children = childrenCatalog
							.iterator(); children != null && children.hasNext();) {
						Catalog child = children.next();
						htmlBuilding(tree, String.valueOf(child.getId()), nodeRoot, isOnclick,
								showRoot);
					}
				}
			}
		} else {
			if (catalogDao.isHasNode(node.getId())) {
				Set<Catalog> childrenCatalog = node.getChildren();
				if (childrenCatalog != null) {
					for (Iterator<Catalog> children = childrenCatalog
							.iterator(); children.hasNext();) {
						Catalog child = children.next();
						htmlBuilding(tree, String.valueOf(child.getId()), root, isOnclick,
								showRoot);
					}
				}
			}
		}
	}

	/**
	 * 将所有节点都以BPNode对象存到Map中
	 * 
	 * @param tree
	 * @param node
	 * @param parentId
	 */
	private void treeBuilding(Map<String, Catalog> tree, Catalog node, String rootId) {
		if (!rootId.equals(node.getId())) { // 节点是不是根节点
			Catalog parent = tree.get(node.getParentId());// 取出父节点
			if (parent == null) {
				return;
			}
			node.setParent(parent); // 设置父节点
			Set<Catalog> children = parent.getChildren();// 取出父节点的孩子
			if (children == null) { // 如果没有孩子
				children = new LinkedHashSet<Catalog>(); // 则创建一个孩子序列
				parent.setChildren(children);
			}
			children.add(node); // 将自己添加到队列中
		}
		tree.put(String.valueOf(node.getId()), node);
	}

	public List<Catalog> getSysCatalog(){
		return this.getSysCatalogTree();
	}
	
	public List<Catalog> getSysCatalogTree(){
		return catalogDao.getSysCatalogTree();
	}

	@Override
	public void doUpdateCatalogList(Long currentSaveId, Long listId) {
		catalogDao.doUpdateCatalogList(currentSaveId,listId);
	}

	@Override
	@Cacheable(value="resourceCache",key="'findCatalogByListId:'+#tabulationId")
	public Catalog findCatalogByListId(Long tabulationId) {
		return catalogDao.findCatalogByListId(tabulationId);
	}

	@Override
	public List useListCount(String listid, Long id) {
		return catalogDao.useListCount(listid,id);
	}

	@Override
	public void doUpdateOrder(Catalog catalog) {
		catalogDao.updateOrder(catalog);
	}

	@Override
	public String buildAllCatalogSql(String catalogId) {
		Catalog catalog = catalogDao.selectById(Long.parseLong(catalogId));
		Long tabulationId = catalog.getTabulationId();
		Tabulation tabulation = tabulationDao.selectById(tabulationId);
		Form form = formDao.selectById(tabulation.getFormId());
		List<Tab> tabs = form.getTabs();
		String model = form.getMainTable();
		Long modelId = form.getTableId();
		
		//插入sys_tables sql
		StringBuilder result = new StringBuilder();
		Map modelTableMap = tableDao.findTableById(modelId);
		String modelTableId = modelTableMap.get("id").toString();
		Object[] args = new Object[]{"sys_tables"};
		String columnSql = "select a.*,b.tbl_tableZhName as tbl_tableZhName,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_columns a left join sys_tables b on a.tbl_tableId=b.id left join sys_user u on a.comm_createBy = u.id   left join sys_user u2 on a.comm_updateBy = u2.id  ";
		List<Column> tableColumns = columnDao.select(columnSql + " where a.tbl_belongTable=?", args, 0, -1);
        result.append(buildInsertSql("sys_tables",modelTableMap,tableColumns)).append("####");
		
		//插入sys_columns sql
		Object[] columnArgs = new Object[]{"sys_columns"};
		List<Column> columnColumns = columnDao.select(columnSql + " where a.tbl_belongTable=?", columnArgs, 0, -1);
		List<Map<String,Object>> modelColumns = columnDao.findColumnsByTableId(modelTableId);
		for(Map<String,Object> map:modelColumns) {
	        result.append(buildInsertSql("sys_columns",map,columnColumns)).append("####");
		}
		
		//model table create sql
		StringBuffer createSql = new StringBuffer("CREATE TABLE " + model + " (id varchar(50) NOT NULL,");
		Object[] modelArgs = new Object[]{modelId};
		List<Column> modelColumnList = columnDao.select(columnSql + " where a.tbl_tableId=?", modelArgs, 0, -1);
		for(Column column:modelColumnList){
			if (column.getDataType().equals("varchar") || column.getDataType().equals("int") || column.getDataType().equals("bigint") || column.getDataType().equals("number")) {
				createSql.append(column.getColumnName() + " " + column.getDataType()+"("+column.getLength()+") DEFAULT "+("".equals(column.getDefaultValue())?"NULL":column.getDefaultValue())+",");
			} else if (column.getDataType().equals("date") || column.getDataType().equals("timestamp")) {
				createSql.append(column.getColumnName() + " timestamp NULL DEFAULT NULL,");
			}
		}
		createSql.append("PRIMARY KEY (id)) \r\n");
		result.append(createSql).append("####");
		
		//model data sql
		List modelDatas = compexDomainDao.queryTableData(model, null);
		for(Object obj:modelDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql(model,map,modelColumnList)).append("####");
		}
		
		//model form sql
		List modelFormDatas = compexDomainDao.queryTableData("sys_biaodan", "id:"+form.getId());
		Object[] modelFormArgs = new Object[]{"sys_biaodan"};
		List<Column> modelFormList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelFormArgs, 0, -1);
		for(Object obj:modelFormDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_biaodan",map,modelFormList)).append("####");
		}
		
		//model form tab sql
		List modelFormTabDatas = compexDomainDao.queryTableData("sys_biaodantab", "tbl_form:"+form.getId());
		Object[] modelFormTabArgs = new Object[]{"sys_biaodantab"};
		List<Column> modelFormTabList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelFormTabArgs, 0, -1);
		for(Object obj:modelFormTabDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_biaodantab",map,modelFormTabList)).append("####");
		}
		
		//model form template sql
		for(Tab tab:tabs){
			Long tempId = tab.getTemplateId();
			Integer tempType = tab.getTemplateType();
			if(tempType==0) {
				List modelTemplateDatas = compexDomainDao.queryTableData("sys_template", "id:"+tempId);
				Object[] modelTemplateArgs = new Object[]{"sys_template"};
				List<Column> modelTemplateList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelTemplateArgs, 0, -1);
				for(Object obj:modelTemplateDatas){
					Map map = (Map)obj;
					result.append(buildInsertSql("sys_template",map,modelTemplateList)).append("####");
				}
			}
			if(tempType==1) {
				List modelTemplateDatas = compexDomainDao.queryTableData("sys_templatecomb", "id:"+tempId);
				Object[] modelTemplateArgs = new Object[]{"sys_templatecomb"};
				List<Column> modelTemplateList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelTemplateArgs, 0, -1);
				for(Object obj:modelTemplateDatas){
					Map map = (Map)obj;
					result.append(buildInsertSql("sys_templatecomb",map,modelTemplateList)).append("####");
					//分区
					String tempCombId = (String)map.get("id");
					List modelPartitionDatas = compexDomainDao.queryTableData("sys_partition", "tbl_templateid:"+tempCombId);
					Object[] modelPartitionArgs = new Object[]{"sys_partition"};
					List<Column> modelPartitionList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelPartitionArgs, 0, -1);
					for(Object pobj:modelPartitionDatas){
						Map pmap = (Map)pobj;
						result.append(buildInsertSql("sys_partition",pmap,modelPartitionList)).append("####");
						
						//分区中包含的模板
						String baseTempId = (String)pmap.get("tbl_basetemplate");
						List modelBaseTemplateDatas = compexDomainDao.queryTableData("sys_template", "id:"+baseTempId);
						Object[] modelBaseTemplateArgs = new Object[]{"sys_template"};
						List<Column> modelBaseTemplateList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelBaseTemplateArgs, 0, -1);
						for(Object btobj:modelBaseTemplateDatas){
							Map btmap = (Map)btobj;
							result.append(buildInsertSql("sys_template",btmap,modelBaseTemplateList)).append("####");
						}
					}
				}
			}
		}
		
		//model form button sql
		List modelFormButtonDatas = compexDomainDao.queryTableData("sys_biaodanbutton", "tbl_formid:"+form.getId());
		Object[] modelFormButtonArgs = new Object[]{"sys_biaodanbutton"};
		List<Column> modelFormButtonList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelFormButtonArgs, 0, -1);
		for(Object obj:modelFormButtonDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_biaodanbutton",map,modelFormButtonList)).append("####");
		}
		
		//model form sheji sql
		List modelFormShejiDatas = compexDomainDao.queryTableData("sys_biaodansheji", "tbl_form:"+form.getId());
		Object[] modelFormShejiArgs = new Object[]{"sys_biaodansheji"};
		List<Column> modelFormShejiList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelFormShejiArgs, 0, -1);
		for(Object obj:modelFormShejiDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_biaodansheji",map,modelFormShejiList)).append("####");
		}
		
		//model tabulation sql
		List modelTabulationDatas = compexDomainDao.queryTableData("sys_liebiao", "id:"+tabulationId);
		Object[] modelTabulationArgs = new Object[]{"sys_liebiao"};
		List<Column> modelTabulationList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelTabulationArgs, 0, -1);
		for(Object obj:modelTabulationDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_liebiao",map,modelTabulationList)).append("####");
		}
		
		//model tabulation button sql
		List modelTabulationButtonDatas = compexDomainDao.queryTableData("sys_liebiaobutton", "tbl_tabulation:"+tabulationId);
		Object[] modelTabulationButtonArgs = new Object[]{"sys_liebiaobutton"};
		List<Column> modelTabulationButtonList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelTabulationButtonArgs, 0, -1);
		for(Object obj:modelTabulationButtonDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_liebiaobutton",map,modelTabulationButtonList)).append("####");
		}
		
		//model tabulation opr sql
		List modelTabulationOprDatas = compexDomainDao.queryTableData("sys_oprtbutton", "tbl_tabulation:"+tabulationId);
		Object[] modelTabulationOprArgs = new Object[]{"sys_oprtbutton"};
		List<Column> modelTabulationOprList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelTabulationOprArgs, 0, -1);
		for(Object obj:modelTabulationOprDatas){
			Map map = (Map)obj;
			result.append(buildInsertSql("sys_oprtbutton",map,modelTabulationOprList)).append("####");
		}
		
		//model catalog sql
		List modelCatalogDatas = compexDomainDao.queryTableData("sys_catalog", "id:"+catalogId);
		Object[] modelCatalogArgs = new Object[]{"sys_catalog"};
		List<Column> modelCatalogList = columnDao.select(columnSql + " where a.tbl_belongTable=?", modelCatalogArgs, 0, -1);
		for(Object obj:modelCatalogDatas){
			Map map = (Map)obj;
			map.put("tbl_parentId","290");
			result.append(buildInsertSql("sys_catalog",map,modelCatalogList)).append("####");
		}
		
		//add catalog id
		result.append("catalogId=" + catalogId);
		return result.toString();
	}
	
	private String buildInsertSql(String tableName,Map map,List<Column> columns) {
		StringBuffer sql = new StringBuffer("insert "+tableName+" (");
		Set<Map.Entry<String, Object>> set = map.entrySet();
        StringBuffer value = new StringBuffer(" values(");
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            sql.append(entry.getKey()+",");
            if(entry.getKey().equals("id")) {
            	value.append("'"+entry.getValue() + "',");
            }else{
	            for(Column column:columns) {
	            	if(column.getColumnName().trim().equals(entry.getKey())){
	            		if(column.getDataType().equals("int") || column.getDataType().equals("bigint") || column.getDataType().equals("long") || column.getDataType().equals("number")) {
	            			value.append((entry.getValue()!=null?entry.getValue().toString():"null")+",");
	            		}else{
	            			value.append((entry.getValue()!=null?"'"+entry.getValue()+"'":"null")+",");
	            		}
	            		break;
	            	}
	    		}
            }
        }
		sql.deleteCharAt(sql.length()-1)
			.append(")")
			.append(value.deleteCharAt(value.length()-1))
			.append(")")
			.append("\r\n");
		return sql.toString();
	}
	
	public void doExecuteSqlFile(String path) throws AppException {
		int number = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(path)));
			String b = "";
			int i=0;
			StringBuffer sum = new StringBuffer("");
			while (br.ready()) {
				sum.append((char) br.read());
            }
			br.close();
			if(!"".equals(sum.toString())) {
				String[] sqls = sum.toString().split("####");
				String catalogId = null;
				for(String sql:sqls) {
					number = ++i;
					if(sql.contains("sys_template")) {
						try {
							catalogDao.executeSql(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(sql.contains("catalogId=")){
						catalogId  = sql.substring(10);
					}else{
						catalogDao.executeSql(sql);
					}
				}
				
				if(catalogId != null) {
					seqcodeService.doKnowledgeReuse(new Object[]{catalogId}, "1");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("SQL文件第" + number + "句执行错误！");
		}
	}

	@Override
	public void doDeleteAllCatalog(Long id) {
		Catalog catalog = catalogDao.selectById(id);
		Long tabulationId = catalog.getTabulationId();
		Tabulation tabulation = tabulationDao.selectById(tabulationId);
		Long formId = tabulation.getFormId();
		Form form = formDao.selectById(formId);
		Long tableId = form.getTableId();	
		String sql = "select count(*) from sys_biaodan where tbl_zhubiaoid=?";
		int tableCount = compexDomainDao.count(sql, new Object[]{tableId});
		
		//删除目录
		catalogDao.delete(id);
		//删除列表
		if(tabulationId != null && !tabulationId.equals("")){
			tabulationDao.delete(tabulationId);
		}
		//删除表单
		if(formId != null && !formId.equals("")){
			formDao.delete(formId);
		}
		
		if(tableCount==1) {
			//删除字段
			if(tableId != null && !tableId.equals("")){
				columnDao.deleteByTableId(tableId);
			}
			//删除表
			if(tableId != null && !tableId.equals("")){
				tableDao.delete(tableId);
			}
		}
	}

	@Override
	public Catalog findCatalogByFormId(Long id) {
		return catalogDao.getCatalogByFormId(id);
	}
	
}
