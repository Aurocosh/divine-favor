package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber
public class MultiBlockWatcher {
    private static Map<World,Set<IMultiblockController>> controllers = new HashMap<>();

    public static void registerController(IMultiblockController controller){
        Set<IMultiblockController> controllerSet = getOrMakeControllerSet(controller.getWorld());
        controllerSet.add(controller);
    }

    public static void unRegisterController(IMultiblockController controller){
        Set<IMultiblockController> controllerSet = controllers.get(controller.getWorld());
        if(controllerSet == null)
            return;
        controllerSet.remove(controller);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        Set<IMultiblockController> controllerSet = controllers.get(event.getWorld());
        if(controllerSet == null)
            return;

        Vector3i position = Vector3i.convert(event.getPos());
        for (IMultiblockController controller : controllerSet)
            if (controller.getMultiblockInstance().isPartOfMultiblock(position))
                controller.multiblockDamaged();
    }

    private static Set<IMultiblockController> getOrMakeControllerSet(World world){
        Set<IMultiblockController> controllerSet = controllers.get(world);
        if(controllerSet != null)
            return controllerSet;

        controllerSet = new HashSet<>();
        controllers.put(world,controllerSet);
        return controllerSet;
    }
}