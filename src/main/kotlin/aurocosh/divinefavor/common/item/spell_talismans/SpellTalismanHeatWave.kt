package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesWave
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumParticleTypes
import java.util.*

class SpellTalismanHeatWave(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val player = context.player
        val origin = player.position

        val boundingBox = UtilCoordinates.getBoundingBox(origin, ConfigSpell.heatWave.radius)
        val list = world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox) { e -> e !== player && e != null && e.getDistanceSq(origin) <= RADIUS_SQ }

        for (entity in list) {
            entity.attackEntityFrom(DamageSource.ON_FIRE, ConfigSpell.heatWave.damage)

            if (UtilRandom.rollDice(ConfigSpell.heatWave.chanceToSetEnemyOnFire))
                entity.setFire(ConfigSpell.heatWave.enemyBurnTime)
            if (UtilRandom.rollDice(ConfigSpell.heatWave.chanceToSetGroundOnFire))
                UtilBlock.ignite(player, world, entity.position)
        }

        val positionEyes = player.getPositionEyes(0f)
        MessageParticlesWave(EnumParticleTypes.FLAME, positionEyes).sendToAllAround(world, player.position, ConfigGeneral.particleRadius)
    }

    companion object {
        private val RADIUS_SQ = ConfigSpell.heatWave.radius * ConfigSpell.heatWave.radius
    }
}
