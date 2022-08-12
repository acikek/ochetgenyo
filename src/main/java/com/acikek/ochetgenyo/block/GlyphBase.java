package com.acikek.ochetgenyo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class GlyphBase extends HorizontalFacingBlock {

	public static final Settings SETTINGS = QuiltBlockSettings.copy(Blocks.BASALT);
	public static final EnumProperty<Connection> CONNECTION = EnumProperty.of("connection", Connection.class);

	public GlyphBase() {
		super(SETTINGS);
		setDefaultState(getStateManager().getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(CONNECTION, Connection.NONE));
	}

	public static boolean canConnect(BlockState state, BlockState other) {
		return other.getBlock() instanceof GlyphBase && state.get(FACING) == other.get(FACING);
	}

	public BlockState update(WorldAccess world, BlockState state, BlockPos pos) {
		BlockState above = world.getBlockState(pos.up());
		BlockState below = world.getBlockState(pos.down());
		return update(state, above, canConnect(state, above), below, canConnect(state, below));
	}

	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow) {
		return state.with(CONNECTION, Connection.getByNeighbors(connectAbove, connectBelow));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (neighborPos.equals(pos.up()) || neighborPos.equals(pos.down())) {
			return update(world, state, pos);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing().getOpposite());
		return update(ctx.getWorld(), state, ctx.getBlockPos());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING, CONNECTION);
	}
}
