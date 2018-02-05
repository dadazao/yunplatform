package com.cloudstong.platform.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.system.dao.LogoDao;
import com.cloudstong.platform.system.model.Logo;
import com.cloudstong.platform.system.service.LogoService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:LOGO服务接口实现类
 */
@Repository("logoService")
public class LogoServiceImpl implements LogoService {

	@Resource
	private LogoDao logoDao;

	public LogoDao getLogoDao() {
		return logoDao;
	}

	public void setLogoDao(LogoDao logoDao) {
		this.logoDao = logoDao;
	}

	@Override
	public List<Logo> findLogo() {
		List<Logo> list = new ArrayList<Logo>();
		String path = this.getClass().getResource("/").getPath();
		path = path.split("WEB-INF")[0];
		String imgpath = path + "/images/logo/";
		File dir = new File(imgpath);
		File file[] = dir.listFiles();
		Logo logo = null;
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				logo = new Logo();
				String fileName = file[i].getName();
				String filePath = file[i].getPath();
				logo.setLogoName(fileName);
				logo.setLogoPath(filePath);
				list.add(logo);
			}
		}
		return list;
	}

	@Override
	public String getLogoName() {
		return logoDao.getLogoName();
	}

	@Override
	public void doDelete(Long[] ids) {
		logoDao.delete(ids);
	}

	@Override
	public void doInsertLogo(Logo logo) {
		logoDao.insert(logo);
	}

	@Override
	public void doUpdateLogo(Long id) {
		logoDao.update(id);
	}

	@Override
	public Logo findLogoById(Long id) {
		return logoDao.findLogoById(id);
	}

	@Override
	public Logo findDefaultLogo() {
		return logoDao.findDefaultLogo();
	}

	
}
