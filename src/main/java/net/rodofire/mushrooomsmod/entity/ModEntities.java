package net.rodofire.mushrooomsmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rodofire.mushrooomsmod.MushrooomsMod;
import net.rodofire.mushrooomsmod.entity.custom.BoleteCowEntity;
import net.rodofire.mushrooomsmod.entity.custom.GrokiEntity;

public class ModEntities {
    public static final EntityType<GrokiEntity> GROKI = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MushrooomsMod.MOD_ID, "groki"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GrokiEntity::new).dimensions(EntityDimensions.fixed(0.9f, 1.45f)).build());

    public static final EntityType<BoleteCowEntity> BOLETE_COW = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MushrooomsMod.MOD_ID, "bolete_cow"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BoleteCowEntity::new).dimensions(EntityDimensions.fixed(1.3f, 1.8f)).build());
}
