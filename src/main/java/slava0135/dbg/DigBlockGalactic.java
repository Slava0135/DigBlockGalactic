package slava0135.dbg;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigBlockGalactic implements ModInitializer {
	public static final String MOD_ID = "dig-block-galactic";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Feature<DefaultFeatureConfig> NITRA_BLOB =
			registerFeature(Identifier.of(MOD_ID, ModFeatureProvider.NITRA_BLOB_FEATURE),
					new NitraBlobFeature(DefaultFeatureConfig.CODEC));

	private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(
			Identifier identifier, F feature) {
		return Registry.register(Registries.FEATURE, identifier, feature);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("load items");
		ModItems.initialize();

		LOGGER.info("load blocks");
		ModBlocks.initialize();

		LOGGER.info("load placed features");
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(RegistryKeys.PLACED_FEATURE,
						Identifier.of(DigBlockGalactic.MOD_ID, ModFeatureProvider.NITRA_BLOB_FEATURE)));
	}
}
