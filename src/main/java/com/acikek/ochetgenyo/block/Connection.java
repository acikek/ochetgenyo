package com.acikek.ochetgenyo.block;

import net.minecraft.util.StringIdentifiable;

/**
 * Represents an object's connections to other objects. For example, {@link Connection#TOP} would be connected to only the top object.<br>
 * The object can be either a glyph, or the block it rests on.
 */
public enum Connection implements StringIdentifiable {

	NONE,
	TOP,
	BOTTOM,
	BOTH;

	public final String name;

	Connection() {
		name = name().toLowerCase();
	}

	/**
	 * Gets the proper connection state for a block given how it should connect to its vertical neighbors.
	 */
	public static Connection getByNeighbors(boolean above, boolean below) {
		if (above && below) {
			return BOTH;
		}
		if (above) {
			return TOP;
		}
		if (below) {
			return BOTTOM;
		}
		return NONE;
	}

	@Override
	public String asString() {
		return name().toLowerCase();
	}
}
