package com.qualityplus.assistant.hologram;

import com.google.common.collect.Lists;
import com.qualityplus.assistant.util.StringUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to create easily holograms
 *
 * TODO separate instance of hologram with an util hologram class
 */
public final class TheHologram {
    private List<ArmorStand> armorStands;
    private Location location;
    @Getter
    private List<String> txt;

    /**
     * Default constructor
     *
     * @param txt      hologram text lines
     * @param location {@link Location} hologram location
     */
    private TheHologram(final List<String> txt, final Location location) {
        this.txt = txt;
        this.location = location;
        this.armorStands = createArmorStands();
    }

    /**
     * Creates a new hologram
     *
     * @param txt      hologram text lines
     * @param location {@link Location} hologram location
     * @return {@link TheHologram}
     */
    public static TheHologram create(final List<String> txt, final Location location) {
        return new TheHologram(txt, location);
    }

    /**
     * Move hologram to another place
     *
     * @param location {@link Location}
     */
    public void move(final Location location) {
        remove();

        this.location = location;

        this.armorStands = createArmorStands();
    }

    /**
     * Retrieve hologram with updated lines
     *
     * @param txt hologram text lines
     * @return {@link TheHologram}
     */
    public TheHologram rename(final List<String> txt) {
        remove();

        this.txt = txt;
        this.armorStands = createArmorStands();

        return this;
    }

    /**
     * Removes an hologram
     */
    public void remove() {
        this.armorStands.stream()
                .filter(Objects::nonNull)
                .forEach(ArmorStand::remove);

        this.armorStands.clear();
    }


    private List<ArmorStand> createArmorStands() {
        final List<ArmorStand> armorStands = new ArrayList<>();

        final double amount = 0.25;

        final double initial = this.txt.size() * amount;

        final Location initialLocation = this.location.clone().add(0, initial, 0);

        int size = 0;

        for (final String line : Lists.reverse(this.txt)) {
            final double newY = initial - (amount * size);

            final ArmorStand armorStand = this.location.getWorld()
                    .spawn(initialLocation.clone().subtract(new Vector(0, newY, 0)), ArmorStand.class);

            armorStand.setArms(true);
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setInvulnerable(true);
            armorStand.setGravity(false);
            armorStand.setBasePlate(false);
            armorStand.setCollidable(false);
            armorStand.setCustomNameVisible(true);

            final String colored = StringUtils.color(line);

            armorStand.setCustomName(colored);

            armorStands.add(armorStand);

            size++;
        }

        return armorStands;
    }
}
