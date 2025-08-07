import Exceptions.InvalidPlayerException;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ServerPlayer implements AutoCloseable {
    static Connection connection;

    public final UUID id;
    public final boolean staff;
    public final int staffRank;
    public final int balance;
    public final String displayName;
    public final Player player;

    private ServerPlayer(UUID id, int staff, int balance, String displayName, Player player) {
        this.id = id;
        this.staff = staff > 0;
        this.staffRank = staff;
        this.balance = balance;
        this.displayName = displayName;
        this.player = player;
    }

    public static ServerPlayer createPlayer(Player player) throws SQLException, InvalidPlayerException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO USERS(id) VALUES(?)");
        stmt.setObject(1, player.getUniqueId());
        int res = stmt.executeUpdate();
        if (res > 0) {
            return new ServerPlayer(player.getUniqueId(), 0, 0, player.getName(), player);
        } else throw new InvalidPlayerException(player.getUniqueId());
    }

    public static ServerPlayer loadPlayer(Player player) throws SQLException, InvalidPlayerException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM USERS WHERE id = ?");
        stmt.setObject(1, player.getUniqueId());
        ResultSet res = stmt.executeQuery();
        if (res.next()) {
            return new ServerPlayer(player.getUniqueId(), res.getInt("staff"), res.getInt("balance"), player.getName(), player);
        } else throw new InvalidPlayerException(player.getUniqueId());
    }

    @Override
    public void close() throws Exception {
    }
}
