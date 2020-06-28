package me.kaotich00.bounties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bounties extends JavaPlugin {

    public static FileConfiguration defaultConfig;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounties]" + ChatColor.RESET + " Loading configuration...");
        loadConfiguration();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        defaultConfig = getConfig();
    }

}
