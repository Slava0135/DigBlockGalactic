package slava0135.dbg;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class LootbugEntity extends AnimalEntity {
  protected LootbugEntity(EntityType<? extends AnimalEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  protected void initGoals() {
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
    this.goalSelector.add(3, new AnimalMateGoal(this, 1.0));
    this.goalSelector.add(4, new TemptGoal(this, 1.2, stack -> this.isBreedingItem(stack), false));
    this.goalSelector.add(5, new FollowParentGoal(this, 1.1));
    this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
    this.goalSelector.add(7, new LookAroundGoal(this));
  }

  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack.isIn(ItemTags.GOLD_ORES) || stack.isIn(ItemTags.GOLD_TOOL_MATERIALS)
        || stack.isIn(ItemTags.REPAIRS_GOLD_ARMOR) || stack.isOf(ModItems.NITRA);
  }

  @Override
  public boolean canSpawn(WorldView world) {
    return world.doesNotIntersectEntities(this) && world.isInHeightLimit(0);
  }

  @Override
  public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
    return ModEntities.LOOTBUG.create(world, SpawnReason.BREEDING);
  }

  public static DefaultAttributeContainer.Builder createLootbugAttributes() {
    return AnimalEntity.createAnimalAttributes().add(EntityAttributes.MAX_HEALTH, 20.0)
        .add(EntityAttributes.MOVEMENT_SPEED, 0.1);
  }

  @Override
  public int getMinAmbientSoundDelay() {
    return 120;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_CAT_PURR;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_CAT_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_CAT_DEATH;
  }

  @Override
  protected void playEatSound() {
    this.playSound(SoundEvents.ENTITY_CAT_EAT, 1.0F, 1.0F);
  }

  @Override
  protected void playStepSound(BlockPos pos, BlockState state) {
    // no sound, no memory
  }
}
