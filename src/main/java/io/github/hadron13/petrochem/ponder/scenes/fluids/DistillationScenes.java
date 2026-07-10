package io.github.hadron13.petrochem.ponder.scenes.fluids;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillationControllerBlockEntity;
import io.github.hadron13.petrochem.blocks.distillation_tower.DistillationOutputBlockEntity;
import io.github.hadron13.petrochem.blocks.steel_pump.SteelPumpBlock;
import io.github.hadron13.petrochem.register.PetrochemBlocks;
import io.github.hadron13.petrochem.register.PetrochemFluids;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import static io.github.hadron13.petrochem.blocks.distillation_tower.DistillationControllerBlockEntity.DistilMode.DISTIL_ATMOSPHERIC;
import static io.github.hadron13.petrochem.blocks.distillation_tower.DistillationControllerBlockEntity.DistilMode.DISTIL_VACUUM;

public class DistillationScenes {


    public static void distillation_tower(SceneBuilder builder, SceneBuildingUtil util) {

        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("distillation_tower", "Processing resources with a distillation tower");
        scene.configureBasePlate(0, 0, 9);


        BlockPos controller = util.grid().at(4, 1, 3);

        BlockPos out1 = util.grid().at(2, 2, 4);
        BlockPos out2 = util.grid().at(2, 4, 4);
        BlockPos out3 = util.grid().at(2, 6, 4);
        BlockPos out4 = util.grid().at(4, 8, 4);


        BlockPos tank_point = util.grid().at(3, 4, 5);


        scene.world().showSection(util.select().fromTo(0, 0, 0, 8, 0, 8), Direction.UP);
        scene.idle(10);

        //show burners
        scene.world().showSection(util.select().fromTo(3, 1, 5, 5, 1, 5), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(3, 1, 4, 5, 1, 4), Direction.DOWN);
        scene.idle(5);

        //show tank
        scene.world().showSection(util.select().fromTo(3, 2, 3, 5, 7, 5), Direction.DOWN);
        scene.idle(10);

        scene.world().showSection(util.select().position(controller), Direction.SOUTH);
        scene.idle(5);

        scene.world().showSection(util.select().position(out1), Direction.EAST);
        scene.idle(4);
        scene.world().showSection(util.select().position(out2), Direction.EAST);
        scene.idle(4);
        scene.world().showSection(util.select().position(out3), Direction.EAST);
        scene.idle(4);
        scene.world().showSection(util.select().position(out4), Direction.DOWN);
        scene.idle(5);

        scene.overlay().showText(55)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The distillation column is a multi-block processing machine")
                .pointAt(Vec3.atCenterOf(tank_point));

        scene.idle(70);

        scene.overlay().showText(50)
                .placeNearTarget()
                .attachKeyFrame()
                .text("It consists of 3 types of block")
                .pointAt(Vec3.atCenterOf(tank_point));

        scene.idle(65);
        scene.overlay().showText(90)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Steel fluid tanks")
                .pointAt(Vec3.atCenterOf(tank_point.north()));

        scene.idle(15);

        scene.overlay().showText(75)
                .placeNearTarget()
                .text("Distillation controller")
                .pointAt(Vec3.atCenterOf(controller));

        scene.idle(15);

        scene.overlay().showText(60)
                .independent()
                .placeNearTarget()
                .text("Distillation outputs")
                .pointAt(Vec3.atCenterOf(out3));

        scene.idle(70);


        scene.overlay().showText(75)
                .placeNearTarget()
                .text("The controller must be placed below the tank")
                .pointAt(Vec3.atCenterOf(controller));

        scene.idle(85);

        scene.overlay().showText(75)
                .independent()
                .attachKeyFrame()
                .placeNearTarget()
                .text("One single output must be placed anywhere on each layer")
                .pointAt(Vec3.atCenterOf(out3));

        scene.idle(85);

        //layers

        scene.overlay().showText(80)
                .independent()
                .attachKeyFrame()
                .placeNearTarget()
                .text("Layers are 2 block high sections, there must be one per recipe output")
                .pointAt(Vec3.atCenterOf(tank_point));

        scene.idle(90);

        scene.overlay().showText(75)
                .placeNearTarget()
                .text("Layer 1")
                .pointAt(Vec3.atCenterOf(util.grid().at(3, 2, 3)).add(0, -0.5f, 0));

        scene.overlay().showText(75)
                .placeNearTarget()
                .text("Layer 2")
                .pointAt(Vec3.atBottomCenterOf(util.grid().at(3, 3, 3)));

        scene.overlay().showText(75)
                .placeNearTarget()
                .text("Layer 3")
                .pointAt(Vec3.atBottomCenterOf(util.grid().at(3, 5, 3)));

        scene.overlay().showText(75)
                .placeNearTarget()
                .text("Layer 4")
                .pointAt(Vec3.atBottomCenterOf(util.grid().at(3, 7, 3)));

        scene.idle(85);


        //fluid input

        scene.world().showSection(util.select().fromTo(5, 1, 3, 6, 1, 3), Direction.SOUTH);
        scene.idle(4);
        scene.world().showSection(util.select().fromTo(7, 1, 3, 7, 2, 3), Direction.DOWN);
        scene.idle(4);
        scene.world().showSection(util.select().fromTo(6, 1, 4, 9, 1, 4), Direction.NORTH);
        scene.world().showSection(util.select().position(9, 0, 5), Direction.UP);
        scene.idle(15);

        scene.world().setKineticSpeed(util.select().position(9, 0, 5), 32f);
        scene.world().setKineticSpeed(util.select().fromTo(6, 1, 4, 9, 1,4), -64f);
        scene.world().setKineticSpeed(util.select().position(6, 1, 3), 64f);

        scene.world().propagatePipeChange(util.grid().at(6, 1, 3));


        scene.overlay().showText(60)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Fluid input can be inserted through the sides of the controller")
                .pointAt(Vec3.atCenterOf(controller).add(-0.5f, -0.25, 0));

        scene.idle(70);


        //recipe types

        Vec3 blockSurface = util.vector().blockSurface(controller, Direction.NORTH);
        scene.overlay().showFilterSlotInput(blockSurface, Direction.NORTH, 80);
        scene.overlay().showControls(blockSurface, Pointing.DOWN, 60).rightClick();

        scene.overlay().showText(60)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The controller can be configured for three types of recipes")
                .pointAt(Vec3.atCenterOf(controller));

        scene.idle(70);

        scene.overlay().showText(40)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Flash")
                .pointAt(Vec3.atCenterOf(controller).add(0, 0.5f, 0));

        scene.overlay().showText(40)
                .placeNearTarget()
                .text("Atmospheric")
                .pointAt(Vec3.atCenterOf(controller));

        scene.overlay().showText(40)
                .placeNearTarget()
                .text("Vacuum")
                .pointAt(Vec3.atCenterOf(controller).add(0, -0.5f, 0));

        scene.idle(55);

        scene.overlay().showText(60)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Each having it's own set of requirements")
                .pointAt(Vec3.atCenterOf(controller));

        scene.idle(70);

        scene.overlay().showText(80)
                .placeNearTarget()
                .attachKeyFrame()
                .text("This atmospheric column requires a 3x3 tank, as well as constant blaze burner heat")
                .pointAt(Vec3.atCenterOf(tank_point));

        scene.idle(100);

        //change to flash mode
        //hide blazes
        scene.world().hideSection(util.select().fromTo(3, 1, 4, 5, 1, 4), Direction.WEST);
        //show pipes
        scene.world().showSection(util.select().fromTo(3, 1, 2, 7, 1, 2), Direction.SOUTH);
        scene.world().showSection(util.select().position(3, 1, 3), Direction.SOUTH);
        scene.world().setKineticSpeed(util.select().position(6, 1, 2), -64f);
        scene.idle(4);
        //show tank
        scene.world().showSection(util.select().fromTo(8, 1, 2, 8, 2, 2), Direction.DOWN);
        scene.idle(4);
        scene.world().propagatePipeChange(util.grid().at(6, 1, 2));

        scene.idle(5);

        scene.overlay().showText(80)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Flash requires only a 2x2, steam fluid input, but no heat")
                .pointAt(Vec3.atCenterOf(controller));

        scene.idle(100);


        //change to vacuum mode
        //show blazes
        scene.world().modifyBlockEntity(controller, DistillationControllerBlockEntity.class,
                (be) -> {be.distilMode.setValue(DISTIL_VACUUM.ordinal());});
        scene.world().showSection(util.select().fromTo(3, 1, 4, 5, 1, 4), Direction.EAST);
        scene.idle(4);
        //hide tank
        scene.world().hideSection(util.select().fromTo(8, 1, 2, 8, 2, 2), Direction.UP);
        scene.idle(4);
        scene.world().setBlock(util.grid().at(6, 1, 2),
                PetrochemBlocks.STEEL_PUMP.getDefaultState().setValue(SteelPumpBlock.FACING, Direction.EAST), true);
        scene.world().propagatePipeChange(util.grid().at(6, 1, 2));


        scene.overlay().showText(80)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Vacuum requires 2x2, heat and constant draining of air")
                .pointAt(Vec3.atCenterOf(controller));

        scene.idle(95);

        //hide pipes
        scene.world().hideSection(util.select().fromTo(3, 1, 2, 7, 1, 2), Direction.NORTH);
        scene.world().hideSection(util.select().position(3, 1, 3), Direction.NORTH);

        scene.world().modifyBlockEntity(controller, DistillationControllerBlockEntity.class,
                (be) -> {be.distilMode.setValue(DISTIL_ATMOSPHERIC.ordinal());});


        scene.world().modifyBlockEntity(out1, DistillationOutputBlockEntity.class, (be) ->
            {
                be.tankInventory.allowInsertion();
                be.tankInventory.getPrimaryHandler().fill(new FluidStack(PetrochemFluids.OIL_RESIDUE.get(), 4000), IFluidHandler.FluidAction.EXECUTE);
                be.tankInventory.forbidInsertion();
            }
        );

        scene.idle(15);
        scene.rotateCameraY(-90);
        scene.idle(15);


        scene.world().showSection(util.select().fromTo(2, 1, 4, 2, 1, 9), Direction.EAST);
        scene.world().showSection(util.select().position(2, 0, 9), Direction.EAST);
        scene.idle(4);
        scene.world().showSection(util.select().fromTo(3, 1, 7, 3, 1, 9), Direction.WEST);
        scene.world().showSection(util.select().position(4, 0, 9), Direction.UP);

        scene.world().setKineticSpeed(util.select().position(4, 0, 9), 32f);
        scene.world().setKineticSpeed(util.select().fromTo(3, 1, 7, 3, 1, 9), -64f);
        scene.world().setKineticSpeed(util.select().position(2, 1, 7), 64f);

        scene.world().propagatePipeChange(util.grid().at(2, 1, 7));

        scene.idle(10);

        scene.overlay().showText(70)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Results can then be extracted with pipes")
                .pointAt(Vec3.atCenterOf(out1));

        scene.idle(80);
        scene.world().showSection(util.select().position(2, 2, 5), Direction.EAST);
        scene.idle(10);

        scene.overlay().showText(70)
                .placeNearTarget()
                .attachKeyFrame()
                .text("Or voided with redstone")
                .pointAt(Vec3.atCenterOf(util.grid().at(2, 2, 5)).add(0.5f, 0, 0));

        scene.idle(10);

        scene.world().toggleRedstonePower(util.select().fromTo(2, 2, 4,2, 2, 5));

        scene.world().modifyBlockEntity(out1, DistillationOutputBlockEntity.class, (be) ->
                be.tankInventory.getPrimaryHandler().drain(4000, IFluidHandler.FluidAction.EXECUTE)
        );

        scene.world().propagatePipeChange(util.grid().at(2, 1, 7));

        scene.idle(65);
        scene.rotateCameraY(90);




    }
}
