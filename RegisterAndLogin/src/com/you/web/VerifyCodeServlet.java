package com.you.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.vcode.utils.VerifyCode;

/**
 * 获取图片验证码
 * @author Yorick
 *
 */
@WebServlet("/VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取图片验证码对象
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		System.out.println("测试...");
		//获取验证码
		String code = vc.getText();
		//将验证码保存到session
		request.getSession().setAttribute("code", code);
		//图片输出给客户端
		response.setContentType("image/jpeg");
		ImageIO .write(image, "JPEG", response.getOutputStream());
	}

}
