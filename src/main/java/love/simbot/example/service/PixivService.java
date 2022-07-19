package love.simbot.example.service;

import com.alibaba.fastjson.JSONObject;
import love.simbot.example.bean.pixiv.KeywordResBean;
import love.simbot.example.utils.PivixHttpClientUtil;
import love.simbot.example.utils.PixivDownloadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.Random;

public enum  PixivService {
    INSTANCE;
    private static final String artworksUrl = "https://www.pixiv.net/ajax/search/artworks/";
    //model:分级 all/safe/r18     p:页码    order:排序 date/date_d/popular_d/popular_male_d/popular_female_d
    //s_model: s_tag/s_type
    private static final String artworksUrlEnd = "?mode=all&p=1&type=all&lang=zh&s_mode=s_tag&order=popular_d&word=";

    private static final String DEFAULT = "当前图片功能出错啦";
    private static final String DEFAULT_DOWNLOAD = "图片下载时出错啦";

    //将图片下载到本地
    public String downloadPicture(String picId, String path) {
        String url = getPictureUrl(picId);
        if (url.equals("")) {
            System.out.println("未找到图片！");
            return DEFAULT_DOWNLOAD;
        }
        String pictureType = getPictureType(url);
        String fileName = path + picId + "_p0." + pictureType;
        // 判断是否已有同名文件，减少下载次数
        File file = new File(fileName);
        if (file.exists()) {
            System.out.println("文件已存在！");
            return fileName;
        }
        PixivDownloadUtil downloadUtil = new PixivDownloadUtil();
        try {
            downloadUtil.downloadPicture(url, file);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DEFAULT_DOWNLOAD;
    }

    //获取图片id
    public String getPictureId(String keyword) {
        String keywordUrl = artworksUrl + keyword + artworksUrlEnd + keyword;
        String resJson = "";
        PivixHttpClientUtil hcu = PivixHttpClientUtil.getInstance();
        try {
            CloseableHttpResponse response = hcu.doGet(keywordUrl);
            HttpEntity entity = response.getEntity();
            resJson = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return DEFAULT;
        }
        if (StringUtils.isNotBlank(resJson)) {
            KeywordResBean krBean = JSONObject.parseObject(resJson, KeywordResBean.class);
            if (krBean == null || krBean.getBody() == null || krBean.getBody().getIllustManga().getData() == null) {
                return DEFAULT;
            }
            //获取第二张图片
            Random random = new Random();
            String id = krBean.getBody().getIllustManga().getData().get(random.nextInt(6)).getId();
            //待优化，https://www.pixiv.net/ajax/illust/99569754?ref=https%3A%2F%2Fwww.pixiv.net%2Ftags%2F%25E4%25BA%2591%25E9%259F%25B5%2Fartworks%3Fs_mode%3Ds_tag&lang=zh
            //遍历 krBean.getBody().getIllustManga().getData() ，使用此接口对热度进行筛选
            return id;
        }
        return DEFAULT;
    }

    // 从HTML页面获取到原图链接
    public String getPictureUrl(String pictureId) {
        // 图片详情页链接，如https://www.pixiv.net/artworks/60095408
        String getTimeUrl = "https://www.pixiv.net/artworks/" + pictureId;
        // 原图链接，如https://i.pximg.net/img-original/img/2016/11/25/00/18/01/60095408_p0.jpg
        String url = "https://i.pximg.net/img-original/img/";
        String[] timeParts;
        String html = "";
        PivixHttpClientUtil hcu = PivixHttpClientUtil.getInstance();
        try {
            CloseableHttpResponse response = hcu.doGet(getTimeUrl);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        // 通过字符串切割获取时间
        String timeHtml = StringUtils.substringBetween(html, "\"original\":\"", "\"}");
        if (timeHtml == null || timeHtml.equals(""))
            return "";
        // 获取HTML网页中的时间部分，如2016\/11\/25\/00\/18\/01\/，并将其分割为字符串数组
        timeParts = StringUtils.substringBetween(timeHtml, "img/", "/" + pictureId).split("/");
        // 图片文件类型（jpg、png，暂未测试其他类型图片）
        String pictureType = getPictureType(timeHtml);
        // 拼接为原图链接
        for (String string : timeParts) {
            url = url + string + "/";
        }
        url = url + pictureId + "_p0." + pictureType;
        return url;
    }

    public String getPictureType(String s) {
        String pictureType = s.substring(s.lastIndexOf('.') + 1);
        return pictureType;
    }
}
