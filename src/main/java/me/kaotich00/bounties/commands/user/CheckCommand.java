package me.kaotich00.bounties.commands.user;

import me.kaotich00.bounties.api.service.BountyService;
import me.kaotich00.bounties.commands.CommandName;
import me.kaotich00.bounties.commands.SubCommand;
import me.kaotich00.bounties.service.SimpleBountyService;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class CheckCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.CHECK;
    }

    @Override
    public String getInfo() {
        return "Check player bounties";
    }

    @Override
    public String getUsage() {
        return ChatColor.DARK_GREEN + "/bounties " + ChatColor.GREEN + CommandName.CHECK + ChatColor.DARK_GRAY +
                " <" + ChatColor.GRAY + "player_name" + ChatColor.DARK_GRAY + "> ";
    }

    @Override
    public String getPerm() {
        return "bounty.check";
    }

    @Override
    public int getArgsRequired() {
        return 1;
    }

    @Override
    public void perform(Player sender, String[] args) {
        BountyService bountyService = SimpleBountyService.getInstance();

        if(args.length == 2) {
            String nameToCheck = args[1];
            Player onlinePlayerToCheck = Bukkit.getPlayer(nameToCheck);
            if(onlinePlayerToCheck == null) {
                sender.sendMessage(ChatFormatter.formatErrorMessage("The player " + ChatColor.GOLD + nameToCheck + ChatColor.RED + " doesn't exist or is currently offline"));
                return;
            }

            if( bountyService.getPlayerBounty(onlinePlayerToCheck.getUniqueId()).isPresent()) {
                Double bounty = bountyService.getPlayerBounty(onlinePlayerToCheck.getUniqueId()).get();
                sender.sendMessage(ChatFormatter.formatSuccessMessage("The player " + ChatColor.GOLD + nameToCheck + ChatColor.GREEN + " bounty amounts to " + ChatColor.GOLD + "$" + ChatFormatter.thousandSeparator(bounty)));
            } else {
                sender.sendMessage(ChatFormatter.formatErrorMessage("The player " + ChatColor.GOLD + nameToCheck + ChatColor.RED + " has no bounty"));
            }

            return;
        }

        if( bountyService.getPlayerBounty(sender.getUniqueId()).isPresent()) {
            Double bounty = bountyService.getPlayerBounty(sender.getUniqueId()).get();
            sender.sendMessage(ChatFormatter.formatSuccessMessage("Your bounty amounts to " + ChatColor.GOLD + "$" + ChatFormatter.thousandSeparator(bounty)));
        } else {
            sender.sendMessage(ChatFormatter.formatErrorMessage("You have no bounty, kill anyone to gather the default one"));
        }

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2)
           return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return null;
    }
}
