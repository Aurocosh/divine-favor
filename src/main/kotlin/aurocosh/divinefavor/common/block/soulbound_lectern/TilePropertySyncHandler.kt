package aurocosh.divinefavor.common.block.soulbound_lectern

import net.minecraft.tileentity.TileEntity

class TilePropertySyncHandler(var autoSync: Boolean = true) {
    fun sync(tileEntity: TileEntity) {
        if (!autoSync)
            return
        tileEntity.markDirty()
        val pos = tileEntity.pos
        val world = tileEntity.world ?: return
        val blockState = world.getBlockState(pos)
        world.notifyBlockUpdate(pos, blockState, blockState, 3)
    }
}