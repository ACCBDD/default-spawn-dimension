package com.accbdd.default_spawn_dimension.fabric;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.nio.file.Files;
import java.nio.file.Path;

public class DimensionConfigImpl {
    private static String cachedDim = "minecraft:overworld";

    public static ResourceKey<Level> getStartingDim() {
        loadConfig();
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(cachedDim));
    }

    public static void loadConfig() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("default_spawn_dimension.json");
        try {
            if (Files.exists(path)) {
                JsonObject json = new Gson().fromJson(Files.newBufferedReader(path), JsonObject.class);
                cachedDim = json.get("startingDimension").getAsString();
            } else {
                Files.writeString(path, "{\"startingDimension\":\"minecraft:overworld\"}");
            }
        } catch (Exception ignored) {}
    }
}
