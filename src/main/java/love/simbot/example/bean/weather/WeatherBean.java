package love.simbot.example.bean.weather;

import lombok.Data;

@Data
public class WeatherBean {

    private String code;
    private boolean charge;
    private String msg;
    private Result result;

}
