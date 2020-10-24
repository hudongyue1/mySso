package com.cqu.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Ziry on 2018/5/6.
 */
public class MySessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    	System.out.printf("id为%s的会话已经创建\n", httpSessionEvent.getSession().getId());
        MySessionContext.addSession(httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        System.out.printf("id为%s的会话已经销毁\n", session.getId());
        MySessionContext.delSession(session);
    }

}
