package aurocosh.divinefavor.common.tasks.base;

import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BaseTask {
    private boolean isRunning;

    protected final World world;
    public BaseTask(World world) {
        this.world = world;
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected boolean isSameDimension(World world) {
        return this.world.provider.getDimension() == world.provider.getDimension();
    }

    public void start() {
        isRunning = true;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void finish() {
        isRunning = false;
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}