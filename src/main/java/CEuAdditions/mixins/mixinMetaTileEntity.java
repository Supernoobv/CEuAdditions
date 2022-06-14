package CEuAdditions.mixins;

import CEuAdditions.CEuAdditions;
import CEuAdditions.api.CEuAdLogger;
import CEuAdditions.api.ConfigHandler;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MetaTileEntity.class)
public class mixinMetaTileEntity {

    private CEuAdditions ceuadditions;
    private CEuAdditions additions;



    @Shadow protected boolean shouldUpdate(MTETrait trait) {
        CEuAdLogger.logger.error("Failed to shadow shouldUpdate!()");
        return false;
    }

    @Shadow public World getWorld() {
        CEuAdLogger.logger.error("Failed to shadow getWorld()!");
        return null;
    }

    @Shadow public String getMetaName() {
        CEuAdLogger.logger.error("Failed to shadow getMetaName()!");
        return "fail";
    }

    @Shadow public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        CEuAdLogger.logger.error("Failed to shadow getCapability()!");
        return null;
    }

    @Shadow public BlockPos getPos() {
        CEuAdLogger.logger.error("Failed to shadow getWorld()!");
        return null;
    }

    @Shadow public long getOffsetTimer() {
        CEuAdLogger.logger.error("Failed to shadow getWorld()!");
        return 0;
    }

    @Inject(at = @At("TAIL"), method = "update", remap = false)
    public void update(CallbackInfo ci) {
        IWorkable workable = getCapability(GregtechTileCapabilities.CAPABILITY_WORKABLE, null);
        if (!(workable == null)) {
            if (CEuAdditions.polutil.filterList.containsKey(getMetaName()) && workable.isActive()) {
                additions.proxy.pollution.addPollution(getWorld().getChunk(getPos()).getPos(), CEuAdditions.polutil.filterList.get(getMetaName()));
            }
        }


    }

    @Inject(at = @At("TAIL"), method = "doExplosion", remap = false)
    public void doExplosion(float explosionPower, CallbackInfo ci) {
        CEuAdLogger.logger.info("Explosion happened!");
        int explosionPowerint = (int) explosionPower;
        additions.proxy.pollution.addPollution(getWorld().getChunk(getPos()).getPos(), (ConfigHandler.polOptions.ExplosionEmissionAmount * explosionPowerint));
    }

}
