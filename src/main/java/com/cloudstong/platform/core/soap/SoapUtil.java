package com.cloudstong.platform.core.soap;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.soap.type.SoapType;
import com.cloudstong.platform.core.soap.type.SoapTypes;
import com.cloudstong.platform.core.util.StringUtil;

public class SoapUtil {
	private static Logger logger = LoggerFactory.getLogger(SoapUtil.class);

	private static String getAttribute(Node node, String name) {
		Node tmp = node.getAttributes().getNamedItem(name);
		return tmp != null ? tmp.getTextContent() : null;
	}

	private static SOAPMessage invoke(URL invokeURL, SOAPMessage request) throws Exception {
		SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = null;
		try {
			URL endpoint = new URL(null, invokeURL.toString(), new URLStreamHandler() {
				protected URLConnection openConnection(URL u) throws IOException {
					URL clone_url = new URL(u.toString());
					HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
					clone_urlconnection.setConnectTimeout(2000);
					clone_urlconnection.setReadTimeout(3000);
					return clone_urlconnection;
				}
			});
			connection = soapConnFactory.createConnection();

			SOAPMessage reply = connection.call(request, endpoint);

			return reply;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	public static void invoke(Map variables, JSONArray jArray) throws Exception {
		if (jArray.size() == 0) {
			logger.warn("没有找到webservice的调用配置.", jArray);
			return;
		}
		try {
			for (Iterator localIterator = jArray.iterator(); localIterator.hasNext();) {
				Object obj = localIterator.next();
				JSONObject jObject = (JSONObject) obj;
				JSONArray inputs = jObject.getJSONArray("inputs");
				JSONArray outputs = jObject.getJSONArray("outputs");
				String url = jObject.getString("url");
				String namespace = jObject.getString("namespace");
				String method = jObject.getString("method");
				if ((StringUtil.isEmpty(url)) || (StringUtil.isEmpty(namespace)) || (StringUtil.isEmpty(method))) {
					logger.warn("没有获取到webservice的调用地址、名称空间或调用方法.", jObject);
				} else {
					SOAPMessage requestMessage = RequestBuilder.build(inputs, namespace, method, variables);
					Long t1 = Long.valueOf(System.currentTimeMillis());

					SOAPMessage responseMessage = invoke(new URL(url), requestMessage);

					Long t2 = Long.valueOf(System.currentTimeMillis());

					System.out.println("[获取返回值所用时间为:]" + (t2.longValue() - t1.longValue()) + "毫秒");

					ResponseBuilder.build(variables, outputs, responseMessage);
				}
			}
		} catch (Exception e) {
			logger.error("调用webservice出错.", e);
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		Map map = new HashMap();
//		map.put("a", new PageBean(100, 100));
//		map.put("abc", Integer.valueOf(10));

		String jsonStr = "[{\"url\":\"http://172.29.20.111:8080/bpm/service/ProcessService\",\"serviceName\":\"ProcessServiceImplService\",\"method\":\"endProcessByTaskId\",\"namespace\":\"http://api.webservice.platform.cloudstong.com/\",\"inputs\":[{\"name\":\"arg0\",\"soapType\":\"string\",\"bindingType\":\"3\",\"bindingVal\":\"if(1==1)&nuot;     return &quot;abc:123,,,,.....[}&quot;;\",\"javaType\":\"\"}],\"outputs\":[{\"name\":\"return\",\"soapType\":\"boolean\",\"bindingType\":\"2\",\"bindingVal\":\"abc\",\"javaType\":\"number\"}]}]";
		JSONArray jArray = JSONArray.fromObject(jsonStr);

		invoke(map, jArray);

		System.out.println(map.get("abc"));
	}

	public static class InvokeException extends Exception {
		private String code;
		private String msg;

		public InvokeException(String code, String msg) {
			this(code, msg, null);
		}

		public InvokeException(String code, String msg, Throwable e) {
			super(e);
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}

	private static class RequestBuilder {
		public static SOAPMessage build(JSONArray jarray, String namespace, String method, Map variables) throws SOAPException, SAXException,
				IOException, ParserConfigurationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
				ClassNotFoundException {
			return buildRequest(createRequest(jarray, namespace, method, variables));
		}

		private static SOAPMessage buildRequest(SOAPElement element) throws SOAPException {
			MessageFactory messageFactory = MessageFactory.newInstance();

			SOAPMessage message = messageFactory.createMessage();

			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();

			SOAPBody body = envelope.getBody();
			body.addChildElement(element);

			message.saveChanges();
			return message;
		}

		private static void buildSoapElementValue(SOAPElement soapElement, JSONObject jobject, Map variables) throws SOAPException,
				ClassNotFoundException {
			if (jobject == null)
				return;

			String binding = jobject.getString("bindingVal");
			String soapType = jobject.getString("soapType");
			String beanType = jobject.getString("javaType");
			Long bindingType = Long.valueOf(jobject.getLong("bindingType"));
			binding = StringUtil.jsonUnescape(binding);

			if ((bindingType.longValue() == 1L) && (StringUtil.isNotEmpty(binding))) {
				soapElement.setTextContent(binding);
			}

			if ((binding != null) || (bindingType.longValue() == 3L)) {
				Object obj = null;
				try {
					if (bindingType.longValue() == 3L) {
						GroovyScriptEngine scriptEngine = new GroovyScriptEngine();

						String scriptContent = binding;
						obj = scriptEngine.executeObject(scriptContent, variables);
					} else if (binding != null) {
						obj = PropertyUtils.getProperty(variables, binding);
					}
				} catch (Exception e) {
					SoapUtil.logger.error("动态设值出错.", e);
				}

				if (obj != null) {
					if (soapType != null) {
						SoapType converter = SoapTypes.getTypeBySoap(soapType);
						converter.setValue(soapElement, obj);
					} else if (beanType != null) {
						Class klass = Class.forName(beanType);
						SoapType converter = SoapTypes.getTypeByBean(klass);
						converter.setValue(soapElement, obj, klass);
					} else {
						soapElement.setTextContent(obj.toString());
					}
				}
			}
		}

		private static SOAPElement createRequest(JSONArray jarray, String namespace, String method, Map variables) throws SOAPException,
				SAXException, IOException, ParserConfigurationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
				ClassNotFoundException {
			String prefix = "api";
			SOAPElement bodyElement = SOAPFactory.newInstance().createElement(method, prefix, namespace);

			for (Iterator localIterator = jarray.iterator(); localIterator.hasNext();) {
				Object obj = localIterator.next();
				JSONObject jobject = (JSONObject) obj;
				if (jobject != null) {
					SOAPElement element = bodyElement.addChildElement(jobject.getString("name"));

					buildSoapElementValue(element, jobject, variables);
				}
			}
			return bodyElement;
		}
	}

	private static class ResponseBuilder {
		public static void build(Map variables, JSONArray jarray, SOAPMessage message) throws SOAPException, SoapUtil.InvokeException {
			checkFault(message);

			NodeList nodeList = message.getSOAPBody().getFirstChild().getOwnerDocument().getElementsByTagName("return");
			if ((nodeList == null) || (nodeList.getLength() < 1)) {
				return;
			}

			SOAPElement[] elements = new SOAPElement[nodeList.getLength()];
			for (int i = 0; i < elements.length; i++) {
				elements[i] = ((SOAPElement) nodeList.item(i));
			}

			for (Iterator localIterator = jarray.iterator(); localIterator.hasNext();) {
				Object obj = localIterator.next();
				JSONObject jobject = (JSONObject) obj;
				build(variables, elements, jobject);
			}
		}

		private static void build(Map variables, SOAPElement[] elements, JSONObject jobject) {
			if (jobject == null)
				return;

			String binding = jobject.getString("bindingVal");
			String soapType = jobject.getString("soapType");
			String beanType = jobject.getString("javaType");
			Long bindingType = Long.valueOf(jobject.getLong("bindingType"));
			binding = StringUtil.jsonUnescape(binding);

			if (binding != null) {
				try {
					if (soapType != null) {
						SoapType converter = SoapTypes.getTypeBySoap(soapType);
						Object obj = converter.convertToBean(elements);
						PropertyUtils.setProperty(variables, binding, obj);
					} else if (beanType != null) {
						Class klass = Class.forName(beanType);
						SoapType converter = SoapTypes.getTypeByBean(klass);
						Object obj = converter.convertToBean(klass, elements);
						PropertyUtils.setProperty(variables, binding, obj);
					} else if (elements.length > 1) {
						List list = new ArrayList();
						for (SOAPElement element : elements) {
							list.add(element.getTextContent());
						}
						PropertyUtils.setProperty(variables, binding, list);
					} else {
						PropertyUtils.setProperty(variables, binding, elements[0].getTextContent());
					}

					if ((bindingType.longValue() == 1L) && (PropertyUtils.getProperty(variables, binding) == null))
						PropertyUtils.setProperty(variables, binding, binding);
				} catch (Exception e) {
					SoapUtil.logger.warn("获取值不成功,跳过.", e);
				}
			}
		}

		private static void checkFault(SOAPMessage message) throws SOAPException, SoapUtil.InvokeException {
			SOAPBody body = message.getSOAPBody();
			SOAPFault fault = body.getFault();
			if ((fault != null) && (fault.getFaultCode() != null))
				throw new SoapUtil.InvokeException(fault.getFaultCode(), fault.getFaultString());
		}
	}
}