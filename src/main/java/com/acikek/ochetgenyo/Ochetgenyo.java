package com.acikek.ochetgenyo;

import com.acikek.ochetgenyo.block.OchetgenyoBlocks;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import com.acikek.ochetgenyo.item.GlyphChisel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ochetgenyo implements ModInitializer {

	public static final String ID = "ochetgenyo";

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(GlyphChisel.INSTANCE))
			.build();

	public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, id("main"));

	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY.getValue(), ITEM_GROUP);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("onkyo go ota Ochetgenyo");

		OchetgenyoBlocks.register();
		GlyphChisel.register();
		registerItemGroup();

		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(entries -> {
			entries.add(OchetgenyoBlocks.GLYPH_BASE);
			for (GlyphBlock glyphBlock : OchetgenyoBlocks.glyphBlocks) {
				entries.add(glyphBlock);
			}
			entries.add(GlyphChisel.INSTANCE);
		});
	}
}
