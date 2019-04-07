package aurocosh.divinefavor.common.network.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class NetworkWrappedClientMessage extends NetworkClientMessage {
    private static SimpleNetworkWrapper networkWrapper;

    public NetworkWrappedClientMessage() {
    }

    public static void setNetworkWrapper(SimpleNetworkWrapper wrapper) {
        networkWrapper = wrapper;
    }

    public void sendTo(EntityPlayer player) {
        if (player instanceof EntityPlayerMP)
            networkWrapper.sendTo(this, (EntityPlayerMP) player);
    }

    public void sendToAllAround(World world, BlockPos pos, int range) {
        networkWrapper.sendToAllAround(this, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
    }
}
