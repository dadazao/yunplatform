package com.cloudstong.platform.third.bpm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmDefRightsDao;
import com.cloudstong.platform.third.bpm.dao.GlobalTypeDao;
import com.cloudstong.platform.third.bpm.model.BpmDefRights;
import com.cloudstong.platform.third.bpm.model.GlobalType;

@Service
public class BpmDefRightsService {

	@Resource
	private BpmDefRightsDao dao;

	@Resource
	private GlobalTypeDao globalTypeDao;

	public List<BpmDefRights> getDefRight(Long defId, Short rightType) {
		return dao.getDefRight(defId, rightType);
	}

	public List<BpmDefRights> getTypeRight(Long typeId, Short rightType) {
		return dao.getTypeRight(typeId, rightType);
	}

	public Map<String, String> getRights(String assignId, int assignType) {
		Map rightsMap = new HashMap();
		List<BpmDefRights> list = null;
		if (assignType == 0) {
			list = this.dao.getByDefKey(assignId);
		} else {
			list = this.dao.getByTypeId(Long.valueOf(Long.parseLong(assignId)));
		}
		List all = new ArrayList();
		List user = new ArrayList();
		List role = new ArrayList();
		List org = new ArrayList();
		List position = new ArrayList();
		List posGroup = new ArrayList();
		List job = new ArrayList();
		List orgGrant = new ArrayList();

		for (BpmDefRights rights : list)
			switch (rights.getRightType().shortValue()) {
			case 0:
				all.add(rights);
				break;
			case 1:
				user.add(rights);
				break;
			case 2:
				role.add(rights);
				break;
			case 3:
				org.add(rights);
				break;
			case 4:
				position.add(rights);
				break;
			case 7:
				orgGrant.add(rights);
			case 5:
			case 6:
			}
		String allData = coverList2Json(all);
		String userData = coverList2Json(user);
		String roleData = coverList2Json(role);
		String orgData = coverList2Json(org);
		String positionData = coverList2Json(position);
		String posGroupData = coverList2Json(posGroup);
		String jobData = coverList2Json(job);
		String orgGrantData = coverList2Json(orgGrant);
		rightsMap.put("all", allData);
		rightsMap.put("user", userData);
		rightsMap.put("role", roleData);
		rightsMap.put("org", orgData);
		rightsMap.put("position", positionData);
		rightsMap.put("posGroup", posGroupData);
		rightsMap.put("job", jobData);
		rightsMap.put("orgGrant", orgGrantData);
		return rightsMap;
	}

	public String coverList2Json(List<BpmDefRights> list) {
		if (BeanUtils.isEmpty(list))
			return "";
		JSONArray jarray = new JSONArray();

		for (BpmDefRights r : list) {
			JSONObject jobject = new JSONObject();
			jobject.accumulate("id", r.getOwnerId()).accumulate("name", r.getOwnerName());
			jarray.add(jobject);
		}
		return jarray.toString();
	}

	public Map<String, String> coverList2Map(List<BpmDefRights> list) {
		Map m = new HashMap();
		if (BeanUtils.isEmpty(list))
			return m;

		String ownerId = "";
		String ownerName = "";
		for (BpmDefRights r : list) {
			ownerId = ownerId + r.getOwnerId() + ",";
			ownerName = ownerName + r.getOwnerName() + ",";
		}
		if (ownerId.length() > 0)
			ownerId = ownerId.substring(0, ownerId.length() - 1);
		if (ownerName.length() > 0)
			ownerName = ownerName.substring(0, ownerName.length() - 1);
		m.put("ownerId", ownerId);
		m.put("ownerName", ownerName);

		return m;
	}

	public void saveRights(String assignId, int assignType, String[] rightType, String[] ownerId, String[] ownerName,
			int isChange) throws Exception {
		if (assignType == 0) {
			dao.delByDefKey(assignId);
		} else if (isChange == 1) {
			GlobalType gt = (GlobalType) globalTypeDao.getById(new Long(assignId));
			String nodePath = gt.getNodePath();
			List<GlobalType> globalTypelist = globalTypeDao.getByNodePath(nodePath);
			for (GlobalType glbtype : globalTypelist)
				dao.delByTypeId(glbtype.getTypeId());
		} else {
			dao.delByTypeId(new Long(assignId));
		}

		List rightList = coverTypeRights(assignId, assignType, rightType, ownerId, ownerName, isChange);
		add(rightList);
	}

	public void add(List<BpmDefRights> rightList) {
		if ((rightList == null) || (rightList.size() == 0))
			return;
		for (BpmDefRights r : rightList)
			dao.save(r);
	}

