package love.simbot.example.bean.pixiv;

import lombok.Data;

import java.util.List;

@Data
public class DataDO {

    private String alt;
    private String bookmarkData;
    private String createDate;
    private String description;
    private Integer height;
    private String id;
    private Integer illustType;
    private Boolean isBookmarkable;
    private Boolean isMasked;
    private Boolean isUnlisted;
    private Integer pageCount;
    private String profileImageUrl;
    private Integer restrict;
    private Integer sl;
    private List<String> tags;
    private String title;
    private TitleCaptionTranslationDO titleCaptionTranslation;
    private String updateDate;
    private String url;
    private String userId;
    private String userName;
    private Integer width;
    private Integer xRestrict;
}
