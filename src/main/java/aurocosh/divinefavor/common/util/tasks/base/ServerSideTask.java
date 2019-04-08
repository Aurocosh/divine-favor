package aurocosh.divinefavor.common.util.tasks.base;

import net.minecraft.world.World;

public class ServerSideTask extends BaseTask {
    public ServerSideTask(World world) {
        super(world);
    }

    @Override
    public void start() {
        if (!world.isRemote)
            super.start();
    }
}