package slava0135.dbg;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FlareEntity extends ThrownItemEntity {
  private final ProjectileDeflection FLARE = (projectile, hitEntity, random) -> {
		float f = 170.0F + random.nextFloat() * 20.0F;
		projectile.setVelocity(projectile.getVelocity().multiply(-0.1));
		projectile.setYaw(projectile.getYaw() + f);
		projectile.lastYaw += f;
		projectile.velocityDirty = true;
  };

  public FlareEntity(EntityType<? extends FlareEntity> entityType, World world) {
    super(entityType, world);
  }

  public FlareEntity(World world, LivingEntity owner, ItemStack stack) {
    super(ModEntities.FLARE, owner, world, stack);
  }

  public FlareEntity(World world, double x, double y, double z, ItemStack stack) {
    super(ModEntities.FLARE, x, y, z, world, stack);
  }

  @Override
  public void handleStatus(byte status) {
    super.handleStatus(status);
  }

  @Override
  protected void onEntityHit(EntityHitResult entityHitResult) {
    this.deflect(FLARE, getControllingPassenger(), getOwner(), false);
  }

  @Override
  protected void onBlockHit(BlockHitResult hitResult) {
    super.onBlockHit(hitResult);
    if (this.getWorld() instanceof ServerWorld serverWorld) {
      if (hitResult.getSide() != Direction.UP) {
        this.deflect(FLARE, getControllingPassenger(), getOwner(), false);
      } else {
        if (this.getBlockStateAtPos().isAir()
            && Block.sideCoversSmallSquare(serverWorld, getBlockPos().down(), Direction.UP)) {
          serverWorld.setBlockState(this.getBlockPos(), ModBlocks.FLARE_BLOCK.getDefaultState());
        } else {
          this.dropItem(serverWorld, getDefaultItem());
        }
        this.discard();
      }
    }
  }

  @Override
  protected Item getDefaultItem() {
    return ModItems.FLARE;
  }
}
