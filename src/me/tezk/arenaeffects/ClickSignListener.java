package me.tezk.arenaeffects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickSignListener implements Listener {

    private ArenaEffects plugin;

    public ClickSignListener(ArenaEffects plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN) || event.getClickedBlock().getType().equals(Material.OAK_SIGN)) {
            Sign sign = (Sign) event.getClickedBlock().getState();

            // sign activation line number
            String signLineAct = sign.getLine(plugin.getSignActLineNumber() - 1);

            if (!(signLineAct.equals(ChatColor.translateAlternateColorCodes('&', plugin.getSignActivationText() )))) return;

            // sign effect line number
            String arenaEffectLine = ChatColor.stripColor(sign.getLine(plugin.getArenaEffectSignLine() - 1));

            if (!(event.getPlayer().hasPermission("arenaeffects.sign.activation")) && !(event.getPlayer().isOp())) return;

            if (arenaEffectLine.equals(plugin.getLightningItemTitle())) {
                plugin.lightningEffect();

            } else if (arenaEffectLine.equals(plugin.getSkeleItemTitle())) {
                plugin.skeletonEffect();

            } else if (arenaEffectLine.equals(plugin.getWaterItemTitle())) {
                plugin.waterEffect();

            } else if (arenaEffectLine.equals(plugin.getPartyItemTitle())) {
                plugin.partyEffect();

            } else if (arenaEffectLine.equals(plugin.getLavaItemTitle())) {
                plugin.lavaEffect();

            } else if (arenaEffectLine.equals(plugin.getRandomInventoryItemTitle())) {
                plugin.randomInventoryEffect();

            }
        }
    }

    @EventHandler
    public void colourSigns(SignChangeEvent event) {
        int lineNumber = 0;
        for (String line : event.getLines()) {
            event.setLine(lineNumber, ChatColor.translateAlternateColorCodes('&', line));
            lineNumber++;
        }
    }
}
