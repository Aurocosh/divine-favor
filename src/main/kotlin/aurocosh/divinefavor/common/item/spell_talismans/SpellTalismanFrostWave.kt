package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesWave
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumParticleTypes
import java.util.*

class SpellTalismanFrostWave(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val origin = player.positionVector

        val alignedBB = UtilCoordinates.getBoundingBox(origin, ConfigSpell.frostWave.radius)
        val list = context.world.getEntitiesWithinAABB(EntityLivingBase::class.java, alignedBB) { e -> e !== player && e != null && origin.squareDistanceTo(e.positionVector) <= RADIUS_SQ }

        for (entity in list) {
            entity.attackEntityFrom(ModDamageSources.frostDamage, ConfigSpell.frostWave.damage)
            val direction = origin.subtract(entity.positionVector).normalize()
            entity.knockBack(context.player, ConfigSpell.frostWave.knockbackPower, direction.x, direction.z)
        }

        val positionEyes = player.getPositionEyes(0f)
        MessageParticlesWave(EnumParticleTypes.SNOW_SHOVEL, positionEyes).sendToAllAround(context.world, player.position, ConfigGeneral.particleRadius)
    }

    companion object {
        private val RADIUS_SQ = ConfigSpell.frostWave.radius * ConfigSpell.frostWave.radius
    }
}
