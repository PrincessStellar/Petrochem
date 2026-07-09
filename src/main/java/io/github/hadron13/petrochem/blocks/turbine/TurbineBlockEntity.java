package io.github.hadron13.petrochem.blocks.turbine;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import io.github.hadron13.petrochem.PetrochemLang;
import io.github.hadron13.petrochem.blocks.electrolyzer.InternalEnergyStorage;
import io.github.hadron13.petrochem.blocks.small_engine.EngineFuelRecipe;
import io.github.hadron13.petrochem.blocks.small_engine.EngineSoundInstance;
import io.github.hadron13.petrochem.config.PetrochemConfig;
import io.github.hadron13.petrochem.register.PetrochemBlockEntities;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import io.github.hadron13.petrochem.register.PetrochemSoundEvents;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;
import java.util.Optional;

import static io.github.hadron13.petrochem.blocks.electrolyzer.ElectrolyzerBlock.HORIZONTAL_FACING;
import static io.github.hadron13.petrochem.blocks.turbine.TurbineBlock.FACING;

public class TurbineBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    @OnlyIn(Dist.CLIENT)
    public EngineSoundInstance soundInstance;
    public SmartFluidTankBehaviour tank;
    public InternalEnergyStorage energyStorage;
    public EngineFuelRecipe currentFuel = null;
    public LerpedFloat turbineSpeed = LerpedFloat.linear();
    float turbineAngle = 0;
    public float consumptionCounter = 0;



    public TurbineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        energyStorage = new InternalEnergyStorage(16384, 0, 16384);
        turbineSpeed.chase(0f, 1 / 64f, LerpedFloat.Chaser.EXP);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, 4000, true);
        tank.whenFluidUpdates(this::fluidUpdate);
        behaviours.add(tank);
    }

    public void fluidUpdate(){
        FluidStack fluid = tank.getPrimaryHandler().getFluidInTank(0);
        if(fluid.isEmpty()){
            currentFuel = null;
        }else{
            if(currentFuel == null){
                List<RecipeHolder<EngineFuelRecipe>> allFuels= level.getRecipeManager().getAllRecipesFor(PetrochemRecipeTypes.TURBINE_FUEL.getType());

                Optional<EngineFuelRecipe> matchingFuel =
                        allFuels.stream().map(RecipeHolder::value).filter(recipe -> recipe.match(fluid) ).findAny();
                if(matchingFuel.isEmpty())
                    return;
                currentFuel = matchingFuel.get();
//                sendData();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();


        if (level.isClientSide) {
            turbineSpeed.tickChaser();
            turbineAngle += turbineSpeed.getValue() * 3 / 10f;
            turbineAngle %= 360;
            CatnipServices.PLATFORM.executeOnClientOnly(() -> () -> this.tickAudio());
            return;
        }
        if(currentFuel != null && !tank.isEmpty() && energyStorage.getSpace() != 0){
            consumptionCounter += currentFuel.getConsumptionRate();
            if(consumptionCounter > 1f){
                tank.getPrimaryHandler().drain(Mth.floor(consumptionCounter), IFluidHandler.FluidAction.EXECUTE);
                consumptionCounter = Mth.frac(consumptionCounter);
            }
            energyStorage.internalProduceEnergy(PetrochemConfig.common().turbineEnergyProduction.get());
        }
    }



    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        if(turbineSpeed.getValue() > 0.01f){
            if(soundInstance == null || soundInstance.isStopped()){
                soundInstance = new EngineSoundInstance(PetrochemSoundEvents.TURBINE_HUMMING.getMainEvent(),this);

                Minecraft.getInstance().getSoundManager().play(soundInstance);
            }

            soundInstance.setPitch( 1.0f + (turbineSpeed.getValue()/1024.0f));
            soundInstance.setVolume( (turbineSpeed.getValue() / 1024.0f) * 0.07f);
        }else{
            if(soundInstance != null)
                soundInstance.cease();
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        energyStorage.storedEnergyTooltip(tooltip);
        if(turbineSpeed.getValue() > 0.1f)
            InternalEnergyStorage.energyProductionTooltip(tooltip, PetrochemConfig.common().turbineEnergyProduction.get());
        containedFluidTooltip(tooltip, isPlayerSneaking, tank.getCapability());
//        PetrochemLang.text("speed " + turbineSpeed.getValue()).forGoggles(tooltip);
        return true;
    }


    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        energyStorage.write(compound);
        if(clientPacket){
            compound.putFloat("speed", currentFuel == null? 0 : energyStorage.getSpace() == 0? 128.0f : 1024.0f);
        }
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        energyStorage.read(compound);
        if(clientPacket) {
             turbineSpeed.updateChaseTarget(compound.getFloat("speed"));
        }
    }



    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                PetrochemBlockEntities.TURBINE.get(),
                (be, context) -> {
                    if (context == null || context == be.getBlockState().getValue(FACING).getOpposite()){
                        return be.tank.getCapability();
                    }
                    return null;
                }
        );
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                PetrochemBlockEntities.TURBINE.get(),
                (be, context) -> {
                    if (context == null || context.getAxis() != be.getBlockState().getValue(FACING).getAxis()){
                        return be.energyStorage;
                    }
                    return null;
                }
        );
    }


}
