package com.acikek.ochetgenyo.block.glyph;

import net.minecraft.util.StringIdentifiable;

public enum Orientation implements StringIdentifiable {

	LEFT,
	RIGHT;

	@Override
	public String asString() {
		return this == LEFT ? "left" : "right";
	}
}
