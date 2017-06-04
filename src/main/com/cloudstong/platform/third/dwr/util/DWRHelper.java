/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.third.dwr.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import com.cloudstong.platform.third.dwr.model.Message;

/**
 * @author Allan
 * Created on 2014-3-13
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class DWRHelper {
	private static LinkedList<Message> messages = new LinkedList<Message>();   
    private static ReentrantLock lock = new ReentrantLock(); //JDK5锁   
       
    public void addMessage(String text){   
        try{   
            lock.lock();   
               
            if(text!=null && text.trim().length()>0){   
                messages.addFirst(new Message(text));   
                if(messages.size()>10){   
                    messages.removeLast();   
                }   
            }   
        }catch(Exception ex){   
            ex.printStackTrace();   
        }finally{   
            lock.unlock();   
        }   
           
        //获得DWR上下文   
        WebContext webContext = WebContextFactory.get();   
           
        //获取当前页面URL，比如/ext3/test_tag.jsp   
        String currentPage = webContext.getCurrentPage();   
        //当前脚本sessin   
        ScriptSession scriptSession = webContext.getScriptSession();   
           
        //设置页面控件的值   
        Util util = new Util(scriptSession);   
        util.setValue("text", ""); //这里是清空页面输入框的值   
           
        //设置脚本sessin的属性值   
        scriptSession.setAttribute("uid", "cjm");   
           
        //获取脚本session的属性值   
        for(Iterator it=scriptSession.getAttributeNames();it.hasNext();){   
            String attrName = (String)it.next();   
        }   
           
        //获取所有浏览当前页面的脚本session   
        Collection<ScriptSession> sessions = webContext.getScriptSessionsByPage(currentPage);   
           
        Util utilAll = new Util(sessions);   
           
        //执行客户端脚本   
        ScriptBuffer script = new ScriptBuffer();   
        script.appendScript("clientFunction(")
          .appendData(scriptSession.getAttribute("uid"))   
          .appendScript(");");   
           
        for(ScriptSession session: sessions){   
            session.addScript(script);   
        }   
           
        //更新这些脚本session的一些元素   
        utilAll.removeAllOptions("messages");   
        utilAll.addOptions("messages", messages, "id", "text");   
    }   
}
