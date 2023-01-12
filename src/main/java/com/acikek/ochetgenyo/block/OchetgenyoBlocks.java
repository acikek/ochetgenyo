package com.acikek.ochetgenyo.block;

import com.acikek.ochetgenyo.Ochetgenyo;
import com.acikek.ochetgenyo.api.glyph.GlyphFactory;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import com.acikek.ochetgenyo.block.glyph.Orientation;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.acikek.ochetgenyo.Ochetgenyo.ID;
import static com.acikek.ochetgenyo.Ochetgenyo.ITEM_GROUP;

public class OchetgenyoBlocks {

	public static GlyphBase GLYPH_BASE;
	public static GlyphBlock A_GLYPH;
	public static GlyphBlock E_GLYPH;
	public static GlyphBlock O_GLYPH;
	public static GlyphBlock T_GLYPH;
	public static GlyphBlock K_GLYPH;
	public static GlyphBlock J_GLYPH;
	public static GlyphBlock V_GLYPH;
	public static GlyphBlock S_GLYPH;
	public static GlyphBlock N_GLYPH;
	public static GlyphBlock P_GLYPH;
	public static GlyphBlock G_GLYPH;
	public static GlyphBase STOP_GLYPH;

	public static final GlyphFactory FACTORY = GlyphFactory.create();

	public static <T extends Block> T register(String id, T block) {
		Identifier blockId = Ochetgenyo.id(id);
		Registry.register(Registry.BLOCK, blockId, block);
		Registry.register(Registry.ITEM, blockId, new BlockItem(block, new FabricItemSettings().group(Ochetgenyo.ITEM_GROUP)));
		return block;
	}

	public static void register() {
		GLYPH_BASE = register("glyph_base", new GlyphBase());
		A_GLYPH = FACTORY.start().vowel().build("a");
		E_GLYPH = FACTORY.start().vowel(true).build("e");
		O_GLYPH = FACTORY.start().vowel(true).build("o");
		T_GLYPH = FACTORY.start().consonant(Orientation.RIGHT).build("t");
		K_GLYPH = FACTORY.start().consonant(Orientation.RIGHT).build("k");
		J_GLYPH = FACTORY.start().consonant(Orientation.RIGHT, true, T_GLYPH).build("j");
		V_GLYPH = FACTORY.start().consonant(Orientation.LEFT).build("v");
		S_GLYPH = FACTORY.start().consonant(Orientation.RIGHT).build("s");
		N_GLYPH = FACTORY.start().consonant(Orientation.LEFT).build("n");
		P_GLYPH = FACTORY.start().consonant(Orientation.RIGHT).build("p");
		G_GLYPH = FACTORY.start().consonant(Orientation.LEFT).build("g");
		STOP_GLYPH = FACTORY.start().stop().build(".");
		FACTORY.register(ID, ITEM_GROUP);
	}
}
