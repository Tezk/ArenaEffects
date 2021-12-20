package me.tezk.arenaeffects.commands;

import me.tezk.arenaeffects.ArenaEffects;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetEffectCommand implements CommandExecutor {

    private ArenaEffects plugin;
    public SetEffectCommand(ArenaEffects plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("seteffect")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to use that command.");
                return true;
            }
            Player player = (Player) sender;

            if (!(player.isOp()) || !(player.hasPermission("arenaeffects.seteffectcommand"))) {

                player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(ChatColor.YELLOW + "Try: " + ChatColor.RESET + "/seteffect <effect>. " + ChatColor.YELLOW + "Effects: " + ChatColor.RESET + "lightning, skeletons, water, lava, party & random.");
                return true;
            }

            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                player.sendMessage(ChatColor.RED + "You must hold an item in your hand to add effects to it.");
                return true;
            }

            if (plugin.getArenaRadius() == 0) {
                player.sendMessage(ChatColor.RED + "Reminder: No arena has been set yet. Set an arena using /setarena <size>");
            }

            if (args[0].equalsIgnoreCase("lightning")) {
                setEffect(player, "lightning");

            } else if (args[0].equalsIgnoreCase("skeletons")) {
                setEffect(player, "skeletons");

            } else if (args[0].equalsIgnoreCase("water")) {
                setEffect(player, "water");

            } else if (args[0].equalsIgnoreCase("party")) {
                setEffect(player, "party");

            } else if (args[0].equalsIgnoreCase("lava")) {
                setEffect(player, "lava");

            } else if (args[0].equalsIgnoreCase("random")) {
                setEffect(player, "random-inv");

            } else {
                player.sendMessage(ChatColor.YELLOW + "Try: " + ChatColor.RESET + "/seteffect <effect>. " + ChatColor.YELLOW + "Effects: " + ChatColor.RESET + "lightning, skeletons, water, lava, party & random.");
                return true;
            }
        }
        return true;
    }

    public void setEffect(Player player, String effect) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("item-effects."+ effect +".title")));

        List<String> lore = plugin.getConfig().getStringList("item-effects." + effect + ".lore");
        List<String> colouredLore = new ArrayList<>();
        for (String line : lore) {
            colouredLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(colouredLore);
        if (plugin.getConfig().getBoolean("item-effects." + effect + ".enchantment")) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        item.setItemMeta(meta);
        player.getInventory().setItemInMainHand(item);
        player.sendMessage("Effect " + ChatColor.YELLOW + ChatColor.BOLD + effect + ChatColor.RESET + " added to item in hand!");
    }
}
