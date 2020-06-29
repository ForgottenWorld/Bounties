package me.kaotich00.bounties.api.command;

import org.bukkit.command.CommandSender;

public interface Command {

    void onCommand(CommandSender sender, String args[]);

    String getName();

    String getUsage();

    String getInfo();

}
