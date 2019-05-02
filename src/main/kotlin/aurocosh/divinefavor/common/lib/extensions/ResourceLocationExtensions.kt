package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import net.minecraft.util.ResourceLocation

fun ResourceLocation.isEmpty(): Boolean {
    return this == emptyLocation() || this.path.isEmpty();
}

