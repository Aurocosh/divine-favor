package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionSuffocatingFumes : ModPotion("suffocating_fumes", 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)

        livingBase.divineLivingData.suffocatingFumesData.y = livingBase.position.y
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (DAMAGE_COUNTER.isFinished)
            livingBase.attackEntityFrom(DamageSource.DROWN, ConfigArrow.suffocatingFumes.damage)
        if (CURE_COUNTER.isFinished && isCured(livingBase))
            livingBase.removePotionEffect(ModCurses.suffocating_fumes)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    private fun isCured(livingBase: EntityLivingBase): Boolean {
        val oldY = livingBase.divineLivingData.suffocatingFumesData.y
        val newY = livingBase.position.y
        return newY - oldY > ConfigArrow.suffocatingFumes.heightToClimb
    }

    companion object {
        private val DAMAGE_COUNTER = LoopedCounter(ConfigArrow.suffocatingFumes.damageRate)
        private val CURE_COUNTER = LoopedCounter(ConfigArrow.suffocatingFumes.cureRate)

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase != TickEvent.Phase.END)
                return
            DAMAGE_COUNTER.tick()
            CURE_COUNTER.tick()
        }
    }
}
