package aurocosh.divinefavor.common.area;

import net.minecraft.world.World;

class WatcherToDelete {
    public final World world;
    public final IAreaWatcher watcher;

    public WatcherToDelete(World world, IAreaWatcher watcher) {
        this.world = world;
        this.watcher = watcher;
    }
}
