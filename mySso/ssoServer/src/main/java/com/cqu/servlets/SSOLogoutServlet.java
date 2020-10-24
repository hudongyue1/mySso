package com.cqu.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.cqu.database.DB;
import com.cqu.domains.SessionStorage;
import com.cqu.listener.MySessionContext;

import java.io.IOException;
import java.util.List;

/**
 * SSO系统注销通知Servlet
 */
@WebServlet("/logout")
public class SSOLogoutServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);//获取全局session
    	if(session == null) {
    		System.out.println("没有已登录账号，去到登录页面");
    		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request,response);
    		return;
    	}
        System.out.println("SSO退出登录");
        
        String token = (String)session.getAttribute("token");
        String backUrl = (String)request.getParameter("backUrl");
        System.out.println("ssoLogout，token:"+token);
        
        System.out.println("全局session，sessionID为："+session.getId().toString());
        List<SessionStorage> sessionStorageList = DB.findSessionStorage(token);	
        if(sessionStorageList == null)
        	System.out.println("sessionStorageList为空！！！");
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = null;
        HttpPost httpPost;
        try {
	          for (SessionStorage s : sessionStorageList) {

        		  System.out.println("退出子系统："+s.getBackUrl());

	        	  httpClient = HttpClientBuilder.create().build();
	        	  httpPost = new HttpPost(s.getBackUrl()+"/logout?sessionID="+s.getSessionId());

	        	  httpClient.execute(httpPost);

	          }
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        DB.deleteSessionStorage(token);
        DB.deleteServiceTicket(token);//删去ticket库里面的ticket
        MySessionContext.delSession(session);//从session库中删去session
        System.out.println("成功退出，回到登录页面");
        response.sendRedirect(backUrl+"/login");
    }

}
