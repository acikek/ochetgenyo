package com.acikek.ochetgenyo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class VowelBlock extends GlyphBlock {

	public enum Orientation implements StringIdentifiable {

		LEFT,
		RIGHT;

		@Override
		public String asString() {
			return this == LEFT ? "left" : "right";
		}
	}

	public static final BooleanProperty ATTACHED = BooleanProperty.of("attached");
	public static final EnumProperty<Orientation> ORIENTATION = EnumProperty.of("orientation", Orientation.class);

	public VowelBlock(char character) {
		super(character);
		setDefaultState(getDefaultState()
				.with(ATTACHED, false)
				.with(ORIENTATION, Orientation.RIGHT));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATTACHED, ORIENTATION);
	}
}
