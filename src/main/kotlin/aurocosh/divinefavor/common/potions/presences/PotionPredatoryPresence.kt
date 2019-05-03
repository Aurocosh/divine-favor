package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionPredatoryPresence : ModPotion("predatory_presence", true, 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.predatoryPresenceData.reset()
    }

    companion object {

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val target = event.entity as? EntityLivingBase ?: return
            if (target.isPotionActive(ModBlessings.predatory_presence))
                target.removePotionEffect(ModBlessings.predatory_presence)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onMobDeath(event: LivingDeathEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val attacker = source.trueSource as? EntityPlayer ?: return
            if (!attacker.isPotionActive(ModBlessings.predatory_presence))
                return
            if (event.entity !is IMob)
                return

            if (attacker.divinePlayerData.predatoryPresenceData.count()) {
                attacker.removePotionEffect(ModBlessings.predatory_presence)
                attacker.addItemStackToInventory(ItemStack(ModCallingStones.calling_stone_arbow))
            }
        }
    }
}
