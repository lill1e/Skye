import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

public class ScoreboardPrimary implements IScoreboard {
    @Override
    public String title() {
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Skye";
    }

    @Override
    public List<ScoreboardRow> rows(ServerPlayer player) {
        List<ScoreboardRow> rowList = new LinkedList<>();
        rowList.add(ScoreboardRow.empty());
        rowList.add(new ScoreboardRow(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Balance: ", Integer.toString(player.balance)));
        if (player.staff) {
            rowList.add(new ScoreboardRow(ChatColor.GRAY + "" + ChatColor.BOLD + "Staff: ", Integer.toString(player.staffRank)));
        }
        rowList.add(ScoreboardRow.empty());
        rowList.add(new ScoreboardRow(ChatColor.LIGHT_PURPLE + "lillie.rs/minecraft", ""));

        return rowList;
    }

    @Override
    public BiFunction<ServerPlayer, Scoreboard, Scoreboard> updateFunction() {
        return (player, board) -> {
            Team balance = board.getTeam(ChatColor.values()[player.staff ? 3 : 2].toString());
            if (balance != null) balance.setSuffix(Integer.toString(player.balance));
            if (player.staff) {
                Team staff = board.getTeam(ChatColor.values()[2].toString());
                if (staff != null) staff.setSuffix(Integer.toString(player.staffRank));
            }
            return board;
        };
    }
}
