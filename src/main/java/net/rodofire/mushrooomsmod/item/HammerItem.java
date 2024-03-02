package net.rodofire.mushrooomsmod.item;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rodofire.mushrooomsmod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HammerItem extends ToolItem {
    double attackDamage;
    double attackSpeed;
    int maxcrushableblocks;
    public static int hammeruse = 0;

    public HammerItem(ToolMaterial material, int attackDamage, float attackSpeed, int maxcrushableblocks, Settings settings) {
        super(material, settings);
        this.attackDamage = attackDamage;
        double attackDamage1 = attackDamage + (float) Random.create().nextBetween(0, 2) / 2;
        this.attackSpeed = attackSpeed;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",  attackDamage1, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",  attackSpeed, EntityAttributeModifier.Operation.ADDITION));

        this.maxcrushableblocks = maxcrushableblocks;
    }


    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (hammeruse != 0 || world.isClient()) return mine( world, pos);
        System.out.println(hammeruse);
        return use(world, pos);
    }

    public boolean use( World world, BlockPos pos) {
        Block targetedblock = world.getBlockState(pos).getBlock();
        if (targetedblock.equals(ModBlocks.FORGE_BLOCK)) {
            hammeruse = 100;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            Inventory inventory = (Inventory) blockEntity;
            System.out.println(((Inventory) blockEntity).getStack(1));
            if (inventory.getStack(0).getCount() == 0) return false;
            else {
                int transfer;
                int crushed = inventory.getStack(1).getCount();
                int crush = inventory.getStack(0).getCount();
                if (64 - crushed <= crush) crush = crushed;
                ItemStack stack = inventory.getStack(0);
                ItemStack result = CrushableItems.getCrushed(stack);
                if (crush <= maxcrushableblocks) {
                    System.out.println("if1");
                    inventory.removeStack(0, transfer = Random.create().nextBetween(1, crush));
                    result.setCount(transfer);
                    inventory.setStack(1, result);
                } else {
                    System.out.println("if2");
                    inventory.removeStack(0, transfer = Random.create().nextBetween(1, maxcrushableblocks));
                    result.setCount(transfer);
                    inventory.setStack(1, result);
                }
            }
            return false;
        }
        return true;
    }

    public boolean mine(World world, BlockPos pos) {
        return !world.getBlockState(pos).isOf(ModBlocks.FORGE_BLOCK);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (hammeruse != 0) hammeruse--;
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.mushrooomsmod.hammer.attack_damage", attackDamage));
        tooltip.add(Text.translatable("tooltip.mushrooomsmod.hammer.usage"));
        super.appendTooltip(stack, world, tooltip, context);


    }
}
