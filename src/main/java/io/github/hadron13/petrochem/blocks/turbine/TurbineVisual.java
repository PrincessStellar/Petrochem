package io.github.hadron13.petrochem.blocks.turbine;

import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import io.github.hadron13.petrochem.register.PetrochemPartialModels;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.function.Consumer;

import static io.github.hadron13.petrochem.blocks.turbine.TurbineBlock.FACING;

public class TurbineVisual extends AbstractBlockEntityVisual<TurbineBlockEntity> implements SimpleDynamicVisual {

    protected final TransformedInstance shaft;
    protected final TransformedInstance fan1, fan2, fan3;
    protected float lastAngle = Float.NaN;
    protected final Matrix4f baseTransform = new Matrix4f();

    public TurbineVisual(VisualizationContext ctx, TurbineBlockEntity blockEntity, float partialTick) {
        super(ctx, blockEntity, partialTick);


        shaft = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.SHAFT_HALF))
                .createInstance();
        fan1 = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(PetrochemPartialModels.TURBINE_PROPELLER))
                .createInstance();
        fan2 = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(PetrochemPartialModels.TURBINE_PROPELLER))
                .createInstance();
        fan3 = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(PetrochemPartialModels.TURBINE_PROPELLER))
                .createInstance();

        Direction facing = blockEntity.getBlockState().getValue(FACING);

        fan1.translate(getVisualPosition())
                .center()
                .rotateToFace(facing)
        ;

        baseTransform.set(fan1.pose);



        animate(0);

    }

    public void animate(float angle){
        Direction facing = blockEntity.getBlockState().getValue(FACING);

        shaft.setIdentityTransform()
                .translate(getVisualPosition())
                .center()
                .rotateToFace(facing.getOpposite())
                .rotateZDegrees(angle)
                .uncenter()
                .translateZ(-1.1f/16f)
                .setChanged();
        ;


        fan1.setTransform(baseTransform)
                .rotateZDegrees(angle)
                .uncenter()
                .setChanged();

        fan2.setTransform(baseTransform)
                .rotateZDegrees(-angle + 30)
                .uncenter()
                .translateZ(1/16f)
                .setChanged();

        fan3.setTransform(baseTransform)
                .rotateZDegrees(angle + 60)
                .uncenter()
                .translateZ(2/16f)
                .setChanged();
    }

    @Override
    public void beginFrame(Context ctx) {
        float speed = blockEntity.turbineSpeed.getValue(ctx.partialTick()) * 3/10f;
        float angle = blockEntity.turbineAngle + speed * ctx.partialTick();

        animate(angle);
    }


    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(shaft);
        consumer.accept(fan1);
        consumer.accept(fan2);
        consumer.accept(fan3);
    }

    @Override
    public void updateLight(float partialTick) {
        relight(shaft, fan1, fan2, fan3);
    }

    @Override
    protected void _delete() {
        shaft.delete();
        fan1.delete();
        fan2.delete();
        fan3.delete();
    }

}
