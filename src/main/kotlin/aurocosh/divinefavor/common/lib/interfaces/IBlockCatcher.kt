package aurocosh.divinefavor.common.lib.interfaces

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent

interface IBlockCatcher {
    fun catch(player: EntityPlayer, stack: ItemStack, event: BlockEvent.HarvestDropsEvent)
}