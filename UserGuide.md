# AlgoVision — User Guide

This guide explains how to use AlgoVision in-game: selecting regions, running visualizations, and using settings, presets, data, and stats.

---

## 1. Getting started

### Base command

All AlgoVision commands start with:

- **`/algo`** (or **`/av`** / **`/algoviz`**)

Example: `/algo help`

### Basic flow

1. Get the wand: **`/algo wand`**
2. Select a region with the wand (see below).
3. Start a visualization: **`/algo visualize <category> <algorithm>`**
4. Optionally control it: **`/algo control pause`**, **resume**, **stop**, **list**, etc.

---

## 2. Region selection

### Getting the wand

- **`/algo wand`**  
  Gives you the **AlgoVision Wand** (Blaze Rod). You need `algovision.wand`.

### Setting the region

- **Left-click** a block → sets **Position 1**
- **Right-click** a block → sets **Position 2**

Both positions must be set and in the **same world**. The region is the box between the two corners.

You’ll see chat messages like:  
`Position 1 set to (x, y, z)` / `Position 2 set to (x, y, z)`.

### Region commands

| Command | Description |
|---------|-------------|
| `/algo region info` | Show pos1, pos2, dimensions, volume |
| `/algo region clear` | Clear your region (forget pos1 and pos2) |
| `/algo region pos1 set` | Set pos1 to your current location |
| `/algo region pos2 set` | Set pos2 to your current location |
| `/algo region pos1 clear` | Clear only pos1 |
| `/algo region pos2 clear` | Clear only pos2 |
| `/algo region expand <amount> [direction]` | Expand region (e.g. `expand 5 all`) |
| `/algo region contract <amount> [direction]` | Shrink region |
| `/algo region shift <amount> <direction>` | Move region (e.g. `shift 10 north`) |

**Directions:** `up`, `down`, `north`, `south`, `east`, `west`, `horizontal`, `vertical`, `all` (where applicable).

### Region limits

Regions must stay within server limits (set in `config.yml`), for example:

- Min/max width, height, length
- Max volume (total blocks)

If you get “invalid region” or “exceeds limits”, make the region smaller or check server config.

---

## 3. Running visualizations

### Command format

```text
/algo visualize <category> <algorithm>
```

You must have a **complete region** (pos1 and pos2 set) before running this.

### Categories and algorithms

| Category      | Algorithms |
|---------------|------------|
| **sorting**   | bubble, quick, merge, insertion, selection, heap, shell |
| **searching** | binary, linear, jump |
| **graph**     | bfs, dfs, dijkstra |
| **tree**      | bst |
| **pathfinding** | astar |
| **dynamic**   | fibonacci |

### Examples

- `/algo visualize sorting bubble`
- `/algo visualize sorting quick`
- `/algo visualize searching binary`
- `/algo visualize graph bfs`
- `/algo visualize tree bst`
- `/algo visualize pathfinding astar`
- `/algo visualize dynamic fibonacci`

### What you see

- **Sorting / searching:** Pillars of blocks (height = value). Colors show compared, swapped, or sorted elements; particles and sounds can show steps.
- **Graph / tree / pathfinding / dynamic:** Block highlights and colors show progress (e.g. visited nodes, path, table values).

Behavior (speed, colors, sounds, particles) is controlled by **settings** (see below).

---

## 4. Controlling visualizations

| Command | Description |
|---------|-------------|
| `/algo control list` | List your active visualizations and their state |
| `/algo control pause [sessionId]` | Pause (default: most recent session) |
| `/algo control resume [sessionId]` | Resume |
| `/algo control stop [sessionId]` | Stop and cleanup |
| `/algo control step [sessionId] [steps]` | Advance by 1 or more steps (e.g. when paused) |

If you omit `sessionId`, the **most recent** session for you is used.

---

## 5. Settings

Settings control speed, colors, effects, and display for your visualizations.

### Viewing settings

- **`/algo settings list`**  
  Lists all setting names.

- **`/algo settings get <key>`**  
  Shows your value for that key.

- **`/algo settings get <key> global`**  
  Shows the global default (requires `algovision.settings.global`).

### Changing settings

- **`/algo settings set <key> <value>`**  
  Set **your** value.

