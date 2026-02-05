package com.kartersanamo.algoVision.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    boolean execute(CommandSender sender, String[] args);

    String getName();

    String getDescription();

    String getUsage();

    String getPermission();

    default List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }

    default List<String> getAliases() {
        return List.of();
    }
}