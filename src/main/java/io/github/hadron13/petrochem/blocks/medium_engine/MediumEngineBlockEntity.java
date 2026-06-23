package io.github.hadron13.petrochem.blocks.medium_engine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamJetParticleData;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import io.github.hadron13.petrochem.PetrochemLang;
import io.github.hadron13.petrochem.blocks.small_engine.EngineFuelRecipe;
import io.github.hadron13.petrochem.blocks.small_engine.EngineSoundInstance;
import io.github.hadron13.petrochem.mixin.accessor.KineticBlockEntityAccessor;
import io.github.hadron13.petrochem.register.PetrochemBlockEntities;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import io.github.hadron13.petrochem.register.PetrochemSoundEvents;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.Pointing;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

;

public class MediumEngineBlockEntity extends SteamEngineBlockEntity implements IHaveGoggleInformation {


    public boolean initialized = false;


    public EngineSoundInstance soundInstance;
    public SmartFluidTankBehaviour tank;
    public EngineFuelRecipe currentFuel = null;
    public ScrollValueBehaviour targetSpeed;
    public float consumptionCounter = 0;
    public float load = 0;
    public float consumption = 0;

    float prevAngle = 0;

    public MediumEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(5);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, 4000, true);
        tank.whenFluidUpdates(this::fluidUpdate);
        behaviours.add(tank);


        targetSpeed = new KineticScrollValueBehaviour(CreateLang.translateDirect("kinetics.speed_controller.rotation_speed"),
                this, new MediumEngineValueBox());
        targetSpeed.between(-256, 256);
        targetSpeed.value = 64;
        targetSpeed.withCallback(i -> this.updateRotation());


        behaviours.add(targetSpeed);
    }

    public void fluidUpdate(){
        FluidStack fluid = tank.getPrimaryHandler().getFluidInTank(0);
        if(fluid.isEmpty()){
            if(currentFuel != null){
                currentFuel = null;
                updateRotation();
            }
            currentFuel = null;
        }else{
            if(currentFuel == null){
                List<RecipeHolder<EngineFuelRecipe>> allFuels = level.getRecipeManager().getAllRecipesFor(PetrochemRecipeTypes.DIESEL_ENGINE_FUEL.getType());

                Optional<EngineFuelRecipe> matchingFuel =
                        allFuels.stream().map(RecipeHolder::value).filter(recipe -> recipe.match(fluid) ).findAny();
                if(matchingFuel.isEmpty())
                    return;
                currentFuel = matchingFuel.get();
                updateRotation();
            }
        }
    }

    public void updateRotation(){
        PoweredShaftBlockEntity shaft = getShaft();
        if (shaft == null){
            return;
        }

        if(currentFuel == null) {
            if (!shaft.getBlockPos()
                    .subtract(worldPosition)
                    .equals(shaft.enginePos))
                return;
            if (shaft.engineEfficiency == 0)
                return;
            shaft.update(worldPosition, 0, 0);
        }

        Direction facing = MediumEngineBlock.getFacing(getBlockState());

        BlockState shaftState = shaft.getBlockState();
        Direction.Axis targetAxis = Direction.Axis.X;
        if (shaftState.getBlock() instanceof IRotate ir)
            targetAxis = ir.getRotationAxis(shaftState);
        boolean verticalTarget = targetAxis == Direction.Axis.Y;

        BlockState blockState = getBlockState();
        if (!PetrochemBlocks.MEDIUM_ENGINE.has(blockState))
            return;

        if (facing.getAxis() == Direction.Axis.Y)
            facing = blockState.getValue(MediumEngineBlock.FACING);


        float efficiency = currentFuel != null? 1.0f : 0.0f;
//        float efficiency = 1.0f;

        int rotationSpeed =
                efficiency == 0 ? 1 : verticalTarget ? 1 : (int) GeneratingKineticBlockEntity.convertToDirection(1, facing);
        if (targetAxis == Direction.Axis.Z)
            rotationSpeed *= -1;

        float shaftSpeed = shaft.getTheoreticalSpeed();
        if (shaft.hasSource() && shaftSpeed != 0 && rotationSpeed != 0
                && (shaftSpeed > 0) != (rotationSpeed > 0)) {
            rotationSpeed *= -1;
        }

        shaft.update(worldPosition, rotationSpeed * targetSpeed.getValue(), efficiency / Mth.abs(targetSpeed.getValue()));
    }


    @Override
    public void tick() {

        //SmartBlockEntity tick, skip SteamEngineBlockEntity
        {
            if (!initialized && hasLevel()) {
                initialize();
                initialized = true;
            }

            if (lazyTickCounter-- <= 0) {
                lazyTickCounter = lazyTickRate;
                lazyTick();
            }

            forEachBehaviour(BlockEntityBehaviour::tick);
        }


        if(level.isClientSide){
            CatnipServices.PLATFORM.executeOnClientOnly(() -> this::tickAudio);
            CatnipServices.PLATFORM.executeOnClientOnly(() -> this::spawnParticles);
            return;
        }


        PoweredShaftBlockEntity shaft = getShaft();
        if (shaft == null){
            return;
        }

        Direction facing = MediumEngineBlock.getFacing(getBlockState());

        float previous_load = load;
        load = ((KineticBlockEntityAccessor)shaft).getStress() / ((KineticBlockEntityAccessor)shaft).getCapacity();
        if(Float.isNaN(load))
            load = 0;
        if(previous_load != load)
            sendData();

        if(currentFuel != null && shaft.getGeneratedSpeed() != 0){
            consumptionCounter += getConsumption();
            if(consumptionCounter > 1f){
                tank.getPrimaryHandler().drain(Mth.floor(consumptionCounter), IFluidHandler.FluidAction.EXECUTE);
                consumptionCounter = Mth.frac(consumptionCounter);
            }
        }
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        if(!level.isClientSide)
            updateRotation();
    }

    public float getConsumption(){
        if(currentFuel == null)
            return 0;
        return currentFuel.getConsumptionRate() * (float)Math.max(load, 0.3) ;
    }




    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        PoweredShaftBlockEntity shaft = getShaft();
        if(shaft != null) {
            shaft.addToEngineTooltip(tooltip, isPlayerSneaking);
            if (shaft.getGeneratedSpeed() != 0) {
                PetrochemLang.translate("gui.engine.load")
                        .style(ChatFormatting.GRAY)
                        .forGoggles(tooltip);

                IRotate.StressImpact.getFormattedStressText(load)
                        .forGoggles(tooltip);

                PetrochemLang.translate("gui.engine.consumption")
                        .style(ChatFormatting.GRAY)
                        .forGoggles(tooltip);

                LangBuilder mb = CreateLang.translate("generic.unit.millibuckets");

                PetrochemLang.builder()
                        .add(PetrochemLang.number(consumption))
                        .add(mb)
                        .text("/t")
                        .style(ChatFormatting.AQUA)
                        .forGoggles(tooltip, 1);
            }
        }

        containedFluidTooltip(tooltip, isPlayerSneaking, tank.getCapability());

        return true;
    }


    @OnlyIn(Dist.CLIENT)
    private void spawnParticles() {
        Float targetAngle = getTargetAngle();
        PoweredShaftBlockEntity ste = target.get();
        if (ste == null)
            return;
        if (!ste.isPoweredBy(worldPosition) || ste.engineEfficiency == 0)
            return;
        if (targetAngle == null)
            return;

        float angle = AngleHelper.deg(targetAngle);
        angle += (angle < 0) ? -180 + 75 : 360 - 75;
        angle %= 360;

        PoweredShaftBlockEntity shaft = getShaft();
        if (shaft == null || shaft.getSpeed() == 0)
            return;

        if (angle >= 0 && !(prevAngle > 180 && angle < 180)) {
            prevAngle = angle;
            return;
        }
        if (angle < 0 && !(prevAngle < -180 && angle > -180)) {
            prevAngle = angle;
            return;
        }

        Direction facing = SteamEngineBlock.getFacing(getBlockState());

        Vec3 offset = VecHelper.rotate(new Vec3(0, 0, 1).add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
                .multiply(1, 1, 0)
                .normalize()
                .scale(.5f)), AngleHelper.verticalAngle(facing), Direction.Axis.X);
        offset = VecHelper.rotate(offset, AngleHelper.horizontalAngle(facing), Direction.Axis.Y);
        Vec3 v = offset.scale(.5f)
                .add(Vec3.atCenterOf(worldPosition));
        Vec3 m = offset.subtract(Vec3.atLowerCornerOf(facing.getNormal())
                .scale(.75f));
        level.addParticle(new SteamJetParticleData(1), v.x, v.y, v.z, m.x, m.y, m.z);

        prevAngle = angle;
    }

    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        PoweredShaftBlockEntity shaft = getShaft();
        if(shaft != null && shaft.getSpeed() != 0){
            if(soundInstance == null || soundInstance.isStopped()){
                soundInstance = new EngineSoundInstance(PetrochemSoundEvents.SMALL_ENGINE_HUMMING.getMainEvent(),this);

                Minecraft.getInstance().getSoundManager().play(soundInstance);
            }

            soundInstance.setPitch( 0.4f + load * 0.2f + Mth.abs(shaft.getSpeed()/256f) * 0.2f);
            soundInstance.setVolume( 0.1f + Mth.abs(shaft.getSpeed()/256f) * 0.07f);
//            soundInstance.setVolume(1.0f);
//            soundInstance.setPitch(1.0f);
        }else{
            if(soundInstance != null && !soundInstance.isStopped())
                soundInstance.cease();
        }
    }


    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Float getTargetAngle() {
        float angle = 0;
        BlockState blockState = getBlockState();
        if (!PetrochemBlocks.MEDIUM_ENGINE.has(blockState))
            return null;

        Direction facing = MediumEngineBlock.getFacing(blockState);
        PoweredShaftBlockEntity shaft = getShaft();
        Direction.Axis facingAxis = facing.getAxis();
        Direction.Axis axis = Direction.Axis.Y;

        if (shaft == null)
            return null;

        axis = KineticBlockEntityRenderer.getRotationAxisOf(shaft);
        angle = KineticBlockEntityRenderer.getAngleForBe(shaft, shaft.getBlockPos(), axis);

        if (axis == facingAxis)
            return null;
        if (axis.isHorizontal() && (facingAxis == Direction.Axis.X ^ facing.getAxisDirection() == Direction.AxisDirection.POSITIVE))
            angle *= -1;
        if (axis == Direction.Axis.X && facing == Direction.DOWN)
            angle *= -1;
        return angle;
    }


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                PetrochemBlockEntities.MEDIUM_ENGINE.get(),
                (be, context) -> {
                    Direction dir = SteamEngineBlock.getConnectedDirection(be.getBlockState()).getOpposite();
                    if (context == null || context == dir){
                        return be.tank.getCapability();
                    }
                    return null;
                }
        );
    }


    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putFloat("load", load);
        tag.putFloat("consumption", getConsumption());
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        load = tag.getFloat("load");
        consumption = tag.getFloat("consumption");
    }

    @Override
    public void remove() {
        PoweredShaftBlockEntity shaft = getShaft();
        if (shaft != null)
            shaft.remove(worldPosition);
        super.remove();
    }


    public class MediumEngineValueBox extends ValueBoxTransform.Sided {

        @Override
        protected boolean isSideActive(BlockState state, Direction side) {
            Direction engineFacing = SteamEngineBlock.getFacing(state);
            if (engineFacing.getAxis() == side.getAxis())
                return false;

            float roll = 0;
            for (Pointing p : Pointing.values())
                if (p.getCombinedDirection(engineFacing) == side)
                    roll = p.getXRotation();
            if (engineFacing == Direction.UP)
                roll += 180;

            boolean recessed = roll % 180 == 0;
            if (engineFacing.getAxis() == Direction.Axis.Y)
                recessed ^= state.getValue(SteamEngineBlock.FACING)
                        .getAxis() == Direction.Axis.X;

            return !recessed;
        }

        @Override
        public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            Direction side = getSide();
            Direction engineFacing = SteamEngineBlock.getFacing(state);

            float roll = 0;
            for (Pointing p : Pointing.values())
                if (p.getCombinedDirection(engineFacing) == side)
                    roll = p.getXRotation();
            if (engineFacing == Direction.UP)
                roll += 180;

            float horizontalAngle = AngleHelper.horizontalAngle(engineFacing);
            float verticalAngle = AngleHelper.verticalAngle(engineFacing);
            Vec3 local = VecHelper.voxelSpace(8, 14.5, 9);

            local = VecHelper.rotateCentered(local, roll, Direction.Axis.Z);
            local = VecHelper.rotateCentered(local, horizontalAngle, Direction.Axis.Y);
            local = VecHelper.rotateCentered(local, verticalAngle, Direction.Axis.X);

            return local;
        }

        @Override
        public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
            Direction facing = SteamEngineBlock.getFacing(state);

            if (facing.getAxis() == Direction.Axis.Y) {
                super.rotate(level, pos, state, ms);
                return;
            }

            float roll = 0;
            for (Pointing p : Pointing.values())
                if (p.getCombinedDirection(facing) == getSide())
                    roll = p.getXRotation();

            float yRot = AngleHelper.horizontalAngle(facing) + (facing == Direction.DOWN ? 180 : 0);
            TransformStack.of(ms)
                    .rotateYDegrees(yRot)
//                    .rotateXDegrees(facing == Direction.DOWN ? -90 : 90)
                    .rotateYDegrees(roll)
            ;
        }

        @Override
        protected Vec3 getSouthLocation() {
            return Vec3.ZERO;
        }

    }

}
