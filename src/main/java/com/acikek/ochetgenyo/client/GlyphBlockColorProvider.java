package com.acikek.ochetgenyo.client;

import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GlyphBlockColorProvider implements BlockColorProvider {

	public static Map<DyeColor, Pair<Integer, Integer>> HUES = new HashMap<>();

	static {
		HUES.put(DyeColor.RED, new Pair<>(0xB83748, 0x913C62));
	}

	@Override
	public int getColor(BlockState blockState, @Nullable BlockRenderView blockRenderView, @Nullable BlockPos blockPos, int i) {
		if (i < 0 || i > 1) {
			return 0;
		}
		DyeColor color = blockState.get(GlyphBlock.COLOR);
		Pair<Integer, Integer> pair = HUES.get(color);
		if (pair == null) {
			return color.getSignColor();
		}
		return i == 0 ? pair.getLeft() : pair.getRight();
	}
}
