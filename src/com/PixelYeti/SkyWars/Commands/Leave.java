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

@CommandInfo(aliases = { "leave" }, description = "Leave a game.", usage = "", op = false)
public class Leave extends GameCommand {

    public void onCommand(Player p, String[] args) {
        Arena a = ArenaManager.getInstance().getArena(p);

        if (a == null) {
            p.sendMessage(ChatColor.RED + "You are not in a game.");
            return;
        }

        a.removePlayer(p);
    }
}
