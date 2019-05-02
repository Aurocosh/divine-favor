package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionSpiderMight : ModPotionToggle("spider_might", true, 0x7FB8A4) {
    companion object {
        val CLIMB_SPEED = 0.288

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val livingBase = event.entityLiving
            if (!livingBase.isPotionActive(ModPotions.spider_might))
                return

            val source = event.source
            if (source.isFireDamage)
                event.amount = event.amount + ConfigSpells.spider_might.fireDamage

            val entity = source.trueSource
            if (entity is EntityPlayer) {
                val player = entity as EntityPlayer?
                val stack = player!!.heldItemMainhand
                if (!stack.isEmpty) {
                    val baneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack)
                    event.amount = event.amount + baneLevel * ConfigSpells.spider_might.baneDamage
                }
            }
        }

        @SubscribeEvent
        fun onEntityUpdate(event: LivingEvent.LivingUpdateEvent) {
            val entity = event.entityLiving ?: return
            if (!entity.isPotionActive(ModPotions.spider_might))
                return
            if (!entity.collidedHorizontally)
                return

            if (entity.isSneaking)
                entity.motionY = 0.0
            else if (entity.moveForward > 0.0f && entity.motionY < CLIMB_SPEED)
                entity.motionY = CLIMB_SPEED
            if (!entity.world.isRemote)
                entity.fallDistance = 0f
        }
    }
}
