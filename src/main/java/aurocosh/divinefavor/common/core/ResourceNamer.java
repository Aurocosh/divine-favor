package aurocosh.divinefavor.common.core;

import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraft.util.ResourceLocation;

public class ResourceNamer {
    public static ResourceLocation getFullName(String name) {
        return new ResourceLocation(ConstMisc.MOD_ID, name);
    }

    public static ResourceLocation getFullName(String prefix, String name) {
        return new ResourceLocation(ConstMisc.MOD_ID, prefix + "." + name);
    }
}
