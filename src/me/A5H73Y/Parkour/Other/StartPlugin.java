package me.A5H73Y.Parkour.Other;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import me.A5H73Y.Parkour.Parkour;
import me.A5H73Y.Parkour.Player.PPlayer;
import me.A5H73Y.Parkour.Player.PlayerMethods;
import me.A5H73Y.Parkour.Utilities.Static;
import me.A5H73Y.Parkour.Utilities.Utils;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.huskehhh.mysql.Database;
import com.huskehhh.mysql.mysql.MySQL;
import com.huskehhh.mysql.sqlite.SQLite;

public class StartPlugin {

	static Plugin vault;
	
	public static void run(){
		Static.initiate();
		initiateSQL();
		setupVault();
		populatePlayers();
		//Updater
		Parkour.getMCLogger().info("[Parkour] Enabled Parkour v" + Static.getVersion() + "!");
	}

	private static void setupVault() {
		PluginManager pm = Parkour.getPlugin().getServer().getPluginManager();
		vault = pm.getPlugin("Vault");
		if (vault != null && vault.isEnabled()) {
			if (!setupEconomy()) {
				Parkour.getMCLogger().info("[Parkour] Attempted to link with Vault, but something went wrong: ");
				Parkour.getPlugin().getConfig().set("Other.Use.Economy", false);
				Parkour.getPlugin().saveConfig();
			} else {
				Parkour.getMCLogger().info("[Parkour] Linked with Economy v" + vault.getDescription().getVersion());
			}
		} else {
			Parkour.getMCLogger().info("[Parkour] Vault is missing, disabling Economy Use.");
			Parkour.getPlugin().getConfig().set("Other.Use.Economy", false);
			Parkour.getPlugin().saveConfig();
		}
	}

	public static void initiateSQL(){
		Database database;
		FileConfiguration config = Parkour.getParkourConfig().getConfig();

		if (config.getBoolean("MySQL.Use")){
			database = new MySQL(config.getString("SQL.Host"), config.getString("SQL.Port"), config.getString("SQL.Database"), config.getString("SQL.User"), config.getString("SQL.Password"));
		}else{
			database = new SQLite(Parkour.getPlugin().getDataFolder().toString() + File.separator + "parkour.db");
		}
		
		try {
			database.openConnection();
		} catch (ClassNotFoundException e) {
			Parkour.getMCLogger().info("[Parkour] MySQL connection problem: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			Parkour.getMCLogger().info("[Parkour] MySQL connection problem: " + e.getMessage());
			e.printStackTrace();
		}

		database.setupTables();
		
		Parkour.setDatabaseObj(database);
	}

	private static boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Parkour.getPlugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			Parkour.setEconomy(economyProvider.getProvider());
		}
		return (Parkour.getEconomy() != null);
	}
	
	
	private static void populatePlayers(){
		if (!new File(Static.PATH).exists())
			return;
		
		try {
			HashMap<String, PPlayer> players = (HashMap<String, PPlayer>) Utils.loadAllPlaying(Static.PATH);
			PlayerMethods.setPlaying(players);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}