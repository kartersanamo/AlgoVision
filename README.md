# AlgoVision

**Visualize computer science algorithms in Minecraft.**

AlgoVision is a Spigot plugin that lets players select 3D regions in-game and run algorithm visualizations in real time. Watch sorting, searching, graph traversal, pathfinding, and more animate with blocks, particles, and sounds.

---

## Features

- **Region selection** — Use a wand (Blaze Rod) to set two corners and define a visualization area.
- **Multiple algorithm categories**
  - **Sorting** — Bubble, Quick, Merge, Insertion, Selection, Heap, Shell
  - **Searching** — Binary, Linear, Jump
  - **Graph** — BFS, DFS, Dijkstra
  - **Tree** — BST
  - **Pathfinding** — A*
  - **Dynamic programming** — Fibonacci
- **Live control** — Pause, resume, stop, or step through visualizations.
- **Customizable settings** — Speed, delay, block colors, sound/particle effects, display mode.
- **Presets** — Save and load visualization settings by name.
- **Data generation** — Random, sorted, reversed, nearly sorted, mountain, valley patterns.
- **Statistics** — Comparisons, swaps, duration; history and leaderboards per algorithm.

---

## Requirements

- **Server:** Spigot 1.20+ (API version 1.20)
- **Java:** 17 or higher (plugin built with Java 21)

---

## Quick start

1. Install the plugin (see [SETUP.md](SETUP.md)).
2. Run `/algo wand` to get the region selection wand.
3. Left-click a block for **Position 1**, right-click for **Position 2**.
4. Run `/algo visualize sorting bubble` (or another category/algorithm) to start a visualization.

For full setup and usage, see [SETUP.md](SETUP.md) and [UserGuide.md](UserGuide.md).

---

## Commands

| Command | Description |
|--------|-------------|
| `/algo help` | Show help and list subcommands |
| `/algo wand` | Get the region selection wand |
| `/algo region` | Manage region (pos1, pos2, info, clear, expand, contract, shift) |
| `/algo settings` | View or change visualization settings |
| `/algo visualize <category> <algorithm>` | Start a visualization |
| `/algo control` | Pause, resume, stop, step, or list active visualizations |
| `/algo preset` | Save, load, list, or delete presets |
| `/algo data` | Generate, clear, or view input data |
| `/algo stats` | View recent runs or leaderboards |

**Aliases:** `/av`, `/algoviz`

---

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `algovision.*` | All AlgoVision permissions | OP |
| `algovision.help` | View help | true |
| `algovision.wand` | Get the wand | OP |
| `algovision.region` | Manage regions | OP |
| `algovision.settings` | Change personal settings | OP |
| `algovision.settings.global` | Change global settings | OP |
| `algovision.visualize` | Start visualizations | OP |
| `algovision.control` | Control visualizations | OP |
| `algovision.preset` | Manage presets | OP |
| `algovision.data` | Manage data | OP |
| `algovision.stats` | View statistics | OP |

---

## Configuration

Main options are in `plugins/AlgoVision/config.yml`:

- **settings** — Default speed, delay, colors, effects, display mode, base block, height range.
- **region** — Min/max width, height, length, and max volume.
- **performance** — Max concurrent visualizations, cleanup interval.
- **data** — Default size, max size, random value range.

See [UserGuide.md](UserGuide.md) for details.

---

## Documentation

| Document | Description |
|----------|-------------|
| [SETUP.md](SETUP.md) | Installation and server setup |
| [UserGuide.md](UserGuide.md) | How to use commands and features |
| [FAQs.md](FAQs.md) | Frequently asked questions |
| [Troubleshoot.md](Troubleshoot.md) | Common issues and fixes |

---

## Building from source

```bash
git clone https://github.com/kartersanamo/AlgoVision.git
cd AlgoVision
mvn clean package
```

The built JAR will be in `target/`. Copy it to your server’s `plugins/` folder.

---

## Author & links

- **Author:** Karter Sanamo  
- **Website:** https://github.com/kartersanamo/AlgoVision  
- **License:** See repository for license information.

---

## Version

**1.0.0** — Initial release with sorting, searching, graph, tree, pathfinding, and dynamic programming visualizations.
