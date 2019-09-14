package kun.nicko.StormAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;

@Deprecated
public class UserProfile {
    private UUID uuid;
    private final HashMap<String, String> settings = new HashMap<String, String>();
    private final ArrayList<String> changelist = new ArrayList<String>();

    protected UserProfile(UUID uuid, HashMap<String, String> fromDB) {
        this.uuid = uuid;
        copy(fromDB);
    }

    private void copy(HashMap<String, String> fromDB) {
        for (String s : fromDB.keySet()) {
            settings.put(s, fromDB.get(s));
        }
    }

    public String getValue(String path, String defaultValue) {
        synchronized (settings) {
            if (settings.containsKey(path)) {
                return settings.get(path);
            }
        }
        return defaultValue;
    }

    public ArrayList<String> getChangeList() {
        return (ArrayList<String>) changelist.clone();
    }

    public void setValue(String path, String value) {
        setValue(path, value, true);
    }

    public void setValue(String path, String value, boolean store) {
        String oldvlue = settings.get(path);
        synchronized (settings) {
            settings.put(path, value);
        }
        if (!changelist.contains(path) && !value.equals(oldvlue)) changelist.add(path);
        if (store) store();
    }

    public void store() {
        SaveThread st = new SaveThread(Util.getUtil(Bukkit.getServer()), getChangeList(), uuid);
        st.runTaskAsynchronously((Plugin) Util.getUtil(Bukkit.getServer()));
        changelist.clear();
    }
}