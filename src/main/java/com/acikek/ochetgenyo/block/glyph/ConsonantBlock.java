package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.Connection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class ConsonantBlock extends GlyphBlock {

	public static final EnumProperty<Connection> FORM = EnumProperty.of("form", Connection.class);

	public Orientation vowelOrientation;

	public ConsonantBlock(char character, Orientation vowelOrientation) {
		super(character);
		this.vowelOrientation = vowelOrientation;
		setDefaultState(getStateManager().getDefaultState().with(FORM, Connection.NONE));
	}

	@Override
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow) {
		Connection connection = Connection.getByNeighbors(connectAbove && above.getBlock() instanceof VowelBlock, connectBelow && below.getBlock() instanceof VowelBlock);
		return super.update(state, above, connectAbove, below, connectBelow).with(FORM, connection);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FORM);
	}
}
