package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.GlyphBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

	public String gatherSentence(World world, BlockPos pos) {
		StringBuilder sentence = new StringBuilder("" + character);
		char lastChar = character;
		BlockPos next = pos.down();
		while (world.getBlockState(next).getBlock() instanceof GlyphBase glyphBase) {
			char nextChar = glyphBase instanceof GlyphBlock glyphBlock ? glyphBlock.character : ' ';
			if (lastChar == ' ' && nextChar == ' ') {
				break;
			}
			sentence.append(nextChar);
			lastChar = nextChar;
			next = next.down();
		}
		return sentence.toString().trim();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (hand != Hand.MAIN_HAND) {
			return ActionResult.PASS;
		}
		ItemStack handStack = player.getStackInHand(hand);
		if (handStack.isEmpty() && player.isSneaking() && !world.isClient()) {
			String sentence = gatherSentence(world, pos);
			player.sendMessage(Text.literal("'" + sentence + "'").styled(style -> style.withItalic(true)), false);
		}
		else if (handStack.getItem() instanceof DyeItem dyeItem) {
			world.setBlockState(pos, state.with(COLOR, dyeItem.getColor()));
			world.playSound(null, pos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
			if (!player.isCreative()) {
				handStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	public static DyeColor getContextualColor(BlockState state, BlockState above) {
		if (state != null && canConnect(state, above) && above.getBlock() instanceof GlyphBlock && above.get(COLOR) != DyeColor.WHITE) {
			return above.get(COLOR);
		}
		return DyeColor.WHITE;
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState above = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
		BlockState state = super.getPlacementState(ctx);
		return state.with(COLOR, getContextualColor(state, above));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(COLOR);
	}
}
