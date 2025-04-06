package slava0135.dbg;

import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class EntityLootTableProvider extends FabricEntityLootTableProvider {

	protected EntityLootTableProvider(FabricDataOutput output,
			@NotNull CompletableFuture<WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generate() {
		this.register(ModEntities.LOOTBUG,
				LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
						.with(ItemEntry.builder(ModItems.NITRA)
								.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(6.0F, 16.0F)))
								.apply(EnchantedCountIncreaseLootFunction.builder(this.registries,
										UniformLootNumberProvider.create(0.0F, 1.0F)))))
						.pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
								.with(ItemEntry.builder(Items.GOLD_NUGGET)
										.apply(
												SetCountLootFunction.builder(UniformLootNumberProvider.create(6.0F, 16.0F)))
										.apply(EnchantedCountIncreaseLootFunction.builder(this.registries,
												UniformLootNumberProvider.create(0.0F, 1.0F))))));
	}
}
