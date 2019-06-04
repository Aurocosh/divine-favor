package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSourceIndirect
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionHuntersAura : ModPotion("hunters_aura", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.huntersAuraData.reset()
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onMobDeath(event: LivingDeathEvent) {
            if (!ModSpirits.arbow.isActive)
                return
            val source = event.source as? EntityDamageSourceIndirect ?: return
            if (source.damageType != "arrow")
                return
            val attacker = source.trueSource as? EntityPlayer ?: return
            if (!attacker.isPotionActive(ModBlendEffects.hunters_aura))
                return
            val mob = event.entity
            if (mob !is IMob)
                return
            if (attacker.divinePlayerData.huntersAuraData.tryLuck()) {
                attacker.removePotionEffect(ModBlendEffects.hunters_aura)
                attacker.addPotionEffect(ModEffect(ModBlessings.predatory_presence, ConfigPresence.predatoryPresence.duration))
            }
        }
    }
}
