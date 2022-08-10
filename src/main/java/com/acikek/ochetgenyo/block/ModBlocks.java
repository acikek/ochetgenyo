package com.acikek.ochetgenyo.block;

import com.acikek.ochetgenyo.Ochetgenyo;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

	public static GlyphBase GLYPH_BASE;
	public static GlyphBlock OCHETGENYO_A_GLYPH;
	public static GlyphBlock OCHETGENYO_K_GLYPH;

	public static List<GlyphBlock> GLYPH_BLOCKS = new ArrayList<>();

	public static <T extends Block> T registerBlock(String id, T block) {
		Identifier blockId = Ochetgenyo.id(id);
		Registry.register(Registry.BLOCK, blockId, block);
		Registry.register(Registry.ITEM, blockId, new BlockItem(block, new QuiltItemSettings().group(Ochetgenyo.ITEM_GROUP)));
		return block;
	}

	public static GlyphBlock registerGlyphBlock(char character, boolean consonant) {
		GlyphBlock block = consonant ? new ConsonantBlock(character) : new VowelBlock(character);
		GLYPH_BLOCKS.add(block);
		return registerBlock("ochetgenyo_" + character + "_glyph", block);
	}

	public static void register() {
		GLYPH_BASE = registerBlock("glyph_base", new GlyphBase());
		OCHETGENYO_A_GLYPH = registerGlyphBlock('a', false);
		OCHETGENYO_K_GLYPH = registerGlyphBlock('k', true);
	}
}
