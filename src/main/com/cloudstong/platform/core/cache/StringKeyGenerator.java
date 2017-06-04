/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.cache;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * @author Allan
 * Created on 2014-8-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class StringKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		StringBuffer buffer = new StringBuffer(); 
        if (params != null && params.length > 0) {  
            for (Object obj : params) {  
                if (obj != null) {  
                    if (obj instanceof AtomicInteger || obj instanceof AtomicLong || obj instanceof BigDecimal  
                            || obj instanceof BigInteger || obj instanceof Byte || obj instanceof Double  
                            || obj instanceof Float || obj instanceof Integer || obj instanceof Long  
                            || obj instanceof Short) {  
                        buffer.append(obj);  
                    } else if (obj instanceof List || obj instanceof Set || obj instanceof Map) {  
                        buffer.append(obj);  
                    } else if(obj instanceof Object[]){
                    	Object[] args = (Object[])obj;
                    	int len = args.length;
                    	for(int i=0;i<len;i++){
                    		buffer.append(args[i]);
                    	}
                    } else{
                        buffer.append(obj.hashCode());  
                    }  
                }  
            }  
        }  
        String pre = target.getClass().getSimpleName()+"."+method.getName();
        String keyGenerator = pre+buffer.toString().hashCode();  
        return keyGenerator; 
	}

}
