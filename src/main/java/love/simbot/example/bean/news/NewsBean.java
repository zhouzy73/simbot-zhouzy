package love.simbot.example.bean.news;

import lombok.Data;

import java.util.List;

@Data
public class NewsBean {

    private Integer code;
    private String msg;
    private List<NewsDO> newsList;
}
