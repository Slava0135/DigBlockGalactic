package slava0135.dbg;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// Based on `AgeableMobEntityRenderer` because it is deprecated
public class LootbugEntityRenderer
        extends LivingEntityRenderer<LootbugEntity, LootbugEntityRenderState, LootbugEntityModel> {
    private final LootbugEntityModel adultModel;
    private final LootbugEntityModel babyModel;

    public LootbugEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LootbugEntityModel(context.getPart(DigBlockGalacticClient.LOOTBUG)), 1f);
        this.adultModel = this.model;
        this.babyModel =
                new LootbugEntityModel(context.getPart(DigBlockGalacticClient.LOOTBUG_BABY));
    }

    @Override
    public Identifier getTexture(LootbugEntityRenderState state) {
        return ModIdentifier.of("textures/entity/lootbug/lootbug.png");
    }

    @Override
    public LootbugEntityRenderState createRenderState() {
        return new LootbugEntityRenderState();
    }

    @Override
    public void render(LootbugEntityRenderState livingEntityRenderState, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = livingEntityRenderState.baby ? this.babyModel : this.adultModel;
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
