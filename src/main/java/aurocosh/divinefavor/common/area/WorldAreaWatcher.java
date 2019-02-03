package aurocosh.divinefavor.common.area;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

@Mod.EventBusSubscriber
public class WorldAreaWatcher {
    private static List<WatcherToDelete> watchersToDelete = new ArrayList<>();
    private static Map<World, Set<IAreaWatcher>> watchers = new HashMap<>();

    public static void registerWatcher(IAreaWatcher watcher) {
        Set<IAreaWatcher> controllerSet = watchers.computeIfAbsent(watcher.getWorld(), world -> new HashSet<>());
        controllerSet.add(watcher);
    }

    public static void unRegisterController(IAreaWatcher watcher) {
        watchersToDelete.add(new WatcherToDelete(watcher.getWorld(), watcher));
    }

    private static void cleanup(){
        for (WatcherToDelete watcher : watchersToDelete) {
            Set<IAreaWatcher> controllerSet = watchers.get(watcher.world);
            if (controllerSet != null)
                controllerSet.remove(watcher.watcher);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        cleanup();
        Set<IAreaWatcher> controllerSet = watchers.get(event.getWorld());
        if (controllerSet == null)
            return;
        Vector3i position = new Vector3i(event.getPos());
        for (IAreaWatcher iAreaWatcher : controllerSet)
            if (iAreaWatcher.getArea().isApartOfArea(position))
                iAreaWatcher.blockBroken(event.getWorld(), event.getPos(), event.getState(), event.getPlayer());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlaceEvent(BlockEvent.PlaceEvent event) {
        cleanup();
        Set<IAreaWatcher> controllerSet = watchers.get(event.getWorld());
        if (controllerSet == null)
            return;
        Vector3i position = new Vector3i(event.getPos());
        for (IAreaWatcher iAreaWatcher : controllerSet)
            if (iAreaWatcher.getArea().isApartOfArea(position))
                iAreaWatcher.blockPlaced(event.getWorld(), event.getPos(), event.getState(), event.getPlayer());
    }
}