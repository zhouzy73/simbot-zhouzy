package love.simbot.example.bean.pixiv;

import lombok.Data;

import java.util.List;

@Data
public class IllustMangaDO {

    private List<BookmarkRangesDO> bookmarkRanges;
    private List<DataDO> data;
    private Integer total;
}
