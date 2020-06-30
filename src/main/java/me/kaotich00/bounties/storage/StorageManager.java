package me.kaotich00.bounties.storage;

import me.kaotich00.bounties.Bounties;
import me.kaotich00.bounties.api.service.BountyService;
import me.kaotich00.bounties.service.SimpleBountyService;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageManager {

    private String saveBountiesFile = "bounties.yml";
    private Bounties plugin;

    public StorageManager(Bounties plugin) {
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

        Map<UUID, Double> bounties = new HashMap<>();
        for(String key : data.getConfigurationSection("bounties").getKeys(false)) {
            bounties.put(UUID.fromString(key), Double.parseDouble(data.getString("bounties." + key)));
        }

        bountyService.setBounties(bounties);
    }

}
