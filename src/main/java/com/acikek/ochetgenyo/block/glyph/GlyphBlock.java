package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.GlyphBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class GlyphBlock extends GlyphBase {

	public static final EnumProperty<DyeColor> COLOR = EnumProperty.of("color", DyeColor.class);

	public char character;

	public GlyphBlock(char character) {
		this.character = character;
		setDefaultState(getStateManager().getDefaultState().with(COLOR, DyeColor.WHITE));
	}

	public String getId() {
		return character + "_glyph";
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (hand != Hand.MAIN_HAND) {
			return ActionResult.PASS;
		}
		ItemStack handStack = player.getStackInHand(hand);
		if (handStack.getItem() instanceof DyeItem dyeItem) {
			world.setBlockState(pos, state.with(COLOR, dyeItem.getColor()));
			world.playSound(null, pos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative()) {
				handStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(COLOR);
	}
}
