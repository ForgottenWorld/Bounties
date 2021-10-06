package me.kaotich00.bounties.commands.admin;

import me.kaotich00.bounties.commands.CommandName;
import me.kaotich00.bounties.commands.SubCommand;
import me.kaotich00.bounties.service.SimpleBountyService;
import me.kaotich00.bounties.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModifyCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.MODIFY;
    }

    @Override
    public String getInfo() {
        return "Modify player bounty";
    }

    @Override
    public String getUsage() {
        return ChatColor.DARK_GREEN + "/bounty " + ChatColor.GREEN + CommandName.MODIFY + ChatColor.DARK_GRAY + " <" +
                ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> <" + ChatColor.GRAY +
                "add" + ChatColor.DARK_GRAY + "/" + ChatColor.GRAY + "subtract" + ChatColor.DARK_GRAY + "> <"
                + ChatColor.GRAY + "value" + ChatColor.DARK_GRAY + ">";
    }

    @Override
    public String getPerm() {
        return "bounty.modify";
    }

    @Override
    public int getArgsRequired() {
        return 4;
    }

    private static final List<String> actions = Arrays.asList("add","subtract");

    @Override
    public void perform(Player sender, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if (Objects.isNull(player)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Selected player must be online"));
            return;
        }

        double value;

        try {
            value =  Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Inserted <value> must be numeric"));
            return;
        }

        SimpleBountyService service = SimpleBountyService.getInstance();

        switch (args[2].toLowerCase()) {
            case "add":
                service.addBountyToPlayer(player.getUniqueId(),value);
                break;
            case "subtract":
                service.subtractBountyFromPlayer(player.getUniqueId(),value);
                break;
            default:
                sender.sendMessage("Not a valid argument, allowed arguments: add,subtract");
                return;
        }

        sender.sendMessage(ChatFormatter.formatSuccessMessage("Player " + ChatColor.YELLOW + player.getName() +
                ChatColor.RESET + " bounty has been updated"));

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2)
            return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        if (args.length == 3)
            return actions;
        if (args.length == 4)
            return Collections.singletonList("<value>");
        return null;
    }
}
