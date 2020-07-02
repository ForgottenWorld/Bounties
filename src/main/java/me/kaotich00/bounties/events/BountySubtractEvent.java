package me.kaotich00.bounties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class BountySubtractEvent extends Event {

    private final HandlerList handlers = new HandlerList();
    private final UUID playerUUID;
    private final Double amount;

    public BountySubtractEvent(UUID playerUUID, Double amount) {
        this.playerUUID = playerUUID;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public Double getAmount() {
        return this.amount;
    }

}
