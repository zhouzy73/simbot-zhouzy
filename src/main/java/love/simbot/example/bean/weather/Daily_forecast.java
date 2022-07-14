package love.simbot.example.bean.weather;
import lombok.Data;

import java.util.Date;

@Data
public class Daily_forecast {

    private Astro astro;
    private Cond cond;
    private Date date;
    private String hum;
    private String pcpn;
    private String pop;
    private String pres;
    private Tmp tmp;
    private String uv;
    private String vis;
    private Wind wind;

}
