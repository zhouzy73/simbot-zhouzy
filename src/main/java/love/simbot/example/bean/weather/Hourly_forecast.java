package love.simbot.example.bean.weather;
import lombok.Data;

import java.util.Date;

@Data
public class Hourly_forecast {

    private Cond cond;
    private Date date;
    private String hum;
    private String pop;
    private String pres;
    private String tmp;
    private Wind wind;

}
