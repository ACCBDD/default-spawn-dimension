package com.accbdd.default_spawn_dimension.forge;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public class DimensionConfigImpl {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<String> DIM_ID;
    public static final ForgeConfigSpec SPEC;

    static {
        DIM_ID = BUILDER.define("defaultDimension", "minecraft:overworld");
        SPEC = BUILDER.build();
    }

    public static ResourceKey<Level> getStartingDim() {
        return ResourceKey.create(Registries.DIMENSION, Objects.requireNonNull(ResourceLocation.tryParse(DIM_ID.get())));
    }
}
