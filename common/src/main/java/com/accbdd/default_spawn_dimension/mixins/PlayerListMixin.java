package com.accbdd.default_spawn_dimension.mixins;

import com.accbdd.default_spawn_dimension.DimensionConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

    @Inject(method = "load", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void spawnPlatformOnFirstLogin(ServerPlayer player, CallbackInfoReturnable<CompoundTag> cir, CompoundTag compoundTag, CompoundTag compoundTag2) {
        if (compoundTag2 == null && DimensionConfig.generatePlatform()) {
            ServerLevel level = player.serverLevel();
            for (int i = 4; i > 0; i--) {
                BlockState state = level.getBlockState(player.blockPosition().below(i));
                if (state.entityCanStandOn(player.level(), player.blockPosition(), player)) {
                    return;
                }
            }
            //no standable block 4 blocks below, build 3x3 platform
            BlockPos platformCenter = player.blockPosition().below();
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos platformPos = platformCenter.offset(x, 0, z);
                    if (level.getBlockState(platformPos).isAir()) {
                        level.setBlock(platformPos, DimensionConfig.getPlatformBlockState(), 3);
                    }
                }
            }
        }
    }
}
