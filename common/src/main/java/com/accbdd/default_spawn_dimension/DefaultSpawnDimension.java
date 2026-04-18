package com.accbdd.default_spawn_dimension;

import com.mojang.logging.LogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;

public final class DefaultSpawnDimension {
    public static final String MOD_ID = "default_spawn_dimension";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        // Write common init code here.
    }

    public static void generatePlatform(ServerLevel level, BlockPos pos) {
        throw new AssertionError("Expected a way to generate a platform!");
    }
}
