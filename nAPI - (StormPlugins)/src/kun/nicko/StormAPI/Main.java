package kun.nicko.StormAPI;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main instance;
	
	@Override
	public void onEnable() {
		System.out.println("oOo nStormAPI - habilitada.");
	}
	
	@Override
	public void onDisable() {
		System.out.println("oOo nStormAPI - desabilitada.");
	}

}
