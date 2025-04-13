package slava0135.dbg;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
	protected BlockLootTableProvider(FabricDataOutput dataOutput,
			CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		var enchantments = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
		this.addDrop(ModBlocks.NITRA_BLOCK, block -> this.dropsWithSilkTouch(block,
				(LootPoolEntry.Builder<?>) this.applyExplosionDecay(block,
						ItemEntry.builder(ModItems.NITRA)
								.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 4.0F)))
								.apply(ApplyBonusLootFunction
										.uniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
								.apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 4))))));
		this.addDrop(ModBlocks.FLARE_BLOCK, block -> this.drops(ModItems.FLARE));
	}
}
