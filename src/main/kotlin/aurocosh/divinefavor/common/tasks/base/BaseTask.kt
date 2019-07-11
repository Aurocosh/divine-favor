package aurocosh.divinefavor.common.tasks.base

import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

open class BaseTask(protected val world: World) {
    var isRunning: Boolean = false
        private set

    private val finishActions = ArrayList<(World) -> Unit>()
    fun addFinishAction(action: (World) -> Unit) = finishActions.add(action)

    protected fun isSameDimension(world: World): Boolean {
        return this.world.provider.dimension == world.provider.dimension
    }

    open fun start() {
        isRunning = true
        MinecraftForge.EVENT_BUS.register(this)
        finishActions.forEach { it.invoke(world) }
    }

    fun finish() {
        isRunning = false
        MinecraftForge.EVENT_BUS.unregister(this)
    }
}