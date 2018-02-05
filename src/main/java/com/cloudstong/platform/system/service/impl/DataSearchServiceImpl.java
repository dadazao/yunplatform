package com.cloudstong.platform.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.system.dao.IDataSearchDao;
import com.cloudstong.platform.system.model.DataSearch;
import com.cloudstong.platform.system.service.IDataSearchService;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:数据查询策略接口的实现类
 */
@Service("dataSearchService")
public class DataSearchServiceImpl implements IDataSearchService {

	@Resource
	private IDataSearchDao dataSearchDao;
	
	public List<DataSearch> queryDataSearch(String psColumnName, String plColumnValue){
		return dataSearchDao.queryDataSearch(psColumnName, plColumnValue);
	}
	@Override
	public boolean testSql(String psSql) {
		// TODO Auto-generated method stub need to be implemented.
		return dataSearchDao.testSql(psSql);
	}
	public IDataSearchDao getDataSearchDao() {
		return dataSearchDao;
	}
	public void setDataSearchDao(IDataSearchDao dataSearchDao) {
		this.dataSearchDao = dataSearchDao;
	}
}
