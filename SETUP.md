# AlgoVision — Setup Guide

This guide covers installing and configuring AlgoVision on your Minecraft server.

---

## 1. Requirements

Before installing, ensure:

- **Server software:** Spigot (or compatible fork) **1.20 or newer**
- **Java:** **17 or higher** (Java 21 recommended)
- **Access:** Ability to place the plugin JAR in the server’s `plugins/` folder and restart (or use a plugin manager)

---

## 2. Installation

### Option A: Pre-built JAR

1. Download the latest `AlgoVision-1.0.0.jar` from the [releases](https://github.com/kartersanamo/AlgoVision/releases) (or your build output).
2. Stop your Minecraft server (if it is running).
3. Copy `AlgoVision-1.0.0.jar` into the server’s **`plugins/`** folder.
4. Start the server.
5. Confirm in the console that AlgoVision loaded, e.g.:
   ```text
   [AlgoVision] AlgoVision has been enabled!
   ```

### Option B: Build from source

1. Clone the repository:
   ```bash
   git clone https://github.com/kartersanamo/AlgoVision.git
   cd AlgoVision
   ```
2. Build with Maven:
   ```bash
   mvn clean package
   ```
3. Find the JAR in **`target/AlgoVision-1.0.0.jar`** (or the version in `pom.xml`).
4. Copy that JAR into your server’s **`plugins/`** folder and start the server as in Option A.

---

## 3. First run

After the first successful start:

- A **`plugins/AlgoVision/`** folder is created.
- **`config.yml`** is generated with default settings (if not present, the plugin creates it from defaults).

You can edit `config.yml` before or after players use the plugin; changes take effect on the next reload or restart (see below).

---

## 4. Configuration file

Location: **`plugins/AlgoVision/config.yml`**

### Sections

| Section | Purpose |
|--------|---------|
| **settings** | Default visualization behavior: speed, delay, block colors, sound/particle effects, display mode, base block, min/max height |
| **region** | Limits for selected regions: min/max width, height, length, max volume |
| **performance** | Max concurrent visualizations per server, cleanup interval (ticks) |
| **data** | Data generation: default size, max size, random value range (min/max) |

### Applying changes

- **Restart:** Stop and start the server.
- **Reload (if supported):** Use your server’s or another plugin’s config reload; AlgoVision does not define its own reload command in the default setup, so restart is the guaranteed way.

---

## 5. Permissions setup

AlgoVision uses Bukkit/Spigot permissions. By default:

- **OPs** get full access via `algovision.*` (or individual nodes).
- **Non-OPs** only have `algovision.help` (default: true).

To give non-OP players access:

1. Use a permission plugin (e.g. LuckPerms, GroupManager).
2. Grant the nodes you want, for example:
   - `algovision.wand` — get the wand
   - `algovision.region` — use region commands
   - `algovision.visualize` — start visualizations
   - `algovision.control` — pause/resume/stop/step
   - `algovision.settings` — change personal settings
   - `algovision.preset` — save/load presets
   - `algovision.data` — generate/clear data
   - `algovision.stats` — view stats
   - `algovision.settings.global` — change global settings (use sparingly)

Granting **`algovision.*`** gives all AlgoVision permissions.

---

## 6. Verifying installation

1. Join the server and run:
   ```text
   /algo help
   ```
   You should see the help message and list of subcommands.

2. If you have permission:
   ```text
   /algo wand
   ```
   You should receive the AlgoVision wand (Blaze Rod).

3. Select a region (wand: left-click = pos1, right-click = pos2), then:
   ```text
   /algo visualize sorting bubble
   ```
   A bubble sort visualization should run in the selected region.

If any of these fail, see [Troubleshoot.md](Troubleshoot.md).

---

## 7. Updating

1. Stop the server.
2. Replace the old `AlgoVision-*.jar` in `plugins/` with the new JAR.
3. Start the server.  
   Your existing `config.yml` is kept; new options may need to be added manually if the plugin adds them in a future version.

---

## 8. Uninstallation

1. Stop the server.
2. Remove `AlgoVision-*.jar` from `plugins/`.
3. Optionally delete the `plugins/AlgoVision/` folder to remove config and any stored data.
4. Start the server.

---

## Next steps

- [UserGuide.md](UserGuide.md) — Daily usage: wand, regions, visualize, control, settings, presets, data, stats.
- [FAQs.md](FAQs.md) — Common questions.
- [Troubleshoot.md](Troubleshoot.md) — Fixing common issues.
