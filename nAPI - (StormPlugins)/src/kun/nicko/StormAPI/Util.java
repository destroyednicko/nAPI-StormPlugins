package kun.nicko.StormAPI;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public class Util {
	private InitialLoader loader;
	private static Registry componentRegistry;
	 private SettingsManager settingsManager;
	@Deprecated
    public static Util getUtil(Server server) {
        PluginManager manager = server.getPluginManager();
        if (manager.isPluginEnabled("HYLICH_NickAPI")) return (Util) manager.getPlugin("HYLICH_NickAPI");
        return null;
    }
	
	  public static Main getUtil() {
	        return Main.instance;
	    }
	  
	  public static Registry getComponentRegistry() { return componentRegistry; }

	    public void enableInitialLoader() {
	        if (!loader.isEnabled()) loader.setEnabled(true);
	    }
	    
	    

	    
	    
	    public SettingsManager getSettingsManager()
	    {
	        return settingsManager;
	    }




}
