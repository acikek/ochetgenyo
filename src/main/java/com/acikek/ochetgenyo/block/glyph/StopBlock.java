package com.acikek.ochetgenyo.block.glyph;

public class StopBlock extends GlyphBlock {

	public StopBlock(String phoneme) {
		super(phoneme);
	}

	@Override
	public String getId() {
		return "stop_glyph";
	}

	@Override
	public boolean isVowelConnectable() {
		return false;
	}
}
