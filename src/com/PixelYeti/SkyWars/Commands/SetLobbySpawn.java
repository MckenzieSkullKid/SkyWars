package com.PixelYeti.SkyWars.Commands;

/**
 * Created by Callum on 13/03/2015.
 */

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.PixelYeti.SkyWars.CommandInfo;
import com.PixelYeti.SkyWars.GameCommand;
import com.PixelYeti.SkyWars.Main;
import com.PixelYeti.SkyWars.SettingsManager;

@CommandInfo(aliases = { "setlobbyspawn", "sls" }, description = "Add a spawn location to the Lobby.", usage = "", op = true)
public class SetLobbySpawn extends GameCommand {

    @Override
    public void onCommand(Player p, String[] args) {
        if(args.length != 0) {
            p.sendMessage(ChatColor.RED + "You have specified too many arguments.");
            return;
        }

        String sectionName = "LobbySpawn";

        SettingsManager config = SettingsManager.getConfig();
        config.createSection(sectionName);
        Main.saveLocation(p.getLocation(), config.<ConfigurationSection>get(sectionName));
        config.save();
    }

}
