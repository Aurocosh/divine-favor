package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.IAnimals
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LootingLevelEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PotionButcheringStrike : ModPotionToggleLimited("butchering_strike", 0x7FB8A4) {
    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }
}

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object ButcheringStrike {
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

        val spiritData = entity.divinePlayerData.spiritData
        val talisman = ModPotions.butchering_strike.talisman
        val spirit = talisman.spirit
        if (!spiritData.consumeFavor(spirit.id, Math.ceil(talisman.favorCost / 2.0).toInt()))
            return

        MessageSyncFavor(spirit, spiritData).sendTo(entity)
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

        val spiritData = entity.divinePlayerData.spiritData
        val talisman = ModPotions.butchering_strike.talisman
        val spirit = talisman.spirit
        if (!spiritData.consumeFavor(spirit.id, Math.floor(talisman.favorCost / 2.0).toInt()))
            return

        MessageSyncFavor(spirit, spiritData).sendTo(entity)
        event.lootingLevel = event.lootingLevel + ConfigSpells.butcheringStrike.extraLooting
    }
}