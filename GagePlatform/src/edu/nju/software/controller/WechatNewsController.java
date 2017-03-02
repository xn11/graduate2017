package edu.nju.software.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cebbank.gage.model.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WechatNewsController {

	@Autowired
	private NewsService newsService;

	@RequestMapping(value = { "/wechat/NewsDetail" }, method = RequestMethod.GET)
	public ModelAndView newsDetail(HttpServletRequest request,
			HttpServletResponse response) {
		int newsId = CoUtils.getRequestIntValue(request, "newsId", true);

		Map<String, Object> model = new CoHashMap(request);
		GeneralResult<News> newsResult = newsService.getById(newsId);
		if (newsResult.getResultCode() == ResultCode.NORMAL) {
			model.put("wxnews", newsResult.getData());
			model.put("newspic", (newsId % 11));
		}

		// return new ModelAndView("wechat/newsDetail", "model", model);
		return new ModelAndView("wechat/tecent_news", "model", model);
	}

}
