package slava0135.dbg;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigBlockGalactic implements ModInitializer {
	public static final String MOD_ID = "dig-block-galactic";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier NITRA_BLOB_FEATURE_ID = Identifier.of(MOD_ID, "nitra_blob");
	public static final NitraBlobFeature NITRA_BLOB_FEATURE =
			new NitraBlobFeature(DefaultFeatureConfig.CODEC);
	public static final ConfiguredFeature<DefaultFeatureConfig, NitraBlobFeature> NITRA_BLOB_FEATURE_CONFIGURED =
			new ConfiguredFeature<>(NITRA_BLOB_FEATURE, DefaultFeatureConfig.INSTANCE);

	public static final PlacedFeature NITRA_BLOB_FEATURE_PLACED =
			new PlacedFeature(RegistryEntry.of(NITRA_BLOB_FEATURE_CONFIGURED), List.of());

	@Override
	public void onInitialize() {
		LOGGER.info("load items");
		ModItems.initialize();

		LOGGER.info("load blocks");
		ModBlocks.initialize();

		LOGGER.info("load features");
		Registry.register(Registries.FEATURE, NITRA_BLOB_FEATURE_ID, NITRA_BLOB_FEATURE);

		// LOGGER.info("load configured features");
		// var registry = BuiltinRegistries.createWrapperLookup()
		// 		.getEntryOrThrow(RegistryKeys.CONFIGURED_FEATURE).value();
		// Registry.register(registry, NITRA_BLOB_FEATURE_ID, NITRA_BLOB_FEATURE_CONFIGURED);
		// LOGGER.info("load placed features");
		// BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
		// 		GenerationStep.Feature.UNDERGROUND_ORES,
		// 		RegistryKey.of(RegistryKeys.PLACED_FEATURE, NITRA_BLOB_FEATURE_ID));
	}
}
