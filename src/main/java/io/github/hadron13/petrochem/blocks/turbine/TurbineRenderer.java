package io.github.hadron13.petrochem.blocks.turbine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import io.github.hadron13.petrochem.register.PetrochemPartialModels;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class TurbineRenderer extends SafeBlockEntityRenderer<TurbineBlockEntity> {

    public TurbineRenderer(BlockEntityRendererProvider.Context context){
    }


    @Override
    protected void renderSafe(TurbineBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        Direction direction = be.getBlockState()
                .getValue(FACING);
        VertexConsumer vb = bufferSource.getBuffer(RenderType.cutoutMipped());

        int lightInFront = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().relative(direction));

        SuperByteBuffer shaftHalf = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, be.getBlockState(), direction);
        SuperByteBuffer fan = CachedBuffers.partialFacing(PetrochemPartialModels.TURBINE_PROPELLER, be.getBlockState(), direction.getOpposite());

        float speed = be.turbineSpeed.getValue(partialTicks) * 3/10f;
        float angle = be.turbineAngle + speed * partialTicks;

        shaftHalf.light(lightInFront)
                .translate(Vec3.atLowerCornerOf(direction.getNormal()).scale(-2/16f))
                .rotateCenteredDegrees(angle, direction)
                .renderInto(ms, vb);

        fan.light(lightInFront)
            .rotateCenteredDegrees(angle, direction)
            .renderInto(ms, vb);

        fan.light(lightInFront)
            .translate(Vec3.atLowerCornerOf(direction.getNormal()).scale(-1/16f))
            .rotateCenteredDegrees(-angle + 30, direction)
            .renderInto(ms, vb);

        fan.light(lightInFront)
                .translate(Vec3.atLowerCornerOf(direction.getNormal()).scale(-2/16f))
                .rotateCentered(angle + 60, direction)
                .renderInto(ms, vb);
    }
}
