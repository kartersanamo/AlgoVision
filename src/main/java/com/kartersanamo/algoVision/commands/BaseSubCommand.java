package com.kartersanamo.algoVision.commands;

import com.kartersanamo.algoVision.AlgoVision;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseSubCommand implements SubCommand {

    protected final AlgoVision plugin;

    public BaseSubCommand(AlgoVision plugin) {
        this.plugin = plugin;
    }

    protected boolean hasPermission(CommandSender sender) {
        if (getPermission() == null || getPermission().isEmpty()) {
            return true;
        }
        return sender.hasPermission(getPermission());
    }

    protected boolean isPlayer(CommandSender sender) {
        return !(sender instanceof Player);
    }

    protected void sendError(CommandSender sender, String message) {
        sender.sendMessage("§c" + message);
    }

    protected void sendSuccess(CommandSender sender, String message) {
        sender.sendMessage("§a" + message);
    }

    protected void sendInfo(CommandSender sender, String message) {
        sender.sendMessage("§e" + message);
    }

}
