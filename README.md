# Minecraft Glitch SMP Mod

Rift Glitch SMP is a server-side Fabric Minecraft mod that adds opt-in rotating glitch events to survival worlds. Operators can manage automatic anomaly cycles or trigger five glitch events directly.

## Install

1. Install Fabric Loader for Minecraft 26.3.
2. Install Fabric API for the same Minecraft version.
3. Place the Rift Glitch SMP mod JAR in the server’s `mods` folder.
4. Start or restart the server.

## Use

Run these commands as an operator with permission level 2:

- `/glitch start` enables automatic events. The first event occurs after about 15 seconds; later events occur approximately every 30 to 90 seconds.
- `/glitch stop` disables the cycle and clears the displayed active event.
- `/glitch status` reports whether the cycle is enabled and the current anomaly.
- `/glitch now` triggers an event immediately.
- `/glitch event <name>` triggers a specific event: `static`, `echo`, `moonflip`, `corruption`, or `null`.

Events announce themselves to players and apply short-lived effects or velocity changes. The mod does not modify blocks, teleport players, or delete inventory.

---

More Minecraft mod projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/4665626b-b22c-4201-bbe5-3eb1120f5d30?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=4665626b-b22c-4201-bbe5-3eb1120f5d30
