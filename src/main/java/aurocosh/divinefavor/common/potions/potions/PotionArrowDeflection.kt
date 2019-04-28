package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.LimitedTimer
import aurocosh.divinefavor.common.lib.PrivateField
import aurocosh.divinefavor.common.lib.extensions.getMotionVector
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilList
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.util.EntityDamageSourceIndirect
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionArrowDeflection : ModPotion("arrow_deflection", true, 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (!COOLDOWN_COUNTER.tick())
            return

        val entities = livingBase.world.getEntitiesWithinAABB(EntityArrow::class.java, AxisAlignedBB(livingBase.position).grow(ConfigSpells.arrowDeflection.radius.toDouble()))
        val projectiles = UtilList.select(entities) { element -> !IN_GROUND.get(element) && element.getDistanceSq(livingBase) <= RADIUS_SQ }
        for (projectile in projectiles) {
            val directionToTarget = livingBase.positionVector.subtract(projectile.positionVector).normalize()
            val motionVector = projectile.getMotionVector()
            val motionDirection = motionVector.normalize()
            val product = directionToTarget.dotProduct(motionDirection)
            if (product > ConfigSpells.arrowDeflection.tolerance)
                UtilEntity.setVelocity(projectile, motionDirection.scale(-1.0), motionVector.length().toFloat())
            COOLDOWN_COUNTER.reset()
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val COOLDOWN_COUNTER = LimitedTimer(ConfigSpells.arrowDeflection.deflectionCooldown)
        private val RADIUS_SQ = ConfigSpells.arrowDeflection.radius * ConfigSpells.arrowDeflection.radius
        private val IN_GROUND = PrivateField(EntityArrow::class.java, 7, false)

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onEntityDamaged(event: LivingDamageEvent) {
            val livingBase = event.entityLiving
            if (!livingBase.isPotionActive(ModPotions.arrow_deflection))
                return
            val source = event.source
            if (source is EntityDamageSourceIndirect && source.damageType == "arrow")
                return

            var duration = -ConfigSpells.arrowDeflection.durationDecrease
            val potionEffect = livingBase.activePotionMap[ModPotions.arrow_deflection]
            if (potionEffect != null)
                duration += potionEffect.duration
            livingBase.removePotionEffect(ModPotions.arrow_deflection)
            livingBase.addPotionEffect(ModEffect(ModPotions.arrow_deflection, duration))
        }
    }
}
