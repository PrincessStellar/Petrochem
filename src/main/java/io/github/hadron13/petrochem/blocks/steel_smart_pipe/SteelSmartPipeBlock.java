package io.github.hadron13.petrochem.blocks.steel_smart_pipe;

import com.simibubi.create.content.fluids.pipes.SmartFluidPipeBlock;
import com.simibubi.create.content.fluids.pipes.SmartFluidPipeBlockEntity;
import io.github.hadron13.petrochem.register.PetrochemBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SteelSmartPipeBlock extends SmartFluidPipeBlock {
    public SteelSmartPipeBlock(Properties p_i48339_1_) {
        super(p_i48339_1_);
    }

    @Override
    public BlockEntityType<? extends SmartFluidPipeBlockEntity> getBlockEntityType() {
        return PetrochemBlockEntities.STEEL_SMART_FLUID_PIPE.get();
    }
}
