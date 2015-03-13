package com.PixelYeti.SkyWars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

public class Arena {

    public enum ArenaState {
        WAITING, COUNTDOWN, STARTED
    }

    private String id;
    private CuboidSelection bounds;
    private ArenaState state;

    private ArrayList<Spawn> spawns;
    private ArrayList<GameChest> chests;

    private ArrayList<Player> spectators;
    private ArrayList<Player> players;
    private ArrayList<BlockState> changedBlocks;

    protected Arena(String id) {
        this.id = id;

        this.bounds = new CuboidSelection(Bukkit.getServer().getWorld(
                SettingsManager.getArenas().<String> get(id + ".world")),
                Main.loadLocation(SettingsManager.getArenas()
                        .<ConfigurationSection> get(id + ".cornerA")),
                Main.loadLocation(SettingsManager.getArenas()
                        .<ConfigurationSection> get(id + ".cornerB")));

        this.state = ArenaState.WAITING;

        this.spawns = new ArrayList<Spawn>();
        if (SettingsManager.getArenas().contains(id + ".spawns")) {
            for (String spawnID : SettingsManager.getArenas()
                    .<ConfigurationSection> get(id + ".spawns").getKeys(false)) {
                spawns.add(new Spawn(Main.loadLocation(SettingsManager
                        .getArenas().<ConfigurationSection> get(
                                id + ".spawns." + spawnID))));
            }
        }

        this.chests = new ArrayList<GameChest>();
        if (SettingsManager.getArenas().contains(id + ".chests")) {
            for (String chestID : SettingsManager.getArenas()
                    .<ConfigurationSection> get(id + ".chests").getKeys(false)) {
                Location loc = Main.loadLocation(SettingsManager.getArenas()
                        .<ConfigurationSection> get(
                                id + ".chests." + chestID + ".location"));

                if (loc.getBlock() == null
                        || !(loc.getBlock().getState() instanceof Chest)) {
                    SettingsManager.getArenas().set(id + ".chests." + chestID,
                            null);
                }

                else {
                    chests.add(new GameChest((Chest) loc.getBlock().getState(),
                            SettingsManager.getArenas().<Integer> get(
                                    id + ".chests." + chestID + ".tier")));
                }
            }
        }

        this.players = new ArrayList<Player>();
        this.changedBlocks = new ArrayList<BlockState>();
    }

    public String getID() {
        return id;
    }

    public CuboidSelection getBounds() {
        return bounds;
    }

    public ArenaState getState() {
        return state;
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    public Player[] getSpectators() {
        return spectators.toArray(new Player[spectators.size()]);
    }

    public boolean hasPlayer(Player p) {
        return players.contains(p);
    }

    public boolean isSpectator(Player p) {
        return spectators.contains(p);
    }

    public void addPlayer(Player p) {
        if (state == ArenaState.STARTED) {
            p.sendMessage(ChatColor.RED + "This arena has already started.");
            return;
        }

        if (players.size() + 1 > spawns.size()) {
            p.sendMessage(ChatColor.RED + "This arena is full.");
            return;
        }

        boolean success = false;

        for (Spawn spawn : spawns) {
            if (!spawn.hasPlayer()) {
                spawn.setPlayer(p);
                p.teleport(spawn.getLocation());
                success = true;
                break;
            }
        }

        if (!success) {
            p.sendMessage(ChatColor.RED + "Could not find spawn.");
            return;
        }

        players.add(p);

        p.getInventory().clear();

        ItemStack kitSelector = new ItemStack(Material.WATCH, 1);
        ItemMeta meta = kitSelector.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Kit Selector");
        meta.setLore(Arrays.asList("Right click this to", "choose your kit"));
        kitSelector.setItemMeta(meta);
        p.getInventory().addItem(kitSelector);

        p.sendMessage(ChatColor.GREEN + "You have joined arena " + id + ".");

        if (players.size() >= spawns.size() && state == ArenaState.WAITING) {
            this.state = ArenaState.COUNTDOWN;
            new Countdown(this, 30, 20, 10, 5, 4, 3, 2, 1).runTaskTimer(
                    Main.getPlugin(), 0, 20L);
        }

    }

    public void addSpawn(Location loc) {
        spawns.add(new Spawn(loc));
    }

    public void addChest(Chest chest, int tier) {
        chests.add(new GameChest(chest, tier));
    }

    public void addBlockState(BlockState state) {
        if (!changedBlocks.contains(state)) {
            changedBlocks.add(state);
        }
    }

    public void regen(final List<BlockState> blocks, final boolean effect,
                      final long speed) {

        new BukkitRunnable() {
            int i = -1;

            public void run() {
                if (i != blocks.size() - 1) {
                    i++;
                    BlockState bs = changedBlocks.get(i);
                    bs.update(true, false);
                    if (effect)
                        bs.getBlock()
                                .getWorld()
                                .playEffect(bs.getLocation(),
                                        Effect.STEP_SOUND,
                                        bs.getBlock().getType());
                } else {

                    blocks.clear();
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getPlugin(), speed, speed);
    }

    private void rollback() {
        regen(changedBlocks, true, (long) 1);
    }

    public void removePlayer(Player p) {
        players.remove(p);

        for (Spawn spawn : spawns) {
            if (spawn.hasPlayer() && spawn.getPlayer().equals(p)) {
                spawn.setPlayer(null);
            }
        }

        p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation()); // TODO:
        // Temporary.

        if (players.size() <= 1) {
            if (players.size() == 1) {
                Bukkit.getServer()
                        .broadcastMessage(
                                players.get(0).getName() + " has won arena "
                                        + id + "!");
                players.remove(0);
                players.get(0).teleport(
                        Bukkit.getServer().getWorlds().get(0)
                                .getSpawnLocation()); // TODO: Temporary.
            }

            else {
                Bukkit.getServer().broadcastMessage(
                        "Arena " + id + " has ended.");
            }

            for (Entity e : Bukkit.getWorld(
                    this.getBounds().getWorld().getName()).getEntities()) {
                if (this.getBounds().contains(e.getLocation())
                        && e.getType() == EntityType.DROPPED_ITEM) {
                    e.remove();
                }
            }

            players.clear();
            rollback();
            state = ArenaState.WAITING;
        }
    }

    public void addSpectator(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);

        Arena a = ArenaManager.getInstance().getArena(p);
        for (Player player : a.getPlayers()) {
            player.hidePlayer(p);
        }

    }

    public int getMaxPlayers() {
        return spawns.size();
    }

    @SuppressWarnings("unused")
    public void start() {
        this.state = ArenaState.STARTED;
        boolean started = true;

        for (Player p : players) {
            p.setHealth(20.0D);
            p.setGameMode(GameMode.SURVIVAL);
        }

        for (Spawn spawn : spawns) {
            spawn.setPlayer(null);
        }

        for (GameChest chest : chests) {
            chest.fillChest();
        }

    }

    public Location getSpawn(Player p) {
        for (Spawn s : spawns) {
            if (s.getPlayer() == p) {
                return s.getLocation();
            }
        }
        return null;
    }
}
