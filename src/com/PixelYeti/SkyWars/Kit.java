package com.PixelYeti.SkyWars;

/**
 * Created by Callum on 13/03/2015.
 */
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Kit
{

    private String name;
    private ArrayList<ItemStack> items;
    private Material displayItem;

    public Kit(String name, Material displayItem)
    {
        this.name = name;
        this.displayItem = displayItem;
        this.items = new ArrayList<ItemStack>();
    }

    public final String getName() {
        return name;
    }

    public final Material getDisplayItem() {
        return displayItem;
    }

    @SuppressWarnings("unchecked")
    public final ArrayList<ItemStack> getItems() {
        return (ArrayList<ItemStack>) items.clone();
    }

    public void apply(Player p) {

    }

    @SafeVarargs
    public final void addItem(Material material, int amnt, String name, String[] lore, SimpleEntry<Enchantment, Integer>... enchantments) {
        ItemStack item = new ItemStack(material, amnt);
        for(SimpleEntry<Enchantment, Integer> e : enchantments) {
            item.addEnchantment(e.getKey(), e.getValue());
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        items.add(item);
    }
}