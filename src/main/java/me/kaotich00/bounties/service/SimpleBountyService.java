package me.kaotich00.bounties.service;

import me.kaotich00.bounties.api.service.BountyService;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SimpleBountyService implements BountyService {

    private static SimpleBountyService bountyServiceInstance;
    private Map<UUID,Double> bounties;

    private SimpleBountyService() {
        if(bountyServiceInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.bounties = new HashMap<>();
    }

    public static SimpleBountyService getInstance() {
        if(bountyServiceInstance == null) {
            bountyServiceInstance = new SimpleBountyService();
        }
        return bountyServiceInstance;
    }

    @Override
    public void updatePlayerBounty(UUID playerUUID, Double bounty) {
        Player player = Bukkit.getPlayer(playerUUID);
        this.bounties.put(playerUUID, bounty);
        if(player != null) {
            player.sendMessage(ChatFormatter.formatSuccessMessage("New bounty: " + ChatColor.GOLD + "$" + ChatFormatter.thousandSeparator(getPlayerBounty(playerUUID).get())) );
        }
    }

    @Override
    public void addBountyToPlayer(UUID playerUUID, Double amount) {
        getPlayerBounty(playerUUID).ifPresent(bounty -> {
            updatePlayerBounty(playerUUID, bounty + amount);
        });
    }

    @Override
    public void subtractBountyFromPlayer(UUID playerUUID, Double amount) {
        getPlayerBounty(playerUUID).ifPresent(bounty -> {
            updatePlayerBounty(playerUUID, Math.max(bounty - amount, 0));
        });
    }

    @Override
    public Optional<Double> getPlayerBounty(UUID playerUUID) {
        return this.bounties.containsKey(playerUUID) ? Optional.ofNullable(this.bounties.get(playerUUID)) : Optional.empty();
    }

    @Override
    public Map<UUID, Double> getBounties() {
        return this.bounties;
    }

    @Override
    public void setBounties(Map<UUID, Double> bounties) {
        this.bounties = bounties;
    }

}
