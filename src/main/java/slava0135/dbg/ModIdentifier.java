package slava0135.dbg;

import net.minecraft.util.Identifier;

public class ModIdentifier {
  public static Identifier of(String path) {
    return Identifier.of(DigBlockGalactic.MOD_ID, path);
  }
}
