package io.github.hadron13.petrochem.mixin;


import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasinBlockEntity.class)
public class BasinBlockEntityMixin {


    @Shadow protected IFluidHandler fluidCapability;

    @Shadow
    private boolean contentsChanged;

    @Shadow
    public SmartFluidTankBehaviour inputTank;

    @Inject(method = "lazyTick", at = @At("HEAD"), remap = false)
    public void petrochem$lazyTick(CallbackInfo ci){

        IFluidHandler fluids  = this.fluidCapability;
        for(int i = 0; i < fluids.getTanks(); i++){
            FluidStack fluidStack = fluids.getFluidInTank(i);
            if(!fluidStack.getFluidType().isLighterThanAir())
                continue;

            FluidStack to_drain = fluidStack.copyWithAmount(Mth.ceil(fluidStack.getAmount() * 0.03));

            fluids.drain(to_drain, IFluidHandler.FluidAction.EXECUTE);

            contentsChanged = true;
        }

    }




}
