package love.simbot.example.bean.weather;
import lombok.Data;

import java.util.Date;

@Data
public class Update {

    private Date loc;
    private Date utc;

}
