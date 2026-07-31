package io.github.hadron13.petrochem.mixin;


import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import io.github.hadron13.petrochem.blocks.medium_engine.MediumEngineBlock;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoweredShaftBlockEntity.class)
public class PoweredShaftBlockEntityMixin {


    @Shadow public Block capacityKey;

    @Shadow public int movementDirection;

    @Inject(
        method = "getGeneratedSpeed",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    public void petrochem$getGeneratedSpeed(CallbackInfoReturnable<Float> cir) {
        if(capacityKey instanceof MediumEngineBlock){
            cir.setReturnValue((float)movementDirection);
        }
    }

    @Inject(
            method = "calculateAddedStressCapacity",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void petrochem$calculateAddedStressCapacity(CallbackInfoReturnable<Float> cir) {
        if(capacityKey instanceof MediumEngineBlock){
            if(movementDirection == 0)
                cir.setReturnValue(0.0f);
            else {

                float capacity = (float)BlockStressValues.getCapacity(capacityKey);
                float multiplier = (capacity == 131072.0f)?  (0.75f / Mth.abs(movementDirection)): (64f / Mth.abs(movementDirection));

                cir.setReturnValue(capacity * multiplier );
            }
        }
    }



}
