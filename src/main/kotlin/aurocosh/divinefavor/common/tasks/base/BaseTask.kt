package aurocosh.divinefavor.common.tasks.base

import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

open class BaseTask(protected val world: World) {
    var isRunning: Boolean = false
        private set

    protected fun isSameDimension(world: World): Boolean {
        return this.world.provider.dimension == world.provider.dimension
    }

    open fun start() {
        isRunning = true
        MinecraftForge.EVENT_BUS.register(this)
    }

    fun finish() {
        isRunning = false
        MinecraftForge.EVENT_BUS.unregister(this)
    }
}