package love.simbot.example.bean.pixiv;

import lombok.Data;

import java.util.List;

@Data
public class BodyDO {

    private ExtraDataDO extraData;
    private IllustMangaDO illustManga;
    private List<String> relatedTags;
    //省略其他
}
