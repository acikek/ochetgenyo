package com.acikek.ochetgenyo.block.glyph;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class OrientableVowelBlock extends VowelBlock {

	public static final EnumProperty<Orientation> ORIENTATION = EnumProperty.of("orientation", Orientation.class);

	public OrientableVowelBlock(String phoneme) {
		super(phoneme);
		setDefaultState(getDefaultState().with(ORIENTATION, Orientation.RIGHT));
	}

	@Override
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow, boolean isPlacement) {
		BlockState newState = super.update(state, above, connectAbove, below, connectBelow, isPlacement);
		if (connectAbove && above.getBlock() instanceof ConsonantBlock consonantBlock) {
			newState = newState.with(ORIENTATION, consonantBlock.vowelOrientation);
		}
		return newState;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ORIENTATION);
	}
}
