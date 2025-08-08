import Exceptions.InvalidScoreboardException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardHandler {
    private final HashMap<String, IScoreboard> boards;
    private final HashMap<UUID, String> playerBoards;

    public ScoreboardHandler() {
        this.boards = new HashMap<>();
        this.playerBoards = new HashMap<>();
    }

    public void addScoreboard(String key, IScoreboard scoreboard) {
        boards.put(key, scoreboard);
    }

    private Scoreboard createScoreboard(ServerPlayer player, IScoreboard scoreboard) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("Skye", Criteria.create("Skye"), "Skye");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(scoreboard.title());
        List<ScoreboardRow> rows = scoreboard.rows(player);
        AtomicInteger counter = new AtomicInteger(rows.size() - 1);
        rows.forEach(row -> {
            int c = counter.getAndDecrement();
            String teamEntry = ChatColor.values()[c].toString();
            Team team = board.registerNewTeam(Integer.toString(c));
            team.addEntry(teamEntry);
            team.setPrefix(row.lhs());
            team.setSuffix(row.rhs());
            objective.getScore(teamEntry).setScore(c);
        });
        return board;
    }

    public void setScoreboard(ServerPlayer player, String scoreboard) throws InvalidScoreboardException {
        IScoreboard board = boards.get(scoreboard);
        if (board == null) throw new InvalidScoreboardException(scoreboard);
        playerBoards.put(player.id, scoreboard);

        Scoreboard scoreboardInstance = createScoreboard(player, board);
        player.player.setScoreboard(scoreboardInstance);
    }

    public void updateScoreboard(ServerPlayer player) {
        String boardKey = playerBoards.get(player.id);
        if (boardKey == null) throw new InvalidScoreboardException("Dynamic");
        IScoreboard board = boards.get(boardKey);
        if (board == null) throw new InvalidScoreboardException(boardKey);

        board.updateFunction().apply(player, player.player.getScoreboard());
    }
}
