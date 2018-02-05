package com.cloudstong.platform.resource.tree.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.service.ColumnService;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.metadata.service.TableService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.template.model.TreeShowForm;
import com.cloudstong.platform.resource.template.vo.TemplateColumn;
import com.cloudstong.platform.resource.tree.model.KMap;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.resource.tree.service.MgrTreeService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:树模板Action
 */
public class MgrTreeAction extends CompexDomainAction {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 树模板
	 */
	private MgrTree mgrTree;
	
	/**
	 * 缓存树模板
	 */
	public static MgrTree TEMPLATETREE;

	/**
	 * 显示树表单
	 */
	private TreeShowForm treeShowForm;

	/**
	 * 表属性集合
	 */
	private List tables;
	
	/**
	 * 表属性集合
	 */
	private List tablesBoth;

	/**
	 * 字段集合
	 */
	private List columnList;

	/**
	 * 字段下拉框类型
	 */
	private String selectType;

	/**
	 * 树类型集合
	 */
	private List treeTypes;

	/**
	 * 按钮名称
	 */
	private String buttonName;

	/**
	 * 父节点ID
	 */
	private String parentId;

	/**
	 * 父节点名称
	 */
	private String parentName;

	/**
	 * 判断是【新增】还是【修改】的标示符
	 */
	private String op;

	/**
	 * 操作表属性的服务接口,<code>tableService</code> 对象是TableService接口的一个实例
	 */
	@Resource
	private TableService tableService;
	/**
	 * 操作列属性的服务接口,<code>columnService</code> 对象是ColumnService接口的一个实例
	 */
	@Resource
	private ColumnService columnService;
	/**
	 * 操作树模板的服务接口,<code>mgrTreeService</code> 对象是MgrTreeService接口的一个实例
	 */
	@Resource
	private MgrTreeService mgrTreeService;

	/**
	 * 操作代码的服务接口,<code>dicService</code> 对象是DictionaryService接口的一个实例
	 */
	@Resource
	private DictionaryService dicService;
	
	/**
	 * 表二数据列
	 */
	private List columnListChild;
	
	/**
	 * 操作配置模块的服务接口,<code>compexDomainService</code> 对象是CompexDomainService接口的一个实例
	 */
	@Resource
	private CompexDomainService compexDomainService;

	/**
	 * 表二名称
	 */
	private String tableChildName;
	
	/**
	 * 表二显示字段
	 */
	private String disColumnIdChildName;
	
	/**
	 * 表二顺序字段
	 */
	private String orderColumnIdChildName;
	
	/**
	 * 表二关联字段
	 */
	private String parentColumnIdChildName;
	
	/**
	 * 选择名称
	 */
	private String choiseName;
	/**
	 * 选择ID
	 */
	private String choiseId;
	
