package com.qualityplus.assistant.util;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.message.MultipleSpecialMessage;
import com.qualityplus.assistant.util.message.SpecialMessage;
import lombok.experimental.UtilityClass;
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
import java.util.stream.IntStream;

@UtilityClass
public final class StringUtils {
    private final Pattern PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String unColor(final String string) {
        return ChatColor.stripColor(string);
    }

    public static String color(final String string) {
        final String hexMessage = hexColor(string);

        return ChatColor.translateAlternateColorCodes('&', hexMessage);
    }

    public static List<String> color(final List<String> strings) {
        return strings.stream()
                .map(StringUtils::color)
                .collect(Collectors.toList());
    }

    public static String hexColor(String message) {
        Matcher matcher = PATTERN.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, "" + net.md_5.bungee.api.ChatColor.of(color));
            matcher = PATTERN.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> intStream(final int from, final int to){
        return IntStream.range(from, to)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public static List<String> processMulti(final List<String> lines, final List<IPlaceholder> placeholders) {
        if(placeholders.stream().anyMatch(IPlaceholder::isListPlaceholder)){
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
        }else{
            return lines.stream().map(s -> processMulti(s, placeholders)).collect(Collectors.toList());
        }
    }

    public static String processMulti(final String line, final List<IPlaceholder> placeholders) {
        String processedLine = line;
        for (final IPlaceholder placeholder : placeholders.stream()
                .filter(p -> !p.isListPlaceholder())
                .collect(Collectors.toList())) {
            processedLine = placeholder.process(processedLine);
        }
        return color(processedLine);
    }

    public static TextComponent getMessage(final MultipleSpecialMessage multipleSpecialMessage,
                                           final List<IPlaceholder> placeholders) {
        final List<TextComponent> textComponents = multipleSpecialMessage.specialMessages.stream()
                .map(specialMessage -> getMessage(specialMessage, placeholders))
                .collect(Collectors.toList());
        /*for(SpecialMessage specialMessage : multipleSpecialMessage.specialMessages){
            List<String> messages = specialMessage.message.stream().map(message -> processMulti(message, placeholders))
                    .collect(Collectors.toList());
            String action = processMulti(specialMessage.action, placeholders);
            String aboveMessage = processMulti(specialMessage.aboveMessage, placeholders);
            messages.stream().map(message -> MessageBuilder.get(message, action, aboveMessage)).forEach(textComponents::add);
        }*/
        final TextComponent textComponent = new TextComponent();
        textComponents.forEach(textComponent::addExtra);
        return textComponent;
    }

    public static TextComponent getMessage(final SpecialMessage specialMessage, final List<IPlaceholder> placeholders) {
        final TextComponent component = new TextComponent();

        final List<String> messages = specialMessage.message
                .stream()
                .map(message -> processMulti(message, placeholders))
                .collect(Collectors.toList());

        final String action = processMulti(specialMessage.action, placeholders);
        final String aboveMessage = processMulti(specialMessage.aboveMessage, placeholders);

        messages.stream()
                .map(message -> MessageBuilder.get(message, action, aboveMessage))
                .forEach(component::addExtra);

        return component;
    }

    public static class MessageBuilder{
        public static TextComponent get(final String message, final String command, final String hover) {
            final TextComponent textComponent = new TextComponent(StringUtils.color(message));

            if (command != null) {
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            }
            if (hover != null) {
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtils.color(hover)).create()));
            }
            return textComponent;
        }
    }

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
