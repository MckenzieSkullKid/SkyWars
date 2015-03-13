package com.PixelYeti.SkyWars;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

public class LobbySign
{

    private Location loc;
    private Sign sign;
    private Arena arena;

    public LobbySign(Location loc, Arena arena)
    {
        this.loc = loc;
        this.sign = (Sign) loc.getBlock().getState();
        this.arena = arena;
    }

    public Location getLoc() {
        return loc;
    }

    public Sign getSign() {
        return sign;
    }

    public Arena getArena() {
        return arena;
    }

    public void update()
    {
        sign.setLine(0, "[SkyWars]");
        sign.setLine(1, arena.getID());
        sign.setLine(2, arena.getState().toString().toLowerCase().substring(0, 1).toUpperCase() + arena.getState().toString().toLowerCase().substring(1));
        if (arena.getPlayers().length != arena.getMaxPlayers())
            sign.setLine(3, ChatColor.GREEN + "" + arena.getPlayers().length + "/" + arena.getMaxPlayers());
        else sign.setLine(3, ChatColor.RED + "" + arena.getPlayers().length + "/" + arena.getMaxPlayers());
        sign.update();
    }

    public void save(ConfigurationSection section)
    {
        section.set("location.world", loc.getWorld().getName());
        section.set("location.x", loc.getX());
        section.set("location.y", loc.getY());
        section.set("location.z", loc.getZ());
        section.set("arena", arena.getID());
    }
}