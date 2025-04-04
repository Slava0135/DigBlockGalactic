package slava0135.dbg;

import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class OnFireEffect extends InstantStatusEffect {
  private static int BASE_TIME = 45;

  public OnFireEffect(StatusEffectCategory statusEffectCategory, int color) {
    super(statusEffectCategory, color);
  }

  @Override
  public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
    entity.setOnFireFor((amplifier + 1) * BASE_TIME);
    return true;
  }

  @Override
  public void applyInstantEffect(ServerWorld world, @Nullable Entity effectEntity,
      @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
    target.setOnFireFor((amplifier + 1) * BASE_TIME);
  }
}
