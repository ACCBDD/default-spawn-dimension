package com.accbdd.default_spawn_dimension;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DimensionConfig {

    @ExpectPlatform
    public static ResourceKey<Level> getStartingDim() {
        throw new AssertionError("Expected a way to read a config!");
    }

    @ExpectPlatform
    public static Vec3 getStartingPos() {
        throw new AssertionError("Expected a way to read a config!");
    }

    @ExpectPlatform
    public static boolean useSpecificCoordinates() {
        throw new AssertionError("Expected a way to read a config!");
    }

    @ExpectPlatform
    public static boolean generatePlatform() {
        throw new AssertionError("Expected a way to read a config!");
    }

    @ExpectPlatform
    public static BlockState getPlatformBlockState() {
        throw new AssertionError("Expected a way to read a config!");
    }
}
