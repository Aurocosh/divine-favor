package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import net.minecraft.util.ResourceLocation

fun String.toResourceLocation(): ResourceLocation {
    return if (this.isEmpty())
        emptyLocation()
    else
        ResourceLocation(this)
}

fun String.toResourceLocation(namespace: String): ResourceLocation {
    return if (this.isEmpty())
        emptyLocation()
    else
        ResourceLocation(namespace, this)
}
