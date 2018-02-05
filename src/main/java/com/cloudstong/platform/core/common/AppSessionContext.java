/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.common;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * @author michael
 * Created on 2012-12-4
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:实现对session的读取等操作
 * 
 */
public class AppSessionContext {
	@SuppressWarnings("rawtypes")
	private static HashMap mymap = new HashMap();

    @SuppressWarnings("unchecked")
	public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
        	return null;
        return (HttpSession) mymap.get(session_id);
    }
}
