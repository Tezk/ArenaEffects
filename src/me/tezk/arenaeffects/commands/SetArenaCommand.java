package me.tezk.arenaeffects.commands;

import me.tezk.arenaeffects.ArenaEffects;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetArenaCommand implements CommandExecutor {

    private ArenaEffects plugin;

    public SetArenaCommand(ArenaEffects plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setarena")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
                return true;
            }
            Player player = (Player) sender;

            if (!(player.isOp()) && !(player.hasPermission("arenaeffects.setarenacommand"))) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }

            if (args.length == 1) {
                int radius;
                try {
                    radius = Integer.parseInt(args[0]);
                } catch (Exception exc) {
                    player.sendMessage(ChatColor.RED + "Wrong usage. " + ChatColor.RESET + "Size must be an integer!");
                    return true;
                }
                String world = player.getWorld().getName();
                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();

                plugin.getConfig().set("data.arena-location.world", world);
                plugin.getConfig().set("data.arena-location.x", x);
                plugin.getConfig().set("data.arena-location.y", y);
                plugin.getConfig().set("data.arena-location.z", z);
                plugin.getConfig().set("data.arena-location.radius", radius);
                plugin.saveConfig();

                plugin.updateArenaLocation();
                player.sendMessage("Arena set to a size of " + ChatColor.YELLOW + ChatColor.BOLD + args[0] + ChatColor.RESET + " at this location.");

            } else {
                player.sendMessage(ChatColor.RED + "Wrong usage. " + ChatColor.RESET + "/setarena <size> - sets " +
                        "arena size from your present location.");
                return true;
            }
            return true;
        }
        return true;
    }
}
