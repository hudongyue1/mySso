package com.cqu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cqu.database.DB;
import com.cqu.domains.Mapping;
import com.cqu.domains.TokenStorage;
import com.cqu.domains.User;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String token = request.getParameter("token");
        String backUrl = request.getParameter("backUrl");
        
        System.out.println("Verify backUrl:" + backUrl);
        
        if(token == null){
            response.getWriter().write("{\"result\":\"false\"}");
            return;
        }
        //token是否是登录时服务端生成的
        //database 里面st即使token
        TokenStorage serviceTicket = DB.findTokenStoragebyToken(token);
        
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        
        if (serviceTicket != null) {
        	User user = serviceTicket.getUser();
        	
        	
        	Mapping map = DB.findMappingByBackUrlAndCasUser(backUrl, user);
        	if (map == null)
        		System.out.println("map为空！");
        	String localUserID = map.getLocalUser();
        	
        	System.out.println("localUser:" + localUserID);
        	System.out.println("find backUrl in map:"+backUrl);

        	String jsonStr ="{\"result\":\"success\",\"localUserID\":\""+localUserID+"\"}";
    	    PrintWriter out = response.getWriter() ;
            out.write(jsonStr);
            out.close();
        }else {
        	System.out.println("verify token验证失败，返回fail:"+backUrl);
        	String jsonStr ="{\"result\":\"fail\"}";
    	    PrintWriter out = response.getWriter() ;
            out.write(jsonStr);
            out.close();
        }
    }
}
