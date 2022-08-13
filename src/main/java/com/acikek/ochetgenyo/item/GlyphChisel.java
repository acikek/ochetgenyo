package com.acikek.ochetgenyo.item;

import com.acikek.ochetgenyo.Ochetgenyo;
import com.acikek.ochetgenyo.block.GlyphBase;
import com.acikek.ochetgenyo.block.ModBlocks;
import com.acikek.ochetgenyo.block.glyph.GlyphBlock;
import com.acikek.ochetgenyo.block.glyph.OrientableVowelBlock;
import com.acikek.ochetgenyo.block.glyph.Orientation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class GlyphChisel extends Item {

	public static final GlyphChisel INSTANCE = new GlyphChisel(new QuiltItemSettings()
			.group(Ochetgenyo.ITEM_GROUP)
			.maxCount(1));

	public GlyphChisel(Settings settings) {
		super(settings);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return false;
	}

	public static GlyphBlock getNextBlock(GlyphBase base) {
		if (base instanceof GlyphBlock glyphBlock) {
			int index = ModBlocks.GLYPH_BLOCKS.indexOf(glyphBlock) + 1;
			return ModBlocks.GLYPH_BLOCKS.get(index >= ModBlocks.GLYPH_BLOCKS.size() ? 0 : index);
		}
		return ModBlocks.GLYPH_BLOCKS.get(0);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockState state = context.getWorld().getBlockState(context.getBlockPos());
		SoundEvent event = null;
		boolean sneaking = context.getPlayer() != null && context.getPlayer().isSneaking();
		if (sneaking && state.getBlock() instanceof OrientableVowelBlock) {
			Orientation newOrientation = state.get(OrientableVowelBlock.ORIENTATION) == Orientation.LEFT ? Orientation.RIGHT : Orientation.LEFT;
			context.getWorld().setBlockState(context.getBlockPos(), state.with(OrientableVowelBlock.ORIENTATION, newOrientation));
			event = SoundEvents.ITEM_DYE_USE;
		}
		else if (!sneaking && state.getBlock() instanceof GlyphBase glyphBase) {
			GlyphBlock nextBlock = getNextBlock(glyphBase);
			BlockState nextState = nextBlock.getDefaultState()
					.with(GlyphBlock.FACING, state.get(GlyphBlock.FACING))
					.with(GlyphBlock.COLOR, state.contains(GlyphBlock.COLOR)
							? state.get(GlyphBlock.COLOR)
							: GlyphBlock.getContextualColor(state, context.getWorld().getBlockState(context.getBlockPos().up())));
			context.getWorld().setBlockState(context.getBlockPos(), nextBlock.update(context.getWorld(), nextState, context.getBlockPos()));
			event = SoundEvents.BLOCK_GRINDSTONE_USE;
		}
		if (event != null) {
			context.getWorld().playSound(null, context.getBlockPos(), event, SoundCategory.BLOCKS, 1.0f, 1.0f);
			return ActionResult.SUCCESS;
		}
		return super.useOnBlock(context);
	}

	public static void register() {
		Registry.register(Registry.ITEM, Ochetgenyo.id("glyph_chisel"), INSTANCE);
	}
}
