package com.PixelYeti.SkyWars;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.entity.Player;

public abstract class GameCommand
{

    public abstract void onCommand(Player p, String[] args);

}