package aurocosh.divinefavor.common.lib.interfaces

import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent

interface IBlockCatcher {
    fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent)
}