package me.kaotich00.bounties.commands.admin;

import me.kaotich00.bounties.Bounties;
import me.kaotich00.bounties.commands.CommandName;
import me.kaotich00.bounties.commands.SubCommand;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.RELOAD;
    }

    @Override
    public String getInfo() {
        return "Reload configuration file";
    }

    @Override
    public String getUsage() {
        return ChatColor.DARK_GREEN + "/bounties " + ChatColor.GREEN + CommandName.RELOAD;
    }

    @Override
    public String getPerm() {
        return "bounty.reload";
    }

    @Override
    public int getArgsRequired() {
        return 1;
    }

    @Override
    public void perform(Player sender, String[] args) {
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
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
