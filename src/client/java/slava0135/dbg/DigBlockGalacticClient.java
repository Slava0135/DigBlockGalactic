package slava0135.dbg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class DigBlockGalacticClient implements ClientModInitializer {
	public static final EntityModelLayer LOOTBUG_LAYER =
			new EntityModelLayer(ModIdentifier.of("lootbug"), "main");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.LOOTBUG, (context) -> {
			return new LootbugEntityRenderer(context);
		});

		EntityModelLayerRegistry.registerModelLayer(LOOTBUG_LAYER,
				LootbugEntityModel::getTexturedModelData);
	}
}
