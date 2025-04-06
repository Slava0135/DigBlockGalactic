package slava0135.dbg;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
  public static final Identifier LOOTBUG_ID = ModIdentifier.of("lootbug");
  public static final EntityType<LootbugEntity> LOOTBUG =
      Registry.register(Registries.ENTITY_TYPE, LOOTBUG_ID,
          EntityType.Builder.create(LootbugEntity::new, SpawnGroup.CREATURE).dimensions(1.5f, 1f)
              .maxTrackingRange(10).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, LOOTBUG_ID)));

  public static void initialize() {
    FabricDefaultAttributeRegistry.register(LOOTBUG, LootbugEntity.createLootbugAttributes());
  }
}
