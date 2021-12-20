package me.tezk.arenaeffects;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class DropItemListener implements Listener {

    private ArenaEffects plugin;

    public DropItemListener(ArenaEffects plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDropItemInHopper(InventoryMoveItemEvent event) {
        if (event.getSource().getType() == InventoryType.HOPPER) {

            String itemDisplayName = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());

            if (itemDisplayName.equals(plugin.getSkeleItemTitle())) {
                plugin.skeletonEffect();

            } else if (itemDisplayName.equals(plugin.getLightningItemTitle())) {
                plugin.lightningEffect();

            } else if (itemDisplayName.equals(plugin.getWaterItemTitle())) {
                plugin.waterEffect();

            } else if (itemDisplayName.equals(plugin.getLavaItemTitle())) {
                plugin.lavaEffect();

            } else if (itemDisplayName.equals(plugin.getPartyItemTitle())) {
                plugin.partyEffect();

            } else if (itemDisplayName.equals(plugin.getRandomInventoryItemTitle())) {
                plugin.randomInventoryEffect();

            }
        }
    }
}

