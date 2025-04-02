package slava0135.dbg;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModFeatureProvider extends FabricDynamicRegistryProvider {
  public static final String NITRA_BLOB_FEATURE = "nitra_blob";

  public ModFeatureProvider(FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public String getName() {
    return DigBlockGalactic.MOD_ID + "_feature_provider";
  }

  // This method is called when the data provider needs to provide its data
  // We are telling it to add all of the entries in the configured feature and placed feature
  // registries to the list of generated data
  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    entries.addAll(registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
    entries.addAll(registries.getOrThrow(RegistryKeys.PLACED_FEATURE));
  }

  private static RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String path) {
    return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id(path));
  }

  private static RegistryKey<PlacedFeature> placedFeatureKey(String path) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, id(path));
  }

  private static Identifier id(String path) {
    return Identifier.of(DigBlockGalactic.MOD_ID, path);
  }

  public static void bootstrapConfiguredFeatures(
      Registerable<ConfiguredFeature<?, ?>> registerable) {
    registerable.register(configuredFeatureKey(NITRA_BLOB_FEATURE),
        new ConfiguredFeature<>(DigBlockGalactic.NITRA_BLOB, DefaultFeatureConfig.INSTANCE));
  }

  private static List<PlacementModifier> modifiers(PlacementModifier countModifier,
      PlacementModifier heightModifier) {
    return List.of(countModifier, SquarePlacementModifier.of(), heightModifier,
        BiomePlacementModifier.of());
  }

  private static List<PlacementModifier> modifiersWithCount(int count,
      PlacementModifier heightModifier) {
    return modifiers(CountPlacementModifier.of(count), heightModifier);
  }

  // This one adds a placed feature to the placed feature registry
  // notice it creates a lookup to the configured feature registry.
  // This is required so a registry entry can be used in the PlacedFeature constructor.
  public static void bootstrapPlacedFeatures(Registerable<PlacedFeature> registerable) {
    RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup =
        registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

    registerable.register(placedFeatureKey(NITRA_BLOB_FEATURE),
        new PlacedFeature(lookup.getOptional(configuredFeatureKey(NITRA_BLOB_FEATURE)).get(),
            modifiersWithCount(100,
                HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(0)))));
  }
}
