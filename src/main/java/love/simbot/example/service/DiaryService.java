package love.simbot.example.service;

import com.alibaba.fastjson.JSONObject;
import love.simbot.example.bean.news.NewsBean;
import love.simbot.example.bean.news.NewsDO;
import love.simbot.example.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public enum  DiaryService {
    INSTANCE;
    private static final String host = "http://api.tianapi.com/tiangou/index?key=6f9c05fcda37b400558f13cc66784f51";
    private static final String DEFAULT = "今天没有暖心日记了哦";

    public String getDiary() {
        String response = HttpClientUtil.httpGetRequest(host);
        if (StringUtils.isNotBlank(response)) {
            NewsBean newsBean = JSONObject.parseObject(response, NewsBean.class);
            if (newsBean == null) {
                return DEFAULT;
            }
            List<NewsDO> newsList = newsBean.getNewsList();
            String content = newsList.get(0).getContent();
            if (!content.isEmpty()) {
                return content;
            }
        }
        return DEFAULT;
    }
}
