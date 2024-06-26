package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionEscapePlan : ModPotion("escape_plan", 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {

        @SubscribeEvent
        @JvmStatic
        fun onEntityDamaged(event: LivingDamageEvent) {
            val entity = event.entity as? EntityPlayer ?: return
            if (entity.health > entity.maxHealth / 2)
                return
            if (!entity.isPotionActive(ModPotions.escape_plan))
                return

            val pos = entity.divinePlayerData.escapePlanData.globalPosition ?: return
            UtilEntity.teleport(entity, pos)
            entity.removePotionEffect(ModPotions.escape_plan)
        }
    }
}
