package love.simbot.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  HelpEnum {
    HELP_WEATHER("0001", "查询天气（例：济宁天气）"),
    HELP_NEWS("0002", "新闻（例：百度新闻）（类型：百度/国内/全网/NBA）"),
    HELP_DIARY("0003", "舔狗日记（例：舔狗日记）"),
    HELP_APEX_CRAFT("0004", "Apex制造轮换（例：制造轮换）"),
    HELP_APEX_MAP("0005", "Apex地图轮换（例：地图轮换）"),
    ;

    private String code;
    private String message;
}
