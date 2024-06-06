package net.rodofire.mushrooomsmod.world.features.configuredfeatures.custom;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.rodofire.mushrooomsmod.util.WorldGenUtil;
import net.rodofire.mushrooomsmod.world.features.config.ModSimpleBlockFeatureConfig;

public class BigCrystal extends Feature<ModSimpleBlockFeatureConfig> {

    public BigCrystal(Codec<ModSimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }


    @Override
    public boolean generate(FeatureContext<ModSimpleBlockFeatureConfig> context) {
        BlockPos pos = context.getOrigin();
        BlockPos secondpos = pos.add(Random.create().nextBetween(1, 16) * WorldGenUtil.getRandomOpposite(), Random.create().nextBetween(6, 30) *  WorldGenUtil.getRandomOpposite(), Random.create().nextBetween(1, 16) *  WorldGenUtil.getRandomOpposite());

        ModSimpleBlockFeatureConfig config = context.getConfig();
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        BlockState state = config.blockprovider.get(random, pos);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        mutable.set(pos);

        BlockPos middlestate = new BlockPos((secondpos.getX() + pos.getX()) / 2 , (secondpos.getY() + pos.getY()) / 2,( secondpos.getZ() + pos.getZ()) / 2);
        BlockState state1 = world.getBlockState(pos);
        BlockState state2 = world.getBlockState(middlestate);

        Direction direction;
        if(secondpos.getY() - pos.getY() > 0) direction = Direction.DOWN;
        else direction = Direction.UP;

        if (!state2.isAir()) return false;
        if (state1.isAir() && world.getBlockState(mutable.down()).isAir() && world.getBlockState(mutable.up()).isAir()) return false;

        //create a round base
        int baselarge = Random.create().nextBetween(2, 5);
        for (int i = 1; i <= baselarge; i++) {
            for (float j = (float) -Math.PI; j < Math.PI; j += (float) (Math.PI / (4 * i))) {
                int x = (int) ((i) * Math.cos(j));
                int z = (int) ((i) * Math.sin(j));
                mutable.set(pos, x, 0, z);
                world.setBlockState(mutable, state, 2);

                WorldGenUtil.drawLine(world, mutable, secondpos, state);
            }
        }
        WorldGenUtil.generateHalfSphere(world, random, baselarge, pos, direction, state);
        return true;
    }
}
