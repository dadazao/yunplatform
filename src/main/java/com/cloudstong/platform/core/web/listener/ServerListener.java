package com.cloudstong.platform.core.web.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.enterinfo.service.EnterpriseInfoService;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.service.FormService;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.service.TabulationService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.LogoService;
import com.cloudstong.platform.system.service.SysResourceService;
import com.cloudstong.platform.system.service.SysRoleService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.system.service.ThemeService;

public class ServerListener implements ServletContextListener {
	private Log logger = LogFactory.getLog(ServerListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		// ModemMessage.getInstance().stopService();
		// logger.debug("[contextDestroyed]停止短信猫服务。");
	}

	public void contextInitialized(ServletContextEvent event) {
		// logger.debug("[contextInitialized]开始初始化桌面设置。");
		// DesktopColumnService.initDesk();
		// DesktopLayoutcolService.initDefaultDesk();
		// logger.debug("[contextInitialized]初始化桌面设置成功。");

		Properties ps = (Properties) AppUtil.getBean("configproperties");
		String isCacheData = ps.getProperty("isCacheData");
		String isClearCache = ps.getProperty("isClearCache");
		if ("true".equals(isCacheData)) {// 是否缓存数据
			// if ("true".equals(isClearCache)) {// 是否启动服务器时清空缓存
			// ExtendedSSMCacheManager ecm = (ExtendedSSMCacheManager)
			// AppUtil.getBean("ssmCacheManager");
			// Collection<SSMCache> caches = ecm.getCaches();
			// for (SSMCache cache : caches) {
			// cache.clear();
			// }
			// }
			
			SysResourceService sysResourceService = (SysResourceService) AppUtil.getBean("sysResourceService");
			sysResourceService.getUrlRightMap(Constants.DEFAULT_SYSTEM_ID);
			sysResourceService.getFunctionRoleList(Constants.DEFAULT_SYSTEM_ID);
			
			SysUserService sysUserService = (SysUserService) AppUtil.getBean("sysUserService");
			
			//缓存用户角色和相关资源
			List<SysUser> users = sysUserService.findAllUser();
			SysRoleService sysRoleService = (SysRoleService)AppUtil.getBean("sysRoleService");
			for(SysUser user:users) {
				sysRoleService.getRolesByUserId(user.getId());
				sysResourceService.queryResourceSysRole(Constants.DEFAULT_SYSTEM_ID, user);
			}
			
			// 模拟用户
			SysUser user = sysUserService.getByAccount("admin");

			// 查询所有记录
			QueryCriteria qc = new QueryCriteria();
			qc.setPageSize(-1);

			TabulationService tabulationService = (TabulationService) AppUtil.getBean("tabulationService");
			List<Tabulation> tabulations = tabulationService.queryTabulation(qc);
			
			QueryCriteria queryCriteria = new QueryCriteria();
			queryCriteria.setCurrentIndex(1);
			CatalogService catalogService = (CatalogService) AppUtil.getBean("catalogService");
			for (Tabulation tabulation : tabulations) {
				try {
					// 缓存列表数据
					tabulationService.findTabulationByListId(tabulation.getId(), queryCriteria, user);
				} catch (Exception e) {
				}

				try {
					// 缓存列表对应的模块数据
					catalogService.findCatalogByListId(tabulation.getId());
				} catch (Exception e) {
				}
			}

			FormService formService = (FormService) AppUtil.getBean("formService");
			List<Form> forms = formService.queryForm(qc);
			List<DomainVO> domainVOs = new ArrayList<DomainVO>();
			for (Form form : forms) {
				try {
					// 缓存表单数据
					formService.getFormByIdAndDomainVO(form.getId(), domainVOs, user);
				} catch (Exception e) {
				}
			}

			// 缓存默认主题
			ThemeService themeService = (ThemeService) AppUtil.getBean("themeService");
			themeService.doGetDefaultTheme();

			// 缓存默认Logo
			LogoService logoService = (LogoService) AppUtil.getBean("logoService");
			logoService.findDefaultLogo();

			// 缓存企业信息
			EnterpriseInfoService enterpriseInfoService = (EnterpriseInfoService) AppUtil.getBean("enterpriseInfoService");
			enterpriseInfoService.findDefaultInfo();
		}
	}
}