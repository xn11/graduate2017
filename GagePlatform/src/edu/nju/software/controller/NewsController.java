package edu.nju.software.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cebbank.gage.pojo.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;
import edu.nju.software.wechat.WechatBroadcast;

@Controller
public class NewsController {

	@Autowired
	private NewsService newsService;

	@RequestMapping(value = { "/NewsList" }, method = RequestMethod.GET)
	public ModelAndView newsList(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<List<News>> newsResult = newsService
				.getLatestNews(companyId);
		if (newsResult.getResultCode() == ResultCode.NORMAL) {
			model.put("newsList", newsResult.getData());
		}

		GeneralResult<List<News>> notPublishNewsResult = newsService
				.getNotPublishedNews(companyId);
		if (notPublishNewsResult.getResultCode() == ResultCode.NORMAL) {
			model.put("notPublishedNewsList", notPublishNewsResult.getData());
		}

		return new ModelAndView("newsList", "model", model);
	}

	@RequestMapping(value = { "/NewsDetail" }, method = RequestMethod.GET)
	public ModelAndView newsDetail(HttpServletRequest request,
			HttpServletResponse response) {
		int newsId = CoUtils.getRequestIntValue(request, "newsId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<News> newsResult = newsService.getById(newsId);
		if (newsResult.getResultCode() == ResultCode.NORMAL) {
			model.put("news", newsResult.getData());
		}

		return new ModelAndView("newsDetail", "model", model);
	}

	@RequestMapping(value = { "/CreateNews" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult createNews(HttpServletRequest request,
			HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		News news = new News(companyId, title, content, new Date(), null);
		GeneralResult<Integer> createResult = newsService.create(news);
		return new NoDataJsonResult(createResult);
	}

	@RequestMapping(value = { "/EditNews" }, method = RequestMethod.GET)
	public ModelAndView editNews(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new CoHashMap(request);
		return new ModelAndView("newsEdit", "model", model);
	}

	@RequestMapping(value = { "/DeleteNews" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteNews(HttpServletRequest request,
			HttpServletResponse response) {
		int newsId = CoUtils.getRequestIntValue(request, "newsId", true);
		NoDataResult result = newsService.delete(newsId);
		return new NoDataJsonResult(result);
	}

	@RequestMapping(value = { "/PublishNews" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult publishNews(HttpServletRequest request,
			HttpServletResponse response) {
		int newsId = CoUtils.getRequestIntValue(request, "newsId", true);
		GeneralResult<News> newsResult = newsService.getById(newsId);
		if (newsResult.getResultCode() == ResultCode.NORMAL) {
			News news = newsResult.getData();
			news.setPublishTime(new Date());
			NoDataResult result = newsService.update(news);
			return new NoDataJsonResult(result);
		} else {
			return new NoDataJsonResult(newsResult);
		}
	}
	
	@Autowired
	private WechatBroadcast wechatBroadcast;

	@RequestMapping(value = { "/SubscribeNews" }, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult subscribeNews(HttpServletRequest request,
			HttpServletResponse response) {
		int newsId = CoUtils.getRequestIntValue(request, "newsId", true);
		GeneralResult<News> newsResult = newsService.getById(newsId);
		if (newsResult.getResultCode() == ResultCode.NORMAL) {
			News news = newsResult.getData();
			news.setPublishTime(new Date());
			NoDataResult result = newsService.update(news);
			wechatBroadcast.broadcastNews(news);
			return new NoDataJsonResult(result);
		} else {
			return new NoDataJsonResult(newsResult);
		}
	}
}
