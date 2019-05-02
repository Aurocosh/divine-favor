package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.EmptyConst
import net.minecraft.util.ResourceLocation

fun ResourceLocation.isEmpty(): Boolean {
    return this == EmptyConst.resourceLocation || this.path.isEmpty();
}

