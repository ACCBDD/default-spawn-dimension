package com.accbdd.default_spawn_dimension;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class DimensionConfig {

    @ExpectPlatform
    public static ResourceKey<Level> getStartingDim() {
        throw new AssertionError("Expected a way to read a config!");
    }
}
