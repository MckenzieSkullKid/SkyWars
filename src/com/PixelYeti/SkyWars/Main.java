package com.PixelYeti.SkyWars;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Callum on 13/03/2015.
 */
public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {

    }

    @Override
    public void onDisable()
    {

    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("SkyWars");
    }

    public static WorldEditPlugin getWorldEdit()
    {
        return (WorldEditPlugin) Bukkit.getServer().getPluginManager()
                .getPlugin("WorldEdit");
    }

    public static void saveLocation(Location location,
                                    ConfigurationSection section)
    {
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("pitch", location.getPitch());
        section.set("yaw", location.getYaw());
    }

    public static Location loadLocation(ConfigurationSection section)
    {
        return new Location(Bukkit.getServer().getWorld(
                section.getString("world")), section.getDouble("x"),
                section.getDouble("y"), section.getDouble("z"),
                (float) section.getDouble("pitch"),
                (float) section.getDouble("yaw"));
    }
}
