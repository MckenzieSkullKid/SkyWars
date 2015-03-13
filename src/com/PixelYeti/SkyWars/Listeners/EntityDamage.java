package com.PixelYeti.SkyWars.Listeners;

/**
 * Created by Callum on 13/03/2015.
 */
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.PixelYeti.SkyWars.Arena;
import com.PixelYeti.SkyWars.ArenaManager;
import com.PixelYeti.SkyWars.Arena.ArenaState;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();

        Arena a = ArenaManager.getInstance().getArena(p);

        if (a == null) {
            return;
        }

        if (a.getState() == ArenaState.STARTED) {
            return;
        }

        e.setCancelled(true);
    }
}