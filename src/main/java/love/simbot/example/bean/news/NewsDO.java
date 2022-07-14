package love.simbot.example.bean.news;

import lombok.Data;

@Data
public class NewsDO {

    //nba/国内
    private String id;
    private String ctime;
    private String title;
    private String description;
    private String source;
    private String picUrl;
    private String url;

    //全网
//    private String title;
    private String hotnum;
    private String digest;

    //baidu
    private String keyword;
    private String brief;
    private String index;
    private String trend;

    //日记内容
    private String content;

}
