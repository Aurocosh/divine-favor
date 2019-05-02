package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getDistanceSq
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesWinterBreath
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLivingBase
import java.util.*

class SpellTalismanWinterBreath(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val origin = player.positionVector
        val lookVec = player.lookVec
        val radius = ConfigSpells.winterBreath.radius
        val radiusSq = (radius * radius).toDouble()

        val function = { livingBase: EntityLivingBase? -> livingBase != null && livingBase !== player && livingBase.getDistanceSq(origin) < radiusSq && UtilEntity.isInCone(lookVec, origin, livingBase, ConfigSpells.winterBreath.coneTolerance) }

        val boundingBox = UtilCoordinates.getBoundingBox(origin, radius.toDouble())
        val entities = context.world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox, function)
        for (entity in entities) {
            entity.attackEntityFrom(ModDamageSources.frostDamage, ConfigSpells.winterBreath.damage.toFloat())
            UtilEntity.addVelocity(entity, lookVec, ConfigSpells.winterBreath.knockback.toFloat())
        }

        val positionEyes = player.getPositionEyes(0f)
        MessageParticlesWinterBreath(positionEyes, lookVec).sendToAllAround(context.world, player.position, ConfigGeneral.particleRadius)
    }
}
