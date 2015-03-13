package com.PixelYeti.SkyWars.Commands;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.PixelYeti.SkyWars.Arena;
import com.PixelYeti.SkyWars.ArenaManager;
import com.PixelYeti.SkyWars.CommandInfo;
import com.PixelYeti.SkyWars.GameCommand;
import com.PixelYeti.SkyWars.LobbySign;
import com.PixelYeti.SkyWars.SettingsManager;

@CommandInfo(aliases = { "addsign" }, description = "Add a Lobby sign.", usage = "<arenaName>", op = true)
public class AddLobbySign extends GameCommand{

    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must specify the arena to which you want to bind this sign.");
            return;
        }

        Arena a = ArenaManager.getInstance().getArena(args[0]);

        if (a == null) {
            p.sendMessage(ChatColor.RED + "An arena with that name does not exist.");
            return;
        }

        @SuppressWarnings("deprecation")
        Block target = p.getTargetBlock(null, 10);

        if (target == null) {
            p.sendMessage(ChatColor.RED + "You are not looking at a block.");
            return;
        }

        if (!(target.getState() instanceof Sign)) {
            p.sendMessage(ChatColor.RED + "You are not looking at a sign.");
            return;
        }

        new LobbySign(target.getLocation(), a).save(SettingsManager.getSigns().createSection(String.valueOf(SettingsManager.getSigns().getKeys().size())));
        SettingsManager.getSigns().save();

        // New sign would not be in SignManager.

        p.sendMessage(ChatColor.GREEN + "Added lobby sign.");
    }
}
