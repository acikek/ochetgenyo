package com.acikek.ochetgenyo.api.impl.glyph;

import com.acikek.ochetgenyo.api.glyph.GlyphBuilder;
import com.acikek.ochetgenyo.api.glyph.GlyphFactory;
import com.acikek.ochetgenyo.block.glyph.*;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class GlyphBuilderImpl implements GlyphBuilder {

	public enum	Type {
		VOWEL,
		CONSONANT,
		STOP
	}

	public GlyphFactory factory;

	public Type type;
	public boolean orientable;
	public Orientation vowelOrientation;
	public boolean alwaysConnect;
	public GlyphBlock[] exceptions;

	public GlyphBuilderImpl(GlyphFactory factory) {
		this.factory = factory;
	}

	@Override
	public GlyphBuilder vowel(boolean orientable) {
		type = Type.VOWEL;
		this.orientable = orientable;
		return this;
	}

	@Override
	public GlyphBuilder consonant(Orientation vowelOrientation, boolean alwaysConnect, GlyphBlock... exceptions) {
		type = Type.CONSONANT;
		this.vowelOrientation = vowelOrientation;
		this.alwaysConnect = alwaysConnect;
		this.exceptions = exceptions;
		return this;
	}

	@Override
	public GlyphBuilder stop() {
		this.type = Type.STOP;
		return this;
	}

	@Override
	public GlyphBlock build(String phoneme) {
		GlyphBlock block = switch (type) {
			case VOWEL -> orientable
					? new OrientableVowelBlock(phoneme)
					: new VowelBlock(phoneme);
			case CONSONANT -> new ConsonantBlock(
					phoneme, vowelOrientation, alwaysConnect,
					exceptions.length > 0 ? List.of(exceptions) : null);
			case STOP -> new StopBlock(phoneme);
		};
		factory.addGlyph(block);
		return block;
	}
}
