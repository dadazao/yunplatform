package com.cloudstong.platform.third.bpm.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Document;
import org.dom4j.Element;

import com.cloudstong.platform.core.model.ResultMessage;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.core.util.FileUtil;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.util.XmlBeanUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.third.bpm.model.BpmButton;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;
import com.cloudstong.platform.third.bpm.model.BpmNodeButton;
import com.cloudstong.platform.third.bpm.model.BpmNodeButtonXml;
import com.cloudstong.platform.third.bpm.model.NodeCache;
import com.cloudstong.platform.third.bpm.service.BpmDefinitionService;
import com.cloudstong.platform.third.bpm.service.BpmNodeButtonService;
import com.cloudstong.platform.third.bpm.service.BpmNodeSetService;
import com.cloudstong.platform.third.bpm.service.BpmService;
import com.cloudstong.platform.third.bpm.thread.MessageUtil;

@ParentPackage("default")
@Namespace("/pages/third/bpm/bpmNodeButton")
@Results(value = { @Result(name = "list", location = "/WEB-INF/view/third/bpm/bpmNodeButtonList.jsp"),
		@Result(name = "getByNode", location = "/WEB-INF/view/third/bpm/bpmNodeButtonGetByNode.jsp")
})
public class BpmNodeButtonAction extends BaseAction {

	@Resource
	private BpmNodeButtonService bpmNodeButtonService;

	@Resource
	private BpmDefinitionService bpmDefinitionService;

	@Resource
	private BpmNodeSetService bpmNodeSetService;

	@Resource
	private BpmService bpmService;

	@Action("list")
	public String list() throws Exception {
		HttpServletRequest request = getRequest();
		Long defId = Long.valueOf(RequestUtil.getLong(request, "definitionId"));

		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(defId);

		String actDefId = bpmDefinition.getActDefId();

		List list = this.bpmNodeSetService.getByDefId(defId);

		Map taskMap = NodeCache.getByActDefId(actDefId);

		Map btnMap = this.bpmNodeButtonService.getMapByDefId(defId);

		String buttonPath = getDesignButtonPath();
		String xml = FileUtil.readFile(buttonPath + "button.xml");
		Document document = Dom4jUtil.loadXml(xml);
		Element root = document.getRootElement();
		String xmlStr = root.asXML();
		BpmNodeButtonXml bpmButtonList = (BpmNodeButtonXml) XmlBeanUtil.unmarshall(xmlStr, BpmNodeButtonXml.class);

		List<BpmButton> btnList = bpmButtonList.getButtons();

		List startBtnList = new ArrayList();
		List firstNodeBtnList = new ArrayList();
		List signBtnList = new ArrayList();
		List commonBtnList = new ArrayList();
		for (BpmButton button : btnList) {
			if (button.getInit().intValue() != 0) {
				if (button.getType().intValue() == 0) {
					startBtnList.add(button);
				} else if (button.getType().intValue() == 1) {
					firstNodeBtnList.add(button);
				} else if (button.getType().intValue() == 2) {
					signBtnList.add(button);
				} else if (button.getType().intValue() == 3) {
					commonBtnList.add(button);
				} else if (button.getType().intValue() == 4) {
					signBtnList.add(button);
					commonBtnList.add(button);
				}
			}
		}
		request.setAttribute("btnMap", btnMap);
		request.setAttribute("taskMap", taskMap);
		request.setAttribute("bpmNodeSetList", list);
		request.setAttribute("bpmDefinition", bpmDefinition);
		request.setAttribute("startBtnList", startBtnList);
		request.setAttribute("firstNodeBtnList", firstNodeBtnList);
		request.setAttribute("signBtnList", signBtnList);
		request.setAttribute("commonBtnList", commonBtnList);

		return "list";
	}

	public static String getDesignButtonPath() {
		return FileUtil.getClassesPath() + "com" + File.separator + "cloudstong" + File.separator + "platform"
				+ File.separator + "third" + File.separator + "bpm" + File.separator + "model" + File.separator;
	}

	@Action("getByNode")
	public String getByNode() throws Exception {
		HttpServletRequest request = getRequest();
		Boolean buttonFlag = Boolean.valueOf(RequestUtil.getBoolean(request, "buttonFlag", true));
		long defId = RequestUtil.getLong(request, "defId", 0L);
		String nodeId = RequestUtil.getString(request, "nodeId");
		if (defId == 0L) {
			throw new Exception("没有输入流程定义ID");
		}

		BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(Long.valueOf(defId));

		if (StringUtil.isEmpty(nodeId)) {
			List list = this.bpmNodeButtonService.getByStartForm(Long.valueOf(defId));
			request.setAttribute("btnList", list);
			request.setAttribute("isStartForm", Integer.valueOf(1));
		} else {
			List list = this.bpmNodeButtonService.getByDefNodeId(Long.valueOf(defId), nodeId);
			request.setAttribute("btnList", list);
			request.setAttribute("isStartForm", Integer.valueOf(0));
		}
		request.setAttribute("bpmDefinition", bpmDefinition);
		request.setAttribute("defId", Long.valueOf(defId));
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("buttonFlag", buttonFlag);

		return "getByNode";
	}

