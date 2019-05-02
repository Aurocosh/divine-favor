package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncPotionCharge
import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.IAnimals
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LootingLevelEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionButcheringStrike : ModPotionCharge("butchering_strike", true, 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {
        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.butchering_strike))
                return
            val animal = event.entity
            if (animal !is IAnimals)
                return
            if (animal is IMob)
                return

            val effectCharge = (entity.getActivePotionEffect(ModPotions.butchering_strike) as ModEffectCharge?)!!
            val charges = effectCharge.consumeCharge()
            MessageSyncPotionCharge(ModPotions.butchering_strike, charges).sendTo(entity)

            event.amount = event.amount + ConfigSpells.butcheringStrike.extraDamage
        }

        @SubscribeEvent
        fun onLootingLevelEvent(event: LootingLevelEvent) {
            val source = event.damageSource as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.butchering_strike))
                return
            val animal = event.entity
            if (animal !is IAnimals)
                return
            if (animal is IMob)
                return
            event.lootingLevel = event.lootingLevel + ConfigSpells.butcheringStrike.extraLooting
        }
    }

}
