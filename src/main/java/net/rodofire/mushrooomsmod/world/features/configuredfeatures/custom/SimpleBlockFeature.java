package net.rodofire.mushrooomsmod.world.features.configuredfeatures.custom;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.rodofire.mushrooomsmod.world.features.config.ModSimpleBlockFeatureConfig;

public class SimpleBlockFeature extends Feature<ModSimpleBlockFeatureConfig> implements FeatureConfig {
    public SimpleBlockFeature(Codec<ModSimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<ModSimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        BlockState blockState = context.getConfig().blockprovider.get(random, pos);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 81; i++) {
            mutable.set(pos).move(Random.create().nextBetween(-9, 9), Random.create().nextBetween(-9, 9), Random.create().nextBetween(-9, 9));
            if (!world.getBlockState(mutable).isAir() || !blockState.canPlaceAt(world, mutable)) continue;
            world.setBlockState(mutable, blockState, 2);
        }
        return true;
    }
}
