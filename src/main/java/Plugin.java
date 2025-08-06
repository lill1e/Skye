import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    private Postgres database;
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
    }
}