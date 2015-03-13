package com.PixelYeti.SkyWars.Commands;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.PixelYeti.SkyWars.ArenaManager;
import com.PixelYeti.SkyWars.CommandInfo;
import com.PixelYeti.SkyWars.GameCommand;
import com.PixelYeti.SkyWars.SettingsManager;

@CommandInfo(aliases = { "removearena", "ra" }, description = "Remove an arena.", usage = "<name>", op = true)
public class RemoveArena extends GameCommand {

    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must specify the name of the arena.");
            return;
        }

        String name = args[0];

        if (ArenaManager.getInstance().getArena(name) == null) {
            p.sendMessage(ChatColor.RED + "An arena with that name does not exist.");
            return;
        }

        SettingsManager.getArenas().set(name, null);

        p.sendMessage(ChatColor.GREEN + "Removed arena " + name + ". This will be applied on reload.");
    }

}
