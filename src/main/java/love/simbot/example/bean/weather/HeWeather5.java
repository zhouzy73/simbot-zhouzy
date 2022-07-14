package love.simbot.example.bean.weather;

import lombok.Data;

import java.util.List;

@Data
public class HeWeather5 {

    private Aqi aqi;
    private Basic basic;
    private List<Daily_forecast> daily_forecast;
    private List<Hourly_forecast> hourly_forecast;
    private Now now;
    private String status;
    private Suggestion suggestion;

}
