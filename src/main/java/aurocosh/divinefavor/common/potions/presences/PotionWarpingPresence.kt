package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionWarpingPresence : ModPotion("warping_presence", true, 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divineCustomData.warpingPresenceData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onEntityDamaged(event: EnderTeleportEvent) {
            val entity = event.entity as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModBlessings.warping_presence))
                return
            if (!entity.world.isRemote && entity.divineCustomData.warpingPresenceData.tryLuck()) {
                entity.removePotionEffect(ModBlessings.warping_presence)
                entity.addItemStackToInventory(ItemStack(ModCallingStones.calling_stone_endererer))
            }
        }
    }
}
