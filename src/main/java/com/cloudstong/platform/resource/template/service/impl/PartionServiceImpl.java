package com.cloudstong.platform.resource.template.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.template.dao.PartitionDao;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.service.PartitionService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分区服务接口实现类
 */
@Repository("partitionService")
public class PartionServiceImpl implements PartitionService {
	@Resource
	private PartitionDao partitionDao;

	public PartitionDao getPartitionDao() {
		return partitionDao;
	}

	public void setPartitionDao(PartitionDao partitionDao) {
		this.partitionDao = partitionDao;
	}

	@Override
	public int countPartition(String templateId) {
		return partitionDao.count(templateId);
	}

	@Override
	public List queryPartition(QueryCriteria queryCriteria) {
		Map<String, Object> map = queryCriteria.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select p.*,t.tbl_templatechname as tbl_baseTemplateName,t.tbl_templatefilename as tbl_templateFileName from sys_partition p left join sys_template t on p.tbl_basetemplate = t.id where 1=1");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			if (entry.getValue() instanceof String) {
				sql.append(" and p." + entry.getKey() + " = ? ");
			} else {
				sql.append(" and p." + entry.getKey() + " =? ");
			}
			param.add(entry.getValue());
		}
		sql.append(" order by p.tbl_partitiontype asc ");
		return partitionDao.select(sql.toString(), param.toArray(),
				queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());
	}

	@Override
	public void doSavePartition(Partition partition) {
		partitionDao.insert(partition);
	}

	@Override
	public void doUpdatePartition(Partition partition) {
		partitionDao.update(partition);
	}

	@Override
	public Partition findPartitionById(Long id) {
		return partitionDao.selectById(id);
	}

	@Override
	public void doDeletePartitions(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			partitionDao.delete(id);
		}

	}

}
