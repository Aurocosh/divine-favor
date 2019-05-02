package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.living.LivingData
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionSoulTheft : ModPotionToggle("soul_theft", true, 0x7FB8A4) {

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        val theftData = LivingData.get(livingBase).soulTheftData
        theftData.reset()
    }

    companion object {
        var EXTRA_DAMAGE = 6f

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entityAttacker = source.trueSource as? EntityLivingBase ?: return
            if (!entityAttacker.isPotionActive(ModCurses.soul_theft))
                return
            val victim = event.entityLiving as? EntityPlayer ?: return
            val theftData = LivingData.get(entityAttacker).soulTheftData
            if (theftData.isThief(victim))
                event.amount = event.amount + EXTRA_DAMAGE
        }
    }
}
