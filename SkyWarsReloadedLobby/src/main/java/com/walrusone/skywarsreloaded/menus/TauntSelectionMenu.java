package com.walrusone.skywarsreloaded.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.database.DataStorage;
import com.walrusone.skywarsreloaded.objects.PlayerStat;
import com.walrusone.skywarsreloaded.objects.Taunt;
import com.walrusone.skywarsreloaded.utilities.Messaging;

public class TauntSelectionMenu {

    private static final int menuSlotsPerRow = 9;
    private static final int menuSize = 45;
    private static final String menuName = new Messaging.MessageFormatter().format("menu.usetaunt-menu-title");
    
    public TauntSelectionMenu(final Player player) {
    	List<Taunt> availableItems = SkyWarsReloaded.getLM().getTaunts();

        int rowCount = menuSlotsPerRow;
        while (rowCount < 45 && rowCount < menuSize) {
            rowCount += menuSlotsPerRow;
        }

        SkyWarsReloaded.getIC().create(player, menuName, rowCount, new IconMenu.OptionClickEventHandler() {
			@Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                           	
				String tauntName = event.getName();
				Taunt taunt = SkyWarsReloaded.getLM().getTauntFromName(tauntName);
	            if (taunt == null) {
	              	return;
	            }           
	            
            	if (player.getLevel() < taunt.getLevel() && !player.hasPermission("sw.taunt." + taunt.getKey())) {
                    return;
            	} else {
                    event.setWillClose(true);
                    event.setWillDestroy(true);
            
            		PlayerStat ps = PlayerStat.getPlayerStats(player);
                	ps.setTaunt(taunt.getKey());
                	DataStorage.get().saveStats(ps);
                	player.sendMessage(new Messaging.MessageFormatter().setVariable("taunt", taunt.getName()).format("menu.usetaunt-playermsg"));
            	}   
            	
            }
        });

        ArrayList<Integer> placement = new ArrayList<Integer>(Arrays.asList(44, 0, 2, 4, 6, 8, 9, 11, 13, 15, 17, 18, 20, 22, 24, 26, 27, 29, 31, 33, 35));
        
        for (int iii = 0; iii < availableItems.size(); iii ++) {
        	if (iii >= menuSize || iii > 21) {
                break;
            }

            Taunt taunt = availableItems.get(iii);
            List<String> loreList = Lists.newLinkedList();
            ItemStack item = new ItemStack(Material.valueOf(SkyWarsReloaded.getCfg().getMaterial("nopermission")), 1);
            
            if (player.getLevel() >= taunt.getLevel() || player.hasPermission("sw.taunt." + taunt.getKey())) {
            	loreList.add(new Messaging.MessageFormatter().format("menu.usetaunt-settaunt"));
            	loreList.addAll(taunt.getLore());
            	item = new ItemStack(taunt.getIcon(), 1);
            } else {
            	loreList.add(new Messaging.MessageFormatter().setVariable("level", "" + taunt.getLevel()).format("menu.no-use"));
            }
  
            if (player != null) {
                SkyWarsReloaded.getIC().setOption(
                        player,
                        placement.get(iii),
                        item,
                        taunt.getName(),
                        loreList.toArray(new String[loreList.size()]));
            }
         }
                
        if (player != null) {
            SkyWarsReloaded.getIC().show(player);
        }
    }
}