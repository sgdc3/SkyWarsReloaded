package com.walrusone.skywarsreloaded.menus.playeroptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.managers.PlayerStat;
import com.walrusone.skywarsreloaded.utilities.Messaging;

public class GlassColorOption extends PlayerOption {
	private static ArrayList<PlayerOption> playerOptions = new ArrayList<PlayerOption>();
	
    public GlassColorOption(String color, String name, ItemStack item, int level, int cost, int position, int page, int menuSize) {
        this.item = item;
        this.level = level;
        this.cost = cost;
        this.key = color;
        this.name = name;
        this.position = position;
        this.page = page;
        this.menuSize = menuSize;
    }
	public static void loadPlayerOptions() {
    	playerOptions.clear();
        File glassFile = new File(SkyWarsReloaded.get().getDataFolder(), "glasscolors.yml");

        if (!glassFile.exists()) {
        	SkyWarsReloaded.get().saveResource("glasscolors.yml", false);
        }

        if (glassFile.exists()) {
            FileConfiguration storage = YamlConfiguration.loadConfiguration(glassFile);

            if (storage.getConfigurationSection("colors") != null) {
            	for (String key: storage.getConfigurationSection("colors").getKeys(false)) {
            		String color = key;
                	String name = storage.getString("colors." + key + ".displayname");
                	String material = storage.getString("colors." + key + ".material");
                	int level = storage.getInt("colors." + key + ".level");
                	int cost = storage.getInt("colors." + key + ".cost");
                	int data = storage.getInt("colors." + key + ".datavalue");
                	int position = storage.getInt("colors." + key + ".position");
                	int page = storage.getInt("colors." + key + ".page");
                	int menuSize = storage.getInt("menuSize");
                	                	
                	Material mat = Material.matchMaterial(material);
                	if (mat != null) {
                		ItemStack itemStack = null;
                		if (data == -1) {
                			itemStack = new ItemStack(mat, 1);
                		} else {
                			itemStack = new ItemStack(mat, 1, (short) data);
                		}
                		
                        if (itemStack != null) {
                            playerOptions.add(new GlassColorOption(color, name, itemStack, level, cost, position, page, menuSize));
                        }
                	}
            	}
            }
        }
        
        Collections.<PlayerOption>sort(playerOptions);
        if (playerOptions.get(3) != null && playerOptions.get(3).getPosition() == 0 || playerOptions.get(3).getPage() == 0) {
       	 	FileConfiguration storage = YamlConfiguration.loadConfiguration(glassFile);
       	 	updateFile(glassFile, storage);
        }
    }
	
    private static void updateFile(File file, FileConfiguration storage) {
        ArrayList<Integer> placement = new ArrayList<Integer>(Arrays.asList(0, 2, 4, 6, 8, 9, 11, 13, 15, 17, 18, 20, 22, 24, 26, 27, 29, 31, 33, 35, 
        		36, 38, 40, 42, 44, 45, 47, 49, 51, 53));
        storage.set("menuSize", 45);
		for (int i = 0; i < playerOptions.size(); i++) {
			playerOptions.get(i).setPosition(placement.get(i) % 45);
			playerOptions.get(i).setPage((Math.floorDiv(placement.get(i), 45))+1);
			playerOptions.get(i).setMenuSize(45);
			storage.set("colors." + playerOptions.get(i).getKey() + ".position", playerOptions.get(i).getPosition());
			storage.set("colors." + playerOptions.get(i).getKey() + ".page", playerOptions.get(i).getPage());
		}
		try {
			storage.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getPermission() {
		return ("sw.glasscolor." + key);
	}

	@Override
	public String getMenuName() {
		return "menu.usecolor-menu-title";
	}
	
	@Override
	public String getUseLore() {
		return "menu.usecolor-setcolor";
	}

	@Override
	public String getPurchaseMessage() {
		return new Messaging.MessageFormatter().setVariable("cost", "" + cost)
				.setVariable("item", name).format("menu.purchase-glass");
	}

	@Override
	public String getUseMessage() {
		return new Messaging.MessageFormatter().setVariable("color", name).format("menu.usecolor-playermsg");
	}
	
	@Override
	public void setEffect(PlayerStat stat) {
		 stat.setGlassColor(key);	
	}

    public static PlayerOption getPlayerOptionByName(String name) {
    	for (PlayerOption pOption: playerOptions) {
    		if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', pOption.getName())).equalsIgnoreCase(ChatColor.stripColor(name))) {
    			return pOption;
    		}
    	}
        return null;
    }
    
    public static PlayerOption getPlayerOptionByKey(String key) {
    	for (PlayerOption pOption: playerOptions) {
    		if (pOption.getKey().equalsIgnoreCase(key)) {
    			return pOption;
    		}
    	}
        return null;
    }
    
    public static ArrayList<PlayerOption> getPlayerOptions() {
    	return playerOptions;
    }

}
