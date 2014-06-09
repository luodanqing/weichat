package org.luolu.course.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.luolu.course.message.resp.Music;
import org.luolu.course.message.resp.MusicMessage;
import org.luolu.course.message.resp.TextMessage;
import org.luolu.course.util.MessageUtil;
import org.luolu.course.util.WeatherReport;
import org.luolu.course.util.XiaoLuUtil;


/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-05-20
 */
public class CoreService {
    /**
     * 处理微信发来的请求
     * 
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            String responseStr = null;
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                if (requestMap.get("Content").trim().startsWith("歌曲")) {

                    // 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
                    String keyWord = requestMap.get("Content").trim().replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");
                    // 如果歌曲名称为空
                    if ("".equals(keyWord)) { // 返回文本消息
                        responseStr = XiaoLuUtil.getMusicUsage();
                        TextMessage textMessage = new TextMessage();
                        textMessage.setToUserName(fromUserName);
                        textMessage.setFromUserName(toUserName);
                        textMessage.setCreateTime(new Date().getTime());
                        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        textMessage.setFuncFlag(0);
                        respContent = doTextMessage(requestMap);
                        textMessage.setContent(responseStr);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                        return respMessage;
                    }
                    else {
                        String[] kwArr = keyWord.split("@");
                        // 歌曲名称
                        String musicTitle = kwArr[0];
                        // 演唱者默认为空
                        String musicAuthor = "";
                        if (2 == kwArr.length) {

                            musicAuthor = kwArr[1];
                        }
                        // 搜索音乐
                        Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);
                        MusicMessage musicMessage = new MusicMessage();
                        musicMessage.setToUserName(fromUserName);
                        musicMessage.setFromUserName(toUserName);
                        musicMessage.setCreateTime(new Date().getTime());
                        musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                        musicMessage.setMusic(music);
                        respMessage = MessageUtil.musicMessageToXml(musicMessage);
                    }

                }
                else {
                    // 回复文本消息
                    TextMessage textMessage = new TextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setCreateTime(new Date().getTime());
                    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    textMessage.setFuncFlag(0);
                    respContent = doTextMessage(requestMap);
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }

            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {

                // 回复文本消息
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                textMessage.setFuncFlag(0);
                String result = doImgMessage(requestMap);
                textMessage.setContent(result);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

                    // 回复文本消息
                    TextMessage textMessage = new TextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setCreateTime(new Date().getTime());
                    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    textMessage.setFuncFlag(0);
                    respContent = XiaoLuUtil.getMainMenu();
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");

                    if (eventKey.equals("11")) {
                        respContent = "天气预报菜单项被点击！";
                    }
                    else if (eventKey.equals("12")) {
                        respContent = "公交查询菜单项被点击！";
                    }
                    else if (eventKey.equals("13")) {
                        respContent = "周边搜索菜单项被点击！";
                    }
                    else if (eventKey.equals("14")) {
                        respContent = "历史上的今天菜单项被点击！";
                    }
                    else if (eventKey.equals("21")) {
                        respContent = "歌曲点播菜单项被点击！";
                    }
                    else if (eventKey.equals("22")) {
                        respContent = "经典游戏菜单项被点击！";
                    }
                    else if (eventKey.equals("23")) {
                        respContent = "美女电台菜单项被点击！";
                    }
                    else if (eventKey.equals("24")) {
                        respContent = "人脸识别菜单项被点击！";
                    }
                    else if (eventKey.equals("25")) {
                        respContent = "聊天唠嗑菜单项被点击！";
                    }
                    else if (eventKey.equals("31")) {
                        respContent = "Q友圈菜单项被点击！";
                    }
                    else if (eventKey.equals("32")) {
                        respContent = "电影排行榜菜单项被点击！";
                    }
                    else if (eventKey.equals("33")) {
                        respContent = "幽默笑话菜单项被点击！";
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }


    public static String doTextMessage(Map<String, String> requestMap) {
        String content = requestMap.get("Content").trim();
        StringBuffer buffer = new StringBuffer();
        if (content.equals("?") || content.equals("？")) {
            return XiaoLuUtil.getMainMenu();
        }
        if (content != null && content.equals("1")) {
            // 查看天气预报
            buffer.append("hello~ ，想知道明天天气\ue04a\ue40b到底适合宅在家里睡觉？还是睡觉？还是睡觉呢？那就来查一下吧~\n");
            buffer.append("向我发送【城市名+天气】即可查询实时天气");
            buffer.append("例如：\n【衡阳天气】\n【深圳天气】\n");
            buffer.append("\ue412 这么简单你不会告诉我还不会用吧?~那我就真哭了");
            // 1：调用天气预报
            // String info = WeatherReport.getWeather() ;
        }
        else if (content.equals("2")) {
            return XiaoLuUtil.getTranslateUsage();
        }
        else if (content.equals("3")) {
            return XiaoLuUtil.getMusicUsage();
        }
        else if (content.equals("4")) {
            return XiaoLuUtil.getFaceUsage();
        }

        // 判断用户发送的是否是单个QQ表情
        if (XiaoLuUtil.isQqFace(content)) {
            // 用户发什么QQ表情，就返回什么QQ表情
            return content;

        }
        if (content.contains("天气")) {
            String city = content.substring(0, content.indexOf("天气"));
            String result = "赞无所查询城市天气预报\ue107  老实说你是不是乱输入些什么不存在的地名了?\ue40b";
            String weatherStr = WeatherReport.getWeather(city);
            if (!weatherStr.equals("#")) {
                String[] weatherInfo = weatherStr.split("#");
                result =
                        weatherInfo[0] + "省 " + weatherInfo[1] + "市 " + weatherInfo[4] + "" + weatherInfo[6]
                                + "" + weatherInfo[5] + "" + weatherInfo[10];
            }

            return result;
        }

        if (content.startsWith("翻译")) {
            String keyWord = content.replaceAll("^翻译", "").trim();
            if ("".equals(keyWord)) {
                return XiaoLuUtil.getTranslateUsage();
            }
            else {
                return BaiduTranslateService.translate(keyWord);
            }

        }
        return buffer.toString();
    }


    public static String doImgMessage(Map<String, String> requestMap) {

        // 取得图片地址
        String picUrl = requestMap.get("PicUrl");
        // 人脸检测
        String detectResult = FaceService.detect(picUrl);
        return detectResult;

    }

}
