package com.acikek.ochetgenyo.api;

import com.acikek.ochetgenyo.client.GlyphBlockColorProvider;
import com.acikek.ochetgenyo.api.impl.OchetgenyoAPIImpl;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.tag.TagKey;

import java.util.Collection;
import java.util.List;

public class OchetgenyoAPI {

	/**
	 * @return the {@code ochetgenyo:glyphs} block tag.
	 */
	public static TagKey<Block> getGlyphTag() {
		return OchetgenyoAPIImpl.GLYPHS;
	}

	/**
	 * @return a list of {@link GlyphBlock}s found in the {@code ochetgenyo:glyphs} block tag.
	 * @throws IllegalStateException if any entry within the tag is not an instance of {@link GlyphBlock}.
	 */
	public static List<GlyphBlock> getGlyphBlocks() {
		return OchetgenyoAPIImpl.getGlyphBlocks();
	}

	/**
	 * Puts one or more provided {@link GlyphBlock}s on the {@link RenderLayer#getCutoutMipped()} render layer and registers them on the {@link GlyphBlockColorProvider#INSTANCE}.
	 */
	@Environment(EnvType.CLIENT)
	public static void registerClientGlyphBehavior(GlyphBlock... glyphBlocks) {
		OchetgenyoAPIImpl.registerClientGlyphBehavior(glyphBlocks);
	}

	/**
	 * @see	OchetgenyoAPI#registerClientGlyphBehavior(GlyphBlock...)
	 */
	@Environment(EnvType.CLIENT)
	public static void registerClientGlyphBehavior(Collection<GlyphBlock> glyphBlocks) {
		OchetgenyoAPIImpl.registerClientGlyphBehavior(glyphBlocks);
	}
}
