package ceuadditions.mixins;


import ceuadditions.CommonProxy;
import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import gregtech.common.items.behaviors.TricorderBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TricorderBehavior.class)
public class mixinTriCorderScanner {

    @Inject(method = "onItemUseFirst", at = @At(value = "INVOKE_ASSIGN", target = "Lgregtech/common/items/behaviors/TricorderBehavior;drainEnergy(Lnet/minecraft/item/ItemStack;JZ)Z", shift = At.Shift.AFTER), remap = false)
    public void onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand, CallbackInfoReturnable<EnumActionResult> cir) {
        player.sendMessage(new TextComponentString("Pollution in chunk: \u00A7c" + CommonProxy.pollution.getPollution(new ChunkPosDimension(world.provider.getDimension(), world.getChunk(pos).x, world.getChunk(pos).z))));
    }
    
}
