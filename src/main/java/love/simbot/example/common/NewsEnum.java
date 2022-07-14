package love.simbot.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  NewsEnum {

    BAIDU_NEWS("1001", "百度", "http://api.tianapi.com/nethot/index?key=6f9c05fcda37b400558f13cc66784f51"),
    DOMESTIC_NEWS("1002", "国内", "http://api.tianapi.com/guonei/index?key=6f9c05fcda37b400558f13cc66784f51&num=10"),
    ALLNET_NEWS("1003", "全网", "http://api.tianapi.com/networkhot/index?key=6f9c05fcda37b400558f13cc66784f51"),
    NBA_NEWS("1004", "NBA", "http://api.tianapi.com/nba/index?key=6f9c05fcda37b400558f13cc66784f51&num=10");

    private String code;
    private String message;
    private String url;

    public static String getUrlByMessage(String message) {
        for (NewsEnum news : values()) {
            if (message.equals(news.message))
                return news.url;
        }
        return "";
    }

    public static String getCodeByMessage(String message) {
        for (NewsEnum news : values()) {
            if (message.equals(news.message))
                return news.code;
        }
        return "";
    }
}
