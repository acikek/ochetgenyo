package com.acikek.ochetgenyo.client;

import com.acikek.ochetgenyo.api.OchetgenyoAPI;
import com.acikek.ochetgenyo.block.OchetgenyoBlocks;
import net.fabricmc.api.ClientModInitializer;

public class OchetgenyoClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		OchetgenyoAPI.registerClientGlyphBehavior(OchetgenyoBlocks.FACTORY.getGlyphs());
	}
}
