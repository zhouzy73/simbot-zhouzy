package love.simbot.example.service;

import com.alibaba.fastjson.JSONObject;
import love.simbot.example.bean.news.NewsBean;
import love.simbot.example.bean.news.NewsDO;
import love.simbot.example.common.NewsEnum;
import love.simbot.example.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public enum NewsService {
    INSTANCE;
    private static final String DEFAULT = "当前新闻查询功能出错啦";

    public String getNews(String url, String message, String code) {
        String response = HttpClientUtil.httpGetRequest(url);
        if (StringUtils.isNotBlank(response)) {
            NewsBean newsBean = JSONObject.parseObject(response, NewsBean.class);
            if (newsBean == null) {
                return DEFAULT;
            }
            String result = message + "热点新闻：";
            List<NewsDO> newsList = newsBean.getNewsList();
            int count = 1;
            if (code.equals(NewsEnum.BAIDU_NEWS.getCode())) {
                for (NewsDO newsDO : newsList) {
                    if (newsDO.getBrief().contains("。")) {
                        newsDO.setBrief(newsDO.getBrief().substring(0, newsDO.getBrief().lastIndexOf("。") + 1));
                    } else {
                        newsDO.setBrief("");
                    }
                    result += "\n" + count + ".  " + newsDO.getKeyword() + "\n     " + newsDO.getBrief();
                    ++count;
                }
                return result;
            } else if (code.equals(NewsEnum.DOMESTIC_NEWS.getCode())) {
                for (NewsDO newsDO : newsList) {
                    result += "\n" + count + ".  " + newsDO.getTitle() + "\n     点击跳转:" + newsDO.getUrl();
                    ++count;
                }
                return result;
            } else if (code.equals(NewsEnum.ALLNET_NEWS.getCode())) {
                for (NewsDO newsDO : newsList) {
                    result += "\n" + count + ".  " + newsDO.getTitle();
                    ++count;
                }
                return result;
            } else if (code.equals(NewsEnum.NBA_NEWS.getCode())) {
                for (NewsDO newsDO : newsList) {
                    result += "\n" + count + ".  " + newsDO.getTitle() + "\n     " +
                            newsDO.getDescription().substring(0, newsDO.getDescription().length()-4) + "···" +
                            "\n     点击跳转:" + newsDO.getUrl();
                    ++count;
                }
                return result;
            }
        }
        return DEFAULT;
    }
}
