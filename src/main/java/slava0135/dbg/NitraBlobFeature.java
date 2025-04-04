package slava0135.dbg;

import java.util.List;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/// Shape.
/// X is world access, must be air.
/// Block behind X must be deep slate.
/// All nitra blocks must be next to each other and deep slate behind (surface).
/// Nitra blocs can be offset by 1 at most, but always stay on one height.
///
/// |NNNNNN | -- | NNNNNN|
/// |NNNXNNN| or |NNNXNNN|
/// | NNNNNN| -- |NNNNNN |
public class NitraBlobFeature extends Feature<DefaultFeatureConfig> {
	private static int WIDTH = 3;

	private static List<Direction> HORIZONTAL =
			List.of(Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);

	public NitraBlobFeature(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		var structureWorldAccess = context.getWorld();
		var blockPos = context.getOrigin();
		var random = context.getRandom();
		if (!structureWorldAccess.isAir(blockPos)) {
			return false;
		} else {
			Direction towardSurface = null;
			for (var direction : HORIZONTAL) {
				var surfaceBlockPos = blockPos.offset(direction);
				if (isSurface(structureWorldAccess, surfaceBlockPos)) {
					towardSurface = direction;
					break;
				}
			}
			if (towardSurface == null) {
				return false;
			}
			var oppositeSurface = towardSurface.getOpposite();
			placeNitra(structureWorldAccess, blockPos);
			stripe(structureWorldAccess, towardSurface, oppositeSurface, blockPos,
					towardSurface.rotateYCounterclockwise(), WIDTH);
			stripe(structureWorldAccess, towardSurface, oppositeSurface, blockPos,
					towardSurface.rotateYClockwise(), WIDTH);

			int topLeftWidth, topRightWidth, botLeftWidth, botRightWidth;
			if (random.nextBoolean()) {
				topLeftWidth = WIDTH;
				botRightWidth = WIDTH;
				topRightWidth = WIDTH - 1;
				botLeftWidth = WIDTH - 1;
			} else {
				topLeftWidth = WIDTH - 1;
				botRightWidth = WIDTH - 1;
				topRightWidth = WIDTH;
				botLeftWidth = WIDTH;
			}

			var topBlockPos =
					stripe(structureWorldAccess, towardSurface, oppositeSurface, blockPos, Direction.UP, 1);
			stripe(structureWorldAccess, towardSurface, oppositeSurface, topBlockPos,
					towardSurface.rotateYCounterclockwise(), topLeftWidth);
			stripe(structureWorldAccess, towardSurface, oppositeSurface, topBlockPos,
					towardSurface.rotateYClockwise(), topRightWidth);

			var botBlockPos =
					stripe(structureWorldAccess, towardSurface, oppositeSurface, blockPos, Direction.DOWN, 1);
			stripe(structureWorldAccess, towardSurface, oppositeSurface, botBlockPos,
					towardSurface.rotateYCounterclockwise(), botLeftWidth);
			stripe(structureWorldAccess, towardSurface, oppositeSurface, botBlockPos,
					towardSurface.rotateYClockwise(), botRightWidth);
			return true;
		}
	}

	private BlockPos stripe(StructureWorldAccess world, Direction towardSurface,
			Direction oppositeSurface, BlockPos blockPos, Direction direction, int width) {
		if (blockPos == null) {
			return null;
		}
		for (var offset = 1; offset <= width; offset++) {
			blockPos = blockPos.offset(direction);
			if (!isSurface(world, blockPos)) {
				if (!isSurface(world, blockPos.offset(towardSurface))) {
					blockPos = blockPos.offset(towardSurface);
					if (!isSurface(world, blockPos.offset(towardSurface))) {
						return null;
					}
				}
			} else {
				blockPos = blockPos.offset(oppositeSurface);
				if (isSurface(world, blockPos)) {
					return null;
				}
			}
			placeNitra(world, blockPos);
		}
		return blockPos;
	}

	private void placeNitra(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
		structureWorldAccess.setBlockState(blockPos, ModBlocks.NITRA_BLOCK.getDefaultState(),
				Block.NOTIFY_LISTENERS);
	}

	private boolean isSurface(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
		var state = structureWorldAccess.getBlockState(blockPos);
		return state.isOf(Blocks.DEEPSLATE);
	}
}
