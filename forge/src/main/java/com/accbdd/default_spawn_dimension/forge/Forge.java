package com.accbdd.default_spawn_dimension.forge;

import com.accbdd.default_spawn_dimension.DefaultSpawnDimension;
import com.accbdd.default_spawn_dimension.DimensionConfig;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DefaultSpawnDimension.MOD_ID)
public final class Forge {
    public Forge(FMLJavaModLoadingContext context) {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(DefaultSpawnDimension.MOD_ID, context.getModEventBus());

        // Run our common setup.
        DefaultSpawnDimension.init();
        context.registerConfig(ModConfig.Type.SERVER, DimensionConfigImpl.SPEC);
    }
}
