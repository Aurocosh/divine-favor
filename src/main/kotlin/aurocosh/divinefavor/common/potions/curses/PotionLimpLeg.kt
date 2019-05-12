package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.math.MathHelper
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionLimpLeg : ModPotion("limp_leg", 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        livingBase.divineLivingData.limpLegData.resetCureTimer()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase.isSneaking) {
            if (livingBase.divineLivingData.limpLegData.cureTick())
                livingBase.removePotionEffect(ModCurses.limp_leg)
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {

        @SubscribeEvent
        fun onPlayerJump(event: LivingEvent.LivingJumpEvent) {
            val entity = event.entityLiving
            if (!entity.isPotionActive(ModCurses.limp_leg))
                return
            entity.motionY = 0.0
            if (entity.isSprinting) {
                val f = entity.rotationYaw * 0.017453292f
                entity.motionX += (MathHelper.sin(f) * 0.2f).toDouble()
                entity.motionZ -= (MathHelper.cos(f) * 0.2f).toDouble()
            }
            entity.isAirBorne = false
        }
    }
}