	/**
	 * 操作数ID
	 */
	private Long treeId;
	/**
	 * Description:获取树类型选择列表数据
	 * @return 树类型列表数据
	 */
	public List fetchTreeTypesAction() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		List result = this.dicService.queryByParent(1455908064L);
		return result;
	}

	/**
	 * Description:获取在显示树之前需要加载的参数值,返回显示树表单的json对象
	 * @return forward
	 */
	public String fetchShowTreeParamAction() {
		try {
			String tableId = getRequest().getParameter("tableId");
			Long tId = Long.valueOf(tableId);
			String columnId = this.getRequest().getParameter("columnId");
			Long cId = Long.valueOf(columnId);
			String disColumnId = this.getRequest().getParameter("disColumnId");
			Long dId = Long.valueOf(disColumnId);
			String orderColumnId = this.getRequest().getParameter(
					"orderColumnId");
			Long oId = Long.valueOf(orderColumnId);
			String idStr = this.getRequest().getParameter("id");
			Long id = null;
			if (idStr != null && !idStr.isEmpty()) {
				id = Long.valueOf(idStr);
			}

			treeShowForm = mgrTreeService.doSearchTreeParam(tId, cId, dId,oId, id);
			rootId = treeShowForm.getRoot();
			belongTable = treeShowForm.getModel();
			nameColumn = treeShowForm.getNameColumn();
			parentColumn = treeShowForm.getParentIdColumn();
			orderColumn = treeShowForm.getOrderColumn();
			showRoot = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "treeParam";
	}

	/**
	 * Description:显示树
	 * @return forward
	 */
	public String fetchShowTreeParamById() {
		treeId = Long.valueOf(getRequest().getParameter("treeId"));
		MgrTree mgrTree = mgrTreeService.findMgrTreeById(treeId);
		if(mgrTree.getTableType() != null && mgrTree.getTableType().equals("1")){
			choiseName = getRequest().getParameter("parentName");
			choiseId = getRequest().getParameter("parentId");
			if(mgrTree.getType()==4L){
				return "personPage";
			}else{
				return "userPage";
			}
			
		}else{
			rootId = mgrTree.getRootId();
			belongTable = mgrTree.getTableName();//mgrTreeService.getValueByParam("", "", mgrTree.getTableId());
			nameColumn = mgrTree.getDisColumnName();
			parentColumn = mgrTree.getParentColumnName();
			orderColumn = mgrTree.getOrderColumnName();
			showRoot = true;
			return "treeParam";
		}
		
	}

	/**
	 * Description:显示树--类型2
	 * @return forward
	 */
	public String fetchShowTreeParam() {
		return "treeParam";
	}
	
	/* 
	 * 显示树模板记录列表
	 */
	public String list() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		this.treeTypes = this.dicService.queryByParent(1455908064L);
		return super.list();
	}

	/* 
	 * 打开树模板新建页面
	 */
	public String add() {
		return super.add();
	}
	
	public String addsingle() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			addInit(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addsingle";
	}
	
	public String addboth() {
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			addInit(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addboth";
	}
	
	private void addInit(SysUser user){
		queryCriteria = new QueryCriteria();
		
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tablesBoth = tableService.queryTable(queryCriteria);
		queryCriteria.addQueryCondition("tbl_tableType", 4);
		tables = tableService.queryTable(queryCriteria);
		treeTypes = this.dicService.queryByParent(1455908064L);
	}
	/**
	 * Description:初始化树类型集合
	 */
	private void initTypeName() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		if (this.treeTypes == null) {
			this.treeTypes = this.dicService.queryByParent(1455908064L);
		}
		Long tId = this.mgrTree.getType();
		for (int i = 0; i < this.treeTypes.size(); i++) {
			Dictionary type = (Dictionary) this.treeTypes.get(i);
			Long id = type.getId();
			if (id.equals(tId)) {
				this.mgrTree.setTypeName(type.getName());
			}
		}
	}

	/* 
	 * 保存树模板
	 */
	public String save(){
		HttpServletRequest request = getRequest();
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		try {
			//判断树名称是否重复
			List list=compexDomainService.findReplyResult("sys_tree","tbl_compname",mgrTree.getTreename(),mgrTree.getId());
			if(list.size()>0){
				printJSON("300","树名称 不允许重复，请修改后重新提交！", false);
				return NONE;
			}
			Long _mgrTreeId;
			if (mgrTree.getId() != null && !mgrTree.getId().equals("")) {
				mgrTree.setUpdateBy(user.getId());
				mgrTree.setUpdateDate(new Date());
				this.mgrTreeService.doUpdateMgrTree(mgrTree);
				_mgrTreeId=mgrTree.getId();
			} else {
				mgrTree.setCreateBy(user.getId());
				mgrTree.setCreateDate(new Date());
				mgrTree.setUpdateBy(user.getId());
				mgrTree.setUpdateDate(new Date());
				initTypeName();
				_mgrTreeId = this.mgrTreeService.doSaveMgrTree(mgrTree);
			}
			printJSON("success", false, _mgrTreeId.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * Description:解析params参数
	 * @return 树模板ID
	 */
	private Long parseParamsForID() {
		if (this.params.isEmpty()) {
			return null;
		}
		Long idStr = Long.valueOf(this.params.substring(this.params.indexOf(":") + 1,
				this.params.length() - 1));
		return idStr;
	}

	/* 
	 * 查询树模板表单页面
	 */
	public String view() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		if (this.treeTypes == null || this.treeTypes.isEmpty()) {
			this.treeTypes = this.dicService.queryByParent(1455908064L);
		}
		this.mgrTree = this.mgrTreeService
				.findMgrTreeById(parseParamsForID());
		queryCriteria = new QueryCriteria();
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tables = tableService.queryTable(queryCriteria);
		tableChildName=tableService.findTableById(mgrTree.getTableIdChild()).getTableZhName(); 
		
		Column column = columnService.findColumnById(mgrTree.getOrderColumnIdChild());
		orderColumnIdChildName=column.getColumnZhName();
		column = columnService.findColumnById(mgrTree.getDisColumnIdChild());
		disColumnIdChildName=column.getColumnZhName();
		column = columnService.findColumnById(mgrTree.getParentColumnIdChild());
		parentColumnIdChildName=column.getColumnZhName();
		
		return super.view();
	}

	/* 
	 * 编辑树模板表单页面
	 */
	public String edit() throws Exception {
		mgrTree = this.mgrTreeService
				.findMgrTreeById(parseParamsForID());
		TEMPLATETREE = mgrTree;
		return super.edit();
	}

	public String editsingle() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		editInit(user);
		return "editsingle";
	}
	
	public String editboth() throws IOException {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		editInit(user);
		return "editboth";
	}
	
	private void editInit(SysUser user){
		queryCriteria = new QueryCriteria();
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tablesBoth = tableService.queryTable(queryCriteria);
			
		mgrTree = TEMPLATETREE;
		Long tableId = mgrTree.getTableId();
		Long tableIdChild = mgrTree.getTableIdChild();
		columnList = mgrTreeService.getColumnAll(tableId);
		columnListChild = mgrTreeService.getColumnAll(tableIdChild);
		queryCriteria = new QueryCriteria();
		queryCriteria.setCurrentIndex(0);
		queryCriteria.setPageSize(-1);
		tables = tableService.queryTable(queryCriteria);
		treeTypes = this.dicService.queryByParent(1455908064L);
	}
	/**
	 * Description:根据字段下拉框类型返回拼好的字段select字符串
	 * @return NONE
	 * @throws IOException
	 */
	public String getColumnParent() throws IOException {
		try {
			columnList = mgrTreeService.getColumnAll(id);
			StringBuffer sb = null;
			if ("parentIdColumn".equals(this.selectType)) {
				sb = new StringBuffer(
						"<select id=\"parentColumnId\" name=\"mgrTree.parentColumnId\" style=\"width:186px\" onchange=\"genPath();\" >");
			} else if ("nameColumn".equals(this.selectType)) {
				sb = new StringBuffer(
						"<select id=\"disColumnId\" name=\"mgrTree.disColumnId\" style=\"width:186px\" onchange=\"genPath();\" >");
			} else if ("paiXu".equals(this.selectType)) {
				sb = new StringBuffer(
						"<select id=\"orderColumnId\" name=\"mgrTree.orderColumnId\" style=\"width:186px\" onchange=\"genPath();\" >");
			}
			for (int i = 0; i < columnList.size(); i++) {
				if (this.mgrTree == null) { // 新增的情况
					if (i == 0) {
						sb.append("<option selected=selected value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					} else {
						sb.append("<option value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					}
				} else { // 修改的情况
					if (this.mgrTree.getParentColumnId().equals(
							((TemplateColumn) columnList.get(i)).getId())
							|| this.mgrTree.getDisColumnId().equals(
									((TemplateColumn) columnList.get(i)).getId())
							|| this.mgrTree.getOrderColumnId().equals(
									((TemplateColumn) columnList.get(i)).getId())) {
						sb.append("<option selected=selected value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					} else {
						sb.append("<option value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					}
				}
			}

			sb.append("</select>");
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * Description:根据字段下拉框类型返回拼好的字段select字符串
	 * @return NONE
	 * @throws IOException
	 */
	public String getColumnParentChild() throws IOException {
		try {
			columnList = mgrTreeService.getColumnAll(id);
			StringBuffer sb = null;
			if ("parentIdColumn".equals(this.selectType)) {
				sb = new StringBuffer(
						"<select id=\"parentColumnIdChild\" name=\"mgrTree.parentColumnIdChild\" style=\"width:186px\">");
			} else if ("nameColumn".equals(this.selectType)) {
				sb = new StringBuffer(
						"<select id=\"disColumnId\" name=\"mgrTree.disColumnIdChild\" style=\"width:186px\">");
			} else if ("paiXu".equals(this.selectType)) {
				sb = new StringBuffer(
						"<select id=\"orderColumnIdChild\" name=\"mgrTree.orderColumnIdChild\" style=\"width:186px\">");
			}
			for (int i = 0; i < columnList.size(); i++) {
				if (this.mgrTree == null) { // 新增的情况
					if (i == 0) {
						sb.append("<option selected=selected value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					} else {
						sb.append("<option value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					}
				} else { // 修改的情况
					if (this.mgrTree.getParentColumnId().equals(
							((TemplateColumn) columnList.get(i)).getId())
							|| this.mgrTree.getDisColumnId().equals(
									((TemplateColumn) columnList.get(i)).getId())
							|| this.mgrTree.getOrderColumnId().equals(
									((TemplateColumn) columnList.get(i)).getId())) {
						sb.append("<option selected=selected value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					} else {
						sb.append("<option value=\""
								+ ((TemplateColumn) columnList.get(i)).getId()
								+ "\">"
								+ ((TemplateColumn) columnList.get(i))
										.getColumnZhName() + "</option>");
					}
				}
			}

			sb.append("</select>");
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public MgrTree getMgrTree() {
		return mgrTree;
	}

	public void setMgrTree(MgrTree mgrTree) {
		this.mgrTree = mgrTree;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List getTables() {
		return tables;
	}

	public void setTables(List tables) {
		this.tables = tables;
	}

	public List getColumnList() {
		return columnList;
	}

	public void setColumnList(List columnList) {
		this.columnList = columnList;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public TreeShowForm getTreeShowForm() {
		return treeShowForm;
	}

	public void setTreeShowForm(TreeShowForm treeShowForm) {
		this.treeShowForm = treeShowForm;
	}

	public List getTreeTypes() {
		return treeTypes;
	}

	public void setTreeTypes(List treeTypes) {
		this.treeTypes = treeTypes;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTableChildName() {
		return tableChildName;
	}

	public void setTableChildName(String tableChildName) {
		this.tableChildName = tableChildName;
	}

	public String getDisColumnIdChildName() {
		return disColumnIdChildName;
	}

	public void setDisColumnIdChildName(String disColumnIdChildName) {
		this.disColumnIdChildName = disColumnIdChildName;
	}

	public String getOrderColumnIdChildName() {
		return orderColumnIdChildName;
	}

	public void setOrderColumnIdChildName(String orderColumnIdChildName) {
		this.orderColumnIdChildName = orderColumnIdChildName;
	}

	public String getParentColumnIdChildName() {
		return parentColumnIdChildName;
	}

	public void setParentColumnIdChildName(String parentColumnIdChildName) {
		this.parentColumnIdChildName = parentColumnIdChildName;
	}

	public List getColumnListChild() {
		return columnListChild;
	}

	public void setColumnListChild(List columnListChild) {
		this.columnListChild = columnListChild;
	}

	public String getChoiseName() {
		return choiseName;
	}

	public void setChoiseName(String choiseName) {
		this.choiseName = choiseName;
	}

	public String getChoiseId() {
		return choiseId;
	}

	public void setChoiseId(String choiseId) {
		this.choiseId = choiseId;
	}


	public List getTablesBoth() {
		return tablesBoth;
	}

	public void setTablesBoth(List tablesBoth) {
		this.tablesBoth = tablesBoth;
	}
	
	/**
	 * 根节点的ID值
	 */
	private Long rootId;
	
	/**
	 * 要生成树的所属表
	 */
	private String belongTable;
	
	/**
	 * 树的显示字段
	 */
	private String nameColumn;
	
	/**
	 * 父节点字段
	 */
	private String parentColumn;
	
	/**
	 * 树节点的排序字段
	 */
	private String orderColumn;
	
	/**
	 * 节点的排序方式
	 */
	private String orderType;
	
	/**
	 * 树是否展开
	 */
	private boolean expand;
	
	/**
	 * 如果展开,定义展开的级数,默认为1级
	 */
	private int level;
	
	/**
	 * 是否显示根节点
	 */
	private boolean showRoot;
	
	public String showCatalogTree() throws Exception{
//		SysUser user = (SysUser)getSession().getAttribute("user");
//		Map<String, Seqcode> curUserSeqcode= user.getCurUserSeqcode();
		
		List<KMap> resutList = new ArrayList<KMap>();
		List<Map> lstTmp = mgrTreeService.queryTreeByParam(belongTable, orderColumn, orderType);
		
		for(int k=0; k<lstTmp.size(); k++) {
			Map cmap = lstTmp.get(k);
			Map ztree = null;
			Long hasJoinTree =null;
			String nodeId = "";
			boolean hasAuth = true;
			String nodeName = "";
			String nodeCss = "";
			Long parentId = Long.valueOf(cmap.get(parentColumn).toString());
			if(parentId == 1L){
				KMap kmap = new KMap();
				List<Map> _lstResult = dataFilter(lstTmp, (Long)cmap.get("id"), parentColumn, false);
				List<Map> _lstZtree = new ArrayList<Map>();
				if (_lstResult != null && _lstResult.size() > 0) {
					for (int i = 0; i < _lstResult.size(); ++i) {
						Map mapEntries = (Map) _lstResult.get(i);
						Iterator itEntries = mapEntries.entrySet().iterator();
						ztree = new HashMap();
						hasJoinTree =-1L;
						nodeId = "";
						hasAuth = true;
						nodeName = "";
						nodeCss = "";
						while (itEntries.hasNext()) {
							Map.Entry entry = (Map.Entry) itEntries.next();
							String _sKey = "";
							String _sValue = "";
							if (entry.getKey() != null && entry.getValue() != null) {
								_sKey = entry.getKey().toString();
								_sValue = entry.getValue().toString();
								if(_sKey.equalsIgnoreCase("id")){
									ztree.put("id", _sValue);
									ztree.put("belong", _sValue);
									nodeId = _sValue;
								}else if(_sKey.equalsIgnoreCase(nameColumn)){
									nodeName = _sValue;
								}else if(_sKey.equalsIgnoreCase(parentColumn)){
									ztree.put("pId", _sValue);
								}else if(belongTable.equalsIgnoreCase("sys_catalog")){
									if(_sKey.equalsIgnoreCase("tbl_path") && _sValue != null){
										ztree.put("path", _sValue);
									}else if(_sKey.equalsIgnoreCase("tbl_treeid") && _sValue != null && !_sValue.equals("-1")){
										hasJoinTree =Long.valueOf(_sValue);
									}else if(!showRoot && _sKey.equalsIgnoreCase("tbl_ziti")){
										nodeCss += "font-family:"+_sValue+";";
									}else if(!showRoot && _sKey.equalsIgnoreCase("tbl_zihao")){
										nodeCss += "font-size:"+_sValue+"px;";
									}else if(!showRoot && _sKey.equalsIgnoreCase("tbl_yanse")){
										nodeCss += "color:"+_sValue+";";
									}
								}
							}
						}
						if(!showRoot && belongTable.equalsIgnoreCase("sys_catalog")){
							ztree.put("name", "<span style='"+nodeCss+"'>"+nodeName+"</span>");
						}else{
							ztree.put("name", nodeName);
						}
						if(hasAuth){
							try {
								if(hasJoinTree != -1L){
									MgrTree mgrTree = mgrTreeService.findMgrTreeById(hasJoinTree);
									List<Map> _lstZtreeTmp = getJoinTree(mgrTree, nodeId);
									for (Map map : _lstZtreeTmp) {
										_lstZtree.add(map);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							ztree.put("open", expand);
							
						}else{
							ztree.put("isHidden", true);
						}
						if(nodeId.equals("1")){
							if(showRoot){
								_lstZtree.add(ztree);
								ztree.put("open", true);
							}
						}else{
							_lstZtree.add(ztree);
						}
					}
				}
				kmap.setKey(cmap);
				kmap.setValue(_lstZtree);
				resutList.add(kmap);
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		Writer writer = (Writer) response.getWriter();
		writer.write(objectMapper.writeValueAsString(resutList));
		
		return NONE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String showtreeByUrl(){
		try {
			List<Map> lstTmp = mgrTreeService.queryTreeByParam(belongTable, orderColumn, orderType);
			List<Map> _lstResult = dataFilter(lstTmp, rootId, parentColumn, true);//获取根节点过滤掉所有不属于它下级的节点;
			List<Map> _lstZtree = new ArrayList<Map>();
			Map ztree = null;
			Long hasJoinTree =null;
			String nodeId = "";
			boolean hasAuth = true;
			String nodeName = "";
			String nodeCss = "";
			SysUser user = (SysUser)getSession().getAttribute("user");
//			Map<String, Seqcode> curUserSeqcode= user.getCurUserSeqcode();
			
			if (_lstResult != null && _lstResult.size() > 0) {
				for (int i = 0; i < _lstResult.size(); ++i) {
					Map mapEntries = (Map) _lstResult.get(i);
					Iterator itEntries = mapEntries.entrySet().iterator();
					ztree = new HashMap();
					hasJoinTree =null;
					nodeId = "";
					hasAuth = true;
					nodeName = "";
					nodeCss = "";
					while (itEntries.hasNext()) {
						Map.Entry entry = (Map.Entry) itEntries.next();
						String _sKey = "";
						String _sValue = "";
						if (entry.getKey() != null && entry.getValue() != null) {
							_sKey = entry.getKey().toString();
							_sValue = entry.getValue().toString();
							if(_sKey.equalsIgnoreCase("id")){
								ztree.put("id", _sValue);
								ztree.put("belong", _sValue);
								nodeId = _sValue;
							}else if(_sKey.equalsIgnoreCase(nameColumn)){
								nodeName = _sValue;
							}else if(_sKey.equalsIgnoreCase(parentColumn)){
								ztree.put("pId", _sValue);
							}else if(_sKey.equalsIgnoreCase("tbl_bianma")){
//								if(curUserSeqcode.get(_sValue) == null){
//									hasAuth = false;
//								}
							}else if(belongTable.equalsIgnoreCase("sys_catalog")){
								if(_sKey.equalsIgnoreCase("tbl_path") && _sValue != null){
									ztree.put("path", _sValue);
								}else if(_sKey.equalsIgnoreCase("tbl_treeid") && _sValue != null && !_sValue.equals("-1")){
									hasJoinTree =Long.valueOf(_sValue);
								}else if(!showRoot && _sKey.equalsIgnoreCase("tbl_ziti")){
									nodeCss += "font-family:"+_sValue+";";
								}else if(!showRoot && _sKey.equalsIgnoreCase("tbl_zihao")){
									nodeCss += "font-size:"+_sValue+"px;";
								}else if(!showRoot && _sKey.equalsIgnoreCase("tbl_yanse")){
									nodeCss += "color:"+_sValue+";";
								}
							}
						}
					}
					if(!showRoot && belongTable.equalsIgnoreCase("sys_catalog")){
						ztree.put("name", "<span style='"+nodeCss+"'>"+nodeName+"</span>");
					}else{
						ztree.put("name", nodeName);
					}
					if(hasAuth){
						try {
							if(hasJoinTree!=null && !hasJoinTree.equals(0L)){
								MgrTree mgrTree = mgrTreeService.findMgrTreeById(hasJoinTree);
								List<Map> _lstZtreeTmp = getJoinTree(mgrTree, nodeId);
								for (Map map : _lstZtreeTmp) {
									_lstZtree.add(map);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						ztree.put("open", expand);
						
					}else{
						ztree.put("isHidden", true);
					}
					if(nodeId.equals("1")){
						if(showRoot){
							_lstZtree.add(ztree);
							ztree.put("open", true);
						}
					}else{
						_lstZtree.add(ztree);
					}
				}
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			Writer writer = (Writer) response.getWriter();
			writer.write(objectMapper.writeValueAsString(_lstZtree));
			
			getSession().setAttribute("treeResult", _lstZtree);//用session缓存查询到的结果
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private List<Map> getJoinTree(MgrTree mgrTree, String nodeId){
		List<Form> lstForm = formService.queryFormByTableid(mgrTree.getTableId());
		String tabulationId = "";
		if(null != lstForm && lstForm.size()>0){
			List<Tabulation> lstTabulation = tabulationService.queryTabulationByFormid(lstForm.get(0).getId());
			if(null != lstTabulation && lstTabulation.size()>0){
				tabulationId = String.valueOf(lstTabulation.get(0).getId());
			}
		}
		Long rootId = mgrTree.getRootId();//根节点ID
		
		Table table = tableService.findTableById(mgrTree.getTableId());
		Column column = columnService.findColumnById(mgrTree.getOrderColumnId());//排序
		List<Map> _lstResult = mgrTreeService.queryTreeByParam(table.getTableName(), column.getColumnName(), orderType);
		
		String name = columnService.findColumnById(mgrTree.getDisColumnId()).getColumnName();//显示字段
		String parentName  = columnService.findColumnById(mgrTree.getParentColumnId()).getColumnName();//父节点字段
		
		List<Map> lstTmp = dataFilter(_lstResult, rootId, parentName, true);//获取根节点过滤掉所有不属于它下级的节点;
		
		List<Map> _lstZtree = new ArrayList<Map>();
		Map ztree = null;
		boolean isRoot = false;
		Long id = null;
		if (lstTmp != null && lstTmp.size() > 0) {
			for (int i = 0; i < lstTmp.size(); ++i) {
				Map mapEntries = (Map) lstTmp.get(i);
				Iterator itEntries = mapEntries.entrySet().iterator();
				ztree = new HashMap();
				isRoot = false;
				while (itEntries.hasNext()) {
					Map.Entry entry = (Map.Entry) itEntries.next();
					String _sKey = "";
					String _sValue = "";
					if (entry.getKey() != null && entry.getValue() != null) {
						_sKey = entry.getKey().toString();
						_sValue = entry.getValue().toString();
						if(_sKey.equals("id")){
							ztree.put("id", _sValue+"_"+nodeId);
							ztree.put("belong", _sValue+"_"+nodeId);
							id = Long.valueOf(_sValue);
							if(_sValue.equals(rootId)){
								isRoot = true;
							}
						}else if(_sKey.equals(name)){
							ztree.put("name", _sValue);
						}else if(_sKey.equals(parentName)){
							ztree.put("pId", _sValue+"_"+nodeId);
						}
					}
				}
				if(isRoot){
					itEntries = mapEntries.entrySet().iterator();
					while (itEntries.hasNext()) {
						Map.Entry entry = (Map.Entry) itEntries.next();
						String _sKey = "";
						String _sValue = "";
						if (entry.getKey() != null && entry.getValue() != null) {
							_sKey = entry.getKey().toString();
							_sValue = entry.getValue().toString();
							if(_sKey.equals(parentName)){
								ztree.put("pId", nodeId);
							}
						}
					}
				}
				ztree.put("path", "pages/system/joinlist.action?listId="+tabulationId+"&dyncMapString.tbl_parentId="+id);
				ztree.put("gjTree", true);
				_lstZtree.add(ztree);
			}
		}
		
		return _lstZtree;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> dataFilter(List<Map> lstResult, Long parentid, String parentName, boolean hasRoot){
		boolean flag = false;
		Long id = null;
		List<Map> lstJoinZtree = new ArrayList<Map>();
		for (int i = 0; i < lstResult.size(); ++i) {
			Map mapEntries = (Map) lstResult.get(i);
			Iterator itEntries = mapEntries.entrySet().iterator();
			flag = false; 
			id=null;
			while (itEntries.hasNext()) {
				Map.Entry entry = (Map.Entry) itEntries.next();
				String _sKey = "";
				String _sValue = "";
				if (entry.getKey() != null && entry.getValue() != null) {
					_sKey = entry.getKey().toString();
					_sValue = entry.getValue().toString();
					if(_sKey.equals("id") ){
						id = Long.valueOf(_sValue);
					}
					if(_sKey.equals(parentName) && parentid.equals(Long.valueOf(_sValue))){
						flag = true; 
					}
				}
			}
			if(id.equals(parentid) && hasRoot){
				lstJoinZtree.add(mapEntries);
			}
			if(flag){
				List<Map> lstTmp = dataFilter(lstResult, id, parentName, true);
				for (Map map : lstTmp) {
					lstJoinZtree.add(map);
				}
			}
		}
		return lstJoinZtree;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String menuChildren() throws IOException {
		List<Map> _lstResultTmp = (List<Map>)getSession().getAttribute("treeResult");
		List<Map> _lstResult = dataFilter(_lstResultTmp, rootId, "pId", false);
		
		List<Map> _lstZtree = new ArrayList<Map>();
		Map ztree = null;
		if (_lstResult != null && _lstResult.size() > 0) {
			for (int i = 0; i < _lstResult.size(); ++i) {
				Map mapEntries = (Map) _lstResult.get(i);
				Iterator itEntries = mapEntries.entrySet().iterator();
				ztree = new HashMap();
				while (itEntries.hasNext()) {
					Map.Entry entry = (Map.Entry) itEntries.next();
					String _sKey = "";
					String _sValue = "";
					if (entry.getKey() != null && entry.getValue() != null) {
						_sKey = entry.getKey().toString();
						_sValue = entry.getValue().toString();
						if(_sKey.equals("id")){
							ztree.put("id", _sValue);
						}else if(_sKey.equals("name")){
							ztree.put("name", _sValue);
						}else if(_sKey.equals("pId")){
							ztree.put("pId", _sValue);
						}else if(_sKey.equals("path")){
							ztree.put("path", _sValue);
						}
					}
				}
				_lstZtree.add(ztree);
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		Writer writer = (Writer) response.getWriter();
		writer.write(objectMapper.writeValueAsString(_lstZtree));
		writer.close();
		return NONE;
	}
	
	public String comment(){
		try {
			List<Catalog> _lstResult = mgrTreeService.getCommentById(rootId);
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter printWriter = response.getWriter();
			objectMapper.writeValue(printWriter, _lstResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return NONE;
	}
	
	public String showTreeById(){
		return NONE;
	}
	
	public String JoinTree(){
		return NONE;
	}
	
	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public String getBelongTable() {
		return belongTable;
	}

	public void setBelongTable(String belongTable) {
		this.belongTable = belongTable;
	}

	public String getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	public String getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(String parentColumn) {
		this.parentColumn = parentColumn;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isShowRoot() {
		return showRoot;
	}

	public void setShowRoot(boolean showRoot) {
		this.showRoot = showRoot;
	}

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}
	
	
}
