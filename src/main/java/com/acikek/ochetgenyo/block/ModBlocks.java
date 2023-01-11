package com.acikek.ochetgenyo.block;

import com.acikek.ochetgenyo.Ochetgenyo;
import com.acikek.ochetgenyo.block.glyph.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

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

	public static List<GlyphBlock> GLYPH_BLOCKS = new ArrayList<>();

	public static <T extends Block> T registerBlock(String id, T block) {
		Identifier blockId = Ochetgenyo.id(id);
		Registry.register(Registry.BLOCK, blockId, block);
		Registry.register(Registry.ITEM, blockId, new BlockItem(block, new FabricItemSettings().group(Ochetgenyo.ITEM_GROUP)));
		return block;
	}

	public static GlyphBlock registerGlyph(GlyphBlock block) {
		GLYPH_BLOCKS.add(block);
		return registerBlock(block.getId(), block);
	}

	public static void register() {
		GLYPH_BASE = registerBlock("glyph_base", new GlyphBase());
		A_GLYPH = registerGlyph(new VowelBlock('a'));
		E_GLYPH = registerGlyph(new OrientableVowelBlock('e'));
		O_GLYPH = registerGlyph(new OrientableVowelBlock('o'));
		T_GLYPH = registerGlyph(new ConsonantBlock('t', Orientation.RIGHT));
		K_GLYPH = registerGlyph(new ConsonantBlock('k', Orientation.RIGHT));
		J_GLYPH = registerGlyph(new ConsonantBlock('j', Orientation.RIGHT, true, List.of('t')));
		V_GLYPH = registerGlyph(new ConsonantBlock('v', Orientation.LEFT));
		S_GLYPH = registerGlyph(new ConsonantBlock('s', Orientation.RIGHT));
		N_GLYPH = registerGlyph(new ConsonantBlock('n', Orientation.LEFT));
		P_GLYPH = registerGlyph(new ConsonantBlock('p', Orientation.RIGHT));
		G_GLYPH = registerGlyph(new ConsonantBlock('g', Orientation.LEFT));
		STOP_GLYPH = registerGlyph(new StopBlock());
	}
}
