package ml.bmlzootown.config;

import ml.bmlzootown.EDragon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ConfigManager {
    static Plugin pl = EDragon.plugin;

    public static void setConfigDefaults() {
        FileConfiguration config = pl.getConfig();
        config.options().header("Simple fix for the dragon\nNote: xp.drop/drop-egg.drop MUST be set to true, else NO xp/egg will be given/dropped\nIf drop-egg.drop is enabled, and egg-on-portal/-to-slayer are disabled, egg will spawn at specified (drop-egg.eop.x/-y/-z) coords");
        List<String> enabledWorld = Arrays.asList("Survival_The_End", "Lobby_The_End");
        config.addDefault("debug", true);
        config.addDefault("drop-egg.drop", true);
        config.addDefault("drop-egg.egg-on-portal", true);
        config.addDefault("drop-egg.eop.x", 0);
        config.addDefault("drop-egg.eop.y", 61);
        config.addDefault("drop-egg.eop.z", 0);
        config.addDefault("drop-egg.egg-to-slayer", false);
        config.addDefault("xp.drop", true);
        config.addDefault("xp.xp-to-slayer", false);
        config.addDefault("xp.share-xp-equally", false);
        config.addDefault("crystal.1.x", 3);
        config.addDefault("crystal.1.y", 58);
        config.addDefault("crystal.1.z", 0);
        config.addDefault("crystal.2.x", 0);
        config.addDefault("crystal.2.y", 58);
        config.addDefault("crystal.2.z", -3);
        config.addDefault("crystal.3.x", -3);
        config.addDefault("crystal.3.y", 58);
        config.addDefault("crystal.3.z", 0);
        config.addDefault("crystal.4.x", 0);
        config.addDefault("crystal.4.y", 58);
        config.addDefault("crystal.4.z", 3);
        config.addDefault("enabled-worlds", enabledWorld);
        config.options().copyDefaults(true);
        pl.saveConfig();
    }

    public static List<String> getList(String key) {
        return pl.getConfig().getStringList(key);
    }

    public static boolean getBoolean(String key) {
        return pl.getConfig().getBoolean(key);
    }

    public static int getInt(String key) {
        return pl.getConfig().getInt(key);
    }

}
