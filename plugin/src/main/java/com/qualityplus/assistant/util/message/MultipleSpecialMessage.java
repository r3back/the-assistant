package com.qualityplus.assistant.util.message;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Multiple spigot message
 */
@NoArgsConstructor
@AllArgsConstructor
public final class MultipleSpecialMessage extends OkaeriConfig {
    public List<SpecialMessage> specialMessages;
}
