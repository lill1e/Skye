import Exceptions.InvalidPlayerException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try (ServerPlayer serverPlayer = ServerPlayer.loadPlayer(player)) {
        } catch (SQLException | InvalidPlayerException e) {
            try (ServerPlayer serverPlayer = ServerPlayer.createPlayer(player)) {
            } catch (SQLException | InvalidPlayerException registerException) {
                player.kickPlayer("There was a problem fetching your account");
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
    }
}
