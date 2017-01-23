package edu.nju.software.wechat;

public class KeywordHandler {
	private static String[] KEYWORDS_CH = {"任务","资讯","帮助","公司","@"};
	private static String[] KEYWORDS_EN = {"task","news","help","Company","@"};
	private static String[] KEYWORDS_SHORT = {"t","n","h","c","@"};

	public static boolean isTask(String content){
		content = content.toLowerCase();
		return content.contains(KEYWORDS_CH[0])||content.contains(KEYWORDS_EN[0])||content.equals(KEYWORDS_SHORT[0]);
	}
	
	public static boolean isNews(String content){
		content = content.toLowerCase();
		return content.contains(KEYWORDS_CH[1])||content.contains(KEYWORDS_EN[1])||content.equals(KEYWORDS_SHORT[1]);
	}
	
	public static boolean isHelp(String content){
		content = content.toLowerCase();
		return content.contains(KEYWORDS_CH[2])||content.contains(KEYWORDS_EN[2])||content.equals(KEYWORDS_SHORT[2]);
		
	}
	
	public static boolean isCompany(String content){
		return content.contains(KEYWORDS_CH[3]);
	}
	
	public static boolean isOrder(String content){
		return content.contains(KEYWORDS_CH[4]);
	}
}
