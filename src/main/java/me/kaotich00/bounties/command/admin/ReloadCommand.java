package me.kaotich00.bounties.command.admin;

import me.kaotich00.bounties.Bounties;
import me.kaotich00.bounties.command.api.AdminCommand;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class ReloadCommand extends AdminCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        super.onCommand(sender, args);

        Bounties plugin = Bounties.getPlugin(Bounties.class);
        try {
            plugin.reloadDefaultConfig();
            sender.sendMessage(ChatFormatter.formatSuccessMessage("Reloaded config.yml"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage("Reloaded worlds.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getInfo() {
        return super.getInfo();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    @Override
    public String getName() {
        return super.getName();
    }

}
