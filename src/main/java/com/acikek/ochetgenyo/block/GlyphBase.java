package com.acikek.ochetgenyo.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class GlyphBase extends HorizontalFacingBlock {

	public static final EnumProperty<Connection> CONNECTION = EnumProperty.of("connection", Connection.class);
	public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");

	public static final Settings SETTINGS = FabricBlockSettings.copy(Blocks.BASALT)
			.luminance(value -> value.get(GLOWING) ? 3 : 0)
			.emissiveLighting((blockState, blockView, blockPos) -> blockState.get(GLOWING));

	public GlyphBase() {
		super(SETTINGS);
		setDefaultState(getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(CONNECTION, Connection.NONE)
				.with(GLOWING, false));
	}

	/**
	 * Handles player interaction using a held item.
	 * @return {@link ActionResult#SUCCESS} one-line calls and returns.
	 */
	public static ActionResult handlePlayerInteraction(World world, BlockPos pos, SoundEvent event, PlayerEntity player, ItemStack handStack) {
		world.playSound(null, pos, event, SoundCategory.BLOCKS, 1.0f, 1.0f);
		if (!player.isCreative()) {
			handStack.decrement(1);
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (hand == Hand.MAIN_HAND && player.getStackInHand(hand).isOf(Items.GLOW_INK_SAC) && !state.get(GLOWING)) {
			world.setBlockState(pos, state.with(GLOWING, true));
			return handlePlayerInteraction(world, pos, SoundEvents.ITEM_GLOW_INK_SAC_USE, player, player.getStackInHand(hand));
		}
		return ActionResult.PASS;
	}

	/**
	 * Whether the <b>glyph base</b> states can connect to form a sentence pillar.
	 * <b>Does not</b> check whether the states have connectable chiseled glyphs.
	 */
	public static boolean canConnectBases(BlockState state, BlockState other) {
		return other.getBlock() instanceof GlyphBase && state.get(FACING) == other.get(FACING);
	}

	public boolean isVowelConnectable() {
		return false;
	}

	/**
	 * A wrapper around {@link GlyphBase#update(BlockState, BlockState, boolean, BlockState, boolean, boolean)} that calculates connections.
	 * Should be used for external calls.
	 */
	public BlockState update(WorldAccess world, BlockState state, BlockPos pos, boolean isPlacement) {
		BlockState above = world.getBlockState(pos.up());
		BlockState below = world.getBlockState(pos.down());
		return update(state, above, canConnectBases(state, above), below, canConnectBases(state, below), isPlacement);
	}

	/**
	 * Modifies a base glyph state either when initially placed or during a neighbor block update based on several contextual parameters.
	 * @param state the base state to be modified
	 * @param above the state above this block
	 * @param connectAbove whether this block can connect bases with the above state
	 * @param below the state below this block
	 * @param connectBelow whether this block can connect bases with the state below
	 * @param isPlacement whether this block has just been placed by a player
	 * @return the updated state
	 * @deprecated Override, don't call!
	 */
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow, boolean isPlacement) {
		BlockState newState = state.with(CONNECTION, Connection.getByNeighbors(connectAbove, connectBelow));
		if (isPlacement && connectAbove) {
			newState = newState.with(GLOWING, above.get(GLOWING));
		}
		return newState;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (neighborPos.equals(pos.up()) || neighborPos.equals(pos.down())) {
			return update(world, state, pos, false);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing().getOpposite());
		return update(ctx.getWorld(), state, ctx.getBlockPos(), true);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING, CONNECTION, GLOWING);
	}
}
