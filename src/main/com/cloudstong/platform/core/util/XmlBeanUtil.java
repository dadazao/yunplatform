package com.cloudstong.platform.core.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

public class XmlBeanUtil {
	public static Object unmarshall(String xml, Class clsToUnbound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[] { clsToUnbound });
		return unmarshall(jc, xml);
	}

	public static String marshall(Object serObj, Class clsToBound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[] { clsToBound });
		return marshall(jc, serObj);
	}

	private static Object unmarshall(JAXBContext jc, String xml) throws JAXBException {
		Unmarshaller u = jc.createUnmarshaller();
		return u.unmarshal(new StringReader(xml));
	}

	private static String marshall(JAXBContext jc, Object serObj) throws JAXBException, PropertyException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Marshaller m = jc.createMarshaller();
		m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
		m.setProperty("jaxb.encoding", System.getProperty("file.encoding"));
		m.marshal(serObj, out);
		return out.toString();
	}
}