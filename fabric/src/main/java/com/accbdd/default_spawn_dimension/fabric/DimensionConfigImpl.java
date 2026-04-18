package com.accbdd.default_spawn_dimension.fabric;

import com.accbdd.default_spawn_dimension.DefaultSpawnDimension;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.nio.file.Files;
import java.nio.file.Path;

public class DimensionConfigImpl {
    private static String cachedDim = "minecraft:overworld";
    private static Boolean specificCoordinates = false;
    private static double spawnX = 0;
    private static double spawnY = 0;
    private static double spawnZ = 0;
    private static boolean generatePlatform = false;
    private static BlockState platformBlockState = Blocks.STONE.defaultBlockState();

    public static void loadConfig() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("default_spawn_dimension.json");
        try {
            if (Files.exists(path)) {
                JsonObject json = new Gson().fromJson(Files.newBufferedReader(path), JsonObject.class);
                cachedDim = json.get("startingDimension").getAsString();
                specificCoordinates = json.get("specificCoordinates").getAsBoolean();
                spawnX = json.get("spawnX").getAsDouble();
                spawnY = json.get("spawnY").getAsDouble();
                spawnZ = json.get("spawnZ").getAsDouble();
                generatePlatform = json.get("generatePlatform").getAsBoolean();
                platformBlockState = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), json.get("platformBlockState").getAsString(), false).blockState();
            } else {
                generateDefaults(path);
            }
        } catch (Exception exception) {
            DefaultSpawnDimension.LOGGER.error("malformed config, writing default values");
            generateDefaults(path);
        }
    }

    private static void generateDefaults(Path path) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("startingDimension", "minecraft:overworld");
            json.addProperty("specificCoordinates", false);
            json.addProperty("spawnX", 0.0D);
            json.addProperty("spawnY", 0.0D);
            json.addProperty("spawnZ", 0.0D);
            json.addProperty("generatePlatform", false);
            json.addProperty("platformBlockState", "minecraft:stone");
            Files.writeString(path, new GsonBuilder().setPrettyPrinting().create().toJson(json));
        } catch (Exception exception) {
            DefaultSpawnDimension.LOGGER.error("error writing defaults: " + exception);
        }
    }

    public static ResourceKey<Level> getStartingDim() {
        loadConfig();
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(cachedDim));
    }

    public static boolean useSpecificCoordinates() {
        loadConfig();
        return specificCoordinates;
    }

    public static Vec3 getStartingPos() {
        loadConfig();
        return new Vec3(spawnX, spawnY, spawnZ);
    }

    public static BlockState getPlatformBlockState() {
        loadConfig();
        return platformBlockState;
    }

    public static boolean generatePlatform() {
        loadConfig();
        return generatePlatform;
    }
}
