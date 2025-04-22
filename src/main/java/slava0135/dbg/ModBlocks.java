package slava0135.dbg;

import java.util.function.Function;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
  public static final Block NITRA_BLOCK = register("nitra_block", Block::new,
      AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.PLING)
          .strength(0.3F).sounds(BlockSoundGroup.GLASS).luminance(state -> 3),
      true);
  public static final Block FLARE_BLOCK = register("flare_block", FlareBlock::new,
      AbstractBlock.Settings.create().noCollision().breakInstantly().luminance(state -> 14)
          .sounds(BlockSoundGroup.IRON).pistonBehavior(PistonBehavior.DESTROY),
      false);

  private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory,
      AbstractBlock.Settings settings, boolean shouldRegisterItem) {
    // Create a registry key for the block
    var blockKey = keyOfBlock(name);
    // Create the block instance
    var block = blockFactory.apply(settings.registryKey(blockKey));

    // Sometimes, you may not want to register an item for the block.
    // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
    if (shouldRegisterItem) {
      // Items need to be registered with a different type of registry key, but the ID
      // can be the same.
      var itemKey = keyOfItem(name);

      var blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
      Registry.register(Registries.ITEM, itemKey, blockItem);
    }

    return Registry.register(Registries.BLOCK, blockKey, block);
  }

  private static RegistryKey<Block> keyOfBlock(String name) {
    return RegistryKey.of(RegistryKeys.BLOCK, ModIdentifier.of(name));
  }

  private static RegistryKey<Item> keyOfItem(String name) {
    return RegistryKey.of(RegistryKeys.ITEM, ModIdentifier.of(name));
  }

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ModItems.MOD_ITEM_GROUP_KEY).register(itemGroup -> {
      itemGroup.add(ModBlocks.NITRA_BLOCK.asItem());
    });

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
      itemGroup.add(ModBlocks.NITRA_BLOCK.asItem());
    });
  }
}
