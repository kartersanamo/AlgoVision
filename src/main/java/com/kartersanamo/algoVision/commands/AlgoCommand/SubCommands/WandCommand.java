package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WandCommand extends BaseSubCommand {

    public WandCommand(AlgoVision plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            sendError(sender, "Only players can use this command.");
            return true;
        }
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to use this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6AlgoVision Wand");
            wand.setItemMeta(meta);
        }

        player.getInventory().addItem(wand);
        sendSuccess(player, "You have been given the AlgoVision wand.");
        return true;
    }

    @Override
    public String getName() {
        return "wand";
    }

    @Override
    public String getDescription() {
        return "Get the region selection wand.";
    }

    @Override
    public String getUsage() {
        return "/algo wand";
    }

    @Override
    public String getPermission() {
        return "algovision.wand";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return super.onTabComplete(sender, args);
    }
}
