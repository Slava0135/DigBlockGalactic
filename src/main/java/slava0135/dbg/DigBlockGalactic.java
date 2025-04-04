package slava0135.dbg;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigBlockGalactic implements ModInitializer {
	public static final String MOD_ID = "dig-block-galactic";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("load items");
		ModItems.initialize();

		LOGGER.info("load blocks");
		ModBlocks.initialize();

		LOGGER.info("load placed features");
		ModFeatures.initialize();

		LOGGER.info("load status effects");
		ModEffects.initialize();

		LOGGER.info("load potions");
		ModPotions.initialize();
	}
}
