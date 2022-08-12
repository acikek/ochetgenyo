package com.acikek.ochetgenyo.block.glyph;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class OrientableVowelBlock extends VowelBlock {

	public enum Orientation implements StringIdentifiable {

		LEFT,
		RIGHT;

		@Override
		public String asString() {
			return this == LEFT ? "left" : "right";
		}
	}

	public static final EnumProperty<Orientation> ORIENTATION = EnumProperty.of("orientation", Orientation.class);

	public OrientableVowelBlock(char character) {
		super(character);
		setDefaultState(getStateManager().getDefaultState().with(ORIENTATION, Orientation.RIGHT));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ORIENTATION);
	}
}
