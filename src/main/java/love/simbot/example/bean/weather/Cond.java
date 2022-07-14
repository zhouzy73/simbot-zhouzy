package love.simbot.example.bean.weather;

import lombok.Data;

@Data
public class Cond {

    //hourly_forecast
    private String code;
    private String txt;

    //daily_forecast
    private String code_d;
    private String code_n;
    private String txt_d;
    private String txt_n;

}
