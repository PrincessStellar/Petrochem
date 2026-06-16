package io.github.hadron13.petrochem.blocks.steel_pump;


import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import io.github.hadron13.petrochem.register.PetrochemPartialModels;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class SteelPumpRenderer extends KineticBlockEntityRenderer<PumpBlockEntity> {

    public SteelPumpRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(PumpBlockEntity be, BlockState state) {
        return CachedBuffers.partialFacing(PetrochemPartialModels.STEEL_PUMP_COG, state);
    }

}

