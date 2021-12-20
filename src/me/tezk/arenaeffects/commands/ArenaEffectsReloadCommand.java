package me.tezk.arenaeffects.commands;

import me.tezk.arenaeffects.ArenaEffects;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArenaEffectsReloadCommand implements CommandExecutor {

    private ArenaEffects plugin;

    public ArenaEffectsReloadCommand(ArenaEffects plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("arenaeffects")) {

            if (!(sender.isOp()) && !(sender.hasPermission("arenaeffects.reloadcommand"))) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }
            if (args.length == 0) {
                sendHelpMenu(sender);
                return true;

            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    plugin.updateConfigVariables();
                    plugin.updateArenaLocation();
                    sender.sendMessage(ChatColor.YELLOW + ""+ ChatColor.BOLD + "ArenaEffects " + ChatColor.RESET + "reloaded successfully!");
                    return true;

                } else {
                    sendHelpMenu(sender);
                    return true;
                }

            } else {
                sendHelpMenu(sender);
                return true;
            }
        }
        return true;
    }

    private void sendHelpMenu(CommandSender sender) {
        sender.sendMessage("---- ArenaEffects help menu ----");
        sender.sendMessage(ChatColor.YELLOW + "/setarena <size> " + ChatColor.RESET + "- sets arena size from present location");
        sender.sendMessage(ChatColor.YELLOW + "/seteffect <effect> " + ChatColor.RESET + "- assigns arena effect to item in hand");
        sender.sendMessage(ChatColor.YELLOW + "/playeffect <effect> " + ChatColor.RESET + "- conveniently execute effect on current arena");
        sender.sendMessage(ChatColor.YELLOW + "/arenaeffect;ae reload " + ChatColor.RESET + "- reloads configuration");
    }
}
