package aurocosh.divinefavor.common.muliblock.common;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.muliblock.IMultiblockController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class MultiblockWatcher {
    private static Map<World, Set<IMultiblockController>> controllers = new HashMap<>();

    public static void registerController(IMultiblockController controller) {
        Set<IMultiblockController> controllerSet = getOrMakeControllerSet(controller.getWorld());
        controllerSet.add(controller);
    }

    public static void unRegisterController(IMultiblockController controller) {
        Set<IMultiblockController> controllerSet = controllers.get(controller.getWorld());
        if (controllerSet == null)
            return;
        controllerSet.remove(controller);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        Set<IMultiblockController> controllerSet = controllers.get(event.getWorld());
        if (controllerSet == null)
            return;

        IMultiblockController[] controllers = new IMultiblockController[controllerSet.size()];
        controllers = controllerSet.toArray(controllers);

        BlockPos position = event.getPos();
        for (IMultiblockController controller : controllers)
            if (controller.getMultiblockInstance().isSolidPart(position))
                controller.multiblockDamaged(event.getPlayer(), event.getWorld(), event.getPos(), event.getState());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlaceEvent(BlockEvent.PlaceEvent event) {
        Set<IMultiblockController> controllerSet = controllers.get(event.getWorld());
        if (controllerSet == null)
            return;

        BlockPos position = event.getPos();
        for (IMultiblockController controller : controllerSet)
            if (controller.getMultiblockInstance().isSupposedToBeEmpty(position))
                controller.multiblockDamaged(event.getPlayer(), event.getWorld(), event.getPos(), event.getState());
    }

    private static Set<IMultiblockController> getOrMakeControllerSet(World world) {
        Set<IMultiblockController> controllerSet = controllers.get(world);
        if (controllerSet != null)
            return controllerSet;

        controllerSet = new HashSet<>();
        controllers.put(world, controllerSet);
        return controllerSet;
    }
}