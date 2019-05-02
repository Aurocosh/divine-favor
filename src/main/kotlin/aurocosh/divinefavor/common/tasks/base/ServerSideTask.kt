package aurocosh.divinefavor.common.tasks.base

import net.minecraft.world.World

open class ServerSideTask(world: World) : BaseTask(world) {
    override fun start() {
        if (!world.isRemote)
            super.start()
    }
}