package com.qualityplus.assistant.api.nms.tab;

import com.qualityplus.assistant.api.nms.tab.entry.TabElement;
import com.qualityplus.assistant.api.nms.tab.entry.TabEntry;
import com.qualityplus.assistant.api.team.TeamInfo;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Tab Adapter
 */
public abstract class TabAdapter {
    public static final Map<String, TeamInfo> PLAYER_COLORS = new HashMap<>();
    private static final Integer ROWS = 2;
    protected static Unsafe unsafe;

    static {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup the profiles of the tab adapter
     *
     * @param player {@link Player}
     * @return {@link TabAdapter}
     */
    public TabAdapter setupProfiles(final Player player) {

        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < ROWS; x++) {
                final int index = y * ROWS + x;
                final String text = "§0§" + x + (y > 9
                        ? "§" + String.valueOf(y).toCharArray()[0] + "§" + String.valueOf(y).toCharArray()[1]
                        : "§0§" + String.valueOf(y).toCharArray()[0]
                );

                this.createProfiles(index, text, player);
            }
        }

        return this;
    }

    /**
     * Handle an element being send to a player
     *
     * @param player  the player
     * @param element the element to send
     * @return {@link TabAdapter}
     */
    public TabAdapter handleElement(final Player player, final TabElement element) {

        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < ROWS; x++) {
                final TabEntry entry = element.getEntry(x, y);
                final int index = y * ROWS + x;

                this.sendEntryData(player, index, entry.getPing(), entry.getText());

                if (entry.getSkinData() != null && entry.getSkinData().length > 1) {
                    this.updateSkin(entry.getSkinData(), index, player);
                }
            }
        }


        return this;
    }

    /**
     * Split the text to display on the tablist
     *
     * @param text the text to split
     * @return the split text
     */
    public String[] splitText(final String text) {
        if (text.length() < 17) {
            return new String[]{text, ""};
        } else {
            final String left = text.substring(0, 16);
            final String right = text.substring(16);

            return left.endsWith("§")
                    ? new String[]{left.substring(0, left.toCharArray().length - 1), StringUtils.left(ChatColor.getLastColors(left) + "§" + right, 16)}
                    : new String[]{left, StringUtils.left(ChatColor.getLastColors(left) + right, 16)};
        }
    }

    /**
     * Setup the scoreboard for the player
     *
     * @param player the player to setup the scoreboard for
     * @param text   the text to display
     * @param name   the name of the team
     */
    public void setupScoreboard(final Player player, final String text, final String name) {
        final String[] splitText = this.splitText(text);

        final Scoreboard scoreboard = player.getScoreboard() == null
                ? Bukkit.getScoreboardManager().getNewScoreboard()
                : player.getScoreboard();

        final Team team = scoreboard.getTeam(name) == null
                ? scoreboard.registerNewTeam(name)
                : scoreboard.getTeam(name);

        if (!team.hasEntry(name)) {
            team.addEntry(name);
        }

        team.setPrefix(splitText[0]);
        team.setSuffix(splitText[1]);

        player.setScoreboard(scoreboard);
    }

    /**
     * Update the skin on the tablist for a player
     *
     * @param skinData the data of the new skin
     * @param index    the index of the profile
     * @param player   the player to update the skin for
     */
    public abstract void updateSkin(String[] skinData, int index, Player player);

    /**
     * Check if the player should be able to see the fourth row
     *
     * @param player the player
     * @return whether they should be able to see the fourth row
     */
    public abstract int getMaxElements(Player player);

    /**
     * Create a new game profile
     *
     * @param index  the index of the profile
     * @param text   the text to display
     * @param player the player to make the profiles for
     */
    public abstract void createProfiles(final int index, final String text, final Player player);

    /**
     * Send the header and footer to a player
     *
     * @param player the player to send the header and footer to
     * @param header the header to send
     * @param footer the footer to send
     * @return the current adapter instance
     */
    public abstract TabAdapter sendHeaderFooter(final Player player, final String header, final String footer);

    /**
     * Send an entry's data to a player
     *
     * @param player the player
     * @param axis   the axis of the entry
     * @param ping   the ping to display on the entry's position
     * @param text   the text to display on the entry's position
     * @return the current adapter instance
     */
    public abstract TabAdapter sendEntryData(final Player player, final int axis, final int ping, final String text);

    /**
     * Set fake ping to player
     *
     * @param play {@link Player}
     */
    public void setFakePing(final Player play) {

    }

    /**
     * Add fake players to the player's tablist
     *
     * @param player the player to send the fake players to
     * @return the current adapter instance
     */
    public abstract TabAdapter addFakePlayers(final Player player);

    /**
     * Hide all real players from the tab
     *
     * @param player the player
     * @return the current adapter instance
     */
    public abstract TabAdapter hideRealPlayers(final Player player);

    /**
     * Hide a real player from the tab
     *
     * @param player the player to hide the player from
     * @param target the player to hide
     * @return the current adapter instance
     */
    public abstract TabAdapter hidePlayer(final Player player, final Player target);

    /**
     * Show all real players on the tab
     *
     * @param player the player
     * @return the current adapter instance
     */
    public abstract TabAdapter showRealPlayers(final Player player);

    /**
     * Show a real player to a player
     *
     * @param player the player
     * @param target the player to show to the other player
     * @return the current adapter instance
     */
    public abstract TabAdapter showPlayer(final Player player, final Player target);

}
