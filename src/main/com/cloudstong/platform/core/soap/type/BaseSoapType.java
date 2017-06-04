package com.cloudstong.platform.core.soap.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.w3c.dom.Node;

abstract class BaseSoapType implements SoapType {
	public abstract Class<?>[] getBeanTypes();

	public abstract String[] getSoapTypes();

	private final Class<?> getDefaultClass() {
		Class[] klasses = getBeanTypes();
		if ((klasses == null) || (klasses.length == 0)) {
			return Object.class;
		}

		return klasses[0];
	}

	abstract Object convertCurrent(Class<?> paramClass, SOAPElement paramSOAPElement);

	public final Object convertToBean(Class<?> klass, SOAPElement[] elements) throws SOAPException {
		if ((elements == null) || (elements.length < 1)) {
			return null;
		}

		if (elements.length > 1) {
			List list = new ArrayList();
			for (SOAPElement element : elements) {
				Object obj = convertCurrent(klass, element);
				list.add(obj);
			}
			return list;
		}
		return convertCurrent(klass, elements[0]);
	}

	abstract void setCurrentValue(SOAPElement paramSOAPElement, Object paramObject, Class<?> paramClass);

	public final void setValue(SOAPElement element, Object obj, Class<?> klass) throws SOAPException {
		if (obj == null) {
			return;
		}

		if (((obj instanceof List)) || ((obj instanceof ArrayList))) {
			List list = (List) obj;
			if (list.size() == 0)
				return;
			if (list.size() == 1)
				setCurrentValue(element, list.get(0), klass);
			else
				for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
					Object vo = localIterator.next();
					Node newNode = element.cloneNode(true);
					SOAPElement newElement = element.getParentElement().addChildElement((SOAPElement) newNode);
					setCurrentValue(newElement, vo, klass);
				}
		} else {
			setCurrentValue(element, obj, klass);
		}
	}

	public final Object convertToBean(SOAPElement[] elements) throws SOAPException {
		return convertToBean(getDefaultClass(), elements);
	}

	public final void setValue(SOAPElement element, Object obj) throws SOAPException {
		setValue(element, obj, getDefaultClass());
	}
}