package me.kaotich00.bounties.storage;

import me.kaotich00.bounties.Bounties;
import me.kaotich00.bounties.api.service.BountyService;
import me.kaotich00.bounties.service.SimpleBountyService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StorageManager {

    private static StorageManager storageManagerInstance;
    private String saveBountiesFile = "bounties.yml";
    private String worldConfigsFile = "worlds.yml";
    private FileConfiguration worldConfig;
    private Bounties plugin;

    public StorageManager(Bounties plugin) {
        if(storageManagerInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.plugin = plugin;
    }

    public void saveBounties() throws IOException {
        BountyService bountyService = SimpleBountyService.getInstance();
        Map<UUID,Double> bounties = bountyService.getBounties();

        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), saveBountiesFile));
        data.createSection("bounties", bounties);

        data.save(new File(plugin.getDataFolder(), saveBountiesFile));
    }

    public void loadBounties() {
        BountyService bountyService = SimpleBountyService.getInstance();

        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), saveBountiesFile));

        if(data != null) {
            Map<UUID, Double> bounties = new HashMap<>();
            for (String key : data.getConfigurationSection("bounties").getKeys(false)) {
                bounties.put(UUID.fromString(key), Double.parseDouble(data.getString("bounties." + key)));
            }

            bountyService.setBounties(bounties);
        }
    }

    public void updateWorldConfigs() throws IOException {
        List<World> worldsList = Bukkit.getServer().getWorlds();

        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), worldConfigsFile));

        for(World world: worldsList ){
            String worldName = world.getName();
            if(data.getConfigurationSection(worldName) == null) {
                data.createSection(worldName);
                data.set(worldName + ".enabled", true);
                data.set(worldName + ".percentage", 10);
                data.set(worldName + ".min_bounty_before_percentage", 30);
            }
        }

        data.save(new File(plugin.getDataFolder(), worldConfigsFile));

        this.worldConfig = data;
    }

    public void reloadWorldConfigs() {
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), worldConfigsFile));
        this.worldConfig = data;
    }

    public FileConfiguration getWorldConfig() {
        return this.worldConfig;
    }

    public static StorageManager getInstance() {
        if(storageManagerInstance == null) {
            storageManagerInstance = new StorageManager(Bounties.getPlugin(Bounties.class));
        }
        return storageManagerInstance;
    }

}
