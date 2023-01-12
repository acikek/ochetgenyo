package com.acikek.ochetgenyo.api.glyph;

import com.acikek.ochetgenyo.api.impl.glyph.GlyphBuilderImpl;
import com.acikek.ochetgenyo.block.glyph.*;

public interface GlyphBuilder {

	static GlyphBuilder create(GlyphFactory factory) {
		return new GlyphBuilderImpl(factory);
	}

	/**
	 * Sets this glyph as a {@link VowelBlock}
	 * @param orientable Whether this vowel can switch direction horizontally. If {@code true}, the end result will be a {@link OrientableVowelBlock}.
	 */
	GlyphBuilder vowel(boolean orientable);

	/**
	 * @see GlyphBuilder#vowel(boolean)
	 */
	default GlyphBuilder vowel() {
		return vowel(false);
	}

	/**
	 * Sets this glyph as a {@link ConsonantBlock}.
	 * @param vowelOrientation The direction that orientable vowels should face when connected below this glyph.
	 * @param alwaysConnect Whether to always connect to vertical glyphs regardless of their type.
	 * @param exceptions Exceptions to the {@code alwaysConnect} rule.
	 */
	GlyphBuilder consonant(Orientation vowelOrientation, boolean alwaysConnect, GlyphBlock... exceptions);

	/**
	 * @see GlyphBuilder#consonant(Orientation, boolean, GlyphBlock...)
	 */
	default GlyphBuilder consonant(Orientation vowelOrientation, boolean alwaysConnect) {
		return consonant(vowelOrientation, alwaysConnect, new GlyphBlock[0]);
	}

	/**
	 * @see GlyphBuilder#consonant(Orientation, boolean, GlyphBlock...)
	 */
	default GlyphBuilder consonant(Orientation vowelOrientation) {
		return consonant(vowelOrientation, false);
	}

	/**
	 * Sets this glyph as a {@link StopBlock}.
	 */
	GlyphBuilder stop();

	/**
	 * Constructs the glyph.
	 * @param phoneme the glyph's unit of sound. Used in {@link GlyphBlock#getId()} and in chat sentences.
	 */
	GlyphBlock build(String phoneme);
}
