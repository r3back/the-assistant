package com.qualityplus.assistant.api.nms;

import com.qualityplus.assistant.api.gui.FakeInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * NMS Interface
 */
public interface NMS {
    /**
     *
     * @param player {@link Player} player to send packet
     * @param name   new player name
     */
    public void addPlayer(final Player player, final String name);

    /**
     * Set block age
     *
     * @param block {@link Block} block
     * @param age   block age
     */
    public void setBlockAge(final Block block, final int age);

    /**
     * Retrieves block age
     *
     * @param block {@link Block} block
     * @return block age
     */
    public int getAge(final Block block);

    /**
     * Retrieves max block age
     * @param block {@link Block} block
     * @return max block age
     */
    public int getMaxAge(final Block block);

    /**
     * Damages a block with specific damage amount
     *
     * @param player List of {@link Player} to show damage animation
     * @param block  {@link Block} block
     * @param damage damage to be done
     */
    public void damageBlock(final List<Player> player, final Block block, final int damage);

    /**
     * Damages a block with specific damage amount for a single player
     * @param player {@link Player} to show damage animation
     * @param block  {@link Block} block
     * @param damage damage to be done
     */
    public void damageBlock(final Player player, final Block block, final int damage);

    /**
     * Send action bar to a player
     *
     * @param player  {@link Player}
     * @param message message to send in action bar
     */
    public void sendActionBar(final Player player, final String message);

    /**
     * Remove fake inventory from cache
     *
     * @param fakeInventory {@link FakeInventory}
     */
    public void removeFakePlayer(final FakeInventory fakeInventory);

    /**
     * Creates fake worbench
     *
     * @param player {@link Player}
     * @return {@link InventoryView}
     */
    public InventoryView createWorkBench(final Player player);

    /**
     * Retrieves a copy of a fake inventory
     *
     * @param player    {@link Player}
     * @param inventory {@link FakeInventory} inventory to be copied
     * @return {@link FakeInventory}
     */
    public FakeInventory getFakeInventory(final Player player, final FakeInventory inventory);

    /**
     * Return empty fake inventory with specific slots amount
     *
     * @param player   {@link Player}
     * @param maxSlots max slots in inventory
     * @return {@link FakeInventory}
     */
    public FakeInventory getFakeInventory(final Player player, final int maxSlots);

    /**
     * Change item durability
     *
     * @param itemStack  {@link ItemStack}
     * @param durability item durability
     * @return {@link ItemStack}
     */
    public ItemStack setDurability(final ItemStack itemStack, final short durability);

    /**
     * Return specific location of a dragon part
     *
     * @param enderDragon {@link EnderDragon} entity
     * @param dragonPart  {@link DragonPart} dragon part
     * @return {@link Location} of dragon part
     */
    public Location getDragonPart(final EnderDragon enderDragon, final DragonPart dragonPart);

    /**
     * Send boss bar to a player
     *
     * @param player  {@link Player}
     * @param bossBar message to be sent
     */
    public void sendBossBar(final Player player, final String bossBar);

    /**
     * Set ender eye status for a bloc
     * @param block       {@link Block}
     * @param setEnderEye true if ender eye should be on
     */
    public void setEnderEye(final Block block, final boolean setEnderEye);

    /**
     * Send title to a player
     *
     * @param player   {@link Player}
     * @param title    main title
     * @param subtitle sub title
     * @param fadeIn   fade in time
     * @param stay     stay time
     * @param fadeOut  fade out time
     */
    public void sendTitle(final Player player, final String title, final String subtitle,
                   final int fadeIn, final int stay, final int fadeOut);

    /**
     * Force a player to respawn
     *
     * @param player {@link Player}
     */
    public void respawnPlayer(final Player player);

    /**
     * Retrieves NMS ChunkGenerator
     *
     * @return {@link ChunkGenerator}
     */
    public ChunkGenerator getChunkGenerator();

    /**
     * Set player's max health
     *
     * @param player    {@link Player}
     * @param maxHealth New player's max health
     */
    public void setMaxHealth(final Player player, final int maxHealth);

    /**
     * Change world's game rule
     *
     * @param world {@link World}
     * @param key   Game Rule key
     * @param value Game Rule value
     * @param <T> Generic Game rule value type
     */
    public <T> void setGameRule(final World world, final String key, final T value);

    /**
     * Send particles for all players in a world
     *
     * @param world    {@link World}
     * @param particle Particle name
     * @param x        X Location
     * @param y        Y Location
     * @param z        Z Location
     * @param offsetX  Location X Offset
     * @param offsetY  Location Y Offset
     * @param offsetZ  Location Z Offset
     * @param data     Particle data
     * @param amount   Particle amount
     */
    public void sendParticles(final World world, final String particle,
                              final float x, final float y,
                              final float z, final float offsetX,
                              final float offsetY, final float offsetZ,
                              final float data, final int amount);

    /**
     *
     * @return if new nbt api resolver should be used
     */
    public default boolean isNewNBTAPIResolver() {
        return false;
    }

    /**
     * Ender Dragon Parts representation
     */
    @Getter
    @AllArgsConstructor
    public static enum DragonPart {
        /**
         * Represents head position
         */
        HEAD(3),
        /**
         * Represents body position
         */
        BODY(0);

        private final int nmsDistance;
    }
}
