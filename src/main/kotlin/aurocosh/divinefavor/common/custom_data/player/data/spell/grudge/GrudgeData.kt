package aurocosh.divinefavor.common.custom_data.player.data.spell.grudge

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityList
import net.minecraft.util.ResourceLocation

// The default implementation of the capability. Holds all the logic.
class GrudgeData {
    var mobTypeId: ResourceLocation = emptyLocation()

    val mobName: String
        get() {
            val translationName = EntityList.getTranslationName(mobTypeId)
            return translationName ?: "divinefavor:no_grudge_assigned"
        }

    fun reset() {
        mobTypeId = emptyLocation()
    }

    fun setGrudge(entity: Entity) {
        mobTypeId = EntityList.getKey(entity) ?: emptyLocation()
    }

    fun hasGrudge(entity: Entity): Boolean {
        return EntityList.getKey(entity) == mobTypeId
    }
}
