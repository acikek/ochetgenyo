package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.Connection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

import java.util.List;

public class ConsonantBlock extends GlyphBlock {

	public static final EnumProperty<Connection> FORM = EnumProperty.of("form", Connection.class);

	public Orientation vowelOrientation;
	public boolean alwaysConnect;
	public List<Character> exceptions;

	public ConsonantBlock(char character, Orientation vowelOrientation, boolean alwaysConnect, List<Character> exceptions) {
		super(character);
		this.vowelOrientation = vowelOrientation;
		this.alwaysConnect = alwaysConnect;
		this.exceptions = exceptions;
		setDefaultState(getDefaultState().with(FORM, Connection.NONE));
	}

	public ConsonantBlock(char character, Orientation vowelOrientation) {
		this(character, vowelOrientation, false, null);
	}

	@Override
	public boolean isVowelConnectable() {
		return true;
	}

	public boolean isException(GlyphBlock other) {
		return exceptions == null || !exceptions.contains(other.character);
	}

	public boolean canConnect(BlockState other) {
		if (other.getBlock() instanceof VowelBlock) {
			return true;
		}
		if (other.getBlock() instanceof GlyphBlock glyphBlock) {
			return glyphBlock instanceof ConsonantBlock consonantBlock && consonantBlock.alwaysConnect
					? consonantBlock.isException(this)
					: alwaysConnect && isException(glyphBlock);
		}
		return false;
	}

	@Override
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow, boolean isPlacement) {
		Connection connection = Connection.getByNeighbors(connectAbove && canConnect(above), connectBelow && canConnect(below));
		return super.update(state, above, connectAbove, below, connectBelow, isPlacement).with(FORM, connection);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FORM);
	}
}
