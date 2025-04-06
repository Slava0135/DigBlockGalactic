package slava0135.dbg;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();
		pack.addProvider(BlockLootTableProvider::new);
		pack.addProvider(ModFeatureProvider::new);
		pack.addProvider(EntityLootTableProvider::new);
	}

	// Here we build registries that will be passed to our data providers.
	// This allows you to add Java objects to dynamic registries for lookup during data generation
	// the methods used to "create" the registry content are called "bootstrap" methods
	// we are registering a boostrap method for configured features and placed features registries
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		DigBlockGalactic.LOGGER.info("build registry");
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE,
				ModFeatureProvider::bootstrapConfiguredFeatures);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE,
				ModFeatureProvider::bootstrapPlacedFeatures);
	}
}
