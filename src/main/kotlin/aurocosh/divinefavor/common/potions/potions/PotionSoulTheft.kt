package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionSoulTheft : ModPotionToggle("soul_theft", 0x7FB8A4) {

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        livingBase.divineLivingData.soulTheftData.reset()
    }

    companion object {
        var EXTRA_DAMAGE = 6f

        @SubscribeEvent
        @JvmStatic
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entityAttacker = source.trueSource as? EntityLivingBase ?: return
            if (!entityAttacker.isPotionActive(ModCurses.soul_theft))
                return
            val victim = event.entityLiving as? EntityPlayer ?: return
            if (entityAttacker.divineLivingData.soulTheftData.isThief(victim))
                event.amount = event.amount + EXTRA_DAMAGE
        }
    }
}