- **`/algo settings set <key> <value> global`**  
  Set the **global** default (requires `algovision.settings.global`).

### Resetting

- **`/algo settings reset`**  
  Reset **your** settings to global defaults.

- **`/algo settings reset global`**  
  Reset **global** settings to config defaults (requires `algovision.settings.global`).

### Common settings (examples)

| Key | Meaning | Example values |
|-----|--------|-----------------|
| speed | Ticks between steps (lower = faster) | 5, 10, 20 |
| delay | Ticks before first step | 0, 20 |
| highlightColor | Block for highlights | YELLOW_CONCRETE, BLUE_CONCRETE |
| swapColor | Block for swaps | RED_CONCRETE |
| sortedColor | Block for sorted elements | GREEN_CONCRETE |
| compareColor | Block for comparisons | GOLD_BLOCK |
| soundEffects | Play sounds | true, false |
| particleEffects | Show particles | true, false |
| showStats | Show stats in chat when done | true, false |
| autoReset | Clear region when visualization ends | true, false |
| displayMode | HEIGHT, COLOR, or BOTH | HEIGHT |
| baseBlock | Block used for pillars | STONE, OAK_PLANKS |
| minHeight / maxHeight | Height range for values | 1, 64 |

Material names must be valid Bukkit material names (e.g. `RED_CONCRETE`, not `red_concrete` if the plugin expects uppercase; check with `/algo settings get`).

---

## 6. Presets

Presets save and load a full set of visualization settings by name.

- **`/algo preset save <name>`**  
  Save your current settings as a preset named `<name>`.

- **`/algo preset load <name>`**  
  Load preset `<name>` into your current settings.

- **`/algo preset list`**  
  List all preset names.

- **`/algo preset delete <name>`**  
  Delete the preset `<name>`.

Presets are stored per server (in memory by default; persistence depends on plugin implementation).

---

## 7. Data (for sorting/searching)

Sorting and some searching algorithms use an array of numbers. You can pre-generate that data.

- **`/algo data generate <pattern> [size]`**  
  Generate data and assign it to you.  
  **Patterns:** random, sorted, reversed, nearlysorted, mountain, valley.  
  **Size:** optional; default from config (e.g. 30), capped at max (e.g. 100).

- **`/algo data clear`**  
  Clear your stored data. Next visualization may use a new random set.

- **`/algo data info`**  
  Show whether you have data and its size.

Examples:

- `/algo data generate random 50`
- `/algo data generate sorted`
- `/algo data clear`

---

## 8. Statistics

AlgoVision can record comparisons, swaps, and duration for runs.

- **`/algo stats recent [limit]`**  
  Show your most recent runs (default limit, e.g. 10).

- **`/algo stats top <algorithm> [limit]`**  
  Show best runs (e.g. by time) for that algorithm.  
  **Algorithm** is the name used internally (e.g. `sorting:bubble`, `searching:binary`); try the same format as in the stats messages.

Use these to compare algorithms or track improvement.

---

## 9. Tips

- **Performance:** Large regions and low speed (many steps per second) can cause lag. Prefer smaller regions or higher `speed` (fewer steps per second) on busy servers.
- **Visibility:** Use a flat, clear area for the region so pillars and highlights are easy to see.
- **Teaching:** Use **pause** and **step** to walk through an algorithm one step at a time.
- **Presets:** Create presets like “slow-demo”, “fast”, “no-sound” for different situations.

---

## 10. Quick reference

| Task | Command |
|------|---------|
| Help | `/algo help` |
| Get wand | `/algo wand` |
| Set pos1/pos2 | Left/right click with wand |
| Region info | `/algo region info` |
| Start bubble sort | `/algo visualize sorting bubble` |
| Start binary search | `/algo visualize searching binary` |
| Pause | `/algo control pause` |
| Resume | `/algo control resume` |
| Stop | `/algo control stop` |
| List sessions | `/algo control list` |
| Change speed | `/algo settings set speed 5` |
| Save preset | `/algo preset save mypreset` |
| Load preset | `/algo preset load mypreset` |
| Generate random data | `/algo data generate random 40` |
| Recent stats | `/algo stats recent` |

For problems, see [Troubleshoot.md](Troubleshoot.md) and [FAQs.md](FAQs.md).
