package slava0135.dbg;

import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class FlareBlock extends Block implements Waterloggable {
  public static final MapCodec<FlareBlock> CODEC = createCodec(FlareBlock::new);

  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape SHAPE = Block.createColumnShape(4.0, 0.0, 8.0);

  public FlareBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
  }

  @Override
  public MapCodec<FlareBlock> getCodec() {
    return CODEC;
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    var fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    var blockState = this.getDefaultState();
    if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
      return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    return null;
  }

  public BlockState getThrowState(BlockState state) {
    return getDefaultState().with(WATERLOGGED, state.getFluidState().getFluid() == Fluids.WATER);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos,
      ShapeContext context) {
    return SHAPE;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED);
  }

  @Override
  protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world,
      ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos,
      BlockState neighborState, Random random) {
    if ((Boolean) state.get(WATERLOGGED)) {
      tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }

    return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
        : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos,
            neighborState, random);
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
    return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
  }
}
