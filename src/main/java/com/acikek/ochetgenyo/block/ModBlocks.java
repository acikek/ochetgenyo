package com.acikek.ochetgenyo.block;

import com.acikek.ochetgenyo.Ochetgenyo;
import com.acikek.ochetgenyo.block.glyph.ConsonantBlock;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import com.acikek.ochetgenyo.block.glyph.OrientableVowelBlock;
import com.acikek.ochetgenyo.block.glyph.VowelBlock;
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
	public static GlyphBlock OCHETGENYO_E_GLYPH;
	public static GlyphBlock OCHETGENYO_O_GLYPH;
	public static GlyphBlock OCHETGENYO_K_GLYPH;

	public static List<GlyphBlock> GLYPH_BLOCKS = new ArrayList<>();

	public static <T extends Block> T registerBlock(String id, T block) {
		Identifier blockId = Ochetgenyo.id(id);
		Registry.register(Registry.BLOCK, blockId, block);
		Registry.register(Registry.ITEM, blockId, new BlockItem(block, new QuiltItemSettings().group(Ochetgenyo.ITEM_GROUP)));
		return block;
	}

	public static GlyphBlock registerGlyph(GlyphBlock block) {
		GLYPH_BLOCKS.add(block);
		return registerBlock("ochetgenyo_" + block.character + "_glyph", block);
	}

	public static void register() {
		GLYPH_BASE = registerBlock("glyph_base", new GlyphBase());
		OCHETGENYO_A_GLYPH = registerGlyph(new VowelBlock('a'));
		OCHETGENYO_E_GLYPH = registerGlyph(new OrientableVowelBlock('e'));
		OCHETGENYO_O_GLYPH = registerGlyph(new OrientableVowelBlock('o'));
		OCHETGENYO_K_GLYPH = registerGlyph(new ConsonantBlock('k'));
	}
}
