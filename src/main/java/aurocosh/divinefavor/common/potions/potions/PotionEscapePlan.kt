package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionEscapePlan : ModPotion("escape_plan", true, 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val entity = event.entity as? EntityPlayer ?: return
            if (entity.health > entity.maxHealth / 2)
                return
            if (!entity.isPotionActive(ModPotions.escape_plan))
                return

            val planData = PlayerData.get(entity).escapePlanData
            UtilEntity.teleport(entity, planData.globalPosition)
            entity.removePotionEffect(ModPotions.escape_plan)
        }
    }
}
