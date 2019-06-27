package aurocosh.divinefavor.common.lib

import net.minecraft.util.ResourceLocation
import java.util.*

object EmptyConst {
    private val resourceLocation: ResourceLocation = ResourceLocation("")
    fun emptyLocation(): ResourceLocation = resourceLocation

    // TODO rework
    private val uuid = UUID.fromString("00000000-0000-0000-0000-000000000000")
    fun emptyUUID(): UUID = uuid
}