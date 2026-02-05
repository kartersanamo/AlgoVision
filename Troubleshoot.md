# AlgoVision — Troubleshooting

This guide helps you fix common issues with AlgoVision.

---

## Plugin won’t load

### Symptom

- Server starts but AlgoVision doesn’t appear in `/plugins` or doesn’t run.
- Console shows an error when loading the plugin or no “AlgoVision has been enabled!” message.

### Checks

1. **JAR location**  
   The file must be **`AlgoVision-1.0.0.jar`** (or current version) inside the server’s **`plugins/`** folder, not in a subfolder.

2. **Server type and version**  
   AlgoVision targets **Spigot 1.20+** (API 1.20). If you use an older Spigot or a non-Spigot server, the plugin may fail to load. Use a Spigot 1.20+ or compatible fork (e.g. Paper 1.20+).

3. **Java version**  
   The server must run on **Java 17 or higher**. Check with `java -version`. Upgrade the server’s Java if needed.

4. **Corrupt or wrong JAR**  
   Re-download or rebuild the JAR. After replacing it, restart the server and check the console again.

5. **Console errors**  
   Look for stack traces containing `AlgoVision` or `com.kartersanamo.algoVision`. Note the first error line (e.g. missing class, incompatible API) and use it to narrow down the cause (wrong server/API, wrong Java, or a bug).

---

## “Unknown command” for /algo

### Symptom

Typing `/algo` (or `/av`, `/algoviz`) shows “Unknown command”.

### Fixes

1. Confirm the plugin is loaded: run **`/plugins`** and check that AlgoVision is listed and enabled.
2. Restart the server after adding or updating the JAR.
3. If you use a command alias plugin, ensure it doesn’t override or block `/algo`.

---

## Permission denied

### Symptom

- “You do not have permission to use this command” (or similar).
- You can’t get the wand, visualize, or use other features.

### Fixes

1. **OPs:** If you are OP, you should have `algovision.*`. De-op and re-op: **`/deop <yourname>`** then **`/op <yourname>`**, or check server `ops.json`/`ops.txt`.
2. **Non-OPs:** An admin must grant the right permission nodes (e.g. `algovision.wand`, `algovision.visualize`, `algovision.region`). Use a permission plugin (LuckPerms, GroupManager, etc.) and grant at least the nodes you need. See [SETUP.md](SETUP.md) for the full list.
3. **Permission plugin:** If you use a permission plugin, ensure it’s loaded before AlgoVision and that your group/user has the AlgoVision nodes. Reload the permission plugin and try again.

---

## Wand doesn’t set positions

### Symptom

Left- or right-clicking blocks with the wand doesn’t set Position 1 or 2; no chat message.

### Fixes

1. **Correct item:** You must hold the **AlgoVision Wand** (Blaze Rod with the correct display name). Get it again with **`/algo wand`**.
2. **Click on a block:** You must click on a **block**, not air or an entity. Try clicking on solid blocks (e.g. stone, dirt).
3. **Left vs right:** **Left-click** = Position 1, **Right-click** = Position 2.
4. **Other plugins:** Another plugin might be cancelling the click (e.g. protection, plot, or block-interaction plugins). Try in a different area or temporarily disable other plugins that change block clicks.
5. **Build permission:** Ensure the server allows block interactions in that area (e.g. not in a region where players can’t interact).

---

## “You must select a complete region first”

### Symptom

This message appears when running **`/algo visualize ...`**.

### Fixes

1. Set **both** corners with the wand: **left-click** one corner (Position 1), **right-click** the opposite corner (Position 2).
2. Both positions must be in the **same world**. If you set pos1 in the Overworld and pos2 in the Nether, the region is invalid.
3. Check with **`/algo region info`**. It should show both positions and dimensions. If not, set pos1 and pos2 again.

---

## “Selected region is invalid or exceeds configured limits”

### Symptom

You have a complete region but the plugin says the region is invalid or over the limit.

### Fixes

1. **Smaller region:** Make the box between pos1 and pos2 smaller. Use **`/algo region info`** to see width, height, length, and volume.
2. **Config limits:** In **`plugins/AlgoVision/config.yml`**, check the **region** section, e.g.:
   - `minWidth`, `minHeight`, `minLength`
   - `maxWidth`, `maxHeight`, `maxLength`
   - `maxVolume`
   Adjust these if you need larger or smaller regions (then restart or reload config).
3. **Contract:** Use **`/algo region contract <amount> all`** to shrink the region, then try again.

---

## “Unknown category” or “Unknown algorithm”

### Symptom

You get this when running **`/algo visualize <category> <algorithm>`**.

### Fixes

1. Use **lowercase** category and algorithm names, e.g. `sorting`, `bubble`, `searching`, `binary`.
2. Use only supported combinations, e.g.:
   - **sorting:** bubble, quick, merge, insertion, selection, heap, shell  
   - **searching:** binary, linear, jump  
   - **graph:** bfs, dfs, dijkstra  
   - **tree:** bst  
   - **pathfinding:** astar  
   - **dynamic:** fibonacci  
