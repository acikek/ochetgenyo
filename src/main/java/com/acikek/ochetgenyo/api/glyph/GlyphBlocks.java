package com.acikek.ochetgenyo.api.glyph;

import com.acikek.ochetgenyo.block.glyph.*;

import java.util.List;

/**
 * Utility methods for creating {@link GlyphBlock}s.<br>
 * {@code phoneme} parameters are the glyph's unit of sound, used in {@link GlyphBlock#getId()} and in chat sentences.
 */
public class GlyphBlocks {

	/**
	 * @param orientable Whether this vowel can switch direction horizontally. If {@code true}, the end result will be a {@link OrientableVowelBlock}.
	 * @return a vowel block
	 */
	public static GlyphBlock vowel(String phoneme, boolean orientable) {
		return orientable
				? new OrientableVowelBlock(phoneme)
				: new VowelBlock(phoneme);
	}

	/**
	 * @see GlyphBlocks#vowel(String, boolean)
	 */
	public static GlyphBlock vowel(String phoneme) {
		return vowel(phoneme, false);
	}

	/**
	 * @see GlyphBlocks#vowel(String, boolean)
	 */
	public static GlyphBlock orientable(String phoneme) {
		return vowel(phoneme, true);
	}

	/**
	 * @param vowelOrientation The direction that orientable vowels should face when connected below this glyph.
	 * @param alwaysConnect Whether to always connect to vertical glyphs regardless of their type.
	 * @param exceptions Exceptions to the {@code alwaysConnect} rule.
	 * @return a {@link ConsonantBlock}
	 */
	public static GlyphBlock consonant(String phoneme, Orientation vowelOrientation, boolean alwaysConnect, GlyphBlock... exceptions) {
		return new ConsonantBlock(phoneme, vowelOrientation, alwaysConnect, List.of(exceptions));
	}

	/**
	 * @see GlyphBlocks#consonant(String phoneme, Orientation, boolean, GlyphBlock...)
	 */
	public static GlyphBlock consonant(String phoneme, Orientation vowelOrientation) {
		return consonant(phoneme, vowelOrientation, false);
	}

	/**
	 * @see GlyphBlocks#consonant(String phoneme, Orientation)
	 */
	public static GlyphBlock left(String phoneme) {
		return consonant(phoneme, Orientation.LEFT, false);
	}

	/**
	 * @see GlyphBlocks#consonant(String phoneme, Orientation)
	 */
	public static GlyphBlock right(String phoneme) {
		return consonant(phoneme, Orientation.RIGHT, false);
	}

	/**
	 * @see GlyphBlocks#consonant(String phoneme, Orientation, boolean, GlyphBlock...)
	 */
	public static GlyphBlock left(String phoneme, boolean alwaysConnect, GlyphBlock... exceptions) {
		return consonant(phoneme, Orientation.LEFT, alwaysConnect, exceptions);
	}

	/**
	 * @see GlyphBlocks#consonant(String phoneme, Orientation, boolean, GlyphBlock...)
	 */
	public static GlyphBlock right(String phoneme, boolean alwaysConnect, GlyphBlock... exceptions) {
		return consonant(phoneme, Orientation.RIGHT, alwaysConnect, exceptions);
	}

	/**
	 * @return a {@link StopBlock}
	 */
	public static GlyphBlock stop(String phoneme) {
		return new StopBlock(phoneme);
	}
}
