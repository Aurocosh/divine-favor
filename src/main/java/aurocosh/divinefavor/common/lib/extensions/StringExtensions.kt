package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.lib.EmptyConst
import net.minecraft.util.ResourceLocation

fun String.toResourceLocation(): ResourceLocation {
    return if (this.isEmpty())
        EmptyConst.resourceLocation
    else
        ResourceLocation(this)
}

fun String.toResourceLocation(namespace: String): ResourceLocation {
    return if (this.isEmpty())
        EmptyConst.resourceLocation
    else
        ResourceLocation(namespace, this)
}
