package com.walrusone.skywarsreloaded.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.game.GameMap;

public class ArenasMenu {
	
    private static int menuSize = 27;
    private static final String menuName = ChatColor.DARK_PURPLE + "Arenas Manager";
    
    public ArenasMenu() {	
    	Inventory menu = Bukkit.createInventory(null, menuSize + 9, menuName);
    	ArrayList<Inventory> invs = new ArrayList<Inventory>();
    	invs.add(menu);
    	
    	Runnable update = new Runnable() {
			@Override
			public void run() {
				if ((SkyWarsReloaded.getIC().hasViewers("arenasmenu"))) {				
					ArrayList<GameMap> maps = GameMap.getSortedArenas();
					ArrayList<Inventory> invs = SkyWarsReloaded.getIC().getMenu("arenasmenu").getInventories();
					
					for (Inventory inv: invs) {
						for (int i = 0; i < menuSize; i++) {
							inv.setItem(i, new ItemStack(Material.AIR, 1));
						}
					}
										
					List<String> lores = new ArrayList<String>();
					int i = 0;
					for(GameMap gMap: maps) {
						int index = Math.floorDiv(i, menuSize);
			            if(invs.isEmpty() || invs.size() < index + 1) {
			            	invs.add(Bukkit.createInventory(null, menuSize + 9, menuName));
			            }
						ItemStack item = new ItemStack(Material.WOOL, 1, (short) 13);
						if (!gMap.isRegistered()) {
							item = new ItemStack(Material.WOOL, 1, (short) 14);
						}
						lores.clear();
						lores.add(ChatColor.AQUA + "Display Name: " + ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
						lores.add(ChatColor.AQUA + "Creator: " + ChatColor.translateAlternateColorCodes('&', gMap.getDesigner()));
						if (gMap.isRegistered()) {
							lores.add(ChatColor.AQUA + "Status: " + ChatColor.GREEN + "REGISTERED");
						} else {
							lores.add(ChatColor.AQUA + "Status: " + ChatColor.RED + "UNREGISTERED");
						}
						lores.add(ChatColor.AQUA + "Match State: " + ChatColor.GOLD + gMap.getMatchState().toString());
						lores.add(ChatColor.AQUA + "Minimum Players: " + ChatColor.GOLD + gMap.getMinTeams());
						lores.add(ChatColor.AQUA + "Current Players: " + ChatColor.GOLD + gMap.getAlivePlayers().size() + " of " + gMap.getMaxPlayers());
						lores.add(ChatColor.AQUA + "Number of Join Signs: " + ChatColor.GOLD + gMap.getSigns().size());
						lores.add(ChatColor.AQUA + "Cage Type: " + ChatColor.GOLD + gMap.getCage().getType().toString());
						invs.get(index).setItem(i % menuSize, SkyWarsReloaded.getNMS().getItemStack(item, lores, ChatColor.DARK_PURPLE + gMap.getName()));
						i++;
					}
				}
			}   		
    	};
  
        SkyWarsReloaded.getIC().create("arenasmenu", invs, new IconMenu.OptionClickEventHandler() {
			@Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
				Player player = event.getPlayer();
				String name = event.getName();
	            if (name.equalsIgnoreCase(SkyWarsReloaded.getNMS().getItemName(SkyWarsReloaded.getIM().getItem("exitMenuItem")))) {
	            	player.closeInventory();
	            	return;
	            }

				if (!player.hasPermission("sw.map.arenas")) {
					return;
				}

				if (event.getClick().equals(ClickType.LEFT) && event.getItem().getType().equals(Material.WOOL)) {
					GameMap gMap = GameMap.getMap(name);
   					if (gMap != null) {
   						SkyWarsReloaded.getIC().show(player, gMap.getArenaKey());
   						new BukkitRunnable() {
							@Override
							public void run() {
								gMap.updateArenaManager();
							}
   						}.runTaskLater(SkyWarsReloaded.get(), 2);	
   					}
				}
			}
        });
        
        
        SkyWarsReloaded.getIC().getMenu("arenasmenu").setUpdate(update);
    }

}
