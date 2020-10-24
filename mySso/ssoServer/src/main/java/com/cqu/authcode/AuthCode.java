package com.cqu.authcode;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/authCode")
public class AuthCode extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH=100;
	private static final int HEIGHT=40;
	private static final int LENGTH=4;
	private static final int FONT_SIZE=30;
	//设置验证码的尺寸
	
	char[] chars=("12334567890abcdefghijklmnopqrstuvwxyz"+
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	//所有可能出现的字符
	
	private static Random random = new Random();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		//设置输出格式
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		//设置不进行缓存
		
		BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
		//设置bufferedimage
		
		Graphics graphics = image.getGraphics();
		//取得画笔
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		graphics.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
		//设置画笔
		
		String code = "";
		//记录验证码，存于session中
		
		for(int i = 0; i < LENGTH; i++) {
			String c=""+chars[random.nextInt(chars.length)];
			//每次随机取出一个字符
			graphics.setColor(getColor());
			graphics.drawString(c, i * WIDTH / 4, HEIGHT/2+10);
			code+=c;
		}
		//绘制验证码
		
		for(int i=0;i<LENGTH;i++) {
			graphics.setColor(getColor());
			graphics.drawLine(random.nextInt(WIDTH),random.nextInt(HEIGHT),
					      random.nextInt(WIDTH),random.nextInt(HEIGHT));
		}
		//绘制干扰
		
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		//将验证码存在session中
		
		graphics.dispose();
		//结束绘制
		
		ImageIO.write(image,"JPEG",response.getOutputStream());
		//将图片输出
	}
	private Color getColor() {
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
	}
}
