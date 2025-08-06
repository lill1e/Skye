import Exceptions.InvalidDatabaseException;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgres {
    public final Connection connection;

    private Postgres(Connection connection) {
        this.connection = connection;
    }

    public static Postgres connect(ConfigurationSection config) throws InvalidDatabaseException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://" + config.getString("address") + ":" + config.getString("port") + "/" +  config.getString("database"), config.getString("username"), config.getString("password"));
            return new Postgres(connection);
        } catch (ClassNotFoundException e) {
            throw  new InvalidDatabaseException("PostgreSQL Driver not found");
        } catch (SQLException e) {
            throw  new InvalidDatabaseException(e.toString());
        }
    }
}
