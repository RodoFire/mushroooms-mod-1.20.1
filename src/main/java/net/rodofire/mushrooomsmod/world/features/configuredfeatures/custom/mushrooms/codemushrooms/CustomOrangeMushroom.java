package net.rodofire.mushrooomsmod.world.features.configuredfeatures.custom.mushrooms.codemushrooms;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.rodofire.mushrooomsmod.block.ModBlocks;

public abstract class CustomOrangeMushroom extends Feature<HugeMushroomFeatureConfig> {

    public CustomOrangeMushroom(Codec<HugeMushroomFeatureConfig> configCodec) {
        super(configCodec);
    }

    protected boolean canGenerate(StructureWorldAccess world, BlockPos pos, int height, int large, BlockPos.Mutable mutablePos, HugeMushroomFeatureConfig config) {
        for (int i = -1; i <= 1; ++i) {
            for (int k = -1; k <= 1; ++k) {
                for (int j = 1; j <= height; ++j) {
                    BlockState blockState = world.getBlockState(pos.add(i, j,k));
                    if (!blockState.isIn(BlockTags.LEAVES) && !blockState.isAir())
                        return false;
                }
            }
        }
        for (int i = -large; i <= large; i++) {
            for (int j = -large; j <= large; ++j) {
                for (int k = (int) (height - 2.4 * large); k <= height + 3; ++k) {
                    BlockState blockState = world.getBlockState(pos.add(i, k, j));
                    if (!blockState.isIn(BlockTags.LEAVES) && !blockState.isAir())
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean generate(FeatureContext<HugeMushroomFeatureConfig> context) {
        BlockPos.Mutable mutable;
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        HugeMushroomFeatureConfig hugeMushroomFeatureConfig = context.getConfig();
        mutable = new BlockPos.Mutable();

        if (world.getBlockState(blockPos.east()).isOf(ModBlocks.ORANGE_MUSHROOM) || world.getBlockState(blockPos.north()).isOf(ModBlocks.ORANGE_MUSHROOM) || world.getBlockState(blockPos.south()).isOf(ModBlocks.ORANGE_MUSHROOM) || world.getBlockState(blockPos.west()).isOf(ModBlocks.ORANGE_MUSHROOM)) {

            int height = Random.create().nextBetween(14, 30);
            int large = Random.create().nextBetween(4, (int) (height / 2.4));

            if (!this.canGenerate(world, blockPos, height, large, mutable, hugeMushroomFeatureConfig)) {
                return false;
            }

            this.generateGiantCap(world, random, blockPos, height, mutable, hugeMushroomFeatureConfig, large);
            this.generateGiantTrunk(world, random, blockPos, mutable, height, hugeMushroomFeatureConfig);
            return true;
        }
        int height = Random.create().nextBetween(6, 14);
        int large = Random.create().nextBetween(2, 4);

        if (!this.canGenerate(world, blockPos, height, large, mutable, hugeMushroomFeatureConfig)) {
            return false;
        }

        this.generateCap(world, random, blockPos, height, mutable, hugeMushroomFeatureConfig, large);
        this.generateTrunk(world, random, blockPos, mutable, height, hugeMushroomFeatureConfig);
        return true;
    }

    protected abstract void generateGiantTrunk(StructureWorldAccess world, Random random, BlockPos pos, BlockPos.Mutable mutable, int height, HugeMushroomFeatureConfig config);

    protected abstract void generateTrunk(StructureWorldAccess world, Random random, BlockPos pos, BlockPos.Mutable mutable, int height, HugeMushroomFeatureConfig config);

    protected abstract void generateGiantCap(StructureWorldAccess var1, Random var2, BlockPos var3, int var4, BlockPos.Mutable var5, HugeMushroomFeatureConfig var6, int large);

    protected abstract void generateCap(StructureWorldAccess var1, Random var2, BlockPos var3, int var4, BlockPos.Mutable var5, HugeMushroomFeatureConfig var6, int large);

}
