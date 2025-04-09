package slava0135.dbg;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class FlareEntity extends ThrownItemEntity {
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
    super.onEntityHit(entityHitResult);
  }

  @Override
  protected void onCollision(HitResult hitResult) {
    super.onCollision(hitResult);
    if (this.getWorld() instanceof ServerWorld serverWorld) {
      if (this.getBlockStateAtPos().isAir()) {
        serverWorld.setBlockState(this.getBlockPos(), Blocks.TORCH.getDefaultState(),
            Block.NOTIFY_LISTENERS);
      } else {
        this.dropItem(serverWorld, getDefaultItem());
      }
      this.discard();
    }
  }

  @Override
  protected Item getDefaultItem() {
    return ModItems.FLARE;
  }
}
