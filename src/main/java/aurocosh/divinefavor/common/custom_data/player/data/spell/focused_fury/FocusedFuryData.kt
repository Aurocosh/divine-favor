package aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityList
import net.minecraft.util.ResourceLocation

// The default implementation of the capability. Holds all the logic.
class FocusedFuryData {
    var mobTypeId: ResourceLocation = emptyLocation()

    val mobName: String
        get() {
            val translationName = EntityList.getTranslationName(mobTypeId)
            return translationName ?: "divinefavor:no_fury_assigned"
        }

    fun reset() {
        mobTypeId = emptyLocation()
    }

    fun setFury(entity: Entity) {
        mobTypeId = EntityList.getKey(entity) ?: emptyLocation()
    }

    fun hasFury(entity: Entity): Boolean {
        return EntityList.getKey(entity) == mobTypeId
    }

    fun hasFury(): Boolean {
        return mobTypeId.path.isNotEmpty()
    }
}
