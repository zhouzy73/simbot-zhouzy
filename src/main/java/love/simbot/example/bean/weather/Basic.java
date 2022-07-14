package love.simbot.example.bean.weather;

import lombok.Data;

@Data
public class Basic {

    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private Update update;

}
