package slava0135.dbg;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
  public static final RegistryEntry<StatusEffect> ON_FIRE = Registry.registerReference(
      Registries.STATUS_EFFECT, Identifier.of(DigBlockGalactic.MOD_ID, "on_fire"),
      new OnFireEffect(StatusEffectCategory.HARMFUL, 16750848));

  public static void initialize() {}
}
