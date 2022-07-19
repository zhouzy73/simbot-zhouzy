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
    public static final String COOKIE = "first_visit_datetime_pc=2022-07-06+12%3A04%3A43; p_ab_id=6; p_ab_id_2=8; p_ab_d_id=488415002; yuid_b=IJl3aGk; _gcl_au=1.1.1368597599.1657076684; device_token=fabe12bd0241fe0755473438c837b946; privacy_policy_notification=0; a_type=0; _fbp=fb.1.1657077331564.1531604359; _im_vid=01G78R2YWFS6P6X1YRJ6JGHG1M; adr_id=ocAqWZP57vy1vd2JiWWu8UvX4YaopCKLIUSaGzt5OM0R0oYN; _im_uid.3929=i.gVB9x2sBS0erbhD9aUTiGA; __utmz=235335808.1657171996.4.4.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); login_ever=yes; privacy_policy_agreement=4; cto_bundle=9NH4O19vVnptYzVFZUhtYTM3U0t3ZFNRUld6JTJCMHdwV09QVDcxVURmJTJGQlUwdXoxak14VjBYR2E5ajlENG1jbVpIS2lpd1NwMVRpRDY2RFRYVld1NGRFU1hQJTJCc0dQb3oyZTVtOGtyVlJWYSUyRnlDSSUyQmxQOU1lakRTaWN2RGw4YmxSWU5va3Y0NFdzVWZ0b3g5UHBtRHhHSUpmekZnJTNEJTNE; p_b_type=1; QSI_S_ZN_5hF4My7Ad6VNNAi=v:0:0; _gid=GA1.2.1264716158.1658052569; user_language=zh; __utma=235335808.1747766826.1657076684.1658135051.1658193473.13; __utmc=235335808; tag_view_ranking=m3EJRa33xU~ThlAk1fdQu~RTJMXD26Ak~1GBicHu4Rs~EgUskyOdoa~54lL52L-wl~RV_kfGZ8ds~Lt-oEicbBr~O0WKFZuVbs~_EOd7bsGyl~TqiZfKmSCg~LJo91uBPz4~pzzjRSV6ZO~Nbvc4l7O_x~GNMs-PMZvG~K8esoIs2eW~n-1xsaLnkz~jyw53VSia0~jH0uD88V6F~Nqn2kKfM8q~1VgdMhBiax~MWAZ9b1q01~uusOs0ipBx~jhuUT0OJva~E22Fw6ZrnB~GeMrue5H_4~BSlt10mdnm~ZJC17bcQNT~BrsBIfWNi7~bmlUwNhlTg~xlTs57a9Mp~m47KFuLUuf~ZpK28jiWF0~ziiAzr_h04~j3leh4reoN~Ie2c51_4Sp~Nx1CukYg8-~_GuOetFMMO~xufWQ15ZA3~oeyWRL1M7N~FrWKuqe40u~wSclANo2yG~F3cbycMFub~NaPTSg42YI~tgP8r-gOe_~YXsA4N8tVW~zyKU3Q5L4C~2PzZzrnP0p~mEY_dOiRBE~4GFaTkgcfI~vVXuUaW_Em~AMwBN_-Fo_~kGYw4gQ11Z~oyyx_C5T6j~yn0q6EacKH~ZHC4UJ0Yqv~r6Py13-NWH~_dOo6zAywB~lH5YZxnbfC~eLGuAzPy_R~eLV8EX906K~w6DOLSTOSN~LoDIs84uJh~PwDMGzD6xn~2R7RYffVfj~EZQqoW9r8g~-98s6o2-Rp~vuWQbxozJv~xPzNjdEfl2~mFosrNEiIG~FrSumU5wXT~QBodg0XINN~PUu9jDANP3~0Chn6pjoxR~o1MLTQ4V25~gQ9f732ax3~_bmhOhm8AL~aOY75z_H9A~nQRrj5c6w_~qVKTpGjJzt~YOrjV-1jNa~AysSzsXCYa~IrS8a9doZP~KMpT0re7Sq~ZNRc-RnkNl~ZPjQtvhTg3~xCR2NgnI9z~PiKFMvIHS1; pt_s_47mvrj9g=vt=1658193722489&cad=; pt_47mvrj9g=uid=QL4t5TOSwRH46qvo7X-4nQ&nid=0&vid=Y7K6VN3m4cdjmmL8tmJ3bQ&vn=3&pvn=1&sact=1658193741853&to_flag=0&pl=IZoRf4OCSv6aouL5bhDEAg*pt*1658193722489; __cf_bm=1PUDY2D7lSjG2OXCPTb0irsSHJRb6W1or4X0UnvoO2o-1658194933-0-Abibd9BDaiNdnQqoy/wO2jbfeDddXYYl7l/mJ0RAnpJ391n9qfZF+ypEmPnTRUqJsaEP5vCpLFOfcqUL9a7ghaFq4kFoGZZQFhF/mMRoo3UJXs/PpsjfLucG9fpoHHyniQYmWkzq/TxvX75OSCMMB3llOjNewkf4N5YUrWcbSIogdtws8hluBpuPCMLRGVP+mA==; __utmt=1; PHPSESSID=83937212_DtxbmWirHfZ3zQA72VXxCXX5ftW5cl8B; c_type=29; b_type=2; __utmv=235335808.|2=login%20ever=yes=1^3=plan=premium=1^5=gender=female=1^6=user_id=83937212=1^9=p_ab_id=6=1^10=p_ab_id_2=8=1^11=lang=zh=1; _ga_75BBYNYN9J=GS1.1.1658193474.23.1.1658195416.0; _ga=GA1.2.1747766826.1657076684; _gat_UA-1830249-3=1; __utmb=235335808.27.9.1658193730013";

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
        String[] headers = { "User-Agent", USER_AGENT, "Referer", REFERER, "Cookie", COOKIE};
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
