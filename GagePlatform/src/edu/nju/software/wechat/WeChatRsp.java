package edu.nju.software.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sword.wechat4j.response.ArticleResponse;

import cebbank.gage.model.Member;
import cebbank.gage.model.News;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.WeChatInstruct;

/**
 * 微信事件回复
 */
@Component
public class WeChatRsp {
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private MemberService memberService;

	private String prefix = WeChatInstruct.DIMAIN;

	public WeChatRsp() {
	}

	// 返回任务相关图文链接
	public List<ArticleResponse> tasksRsp(String openId) {

		String parameter = "?openId=" + openId;

		List<ArticleResponse> tasksRsp = new ArrayList<ArticleResponse>();

		ArticleResponse viewTasksRsp = new ArticleResponse();
		viewTasksRsp.setTitle("查看任务");
		viewTasksRsp.setDescription("查看任务");
		viewTasksRsp.setUrl(prefix + "/wechat/MyTasks" + parameter);
		viewTasksRsp
				.setPicUrl("http://njucowork-pic.stor.sinaapp.com/assign.png");
		tasksRsp.add(viewTasksRsp);

		/*
		 * ArticleResponse modifyTasksRsp = new ArticleResponse();
		 * modifyTasksRsp.setTitle("修改任务状态");
		 * modifyTasksRsp.setDescription("修改任务状态"); modifyTasksRsp.setUrl("" +
		 * parameter); // TODO modifyTasksRsp
		 * .setPicUrl("http://njucowork-pic.stor.sinaapp.com/5.jpg");
		 * tasksRsp.add(modifyTasksRsp);
		 */
		return tasksRsp;
	}

	// 返回资讯消息体,图片URL默认为info.jpg
	public static ArticleResponse getNewsRsp(News news, String picURL) {

		if (null == news) {
			return null;
		}
		if (null == picURL || StringUtils.isBlank(picURL)) {
			picURL = "http://njucowork-pic.stor.sinaapp.com/info.jpg";
		}

		ArticleResponse newsRsp = new ArticleResponse();
		String parameter = "?newsId=" + news.getId();

		newsRsp.setTitle(news.getTitle());
		newsRsp.setDescription(news.getContent());
		newsRsp.setPicUrl(picURL);
		newsRsp.setUrl(WeChatInstruct.DIMAIN + "/wechat/NewsDetail" + parameter);

		return newsRsp;
	}

	// 返回资讯
	public List<ArticleResponse> newsRsp() {
		GeneralResult<List<News>> result = newsService.getLatestNews(1);

		if (result == null || result.getData() == null
				|| result.getData().isEmpty()) {
			return null;
		} else {
			List<News> newsList = result.getData();
			List<ArticleResponse> newsRsps = new ArrayList<ArticleResponse>();

			int index = 1;
			for (News news : newsList) {
				String picURL = "http://njucowork-pic.stor.sinaapp.com/number"
						+ index + ".png";
				ArticleResponse newsRsp = WeChatRsp.getNewsRsp(news, picURL);
				if (null == newsRsp) {
					continue;
				}

				/*
				 * ArticleResponse newsRsp = new ArticleResponse();
				 * newsRsp.setTitle(news.getTitle());
				 * newsRsp.setDescription(news.getContent());
				 * newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/number"
				 * + index + ".png");
				 */
				newsRsps.add(newsRsp);
				index++;
			}
			return newsRsps;
		}
	}

	// 返回帮助信息
	public String helpRsp() {
		String result = "您好，若需查询任务或修改任务状态请发送" + WeChatInstruct.TASKS
				+ ",若需获取最新资讯请发送" + WeChatInstruct.NEWS + ",若需要帮助请发送"
				+ WeChatInstruct.HELP + "。";

		return result;
	}

	// 返回公司信息
	public String companyRsp() {
		String result = "公司名称：南京大学软件学院\n"
				+ "公司简介：南京大学软件学院是南京大学所属的教学研究型工科学院，现设有软件工程本科专业、软件工程专业硕士专业、应用软件工程工学硕士与博士专业；拥有国家级软件工程人才培养模式创新实验区、国家软件人才国际培训（南京）基地。\n"
				+ "联系电话：18251324234\n" + "联系地址：南京市鼓楼区汉口路22号";

		return result;
	}

	// 返回公司信息
	/*public String orderRsp() {
		String result = "公司名称：\t南京大学软件学院\n"
				+ "公司简介：\t南京大学软件学院是南京大学所属的教学研究型工科学院，现设有软件工程本科专业、软件工程专业硕士专业、应用软件工程工学硕士与博士专业；拥有国家级软件工程人才培养模式创新实验区、国家软件人才国际培训（南京）基地。\n"
				+ "联系电话：\t18251324234\n" + "联系地址：\t南京市鼓楼区汉口路22号";

		return result;
	}*/

	private String getInfo(Member member){
		return "姓名："+member.getName()+"\n"
				+"工号："+ member.getWorkId()+"\n"
				+"联系电话："+member.getPhone();
	}
	
	// 返回用户信息
	public String userInfoRsp(String name) {
		GeneralResult<List<Member>> result = memberService.getByName(name);
		String res = "";
		if (result == null || result.getData() == null
				|| result.getData().isEmpty()) {
			return "查无此人╮(╯▽╰)╭!";
		} else {
			List<Member> memberList = result.getData();
			for(Member m:memberList){
				res = res + getInfo(m) + "\n";
			}
		}
		return res;
	}

	// 返回发送信息
	public String sendInfoRsp(String name, String info,String c) {
		String result = "消息发送成功！ 名字="+name+"；消息="+info+"；内容="+c;

		return result;
	}

	// 返回订阅反馈
	public String subscribeRsp(String openId) {
		String parameter = "?openId=" + openId;
		String link = "<a href=\"http://" + prefix + "/wechat/MyTasks"
				+ parameter + "\">绑定南大协同工作平台PC端账号~点我！</a>";

		String result = "感谢您关注南大任务协同平台!" + "\n\n" + helpRsp() + "\n\n" + link;

		return result;
	}

	// 返回聊天机器人回复
	public String chattingRobotRsp(String content) {
		String info = "";

		try {
			info = URLEncoder.encode(content.replaceAll(" ", "%20"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String requesturl = "http://www.tuling123.com/openapi/api?key=525dc3676cf81a5e8def59891d1ef813&info="
				+ info;

		HttpGet request = new HttpGet(requesturl);
		HttpResponse response;

		try {
			DefaultHttpClient client = new DefaultHttpClient();
			response = client.execute(request);

			// 200即正确的返回码
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				String[] splitString = result.split("\"");
				if (splitString.length >= 6) {
					result = splitString[5];
					result.replaceAll("<br>", " ");
				} else {
					result = "好像有哪里不对...";
				}
				return result;
			} else {
				return "什么鬼？？？";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "什么鬼？？？";
	}
}
