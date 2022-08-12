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
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATTACHED);
	}
}
