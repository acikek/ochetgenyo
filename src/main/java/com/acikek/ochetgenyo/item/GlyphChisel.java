package com.acikek.ochetgenyo.item;

import com.acikek.ochetgenyo.Ochetgenyo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class GlyphChisel extends Item {

	public static final GlyphChisel INSTANCE = new GlyphChisel(new QuiltItemSettings()
			.group(Ochetgenyo.ITEM_GROUP)
			.maxCount(1));

	public GlyphChisel(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return super.useOnBlock(context);
	}

	public static void register() {
		Registry.register(Registry.ITEM, Ochetgenyo.id("glyph_chisel"), INSTANCE);
	}
}
