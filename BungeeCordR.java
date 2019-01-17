package me.kennl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.kennl.commands.CommandsManager;
import me.kennl.database.Database;
import me.kennl.listeners.ListenerManagerBungee;
import me.kennl.utils.CheckVersion;
import me.kennl.utils.Config;
import me.kennl.utils.ReportData;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordR extends Plugin {
	
	private static BungeeCordR instance;
	
	private Database database;
	private Config config;
	
	private List<ReportData> reports;
	private Map<Integer, List<String>> reasons;
	private List<ProxiedPlayer> mods;
	
	@Override
	public void onEnable() {
		
		instance = this;
		this.config = new Config();
		this.reports = new ArrayList<>();
		this.reasons = new HashMap<>();
		this.mods = new ArrayList<>();
		
		new CommandsManager(this);
		new ListenerManagerBungee(this);
		
		if(this.config.isMySQL())
			
			this.database = new Database(this.config.getString("MySql.Host", null, null), this.config.getString("MySql.User", null, null), this.config.getString("MySql.Password", null, null), this.config.getString("MySql.DataBase", null, null));
		
		logConsole("§8§m--------------------------------");
		logConsole("");
		logConsole("  §7! §cBungeeCordReports §7! §8- §4MySQL");
		logConsole("       §fPlugin by KennL");
		logConsole("");
		
		if(CheckVersion.hasUpdate())
			
			logConsole("§cThere is an update available on Spigot. Please download it.");
		
		else
			
			logConsole("§7The plugin is up to date !");
		
		logConsole("");
		logConsole("§8§m--------------------------------");
		
		getProxy().registerChannel("BungeeCordReports");
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		if(database != null)
			
			this.database.disconnect(true);
		
		super.onDisable();
	}
	
	public static BungeeCordR getInstance() {
		return instance;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public List<ProxiedPlayer> getMods() {
		return mods;
	}
	
	public List<ReportData> getReports() {
		return reports;
	}
	
	public Map<Integer, List<String>> getReasons() {
		return reasons;
	}
	
	public void logConsole(final String message) {
		
		ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message));
		
	}

}