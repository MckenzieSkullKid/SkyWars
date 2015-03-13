package com.PixelYeti.SkyWars.Commands;

/**
 * Created by Callum on 13/03/2015.
 */
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import com.PixelYeti.SkyWars.Arena;
import com.PixelYeti.SkyWars.ArenaManager;
import com.PixelYeti.SkyWars.CommandInfo;
import com.PixelYeti.SkyWars.GameChest;
import com.PixelYeti.SkyWars.GameCommand;
import com.PixelYeti.SkyWars.SettingsManager;

@CommandInfo(aliases = { "addchest" }, description = "Add a chest to an arena.", usage = "<arenaName> <tier>", op = true)
public class AddChest extends GameCommand{

    @Override
    public void onCommand(Player p, String[] args) {
        if(args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must specify the arena to which the chest will be added.");
            return;
        }

        String arenaID = args[0];
        Arena a = ArenaManager.getInstance().getArena(arenaID);

        if(a == null) {
            p.sendMessage(ChatColor.RED + "An arena with that name does not exist.");
            return;
        }

        Random r = new Random();

        int tier = r.nextInt(2) + 1;



        @SuppressWarnings("deprecation")
        Block target = p.getTargetBlock(null, 10);

        if (target == null) {
            p.sendMessage(ChatColor.RED + "You are not looking at a block.");
            return;
        }

        if(!(target.getState() instanceof Chest)) {
            p.sendMessage(ChatColor.RED + "You are not looking at a chest.");
            return;
        }

        Chest chest = (Chest) target.getState();

        if (!a.getBounds().contains(chest.getLocation())) {
            p.sendMessage(ChatColor.RED + "That chest is outside of the bounds of the arena.");
            return;
        }

        a.addChest(chest, tier);

        if (!SettingsManager.getArenas().contains(a.getID() + ".chests")) {
            SettingsManager.getArenas().createSection(a.getID() + ".chests");
        }

        new GameChest(chest, tier).save(SettingsManager.getArenas().createSection(a.getID() + ".chests." + SettingsManager.getArenas().<ConfigurationSection>get(a.getID() + ".chests").getKeys(false).size()));
        SettingsManager.getArenas().save();

        p.sendMessage(ChatColor.GREEN + "Added chest.");
    }
}
