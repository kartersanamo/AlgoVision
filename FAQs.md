# AlgoVision — Frequently Asked Questions

---

## General

### What is AlgoVision?

AlgoVision is a Minecraft (Spigot) plugin that visualizes computer science algorithms in-game. You select a 3D region with a wand and run algorithms (sorting, searching, graph, tree, pathfinding, dynamic programming) that animate using blocks, particles, and sounds.

### What server software does it need?

Spigot (or a compatible fork) **1.20 or newer**. It uses the Spigot API.

### What Java version?

The server must run on **Java 17 or higher**. The project is built with Java 21.

### Can I use it on Paper / Purpur / etc.?

Yes, as long as the server is Spigot-compatible and API version 1.20 or compatible. Paper, Purpur, and similar forks are typically compatible.

---

## Commands & permissions

### Why does it say “Unknown command” when I type /algo?

The plugin is not loaded. Check that:

1. The AlgoVision JAR is in the server’s `plugins/` folder.
2. The server was restarted (or the plugin was loaded) after placing the JAR.
3. The console does not show errors when loading AlgoVision.

### Why do I get “You do not have permission”?

You need the right AlgoVision permission for that command. By default, only OPs have full access. Ask an admin to grant you the needed permission (e.g. `algovision.wand`, `algovision.visualize`) or use a permission plugin. See [SETUP.md](SETUP.md) for the permission list.

### What are the command aliases?

You can use **`/av`** or **`/algoviz`** instead of **`/algo`**. For example: `/av help`, `/algoviz wand`.

---

## Wand & region

### How do I get the wand?

Run **`/algo wand`** (with `algovision.wand`). You receive a Blaze Rod named “AlgoVision Wand”.

### The wand doesn’t set positions when I click.

- Make sure you’re **left-clicking** for Position 1 and **right-clicking** for Position 2.
- You must **click on a block** (not air).
- Confirm the item is the AlgoVision wand (Blaze Rod with the correct name). If in doubt, run `/algo wand` again.

### Why “You must select a complete region first”?

Both **Position 1** and **Position 2** must be set, and in the **same world**. Use the wand: left-click one corner, right-click the opposite corner, then run `/algo visualize ...` again.

### Why “Selected region is invalid or exceeds configured limits”?

The box between pos1 and pos2 is too big, too small, or outside limits set in `config.yml` (e.g. max width/height/length, max volume). Choose a smaller or different region, or ask an admin to adjust the region limits in config.

### Can I resize or move the region after setting it?

Yes. Use:

- **`/algo region expand <amount> [direction]`** to grow the region.
- **`/algo region contract <amount> [direction]`** to shrink it.
- **`/algo region shift <amount> <direction>`** to move it (e.g. north, up).

---

## Visualizations

### What categories and algorithms are available?

- **Sorting:** bubble, quick, merge, insertion, selection, heap, shell  
- **Searching:** binary, linear, jump  
- **Graph:** bfs, dfs, dijkstra  
- **Tree:** bst  
- **Pathfinding:** astar  
- **Dynamic:** fibonacci  

Use: **`/algo visualize <category> <algorithm>`** (e.g. `/algo visualize sorting bubble`).

### Why is the visualization so fast/slow?

Speed is controlled by the **speed** setting (ticks between steps). Lower = faster. Change it with:

- **`/algo settings set speed 5`** (faster)
- **`/algo settings get speed`** to see current value

You can also use presets (e.g. a “slow-demo” preset) to switch quickly.

### Can I pause or step through a visualization?

Yes. Use **`/algo control pause`**, then **`/algo control step`** (or **`step <sessionId> <steps>`**). Use **`/algo control resume`** to run again, or **`/algo control stop`** to end it.

### Does the plugin modify my world permanently?

Visualizations place and remove blocks **inside the region** you selected. With default **autoReset** (true), the region is cleared when the visualization ends or is stopped. If **autoReset** is false, blocks may stay until you clear them or run another visualization that clears the region.

### Can I run more than one visualization at once?

Yes. Each run gets a session. You can list them with **`/algo control list`** and control a specific one with **`/algo control pause <sessionId>`**, etc. Server config may limit how many visualizations can run at once (e.g. `maxConcurrentVisualizations`).

---

## Data & stats

### Where does the data for sorting come from?

If you ran **`/algo data generate <pattern> [size]`** before, that data is used. Otherwise, the plugin generates **random** data (size from region width and config limits).

### How do I clear my data?

Run **`/algo data clear`**. The next sorting (or similar) visualization will use newly generated data (e.g. random) according to config.

### What do “comparisons” and “swaps” mean in stats?

- **Comparisons:** how many times two elements were compared (e.g. in sorting).
- **Swaps:** how many times two elements were swapped.

These are shown when a visualization finishes (if **showStats** is true) and can be viewed with **`/algo stats recent`** or **`/algo stats top <algorithm>`**.

---

## Settings & presets

### Where are my settings stored?

Personal settings are stored in memory per player. Presets are stored by the plugin (in memory by default; persistence depends on implementation). The **config.yml** holds global defaults and region/performance/data limits.

### Can I turn off sounds or particles?

Yes. Use:

- **`/algo settings set soundEffects false`**
- **`/algo settings set particleEffects false`**

### What’s the difference between “my” settings and “global”?

- **Your settings:** only affect your visualizations. Set with **`/algo settings set <key> <value>`** (no “global”).
- **Global settings:** defaults for everyone (and for players who haven’t set a value). Set with **`/algo settings set <key> <value> global`** (requires `algovision.settings.global`).

### How do I reset my settings to default?

Use **`/algo settings reset`**. Your settings revert to the global defaults.

---

## Errors & problems

### Plugin doesn’t load / server won’t start.

- Ensure the JAR is for AlgoVision and not corrupted.
- Check that the server runs **Java 17+** and **Spigot 1.20+**.
- Read the server console/log for errors mentioning “AlgoVision” or “com.kartersanamo.algoVision”. See [Troubleshoot.md](Troubleshoot.md) for common causes.

### I get “Unknown category” or “Unknown algorithm”.

Use only supported categories and algorithms (see “What categories and algorithms are available?” above). Category and algorithm names are **lowercase** (e.g. `sorting`, `bubble`).

### Blocks don’t appear or the region doesn’t change.

- Confirm the region is valid (**`/algo region info`**).
- Check that you have build permission in that area (e.g. not in a protected zone that blocks the plugin).
- Try a smaller region and a simple algorithm (e.g. **`/algo visualize sorting bubble`**).

For more fixes, see [Troubleshoot.md](Troubleshoot.md).

---

## More help

- **Setup:** [SETUP.md](SETUP.md)  
- **Usage:** [UserGuide.md](UserGuide.md)  
- **Problems:** [Troubleshoot.md](Troubleshoot.md)  
- **Repository:** https://github.com/kartersanamo/AlgoVision  
