package kun.nicko.StormAPI;

import java.util.ArrayList;

/**
 * Platform for plugins to request certain automatically applied features.
 */
public class Registry {
    /**
     * Components provided by LABS_Util
     */
    public static enum GlobalComponent {
        autoLoadDataRegistry, // Automatic reload of the data registry on player login
        autoSaveDataRegistry,
        autoRespawn, // Automatic respawn on player death
        cacheNames;

        /**
         * Enables this feature permanentely.
         */
        public void require() {
            Util.getComponentRegistry().requireComponent(name());
        }

        /**
         * @return Is this feature enabled?
         */
        public boolean isRequired() {
            return Util.getComponentRegistry().isComponentRequired(name());
        }
    }
    
    

    private ArrayList<String> requiredComponents = new ArrayList<>();

    protected Registry() {}

    /**
     * @param s The requested feature key.
     * @return Is a certain component is enabled?
     */
    public boolean isComponentRequired(String s) {
        return requiredComponents.contains(s.toLowerCase());
    }

    /**
     * Enables a certain component permanentely.
     * @param s The requested feature key.
     */
    public void requireComponent(String s) {
        s = s.toLowerCase();
        if (!requiredComponents.contains(s)) requiredComponents.add(s);
    }
}