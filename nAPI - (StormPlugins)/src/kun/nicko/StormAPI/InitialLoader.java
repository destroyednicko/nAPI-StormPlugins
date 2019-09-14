package kun.nicko.StormAPI;

import org.bukkit.event.player.PlayerLoginEvent;

@Deprecated
public class InitialLoader {
    private Util util;
    private boolean enabled;

    public InitialLoader(Util util) {
        this.util = util;
    }

    public void onPlayerLogin(PlayerLoginEvent e) {
    	System.out.println("Carregando configurações para " + e.getPlayer().getName() + "...");
        Thread thread = new Thread(new LoadThread(util, e.getPlayer().getUniqueId()));
        thread.start();
        try {
            thread.join(1000L);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public void loginMonitor(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) { util.getSettingsManager().removeProfile(e.getPlayer().getUniqueId());
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}