package slava0135.dbg;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

public class ModEntities {
  public static final Identifier LOOTBUG_ID = ModIdentifier.of("lootbug");
  public static final Identifier FLARE_ID = ModIdentifier.of("flare");

  public static final EntityType<LootbugEntity> LOOTBUG = register(LOOTBUG_ID, EntityType.Builder
      .create(LootbugEntity::new, SpawnGroup.CREATURE).dimensions(1.5f, 1f).maxTrackingRange(10));
  public static final EntityType<FlareEntity> FLARE =
      register(FLARE_ID, EntityType.Builder.<FlareEntity>create(FlareEntity::new, SpawnGroup.MISC)
          .dropsNothing().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));

  private static <T extends Entity> EntityType<T> register(Identifier id,
      EntityType.Builder<T> builder) {
    return Registry.register(Registries.ENTITY_TYPE, id,
        builder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, id)));
  }

  public static void initialize() {
    FabricDefaultAttributeRegistry.register(LOOTBUG, LootbugEntity.createLootbugAttributes());
    SpawnRestriction.register(LOOTBUG, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LootbugEntity::canSpawn);
    // TODO: add mobs to biome features
  }
}
