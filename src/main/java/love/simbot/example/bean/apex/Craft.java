package love.simbot.example.bean.apex;

import lombok.Data;

import java.util.List;

@Data
public class Craft {

    private String bundle;
    private Long start;
    private Long end;
    private String startDate;
    private String endDate;
    private String bundleType;
    private List<BundleContent> bundleContent;

}
