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
class PotionFrostyAura : ModPotion("frosty_aura", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.frostyAuraData.reset()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase !is EntityPlayer)
            return
        val auraData = livingBase.divinePlayerData.frostyAuraData
        if (auraData.count()) {
            livingBase.removePotionEffect(ModBlendEffects.frosty_aura)
            livingBase.addPotionEffect(ModEffect(ModBlessings.chilling_presence, ConfigPresence.chillingPresence.duration))
            return
        }
        if (!isConditionsMet(livingBase))
            auraData.reset()
    }

    private fun isConditionsMet(player: EntityPlayer): Boolean {
        if (!ModSpirits.blizrabi.isActive)
            return false;
        if (!COUNTER.isFinished)
            return true
        val itemSequence = player.armorInventoryList.asSequence() + player.heldEquipment.asSequence()
        return itemSequence.all { it.isEmpty }
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
