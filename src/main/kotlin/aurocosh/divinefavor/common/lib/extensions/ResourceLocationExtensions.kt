package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.ResourceLocation

fun ResourceLocation.isEmpty(): Boolean {
    return this.path.isEmpty()
}
