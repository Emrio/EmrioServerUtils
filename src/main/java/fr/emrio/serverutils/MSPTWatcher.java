package fr.emrio.serverutils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;

public class MSPTWatcher implements Listener {
	public double mspt;
	
	@EventHandler
	public void event(ServerTickEndEvent event) {
		mspt = event.getTickDuration();
	}
}
