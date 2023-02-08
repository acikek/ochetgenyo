package com.acikek.ochetgenyo;

import com.acikek.ochetgenyo.block.OchetgenyoBlocks;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import com.acikek.ochetgenyo.item.GlyphChisel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ochetgenyo implements ModInitializer {

	public static final String ID = "ochetgenyo";

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(id("main"))
			.icon(() -> new ItemStack(GlyphChisel.INSTANCE))
			.build();

	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("onkyo go ota Ochetgenyo");
		OchetgenyoBlocks.register();
		GlyphChisel.register();
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> {
			entries.add(OchetgenyoBlocks.GLYPH_BASE);
			for (GlyphBlock glyphBlock : OchetgenyoBlocks.glyphBlocks) {
				entries.add(glyphBlock);
			}
			entries.add(GlyphChisel.INSTANCE);
		});
	}
}
