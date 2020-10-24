package com.cqu.filter;

import net.sf.json.JSONObject;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.cqu.constants.Constants;
import com.cqu.listener.MySessionContext;
import com.cqu.model.User;
import database.DB;


/**
 * 登录拦截器
 * Created by Ziry on 2018/4/23.
 */
@WebFilter("/login")
public class LoginFilter implements Filter {

    public void destroy() {
        System.out.println("LoginFilter destroy ...");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("LoginFilter doFilter ...");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = (HttpSession) req.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user != null) {//存在局部session，且有已登录的用户

        	System.out.println("存在局部session，直接放行, ");
        	System.out.println("SessionId: "+ session.getId());
        	chain.doFilter(request, response);

        }else {
        	String token = req.getParameter("token");
        	
        	if(token == null || token.length() == 0) {//如果没有token或者token长度为0（无效token）
        		System.out.println("没有token，去到登录中心");
        		gotoLogin(req, resp);//重定向到登录中心
        		return;
        	}
        	
        	JSONObject verifyResult = this.verify(Constants.SSO_SERVER_URL+"/verify?backUrl=" 
        			+ Constants.WEB2_URL + "&token=" + token);
        	if(verifyResult != null) { 
        		String result = (String)verifyResult.get("result");//获取登录结果
        		String localUserID = (String)verifyResult.get("localUserID");
        		if(result.equals("success") && localUserID.length() != 0) {
            		User localUser = DB.getUser(localUserID);
            		System.out.println("校验token成功！登录成功！");
            		System.out.println("登录用户" + localUser.getId().toString());
            		//生成局部session
            		session.setAttribute("user", localUser);
            		//将局部session存储在sessionMap中
            		System.out.println("创建局部session，sessionID为："+session.getId().toString());
            		
            		MySessionContext.addSession(session);
            		System.out.println("session:"+MySessionContext.getSession(session.getId().toString()));
            		chain.doFilter(request, response);
            		return;
        		}
        		
        	}
        	
    		System.out.println("校验token失败，去到登陆中心" );
    		gotoLogin(req, resp);
        }  
    }
    private JSONObject verify(String verifyUrl) {

        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost(verifyUrl);
        JSONObject resultJson = null;
        // 设置ContentType
        //httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());// 返回json格式：
            resultJson = JSONObject.fromObject(result);   
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultJson;
    }
    /**
     * 重定向到登录页面
     * @param request
     * @param response
     * @throws IOException
     */
    public void gotoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String backUrl = Constants.WEB2_URL;
        System.out.println("qotoLogin, backUrl:" + backUrl);
        System.out.println("SSO_SERVER_URL:" + Constants.SSO_SERVER_URL);
        System.out.println("http://localhost:8080/ssoServer/login"+"?backUrl=" + backUrl);
        response.sendRedirect(Constants.SSO_SERVER_URL+"/login?backUrl=" + backUrl 
        		+ "&sessionID=" + request.getSession().getId().toString());
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("LoginFilter init ...");
    }

}
