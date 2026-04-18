package com.accbdd.default_spawn_dimension.mixins;

import com.accbdd.default_spawn_dimension.DimensionConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

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

    /**
     * Sets a player's spawn to specific coordinates if the config option is true
     */
    @Inject(method = "fudgeSpawnLocation", at = @At(value = "HEAD"), cancellable = true)
    private void setSpecificCoordinates(ServerLevel level, CallbackInfo ci) {
        if (DimensionConfig.useSpecificCoordinates()) {
            Vec3 pos = DimensionConfig.getStartingPos();
            ((Entity)(Object)this).setPos(pos.x(), pos.y(), pos.z());
            ci.cancel();
        }
    }
}
