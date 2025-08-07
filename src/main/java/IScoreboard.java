import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.function.BiFunction;

public interface IScoreboard {
    public String title();

    public List<ScoreboardRow> rows(ServerPlayer player);

    public BiFunction<ServerPlayer, Scoreboard, Scoreboard> updateFunction();
}
