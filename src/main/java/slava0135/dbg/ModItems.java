package slava0135.dbg;

import java.util.function.Function;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
  public static final Item NITRA = register("nitra", Item::new, new Item.Settings());

  public static Item register(String name, Function<Item.Settings, Item> itemFactory,
      Item.Settings settings) {
    // Create the item key.
    var itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(DigBlockGalactic.MOD_ID, name));

    // Create the item instance.
    Item item = itemFactory.apply(settings.registryKey(itemKey));

    // Register the item.
    Registry.register(Registries.ITEM, itemKey, item);

    return item;
  }

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
        .register((itemGroup) -> itemGroup.add(ModItems.NITRA));
    CompostingChanceRegistry.INSTANCE.add(ModItems.NITRA, 1f);
    FuelRegistryEvents.BUILD.register((builder, context) -> {
      builder.add(ModItems.NITRA, 80 * 20); // Same as coal
    });
  }
}
