package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionMode
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getDistanceSq
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLivingBase
import java.util.*

class SpellTalismanTarget(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun validate(context: TalismanContext): Boolean {
        return context.target != null
    }

    override fun performActionServer(context: TalismanContext) {
        val origin = context.player.positionVector
        val radiusSq = RADIUS * RADIUS
        val boundingBox = UtilCoordinates.getBoundingBox(origin, RADIUS);
        val entities = context.world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox) { livingBase ->
            (livingBase is IMinion && livingBase.getDistanceSq(origin) < radiusSq)
        }

        for (entity in entities) {
            val minion = entity as IMinion
            val minionData = minion.minionData
            minionData.attackTarget = context.target
            minionData.mode = MinionMode.Normal
        }
    }

    companion object {
        private val RADIUS = 30.0
    }
}
