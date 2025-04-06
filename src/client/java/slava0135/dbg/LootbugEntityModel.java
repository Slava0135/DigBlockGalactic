package slava0135.dbg;

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BabyModelTransformer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelTransformer;

@Environment(EnvType.CLIENT)
public class LootbugEntityModel extends EntityModel<LootbugEntityRenderState> {
  public static final ModelTransformer BABY_TRANSFORMER =
      new BabyModelTransformer(true, 0F, 0F, Set.of());
  private static final String BODY = "body";

  private final ModelPart body;

  public LootbugEntityModel(ModelPart root) {
    super(root);
    this.body = root.getChild(BODY);
  }

  public static TexturedModelData getTexturedModelData() {
    var modelData = new ModelData();
    var modelPartData = modelData.getRoot();
    modelPartData.addChild(BODY,
        ModelPartBuilder.create().uv(0, 0)
            .cuboid(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)).uv(32, 32)
            .cuboid(-6.0F, -8.0F, -12.0F, 12.0F, 8.0F, 4.0F, new Dilation(0.0F)).uv(0, 32)
            .cuboid(-6.0F, -12.0F, 8.0F, 12.0F, 12.0F, 4.0F, new Dilation(0.0F)).uv(32, 44)
            .cuboid(-4.0F, -8.0F, 12.0F, 8.0F, 8.0F, 4.0F, new Dilation(0.0F)),
        ModelTransform.origin(0.0F, 24.0F, 0.0F));
    return TexturedModelData.of(modelData, 64, 64);
  }

  @Override
  public void setAngles(LootbugEntityRenderState state) {
    super.setAngles(state);
  }
}
