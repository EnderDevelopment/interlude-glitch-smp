package com.enderdevelopment.riftglitch;

import net.fabricmc.api.ModInitializer;

public final class RiftGlitchMod implements ModInitializer {
    public static final String MOD_ID = "riftglitch";

    @Override
    public void onInitialize() {
        GlitchCommands.register();
        GlitchEvents.register();
    }
}
