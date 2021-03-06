package me.kaotich00.bounties.command.api;

import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.command.CommandSender;

public class AdminCommand extends UserCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bounties.admin")) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("You don't have permissions to run this command"));
            return;
        }
    }

}
