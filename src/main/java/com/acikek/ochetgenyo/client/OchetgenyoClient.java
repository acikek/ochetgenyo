package com.acikek.ochetgenyo.client;

import com.acikek.ochetgenyo.block.ModBlocks;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class OchetgenyoClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		GlyphBlock[] glyphBlocks = ModBlocks.GLYPH_BLOCKS.toArray(new GlyphBlock[0]);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), glyphBlocks);
		ColorProviderRegistry.BLOCK.register(new GlyphBlockColorProvider(), glyphBlocks);
	}
}
