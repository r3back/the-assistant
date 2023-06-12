package com.qualityplus.assistant.util.sound;

import com.cryptomorin.xseries.XSound;
import com.qualityplus.assistant.api.config.ConfigSound;
import com.qualityplus.assistant.util.console.ConsoleUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public final class SoundUtils {
    public static void playSound(final Player player, final XSound xsound, final float volume, final float pitch) {
        try {
            player.playSound(player.getLocation(), xsound.parseSound(), volume, pitch);
        } catch (final Exception e) {
            ConsoleUtils.msg("Invalid Sound! Name: " + getSound(xsound));
        }
    }

    public static void playSound(final Player player, final ConfigSound configSound){
        if (!configSound.isEnabled()) {
            return;
        }
        playSound(player, configSound.getSound(), configSound.getVolume(), configSound.getPitch());
    }

    public static void playSound(final Player player, final XSound xsound){
        playSound(player, xsound, 0.2f, 1f);
    }


    public static void playSound(final Player player, final String xsound,
                                 final float volume, final float pitch){
        playSound(player, byName(xsound), volume, pitch);
    }

    public static void playSound(final Player player, final String xsound){
        playSound(player, byName(xsound), 0.2f, 1f);
    }


    private static XSound byName(final String name){
        try {
            return XSound.valueOf(name);
        } catch (final Exception e) {
            return null;
        }
    }

    private static String getSound(final XSound sound){
        try {
            return sound.toString();
        } catch (final Exception e) {
            return "[Unrecognized Sound]";
        }
    }
}
