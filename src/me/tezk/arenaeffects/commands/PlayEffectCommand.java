package me.tezk.arenaeffects.commands;

import me.tezk.arenaeffects.ArenaEffects;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayEffectCommand implements CommandExecutor {

    private ArenaEffects plugin;

    public PlayEffectCommand(ArenaEffects plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playeffect")) {

            if (!(sender.isOp()) && !(sender.hasPermission("arenaeffects.playeffectcommand"))) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("skeletons")) {
                    plugin.skeletonEffect();
                    sender.sendMessage("Executed " + ChatColor.YELLOW + ChatColor.BOLD + "skeleton" + ChatColor.RESET + " effect on arena.");

                } else if (args[0].equalsIgnoreCase("lightning")) {
                    plugin.lightningEffect();
                    sender.sendMessage("Executed " + ChatColor.YELLOW + ChatColor.BOLD + "lightning" + ChatColor.RESET + " effect on arena.");

                } else if (args[0].equalsIgnoreCase("water")) {
                    plugin.waterEffect();
                    sender.sendMessage("Executed " + ChatColor.YELLOW + ChatColor.BOLD + "water" + ChatColor.RESET + " effect on arena.");

                } else if (args[0].equalsIgnoreCase("party")) {
                    plugin.partyEffect();
                    sender.sendMessage("Executed " + ChatColor.YELLOW + ChatColor.BOLD + "party" + ChatColor.RESET + " effect on arena.");

                } else if (args[0].equalsIgnoreCase("lava")) {
                    plugin.lavaEffect();
                    sender.sendMessage("Executed " + ChatColor.YELLOW + ChatColor.BOLD + "lava" + ChatColor.RESET + " effect on arena.");

                } else if (args[0].equalsIgnoreCase("random")) {
                    plugin.randomInventoryEffect();
                    sender.sendMessage("Executed " + ChatColor.YELLOW + ChatColor.BOLD + "random" + ChatColor.RESET + " effect on arena.");

                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Try: " + ChatColor.RESET + "/seteffect <effect>. " + ChatColor.YELLOW + "Effects: " + ChatColor.RESET + "lightning, skeletons, water, lava, party & random.");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Try: " + ChatColor.RESET + "/playeffect <effect>. " + ChatColor.YELLOW + "Effects: " + ChatColor.RESET + "lightning, skeletons, water, lava, party & random.");
                return true;
            }
        }
        return true;
    }
}
