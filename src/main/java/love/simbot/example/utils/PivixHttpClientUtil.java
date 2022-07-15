package love.simbot.example.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * get请求配置了Trojan代理，一般只用于Pixiv请求
 */
public class PivixHttpClientUtil {
    private volatile static PivixHttpClientUtil instance;
    private CloseableHttpClient httpClient;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64 ) " + "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/103.0.0.0 Safari/537.36";
    public static final String REFERER = "https://accounts.pixiv.net/login?lang=zh&source=pc&view_type=page&ref=wwwtop_accounts_index";

    private PivixHttpClientUtil() {
        // 以下三句是使用代理的方式，不使用代理就注释掉
//		HttpHost proxy = new HttpHost("127.0.0.1", 58591, "http");
//		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
//				.setSocketTimeout(5000).setProxy(proxy).build();
//		httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        // 不使用代理就打开下一行（在get请求配置代理，上面方式不生效）
        httpClient = HttpClients.createDefault();
    }

    // 双重检查锁单例模式，节约资源
    public static PivixHttpClientUtil getInstance() {
        if (instance == null) {
            synchronized (PivixHttpClientUtil.class) {
                if (instance == null) {
                    instance = new PivixHttpClientUtil();
                }
            }
        }
        return instance;
    }

    // 处理get请求
    public CloseableHttpResponse doGet(String url) throws ClientProtocolException, IOException {
        // 防止反爬虫
        String[] headers = { "User-Agent", USER_AGENT, "Referer", REFERER};
        return doGet(url, headers);
    }

    public CloseableHttpResponse doGet(String url, String[] headers) throws ClientProtocolException, IOException {
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        HttpHost proxy = new HttpHost("127.0.0.1", 58591, "http");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(5000).setProxy(proxy).build();
        for (int i = 0; i < headers.length; i += 2) {
            httpGet.addHeader(headers[i], headers[i + 1]);
        }
        httpGet.setConfig(requestConfig);
        response = httpClient.execute(httpGet);
        return response;
    }
}
