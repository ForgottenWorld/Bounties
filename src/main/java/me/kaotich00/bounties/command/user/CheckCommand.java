package me.kaotich00.bounties.command.user;

import me.kaotich00.bounties.api.service.BountyService;
import me.kaotich00.bounties.command.api.UserCommand;
import me.kaotich00.bounties.service.SimpleBountyService;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand extends UserCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        super.onCommand(sender, args);

        BountyService bountyService = SimpleBountyService.getInstance();
        Player player = (Player) sender;

        if( bountyService.getPlayerBounty(player.getUniqueId()).isPresent()) {
            Double bounty = bountyService.getPlayerBounty(player.getUniqueId()).get();
            player.sendMessage(ChatFormatter.formatSuccessMessage("Your bounty amounts to " + ChatColor.GOLD + "$" + ChatFormatter.thousandSeparator(bounty.intValue())));
        } else {
            player.sendMessage(ChatFormatter.formatErrorMessage("You have no bounty, kill anyone to gather the default one"));
        };
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
