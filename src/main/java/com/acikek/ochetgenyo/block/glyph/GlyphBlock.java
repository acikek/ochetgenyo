package com.acikek.ochetgenyo.block.glyph;

import com.acikek.ochetgenyo.block.GlyphBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class GlyphBlock extends GlyphBase {

	public static final EnumProperty<DyeColor> COLOR = EnumProperty.of("color", DyeColor.class);

	public char character;

	public GlyphBlock(char character) {
		this.character = character;
		setDefaultState(getDefaultState().with(COLOR, DyeColor.WHITE));
	}

	public String getId() {
		return character + "_glyph";
	}

	public static BlockPos iterateToFinalPos(World world, BlockPos pos, Function<BlockPos, BlockPos> direction, Consumer<Character> function) {
		char lastChar = '\0';
		BlockPos next = direction.apply(pos);
		while (world.getBlockState(next).getBlock() instanceof GlyphBase glyphBase) {
			char nextChar = glyphBase instanceof GlyphBlock glyphBlock ? glyphBlock.character : ' ';
			if (lastChar == ' ' && nextChar == ' ') {
				break;
			}
			if (function != null) {
				function.accept(nextChar);
			}
			lastChar = nextChar;
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
	
	public static String processSentence(String sentence) {
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
			return changeState(world, pos, SoundEvents.ITEM_DYE_USE, player, handStack);
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
