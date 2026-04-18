package com.accbdd.default_spawn_dimension.mixins;

import com.accbdd.default_spawn_dimension.DimensionConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Shadow private ResourceKey<Level> respawnDimension;

    /**
     * Sets the default respawn dimension on construction of a new ServerPlayer
     */
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"))
    private ResourceKey onInit() {
        return DimensionConfig.getStartingDim();
    }

    /**
     * Modifies the default when setRespawnPosition is called and provided a null position
     */
    @Redirect(method = "setRespawnPosition", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"))
    public ResourceKey<Level> onSetRespawnPosition() {
        return DimensionConfig.getStartingDim();
    }
}
