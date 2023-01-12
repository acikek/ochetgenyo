package com.acikek.ochetgenyo.api.impl;

import com.acikek.ochetgenyo.Ochetgenyo;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import com.acikek.ochetgenyo.client.GlyphBlockColorProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApiStatus.Internal
public class OchetgenyoAPIImpl {

	public static TagKey<Block> GLYPHS = TagKey.of(Registry.BLOCK_KEY, Ochetgenyo.id("glyphs"));

	public static List<GlyphBlock> getGlyphBlocks() {
		var list = Registry.BLOCK.getEntryList(GLYPHS)
				.orElseThrow(() -> new IllegalStateException("tag " + GLYPHS.id() + " not found"));
		List<GlyphBlock> blocks = new ArrayList<>();
		for (RegistryEntry<Block> blockEntry : list) {
			Block block = blockEntry.value();
			if (block instanceof GlyphBlock glyphBlock) {
				blocks.add(glyphBlock);
			}
			else {
				throw new IllegalStateException(block + " is not a glyph block");
			}
		}
		return blocks;
	}

	@Environment(EnvType.CLIENT)
	public static void registerClientGlyphBehavior(GlyphBlock... glyphBlocks) {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), glyphBlocks);
		ColorProviderRegistry.BLOCK.register(GlyphBlockColorProvider.INSTANCE, glyphBlocks);
	}

	@Environment(EnvType.CLIENT)
	public static void registerClientGlyphBehavior(Collection<GlyphBlock> glyphBlocks) {
		registerClientGlyphBehavior(glyphBlocks.toArray(new GlyphBlock[0]));
	}
}
