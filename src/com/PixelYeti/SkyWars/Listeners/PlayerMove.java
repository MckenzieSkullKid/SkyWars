package com.PixelYeti.SkyWars.Listeners;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.PixelYeti.SkyWars.Arena;
import com.PixelYeti.SkyWars.Arena.ArenaState;
import com.PixelYeti.SkyWars.ArenaManager;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Arena a = ArenaManager.getInstance().getArena(e.getPlayer());

        if (a == null) {
            return;
        }

        if (a.getState() == ArenaState.STARTED) {
            return;
        }

//		if (Math.abs(e.getTo().getBlockX() - a.getSpawn(e.getPlayer()).getBlockX()) < 0.2 && Math.abs(e.getTo().getBlockZ() - a.getSpawn(e.getPlayer()).getBlockZ()) < 0.2) { // Ignore y so they can jump.
//			return; // Remove this if statement if you don't want to allow looking around.
//		}
//
//        Location newLocation = new Location(e.getFrom().getWorld(), e.getFrom().getBlockX(), e.getFrom().getBlockY(), e.getFrom().getBlockZ(), e.getPlayer().getLocation().getPitch(), e.getPlayer().getLocation().getYaw());
//		e.setCancelled(true);
        if (e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) { // Ignore y so they can jump.
            return; // Remove this if statement if you don't want to allow looking around.
        }
        e.setTo(e.getFrom());
    }
}