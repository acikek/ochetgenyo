package com.acikek.ochetgenyo.block;

import com.acikek.ochetgenyo.Ochetgenyo;
import com.acikek.ochetgenyo.api.glyph.GlyphBlocks;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class OchetgenyoBlocks {

	public static GlyphBase GLYPH_BASE = new GlyphBase();
	public static GlyphBlock A_GLYPH = GlyphBlocks.vowel("a");
	public static GlyphBlock E_GLYPH = GlyphBlocks.orientable("e");
	public static GlyphBlock O_GLYPH = GlyphBlocks.orientable("o");
	public static GlyphBlock T_GLYPH = GlyphBlocks.right("t");
	public static GlyphBlock K_GLYPH = GlyphBlocks.right("k");
	public static GlyphBlock J_GLYPH = GlyphBlocks.right("j", true, T_GLYPH);
	public static GlyphBlock V_GLYPH = GlyphBlocks.left("v");
	public static GlyphBlock S_GLYPH = GlyphBlocks.right("s");
	public static GlyphBlock N_GLYPH = GlyphBlocks.left("n");
	public static GlyphBlock P_GLYPH = GlyphBlocks.right("p");
	public static GlyphBlock G_GLYPH = GlyphBlocks.left("g");
	public static GlyphBase STOP_GLYPH = GlyphBlocks.stop(".");

	public static List<GlyphBlock> glyphBlocks = new ArrayList<>();

	public static <T extends Block> void register(String id, T block) {
		Identifier blockId = Ochetgenyo.id(id);
		Registry.register(Registry.BLOCK, blockId, block);
		Registry.register(Registry.ITEM, blockId, new BlockItem(block, new FabricItemSettings().group(Ochetgenyo.ITEM_GROUP)));
		if (block instanceof GlyphBlock glyphBlock) {
			glyphBlocks.add(glyphBlock);
		}
	}

	public static void register(GlyphBlock block) {
		register(block.getId(), block);
	}

	public static void register() {
		register("glyph_base", GLYPH_BASE);
		register(A_GLYPH);
		register(E_GLYPH);
		register(O_GLYPH);
		register(T_GLYPH);
		register(K_GLYPH);
		register(J_GLYPH);
		register(V_GLYPH);
		register(S_GLYPH);
		register(N_GLYPH);
		register(P_GLYPH);
		register(G_GLYPH);
		register("stop_glyph", STOP_GLYPH);
	}
}
