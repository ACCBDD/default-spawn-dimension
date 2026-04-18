package com.accbdd.default_spawn_dimension.forge;

import com.accbdd.default_spawn_dimension.DefaultSpawnDimension;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public class DimensionConfigImpl {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<String> DIM_ID;
    public static final ForgeConfigSpec.BooleanValue SPECIFIC_COORDINATES;
    public static final ForgeConfigSpec.ConfigValue<Double> SPAWN_X;
    public static final ForgeConfigSpec.ConfigValue<Double> SPAWN_Y;
    public static final ForgeConfigSpec.ConfigValue<Double> SPAWN_Z;
    public static final ForgeConfigSpec.BooleanValue GENERATE_PLATFORM;
    public static final ForgeConfigSpec.ConfigValue<String> PLATFORM_BLOCK_STATE;
    public static final ForgeConfigSpec SPEC;

    static {
        DIM_ID = BUILDER.define("defaultDimension", "minecraft:overworld");
        BUILDER.comment("Whether or not to use specific spawn coordinates in the dimension");
        SPECIFIC_COORDINATES = BUILDER.define("specificCoordinates", false);
        BUILDER.comment("The spawn coordinates - has no effect if specificCoordinates is false");
        SPAWN_X = BUILDER.define("spawnX", 0.0D);
        SPAWN_Y = BUILDER.define("spawnY", 0.0D);
        SPAWN_Z = BUILDER.define("spawnZ", 0.0D);
        BUILDER.comment("Whether or not to generate a platform under a spawning player if they're in the air");
        GENERATE_PLATFORM = BUILDER.define("generatePlatform", false);
        BUILDER.comment("The blockstate the platform is made out of - has no effect if generatePlatform is false");
        BUILDER.comment("e.g. minecraft:oak_leaves[persistent=true]");
        PLATFORM_BLOCK_STATE = BUILDER.define("platformBlockState", "minecraft:stone");
        SPEC = BUILDER.build();
    }

    public static ResourceKey<Level> getStartingDim() {
        return ResourceKey.create(Registries.DIMENSION, Objects.requireNonNull(ResourceLocation.tryParse(DIM_ID.get())));
    }

    public static boolean useSpecificCoordinates() {
        return SPECIFIC_COORDINATES.get();
    }

    public static Vec3 getStartingPos() {
        return new Vec3(SPAWN_X.get(), SPAWN_Y.get(), SPAWN_Z.get());
    }

    public static BlockState getPlatformBlockState() {
        String input = PLATFORM_BLOCK_STATE.get();
        try {
            return BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), input, false).blockState();
        } catch (Exception exception) {
            DefaultSpawnDimension.LOGGER.error("invalid platform blockstate value, correcting to default stone");
            PLATFORM_BLOCK_STATE.set("minecraft:stone");
        }
        return getPlatformBlockState();
    }

    public static boolean generatePlatform() {
        return GENERATE_PLATFORM.get();
    }
}
