package org.luolu.course.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XiaoLuUtil {

	/**
	 * 判断是否是QQ表情
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isQqFace(String content) {
		boolean result = false;

		// 判断QQ表情的正则表达式
		String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}
	
	
	
	
	
	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	
	
	
	/**
	 * xiaoqrobot的主菜单
	 * 
	 * @return
	 */
	public static String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("今天真是个好天气啊！天空碧空如洗，晴空万里，风轻云淡，天高气爽，云兴霞蔚，鹰击长空，天清气朗，星罗棋布，回光返照，海阔天空......咦？少年别走啊~ 这次算你走运我这有几款休闲益智类小游戏，不如来一发？ 保证不坑人！"+XiaoLuUtil.emoji(0x1F61D)+"").append("\n\n");
		buffer.append("1、 夜观星象，知天下风云").append("\n");
		buffer.append("2、L译助手，智能翻译").append("\n");
		buffer.append("3、 歌曲点播").append("\n");
		buffer.append("4、 人工智能人脸识别系统").append("\n");
		buffer.append("5  、一战到底答题游戏(暂未完成)").append("\n\n");
		buffer.append("回复“?”显示此帮助菜单");
		return buffer.toString();
	}
	
	
	
	/**
	 * Q译通使用指南
	 * 
	 * @return
	 */
	public static String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(XiaoLuUtil.emoji(0xe148)).append("L译通使用指南").append("\n\n");
		buffer.append("L译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    中 -> 英").append("\n");
		buffer.append("    英 -> 中").append("\n");
		buffer.append("    日 -> 中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}
	
	
	
	

	/**
	 * 歌曲点播使用指南
	 * 
	 * @return
	 */
	public static String getMusicUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("歌曲点播操作指南").append("\n\n");
		buffer.append("回复：歌曲+歌名").append("\n");
		buffer.append("例如：歌曲唯一").append("\n");
		buffer.append("或者：歌曲唯一@王力宏").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}
	
	
	
	/**
	 * 人脸检测帮助菜单
	 */
	public static String getFaceUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("人脸检测使用指南").append("\n\n");
		buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");
		buffer.append("快来试试你是不是长得太着急");
		return buffer.toString();
	}
}
