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

	public static final GlyphBlockColorProvider INSTANCE = new GlyphBlockColorProvider();

	public static Map<DyeColor, Pair<Integer, Integer>> HUES = new HashMap<>();

	static {
		HUES.put(DyeColor.RED, new Pair<>(0xBC3845, 0x933E61));
		HUES.put(DyeColor.ORANGE, new Pair<>(0xBC612B, 0x8C6246));
		HUES.put(DyeColor.YELLOW, new Pair<>(0xBC8F2B, 0x877F43));
		HUES.put(DyeColor.LIME, new Pair<>(0x659343, 0x5D7A5D));
		HUES.put(DyeColor.GREEN, new Pair<>(0x617F39, 0x5C704C));
		HUES.put(DyeColor.LIGHT_BLUE, new Pair<>(0x42B58F, 0x449B9B));
		HUES.put(DyeColor.CYAN, new Pair<>(0x369393, 0x497F78));
		HUES.put(DyeColor.BLUE, new Pair<>(0x5947A3, 0x4141A0));
		HUES.put(DyeColor.PURPLE, new Pair<>(0x7847A3, 0x754767));
		HUES.put(DyeColor.MAGENTA, new Pair<>(0x9B3F96, 0x854A8E));
		HUES.put(DyeColor.PINK, new Pair<>(0xB66495, 0x926DA0));
		HUES.put(DyeColor.WHITE, new Pair<>(0xBAD1B2, 0xA6ADAD));
		HUES.put(DyeColor.LIGHT_GRAY, new Pair<>(0xA1ADAD, 0xA59989));
		HUES.put(DyeColor.GRAY, new Pair<>(0x7D8484, 0x7C7084));
		HUES.put(DyeColor.BLACK, new Pair<>(0x4A4C4C, 0x474354));
		HUES.put(DyeColor.BROWN, new Pair<>(0x6B4E3B, 0x604A53));
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
