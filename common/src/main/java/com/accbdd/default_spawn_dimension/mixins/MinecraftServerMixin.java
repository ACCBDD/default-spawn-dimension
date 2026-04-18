package com.accbdd.default_spawn_dimension.mixins;

import com.accbdd.default_spawn_dimension.DimensionConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    /**
     * redirects default dimension to load when starting up server
     */
    @Redirect(method = "prepareLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"))
    private ServerLevel redirectLevelLoad(MinecraftServer instance) {
        return instance.getLevel(DimensionConfig.getStartingDim());
    }
}
