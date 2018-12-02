package aurocosh.divinefavor.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public abstract class TickableTileEntity extends TileEntity implements ITickable{
    private int tickRate;
    private int tickCounter;

    private boolean tickOnClient;
    private boolean tickOnServer;

    private boolean tickingOnThisSideEnabled;

    public TickableTileEntity(boolean tickOnClient, boolean tickOnServer) {
        tickRate = 40;
        tickCounter = 0;
        this.tickOnClient = tickOnClient;
        this.tickOnServer = tickOnServer;
        tickingOnThisSideEnabled = false;
    }

    @Override
    public void setWorld(World worldIn) {
        super.setWorld(worldIn);
        boolean isClient = world.isRemote;
        tickingOnThisSideEnabled = isClient && tickOnClient || !isClient && tickOnServer;
    }

    public void setTickRateInSeconds(int seconds) {
        this.tickRate = seconds * 20;
    }

    public void setTickRate(int tickRate) {
        this.tickRate = tickRate;
    }

    @Override
    public final void update() {
        if(!tickingOnThisSideEnabled)
            return;
        tickCounter++;
        if (tickCounter < tickRate)
            return;
        tickCounter = 0;
        updateFiltered();
    }

    protected abstract void updateFiltered();
}