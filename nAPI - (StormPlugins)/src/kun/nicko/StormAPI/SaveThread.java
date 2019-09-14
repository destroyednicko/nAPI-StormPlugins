package kun.nicko.StormAPI;

import org.bukkit.scheduler.BukkitRunnable;
import java.sql.*;
import java.util.*;

@Deprecated
public class SaveThread extends BukkitRunnable {
    private Util pluginInstance;
    private UUID uuid;
    private ArrayList<String> changelist;

    public SaveThread(Util pluginInstance, ArrayList<String> changelist, UUID uuid) {
        this.pluginInstance = pluginInstance;
        this.uuid = uuid;
        this.changelist = changelist;
    }

    @Override
    public void run() {
        Connection connection = null;
        Statement statement = null;
        Statement statementExecute = null;
        ResultSet resultSet = null;
        if (changelist.size() < 1) return;
        try {
           // connection = DriverManager.getConnection("jdbc:mysql://" + pluginInstance.getConfig().getString("sql-ip") +
         //           ":" + pluginInstance.getConfig().getString("sql-port") + "/settings", pluginInstance.getConfig().getString("sql-username"), pluginInstance.getConfig().getString("sql-password"));
            statement = connection.createStatement();
            statementExecute = connection.createStatement();
            resultSet = statement.executeQuery("SELECT setting, value FROM settings WHERE uuid = '" + uuid.toString() + "';");

            while (resultSet.next()) {
                String setting = resultSet.getString("setting");
                if (((ArrayList<String>) changelist.clone()).contains(setting)) {
                    statementExecute.execute("UPDATE settings SET value ='" + pluginInstance.getSettingsManager().getProfile(uuid).getValue(setting, "") + "' WHERE setting ='" + setting + "' AND uuid='"+uuid.toString()+"'");
                    changelist.remove(setting);
                }
            }

            for (String change : changelist) {
                statementExecute.execute("INSERT INTO settings (uuid,setting,value) VALUES ('"+uuid.toString()+"','"+change+"','"+pluginInstance.getSettingsManager().getProfile(uuid).getValue(change,"")+"')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) resultSet.close();
                if (statement != null && !statement.isClosed()) statement.close();
                if (statementExecute != null && !statementExecute.isClosed()) statementExecute.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}