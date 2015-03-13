package com.PixelYeti.SkyWars.Commands;

/**
 * Created by Callum on 13/03/2015.
 */

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.PixelYeti.SkyWars.Arena;
import com.PixelYeti.SkyWars.ArenaManager;
import com.PixelYeti.SkyWars.CommandInfo;
import com.PixelYeti.SkyWars.GameCommand;

@CommandInfo(description = "Starts a game.", usage = "<arenaName>", aliases = { "start", "s" }, op = true)
public class ForceStart extends GameCommand {

    public void onCommand(Player p, String[] args) {

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must specify the arena to start.");
            return;
        }

        Arena a = ArenaManager.getInstance().getArena(args[0]);

        if (a == null) {
            p.sendMessage(ChatColor.RED + "An arena by that name does not exist.");
            return;
        }

        a.start();
    }
}