package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.GlyphBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class VowelBlock extends GlyphBlock {

	public static final BooleanProperty ATTACHED = BooleanProperty.of("attached");

	public VowelBlock(char character) {
		super(character);
		setDefaultState(getDefaultState().with(ATTACHED, false));
	}

	@Override
	public boolean isVowelConnectable() {
		return true;
	}

	public boolean canConnect(BlockState other) {
		return other.getBlock() instanceof GlyphBase glyphBase && glyphBase.isVowelConnectable();
	}

	@Override
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow, boolean isPlacement) {
		boolean attached = (connectAbove && canConnect(above)) || (connectBelow && canConnect(below));
		return super.update(state, above, connectAbove, below, connectBelow, isPlacement).with(ATTACHED, attached);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATTACHED);
	}
}
