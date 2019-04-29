package aurocosh.divinefavor.common.network.base;

import aurocosh.divinefavor.common.network.base.message.NetworkClientMessage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class WrappedClientMessage extends NetworkClientMessage {
    private static SimpleNetworkWrapper networkWrapper;

    public WrappedClientMessage() {
    }

    public static void setNetworkWrapper(SimpleNetworkWrapper wrapper) {
        networkWrapper = wrapper;
    }

    public void sendTo(EntityPlayer player) {
        if (player instanceof EntityPlayerMP)
            networkWrapper.sendTo(this, (EntityPlayerMP) player);
    }

    public void sendTo(EntityLivingBase player) {
        if (player instanceof EntityPlayerMP)
            networkWrapper.sendTo(this, (EntityPlayerMP) player);
    }

    public void sendToAllAround(World world, BlockPos pos, int range) {
        networkWrapper.sendToAllAround(this, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
    }
}
