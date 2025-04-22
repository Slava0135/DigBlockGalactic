package slava0135.dbg;

import org.joml.Math;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SideShapeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/// 432101234
/// =========
/// XXXXXXXXX
/// -XXXXXXX-
/// ---XXX---
public class RoofWebFeature extends Feature<DefaultFeatureConfig> {

  public RoofWebFeature(Codec<DefaultFeatureConfig> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
    var structureWorldAccess = context.getWorld();
    var blockPos = context.getOrigin();
    var random = context.getRandom();

    if (!placeWebStripe(structureWorldAccess, blockPos, stripeSize(0))) {
      return false;
    }

    var length = 5;
    var horizontalSlope = generateSlope(random, length);
    if (random.nextBoolean()) {
      for (var i = 1; i < length; i++) {
        horizontalSlope[i] *= -1;
      }
    }
    // actually, halves were supposed to remain in one "plane", but by accident i made them form a curve (which is cooler)... 
    if (random.nextBoolean()) {
      for (var x = 1; x < length; x++) {
        var z = horizontalSlope[x];
        if (!placeWebStripe(structureWorldAccess, blockPos.add(x, 0, z), stripeSize(x))) {
          break;
        }
      }
      for (var x = 1; x < length; x++) {
        var z = horizontalSlope[x];
        if (!placeWebStripe(structureWorldAccess, blockPos.add(-x, 0, z), stripeSize(x))) {
          break;
        }
      }
    } else {
      for (var z = 1; z < length; z++) {
        var x = horizontalSlope[z];
        if (!placeWebStripe(structureWorldAccess, blockPos.add(x, 0, z), stripeSize(z))) {
          break;
        }
      }
      for (var z = 1; z < length; z++) {
        var x = horizontalSlope[z];
        if (!placeWebStripe(structureWorldAccess, blockPos.add(x, 0, -z), stripeSize(z))) {
          break;
        }
      }
    }

    return true;
  }

  private boolean isSurface(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
    var state = structureWorldAccess.getBlockState(blockPos);
    return state.isSideSolid(structureWorldAccess, blockPos, Direction.DOWN, SideShapeType.FULL);
  }

  private void placeWeb(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
    structureWorldAccess.setBlockState(blockPos, Blocks.COBWEB.getDefaultState(),
        Block.NOTIFY_LISTENERS);
  }

  private boolean placeWebStripe(StructureWorldAccess structureWorldAccess, BlockPos blockPos,
      int size) {
    DigBlockGalactic.LOGGER.info(blockPos.toShortString());
    if (!isSurface(structureWorldAccess, blockPos)) {
      // stripe can be offset one tile up or down from previous
      blockPos = blockPos.up();
      if (!isSurface(structureWorldAccess, blockPos)) {
        blockPos = blockPos.down(2);
        if (!isSurface(structureWorldAccess, blockPos)) {
          return false;
        }
      }
    }
    var placed = false;
    blockPos = blockPos.down();
    while (size > 0 && structureWorldAccess.getBlockState(blockPos).isAir()) {
      placeWeb(structureWorldAccess, blockPos);
      placed = true;
      size--;
      blockPos = blockPos.down();
    }
    return placed;
  }

  // y = kx
  private int[] generateSlope(Random random, int length) {
    var result = new int[length];
    // close to PI/4, but smaller to avoid sharp corners
    var k = Math.tan(0.5 * random.nextDouble());
    for (var x = 0; x < length; x++) {
      result[x] = (int) Math.round(k * x);
    }
    return result;
  }

  private int stripeSize(int offset) {
    switch (offset) {
      case 0, 1:
        return 3;
      case 2, 3:
        return 2;
      default:
        return 1;
    }
  }
}