3. Check for typos (e.g. `buble` → `bubble`, `quicksort` → `quick`).

---

## Nothing happens when I start a visualization

### Symptom

You run **`/algo visualize ...`** and get a success message, but no blocks appear or no animation.

### Fixes

1. **Region in view:** Move so the region is loaded and visible. If the region is far away or in an unloaded chunk, the server might not render it for you.
2. **Build permission:** The plugin must be allowed to place/break blocks in that area. If a protection plugin blocks the plugin’s edits, nothing will appear. Test in a world/area where the server (or a trusted source) can build.
3. **Speed:** If **speed** is very high (e.g. 100), steps are rare and the animation may seem stuck. Try **`/algo settings set speed 10`** and run again.
4. **Region size:** For sorting/searching, the region **width** (X size) usually defines the array size. If the region is very small (e.g. width 1), the visualization may finish almost instantly or behave oddly. Use a region with width at least about 5–10 for a visible effect.
5. **Console:** Check the server console for errors when you start the visualization (e.g. NullPointerException, “cannot place block”). That will point to a bug or environment issue.

---

## Visualization is too fast or too slow

### Symptom

Animation is hard to follow (too fast) or boring (too slow).

### Fixes

1. **Speed (ticks between steps):**  
   - **Faster:** lower value, e.g. **`/algo settings set speed 5`**.  
   - **Slower:** higher value, e.g. **`/algo settings set speed 20`**.  
   Use **`/algo settings get speed`** to see the current value.
2. **Presets:** Create presets for different speeds, e.g. **`/algo preset save slow-demo`** after setting a high speed, then **`/algo preset load slow-demo`** when you want that speed.

---

## Blocks stay after the visualization ends

### Symptom

The region is still filled with blocks after the algorithm finishes or you stop it.

### Fixes

1. **autoReset:** If **autoReset** is false, the plugin may not clear the region. Set it to true: **`/algo settings set autoReset true`**.
2. **Manual clear:** You can clear the area manually (break blocks or use another plugin) if needed.
3. **New visualization:** Starting another visualization in the same region often clears and redraws; try **`/algo visualize sorting bubble`** again (or stop the current one first).

---

## Performance / lag

### Symptom

Server TPS drops or players lag when running visualizations.

### Fixes

1. **Smaller regions:** Use a smaller region (fewer blocks). Volume and width directly affect how much work and how many block updates happen.
2. **Fewer concurrent visualizations:** In **`config.yml`**, lower **`performance.maxConcurrentVisualizations`** (e.g. 2 or 3) so fewer run at once.
3. **Slower speed:** Increase **speed** (e.g. 15–20) so there are fewer steps per second and fewer block updates.
4. **Disable effects:** Turn off particles and sounds for that player: **`/algo settings set particleEffects false`** and **`/algo settings set soundEffects false`**.
5. **Limit who can run:** Restrict **algovision.visualize** to a few users or a dedicated area so not everyone runs large visualizations at once.

---

## Config changes don’t apply

### Symptom

You edit **`config.yml`** but in-game behavior doesn’t change.

### Fixes

1. **Restart:** Many configs are read only at startup. Fully restart the server after editing `config.yml`.
2. **Correct file:** Edit **`plugins/AlgoVision/config.yml`** on the server that is actually running. If you edit a copy or another server’s file, changes won’t apply.
3. **YAML format:** Keep correct indentation (spaces, no tabs). Invalid YAML can cause the plugin to ignore the file or fail to load. Check the console for config-related errors.
4. **Defaults:** If you remove an option, the plugin may fall back to built-in defaults. Re-add the option with the value you want if it disappears.

---

## Stats or presets missing after restart

### Symptom

After a server restart, stats history or saved presets are gone.

### Explanation

By default, stats and presets may be kept only in memory. If the plugin does not save them to disk (or the data folder is not persisted), they are lost on restart. This is expected unless the plugin or config explicitly enables persistence for stats/presets.

### What you can do

- Use **`/algo stats recent`** and **`/algo preset list`** before restart if you need to record something.
- Check the plugin documentation or **config.yml** for any option to “save stats” or “persist presets”; if present, enable it.
- If you need long-term stats or presets, request or implement a feature to save them to a file (e.g. config or JSON).

---

## Getting more help

1. **Console and logs:** When something goes wrong, copy the **exact** error from the server console or log (including the line that mentions AlgoVision or the exception).
2. **Steps to reproduce:** Note exactly what you did (commands, wand clicks, region size, algorithm).
3. **Environment:** Server type and version (e.g. Paper 1.20.4), Java version, other plugins that might interact (protection, permissions, world edit).
4. **Docs:** [SETUP.md](SETUP.md), [UserGuide.md](UserGuide.md), [FAQs.md](FAQs.md).  
5. **Issues:** If you believe it’s a bug, open an issue on the project repository (e.g. https://github.com/kartersanamo/AlgoVision) with the information above.
