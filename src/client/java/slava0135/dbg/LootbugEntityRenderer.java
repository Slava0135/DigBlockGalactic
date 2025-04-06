package slava0135.dbg;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class LootbugEntityRenderer
        extends MobEntityRenderer<LootbugEntity, LootbugEntityRenderState, LootbugEntityModel> {

    public LootbugEntityRenderer(EntityRendererFactory.Context context) {
        super(context,
                new LootbugEntityModel(context.getPart(DigBlockGalacticClient.LOOTBUG_LAYER)),
                0.5f);
    }

    @Override
    public Identifier getTexture(LootbugEntityRenderState state) {
        return ModIdentifier.of("textures/entity/lootbug/lootbug.png");
    }

    @Override
    public LootbugEntityRenderState createRenderState() {
        return new LootbugEntityRenderState();
    }
}
