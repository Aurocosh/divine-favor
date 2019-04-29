package aurocosh.divinefavor.common.muliblock.common

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.muliblock.IMultiblockController
import net.minecraft.world.World
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
object MultiblockWatcher {
    private val controllers = HashMap<World, MutableSet<IMultiblockController>>()

    fun registerController(controller: IMultiblockController) {
        val controllerSet = getOrMakeControllerSet(controller.getWorld())
        controllerSet.add(controller)
    }

    fun unRegisterController(controller: IMultiblockController) {
        val controllerSet = controllers[controller.getWorld()] ?: return
        controllerSet.remove(controller)
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onBreakEvent(event: BlockEvent.BreakEvent) {
        val controllerSet = controllers[event.world] ?: return
        val controllers = controllerSet.toTypedArray()

        val position = event.pos
        for (controller in controllers) {
            val multiblockInstance = controller.getMultiblockInstance()
            if (multiblockInstance?.isSolidPart(position) == true)
                controller.multiblockDamaged(event.player, event.world, event.pos, event.state)
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onPlaceEvent(event: BlockEvent.PlaceEvent) {
        val controllerSet = controllers[event.world] ?: return

        val position = event.pos
        for (controller in controllerSet) {
            val multiblockInstance = controller.getMultiblockInstance()
            if (multiblockInstance?.isSupposedToBeEmpty(position) == true)
                controller.multiblockDamaged(event.player, event.world, event.pos, event.state)
        }
    }

    private fun getOrMakeControllerSet(world: World): MutableSet<IMultiblockController> {
        var controllerSet = controllers[world]
        if (controllerSet != null)
            return controllerSet

        controllerSet = HashSet()
        controllers[world] = controllerSet
        return controllerSet
    }
}