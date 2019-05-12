package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionScorchingPresence : ModPotion("scorching_presence", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.scorchingPresenceData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source
            if (!(source === DamageSource.IN_FIRE) && !(source === DamageSource.ON_FIRE))
                return
            val entity = event.entity as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModBlessings.scorching_presence))
                return
            if (!entity.world.isRemote && entity.divinePlayerData.scorchingPresenceData.tryLuck()) {
                entity.removePotionEffect(ModBlessings.scorching_presence)
                entity.addItemStackToInventory(ItemStack(ModCallingStones.calling_stone_neblaze))
            }
        }
    }
}
