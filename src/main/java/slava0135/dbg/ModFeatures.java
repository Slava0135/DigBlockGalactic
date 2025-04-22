package slava0135.dbg;

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

public class ModFeatures {
  public static final Identifier NITRA_BLOB_FEATURE_KEY = ModIdentifier.of("nitra_blob");
  public static final Identifier ROOF_WEB_FEATURE_KEY = ModIdentifier.of("roof_web");

  public static final Feature<DefaultFeatureConfig> NITRA_BLOB_FEATURE =
      registerFeature(NITRA_BLOB_FEATURE_KEY, new NitraBlobFeature(DefaultFeatureConfig.CODEC));
  public static final Feature<DefaultFeatureConfig> ROOF_WEB_FEATURE =
      registerFeature(ROOF_WEB_FEATURE_KEY, new RoofWebFeature(DefaultFeatureConfig.CODEC));

  private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(
      Identifier identifier, F feature) {
    return Registry.register(Registries.FEATURE, identifier, feature);
  }

  public static void initialize() {
    BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_ORES,
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, NITRA_BLOB_FEATURE_KEY));
    BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
        GenerationStep.Feature.UNDERGROUND_DECORATION,
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, ROOF_WEB_FEATURE_KEY));
  }
}
