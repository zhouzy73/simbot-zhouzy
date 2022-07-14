package love.simbot.example.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws Exception {
        subStr();

    }

    //java发送get请求，并带上参数
    public static String sendGet(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = null;
        String data = "";
        try {
            uriBuilder = new URIBuilder("https://www.pixiv.net/ajax/search/artworks/noe?word=noe&mode=safe&p=1&type=all&lang=zh&s_mode=s_type");//你的host
//            uriBuilder.setParameter("key","123");     //附带参数
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            HttpHost proxy = new HttpHost("127.0.0.1", 58591, "http");
            RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(500000).setConnectionRequestTimeout(500000).setSocketTimeout(500000).build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            System.out.println(entity.getContent());
            if (entity != null) {
                // return it as a String
                String json_result = EntityUtils.toString(entity);
                System.out.println(json_result);
                JSONObject jsonObject = JSONObject.parseObject(json_result);
                if (jsonObject.containsKey("code") && jsonObject.get("code").toString().equals("1")){
                    data = jsonObject.get("data").toString();
                    System.out.println(data);
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void subStr() {
        String str = "https://i.pximg.net/img-original/img/2022/07/07/14/53/44/99559748_p0.png";
        String pid = "99559748";
        String[] res;

        String substringBetween = StringUtils.substringBetween(str, "img/", "/" + pid);
        res = substringBetween.split("/");
        for (String re : res) {
            System.out.println(re);
        }

    }


}
