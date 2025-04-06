package slava0135.dbg;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModPotions {
  public static final Potion FIRE_POTION =
      Registry.register(Registries.POTION, ModIdentifier.of("fire"),
          new Potion("fire", new StatusEffectInstance(ModEffects.ON_FIRE, 1, 0)));
  public static final Potion LONG_FIRE_POTION =
      Registry.register(Registries.POTION, ModIdentifier.of("long_fire"),
          new Potion("fire", new StatusEffectInstance(ModEffects.ON_FIRE, 1, 1)));

  public static void initialize() {
    FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
      builder.registerPotionRecipe(
          // Input potion.
          Potions.AWKWARD,
          // Ingredient
          ModItems.NITRA,
          // Output potion.
          Registries.POTION.getEntry(FIRE_POTION));
    });
    FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
      builder.registerPotionRecipe(
          // Input potion.
          Registries.POTION.getEntry(FIRE_POTION),
          // Ingredient
          Items.REDSTONE,
          // Output potion.
          Registries.POTION.getEntry(LONG_FIRE_POTION));
    });
    FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
      builder.registerPotionRecipe(
          // Input potion.
          Potions.FIRE_RESISTANCE,
          // Ingredient
          Items.FERMENTED_SPIDER_EYE,
          // Output potion.
          Registries.POTION.getEntry(FIRE_POTION));
    });
    FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
      builder.registerPotionRecipe(
          // Input potion.
          Potions.LONG_FIRE_RESISTANCE,
          // Ingredient
          Items.FERMENTED_SPIDER_EYE,
          // Output potion.
          Registries.POTION.getEntry(LONG_FIRE_POTION));
    });
  }
}
