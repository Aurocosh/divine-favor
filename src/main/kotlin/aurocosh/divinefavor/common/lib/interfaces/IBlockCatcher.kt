package aurocosh.divinefavor.common.lib.interfaces

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent

interface IBlockCatcher {
    fun catchDrops(player: EntityPlayer, stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent)
}