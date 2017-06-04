package com.cloudstong.platform.resource.imagepage.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.imagepage.model.ImagePage;
import com.cloudstong.platform.resource.imagepage.service.ImagePageService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:图片页面Action
 */
public class ImagePageAction extends CompexDomainAction {

	private static final long serialVersionUID = -9024137631415230734L;

	/**
	 * 操作图片页面的服务接口,<code>imagePageService</code> 对象是ImagePageService接口的一个实例
	 */
	@Resource
	private ImagePageService imagePageService;

	public ImagePageService getImagePageService() {
		return imagePageService;
	}

	public void setImagePageService(ImagePageService imagePageService) {
		this.imagePageService = imagePageService;
	}

	public String list() {
		super.list();
		return "list";
	}

	/* 
	 * 保存图片页面信息
	 */
	public String save() {
		try {
			Long id = super.simpleSave();
			if(id!=null&&!id.equals("")){
				ImagePage imagePage = imagePageService.findImagePageById(id);
				imagePage.setImagePagePath("pages/system/imagepageCompexshowImgPage.action?imgId=" + imagePage.getId());
				imagePageService.doUpdateImagePage(imagePage);
			}
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		}
		return NONE;
	}

	/**
	 * Description:显示图片页面
	 * @return forward
	 */
	public String showImgPage() {
		Long id = Long.valueOf(getRequest().getParameter("imgId") == null ? "-1" : getRequest().getParameter("imgId"));
		ImagePage imagePage = imagePageService.findImagePageById(id);
		this.getRequest().setAttribute("imagePage", imagePage);
		return "imgpage";
	}

	/**
	 * Description:获得图片页面列表
	 * @return NONE
	 * @throws Exception
	 */
	public String getImagePages() throws Exception {
		QueryCriteria qc = new QueryCriteria();
		qc.setPageSize(-1);
		List imagepages = imagePageService.queryImagePage(qc);

		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter = response.getWriter();
		objectMapper.writeValue(printWriter, imagepages);
		return NONE;
	}
}
