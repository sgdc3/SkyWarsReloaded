package com.walrusone.skywarsreloaded.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.enums.MatchState;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import com.walrusone.skywarsreloaded.utilities.Messaging;
import com.walrusone.skywarsreloaded.utilities.Party;
import com.walrusone.skywarsreloaded.utilities.Util;

public class JoinMenu {
	
    private static int menuSize = 45;
    private static final String menuName = new Messaging.MessageFormatter().format("menu.joingame-menu-title");
    
    public JoinMenu() {	
    	Inventory menu = Bukkit.createInventory(null, menuSize + 9, menuName);
    	ArrayList<Inventory> invs = new ArrayList<Inventory>();
    	invs.add(menu);
    	
    	Runnable update = new Runnable() {
			@Override
			public void run() {
				if ((SkyWarsReloaded.getIC().hasViewers("joinmenu") || SkyWarsReloaded.getIC().hasViewers("spectatemenu"))) {
					ArrayList<GameMap> games = GameMap.getPlayableArenas();
					ArrayList<Inventory> invs = SkyWarsReloaded.getIC().getMenu("joinmenu").getInventories();
					
					for (Inventory inv: invs) {
						for (int i = 0; i < menuSize; i++) {
							inv.setItem(i, new ItemStack(Material.AIR, 1));
						}
					}
			        
			        for (int iii = 0; iii < games.size(); iii++) {
			        	int invent = Math.floorDiv(iii, menuSize);
			            if(invs.isEmpty() || invs.size() < invent + 1) {
			            	invs.add(Bukkit.createInventory(null, menuSize + 9, menuName));
			            }

			            GameMap gMap = games.get(iii);
			            	            
			            List<String> loreList = Lists.newLinkedList();
			            if (gMap.getMatchState() != MatchState.OFFLINE) {
			            	 if (gMap.getMatchState() == MatchState.WAITINGSTART) {
						            loreList.add((new Messaging.MessageFormatter().format("signs.joinable").toUpperCase()));
					            } else if (gMap.getMatchState().equals(MatchState.PLAYING)) {
					            	loreList.add((new Messaging.MessageFormatter().format("signs.playing").toUpperCase()));
					            }  else if (gMap.getMatchState().equals(MatchState.ENDING)) {
					            	loreList.add((new Messaging.MessageFormatter().format("signs.ending").toUpperCase()));
					            }
					            loreList.add((new Messaging.MessageFormatter().setVariable("playercount", "" + gMap.getAlivePlayers().size()).setVariable("maxplayers", "" + gMap.getMaxPlayers()).format("signs.line4")));
					            for (Player p: gMap.getAllPlayers()) {
					            	if (p != null) {
					            		if (gMap.getAlivePlayers().contains(p)) {
					            			loreList.add(ChatColor.GREEN + p.getName());
					            		} else {
					            			loreList.add(ChatColor.RED + p.getName());
					            		}
					            	}
					            }
					            
					            double xy = ((double) (gMap.getAlivePlayers().size() / gMap.getMaxPlayers()));
					            
					            ItemStack gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("blockwaiting"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					            if (gMap.getMatchState().equals(MatchState.PLAYING)) {
					           	 gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("blockplaying"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					            } else if (gMap.getMatchState().equals(MatchState.ENDING)) {
					           	 gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("blockending"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					            } else if (gMap.getMatchState() == MatchState.WAITINGSTART) {
					           	 gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("almostfull"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					    	    	if (xy < 0.75) {
					    	    		gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("threefull"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					    	    	} 
					     	    	if (xy < 0.50) {
					    	    		gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("halffull"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					    	    	} 
					    	    	if (xy < 0.25) {
					    	    		gameIcon = SkyWarsReloaded.getNMS().getItemStack(SkyWarsReloaded.getIM().getItem("almostempty"), loreList, ChatColor.translateAlternateColorCodes('&', gMap.getDisplayName()));
					    	    	} 
					            }  
					            invs.get(invent).setItem(iii % menuSize, gameIcon);      
			            }
			        }
			        if (SkyWarsReloaded.getCfg().spectateMenuEnabled()) {
				        ArrayList<Inventory> specs = SkyWarsReloaded.getIC().getMenu("spectatemenu").getInventories();
				        int i = 0;
				        for (Inventory inv: invs) {
				        	if (specs.get(i) == null) {
				        		specs.add(Bukkit.createInventory(null, menuSize, new Messaging.MessageFormatter().format("menu.spectategame-menu-title")));
				        	}
				        	specs.get(0).setContents(inv.getContents());
				        	i++;
				        }
			        }
				}
			}
   		
    	};
  
        SkyWarsReloaded.getIC().create("joinmenu", invs, new IconMenu.OptionClickEventHandler() {
			@Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                Player player = event.getPlayer();
                GameMap gMap = MatchManager.get().getPlayerMap(player);
                if (gMap != null) {
                    return;
                }
                
                String name = event.getName();
                if (name.equalsIgnoreCase(SkyWarsReloaded.getNMS().getItemName(SkyWarsReloaded.getIM().getItem("exitMenuItem")))) {
	            	player.closeInventory();
	            	return;
	            }
                gMap = GameMap.getMapByDisplayName(ChatColor.stripColor(name));
                if (gMap == null) {
                	return;
                }
                
                if (gMap.getMatchState() != MatchState.WAITINGSTART) {
                	Util.get().playSound(player, player.getLocation(), SkyWarsReloaded.getCfg().getErrorSound(), 1, 1);
                	return;
                }
                    
                if (player.hasPermission("sw.join")) {
	                if (player!= null) {
	                	boolean joined = true;
			    		Party party = Party.getParty(player);
			    		if (party != null) {
			    			if(party.getLeader().equals(player.getUniqueId())) {
			    				if (gMap.getMatchState() == MatchState.WAITINGSTART && gMap.canAddParty(party)) {
				    				player.closeInventory();
					    			joined = gMap.addPlayers(party);
				    			}
			    			} else {
			    				player.closeInventory();
			    				player.sendMessage(new Messaging.MessageFormatter().format("party.onlyleader"));
			    			}
			    		} else {
		                	if (gMap.getMatchState() == MatchState.WAITINGSTART && gMap.canAddPlayer()) {
			                	player.closeInventory();
		                		joined = gMap.addPlayers(player);
				                if (!joined) {
				                	player.sendMessage(new Messaging.MessageFormatter().format("error.could-not-join2"));
				                }
		                	}
			    		}
	                }
                }
            }            
        });
        
        
        SkyWarsReloaded.getIC().getMenu("joinmenu").setUpdate(update);
        
    }

}
