package love.simbot.example.bean.weather;

import lombok.Data;

@Data
public class Now {

    private Cond cond;
    private String fl;
    private String hum;
    private String pcpn;
    private String pres;
    private String tmp;
    private String vis;
    private Wind wind;

}
