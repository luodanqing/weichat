package org.luolu.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.luolu.course.service.CoreService;
import org.luolu.course.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sina.sae.util.SaeUserInfo;


/**
 * 核心请求处理类
 * 
 * @author luolu
 * @date 2013-05-18
 */
public class CoreServlet extends HttpServlet {
    private static final long serialVersionUID = 4440739483644821986L;
    private Logger logger = LoggerFactory.getLogger(CoreServlet.class);


    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }


    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String respMessage = CoreService.processRequest(request);
        logger.info("respMessage:|" + respMessage);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(respMessage);
            logger.info("response:|" + respMessage);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            pw.close();
        }
    }

}
