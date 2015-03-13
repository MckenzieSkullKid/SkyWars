package com.PixelYeti.SkyWars;

/**
 * Created by Callum on 13/03/2015.
 */
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitManager
{

    private static KitManager instance = new KitManager();

    public static KitManager getInstance() {
        return instance;
    }

    private ArrayList<Kit> kits;
    private Inventory gui;


    private KitManager()
    {
        kits = new ArrayList<Kit>();

        //TODO: Add all other kits

        gui = Bukkit.getServer().createInventory(null, InventoryType.CHEST, "Kit Selector");

        for (Kit kit : kits)
        {
            ItemStack item = new ItemStack(kit.getDisplayItem(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(kit.getName());
            meta.setLore(Arrays.asList("Click here to select", "the " + kit.getName() + " kit."));
            item.setItemMeta(meta);
            gui.addItem(item);
        }
    }

    public Kit getKit(String name)
    {
        for(Kit kit : kits)
        {
            if(kit.getName().equals(name))
            {
                return kit;
            }
        }
        return null;
    }

    public Inventory getGUI() {
        return gui;
    }
}
