package com.cqu.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqu.listener.MySessionContext;

import java.io.IOException;

/**
 * 退出登录
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("web1退出登录");
        String sessionID = request.getParameter("sessionID");
        System.out.println("sesionID:"+sessionID);
        
        HttpSession session = request.getSession();
        System.out.println("web1本地session，sessionID为："+session.getId().toString());
        MySessionContext.delSessionById(sessionID);
        
        return;
    }
}
