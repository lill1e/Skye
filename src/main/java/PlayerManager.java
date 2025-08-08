import Exceptions.InvalidPlayerException;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    public final HashMap<UUID, ServerPlayer> players;

    public PlayerManager() {
        players = new HashMap<>();
    }

    public void add(ServerPlayer player) {
        players.put(player.id, player);
    }

    public void remove(ServerPlayer player) throws InvalidPlayerException {
        ServerPlayer playerRet = players.remove(player.id);
        if (playerRet == null) throw new InvalidPlayerException(player.id);
    }

    public ServerPlayer get(Player player) throws InvalidPlayerException {
        ServerPlayer serverPlayer = players.get(player.getUniqueId());
        if (serverPlayer == null) throw new InvalidPlayerException(player.getUniqueId());
        return serverPlayer;
    }
}
