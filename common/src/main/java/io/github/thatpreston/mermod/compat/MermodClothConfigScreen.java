package io.github.thatpreston.mermod.compat;

import io.github.thatpreston.mermod.config.MermodConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MermodClothConfigScreen {
    public static Screen build(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent);
        ConfigCategory category = builder.getOrCreateCategory(Component.empty());
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        category.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.mermod.nightVisionFlashingFix"), MermodConfig.getNightVisionFlashingFix())
                .setDefaultValue(true).setSaveConsumer(MermodConfig::setNightVisionFlashingFix).build());
        category.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.mermod.replaceSwimAnimation"), MermodConfig.getReplaceSwimAnimation())
                .setDefaultValue(true).setSaveConsumer(MermodConfig::setReplaceSwimAnimation).build());
        return builder.build();
    }
}