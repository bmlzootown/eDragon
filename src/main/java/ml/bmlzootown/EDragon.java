package ml.bmlzootown;

import ml.bmlzootown.commands.Commander;
import ml.bmlzootown.config.ConfigManager;
import ml.bmlzootown.listeners.DragonListener;
import ml.bmlzootown.listeners.PlayerListener;
import ml.bmlzootown.versions.NMSAbstract;
import ml.bmlzootown.versions.v1_12.NMSAbstract1_12_R1;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class EDragon extends JavaPlugin {
    public static Plugin plugin;

    private NMSAbstract nmsAbstract;

    public void onEnable() {
        plugin = this;
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            ConfigManager.setConfigDefaults();
        }
        if (!this.setupNMSAbstract()) {
            this.getLogger().severe("THE CURRENT SERVER VERSION IS NOT SUPPORTED.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new DragonListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("edragon").setExecutor(new Commander());
    }

    public void onDisable() {
        HandlerList.unregisterAll();
    }

    /**
     * Get the current implementation of the NMSAbstract interface
     *
     * @return the NMSAbstract interface
     */
    public NMSAbstract getNMSAbstract() {
        return nmsAbstract;
    }

    private final boolean setupNMSAbstract(){
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        if (version.equals("v1_12_R1")) { // 1.12.0 - 1.12.1
            this.nmsAbstract = new NMSAbstract1_12_R1();
        }

        return this.nmsAbstract != null;
    }

}
