package com.qualityplus.assistant.util.message;

import eu.okaeri.configs.OkaeriConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Multiple spigot message
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class MultipleSpecialMessage extends OkaeriConfig {
    private List<SpecialMessage> specialMessages;
}
