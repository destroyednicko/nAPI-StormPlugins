package kun.nicko.StormAPI;

import org.bukkit.scheduler.BukkitRunnable;
import java.sql.*;
import java.util.*;

@Deprecated
public class LoadThread extends BukkitRunnable {
    private Util pluginInstance;
    private UUID uuid;

    public LoadThread(Util pluginInstance, UUID uuid) {
        this.pluginInstance = pluginInstance;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
       //      connection = DriverManager.getConnection("jdbc:mysql://" + pluginInstance.getConfig().getString("sql-ip") +
       //             ":" + pluginInstance.getConfig().getString("sql-port") + "/settings", pluginInstance.getConfig().getString("sql-username"), pluginInstance.getConfig().getString("sql-password"));
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT setting, value FROM settings WHERE uuid = '" + uuid.toString() + "';");
            HashMap<String, String> map = new HashMap<String, String>();
            while (resultSet.next()) {
                map.put(resultSet.getString("setting"), resultSet.getString("value"));
            }
            UserProfile profile = new UserProfile(uuid, map);
            pluginInstance.getSettingsManager().addProfile(uuid, profile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) resultSet.close();
                if (statement != null && !statement.isClosed()) statement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}