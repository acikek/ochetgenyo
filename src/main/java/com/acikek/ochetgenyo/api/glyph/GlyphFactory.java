package com.acikek.ochetgenyo.api.glyph;

import com.acikek.ochetgenyo.api.impl.glyph.GlyphFactoryImpl;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.minecraft.item.ItemGroup;

import java.util.List;

/**
 * A structure for creating, listing, and registering glyph blocks using {@link GlyphBuilder}s.
 *
 * <pre>
 * {@code
 * GlyphFactory factory = GlyphFactory.create();
 * factory.start().vowel().build("a");
 * factory.start().consonant(Orientation.RIGHT).build("k");
 * GlyphBlock stop = factory.start().stop().build(".");
 * factory.register("modid", itemGroup);
 *
 * OchetgenyoAPI.registerClientGlyphBehavior(factory.getGlyphs());
 * }
 * </pre>
 */
public interface GlyphFactory {

	static GlyphFactory create() {
		return new GlyphFactoryImpl();
	}

	/**
	 * Creates a new {@link GlyphBuilder} linked to this factory.
	 * @see GlyphBuilder#create(GlyphFactory)
	 */
	GlyphBuilder start();

	void addGlyph(GlyphBlock block);

	/**
	 * @return the built glyphs
	 */
	List<GlyphBlock> getGlyphs();

	/**
	 * Registers all built glyph blocks and block items.
	 * @param namespace The namespace to combine paths and register to.
	 * @param group The item group to add the block items to.
	 */
	void register(String namespace, ItemGroup group);
}
