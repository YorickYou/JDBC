package com.customer.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ϊ�˱���Servlet�ġ����͡�������дһ��BaseServlet��������������һ��Servlet���Դ�
 * �����ֲ�ͬ�����󡣲�ͬ���������Servlet�Ĳ�ͬ������
 * @author Yorick
 *
 */
@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//������Ӧ�ͽ����ַ���
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//��ȡ��������
		String methodName = request.getParameter("method");
		// ��û��ָ��Ҫ���õķ���ʱ����ôĬ���������execute()������
		if(methodName==null||methodName.isEmpty()){
			throw new RuntimeException("�����봫��Ҫ���õķ������ƣ�ͨ��method��������ɣ�");
		}
		//ͨ������,��ȡMethod
		Class clazz = this.getClass();
		Method method = null;
		try {
			method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
//			System.out.println(method);
		} catch (Exception e) {
			//�ߵ�����˵��Ҫ�ҵķ���������
			throw new RuntimeException("��Ҫ���õķ���"+methodName+"������!");
		}
		//����Method��invoke����
		try {
			String path = (String) method.invoke(this, request,response);
			System.out.println(path);
			//����������������ص���null��գ���ô��ʾ���ض���Ҳ��ת��
			if(path==null||path.trim().isEmpty()){
				return;
			}
			 /*
			 * path����login��regist�������ص����ַ���
			 * path����ǰ׺������ʹ��ð�ŷָ��ַ������õ�ǰ׺�ͺ�׺
			 * ����ǰ׺˵����ת�������ض��򣬺�׺˵����Ҫת�����ض����·��
			 * ���û����ǰ׺����ʾת��
			 */
			int index = path.indexOf(":");
			//���û��ð�ţ�˵��û��ǰ׺����ô����ת��
			if(index==-1){
				request.getRequestDispatcher(path).forward(request, response);
			}else {
				String s1 = path.substring(0, index);
				String s2 = path.substring(index+1);
				//ǰ׺��f����ô˵��Ҫת��
				if(s1.equalsIgnoreCase("f")){
					request.getRequestDispatcher(s2).forward(request, response);
				}else if (s1.equalsIgnoreCase("r")) {//ǰ׺��r����ô˵��Ҫ�ض���
					if(s2.toLowerCase().startsWith("http://")){//��������Ŀ
						response.sendRedirect(s2);
					}else {//����Ŀ����
						response.sendRedirect(request.getContextPath()+s2);
					}
				}else {//�׳��쳣��˵��ǰ׺�Ǵ���ģ�
					throw new RuntimeException("��ָ���Ĳ�����" + s1 + ", ��ǰ����֧�֣�");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("�����õķ�����" + methodName + "�׳����쳣��", e);
		} 
		
	}

}