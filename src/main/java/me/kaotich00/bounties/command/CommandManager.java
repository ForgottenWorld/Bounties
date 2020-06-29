package me.kaotich00.bounties.command;

import me.kaotich00.bounties.Bounties;
import me.kaotich00.bounties.api.command.Command;
import me.kaotich00.bounties.command.user.CheckCommand;
import me.kaotich00.bounties.utils.ChatFormatter;
import me.kaotich00.bounties.utils.CommandTypes;
import me.kaotich00.bounties.utils.NameUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

public class CommandManager implements TabExecutor {

    private Map<String,Command> commandRegistry;
    private Bounties plugin;

    public CommandManager(Bounties plugin) {
        this.commandRegistry = new HashMap<>();
        this.plugin = plugin;
        setup();
    }

    private void setup() {
        this.commandRegistry.put(CommandTypes.CHECK_COMMAND, new CheckCommand());
    }

    private Command getCommand(String name) {
        return this.commandRegistry.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if( args.length == 0 ) {
            sender.sendMessage(ChatFormatter.helpMessage());
            return CommandTypes.COMMAND_SUCCESS;
        }

        Command erCommand = getCommand(args[0]);

        if( erCommand != null ) {
            erCommand.onCommand(sender, args);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        String argsIndex = "";

        /* Suggest child commands */
        if( args.length == 1 ) {
            argsIndex = args[0];
            /* Admin commands */
            /* User commands */
            suggestions.add("check");
        }

        return NameUtil.filterByStart(suggestions, argsIndex);
    }
}
