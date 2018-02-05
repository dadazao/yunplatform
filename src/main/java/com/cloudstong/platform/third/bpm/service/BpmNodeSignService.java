package com.cloudstong.platform.third.bpm.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.third.bpm.dao.BpmNodePrivilegeDao;
import com.cloudstong.platform.third.bpm.dao.BpmNodeSignDao;
import com.cloudstong.platform.third.bpm.model.BpmNodePrivilege;
import com.cloudstong.platform.third.bpm.model.BpmNodeSign;

@Service
public class BpmNodeSignService {

	@Resource
	private BpmNodeSignDao dao;

	@Resource
	private GroovyScriptEngine groovyScriptEngine;

	@Resource
	private BpmNodePrivilegeDao bpmNodePrivilegeDao;

	@Resource
	private SysUserDao sysUserDao;

	public BpmNodeSign getByDefIdAndNodeId(String actDefId, String nodeId) {
		return dao.getByDefIdAndNodeId(actDefId, nodeId);
	}

	public List<BpmNodePrivilege> getPrivilegesByDefIdAndNodeId(String actDefId, String nodeId) {
		return bpmNodePrivilegeDao.getPrivilegesByDefIdAndNodeId(actDefId, nodeId);
	}

	public void addOrUpdateSignAndPrivilege(BpmNodeSign bpmNodeSign, List<BpmNodePrivilege> list) {
		BpmNodeSign nodeSign = dao.getByDefIdAndNodeId(bpmNodeSign.getActDefId(), bpmNodeSign.getNodeId());
		if (nodeSign == null) {
			bpmNodeSign.setSignId(Long.valueOf(UniqueIdUtil.genId()));
			dao.save(bpmNodeSign);
		} else {
			dao.update(bpmNodeSign);
		}

		bpmNodePrivilegeDao.delByDefIdAndNodeId(bpmNodeSign.getActDefId(), bpmNodeSign.getNodeId());

		for (BpmNodePrivilege vo : list) {
			vo.setPrivilegeid(Long.valueOf(UniqueIdUtil.genId()));
			vo.setActdefid(bpmNodeSign.getActDefId());
			vo.setNodeid(bpmNodeSign.getNodeId());
			bpmNodePrivilegeDao.save(vo);
		}
	}

	private boolean containList(List<String> list, String id) {
		for (String str : list) {
			if (str.equals(id)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkNodeSignPrivilege(String actDefId, String nodeId, BpmNodePrivilegeType type, Long userId, Long orgId) {
		List<BpmNodePrivilege> list = bpmNodePrivilegeDao.getPrivilegesByDefIdAndNodeIdAndMode(actDefId, nodeId, type.getMode());

		if (BeanUtils.isEmpty(list)) {
			return false;
		}

		for (BpmNodePrivilege rule : list) {
			switch (rule.getUsertype().intValue()) {
			case 1:
				if (!StringUtil.isEmpty(rule.getCmpids())) {
					List allowList = Arrays.asList(rule.getCmpids().split(","));
					boolean rtn = containList(allowList, String.valueOf(userId));
					if (rtn) {
						return true;
					}

				}

				break;
			case 3:
				if (!StringUtil.isEmpty(rule.getCmpids())) {
					List allowList = Arrays.asList(rule.getCmpids().split(","));
					boolean rtn = containList(allowList, String.valueOf(orgId));
					if (rtn) {
						return true;
					}

				}

				break;
			case 4:
				if (!StringUtil.isEmpty(rule.getCmpids())) {
					List<String> allowList = Arrays.asList(rule.getCmpids().split(","));
					for (String currentOrgId : allowList) {
						List<SysUserOrg> userIdList = sysUserDao.getChargeByOrgId(new Long(currentOrgId));
						if (!BeanUtils.isEmpty(userIdList)) {
							for (SysUserOrg userOrg : userIdList) {
								if (userId.equals(userOrg.getSysUser().getId())) {
									return true;
								}
							}
						}
					}
				}
				break;
			case 12:
				Map vars = new HashMap();
				Object result = groovyScriptEngine.executeObject(rule.getCmpnames(), vars);
				if (result != null) {
					Set set = (Set) result;
					if (set.contains(String.valueOf(userId))) {
						return true;
					}
				}
				break;
			}

		}

		return false;
	}

	public static enum BpmNodePrivilegeType {
		DEFAULT(Long.valueOf(0L)),

		ALLOW_DIRECT(Long.valueOf(1L)),

		ALLOW_ONE_VOTE(Long.valueOf(2L)),

		ALLOW_RETROACTIVE(Long.valueOf(3L));

		private Long mode;

		private BpmNodePrivilegeType(Long mode) {
			this.mode = mode;
		}

		private Long getMode() {
			return mode;
		}
	}

	private static class NodeSignPrivilegeRuleChecker {
		public static final short ASSIGN_TYPE_USER = 1;
		public static final short ASSIGN_TYPE_ORG = 3;
		public static final short ASSIGN_TYPE_ORG_CHARGE = 4;
		public static final short ASSIGN_TYPE_SCRIPT = 12;
	}
}