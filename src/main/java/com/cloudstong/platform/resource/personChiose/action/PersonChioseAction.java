package com.cloudstong.platform.resource.personChiose.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.personChiose.service.PersonChioseService;
import com.cloudstong.platform.resource.tree.model.MgrTree;
import com.cloudstong.platform.resource.tree.service.MgrTreeService;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-16
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:人员选择构件Action
 */
public class PersonChioseAction extends CompexDomainAction {
	/**
	 * 操作人员选择构件的服务接口,<code>personChioseService</code>
	 * 对象是PersonChioseService接口的一个实例
	 */
	@Resource
	private PersonChioseService personChioseService;

	/**
	 * 操作树模板的服务接口,<code>mgrTreeService</code> 对象是MgrTreeService接口的一个实例
	 */
	@Resource
	private MgrTreeService mgrTreeService;

	/**
	 * 选择人员
	 */
	private String choisePerson;

	/**
	 * 人员选择名称
	 */
	private String choiseName;

	/**
	 * 人员选择ID
	 */
	private String choiseId;

	/**
	 * 树ID
	 */
	private Long treeId;

	List<Org> ORGS = new ArrayList<Org>();
	/**
	 * Description:机构人员树
	 * 
	 * @return NONE
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String depmPersonTree() {
		try {
			MgrTree mgrTree = mgrTreeService.findMgrTreeById(treeId);
			Long rootId = mgrTree.getRootId();
			List<Map> _lstZtree = new ArrayList<Map>();
			List<Org> _lstOrg = personChioseService.getOrgs();
			List<Org> orgs = new ArrayList<Org>();
			for (Org org : _lstOrg) {
				if(org.getId().equals(rootId)){
					orgs.add(org);
					ORGS.add(org);
				}
			}
			getOrg(_lstOrg, orgs);
			for (Org org : ORGS) {
				Map ztree = new HashMap();
				ztree.put("id", org.getId());
				ztree.put("pId", org.getTblParentid());
				ztree.put("name", org.getTblName());
				ztree.put("open", true);
				ztree.put("isParent", true);
				_lstZtree.add(ztree);
			}

			List<Person> _lstPerson = personChioseService.getPersons();
			for (Person person : getPerson(ORGS, _lstPerson)) {
				Map ztree = new HashMap();
				ztree.put("id", person.getId());
				ztree.put("pId", person.getTblSuoshubumen());
				ztree.put("name", person.getTblXingming());
				if (getChoisePerson(String.valueOf(person.getId()))) {
					ztree.put("checked", true);
				}
				ztree.put("open", false);
				ztree.put("isParent", false);
				_lstZtree.add(ztree);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			Writer writer = (Writer) response.getWriter();
			writer.write(objectMapper.writeValueAsString(_lstZtree));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("depmPersonTree:" + e.getMessage());
			}
		}
		return NONE;
	}

	public void getOrg(List<Org> lstOrg, List<Org> orgs) {
		if(orgs != null && orgs.size()>0){
			List<Org> orgsTmp = new ArrayList<Org>();
			for (Org org : orgs) {
				for (Org orgA : lstOrg) {
					if (org.getId().equals(orgA.getTblParentid())) {
						orgsTmp.add(orgA);
						ORGS.add(orgA);
					}
				}
			}
			getOrg(lstOrg, orgsTmp);
		}
	}

	public List<Person> getPerson(List<Org> orgs, List<Person> persons){
		List<Person> personsTmp = new ArrayList<Person>();
		for (Org org : orgs) {
			for (Person person : persons) {
				if(org.getId().equals(person.getTblSuoshubumen())){
					personsTmp.add(person);
				}
			}
		}
		return personsTmp;
	}
	
	public List<SysUser> getUser(List<Org> orgs, List<SysUser> users){
		List<SysUser> usersTmp = new ArrayList<SysUser>();
		for (Org org : orgs) {
			for (SysUser user : users) {
				if(org.getId().equals(user.getDepartment())){
					usersTmp.add(user);
				}
			}
		}
		return usersTmp;
	}
	
	public String page() {
		return "page";
	}

	/**
	 * Description:列表显示人员名称
	 * 
	 * @return NONE
	 */
	public String depmPersonChange() {
		try {
			List<Person> _lstPerson = personChioseService.getPersons();
			List<Person> _lstPersonTmp = new ArrayList<Person>();
			for (Person person : _lstPerson) {
				for (String _sPerson : choisePerson.split(";")) {
					if (person.getId().equals(_sPerson)) {
						_lstPersonTmp.add(person);
					}
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			Writer writer = (Writer) response.getWriter();
			writer.write(objectMapper.writeValueAsString(_lstPersonTmp));
			writer.close();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("depmPersonChange:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:根据人员ID判断是否选中
	 * 
	 * @param personId
	 *            人员ID
	 * @return 是否选中
	 */
	private boolean getChoisePerson(String personId) {
		boolean _bFlag = false;
		for (String person : choisePerson.split(";")) {
			if (person.equals(personId)) {
				_bFlag = true;
				break;
			}
		}
		return _bFlag;
	}

	/**
	 * Description:显示人员页面
	 * 
	 * @return
	 */
	public String userPage() {
		return "userPage";
	}

	/**
	 * Description:部门用户树
	 * 
	 * @return NONE
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String depmUserTree() {
		try {
			MgrTree mgrTree = mgrTreeService.findMgrTreeById(treeId);
			Long rootId = mgrTree.getRootId();
			List<Map> _lstZtree = new ArrayList<Map>();
			List<Org> _lstOrg = personChioseService.getOrgs();
			List<Org> orgs = new ArrayList<Org>();
			for (Org org : _lstOrg) {
				if(org.getId().equals(rootId)){
					orgs.add(org);
					ORGS.add(org);
				}
			}
			getOrg(_lstOrg, orgs);
			for (Org org : ORGS) {
				Map ztree = new HashMap();
				ztree.put("id", org.getId());
				ztree.put("pId", org.getTblParentid());
				ztree.put("name", org.getTblName());
				ztree.put("open", true);
				ztree.put("isParent", true);
				_lstZtree.add(ztree);
			}

			List<SysUser> _lstUser = personChioseService.getUsers();
			for (SysUser user : getUser(ORGS, _lstUser)) {
				Map ztree = new HashMap();
				ztree.put("id", user.getId());
				ztree.put("pId", user.getDepartment());
				ztree.put("name", user.getFullname());
				ztree.put("open", false);
				ztree.put("isParent", false);
				_lstZtree.add(ztree);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			Writer writer = (Writer) response.getWriter();
			writer.write(objectMapper.writeValueAsString(_lstZtree));
			writer.close();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("depmUserTree:" + e.getMessage());
			}
		}
		return NONE;
	}

	public PersonChioseService getPersonChioseService() {
		return personChioseService;
	}

	public void setPersonChioseService(PersonChioseService personChioseService) {
		this.personChioseService = personChioseService;
	}

	public String getChoisePerson() {
		return choisePerson;
	}

	public void setChoisePerson(String choisePerson) {
		this.choisePerson = choisePerson;
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

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}
}
