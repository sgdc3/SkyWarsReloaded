package com.walrusone.skywarsreloaded.commands;

import org.bukkit.Location;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.utilities.Messaging;

public class SetSpawnCmd extends BaseCmd { 
	
	public SetSpawnCmd() {
		forcePlayer = true;
		cmdName = "setspawn";
		alias = new String[]{"sspawn"};
		argLength = 1; //counting cmdName
	}

	@Override
	public boolean run() {
	    Location spawn = player.getLocation();
	    SkyWarsReloaded.getCfg().setSpawn(spawn);
	    SkyWarsReloaded.getCfg().save();
	    player.sendMessage(new Messaging.MessageFormatter().format("command.spawnset"));
		return true;
	}

}