	@Action("del")
	public void del() throws Exception {
		HttpServletRequest request = getRequest();
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage resultMessage = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			this.bpmNodeButtonService.delByIds(lAryId);
			printJSON("success");
		} catch (Exception ex) {
			printJSON("fail");
		}
	}

	@Action("edit")
	public String edit() throws Exception {
		HttpServletRequest request = getRequest();
		Boolean buttonFlag = Boolean.valueOf(RequestUtil.getBoolean(request, "buttonFlag", true));
		Long id = Long.valueOf(RequestUtil.getLong(request, "id"));

		BpmNodeButton bpmNodeButton = null;

		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId", 0L));
		String nodeId = RequestUtil.getString(request, "nodeId");

		if (id.longValue() != 0L) {
			bpmNodeButton = (BpmNodeButton) this.bpmNodeButtonService.getById(id);
			BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(bpmNodeButton.getDefId());
			request.setAttribute("bpmDefinition", bpmDefinition);
		} else {
			BpmDefinition bpmDefinition = (BpmDefinition) this.bpmDefinitionService.getById(defId);
			request.setAttribute("bpmDefinition", bpmDefinition);

			String actDefId = bpmDefinition.getActDefId();

			bpmNodeButton = new BpmNodeButton();
			bpmNodeButton.setDefId(defId);
			if (StringUtil.isNotEmpty(nodeId)) {
				boolean rtn = this.bpmService.isSignTask(actDefId, nodeId);
				bpmNodeButton.setNodetype(Integer.valueOf(rtn ? 1 : 0));
				bpmNodeButton.setIsstartform(Integer.valueOf(0));
			} else {
				bpmNodeButton.setIsstartform(Integer.valueOf(1));
			}
			bpmNodeButton.setActdefid(actDefId);
			bpmNodeButton.setNodeid(nodeId);
		}

		String buttonPath = getDesignButtonPath();
		String xml = FileUtil.readFile(buttonPath + "button.xml");
		Document document = Dom4jUtil.loadXml(xml);
		Element root = document.getRootElement();
		String xmlStr = root.asXML();
		BpmNodeButtonXml bpmButtonList = (BpmNodeButtonXml) XmlBeanUtil.unmarshall(xmlStr, BpmNodeButtonXml.class);

		List list = bpmButtonList.getButtons();
		JSONArray array = JSONArray.fromObject(list);
		String buttonStr = array.toString();

		request.setAttribute("bpmNodeButton", bpmNodeButton);
		request.setAttribute("defId", defId);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("buttonFlag", buttonFlag);
		request.setAttribute("buttonStr", buttonStr);

		return "edit";
	}

	@Action("get")
	public String get() throws Exception {
		HttpServletRequest request = getRequest();
		long id = RequestUtil.getLong(request, "id");
		BpmNodeButton bpmNodeButton = (BpmNodeButton) this.bpmNodeButtonService.getById(Long.valueOf(id));
		request.setAttribute("bpmNodeButton", bpmNodeButton);
		return "get";
	}

	@Action("sort")
	public void sort() throws Exception {
		HttpServletRequest request = getRequest();
		String ids = RequestUtil.getString(request, "ids");
		if (StringUtil.isEmpty(ids)) {
			printJSON("300", "没有设置操作类型!");
			return;
		}
		try {
			this.bpmNodeButtonService.sort(ids);
			printJSON("200", "没有设置操作类型!");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				printJSON("300", "设置操作类型失败:" + str);
			} else {
				printJSON("300");
			}
		}
	}

	@Action("init")
	public void init() throws Exception {
		HttpServletRequest request = getRequest();
		String returnUrl = RequestUtil.getPrePage(request);
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId", 0L));
		String nodeId = RequestUtil.getString(request, "nodeId");
		ResultMessage resultMessage = null;
		try {
			this.bpmNodeButtonService.init(defId, nodeId);
			printJSON("success", "初始化按钮成功!");
		} catch (Exception ex) {
			printJSON("fail", "初始化按钮失败:" + ex.getMessage());
		}
	}

	@Action("initAll")
	public void initAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId", 0L));

		ResultMessage resultMessage = null;
		try {
			this.bpmNodeButtonService.initAll(defId);
			printJSON("success", "初始化按钮成功!");
		} catch (Exception ex) {
			printJSON("fail", "初始化按钮失败:" + ex.getMessage());
		}
	}

	@Action("delByDefId")
	public void delByDefId() throws Exception {
		HttpServletRequest request = getRequest();
		String returnUrl = RequestUtil.getPrePage(request);
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId", 0L));

		ResultMessage resultMessage = null;
		try {
			this.bpmNodeButtonService.delByDefId(defId);
			printJSON("success", "清除流程表单按钮成功!");
		} catch (Exception ex) {
			printJSON("fail", "清除流程表单按钮失败:" + ex.getMessage());
		}
	}

	@Action("deByDefNodeId")
	public void deByDefNodeId() throws IOException {
		HttpServletRequest request = getRequest();
		String returnUrl = RequestUtil.getPrePage(request);
		Long defId = Long.valueOf(RequestUtil.getLong(request, "defId", 0L));
		String nodeId = RequestUtil.getString(request, "nodeId");

		ResultMessage resultMessage = null;
		try {
			this.bpmNodeButtonService.delByDefNodeId(defId, nodeId);
			printJSON("success", "删除流程表单按钮成功!");
		} catch (Exception ex) {
			printJSON("fail", "删除流程表单按钮失败:" + ex.getMessage());
		}
	}
}