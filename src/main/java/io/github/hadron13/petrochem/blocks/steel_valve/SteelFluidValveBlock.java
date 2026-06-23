package io.github.hadron13.petrochem.blocks.steel_valve;

import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlock;
import com.simibubi.create.content.fluids.pipes.valve.FluidValveBlockEntity;
import io.github.hadron13.petrochem.register.PetrochemBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SteelFluidValveBlock extends FluidValveBlock {
    public SteelFluidValveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends FluidValveBlockEntity> getBlockEntityType() {
        return PetrochemBlockEntities.STEEL_FLUID_VALVE.get();
    }
}
