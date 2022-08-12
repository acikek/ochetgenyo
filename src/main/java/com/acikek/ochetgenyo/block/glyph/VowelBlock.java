package com.acikek.ochetgenyo.block.glyph;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class VowelBlock extends GlyphBlock {

	public static final BooleanProperty ATTACHED = BooleanProperty.of("attached");

	public VowelBlock(char character) {
		super(character);
		setDefaultState(getStateManager().getDefaultState().with(ATTACHED, false));
	}

	@Override
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow) {
		boolean attached = (connectAbove && above.getBlock() instanceof ConsonantBlock) || (connectBelow && below.getBlock() instanceof ConsonantBlock);
		return super.update(state, above, connectAbove, below, connectBelow).with(ATTACHED, attached);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATTACHED);
	}
}
