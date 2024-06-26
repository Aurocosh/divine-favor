package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.LimitedTimer
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.getMotionVector
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.util.EntityDamageSourceIndirect
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionArrowDeflection : ModPotion("arrow_deflection", 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (!COOLDOWN_COUNTER.tick())
            return

        val entities = livingBase.world.getEntitiesWithinAABB(EntityArrow::class.java, AxisAlignedBB(livingBase.position).grow(ConfigSpell.arrowDeflection.radius.toDouble()))
        val projectiles = entities.S.filter { arrow -> arrow.getDistanceSq(livingBase) <= RADIUS_SQ }
        for (projectile in projectiles) {
            val directionToTarget = livingBase.positionVector.subtract(projectile.positionVector).normalize()
            val motionVector = projectile.getMotionVector()
            val motionDirection = motionVector.normalize()
            val product = directionToTarget.dotProduct(motionDirection)
            if (product > ConfigSpell.arrowDeflection.tolerance)
                UtilEntity.setVelocity(projectile, motionDirection.scale(-1.0), motionVector.length().toFloat())
            COOLDOWN_COUNTER.reset()
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val COOLDOWN_COUNTER = LimitedTimer(ConfigSpell.arrowDeflection.deflectionCooldown)
        private val RADIUS_SQ = ConfigSpell.arrowDeflection.radius * ConfigSpell.arrowDeflection.radius

        @SubscribeEvent(priority = EventPriority.LOWEST)
        @JvmStatic
        fun onEntityDamaged(event: LivingDamageEvent) {
            val livingBase = event.entityLiving
            if (!livingBase.isPotionActive(ModPotions.arrow_deflection))
                return
            val source = event.source
            if (source is EntityDamageSourceIndirect && source.damageType == "arrow")
                return

            var duration = -ConfigSpell.arrowDeflection.durationDecrease
            val potionEffect = livingBase.activePotionMap[ModPotions.arrow_deflection]
            if (potionEffect != null)
                duration += potionEffect.duration
            livingBase.removePotionEffect(ModPotions.arrow_deflection)
            livingBase.addPotionEffect(ModEffect(ModPotions.arrow_deflection, duration))
        }
    }
}
