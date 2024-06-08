package aurocosh.divinefavor.common.area

import aurocosh.divinefavor.DivineFavor
import net.minecraft.world.World
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object WorldAreaWatcher {
    private val watchersToDelete = ArrayList<WatcherToDelete>()
    private val watchers = HashMap<World, MutableSet<IAreaWatcher>>()

    fun registerWatcher(watcher: IAreaWatcher) {
        val controllerSet = watchers.computeIfAbsent(watcher.getAreaWorld()) { HashSet() }
        controllerSet.add(watcher)
    }

    fun unRegisterController(watcher: IAreaWatcher) {
        watchersToDelete.add(WatcherToDelete(watcher.getAreaWorld(), watcher))
    }

    private fun cleanup() {
        for (watcher in watchersToDelete) {
            val controllerSet = watchers[watcher.world]
            controllerSet?.remove(watcher.watcher)
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @JvmStatic
    fun onBlockChanged(event: BlockEvent) {
        cleanup()
        val controllerSet = watchers[event.world] ?: return
        val position = event.pos
        for (areaWatcher in controllerSet)
            if (areaWatcher.getArea().isApartOfArea(position))
                areaWatcher.blockChanged(event.world, event.pos, event.state)
    }
}