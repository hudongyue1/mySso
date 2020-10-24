package com.cqu.servlets;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqu.database.DB;
import com.cqu.domains.User;
import com.cqu.listener.MySessionContext;

import java.io.IOException;
import java.util.UUID;

/**
 * 登录，Get请求访问页面，Post请求处理登录
 * 采用写死的方式模仿登录：
 * 用户名：test，密码：123456
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("到达cas登录中心");
        HttpSession session = request.getSession();
        System.out.println("全局sessionID:"+session.getId());
        
        String backUrl = (String)request.getParameter("backUrl");
        String sessionID = (String)request.getParameter("sessionID");
        System.out.println("backUrl:"+backUrl+" sessionID:"+sessionID);
        if(session != null) {//全局session不为空
        	String token = (String)session.getAttribute("token");
        	if(token != null) {
        		System.out.println("cas中心,backUrl:"+backUrl);
            	System.out.println("cas中心,token:"+token);
        		System.out.println("拥有全局session，但是没有局部session！");
        		System.out.println("backUrl"+backUrl+"token:"+token+"sessionID:"+sessionID);

            	if(backUrl == null || backUrl.length() == 0) {
            		request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request,response);
            		return;
            	}
        		System.out.println("backUrl"+backUrl);
                if(sessionID != null && backUrl != null && token != null) 
                	DB.addSessionStorage(backUrl, token, sessionID);
        		response.sendRedirect(backUrl+"/login?token="+token);
        		return;
        	}
        	//拥有全局session，所以直接返回token
        }
    	System.out.println("没有全局session，跳转到密码输入页面！");
    	request.setAttribute("backUrl", backUrl);
    	request.setAttribute("sessionID", sessionID);
    	request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request,response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String sessionID = request.getParameter("sessionID");
        String backUrl = request.getParameter("backUrl");


        System.out.println("获取到登录信息, id:"+id+", password:"+pwd);
        System.out.println("sessionID:"+sessionID+", backUrl:"+backUrl);
        
        User user = DB.findUser(id, pwd);

        if(user==null) {
            request.setAttribute("remind", "账户密码错误！");
            System.out.println("密码错误！");
            request.setAttribute("backUrl",backUrl);
            request.setAttribute("sessionID", sessionID);
        	request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request,response);
            return;
        }
        //密码正确，生成token，存在database里面
        String token = UUID.randomUUID().toString();
        System.out.println("token:"+token);
        //生成全局session
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setAttribute("token", token);
        MySessionContext.addSession(session);//将session装入session库
        System.out.println("生成全局session");
        //存储sessionID
        
        System.out.println("sessionID:"+sessionID+"backUrl:"+backUrl);
        if(sessionID != null && backUrl != null && token != null) 
            DB.addSessionStorage(backUrl, token, sessionID);

        DB.addTokenStorage(user, token);
        if(backUrl!=null && backUrl.length()!=0) {
        	System.out.println("密码正确！返回token");
            response.sendRedirect(backUrl+"/login?token="+token);
            return;
        }
        System.out.println("没有backurl");
        request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request,response);
    }

}
