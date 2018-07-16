package ml.bmlzootown.listeners;

import ml.bmlzootown.EDragon;
import ml.bmlzootown.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.logging.Level;

public class DragonListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDragonDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            if (ConfigManager.getBoolean("debug")) {
                Bukkit.getLogger().log(Level.INFO, "The dragon in " + event.getEntity().getWorld().getName() + " has been defeated!");
            }
            List<String> worlds = ConfigManager.getList("enabled-worlds");
            if (worlds.stream().anyMatch(event.getEntity().getWorld().getName()::equalsIgnoreCase)) {
                int xp = event.getDroppedExp();
                if (ConfigManager.getBoolean("xp.drop")) {
                    if (ConfigManager.getBoolean("xp.xp-to-slayer")) {
                        event.setDroppedExp(0);
                        Player killer = event.getEntity().getKiller();
                        killer.giveExp(xp);
                    } else if (ConfigManager.getBoolean("xp.share-xp-equally")) {
                        event.setDroppedExp(0);
                        List<Player> players = Bukkit.getWorld(event.getEntity().getWorld().getUID()).getPlayers();
                        int xpShare = (xp / players.size());
                        for (Player p : players) {
                            p.giveExp(xpShare);
                        }
                    }
                } else {
                    event.setDroppedExp(0);
                }
                if (ConfigManager.getBoolean("drop-egg.drop")) {
                    if (ConfigManager.getBoolean("drop-egg.egg-on-portal")) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                event.getEntity().getWorld().getBlockAt(
                                        ConfigManager.getInt("drop-egg.eop.x"),
                                        ConfigManager.getInt("drop-egg.eop.y"),
                                        ConfigManager.getInt("drop-egg.eop.z")
                                ).setType(Material.DRAGON_EGG, true);
                                Bukkit.getLogger().log(Level.INFO, "[eDragon] Egg dropped!");
                            }
                        }.runTaskLater(EDragon.plugin, 200);
                    } else if (ConfigManager.getBoolean("drop-egg.egg-to-slayer")) {
                        PlayerInventory pi = event.getEntity().getKiller().getInventory();
                        Location pl = event.getEntity().getKiller().getLocation();
                        if (pi.firstEmpty() == -1) {
                            pl.getWorld().dropItemNaturally(pl, new ItemStack(Material.DRAGON_EGG));
                        } else {
                            event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.DRAGON_EGG));
                        }
                    }
                }
            }
        }
    }

}
