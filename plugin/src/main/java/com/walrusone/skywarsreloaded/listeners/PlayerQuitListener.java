package com.walrusone.skywarsreloaded.listeners;

import org.bukkit.event.EventHandler;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import com.walrusone.skywarsreloaded.managers.PlayerStat;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent a1) {
    	final String id = a1.getPlayer().getUniqueId().toString();
        final GameMap gameMap = MatchManager.get().getPlayerMap(a1.getPlayer());
        if (gameMap == null) {
        	new BukkitRunnable() {
				@Override
				public void run() {
			   		PlayerStat.removePlayer(id);
				}
   			}.runTaskLater(SkyWarsReloaded.get(), 5);
            return;
        }
        
        MatchManager.get().playerLeave(a1.getPlayer(), DamageCause.CUSTOM, true, true);
        
   		if (PlayerStat.getPlayerStats(id) != null) {
   			new BukkitRunnable() {
				@Override
				public void run() {
			   		PlayerStat.removePlayer(id);
				}
   			}.runTaskLater(SkyWarsReloaded.get(), 20);
   		}
    }
}
