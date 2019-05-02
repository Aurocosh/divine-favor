package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionFieryMark : ModPotion("fiery_mark", true, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.isInWater)
            livingBase.removePotionEffect(ModCurses.fiery_mark)
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        explode(livingBase)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onMobDeath(event: LivingDeathEvent) {
            val entity = event.entity as? EntityLivingBase ?: return
            if (entity.isPotionActive(ModCurses.fiery_mark))
                explode(entity)
        }

        private fun explode(livingBase: EntityLivingBase) {
            if (livingBase.isInWater)
                return
            livingBase.setFire(ConfigArrow.fieryMark.onFireSeconds)
            livingBase.attackEntityFrom(DamageSource.ON_FIRE, ConfigArrow.fieryMark.extraDamage)
            val pos = livingBase.position
            livingBase.world.newExplosion(livingBase, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), ConfigArrow.fieryMark.explosionPower.toFloat(), ConfigArrow.fieryMark.causeFire, ConfigArrow.fieryMark.damageTerrain)
        }
    }
}
