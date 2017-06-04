package com.cloudstong.platform.core.xml;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.DateFormatUtil;

public class XmlDateAdapter extends XmlAdapter<String, Date> {
	public Date unmarshal(String dateString) throws Exception {
		if (BeanUtils.isEmpty(dateString))
			return null;
		return DateFormatUtil.parse(dateString);
	}

	public String marshal(Date date) throws Exception {
		if (BeanUtils.isEmpty(date))
			return null;
		return DateFormatUtil.format(date);
	}
}