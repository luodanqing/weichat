package com.qinglang.maicc.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.luolu.course.service.CoreService;
import org.luolu.course.util.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WebController {
    /**
     * 处理微信服务器发来的消息
     */
    @RequestMapping(value = "/messageHandle.htm")
    public void messageHandle(HttpServletRequest request, HttpServletResponse response) {
        String respMessage = CoreService.processRequest(request);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(respMessage);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            pw.close();
        }
        // 响应消息 PrintWriter out = response.getWriter();

    }


    @RequestMapping(value = "/signHandle.htm")
    public void signHandle(HttpServletRequest request, HttpServletResponse response) {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败

            out.close();
            out = null;
        }
    }
}
