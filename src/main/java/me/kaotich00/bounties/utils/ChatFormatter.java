package me.kaotich00.bounties.utils;

import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.Locale;

public class ChatFormatter {

    public static String pluginPrefix() {
        return  ChatColor.YELLOW + "[" +
                ChatColor.GOLD + "Bounty" +
                ChatColor.YELLOW + "] " +
                ChatColor.RESET;
    }

    public static String chatHeader() {
        return  ChatColor.YELLOW + "------------------------[ " +
                ChatColor.GOLD + ChatColor.BOLD + "Bounties" +
                ChatColor.YELLOW + " ]------------------------";
    }

    public static String formatSuccessMessage(String message) {
        message = pluginPrefix() + ChatColor.GREEN + message;
        return message;
    }

    public static String formatErrorMessage(String message) {
        message = pluginPrefix() + ChatColor.RED + message;
        return message;
    }

    public static String thousandSeparator(Integer value) {
        return NumberFormat.getNumberInstance(Locale.ITALY).format(value);
    }

    public static String helpMessage() {
        String message = chatHeader();
        message = message.concat(
                "\n" + ChatColor.YELLOW + ">> " + ChatColor.GOLD + "/er " + ChatColor.GREEN + "check "
        );
        return message;
    }

}
