package kun.nicko.StormAPI;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.*;

public class NickCompat {
    private static Plugin nickPlugin;
    private static Method methodIsNicked;
    private static Method methodGetNickProfile;
    private static Field nickOriginalName;

    public static String getRealName(Player p) {
        if (p != null) {
            if (isPlayerNicked(p)) {
                if (methodGetNickProfile == null) methodGetNickProfile = Reflection.getMethod(nickPlugin.getClass(), "getNickProfile", Player.class);
                try {
                    Object o = methodGetNickProfile.invoke(nickPlugin, p);
                    if (o != null) {
                        if (nickOriginalName == null) nickOriginalName = Reflection.getField(o.getClass(), "originalName");
                        return (String) nickOriginalName.get(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return p.getName();
        }
        throw new NullPointerException("o jogador n pode ser nulo!");
    }

    public static boolean isPlayerNicked(Player p)
    {
        Main util = Util.getUtil();
        if (util.getServer().getPluginManager().isPluginEnabled("nickoAndStorm")) {
            if (nickPlugin == null) nickPlugin = util.getServer().getPluginManager().getPlugin("nickoAndStorm");
            if (methodIsNicked == null) methodIsNicked = Reflection.getMethod(nickPlugin.getClass(), "isPlayerNicked", Player.class);
            try {
                return (Boolean) methodIsNicked.invoke(nickPlugin, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isPresent() {
        return nickPlugin != null || Util.getUtil().getServer().getPluginManager().isPluginEnabled("HYLICH_Nick");
    }
}