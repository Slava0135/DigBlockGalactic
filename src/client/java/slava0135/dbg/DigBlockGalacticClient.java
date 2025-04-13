package slava0135.dbg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class DigBlockGalacticClient implements ClientModInitializer {
	public static final EntityModelLayer LOOTBUG =
			new EntityModelLayer(ModIdentifier.of("lootbug"), "main");
	public static final EntityModelLayer LOOTBUG_BABY =
			new EntityModelLayer(ModIdentifier.of("lootbug_baby"), "main");

	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(ModEntities.LOOTBUG, (context) -> {
			return new LootbugEntityRenderer(context);
		});
		EntityRendererRegistry.register(ModEntities.FLARE, (context) -> {
			return new FlyingItemEntityRenderer<>(context);
		});

		EntityModelLayerRegistry.registerModelLayer(LOOTBUG, LootbugEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LOOTBUG_BABY, () -> LootbugEntityModel
				.getTexturedModelData().transform(LootbugEntityModel.BABY_TRANSFORMER));
	}
}
