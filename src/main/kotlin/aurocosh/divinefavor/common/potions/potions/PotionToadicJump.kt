package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContextGenerator
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PotionToadicJump : ModPotionToggleLimited("toadic_jump", 0x7FB8A4) {
    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }
}

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object ToadicJump {
    @SubscribeEvent
    fun onPlayerJump(event: LivingEvent.LivingJumpEvent) {
        val entity = event.entityLiving as? EntityPlayer ?: return
        if (!entity.isPotionActive(ModPotions.toadic_jump))
            return

        val talisman = ModPotions.crushing_palm.talisman
        val stack = UtilPlayer.getItemInHand(entity) { it === talisman }

        val context = TalismanContextGenerator.rightClick(entity.world, entity, EnumHand.MAIN_HAND, stack)
        if (talisman.claimCost(context))
            entity.motionY += ConfigSpell.toadicJump.jumpBoost.toDouble()
    }
}
