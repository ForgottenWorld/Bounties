package me.kaotich00.bounties.listener;

import me.kaotich00.bounties.Bounties;
import me.kaotich00.bounties.api.service.BountyService;
import me.kaotich00.bounties.service.SimpleBountyService;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }

        FileConfiguration defaultConfig = Bounties.getDefaultConfig();
        BountyService bountyService = SimpleBountyService.getInstance();
        Player deadPlayer = event.getEntity();
        Player killer = event.getEntity().getKiller();

        killer.sendMessage(ChatFormatter.chatHeader());
        deadPlayer.sendMessage(ChatFormatter.chatHeader());

        if(bountyService.getPlayerBounty(killer.getUniqueId()).isPresent()) {

            if(!bountyService.getPlayerBounty(deadPlayer.getUniqueId()).isPresent()){
                killer.sendMessage(ChatFormatter.formatErrorMessage("The player " + ChatColor.GOLD + deadPlayer.getPlayerListName() + ChatColor.RED + " has no bounty, nothing was collected"));
                deadPlayer.sendMessage(ChatFormatter.formatSuccessMessage("You were just killed by " + ChatColor.GOLD + killer.getPlayerListName() + ChatColor.RED + " but you don't have any bounty, nothing was lost."));

                killer.sendMessage(ChatFormatter.chatFooter());
                deadPlayer.sendMessage(ChatFormatter.chatFooter());
                return;
            }

            Double playerBounty = bountyService.getPlayerBounty(deadPlayer.getUniqueId()).get();

            if(playerBounty == 0) {
                killer.sendMessage(ChatFormatter.formatErrorMessage("The player " + ChatColor.GOLD + deadPlayer.getPlayerListName() + ChatColor.RED + " has an empty bounty, nothing was collected"));
                deadPlayer.sendMessage(ChatFormatter.formatSuccessMessage("You were just killed by " + ChatColor.GOLD + killer.getPlayerListName() + ChatColor.RED + " but you have a bounty of 0, nothing was lost."));

                killer.sendMessage(ChatFormatter.chatFooter());
                deadPlayer.sendMessage(ChatFormatter.chatFooter());
                return;
            }

            Double transactionAmount = 0.0;

            Double bountyPercent = Double.valueOf(defaultConfig.getString("bounty.percentage"));
            Double minBountyBeforePercentage = Double.valueOf(defaultConfig.getString("bounty.min_bounty_before_percentage"));

            if(playerBounty <= minBountyBeforePercentage) {
                transactionAmount = playerBounty;
                killer.sendMessage(ChatFormatter.formatSuccessMessage("You collected the totality of " + ChatColor.GOLD + deadPlayer.getPlayerListName() + "'s " + ChatColor.GREEN + "bounty: " + ChatColor.GOLD + ChatFormatter.thousandSeparator(transactionAmount) + "$"));
                deadPlayer.sendMessage(ChatFormatter.formatErrorMessage("Your bounty was just collected in its totality by " + ChatColor.GOLD + killer.getPlayerListName()));
            } else {
                transactionAmount = (playerBounty * bountyPercent) / 100;
                killer.sendMessage(ChatFormatter.formatSuccessMessage("You collected the " + ChatColor.GOLD + bountyPercent + "%" + ChatColor.GREEN + " of " + ChatColor.GOLD + deadPlayer.getPlayerListName() + "'s " + ChatColor.GREEN + "bounty: " + ChatColor.GOLD + ChatFormatter.thousandSeparator(transactionAmount) + "$"));
                deadPlayer.sendMessage(ChatFormatter.formatErrorMessage("You just lost the " + ChatColor.GOLD + bountyPercent + "%" + ChatColor.RED + " of your bounty"));
            }

            bountyService.addBountyToPlayer(killer.getUniqueId(), transactionAmount);
            bountyService.subtractBountyFromPlayer(deadPlayer.getUniqueId(), transactionAmount);
        } else {
            Double defaultBounty = Double.valueOf(defaultConfig.getString("bounty.default_bounty"));

            killer.sendMessage(ChatFormatter.formatSuccessMessage("You killed your first player, collecting the default bounty of " + ChatColor.GOLD + "$" + ChatFormatter.thousandSeparator(defaultBounty)));
            deadPlayer.sendMessage(ChatFormatter.formatSuccessMessage("Your were just killed by a bountyless player, you didn't lose anything this time!"));

            bountyService.updatePlayerBounty(killer.getUniqueId(), defaultBounty);
        }

        killer.sendMessage(ChatFormatter.chatFooter());
        deadPlayer.sendMessage(ChatFormatter.chatFooter());

    }

}
