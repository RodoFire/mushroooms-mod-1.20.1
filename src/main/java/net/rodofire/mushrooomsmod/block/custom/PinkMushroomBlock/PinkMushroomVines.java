package net.rodofire.mushrooomsmod.block.custom.PinkMushroomBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.rodofire.mushrooomsmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public interface PinkMushroomVines {
    VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12.0, 16, 12);
    BooleanProperty BERRIES = Properties.BERRIES;


    static ActionResult pickBerries(@Nullable Entity picker, BlockState state, World world, BlockPos pos) {
        if (state.get(BERRIES).booleanValue()) {
            Block.dropStack(world, pos, new ItemStack(ModItems.PINK_MUSHROOM_VINES_ITEM, 1));
            float f = MathHelper.nextBetween(world.random, 0.8f, 1.2f);
            world.playSound(null, pos, SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, f);
            BlockState blockState = state.with(BERRIES, false);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(picker, blockState));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    static boolean hasBerries(BlockState state) {
        return state.contains(BERRIES) && state.get(BERRIES);
    }

    static ToIntFunction<BlockState> getLuminanceSupplier(int luminance) {
        return state -> state.get(Properties.BERRIES) ? luminance : 0;
    }
}
