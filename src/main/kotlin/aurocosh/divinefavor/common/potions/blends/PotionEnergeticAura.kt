package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionEnergeticAura : ModPotion("energetic_aura", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.energeticAuraData.reset()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (!ModSpirits.redwind.isActive)
            return
        if (!COUNTER.isFinished)
            return
        if (livingBase !is EntityPlayer)
            return
        if (!livingBase.isSprinting || !livingBase.divinePlayerData.energeticAuraData.tryLuck())
            return
        livingBase.removePotionEffect(ModBlendEffects.energetic_aura)
        livingBase.addPotionEffect(ModEffect(ModBlessings.energetic_presence, ConfigPresence.energeticPresence.duration))
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        val CHECK_RATE = UtilTick.secondsToTicks(1f)
        private val COUNTER = LoopedCounter(CHECK_RATE)

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                COUNTER.tick()
        }
    }
}
