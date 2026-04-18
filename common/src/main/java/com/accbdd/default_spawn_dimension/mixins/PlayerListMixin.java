package com.accbdd.default_spawn_dimension.mixins;

import com.accbdd.default_spawn_dimension.DimensionConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    /**
     * redirects default dimension to spawn in if player has corrupted or missing NBT
     */
    @Redirect(method = "placeNewPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"))
    private ResourceKey<Level> redirectDefaultDirection() {
        return DimensionConfig.getStartingDim();
    }

    /**
     * redirects fallback dimension for spawning if bed or lodestone is missing
     */
    @Redirect(method = "respawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"))
    private ServerLevel redirectFallbackDimension(MinecraftServer instance) {
        return instance.getLevel(DimensionConfig.getStartingDim());
    }

    /**
     * redirects default dimension for player's first time logging in, for spawn location purposes
     */
    @Redirect(method = "getPlayerForLogin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"))
    private ServerLevel redirectLoginDefaultDimension(MinecraftServer instance) {
        return instance.getLevel(DimensionConfig.getStartingDim());
    }
}
