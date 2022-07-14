package love.simbot.example.bean.apex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CraftBO {

    private String dailyTime;
    private String weeklyTime;
    private List<String> dailyItemList;
    private List<String> dailyAssetList;
    private List<String> weeklyItemList;
    private List<String> weeklyAssetList;
}
