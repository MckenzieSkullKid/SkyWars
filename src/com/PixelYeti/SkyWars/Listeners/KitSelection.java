package com.PixelYeti.SkyWars.Listeners;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.PixelYeti.SkyWars.Kit;
import com.PixelYeti.SkyWars.KitManager;

public class KitSelection implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (e.getItem() == null || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()) {
            return;
        }

        if (!e.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Kit Selector")) {
            return;
        }

        e.getPlayer().openInventory(KitManager.getInstance().getGUI());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        if (e.getCurrentItem() == null || !(e.getCurrentItem().hasItemMeta())) {
            return;
        }
        Player p = (Player) e.getWhoClicked();

        if (!e.getInventory().getName().equals("Kit Selector")) {
            return;
        }

        e.setCancelled(true);
        e.getWhoClicked().closeInventory();

        Kit kit = KitManager.getInstance().getKit(e.getCurrentItem().getItemMeta().getDisplayName());

        p.getInventory().clear();

        for (ItemStack item : kit.getItems()) {
            p.getInventory().addItem(item);
        }

        p.sendMessage(ChatColor.GREEN + "You have chosen kit " + ChatColor.LIGHT_PURPLE + kit.getName());
    }
}