package io.github.thatpreston.mermod.fabric.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.architectury.platform.Platform;
import io.github.thatpreston.mermod.compat.MermodClothConfigScreen;

public class ModMenuAPI implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(Platform.isModLoaded("cloth-config")) {
            return MermodClothConfigScreen::build;
        }
        return null;
    }
}