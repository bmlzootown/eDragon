package ml.bmlzootown.listeners;

import ml.bmlzootown.EDragon;
import ml.bmlzootown.api.PortalCrystal;
import ml.bmlzootown.config.ConfigManager;
import ml.bmlzootown.versions.DragonBattle;
import ml.bmlzootown.versions.NMSAbstract;
import net.minecraft.server.v1_12_R1.EnderDragonBattle;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlayerListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        List<String> worlds = ConfigManager.getList("enabled-worlds");
        Player p = event.getPlayer();
        if (worlds.stream().anyMatch(event.getPlayer().getWorld().getName()::equalsIgnoreCase)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getClickedBlock().getType().equals(Material.BEDROCK)) {
                    if (event.getHand().equals(EquipmentSlot.HAND)) {
                        //int count = 0;
                        PlayerInventory pi = p.getInventory();
                        if (pi.getItemInMainHand().getType().equals(Material.END_CRYSTAL)) {
                            event.setCancelled(true);
                            int count = 0;
                            ItemStack[] inv = pi.getContents();
                            for (ItemStack item : inv) {
                                if (item != null && item.getType().equals(Material.END_CRYSTAL)) {
                                    //count = count + 1;
                                    count = count + item.getAmount();
                                }
                            }

                            if (ConfigManager.getBoolean("debug")) {
                                if (p.getGameMode().equals(GameMode.CREATIVE)) {
                                    Bukkit.getLogger().log(Level.INFO, "4 end crystal(s) [creative mode]");
                                } else {
                                    Bukkit.getLogger().log(Level.INFO, count + " end crystal(s)");
                                }
                            }

                            if (count > 3 || p.getGameMode().equals(GameMode.CREATIVE)) {
                                List<Location> crystals = new ArrayList<Location>();
                                crystals.add(new Location(p.getWorld(), (double) ConfigManager.getInt("crystal.1.x") + 0.5, (double) ConfigManager.getInt("crystal.1.y") + 0.5, (double) ConfigManager.getInt("crystal.1.z") + 0.5));
                                crystals.add(new Location(p.getWorld(), (double) ConfigManager.getInt("crystal.2.x") + 0.5, (double) ConfigManager.getInt("crystal.2.y") + 0.5, (double) ConfigManager.getInt("crystal.2.z") + 0.5));
                                crystals.add(new Location(p.getWorld(), (double) ConfigManager.getInt("crystal.3.x") + 0.5, (double) ConfigManager.getInt("crystal.3.y") + 0.5, (double) ConfigManager.getInt("crystal.3.z") + 0.5));
                                crystals.add(new Location(p.getWorld(), (double) ConfigManager.getInt("crystal.4.x") + 0.5, (double) ConfigManager.getInt("crystal.4.y") + 0.5, (double) ConfigManager.getInt("crystal.4.z") + 0.5));
                                for (Location l : crystals) {
                                    //if (p.getLocation().distance(l) < (double) 10) {
                                    EnderCrystal c = p.getWorld().spawn(l, EnderCrystal.class);
                                    c.setShowingBottom(false);
                                    //}
                                }
                                if (ConfigManager.getBoolean("debug")) {
                                    Bukkit.getLogger().log(Level.INFO, p.getName() + " has respawned the dragon in " + p.getWorld().getName());
                                }
                                p.getInventory().removeItem(new ItemStack(Material.END_CRYSTAL, 4));

                                //Spawn Dragon
                                EDragon plugin = (EDragon)EDragon.plugin;
                                NMSAbstract nmsAbstract = plugin.getNMSAbstract();
                                DragonBattle dragonBattle = nmsAbstract.getEnderDragonBattleFromWorld(p.getWorld());
                                dragonBattle.respawnEnderDragon();

                                //event.setCancelled(true);
                            } else {
                                p.sendMessage(ChatColor.YELLOW + "[eDragon] You need at least " + ChatColor.RED + "4" + ChatColor.YELLOW + " end crystals to respawn the dragon!");
                            }
                        }
                    }
                }
            }
        }
    }

}
