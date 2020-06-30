package me.kaotich00.bounties.api.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface BountyService {

    void updatePlayerBounty(UUID playerUUID, Double bounty);

    void addBountyToPlayer(UUID playerUUID, Double amount);

    void subtractBountyFromPlayer(UUID playerUUID, Double amount);

    Optional<Double> getPlayerBounty(UUID playerUUID);

    Map<UUID,Double> getBounties();

    void setBounties(Map<UUID,Double> bounties);

}
