package com.cloudstong.platform.third.bpm.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.service.BpmDefRightsService;
import com.cloudstong.platform.third.bpm.service.BpmDefinitionService;

@ParentPackage("default")
@Namespace("/pages/third/bpm/bpmDefRight")
@Results(value = { @Result(name = "list", location = "/pages/third/bpm/bpmDefRightList.jsp")

})
public class BpmDefRightAction extends BaseAction {

	@Resource
	private BpmDefRightsService bpmDefRightService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Action("list")
	public String list() throws Exception {
		HttpServletRequest request = getRequest();
		String id = RequestUtil.getString(request, "id");
		String defKey = RequestUtil.getString(request, "defKey", "");
		int type = RequestUtil.getInt(request, "type");
		int isParent = RequestUtil.getInt(request, "isParent", 0);

		Map rightsMap = this.bpmDefRightService.getRights("".equals(defKey) ? id + "" : defKey, type);

		String[] ids = id.split(",");
		if (type == 0) {
			List bpmDefinitions = new ArrayList();
			for (String defId : ids) {
				BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(Long.valueOf(Long
						.parseLong(defId)));
				bpmDefinitions.add(bpmDefinition);
			}
			request.setAttribute("bpmDefinitions", bpmDefinitions);
		} else {
			// List globalTypes = new ArrayList();
			// for (String gloId : ids) {
			// GlobalType globalType =
			// (GlobalType)this.globalTypeService.getById(Long.valueOf(Long.parseLong(gloId)));
			// globalTypes.add(globalType);
			// }
			// mv.addObject("globalTypes", globalTypes);
		}

		request.setAttribute("rightsMap", rightsMap);
		request.setAttribute("id", id);
		request.setAttribute("defKey", defKey);
		request.setAttribute("type", Integer.valueOf(type));
		request.setAttribute("isParent", Integer.valueOf(isParent));

		return "list";
	}

	@Action("del")
	public void del() throws Exception {
		HttpServletRequest request = getRequest();
		String preUrl = RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "rightId");
		this.bpmDefRightService.delByIds(lAryId);
	}

	@Action("save")
	public void save() throws Exception {
		HttpServletRequest request = getRequest();
		String id = RequestUtil.getString(request, "id");
		int type = RequestUtil.getInt(request, "type");
		String defKey = RequestUtil.getString(request, "defKey");
		int isChange = RequestUtil.getInt(request, "isChange", 0);

		String[] rightType = request.getParameterValues("rightType");
		String[] owner = request.getParameterValues("owner");
		try {
			this.bpmDefRightService.saveRights("".equals(defKey) ? id : defKey, type, rightType, owner, isChange);
			printJSON("success");
		} catch (Exception e) {
			e.printStackTrace();
			printJSON("fail");
		}
	}
}