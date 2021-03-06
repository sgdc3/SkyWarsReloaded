package com.walrusone.skywarsreloaded.utilities.holograms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.enums.LeaderType;
import com.walrusone.skywarsreloaded.utilities.Util;

public abstract class HologramsUtil {
	protected static FileConfiguration fc;
	protected static File holoFile;
	protected static HashMap<LeaderType, List<String>> formats = new HashMap<LeaderType, List<String>>();
	
	public abstract void createLeaderHologram(Location loc, LeaderType type, String formatKey);
	public abstract void updateLeaderHolograms(LeaderType type);
	public abstract boolean removeHologram(Location loc);
	
	public void getFC() {
		holoFile = new File(SkyWarsReloaded.get().getDataFolder(), "holograms.yml");

        if (!holoFile.exists()) {
        	SkyWarsReloaded.get().saveResource("holograms.yml", false);
        }

        if (holoFile.exists()) {
            fc = YamlConfiguration.loadConfiguration(holoFile);
            for (LeaderType type: LeaderType.values()) {
            	 if (fc.getConfigurationSection("leaderboard." + type.toString().toLowerCase()) != null) {
                 	for (String key: fc.getConfigurationSection("leaderboard." + type.toString().toLowerCase()).getKeys(false)) {
                 		if (formats.get(type) == null) {
                 			formats.put(type, new ArrayList<String>());           		               			
                 		}
                 		formats.get(type).add(key);
                 	}
            	 }
            }
        }
	}
	
	public void load() {
		if (fc == null) {
			getFC();
		}
		if (fc != null) {
			for (LeaderType type: LeaderType.values()) {
				if (SkyWarsReloaded.getCfg().isTypeEnabled(type)) {
					if (fc.getConfigurationSection("leaderboard." + type.toString().toLowerCase()) != null) {
	                 	for (String key: fc.getConfigurationSection("leaderboard." + type.toString().toLowerCase()).getKeys(false)) {
	    					List<String> holograms = fc.getStringList("leaderboard." + type.toString().toLowerCase() + "." + key + ".locations");
	    					if (holograms != null) {
	    						for (String hologram: holograms) {
	    							createLeaderHologram(Util.get().stringToLocation(hologram), type, key);
	    						}
	    					}
	                 	}
					}
				}
			}
		}
	}
		
	public String getFormattedString(String string, @Nullable LeaderType type) {
		String toReturn = string;
		if (string.startsWith("item:")) {
			return string;
		}
		String[] variables = StringUtils.substringsBetween(string, "{", "}");
		if (variables == null) {
			return string;
		}
		for (String var: variables) {
			String value = getVariable(var, type);
			toReturn = toReturn.replaceAll("\\{" + var + "\\}", value);
		}
		return toReturn;
	}
	
	public String getVariable(String var, @Nullable LeaderType type) {
		String[] parts = var.split("_");
			if (SkyWarsReloaded.getLB() != null && SkyWarsReloaded.getLB().getTopList(type) != null && Util.get().isInteger(parts[1])) {
				if (SkyWarsReloaded.getLB().getTopList(type).size() > Integer.valueOf(parts[1])-1) {
					if (parts[0].equalsIgnoreCase("elo")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getElo();
					} else if (parts[0].equalsIgnoreCase("wins")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getWins();
					} else if (parts[0].equalsIgnoreCase("losses")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getLoses();
					} else if (parts[0].equalsIgnoreCase("kills")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getKills();
					} else if (parts[0].equalsIgnoreCase("deaths")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getDeaths();
					} else if (parts[0].equalsIgnoreCase("xp")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getXp();
					} else if (parts[0].equalsIgnoreCase("player")) {
						return "" + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getName();
					} else if (parts[0].equalsIgnoreCase("games_played")) {
						return "" + (SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getLoses() + SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getWins());
					} else if (parts[0].equalsIgnoreCase("kill_death")) {
						double stat = (double)((double)SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getKills()/(double)SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getDeaths());
						String statString = String.format("%1$,.2f", stat);
						return statString;
					} else if (parts[0].equalsIgnoreCase("win_loss")) {
						double stat = (double)((double)SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getWins()/(double)SkyWarsReloaded.getLB().getTopList(type).get(Integer.valueOf(parts[1]) - 1).getLoses());
						String statString = String.format("%1$,.2f", stat);
						return statString;	
					}
				}
			}
		return "NO DATA";
	}
	
	public List<String> getFormats(LeaderType type) {
		return formats.get(type);
	}

}
