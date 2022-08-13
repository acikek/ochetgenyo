package com.acikek.ochetgenyo;

import com.acikek.ochetgenyo.block.ModBlocks;
import com.acikek.ochetgenyo.item.GlyphChisel;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ochetgenyo implements ModInitializer {

	public static final String ID = "ochetgenyo";

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	public static final ItemGroup ITEM_GROUP = QuiltItemGroup.builder(id("main"))
			.icon(() -> new ItemStack(GlyphChisel.INSTANCE))
			.build();

	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("onkyo go ota {}", mod.metadata().name());
		ModBlocks.register();
		GlyphChisel.register();
	}
}
