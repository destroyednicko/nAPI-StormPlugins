package kun.nicko.StormAPI;

import java.util.*;

@Deprecated
public class SettingsManager { // TODO - Merge with data registry (low priority)
    private HashMap<UUID, UserProfile> profiles = new HashMap<>();
    private Util pluginInstance;

    public SettingsManager(Util pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    public UserProfile getProfile(UUID uuid) {
        if (profiles.containsKey(uuid)) {
            return profiles.get(uuid);
        }
        return null;
    }

    public void addProfile(UUID uuid, UserProfile profile) {
        synchronized (profiles) {
            profiles.put(uuid, profile);
        }
    }

    public void removeProfile(UUID uuid) {
        synchronized (profiles) {
            if (profiles.containsKey(uuid)) {
                profiles.remove(uuid);
            }
        }
    }
}