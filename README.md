# ArenaEffects
ArenaEffects is a custom Java plugin utilizing the Bukkit API, developed to give Minecraft server owners the ability to execute special effects in a nominated PVP arena.

## Features & Commands
1. Use "/setarena <area>" to set the arena location and size from present location upon executing command in-game
2. Play a handful of unique special effects within the PVP arena, including spawning skeleton armies, water drowning, lava burning, lightning strikes, inventory randomization, and random potion effects!
3. The special effects can be executed through various modalities:
- Assigning a special effect to item in hand using "/seteffect \<effect>" and dropping that item into a hopper
- Adding the special effect to a sign in-game, which is then activated when right-clicked
- Using the "/playeffect \<effect>" command to execute special effects at will

The project is highly configurable using config.yml. Custom permissions are utilized to prevent the wrong players from executing operator commands.
Reload the configuration in game using /arenaeffects reload.
