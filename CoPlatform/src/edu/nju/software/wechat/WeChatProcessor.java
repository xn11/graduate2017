package edu.nju.software.wechat;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sword.wechat4j.WechatSupport;
import org.sword.wechat4j.response.ArticleResponse;

import edu.nju.software.util.WeChatInstruct;

@Component
public class WeChatProcessor extends WechatSupport {

	private static Logger logger = Logger.getLogger(WeChatProcessor.class);

	@Autowired
	private WeChatRsp weChatRsp;

	@Autowired
	public WeChatProcessor(HttpServletRequest request) {
		super(request);
		weChatRsp = new WeChatRsp();
	}

	/**
	 * 文本消息
	 */
	@Override
	protected void onText() {
		// String prefix = "njucowork.sinaapp.com";
		String content = wechatRequest.getContent().trim();
		String openId = wechatRequest.getFromUserName();
		// String parameter = "?openId=" + openID;

		logger.info(content);

		// 回复任务相关图文链接
		// if ((content.toUpperCase()).equals(WeChatInstruct.TASKS)) {
		/*if (KeywordHandler.isTask(content)) {
			List<ArticleResponse> tasksRsp = weChatRsp.tasksRsp(openId);
			responseNews(tasksRsp);
		}*/
		// 回复他人信息
		if (KeywordHandler.isOrder(content)) {
			content = content.substring(1);
			String result = "糟糕，您没有权限(⊙o⊙)！ ";
			String[] info = content.split(" ");
			if (info.length == 1) {
				result = weChatRsp.userInfoRsp(info[0]);
			} else if (info.length == 2) {
				result = weChatRsp.sendInfoRsp(info[0], info[1], content);
			} else {
				result = "并不能看懂o(╯□╰)o！"+content+"  "+info.length;
			}
			 responseText(result);
		}
		// 回复任务相关图文链接
		else if (KeywordHandler.isTask(content)) {
			List<ArticleResponse> tasksRsp = weChatRsp.tasksRsp(openId);
			responseNews(tasksRsp);
		}
		// 回复资讯
		// else if ((content.toUpperCase()).equals(WeChatInstruct.NEWS)) {
		else if (KeywordHandler.isNews(content)) {
			List<ArticleResponse> news = weChatRsp.newsRsp();

			if (news == null) {
				responseText("抱歉，没有最新资讯咯╮(╯▽╰)╭");
			} else {
				responseNews(news);
			}
		}
		// 回复公司信息
		else if (KeywordHandler.isCompany(content)) {
			responseText(weChatRsp.companyRsp());
		}
		// 回复帮助信息
		// else if ((content.toUpperCase()).equals(WeChatInstruct.HELP)) {
		else if (KeywordHandler.isHelp(content)) {
			responseText(weChatRsp.helpRsp());
		} else {
			// 聊天机器人
			responseText(weChatRsp.chattingRobotRsp(content));
		}
	}

	/**
	 * 图片消息
	 */
	@Override
	protected void onImage() {
		String picUrl = wechatRequest.getPicUrl();
		String MediaId = wechatRequest.getMediaId();
		String MsgId = wechatRequest.getMsgId();

		String result = "图片消息picUrl:" + picUrl + ", MediaId:" + MediaId
				+ ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
		// responseImage(mediaId);
	}

	/**
	 * 语音消息
	 */
	@Override
	protected void onVoice() {
		String Format = wechatRequest.getFormat();
		String MediaId = wechatRequest.getMediaId();// 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
		String MsgId = wechatRequest.getMsgId();

		String result = "语音消息Format:" + Format + ", MediaId:" + MediaId
				+ ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
		// responseVoice(mediaId);

		// 回复音乐消息
		// MusicResponse music = new MusicResponse();
		// music.setTitle(title);
		// music.setDescription(description);
		// music.setMusicURL(musicURL);
		// music.setHQMusicUrl(hQMusicUrl);
		// music.setThumbMediaId(thumbMediaId);
		// responseMusic(music);
		//
		// responseMusic(title, description, musicURL, hQMusicUrl,
		// thumbMediaId);
	}

	/**
	 * 视频消息
	 */
	@Override
	protected void onVideo() {
		String ThumbMediaId = wechatRequest.getThumbMediaId();
		String MediaId = wechatRequest.getMediaId();// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
		String MsgId = wechatRequest.getMsgId();

		String result = "视频消息ThumbMediaId:" + ThumbMediaId + ", MediaId:"
				+ MediaId + ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);

