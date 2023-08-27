package com.qualityplus.assistant.util;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.message.MultipleSpecialMessage;
import com.qualityplus.assistant.util.message.SpecialMessage;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class for Strings
 */
@UtilityClass
public final class StringUtils {
    private static final Pattern PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    /**
     * Removes Color from a string
     *
     * @param string string to remove color
     * @return String without color
     */
    public String unColor(final String string) {
        return ChatColor.stripColor(string);
    }

    /**
     * Colorize a string
     *
     * @param string string to be colored
     * @return Colored string
     */
    public String color(final String string) {
        final String hexMessage = hexColor(string);

        return ChatColor.translateAlternateColorCodes('&', hexMessage);
    }

    /**
     * Colorize all lines of a list
     *
     * @param strings List of {@link String}
     * @return List of {@link String} colored
     */
    public List<String> color(final List<String> strings) {
        return strings.stream()
                .map(StringUtils::color)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a message with hex colors parsed
     *
     * @param messageParam message to be parsed
     * @return message with hex colors
     */
    public String hexColor(final String messageParam) {
        String message = messageParam;
        Matcher matcher = PATTERN.matcher(message);

        while (matcher.find()) {
            final String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, "" + net.md_5.bungee.api.ChatColor.of(color));
            matcher = PATTERN.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }


    /**
     * Retrieves a list of string with parsed placeholders
     *
     * @param lines        List of {@link String} to be parsed
     * @param placeholders List of {@link IPlaceholder} to parse
     * @return List of {@link String} with placeholders parsed
     */
    public List<String> processMulti(final List<String> lines, final List<IPlaceholder> placeholders) {
        if (placeholders.stream().anyMatch(IPlaceholder::isListPlaceholder)) {
            //Se Hace Una lista con los placeholders que no son lista
            final List<String> finalLore = processMulti(lines, placeholders.stream()
                    .filter(p -> !p.isListPlaceholder())
                    .collect(Collectors.toList()));

            //Se Agregan todos los placeholders que sean lista
            placeholders.stream()
                    .filter(IPlaceholder::isListPlaceholder)
                    .collect(Collectors.toList())
                    .forEach(p -> p.processList(finalLore));

            //Se parsean todas las lineas incluidas las nuevas
            return finalLore.stream().map(s -> processMulti(s, placeholders)).collect(Collectors.toList());
        } else {
            return lines.stream().map(s -> processMulti(s, placeholders)).collect(Collectors.toList());
        }
    }

    /**
     * Retrieves a string with placeholders parsed
     *
     * @param line         string to be parsed
     * @param placeholders List of {@link IPlaceholder} to parse
     * @return string with placeholders parsed
     */
    public String processMulti(final String line, final List<IPlaceholder> placeholders) {
        String processedLine = line;
        for (final IPlaceholder placeholder : placeholders.stream()
                .filter(p -> !p.isListPlaceholder())
                .collect(Collectors.toList())) {
            processedLine = placeholder.process(processedLine);
        }
        return color(processedLine);
    }

    /**
     * Retrieves a Special message with placeholders parsed
     *
     * @param multipleSpecialMessage {@link MultipleSpecialMessage}
     * @param placeholders           List of {@link IPlaceholder}
     * @return {@link TextComponent} with placeholders parsed
     */
    public TextComponent getMessage(final MultipleSpecialMessage multipleSpecialMessage,
                                    final List<IPlaceholder> placeholders) {
        final List<TextComponent> textComponents = multipleSpecialMessage.getSpecialMessages().stream()
                .map(specialMessage -> getMessage(specialMessage, placeholders))
                .collect(Collectors.toList());

        final TextComponent textComponent = new TextComponent();

        textComponents.forEach(textComponent::addExtra);
        return textComponent;
    }

    /**
     * Retrieves a Special message with placeholders parsed
     *
     * @param specialMessage {@link SpecialMessage}
     * @param placeholders   List of {@link IPlaceholder}
     * @return {@link TextComponent} with placeholders parsed
     */
    public TextComponent getMessage(final SpecialMessage specialMessage, final List<IPlaceholder> placeholders) {
        final TextComponent component = new TextComponent();

        final List<String> messages = specialMessage.getMessage()
                .stream()
                .map(message -> processMulti(message, placeholders))
                .collect(Collectors.toList());

        final String action = processMulti(specialMessage.getAction(), placeholders);
        final String aboveMessage = processMulti(specialMessage.getAboveMessage(), placeholders);

        messages.stream()
                .map(message -> MessageBuilder.get(message, action, aboveMessage))
                .forEach(component::addExtra);

        return component;
    }

    /**
     * Spigot Chat Message Builder
     */
    public class MessageBuilder {
        /**
         *
         * @param message message
         * @param command message command
         * @param hover   text hover message
         * @return {@link TextComponent}
         */
        public static TextComponent get(final String message, final String command, final String hover) {
            final TextComponent textComponent = new TextComponent(StringUtils.color(message));

            if (command != null) {
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            }
            if (hover != null) {
                final BaseComponent[] hoverC = new ComponentBuilder(StringUtils.color(hover)).create();

                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverC));
            }
            return textComponent;
        }
    }

    /**
     *
     * @param str   string
     * @param count count
     * @return {@link String}
     */
    public String repeat(final String str, final int count) {
        final byte[] value = str.getBytes(StandardCharsets.UTF_8);

        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }
        if (count == 1) {
            return str;
        }
        final int len = value.length;
        if (len == 0 || count == 0) {
            return "";
        }
        if (Integer.MAX_VALUE / count < len) {
            throw new OutOfMemoryError("Required length exceeds implementation limit");
        }
        if (len == 1) {
            final byte[] single = new byte[count];
            Arrays.fill(single, value[0]);
            return new String(single, StandardCharsets.UTF_8);
        }
        final int limit = len * count;
        final byte[] multiple = new byte[limit];
        System.arraycopy(value, 0, multiple, 0, len);
        int copied = len;
        for (; copied < limit - copied; copied <<= 1) {
            System.arraycopy(multiple, 0, multiple, copied, copied);
        }
        System.arraycopy(multiple, 0, multiple, copied, limit - copied);
        return new String(multiple, StandardCharsets.UTF_8);
    }
}
