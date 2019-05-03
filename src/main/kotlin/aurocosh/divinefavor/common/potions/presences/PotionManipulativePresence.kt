package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionManipulativePresence : ModPotion("manipulative_presence", true, 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.manipulativePresenceData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onEntityConstructing(event: BlockEvent.PlaceEvent) {
            val player = event.player
            if (!player.isPotionActive(ModBlessings.manipulative_presence))
                return
            if (event.placedBlock.block !== Blocks.PUMPKIN)
                return
            if (ModMultiBlocks.iron_golem.match(event.world, event.pos) == null)
                return

            if (player.divinePlayerData.manipulativePresenceData.tryLuck()) {
                player.removePotionEffect(ModBlessings.manipulative_presence)
                player.addItemStackToInventory(ItemStack(ModCallingStones.calling_stone_loon))
            }
        }
    }
}
