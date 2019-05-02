package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionMode
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.math.Vec3d
import java.util.*

class SpellTalismanFollow(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val origin: Vec3d = context.player.positionVector
        val radiusSq = RADIUS * RADIUS
        val boundingBox = UtilCoordinates.getBoundingBox(origin, RADIUS)
        val predicate: (EntityLivingBase?) -> Boolean = { livingBase: EntityLivingBase? -> (livingBase is IMinion && origin.distanceTo(livingBase.positionVector) < radiusSq) }
        val minions = context.world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox, predicate)

        for (livingBase in minions) {
            val minion = livingBase as IMinion
            val minionData = minion.minionData
            minionData.attackTarget = null
            minionData.setMode(MinionMode.Follow)
        }
    }

    companion object {
        private const val RADIUS = 30.0
    }
}
