import Exceptions.InvalidPlayerException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Plugin extends JavaPlugin {
    private Postgres database;
    public static ScoreboardHandler scoreboardHandler = new ScoreboardHandler();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = getConfig();
        ConfigurationSection dbConfig = config.getConfigurationSection("db");

        if (dbConfig == null) {
            System.out.println("The database is not properly configured.");
            return;
        }
        database = Postgres.connect(dbConfig);
        ServerPlayer.connection = database.connection;
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        scoreboardHandler.addScoreboard("primary", new ScoreboardPrimary());

        getServer().getOnlinePlayers().forEach(player -> {
            try (ServerPlayer serverPlayer = ServerPlayer.loadPlayer(player)) {
                scoreboardHandler.setScoreboard(serverPlayer, "primary");
            } catch (SQLException | InvalidPlayerException e) {
                try (ServerPlayer serverPlayer = ServerPlayer.createPlayer(player)) {
                    scoreboardHandler.setScoreboard(serverPlayer, "primary");
                } catch (SQLException | InvalidPlayerException registerException) {
                    player.kickPlayer("There was a problem fetching your account");
                } catch (Exception ignored) {
                }
            } catch (Exception ignored) {
            }
        });
    }
}