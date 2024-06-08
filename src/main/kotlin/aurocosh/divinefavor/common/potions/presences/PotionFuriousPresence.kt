package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionFuriousPresence : ModPotion("furious_presence", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.furiousPresenceData.reset()
    }

    companion object {
        @SubscribeEvent
        @JvmStatic
        fun onEntityDamaged(event: LivingDamageEvent) {
            if (isVictimHasBlessing(event) || isAttackerHasBlessing(event))
                event.amount = event.amount * ConfigPresence.furiousPresence.damageMultiplier
        }

        private fun isVictimHasBlessing(event: LivingDamageEvent): Boolean {
            val target = event.entity as? EntityLivingBase ?: return false
            return target.isPotionActive(ModBlessings.furious_presence)
        }

        private fun isAttackerHasBlessing(event: LivingDamageEvent): Boolean {
            val source = event.source as? EntityDamageSource ?: return false
            val entity = source.trueSource as? EntityPlayer ?: return false
            return entity.isPotionActive(ModBlessings.furious_presence)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        @JvmStatic
        fun onMobDeath(event: LivingDeathEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val attacker = source.trueSource as? EntityPlayer ?: return
            if (!attacker.isPotionActive(ModBlessings.furious_presence))
                return
            val mob = event.entity
            if (mob !is IMob)
                return

            if (attacker.divinePlayerData.furiousPresenceData.count()) {
                attacker.removePotionEffect(ModBlessings.furious_presence)
                UtilSpirit.convertMarksToInvites(attacker, ModSpirits.squarefury, ModCallingStones.calling_stone_squarefury)
                MaterialPresence.onInviteGiven(attacker)
            }
        }
    }
}
