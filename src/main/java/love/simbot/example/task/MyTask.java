package love.simbot.example.task;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.sender.BotSender;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.timer.EnableTimeTask;
import love.forte.simbot.timer.Fixed;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Beans
@EnableTimeTask
public class MyTask {

    @Depend
    private BotManager manager;

    @Fixed(value = 5, timeUnit = TimeUnit.SECONDS, repeat = 0)
    public void task() {
        Bot bot = manager.getDefaultBot();
        BotSender sender = bot.getSender();
        sender.SENDER.sendPrivateMsg(1415084593L, LocalDateTime.now().toString() + " 启动");
    }
}
