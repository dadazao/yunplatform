package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.TimeUtil;
import com.cloudstong.platform.third.bpm.dao.BpmFormQueryDao;
import com.cloudstong.platform.third.bpm.form.model.QueryResult;
import com.cloudstong.platform.third.bpm.model.BpmFormQuery;

@Service
public class BpmFormQueryService {

	@Resource
	private BpmFormQueryDao dao;

	public BpmFormQuery getByAlias(String alias) {
		return dao.getByAlias(alias);
	}

	public boolean isExistAlias(String alias) {
		return dao.isExistAlias(alias).intValue() > 0;
	}

	public boolean isExistAliasForUpd(Long id, String alias) {
		return dao.isExistAliasForUpd(id, alias).intValue() > 0;
	}

	public QueryResult getData(BpmFormQuery bpmFormQuery, String queryData, Integer page, Integer pageSize) throws Exception {
		Map params = new HashMap();
		if (StringUtil.isNotEmpty(queryData)) {
			JSONObject jsonObj = JSONObject.fromObject(queryData);
			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString().toUpperCase();
				String value = jsonObj.getString(key);
				params.put(key, value);
			}
		}

//		JdbcHelper jdbcHelper = ServiceUtil.getJdbcHelper(bpmFormQuery.getDsalias());
//
//		List resultList = bpmFormQuery.getReturnList();
//		List conditionList = bpmFormQuery.getConditionList();
//		String objectName = bpmFormQuery.getObjName();
//
		QueryResult queryResult = new QueryResult();
//
//		if ((page.intValue() > 0) && (pageSize.intValue() > 0)) {
//			String sql = ServiceUtil.getSql(objectName, resultList, null, conditionList, params);
//			PageBean pageBean = new PageBean(page.intValue(), pageSize.intValue());
//			List list = jdbcHelper.getPage(page.intValue(), pageSize.intValue(), sql, params, pageBean);
//			list = handList(list);
//			queryResult.setList(list);
//			queryResult.setIsPage(1);
//			queryResult.setPage(page.intValue());
//			queryResult.setPageSize(pageSize.intValue());
//			int totalCount = pageBean.getTotalCount();
//			int totalPage = pageBean.getTotalPage();
//			queryResult.setTotalCount(totalCount);
//			queryResult.setTotalPage(totalPage);
//		} else {
//			String sql = ServiceUtil.getSql(objectName, resultList, null, conditionList, params);
//			List list = jdbcHelper.queryForList(sql, params);
//			list = handList(list);
//			queryResult.setList(list);
//			queryResult.setTotalCount(list.size());
//		}
		return queryResult;
	}

	private List<Map<String, Object>> handList(List<Map<String, Object>> list) {
		List rtnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Map rtnMap = handMap(map);
			rtnList.add(rtnMap);
		}
		return rtnList;
	}

	private Map<String, Object> handMap(Map<String, Object> map) {
		Map rtnMap = new HashMap();
		for (Map.Entry entry : map.entrySet()) {
			String key = ((String) entry.getKey()).toLowerCase();
			Object obj = entry.getValue();
			if (obj == null) {
				rtnMap.put(key, "");
			} else if ((obj instanceof Date)) {
				String format = "yyyy-MM-dd HH:mm:ss";
				String str = TimeUtil.getDateTimeString((Date) obj, format);
				rtnMap.put(key, str);
			} else {
				rtnMap.put(key, obj);
			}
		}
		return rtnMap;
	}
}