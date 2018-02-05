package com.cloudstong.platform.resource.tree.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.template.model.TreeShowForm;
import com.cloudstong.platform.resource.template.vo.TemplateColumn;
import com.cloudstong.platform.resource.tree.dao.MgrTreeDao;
import com.cloudstong.platform.resource.tree.model.CommonTree;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.resource.tree.service.MgrTreeService;

/**
 * @author Allan
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:树模板服务接口实现类
 */
@Repository("mgrTreeService")
public class MgrTreeServiceImpl implements MgrTreeService {

	@Resource
	private MgrTreeDao mgrTreeDao;

	private MgrTree fetchZhnames(MgrTree mgrTree) {
		Long tableId = mgrTree.getTableId();
		Table t = this.mgrTreeDao.getTable(tableId);
		mgrTree.setTableZhName(t.getTableZhName());
		mgrTree.setTableName(t.getTableName());

		Long disId = mgrTree.getDisColumnId();
		String disZhName = this.mgrTreeDao.getColumn(disId)
				.getColumnZhName();
		mgrTree.setDisColumnZhName(disZhName);
		String disName = this.mgrTreeDao.getColumn(disId).getColumnName();
		mgrTree.setDisColumnName(disName);

		Long orderId = mgrTree.getOrderColumnId();
		String orderName = this.mgrTreeDao.getColumn(orderId)
				.getColumnZhName();
		mgrTree.setOrderColumnZhName(orderName);

		Long parId = mgrTree.getParentColumnId();
		String parName = this.mgrTreeDao.getColumn(parId)
				.getColumnZhName();
		mgrTree.setParentColumnZhName(parName);

		Long rootId = mgrTree.getRootId();
		String rootName = this.mgrTreeDao.getRootName(
				mgrTree.getTableName(), mgrTree.getDisColumnName(),
				rootId);
		mgrTree.setRootZhName(rootName);
		return mgrTree;
	}

	@Override
	public Long doSaveMgrTree(MgrTree mgrTree) {
		fetchZhnames(mgrTree);
		return mgrTreeDao.insert(mgrTree);
	}

	@Override
	public void doDeleteMgrTree(Long id) {
		mgrTreeDao.delete(id);
	}

	@Override
	public void doUpdateMgrTree(MgrTree mgrTree) {
		fetchZhnames(mgrTree);
		mgrTreeDao.update(mgrTree);
	}

	@Override
	public MgrTree findMgrTreeById(Long id) {
		return mgrTreeDao.selectById(id);
	}

	@Override
	public TreeShowForm doSearchTreeParam(Long tableId, Long columnId,
			Long disColumnId, Long orderColumnId, Long id) {
		String tableName = mgrTreeDao.getTable(tableId).getTableName();
		String columnName = mgrTreeDao.getColumn(columnId).getColumnName();
		String disColumnName = mgrTreeDao.getColumn(disColumnId)
				.getColumnName();
		String orderColumnName = mgrTreeDao.getColumn(orderColumnId)
				.getColumnName();
		Long rootId = null;
		if (id != null) {
			MgrTree tree = this.mgrTreeDao.selectById(id);
			rootId = tree.getRootId();
		} else {
			rootId = mgrTreeDao.getRootId(tableName, columnName);
		}

		TreeShowForm form = new TreeShowForm();
		form.setModel(tableName);
		form.setRoot(rootId);
		form.setIdColumn("id");
		form.setNameColumn(disColumnName);
		form.setParentIdColumn(columnName);
		// form.setPathColumn("path");
		form.setOrderColumn(orderColumnName);

		return form;
	}

	@Override
	public List<MgrTree> queryMgrTree(QueryCriteria queryCriteria) {
		Map<String, Object> map = queryCriteria.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer();
		sql.append("  select s.id ,s.tbl_compname as tbl_treename ,s.tbl_suoshubiao, t.tbl_tablename , t.tbl_tableZhname , c1.id , c1.tbl_columnName,  c1.tbl_columnZhName, " +
				"c2.id, c2.tbl_columnName,  c2.tbl_columnZhName, c3.id , c3.tbl_columnName ,  c3.tbl_columnZhName  , s.tbl_genid , s.tbl_genidname , s.comm_createBy, s.comm_createDate, " +
				"s.tbl_beizhu, s.tbl_comment,s.comm_updateBy , s.comm_updateDate,s.tbl_systemteam,s.tbl_type,s.tbl_tableidchild,s.tbl_discolumnidchild,s.tbl_ordercolumnidchild,s.tbl_parentcolumnidchild,s.tbl_tabletype,user.tbl_yonghuxingming,user2.tbl_yonghuxingming ");
		sql.append("  from sys_tree  s left join  sys_tables t on s.tbl_suoshubiao = t.id   ");
		sql.append("       left join sys_columns c1 on  s.tbl_xianshiziduan = c1.id ");
		sql.append("       left join sys_columns c2 on  s.tbl_fuidziduan=c2.id  ");
		sql.append("       left join sys_columns c3 on  s.tbl_paixu=c3.id ");
		sql.append(" left join sys_user user on  s.comm_createBy=user.id ");
		sql.append(" left join sys_user user2 on  s.comm_updateBy=user2.id ");
		sql.append("  where s.tbl_passed=1 and 1=1  ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			String key = entry.getKey();
			if ("s.tbl_compname".equals(key)) {
				sql.append(" and " + key + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and " + entry.getKey() + " = ? ");
				param.add(entry.getValue());
			}
		}
		sql.append(" order by s.id desc ");

		List<MgrTree> result=null;
		try {
			result = mgrTreeDao.select(sql.toString(),
					param.toArray(), queryCriteria.getCurrentIndex(),
					queryCriteria.getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public int countMgrTree() {
		return mgrTreeDao.count();
	}

	@Override
	public void doDeleteMgrTrees(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			mgrTreeDao.delete(id);
		}
	}

	public MgrTreeDao getMgrTreeDao() {
		return mgrTreeDao;
	}

	public void setMgrTreeDao(MgrTreeDao mgrTreeDao) {
		this.mgrTreeDao = mgrTreeDao;
	}

	@Override
	public List<TemplateColumn> getColumnAll(Long id) {
		// TODO Auto-generated method stub
		return mgrTreeDao.selectColumnAll(id);
	}

	public List<Map> queryTreeByParam(String belongTable, String orderColumn, String orderType){
		return mgrTreeDao.queryTreeByParam(belongTable, orderColumn, orderType);
	}
	
	public List<Catalog> getCommentById(Long catalogId){
		return mgrTreeDao.getCommentById(catalogId);
	}
	
	public String getValueByParam(String belongTable, String columntName, Long id){
		return mgrTreeDao.getValueByParam(belongTable, columntName, id);
	}
	
	public CommonTree findCommonTreeByMgrTree(MgrTree mgrTree, Long id){
		return mgrTreeDao.findCommonTree(mgrTree, id);
	}
	
	
	
	
	
}
