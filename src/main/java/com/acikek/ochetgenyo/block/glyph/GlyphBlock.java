package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.GlyphBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
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

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GlyphBlock extends GlyphBase {

	public static final EnumProperty<DyeColor> COLOR = EnumProperty.of("color", DyeColor.class);

	/**
	 * The romanized phoneme.
	 */
	public String phoneme;

	public GlyphBlock(String phoneme) {
		this.phoneme = phoneme;
		setDefaultState(getDefaultState().with(COLOR, DyeColor.WHITE));
	}

	/**
	 * @return the block ID path.
	 */
	public String getId() {
		return phoneme + "_glyph";
	}

	public static BlockPos iterateToFinalPos(World world, BlockPos pos, Function<BlockPos, BlockPos> direction, Consumer<String> function) {
		String lastSound = "";
		BlockPos next = direction.apply(pos);
		while (world.getBlockState(next).getBlock() instanceof GlyphBase glyphBase) {
			String nextSound = glyphBase instanceof GlyphBlock glyphBlock ? glyphBlock.phoneme : " ";
			if (lastSound.equals(" ") && nextSound.equals(" ")) {
				break;
			}
			if (function != null) {
				function.accept(nextSound);
			}
			lastSound = nextSound;
			next = direction.apply(next);
		}
		return next;
	}

	public static String gatherSentence(World world, BlockPos pos) {
		StringBuilder sentence = new StringBuilder();
		BlockPos topPos = iterateToFinalPos(world, pos, BlockPos::up, null);
		iterateToFinalPos(world, topPos, BlockPos::down, sentence::append);
		return sentence.toString().trim();
	}

	public String processSentence(String sentence) {
		return sentence
				.replace("tj", "ch")
				.replace("j", "y");
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (hand != Hand.MAIN_HAND) {
			return ActionResult.PASS;
		}
		ItemStack handStack = player.getStackInHand(hand);
		if (handStack.isEmpty() && player.isSneaking()) {
			if (!world.isClient()) {
				String sentence = processSentence(gatherSentence(world, pos));
				player.sendMessage(Text.literal("'" + sentence + "'").styled(style -> style.withItalic(true)), false);
			}
			return ActionResult.SUCCESS;
		}
		else if (handStack.getItem() instanceof DyeItem dyeItem && state.get(COLOR) != dyeItem.getColor()) {
			world.setBlockState(pos, state.with(COLOR, dyeItem.getColor()));
			return handlePlayerInteraction(world, pos, SoundEvents.ITEM_DYE_USE, player, handStack);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public BlockState update(BlockState state, BlockState above, boolean connectAbove, BlockState below, boolean connectBelow, boolean isPlacement) {
		BlockState newState = super.update(state, above, connectAbove, below, connectBelow, isPlacement);
		if (isPlacement && connectAbove && above.getBlock() instanceof GlyphBlock) {
			newState = newState.with(COLOR, above.get(COLOR));
		}
		return newState;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(COLOR);
	}
}
