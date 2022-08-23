package com.qualityplus.bank.base.commands.provider;

import com.qualityplus.bank.api.box.Box;
import com.qualityplus.assistant.api.commands.LabelProvider;
import eu.okaeri.commons.bukkit.time.MinecraftTimeEquivalent;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.bukkit.annotation.Delayed;
import eu.okaeri.platform.core.annotation.Component;

@Component
public final class BankCommandProvider {
    @Delayed(time = MinecraftTimeEquivalent.SECOND / 20, async = true)
    private void configureProvider(@Inject Box box){
        LabelProvider.builder()
                .id("thebank")
                .label("thebank")
                .plugin(box.plugin())
                .useHelpMessage(box.files().messages().pluginMessages.useHelp)
                .unknownCommandMessage(box.files().messages().pluginMessages.unknownCommand)
                .onlyForPlayersMessage(box.files().messages().pluginMessages.mustBeAPlayer)
                .noPermissionMessage(box.files().messages().pluginMessages.noPermission)
                .build()
                .register();
    }
}
