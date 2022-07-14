package love.simbot.example.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import love.simbot.example.bean.apex.BundleContent;
import love.simbot.example.bean.apex.Craft;
import love.simbot.example.bean.apex.CraftBO;
import love.simbot.example.bean.apex.CraftBean;
import love.simbot.example.utils.FileUtil;
import love.simbot.example.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum  ApexCraftService {
    INSTANCE;
    private static final String host = "https://api.mozambiquehe.re/crafting?auth=632580f53329778387bc044b54c2530b";
    private static final String DEFAULT = "查询功能当前不可用哦";

    public CraftBO getCraft() {
        String response = HttpClientUtil.httpGetRequest(host);
        String resJson = "{\"data\":" + response + "}";
        if (StringUtils.isNotBlank(response)) {
            CraftBean craftBean = JSONObject.parseObject(resJson, CraftBean.class);
            if (craftBean == null) {
                return null;
            }
            List<Craft> crafts = craftBean.getData();
            if (crafts.isEmpty()) {
                return null;
            }
            Craft craftDaily = crafts.get(0);
            String dailyTime = craftDaily.getStartDate() + "~" + craftDaily.getEndDate();
            List<String> dailyItemList = new ArrayList<>();
            List<String> dailyAssetList = new ArrayList<>();
            List<BundleContent> bundleContent1 = craftDaily.getBundleContent();
            if (!bundleContent1.isEmpty()) {
                for (BundleContent bundleContent : bundleContent1) {
                    dailyItemList.add(bundleContent.getItem());
                    dailyAssetList.add(bundleContent.getItemType().getAsset());
                }
            }
            Craft craftWeekly = crafts.get(1);
            String weeklyTime = craftWeekly.getStartDate() + "~" + craftWeekly.getEndDate();
            List<String> weeklyItemList = new ArrayList<>();
            List<String> weeklyAssetList = new ArrayList<>();
            List<BundleContent> bundleContent2 = craftWeekly.getBundleContent();
            if (!bundleContent2.isEmpty()) {
                for (BundleContent bundleContent : bundleContent2) {
                    weeklyItemList.add(bundleContent.getItem());
                    weeklyAssetList.add(bundleContent.getItemType().getAsset());
                }
            }
            return new CraftBO(dailyTime, weeklyTime, dailyItemList, dailyAssetList, weeklyItemList, weeklyAssetList);
        }
        return null;
    }

    public String getAsset(String url, String dir) {
        String[] nameSplit = url.split("/");
        String fileName = nameSplit[nameSplit.length - 1];
        File file = new File(dir + fileName);
        if (file.exists()) {
            return fileName;
        }
        FileUtil.download(url, dir, fileName);
        return fileName;
    }

    private String getNameCN(String name) {
        if (name.equals("bullets_mag_l3")) {
            return "加长式轻型弹夹-l3";
        } else if (name.equals("stock_tactical_l3")) {
            return "标准枪托";
        }
        return name;
    }
}
