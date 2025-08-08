import Exceptions.InvalidPlayerException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try (ServerPlayer serverPlayer = ServerPlayer.loadPlayer(player)) {
            serverPlayer.add();
            Plugin.scoreboardHandler.setScoreboard(serverPlayer, "primary");
        } catch (SQLException | InvalidPlayerException e) {
            try (ServerPlayer serverPlayer = ServerPlayer.createPlayer(player)) {
                serverPlayer.add();
                Plugin.scoreboardHandler.setScoreboard(serverPlayer, "primary");
            } catch (SQLException | InvalidPlayerException registerException) {
                player.kickPlayer("There was a problem fetching your account");
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        try (ServerPlayer serverPlayer = Plugin.playerManager.get(player)) {
            PreparedStatement stmt = ServerPlayer.connection.prepareStatement("UPDATE USERS SET balance = ? WHERE id = ?");
            stmt.setInt(1, serverPlayer.balance);
            stmt.setObject(2, serverPlayer.id);
            serverPlayer.remove();
        } catch (Exception ignored) {
        }
    }
}
