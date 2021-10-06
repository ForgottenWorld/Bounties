package me.kaotich00.bounties.commands;

import me.kaotich00.bounties.commands.admin.ModifyCommand;
import me.kaotich00.bounties.commands.admin.ReloadCommand;
import me.kaotich00.bounties.commands.user.CheckCommand;
import me.kaotich00.bounties.utils.ChatFormatter;
import me.kaotich00.bounties.utils.NameUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class CommandManager implements TabExecutor{

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        //** admin commands **//
        subcommands.add(new ReloadCommand());
        subcommands.add(new ModifyCommand());

        //** user commands **//
        subcommands.add(new CheckCommand());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Error: can't run commands from console");
            return true;
        }

        Player p = (Player) sender;

        if (args.length > 0) {
            for (int i = 0; i < getSubcommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {

                    SubCommand subCommand = getSubcommands().get(i);

                    if (!sender.hasPermission(subCommand.getPerm())) {
                        sender.sendMessage("You don't have permissions to run this command");
                        return true;
                    }

                    if (args.length < subCommand.getArgsRequired()) {
                        sender.sendMessage("Not enough arguments, usage: " + subCommand.getUsage());
                        return true;
                    }

                    subCommand.perform(p, args);

                }
            }
        }else{
            String helpMsg = ChatFormatter.chatHeader();

            for (int i = 0; i < getSubcommands().size(); i++) {

                SubCommand subCommand = getSubcommands().get(i);

                if (!sender.hasPermission(subCommand.getPerm()))
                    continue;

                helpMsg = helpMsg
                        .concat("\n" + ChatColor.GRAY + ">> " + ChatColor.RESET + subCommand.getUsage()
                        + "\n" + ChatColor.GRAY + "   -- " + ChatColor.RESET + subCommand.getInfo());

            }

            p.sendMessage(helpMsg);
        }

        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String argChar;

        if (args.length == 1) {
            argChar = args[0];

            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubcommands().size(); i++) {
                SubCommand subCommand = getSubcommands().get(i);

                if (!sender.hasPermission(subCommand.getPerm()))
                    continue;

                subcommandsArguments.add(subCommand.getName());
            }

            return NameUtil.filterByStart(subcommandsArguments,argChar);

        }else if (args.length >= 2) {
            argChar = args[args.length - 1];

            for (int i = 0; i < getSubcommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                    return NameUtil.filterByStart(getSubcommands().get(i).getSubcommandArguments((Player) sender, args),argChar);
                }
            }
        }

        return null;
    }

}