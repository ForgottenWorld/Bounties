package me.kaotich00.bounties.utils;

import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.Formatter;
import java.util.Locale;

public class ChatFormatter {

    public static String pluginPrefix() {
        return  ChatColor.AQUA + "[" +
                ChatColor.DARK_AQUA + "Bounty" +
                ChatColor.AQUA + "] " +
                ChatColor.RESET;
    }

    public static String chatHeader() {
        return  ChatColor.AQUA + "oOo------------------[ " +
                ChatColor.DARK_AQUA + ChatColor.BOLD + "Bounties" +
                ChatColor.AQUA + " ]------------------oOo";
    }

    public static String chatFooter() {
        return  ChatColor.AQUA + String.join("", Collections.nCopies(53, "-"));
    }

    public static String formatSuccessMessage(String message) {
        message = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + ">> " + ChatColor.GREEN + message;
        return message;
    }

    public static String formatErrorMessage(String message) {
        message = ChatColor.DARK_RED + "" + ChatColor.BOLD + ">> " + ChatColor.RED + message;
        return message;
    }

    public static String thousandSeparator(Double value) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.ITALY);
        formatter.format("%,.2f", value);
        return sb.toString();
    }

    public static String helpMessage() {
        String message = chatHeader();
        message = message.concat(
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + "/bounty " + ChatColor.AQUA + "check " + ChatColor.GRAY + "[player]" +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + "/bounty " + ChatColor.AQUA + "reload "
        );
        return message;
    }

}
