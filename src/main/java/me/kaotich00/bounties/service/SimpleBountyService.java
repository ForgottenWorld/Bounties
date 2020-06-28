package me.kaotich00.bounties.service;

import me.kaotich00.bounties.api.service.BountyService;

import java.util.HashMap;
import java.util.Map;
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
    public void updatePlayerBounty() {

    }

    @Override
    public Double getPlayerBounty() {
        return null;
    }

}
