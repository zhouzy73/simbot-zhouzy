package love.simbot.example.service;

import love.simbot.example.utils.HttpClientUtil;

/**
 * apex地图轮换实体类
 */
public enum ApexMapService {
    INSTANCE;
    private static final String host = "https://api.mozambiquehe.re/maprotation?auth=632580f53329778387bc044b54c2530b&version=2";
    private static final String DEFAULT = "查询功能当前不可用哦";

    public String getMap() {
        String response = HttpClientUtil.httpGetRequest(host);


        return "";
    }
}
