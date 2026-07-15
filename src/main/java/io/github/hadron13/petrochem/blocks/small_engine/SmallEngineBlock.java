package io.github.hadron13.petrochem.blocks.small_engine;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import io.github.hadron13.petrochem.register.PetrochemBlockEntities;
import io.github.hadron13.petrochem.register.PetrochemRecipeTypes;
import io.github.hadron13.petrochem.register.PetrochemShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class SmallEngineBlock extends HorizontalKineticBlock implements IBE<SmallEngineBlockEntity>{

    public SmallEngineBlock(Properties properties) {
        super(properties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn,
                               BlockPos pos, CollisionContext context) {
        return PetrochemShapes.SMALL_ENGINE.get(state.getValue(HORIZONTAL_FACING));
    }



    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        return onBlockEntityUseItemOn(level, pos, be -> {
            if (!stack.isEmpty()) {

                if(stack.getItem() instanceof BucketItem bucketItem){
                    if(!EngineFuelRecipe.validFuels.get(PetrochemRecipeTypes.GASOLINE_ENGINE_FUEL).contains(bucketItem.content))
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

                }

                if(FluidHelper.tryEmptyItemIntoBE(level, player, hand, stack, be)){
                    return ItemInteractionResult.SUCCESS;
                }else{
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            }
            return ItemInteractionResult.SUCCESS;
        });
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return state.getValue(HORIZONTAL_FACING).getAxis() == face.getAxis();
    }


    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (worldIn.isClientSide)
            return;

        int signal = worldIn.getBestNeighborSignal(pos);
        withBlockEntityDo(worldIn, pos, be -> {
            be.speed_modulator = ((float)(15 - signal))/15.0f;
            be.updateGeneratedRotation();
        }
        );
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }


    @Override
    public Class<SmallEngineBlockEntity> getBlockEntityClass() {
        return SmallEngineBlockEntity.class;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public BlockEntityType<? extends SmallEngineBlockEntity> getBlockEntityType() {
        return PetrochemBlockEntities.SMALL_ENGINE.get();
    }
}
