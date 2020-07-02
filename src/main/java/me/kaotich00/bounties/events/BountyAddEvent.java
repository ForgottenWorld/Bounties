package me.kaotich00.bounties.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class BountyAddEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final UUID playerUUID;
    private final Double amount;

    public BountyAddEvent(UUID playerUUID, Double amount) {
        this.playerUUID = playerUUID;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public Double getAmount() {
        return this.amount;
    }

}
