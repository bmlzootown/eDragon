package ml.bmlzootown.commands;

import ml.bmlzootown.EDragon;
import ml.bmlzootown.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commander implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            if (sender.isOp()) {
                if (args[0].equalsIgnoreCase("init")) {
                    ConfigManager.setConfigDefaults();
                    sender.sendMessage(ChatColor.RED + "[eDragon] Config reset to default/initiated!");
                } else if (args[0].equalsIgnoreCase("reload")) {
                    EDragon.plugin.reloadConfig();
                    sender.sendMessage(ChatColor.RED + "[eDragon] Config reloaded!");
                }
            }
        }
        return false;
    }

}
