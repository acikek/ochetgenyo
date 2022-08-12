package com.acikek.ochetgenyo.client;

import com.acikek.ochetgenyo.block.ModBlocks;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class OchetgenyoClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		for (GlyphBlock block : ModBlocks.GLYPH_BLOCKS) {
			BlockRenderLayerMap.put(RenderLayer.getCutoutMipped(), block);
		}
		ColorProviderRegistry.BLOCK.register(new GlyphBlockColorProvider(), ModBlocks.GLYPH_BLOCKS.toArray(new GlyphBlock[0]));
	}
}
