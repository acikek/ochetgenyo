package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.Connection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class ConsonantBlock extends GlyphBlock {

	public static final EnumProperty<Connection> FORM = EnumProperty.of("form", Connection.class);

	public record Rules(Orientation vowelOrientation, boolean connectLikeVowel, char[] exceptions) {
	}

	public Rules rules;

	public ConsonantBlock(char character, Rules rules) {
		super(character);
		this.rules = rules;
		setDefaultState(getStateManager().getDefaultState().with(FORM, Connection.NONE));
	}

	public ConsonantBlock(char character, Orientation vowelOrientation) {
		this(character, new Rules(vowelOrientation, false, null));
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
