package com.kartersanamo.algoVision.commands.AlgoCommand.SubCommands;

import com.kartersanamo.algoVision.AlgoVision;
import com.kartersanamo.algoVision.commands.BaseSubCommand;
import com.kartersanamo.algoVision.managers.RegionManager;
import com.kartersanamo.algoVision.models.Region;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionCommand extends BaseSubCommand {

    private final RegionManager regionManager;

    public RegionCommand(AlgoVision plugin) {
        super(plugin);
        this.regionManager = plugin.getRegionManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        // Ensure a player ran this command
        if (isPlayer(sender)) {
            sendError(sender, "Only players can use this command!");
            return true;
        }

        // Ensure they have the command permission to run it
        if (!hasPermission(sender)) {
            sendError(sender, "You do not have permission to use this command!");
            return true;
        }

        Player player = (Player) sender;

        // Ensure they send at least one argument
        if (args.length == 0) {
            sendInfo(player, getUsage());
            return true;
        }

        String subCommand = args[0].toLowerCase();

        // Create sub-args array (everything after the first argument)
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (subCommand) {
            case "pos1":
                return handlePos1(player, subArgs);
            case "pos2":
                return handlePos2(player, subArgs);
            case "info":
                return handleInfo(player);
            case "clear":
                return handleClear(player);
            case "expand":
                return handleExpand(player, subArgs);
            case "contract":
                return handleContract(player, subArgs);
            case "shift":
                return handleShift(player, subArgs);
            default:
                sendError(player, "Unknown subcommand: " + subCommand);
                sendInfo(player, getUsage());
                return true;
        }
    }

    // ===== SUB-COMMAND HANDLERS =====

    private boolean handlePos1(Player player, String[] args) {
        if (args.length == 0) {
            // Just "/algo region pos1" - show current pos1
            Region region = regionManager.getRegion(player);
            if (region == null || region.getPos1() == null) {
                sendInfo(player, "Position 1 is not set");
            } else {
                sendInfo(player, "Position 1: " + formatLocation(region.getPos1()));
            }
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "set":
                regionManager.setPos1(player, player.getLocation());
                sendSuccess(player, "Position 1 set to your current location");
                return true;
            case "clear":
                regionManager.clearPos1(player);
                sendSuccess(player, "Position 1 cleared");
                return true;
            case "info":
                Region region = regionManager.getRegion(player);
                if (region == null || region.getPos1() == null) {
                    sendInfo(player, "Position 1 is not set");
                } else {
                    sendInfo(player, "Position 1: " + formatLocation(region.getPos1()));
                }
                return true;
            default:
                sendError(player, "Unknown action: " + action);
                sendInfo(player, "Usage: /algo region pos1 [set|clear|info]");
                return true;
        }
    }

    private boolean handlePos2(Player player, String[] args) {
        if (args.length == 0) {
            Region region = regionManager.getRegion(player);
            if (region == null || region.getPos2() == null) {
                sendInfo(player, "Position 2 is not set");
            } else {
                sendInfo(player, "Position 2: " + formatLocation(region.getPos2()));
            }
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "set":
                regionManager.setPos2(player, player.getLocation());
                sendSuccess(player, "Position 2 set to your current location");
                return true;
            case "clear":
                regionManager.clearPos2(player);
                sendSuccess(player, "Position 2 cleared");
                return true;
            case "info":
                Region region = regionManager.getRegion(player);
                if (region == null || region.getPos2() == null) {
                    sendInfo(player, "Position 2 is not set");
                } else {
                    sendInfo(player, "Position 2: " + formatLocation(region.getPos2()));
                }
                return true;
            default:
                sendError(player, "Unknown action: " + action);
                sendInfo(player, "Usage: /algo region pos2 [set|clear|info]");
                return true;
        }
    }

    private boolean handleInfo(Player player) {
        Region region = regionManager.getRegion(player);

        if (region == null || !region.isComplete()) {
            sendError(player, "No complete region selected! Use the wand to select both positions.");
            return true;
        }

        player.sendMessage("§6§l=== Region Info ===");
        sendInfo(player, "Position 1: " + formatLocation(region.getPos1()));
        sendInfo(player, "Position 2: " + formatLocation(region.getPos2()));
        sendInfo(player, "Dimensions: " + region.getWidth() + "x" + region.getHeight() + "x" + region.getLength());
        sendInfo(player, "Volume: " + region.getVolume() + " blocks");

        return true;
    }

    private boolean handleClear(Player player) {
        regionManager.clearRegion(player);
        sendSuccess(player, "Region cleared");
        return true;
    }

    private boolean handleExpand(Player player, String[] args) {
        if (args.length == 0) {
            sendError(player, "Usage: /algo region expand <amount> [direction]");
            return true;
        }

        Region region = regionManager.getRegion(player);
        if (region == null || !region.isComplete()) {
            sendError(player, "You must select a complete region first!");
            return true;
        }

        // Parse amount
        int amount;
        try {
            amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                sendError(player, "Amount must be positive!");
                return true;
            }
        } catch (NumberFormatException e) {
            sendError(player, "Invalid number: " + args[0]);
            return true;
        }

        // Parse direction (default: all)
        String direction = args.length > 1 ? args[1].toLowerCase() : "all";

        regionManager.expandRegion(player, amount, direction);
        sendSuccess(player, "Region expanded by " + amount + " blocks " + direction);

        // Show new dimensions
        Region updated = regionManager.getRegion(player);
        sendInfo(player, "New dimensions: " + updated.getWidth() + "x" + updated.getHeight() + "x" + updated.getLength());

        return true;
    }

    private boolean handleContract(Player player, String[] args) {
        if (args.length == 0) {
            sendError(player, "Usage: /algo region contract <amount> [direction]");
            return true;
        }

        Region region = regionManager.getRegion(player);
        if (region == null || !region.isComplete()) {
            sendError(player, "You must select a complete region first!");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                sendError(player, "Amount must be positive!");
                return true;
            }
        } catch (NumberFormatException e) {
            sendError(player, "Invalid number: " + args[0]);
            return true;
        }

        String direction = args.length > 1 ? args[1].toLowerCase() : "all";

        // Check if contraction would make region too small
        if (!regionManager.canContract(player, amount, direction)) {
            sendError(player, "Cannot contract region by that amount - region would be too small!");
            return true;
        }

        regionManager.contractRegion(player, amount, direction);
        sendSuccess(player, "Region contracted by " + amount + " blocks " + direction);

        Region updated = regionManager.getRegion(player);
        sendInfo(player, "New dimensions: " + updated.getWidth() + "x" + updated.getHeight() + "x" + updated.getLength());

        return true;
    }

    private boolean handleShift(Player player, String[] args) {
        if (args.length < 2) {
            sendError(player, "Usage: /algo region shift <amount> <direction>");
            return true;
        }

        Region region = regionManager.getRegion(player);
        if (region == null || !region.isComplete()) {
            sendError(player, "You must select a complete region first!");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sendError(player, "Invalid number: " + args[0]);
            return true;
        }

        String direction = args[1].toLowerCase();

        // Validate direction
        List<String> validDirections = Arrays.asList("up", "down", "north", "south", "east", "west");
        if (!validDirections.contains(direction)) {
            sendError(player, "Invalid direction! Use: up, down, north, south, east, or west");
            return true;
        }

        regionManager.shiftRegion(player, amount, direction);
        sendSuccess(player, "Region shifted " + amount + " blocks " + direction);

        Region updated = regionManager.getRegion(player);
        sendInfo(player, "Position 1: " + formatLocation(updated.getPos1()));
        sendInfo(player, "Position 2: " + formatLocation(updated.getPos2()));

        return true;
    }

    // ===== UTILITY METHODS =====

    private String formatLocation(Location loc) {
        return String.format("(%d, %d, %d)", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    // ===== SUBCOMMAND INFO =====

    @Override
    public String getName() {
        return "region";
    }

    @Override
    public String getDescription() {
        return "Manage visualization regions";
    }

    @Override
    public String getUsage() {
        return "/algo region <pos1|pos2|info|clear|expand|contract|shift> [args...]";
    }

    @Override
    public String getPermission() {
        return "algovision.region";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            // First argument - subcommand
            String[] subCommands = {"pos1", "pos2", "info", "clear", "expand", "contract", "shift"};
            String partial = args[0].toLowerCase();

            for (String cmd : subCommands) {
                if (cmd.startsWith(partial)) {
                    suggestions.add(cmd);
                }
            }
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            String partial = args[1].toLowerCase();

            switch (subCommand) {
                case "pos1", "pos2" -> {
                    // pos1/pos2 actions
                    String[] actions = {"set", "clear", "info"};
                    for (String action : actions) {
                        if (action.startsWith(partial)) {
                            suggestions.add(action);
                        }
                    }
                }
                case "expand", "contract" ->
                    // These need a number, so suggest some common values
                        suggestions.addAll(Arrays.asList("5", "10", "15", "20"));
                case "shift" ->
                    // Amount for shift
                        suggestions.addAll(Arrays.asList("5", "10", "15", "20"));
            }
        } else if (args.length == 3) {
            String subCommand = args[0].toLowerCase();
            String partial = args[2].toLowerCase();

            if (subCommand.equals("expand") || subCommand.equals("contract")) {
                // Direction suggestions
                String[] directions = {"up", "down", "north", "south", "east", "west", "all", "horizontal", "vertical"};
                for (String dir : directions) {
                    if (dir.startsWith(partial)) {
                        suggestions.add(dir);
                    }
                }
            } else if (subCommand.equals("shift")) {
                // Direction for shift (no 'all' option)
                String[] directions = {"up", "down", "north", "south", "east", "west"};
                for (String dir : directions) {
                    if (dir.startsWith(partial)) {
                        suggestions.add(dir);
                    }
                }
            }
        }

        return suggestions;
    }
}