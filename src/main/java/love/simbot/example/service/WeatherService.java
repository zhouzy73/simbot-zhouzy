package love.simbot.example.service;

import com.alibaba.fastjson.JSONObject;
import love.simbot.example.bean.weather.Daily_forecast;
import love.simbot.example.bean.weather.HeWeather5;
import love.simbot.example.bean.weather.Result;
import love.simbot.example.bean.weather.WeatherBean;
import love.simbot.example.utils.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public enum WeatherService {
    INSTANCE;
    private static final String host = "https://way.jd.com/he/freeweather?appkey=1d33eb24db0c44f04cdda85133fddf70&city=";
    private static final String DEFAULT = "查询不到天气哦";

    public String getWeather(String city) {
        String response = HttpClientUtil.httpGetRequest(host + city);
        if (StringUtils.isNotBlank(response)) {
            WeatherBean weatherBean = JSONObject.parseObject(response, WeatherBean.class);
            if (weatherBean == null) {
                return DEFAULT;
            }
            Result result = weatherBean.getResult();
            if (result == null) {
                return DEFAULT;
            }
            List<HeWeather5> heWeather5 = result.getHeWeather5();
            if (CollectionUtils.isNotEmpty(heWeather5)) {
                HeWeather5 heWeather51 = heWeather5.get(0);
                if (heWeather51 == null || heWeather51.getStatus().equals("unknown location")) {
                    return DEFAULT;
                }
                String tmp = "";
                String weatherNow = "";
                String hum = "";
                String wind = "";
                if (heWeather51.getNow() != null) {
                    tmp = heWeather51.getNow().getTmp();
                    hum = heWeather51.getNow().getHum();
                    if (heWeather51.getNow().getCond() != null) {
                        weatherNow = heWeather51.getNow().getCond().getTxt();
                    }
                    if (heWeather51.getNow().getWind() != null) {
                        wind = heWeather51.getNow().getWind().getDir() + heWeather51.getNow().getWind().getSc() + "级";
                    }
                }
                String qlty = "";
                if (heWeather51.getAqi() != null && heWeather51.getAqi().getCity() != null) {
                    qlty = heWeather51.getAqi().getCity().getQlty();
                }
                String tmpSection = "";
                String sun = "";
                String weather = "";
                if (heWeather51.getDaily_forecast() != null) {
                    Daily_forecast dailyForecast = heWeather51.getDaily_forecast().get(0);
                    tmpSection = dailyForecast.getTmp().getMin() + " ~ " + dailyForecast.getTmp().getMax() + "°C";
                    sun = "日出时间 " + dailyForecast.getAstro().getSr() + "  日落时间 " + dailyForecast.getAstro().getSs();
                    weather = dailyForecast.getCond().getTxt_d();
                }
                String drsgTxt = "";
                String comfTxt = "";
                String uv = "";
                if (heWeather51.getSuggestion() != null && heWeather51.getSuggestion().getDrsg() != null) {
                    drsgTxt = heWeather51.getSuggestion().getDrsg().getTxt();
                    comfTxt = heWeather51.getSuggestion().getComf().getTxt();
                    uv = "紫外线" + heWeather51.getSuggestion().getUv().getBrf();
                }
                String outText = city + "天气：" + weather + "  " + tmpSection + "  " + wind + "  空气质量" + qlty
                        + "\n当前：" + tmp + "°C  " + weatherNow + "  " + uv + "\n" + sun
                        + "\n生活指数：" + comfTxt + "\n穿衣指数：" + drsgTxt;
                return outText;
            }
        }
        return DEFAULT;
    }
}
