package com.qualityplus.assistant.api.commands.details;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command details
 */
@Getter
@NoArgsConstructor
public class CommandDetails extends OkaeriConfig {
    protected List<String> aliases;
    protected String description;
    protected String permission;
    protected String syntax;
    protected boolean onlyForPlayers;
    protected long cooldownInSeconds;
    @Exclude
    protected CooldownProvider<CommandSender> cooldownProvider;
    protected boolean enabled;
    protected String labelProvider;

    /**
     *
     * @param aliases           command aliases
     * @param description       command description
     * @param syntax            command syntax
     * @param permission        command permission
     * @param onlyForPlayers    command is only for players
     * @param cooldownInSeconds command cooldown
     * @param enabled           command enabled
     * @param labelProvider     command label provider
     */
    @Builder
    public CommandDetails(final List<String> aliases, final String description, final String syntax, final String permission,
                          final boolean onlyForPlayers, final long cooldownInSeconds, final boolean enabled,
                          final String labelProvider) {
        this.aliases = aliases;
        this.description = description;
        this.syntax = syntax;
        this.permission = permission;
        this.onlyForPlayers = onlyForPlayers;
        this.cooldownInSeconds = cooldownInSeconds;
        this.enabled = enabled;
        this.labelProvider = labelProvider;
    }

    /**
     *
     * @return CooldownProvider of {@link CommandSender}
     */
    public CooldownProvider<CommandSender> getCooldownProvider() {
        return this.cooldownProvider == null ? CooldownProvider.newInstance(Duration.ofSeconds(this.cooldownInSeconds)) : this.cooldownProvider;
    }

    /**
     *
     * @param <T> Generic Type
     */
    public static class CooldownProvider<T> {
        protected final Map<T, Duration> cooldownTimes = new HashMap<>();
        protected final Duration duration;

        /**
         * Constructor with specific duration
         *
         * @param duration {@link Duration}
         */
        private CooldownProvider(final Duration duration) {
            this.duration = duration;
        }

        /**
         * Retrieve if is on cooldown for specific key
         *
         * @param t key
         * @return true if it"s on cooldown
         */
        public boolean isOnCooldown(final T t) {
            return this.cooldownTimes.containsKey(t) && this.cooldownTimes.get(t).toMillis() > System.currentTimeMillis();
        }

        /**
         * Retrieves remaining time cooldown for specific key
         *
         * @param t key
         * @return {@link Duration}
         */
        public Duration getRemainingTime(final T t) {
            if (!isOnCooldown(t)) {
                return Duration.ZERO;
            }

            return this.cooldownTimes.get(t).minusMillis(System.currentTimeMillis());
        }

        /**
         * Apply cooldown for specific key
         *
         * @param t key
         */
        public void applyCooldown(final T t) {
            this.cooldownTimes.put(t, this.duration.plusMillis(System.currentTimeMillis()));
        }

        /**
         * Creates a cooldown with specific duration
         *
         * @param duration {@link Duration}
         * @return instance of {@link CooldownProvider}
         * @param <T> Generic cooldown type
         */
        public static <T> CooldownProvider<T> newInstance(final Duration duration) {
            return new CooldownProvider<>(duration);
        }
    }

}
