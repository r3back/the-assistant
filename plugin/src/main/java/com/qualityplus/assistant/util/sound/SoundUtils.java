package com.qualityplus.assistant.util.sound;

import com.cryptomorin.xseries.XSound;
import com.qualityplus.assistant.api.config.ConfigSound;
import com.qualityplus.assistant.util.console.ConsoleUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

/**
 * Utility class for sounds
 */
@UtilityClass
public final class SoundUtils {
    private static final String INVALID_SOUND_NAME_MESSAGE = "Invalid Sound! Name: %s";
    private static final String UNRECOGNIZED_SOUND_MESSAGE = "[Unrecognized Sound]";

    /**
     * Plays a sound given a XSound, volume and pitch
     *
     * @param player {@link Player}
     * @param xsound {@link XSound}
     * @param volume volume
     * @param pitch  pitch
     */
    public void playSound(final Player player, final XSound xsound, final float volume, final float pitch) {
        try {
            player.playSound(player.getLocation(), xsound.parseSound(), volume, pitch);
        } catch (final Exception e) {
            ConsoleUtils.msg(String.format(INVALID_SOUND_NAME_MESSAGE, getSound(xsound)));
        }
    }

    /**
     * Plays sound for a player given a config sound
     *
     * @param player      {@link Player}
     * @param configSound {@link ConfigSound}
     */
    public void playSound(final Player player, final ConfigSound configSound) {
        if (!configSound.isEnabled()) {
            return;
        }
        playSound(player, configSound.getSound(), configSound.getVolume(), configSound.getPitch());
    }

    /**
     * Play a sound given a XSound
     *
     * @param player {@link Player}
     * @param xsound {@link XSound}
     */
    public void playSound(final Player player, final XSound xsound) {
        playSound(player, xsound, 0.2f, 1f);
    }

    /**
     * Plays a sound given a XSound as string, volume and pitch
     *
     * @param player {@link Player}
     * @param xsound string xsound
     * @param volume volume
     * @param pitch  pitch
     */
    public void playSound(final Player player, final String xsound, final float volume, final float pitch) {
        playSound(player, byName(xsound), volume, pitch);
    }

    /**
     * Plays a sound given a XSound as string
     *
     * @param player {@link Player}
     * @param xsound string xsound
     */
    public void playSound(final Player player, final String xsound) {
        playSound(player, byName(xsound), 0.2f, 1f);
    }


    private XSound byName(final String name) {
        try {
            return XSound.valueOf(name);
        } catch (final Exception e) {
            return null;
        }
    }

    private String getSound(final XSound sound) {
        try {
            return sound.toString();
        } catch (final Exception e) {
            return UNRECOGNIZED_SOUND_MESSAGE;
        }
    }
}
