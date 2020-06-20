package fr.emrio.serverutils;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private MSPTWatcher watcher;
	private Timer timer;
	
	@Override
	public void onEnable() {
		watcher = new MSPTWatcher();
		getServer().getPluginManager().registerEvents(watcher, this);
		
		// updates the tab list header every second with current MSPT and TPS
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				double mspt = watcher.mspt;
				double tps = getServer().getTPS()[0];
				
				ChatColor msptColor;
				if (mspt < 30) {
					msptColor = ChatColor.DARK_GREEN;
				} else if (mspt < 50) {
					msptColor = ChatColor.GREEN;
				} else if (mspt < 66) {
					msptColor = ChatColor.YELLOW;
				} else if (mspt < 100) {
					msptColor = ChatColor.RED;
				} else {
					msptColor = ChatColor.DARK_RED;
				}
				
				ChatColor tpsColor;
				String tpsPrefix = "";
				if (tps > 20) {
					tpsColor = ChatColor.DARK_GREEN;
					tpsPrefix = "*";
				} else if (tps > 16) {
					tpsColor = ChatColor.GREEN;
				} else if (tps > 13) {
					tpsColor = ChatColor.YELLOW;
				} else if (tps > 10) {
					tpsColor = ChatColor.RED;
				} else {
					tpsColor = ChatColor.DARK_RED;
				}
				tps = Math.min(tps, 20);
				
				DecimalFormat decfmt = new DecimalFormat("#0.00");
				String header = " MSPT: " + msptColor + decfmt.format(mspt) + ChatColor.RESET + " ms - TPS: " + tpsColor + tpsPrefix + decfmt.format(tps) + ChatColor.RESET + " \n";
				
				for (Player player : getServer().getOnlinePlayers()) {
					player.setPlayerListHeader(header);
				}
			}
		}, 1000, 1000);
		
		System.out.println("[ESU] Plugin enabled");
	}
	
	@Override
	public void onDisable() {
		timer.cancel();
		System.out.println("[ESU] Plugin disabled");
	}
}
