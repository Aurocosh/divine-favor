package aurocosh.divinefavor.common.undo

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

abstract class UndoOperation {
    abstract fun perform(player: EntityPlayer, world: World)
}