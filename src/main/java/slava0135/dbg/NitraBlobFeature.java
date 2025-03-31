package slava0135.dbg;

import java.util.List;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class NitraBlobFeature extends Feature<DefaultFeatureConfig> {
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
			Direction surface = null;
			for (var direction : HORIZONTAL) {
				var nextBlockPos = blockPos.offset(direction);
				var blockState = structureWorldAccess.getBlockState(nextBlockPos);
				if (blockState.isOf(Blocks.DEEPSLATE)) {
					surface = direction;
					break;
				}
			}
			if (surface == null) {
				return false;
			}
			structureWorldAccess.setBlockState(blockPos, ModBlocks.NITRA_BLOCK.getDefaultState(),
					Block.NOTIFY_LISTENERS);
			return true;
		}
	}
}
