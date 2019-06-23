package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigCurses
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionFillLungs : ModPotion("fill_lungs", 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase.isInWater)
            livingBase.removePotionEffect(ModCurses.fill_lungs)
        else if (COUNTER.isFinished)
            livingBase.attackEntityFrom(DamageSource.DROWN, ConfigCurses.fillLungs.damage)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val COUNTER = LoopedCounter(ConfigCurses.fillLungs.drowningRate)

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                COUNTER.tick()
        }
    }
}
