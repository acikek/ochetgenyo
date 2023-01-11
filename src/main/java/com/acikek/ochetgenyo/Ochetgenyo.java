package com.acikek.ochetgenyo;

import com.acikek.ochetgenyo.block.ModBlocks;
import com.acikek.ochetgenyo.item.GlyphChisel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
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

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(id("main"))
			.icon(() -> new ItemStack(GlyphChisel.INSTANCE))
			.build();

	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("onkyo go ota ochetgenyo");
		ModBlocks.register();
		GlyphChisel.register();
	}
}
