package com.PixelYeti.SkyWars;

/**
 * Created by Callum on 13/03/2015.
 */
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class ArenaManager
{

    private static ArenaManager instance = new ArenaManager();

    public static ArenaManager getInstance() {
        return instance;
    }

    private ArrayList<Arena> arenas;

    private ArenaManager() {
        this.arenas = new ArrayList<Arena>();
    }

    public void setup()
    {
        arenas.clear();

        for (String arenaID : SettingsManager.getArenas().getKeys())
        {
            arenas.add(new Arena(arenaID));
        }
    }

    public Arena getArena(String id)
    {
        for (Arena arena : arenas)
        {
            if (arena.getID().equals(id))
            {
                return arena;
            }
        }

        return null;
    }

    public Arena getArena(Player p)
    {
        for (Arena arena : arenas)
        {
            if (arena.hasPlayer(p))
            {
                return arena;
            }
        }

        return null;
    }

    public ArrayList<Arena> getArenas() {
        return arenas;
    }
}