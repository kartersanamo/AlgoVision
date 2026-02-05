package com.kartersanamo.algoVision.listeners;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Handles wand left/right click for region selection.
 */
public class PlayerInteractListener implements Listener {

    private final AlgoVision plugin;

    public PlayerInteractListener(AlgoVision plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !isWand(item)) {
            return;
        }

        Action action = event.getAction();
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        event.setCancelled(true);

        RegionManager regionManager = plugin.getRegionManager();
        Location loc = block.getLocation();

        if (action == Action.LEFT_CLICK_BLOCK) {
            regionManager.setPos1(player, loc);
            player.sendMessage("§aPosition 1 set to " + formatLocation(loc));
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            regionManager.setPos2(player, loc);
            player.sendMessage("§aPosition 2 set to " + formatLocation(loc));
        }
    }

    private boolean isWand(ItemStack item) {
        if (item.getType() != Material.BLAZE_ROD) {
            return false;
        }
        if (!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        return meta != null && "§6AlgoVision Wand".equals(meta.getDisplayName());
    }

    private String formatLocation(Location loc) {
        return String.format("(%d, %d, %d)", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