	public void saveRights(String assignId, int assignType, String[] rightType, String[] owner, int isChange)
			throws Exception {
		if (assignType == 0) {
			String[] assignIds = assignId.split(",");
			for (String defKey : assignIds) {
				this.dao.delByDefKey(defKey);
			}

		}

		List rightList = coverTypeRights(assignId, assignType, rightType, owner, isChange);
		add(rightList);
	}

	private List<BpmDefRights> coverTypeRights(String assignId, int assignType, String[] rightType, String[] owner,
			int isChange) throws Exception {
		if ((owner == null) || (owner.length == 0))
			return null;

		String[] assignIds = assignId.split(",");
		List list = new ArrayList();

		for (int i = 0; i < rightType.length; i++) {
			String right = rightType[i];
			String ownerObj = owner[i];
			if (!StringUtil.isEmpty(ownerObj) && !"{}".equals(ownerObj)) {
				JSONArray jarray = JSONArray.fromObject(ownerObj);
				int size = jarray.size();
				if (size == 0)
					continue;
				for (int j = 0; j < size; j++) {
					JSONObject jObject = (JSONObject) jarray.get(j);
					String id = jObject.getString("id");
					String name = jObject.getString("name");
					if (StringUtil.isEmpty(id))
						continue;
					if (assignType == 0) {
						for (String assignid : assignIds) {
							BpmDefRights defRight = setBpmDefRights(assignType, assignid, null, new Short(right),
									new Long(id), name);
							list.add(defRight);
						}

					}

				}

			}

		}

		return list;
	}

	private BpmDefRights setBpmDefRights(int assignType, String defKey, Long flowTypeId, Short rightType, Long ownerId,
			String ownerName) {
		BpmDefRights defRight = new BpmDefRights();
		defRight.setId(Long.valueOf(UniqueIdUtil.genId()));

		if (assignType == 0) {
			defRight.setSearchType((short) 0);
			defRight.setDefKey(defKey);
		} else {
			defRight.setSearchType((short) 1);
			defRight.setFlowTypeId(flowTypeId);
		}
		defRight.setRightType(rightType);
		defRight.setOwnerId(ownerId);
		defRight.setOwnerName(ownerName);
		return defRight;
	}

	private List<BpmDefRights> coverTypeRights(String assignId, int assignType, String[] rightType, String[] ownerId,
			String[] ownerName, int isChange) throws Exception {
		if ((ownerId == null) || (ownerId.length == 0))
			return null;

		List list = new ArrayList();

		for (int i = 0; i < rightType.length; i++) {
			String right = rightType[i];
			String[] ids = ownerId[i].split(",");
			String[] names = ownerName[i].split(",");
			if (!BeanUtils.isEmpty(ids)) {
				for (int j = 0; j < ids.length; j++) {
					String id = ids[j];
					String name = names[j];
					if (!StringUtil.isEmpty(id)) {
						if (assignType == 0) {
							BpmDefRights defRight = new BpmDefRights();
							defRight.setId(Long.valueOf(UniqueIdUtil.genId()));
							defRight.setDefKey(assignId);
							defRight.setSearchType(Short.valueOf((short) 0));
							defRight.setRightType(new Short(right));
							defRight.setOwnerId(new Long(id));
							defRight.setOwnerName(name);
							list.add(defRight);
						} else if (isChange == 1) {
							GlobalType gt = (GlobalType) globalTypeDao.getById(new Long(assignId));
							String nodePath = gt.getNodePath();
							List<GlobalType> globalTypelist = globalTypeDao.getByNodePath(nodePath);
							for (GlobalType glbtype : globalTypelist) {
								BpmDefRights defRight = new BpmDefRights();
								defRight.setId(Long.valueOf(UniqueIdUtil.genId()));
								defRight.setFlowTypeId(glbtype.getTypeId());
								defRight.setSearchType(Short.valueOf((short) 1));
								defRight.setRightType(new Short(right));
								defRight.setOwnerId(new Long(id));
								defRight.setOwnerName(name);
								list.add(defRight);
							}
						} else {
							BpmDefRights defRight = new BpmDefRights();
							defRight.setId(Long.valueOf(UniqueIdUtil.genId()));
							defRight.setFlowTypeId(new Long(assignId));
							defRight.setSearchType(Short.valueOf((short) 1));
							defRight.setRightType(new Short(right));
							defRight.setOwnerId(new Long(id));
							defRight.setOwnerName(name);
							list.add(defRight);
						}
					}
				}
			}
		}
		return list;
	}

	public void delByIds(Long[] lAryId) {
		for (Long id : lAryId) {
			dao.delById(id);
		}

	}
}