		// 回复视频消息
		// VideoResponse video = new VideoResponse();
		// video.setTitle(title);
		// video.setDescription(description);
		// video.setMediaId(mediaId);
		// responseVideo(video);
		//
		// responseVideo(mediaId, title, description);
	}

	/**
	 * 地理位置消息
	 */
	@Override
	protected void onLocation() {
		String Location_X = wechatRequest.getLocation_X();
		String Location_Y = wechatRequest.getLocation_Y();
		String Scale = wechatRequest.getScale();
		String Label = wechatRequest.getLabel();
		String MsgId = wechatRequest.getMsgId();

		String result = "地理位置消息Location_X:" + Location_X + ", Location_Y:"
				+ Location_Y + ", Scale:" + Scale + ", Label:" + Label
				+ ", MsgId:" + MsgId;
		logger.info(result);
		// responseText(result);

		String content = Label + "怎么走";
		if (null == Label && StringUtils.isBlank(Label)) {
			content = "您的地理位置为：Location_X:" + Location_X + ", Location_Y:"
					+ Location_Y;
		}
		responseText("你是要去那里么？\n" + weChatRsp.chattingRobotRsp(content));
	}

	/**
	 * 链接消息
	 */
	@Override
	protected void onLink() {
		String Title = wechatRequest.getTitle();
		String Description = wechatRequest.getDescription();
		String Url = wechatRequest.getUrl();
		String MsgId = wechatRequest.getMsgId();

		String result = "链接消息Title:" + Title + ", Description:" + Description
				+ ", Url:" + Url + ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 未知消息类型，错误处理
	 */
	@Override
	protected void onUnknown() {
		String msgType = wechatRequest.getMsgType();

		String result = "未知消息msgType:" + msgType;
		logger.info(result);
		responseText(result);

	}

	/**
	 * 扫描二维码事件
	 */
	@Override
	protected void scan() {
		String FromUserName = wechatRequest.getFromUserName();
		String Ticket = wechatRequest.getTicket();

		String result = "扫描二维码事件FromUserName:" + FromUserName + ", Ticket:"
				+ Ticket;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 订阅事件
	 */
	@Override
	protected void subscribe() {
		String openId = wechatRequest.getFromUserName();
		responseText(weChatRsp.subscribeRsp(openId));
	}

	/**
	 * 取消订阅事件
	 */
	@Override
	protected void unSubscribe() {
		String FromUserName = wechatRequest.getFromUserName();
		String result = "取消订阅FromUserName:" + FromUserName;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 点击菜单跳转链接时的事件推送
	 */
	@Override
	protected void view() {
		String link = super.wechatRequest.getEventKey();
		logger.info("点击菜单跳转链接时的事件推送link:" + link);
		responseText("点击菜单跳转链接时的事件推送link:" + link);
	}

	/**
	 * 自定义菜单事件
	 */
	@Override
	protected void click() {
		String key = super.wechatRequest.getEventKey();
		logger.info("自定义菜单事件eventKey:" + key);
		responseText("自定义菜单事件eventKey:" + key);
	}

	/**
	 * 上报地理位置事件
	 */
	@Override
	protected void location() {
		String Latitude = wechatRequest.getLatitude();
		String Longitude = wechatRequest.getLongitude();
		String Precision = wechatRequest.getPrecision();
		String result = "上报地理位置事件Latitude:" + Latitude + ", Longitude:"
				+ Longitude + ", Precision:" + Precision;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 模板消息发送成功推送事件
	 */
	@Override
	protected void templateMsgCallback() {
		String MsgID = wechatRequest.getMsgId();
		String Status = wechatRequest.getStatus();
		String result = "模板消息发送成功推送事件MsgID:" + MsgID + ", Status:" + Status;
		logger.info(result);
	}

	/**
	 * 弹出地理位置选择器的事件
	 */
	@Override
	protected void locationSelect() {
		String Location_X = wechatRequest.getSendLocationInfo().getLocation_X();
		String Location_Y = wechatRequest.getSendLocationInfo().getLocation_Y();
		String Scale = wechatRequest.getSendLocationInfo().getScale();
		String Label = wechatRequest.getSendLocationInfo().getLabel();
		String Poiname = wechatRequest.getSendLocationInfo().getPoiname();
		String result = "弹出地理位置选择器的事件Location_X:" + Location_X
				+ ", Location_Y:" + Location_Y + ", Scale:" + Scale
				+ ", Label:" + Label + ", Poiname:" + Poiname;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 弹出拍照或相册发图的事件
	 */
	@Override
	protected void picPhotoOrAlbum() {
		String Count = wechatRequest.getSendPicsInfo().getCount();
		String PicMd5Sum = "";
		if (StringUtils.isNotBlank(Count) && !Count.equals("0")) {
			PicMd5Sum = wechatRequest.getSendPicsInfo().getItem().get(0)
					.getPicMd5Sum();
		}
		String result = "弹出系统拍照发图的事件Count:" + Count + ", PicMd5Sum:"
				+ PicMd5Sum;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 弹出系统拍照发图的事件
	 */
	@Override
	protected void picSysPhoto() {
		String Count = wechatRequest.getSendPicsInfo().getCount();
		String result = "弹出系统拍照发图的事件Count:" + Count;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 弹出微信相册发图器的事件推送
	 */
	@Override
	protected void picWeixin() {
		String Count = wechatRequest.getSendPicsInfo().getCount();
		String result = "弹出系统拍照发图的事件Count:" + Count;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 扫码推事件
	 */
	@Override
	protected void scanCodePush() {
		String ScanType = wechatRequest.getScanCodeInfo().getScanType();
		String ScanResult = wechatRequest.getScanCodeInfo().getScanResult();
		String result = "扫码推事件ScanType:" + ScanType + ", ScanResult:"
				+ ScanResult;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 扫码推事件且弹出“消息接收中”提示框的事件
	 */
	@Override
	protected void scanCodeWaitMsg() {
		String ScanType = wechatRequest.getScanCodeInfo().getScanType();
		String ScanResult = wechatRequest.getScanCodeInfo().getScanResult();
		String result = "扫码推事件ScanType:" + ScanType + ", ScanResult:"
				+ ScanResult;
		logger.info(result);
		responseText(result);
	}

}
