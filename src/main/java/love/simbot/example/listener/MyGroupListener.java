package love.simbot.example.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.bean.apex.CraftBO;
import love.simbot.example.common.HelpEnum;
import love.simbot.example.common.NewsEnum;
import love.simbot.example.service.ApexCraftService;
import love.simbot.example.service.DiaryService;
import love.simbot.example.service.NewsService;
import love.simbot.example.utils.CatImageUtil;
import love.simbot.example.service.WeatherService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 群消息监听的示例类。
 * 所有需要被管理的类都需要标注 {@link Beans} 注解。
 * @author ForteScarlet
 */
@Beans
public class MyGroupListener {

    /** log */
    private static final Logger LOG = LoggerFactory.getLogger(MyGroupListener.class);
    private static final List<String> groupCodeList = Arrays.asList("523316054", "572860040", "632982611", "518968523");
    private static final String makeDir = "E:/Learning/Simbot/make/";
    private static final String mapDir = "E:/Learning/Simbot/map";

    @OnGroup
//    @Filter(value = "来点", matchType = MatchType.STARTS_WITH)
//    @Filter(value = "色图", matchType = MatchType.ENDS_WITH)
    @Filter(value = "来点{{name}}涩图", matchType = MatchType.REGEX_MATCHES)
    public void onMsgPicture(GroupMsg msg, @FilterValue("name") String name) {
        String message = msg.getText();
        //
        System.out.println(name + 111111);

    }

    @OnGroup
    @Filter(value = "天气", matchType = MatchType.ENDS_WITH)
    public void onMsgWeather(GroupMsg msg, MsgSender sender) {
        String groupCode = msg.getGroupInfo().getGroupCode();
        if (!groupCodeList.contains(groupCode)) {
            return;
        }
        String city = msg.getText().replace("天气", "");
        String weather = WeatherService.INSTANCE.getWeather(city);
        sender.SENDER.sendGroupMsg(groupCode, weather);
        return;
    }

    @OnGroup
    @Filter(value = "制造轮换", matchType = MatchType.EQUALS)
    public void onMsgApexCraft(GroupMsg msg, MsgSender sender) {
        String groupCode = msg.getGroupInfo().getGroupCode();
        if (!groupCodeList.contains(groupCode)) {
            return;
        }
        CraftBO craftBO = ApexCraftService.INSTANCE.getCraft();
        if (craftBO == null) {
            sender.SENDER.sendGroupMsg(groupCode, "没有查到制造轮换呢，请再试一次");
            return;
        }
        List<String> dailyAssetList = craftBO.getDailyAssetList();
        String dailyMsg = "";
        for (String asset : dailyAssetList) {
            String res = ApexCraftService.INSTANCE.getAsset(asset, makeDir);
            dailyMsg += CatImageUtil.INSTANCE.msg(makeDir + res);
        }
        List<String> weeklyAssetList = craftBO.getWeeklyAssetList();
        String weeklyMsg = "";
        for (String asset : weeklyAssetList) {
            String res = ApexCraftService.INSTANCE.getAsset(asset, makeDir);
            weeklyMsg += CatImageUtil.INSTANCE.msg(makeDir + res);
        }
/*        sender.SENDER.sendGroupMsg(groupCode, dailyMsg + "\n" + craftBO.getDailyTime() + "\n"
                + weeklyMsg + "\n" + craftBO.getWeeklyTime());*/
        sender.SENDER.sendGroupMsg(groupCode, dailyMsg + "\n" + weeklyMsg);    //只显示图片，无时间
        return;
    }

    @OnGroup
    @Filter(value = "地图轮换", matchType = MatchType.EQUALS)
    public void onMsgApexMap(GroupMsg msg, MsgSender sender) {
        String groupCode = msg.getGroupInfo().getGroupCode();
        if (!groupCodeList.contains(groupCode)) {
            return;
        }

    }

    @OnGroup
    @Filter(value = "{{type}}新闻", matchType = MatchType.REGEX_MATCHES)
    public void onMsgNews(GroupMsg msg, MsgSender sender, @FilterValue("type") String type) {
        String groupCode = msg.getGroupInfo().getGroupCode();
        if (!groupCodeList.contains(groupCode)) {
            return;
        }
        String url = "";
        String code = "";
        type = type.toUpperCase();
        if ((url = NewsEnum.getUrlByMessage(type)) != "") {
            code = NewsEnum.getCodeByMessage(type);
            String news = NewsService.INSTANCE.getNews(url, type, code);
            sender.SENDER.sendGroupMsg(groupCode, news);
        }
        return;
    }

    @OnGroup
    @Filter(value = "舔狗日记", matchType = MatchType.EQUALS)
    public void onMsgDiary(GroupMsg msg, MsgSender sender) {
        String groupCode = msg.getGroupInfo().getGroupCode();
        if (!groupCodeList.contains(groupCode)) {
            return;
        }
        String diary = DiaryService.INSTANCE.getDiary();
        sender.SENDER.sendGroupMsg(groupCode, diary);
        return;
    }

    @OnGroup
    @Filter(value = "help", matchType = MatchType.EQUALS)
    public void onGroupMsgHelp(GroupMsg groupMsg, MsgSender sender) {
        String groupCode = groupMsg.getGroupInfo().getGroupCode();
        if (!groupCodeList.contains(groupCode)) {
            return;
        }
        int count = 1;
        String help = "小善人大善举：";
        for (HelpEnum item : HelpEnum.values()) {
            help += "\n" + count + ". " + item.getMessage();
            ++count;
        }
        sender.SENDER.sendGroupMsg(groupCode, help);
    }

    @OnGroup
    @Filter(value = "111", matchType = MatchType.CONTAINS)
    public void test(GroupMsg groupMsg, MsgSender sender) {
        String groupCode = groupMsg.getGroupInfo().getGroupCode();
        System.out.println("111: " + groupCode);
    }

}
