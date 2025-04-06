package slava0135.dbg;

import java.util.function.Function;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ModItems {

  public static final Item NITRA = register("nitra", BoneMealItem::new, new Item.Settings());

  public static final RegistryKey<ItemGroup> MOD_ITEM_GROUP_KEY =
      RegistryKey.of(Registries.ITEM_GROUP.getKey(), ModIdentifier.of("item_group"));
  public static final ItemGroup MOD_ITEM_GROUP =
      FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.NITRA))
          .displayName(Text.translatable("itemGroup.dig-block-galactic")).build();

  public static Item register(String name, Function<Item.Settings, Item> itemFactory,
      Item.Settings settings) {
    // Create the item key.
    var itemKey = RegistryKey.of(RegistryKeys.ITEM, ModIdentifier.of(name));

    // Create the item instance.
    Item item = itemFactory.apply(settings.registryKey(itemKey));

    // Register the item.
    Registry.register(Registries.ITEM, itemKey, item);

    return item;
  }

  public static void initialize() {
    Registry.register(Registries.ITEM_GROUP, MOD_ITEM_GROUP_KEY, MOD_ITEM_GROUP);

    ItemGroupEvents.modifyEntriesEvent(MOD_ITEM_GROUP_KEY).register(itemGroup -> {
      itemGroup.add(ModItems.NITRA);
    });

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
        .register((itemGroup) -> itemGroup.add(ModItems.NITRA));
    CompostingChanceRegistry.INSTANCE.add(ModItems.NITRA, 1f);
    FuelRegistryEvents.BUILD.register((builder, context) -> {
      builder.add(ModItems.NITRA, 80 * 20); // Same as coal
    });
  }
}
