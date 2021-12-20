package me.tezk.arenaeffects;

import me.tezk.arenaeffects.commands.ArenaEffectsReloadCommand;
import me.tezk.arenaeffects.commands.PlayEffectCommand;
import me.tezk.arenaeffects.commands.SetArenaCommand;
import me.tezk.arenaeffects.commands.SetEffectCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ArenaEffects extends JavaPlugin {

    private int radius = 0;
    private String world = "world";
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private Location arenaLocation = new Location(getServer().getWorld(world), x, y, z);

    private String lightningItemTitle;
    private String skeleItemTitle;
    private String waterItemTitle;
    private String partyItemTitle;
    private String randomInventoryItemTitle;
    private String lavaItemTitle;

    private List<String> partyEffectList;

    private String signActivationText;
    private int signActLineNumber;
    private int arenaEffectSignLine;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        updateArenaLocation();
        updateConfigVariables();

        getServer().getPluginManager().registerEvents(new DropItemListener(this), this);
        getServer().getPluginManager().registerEvents(new ClickSignListener(this), this);
        getCommand("setarena").setExecutor(new SetArenaCommand(this));
        getCommand("seteffect").setExecutor(new SetEffectCommand(this));
        getCommand("arenaeffects").setExecutor(new ArenaEffectsReloadCommand(this));
        getCommand("playeffect").setExecutor(new PlayEffectCommand(this));
    }

    public void updateConfigVariables() {
        this.lightningItemTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("item-effects.lightning.title")));
        this.skeleItemTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("item-effects.skeletons.title")));
        this.waterItemTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("item-effects.water.title")));
        this.lavaItemTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("item-effects.lava.title")));
        this.partyItemTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("item-effects.party.title")));
        this.randomInventoryItemTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("item-effects.random-inv.title")));

        this.partyEffectList = getConfig().getStringList("effect-config.party.effects");

        this.signActivationText = getConfig().getString("signs.activation-text");
        this.signActLineNumber = getConfig().getInt("signs.activation-text-line");
        this.arenaEffectSignLine = getConfig().getInt("signs.arena-effect-line");
    }

    public void updateArenaLocation() {
        try {
            this.radius = getConfig().getInt("data.arena-location.radius");
            this.world = getConfig().getString("data.arena-location.world");
            this.x = getConfig().getInt("data.arena-location.x");
            this.y = getConfig().getInt("data.arena-location.y");
            this.z = getConfig().getInt("data.arena-location.z");
            this.arenaLocation = new Location(getServer().getWorld(world), x, y, z);
        } catch (Exception exc) {
            getLogger().info("Arena has not been set yet! Use /setarena <radius>");
        }
    }

    public int getArenaRadius() {
        return this.radius;
    }


    public void skeletonEffect() {
        int skeleCount = 0;

        for (Block block : getArenaBlocks(arenaLocation, 25, y)) {
            Random r = new Random();
            int game = r.nextInt(200);

            if (game < 0.2) { // this is the perfect amount for these ratios, do not touch
                if (skeleCount == 20) { // maximum skeleton spawn amount
                    return;
                }
                skeleCount++;
                try {
                    getServer().getWorld(world).spawnEntity(block.getLocation(), EntityType.SKELETON);
                } catch (Exception exc) {

                }
                // for debugging block.setType(Material.DIAMOND_BLOCK);
            }
        }
    }

    public void lightningEffect() {
        new BukkitRunnable() {
            int counter = 20;

            @Override
            public void run() {
                counter += 20;
                // 1200L = 60s
                if (counter == 1200) this.cancel();

                for (Block block : getArenaBlocks(arenaLocation, radius, y)) {
                    Random r = new Random();
                    int game = r.nextInt(1000);
                    if (game < 10) { // 20%
                        getServer().getWorld(world).strikeLightning(block.getLocation());
                    }
                }
            }
        }.runTaskTimer(this, 0L,
                20L); // 20L = 1s
    }

    public void waterEffect() {
        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                counter++;

                for (Block block : getArenaBlocks(arenaLocation, radius, y)) {
                    if (counter == 1) {
                        if (block.isEmpty()) {
                            block.setType(Material.WATER);
                        }
                    } else if (counter == 2) {
                        if (block.isLiquid()) {
                            block.setType(Material.AIR);
                        }
                    }
                }
                if (counter == 2) {
                    this.cancel();
                }
            }
        }.runTaskTimer(this, 0L,
                300L);
    }

    public void lavaEffect() {
        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                counter++;

                for (Block block : getArenaBlocks(arenaLocation, radius, y)) {
                    if (counter == 1) {
                        if (block.isEmpty()) {
                            Random r = new Random();
                            int game = r.nextInt(200);
                            int blockCount = 0;
                            if (game < 5) { // this is the perfect amount for these ratios, do not touch
                                if (blockCount == 20) { // maximum block spawn amount
                                    return;
                                }
                                blockCount++;
                                block.setType(Material.LAVA);
                            }
                        }
                    } else if (counter == 2) {
                        if (block.isLiquid()) {
                            block.setType(Material.AIR);
                        }
                    }
                }
                if (counter == 2) {
                    this.cancel();
                }
            }
        }.runTaskTimer(this, 0L,
                300L);
    }

    public void partyEffect() {
        for (Block block : getArenaBlocks(arenaLocation, radius, y)) {
            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                if (onlinePlayer.getLocation().getBlockX() == block.getLocation().getBlockX()
                        && onlinePlayer.getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
                    // in arena
                    // 600 = 20L x 30s (20L = 1s)
                    // get list of effects from config
                    List<PotionEffectType> potions = new ArrayList<>();
                    for (String effect : getPartyEffectList()) {
                        try {
                            potions.add(PotionEffectType.getByName(effect));
                        } catch (Exception ex) {
                            getLogger().info("Wrong potion effect detected in party effects configuration. Use " +
                                    "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html");
                        }
                    }

                    Random random = new Random();
                    onlinePlayer.addPotionEffect(new PotionEffect(potions.get(random.nextInt(potions.size())), 600, 1));
                }
            }
        }
    }

    public void randomInventoryEffect() {
        for (Block block : getArenaBlocks(arenaLocation, radius, y)) {
            for (Player onlinePlayer : getServer().getOnlinePlayers()) {

                if (onlinePlayer.getLocation().getBlockX() == block.getLocation().getBlockX()
                        && onlinePlayer.getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
                    // player is in arena
                    ItemStack[] items = onlinePlayer.getInventory().getContents();
                    List<ItemStack> inventory = new ArrayList<>();

                    for (ItemStack item : items) {
                        if (item != null) {
                            inventory.add(item);
                            System.out.println(item.getType().toString());
                        }
                    }

                    Collections.shuffle(inventory);

                    onlinePlayer.getInventory().clear();
                    for (ItemStack item : inventory) {
                        // add randomizer
                        // slots 0 - 35
                        Random r = new Random();
                        int slot = r.nextInt(35);
                        while (onlinePlayer.getInventory().getItem(slot) != null) {
                            slot = r.nextInt(35);
                        }
                        onlinePlayer.getInventory().setItem(slot, item);
                    }
                }
            }
        }
    }

    public static List<Block> getArenaBlocks(Location location, int radius, int y) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                blocks.add(location.getWorld().getBlockAt(x, y, z));
            }
        }
        return blocks;
    }

    public String getLightningItemTitle() {
        return this.lightningItemTitle;
    }

    public String getSkeleItemTitle() {
        return this.skeleItemTitle;
    }

    public String getWaterItemTitle() {
        return this.waterItemTitle;
    }

    public String getPartyItemTitle() {
        return this.partyItemTitle;
    }

    public String getRandomInventoryItemTitle() {
        return this.randomInventoryItemTitle;
    }

    public String getLavaItemTitle() {
        return this.lavaItemTitle;
    }

    public List<String> getPartyEffectList() {
        return this.partyEffectList;
    }

    public String getSignActivationText() {
        return this.signActivationText;
    }

    public int getSignActLineNumber() {
        return this.signActLineNumber;
    }

    public int getArenaEffectSignLine() {
        return this.arenaEffectSignLine;
    }
}
