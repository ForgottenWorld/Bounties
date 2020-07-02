package me.kaotich00.bounties;

import me.kaotich00.bounties.command.CommandManager;
import me.kaotich00.bounties.listener.PlayerDeathListener;
import me.kaotich00.bounties.service.SimpleBountyService;
import me.kaotich00.bounties.storage.StorageManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Bounties extends JavaPlugin {

    public static FileConfiguration defaultConfig;
    public static Economy economyService;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Loading configuration...");
        loadConfiguration();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Registering services...");
        registerServices();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Registering listeners...");
        registerListeners();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Registering commands...");
        registerCommands();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Loading bounties from file...");
        try {
            loadBounties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Registering economy...");
        if (!setupEconomy()) {
            this.getLogger().severe("This plugin needs Vault and an Economy plugin in order to function!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        boolean isTownyEnable = checkTowny();
        if(isTownyEnable) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Hooking into TownyAPI...");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Towny not found, skipping.");
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Saving bounties to file...");
        saveBounties();
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        defaultConfig = getConfig();
    }

    public void registerServices() {
        SimpleBountyService.getInstance();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    public void registerCommands() {
        getCommand("bounty").setExecutor(new CommandManager(this));
    }

    public boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economyService = rsp.getProvider();
        return economyService != null;
    }

    public static boolean checkTowny() {
        return Bukkit.getPluginManager().getPlugin("Towny") != null;
    }

    public void loadBounties() throws IOException {
        StorageManager storageManager = StorageManager.getInstance();
        storageManager.loadBounties();
        storageManager.updateWorldConfigs();
    }

    public void saveBounties() {
        StorageManager storageManager = StorageManager.getInstance();
        try {
            storageManager.saveBounties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getDefaultConfig() {
        return defaultConfig;
    }

    public void reloadDefaultConfig() throws IOException {
        reloadConfig();
        defaultConfig = getConfig();
        StorageManager storageManager = StorageManager.getInstance();
        storageManager.updateWorldConfigs();
        storageManager.reloadWorldConfigs();
    }

}
