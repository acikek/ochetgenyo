package com.acikek.ochetgenyo.api.impl.glyph;

import com.acikek.ochetgenyo.api.glyph.GlyphBuilder;
import com.acikek.ochetgenyo.api.glyph.GlyphFactory;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class GlyphFactoryImpl implements GlyphFactory {

	List<GlyphBlock> blocks = new ArrayList<>();

	@Override
	public GlyphBuilder start() {
		return GlyphBuilder.create(this);
	}

	@Override
	public void addGlyph(GlyphBlock block) {
		blocks.add(block);
	}

	@Override
	public List<GlyphBlock> getGlyphs() {
		return blocks;
	}

	@Override
	public void register(String namespace, ItemGroup group) {
		for (GlyphBlock block : blocks) {
			Identifier id = new Identifier(namespace, block.getId());
			Registry.register(Registry.BLOCK, id, block);
			Registry.register(Registry.ITEM, id, new BlockItem(block, new FabricItemSettings().group(group)));
		}
	}
}
